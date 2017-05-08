package com.example.tarik.triggerwordsv1.Information;

/**
 * Created by huanghe on 11/04/2017.
 */

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.tarik.triggerwordsv1.R;
import java.util.ArrayList;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Tarik on 08-Apr-17.
 */

public class AQResult extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<Float> score;

    private BarChart barChart;
    private TextView mDesc;

    private Button mQuit;
    private Button mBack;

    private CardView disorientationTitle;
    private CardView readspellTitle;
    private CardView attentionTitle;
    private CardView mathTitle;
    private CardView handwritingTitle;
    private CardView personalityTitle;

    private TextView disorientationExp;
    private TextView readspellExp;
    private TextView attentionExp;
    private TextView mathExp;
    private TextView handwritingExp;
    private TextView personalityExp;

    private ImageView downArrowImageDis;
    private ImageView upArrowImageDis;
    private ImageView downArrowImageRS;
    private ImageView upArrowImageRS;
    private ImageView downArrowImageAF;
    private ImageView upArrowImageAF;
    private ImageView downArrowImageM;
    private ImageView upArrowImageM;
    private ImageView downArrowImageHW;
    private ImageView upArrowImageHW;
    private ImageView downArrowImageP;
    private ImageView upArrowImageP;

    private TextView disorientationSeverity;
    private TextView readspellSeverity;
    private TextView attentionSeverity;
    private TextView mathSeverity;
    private TextView handwritingSeverity;
    private TextView personalitySeverity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aqresult);

        init();
        initExpViews();
        initArrowImages();
        initTitleHeads();
        initSeverityRatingViews();



        score = (ArrayList<Float>) getIntent().getSerializableExtra("score");

        Float [] scoreArray = score.toArray(new Float[score.size()]);
        Log.d("scoreInRESULT", "scoreInRESULT: " + scoreArray.toString());

        barChart = (BarChart) findViewById(R.id.barChartView);
        YAxis leftAxis = barChart.getAxisLeft();
        YAxis rightAxis = barChart.getAxisRight();
        XAxis xAxis = barChart.getXAxis();
        leftAxis.setAxisMaxValue(100.0f);
        leftAxis.setAxisMinValue(0.0f);
        leftAxis.setLabelCount(5, false);
        leftAxis.setDrawGridLines(false);
        xAxis.setDrawGridLines(false);
        rightAxis.setEnabled(false);



        ArrayList<BarEntry> yEntries = new ArrayList<BarEntry>();
        for (int count = 0; count < 6; count++) {
            yEntries.add(new BarEntry(scoreArray[count], count));
        }

        BarDataSet scoreDataSet = new BarDataSet(yEntries, "scores");

        scoreDataSet.setColors(new int[] {  getResources().getColor(R.color.dis),
                getResources().getColor(R.color.rs),
                getResources().getColor(R.color.af),
                getResources().getColor(R.color.mat),
                getResources().getColor(R.color.hw),
                getResources().getColor(R.color.ps)});


        ArrayList<String> classes =  new ArrayList<String>();
        classes.add("DIS");
        classes.add("R&S");
        classes.add("AF");
        classes.add("MAT");
        classes.add("CHW");
        classes.add("P&S");


        BarData data = new BarData(classes, scoreDataSet);


        barChart.setData(data);
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.setDoubleTapToZoomEnabled(true);
        barChart.setDrawValueAboveBar(false);
        barChart.setDescription("");
        barChart.setAutoScaleMinMaxEnabled(false);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar12);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        severitySetter(score);

    }

    private void severitySetter(ArrayList<Float> score) {

        Log.d("score:", "score in severity: " + score.toString());
        severityIndicator(disorientationSeverity, score.get(0));
        severityIndicator(readspellSeverity, score.get(1));
        severityIndicator(attentionSeverity, score.get(2));
        severityIndicator(mathSeverity, score.get(3));
        severityIndicator(handwritingSeverity, score.get(4));
        severityIndicator(personalitySeverity, score.get(5));
    }

    private void severityIndicator(TextView severityRating, float severity) {
        if (severity >= 70.0f) {
            severityRating.setText("High");
            severityRating.setTextColor(Color.RED);
        }
        else if (severity >= 55.0f) {
            severityRating.setText("Medium");
            severityRating.setTextColor(Color.YELLOW);
        }
        else {
            severityRating.setText("Normal");
            severityRating.setTextColor(Color.GREEN);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.backButton:
                onBackPressed();
                break;

            case R.id.quitButton:
                Intent intent2 = new Intent(AQResult.this, information.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);

            case R.id.disorientationTitleView:
                toggle_contents(disorientationExp, downArrowImageDis, upArrowImageDis);
                break;

            case R.id.readSpellTitleView:
                toggle_contents(readspellExp, downArrowImageRS, upArrowImageRS);
                break;

            case R.id.attentionFocusTitleView:
                toggle_contents(attentionExp, downArrowImageAF, upArrowImageAF);
                break;

            case R.id.mathTitleView:
                toggle_contents(mathExp, downArrowImageM, upArrowImageM);
                break;

            case R.id.handwritingTitleView:
                toggle_contents(handwritingExp, downArrowImageHW, upArrowImageHW);
                break;

            case R.id.personalityTitleView:
                toggle_contents(personalityExp, downArrowImageP, upArrowImageP);
                break;
        }
    }

    public void toggle_contents(View view, ImageView downImageView, ImageView upImageView) {
        if(!view.isShown()){
            AnimUpDown.slide_down(this, view);
            view.setVisibility(View.VISIBLE);
            downImageView.setVisibility(View.INVISIBLE);
            upImageView.setVisibility(View.VISIBLE);
        }
        else {
            view.setVisibility(View.GONE);
            downImageView.setVisibility(View.VISIBLE);
            upImageView.setVisibility(View.INVISIBLE);
        }
    }

    public void initExpViews() {
        disorientationExp = (TextView) findViewById(R.id.disorientationExpView);
        disorientationExp.setVisibility(View.GONE);
        readspellExp = (TextView) findViewById(R.id.readSpellExpView);
        readspellExp.setVisibility(View.GONE);
        attentionExp = (TextView) findViewById(R.id.attentionFocusExpView);
        attentionExp.setVisibility(View.GONE);
        mathExp = (TextView) findViewById(R.id.mathExpView);
        mathExp.setVisibility(View.GONE);
        handwritingExp = (TextView) findViewById(R.id.handwritingExpView);
        handwritingExp.setVisibility(View.GONE);
        personalityExp = (TextView) findViewById(R.id.personalityExpView);
        personalityExp.setVisibility(View.GONE);
    }

    // initialization methods

    public void init() {
        mDesc = (TextView) findViewById(R.id.descriptionTextView);
        mDesc.setText("If two or more of the key areas of learning above are in the" +
                "High range of severity, chances are it is dyslexia.");


        mQuit = (Button) findViewById(R.id.quitButton);
        mQuit.setOnClickListener(this);
        mBack = (Button) findViewById(R.id.backButton);
        mBack.setOnClickListener(this);
    }

    public void initArrowImages() {
        downArrowImageDis = (ImageView) findViewById(R.id.downArrowImageViewDis);
        upArrowImageDis = (ImageView) findViewById(R.id.upArrowImageViewDis);
        upArrowImageDis.setVisibility(View.INVISIBLE);

        downArrowImageRS = (ImageView) findViewById(R.id.downArrowImageViewRS);
        upArrowImageRS = (ImageView) findViewById(R.id.upArrowImageViewRS);
        upArrowImageRS.setVisibility(View.INVISIBLE);

        downArrowImageAF = (ImageView) findViewById(R.id.downArrowImageViewAF);
        upArrowImageAF = (ImageView) findViewById(R.id.upArrowImageViewAF);
        upArrowImageAF.setVisibility(View.INVISIBLE);

        downArrowImageM = (ImageView) findViewById(R.id.downArrowImageViewM);
        upArrowImageM = (ImageView) findViewById(R.id.upArrowImageViewM);
        upArrowImageM.setVisibility(View.INVISIBLE);

        downArrowImageHW = (ImageView) findViewById(R.id.downArrowImageViewHW);
        upArrowImageHW = (ImageView) findViewById(R.id.upArrowImageViewHW);
        upArrowImageHW.setVisibility(View.INVISIBLE);

        downArrowImageP = (ImageView) findViewById(R.id.downArrowImageViewP);
        upArrowImageP = (ImageView) findViewById(R.id.upArrowImageViewP);
        upArrowImageP.setVisibility(View.INVISIBLE);
    }

    public void initTitleHeads() {

        disorientationTitle = (CardView) findViewById(R.id.disorientationTitleView);
        disorientationTitle.setOnClickListener(this);
        readspellTitle = (CardView) findViewById(R.id.readSpellTitleView);
        readspellTitle.setOnClickListener(this);
        attentionTitle = (CardView) findViewById(R.id.attentionFocusTitleView);
        attentionTitle.setOnClickListener(this);
        mathTitle = (CardView) findViewById(R.id.mathTitleView);
        mathTitle.setOnClickListener(this);
        handwritingTitle = (CardView) findViewById(R.id.handwritingTitleView);
        handwritingTitle.setOnClickListener(this);
        personalityTitle = (CardView) findViewById(R.id.personalityTitleView);
        personalityTitle.setOnClickListener(this);

    }

    public void initSeverityRatingViews() {
        disorientationSeverity = (TextView) findViewById(R.id.severityDisView);
        readspellSeverity = (TextView) findViewById(R.id.severityRSView);
        attentionSeverity = (TextView) findViewById(R.id.severityAFView);
        mathSeverity = (TextView) findViewById(R.id.severityMView);
        handwritingSeverity = (TextView) findViewById(R.id.severityHWView);
        personalitySeverity = (TextView) findViewById(R.id.severityPView);

    }
}
