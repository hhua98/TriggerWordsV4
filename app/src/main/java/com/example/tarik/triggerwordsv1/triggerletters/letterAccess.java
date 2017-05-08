package com.example.tarik.triggerwordsv1.triggerletters;

/**
 * Created by huanghe on 5/05/2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Chad on 28/4/17.
 */

public class letterAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static letterAccess instance;

    private letterAccess(Context context) {
        this.openHelper = new letterDatabase(context);
    }

    public static letterAccess getInstance(Context context) {
        if (instance == null) {
            instance = new letterAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public List<letter1> getLettersAll() {
        List<letter1> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM letter1", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int letterId = cursor.getColumnIndexOrThrow("letter");
            int letterUpId = cursor.getColumnIndexOrThrow("letterup");
            int wordId = cursor.getColumnIndexOrThrow("word");
            int imageId = cursor.getColumnIndexOrThrow("image");
            int tagId = cursor.getColumnIndexOrThrow("tag");

            String letter = cursor.getString(letterId);
            String letterup = cursor.getString(letterUpId);
            String word = cursor.getString(wordId);
            int tag = cursor.getInt(tagId);
            byte[] image = cursor.getBlob(imageId);

            letter1 letter1;

            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            letter1 = new letter1(letter, letterup, word, bmp, tag);

            list.add(letter1);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}
