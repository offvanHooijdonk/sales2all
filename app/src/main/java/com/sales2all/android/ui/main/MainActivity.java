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
import com.sales2all.android.presenter.main.IMainActivityPresenter;
import com.sales2all.android.ui.BaseActivity;
import com.sales2all.android.ui.saleslist.SalesListFragment;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements IMainActivityView, IHasComponent<IMainActivityComponent> {

    @Inject
    IMainActivityPresenter presenter;

    private IMainActivityComponent mainActivityComponent;
    private FragmentManager fragmentManager;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.fab) FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        fragmentManager = getFragmentManager();

        SalesListFragment salesListFragment = new SalesListFragment();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, salesListFragment).commit();

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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
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
