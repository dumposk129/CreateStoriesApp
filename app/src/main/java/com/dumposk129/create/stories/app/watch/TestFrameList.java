package com.dumposk129.create.stories.app.watch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.api.Globals;

/**
 * Created by DumpOSK129.
 */
public class TestFrameList extends ActionBarActivity implements View.OnClickListener {
    private TextView tv;
    private Button btnNext;
    private int index, size, sId;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_flist);

        tv = (TextView) findViewById(R.id.tvTest);
        btnNext = (Button) findViewById(R.id.btnNext);

        /* Get Extra */
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            if (bundle.containsKey("sId")){
                sId = getIntent().getExtras().getInt("sId");
            }

            if (bundle.containsKey("size")){
                size = getIntent().getExtras().getInt("size");
            }

            if (bundle.containsKey("index")){
                index = getIntent().getExtras().getInt("index");
            }
        }

        Toast.makeText(getApplicationContext(), "size: "+size, Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), "index: "+index, Toast.LENGTH_SHORT).show();

        if (index == size){
            btnNext.setText("Done");
        }
        tv.setText("PicPath: "+ Globals.frames.get(index).getPathPic() + "\n AudPath: "+Globals.frames.get(index).getPathAudio());


        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (index == size){
            intent = new Intent(TestFrameList.this, ShowStories.class);
            startActivity(intent);
        }else {
            intent = new Intent(TestFrameList.this, TestFrameList.class);
            intent.putExtra("index", index+1);
            intent.putExtra("size", size);
            startActivity(intent);
        }
    }
}