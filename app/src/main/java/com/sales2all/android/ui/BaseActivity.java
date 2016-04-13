package com.sales2all.android.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sales2all.android.mvp.Sale2AllApplication;
import com.sales2all.android.mvp.components.ISales2AllAppComponent;

/**
 * Created by Yahor_Fralou on 4/12/2016 15:32 15:34.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupComponent(Sale2AllApplication.get(this).getAppComponent());
    }

    protected abstract void setupComponent(ISales2AllAppComponent appComponent);
}
