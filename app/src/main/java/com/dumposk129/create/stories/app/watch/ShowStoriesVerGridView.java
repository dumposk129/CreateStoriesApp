package com.dumposk129.create.stories.app.watch;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.api.ApiConfig;
import com.dumposk129.create.stories.app.api.Frame;
import com.dumposk129.create.stories.app.api.Globals;
import com.dumposk129.create.stories.app.model.Story;
import com.dumposk129.create.stories.app.navigation_drawer.NavigationDrawerFragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowStoriesVerGridView extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener {
    private Toolbar mToolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int sId, index = 0, size;
    private boolean doubleBackToExitPressedOnce = false;
    private GridView gridView;

    List<Story> stories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_stories);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED);
        swipeRefreshLayout.setOnRefreshListener(this);

        new LoadFrameTask().execute();

        String[] name = getListStoryName();
        String[] imgPath = getImageListForStoryName();

        gridView = (GridView) findViewById(R.id.gridImage);
        gridView.setAdapter(new CustomAdapter(this, name, imgPath));


    }

    private String[] getImageListForStoryName() {
        String[] img = null;
        for (int i = 0; i < Globals.stories.size(); i++){
            img[i] = ApiConfig.apiUrl + Globals.stories.get(i).getPic_path();
        }

        return img;
    }

    private String[] getListStoryName() {
        String[] title = null;

        for (int i =0; i < Globals.stories.size(); i++){
            title[i] = Globals.stories.get(i).getTitle();
        }

        return title;
    }



    @Override
    public void onRefresh() {
        new LoadFrameTask().execute();
    }

    //This class is load story in story

    private class LoadFrameTask extends AsyncTask<String, Void, JSONObject> {
        private ProgressDialog progressDialog = new ProgressDialog(ShowStoriesVerGridView.this);
        //Preparing loading

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
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            if (swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(false);

            Globals.stories = com.dumposk129.create.stories.app.api.Story.getStoryList(jsonObject);
            new CustomAdper(Globals.stories);
        }
    }

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

            Intent intent = new Intent(ShowStoriesVerGridView.this, Watch.class);
            intent.putExtra("sId", sId);
            intent.putExtra("size", size);
            intent.putExtra("index", index);
            startActivity(intent);
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

    private class CustomAdper {
        public CustomAdper(List<Story> stories) {
        }
    }

    private class CustomAdapter extends BaseAdapter {
        private String[] result;
        Context context;
        private static LayoutInflater inflater = null;

        public CustomAdapter(ShowStoriesVerGridView showStoriesVerGridView, List<Story> stories) {


        }

        @Override
        public int getCount() {
            return 0;
        }


        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
