package me.mattlineback.ouichef;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by matt on 10/12/17.
 */
@IgnoreExtraProperties
public class OrderItem {
    public String orderItemText;

    //get item to add to order list
    public String getOrderItemText(){
        return orderItemText;
    }
    //set item to orderItemText
    public void setOrderItemText(String orderItemText){
        this.orderItemText = orderItemText;
    }

    @Override
    public String toString(){
        return this.orderItemText;
    }
    //default constructor used for DataSnapshot

    public OrderItem(){
    }

    public OrderItem(String orderItemText){
        this.orderItemText = orderItemText;
    }
    //used for writing items to firebase database

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("orderItemText", orderItemText);
        return result;
    }
}
