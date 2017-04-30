package com.example.tarik.triggerwordsv1;

/**
 * Created by huanghe on 1/04/2017.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.example.tarik.triggerwordsv1.Eyetracking_ReadingStories.DemoActivity;
import com.example.tarik.triggerwordsv1.Eyetracking_ReadingStories.EyeTracker;
import com.example.tarik.triggerwordsv1.Newtriggerwords.AddWordUi;

public class interactMenu extends AppCompatActivity {
    ImageButton imageButton5;
    ImageButton imageButton6;
    ImageButton imageButton7;
    ImageButton imageButton8;
    ImageButton imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_2menu);
        imageButton6 = (ImageButton) findViewById(R.id.imageButton6);
        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(interactMenu.this, EyeTracker.class);
                startActivity(intentLoadNewActivity);

            }
        });
        imageButton5 = (ImageButton) findViewById(R.id.imageButton5);
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(interactMenu.this, AddWordUi.class);
                startActivity(intentLoadNewActivity);
            }
        });
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(interactMenu.this, DemoActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
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