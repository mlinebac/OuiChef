package me.mattlineback.ouichef;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by matt on 10/12/17.
 */
@IgnoreExtraProperties
public class OrderItem {
    public String orderItem;

    public OrderItem() {
    }

    OrderItem(String orderItemText) {

        this.orderItem = orderItemText;
    }

    //get item to add to order list
    public String getOrderItemText() {

        return orderItem;
    }
}

