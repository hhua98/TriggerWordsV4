package com.example.tarik.triggerwordsv1.Newtriggerwords;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarik.triggerwordsv1.R;

/**
 * Created by huanghe on 15/05/2017.
 */

public class StartTriggerWords extends AppCompatActivity implements View.OnClickListener{

    private Button blackboardBtn;
    private Button triggerListBtn;
    private SqliteAdapter sqliteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list_start);
        init();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar50);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void delayer() {
        try {
            Thread.sleep(800);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private void init() {
        sqliteAdapter = new SqliteAdapter(this);
        delayer();
        blackboardBtn = (Button) findViewById(R.id.startBlackBoardButton);
        blackboardBtn.setOnClickListener(this);
        triggerListBtn = (Button) findViewById(R.id.wordListButton);
        triggerListBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.startBlackBoardButton:
                if (sqliteAdapter.getRowCount() >= 2) {
                    startActivity(new Intent(StartTriggerWords.this, InteractiveSession.class));
                    break;
                }
                else {
                    Toast.makeText(this, "Please go to the word list and add at least 2 words there first!", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.wordListButton:
                startActivity(new Intent(StartTriggerWords.this, AddWordUi.class));
                break;
        }
    }
}
