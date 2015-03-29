package com.dumposk129.create.stories.app;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by DumpOSK129 on 3/29/2015.
 */
public class Question extends ActionBarActivity{
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.question);
    }
}
