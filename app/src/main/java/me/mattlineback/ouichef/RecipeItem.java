package me.mattlineback.ouichef;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by matt on 10/30/17.
 */

public class RecipeItem {
    private int position;
    private String ingredient, unit, instruction;
    private int amount;


    public RecipeItem(String ingredient, int amount, String unit, String instruction){

        //this.setPosition(position);
        this.setIngredient(ingredient);
        this.setAmount(amount);
        this.setUnit(unit);
        this.setInstruction(instruction);
    }

    public int getPosition(){
        return position;
    }

    public void setPosition(int position){
        this.position = position;
    }

    public String getIngredient(){
        return ingredient;
    }

    public void setIngredient(String ingredient){
        this.ingredient = ingredient;
    }

    public int getAmount(){
        return amount;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }

    public String getUnit(){
        return unit;
    }

    public void setUnit(String unit){
        this.unit = unit;
    }

    public String getInstruction(){
        return instruction;
    }

    public void setInstruction(String instruction){
        this.instruction = instruction;
    }

    public String getRecipeItem(){
        return ingredient  + " " + amount + " " + unit + " " + instruction;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("position", position);
        result.put("ingredient", ingredient);
        result.put("amount", amount);
        result.put("unit", unit);
        result.put("instruction", instruction);

        return result;
    }

}
