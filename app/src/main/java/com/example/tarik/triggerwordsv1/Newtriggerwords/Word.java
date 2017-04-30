package com.example.tarik.triggerwordsv1.Newtriggerwords;
import android.os.Parcel;
import android.os.Parcelable;
/**
 * Created by Tarik on 22-Apr-17.
 */

public class Word implements Parcelable {

    private String wordName;
    private String imageUrl;
    private int points;

    public Word() {
        this.wordName = "";
        this.imageUrl = "";
        this.points = 0;
    }

    public Word(String wordName, String imageUrl, int points) {
        this.wordName = wordName;
        this.imageUrl = imageUrl;
        this.points = points;
    }

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }



    protected Word(Parcel in) {
        wordName = in.readString();
        imageUrl = in.readString();
        points = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(wordName);
        dest.writeString(imageUrl);
        dest.writeInt(points);
    }


    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Word> CREATOR = new Parcelable.Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };
}