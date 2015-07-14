package com.dumposk129.create.stories.app.api;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DumpOSK129.
 */
public class Story {
    // Show All Story in Frame
    public static JSONObject getStoryTitleName() {
        JSONParser jParser = new JSONParser(); // Call JSONParser.
        List<NameValuePair> params = new ArrayList<>(); // Create list of params.
        return jParser.makeHttpRequest(ApiConfig.hostname(API.SHOW_TITLE_NAME), ApiConfig.GET, params); // return SHOW_TITLE_NAME.
    }

    /* Save sId in Create Story*/
    public static int saveStoryId(String title_name){
        JSONParser jsonParser = new JSONParser(); // Call JSONParser.
        List<NameValuePair> params = new ArrayList<>(); // Create list of params and add title_name.
        params.add(new BasicNameValuePair("title_name", title_name));
        // Create JSONObject for get CREATE_STORY.
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

    /* Show All Stories Name in Frame */
    public static List<com.dumposk129.create.stories.app.model.Story> getStoryList(JSONObject json){
        List<com.dumposk129.create.stories.app.model.Story> storyList = new ArrayList<>(); // Create storyList.
        try {
            int success = json.getInt(ApiConfig.TAG_SUCCESS);
            if (success == 1){
                JSONArray jStoryList = json.getJSONArray("story"); // Create jStoryList and getJSONArray from table story.
                if (jStoryList != null && jStoryList.length() != 0){
                    for (int i = 0; i < jStoryList.length(); i++){ // Iteration of jStoryList.length, then add data.
                        JSONObject jObjStoryList = jStoryList.getJSONObject(i);
                        int storyId = jObjStoryList.getInt("story_id");
                        String storyTitleName = jObjStoryList.getString("title_name");

                        // SET value from jObjStoryList.
                        com.dumposk129.create.stories.app.model.Story story = new com.dumposk129.create.stories.app.model.Story();
                        story.setId(storyId);
                        story.setTitle(storyTitleName);

                        storyList.add(story); // Add All into storyList.
                    }
                }
            }
        }catch (Exception e){
            Log.e("[All Story:JSON]", e.getMessage());
        }
        return storyList; // return storyList.
    }
}