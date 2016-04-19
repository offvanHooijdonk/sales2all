package com.sales2all.android.ui.salesfilter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.sales2all.android.R;
import com.sales2all.android.helper.AnimationHelper;
import com.sales2all.android.mvp.components.IMainActivityComponent;
import com.sales2all.android.presenter.main.IMainActivityPresenter;
import com.sales2all.android.presenter.salesfilter.ISalesFilterPresenter;
import com.sales2all.android.ui.BaseFragment;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Yahor_Fralou on 4/14/2016 12:16.
 */
public class SalesFilterFragment extends BaseFragment implements ISalesFilterView {
    @Inject
    ISalesFilterPresenter presenter;
    @Inject
    IMainActivityPresenter mainActivityPresenter;

    private Context ctx;

    @Bind(R.id.btnApply)
    ImageButton btnApply;

    @Bind(R.id.layoutContainer)
    ViewGroup layoutContainer;

    private FloatingActionButton viewToCircleOn;

    public SalesFilterFragment() {
        super();
    }

    public void setViewToCircleOn(FloatingActionButton viewToCircleOn) {
        this.viewToCircleOn = viewToCircleOn;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.getComponent(IMainActivityComponent.class).inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.init(this);

        presenter.expandFilter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ctx = getActivity();
        View v = super.onCreateView(inflater, container, savedInstanceState);
        if (v == null) {
            v = inflater.inflate(R.layout.fragment_sales_list_filter, container, false);
        }

        ButterKnife.bind(this, v);

        v.setVisibility(View.INVISIBLE);

        return v;
    }

    @OnClick(R.id.btnApply)
    public void onFilterApply() {
        presenter.collapseFilter();
    }

    @Override
    public void expandFilter() {
        final View v = getView();
        assert v != null;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimationHelper.Circle.revealViewWithFAB(v, viewToCircleOn, new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        AnimationHelper.Fade.fade(layoutContainer, true);
                    }
                });
            }
        }, 50);
    }

    @Override
    public void collapseFilter() {
        // Assume if this fragment is shown - it is on top
        assert getView() != null;

        AnimationHelper.Fade.fade(layoutContainer, false);
        AnimationHelper.Circle.hideViewWithFAB(getView(), viewToCircleOn, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mainActivityPresenter.onBackPressed();
                //getActivity().getFragmentManager().popBackStackForce();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }
}
