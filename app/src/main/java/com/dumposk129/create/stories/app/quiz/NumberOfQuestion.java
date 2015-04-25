package com.dumposk129.create.stories.app.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dumposk129.create.stories.app.R;

/**
 * Created by DumpOSK129.
 */
public class NumberOfQuestion extends ActionBarActivity{
    private EditText numOfQues, qId;
    private Button   numOfQues_btnOK;
    private String quizID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_of_question);

        numOfQues = (EditText) findViewById(R.id.txtNumOfQuestion);
        numOfQues_btnOK = (Button) findViewById(R.id.btnNumOfQuestion);
        qId = (EditText) findViewById(R.id.quizId);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        quizID = bundle.getString("quizID");

        qId.setText(quizID);


        numOfQues_btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = numOfQues.getText().toString();

                Intent intent = new Intent(NumberOfQuestion.this, QuestionNext.class);
                intent.putExtra("NumOfQuestion", number);
                intent.putExtra("QuizID", quizID);
                intent.putExtra("index", 0);
                startActivity(intent);
            }
        });


    }
}
