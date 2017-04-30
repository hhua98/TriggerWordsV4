package com.example.tarik.triggerwordsv1.Newtriggerwords;

/**
 * Created by huanghe on 30/04/2017.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import com.example.tarik.triggerwordsv1.R;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tarik on 30-Apr-17.
 */

public class InteractiveSession extends AppCompatActivity {

    private Context context;
    private ImageView wordImageView;
    private SqliteAdapter sqliteAdapter;
    private ArrayList<Word> al;
    private ArrayList<Word> rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interactivesession);

        context = this;

        LayoutInflater inflater = getLayoutInflater();
        al = new ArrayList<Word>();
        rl = new ArrayList<Word>();
        SwipeFlingAdapterView swipeView = (SwipeFlingAdapterView) findViewById(R.id.swipe_view);
        sqliteAdapter = new SqliteAdapter(this);
        if (sqliteAdapter.getRowCount() != 0) {
            al.addAll(sqliteAdapter.getAllWords());
            Log.d("LIST", ": " + al.toString());
        }

        wordImageView = (ImageView) findViewById(R.id.wordsImage);

        final SwipeWordAdapter swipeWordAdapter = new SwipeWordAdapter(al, inflater, this);

        swipeView.setAdapter(swipeWordAdapter);

        swipeView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                rl.add(al.get(0));
                al.remove(0);
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

                for (Word a : rl) {
                    al.add(a);
                }
            }

            @Override
            public void onScroll(float v) {

            }
        });
    }
}
