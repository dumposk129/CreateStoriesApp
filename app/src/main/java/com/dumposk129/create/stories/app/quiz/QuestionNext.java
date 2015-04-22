package com.dumposk129.create.stories.app.quiz;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.dumposk129.create.stories.app.R;


public class QuestionNext extends ActionBarActivity{
    private EditText question, answer1, answer2, answer3, answer4;
    private Button   btnNext;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.question_next_form);

        //casting
        question = (EditText) findViewById(R.id.txtQuestionNext_question);
        answer1 =  (EditText) findViewById(R.id.txtQuestionNext_answer1);
        answer2 =  (EditText) findViewById(R.id.txtQuestionNext_answer2);
        answer3 =  (EditText) findViewById(R.id.txtQuestionNext_answer3);
        answer4 =  (EditText) findViewById(R.id.txtQuestionNext_answer4);
        btnNext = (Button) findViewById(R.id.btnQuestionNext);

        RadioGroup rg = new RadioGroup(this);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });
    }

    public void onNextQuestionButtonClicked(){

    }

}
