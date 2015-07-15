package com.dumposk129.create.stories.app.quizzes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.api.Globals;
import com.dumposk129.create.stories.app.api.Quiz;
import com.dumposk129.create.stories.app.model.Story;
import com.dumposk129.create.stories.app.navigation_drawer.NavigationDrawerFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by DumpOSK129.
 */
public class Quizzes extends ActionBarActivity {
    private Toolbar mToolbar;
    private ListView listView;
    private TextView tvQuizID, tvQuizName;
    private int quizId;
    private int selectedStory;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_list_all_stories_name);

        // Casting.
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        listView = (ListView) findViewById(R.id.listViewStoriesName);
        tvQuizID = (TextView) findViewById(R.id.quizzesId);
        tvQuizName = (TextView) findViewById(R.id.quizzesName);

        new LoadQuizTask().execute();

        // Set Item Click Listener.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedStory = position;

                // Show Dialog and user choose it.
                final AlertDialog.Builder builder = new AlertDialog.Builder(Quizzes.this);
                builder.setTitle(R.string.choose_item).setItems(R.array.questions_answers, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                intent = new Intent(Quizzes.this, NumberOfQuestion.class);
                                intent.putExtra("quizID", Globals.stories.get(selectedStory).getQuestionId());
                                startActivity(intent);
                                break;
                            case 1:
                                quizId = Globals.stories.get(selectedStory).getQuestionId();
                                new LoadQuestionList().execute();
                                break;
                            case 2:
                                intent = new Intent(Quizzes.this, AllQuestions.class);
                                intent.putExtra("quizID", Globals.stories.get(selectedStory).getQuestionId());
                                startActivity(intent);
                                break;
                            case 3:
                                dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });
    }

    private void setListData(List<Story> stories) {
        // Assign data.
        String[] data = new String[stories.size()];
        for (int i = 0; i < stories.size(); i++) {
            data[i] = stories.get(i).getQuestionName();
        }

        // Create ArrayAdapter.
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, data);

        // Send listItem to ListView.
        listView.setAdapter(adapter);
    }

    /* Load Story all name */
    private class LoadQuizTask extends AsyncTask<String, Void, JSONObject> {
        /* Preparing Load data */
        private ProgressDialog progressDialog = new ProgressDialog(Quizzes.this);

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        // Loading All Story.
        @Override
        protected JSONObject doInBackground(String... params) {
            return Quiz.getAllQuiz();
        }

        // Show Stories Name.
        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (progressDialog.isShowing()) progressDialog.dismiss();
            Globals.stories = Quiz.getQuizList(jsonObject);
            setListData(Globals.stories);
        }
    }

    /* Load Question */
    private class LoadQuestionList extends AsyncTask<String, Void, JSONArray> {

        // Load all questions.
        @Override
        protected JSONArray doInBackground(String... params) {
            return Quiz.getShowQuestion(quizId);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            Globals.questions = Quiz.getQuestions(jsonArray); // get question pass Quiz.getQuestions
            if (Globals.questions.size() == 0) { // if no question show dialog.
                AlertDialog dialog1 = new AlertDialog.Builder(Quizzes.this)
                        .setTitle("No Question")
                        .setMessage("Please create question first.")
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            } else { // has question and set quizID, index and then go to Answers.
                intent = new Intent(Quizzes.this, Answers.class);
                intent.putExtra("quizID", Globals.stories.get(selectedStory).getQuestionId());
                intent.putExtra("index", 0);
                startActivity(intent);
            }
        }
    }
}