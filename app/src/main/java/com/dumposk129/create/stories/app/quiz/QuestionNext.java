package com.dumposk129.create.stories.app.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.dumposk129.create.stories.app.R;

/**
 * Created by DumpOSK129.
 */
public class QuestionNext extends ActionBarActivity {
    private EditText questionNext_question, questionNext_answer1, questionNext_answer2, questionNext_answer3, questionNext_answer4;
    private Button questionNext_btnNext;
    private RadioGroup questionNext_rg;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.question_next_form);

        //casting
        questionNext_question = (EditText) findViewById(R.id.txtQuestionNext_question);
        questionNext_answer1 = (EditText) findViewById(R.id.txtQuestionNext_answer1);
        questionNext_answer2 = (EditText) findViewById(R.id.txtQuestionNext_answer2);
        questionNext_answer3 = (EditText) findViewById(R.id.txtQuestionNext_answer3);
        questionNext_answer4 = (EditText) findViewById(R.id.txtQuestionNext_answer4);
        questionNext_rg = (RadioGroup) findViewById(R.id.rgQuestionNextForm);
        questionNext_btnNext = (Button) findViewById(R.id.btnQuestionNext);

        // Get Data
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            int num = bundle.size();
            for (int i = 0; i < num - 1; i++) {
                if (num != i) {
                    //Click Next to auto save in database and go to next question
                    questionNext_btnNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Fill Question amd Answer
                            String quesNext_question = questionNext_question.getText().toString();
                            String quesNext_answer1 = questionNext_answer1.getText().toString();
                            String quesNext_answer2 = questionNext_answer2.getText().toString();
                            String quesNext_answer3 = questionNext_answer3.getText().toString();
                            String quesNext_answer4 = questionNext_answer4.getText().toString();

                            //Selected Radio Button
                            int selectedId = questionNext_rg.getCheckedRadioButtonId();

                            // Switch case compared by Radio Button Id
                            switch (selectedId) {
                                case R.id.rbQuestionNext_answer1: //is Correct
                                    break;
                                case R.id.rbQuestionNext_answer2: //is Correct
                                    break;
                                case R.id.rbQuestionNext_answer3: //is Correct
                                    break;
                                case R.id.rbQuestionNext_answer4: //is Correct
                                    break;
                            }
                        }
                    });
                } else {
                    Intent lastQuestion = new Intent(getApplicationContext(), QuestionFinish.class);
                    startActivity(lastQuestion);
                }
            }
        }
    }
}
