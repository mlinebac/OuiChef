package me.mattlineback.ouichef;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    ListView recipeView;

    String TAG = "createRecipe";
    private DatabaseReference myRef;
    FirebaseListAdapter<RecipeItem> adapter;
    private Query query;
    String recipeChild = "jam";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        ButterKnife.bind(this);

        this.myRef = FirebaseDatabase.getInstance().getReference("recipes/");
        query = myRef.orderByChild("name");
        Log.d(TAG, "query = " + query);
        recipeNameBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String newRecipeName =  recipeName.getText().toString();
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
                RecipeItem newRecipeItem = new RecipeItem(newRecipeName, ingredient,amount,unit,instruction);
                myRef.push().setValue(newRecipeItem);
                adapter.startListening();
            }
        });

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                   //ERROR HERE//
                    RecipeItem newItem = snapshot.getValue(RecipeItem.class);

                    Log.d(TAG, "Value is: " + newItem.getRecipeItem());

                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                RecipeItem changedItem = dataSnapshot.getValue(RecipeItem.class);
                Log.d(TAG, "Value is: " + changedItem);
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

    @Override
    public void onStart(){
        super.onStart();
        FirebaseListOptions<RecipeItem> options = new FirebaseListOptions.Builder<RecipeItem>()
                .setLayout(android.R.layout.activity_list_item)
                .setQuery(query, RecipeItem.class)
                .build();
        adapter = new FirebaseListAdapter<RecipeItem>(options) {
            @Override
            protected void populateView(View v, RecipeItem model, int position) {
                TextView listRecipe = v.findViewById(android.R.id.text1);
                Log.d(TAG, "populate: " + listRecipe);
                listRecipe.setTextColor(Color.WHITE);
                String str = model.getRecipeItem();
                (listRecipe).setText(str);
            }
        };
        recipeView.setAdapter(adapter);
    }
    public void setRecipeChild(String str){
        this.recipeChild = str;

    }
    @Override
    public void onStop(){
        super.onStop();
        if(adapter != null){
            adapter.stopListening();
        }
    }
    @OnClick(R2.id.button_home)
    public void submit(View view) {
        if (view == home) {
            Intent intent = new Intent(RecipeCreate.this, RecipeMenu.class);
            startActivity(intent);
            finish();
        }

    }
}
