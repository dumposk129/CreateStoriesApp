package com.dumposk129.create.stories.app;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;


public class CreatesFragment extends ActionBarActivity {

    private Toolbar mToolbar;
    //private ImageButton FloatingActionBtn, FloatingCharacterMenu, FloatingBackgroundMenu, FloatingSoundMenu, FloatingSubtitleMenu;
    private  ImageView floatingBtn, iconCharacter, iconBackground, iconSound, iconSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creates_fragment);

        //casting
        mToolbar = (Toolbar) findViewById(R.id.app_bar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout)findViewById(R.id.drawer_layout), mToolbar);


        //Create Floating Button
        floatingBtn = new ImageView(this);
        floatingBtn.setImageResource(R.drawable.ring);
        FloatingActionButton actionButton = new FloatingActionButton.Builder(this).setContentView(floatingBtn).build();

        //Create Menu Items
        iconCharacter = new ImageView(this);
        iconCharacter.setImageResource(R.drawable.sub_ring);
        iconBackground = new ImageView(this);
        iconBackground.setImageResource(R.drawable.sub_ring);
        iconSound = new ImageView(this);
        iconSound.setImageResource(R.drawable.sub_ring);
        iconSubtitle = new ImageView(this);
        iconSubtitle.setImageResource(R.drawable.sub_ring);
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        SubActionButton buttonCharacter = itemBuilder.setContentView(iconCharacter).build();
        SubActionButton buttonBackground = itemBuilder.setContentView(iconBackground).build();
        SubActionButton buttonSound = itemBuilder.setContentView(iconSound).build();
        SubActionButton buttonSubtitle = itemBuilder.setContentView(iconSubtitle).build();

        //Create the menu with the items:
        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(buttonCharacter)
                .addSubActionView(buttonBackground)
                .addSubActionView(buttonSound)
                .addSubActionView(buttonSubtitle)
                        // ...
                .attachTo(actionButton)
                .build();
    }
}
