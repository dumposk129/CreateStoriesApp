package com.dumposk129.create.stories.app.quiz;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;

import com.dumposk129.create.stories.app.R;

public class Answer extends ActionBarActivity{
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.answer);
    }
}
