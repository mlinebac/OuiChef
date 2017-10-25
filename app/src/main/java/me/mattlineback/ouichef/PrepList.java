package me.mattlineback.ouichef;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.xmlpull.v1.XmlPullParser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrepList extends AppCompatActivity {
    private final String TAG = "prepListActivity";
    FirebaseDatabase mDB;
    DatabaseReference myRef;

    @BindView(R2.id.button_home)
    Button home;
    @BindView(R2.id.prep_item_button)
    Button addItemButton;
    @BindView(R2.id.add_prep_item)
    EditText addPrepItem;
    @BindView(R2.id.action_delete_all)
    Button deleteList;
    @BindView(R2.id.prep_list)
    ListView prepList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prep_list);
        ButterKnife.bind(this);

        this.mDB = FirebaseDatabase.getInstance();
        this.myRef = mDB.getReference("prepList");



/*
        private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myRef.removeValue();
            }
        };
       */
        prepList.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                View parentRow = (View) view.getParent();
                ListView listView = (ListView) parentRow.getParent();
                final int position = listView.getPositionForView(parentRow);



            }

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myRef.removeValue();
            }
        });



        deleteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.removeValue();
            }
        });
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderItem item = new OrderItem(addPrepItem.getText().toString());
                myRef.push().setValue(item);
            }
        });

        final FirebaseListAdapter<PrepItem> adapter = new FirebaseListAdapter<PrepItem>(
                this, PrepItem.class, android.R.layout.activity_list_item, myRef

        ) {
            @Override
            protected void populateView(View view, PrepItem item, int i) {
                TextView listItemShow = view.findViewById(android.R.id.text1);
                listItemShow.setTextColor(Color.WHITE);
                listItemShow.setAllCaps(true);
                listItemShow.setTextSize(20);
                (listItemShow).setText(item.getPrepItem());
            }
        };

        prepList.setAdapter(adapter);

    }
    @OnClick(R2.id.button_home)
    public void submit(View view) {
        if (view == home) {
            Intent intent = new Intent(PrepList.this, HomeScreen.class);
            startActivity(intent);
            finish();
        }

    }
}
