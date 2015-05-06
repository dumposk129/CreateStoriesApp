package com.dumposk129.create.stories.app.model;

import java.util.List;

/**
 * Created by DumpOSK129.
 */
public class Question{
    private int questionId;
    private String questionName;
    private List<Choice> choices;

    // SET GETTER AND SETTER.
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