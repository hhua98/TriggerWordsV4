package com.example.tarik.triggerwordsv1.Information;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tarik.triggerwordsv1.R;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;

public class generalInformation extends Fragment implements View.OnClickListener{


    //1st
    Activity activity = getActivity();
    private View titleBox1;
    private TextView titleText1;

    private View contentBox1;
    private TextView contentText1;

    private ImageView downArrowImage1;
    private ImageView upArrowImage1;


    //2nd
    private View titleBox2;
    private TextView titleText2;

    private View contentBox2;
    private TextView contentText2;

    private ImageView downArrowImage2;
    private ImageView upArrowImage2;


    //3rd
    private View titleBox3;
    private TextView titleText3;

    private View contentBox3;
    private TextView contentText3;

    private ImageView downArrowImage3;
    private ImageView upArrowImage3;

    //4th
    private View titleBox4;
    private TextView titleText4;

    private View contentBox4;
    private TextView contentText4;

    private ImageView downArrowImage4;
    private ImageView upArrowImage4;


    //others
    private ObservableScrollView mainScroll;
    private FloatingActionButton fab;
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        myView = inflater.inflate(R.layout.general_information,container,false);
        mainScroll = (ObservableScrollView) myView.findViewById(R.id.mainScrollView);
        fab = (FloatingActionButton) myView.findViewById(R.id.fab);
        fab.attachToScrollView(mainScroll);


        //1st
        titleBox1 = myView.findViewById(R.id.titleBoxView1);
        titleBox1.setOnClickListener(this);

        titleText1 = (TextView) myView.findViewById(R.id.titleTextView1);
        titleText1.setText("What is Dyslexia?");

        contentBox1 = myView.findViewById(R.id.contentBoxView1);
        contentBox1.setVisibility(View.GONE);

        contentText1 = (TextView) myView.findViewById(R.id.contentTextView1);
        contentText1.setText("Dyslexia literally means ‘trouble with words’. " +
                "It is a term used to describe difficulties with reading, writing, " +
                "spelling and mathematical symbols. It has now been sub-sectioned into " +
                "many different labels such as dyspraxia, ADD, ADHD, visual-spatial " +
                "learners, central auditory processing disorder, visual processing " +
                "disorder, dysgraphia, dyscalculia, processing disorders, and so on. " +
                "Although these labels are usually regarded as separate from one another," +
                " the underlying symptoms can result from the same thing: “disorientation”.");

        downArrowImage1 = (ImageView) myView.findViewById(R.id.downArrowImageView1);
        upArrowImage1 = (ImageView) myView.findViewById(R.id.upArrowImageView1);
        upArrowImage1.setVisibility(View.INVISIBLE);


        //2nd
        titleBox2 = myView.findViewById(R.id.titleBoxView2);
        titleBox2.setOnClickListener(this);

        titleText2 = (TextView) myView.findViewById(R.id.titleTextView2);
        titleText2.setText("What is????");

        contentBox2 = myView.findViewById(R.id.contentBoxView2);
        contentBox2.setVisibility(View.GONE);

        contentText2 = (TextView) myView.findViewById(R.id.contentTextView2);
        contentText2.setText("Dyslexia literally means ‘trouble with words’. It is a term " +
                "used to describe difficulties with reading, writing, spelling and " +
                "mathematical symbols. It has now been sub-sectioned into many different " +
                "labels such as dyspraxia, ADD, ADHD, visual-spatial learners, central " +
                "auditory processing disorder, visual processing disorder, dysgraphia, " +
                "dyscalculia, processing disorders, and so on. Although these labels are " +
                "usually regarded as separate from one another, the underlying symptoms " +
                "can result from the same thing: “disorientation”.");

        downArrowImage2 = (ImageView) myView.findViewById(R.id.downArrowImageView2);
        upArrowImage2 = (ImageView) myView.findViewById(R.id.upArrowImageView2);
        upArrowImage2.setVisibility(View.INVISIBLE);


        //3rd
        titleBox3 = myView.findViewById(R.id.titleBoxView3);
        titleBox3.setOnClickListener(this);

        titleText3 = (TextView) myView.findViewById(R.id.titleTextView3);
        titleText3.setText("Contacts");

        contentBox3 = myView.findViewById(R.id.contentBoxView3);
        contentBox3.setVisibility(View.GONE);

        contentText3 = (TextView) myView.findViewById(R.id.contentTextView3);
        contentText3.setText("Dyslexia literally means ‘trouble with words’. It is a term " +
                "used to describe difficulties with reading, writing, spelling and " +
                "mathematical symbols. It has now been sub-sectioned into many different " +
                "labels such as dyspraxia, ADD, ADHD, visual-spatial learners, central " +
                "auditory processing disorder, visual processing disorder, dysgraphia, " +
                "dyscalculia, processing disorders, and so on. Although these labels are " +
                "usually regarded as separate from one another, the underlying symptoms " +
                "can result from the same thing: “disorientation”.");

        downArrowImage3 = (ImageView) myView.findViewById(R.id.downArrowImageView3);
        upArrowImage3 = (ImageView) myView.findViewById(R.id.upArrowImageView3);
        upArrowImage3.setVisibility(View.INVISIBLE);


        //4th
        titleBox4 = myView.findViewById(R.id.titleBoxView4);
        titleBox4.setOnClickListener(this);

        titleText4 = (TextView) myView.findViewById(R.id.titleTextView4);
        titleText4.setText("Links");

        contentBox4 = myView.findViewById(R.id.contentBoxView4);
        contentBox4.setVisibility(View.GONE);

        contentText4 = (TextView) myView.findViewById(R.id.contentTextView4);
        contentText4.setText("Dyslexia literally means ‘trouble with words’. It is a term " +
                "used to describe difficulties with reading, writing, spelling and " +
                "mathematical symbols. It has now been sub-sectioned into many different " +
                "labels such as dyspraxia, ADD, ADHD, visual-spatial learners, central " +
                "auditory processing disorder, visual processing disorder, dysgraphia, " +
                "dyscalculia, processing disorders, and so on. Although these labels are " +
                "usually regarded as separate from one another, the underlying symptoms " +
                "can result from the same thing: “disorientation”.");

        downArrowImage4 = (ImageView) myView.findViewById(R.id.downArrowImageView4);
        upArrowImage4 = (ImageView) myView.findViewById(R.id.upArrowImageView4);
        upArrowImage4.setVisibility(View.INVISIBLE);

        fab.setOnClickListener(this);


        return myView;
    }




    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.titleBoxView1:
                toggle_contents(contentBox1, downArrowImage1, upArrowImage1);
                break;

            case R.id.titleBoxView2:
                toggle_contents(contentBox2, downArrowImage2, upArrowImage2);
                break;

            case R.id.titleBoxView3:
                toggle_contents(contentBox3, downArrowImage3, upArrowImage3);
                break;

            case R.id.titleBoxView4:
                toggle_contents(contentBox4, downArrowImage4, upArrowImage4);
                break;

            case R.id.fab:
                mainScroll.setScrollY(0);
                break;
        }
    }

    public void toggle_contents(View view, ImageView downImageView, ImageView upImageView) {
        if(!view.isShown()){
            AnimUpDown.slide_down(getActivity(), view);
            view.setVisibility(View.VISIBLE);
            downImageView.setVisibility(View.INVISIBLE);
            upImageView.setVisibility(View.VISIBLE);
        }
        else {
            view.setVisibility(View.GONE);
            downImageView.setVisibility(View.VISIBLE);
            upImageView.setVisibility(View.INVISIBLE);
        }
    }
}

