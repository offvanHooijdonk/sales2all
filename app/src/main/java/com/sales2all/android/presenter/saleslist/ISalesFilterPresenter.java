package com.sales2all.android.presenter.saleslist;

import com.sales2all.android.ui.saleslist.ISalesFilterView;

/**
 * Created by Yahor_Fralou on 4/14/2016 12:16.
 */
public interface ISalesFilterPresenter {
    void init(ISalesFilterView view);
    void collapseFilter();
    void expandFilter();
}
