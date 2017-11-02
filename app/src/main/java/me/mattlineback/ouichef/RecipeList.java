package me.mattlineback.ouichef;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeList extends AppCompatActivity {
    FirebaseDatabase mDB;
    DatabaseReference myRef;

    @BindView(R2.id.button_home) Button home;
    @BindView(R2.id.recipe_view) ListView listView;
    @BindView(R2.id.download_recipes_button) Button btnDownload;
    @BindView(R2.id.add_recipe_button) Button btnRecipe;
    @BindView(R2.id.name_recipes) EditText recipeName;

    ArrayList<RecipeItem> recipeItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);

        this.mDB = FirebaseDatabase.getInstance();
        this.myRef = mDB.getReference("Recipes");

/*
        final FirebaseListAdapter<Recipe> adapter = new FirebaseListAdapter<Recipe>(
                this, Recipe.class, R.layout.activity_recipe, myRef
        ) {

            @Override
            protected void populateView(View v, Recipe model, int position) {
                TextView listRecipe = v.findViewById(android.R.id.text1);
                //listRecipe.setTextColor(Color.WHITE);
                String str = model.getRecipeItem();
                (listRecipe).setText(str);
            }
        };
        listView.setAdapter(adapter);

*/
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            btnDownload.setEnabled(true);
        } else {
            btnDownload.setEnabled(false);
        }

    }

    public void buttonClickHandler(View view) {
        new DownloadWebpageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                processJson(object);
            }
        }).execute("https://spreadsheets.google.com/tq?key=1i0pEwEDjCDJfAesI9AadFkhhFaYxAKB9rEIiIlAlvxQ");
    }
    private void processJson(JSONObject object) {
        try {
            JSONArray rows = object.getJSONArray("rows");

            for (int r = 0; r < rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");

                //int position = columns.getJSONObject(0).getInt("v");
                String ingredient = columns.getJSONObject(1).getString("v");
                int amount = columns.getJSONObject(2).getInt("v");
                String unit = columns.getJSONObject(3).getString("v");
                String instruction = columns.getJSONObject(4).getString("v");

                RecipeItem recipeItem = new RecipeItem(ingredient, amount, unit, instruction);
                recipeItems.add(recipeItem);
                final String[] str = new String[1];
                btnRecipe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       str[0] = recipeName.getText().toString();
                       myRef.push().setValue(str[0]);
                    }
                });

                myRef.push().child(str[0]).setValue(recipeItem);
            }

            final RecipesAdapter adapter = new RecipesAdapter(this, R.layout.activity_recipe, recipeItems);
            listView.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @OnClick(R2.id.button_home)
    public void submit(View view) {
        if (view == home) {
            Intent intent = new Intent(RecipeList.this, HomeScreen.class);
            startActivity(intent);
            finish();
        }

    }
}
