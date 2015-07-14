package com.dumposk129.create.stories.app.api;

/**
 * Created by DumpOSK129.
 */
public class ApiConfig {
    public static final String hostname(String api_name) {
      return apiUrl + api_name + ".php";
    }
    public static final String apiUrl = "http://dump.geozigzag.com/api/";
    public static final String TAG_SUCCESS = "success";
    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String TAG_QUIZ = "quiz";
    public static final String TAG_QUIZID = "quiz_id";
    public static final String TAG_STORYTITLE = "title_name";
    public static final int _TRUE = 1;
    public static final int _FALSE = 0;
}