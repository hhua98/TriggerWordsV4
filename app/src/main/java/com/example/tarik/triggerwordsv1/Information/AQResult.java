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

public class AQResult extends AppCompatActivity {

    private ArrayList<Float> score;
    private BarChart barChart;
    private TextView mDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aqresult);

        mDesc = (TextView) findViewById(R.id.descriptionTextView);
        mDesc.setText("The following chart shows the key areas of learning that can be addressed " +
                "by the Davis Dyslexia Correction® Program. If two or more areas are in the" +
                "Moderate to Severe range, chances are it is dyslexia, and can be corrected. ");


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

        findViewById(R.id.quitToMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(AQResult.this, information.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar12);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
