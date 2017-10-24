package me.mattlineback.ouichef;

/**
 * Created by mattlineback on 10/23/2017.
 */

public class PrepItem {
    public String mPrepItem;

    public PrepItem(){

    }

    PrepItem(String prepItem){
        mPrepItem = prepItem;
    }

    //get item to add to prep list
    public String getPrepItem() {
        return mPrepItem;
    }
    public void setPrepItem(String prepItem){
        mPrepItem = prepItem;
    }
}
