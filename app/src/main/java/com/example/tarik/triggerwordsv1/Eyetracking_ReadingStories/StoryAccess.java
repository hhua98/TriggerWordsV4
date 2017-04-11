package com.example.tarik.triggerwordsv1.Eyetracking_ReadingStories;

/**
 * Created by huanghe on 5/04/2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chad on 3/4/17.
 */

public class StoryAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static StoryAccess instance;

    private StoryAccess(Context context) {
        this.openHelper = new storyDatabaseHelper(context);
    }

    public static StoryAccess getInstance(Context context) {
        if (instance == null) {
            instance = new StoryAccess(context);
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

    public List<Story> getStories() {
        List<Story> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM stories", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int storyTitleId = cursor.getColumnIndexOrThrow("title");
            int storyAuthorId = cursor.getColumnIndexOrThrow("author");
            int storyContentId = cursor.getColumnIndexOrThrow("content");
            int storyCommentId = cursor.getColumnIndexOrThrow("comment");
            String title = cursor.getString(storyTitleId);
            String author = cursor.getString(storyAuthorId);
            String content = cursor.getString(storyContentId);
            String comment = cursor.getString(storyCommentId);
            Story story;
            if (author == null) {
                if (comment == null) {
                    story = new Story(title, "Unknow", content, "NA");
                } else {
                    story = new Story(title, "Unknow", content, comment);
                }
            } else {
                story = new Story(title, author, content, comment);
            }
            list.add(story);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}