package me.mattlineback.ouichef;

/**
 * Created by matt on 10/30/17.
 */

public class Recipe {
    private int position;
    private String ingredient, amountUnit;
    private int amount;


    public Recipe(int position, String ingredient, int amount, String amountUnit){
        this.setPosition(position);
        this.setIngredient(ingredient);
        this.setAmount(amount);
        this.setAmountUnit(amountUnit);
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

    public String getAmountUnit(){
        return amountUnit;
    }
    public void setAmountUnit(String amountUnit){
        this.amountUnit = amountUnit;
    }

}
