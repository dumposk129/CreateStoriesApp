package com.dumposk129.create.stories.app.quiz;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.EditText;

import com.dumposk129.create.stories.app.R;


public class Question extends ActionBarActivity{
    private EditText question, answer1, answer2, answer3, answer4;
    private Button   btnNext;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.question);

        //casting
        question = (EditText) findViewById(R.id.txtQuestion);
        answer1 =  (EditText) findViewById(R.id.textAnswer1);
        answer2 =  (EditText) findViewById(R.id.textAnswer2);
        answer3 =  (EditText) findViewById(R.id.textAnswer3);
        answer4 =  (EditText) findViewById(R.id.textAnswer4);
        btnNext = (Button) findViewById(R.id.btnNext_question);
    }

    public void onNextQuestionButtonClicked(){

    }

}
