package com.sales2all.android.mvp.modules;

import android.app.Application;

import com.sales2all.android.mvp.Sale2AllApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Yahor_Fralou on 4/12/2016.
 */
@Module
public class Sales2AllAppModule {
    private final Sale2AllApplication application;

    public Sales2AllAppModule(Sale2AllApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }
}
