package com.sales2all.android.mvp.components;

import com.sales2all.android.mvp.Sale2AllApplication;
import com.sales2all.android.mvp.modules.Sales2AllAppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Yahor_Fralou on 4/12/2016.
 */
@Singleton
@Component(modules = {Sales2AllAppModule.class})
public interface ISales2AllAppComponent {

    void inject(Sale2AllApplication app);
}
