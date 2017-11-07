package me.mattlineback.ouichef;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
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
    private DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase;
    private Query query;
    String recipeChild =" ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        ButterKnife.bind(this);

        this.mFirebaseDatabase = FirebaseDatabase.getInstance();
        this.myRef = mFirebaseDatabase.getReference("recipes");
        this.query = myRef.child("jam");

        FirebaseListOptions<RecipeItem> options = new FirebaseListOptions.Builder<RecipeItem>()
                .setLayout(R.layout.recipe_list_layout)
                .setQuery(query.orderByKey(), RecipeItem.class)
                .build();
        final FirebaseListAdapter<RecipeItem> adapter = new FirebaseListAdapter<RecipeItem>(options) {
            @Override
            protected void populateView(View view, RecipeItem item, int i) {
                Log.d(TAG, "populate Value is: " + item);
                TextView listItemShow = view.findViewById(android.R.id.text1);
                listItemShow.setTextColor(Color.WHITE);
                listItemShow.setAllCaps(true);
                listItemShow.setTextSize(20);
                //listItemShow.setPaintFlags(listItemShow.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                listItemShow.setText(item.getRecipeItem());
            }
        };

        recipeView.setAdapter(adapter);


        query.orderByKey().addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                RecipeItem newItem = dataSnapshot.getValue(RecipeItem.class);
                Log.d(TAG, "Value is: " + newItem.toMap());
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
                //setRecipeChild(newRecipeName);
                String ingredient = recipeIngredient.getText().toString();
                int amount = Integer.parseInt(recipeAmt.getText().toString());
                String unit = recipeUnit.getText().toString();
                String instruction = recipeInstructions.getText().toString();
                RecipeItem newRecipeItem = new RecipeItem(ingredient,amount,unit,instruction);
                myRef.child(newRecipeName).push().setValue(newRecipeItem);
            }
        });
    }

    public void setRecipeChild(String str){
        this.recipeChild = recipeName.getText().toString();

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
