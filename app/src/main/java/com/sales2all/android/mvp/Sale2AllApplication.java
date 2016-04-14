package com.sales2all.android.mvp;

import android.app.Application;
import android.content.Context;

import com.sales2all.android.mvp.components.DaggerISales2AllAppComponent;
import com.sales2all.android.mvp.components.ISales2AllAppComponent;

/**
 * Created by Yahor_Fralou on 4/12/2016.
 */
public class Sale2AllApplication extends Application {
    private ISales2AllAppComponent appComponent;

    public static Sale2AllApplication get(Context ctx) {
        return (Sale2AllApplication) ctx.getApplicationContext();
    }

    public ISales2AllAppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        buildGraphAndInject();
    }

    private void buildGraphAndInject() {
        appComponent = DaggerISales2AllAppComponent.builder()/*.sales2AllAppModule(new Sales2AllAppModule(this))*/.build();
        appComponent.inject(this);
    }
}
