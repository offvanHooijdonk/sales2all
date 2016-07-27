package com.sales2all.android.data.provider;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.sales2all.android.data.loader.DownloadImageTask;
import com.sales2all.android.helper.FSHelper;
import com.sales2all.android.model.SaleBean;

import java.io.File;

/**
 * Created by Yahor_Fralou on 7/25/2016 15:50.
 */
public class ImageProvider {
    // TODO make use of SaleBean argument
    public static void provideImageForSaleMain(Context ctx, SaleBean saleBean, @NonNull final ImageProvideListener listener) {
        if (!saleBean.getImages().isEmpty()) {
            // TODO need to change default image object
            final String filePath = FSHelper.getPicturesPath(ctx) + saleBean.getImages().get(0).getFileName();
            File file = new File(filePath);
            if (file.exists()) {
                // For existing file just return the path
                listener.onImageReady(filePath);
            } else {
                // start loading task
                listener.onImageLoadStart();

                DownloadImageTask downloadImageTask = new DownloadImageTask(ctx, new DownloadImageTask.DownloadListener() {
                    @Override
                    public void onCompleted(String pathToImage) {
                        listener.onImageReady(filePath);
                    }
                });

                downloadImageTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, filePath);
            }
        } else {
            listener.onImageReady(null);
        }
    }

    public interface ImageProvideListener {
        void onImageLoadStart();

        void onImageReady(String imagePath);
    }
}
