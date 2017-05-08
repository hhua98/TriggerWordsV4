package com.example.tarik.triggerwordsv1.Newtriggerwords;

/**
 * Created by huanghe on 30/04/2017.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;
import java.util.Random;
import com.example.tarik.triggerwordsv1.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;
import java.util.Random;

/**
 * Created by Tarik on 30-Apr-17.
 */

public class SwipeWordAdapter extends BaseAdapter {

    private List<Word> words;
    private LayoutInflater inflater;
    private Context context;

    private TextView wordText;
    private SqliteAdapter sqliteAdapter;

    private Typeface disneyn;
    private Typeface disney;
    private Typeface alexbrush;
    private Typeface dyslexiafont;
    private Typeface greatday;
    private Typeface script;



    public SwipeWordAdapter(List<Word> words, LayoutInflater inflater, Context context) {
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
    public View getView(int item, View view, ViewGroup viewGroup) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.word_swipe, viewGroup, false);

        Word word = words.get(item);
        init(convertView);
        initFonts();
        chooseFont();

        wordText.setText(word.getWordName());
        return convertView;
    }

    public void init(View convertView) {
        wordText = (TextView) convertView.findViewById(R.id.item_text);
    }


    public void chooseFont() {
        Random rand = new Random();
        int  n = rand.nextInt(50) + 1;
        switch (n % 10) {
            case 1: wordText.setTypeface(disneyn);
                break;
            case 2: wordText.setTypeface(disney);
                break;
            case 3: wordText.setTypeface(alexbrush);
                break;
            case 4: wordText.setTypeface(greatday);
                break;
            case 5: wordText.setTypeface(greatday);
                break;
            case 6: wordText.setTypeface(script);
                break;
            default: wordText.setTypeface(script);
                break;
        }
    }

    public void initFonts () {
        disneyn = Typeface.createFromAsset(context.getAssets(), "font/disneyn.ttf");
        disney = Typeface.createFromAsset(context.getAssets(), "font/disney.ttf");
        alexbrush = Typeface.createFromAsset(context.getAssets(), "font/alexbrush.ttf");
        //dyslexiafont = Typeface.createFromAsset(context.getAssets(), "font/dyslexiafont.ttf");
        greatday = Typeface.createFromAsset(context.getAssets(), "font/greatday.ttf");
        script = Typeface.createFromAsset(context.getAssets(), "font/script.ttf");
    }




}
