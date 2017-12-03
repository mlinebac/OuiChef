package me.mattlineback.ouichef;

/**
 * Created by Matt Lineback on 10/12/17.
 */

class ListItem {
    private String listItem;
    //empty constructor for firebase
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

