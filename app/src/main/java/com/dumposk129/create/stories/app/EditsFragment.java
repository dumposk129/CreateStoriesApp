package com.dumposk129.create.stories.app;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;


public class EditsFragment extends ActionBarActivity {

    private Toolbar mToolbar;
    private ImageButton imgFloatingActionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edits_fragment);

        //casting
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        imgFloatingActionBtn = (ImageButton) findViewById(R.id.btnFloatingActionEditFragment);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout)findViewById(R.id.drawer_layout), mToolbar);
    }
}
