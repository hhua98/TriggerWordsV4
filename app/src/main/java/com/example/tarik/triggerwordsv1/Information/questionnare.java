package com.example.tarik.triggerwordsv1.Information;

/**
 * Created by huanghe on 26/03/2017.
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.app.Activity;

import com.example.tarik.triggerwordsv1.R;


public class questionnare extends Fragment {
    View myView;
    private TextView mDisclaimer;
    Activity activity = getActivity();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.questionnare, container, false);
        mDisclaimer = (TextView) myView.findViewById(R.id.disclaimerTextView);
        mDisclaimer.setText("This is a simple assessment of an indication of dyslexia amongst " +
                "children aged 7 to 9. The test was originally devised by " +
                "Ronald D. Davis. \nThe user rates each description according " +
                "to how accurately it describes the person concerned" + "\n\n More info at: \n\n" +
                "https://www.davisdyslexia.com/isit.html");

        myView.findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), AQDisorientation.class));
            }
        });
        /*myView.findViewById(R.id.quitToMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),information.class));
            }
        });*/

        return myView;


    }
}