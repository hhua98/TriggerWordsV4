package com.example.tarik.triggerwordsv1.Newtriggerwords;

/**
 * Created by Tarik on 30/04/2017.
 */

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import com.example.tarik.triggerwordsv1.R;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.example.tarik.triggerwordsv1.Newtriggerwords.AddWordUi.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tarik on 30-Apr-17.
 */


public class InteractiveSession extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_PERMISSION = 1;
    private static final int SELECTED_PICTURE = 2;
    private LayoutInflater inflater;
    private SwipeFlingAdapterView swipeView;
    private SqliteAdapter sqliteAdapter;
    private ArrayList<Word> wordList1;
    private ArrayList<Word> wordList2;
    private Word word1;
    private Word word2;
    private ImageView likeBtn;
    private ImageView dislikeBtn;
    private Button changePicBtn;
    private ImageView swipeWordImage;
    private ImageView rightArw;
    private ImageView leftArw;
    TextView mPoints;
    TextView mProgEasy;
    TextView mProgNormal;
    TextView mProgTough;
    TextView mProg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interactivesession2);

        init();
        initAdapter();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar15);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void initWidgets() {
        swipeView = (SwipeFlingAdapterView) findViewById(R.id.swipe_view);
        likeBtn = (ImageView) findViewById(R.id.likeButton);
        likeBtn.setOnClickListener(this);
        dislikeBtn = (ImageView) findViewById(R.id.dislikeButton);
        dislikeBtn.setOnClickListener(this);
        changePicBtn = (Button) findViewById(R.id.changePicButton);
        changePicBtn.setOnClickListener(this);
        swipeWordImage= (ImageView) findViewById(R.id.swipeWordImageView);
        swipeWordImage.setOnClickListener(this);
        /*rightArw = (ImageView) findViewById(R.id.rightArrow);
        rightArw.setOnClickListener(this);
        leftArw = (ImageView) findViewById(R.id.leftArrow);
        leftArw.setOnClickListener(this);*/
        mPoints = (TextView) findViewById(R.id.pointsTextView);
        mProgEasy = (TextView) findViewById(R.id.progressEasyView);
        mProgNormal = (TextView) findViewById(R.id.progressNormalView);
        mProgTough = (TextView) findViewById(R.id.progressToughView);
        mProg = (TextView) findViewById(R.id.progTextView);
    }

    public void init() {
        initWidgets();
        inflater = getLayoutInflater();
        wordList1 = new ArrayList<Word>();
        wordList2 = new ArrayList<Word>();
        sqliteAdapter = new SqliteAdapter(this);
        if (sqliteAdapter.getRowCount() != 0) {
            wordList1.addAll(sqliteAdapter.getAllWords());
            Log.d("LIST", ": " + wordList1.toString());
        }

        if (wordList1.size() != 0) {
            word2 = wordList1.get(0);
            imageSetter();
            ratingBarSetter();
        }
    }

    public void initAdapter() {
        final SwipeWordAdapter swipeWordAdapter = new SwipeWordAdapter(wordList1, inflater, this);
        swipeView.setAdapter(swipeWordAdapter);
        swipeView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {

            @Override
            public void removeFirstObjectInAdapter() {
                word1 = wordList1.get(0);
                word2 = wordList1.get(1);
                imageSetter();
                ratingBarSetter();
                wordList2.add(word1);
                wordList1.remove(0);
                swipeWordAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object o) {
            }

            @Override
            public void onRightCardExit(Object o) {
            }

            @Override
            public void onAdapterAboutToEmpty(int i) {
                wordList1.addAll(wordList2);
                wordList2.clear();
                swipeWordAdapter.notifyDataSetChanged();
            }

            @Override
            public void onScroll(float v) {

            }
        });
    }

    public void imageSetter() {
        Word word = sqliteAdapter.searchWord(word2.getWordName());
        String imageUrl = word.getImageUrl();
        if (!imageUrl.equals("a")) {
            File imgFile = new File(imageUrl);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                swipeWordImage.setImageBitmap(myBitmap);
            }
        }
        else {
            swipeWordImage.setImageResource(R.drawable.ic_action_default);
        }
    }

    public void askSDAccessPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                changePicture();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                changePicture();
            } else {
                Toast.makeText(this, "Permission to Read/Write from external storage denied", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void changePicture() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECTED_PICTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String filePath = null;
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECTED_PICTURE:
                if (resultCode == RESULT_OK) {
                    Uri imageUri = data.getData();
                    String [] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(imageUri, projection, null, null, null);
                    if (cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndex(projection[0]);
                        filePath = cursor.getString(columnIndex);
                        cursor.close();
                    }
                }
                break;
        }
        replacePictureUrl(filePath);
    }

    public void replacePictureUrl(String newImageUrl) {
        if (newImageUrl != null) {
            long exitId = sqliteAdapter.updateWordImage(word2, newImageUrl);
            if (exitId < 0) {
                alertDialog("Error: Could not increase that word's points");
            } else {
                imageSetter();
            }
        }
    }

    public void ratingBarSetter() {
        Word word = sqliteAdapter.searchWord(word2.getWordName());
        int points = word.getPoints();
        mPoints.setText(Integer.toString(points) + ")");
        if (points <= 10) {
            if (points >= 7) {
                mProgEasy.setBackgroundColor(Color.parseColor("#76ff03"));
                mProgNormal.setBackgroundColor(Color.parseColor("#ffffff"));
                mProgTough.setBackgroundColor(Color.parseColor("#ffffff"));
                mProg.setText("Easy!");
            }
            else if (points >= 5) {
                mProgEasy.setBackgroundColor(Color.parseColor("#76ff03"));
                mProgNormal.setBackgroundColor(Color.parseColor("#ffd600"));
                mProgTough.setBackgroundColor(Color.parseColor("#ffffff"));
                mProg.setText("Normal!");
            }
            else if (points >= 0) {
                mProgEasy.setBackgroundColor(Color.parseColor("#76ff03"));
                mProgNormal.setBackgroundColor(Color.parseColor("#ffd600"));
                mProgTough.setBackgroundColor(Color.parseColor("#f4511e"));
                mProg.setText("Tough!");
            }
        }
    }

    public void increaseWordPoints() {
        //Toast.makeText(this, "Like clicked for " + currentWordName, Toast.LENGTH_SHORT).show();
        Word word = sqliteAdapter.searchWord(word2.getWordName());
        int oldPoints = word.getPoints();
        if (oldPoints < 10) {
            //Toast.makeText(this, "points for " + currentWordName + " = " + oldPoints, Toast.LENGTH_SHORT).show();
            long exitId = sqliteAdapter.updateWordPoints(word, (oldPoints + 1));
            ratingBarSetter();
            if (exitId < 0)
                alertDialog("Error: Could not increase that word's points");
        }
        else {
            alertDialog("Cannot increase points to more than 10");
        }
    }

    public void decreaseWordPoints() {
        //sToast.makeText(this, "Like clicked for " + currentWordName, Toast.LENGTH_SHORT).show();
        Word word = sqliteAdapter.searchWord(word2.getWordName());
        int oldPoints = word.getPoints();
        if (oldPoints > 0) {
            //Toast.makeText(this, "points for " + currentWordName + " = " + oldPoints, Toast.LENGTH_SHORT).show();
            long exitId = sqliteAdapter.updateWordPoints(word, (oldPoints - 1));
            ratingBarSetter();
            if (exitId < 0)
                alertDialog("Error: Could not decrease that word's points");
        }
        else {
            alertDialog("Cannot decrease points to less than 0");
        }
    }

    public void zoomPic(String imageUrl) {
        Intent intent = new Intent(this, ZoomPic.class);
        intent.putExtra("ImageUrl", imageUrl);
        startActivity(intent);
    }

    public void alertDialog(String errorMessage) {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setMessage(errorMessage)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        myAlert.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.likeButton:
                increaseWordPoints();
                break;

            case R.id.dislikeButton:
                decreaseWordPoints();
                break;

            /*case R.id.rightArrow:
                swipeView.getTopCardListener().selectRight();
                break;

            case R.id.leftArrow:
                swipeView.getTopCardListener().selectLeft();
                break;*/

            case R.id.changePicButton:
                askSDAccessPermission();
                break;

            case R.id.swipeWordImageView:
                //Toast.makeText(this, "CLICKED!S", Toast.LENGTH_SHORT).show();
                zoomPic(word2.getImageUrl());
                break;
        }
    }
}
