package com.example.tarik.triggerwordsv1.Information;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
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
        titleText2.setText("Dealing with dyslexia");

        contentBox2 = myView.findViewById(R.id.contentBoxView2);
        contentBox2.setVisibility(View.GONE);

        contentText2 = (TextView) myView.findViewById(R.id.contentTextView2);
        contentText2.setText("Even if you aren’t sure what’s causing your child’s reading issues, there are still ways to help him—and get support for yourself, too. Here are a few options to consider:\n" +
                "Learn as much as you can. Understanding your child’s trouble with reading is the first step to getting him the help he needs. The more you know, the better able you’ll be to find ways to build his reading skills and make reading more fun.\n" +
                "Observe and take notes. By closely watching your child’s behavior, you may be able to spot patterns and triggers. Maybe he gets more frustrated when he reads after dinner than before dinner. That allows you to try different strategies such as having him do his reading after school, and save math work for later.\n" +
                "Read out loud. Whether it’s Dr. Seuss or a chapter of Harry Potter, reading together with your child will help reinforce his skills. It may also help him enjoy and learn from books without having to struggle to read them.\n" +
                "Tap into your child’s interests. The more interested your child is in a topic, the more time he’s likely to spend reading. And it doesn’t have to be just books. Comics, magazines and websites may be more fun and appealing. And they can be equally helpful in building skills.\n" +
                "Focus on effort, not outcome. Praise your child for trying hard. Emphasize that everyone makes mistakes, you included! Help him understand how important it is to keep practicing. Acknowledge even the smallest bit of progress. Your encouragement will help him stay motivated.\n" +
                "Use audiobooks. Check to see if your library has audio recordings of books. You can also access them online. Listening to a book while looking at the words can help your child learn to connect the sounds he’s hearing to the words he’s seeing.\n" +
                "Look for apps and other high-tech help. Word processors and spell-checkers can help kids who have trouble with reading and spelling. Voice recognition software can help older students tackle writing assignments by letting them dictate their ideas to the computer. There are also lots of apps and online games that can help your child build reading skills.\n" +
                "Make your home reader-friendly. Try to stock every room (including the bathroom!) with at least a few books or magazines your child might be interested in reading. Take a book on long car rides and read it to your family so you can all discuss it.\n" +
                "See it through your child’s eyes. It’s hard to know what your child is experiencing with his reading difficulties. Get a sense of what it might feel like to have those issues. Having that insight can make it easier to be supportive.\n" +
                "Connect with other parents. Knowing you’re not alone can make you feel supported. Our online community can help you find other parents who also have kids with reading issues. They can be a great source of tips, ideas and encouragement.\n");

        downArrowImage2 = (ImageView) myView.findViewById(R.id.downArrowImageView2);
        upArrowImage2 = (ImageView) myView.findViewById(R.id.upArrowImageView2);
        upArrowImage2.setVisibility(View.INVISIBLE);


        //3rd
        titleBox3 = myView.findViewById(R.id.titleBoxView3);
        titleBox3.setOnClickListener(this);

        titleText3 = (TextView) myView.findViewById(R.id.titleTextView3);
        titleText3.setText("How you get answers");

        contentBox3 = myView.findViewById(R.id.contentBoxView3);
        contentBox3.setVisibility(View.GONE);

        contentText3 = (TextView) myView.findViewById(R.id.contentTextView3);
        contentText3.setText("Talk with your child’s teacher. Knowing what’s happening in school is a good first step to understanding your child’s issues. The teacher can tell you how your child’s trouble with reading is affecting his learning. That information will be helpful if you talk with your child’s doctor or other professionals.\n" +
                "The teacher may also try out some informal supports in class to see if they help with his reading, writing and self-esteem.\n" +
                "Look into an educational evaluation. If your child is evaluated by the school, you may be able to get extra support and services to help with his reading issues. Either you or your child’s teacher can request an evaluation. If the school agrees to do one, it won’t cost you anything.\n" +
                "If your child is eligible for support, the school will commit to providing it in writing, through an Individualized Education Program (IEP) or a 504 plan. If your child is under age 3, you also can contact your state’s early intervention system and request an evaluation free of charge. No referral is needed.\n" +
                "Talk with your child’s doctor. Tell the doctor what you’ve observed at home or what the teacher has noticed. That includes trouble with any other skills besides reading. The doctor may be able to rule out some medical causes, including ADHD. Or she may refer you to a specialist.\n" +
                "Consult with specialists. There are a number of professionals who can help figure out why your child is struggling with reading. A specially trained psychologist can check for learning and attention issues or ADHD. A neurologist can also diagnose ADHD.\n" +
                "Talk to a learning specialist. This professional can evaluate your child for learning and attention issues using the same tests the school would use. But you'll likely need to pay because it’s a private evaluation.\n");

        downArrowImage3 = (ImageView) myView.findViewById(R.id.downArrowImageView3);
        upArrowImage3 = (ImageView) myView.findViewById(R.id.upArrowImageView3);
        upArrowImage3.setVisibility(View.INVISIBLE);


        //4th
        titleBox4 = myView.findViewById(R.id.titleBoxView4);
        titleBox4.setOnClickListener(this);

        titleText4 = (TextView) myView.findViewById(R.id.titleTextView4);
        titleText4.setText("Links Under construction");

        contentBox4 = myView.findViewById(R.id.contentBoxView4);
        contentBox4.setVisibility(View.GONE);


        contentText4 = (TextView) myView.findViewById(R.id.contentTextView4);
        contentText4.setMovementMethod(LinkMovementMethod.getInstance());
        Linkify.addLinks(contentText4, Linkify.ALL);
        contentText4.setText("http://dyslexiaassociation.org.au/\n" +
                "\n https://www.davisdyslexia.com/staff.html\n" +
                "\n https://dyslexiaida.org/\n" +
                "\n http://dyslexiamelbourne.com/\n" +
        "\n https://www.understood.org/en/learning-attention-issues/child-learning-disabilities/dyslexia/understanding-dyslexia\n" +
                "\n");

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

