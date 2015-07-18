package com.dumposk129.create.stories.app.watch;

import android.app.ProgressDialog;
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

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.api.Frame;
import com.dumposk129.create.stories.app.api.Globals;
import com.dumposk129.create.stories.app.model.Story;
import com.dumposk129.create.stories.app.navigation_drawer.NavigationDrawerFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by DumpOSK129.
 */
public class ShowStories extends ActionBarActivity{
    private ListView listView;
    private Toolbar mToolbar;
    private int sId, index = 0, size;
    JSONArray frames = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_list_all_stories_name);

        listView = (ListView) findViewById(R.id.listViewStoriesName);
        mToolbar = (Toolbar) findViewById(R.id.app_bar);

        // Toolbar and Navigation Drawer.
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

        // Call LoadFrameTask
        new LoadFrameTask().execute();
    }

    /* This method is set data after load story has been finished. */
    private void setListData(List<Story> stories) {
        // Assign data.
        String[] data = new String[stories.size()];
        for (int i = 0; i < stories.size(); i++) {
            data[i] = stories.get(i).getTitle();
        }

        // Create ArrayAdapter.
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, data);

        // Send listItem to ListView.
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sId = Globals.stories.get(position).getId();

                // Call LoadFrameList
                new LoadFrameList().execute();
            }
        });
    }

    /* This class is load story in story */
    private class LoadFrameTask extends AsyncTask<String, Void, JSONObject> {
        private ProgressDialog progressDialog = new ProgressDialog(ShowStories.this);
        /* Preparing loading */
        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        // Loading All Story.
        @Override
        protected JSONObject doInBackground(String... params) {
            return com.dumposk129.create.stories.app.api.Story.getStoryTitleName();
        }

        // Show Stories Name.
        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (progressDialog.isShowing())progressDialog.dismiss();
            Globals.stories = com.dumposk129.create.stories.app.api.Story.getStoryList(jsonObject);
            setListData(Globals.stories);
        }
    }

    /* This class is load all frame using story_id*/
    private class LoadFrameList extends AsyncTask<String, Void, Void> {

        // Load All Frame
        @Override
        protected Void doInBackground(String... params) {
            Globals.frames = Frame.getFrameList(sId);
            return null;
        }

        // Set size and intent data
        @Override
        protected void onPostExecute(Void aVoid) {
            size = Globals.frames.size() - 1; // Set size

            Intent intent = new Intent(ShowStories.this, Watch.class);
            intent.putExtra("sId", sId);
            intent.putExtra("size", size);
            intent.putExtra("index", index);
            startActivity(intent);
        }
    }
}