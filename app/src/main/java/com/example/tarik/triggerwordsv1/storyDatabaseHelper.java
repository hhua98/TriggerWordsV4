package com.example.tarik.triggerwordsv1;

/**
 * Created by huanghe on 5/04/2017.
 */

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Chad on 3/4/17.
 */

public class storyDatabaseHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "kidstories.db";
    private static final int DATABASE_VERSION = 1;

    public storyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}


