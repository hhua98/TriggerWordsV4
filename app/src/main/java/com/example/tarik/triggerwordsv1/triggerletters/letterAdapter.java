package com.example.tarik.triggerwordsv1.triggerletters;

/**
 * Created by huanghe on 5/05/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import com.example.tarik.triggerwordsv1.R;

/**
 * Created by Chad on 28/4/17.
 */

public class letterAdapter extends BaseAdapter {

    private List<letter1> letters = new ArrayList<>(0);
    private LayoutInflater inflater;
    private Context context;

    public letterAdapter(List<letter1> letters, LayoutInflater inflater, Context context) {
        this.letters = letters;
        this.inflater = inflater;
        this.context = context;
    }

    @Override
    public int getCount() {
        return letters.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.item_coverflow, null);

            CoverFlowAdapter.ViewHolder viewHolder = new CoverFlowAdapter.ViewHolder();
            viewHolder.text = (TextView) rowView.findViewById(R.id.label);
            viewHolder.image = (ImageView) rowView
                    .findViewById(R.id.image);
            rowView.setTag(viewHolder);
        }


        CoverFlowAdapter.ViewHolder holder = (CoverFlowAdapter.ViewHolder) rowView.getTag();

        holder.image.setImageBitmap(letters.get(position).getImage());
        holder.text.setText(letters.get(position).getWord());

        return rowView;
    }

    static class ViewHolder {
        public TextView text;
        public ImageView image;
    }

}
