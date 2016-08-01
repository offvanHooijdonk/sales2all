package com.sales2all.android.helper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

/**
 * Created by Yahor_Fralou on 4/14/2016 18:30.
 */
public class AnimationHelper {

    public static class Circle {
        private static final int DURATION_IN = 200;
        private static final int DURATION_OUT = 250;
        /**
         *
         * @param v      target view
         * @param viewCircleOn    FloatingActionButton to animate around
         */
        public static void revealViewWithFAB(@NonNull View v, @NonNull View viewCircleOn, @Nullable Integer radius, @Nullable AnimatorListenerAdapter listener) {
            int cx = getAnimCenterX((ViewGroup)v.getParent(), viewCircleOn);
            int cy = getAnimCenterY((ViewGroup)v.getParent(), viewCircleOn);

            v.setVisibility(View.INVISIBLE);
            int finalRadius = radius != null ? radius : getAnimRadius(v);

            Animator anim =
                    ViewAnimationUtils.createCircularReveal(v, cx, cy, viewCircleOn.getWidth()/2, finalRadius);
            anim.setDuration(DURATION_IN);

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
            anim.setDuration(DURATION_OUT);

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
        public static final int DURATION_FAST = 100;
        public static final int DURATION_LONGER = 200;

        public static void fade(@NonNull final View v, final boolean animateIn, int duration, @Nullable final Animation.AnimationListener listener) {
            float start = animateIn ? 0.0f : 1.0f ;
            float end = animateIn ? 1.0f : 0.0f ;
            Animation anim = new AlphaAnimation(start, end);
            anim.setDuration(duration);
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

    public static class ActivityReveal {
        private static final int DURATION_REVEAL = 300;

        /**
         * Place in onResume() method
         * @param ctx
         * @param revealView
         * @param toolbar
         * @param mainLayout
         */
        public static void revealToToolbar(@NonNull Context ctx, @NonNull final View revealView, @NonNull final Toolbar toolbar, @NonNull final ViewGroup mainLayout) {
            prepareRevealToToolbar(revealView, toolbar, mainLayout);

            TypedValue tv = new TypedValue();
            int actionBarHeight = 0;
            if (ctx.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, ctx.getResources().getDisplayMetrics());
            }

            ScaleAnimation anim = new ScaleAnimation(1.0f, 1.0f,
                    1.0f, (float) actionBarHeight / ctx.getResources().getDisplayMetrics().heightPixels);

            anim.setDuration(DURATION_REVEAL);
            anim.setInterpolator(new LinearOutSlowInInterpolator());
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    finishRevealToToolBar(revealView, toolbar, mainLayout);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

            revealView.startAnimation(anim);
        }

        private static void prepareRevealToToolbar(@NonNull View revealView, @NonNull Toolbar toolbar, @NonNull ViewGroup mainLayout) {
            toolbar.setVisibility(View.GONE);
            mainLayout.setVisibility(View.INVISIBLE);
            revealView.setVisibility(View.VISIBLE);
        }

        private static void finishRevealToToolBar(@NonNull View revealView, @NonNull Toolbar toolbar, @NonNull ViewGroup mainLayout) {
            toolbar.setVisibility(View.VISIBLE);

            AnimationHelper.Fade.fade(revealView, false, AnimationHelper.Fade.DURATION_LONGER, null);

            AnimationHelper.Fade.fade(mainLayout, true, AnimationHelper.Fade.DURATION_LONGER, null);
        }
    }
}
