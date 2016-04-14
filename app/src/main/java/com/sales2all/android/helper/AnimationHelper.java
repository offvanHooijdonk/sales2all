package com.sales2all.android.helper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * Created by Yahor_Fralou on 4/14/2016 18:30.
 */
public class AnimationHelper {

    public static class FAB {
        /**
         *
         * @param v      target view
         * @param fab    FloatingActionButton to animate around
         */
        public static void revealViewWithFAB(@NonNull View v, @NonNull FloatingActionButton fab) {
            int cx = getAnimCenterX(v, fab);
            int cy = getAnimCenterY(v, fab);

            int finalRadius = getAnimRadius(v);

            Animator anim =
                    ViewAnimationUtils.createCircularReveal(v, cx, cy, fab.getWidth()/2, finalRadius);
            anim.setDuration(500);

            v.setVisibility(View.VISIBLE);

            fab.hide();
            anim.start();
        }

        /**
         *
         * @param v      target view
         * @param fab    FloatingActionButton to animate around
         */
        public static void hideViewWithFAB(@NonNull final View v, @NonNull FloatingActionButton fab) {
            int cx = getAnimCenterX(v, fab);
            int cy = getAnimCenterY(v, fab);

            int initialRadius = getAnimRadius(v);

            Animator anim = ViewAnimationUtils.createCircularReveal(v, cx, cy, initialRadius, fab.getWidth()/2);
            anim.setDuration(300);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    v.setVisibility(View.INVISIBLE);
                }
            });

            anim.start();
            fab.show();
        }

        private static int getAnimRadius(View v) {
            return Math.max(v.getWidth(), v.getHeight());
        }

        private static int getAnimCenterX(View v, FloatingActionButton fab) {
            return fab.getLeft() - v.getLeft() + fab.getWidth()/2;
        }

        private static int getAnimCenterY(View v, FloatingActionButton fab) {
            return fab.getTop() - v.getTop() + fab.getHeight()/2;
        }
    }

}
