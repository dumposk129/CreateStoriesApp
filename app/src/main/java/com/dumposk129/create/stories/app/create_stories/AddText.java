package com.dumposk129.create.stories.app.create_stories;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.sql.DatabaseHelper;

/**
 * Created by DumpOSK129.
 */
public class AddText extends ActionBarActivity implements View.OnClickListener, View.OnTouchListener{
    private TextView tvSubtitle;
    private EditText txtSubtitle;
    private Button btnOK, btnNext;
    private ImageView imgView;
    private Bitmap bitmap;
    private GestureDetector gestureDetector;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_text);

        txtSubtitle = (EditText) findViewById(R.id.txtSubtitle);
        tvSubtitle = (TextView) findViewById(R.id.tvSubtitle);
        btnOK = (Button) findViewById(R.id.btnOk);
        btnNext = (Button) findViewById(R.id.btnNext);
        imgView = (ImageView) findViewById(R.id.imgAddText);

        btnOK.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        tvSubtitle.setOnTouchListener(this);
      //  tvSubtitle.setOnClickListener(this);

        gestureDetector = new GestureDetector(this, new SingleTapConfirm());

        showImage();
    }

    /* Show Image from database */
    private void showImage() {
        String path_pic = null;
        db = new DatabaseHelper(getApplicationContext());
        path_pic = db.getPath(2);

        imgView.setImageBitmap(BitmapFactory.decodeFile(path_pic));
        bitmap = BitmapFactory.decodeFile(path_pic);
    }

    @Override
    public void onClick(View v) {
        if (v == btnOK){
            String subtitle = txtSubtitle.getText().toString();
            tvSubtitle.append(subtitle);

            tvSubtitle.setVisibility(View.VISIBLE);
        } else if (v == btnNext){
            combineText(bitmap, tvSubtitle);

            Intent intent = new Intent(AddText.this, SelectCharacter.class);
            startActivity(intent);
        }
    }

    private void combineText(Bitmap bitmap, TextView tvSubtitle) {
    }

    /* Ticker Set On Touch */
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        /* Single Tab */
        if (gestureDetector.onTouchEvent(event)){
            return true;
        } else{
            /* Move */
            int x = (int) event.getX();
            int y = (int) event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                }
                break;
                case MotionEvent.ACTION_MOVE: {
                    FrameLayout.LayoutParams mParams = (FrameLayout.LayoutParams) tvSubtitle.getLayoutParams();
                    mParams.leftMargin = x;
                    mParams.topMargin = y;
                    tvSubtitle.setLayoutParams(mParams);
                }
                break;
                case MotionEvent.ACTION_UP: {
                }
                break;
            }
        }
        return false;
    }

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
            alertDialog.setTitle("Select Color");
            alertDialog.setItems(R.array.select_color, new  Dialog.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case 0: tvSubtitle.setTextColor(Color.RED);
                            break;
                        case 1: tvSubtitle.setTextColor(Color.BLACK);
                            break;
                        case 2: tvSubtitle.setTextColor(Color.BLUE);
                            break;
                    }
                }
            });
            return true;
        }
    }
}