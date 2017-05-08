package com.example.tarik.triggerwordsv1.triggerletters;

/**
 * Created by huanghe on 5/05/2017.
 */

import android.graphics.Bitmap;

        import android.graphics.Bitmap;

/**
 * Created by Chad on 28/4/17.
 */

public class letter1 {

    private String letter;
    private String letterup;
    private String word;
    private Bitmap image;
    private int tag;

    public letter1(String letter, String letterup, String word, Bitmap image, int tag) {
        this.letter = letter;
        this.letterup = letterup;
        this.word = word;
        this.image = image;
        this.tag = tag;
    }

    public String getLetter() {
        return letter;
    }

    public String getLetterup() {
        return letterup;
    }

    public String getWord() {
        return word;
    }

    public Bitmap getImage() {
        return image;
    }

    public int getTag() {
        return tag;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public void setLetterup(String letterup) {
        this.letterup = letterup;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return letter  + ", " + word;
    }
}
