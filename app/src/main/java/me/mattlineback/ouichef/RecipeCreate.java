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
    String TAG = "createRecipe : ";
    private DatabaseReference mRef;
    //ListAdapter adapter;
    private Query query;
    String recipeChild ="jam";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        ButterKnife.bind(this);

        this.mRef = FirebaseDatabase.getInstance().getReference("recipes");
        this.query = mRef.child("jam");

       final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);

        recipeView.setAdapter(adapter);


        query.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String newItem = snapshot.getValue().toString();
                    adapter.add(newItem);
                    Log.d(TAG, "Value is: " + newItem);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                RecipeItem newItem = dataSnapshot.getValue(RecipeItem.class);
                Log.d(TAG, "Value is: " + newItem);
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
                RecipeItem newRecipeItem = new RecipeItem(ingredient,amount,unit,instruction);
                mRef.child(recipeChild).push().setValue(newRecipeItem);
            }
        });
    }

    public void setRecipeChild(String str){
        this.recipeChild = str;

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
