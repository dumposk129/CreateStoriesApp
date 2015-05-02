package com.dumposk129.create.stories.app.api;

import android.util.Log;

import com.dumposk129.create.stories.app.model.Choice;
import com.dumposk129.create.stories.app.model.Question;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DumpOSK129.
 */
public class Quiz {

    // Show All Quizzes
    public static JSONObject getAllQuiz() {
        JSONParser jParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<>();
        return jParser.makeHttpRequest(ApiConfig.hostname(API.SHOW_QUIZ), ApiConfig.GET, params);
    }

    // Show All Answer
    public static JSONObject getAllAnswer() {
        JSONParser jParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<>();
        return jParser.makeHttpRequest(ApiConfig.hostname(API.LIST_CHOICE), ApiConfig.GET, params);
    }

    // Show All
    public static JSONObject getAllQuestion() {
        JSONParser jParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<>();
        return jParser.makeHttpRequest(ApiConfig.hostname(API.SHOW_QUESTION), ApiConfig.GET, params);
    }

    /**
     * Save question to database then return question id
     *
     * @param questionName
     * @param quizId
     * @return questionId from API
     */
    public static int saveQuestion(String questionName, String quizId) {
        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("qId", quizId));
        params.add(new BasicNameValuePair("qName", questionName));


        JSONObject json = jsonParser.makeHttpRequest(ApiConfig.hostname(API.CREATE_QUESTION), ApiConfig.GET, params);
        try {
            int success = json.getInt(ApiConfig.TAG_SUCCESS);
            if (success == 1) {
                return json.getInt("questionId");   //questionID
            } else {
                String msg = json.getString("message");
                Log.e("[Save Question:API]", msg);
                return 0;
            }
        } catch (Exception e) {
            Log.e("[Save Question:JSON]", e.getMessage());
            return 0;
        }
    }

    public static boolean saveChoices(List<Choice> choices, int questionID) {
        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            params.add(new BasicNameValuePair("a" + i, choices.get(i - 1).getChoiceName()));
            params.add(new BasicNameValuePair("isA" + i, Integer.toString(choices.get(i - 1).isCorrect())));
        }
        params.add(new BasicNameValuePair("qId", Integer.toString(questionID)));

        JSONObject json = jsonParser.makeHttpRequest(ApiConfig.hostname(API.CREATE_ANSWER), ApiConfig.POST, params);
        try {
            int success = json.getInt(ApiConfig.TAG_SUCCESS);
            if (success == 1) {
                return true;
            } else {
                Log.e("[Save Choices:API]", json.getString("message"));
                return false;
            }

        } catch (Exception e) {
            Log.e("[Save Choices:JSON]", e.getMessage());
            return false;
        }
    }

    public static JSONArray getShowQuestion(String quizID) {
        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("qz_id", quizID));

        JSONObject json = jsonParser.makeHttpRequest(ApiConfig.hostname(API.SHOW_QUESTION), ApiConfig.GET, params);

        try {
            int success = json.getInt(ApiConfig.TAG_SUCCESS);
            if (success == 1) {
                // return List<Question>
                return json.getJSONArray("result");
            } else {
                Log.e("[Show Question:API]", json.getString("message"));
                return null;
            }

        } catch (Exception e) {
            Log.e("[Show Question:JSON]", e.getMessage());
            return null;
        }

    }

    public static List<Question> getQuestions(JSONArray result) {
        List<Question> questions = new ArrayList<>();
        try {
            if (result != null) {
                for (int i = 0; i <= result.length(); i++) {
                    // Get question list
                    JSONObject jQuestion = result.getJSONObject(i);
                    int questionID = jQuestion.getInt("question_id");
                    String questionName = jQuestion.getString("question_name");
                    JSONArray jChoices = jQuestion.getJSONArray("choices");

                    // Get Answer List
                    List<Choice> choices = new ArrayList<>();
                    if (jChoices != null && jChoices.length() != 0) {
                        for (int j = 0; j < jChoices.length(); j++) {
                            JSONObject jChoice = jChoices.getJSONObject(j);
                            int answerID = jChoice.getInt("answer_id");
                            int order = jChoice.getInt("order_id");
                            String answerName = jChoice.getString("answer_name");
                            int isCorrect = jChoice.getInt("is_correct");

                            Choice choice = new Choice();
                            choice.setChoiceId(answerID);
                            choice.setOrder(order);
                            choice.setChoiceName(answerName);
                            choice.setIsCorrect(isCorrect);
                            choices.add(choice);
                        }
                    }
                    Question question = new Question();
                    question.setQuestionId(questionID);
                    question.setQuestionName(questionName);
                    if (jChoices.length() > 0) question.setChoices(choices);
                    questions.add(question);
                }
            }
        } catch (Exception e) {
            Log.e("[Show Question:JSON]", e.getMessage());
        }
        return questions;
    }
}
