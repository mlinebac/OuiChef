package me.mattlineback.ouichef;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by matt on 10/30/17.
 */

public class RecipeItem {
    private String ingredient, unit, instruction;
    private int amount;


    RecipeItem(String ingredient, int amount, String unit, String instruction){
        this.setIngredient(ingredient);
        this.setAmount(amount);
        this.setUnit(unit);
        this.setInstruction(instruction);
    }
    public RecipeItem(){
    }
    String getIngredient(){
        return ingredient;
    }

    private void setIngredient(String ingredient){
        this.ingredient = ingredient;
    }

    int getAmount(){
        return amount;
    }

    private void setAmount(int amount){
        this.amount = amount;
    }

    String getUnit(){
        return unit;
    }

    private void setUnit(String unit){
        this.unit = unit;
    }

    String getInstruction(){
        return instruction;
    }

    private void setInstruction(String instruction){
        this.instruction = instruction;
    }

    public String getRecipeItem(){
        return ingredient  + " " + amount + " " + unit + " " + instruction;
    }
/*
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("ingredient", ingredient);
        result.put("amount", amount);
        result.put("unit", unit);
        result.put("instruction", instruction);

        return result;
    }
*/
}
