package com.example.tarik.triggerwordsv1.Newtriggerwords;

/**
 * Created by huanghe on 30/04/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import com.example.tarik.triggerwordsv1.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Tarik on 24-Apr-17.
 */

public class WordRecyclerAdapter extends RecyclerView.Adapter<WordRecyclerAdapter.WordViewHolder>{

    private LayoutInflater inflater;
    private Context context;
    private static ArrayList<Word> wordList;
    private WordViewHolder wordViewHolder;
    private onClickItemListener clickListener;
    //private static int IMAGE_MAX_SIZE = 70;


    protected WordRecyclerAdapter(Context context, ArrayList<Word> wordList) {
        this.context = context;
        this.wordList = wordList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.word_row, parent, false);
        WordViewHolder wordViewholder = new WordViewHolder(view);
        Log.d("me", "OnCREATE");
        return wordViewholder;
    }

    @Override
    public void onBindViewHolder(final WordViewHolder holder, int position) {
        Word currentWord = wordList.get(position);
        holder.mWordName.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/dyslexiafont.ttf"));
        holder.mWordName.setText(currentWord.getWordName());
        int points = currentWord.getPoints();
        holder.mPoints.setText(Integer.toString(points) + ")");
        ratingBarSetter(holder, points);
        imageSetter(holder, currentWord);

        Log.d("bind", "onBind called");
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

    public void setOnClickItemListener(onClickItemListener clickListener) {
        this.clickListener = clickListener;
    }

    public WordViewHolder getWordViewHolder() {
        return this.wordViewHolder;
    }

    public void addingWord(Word newWord, int position){
        wordList.add(0, newWord);
        notifyItemInserted(position);
    }

    public void removingWord(String inputWordName, int position){
        for (Word word : wordList) {
            if (word.getWordName().equalsIgnoreCase(inputWordName)) {
                wordList.remove(word);
                break;
            }
        }
        notifyItemRemoved(position);
    }

    public void removingAllWords() {
        this.wordList.clear();
        notifyDataSetChanged();
    }

    public void editingWord(Word oldWord, Word newWord, int position){
        for (Word word : wordList) {
            if (word.getWordName().equalsIgnoreCase(oldWord.getWordName())) {
                wordList.get(wordList.indexOf(word)).setWordName(newWord.getWordName());
                break;
            }
        }
        notifyItemChanged(position);
    }

    public void settingImage(Word word, String newImageUrl, int position){
        for (Word currentWord : wordList) {
            if ((currentWord.getWordName()).equalsIgnoreCase(word.getWordName())) {
                wordList.get(wordList.indexOf(currentWord)).setImageUrl(newImageUrl);
                Toast.makeText(context, "Image updated for word: " + word.getWordName(), Toast.LENGTH_SHORT).show();
                break;
            }
        }
        notifyItemChanged(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void increasePoints(Word word, int position){
        for (Word currentWord : wordList) {
            if (currentWord.getWordName().equalsIgnoreCase(word.getWordName())) {
                wordList.get(wordList.indexOf(currentWord)).setPoints(word.getPoints() + 1);
                break;
            }
        }
        notifyItemChanged(position);
    }

    public void decreasePoints(Word word, int position){
        for (Word currentWord : wordList) {
            if (currentWord.getWordName().equalsIgnoreCase(word.getWordName())) {
                wordList.get(wordList.indexOf(currentWord)).setPoints(word.getPoints() - 1);
                break;
            }
        }
        notifyItemChanged(position);
    }

    public void ratingBarSetter(WordViewHolder holder, int points) {
        if (points <= 10) {
            if (points >= 7) {
                holder.mProgEasy.setBackgroundColor(Color.parseColor("#76ff03"));
                holder.mProgNormal.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.mProgTough.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.mProg.setText("Easy!");
            }
            else if (points >= 5) {
                holder.mProgEasy.setBackgroundColor(Color.parseColor("#76ff03"));
                holder.mProgNormal.setBackgroundColor(Color.parseColor("#ffd600"));
                holder.mProgTough.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.mProg.setText("Normal");
            }
            else if (points >= 0) {
                holder.mProgEasy.setBackgroundColor(Color.parseColor("#76ff03"));
                holder.mProgNormal.setBackgroundColor(Color.parseColor("#ffd600"));
                holder.mProgTough.setBackgroundColor(Color.parseColor("#f4511e"));
                holder.mProg.setText("Tough!");
            }
        }
    }

    public void imageSetter(WordViewHolder holder, Word word) {
        String imageUrl = word.getImageUrl();
        if (imageUrl != "a") {
            /*File imgFile = new File(imageUrl);
            if (imgFile.exists()) {*/
                Picasso.with(context)
                        .load("file://" + imageUrl)
                        .fit().centerCrop()
                        .error(R.drawable.ic_default_image)
                        .placeholder(R.drawable.ic_default_image)
                        .into(holder.mWordImage);
                /*Bitmap myBitmap = decodeFile(imgFile);
                holder.mWordImage.setImageBitmap(myBitmap);*/
        }
        else {
            //holder.mWordImage.setImageResource(R.drawable.ic_default_image);
            Picasso.with(context)
                    .load(R.drawable.ic_default_image)
                    .fit().centerCrop()
                    .into(holder.mWordImage);
        }
    }

    /*private Bitmap decodeFile(File f){
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream fis1 = new FileInputStream(f);
            BitmapFactory.decodeStream(fis1, null, o);

            int scale = 1;
            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int) Math.pow(2, (int) Math.ceil(Math.log(IMAGE_MAX_SIZE /
                        (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            FileInputStream fis2 = new FileInputStream(f);
            Bitmap b = BitmapFactory.decodeStream(fis2, null, o2);
            fis1.close();
            fis2.close();
            return b;

    } catch (FileNotFoundException e) {} catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView mWordImage;
        TextView mWordName;
        ImageView mLikeButton;
        ImageView mDislikeButton;
        TextView mPoints;
        TextView mContextMenu;
        TextView mProgEasy;
        TextView mProgNormal;
        TextView mProgTough;
        TextView mProg;

        public WordViewHolder(View itemView) {

            super(itemView);
            init();
        }

        private void init() {
            mWordImage = (ImageView) itemView.findViewById(R.id.listWordImageView);
            mWordImage.setOnClickListener(this);
            mWordName = (TextView) itemView.findViewById(R.id.wordNameView);
            mLikeButton = (ImageView) itemView.findViewById(R.id.likeButton);
            mLikeButton.setOnClickListener(this);
            mDislikeButton = (ImageView) itemView.findViewById(R.id.dislikeButton);
            mPoints = (TextView) itemView.findViewById(R.id.pointsTextView);
            mDislikeButton.setOnClickListener(this);
            mContextMenu = (TextView) itemView.findViewById(R.id.contextMenuView);
            mContextMenu.setOnClickListener(this);
            mProgEasy = (TextView) itemView.findViewById(R.id.progressEasyView);
            mProgNormal = (TextView) itemView.findViewById(R.id.progressNormalView);
            mProgTough = (TextView) itemView.findViewById(R.id.progressToughView);
            mProg = (TextView) itemView.findViewById(R.id.progTextView);
        }

        @Override
        public void onClick(View v) {

            Word currentWord = wordList.get(getAdapterPosition());
            switch(v.getId()) {

                case R.id.likeButton:
                    if (clickListener !=null) {
                        clickListener.increaseWordPoints(currentWord.getWordName(), getAdapterPosition());
                    }
                    break;

                case R.id.dislikeButton:
                    if (clickListener !=null) {
                        clickListener.decreaseWordPoints(currentWord.getWordName(), getAdapterPosition());
                    }
                    break;

                case R.id.contextMenuView:
                    if (clickListener !=null) {
                        clickListener.contextMenuHandler(context, mContextMenu, currentWord, getAdapterPosition());
                    }
                    break;

                case R.id.listWordImageView:
                    //Toast.makeText(context, "CLICKED!L", Toast.LENGTH_SHORT).show();
                    if (clickListener!=null) {
                        clickListener.imageClickHandler(currentWord.getImageUrl());
                    }
                    break;

            }
        }
    }

    public interface onClickItemListener {
        void increaseWordPoints(String currentWordName, int position);
        void decreaseWordPoints(String currentWordName, int position);
        void contextMenuHandler(Context context, TextView contextMenuView, Word currentWord, int position);
        void imageClickHandler(String imageUrl);
    }
}
