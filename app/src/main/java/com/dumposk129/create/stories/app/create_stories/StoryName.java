package com.dumposk129.create.stories.app.create_stories;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.api.Story;
import com.dumposk129.create.stories.app.navigation_drawer.NavigationDrawerFragment;

/**
 * Created by DumpOSK129.
 */
public class StoryName extends ActionBarActivity{
    private Toolbar mToolbar;
    private EditText txtName;
    private Button   btnOk;
    private String name;
    private int sId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_name);

        // Casting
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        txtName = (EditText) findViewById(R.id.txtName);
        btnOk = (Button) findViewById(R.id.btnOk);

        // Toolbar and Navigation Drawer.
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = txtName.getText().toString();
                if (TextUtils.isEmpty(name)){ // Check EditText is empty or not.
                    txtName.setError("Please enter Story Name.");
                }else {
                    new SaveStoryTask().execute(); // Call SaveStoryTask
                }
            }
        });
    }

    /* Save story_id and title name*/
    private class SaveStoryTask extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... params) {
            sId = Story.saveStoryId(name); // Get sId.
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = new Intent(StoryName.this, SelectBackground.class); // Intent and putExtra sId.
            intent.putExtra("sId", sId);
            startActivity(intent);
        }
    }
}