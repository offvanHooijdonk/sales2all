package com.sales2all.android.ui;

import android.app.Fragment;

import com.sales2all.android.mvp.IHasComponent;

/**
 * Created by Yahor_Fralou on 4/12/2016 15:46.
 */
public abstract class BaseFragment extends Fragment {
    @SuppressWarnings("unchecked")
    protected <T> T getComponent(Class<T> componentType) {
        return componentType.cast(((IHasComponent<T>) getActivity()).getComponent());
    }
}

