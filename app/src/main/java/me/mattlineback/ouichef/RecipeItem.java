package me.mattlineback.ouichef;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by matt on 10/30/17.
 */

public class RecipeItem {
    private String ingredient, unit, instruction;
    private double amount;

    RecipeItem( String ingredient, double amount, String unit, String instruction){
        this.ingredient = ingredient;
        this.amount = amount;
        this.unit = unit;
        this.instruction = instruction;
    }
    public RecipeItem(){
    }

    String getIngredient(){
        return ingredient;
    }

    private void setIngredient(String ingredient){
        this.ingredient = ingredient;
    }

    double getAmount(){
        return amount;
    }

    private void setAmount(double amount){
        this.amount = amount;
    }

    public void scaleAmount(double amount, int scaler){
        setAmount(amount*scaler);
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

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("ingredient", ingredient);
        result.put("amount", amount);
        result.put("unit", unit);
        result.put("instruction", instruction);

        return result;
    }

}
