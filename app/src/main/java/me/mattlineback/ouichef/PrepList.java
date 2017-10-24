package me.mattlineback.ouichef;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrepList extends AppCompatActivity {
    private final String TAG = "prepListActivity";
    FirebaseDatabase mDB;
    DatabaseReference myRef;
    ListAdapter listAdapt;

    @BindView(R2.id.button_home)
    Button home;
    @BindView(R2.id.prep_item_button)
    Button addItemButton;
    @BindView(R2.id.add_prep_item)
    EditText addPrepItem;
    @BindView(R2.id.action_delete_all)
    Button deleteList;
    @BindView(R2.id.prep_list)
    RecyclerView prepList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prep_list);
        ButterKnife.bind(this);

        this.mDB = FirebaseDatabase.getInstance();
        this.myRef = mDB.getReference("prepList");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<PrepItem, PrepViewHolder> firebaseAdapter = new FirebaseRecyclerAdapter<PrepItem, PrepViewHolder>(
                PrepItem.class,
                android.R.layout.activity_list_item,
                PrepViewHolder.class,
                myRef

        ) {
            @Override
            protected void populateViewHolder(PrepViewHolder viewHolder, PrepItem model, int position) {
                viewHolder.setPrepItem(model.getPrepItem());
            }
        };
        prepList.setAdapter(firebaseAdapter);
    }

    public static class PrepViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public PrepViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

        }

        public void setPrepItem(String prepItem) {

            TextView post_prepItem = (TextView) mView.findViewById(R.id.add_prep_item);
            post_prepItem.setText(prepItem);
        }
    }
}
/*
        deleteList.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        myRef.removeValue();
    }
    });

        addItemButton.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        PrepItem item = new PrepItem(addPrepItem.getText().toString());
        myRef.push().setValue(item); //removeValue();
    }
    });

        prepList.setOnItemClickListener(new AdapterView.OnItemClickListener()

    {
        @Override
        public void onItemClick (AdapterView < ? > adapterView, View view,int i, long l){
        FirebaseRecyclerAdapter itemtoRemove = adapter.getRef(i);
        itemtoRemove.removeValue();

    }
    });
    final FirebaseListAdapter<PrepItem> adapter = new FirebaseListAdapter<PrepItem>(
            this, PrepItem.class, android.R.layout.activity_list_item, myRef

    ) {
        @Override
        protected void populateView(View view, PrepItem item, int i) {
            TextView prepItemShow = view.findViewById(android.R.id.text1);
            prepItemShow.setTextColor(Color.WHITE);
            prepItemShow.setAllCaps(true);
            prepItemShow.setTextSize(20);
            (prepItemShow).setText(item.getPrepItemText());
        }
    };

        prepList.setAdapter(adapter);


    @OnClick(R2.id.button_home)
    public void submit(View view) {
        if (view == home) {
            Intent intent = new Intent(PrepList.this, HomeScreen.class);
            startActivity(intent);
            finish();
        }

    }
}
*/