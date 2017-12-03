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

public class PrepList extends AppCompatActivity {
    private final String TAG = "prepListActivity";
    private PrepListAdapter prepListAdapter;
    private List<ListItem> allItems;
    DatabaseReference myRef;
    LinearLayoutManager linearLayoutManager;

    @BindView(R2.id.button_home) Button home;
    @BindView(R2.id.prep_item_button) Button addItemButton;
    @BindView(R2.id.add_prep_item) EditText addPrepItem;
    @BindView(R2.id.action_delete_all) Button deleteList;
    @BindView(R2.id.prep_list) RecyclerView prepListRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prep_list);
        ButterKnife.bind(this);
        allItems = new ArrayList<>();
        myRef = FirebaseDatabase.getInstance().getReference("prepItems");
        linearLayoutManager = new LinearLayoutManager(this);
        prepListRV.setLayoutManager(linearLayoutManager);
        prepListAdapter = new PrepListAdapter(PrepList.this, allItems);
        prepListRV.setAdapter(prepListAdapter);

        addItemButton.setOnClickListener(new View.OnClickListener() {
            /**
             * @param view
             */
            @Override
            public void onClick(View view) {
                String enteredItem = addPrepItem.getText().toString();
                ListItem item = new ListItem(enteredItem);
                myRef.push().setValue(item);
                addPrepItem.setText("");
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

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getAllItems(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getAllItems(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                ItemDeletion(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getAllItems(DataSnapshot dataSnapshot) {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            String item = snapshot.getValue(String.class);
            allItems.add(new ListItem(item));
            prepListAdapter = new PrepListAdapter(PrepList.this, allItems);
            prepListRV.setAdapter(prepListAdapter);
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
            Log.d(TAG, "orderItem Removed" + item);
            prepListAdapter.notifyDataSetChanged();
            prepListAdapter = new PrepListAdapter(PrepList.this, allItems);
            prepListRV.setAdapter(prepListAdapter);
        }
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
