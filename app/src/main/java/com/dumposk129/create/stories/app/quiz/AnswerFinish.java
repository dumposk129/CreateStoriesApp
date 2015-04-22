package com.dumposk129.create.stories.app.quiz;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dumposk129.create.stories.app.R;

/**
 * Created by DumpOSK129.
 */
public class AnswerFinish extends ActionBarActivity {
    private TextView answerNext_question, answerNext_answer1, answerNext_answer2,
            answerNext_answer3, answerNext_answer4, answerNext_isCorrect;

    private Button answerNext_btnNext;
    private RadioGroup answerNext_rg;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.answer_next_form);

        // Casting
        answerNext_question = (TextView) findViewById(R.id.tvAnswerNext_question);
        answerNext_answer1 = (TextView) findViewById(R.id.tvAnswerNext_answer1);
        answerNext_answer2 = (TextView) findViewById(R.id.tvAnswerNext_answer2);
        answerNext_answer3 = (TextView) findViewById(R.id.tvAnswerNext_answer3);
        answerNext_answer4 = (TextView) findViewById(R.id.tvAnswerNext_answer4);
        answerNext_btnNext = (Button) findViewById(R.id.btnAnswerNext);
        answerNext_isCorrect = (TextView) findViewById(R.id.tvAnswerNext_isCorrect);

        // Show question and answer from database


        // Answer
        answerNext_btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Selected Radio Button
                int selectedId = answerNext_rg.getCheckedRadioButtonId();

                // Switch case compared by Radio Button Id
                switch (selectedId) {
                    case R.id.rbQuestionNext_answer1:
                        if (/*is_correct*/)
                            answerNext_isCorrect.setVisibility(View.VISIBLE);
                        else // Show is Correct Answer
                            break;
                    case R.id.rbQuestionNext_answer2:
                        if (/*is_correct*/)
                            answerNext_isCorrect.setVisibility(View.VISIBLE);
                        else // Show is Correct Answer
                            break;
                    case R.id.rbQuestionNext_answer3:
                        if (/*is_correct*/)
                            answerNext_isCorrect.setVisibility(View.VISIBLE);
                        else // Show is Correct Answer
                            break;
                    case R.id.rbQuestionNext_answer4:
                        if (/*is_correct*/)
                            answerNext_isCorrect.setVisibility(View.VISIBLE);
                        else // Show is Correct Answer
                            break;
                }
            }
        });
    }
}