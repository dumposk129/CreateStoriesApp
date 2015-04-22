/*

package com.dumposk129.create.stories.app.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.dumposk129.create.stories.app.R;

*/
/**
 * Created by DumpOSK129.
 *//*


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.create.EditStory;

public class Character_Show extends ActionBarActivity {
    public static String CHARACTER_INDEX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_character);

        GridView gridView = (GridView) findViewById(R.id.gridView);
        final CharacterAdapter characterAdapter = new CharacterAdapter(this);
        gridView.setAdapter(characterAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), EditStory.class);
                intent.putExtra(CHARACTER_INDEX,String.valueOf(position));
                startActivity(intent);
            }
        });
    }
}*/