package com.example.tarik.triggerwordsv1.Information;

/**
 * Created by huanghe on 26/03/2017.
 */


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.FloatMath;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.tarik.triggerwordsv1.R;
import com.squareup.picasso.Picasso;

import java.util.Calendar;


public class statistical extends Fragment implements View.OnTouchListener {
    View myView;
    private static final String TAG = "Touch";
    @SuppressWarnings("unused")
    private static final float MIN_ZOOM = 1f,MAX_ZOOM = 1f;
    private static final long DOUBLE_CLICK_TIME_DELTA = 300;//milliseconds

    long lastClickTime = 0;

    // These matrices will be used to scale points of the image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // The 3 states (events) which the user is trying to perform
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    private long lastTouchDown;
    private int deltaX;
    private int deltaY;
    private float initialTouchX;
    private float initialTouchY;
    private boolean isMoved;
    private int lastTouchX;
    private int lastTouchY;


    // these PointF objects are used to record the point(s) the user is touching
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    private RadioButton rb2;
    private RadioButton rb6;
    private RadioButton rb3;
    private RadioButton rb4;
    private RadioButton rb5;
    private RadioButton rb1;
    private RadioGroup rb;
    private ImageView image;
    private Animator mCurrentAnimator;
    private ImageView expand;
    private int CLICK_ACTION_THRESHHOLD = 400;
    private float startX;
    private float startY;
    private final int MAX_CLICK_DURATION = 200;
    private final int MAX_CLICK_DISTANCE = 3;
    private long startClickTime;
    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private float dx;
    private float dy;

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private int mShortAnimationDuration;
    Animation zoomin, zoomout;


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
        image.setImageResource(R.drawable.dyslexialine);
        expand=(ImageView)myView.findViewById(R.id.expanded_image);
        expand.setOnTouchListener(this);

        /*zoomin = AnimationUtils.loadAnimation(this.getContext(), R.anim.zoomin);
        zoomout = AnimationUtils.loadAnimation(this.getContext(), R.anim.zoomout);
        image.setAnimation(zoomin);
        image.setAnimation(zoomout);
        zoomin.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                image.startAnimation(zoomout);

            }
        });
        zoomout.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                image.startAnimation(zoomin);

            }
        });
*/
        rb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId==rb1.getId()){
                    delayer();
                    Picasso.with(getActivity()).load(R.drawable.compare_this_three).into(image);}
                    //image.setImageResource(R.drawable.compare_this_three);}
                else if(checkedId==rb6.getId()){
                    delayer();
                    Picasso.with(getActivity()).load(R.drawable.compare_with_others).into(image);}

                    //image.setImageResource(R.drawable.compare_with_others);}
                else if(checkedId==rb3.getId()){
                    delayer();
                    Picasso.with(getActivity()).load(R.drawable.dyscalculia).into(image);}
                    //image.setImageResource(R.drawable.dyscalculia);}
                else if(checkedId==rb4.getId()){
                    delayer();
                    Picasso.with(getActivity()).load(R.drawable.multiple_learning_difficulties).into(image);}
                    //image.setImageResource(R.drawable.multiple_learning_difficulties);}
                else if(checkedId==rb5.getId()){
                    delayer();
                    Picasso.with(getActivity()).load(R.drawable.moderate_learning_difficulty).into(image);}
                    //image.setImageResource(R.drawable.moderate_learning_difficulty);}
                else{
                    //image.setImageResource(R.drawable.dyslexialine);
                Picasso.with(getActivity()).load(R.drawable.dyslexialine).into(image);}

                /*Toast.makeText(getBaseContext(), selectedId+"", Toast.LENGTH_SHORT).show();*/
            }
        });

        final View thumb1View = myView.findViewById(R.id.imageView3);

        final ImageView expandedImageView = (ImageView) myView.findViewById(
                R.id.expanded_image);

        thumb1View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(rb1.isChecked()==true)

                    zoomImageFromThumb(thumb1View, R.drawable.compare_this_three);

                else if(rb6.isChecked()==true)

                    zoomImageFromThumb(thumb1View, R.drawable.compare_with_others);
                else if(rb3.isChecked()==true)
                    zoomImageFromThumb(thumb1View, R.drawable.dyscalculia);
                else if(rb4.isChecked()==true)
                    zoomImageFromThumb(thumb1View, R.drawable.multiple_learning_difficulties);
                else if(rb5.isChecked()==true)
                    zoomImageFromThumb(thumb1View, R.drawable.moderate_learning_difficulty);
                else
                zoomImageFromThumb(thumb1View, R.drawable.dyslexialine);

            }
        });

        // Retrieve and cache the system's default "short" animation time.
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);




        return myView;
    }
    @Override
    public boolean onTouch(final View v, MotionEvent event)
    {


        ImageView view = (ImageView) v;
        view.setScaleType(ImageView.ScaleType.MATRIX);
        float scale;


        dumpEvent(event);
        // Handle touch events here...

        switch (event.getAction() & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_DOWN:   // first finger down only
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                Log.d(TAG, "mode=DRAG"); // write to LogCat
                mode = DRAG;
                startX = event.getX();
                startY = event.getY();


                break;
            case MotionEvent.ACTION_UP:


                /*float endX = event.getX();
                float endY = event.getY();
                if (isAClick(startX, endX, startY, endY)) {



                }*/
                break;


                // first finger lifted

            case MotionEvent.ACTION_POINTER_UP: // second finger lifted

                mode = NONE;
                Log.d(TAG, "mode=NONE");
                break;

            case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down

                oldDist = spacing(event);
                Log.d(TAG, "oldDist=" + oldDist);
                if (oldDist > 4f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                    Log.d(TAG, "mode=ZOOM");
                }
                break;

            case MotionEvent.ACTION_MOVE:


                if (mode == DRAG)
                {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y); // create the transformation in the matrix  of points
                }
                else if (mode == ZOOM)
                {
                    // pinch zooming
                    float newDist = spacing(event);
                    Log.d(TAG, "newDist=" + newDist);
                    if (newDist > 5f)
                    {
                        matrix.set(savedMatrix);
                        scale = newDist / oldDist; // setting the scaling of the
                        // matrix...if scale > 1 means
                        // zoom in...if scale < 1 means
                        // zoom out
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;


        }
        view.setImageMatrix(matrix);
        v.getParent().requestDisallowInterceptTouchEvent(true); //specific to my project
        return false; //specific to my project
    }

   /* private boolean isAClick(float startX, float endX, float startY, float endY) {
        float differenceX = Math.abs(startX - endX);
        float differenceY = Math.abs(startY - endY);
        if (differenceX > CLICK_ACTION_THRESHHOLD || differenceY > CLICK_ACTION_THRESHHOLD) {
            return false;
        }


         // display the transformation on screen

        return true; // indicate event was handled
    }

    /*
     * --------------------------------------------------------------------------
     * Method: spacing Parameters: MotionEvent Returns: float Description:
     * checks the spacing between the two fingers on touch
     * ----------------------------------------------------
     */

    public void delayer() {
        try {
            Thread.sleep(800);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    private void translate(View expandedImageView  , View thumb1View) {
        expandedImageView.animate()
                .x(thumb1View.getX())
                .y(thumb1View.getY())
                .setDuration(1000)
                .start();
        //fromView.setVisibility(View.INVISIBLE);
        //thumb1View.setVisibility(View.VISIBLE);
    }
    private float spacing(MotionEvent event)
    {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float)Math.sqrt(x * x + y * y);
    }

    /*
     * --------------------------------------------------------------------------
     * Method: midPoint Parameters: PointF object, MotionEvent Returns: void
     * Description: calculates the midpoint between the two fingers
     * ------------------------------------------------------------
     */

    private void midPoint(PointF point, MotionEvent event)
    {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /** Show an event in the LogCat view, for debugging */
    private void dumpEvent(MotionEvent event)
    {
        String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE","POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);

        if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP)
        {
            sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }

        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++)
        {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }

        sb.append("]");
        Log.d("Touch Events ---------", sb.toString());
    }
    private void zoomImageFromThumb(final View thumbView, int imageResId) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) myView.findViewById(
                R.id.expanded_image);
        expandedImageView.setImageResource(imageResId);


        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        myView.findViewById(R.id.container)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                translate(expandedImageView,thumbView);

                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
                return true;
            }
        });
    }


}
