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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by DumpOSK129.
 */
public class ShowStories extends ActionBarActivity{
    private ListView listView;
    private Toolbar mToolbar;
    private int sId, index = 0, size;
    JSONArray frames = null;

    List<com.dumposk129.create.stories.app.model.Frame> list = new ArrayList<>();

    ArrayList<HashMap<String, String>> frameList = new ArrayList<>();

    List<String> data = new ArrayList<>();

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

        new LoadFrameTask().execute();
    }

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
                sId = position;

                new LoadFrameList().execute();
                Globals.frames = Frame.getFrameList(sId);
                size = list.size() - 1;

                Intent intent = new Intent(ShowStories.this, TestFrameList.class);
                intent.putExtra("sId", sId);
                intent.putExtra("size", size);
                intent.putExtra("index", index);


                //TODO:: Loadframelist (Async Task) หลังจากที่ได้ข้อมูลมาแล้วให้มาเก็บในตัวแปล Global.frame = API.Frame.getFrameList(story_id)
                //TODO:: send extra index = 0
                //TODO:: send size of list(frame)
                startActivity(intent);
            }
        });
    }

    private class LoadFrameTask extends AsyncTask<String, Void, JSONObject> {
        private ProgressDialog progressDialog = new ProgressDialog(ShowStories.this);
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

    private class LoadFrameList extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            list = Frame.getFrameList(sId);
            return null;
        }
    }
}