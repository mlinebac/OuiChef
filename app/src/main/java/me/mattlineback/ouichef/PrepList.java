package me.mattlineback.ouichef;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrepList extends AppCompatActivity{
    @BindView(R2.id.button_home)
    Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prep_list);
        ButterKnife.bind(this);
    }

    @OnClick(R2.id.button_home)
    public void submit(View view) {
        if (view == home) {
            Intent intent = new Intent(PrepList.this, HomeScreen.class);
            startActivity(intent);
            finish();
        }

    }
}
