package com.sales2all.android.presenter.main;

import com.sales2all.android.ui.main.IMainActivityView;

/**
 * Created by Yahor_Fralou on 4/12/2016 16:23.
 */
public class MainActivityPresenterImpl implements IMainActivityPresenter {

    private IMainActivityView view;

    public MainActivityPresenterImpl(IMainActivityView view) {
        this.view = view;
    }

    @Override
    public void onBackPressed() {
        view.popFragmentFromStack();
    }
}
