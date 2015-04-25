package com.dumposk129.create.stories.app.api;

import android.util.Log;

import com.dumposk129.create.stories.app.model.Choice;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DumpOSK129.
 */
public class Quiz {

    public static JSONObject getAllQuiz(){
        JSONParser jParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<>();
        return jParser.makeHttpRequest(ApiConfig.hostname(API.SHOW_QUIZ) , ApiConfig.GET, params);
    }

    public static JSONObject getAllAnswer(){
        JSONParser jParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<>();
        return jParser.makeHttpRequest(ApiConfig.hostname(API.LIST_CHOICE) , ApiConfig.GET, params);
    }

    public static JSONObject getAllQuestion(){
        JSONParser jParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<>();
        return jParser.makeHttpRequest(ApiConfig.hostname(API.SHOW_QUESTION) , ApiConfig.GET, params);
    }

    /***
     * Save question to database then return question id
     * @param questionName
     * @param quizId
     * @return questionId from API
     */
    public static int saveQuestion(String questionName, String quizId) {
        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("qId", quizId));
        params.add(new BasicNameValuePair("qName", questionName));



        JSONObject json = jsonParser.makeHttpRequest(ApiConfig.hostname(API.CREATE_QUESTION), ApiConfig.POST, params);
        try {
            int success = json.getInt(ApiConfig.TAG_SUCCESS);
            if(success == 1){
                return json.getInt("questionId");   //questionID
            }else{
                String msg = json.getString("message");
                Log.e("[Save Question]", msg);
                return 0;
            }
        } catch (Exception e)
        {
            Log.e("[Save Question]", e.getMessage());
            return 0;
        }
    }

    public static boolean saveChoices(List<Choice> choices,int questionID){
        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<>();
        for(int i = 1; i <= 4; i++){
            params.add(new BasicNameValuePair("a"+i, choices.get(i-1).getChoiceName()));
            params.add(new BasicNameValuePair("isA"+i, Integer.toString(choices.get(i-1).isCorrect())));
        }
        params.add(new BasicNameValuePair("qId", Integer.toString(questionID)));

        JSONObject json = jsonParser.makeHttpRequest(ApiConfig.hostname(API.CREATE_ANSWER), ApiConfig.POST, params);
        try{
            int success = json.getInt(ApiConfig.TAG_SUCCESS);
            if(success == 1){
                return true;
            }else{
                Log.e("[Save Choices]", json.getString("message"));
                return false;
            }

        }catch (Exception e){
            Log.e("[Save Choices]", e.getMessage());
            return false;
        }
    }
}
