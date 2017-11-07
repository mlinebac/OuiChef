package me.mattlineback.ouichef;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeMenu extends AppCompatActivity {

    @BindView(R2.id.button_search)
    Button btnSearch;
    @BindView(R2.id.button_create)
    Button btnCreate;
    @BindView(R2.id.button_upload)
    Button btnUpload;
    @BindView(R2.id.button_home)
    Button btnHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_menu);
        ButterKnife.bind(this);
    }
    public void getIntent(Class c){
        Intent intent = new Intent(RecipeMenu.this, c);
        startActivity(intent);
        finish();
    }

    @OnClick({R2.id.button_search, R2.id.button_create, R2.id.button_upload, R2.id.button_home})
    public void submit(View view) {
        if (view == btnSearch) {
            getIntent(RecipeSearch.class);
        }else if (view == btnCreate){
            getIntent(RecipeCreate.class);
        }else if(view == btnUpload){
            getIntent(RecipeDownload.class);
        }else{
            getIntent(HomeScreen.class);
        }
    }

}
