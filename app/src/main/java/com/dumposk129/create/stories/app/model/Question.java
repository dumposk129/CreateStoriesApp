package com.dumposk129.create.stories.app.model;

import android.support.v7.app.ActionBarActivity;

import java.util.List;

/**
 * Created by DumpOSK129.
 */
public class Question extends ActionBarActivity{

    private int questionId;
    private String questionName;
    private List<Choice> choices;

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

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

}
