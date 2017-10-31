package me.mattlineback.ouichef;

import org.json.JSONObject;

/**
 * Created by matt on 10/30/17.
 */

interface AsyncResult {
    void onResult(JSONObject object);
}
