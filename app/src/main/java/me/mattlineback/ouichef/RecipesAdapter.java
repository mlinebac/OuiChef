package me.mattlineback.ouichef;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by matt on 10/30/17.
 */

public class RecipesAdapter extends ArrayAdapter<RecipeItem> {
    private static final String TAG = "myApp";
    Context context;
    private ArrayList<RecipeItem> recipe;


    public RecipesAdapter(Context context, int textViewResourceId, ArrayList<RecipeItem> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.recipe= items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.activity_recipe, null);


        }
        RecipeItem o = recipe.get(position);

        if (o != null) {

            TextView ingredient = v.findViewById(R.id.ingredient);
            TextView amt = v.findViewById(R.id.amount);
            TextView amtUnit = v.findViewById(R.id.amount_unit);
            TextView instruction = v.findViewById(R.id.instruction);

            ingredient.setText(String.valueOf(o.getIngredient()));
            editView(ingredient);

            amt.setText(String.valueOf(o.getAmount()));
            editView(amt);
            amtUnit.setText(String.valueOf(o.getUnit()));
            editView(amtUnit);
            instruction.setText(String.valueOf(o.getInstruction()));
            editView(instruction);

        }
        return v;

    }
    public TextView editView(TextView tv){
        tv.setTextColor(Color.WHITE);
        tv.setAllCaps(true);
        tv.setTextSize(20);
        return tv;
    }
}
