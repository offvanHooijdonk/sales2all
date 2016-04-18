package com.sales2all.android.ui.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.sales2all.android.R;
import com.sales2all.android.mvp.IHasComponent;
import com.sales2all.android.mvp.components.DaggerIMainActivityComponent;
import com.sales2all.android.mvp.components.IMainActivityComponent;
import com.sales2all.android.mvp.components.ISales2AllAppComponent;
import com.sales2all.android.mvp.modules.MainActivityModule;
import com.sales2all.android.presenter.main.IMainActivityPresenter;
import com.sales2all.android.ui.BaseActivity;
import com.sales2all.android.ui.saleslist.ISalesFilterView;
import com.sales2all.android.ui.saleslist.SalesFilterFragment;
import com.sales2all.android.ui.saleslist.SalesListFragment;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements IMainActivityView, IHasComponent<IMainActivityComponent>, FragmentManager.OnBackStackChangedListener {
    public static final String FRAG_TAG_SALES_LIST = "FRAG_TAG_SALES_LIST";
    public static final String FRAG_TAG_SALES_FILTER = "FRAG_TAG_SALES_FILTER";

    @Inject
    IMainActivityPresenter presenter;

    private IMainActivityComponent mainActivityComponent;
    private FragmentManager fragmentManager;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.container_filter)
    FrameLayout containerFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        fragmentManager = getFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);

        fragmentManager.beginTransaction().replace(R.id.fragment_container, new SalesListFragment(), FRAG_TAG_SALES_LIST).commit();

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

    @OnClick(R.id.fab)
    public void onFABClicked() {
        presenter.onFilterCalled();
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
        Fragment frFilter = fragmentManager.findFragmentByTag(FRAG_TAG_SALES_FILTER);
        if (frFilter != null && frFilter.isVisible()) {
            ((ISalesFilterView) frFilter).collapseFilter();
        } else {
            fragmentManager.popBackStack();
        }
    }

    @Override
    public void collapseFilterView() {
        Fragment frFilter = fragmentManager.findFragmentByTag(FRAG_TAG_SALES_FILTER);
        if (frFilter != null) {
            ((ISalesFilterView) frFilter).collapseFilter();
        }
    }

    @Override
    public boolean isStackEmpty() {
        return fragmentManager.getBackStackEntryCount() == 0;
    }

    @Override
    public void exitApp() {
        finish();
    }

    @Override
    public void displayFilterView() {
        //fab.hide();
        SalesFilterFragment fr = new SalesFilterFragment();
        fr.setViewToCircleOn(fab);
        fragmentManager.beginTransaction().replace(R.id.container_filter, fr, FRAG_TAG_SALES_FILTER)
                .addToBackStack(FRAG_TAG_SALES_FILTER).commit();

        //revealView(fr.getView());
    }

    /*private boolean isFABToBeDisplayed() {
        boolean result = true;

        return result;
    }*/

    @Override
    public void onBackStackChanged() {
        /*if (isFABToBeDisplayed()) {
            if (!fab.isShown()) {
                fab.show();
            }
        } else {
            if (fab.isShown()) {
                //fab.hide();
            }
        }*/

        /*Fragment frFilter =  fragmentManager.findFragmentByTag(FRAG_TAG_SALES_FILTER);
        if (frFilter != null && frFilter.isVisible()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    AnimationHelper.Circle.revealViewWithFAB(containerFilter, fab);
                }
            }, 50);
        } else {
            //containerFilter.setVisibility(View.INVISIBLE);
        }*/
    }

}
