package com.sales2all.android.ui.salesfilter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.sales2all.android.R;
import com.sales2all.android.helper.AnimationHelper;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yahor_Fralou on 7/29/2016 16:22.
 */
public class SalesFilterActivity extends AppCompatActivity {

    private Context ctx;
    private boolean isRevealCalled = false;

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

        if (!isRevealCalled) {
            AnimationHelper.ActivityReveal.revealToToolbar(this, collapsePlaceholder, toolbar, mainLayout);
            isRevealCalled = true;
        }
    }

}
