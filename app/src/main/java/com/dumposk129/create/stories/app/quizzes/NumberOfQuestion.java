package com.dumposk129.create.stories.app.quizzes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.navigation_drawer.NavigationDrawerFragment;

/**
 * Created by DumpOSK129.
 */
public class NumberOfQuestion extends ActionBarActivity{
    private Toolbar mToolbar;
    private EditText numOfQues, qId;
    private Button btnOK;
    private int quizID;

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_of_question);

        // Casting.
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        numOfQues = (EditText) findViewById(R.id.txtNumOfQuestion);
        btnOK = (Button) findViewById(R.id.btnNumOfQuestion);
        qId = (EditText) findViewById(R.id.quizId);

        // Toolbar and Navigation Drawer.
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

        // Get quizID.
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        quizID = bundle.getInt("quizID"); // quizID is auto increase.

        // Set quizID but not show because users not required.
        qId.setText(Integer.toString(quizID));

        // Button ClickListener.
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = numOfQues.getText().toString();

                if (TextUtils.isEmpty(number)){
                    numOfQues.setError("Please number of question.");
                }else {
                    // Send Data to Question.class
                    Intent intent = new Intent(NumberOfQuestion.this, Questions.class);
                    intent.putExtra("NumOfQuestion", Integer.parseInt(number));
                    intent.putExtra("QuizID", quizID);
                    intent.putExtra("index", 0);
                    startActivity(intent);
                }
            }
        });
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
}