package me.mattlineback.ouichef;

/**
 * Created by matt on 10/12/17.
 */

public class ListItem {
    public String listItem;
    public ListItem() {
    }

    ListItem(String listItem) {
        this.listItem = listItem;
    }

    //get item to add to order list
    public String getListItem() {
        return listItem;
    }
}

