package me.mattlineback.ouichef;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderList extends AppCompatActivity {
    private final String TAG = "OrderListActivity";
    FirebaseDatabase mDB;
    DatabaseReference myRef;

    @BindView(R2.id.button_home) Button home;
    @BindView(R2.id.order_item_button) Button addItemButton;
    @BindView(R2.id.add_order_item) EditText addOrderItem;
    @BindView(R2.id.action_delete_all) Button deleteList;
    @BindView(R2.id.order_list) ListView orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);

        this.mDB = FirebaseDatabase.getInstance();
        this.myRef = mDB.getReference("orderList");

        orderList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id ){
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                tv.setTextColor(Color.DKGRAY);
            }
        });

        deleteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //this.items = new LinkedList<OrderItem>();
                myRef.removeValue();
            }
        });

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListItem item = new ListItem(addOrderItem.getText().toString());
                myRef.push().setValue(item);
            }
        });

        final FirebaseListAdapter<ListItem> adapter = new FirebaseListAdapter<ListItem>(
                this, ListItem.class, android.R.layout.activity_list_item, myRef

        ) {
            @Override
            protected void populateView(View view, ListItem item, int i) {
                TextView listItemShow = view.findViewById(android.R.id.text1);
                listItemShow.setTextColor(Color.WHITE);
                listItemShow.setAllCaps(true);
                listItemShow.setTextSize(20);
                //listItemShow.setPaintFlags(listItemShow.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                (listItemShow).setText(item.getListItem());
            }
        };

        orderList.setAdapter(adapter);

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
