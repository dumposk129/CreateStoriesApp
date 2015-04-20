package com.dumposk129.create.stories.app.create;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.dumposk129.create.stories.app.R;

/**
 * Created by DumpOSK129 on 3/6/2015.
 */
public class Write extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_character);
        SharedPreferences sp = getSharedPreferences(   getString(R.string.write_text_to_sdcard),
                                                                            Context.MODE_PRIVATE  );

        SharedPreferences.Editor e = sp.edit();
        e.putString("write_text_to_sdcard", "Hello World");
        e.commit();
        String stringValue = sp.getString("write_text_to_sdcard", "error");
        Toast.makeText(getApplicationContext(), stringValue, Toast.LENGTH_LONG).show();
    }
}
