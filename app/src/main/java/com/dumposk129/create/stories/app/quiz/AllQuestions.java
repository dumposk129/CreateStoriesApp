package com.dumposk129.create.stories.app.quiz;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.api.Globals;
import com.dumposk129.create.stories.app.api.Quiz;
import com.dumposk129.create.stories.app.model.Question;
import com.dumposk129.create.stories.app.navigation_drawer.NavigationDrawerFragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by DumpOSK129
 */
public class AllQuestions extends ActionBarActivity{

    private Toolbar mToolbar;
    private ListView listView;
    private int quizId;
    ArrayList<HashMap<String, String>> questionList;

    List<String> questions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Casting
        mToolbar = (Toolbar) findViewById(R.id.app_bar);

        // Toolbar and Navigation Drawer.
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

        questionList = new ArrayList<>();
        questionList = new ArrayList<>();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        quizId = bundle.getInt("quizID");

        new LoadQuestionList().execute();
    }


    private void setQuestionList(List<Question> questions){
        // Assign Question
        String[] data = new String[questions.size()];
        for (int i = 0; i < data.length; i++){
            data[i] = questions.get(i).getQuestionName();
        }

        // Create ArrayAdapter.
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, data);

        // Send listItem to ListView.
        listView.setAdapter(adapter);
    }

    private class LoadQuestionList extends AsyncTask<String, Void, JSONObject>{
        @Override
        protected JSONObject doInBackground(String... params) {
            return Quiz.getAllQuestion();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            Globals.questions = (List<Question>) Quiz.getShowQuestion(quizId);
            setQuestionList(Globals.questions);
        }
    }
}
