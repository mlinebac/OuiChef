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
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

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
        prepList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id ){
              TextView tv = view.findViewById(android.R.id.text1);
              tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
              tv.setTextColor(Color.DKGRAY);
          }
        });
        //delete all items in database under prepList
        deleteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.removeValue();
            }
        });
        // add items to database
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListItem item = new ListItem(addPrepItem.getText().toString());
                myRef.push().setValue(item);
            }
        });
        Query query = myRef;

        FirebaseListOptions<ListItem> options = new FirebaseListOptions.Builder<ListItem>()
                .setLayout(android.R.layout.activity_list_item)
                .setQuery(query, ListItem.class)
                .build();
        final FirebaseListAdapter<ListItem> adapter = new FirebaseListAdapter<ListItem>(options) {
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
