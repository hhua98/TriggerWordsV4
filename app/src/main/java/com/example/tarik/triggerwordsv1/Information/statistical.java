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
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.tarik.triggerwordsv1.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;


public class statistical extends Fragment {
    View myView;

    private RadioButton rb2;
    private RadioButton rb6;
    private RadioButton rb3;
    private RadioButton rb4;
    private RadioButton rb5;
    private RadioButton rb1;
    private RadioGroup rb;
    private ImageView image;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        myView = inflater.inflate(R.layout.statistical,container,false);
        image=(ImageView)myView.findViewById(R.id.imageView3);
        rb=(RadioGroup)myView.findViewById(R.id.radiogrp2);
        rb5=(RadioButton)myView.findViewById(R.id.rb5);
        rb6=(RadioButton)myView.findViewById(R.id.rb6);
        rb3=(RadioButton)myView.findViewById(R.id.rb3);
        rb4=(RadioButton)myView.findViewById(R.id.rb4);
        rb2=(RadioButton)myView.findViewById(R.id.rb2);
        rb1=(RadioButton)myView.findViewById(R.id.rb1);

        rb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId==rb1.getId())
                    image.setImageResource(R.drawable.compare_this_three);
                else if(checkedId==rb6.getId())
                    image.setImageResource(R.drawable.compare_with_others);
                else if(checkedId==rb3.getId())
                    image.setImageResource(R.drawable.dyscalculia);
                else if(checkedId==rb4.getId())
                    image.setImageResource(R.drawable.multiple_learning_difficulties);
                else if(checkedId==rb5.getId())
                    image.setImageResource(R.drawable.moderate_learning_difficulty);
                else if(checkedId==rb1.getId())

                    image.setImageResource(R.drawable.dyslexialine);

                else
                    image.setImageResource(R.drawable.dyslexialine);
                /*Toast.makeText(getBaseContext(), selectedId+"", Toast.LENGTH_SHORT).show();*/
            }
        });



        return myView;
    }




}
