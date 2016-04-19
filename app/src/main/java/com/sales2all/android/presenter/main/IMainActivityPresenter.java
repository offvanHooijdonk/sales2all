package com.sales2all.android.presenter.main;

import android.view.View;

/**
 * Created by Yahor_Fralou on 4/12/2016 16:22.
 */
public interface IMainActivityPresenter {
    void onBackPressed();
    void collapseFilter();
    void onSaleItemSelected(int position, View transitionView);
    void onFilterCalled();
}
