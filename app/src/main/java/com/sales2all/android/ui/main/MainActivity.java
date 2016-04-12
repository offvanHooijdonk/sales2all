package com.sales2all.android.ui.main;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sales2all.android.R;
import com.sales2all.android.mvp.IHasComponent;
import com.sales2all.android.mvp.components.DaggerIMainActivityComponent;
import com.sales2all.android.mvp.components.IMainActivityComponent;
import com.sales2all.android.mvp.components.ISales2AllAppComponent;
import com.sales2all.android.mvp.modules.MainActivityModule;
import com.sales2all.android.presenter.main.MainActivityPresenterImpl;
import com.sales2all.android.ui.BaseActivity;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements IMainActivityView, IHasComponent<IMainActivityComponent> {

    @Inject
    MainActivityPresenterImpl presenter;

    private IMainActivityComponent mainActivityComponent;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getFragmentManager();

        MainActivityFragment mainActivityFragment = new MainActivityFragment();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, mainActivityFragment).commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setupComponent(ISales2AllAppComponent appComponent) {
        mainActivityComponent = DaggerIMainActivityComponent.builder()
                .iSales2AllAppComponent(appComponent)
                .mainActivityModule(new MainActivityModule(this)).build();

        mainActivityComponent.inject(this);
    }

    @Override
    public IMainActivityComponent getComponent() {
        return mainActivityComponent;
    }

    @Override
    public void popFragmentFromStack() {
        fragmentManager.popBackStack();
    }
}
