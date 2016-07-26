package com.sales2all.android.presenter.saleslist;

import android.view.View;

import com.sales2all.android.ui.saleslist.ISalesListView;

/**
 * Created by Yahor_Fralou on 4/13/2016 15:05.
 */
public interface ISaleListPresenter {

    void onSaleItemClicked(int position, Long saleId, View transitionView);

    void init(ISalesListView view);
}
