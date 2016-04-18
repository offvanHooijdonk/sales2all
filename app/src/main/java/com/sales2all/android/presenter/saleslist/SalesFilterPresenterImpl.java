package com.sales2all.android.presenter.saleslist;

import com.sales2all.android.ui.saleslist.ISalesFilterView;

import javax.inject.Inject;

/**
 * Created by Yahor_Fralou on 4/14/2016 12:17.
 */
public class SalesFilterPresenterImpl implements ISalesFilterPresenter {
    private ISalesFilterView view;

    @Inject
    public SalesFilterPresenterImpl() {
    }

    @Override
    public void init(ISalesFilterView salesFilterView) {
        this.view = salesFilterView;
    }

    @Override
    public void collapseFilter() {
        view.collapseFilter();
    }

    @Override
    public void expandFilter() {
        view.expandFilter();
    }
}
