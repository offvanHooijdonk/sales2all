package com.sales2all.android.ui.saleslist;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sales2all.android.R;
import com.sales2all.android.helper.DBHelper;
import com.sales2all.android.model.SaleBean;
import com.sales2all.android.mvp.components.IMainActivityComponent;
import com.sales2all.android.presenter.main.IMainActivityPresenter;
import com.sales2all.android.presenter.saleslist.ISaleListPresenter;
import com.sales2all.android.ui.BaseFragment;
import com.sales2all.android.ui.main.MainActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yahor_Fralou on 4/13/2016 15:03.
 */
public class SalesListFragment extends BaseFragment implements ISalesListView, SalesListAdapter.OnSaleItemClickedListener {
    private static final int SPAN_MIN_SIZE = 240;

    @Inject
    ISaleListPresenter presenter;

    @Inject
    IMainActivityPresenter mainActivityPresenter;

    @Bind(R.id.listSales)
    RecyclerView recyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private Context ctx;

    private SalesListAdapter adapter;
    private List<SaleBean> salesList;

    public void addItemPickedListener() {

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
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ctx = getActivity();
        View v = super.onCreateView(inflater, container, savedInstanceState);
        if (v == null) {
            v = inflater.inflate(R.layout.fragment_sales_list, container, false);
        }
        ButterKnife.bind(this, v);

        swipeRefreshLayout.setColorSchemeResources(R.color.refresh_color_1, R.color.refresh_color_2, R.color.refresh_color_3);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(getGridSpanNumber(), StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);

        salesList = SaleBean.listAll(SaleBean.class);

        adapter = new SalesListAdapter(ctx, salesList, this);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    Activity a = SalesListFragment.this.getActivity();
                    if (a instanceof MainActivity) {
                        mainActivityPresenter.collapseFilter();
                    }
                }
            }
        });

        return v;
    }

    @Override
    public void startSaleView(int position, Long saleId, View transitionView) {
        mainActivityPresenter.onSaleItemSelected(position, saleId, transitionView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    private int getGridSpanNumber() {
        DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        float spanNumber = dpWidth / SPAN_MIN_SIZE;

        return (int) spanNumber;
    }

    @Override
    public void onSaleItemClicked(int position, Long saleId, View transitionView) {
        presenter.onSaleItemClicked(position, saleId, transitionView);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.recreate_db) {
            DBHelper.initData(ctx);
            Toast.makeText(ctx, "Database re-initiated", Toast.LENGTH_LONG).show();
            // TODO uncomment and use MainActivity capability of showing Snackbar
            //Snackbar.make(coordinator, "Database re-initiated", Snackbar.LENGTH_LONG).show();

            salesList.clear();
            //adapter.notifyDataSetChanged();
            salesList.addAll(SaleBean.listAll(SaleBean.class));
            adapter.notifyDataSetChanged();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
