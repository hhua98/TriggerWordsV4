package com.example.tarik.triggerwordsv1.Newtriggerwords;

/**
 * Created by Tarik on 30/04/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import com.example.tarik.triggerwordsv1.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Tarik on 22-Apr-17.
 */

public class SqliteAdapter {

    private Context context;
    private SqliteHelper sqliteHelper;

    public SqliteAdapter (Context context) {

        this.context = context;
        sqliteHelper = new SqliteHelper(this.context);
    }


    public long insertData(Word newWord) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(sqliteHelper.WORD_NAME, newWord.getWordName());
        contentValues.put(sqliteHelper.WORD_IMAGE_URL, newWord.getImageUrl());
        contentValues.put(sqliteHelper.WORD_POINTS, newWord.getPoints());
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();
        long exitId = db.insert(sqliteHelper.TABLE_NAME, null, contentValues);
        db.close();
        return exitId;
    }

    public int getRowCount() {
        SQLiteDatabase db = sqliteHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Count(*) FROM " + sqliteHelper.TABLE_NAME + ";", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }

    public ArrayList<Word> getAllWords() {
        ArrayList<Word> wordList = new ArrayList<Word>();
        String[] attributesWanted = {sqliteHelper.WORD_NAME,
                sqliteHelper.WORD_IMAGE_URL,
                sqliteHelper.WORD_POINTS};

        SQLiteDatabase db = sqliteHelper.getWritableDatabase();

        Cursor cursor = db.query(sqliteHelper.TABLE_NAME, attributesWanted,
                null, null, null, null, sqliteHelper.WORD_NAME, null);

        wordList.addAll(allWordsFetcher(cursor));
        cursor.close();
        db.close();
        return wordList;
    }

    public boolean compareName(String inputWordName) {
        inputWordName = inputWordName.toLowerCase();
        boolean nameFoundFlag = false;
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();
        Cursor cursor = db.query(sqliteHelper.TABLE_NAME, new String [] {sqliteHelper.WORD_NAME},
                sqliteHelper.WORD_NAME + "=?", new String [] {inputWordName}, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String nameToCompare = cursor.getString(0);
                if (nameToCompare.equalsIgnoreCase(inputWordName)) {
                    nameFoundFlag = true;
                    break;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return nameFoundFlag;
    }

    public Word searchWord(String inputWordName) {
        inputWordName = inputWordName.trim();
        Word word = null;
        if (inputWordName.length() != 0) {
            inputWordName = inputWordName.toLowerCase();
            String[] attributesWanted = {sqliteHelper.WORD_NAME,
                    sqliteHelper.WORD_IMAGE_URL,
                    sqliteHelper.WORD_POINTS};

            SQLiteDatabase db = sqliteHelper.getWritableDatabase();

            Cursor cursor = db.query(sqliteHelper.TABLE_NAME, attributesWanted,
                    sqliteHelper.WORD_NAME + "=?", new String[]{inputWordName},
                    null, null, null, null);

            word = wordFetcher(cursor, inputWordName);
            cursor.close();
            db.close();
        }
        return word;
    }

    public long editWordFromDB(Word oldWord, Word newWord) {
        String oldWordName = oldWord.getWordName();
        ContentValues contentValues = new ContentValues();
        contentValues.put(sqliteHelper.WORD_NAME, newWord.getWordName());
        contentValues.put(sqliteHelper.WORD_IMAGE_URL, newWord.getImageUrl());
        contentValues.put(sqliteHelper.WORD_POINTS, newWord.getPoints());
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();
        long exitId = db.update(sqliteHelper.TABLE_NAME, contentValues, sqliteHelper.WORD_NAME + "=?", new String [] {oldWordName});
        return exitId;
    }

    public long deleteWordFromDB(String inputWordName) {
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();

        long exitId = db.delete(sqliteHelper.TABLE_NAME,
                sqliteHelper.WORD_NAME + " =? ", new String [] {inputWordName});
        db.close();
        return exitId;
    }

    public void deleteAllWordsFromDB() {
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();
        db.delete(sqliteHelper.TABLE_NAME, null,null);
        db.close();
    }

    public long updateWordImage(Word word, String newImageUrl) {
        String wordName = word.getWordName();
        int points = word.getPoints();
        ContentValues contentValues = new ContentValues();
        contentValues.put(sqliteHelper.WORD_NAME, wordName);
        contentValues.put(sqliteHelper.WORD_IMAGE_URL, newImageUrl);
        contentValues.put(sqliteHelper.WORD_POINTS, points);
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();
        long exitId = db.update(sqliteHelper.TABLE_NAME, contentValues, sqliteHelper.WORD_NAME + "=?", new String [] {wordName});
        return exitId;
    }

    public long updateWordPoints(Word word, int newWordPoints) {
        String wordName = word.getWordName();
        String imageUrl = word.getImageUrl();
        ContentValues contentValues = new ContentValues();
        contentValues.put(sqliteHelper.WORD_NAME, wordName);
        contentValues.put(sqliteHelper.WORD_IMAGE_URL, imageUrl);
        contentValues.put(sqliteHelper.WORD_POINTS, newWordPoints);
        SQLiteDatabase db = sqliteHelper.getWritableDatabase();
        long exitId = db.update(sqliteHelper.TABLE_NAME, contentValues, sqliteHelper.WORD_NAME + "=?", new String [] {wordName});
        return exitId;
    }

    public ArrayList<Word> rankBy(String rankingOption) {
        ArrayList<Word> wordList = new ArrayList<Word>();
        String rankingAttribute = rankOptionChooser(rankingOption);

        String[] attributesWanted = {sqliteHelper.WORD_NAME,
                sqliteHelper.WORD_IMAGE_URL,
                sqliteHelper.WORD_POINTS};

        SQLiteDatabase db = sqliteHelper.getWritableDatabase();

        Cursor cursor = db.query(sqliteHelper.TABLE_NAME, attributesWanted,
                null, null, null, null, rankingAttribute + " ASC", null);

        wordList.addAll(allWordsFetcher(cursor));
        cursor.close();
        db.close();
        return wordList;
    }

    public ArrayList<Word> orderBy(String rankingOption, String orderingOption) {
        ArrayList<Word> wordList = new ArrayList<Word>();
        String rankingAttribute = rankOptionChooser(rankingOption);
        String[] attributesWanted = {sqliteHelper.WORD_NAME,
                sqliteHelper.WORD_IMAGE_URL,
                sqliteHelper.WORD_POINTS};

        SQLiteDatabase db = sqliteHelper.getWritableDatabase();

        Cursor cursor = db.query(sqliteHelper.TABLE_NAME, attributesWanted,
                null, null, null, null, rankingAttribute + " "+ orderingOption, null);

        wordList.addAll(allWordsFetcher(cursor));
        cursor.close();
        db.close();
        return wordList;
    }

    public ArrayList<Word> allWordsFetcher(Cursor cursor) {
        ArrayList<Word> wordList = new ArrayList<Word>();
        while (cursor.moveToNext()) {
            String[] outputs = new String[3];
            outputs[0] = cursor.getString(cursor.getColumnIndex(sqliteHelper.WORD_NAME));
            outputs[1] = cursor.getString(cursor.getColumnIndex(sqliteHelper.WORD_IMAGE_URL));
            outputs[2] = cursor.getString(cursor.getColumnIndex(sqliteHelper.WORD_POINTS));

            Word word = new Word(outputs[0], outputs[1], Integer.parseInt(outputs[2]));
            wordList.add(word);
        }
        return wordList;
    }

    public Word wordFetcher(Cursor cursor, String inputWordName) {
        Word word = null;
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(cursor.getColumnIndex(sqliteHelper.WORD_NAME))
                        .equalsIgnoreCase(inputWordName)) {
                    String[] outputs = new String[3];
                    outputs[0] = cursor.getString(cursor.getColumnIndex(sqliteHelper.WORD_NAME));
                    outputs[1] = cursor.getString(cursor.getColumnIndex(sqliteHelper.WORD_IMAGE_URL));
                    outputs[2] = cursor.getString(cursor.getColumnIndex(sqliteHelper.WORD_POINTS));
                    word = new Word(outputs[0], outputs[1], Integer.parseInt(outputs[2]));
                    break;
                }
            }while (cursor.moveToNext());
        }
        return word;
    }

    protected String rankOptionChooser(String rankingOption) {
        String rankingAttribute;
        switch (rankingOption) {
            case "latest":
                rankingAttribute = sqliteHelper.UID;
                break;

            case "difficulty":
                rankingAttribute = sqliteHelper.WORD_POINTS;
                break;

            case "alphabet":
                rankingAttribute = sqliteHelper.WORD_NAME;
                break;

            default:
                rankingAttribute = sqliteHelper.WORD_NAME;
                break;
        }
        return rankingAttribute;
    }

    class SqliteHelper extends SQLiteOpenHelper {

        private Context context;
        private static final String DATABASE_NAME = "wordDatabase.db";
        private static final String TABLE_NAME = "wordTable";
        private static final int DATABASE_VERSION = 1;
        private static final String UID = "w_id";
        private static final String WORD_NAME = "wordName";
        private static final String WORD_IMAGE_URL = "wordImageUrl";
        private static final String WORD_POINTS = "wordPoints";

        private final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + UID + " INTEGER PRIMARY KEY, " +
                WORD_NAME + " VARCHAR(255) UNIQUE, " + WORD_IMAGE_URL + " VARCHAR(255), "
                + WORD_POINTS + " NUMERIC(2,0))";

        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        private SqliteHelper(Context context) {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
                //Toast.makeText(context, "onCre", Toast.LENGTH_SHORT).show();
            }
            catch (SQLException e) {
                //Toast.makeText(context, "onCreFAILED", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
                //Toast.makeText(context, "onUp", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Toast.makeText(context, "onUpFAILED", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

    }
}
