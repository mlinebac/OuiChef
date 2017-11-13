package me.mattlineback.ouichef;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by matt on 10/30/17.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeHolder>{
    private static final String TAG = "myApp";
    Context context;
    private ArrayList<RecipeItem> recipeItems;


    public RecipesAdapter(Context context, ArrayList<RecipeItem> items) {
        this.context = context;
        this.recipeItems= items;
    }

    public class RecipeHolder extends RecyclerView.ViewHolder{
        public TextView ingredient;
        public TextView amt;
        public TextView amtUnit;
        public TextView instruction;

        private ArrayList<RecipeItem> recipeItemList;
        public RecipeHolder(final View view, final ArrayList<RecipeItem> recipeItemList){
            super(view);
            ingredient = view.findViewById(R.id.ingredient);
            editView(ingredient);
            amt = view.findViewById(R.id.amount);
            editView(amt);
            amtUnit = view.findViewById(R.id.amount_unit);
            editView(amtUnit);
            instruction = view.findViewById(R.id.instruction);
            editView(instruction);
            this.recipeItemList = recipeItemList;
        }
    }

    public TextView editView(TextView tv){
        tv.setTextColor(Color.WHITE);
        tv.setAllCaps(true);
        tv.setTextSize(20);
        return tv;
    }

    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecipeHolder viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recipe, parent, false);
        viewHolder = new RecipeHolder(layoutView, recipeItems);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeHolder holder, int position) {
       holder.ingredient.setText(recipeItems.get(position).getIngredient());
       holder.amt.setText(String.valueOf(recipeItems.get(position).getAmount()));
       holder.amtUnit.setText(recipeItems.get(position).getUnit());
       holder.instruction.setText(recipeItems.get(position).getInstruction());
    }

    @Override
    public int getItemCount() {
        return this.recipeItems.size();
    }
}
