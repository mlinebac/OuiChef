package me.mattlineback.ouichef;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import static android.graphics.Color.DKGRAY;

public class EightySixBoard extends AppCompatActivity {
    private final String TAG = "prepListActivity";
    FirebaseDatabase mDB;
    DatabaseReference myRef;


    @BindView(R2.id.button_home) Button home;
    @BindView(R2.id.EightySix_item_button)
    Button addItemButton;
    @BindView(R2.id.list_86_list)
    EditText addItem;
    @BindView(R2.id.action_delete_all)
    Button deleteList;
    @BindView(R2.id.EightSix_list)
    ListView eightySixList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eighty_six_board);
        ButterKnife.bind(this);
        this.mDB = FirebaseDatabase.getInstance();
        this.myRef = mDB.getReference("eightySixList");
        eightySixList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id ){
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                tv.setTextColor(DKGRAY);
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
                ListItem item = new ListItem(addItem.getText().toString());
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
                (listItemShow).setText(item.getListItem());
            }
        };

        eightySixList.setAdapter(adapter);

    }

    @OnClick(R2.id.button_home)
    public void submit(View view) {
        if (view == home) {
            Intent intent = new Intent(EightySixBoard.this, HomeScreen.class);
            startActivity(intent);
            finish();
        }

    }
}
