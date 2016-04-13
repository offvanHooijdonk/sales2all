package com.sales2all.android.ui.saleslist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sales2all.android.R;
import com.sales2all.android.mvp.components.IMainActivityComponent;
import com.sales2all.android.presenter.saleslist.ISaleListPresenter;
import com.sales2all.android.ui.BaseFragment;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yahor_Fralou on 4/13/2016 15:03.
 */
public class SalesListFragment extends BaseFragment implements ISalesListView {
    @Inject
    ISaleListPresenter presenter;

    @Bind(R.id.listSales)
    RecyclerView recyclerView;

    private Context ctx;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.getComponent(IMainActivityComponent.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ctx = getActivity();
        View v = inflater.inflate(R.layout.fragment_sales_list, container, false);
        ButterKnife.bind(this, v);

        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        SalesListAdapter adapter = new SalesListAdapter(ctx, ctx.getResources().getStringArray(R.array.sales_names));
        recyclerView.setAdapter(adapter);
        recyclerView.setVerticalScrollBarEnabled(true);
        recyclerView.setScrollbarFadingEnabled(true);

        return v;
    }
}
