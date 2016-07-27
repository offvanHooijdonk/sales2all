package com.sales2all.android.presenter.saleslist;

import android.view.View;

import com.sales2all.android.ui.saleslist.ISalesListView;

import javax.inject.Inject;

/**
 * Created by Yahor_Fralou on 4/13/2016 15:05.
 */
public class SalesListPresenterImpl implements ISaleListPresenter {

    @Inject
    ISalesListView view;

    @Override
    public void onSaleItemClicked(int position, Long saleId, View transitionView) {
        view.startSaleView(position, saleId, transitionView);
    }

    @Override
    public void init(ISalesListView view) {
        this.view = view;
    }
}
