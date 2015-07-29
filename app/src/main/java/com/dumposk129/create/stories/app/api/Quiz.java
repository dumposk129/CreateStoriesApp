package com.dumposk129.create.stories.app.api;

import android.util.Log;

import com.dumposk129.create.stories.app.model.Choice;
import com.dumposk129.create.stories.app.model.Question;
import com.dumposk129.create.stories.app.model.Story;

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
    /* This method is show all Story Name in Quiz. */
    public static JSONObject getAllQuiz() {
        JSONParser jParser = new JSONParser(); // Call JSONParser.
        List<NameValuePair> params = new ArrayList<>(); // Create list of param.
        return jParser.makeHttpRequest(ApiConfig.hostname(API.SHOW_QUIZ), ApiConfig.GET, params); // return SHOW_QUIZ.
    }

    /* This method is show all Answers. */
    public static JSONObject getAllAnswer() {
        JSONParser jParser = new JSONParser(); // Call JSONParser.
        List<NameValuePair> params = new ArrayList<>(); // Create list of param
        return jParser.makeHttpRequest(ApiConfig.hostname(API.LIST_CHOICE), ApiConfig.GET, params); // return LIST_CHOICE.
    }

    /* This method is show all Question. */
    public static JSONObject getAllQuestion() {
        JSONParser jParser = new JSONParser(); // Call JSONParser.
        List<NameValuePair> params = new ArrayList<>(); // Create list of param.
        return jParser.makeHttpRequest(ApiConfig.hostname(API.SHOW_QUESTION), ApiConfig.GET, params); // return SHOW_QUESTION.
    }

    /* This method is delete question */
    public static JSONObject deleteQuestion() {
        JSONParser jParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<>();
        return jParser.makeHttpRequest(ApiConfig.hostname(API.DELETE_QUESTION), ApiConfig.GET, params);
    }

    /**
     * Save question to database then return question id
     *
     * @param questionName
     * @param quizId
     * @return questionId from API
     */
    public static int saveQuestion(String questionName, int quizId) {
        JSONParser jsonParser = new JSONParser(); // Call JSONParser.
        List<NameValuePair> params = new ArrayList<>(); // Create list of param and add data.
        params.add(new BasicNameValuePair("qId", Integer.toString(quizId)));
        params.add(new BasicNameValuePair("qName", questionName));
        // Create JSONObject for get CREATE_QUESTION.
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

    /* Save Choices and correct choice */
    public static boolean saveChoices(List<Choice> choices, int questionID) {
        JSONParser jsonParser = new JSONParser(); // Call JSONParser.
        List<NameValuePair> params = new ArrayList<>(); // Create list of param and add data.
        for (int i = 1; i <= 4; i++) {
            params.add(new BasicNameValuePair("a" + i, choices.get(i - 1).getChoiceName()));
            params.add(new BasicNameValuePair("isA" + i, Integer.toString(choices.get(i - 1).isCorrect())));
        }
        params.add(new BasicNameValuePair("qId", Integer.toString(questionID)));

        // Create JSONObject for get CREATE_ANSWER.
        JSONObject json = jsonParser.makeHttpRequest(ApiConfig.hostname(API.CREATE_ANSWER), ApiConfig.GET, params);
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

    /* Show question */
    public static JSONArray getShowQuestion(int quizID) {
        JSONParser jsonParser = new JSONParser(); // Call JSONParser.
        List<NameValuePair> params = new ArrayList<>(); // Create list of param and add data.
        params.add(new BasicNameValuePair("qz_id", Integer.toString(quizID)));
        // Create JSONObject for get SHOW_QUESTION.
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

    // Remove Question and Answer.
    public static boolean removeQuestion(int questionID) {
        JSONParser jsonParser = new JSONParser(); // Call JSONParser.
        List<NameValuePair> params = new ArrayList<>(); // Create list of param and add data.
        params.add(new BasicNameValuePair("question_id", Integer.toString(questionID)));

        JSONObject json = jsonParser.makeHttpRequest(ApiConfig.hostname(API.DELETE_QUESTION), ApiConfig.GET, params);
        try {
            int successA = json.getInt(ApiConfig.TAG_SUCCESS_A);
            int successQ = json.getInt(ApiConfig.TAG_SUCCESS_Q);
            if (successA == 1 && successQ == 1) {
                // return List<Question>
                return true;
            } else if (successA == 0) {
                Log.e("[Delete Answer:API]", json.getString("messageA"));
                return false;
            } else if (successQ == 0) {
                Log.e("[Delete Question:API]", json.getString("messageQ"));
                return false;
            } else {
                return false;
            }
        } catch (Exception e){
            Log.e("[Delete Question:JSON]", e.getMessage());
            return false;
        }
    }

    /* Show question and answer */
    public static List<Question> getQuestions(JSONArray result) {
        List<Question> questions = new ArrayList<>(); // Create list of questions.
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

                            // Set choice
                            Choice choice = new Choice();
                            choice.setChoiceId(answerID);
                            choice.setOrder(order);
                            choice.setChoiceName(answerName);
                            choice.setIsCorrect(isCorrect);
                            choices.add(choice); // Add choice into choices.
                        }
                    }
                    Question question = new Question(); // Call Question and set data.
                    question.setQuestionId(questionID);
                    question.setQuestionName(questionName);
                    if (jChoices.length() > 0) {
                        question.setChoices(choices);
                    }
                    questions.add(question); // Add question into questions.
                }
            }
        } catch (Exception e) {
            Log.e("[Show Question:JSON]", e.getMessage());
        }
        return questions;
    }

    /* Show stories name in quiz */
    public static List<Story> getQuizList(JSONObject json) {
        List<Story> storyList = new ArrayList<>(); // Create list of storyList
        try {
            int success = json.getInt(ApiConfig.TAG_SUCCESS);
            if (success == 1) {
                JSONArray jQuizList = json.getJSONArray("quiz"); // Create jQuizList
                if (jQuizList != null && jQuizList.length() != 0) {
                    for (int i = 0; i < jQuizList.length(); i++) { // Add data
                        JSONObject jQuiz = jQuizList.getJSONObject(i);
                        int quizId = jQuiz.getInt("quiz_id");
                        String quizName = jQuiz.getString("title_name");
                        String quizImagePath = jQuiz.getString("pic_path");

                        // SET value from jQuiz.
                        Story story = new Story();
                        story.setQuestionId(quizId);
                        story.setTitle(quizName);
                        story.setPic_path(quizImagePath);

                        storyList.add(story); // Add All into storyList.
                    }
                }
            }
        } catch (Exception e) {
            Log.e("[All Quiz:JSON]", e.getMessage());
        }
        return storyList; // return storyList.
    }
}