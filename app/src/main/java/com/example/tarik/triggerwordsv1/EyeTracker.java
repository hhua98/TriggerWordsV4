package com.example.tarik.triggerwordsv1;

/**
 * Created by huanghe on 1/04/2017.
 */

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class EyeTracker extends Activity {

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
     * This method controls the animation.
     * It can stop the animation and let the image back to its original location
     */
    View.OnClickListener myOnClickListenerStop = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonStop:
                    stopAnimation();
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
    private void moveImage(final View view) {
        // float x = view.getX();
        // float y = view.getY();
        Path path =  new Path();

        path.moveTo(480, 700);
        path.lineTo(780, 300);
        path.quadTo(480, 75, 180, 300);
        path.lineTo(480, 700);
        path.lineTo(780, 1100);
        path.quadTo(480, 1325, 180, 1100);
        path.lineTo(480, 700);

        path.moveTo(480, 700);
        path.lineTo(780, 300);
        path.quadTo(480, 75, 180, 300);
        path.lineTo(480, 700);
        path.lineTo(780, 1100);
        path.quadTo(480, 1325, 180, 1100);
        path.lineTo(480, 700);

        path.moveTo(480, 700);
        path.lineTo(780, 300);
        path.quadTo(480, 75, 180, 300);
        path.lineTo(480, 700);
        path.lineTo(780, 1100);
        path.quadTo(480, 1325, 180, 1100);
        path.lineTo(480, 700);


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
    private void moveImageLine(final View view) {
        Path path = new Path();
        path.moveTo(480, 700);
        path.lineTo(480, 75);
        path.lineTo(480, 1325);
        path.lineTo(480, 75);
        path.lineTo(480, 1325);
        path.lineTo(480, 700);
        path.moveTo(480, 700);
        path.lineTo(480, 75);
        path.lineTo(480, 1325);
        path.lineTo(480, 75);
        path.lineTo(480, 1325);
        path.lineTo(480, 700);



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
                objectAnimator.cancel();
            }
            button.setImageResource(R.drawable.cycle2);
            imageView.setImageResource(R.drawable.images);
            moveImage(imageView);
        } else {
            objectAnimator.cancel();
            button.setImageResource(R.drawable.change2);
            imageView.setImageResource(R.drawable.images);
            moveImageLine(imageView);
        }

    }


    /**
     * This method will let the image animation stop,
     * and put the image into original location.
     */
    private void stopAnimation() {
        if (flag == true) {
            Log.d("aa", "aaaa");
            objectAnimator.cancel();
            imageView.setX(480);
            imageView.setY(700);
            button.setImageResource(R.drawable.start2);
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
            case 5: imageView.setImageResource(R.drawable.cat2);
                break;
            case 6:
            case 2: imageView.setImageResource(R.drawable.dog2);
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