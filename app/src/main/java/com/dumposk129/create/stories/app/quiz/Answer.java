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
    private int index;
    private Button answerNext_btnNext;
    private RadioGroup radGrp;
    private RadioButton rbAnswer1, rbAnswer2, rbAnswer3, rbAnswer4;
    private Toolbar mToolbar;

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
        answerNext_btnNext = (Button) findViewById(R.id.btnNextAnswer);
        radGrp = (RadioGroup) findViewById(R.id.rgAnswerForm);

        // Toolbar and Navigation Drawer.
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);


        // Show question and answer from database
        answerNext_btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        //get index from bundle
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        int currentIndex = bundle.getInt("index");
        if(currentIndex == 0){
            new ShowQuestionTask().execute();
        }

        if(currentIndex < Globals.questions.size()) {
            Question currentQuestion = Globals.questions.get(currentIndex);

            // set value to UI from currentQuestion

            // bind Next Action
            // - Check corrected the selected choices
            // - If correct, show correct message and appears the next button.
            // - If incorrect, show incorrect and do not show the next button
        }
    }

    private class ShowQuestionTask extends AsyncTask<String, Void, JSONArray>{

        @Override
        protected void onPostExecute(JSONArray result) {
            Globals.questions = Quiz.getQuestions(result);
        }

        @Override
        protected JSONArray doInBackground(String... params) {
            return  Quiz.getShowQuestion("1");
        }

    }
}
