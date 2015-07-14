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
public class Frame {
    /* Show Frame*/
    public static JSONObject getAllFrame(int story_id){
        JSONParser jParser = new JSONParser(); // Call JSONParser.
        List<NameValuePair> params = new ArrayList<>(); // Create list of params and add story_id.
        params.add(new BasicNameValuePair("sId", Integer.toString(story_id)));
        return jParser.makeHttpRequest(ApiConfig.hostname(API.SHOW_FRAME), ApiConfig.GET, params); // return SHOW_FRAME.
    }

    /* Show All Frame List */
    public static List<com.dumposk129.create.stories.app.model.Frame> getFrameList(int story_id){
        JSONParser jsonParser = new JSONParser(); // Call JSONParser
        List<com.dumposk129.create.stories.app.model.Frame> frameList = new ArrayList<>(); // Create object List of frameList.
        List<NameValuePair> params = new ArrayList<>(); // Create object List of params and add story_id.
        params.add(new BasicNameValuePair("sId", Integer.toString(story_id)));
        // Create JSONObject for get SHOW_FRAME.
        JSONObject json = jsonParser.makeHttpRequest(ApiConfig.hostname(API.SHOW_FRAME), ApiConfig.GET, params);

        try {
            int success = json.getInt(ApiConfig.TAG_SUCCESS);
            if (success == 1){
                JSONArray jFrameList = json.getJSONArray("frame"); // Create jFrameList and getJSONArray in table frame.
                if (jFrameList != null && jFrameList.length() != 0){ // has jFrameList.
                    for (int i = 0; i < jFrameList.length(); i++){ // Iteration of jFrameList.length, then add data.
                        JSONObject jFrame = jFrameList.getJSONObject(i);
                        int frameId = jFrame.getInt("frame_id");
                        String framePicPath = jFrame.getString("pic_path");
                        String frameAudPath = jFrame.getString("audio_path");

                        // SET value from jFrame.
                        com.dumposk129.create.stories.app.model.Frame frame = new com.dumposk129.create.stories.app.model.Frame();
                        frame.setId(frameId);
                        frame.setPathPic(framePicPath);
                        frame.setPathAudio(frameAudPath);

                        frameList.add(frame); // Add All into frameList.
                    }
                }
            }
        }catch (Exception e){
            Log.e("[All Frame:JSON]", e.getMessage());
        }
        return frameList; // return frameList.
    }
}