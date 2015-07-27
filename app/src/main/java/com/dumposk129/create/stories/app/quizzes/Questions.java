package com.dumposk129.create.stories.app.quizzes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
public class Questions extends ActionBarActivity {
    private Toolbar mToolbar;
    private EditText txtQuestion, txtAnswer1, txtAnswer2, txtAnswer3, txtAnswer4;
    private Button btnNext;
    private RadioGroup radGrp;
    private RadioButton rbAnswer1, rbAnswer2, rbAnswer3, rbAnswer4;
    private int quizId;
    private int currentIndex;
    private int noOfQuestion;
    private int correctAnswer = 0;

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_form);

        // Casting.
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        txtQuestion = (EditText) findViewById(R.id.txtQuestionName);
        txtAnswer1 = (EditText) findViewById(R.id.txtAnswer_1);
        txtAnswer2 = (EditText) findViewById(R.id.txtAnswer_2);
        txtAnswer3 = (EditText) findViewById(R.id.txtAnswer_3);
        txtAnswer4 = (EditText) findViewById(R.id.txtAnswer_4);
        radGrp = (RadioGroup) findViewById(R.id.rgQuestionForm);
        rbAnswer1 = (RadioButton) findViewById(R.id.rbAnswer_1);
        rbAnswer2 = (RadioButton) findViewById(R.id.rbAnswer_2);
        rbAnswer3 = (RadioButton) findViewById(R.id.rbAnswer_3);
        rbAnswer4 = (RadioButton) findViewById(R.id.rbAnswer_4);
        btnNext = (Button) findViewById(R.id.btnNextQuestion);

        // Toolbar and Navigation Drawer.
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

        // Get NumOfQuestion, QuizID, index from NumberOfQuestion.
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        noOfQuestion = bundle.getInt("NumOfQuestion");
        quizId = bundle.getInt("QuizID");
        currentIndex = bundle.getInt("index") + 1;

        // Change textView from Next to Finished when NumberOfQuestion equal currentIndex.
        if (noOfQuestion == currentIndex) {
            btnNext.setText("Finished");
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SET Correct Answer
                int selectedID = radGrp.getCheckedRadioButtonId();

                if (selectedID == -1) {
                    Toast.makeText(getApplicationContext(), "Please check radio button for answer.", Toast.LENGTH_SHORT).show();
                } else if (selectedID == rbAnswer1.getId()) {
                    correctAnswer = 1;
                } else if (selectedID == rbAnswer2.getId()) {
                    correctAnswer = 2;
                } else if (selectedID == rbAnswer3.getId()) {
                    correctAnswer = 3;
                } else if (selectedID == rbAnswer4.getId()) {
                    correctAnswer = 4;
                }

                if (selectedID != -1) {
                    // Call AsyncTask
                    new SaveQuestionTask().execute();
                }
            }
        });
    }

    // Method Next Click Listener.
    private void onNextClickListener() {
        // Fill Question and Answer.
        String question = txtQuestion.getText().toString();
        String[] answer = new String[4];
        answer[0] = txtAnswer1.getText().toString();
        answer[1] = txtAnswer2.getText().toString();
        answer[2] = txtAnswer3.getText().toString();
        answer[3] = txtAnswer4.getText().toString();

        // Save Question to DB.
        int questionID = Quiz.saveQuestion(question, quizId); //get question from db

        // Set up choices data, it should be ready for saving to DB.
        List<Choice> choices = new ArrayList<>(4);
        for (int s = 1; s <= 4; s++) {
            Choice choice = new Choice();
            choice.setChoiceName(answer[s - 1]);
            if (correctAnswer == s) {
                choice.setIsCorrect(ApiConfig._TRUE);
            } else {
                choice.setIsCorrect(ApiConfig._FALSE);
            }
            choice.setChoiceId(s);

            // Add Choices and isCorrect to DB.
            choices.add(choice);
        }

        // Save List of choices to DB.
        boolean isSuccess = Quiz.saveChoices(choices, questionID);
        if (isSuccess) {
            if (currentIndex == noOfQuestion) {
                // Go to Quizzes Page.
                Intent intent = new Intent(Questions.this, Quizzes.class);
                startActivity(intent);
            } else {
                // Create Next Question.
                Intent intent = new Intent(Questions.this, Questions.class);
                intent.putExtra("NumOfQuestion", noOfQuestion);
                intent.putExtra("QuizID", quizId);
                intent.putExtra("index", currentIndex);
                startActivity(intent);
            }
        }

    }

    // Class SaveQuestionTask using AsyncTask.
    private class SaveQuestionTask extends AsyncTask<String, Void, Void> {
        // Preparing load questions and answer.
        private ProgressDialog progressDialog = new ProgressDialog(Questions.this);
        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Saving...");
            progressDialog.show();
        }

        // Do method onNextClickListener in background.
        @Override
        protected Void doInBackground(String... params) {
            onNextClickListener();
            return null;
        }

        // When save data has finished.
        @Override
        protected void onPostExecute(Void s) {
            if (progressDialog.isShowing()) progressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}