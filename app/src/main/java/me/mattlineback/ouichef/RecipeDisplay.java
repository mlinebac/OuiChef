package me.mattlineback.ouichef;

import android.content.Intent;
import android.graphics.Color;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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


    FirebaseListAdapter<RecipeItem> adapter;
    String recipe = " ";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_display);
        ButterKnife.bind(this);

        this.mDB = FirebaseDatabase.getInstance();
        this.myRef = mDB.getReference("recipes");
        this.mRecipe = myRef.child(recipe);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipe = searchRecipe.getText().toString();
                mRecipe = myRef.child(recipe);

            }

        });



    }
    @Override
    public void onStart(){
        super.onStart();
        adapter.startListening();

        Query query = mRecipe.orderByKey();

        FirebaseListOptions<RecipeItem> options = new FirebaseListOptions.Builder<RecipeItem>()
                .setLayout(android.R.layout.activity_list_item)
                .setQuery(query, RecipeItem.class)
                .build();

        adapter = new FirebaseListAdapter<RecipeItem>(options) {

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
            Intent intent = new Intent(RecipeDisplay.this, RecipeMenu.class);
            startActivity(intent);
            finish();
        }

    }
}
