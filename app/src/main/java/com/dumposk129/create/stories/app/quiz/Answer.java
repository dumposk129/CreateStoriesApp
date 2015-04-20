package com.dumposk129.create.stories.app.quiz;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dumposk129.create.stories.app.R;

public class Answer extends ActionBarActivity{
    private TextView question, answer1, answer2, answer3, answer4;
    private Button btnNext;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.question_form);

        //casting
        question = (TextView) findViewById(R.id.txtQuestion);
        answer1 =  (TextView) findViewById(R.id.textAnswer1);
        answer2 =  (TextView) findViewById(R.id.textAnswer2);
        answer3 =  (TextView) findViewById(R.id.textAnswer3);
        answer4 =  (TextView) findViewById(R.id.textAnswer4);
        btnNext = (Button) findViewById(R.id.btnNext_answer);
    }

    public void onRadioButtonClicked(View view){
        //Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        //Check which radio button was clicked
        switch (view.getId()){
            case R.id.textAnswer1:
                if (checked)

                    break;
            case R.id.textAnswer2:
                if (checked)

                    break;
            case R.id.textAnswer3:
                if (checked)

                    break;
            case R.id.textAnswer4:
                if (checked)

                    break;
        }
    }

    public void onNextAnswerButtonClicked(){

    }

}
