package com.dumposk129.create.stories.app.quizzes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dumposk129.create.stories.app.R;
import com.dumposk129.create.stories.app.api.ApiConfig;
import com.dumposk129.create.stories.app.api.Globals;
import com.dumposk129.create.stories.app.api.Quiz;
import com.dumposk129.create.stories.app.navigation_drawer.NavigationDrawerFragment;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by DumpOSK129 on 28/7/2558.
 */
public class Quizzes extends ActionBarActivity {
    private Toolbar mToolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int sId;
    private boolean doubleBackToExitPressedOnce = false;
    private GridView gridView;
    private int number;
    String[] titleNAME, img, name, imgPath;
    private int quizId;
    private TextView tvQuizID, tvQuizName;
    private int selectedStory;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_stories);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

        tvQuizID = (TextView) findViewById(R.id.quizzesId);
        tvQuizName = (TextView) findViewById(R.id.quizzesName);

        new LoadQuizTask().execute();

        gridView = (GridView) findViewById(R.id.gridImage);
    }


    private String[] getImageListForStoryName(int number) {
        img = new String[number];
        imgPath = new String[number];

        for (int i = 0; i < Globals.stories.size(); i++) {
            img[i] = ApiConfig.apiUrl + Globals.stories.get(i).getPic_path();
            imgPath[i] = img[i];
        }

        return img;
    }

    private String[] getListStoryName(int number) {
        titleNAME = new String[number];
        name = new String[number];
        for (int i = 0; i < Globals.stories.size(); i++) {
            titleNAME[i] = Globals.stories.get(i).getTitle();
            name[i] = titleNAME[i];
        }

        return titleNAME;
    }

    private class CustomAdapter extends BaseAdapter {
        private String[] titleName;
        private Context context;
        private String[] picPath;

        LayoutInflater inflater = null;

        public CustomAdapter(LoadQuizTask loadQuizTask, String[] name, String[] imgPath) {
            titleName = name;
            picPath = imgPath;
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return titleName.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public class Holder {
            TextView tv;
            ImageView img;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Holder holder = new Holder();
            View rowView;

            rowView = inflater.inflate(R.layout.content_story, null);
            holder.tv = (TextView) rowView.findViewById(R.id.textView1);
            holder.img = (ImageView) rowView.findViewById(R.id.imgView);

            holder.tv.setText(titleName[position]);
            Glide.with(Quizzes.this)
                    .load(picPath[position])
                    .centerCrop()
                    .into(holder.img);

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sId = Globals.stories.get(position).getId();
                    selectedStory = position;

                    Toast.makeText(Quizzes.this, " " + titleName[position], Toast.LENGTH_LONG).show();

                    // Show Dialog and user choose it.
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Quizzes.this);
                    builder.setTitle(R.string.choose_item).setItems(R.array.questions_answers, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            switch (position) {
                                case 0:
                                    intent = new Intent(Quizzes.this, NumberOfQuestion.class);
                                    intent.putExtra("quizID", Globals.stories.get(selectedStory).getQuestionId());
                                    startActivity(intent);
                                    break;
                                case 1:
                                    quizId = Globals.stories.get(selectedStory).getQuestionId();
                                    new LoadQuestionList().execute();
                                    break;
                                case 2:
                                    intent = new Intent(Quizzes.this, AllQuestions.class);
                                    intent.putExtra("quizID", Globals.stories.get(selectedStory).getQuestionId());
                                    startActivity(intent);
                                    break;
                                case 3:
                                    dialog.dismiss();
                            }
                        }
                    });
                    builder.show();
                }
            });
            return rowView;
        }
    }

    /* Load Story all name */
    private class LoadQuizTask extends AsyncTask<String, Void, JSONObject> {
        /* Preparing Load data */
        private ProgressDialog progressDialog = new ProgressDialog(Quizzes.this);

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        // Loading All Story.
        @Override
        protected JSONObject doInBackground(String... params) {
            return Quiz.getAllQuiz();
        }

        // Show Stories Name.
        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            /*if (swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(false);*/

            Globals.stories = Quiz.getQuizList(jsonObject);
            number = Globals.stories.size();
            getListStoryName(number);
            getImageListForStoryName(number);
            gridView.setAdapter(new CustomAdapter(this, name, imgPath));
        }
    }

    /* Load Question */
    private class LoadQuestionList extends AsyncTask<String, Void, JSONArray> {

        // Load all questions.
        @Override
        protected JSONArray doInBackground(String... params) {
            return Quiz.getShowQuestion(quizId);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            Globals.questions = Quiz.getQuestions(jsonArray); // get question pass Quiz.getQuestions

            if (Globals.questions.size() == 0) { // if no question show dialog.
                AlertDialog dialog1 = new AlertDialog.Builder(Quizzes.this)
                        .setTitle("No Question")
                        .setMessage("Please create question first.")
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            } else { // has question and set quizID, index and then go to Answers.
                intent = new Intent(Quizzes.this, Answers.class);
                intent.putExtra("quizID", Globals.stories.get(selectedStory).getQuestionId());
                intent.putExtra("index", 0);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}