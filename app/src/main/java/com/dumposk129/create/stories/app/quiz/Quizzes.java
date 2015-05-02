package com.dumposk129.create.stories.app.quiz;

import android.app.AlertDialog;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by DumpOSK129.
 */


public class Quizzes extends ActionBarActivity {

    private Toolbar mToolbar;
    private ListView listView;
    private TextView tvQuizID, tvQuizName;

    JSONArray quizzes = null;

    ArrayList<HashMap<String, String>> quizList;

    List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_list_all_stories_name);
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

        quizList = new ArrayList<>();
        data = new ArrayList<>();

        new LoadQuizTask().execute();

        // Casting.
        listView = (ListView) findViewById(R.id.listViewStoriesName);
        tvQuizID = (TextView) findViewById(R.id.quizzId);
        tvQuizName = (TextView) findViewById(R.id.quizzesName);


        // Set Item Click Listener.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int selectedStory = position;
                // Show Dialog and users choose it.
                final AlertDialog.Builder builder = new AlertDialog.Builder(Quizzes.this);
                builder.setTitle(R.string.choose_item).setItems(R.array.create_question, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        Intent intent;
                        switch (position) {
                            case 0:
                                intent = new Intent(Quizzes.this, NumberOfQuestion.class);
                                intent.putExtra("quizID", Globals.stories.get(selectedStory).getQuestionId());
                                startActivity(intent);
                                break;
                            case 1:
                                intent = new Intent(Quizzes.this, Answers.class);
                                intent.putExtra("quizID", Globals.stories.get(selectedStory).getQuestionId());
                                intent.putExtra("index", 0);
                                startActivity(intent);
                                break;
                            case 2:
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

    private class LoadQuizTask extends AsyncTask<String, Void, JSONObject> {

        // Load All Quiz
        @Override
        protected JSONObject doInBackground(String... params) {
            return Quiz.getAllQuiz();
        }

        // Set
        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            Globals.stories = Quiz.getQuizList(jsonObject);
            setListData(Globals.stories);
        }
    }

}
