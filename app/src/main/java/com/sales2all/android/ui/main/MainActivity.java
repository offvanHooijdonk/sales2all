package com.sales2all.android.ui.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.sales2all.android.R;
import com.sales2all.android.helper.AnimationHelper;
import com.sales2all.android.mvp.IHasComponent;
import com.sales2all.android.mvp.components.DaggerIMainActivityComponent;
import com.sales2all.android.mvp.components.IMainActivityComponent;
import com.sales2all.android.mvp.components.ISales2AllAppComponent;
import com.sales2all.android.mvp.modules.MainActivityModule;
import com.sales2all.android.presenter.main.IMainActivityPresenter;
import com.sales2all.android.ui.BaseActivity;
import com.sales2all.android.ui.preferences.PreferenceActivity;
import com.sales2all.android.ui.salesfilter.SalesFilterActivity;
import com.sales2all.android.ui.saleslist.SalesListFragment;
import com.sales2all.android.ui.saleview.SaleViewActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements IMainActivityView, IHasComponent<IMainActivityComponent>, NavigationView.OnNavigationItemSelectedListener {
    public static final String FRAG_TAG_SALES_LIST = "FRAG_TAG_SALES_LIST";
    public static final String FRAG_TAG_SALES_FILTER = "FRAG_TAG_SALES_FILTER";

    @Inject
    IMainActivityPresenter presenter;

    private IMainActivityComponent mainActivityComponent;
    private FragmentManager fragmentManager;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.navigation)
    NavigationView navigationView;
    @Bind(R.id.coordinator)
    CoordinatorLayout coordinator;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.container_filter)
    FrameLayout containerFilter;
    @Bind(R.id.reveal_placeholder)
    View revealPlaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        fragmentManager = getFragmentManager();

        navigationView.getMenu().performIdentifierAction(R.id.mi_sales_list, 0);
        navigationView.setCheckedItem(R.id.mi_sales_list);
    }

    @Override
    protected void onResume() {
        super.onResume();

        fab.show();
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
            this.startActivity(new Intent(this, PreferenceActivity.class));
            return true;
        } else if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawers();
        } else {
            finish();
        }
    }

    @OnClick(R.id.fab)
    public void onFABClicked() {
        AnimationHelper.Circle.revealViewWithFAB(revealPlaceHolder, fab, 2200, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                Intent intent = new Intent(MainActivity.this, SalesFilterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        revealPlaceHolder.setVisibility(View.INVISIBLE);
                    }
                }, 350);
            }
        });

        //presenter.onFilterCalled();
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
    public void startSaleViewActivity(int position, Long saleId, final View transitionView) {
        Intent intent = new Intent(MainActivity.this, SaleViewActivity.class);
        intent.putExtra(SaleViewActivity.EXTRA_SALE_ID, saleId);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                MainActivity.this, transitionView, MainActivity.this.getString(R.string.transition_sale_main_photo));
        startActivity(intent, options.toBundle());
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment;

        switch (item.getItemId()) {
            case R.id.mi_sales_list: {
                fragment = new SalesListFragment();
            } break;
            default: {
                fragment = new SalesListFragment();
            }
        }

        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, FRAG_TAG_SALES_LIST).commit();
        drawerLayout.closeDrawers();

        return true;
    }


}
