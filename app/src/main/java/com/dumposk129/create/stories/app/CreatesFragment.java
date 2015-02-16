package com.dumposk129.create.stories.app;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public class CreatesFragment extends ActionBarActivity {

    private Toolbar mToolbar;
    private ImageButton imgFloatingActionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creates_fragment);

        //casting
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        imgFloatingActionBtn = (ImageButton) findViewById(R.id.btnFloatingActionCreateFragment);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout)findViewById(R.id.drawer_layout), mToolbar);

        imgFloatingActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Selected Button", Toast.LENGTH_LONG).show();
            }
        });
    }
}
