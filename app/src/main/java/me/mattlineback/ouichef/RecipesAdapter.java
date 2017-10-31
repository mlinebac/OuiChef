package me.mattlineback.ouichef;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by matt on 10/30/17.
 */

public class RecipesAdapter extends ArrayAdapter<Recipe> {
    private static final String TAG = "myApp";
    Context context;
    private ArrayList<Recipe> recipe;

    public RecipesAdapter(Context context, int textViewResourceId, ArrayList<Recipe> items) {
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
        Recipe o = recipe.get(position);
        Log.v(TAG, "did something");
        if (o != null) {
            TextView pos = v.findViewById(R.id.position);
            TextView ingredient = v.findViewById(R.id.ingredient);
            TextView amt = v.findViewById(R.id.amount);
            TextView amtUnit = v.findViewById(R.id.amount_unit);

            Log.v(TAG, String.valueOf(o.getPosition()));
            Log.v(TAG, String.valueOf(o.getIngredient()));


            pos.setText(String.valueOf(o.getPosition()));
            ingredient.setText(String.valueOf(o.getIngredient()));
            amt.setText(String.valueOf(o.getAmount()));
            amtUnit.setText(String.valueOf(o.getAmountUnit()));

        }
        return v;
    }
}
