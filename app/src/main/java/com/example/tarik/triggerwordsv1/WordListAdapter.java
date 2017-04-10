package com.example.tarik.triggerwordsv1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by Tarik on 28-Mar-17.
 */

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordListViewHolder>{

    private LayoutInflater inflater;
    private Context context;
    List<Informationn> data = Collections.emptyList();

    protected WordListAdapter(Context context, List<Informationn> data) {

        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public WordListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row, parent, false);
        WordListViewHolder holder = new WordListViewHolder(view);
        Log.d("me", "OnCREATE");
        return holder;
    }

    @Override
    public void onBindViewHolder(WordListViewHolder holder, int position) {

        Informationn current = data.get(position);
        holder.wordView.setText(current.title);
        holder.icon.setImageResource(current.iconId);
        Log.d("me", "OnBind" + position);
    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    class WordListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView wordView;
        ImageView icon;
        LinearLayout wholeBox;

        public WordListViewHolder(View itemView) {

            super(itemView);
            wordView = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
            wholeBox = (LinearLayout) itemView.findViewById(R.id.wholeBox);
            wholeBox.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context, "Clicked at " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
        }
    }
}
