package com.sales2all.android.presenter.saleslist;

import com.sales2all.android.presenter.main.IMainActivityPresenter;
import com.sales2all.android.ui.saleslist.ISalesFilterView;

import javax.inject.Inject;

/**
 * Created by Yahor_Fralou on 4/14/2016 12:17.
 */
public class SalesFilterPresenterImpl implements ISalesFilterPresenter {
    @Inject
    ISalesFilterView view;

    @Inject
    IMainActivityPresenter mainActivityPresenter;

    @Override
    public void applyFilter() {
        mainActivityPresenter.applyFilter();
    }
}
