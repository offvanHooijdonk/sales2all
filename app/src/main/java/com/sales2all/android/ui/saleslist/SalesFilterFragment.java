package com.sales2all.android.ui.saleslist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.sales2all.android.R;
import com.sales2all.android.helper.AnimationHelper;
import com.sales2all.android.mvp.components.IMainActivityComponent;
import com.sales2all.android.presenter.saleslist.ISalesFilterPresenter;
import com.sales2all.android.ui.BaseFragment;
import com.sales2all.android.ui.main.MainActivity;

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

    private Context ctx;

    @Bind(R.id.btnApply)
    ImageButton btnApply;

    private FloatingActionButton fab;

    public SalesFilterFragment() {
        super();
    }

    public SalesFilterFragment(FloatingActionButton fab) {
        super();

        this.fab = fab;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.getComponent(IMainActivityComponent.class).inject(this);
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

        v.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                AnimationHelper.FAB.revealViewWithFAB(v, fab);
            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        });

        return v;
    }

    @OnClick(R.id.btnApply)
    public void onFilterApply() {
        getActivity().getFragmentManager().popBackStack(MainActivity.FRAG_TAG_SALES_FILTER, 0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }
}
