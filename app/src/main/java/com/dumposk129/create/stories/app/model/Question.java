package com.dumposk129.create.stories.app.model;

import android.support.v7.app.ActionBarActivity;

/**
 * Created by DumpOSK129 on 4/20/2015.
 */
public class Question extends ActionBarActivity{

    private int questionId;
    private String questionName;

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
