package com.example.tarik.triggerwordsv1.Information;

/**
 * Created by huanghe on 26/03/2017.
 */


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tarik.triggerwordsv1.R;


public class successStories extends Fragment {
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        myView = inflater.inflate(R.layout.successstories,container,false);
        return myView;
    }

}
