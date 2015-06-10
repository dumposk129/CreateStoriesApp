package com.dumposk129.create.stories.app.trash;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.navigation_drawer.NavigationDrawerFragment;

/**
 * Created by DumpOSK129.
 */
public class CreateStories extends ActionBarActivity{
    private Button btnCreateStories;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_stories);

        // Casting
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

        btnCreateStories = (Button) findViewById(R.id.mBtnCreateStories);
        btnCreateStories.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                // Show Dialog and user choose it.
                final AlertDialog.Builder builder = new AlertDialog.Builder(CreateStories.this);
                builder.setTitle(R.string.choose_item).setItems(R.array.audio_record_choose_image, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        Intent intent;
                        switch (position) {
                            case 0:
                                intent = new Intent(CreateStories.this, AudioRecording.class);
                                startActivity(intent);
                                break;
                            case 1:
                                intent = new Intent(CreateStories.this, ChooseImage.class);
                                startActivity(intent);
                                break;
                            case 2:
                                intent = new Intent(CreateStories.this, SoundPath.class);
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
}