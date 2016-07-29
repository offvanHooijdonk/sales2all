package com.sales2all.android.presenter.main;

import android.view.View;

import com.sales2all.android.ui.main.IMainActivityView;

import javax.inject.Inject;

/**
 * Created by Yahor_Fralou on 4/12/2016 16:23.
 */
public class MainActivityPresenterImpl implements IMainActivityPresenter {

    private IMainActivityView view;

    @Inject
    public MainActivityPresenterImpl(IMainActivityView view) {
        this.view = view;
    }

    @Override
    public void onSaleItemSelected(int position, Long saleId, View transitionView) {
        view.startSaleViewActivity(position, saleId, transitionView);
    }
}
