package com.sales2all.android.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.sales2all.android.mvp.components.ISales2AllAppComponent;
import com.sales2all.android.mvp.Sale2AllApplication;

/**
 * Created by Yahor_Fralou on 4/12/2016 15:32 15:34.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        setupComponent(Sale2AllApplication.get(this).getAppComponent());
    }

    protected abstract void setupComponent(ISales2AllAppComponent appComponent);
}
