package me.mattlineback.ouichef;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by matt on 10/12/17.
 */
@IgnoreExtraProperties
public class ListItem {
    public String mlistItem;

    public ListItem() {
    }

    ListItem(String listItem) {

        this.mlistItem = listItem;
    }

    //get item to add to order list
    public String getListItem() {

        return mlistItem;
    }
}

