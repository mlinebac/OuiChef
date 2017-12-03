package me.mattlineback.ouichef;

import org.json.JSONObject;

/**
 * Created by Telerik
 * https://github.com/telerik/Android-samples/blob/master/Blogs/Json-Reader/app/src/main/java/com/example/progress/json_reader/MainActivity.java
 * Adapted by Matt Lineback on 10/30/17.
 */

interface AsyncResult {
    void onResult(JSONObject object);
}
