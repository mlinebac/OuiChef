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
import com.google.firebase.database.Query;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeSearch extends AppCompatActivity {
    private final String TAG = "RecipeSearch";

    DatabaseReference myRef;
    Query query;
    Query querySearch;
    @BindView(R2.id.button_home)
    Button home;
    @BindView(R2.id.double_button)
    Button doubleButton;
    @BindView(R2.id.search_recipes)
    Button searchBtn;
    @BindView(R2.id.name_recipes)
    EditText searchRecipe;
    @BindView(R2.id.recipe_view)
    RecyclerView recipeView;

    ArrayList<RecipeItem> recipeList;
    RecipesAdapter adapter;
    String recipe = " ";
    LinearLayoutManager linearLayoutManager;

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
        querySearch = myRef.child("recipeSearch");

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipe = searchRecipe.getText().toString();
                searchRecipe.setText("");
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
    private void getAllItems(DataSnapshot dataSnapshot) {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            RecipeItem item = snapshot.getValue(RecipeItem.class);
            recipeList.add(item);
            Log.d(TAG, "recipeItem Added" + item);
        }
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
