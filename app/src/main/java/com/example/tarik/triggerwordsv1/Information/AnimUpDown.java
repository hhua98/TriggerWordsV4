package com.example.tarik.triggerwordsv1.Information;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.tarik.triggerwordsv1.R;

/**
 * Created by Tarik on 09-Apr-17.
 */

public class AnimUpDown {

    public static void slide_down(Context ctx, View v) {
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.activity_slidedown);
        if (a != null) {
            a.reset();
            if (v != null) {
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }
}
