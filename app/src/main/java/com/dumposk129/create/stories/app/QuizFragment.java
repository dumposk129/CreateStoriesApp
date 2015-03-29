package com.dumposk129.create.stories.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


public class QuizFragment extends ActionBarActivity {

    private Toolbar mToolbar;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionnaires_fragment);
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

        // Assign data
        final String[] data = new String[]{"Test1", "Test2", "Test3"};

        // Create ArrayAdapter
       ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, data);

        // Send listItem to ListView
        ListView listView = (ListView) findViewById(R.id.listViewQuestion);
        listView.setAdapter(adapter);

        // Set Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(QuizFragment.this);
                builder.setTitle(R.string.choose_item).setItems(R.array.create_answer, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent;
                                switch (which){
                                    case 0:
                                        intent = new Intent(getApplicationContext(), Question.class);
                                        startActivity(intent);
                                        break;
                                    case 1:
                                        intent = new Intent(getApplicationContext(), Answer.class);
                                        startActivity(intent);
                                        break;
                                    case 2: dialog.dismiss();
                                }
                            }
                        });
                builder.show();
            }
        });
    }
}
