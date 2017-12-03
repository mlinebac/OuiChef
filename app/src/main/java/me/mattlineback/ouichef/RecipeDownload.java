package me.mattlineback.ouichef;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeDownload extends AppCompatActivity {
    FirebaseDatabase mDB;
    DatabaseReference myRef;

    @BindView(R2.id.button_home) Button home;
    @BindView(R2.id.recipe_view_download) RecyclerView recipeRecyclerView;
    @BindView(R2.id.download_recipes_button) Button btnDownload;
    @BindView(R2.id.name_recipes) EditText recipeName;
    @BindView(R2.id.recipe_title) TextView recipeTitle;
    @BindView(R2.id.url_recipe) EditText recipeURL;

    ArrayList<RecipeItem> recipeItems;
    LinearLayoutManager linearLayoutManager;
    RecipesAdapter adapter;
    private final String USER_URL = "https://spreadsheets.google.com/tq?key=";
    private String USER_KEY;
    String TAG = "RecipeDownload";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_download_layout);
        ButterKnife.bind(this);
        recipeItems = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        recipeRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecipesAdapter(RecipeDownload.this, recipeItems);
        recipeRecyclerView.setAdapter(adapter);

        this.mDB = FirebaseDatabase.getInstance();
        this.myRef = mDB.getReference("recipes");

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connMgr != null;
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            btnDownload.setEnabled(true);
        } else {
            btnDownload.setEnabled(false);
        }
    }

    public void buttonClickHandler(View view) {
        String uriString = recipeURL.getText().toString();
        Uri uri = Uri.parse(uriString);
        List<String> pathsegs;
        pathsegs = uri.getPathSegments();
        for (int i = 0; i < pathsegs.size(); i++){
            if(pathsegs.get(i).length() > 20){
                USER_KEY = pathsegs.get(i);
            }
        }
        Log.d(TAG, "user key = " + USER_KEY);
        ClipboardManager clipboard = (ClipboardManager)
                getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newUri(getContentResolver(),"URI", uri);
        clipboard.setPrimaryClip(clip);

        new DownloadWebpageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                processJson(object);
            }
        }).execute(USER_URL+USER_KEY);
    }

    private void processJson(JSONObject object) {
        try {
            JSONArray rows = object.getJSONArray("rows");
            String name;
            RecipeItem recipeItem;
            for (int r = 0; r < rows.length(); ++r) {
                JSONObject row = rows.getJSONObject(r);
                JSONArray columns = row.getJSONArray("c");

                String ingredient = columns.getJSONObject(0).getString("v");
                int amount = columns.getJSONObject(1).getInt("v");
                String unit = columns.getJSONObject(2).getString("v");
                String instruction = columns.getJSONObject(3).getString("v");

                //entering recipe into database
                name = recipeName.getText().toString();
                recipeTitle.setText(name);
                recipeItem = new RecipeItem(ingredient, amount, unit, instruction);
                //adding each item to recipe
                recipeItems.add(recipeItem);
                //adding each item to recipe database
                myRef.child(name).push().setValue(recipeItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R2.id.button_home)
    public void submit(View view) {
        if (view == home) {
            Intent intent = new Intent(RecipeDownload.this, RecipeMenu.class);
            startActivity(intent);
            finish();
        }
    }
}
