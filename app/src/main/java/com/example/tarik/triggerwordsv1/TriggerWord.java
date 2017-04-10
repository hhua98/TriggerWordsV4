package com.example.tarik.triggerwordsv1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tarik on 27-Mar-17.
 */



public class TriggerWord implements Parcelable {
    private String word;
    private String image;
    private String comment;
    private int mark;


    protected TriggerWord(){
        //required empty constructor for jackson
    }

    protected TriggerWord(String word, String image, String comment) {
        this.word = word;
        this.image = image;
        this.comment = comment;
        this.mark = 0;
    }
    protected TriggerWord(String word, String image, String comment, int mark) {
        this(word, image, comment);
        this.mark = mark;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    protected TriggerWord(Parcel in) {
        word = in.readString();
        image = in.readString();
        comment = in.readString();
        mark = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        dest.writeString(image);
        dest.writeString(comment);
        dest.writeInt(mark);
    }

    @SuppressWarnings("unused")
    public static final Creator<TriggerWord> CREATOR = new Creator<TriggerWord>() {
        @Override
        public TriggerWord createFromParcel(Parcel in) {
            return new TriggerWord(in);
        }

        @Override
        public TriggerWord[] newArray(int size) {
            return new TriggerWord[size];
        }
    };

    @Override
    public String toString() {
        return "TriggerWord{" +
                "word='" + word + '\'' +
                ", image='" + image + '\'' +
                ", comment='" + comment + '\'' +
                ", mark=" + mark +
                '}';
    }
}


