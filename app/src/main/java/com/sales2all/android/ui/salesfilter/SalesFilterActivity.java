package com.sales2all.android.ui.salesfilter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.sales2all.android.R;
import com.sales2all.android.helper.AnimationHelper;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yahor_Fralou on 7/29/2016 16:22.
 */
public class SalesFilterActivity extends AppCompatActivity {

    private Context ctx;
    private int actionBarHeight;

    @Bind(R.id.mainLayout)
    ViewGroup mainLayout;
    @Bind(R.id.collapsePlaceholder)
    View collapsePlaceholder;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_filter);

        ctx = SalesFilterActivity.this;
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }

        // TODO start this activity with delay after Reveal Anim _start_ . Also start this Anim with a delay
        prepareToAnim();
        ScaleAnimation anim = new ScaleAnimation(1.0f, 1.0f,
                1.0f, (float) actionBarHeight / getResources().getDisplayMetrics().heightPixels);

        anim.setDuration(750);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                onBarCollapseEnded();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        collapsePlaceholder.startAnimation(anim);
    }

    private void prepareToAnim() {
        /*assert getSupportActionBar() != null;
        getSupportActionBar().hide();*/
        toolbar.setVisibility(View.GONE);
        mainLayout.setVisibility(View.INVISIBLE);
        collapsePlaceholder.setVisibility(View.VISIBLE);
    }

    private void onBarCollapseEnded() {
        getSupportActionBar().show();

        AnimationHelper.Fade.fade(collapsePlaceholder, false, null);
        //AnimationHelper.Fade.fade(toolbar, true, null);
        AnimationHelper.Fade.fade(mainLayout, true, null);
        //mainLayout.setVisibility(View.VISIBLE);
    }
}
