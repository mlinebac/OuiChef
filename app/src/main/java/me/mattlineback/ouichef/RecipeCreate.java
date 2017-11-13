package me.mattlineback.ouichef;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.FirebaseOptions;
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

public class RecipeCreate extends AppCompatActivity {
    @BindView(R2.id.button_home)
    Button home;
    @BindView(R2.id.enter_recipe_name)
    EditText recipeName;
    @BindView(R2.id.enter_recipe_button)
    Button recipeNameBtn;
    @BindView(R2.id.enter_ingredient)
    EditText recipeIngredient;
    @BindView(R2.id.enter_amount)
    EditText recipeAmt;
    @BindView(R2.id.enter_unit)
    EditText recipeUnit;
    @BindView(R2.id.enter_instructions)
    EditText recipeInstructions;
    @BindView(R2.id.recipe_view_create)
    RecyclerView recipeView;

    String TAG = "createRecipe";
    private DatabaseReference myRef;
    RecipesAdapter adapter;
    private Query query;
    String recipeChild = " ";
    ArrayList<RecipeItem> recipeList;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        ButterKnife.bind(this);
        recipeList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        recipeView.setLayoutManager(linearLayoutManager);
        adapter = new RecipesAdapter(this, recipeList);
        recipeView.setAdapter(adapter);
        this.myRef = FirebaseDatabase.getInstance().getReference("recipes");
        query = myRef.orderByKey();
        Log.d(TAG, "query = " + query);
        recipeNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newRecipeName = recipeName.getText().toString();
                // myRef.push().setValue(newRecipeName);
                setRecipeChild(newRecipeName);

                String ingredient = recipeIngredient.getText().toString();
                recipeIngredient.setText("");
                int amount = Integer.parseInt(recipeAmt.getText().toString());
                recipeAmt.setText("");
                String unit = recipeUnit.getText().toString();
                recipeUnit.setText("");
                String instruction = recipeInstructions.getText().toString();
                recipeInstructions.setText("");
                RecipeItem newRecipeItem = new RecipeItem(ingredient, amount, unit, instruction);
                myRef.child(recipeChild).push().setValue(newRecipeItem);
                //adapter.startListening();
            }
        });

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getKey().equals(recipeChild)) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        RecipeItem newItem = snapshot.getValue(RecipeItem.class);
                        recipeList.add(newItem);
                        Log.d(TAG, "Added newItem = " + newItem);
                    }

                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getKey().equals(recipeChild)) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        RecipeItem changedItem = snapshot.getValue(RecipeItem.class);
                        recipeList.add(changedItem);
                        Log.d(TAG, "Changed Value is: " + changedItem);
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
    public void setRecipeChild(String str){
        this.recipeChild = str;

    }
   // @Override
   // public void onStop(){
     //   super.onStop();
       // if(adapter != null){
            //adapter.stopListening();
     //   }
   // }
    @OnClick(R2.id.button_home)
    public void submit(View view) {
        if (view == home) {
            Intent intent = new Intent(RecipeCreate.this, RecipeMenu.class);
            startActivity(intent);
            finish();
        }

    }
}
