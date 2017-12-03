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

public class EightySixBoard extends AppCompatActivity {
    private EightySixBoardAdapter eightySixBoardAdapter;
    private List<ListItem> allItems;
    private RecyclerView eightySixRV;
    private LinearLayoutManager linearLayoutManager;
    private DatabaseReference myRef;

    @BindView(R2.id.button_home) Button home;
    @BindView(R2.id.EightySix_item_button) Button addItemButton;
    @BindView(R2.id.list_86_list) EditText addItem;
    @BindView(R2.id.action_delete_all) Button deleteList;
    String TAG = "EightySizBoardActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eighty_six_board);
        ButterKnife.bind(this);
        allItems = new ArrayList<>();
        myRef = FirebaseDatabase.getInstance().getReference("eightSixItems");
        eightySixRV = findViewById(R.id.EightSix_list);
        linearLayoutManager = new LinearLayoutManager(this);
        eightySixRV.setLayoutManager(linearLayoutManager);
        eightySixBoardAdapter = new EightySixBoardAdapter(EightySixBoard.this, allItems);
        eightySixRV.setAdapter(eightySixBoardAdapter);

        addItemButton.setOnClickListener(new View.OnClickListener() {
            /**
             * @param view
             */
            @Override
            public void onClick(View view) {
                String enteredItem = addItem.getText().toString();
                ListItem item = new ListItem(enteredItem);
                myRef.push().setValue(item);
                addItem.setText("");
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
            eightySixBoardAdapter = new EightySixBoardAdapter(EightySixBoard.this, allItems);
            eightySixRV.setAdapter(eightySixBoardAdapter);
        }
    }

    private void ItemDeletion(DataSnapshot dataSnapshot) {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            String item = snapshot.getValue(String.class);
            for (int i = 0; i < allItems.size(); i++) {
                if (allItems.get(i).getListItem().equals(item)) {
                    allItems.remove(i);
                    myRef.child(item).removeValue();
                }
            }

            Log.d(TAG, "eighty six Item Removed" + item);
            eightySixBoardAdapter.notifyDataSetChanged();
            eightySixBoardAdapter = new EightySixBoardAdapter(EightySixBoard.this, allItems);
            eightySixRV.setAdapter(eightySixBoardAdapter);
        }
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
