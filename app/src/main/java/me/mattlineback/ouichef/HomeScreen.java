package me.mattlineback.ouichef;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeScreen extends AppCompatActivity{
    @BindView(R2.id.button_recipes) Button recipes;
    @BindView(R2.id.button_prep) Button prep;
    @BindView(R2.id.button_order) Button order;
    @BindView(R2.id.button_86) Button eightySix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        ButterKnife.bind(this);
    }

    public void getIntent(Class c){
        Intent intent = new Intent(HomeScreen.this, c);
        startActivity(intent);
        finish();
    }

    @OnClick({R2.id.button_recipes, R2.id.button_prep, R2.id.button_order, R2.id.button_86})
    public void submit(View view) {
        if (view == recipes) {
            getIntent(RecipeDisplay.class);
        }else if (view == prep){
            getIntent(PrepList.class);
        }else if(view == order){
            getIntent(OrderList.class);
        }else{
            getIntent(EightySixBoard.class);
        }
    }
}
