package com.example.tarik.triggerwordsv1.triggerletters;

/**
 * Created by huanghe on 5/05/2017.
 */

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Chad on 28/4/17.
 */

public class letterDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "letter.db";
    private static final int DATABASE_VERSION = 1;

    public letterDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
