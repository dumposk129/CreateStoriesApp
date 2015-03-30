package com.dumposk129.create.stories.app.create;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.dumposk129.create.stories.app.R;

/**
 * Created by DumpOSK129 on 3/6/2015.
 */
public class Charactor_Show extends ActionBarActivity{
    public static String CHARACTOR_INDEX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_charactor);

        GridView gridView = (GridView) findViewById(R.id.gridView);
        final CharactorAdapter charactorAdapter = new CharactorAdapter(this);
        gridView.setAdapter(charactorAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CreateStory.class);
                intent.putExtra(CHARACTOR_INDEX,String.valueOf(position));
                startActivity(intent);
            }
        });
    }
}
