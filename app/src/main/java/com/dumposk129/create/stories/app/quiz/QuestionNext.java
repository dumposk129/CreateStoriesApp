package com.dumposk129.create.stories.app.quiz;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.api.ApiConfig;
import com.dumposk129.create.stories.app.api.Quiz;
import com.dumposk129.create.stories.app.model.Choice;
import com.dumposk129.create.stories.app.navigation_drawer.NavigationDrawerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DumpOSK129.
 */
public class QuestionNext extends ActionBarActivity {

    private Toolbar mToolbar;
    private EditText questionNext_question, questionNext_answer1, questionNext_answer2, questionNext_answer3, questionNext_answer4;
    private Button questionNext_btnNext;
    private RadioGroup questionNext_rg;
    private RadioButton questionNext_rb;
    private String quizId;
    private int currentIndex;
    private int noOfQuestion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_next_form);

        //casting
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        questionNext_question = (EditText) findViewById(R.id.txtQuestionNext_question);
        questionNext_answer1 = (EditText) findViewById(R.id.txtQuestionNext_answer1);
        questionNext_answer2 = (EditText) findViewById(R.id.txtQuestionNext_answer2);
        questionNext_answer3 = (EditText) findViewById(R.id.txtQuestionNext_answer3);
        questionNext_answer4 = (EditText) findViewById(R.id.txtQuestionNext_answer4);
        questionNext_rg = (RadioGroup) findViewById(R.id.rgQuestionNextForm);
        questionNext_btnNext = (Button) findViewById(R.id.btnQuestionNext);


        // Navigation Drawer
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);


        // Get Data
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        noOfQuestion = bundle.getInt("NumOfQuestion");
        quizId = bundle.getString("quizId");
        currentIndex = bundle.getInt("index") + 1;

        if (noOfQuestion == currentIndex) {
            questionNext_btnNext.setText("Finished");
        }

        questionNext_btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call AsyncTask
                new SaveQuestionTask().execute();
            }
        });
    }

    private void onQuestionNextClickListener() {
        //Fill Question amd Answer
        String quesNext_question = questionNext_question.getText().toString();
        String[] quesNext_answer = new String[4];
        quesNext_answer[0] = questionNext_answer1.getText().toString();
        quesNext_answer[1] = questionNext_answer2.getText().toString();
        quesNext_answer[2] = questionNext_answer3.getText().toString();
        quesNext_answer[3] = questionNext_answer4.getText().toString();

        // Save Question to DB
        int questionID = Quiz.saveQuestion(quesNext_question, quizId); //get question from db

        final int[] correctAnswer = {0};

        questionNext_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // Switch case compared by Radio Button Id
                switch (checkedId) {
                    case R.id.rbQuestionNext_answer1: //is Correct
                        correctAnswer[0] = 1;
                        break;
                    case R.id.rbQuestionNext_answer2: //is Correct
                        correctAnswer[1] = 2;
                        break;
                    case R.id.rbQuestionNext_answer3: //is Correct
                        correctAnswer[2] = 3;
                        break;
                    case R.id.rbQuestionNext_answer4: //is Correct
                        correctAnswer[3] = 4;
                        break;
                }
            }
        });


        // Set up choices data, it should be ready for saving to db.
        List<Choice> choices = new ArrayList<>(4);
        for (int s = 1; s <= 4; s++) {
            Choice choice = new Choice();
            choice.setChoiceName(quesNext_answer[s - 1]);
            if (correctAnswer[0] == s) {
                choice.setIsCorrect(ApiConfig._TRUE);
            } else {
                choice.setIsCorrect(ApiConfig._FALSE);
            }
            choice.setChoiceId(s);

            choices.add(choice);
        }

        // Save List of choices to DB
        boolean isSuccess = Quiz.saveChoices(choices, questionID);
        if (isSuccess) {
            if (currentIndex == noOfQuestion) {
                // Go to Final Question
                Intent intent = new Intent(getApplicationContext(), Quizzes.class);
                startActivity(intent);
            } else {
                // Create Next Question
                Intent intent = new Intent(getApplicationContext(), QuestionNext.class);
                intent.putExtra("NumOfQuestion", noOfQuestion);
                intent.putExtra("QuizID", quizId);
                intent.putExtra("index", currentIndex);
                startActivity(intent);
            }
        } else {
            // Show Warning
        }
    }

    private class SaveQuestionTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            onQuestionNextClickListener();
            return null;
        }
    }
}
