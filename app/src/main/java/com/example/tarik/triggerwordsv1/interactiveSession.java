package com.example.tarik.triggerwordsv1;

/**
 * Created by huanghe on 1/04/2017.
 */

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.example.tarik.triggerwordsv1.triggerWords.Word;
import com.example.tarik.triggerwordsv1.triggerWords.WordAdapter;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class interactiveSession extends AppCompatActivity {


    private List al, rl;
    private Context context;
    private ImageView wordImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interactivesession);

        context = this;

        LayoutInflater inflater = getLayoutInflater();

        SwipeFlingAdapterView swipeView = (SwipeFlingAdapterView) findViewById(R.id.swipe_view);

        al = new ArrayList<Word>();

        initWords();

        wordImageView = (ImageView) findViewById(R.id.wordsImage);

        Word imageId = (Word)al.get(0);
        wordImageView.setImageResource(imageId.getImageId());


        //wordImageView = (ImageView) findViewById(R.id.wordImage);


        rl = new ArrayList<Word>();



        //final WordAdapter arrayAdapter = new WordAdapter(this, R.layout.item, al);

        final WordAdapter arrayAdapter = new WordAdapter(al, inflater, this);

        swipeView.setAdapter(arrayAdapter);

        swipeView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                rl.add(al.get(0));
                Word imageId = (Word)al.get(1);
                wordImageView.setImageResource(imageId.getImageId());
                al.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object o) {
            }

            @Override
            public void onRightCardExit(Object o) {
            }

            @Override
            public void onAdapterAboutToEmpty(int i) {

                for (Object a : rl) {
//                    for (Word b : al) {
//                        if (b.getName() == a) {
                    al.add(a);
//                        }


                }
            }

            @Override
            public void onScroll(float v) {

            }
//
//            private void imageChagne(Word b) {
//                wordImageView.setImageResource(b.getImageId());
//            }
        });




    }

    private void initWords() {

//
//            Word start = new Word("Start!", 0);
//            al.add(start);
        Word what = new Word("What", R.drawable.what);
        al.add(what);
        Word how = new Word("How", R.drawable.how);
        al.add(how);
        Word why = new Word("Why", R.drawable.why);
        al.add(why);
        Word the = new Word("The", R.drawable.the);
        al.add(the);
        Word dont = new Word("Don't", R.drawable.dont);
        al.add(dont);

    }

}