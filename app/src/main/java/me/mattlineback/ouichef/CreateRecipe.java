package me.mattlineback.ouichef;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateRecipe extends AppCompatActivity {
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
    @BindView(R2.id.enter_ingredient_button)
    Button recipeIngredientBtn;
    @BindView(R2.id.enter_instructions)
    EditText recipeInstructions;
    @BindView(R2.id.enter_instructions_button)
    Button recipeInstructionBtn;
    @BindView(R2.id.recipe_view_create)
    ListView recipeView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        ButterKnife.bind(this);
    }

    @OnClick(R2.id.button_home)
    public void submit(View view) {
        if (view == home) {
            Intent intent = new Intent(CreateRecipe.this, RecipeMenu.class);
            startActivity(intent);
            finish();
        }

    }
}
