package com.dumposk129.create.stories.app.model;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import com.dumposk129.create.stories.app.R;

/**
 * Created by DumpOSK129 on 4/20/2015.
 */
public class Question extends ActionBarActivity{

    private int questionId;
    private String questionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_form);
    }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_fragment, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
