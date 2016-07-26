package com.sales2all.android.helper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * Created by Yahor_Fralou on 4/14/2016 18:30.
 */
public class AnimationHelper {
    public static final int DELAY_CIRCULAR_IN = 250;
    public static final int DELAY_CIRCULAR_OUT = 250;

    public static final int DELAY_FADE_IN = 100;

    public static class Circle {
        /**
         *
         * @param v      target view
         * @param viewCircleOn    FloatingActionButton to animate around
         */
        public static void revealViewWithFAB(@NonNull View v, @NonNull View viewCircleOn, @Nullable AnimatorListenerAdapter listener) {
            int cx = getAnimCenterX((ViewGroup)v.getParent(), viewCircleOn);
            int cy = getAnimCenterY((ViewGroup)v.getParent(), viewCircleOn);

            int finalRadius = getAnimRadius(v);

            Animator anim =
                    ViewAnimationUtils.createCircularReveal(v, cx, cy, viewCircleOn.getWidth()/2, finalRadius);
            anim.setDuration(DELAY_CIRCULAR_IN);

            if (listener != null) {
                anim.addListener(listener);
            }

            v.setVisibility(View.VISIBLE);

            if (viewCircleOn instanceof FloatingActionButton) {
                ((FloatingActionButton) viewCircleOn).hide();
            }
            anim.start();
        }

        /**
         *
         * @param v      target view
         * @param viewCircleOn    FloatingActionButton to animate around
         */
        public static void hideViewWithFAB(@NonNull final View v, @NonNull View viewCircleOn, @Nullable AnimatorListenerAdapter listener) {
            int cx = getAnimCenterX((ViewGroup)v.getParent(), viewCircleOn);
            int cy = getAnimCenterY((ViewGroup)v.getParent(), viewCircleOn);

            int initialRadius = getAnimRadius(v);

            Animator anim = ViewAnimationUtils.createCircularReveal(v, cx, cy, initialRadius, viewCircleOn.getWidth()/2);
            anim.setDuration(DELAY_CIRCULAR_OUT);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    v.setVisibility(View.INVISIBLE);
                }
            });

            if (listener != null) {
                anim.addListener(listener);
            }

            anim.start();
            if (viewCircleOn instanceof FloatingActionButton) {
                ((FloatingActionButton) viewCircleOn).show();
            }
        }

        private static int getAnimRadius(View v) {
            return Math.max(v.getWidth(), v.getHeight());
        }

        private static int getAnimCenterX(View v, View viewCircleOn) {
            return viewCircleOn.getLeft() - v.getLeft() + viewCircleOn.getWidth()/2;
        }

        private static int getAnimCenterY(View v, View viewCircleOn) {
            return viewCircleOn.getTop() - v.getTop() + viewCircleOn.getHeight()/2;
        }
    }

    public static class Fade {
        public static void fade(@NonNull final View v, final boolean animateIn, @Nullable final Animation.AnimationListener listener) {
            float start = animateIn ? 0.0f : 1.0f ;
            float end = animateIn ? 1.0f : 0.0f ;
            Animation anim = new AlphaAnimation(start, end);
            anim.setDuration(DELAY_FADE_IN);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (animateIn) {
                        v.setVisibility(View.VISIBLE);
                    } else {
                        v.setVisibility(View.INVISIBLE);
                    }

                    if (listener != null) {
                        listener.onAnimationEnd(animation);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            v.startAnimation(anim);
        }
    }
}
