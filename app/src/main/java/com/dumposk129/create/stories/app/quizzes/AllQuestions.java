package com.dumposk129.create.stories.app.quizzes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.api.Globals;
import com.dumposk129.create.stories.app.api.Quiz;
import com.dumposk129.create.stories.app.model.Question;
import com.dumposk129.create.stories.app.navigation_drawer.NavigationDrawerFragment;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by DumpOSK129
 */
public class AllQuestions extends ActionBarActivity{
    private Toolbar mToolbar;
    private ListView listView;
    private int quizId;

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_name_all_list_item);

        // Casting.
        listView = (ListView) findViewById(R.id.question_name_all_list_item);
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

        // Get quizID.
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        quizId = bundle.getInt("quizID");

        // LoadQuestionList method using AsyncTask.
        new LoadQuestionList().execute();
    }

    private void setListQuestions(List<Question> questions){
        // Assign Questions.
        String[] data = new String[questions.size()];
        for (int i = 0; i < data.length; i++){
            data[i] = questions.get(i).getQuestionName();
        }

        // Create ArrayAdapter.
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, data);

        // Send listItem to ListView.
        listView.setAdapter(adapter);
    }

    private class LoadQuestionList extends AsyncTask<String, Void, JSONArray>{
        /* Preparing Load data */
        private ProgressDialog progressDialog = new ProgressDialog(AllQuestions.this);
        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        // Loading all questions.
        @Override
        protected JSONArray doInBackground(String... params) {
            return Quiz.getShowQuestion(quizId);
        }

        // Show all questions.
        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            if (progressDialog.isShowing())progressDialog.dismiss();
            Globals.questions = Quiz.getQuestions(jsonArray);
            setListQuestions(Globals.questions);
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