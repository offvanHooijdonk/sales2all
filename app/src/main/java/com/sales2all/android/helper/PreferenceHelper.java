package com.sales2all.android.helper;

import android.content.Context;
import android.preference.PreferenceManager;

import com.sales2all.android.R;

/**
 * Created by Yahor_Fralou on 8/4/2016 18:48.
 */
public class PreferenceHelper {
    public static String getServerUrl(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getString(ctx.getString(R.string.prefServerKey), null);
    }
}
