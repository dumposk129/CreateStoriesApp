package com.dumposk129.create.stories.app.create_stories;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
public class AddText extends ActionBarActivity implements View.OnClickListener, View.OnTouchListener {
    private TextView tvSubtitle;
    private EditText txtSubtitle;
    private Button btnOK, btnNext, btnColor;
    private ImageView imgView;
    private Bitmap bitmap;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_text);

        txtSubtitle = (EditText) findViewById(R.id.txtSubtitle);
        tvSubtitle = (TextView) findViewById(R.id.tvSubtitle);
        btnOK = (Button) findViewById(R.id.btnOk);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnColor = (Button) findViewById(R.id.btnSelectColor);
        imgView = (ImageView) findViewById(R.id.imgAddText);

        btnOK.setOnClickListener(this);
        btnColor.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        tvSubtitle.setOnTouchListener(this);

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
        if (v == btnOK) {
            String subtitle = txtSubtitle.getText().toString();
            tvSubtitle.append(subtitle);
            tvSubtitle.setVisibility(View.VISIBLE);
            btnColor.setVisibility(View.VISIBLE);
        } else if (v == btnNext) {
            combineText(bitmap, tvSubtitle);

            Intent intent = new Intent(AddText.this, SelectCharacter.class);
            startActivity(intent);
        } else if (v == btnColor) {
            showSelectColor();
        }
    }

    private void combineText(Bitmap bitmap, TextView tvSubtitle) {
    }

    /* Ticker Set On Touch */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
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
        return true;
    }

    private void showSelectColor() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(AddText.this);
        builder.setTitle(R.string.select_color).setItems(R.array.color, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int postion) {
                switch (postion) {
                    case 0:
                        tvSubtitle.setTextColor(Color.RED);
                        break;
                    case 1:
                        tvSubtitle.setTextColor(Color.BLACK);
                        break;
                    case 2:
                        tvSubtitle.setTextColor(Color.BLUE);
                        break;
                    case 3:
                        tvSubtitle.setTextColor(Color.WHITE);
                        break;
                    case 4:
                        tvSubtitle.setTextColor(Color.YELLOW);
                        break;
                    case 5:
                        tvSubtitle.setTextColor(Color.DKGRAY);
                        break;
                }
            }
        });
        builder.show();
    }
}