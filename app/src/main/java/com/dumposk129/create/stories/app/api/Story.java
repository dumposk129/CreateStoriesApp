package com.dumposk129.create.stories.app.api;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DumpOSK129.
 */
public class Story {
    public static int saveStoryId(String title_name){
        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("title_name", title_name));

        JSONObject json = jsonParser.makeHttpRequest(ApiConfig.hostname(API.CREATE_STORY), ApiConfig.GET, params);
        try {
            int success = json.getInt(ApiConfig.TAG_SUCCESS);
            if (success == 1) {
                return json.getInt("storyId");   //storyId
            } else {
                String msg = json.getString("message");
                Log.e("[Save Story:API]", msg);
                return 0;
            }
        } catch (Exception e) {
            Log.e("[Save Story:JSON]", e.getMessage());
            return 0;
        }
    }
}