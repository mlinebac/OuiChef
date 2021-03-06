package me.mattlineback.ouichef;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeSearch extends AppCompatActivity {

    DatabaseReference myRef;
    Query query;
    ArrayList<RecipeItem> recipeList;
    RecipesAdapter adapter;
    ArrayList<String> recipeTitleList = new ArrayList<>();
    ArrayAdapter<String> titleAdapter;
    String recipe = " ";
    LinearLayoutManager linearLayoutManager;
    String TAG = "RecipeSearch";

    @BindView(R2.id.button_home) Button home;
    @BindView(R2.id.scaler_box) EditText scalerBox;
    @BindView(R2.id.double_button) Button scalerButton;
    @BindView(R2.id.search_recipes) Button searchBtn;
    @BindView(R2.id.name_recipes) EditText searchRecipe;
    @BindView(R2.id.recipe_title) TextView recipeTitle;
    @BindView(R2.id.recipe_view) RecyclerView recipeView;
    @BindView(R2.id.recipe_title_list) ListView recipeTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);
        ButterKnife.bind(this);
        recipeList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        recipeView.setLayoutManager(linearLayoutManager);
        adapter = new RecipesAdapter(this, recipeList);
        recipeView.setAdapter(adapter);
        this.myRef = FirebaseDatabase.getInstance().getReference("recipes");
        query = myRef.orderByKey();

        titleAdapter = new ArrayAdapter<>(
                this,android.R.layout.simple_list_item_1, recipeTitleList
        );
        recipeTitles.setBackgroundColor(Color.WHITE);

        recipeTitles.setAdapter(titleAdapter);
        titleAdapter.notifyDataSetChanged();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                        String recipe = dataSnapshot1.getKey();
                        recipeTitleList.add(recipe);
                        Log.d(TAG, "recipe Title Added" + dataSnapshot1.getKey());
                    }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        scalerButton.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot.getKey().equals(recipe)) {
                        scaleItems(dataSnapshot);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot.getKey().equals(recipe)) {
                        scaleItems(dataSnapshot);
                    }
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    });
        searchBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                recipeTitleList.clear();
                recipe = searchRecipe.getText().toString();
                searchRecipe.setText("");
                recipeTitle.setText(recipe);
                myRef.child("recipeSearched").setValue(recipe);
                recipeList.clear();


        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getKey().equals(recipe)) {
                    getAllItems(dataSnapshot);
                    }
                }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals(recipe)){
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        RecipeItem changedItem = snapshot.getValue(RecipeItem.class);
                        recipeList.add(changedItem);
                        adapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
            }
        });
            }//onCreate
                private void scaleItems(DataSnapshot dataSnapshot) {
                    recipeList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        RecipeItem item = snapshot.getValue(RecipeItem.class);
                        if (item == null) throw new AssertionError();
                        double amount = item.getAmount();
                        int scaler = Integer.parseInt(scalerBox.getText().toString());
                        item.scaleAmount(amount, scaler);
                        Log.d(TAG, "doubled Amount = " + item.getAmount());
                        recipeList.add(item);
                    }

                    adapter.notifyDataSetChanged();
                    adapter = new RecipesAdapter(RecipeSearch.this, recipeList);
                    recipeView.setAdapter(adapter);
                }
                private void getAllItems(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        RecipeItem item = snapshot.getValue(RecipeItem.class);
                        recipeList.add(item);
                        Log.d(TAG, "recipeItem Added" + item);
                    }
                    titleAdapter.notifyDataSetChanged();
                    adapter.notifyDataSetChanged();
                    adapter = new RecipesAdapter(RecipeSearch.this, recipeList);
                    recipeView.setAdapter(adapter);
                }

    @OnClick(R2.id.button_home)
    public void submit(View view) {
        if (view == home) {
            Intent intent = new Intent(RecipeSearch.this, RecipeMenu.class);
            startActivity(intent);
            finish();
        }

    }
}
