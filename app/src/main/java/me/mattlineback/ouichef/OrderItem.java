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
    public String orderItem;

    //get item to add to order list
    public String getOrderItemText(){
        return orderItem;
    }
    //set item to orderItemText
    public void setOrderItemText(String orderItemText){
        this.orderItem = orderItemText;
    }

    @Override
    public String toString(){
        return this.orderItem;
    }
    //default constructor used for DataSnapshot

    public OrderItem(){
    }

    public OrderItem(String orderItemText){
        this.orderItem = orderItemText;
    }
    //used for writing items to firebase database

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("orderItemText", orderItem);
        return result;
    }
}
