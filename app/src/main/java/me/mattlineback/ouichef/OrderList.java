package me.mattlineback.ouichef;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderList extends AppCompatActivity {
    private OrderListAdapter orderListAdapter;
    private List<ListItem> allItems;
    private RecyclerView orderListRV;

    DatabaseReference myRef;
    LinearLayoutManager linearLayoutManager;

    @BindView(R2.id.button_home)
    Button home;
    @BindView(R2.id.order_item_button)
    Button addItemButton;
    @BindView(R2.id.add_order_item)
    EditText addOrderItem;
    @BindView(R2.id.action_delete_all)
    Button deleteList;


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);
        allItems = new ArrayList<>();
        myRef = FirebaseDatabase.getInstance().getReference("orderItems");
        orderListRV = findViewById(R.id.order_list);
        linearLayoutManager = new LinearLayoutManager(this);
        orderListRV.setLayoutManager(linearLayoutManager);
        orderListAdapter = new OrderListAdapter(OrderList.this, allItems);
        orderListRV.setAdapter(orderListAdapter);

        addItemButton.setOnClickListener(new View.OnClickListener() {
            /**
             * @param view
             */
            @Override
            public void onClick(View view) {
                String enteredItem = addOrderItem.getText().toString();
                ListItem item = new ListItem(enteredItem);
                myRef.push().setValue(item);
                addOrderItem.setText("");
            }
        });
        deleteList.setOnClickListener(new View.OnClickListener() {
            /**
             * @param view
             */
            @Override
            public void onClick(View view) {
                myRef.removeValue();
            }
        });

        myRef.addChildEventListener(new ChildEventListener() {
            /**
             * @param dataSnapshot
             * @param s
             */
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getAllItems(dataSnapshot);
            }

            /**
             * @param dataSnapshot
             * @param s
             */
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getAllItems(dataSnapshot);
            }

            /**
             * @param dataSnapshot
             */
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                ItemDeletion(dataSnapshot);

            }

            /**
             * @param dataSnapshot
             * @param s
             */
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            /**
             * @param databaseError
             */
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    /**
     * @param dataSnapshot
     */
    private void getAllItems(DataSnapshot dataSnapshot) {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            String item = snapshot.getValue(String.class);
            allItems.add(new ListItem(item));
            orderListAdapter = new OrderListAdapter(OrderList.this, allItems);
            orderListRV.setAdapter(orderListAdapter);
        }
    }

    private void ItemDeletion(DataSnapshot dataSnapshot) {
        for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
            String item = orderSnapshot.getValue(String.class);
            for (int i = 0; i < allItems.size(); i++) {
                if (allItems.get(i).getListItem().equals(item)) {
                    allItems.remove(i);
                    myRef.child(item).removeValue();
                }
            }
            String TAG = "OrderListActivity";
            Log.d(TAG, "orderItem Removed" + item);
            orderListAdapter.notifyDataSetChanged();
            orderListAdapter = new OrderListAdapter(OrderList.this, allItems);
            orderListRV.setAdapter(orderListAdapter);
        }

    }

    /**
     * Home button in view is clicked create new Intent with HomeScreen Class and start activity
     * @param view
     */
    @OnClick(R2.id.button_home)
    public void submit(View view) {
        if (view == home) {
            Intent intent = new Intent(OrderList.this, HomeScreen.class);
            startActivity(intent);
            finish();
        }
    }
}

