package com.sales2all.android.mvp.components;

import com.sales2all.android.mvp.ActivityScope;
import com.sales2all.android.mvp.modules.MainActivityModule;
import com.sales2all.android.ui.main.MainActivity;
import com.sales2all.android.ui.salesfilter.SalesFilterFragment;
import com.sales2all.android.ui.saleslist.SalesListFragment;

import dagger.Component;

/**
 * Created by Yahor_Fralou on 4/12/2016 16:53.
 */
@ActivityScope
@Component(
        dependencies = ISales2AllAppComponent.class,
        modules = MainActivityModule.class
)
public interface IMainActivityComponent {
    void inject(MainActivity activity);
    void inject(SalesListFragment fragment);
    void inject(SalesFilterFragment fragment);
}
