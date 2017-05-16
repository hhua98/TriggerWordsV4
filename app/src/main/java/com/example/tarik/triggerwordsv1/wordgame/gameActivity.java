package com.example.tarik.triggerwordsv1.wordgame;

/**
 * Created by huanghe on 3/05/2017.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


import com.example.tarik.triggerwordsv1.R;

public class gameActivity extends AppCompatActivity {
    private gamebuild gameBoard;
    private TableLayout tableLayout;
    private Set<String> userWordSet = new HashSet<String>();
    private String currentWord = "";
    private ArrayList<String> coordsPassed = new ArrayList<>();
    private TextView user_current;
    private TextView computer;
    private TextView userScore;
    private TextView wordsBtn;
    private String LOG_TAG = "log";

    private int NUM_ROWS = 8;
    private  int NUM_COLS = 8;
    private int fontSize = 18;
    private int counter=0;
    private int computerScore = -1;

    // Message Handler http://stackoverflow.com/questions/3391272/
    private Handler handler_ = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case 1022:
                    setComputerScore();
                    break;
                case 1242:
                    fillBoard();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        startScreen.progressDialog.dismiss();
        user_current = (TextView) findViewById(R.id.current_word);
        computer = (TextView) findViewById(R.id.max);
        userScore = (TextView) findViewById(R.id.current);
        computer.setVisibility(View.GONE);

        // underline text http://stackoverflow.com/questions/2394935/
        wordsBtn = (TextView) findViewById(R.id.txtShowWords);
        wordsBtn.setPaintFlags(wordsBtn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Intent mIntent = getIntent();
        int Level = mIntent.getIntExtra("Level", 1);
        Log.e("Level", Level + "");
        switch (Level % 4) {
            case 1:
                NUM_COLS = 4;
                NUM_ROWS = 4;
                break;
            case 2:
                NUM_COLS = 5;
                NUM_ROWS = 5;
                break;
            case 3:
                NUM_COLS = 6;
                NUM_ROWS = 6;
                break;
            default:
                NUM_ROWS = 6;
                NUM_COLS = 6;
        }
        // make loader visible
        findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                initBoard();
                gameBoard.makeBoard(NUM_ROWS,NUM_COLS);
                handler_.sendMessage(Message.obtain(handler_, 1242, null));
            }
        }).start();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar16);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.instruction) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    gameActivity.this);

            // set title
            alertDialogBuilder.setTitle("Instructions");

            // set dialog message
            alertDialogBuilder.setMessage(R.string.instructions);
            alertDialogBuilder.setNegativeButton(R.string.leave, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();


            // show it
            alertDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void fillBoard(){
        findViewById(R.id.marker_progress).setVisibility(View.GONE); // hide loader
        tableLayout = (TableLayout) findViewById(R.id.grid);
        float tableHeight = tableLayout.getLayoutParams().height;
        float tableWidth = tableLayout.getLayoutParams().width;
        float tableHeightDP = convertPixelsToDp(tableHeight, getApplicationContext());
        float tableWidthDP = convertPixelsToDp(tableWidth, getApplicationContext());
        float tileHeight = tableHeightDP/(NUM_ROWS + 0.3f);
        float tileWidth = tableWidthDP/ (NUM_COLS + 0.3f);
        Log.e("height:", tableHeightDP + "");
        Log.e("Wi", tableWidthDP + "");
        Log.e("tileHeight", tileHeight + "");
        fontSize = 20 - (NUM_COLS-4)*2;

        for (int i = 0; i < NUM_ROWS; i++) {
            // Make TR
            final TableRow tr = new TableRow(this);
            tr.setId(100 + i);
            tr.setLayoutParams(new TableRow.LayoutParams(GridLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.FILL_PARENT));
            for (int j = 0; j < NUM_COLS; j++) {
                // Make TV to hold the details
                final TextView charTile = new TextView(this);
                charTile.setHeight((int) dpToPixel(tileHeight));
                charTile.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
                charTile.setWidth((int) dpToPixel(tileWidth));
                //charTile.setPadding(8,8,8,8);
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
                    charTile.setElevation(10);
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
                    charTile.setBackground(getDrawable(R.drawable.my_border));
                else
                    charTile.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_border));
                //charTile.setBackgroundColor(Color.GREEN);
                charTile.setId(NUM_ROWS * i + j);
                charTile.setTypeface(null, Typeface.BOLD);
                charTile.setText(gameBoard.chars[i][j]+"");
                charTile.setTextColor(Color.parseColor("#FFFFFF"));
                charTile.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

                // text view listeners
                charTile.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            handleTouch((TextView) v);
                            return true;
                        }
                        return false;
                    }
                });

                charTile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // this is needed for onTouch to work
                    }
                });

                tr.addView(charTile);
            }
            // table row ends
            tableLayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT));
        }
        // loop ends
        //findViewById(R.id.max_score_progress).setVisibility(View.VISIBLE);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // solves the board, so slow
                //computerScore = gameBoard.getComputerScore();
                computerScore = gameBoard.getPossibleWordsCount();
                handler_.sendMessage(Message.obtain(handler_, 1022, null));
            }
        });
        thread.start();
    }

    public int fetchId(int row, int col) {
        return row*NUM_ROWS + col;
    }

    private void setComputerScore(){
        //findViewById(R.id.max_score_progress).setVisibility(View.GONE); // hide loading
        computer.setVisibility(View.VISIBLE);
        computer.setText(computerScore+"");
    }

    private void handleTouch(TextView tv) {
        int id = tv.getId();
        int x = id / NUM_ROWS;
        int y = id % NUM_ROWS;
        String str = x + " " + y;
        if (coordsPassed.contains(str)){

            if (coordsPassed.size() > 0){
                //clickBack(tv);
                int [] ids = coordsFromStr( coordsPassed.get(coordsPassed.indexOf(str)) );
                StringBuilder sb = new StringBuilder(currentWord);
                sb.deleteCharAt(coordsPassed.indexOf(str));
                currentWord = sb.toString();
                coordsPassed.remove(coordsPassed.indexOf(str));
                tv.findViewById(fetchId(ids[0], ids[1]));
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
                    tv.setBackground(getDrawable(R.drawable.my_border));
                else
                    tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_border));
                //showCurrentWord(checkWord());
                showCurrentWord(0);
                int wordStatus = checkWord();
                if (wordStatus == 2){
                    (Toast.makeText(this, "Congrats! " + currentWord + " is a valid word", Toast.LENGTH_SHORT)).show();
                    processCorrectWord();
                } else {
                    showCurrentWord(wordStatus);
                }





            }

            // if already touched check and if the position of this tile is the latest in the coordsPassed arraylist
            // if yes the clickBack else just return
            //clickBack(tv);
            /*if(coordsPassed.indexOf(str) == coordsPassed.size()-1) {
                clickBack(tv);
            }*/
        } else {

            coordsPassed.add(str);
            currentWord += tv.getText();
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
                tv.setBackground(getDrawable(R.drawable.new_border));
            else
                tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.new_border));
            showCurrentWord(0);
            int wordStatus = checkWord();
            if (wordStatus == 2){
                (Toast.makeText(this, "Congrats! " + currentWord + " is a valid word", Toast.LENGTH_SHORT)).show();
                processCorrectWord();
            } else {
                showCurrentWord(wordStatus);
            }
            // get last tile
            /*boolean condition;
            if (coordsPassed.size() > 0) {
                int[] ids = coordsFromStr(coordsPassed.get(coordsPassed.size() - 1));
                condition = (Math.abs(ids[0] - x) <= 1) && (Math.abs(ids[1] - y) <= 1);
            } else {
                condition = true;
            }
            if (condition){
                coordsPassed.add(str);
                currentWord += tv.getText();
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
                    tv.setBackground(getDrawable(R.drawable.new_border));
                else
                    tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.new_border));
                showCurrentWord(0);
                int wordStatus = checkWord();
                if (wordStatus == 2){
                    (Toast.makeText(this, "Congrats! " + currentWord + " is a valid word", Toast.LENGTH_SHORT)).show();
                    processCorrectWord();
                } else {
                    showCurrentWord(wordStatus);
                }
            }*/
        }


        counter = userWordSet.size();
        String testString = Integer.toString(counter);
        userScore.setText(testString);
        String testString2 = Integer.toString(gameBoard.getPossibleWordsCount());
        if(testString==testString2){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    gameActivity.this);

            // set title
            alertDialogBuilder.setTitle("You Win!");
            alertDialogBuilder.setMessage("Do you want to continue or leave?");
            alertDialogBuilder.setPositiveButton(R.string.leave2, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intentLoadNewActivity = new Intent(gameActivity.this, startScreen.class);
                    startActivity(intentLoadNewActivity);

                }
            });
            alertDialogBuilder.setNegativeButton(R.string.confir, new DialogInterface.OnClickListener() {


                public void onClick(DialogInterface dialog, int id) {


                    setContentView(R.layout.game_activity);
                    startScreen.progressDialog.dismiss();
                    user_current = (TextView) findViewById(R.id.current_word);
                    computer = (TextView) findViewById(R.id.max);
                    userScore = (TextView) findViewById(R.id.current);
                    computer.setVisibility(View.GONE);
                    wordsBtn = (TextView) findViewById(R.id.txtShowWords);
                    wordsBtn.setPaintFlags(wordsBtn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    userWordSet.clear();
                    // make loader visible
                    findViewById(R.id.marker_progress).setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            initBoard();
                            gameBoard.makeBoard(NUM_ROWS,NUM_ROWS);
                            handler_.sendMessage(Message.obtain(handler_, 1242, null));
                        }
                    }).start();
                    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar16);
                    setSupportActionBar(toolbar);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);

                }

                // User cancelled the dialog
            });
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();


        }

    }
    @Override
    public void onBackPressed() {


        Intent setIntent = new Intent(this, startScreen.class);
        startActivity(setIntent);

    }
    private void showCurrentWord(int status){
        int color;
        if (status == 1)
            color = R.color.colorCurrentWordFaded;
        else if (status == 2)
            color = R.color.colorCurrentWordCorrect;
        else
            color = R.color.colorCurrentWordDefault;
        user_current.setTextColor(getResources().getColor(color));
        user_current.setText(currentWord);
    }

    private int [] coordsFromStr(String s){
        int [] arr = new int[2];
        arr[0] = Integer.parseInt(s.split("\\s+")[0]);
        arr[1] = Integer.parseInt(s.split("\\s+")[1]);
        return arr;
    }

    /* public void clickBack(View view) {


         if (coordsPassed.size() > 0){

             int [] ids = coordsFromStr( coordsPassed.get(coordsPassed.size()-1) );
             coordsPassed.remove(coordsPassed.size()-1);



             currentWord = currentWord.substring(0, currentWord.length()-1);
             TextView tv = (TextView) findViewById(fetchId(ids[0], ids[1]));
             if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
                 tv.setBackground(getDrawable(R.drawable.my_border));
             else
                 tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_border));
             showCurrentWord(checkWord());
         }
     }
 */
    public float dpToPixel(float dps){
        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        float pixels = (int) (dps * scale + 0.5f);
        return pixels;
    }
    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    private void resetAllTiles(){
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                TextView tv = (TextView) findViewById(fetchId(i, j));
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
                    tv.setBackground(getDrawable(R.drawable.my_border));
                else
                    tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_border));
            }
        }
    }

    private int checkWord(){
        if ("".equals(currentWord))
            return 0;
        if (!userWordSet.contains(currentWord) && gameBoard.isWordOnBoard(currentWord)) {
            return 2;
        } else if (userWordSet.contains(currentWord)){
            return 1;
        } else {
            return 0;
        }
    }

    public void processCorrectWord() {
        // make ui changes
        //counter += currentWord.length();
        userWordSet.add(currentWord);
        counter = userWordSet.size();
        String testString = Integer.toString(counter);
        userScore.setText(testString);
        // reset all tiles
        resetAllTiles();
        // reset vars
        showCurrentWord(2);  // display on UI
        currentWord = "";
        coordsPassed.clear();
    }

    private void initBoard(){
        AssetManager assetManager = getAssets();
        try {
            // Thanks to
            // https://github.com/ManiacDC/TypingAid/tree/master/Wordlists
            InputStream inputStream = assetManager.open("triggerwords.txt");
            gameBoard = new gamebuild(inputStream);
        } catch (IOException e){
            (Toast.makeText(this, "There was a problem loading dictionary", Toast.LENGTH_LONG)).show();
        }
    }

    public void buttonShow(View view) {
        if (computerScore == -1) // return if not ready
            return;
        //wordsBtn.setText("Loading");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                gameActivity.this);

        // set title
        alertDialogBuilder.setTitle("List of trigger words (" +
                gameBoard.getPossibleWordsCount() + ")\n\n");
        alertDialogBuilder.setNegativeButton(R.string.leave, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        String temp= "";
//        String clr = "#" + getString(0+R.color.colorCurrentWordCorrect).substring(3);
        for(String s : gameBoard.getComputerList(true)){
            if (userWordSet.contains(s))
                temp += "<font color=" + "#FF80AB" + ">" + s + "</font><br>";  // @colorAccentLight
            else
                temp += s + "<br>";
        }

        // set dialog message
        alertDialogBuilder.setMessage(Html.fromHtml(temp));
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                wordsBtn.setText(getString(R.string.text_Words));
            }
        });

    }
}
