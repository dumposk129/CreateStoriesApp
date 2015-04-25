package com.dumposk129.create.stories.app.create;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.navigation_drawer.NavigationDrawerFragment;

/**
 * Created by DumpOSK129.
 */
public class Creates extends ActionBarActivity {

    private Toolbar mToolbar;
    public static LinearLayout.LayoutParams layoutParams;
    private int RESULT_GALLERY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_main);

        //casting
        mToolbar = (Toolbar) findViewById(R.id.app_bar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

        /*AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage("Do you want to Create or Edit Stories")
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), SelectBackground.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), SelectStory.class);
                        startActivity(intent);
                    }
                });
        builder.show();*/
    }
}
