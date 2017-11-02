package me.mattlineback.ouichef;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeDisplay extends AppCompatActivity {
    private final String TAG = "recipeDisplay";
    FirebaseDatabase mDB;
    DatabaseReference myRef;
    DatabaseReference mRecipe;
    @BindView(R2.id.button_home)
    Button home;
    @BindView(R2.id.double_button)
    Button doubleButton;
    @BindView(R2.id.search_recipes)
    Button searchBtn;
    @BindView(R2.id.name_recipes)
    EditText searchRecipe;
    @BindView(R2.id.recipe_view)
    ListView recipeView;
    String recipe = "biscuits";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_display);
        ButterKnife.bind(this);

        this.mDB = FirebaseDatabase.getInstance();
        this.myRef = mDB.getReference("recipes");
        this.mRecipe = myRef.child(recipe);

        searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String recipe = searchRecipe.getText().toString();
                mRecipe = myRef.child(recipe);



            }

        });
        final FirebaseListAdapter<RecipeItem> adapter = new FirebaseListAdapter<RecipeItem>(
                this, RecipeItem.class, android.R.layout.activity_list_item, mRecipe
        ) {
            @Override
            protected void populateView(View v, RecipeItem model, int position) {
                TextView listRecipe = v.findViewById(android.R.id.text1);
                listRecipe.setTextColor(Color.WHITE);
                String str = model.getRecipeItem();
                (listRecipe).setText(str);
            }
        };
        recipeView.setAdapter(adapter);

    }


    @OnClick(R2.id.button_home)
    public void submit(View view) {
        if (view == home) {
            Intent intent = new Intent(RecipeDisplay.this, HomeScreen.class);
            startActivity(intent);
            finish();
        }

    }
}
