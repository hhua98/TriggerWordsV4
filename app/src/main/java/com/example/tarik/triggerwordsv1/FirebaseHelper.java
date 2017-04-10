package com.example.tarik.triggerwordsv1;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Tarik on 29-Mar-17.
 */

public class FirebaseHelper{

    private DatabaseReference db;
    private  ArrayList<TriggerWord> wordData;

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
        this.wordData = new ArrayList<TriggerWord>();
        //fetchAllData();
    }

    public ArrayList<TriggerWord> fetchAllData() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    fetchData(dataSnapshot);
                //logger(wordData);
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                //b
            }
        });
        logger(wordData);
        return wordData;
    }
/*
    public ArrayList<TriggerWord> fetchAllData() {
        Log.d("THIS in fetall :", "t ");
        return wordData;
    }

/*
        ArrayList<TriggerWord> wrongWordData = new ArrayList<TriggerWord>();
        TriggerWord triggerWord1 = new TriggerWord("baal", "baal", "baal");
        TriggerWord triggerWord2 = new TriggerWord("saal", "saal", "saal");
        wrongWordData.add(triggerWord1);
        wrongWordData.add(triggerWord2);

        Log.d("THIS in fetall :", "t ");
        logger(wrongWordData);
        //Log.d("THIS :", "t " + wordData.get(1).getWord());
        //return wordData;
*/

    public void fetchData(DataSnapshot dataSnapshot) {
        //wordData.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            String word = ds.child("word").getValue(String.class);
            Log.d("word", "word: " + word);

            String image = ds.child("image").getValue(String.class);
            Log.d("word", "image: " + image);

            String comment = ds.child("comment").getValue(String.class);
            Log.d("word", "comment: " + comment);

            int mark = ds.child("mark").getValue(Integer.class);
            Log.d("mark", "mark: " + mark);

            TriggerWord triggerWord = new TriggerWord(word, image, comment, mark);

            wordData.add(triggerWord);
            Log.d("trigger word", "trigger word: " + triggerWord.getWord() + ", "
                    + triggerWord.getComment());
        }
       // logger(wordData);
    }

    public void logger(ArrayList<TriggerWord> wordData) {
        Log.d("SIZE :", "t " + wordData.size());
        for (TriggerWord word : wordData) {
            if (word != null)
                Log.d("my word list", "in " +  Thread.currentThread().getStackTrace() + " :\n" + word);
            else
                Log.d("my word list", "in "  + "No data");
        }
    }
}
