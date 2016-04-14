package com.sales2all.android.mvp.modules;

import com.sales2all.android.presenter.main.IMainActivityPresenter;
import com.sales2all.android.presenter.main.MainActivityPresenterImpl;
import com.sales2all.android.presenter.saleslist.ISaleListPresenter;
import com.sales2all.android.presenter.saleslist.ISalesFilterPresenter;
import com.sales2all.android.presenter.saleslist.SalesFilterPresenterImpl;
import com.sales2all.android.presenter.saleslist.SalesListPresenterImpl;
import com.sales2all.android.ui.main.IMainActivityView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Yahor_Fralou on 4/12/2016 16:57.
 */
@Module
public class MainActivityModule {

    private IMainActivityView view;

    public MainActivityModule(IMainActivityView view) {
        this.view = view;
    }

    @Provides
    public IMainActivityView provideView() {
        return view;
    }

    @Provides
    public IMainActivityPresenter provideMainActivityPresenter() {
        return new MainActivityPresenterImpl(view);
    }

    @Provides
    public ISaleListPresenter provideSalesListPresenter() {
        return new SalesListPresenterImpl();
    }

    @Provides
    public ISalesFilterPresenter provideSalesFilterPresenter() {
        return new SalesFilterPresenterImpl();
    }
}
