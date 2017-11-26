package me.mattlineback.ouichef;

/**
 * Created by matt on 10/12/17.
 */

class ListItem {
    private String listItem;
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

