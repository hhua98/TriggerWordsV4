package com.example.tarik.triggerwordsv1.Information;

/**
 * Created by huanghe on 11/04/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.example.tarik.triggerwordsv1.R;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tarik on 08-Apr-17.
 */

public class AQHandwriting extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<Float> score;

    //private TextView mTitle;
    private Button mQuit;
    private Button mNext;

    //Group1
    private TextView mQuestion1;
    private RadioGroup mRadioG1;
    private RadioButton mRadioB1A;
    private RadioButton mRadioB1S;
    private RadioButton mRadioB1N;

    //Group2
    private TextView mQuestion2;
    private RadioGroup mRadioG2;
    private RadioButton mRadioB2A;
    private RadioButton mRadioB2S;
    private RadioButton mRadioB2N;

    //Group3
    private TextView mQuestion3;
    private RadioGroup mRadioG3;
    private RadioButton mRadioB3A;
    private RadioButton mRadioB3S;
    private RadioButton mRadioB3N;

    //Group4
    private TextView mQuestion4;
    private RadioGroup mRadioG4;
    private RadioButton mRadioB4A;
    private RadioButton mRadioB4S;
    private RadioButton mRadioB4N;

    //Group5
    private TextView mQuestion5;
    private RadioGroup mRadioG5;
    private RadioButton mRadioB5A;
    private RadioButton mRadioB5S;
    private RadioButton mRadioB5N;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aqhandwriting);

        score = (ArrayList<Float>) getIntent().getSerializableExtra("score");
        Log.d("scoreTopHR", "scoreTopHR: " + score.toString());

        //mTitle = (TextView) findViewById(R.id.titleTextView);

        mQuit = (Button) findViewById(R.id.quitButton);
        mNext = (Button) findViewById(R.id.nextButton);

        //Group1
        mQuestion1 = (TextView) findViewById(R.id.questionTextView1);
        mRadioG1 = (RadioGroup) findViewById(R.id.radioGroup1);
        mRadioB1A  = (RadioButton) findViewById(R.id.radioButton1A);
        mRadioB1S  = (RadioButton) findViewById(R.id.radioButton1S);
        mRadioB1N  = (RadioButton) findViewById(R.id.radioButton1N);

        //Group2
        mQuestion2 = (TextView) findViewById(R.id.questionTextView2);
        mRadioG2 = (RadioGroup) findViewById(R.id.radioGroup2);
        mRadioB2A  = (RadioButton) findViewById(R.id.radioButton2A);
        mRadioB2S  = (RadioButton) findViewById(R.id.radioButton2S);
        mRadioB2N  = (RadioButton) findViewById(R.id.radioButton2N);

        //Group3
        mQuestion3 = (TextView) findViewById(R.id.questionTextView3);
        mRadioG3 = (RadioGroup) findViewById(R.id.radioGroup3);
        mRadioB3A  = (RadioButton) findViewById(R.id.radioButton3A);
        mRadioB3S  = (RadioButton) findViewById(R.id.radioButton3S);
        mRadioB3N  = (RadioButton) findViewById(R.id.radioButton3N);

        //Group4
        mQuestion4 = (TextView) findViewById(R.id.questionTextView4);
        mRadioG4 = (RadioGroup) findViewById(R.id.radioGroup4);
        mRadioB4A  = (RadioButton) findViewById(R.id.radioButton4A);
        mRadioB4S  = (RadioButton) findViewById(R.id.radioButton4S);
        mRadioB4N  = (RadioButton) findViewById(R.id.radioButton4N);

        //Group5
        mQuestion5 = (TextView) findViewById(R.id.questionTextView5);
        mRadioG5 = (RadioGroup) findViewById(R.id.radioGroup5);
        mRadioB5A  = (RadioButton) findViewById(R.id.radioButton5A);
        mRadioB5S  = (RadioButton) findViewById(R.id.radioButton5S);
        mRadioB5N  = (RadioButton) findViewById(R.id.radioButton5N);

        //mTitle.setText("Co-ordination and handwriting");
        mQuit.setOnClickListener(this);
        mNext.setOnClickListener(this);

        mQuestion1.setText("Trouble with writing or copying; pencil grip may be unusual.");
        mQuestion2.setText("Avoids writing tasks. Handwriting varies or is illegible.");
        mQuestion3.setText("May be somewhat or completely ambidextrous.");
        mQuestion4.setText("Letter reversals, such as d for b. Word reversals, such as tip for pit.");
        mQuestion5.setText("Frequently adds and/or forgets letters in a word.");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar8);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.nextButton:
                setUp();
                score.add(calculateScore());
                Intent intent1 = new Intent(this, AQPersonality.class);
                //score.add(1.5);
                intent1.putExtra("score", score);
                startActivity(intent1);
                break;

            case R.id.quitButton:
                Intent intent2 = new Intent(this, information.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                break;

        }
    }

    private float calculateScore() {
        float result1 = RadioFunction1();
        float result2 = RadioFunction2();
        float result3 = RadioFunction3();
        float result4 = RadioFunction4();
        float result5 = RadioFunction5();

        float avg = ((result1 + result2 + result3 + result4 + result5)*100f)/5.0f;
        return avg;
    }


    private void setUp() {

        if (this.getClass().getSimpleName().equals("AQHandwriting")) {
            int screenNumber = 5;
            for (int count = score.size(); count >= screenNumber; count--) {
                if (score.size() != 0)
                    score.remove(count - 1);
                else
                    break;
            }
        }
    }


    private float RadioFunction1() {
        float result = 0;
        int selectedId = mRadioG1.getCheckedRadioButtonId();
        switch (selectedId) {
            case R.id.radioButton1A:
                result = 1;
                break;

            case R.id.radioButton1S:
                result = 0.75f;
                break;

            case R.id.radioButton1N:
                result = 0;
                break;
        }
        return result;
    }

    private float RadioFunction2() {
        float result = 0;
        int selectedId = mRadioG2.getCheckedRadioButtonId();
        switch (selectedId) {
            case R.id.radioButton2A:
                result = 1;
                break;

            case R.id.radioButton2S:
                result = 0.75f;
                break;

            case R.id.radioButton2N:
                result = 0;
                break;
        }
        return result;
    }

    private float RadioFunction3() {
        float result = 0;
        int selectedId = mRadioG3.getCheckedRadioButtonId();
        switch (selectedId) {
            case R.id.radioButton3A:
                result = 1;
                break;

            case R.id.radioButton3S:
                result = 0.75f;
                break;

            case R.id.radioButton3N:
                result = 0;
                break;
        }
        return result;
    }

    private float RadioFunction4() {
        float result = 0;
        int selectedId = mRadioG4.getCheckedRadioButtonId();
        switch (selectedId) {
            case R.id.radioButton4A:
                result = 1;
                break;

            case R.id.radioButton4S:
                result = 0.75f;
                break;

            case R.id.radioButton4N:
                result = 0;
                break;
        }
        return result;
    }

    private float RadioFunction5() {
        float result = 0;
        int selectedId = mRadioG5.getCheckedRadioButtonId();
        switch (selectedId) {
            case R.id.radioButton5A:
                result = 1;
                break;

            case R.id.radioButton5S:
                result = 0.75f;
                break;

            case R.id.radioButton5N:
                result = 0;
                break;
        }
        return result;
    }
}
