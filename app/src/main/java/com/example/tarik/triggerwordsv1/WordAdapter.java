package com.example.tarik.triggerwordsv1;

/**
 * Created by huanghe on 1/04/2017.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

/**
 * Created by Chad on 2017/03/26.
 */

public class WordAdapter extends BaseAdapter{

    private List<Word> words;
    private LayoutInflater inflater;
    private Context context;



    public WordAdapter(List<Word> words, LayoutInflater inflater, Context context) {
        this.words = words;
        this.inflater = inflater;
        this.context = context;
    }

    @Override
    public int getCount() {
        return words.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.item, viewGroup, false);

//        View viewWord = inflater.inflate(R.layout.item, viewGroup, false);



        Word word = words.get(i);

        //Log.d("font", Integer.toString(i));

        ImageView wordImage = (ImageView) convertView.findViewById(R.id.wordImage);
        TextView wordText = (TextView) convertView.findViewById(R.id.item_text);


        final Typeface disneyn = Typeface.createFromAsset(context.getAssets(), "font/disneyn.ttf");
        final Typeface disney = Typeface.createFromAsset(context.getAssets(), "font/disney.ttf");
        final Typeface alexbrush = Typeface.createFromAsset(context.getAssets(), "font/alexbrush.ttf");
        final Typeface dyslexiafont = Typeface.createFromAsset(context.getAssets(), "font/dyslexiafont.ttf");
        final Typeface greatday = Typeface.createFromAsset(context.getAssets(), "font/greatday.ttf");
        final Typeface script = Typeface.createFromAsset(context.getAssets(), "font/script.ttf");




        Random rand = new Random();

        int  n = rand.nextInt(50) + 1;

        switch (n % 10) {
            case 1: wordText.setTypeface(disneyn);
                break;
            case 2:
            case 7: wordText.setTypeface(disney);
                break;
            case 3: wordText.setTypeface(alexbrush);
                break;
            case 4: wordText.setTypeface(dyslexiafont);
                break;
            case 5: wordText.setTypeface(greatday);
                break;
            case 6:
            case 8:    wordText.setTypeface(script);
                break;
            default: wordText.setTypeface(dyslexiafont);
                break;
        }


        // ImageView wordsImage = (ImageView) convertView.findViewById(R.id.wordsImage);

        wordImage.setImageResource(word.getImageId());
        wordText.setText(word.getName());

        //  wordsImage.setImageResource(word.getImageId());

        return convertView;
    }
}