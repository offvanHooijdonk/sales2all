package com.sales2all.android.ui.main;

import android.view.View;

/**
 * Created by Yahor_Fralou on 4/12/2016 16:30.
 */
public interface IMainActivityView {
    void popFragmentFromStack();
    boolean isStackEmpty();
    void exitApp();
    void displayFilterView();
    void collapseFilterView();
    void onSaleItemSelected(int position, Long saleId, View transitionView);
}
