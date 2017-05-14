package com.example.tarik.triggerwordsv1.Information;

/**
 * Created by huanghe on 26/03/2017.
 */


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tarik.triggerwordsv1.R;


public class successStories extends Fragment implements View.OnClickListener{
    View myView;

    //Celebrities
    private CardView titleBox1;
    private TextView titleText1;
    private View contentBox1;

    //Steve Mcqueen
    private TextView contentText1TextA;
    private ImageView contentText1ImageA;
    private TextView contentText1TitleA;

    //Tom Cruise
    private TextView contentText1TextB;
    private ImageView contentText1ImageB;
    private TextView contentText1TitleB;

    //Anderson Cooper
    private TextView contentText1TextC;
    private ImageView contentText1ImageC;
    private TextView contentText1TitleC;

    private ImageView downArrowImage1;
    private ImageView upArrowImage1;

    //Sports
    private CardView titleBox2;
    private TextView titleText2;
    private View contentBox2;

    //Muhammad Ali
    private TextView contentText2TextA;
    private ImageView contentText2ImageA;
    private TextView contentText2TitleA;

    //Michelle Carter
    private TextView contentText2TextB;
    private ImageView contentText2ImageB;
    private TextView contentText2TitleB;

    //Meryl Davis
    private TextView contentText2TextC;
    private ImageView contentText2ImageC;
    private TextView contentText2TitleC;

    private ImageView downArrowImage2;
    private ImageView upArrowImage2;


    //Musicians
    private CardView titleBox3;
    private TextView titleText3;
    private View contentBox3;

    //John Lennon
    private TextView contentText3TextA;
    private ImageView contentText3ImageA;
    private TextView contentText3TitleA;

    //Noel Gallaghar
    private TextView contentText3TextB;
    private ImageView contentText3ImageB;
    private TextView contentText3TitleB;

    //Cher
    private TextView contentText3TextC;
    private ImageView contentText3ImageC;
    private TextView contentText3TitleC;

    private ImageView downArrowImage3;
    private ImageView upArrowImage3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        myView = inflater.inflate(R.layout.successstories,container,false);




        //Celebrities
        titleBox1 = (CardView)myView.findViewById(R.id.titleBoxView1);
        titleBox1.setOnClickListener(this);

        titleText1 = (TextView) myView.findViewById(R.id.titleTextView1);
        titleText1.setText("Celebrities");
        contentBox1 = myView.findViewById(R.id.contentBoxView1);
        contentBox1.setVisibility(View.GONE);

        //Steve Mcqueen
        contentText1TitleA = (TextView) myView.findViewById(R.id.contentTextView1TitleA);
        contentText1TitleA.setText("Steve McQueen");
        contentText1ImageA = (ImageView) myView.findViewById(R.id.contentTextView1ImageA);
        contentText1TextA = (TextView) myView.findViewById(R.id.contentTextView1TextA);
        contentText1TextA.setText("McQueen was promoting his award-winning movie when he talked for the first time " +
                "about his learning struggles. He shared that he has dyslexia and had hidden it for years out of shame. " +
                "\"I thought it meant I was stupid,\" McQueen explained. \n\nHis success spotlights how wrong he was. McQueen's "+
                "storytelling skills created a movie that received Oscars top honor. And so far in his career, he has written, "+
                "directed and produced more than a dozen films.");

        //Tom Cruise
        contentText1TitleB = (TextView) myView.findViewById(R.id.contentTextView1TitleB);
        contentText1TitleB.setText("Tom Cruise");
        contentText1ImageB = (ImageView) myView.findViewById(R.id.contentTextView1ImageB);
        contentText1TextB = (TextView) myView.findViewById(R.id.contentTextView1TextB);
        contentText1TextB.setText("Cruise spent his childhood trying to hide his dyslexia from his peers." +
                "\"Graduating high school in 1980, I was a functional illiterate. When I was about 7 years old, I had been labeled dyslexic. " +
                "I'd try to concentrate on what I was reading, then I'd get to the end of the page and have very little memory of anything I had read." +
                "All through school and well into my career, I felt like I had a secret, " +
                "says Tom Cruise. \n\nHe could barely read in high school or through his earliest roles. " +
                "As he started to embrace his love of acting, Cruise realized that his inability to read would hold him back if he didn't work hard at it. " +
                "\"I had to train myself to focus my attention. I became very visual and learned how to create mental images in order to comprehend what I read\", says Tom.");


        //Anderson Cooper

        contentText1TitleC = (TextView) myView.findViewById(R.id.contentTextView1TitleC);
        contentText1TitleC.setText("Anderson Cooper");
        contentText1ImageC = (ImageView) myView.findViewById(R.id.contentTextView1ImageC);
        contentText1TextC = (TextView) myView.findViewById(R.id.contentTextView1TextC);
        contentText1TextC.setText("I grew up in a home where reading and writing had great value, Cooper has said." +
                "His brother was a voracious reader, always carrying a book around with him. So Cooper did the same." +
                "But he admits, I would just pretend to read it, because I had trouble reading and making sense of the words in particular. " +
                "To a child with a learning disability, school can be an incredibly isolating place, Cooper said. " +
                "\n\nBut a school with understanding teachers changed that for him: It made all the difference in my life early on. " +
                "And the good news is that there are great schools out there, able to provide the necessary resources and support. " +
                "Cooper became a strong student, and went on to graduate from Yale. He started his journalism career straight out of college.");

        downArrowImage1 = (ImageView) myView.findViewById(R.id.downArrowImageView1);
        upArrowImage1 = (ImageView) myView.findViewById(R.id.upArrowImageView1);
        upArrowImage1.setVisibility(View.INVISIBLE);


        //Sports
        titleBox2 = (CardView)myView.findViewById(R.id.titleBoxView2);
        titleBox2.setOnClickListener(this);

        titleText2 = (TextView) myView.findViewById(R.id.titleTextView2);
        titleText2.setText("Sports personalities");
        contentBox2 = myView.findViewById(R.id.contentBoxView2);
        contentBox2.setVisibility(View.GONE);

        //Muhammad Ali
        contentText2TitleA = (TextView) myView.findViewById(R.id.contentTextView2TitleA);
        contentText2TitleA.setText("Muhammad Ali");
        contentText2ImageA = (ImageView) myView.findViewById(R.id.contentTextView2ImageA);
        contentText2TextA = (TextView) myView.findViewById(R.id.contentTextView2TextA);
        contentText2TextA.setText("Boxing world heavy weight champion Muhammad Ali fought hard in the ring as well as the classroom. Diagnosed with dyslexia " +
                "at a young age, Ali struggled with reading. He barely graduated from high school and never felt smart. He hated reading, " +
                "but loved to fight. \n\nAli grew up to become an incredible athlete, winning world championships and gold medals at the Olympics. " +
                "He says his struggles with academics only motivated him to work harder for success.");

        //Michelle Carter
        contentText2TitleB = (TextView) myView.findViewById(R.id.contentTextView2TitleB);
        contentText2TitleB.setText("Michelle Carter");
        contentText2ImageB = (ImageView) myView.findViewById(R.id.contentTextView2ImageB);
        contentText2TextB = (TextView) myView.findViewById(R.id.contentTextView2TextB);
        contentText2TextB.setText("Michelle Carter grew up with dyslexia and ADHD. Reading and spelling were a challenge for her (they still are), " +
                "and she struggled to pay attention in school. Then she found her passion and talent: track and field. " +
                "It motivated her to do well enough in school to be able to continue competing. \n\nAnd in 2016 it took her all the way to Rio, " +
                "where she won an Olympic gold medal in the shot put. Now she is using her new found fame and to help other children follow in her footsteps.");


        //Meryl Davis

        contentText2TitleC = (TextView) myView.findViewById(R.id.contentTextView2TitleC);
        contentText2TitleC.setText("Meryl Davis");
        contentText2ImageC = (ImageView) myView.findViewById(R.id.contentTextView2ImageC);
        contentText2TextC = (TextView) myView.findViewById(R.id.contentTextView2TextC);
        contentText2TextC.setText("Meryl began ice skating at the age of five and was diagnosed with dyslexia in her third grade. While she shined on the ice, " +
                "she secretly struggled with self-esteem and often viewed herself as unintelligent. In the 2014 Sochi Olympics Meryl became one half of the pair of first American Ice Dancers to win a medal in gold. " +
                "\n\nDavis says her dyslexia is what helped her develop a remarkable character. I learned how I learned how my brain worked.  It helped me adjust " +
                "and compensate for my differences. It opened me up to problem solving, seeing things differently, and how I can help myself overcome things, says Meryl.");

        downArrowImage2 = (ImageView) myView.findViewById(R.id.downArrowImageView2);
        upArrowImage2 = (ImageView) myView.findViewById(R.id.upArrowImageView2);
        upArrowImage2.setVisibility(View.INVISIBLE);


        //Musicians
        titleBox3 = (CardView)myView.findViewById(R.id.titleBoxView3);
        titleBox3.setOnClickListener(this);

        titleText3 = (TextView) myView.findViewById(R.id.titleTextView3);
        titleText3.setText("Musicians");
        contentBox3 = myView.findViewById(R.id.contentBoxView3);
        contentBox3.setVisibility(View.GONE);

        //John Lennon
        contentText3TitleA = (TextView) myView.findViewById(R.id.contentTextView3TitleA);
        contentText3TitleA.setText("John Lennon");
        contentText3ImageA = (ImageView) myView.findViewById(R.id.contentTextView3ImageA);
        contentText3TextA = (TextView) myView.findViewById(R.id.contentTextView3TextA);
        contentText3TextA.setText("Lennon excelled at art and music while attending high school, but his grades were poor and he had trouble spelling. He was rowdy in the classroom and was called a troublemaker. " +
                "He failed his general exams in high school, but was accepted into the Liverpool College of Art with the help of the headmaster. His bad behavior continued through college, and he was expelled before graduation. " +
                "\n\nSome teachers noticed his difficulties with school and would encourage Lennon to express himself. Thus, Lennon let his talent shine through his music and writing to be come one of the legendary musicians of our time. His lyrics " +
                "and political activism were grown from his perseverance through dyslexia.");


        //Noel Gallaghar
        contentText3TitleB = (TextView) myView.findViewById(R.id.contentTextView3TitleB);
        contentText3TitleB.setText("Noel Gallagher");
        contentText3ImageB = (ImageView) myView.findViewById(R.id.contentTextView3ImageB);
        contentText3TextB = (TextView) myView.findViewById(R.id.contentTextView3TextB);
        contentText3TextB.setText("Noel's school life was problematic at best. While he was plainly a bright\n" +
                "young man, he battled with a minor case of dyslexia, which, topped with" +
                "the poor quality of Manchester's schools, was a dangerous combination. " +
                "\n\nAs the lead guitarist and songwriter for British rock band Oasis, Gallagher's dyslexia has the potential to stump his band mates: When I " +
                "write, I'll give it to someone else to read, and they'll say, this doesn't make any sense. And then I'll read it back to them, and they'll say, half " +
                "the words are missing. But to me they're in there.");


        //Cher

        contentText3TitleC = (TextView) myView.findViewById(R.id.contentTextView3TitleC);
        contentText3TitleC.setText("Cher");
        contentText3ImageC = (ImageView) myView.findViewById(R.id.contentTextView3ImageC);
        contentText3TextC = (TextView) myView.findViewById(R.id.contentTextView3TextC);
        contentText3TextC.setText("I never read in school. My report cards always said that I was not living up to my potential\" - Cher " +
                "Cher struggled with reading all through grade school, and at age 16, she dropped out. Though she found out at age 30 that she had dyslexia, Cher went untested in grade school and teachers thought she wasn't trying. " +
                "\n\nCher also was successful on her own. She recorded hits such as \"If I Could Turn Back Time,\" \"Believe,\" and \"I Found Someone.\" Her " +
                "dyslexia might have slowed her down in reading, but it didn't hinder her from becoming a great actress and singer.");

        downArrowImage3 = (ImageView) myView.findViewById(R.id.downArrowImageView3);
        upArrowImage3 = (ImageView) myView.findViewById(R.id.upArrowImageView3);
        upArrowImage3.setVisibility(View.INVISIBLE);



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
