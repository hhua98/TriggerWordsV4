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
import com.example.tarik.triggerwordsv1.triggerletters.CoverFlowActivity;
import com.example.tarik.triggerwordsv1.wordgame.gameActivity;
import com.example.tarik.triggerwordsv1.wordgame.startScreen;
import com.example.tarik.triggerwordsv1.wordgame.gamebuild;

public class interactMenu extends AppCompatActivity {
    ImageButton imageButton6;
    ImageButton imageButton7;
    ImageButton imageButton8;
    ImageButton game;
    ImageButton imageButton9;
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
        imageButton7 = (ImageButton) findViewById(R.id.imageButton7);
        imageButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(interactMenu.this, AddWordUi.class);
                startActivity(intentLoadNewActivity);
            }
        });
        imageButton8 = (ImageButton) findViewById(R.id.imageButton8);
        imageButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(interactMenu.this, DemoActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });
        game = (ImageButton) findViewById(R.id.game);
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(interactMenu.this, startScreen.class);
                startActivity(intentLoadNewActivity);

            }
        });
        imageButton9 = (ImageButton) findViewById(R.id.imageButton9);
        imageButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(interactMenu.this, CoverFlowActivity.class);
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
    @Override
    public void onBackPressed(){
        Intent setIntent = new Intent(this, MenuActivity.class);
        startActivity(setIntent);

    }
}