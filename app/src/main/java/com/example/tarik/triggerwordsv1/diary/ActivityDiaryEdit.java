package com.example.tarik.triggerwordsv1.diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tarik.triggerwordsv1.R;


/**
 * Created by huanghe on 1/04/2017.
 */

public class ActivityDiaryEdit extends AppCompatActivity {

    private EditText mTitleText;
    private EditText mBodyText;
    private Long mRowId;
    private DiaryDbAdapter mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = new DiaryDbAdapter(this);
        mDbHelper.open();
        setContentView(R.layout.diary_edit);

        mTitleText = (EditText) findViewById(R.id.title);
        mBodyText = (EditText) findViewById(R.id.body);

        Button confirmButton = (Button) findViewById(R.id.confirm);

        mRowId = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString(DiaryDbAdapter.KEY_TITLE);
            String body = extras.getString(DiaryDbAdapter.KEY_BODY);
            mRowId = extras.getLong(DiaryDbAdapter.KEY_ROWID);

            if (title != null) {
                mTitleText.setText(title);
            }
            if (body != null) {
                mBodyText.setText(body);
            }
        }

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {



                    String title = mTitleText.getText().toString();
                    String body = mBodyText.getText().toString();
                    if (mRowId != null) {
                        mDbHelper.updateDiary(mRowId, title, body);
                    } else
                        mDbHelper.createDiary(title, body);
                    Intent mIntent = new Intent();
                    setResult(RESULT_OK, mIntent);
                    finish();

            }



        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar5);
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