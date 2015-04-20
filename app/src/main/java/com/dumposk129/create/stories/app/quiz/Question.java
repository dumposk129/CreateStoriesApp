package com.dumposk129.create.stories.app.quiz;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dumposk129.create.stories.app.R;


public class Question extends ActionBarActivity{
    private EditText question, answer1, answer2, answer3, answer4;
    private Button   btnNext;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.question_form);

        //casting
        question = (EditText) findViewById(R.id.txtQuestion);
        answer1 =  (EditText) findViewById(R.id.textAnswer1);
        answer2 =  (EditText) findViewById(R.id.textAnswer2);
        answer3 =  (EditText) findViewById(R.id.textAnswer3);
        answer4 =  (EditText) findViewById(R.id.textAnswer4);
        btnNext = (Button) findViewById(R.id.btnNext_question);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.list_question_answer);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) findViewById(checkedId);
                answer1.setText("You Selected"+rb.getText());
                Toast.makeText(getApplicationContext(), rb.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onNextQuestionButtonClicked(){

    }

}
