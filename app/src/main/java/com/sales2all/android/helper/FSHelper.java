package com.sales2all.android.helper;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.Nullable;

import java.io.File;

/**
 * Created by Yahor_Fralou on 4/20/2016 17:10.
 */
public class FSHelper {

    @Nullable
    public static String getPicturesPath(Context ctx) {
        String path;
        File picturesDir = ctx.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (picturesDir != null) {
            path = picturesDir.getAbsolutePath();
        } else {
            path = null;
        }
        return path;
    }
}
