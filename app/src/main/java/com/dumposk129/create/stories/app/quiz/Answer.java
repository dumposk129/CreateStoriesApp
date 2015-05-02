package com.dumposk129.create.stories.app.quiz;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.api.Globals;
import com.dumposk129.create.stories.app.api.Quiz;
import com.dumposk129.create.stories.app.model.Choice;
import com.dumposk129.create.stories.app.model.Question;
import com.dumposk129.create.stories.app.navigation_drawer.NavigationDrawerFragment;

import org.json.JSONArray;

import java.util.List;


public class Answer extends ActionBarActivity {
    private TextView tvQuestion, tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4, tvIsCorrect;
    private Button btnAnswerNext;
    private RadioGroup radGrp;
    private RadioButton rbAnswer1, rbAnswer2, rbAnswer3, rbAnswer4;
    private Toolbar mToolbar;
    private int noOfQuestion;
    private int currentIndex;
    private int correctIndexAnswer;
    private int selectAnswer;
    private int quizId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_form);

        // Casting
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        tvQuestion = (TextView) findViewById(R.id.tvQuestionName);
        tvAnswer1 = (TextView) findViewById(R.id.tvAnswer_1);
        tvAnswer2 = (TextView) findViewById(R.id.tvAnswer_2);
        tvAnswer3 = (TextView) findViewById(R.id.tvAnswer_3);
        tvAnswer4 = (TextView) findViewById(R.id.tvAnswer_4);
        btnAnswerNext = (Button) findViewById(R.id.btnNextAnswer);
        tvIsCorrect = (TextView) findViewById(R.id.tvIsCorrect);
        radGrp = (RadioGroup) findViewById(R.id.rgAnswerForm);
        rbAnswer1 = (RadioButton) findViewById(R.id.rbAnswer_1);
        rbAnswer2 = (RadioButton) findViewById(R.id.rbAnswer_2);
        rbAnswer3 = (RadioButton) findViewById(R.id.rbAnswer_3);
        rbAnswer4 = (RadioButton) findViewById(R.id.rbAnswer_4);

        // Toolbar and Navigation Drawer.
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

        // Get index from bundle
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        currentIndex = bundle.getInt("index");
        quizId = bundle.getInt("quizID");

        // Get Question from currentIndex
        if (currentIndex == 0) {
            new ShowQuestionTask().execute();
        } else {
            setUIText();
        }

        // RadioGroup CheckChangeListener
        radGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rbAnswer1.getId()) {
                    selectAnswer = 0;
                } else if (checkedId == rbAnswer2.getId()) {
                    selectAnswer = 1;
                } else if (checkedId == rbAnswer3.getId()) {
                    selectAnswer = 2;
                } else {
                    selectAnswer = 3;
                }
                checkCorrectAnswer();
            }
        });

        btnAnswerNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAnswerNextClickListener();
            }
        });
    }

    private void checkCorrectAnswer() {
        if (selectAnswer == correctIndexAnswer) {
            ShowMessageAndNextBtn();
        }else {
            btnAnswerNext.setVisibility(View.INVISIBLE);
            btnAnswerNext.setVisibility(View.INVISIBLE);
        }
    }

    // Show Message And Next Button
    private void ShowMessageAndNextBtn() {
        tvIsCorrect.setVisibility(View.VISIBLE);
        btnAnswerNext.setVisibility(View.VISIBLE);
    }

    // Next Button ClickListener
    private void onAnswerNextClickListener() {
        if (currentIndex == noOfQuestion) {
            // Go to Quizzes Page
            Intent intent = new Intent(Answer.this, Quizzes.class);
            startActivity(intent);
        } else {
            // Answer Next
            Intent intent = new Intent(Answer.this, Answer.class);
            intent.putExtra("index", currentIndex + 1);
            startActivity(intent);
        }
    }

    // Set Question and Answer
    private void setUIText() {
        if (currentIndex < Globals.questions.size()) {
            Question currentQuestion = Globals.questions.get(currentIndex);
            // set value to UI from currentQuestion
            tvQuestion.setText(currentQuestion.getQuestionName());
            if (currentQuestion.getChoices() != null) {
                tvAnswer1.setText(currentQuestion.getChoices().get(0).getChoiceName());
                tvAnswer2.setText(currentQuestion.getChoices().get(1).getChoiceName());
                tvAnswer3.setText(currentQuestion.getChoices().get(2).getChoiceName());
                tvAnswer4.setText(currentQuestion.getChoices().get(3).getChoiceName());
            }
            if (currentIndex == noOfQuestion) {
                btnAnswerNext.setText("Finished");
            }
            correctIndexAnswer = getCorrectAnswer(currentQuestion.getChoices());
        }
    }

    // Set Correct Answer
    private int getCorrectAnswer(List<Choice> choices) {
        int correctIndex = -1;
        for (int i = 0; i < choices.size(); i++) {
            if (choices.get(i).isCorrect() == 1) {
                correctIndex = i;
            }
        }
        return correctIndex;
    }

    // doInBackground
    private class ShowQuestionTask extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected void onPostExecute(JSONArray result) {
            Globals.questions = Quiz.getQuestions(result);
            noOfQuestion = Globals.questions.size();
            setUIText();
        }

        @Override
        protected JSONArray doInBackground(String... params) {
            return Quiz.getShowQuestion(quizId);
        }
    }
}
