package com.dumposk129.create.stories.app.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dumposk129.create.stories.app.R;

/**
 * Created by DumpOSK129.
 */

/**
 * HOW TO USE THIS PAGE.
 1. If you select question from Quizzes page.
 2. You fill Number of question.
 3. Click OK.
 4. Send Number of question, quizzID and index to Question.java
 */
public class NumberOfQuestion extends ActionBarActivity{
    private Toolbar mToolbar;
    private EditText numOfQues, qId;
    private Button btnOK;
    private String quizID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_of_question);

        // Casting.
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        numOfQues = (EditText) findViewById(R.id.txtNumOfQuestion);
        btnOK = (Button) findViewById(R.id.btnNumOfQuestion);
        qId = (EditText) findViewById(R.id.quizId);

        // Toolbar.
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Get quizID.
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        quizID = bundle.getString("quizID"); // quizID is auto increase.

        // Set quizID but not show because users not required.
        qId.setText(quizID);

        // Button ClickListener.
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = numOfQues.getText().toString();

                // Send Data to Question.class
                Intent intent = new Intent(NumberOfQuestion.this, Questions.class);
                intent.putExtra("NumOfQuestion", Integer.parseInt(number));
                intent.putExtra("QuizID", quizID);
                intent.putExtra("index", 0);
                startActivity(intent);
            }
        });


    }
}
