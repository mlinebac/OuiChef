package me.mattlineback.ouichef;

import android.app.AlertDialog;
import android.app.LauncherActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.tech.TagTechnology;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderList extends AppCompatActivity {
    private final String TAG = "OrderListActivity";
    FirebaseDatabase mDB;
    DatabaseReference mOrderItemRef;
    private RecyclerView mOrderItemRecyclerView;
    private OrderItemsAdapter mAdapter;
    private ArrayList<OrderItem> myOrderItems;
    @BindView(R2.id.button_home) Button home;
    @BindView(R2.id.action_delete_all) Button deleteList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        ButterKnife.bind(this);

        this.mDB = FirebaseDatabase.getInstance();
        DatabaseReference myRef = mDB.getReference("orderList");
        mOrderItemRef = myRef.child("orderItem");
        //mOrderItemRef.setValue("Hello, World!");
        myOrderItems = new ArrayList<>();
        //mOrderItemRecyclerView = (RecyclerView)findViewById(R.id.order_list);
        //mOrderItemRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        //mOrderItemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateUI();

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar)
        deleteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                createNewOrderItem();
            }
        });

        mOrderItemRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG+"Added", dataSnapshot.getValue(OrderItem.class).toString());
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG+"Changed" ,dataSnapshot.getValue(OrderItem.class).toString());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG+"Removed", dataSnapshot.getValue(OrderItem.class).toString());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG+"Moved", dataSnapshot.getValue(OrderItem.class).toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "loadOrderItem:onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id){
            case R.id.action_delete_all:
                deleteAllOrderItems();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void createNewOrderItem() {
        // Create new List Item  at /listItem
        final String key = FirebaseDatabase.getInstance().getReference().child("orderItem").push().getKey();
        LayoutInflater li = LayoutInflater.from(this);
        View getOrderItemView = li.inflate(R.layout.content_order_list, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(getOrderItemView);

        final EditText userInput = getOrderItemView.findViewById(R.id.add_order_item);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // get user input and set it to result
                        // edit text
                        String orderItemText = userInput.getText().toString();
                        OrderItem orderItem = new OrderItem(orderItemText);
                        Map<String, Object> orderItemValues = orderItem.toMap();
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put("/orderItem/" + key, orderItemValues);
                        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);

                    }
                }).create()
                .show();

    }public void deleteAllOrderItems(){
        FirebaseDatabase.getInstance().getReference().child("orderItem").removeValue();
        myOrderItems.clear();
        mAdapter.notifyDataSetChanged();
        Toast.makeText(this,"Items Deleted Successfully",Toast.LENGTH_SHORT).show();
    }

    private void fetchData(DataSnapshot dataSnapshot) {
        OrderItem orderItem = dataSnapshot.getValue(OrderItem.class);
        myOrderItems.add(orderItem);
        updateUI();
    }

    private void updateUI(){
        mAdapter = new OrderItemsAdapter(myOrderItems);
        mOrderItemRecyclerView.setAdapter(mAdapter);
    }

    private class OrderItemsHolder extends RecyclerView.ViewHolder{
        public TextView mNameTextView;
        public OrderItemsHolder(View orderView){
            super(orderView);
            mNameTextView = itemView.findViewById(R.id.order_list);
        }
        public void bindData(OrderItem s){
            mNameTextView.setText(s.getOrderItemText());
        }
    }
    private class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsHolder> {
        private ArrayList<OrderItem> mOrderItems;

        public OrderItemsAdapter(ArrayList<OrderItem> OrderItems) {
            mOrderItems = OrderItems;
        }

        @Override
        public OrderItemsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(OrderList.this);
            View view = layoutInflater.inflate(R.layout.content_order_list, parent, false);
            return new OrderItemsHolder(view);

        }

        @Override
        public void onBindViewHolder(OrderItemsHolder holder, int position) {
            OrderItem s = mOrderItems.get(position);
            holder.bindData(s);
        }
        @Override
        public int getItemCount() {
            return mOrderItems.size();
        }
    }
        @OnClick(R2.id.button_home)
    public void submit(View view) {
        if (view == home) {
            Intent intent = new Intent(OrderList.this, HomeScreen.class);
            startActivity(intent);
            finish();
        }

    }
}
