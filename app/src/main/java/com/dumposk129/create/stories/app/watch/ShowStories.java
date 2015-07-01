package com.dumposk129.create.stories.app.watch;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.api.Globals;
import com.dumposk129.create.stories.app.api.Quiz;
import com.dumposk129.create.stories.app.model.Story;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by DumpOSK129.
 */
public class ShowStories extends ActionBarActivity{
    private ListView listView;

    ArrayList<HashMap<String, String>> quizList = new ArrayList<>();

    List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_list_all_stories_name);
        listView = (ListView) findViewById(R.id.listViewStoriesName);
        new LoadQuizTask().execute();

        // Set Item Click Listener.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ShowStories.this, Watch.class);
                intent.putExtra("sId", id);
                startActivity(intent);
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
        // Load All Story.
        @Override
        protected JSONObject doInBackground(String... params) {
            return Quiz.getAllQuiz();
        }

        // Show Stories Name.
        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            Globals.stories = Quiz.getQuizList(jsonObject);
            setListData(Globals.stories);
        }
    }
}