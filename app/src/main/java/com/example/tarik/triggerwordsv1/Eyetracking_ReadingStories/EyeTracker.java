package com.example.tarik.triggerwordsv1.Eyetracking_ReadingStories;

/**
 * Created by huanghe on 1/04/2017.
 */

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarik.triggerwordsv1.ActionMenu.MenuActivity;
import com.example.tarik.triggerwordsv1.ActionMenu.interactMenu;
import com.example.tarik.triggerwordsv1.R;


public class EyeTracker extends AppCompatActivity {

    LinearLayout linearLayout;
    ImageButton button;
    ImageButton buttonStop;
    TextView textView;
    ImageView imageView;
    //Using for model control
    private Boolean flag = false;
    //Using for model control
    private Boolean switchFlag = true;
    ObjectAnimator objectAnimator;
    //Using for count the times of click button
    int repeat = 0;
    int x = 0;
    int y = 0;
    //Using for model control
    private Boolean startFlag = true;
    int backButtonCount = 0;

    /**
     * Create method.
     * This method define some layout section and listener.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eye_tracker);

        linearLayout = (LinearLayout) findViewById(R.id.root_view);
        button = (ImageButton)findViewById(R.id.button1);
        imageView = (ImageView) findViewById(R.id.image1);

        buttonStop = (ImageButton)findViewById(R.id.buttonStop);

        //customer method for onclick listener
        button.setOnClickListener(myOnClickListener);
        buttonStop.setOnClickListener(myOnClickListenerStop);


        x = dpToPx(170);
        y = dpToPx(280);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {
        if(backButtonCount >= 1)
        {
            Intent intentLoadNewActivity = new Intent(EyeTracker.this, interactMenu.class);
            startActivity(intentLoadNewActivity);
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to exit.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }
    /**
     * convert dp to px
     * @param dp
     * @return
     */
    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }




    /**
     * This method controls the start button.
     * This button will control the switch between different models and images.
     */
    View.OnClickListener myOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button1:
                    if (startFlag == true) {
                        stopAnimation();
                        imageView.setImageResource(R.drawable.cake);
                        switchModel();
                        button.setImageResource(R.drawable.stop2);
                        startFlag = false;
                    } else {
                        stopAnimation();
                        button.setImageResource(R.drawable.start2);
                        startFlag = true;
                    }

                    break;
                default:
                    break;

            }
        }
    };

    /**
     * This method controls the animation.
     * It can stop the animation and let the image back to its original location
     */
    View.OnClickListener myOnClickListenerStop = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonStop:
                    stopAnimation();
                    switchModel();
                    imageChange();
                    repeat++;
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * This method define the animation of the image.
     * Also, this method define the path of animation.
     * The path of the animation looks like number 8
     * @param view
     */
    private void moveImage(ImageView view) {
        // float x = view.getX();
        // float y = view.getY();

        imageView.setVisibility(View.VISIBLE);

//        int[] location = new int[2];
//        imageView.getLocationInWindow(location);
//        x = location[0];
//        y = location[1];

        Log.d("xxx", Integer.toString(y));

        Path path =  new Path();

        path.moveTo(x, y);
        path.lineTo(1.6f * x, 0.4f * y);
        path.quadTo(x, 0.2f * y, 0.2f * x, 0.4f * y);
        path.lineTo(x, y);
        path.lineTo(1.44f * x, 1.36f * y);
        path.quadTo(x, 1.6f * y, 0.56f * x, 1.36f * y);
        path.lineTo(x, y);

        path.moveTo(x, y);
        path.lineTo(1.6f * x, 0.4f * y);
        path.quadTo(x, 0.2f * y, 0.2f * x, 0.4f * y);
        path.lineTo(x, y);
        path.lineTo(1.44f * x, 1.36f * y);
        path.quadTo(x, 1.6f * y, 0.56f * x, 1.36f * y);
        path.lineTo(x, y);

        path.moveTo(x, y);
        path.lineTo(1.6f * x, 0.4f * y);
        path.quadTo(x, 0.2f * y, 0.2f * x, 0.4f * y);
        path.lineTo(x, y);
        path.lineTo(1.44f * x, 1.36f * y);
        path.quadTo(x, 1.6f * y, 0.56f * x, 1.36f * y);
        path.lineTo(x, y);


        objectAnimator = ObjectAnimator.ofFloat(view, View.X, View.Y, path);
        objectAnimator.setDuration(20000);
        objectAnimator.setRepeatCount(20);
        objectAnimator.start();
        switchFlag = false;
        flag = true;
    }


    /**
     * This method define the animation of image.
     * Also, this method define the path of animation.
     * The animation is straight.
     * @param view
     */
    private void moveImageLine(ImageView view) {


        imageView.setVisibility(View.VISIBLE);

//        int[] location = new int[2];
//        imageView.getLocationInWindow(location);
//        x = location[0];
//        y = location[1];


        Log.d("xxx", Integer.toString(y));


        Path path = new Path();
        path.moveTo(x, y);
        path.lineTo(x, 0.2f * y);
        path.lineTo(x, y);
        path.lineTo(x, 1.6f * y);
        path.lineTo(x, y);
        path.lineTo(x, 0.2f * y);
        path.lineTo(x, y);
        path.lineTo(x, 1.6f * y);
        path.lineTo(x, y);
        path.lineTo(x, 0.2f * y);
        path.lineTo(x, y);
        path.lineTo(x, 1.6f * y);
        path.lineTo(x, y);





        objectAnimator = ObjectAnimator.ofFloat(view, View.X, View.Y, path);
        objectAnimator.setDuration(10000);
        objectAnimator.setRepeatCount(20);
        objectAnimator.start();
        switchFlag = true;
        flag = true;
    }

    /**
     * This method will control the switch between different models.
     */
    private void switchModel() {
        if (switchFlag == true) {
            if (flag == true) {
                stopAnimation();
            }
            buttonStop.setImageResource(R.drawable.model1);
            //imageView.setImageResource(R.drawable.images);
            button.setImageResource(R.drawable.stop2);
            moveImage(imageView);
        } else {
            stopAnimation();
            buttonStop.setImageResource(R.drawable.model2);
            //imageView.setImageResource(R.drawable.images);
            button.setImageResource(R.drawable.stop2);

            moveImageLine(imageView);
        }

    }


    /**
     * This method will let the image animation stop,
     * and put the image into original location.
     */
    private void stopAnimation() {
        if (flag == true) {
            imageView.clearAnimation();
            objectAnimator.end();
            objectAnimator.cancel();
            imageView.setVisibility(View.INVISIBLE);

            x = dpToPx(170);
            y = dpToPx(220);

//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
//            params.gravity = Gravity.CENTER;
//            imageView.setLayoutParams(params);
//            int[] location = new int[2];
//            imageView.getLocationInWindow(location);
//            x = location[0];
//            y = location[1];
            Log.d("aa", Integer.toString(y));

            flag = false;
        }
    }


    /**
     * This method will control the switch between different image.
     * The changing of image is based on the times of START button click.
     */
    private void imageChange() {
        switch(repeat % 10) {
            case 1:
            case 5: imageView.setImageResource(R.drawable.dog2);
                break;
            case 6:
            case 2: imageView.setImageResource(R.drawable.cat2);
                break;
            case 7:
            case 9:
            case 3: imageView.setImageResource(R.drawable.sun2);
                break;
            case 8:
            case 4: imageView.setImageResource(R.drawable.cake);
                break;
            default: imageView.setImageResource(R.drawable.cake);
                break;
        }

    }




}