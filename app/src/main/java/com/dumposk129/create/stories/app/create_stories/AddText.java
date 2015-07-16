package com.dumposk129.create.stories.app.create_stories;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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

/**
 * Created by DumpOSK129.
 */
public class AddText extends ActionBarActivity implements View.OnClickListener, View.OnTouchListener {
    private TextView tvSubtitle;
    private EditText txtSubtitle;
    private Button btnOK, btnNext, btnColor;
    private ImageView imgFullSize, imgTicker;
    private long frame_id, frame_order;
    private int state = 0;
    private int sId;
    private float x, y;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_text);

        // Casting.
        txtSubtitle = (EditText) findViewById(R.id.txtSubtitle);
        tvSubtitle = (TextView) findViewById(R.id.tvSubtitle);
        btnOK = (Button) findViewById(R.id.btnOk);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnColor = (Button) findViewById(R.id.btnTxtColor);
        imgFullSize = (ImageView) findViewById(R.id.imgAddText);
        imgTicker = (ImageView) findViewById(R.id.imageSticker);
        imgTicker.bringToFront();

        // Set event listener
        btnOK.setOnClickListener(this);
        btnColor.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        imgTicker.setOnTouchListener(this);

        // Set TextView to Draw
        tvSubtitle.setDrawingCacheEnabled(true);

        // Get extra
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("sId")) {
                sId = getIntent().getExtras().getInt("sId");
            }

            if (bundle.containsKey("frame_id")){
                frame_id = (int)getIntent().getExtras().getLong("frame_id");
            }

            if (bundle.containsKey("frame_order")){
                frame_order = (int)getIntent().getExtras().getLong("frame_order");
            }
        }

        // Call class ShowImage.
        ShowImage.showImage(AddText.this, sId, imgFullSize);
    }

    @Override
    public void onClick(View v) {
        if (v == btnOK) {
            // Fill data
            String subtitle = txtSubtitle.getText().toString();
            tvSubtitle.append(subtitle);
            txtSubtitle.setText(" ");

            // Set Invisible
            tvSubtitle.setVisibility(View.VISIBLE);
            btnColor.setVisibility(View.VISIBLE);
        } else if (v == btnNext && state == 0) {
            // Change textView to ImageView
            tvSubtitle.buildDrawingCache();
            imgTicker.setImageBitmap(tvSubtitle.getDrawingCache());

            // Set Invisible
            tvSubtitle.setVisibility(View.INVISIBLE);
            txtSubtitle.setVisibility(View.INVISIBLE);
            btnOK.setVisibility(View.INVISIBLE);
            btnColor.setVisibility(View.INVISIBLE);

            // Change State
            state++;

            // Set text
            btnNext.setText("Finish");
        } else if (v == btnNext && state == 1){
            Bitmap bmpCombined = CombineImage.getImageDrawer(imgFullSize, imgTicker);
            String path = PhotoHelper.writeImagePath(bmpCombined); // Write Image Path
            PhotoHelper.updatePath(getApplicationContext(), (int) frame_id, path); // Update Path

            intent = new Intent(AddText.this, SelectCharacter.class); // Intent and putExtra to SelectCharacter.
            Runtime.getRuntime().freeMemory();
            intent.putExtra("sId", sId);
            intent.putExtra("frame_id", frame_id);
            intent.putExtra("frame_order", frame_order);
            startActivity(intent);
        } else if (v == btnColor) {
            showSelectColor(); // Call method showSelectColor.
        }
    }

    /* Ticker Set On Touch */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        /* Move */
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                x = event.getX();
                y = event.getY();
            }
            break;
            case MotionEvent.ACTION_UP: {
                x = event.getX();
                y = event.getY();
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                FrameLayout.LayoutParams mParams = (FrameLayout.LayoutParams) imgTicker.getLayoutParams();
                x = event.getX();
                y = event.getY();
                mParams.leftMargin = Math.round(x);
                mParams.topMargin = Math.round(y);
                imgTicker.setLayoutParams(mParams);
            }
            break;
        }
        return true;
    }

    // Show dialog color and select
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
                }
            }
        });
        builder.show();
    }
}