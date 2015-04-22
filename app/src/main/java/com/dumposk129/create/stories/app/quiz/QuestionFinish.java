package com.dumposk129.create.stories.app.quiz;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.dumposk129.create.stories.app.R;

/**
 * Created by DumpOSK129.
 */
public class QuestionFinish extends ActionBarActivity {
    private EditText questionFinish_question, questionFinish_answer1, questionFinish_answer2, questionFinish_answer3, questionFinish_answer4;
    private Button questionFinish_btnFinish;
    private RadioGroup questionFinish_rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_finish_form);

        //casting
        questionFinish_question = (EditText) findViewById(R.id.txtQuestionFinish_question);
        questionFinish_answer1 = (EditText) findViewById(R.id.txtQuestionFinish_answer1);
        questionFinish_answer2 = (EditText) findViewById(R.id.txtQuestionFinish_answer2);
        questionFinish_answer3 = (EditText) findViewById(R.id.txtQuestionFinish_answer3);
        questionFinish_answer4 = (EditText) findViewById(R.id.txtQuestionFinish_answer4);
        questionFinish_rg = (RadioGroup) findViewById(R.id.rgAnswerFinishForm);
        questionFinish_btnFinish = (Button) findViewById(R.id.btnQuestionFinish);

        //Click Next to auto save in database and go to next question
        questionFinish_btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fill Question amd Answer
                String quesFinish_question = questionFinish_question.getText().toString();
                String quesFinish_answer1 = questionFinish_answer1.getText().toString();
                String quesFinish_answer2 = questionFinish_answer2.getText().toString();
                String quesFinish_answer3 = questionFinish_answer3.getText().toString();
                String quesFinish_answer4 = questionFinish_answer4.getText().toString();

                //Selected Radio Button
                int selectedId = questionFinish_rg.getCheckedRadioButtonId();

                // Switch case compared by Radio Button Id
                switch (selectedId) {
                    case R.id.rbQuestionNext_answer1: //is Correct
                        break;
                    case R.id.rbQuestionNext_answer2: //is Correct
                        break;
                    case R.id.rbQuestionNext_answer3: //is Correct
                        break;
                    case R.id.rbQuestionNext_answer4: //is Correct
                }
            }

        });

    }
}
