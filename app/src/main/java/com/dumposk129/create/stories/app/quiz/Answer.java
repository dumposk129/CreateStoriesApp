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
import com.dumposk129.create.stories.app.model.Question;
import com.dumposk129.create.stories.app.navigation_drawer.NavigationDrawerFragment;

import org.json.JSONArray;


public class Answer extends ActionBarActivity {
    private TextView tvQuestion, tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4, tvIsCorrect;
    private Button btnAnswerNext;
    private RadioGroup radGrp;
    private RadioButton rbAnswer1, rbAnswer2, rbAnswer3, rbAnswer4;
    private Toolbar mToolbar;
    private int noOfQuestion;
    private int currentIndex;

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
        noOfQuestion = bundle.getInt("NumOfQuestion");
        currentIndex = bundle.getInt("index");

        // Get Question from currentIndex
        if (currentIndex == 0) {
            new ShowQuestionTask().execute();
        }

        // Change textView from Next to Finished when NumberOfQuestion equal currentIndex.
        if (currentIndex == noOfQuestion) {
            btnAnswerNext.setText("Finished");
        }

        if (currentIndex < Globals.questions.size()) {
            Question currentQuestion = Globals.questions.get(currentIndex);

            // set value to UI from currentQuestion
            tvQuestion.getText().toString();
            tvAnswer1.getText().toString();
            tvAnswer2.getText().toString();
            tvAnswer3.getText().toString();
            tvAnswer4.getText().toString();

            // bind Next Action
            // - Check corrected the selected choices
            btnAnswerNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int selectedId = radGrp.getCheckedRadioButtonId();

                    // If correct, show correct message and appears the next button.
                    if (selectedId == rbAnswer1.getId() /*&& selectedId == Quiz.getQuestions()*/) {
                        ShowMessageAndNextBtn();
                    } else if (selectedId == rbAnswer2.getId() /*&& selectedId == Quiz.getQuestions()*/) {
                        ShowMessageAndNextBtn();
                    } else if (selectedId == rbAnswer3.getId() /*&& selectedId == Quiz.getQuestions()*/) {
                        ShowMessageAndNextBtn();
                    } else if (selectedId == rbAnswer4.getId() /*&& selectedId == Quiz.getQuestions()*/) {
                        ShowMessageAndNextBtn();
                    }
                }
            });
            // - If incorrect, show incorrect and do not show the next button
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

        }
    }

    private class ShowQuestionTask extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected void onPostExecute(JSONArray result) {
            Globals.questions = Quiz.getQuestions(result);
        }

        @Override
        protected JSONArray doInBackground(String... params) {
            onAnswerNextClickListener();
            return Quiz.getShowQuestion("1");
        }
    }
}
