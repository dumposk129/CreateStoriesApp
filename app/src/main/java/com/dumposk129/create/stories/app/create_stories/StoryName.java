package com.dumposk129.create.stories.app.create_stories;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dumposk129.create.stories.app.R;

/**
 * Created by DumpOSK129.
 */
public class StoryName extends ActionBarActivity implements View.OnClickListener{
    private EditText txtName, tvStoryId;
    private Button   btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_name);

        txtName = (EditText) findViewById(R.id.txtName);
        tvStoryId = (EditText) findViewById(R.id.txtStoryId);
        btnOk = (Button) findViewById(R.id.btnOk);

        btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        txtName.getText().toString();

        saveTitleNameToDB();

        Intent intent = new Intent(StoryName.this, SelectBackground.class);
       // intent.putExtra("sId", tvStoryId);
        startActivity(intent);
    }

    private void saveTitleNameToDB() {

    }
}
