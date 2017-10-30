package me.mattlineback.ouichef;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeList extends AppCompatActivity {

    @BindView(R2.id.button_home)
    Button home;
    @BindView(R2.id.recipe_view)
    ListView listView;
    @BindView(R2.id.download_recipes_button) Button btnDownload;
    ArrayList<Recipe> recipes = new ArrayList<Recipe>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);

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

                int position = columns.getJSONObject(0).getInt("v");
                String ingredient = columns.getJSONObject(1).getString("v");
                int amount = columns.getJSONObject(2).getInt("v");
                String amountUnit = columns.getJSONObject(3).getString("v");

                Recipe recipe = new Recipe(position, ingredient, amount, amountUnit);
                recipes.add(recipe);
            }

            final RecipesAdapter adapter = new RecipesAdapter(this, R.layout.activity_recipe, recipes);
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
