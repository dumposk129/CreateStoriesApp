package com.dumposk129.create.stories.app.api;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DumpOSK129.
 */
public class Frame {
    /* Show Frame*/
    public static JSONObject getAllFrame(){
        JSONParser jParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<>();
        return jParser.makeHttpRequest(ApiConfig.hostname(API.SHOW_FRAME), ApiConfig.GET, params);
    }

    // Show All Story in Frame
    public static JSONObject getFrameTitleName() {
        JSONParser jParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<>();
        return jParser.makeHttpRequest(ApiConfig.hostname(API.SHOW_FRAME_TITLE_NAME), ApiConfig.GET, params);
    }

    /* Show All Frame List */
    public static List<com.dumposk129.create.stories.app.model.Frame> getFrameList(JSONObject json){
        List<com.dumposk129.create.stories.app.model.Frame> frameList = new ArrayList<>();
        try {
            int success = json.getInt(ApiConfig.TAG_SUCCESS);
            if (success == 1){
                JSONArray jFrameList = json.getJSONArray("frame");
                if (jFrameList != null && jFrameList.length() != 0){
                    for (int i = 0; i < jFrameList.length(); i++){
                        JSONObject jFrame = jFrameList.getJSONObject(i);
                        int frameId = jFrame.getInt("id");
                        String framePicPath = jFrame.getString("pic_path");
                        String frameAudPath = jFrame.getString("audio_path");
                        int frameOrder = jFrame.getInt("frame_order");
                        com.dumposk129.create.stories.app.model.Frame frame = new com.dumposk129.create.stories.app.model.Frame();
                        frame.setId(frameId);
                        frame.setPathPic(framePicPath);
                        frame.setPathAudio(frameAudPath);
                        frame.setFrameOrder(frameOrder);
                        frameList.add(frame);
                    }
                }
            }
        }catch (Exception e){
            Log.e("[All Frame:JSON]", e.getMessage());
        }
        return frameList;
    }

    /* Show All Stories Name in Frame */
    public static List<com.dumposk129.create.stories.app.model.Story> getFrameTitleNameList(JSONObject json){
        List<com.dumposk129.create.stories.app.model.Story> storyList = new ArrayList<>();
        try {
            int success = json.getInt(ApiConfig.TAG_SUCCESS);
            if (success == 1){
                JSONArray jFrameTitleNameList = json.getJSONArray("frame");
                if (jFrameTitleNameList != null && jFrameTitleNameList.length() != 0){
                    for (int i = 0; i < jFrameTitleNameList.length(); i++){
                        JSONObject jFrameTitleName = jFrameTitleNameList.getJSONObject(i);
                        int frameId = jFrameTitleName.getInt("frame_id");
                        String frameTitleName = jFrameTitleName.getString("title_name");
                        com.dumposk129.create.stories.app.model.Story story = new com.dumposk129.create.stories.app.model.Story();
                        story.setQuestionId(frameId);
                        story.setQuestionName(frameTitleName);
                        storyList.add(story);
                    }
                }
            }
        }catch (Exception e){
            Log.e("[All Frame:JSON]", e.getMessage());
        }
        return storyList;
    }
}