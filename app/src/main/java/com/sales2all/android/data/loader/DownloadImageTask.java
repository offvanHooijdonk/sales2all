package com.sales2all.android.data.loader;

import android.content.Context;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Yahor_Fralou on 7/25/2016 16:23.
 */
public class DownloadImageTask extends AsyncTask<String, Void, String> {
    private static final int BATCH_SIZE = 4096;
    private static final String HOST_URL = "http://lorempixel.com/";
    private static final int IMAGE_WIDTH = 640;
    private static final int IMAGE_HEIGHT = 480;
    private static final int IMAGE_HEIGHT_NARROW = 360;

    private Context ctx;
    private DownloadListener listener;

    public DownloadImageTask(Context ctx, DownloadListener l) {
        this.ctx = ctx;
        this.listener = l;
    }

    @Override
    protected String doInBackground(String... params) {
        final String fileSavePath = params[0];

        int height = (Math.random() * 2) > 1 ? IMAGE_HEIGHT : IMAGE_HEIGHT_NARROW;
        String urlString = HOST_URL + IMAGE_WIDTH + "/" + height + "/technics";

        FileDownloadService downloadService = ApiClient.getClient().create(FileDownloadService.class);
        Call<ResponseBody> call = downloadService.downloadFile(1L, urlString);
        Response<ResponseBody> response;
        try {
            response = call.execute();
        } catch (IOException e) {
            //Toast.makeText(ctx, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            return null;
        }

        saveToFile(fileSavePath, response.body().byteStream());

        return fileSavePath;
    }

    @Override
    protected void onPostExecute(String filePath) {
        super.onPostExecute(filePath);

        listener.onCompleted(filePath);
    }

    private void saveToFile(String filePath, InputStream is) {
        OutputStream output = null;

        try {
            File f = new File(filePath);
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();

            output = new FileOutputStream(f);

            byte data[] = new byte[BATCH_SIZE];
            int count;
            while ((count = is.read(data)) != -1) {
                // allow canceling on exit
                if (isCancelled()) {
                    is.close();
                }
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException ignored) {
            }
        }
    }

    public interface DownloadListener {
        void onCompleted(String pathToImage);
    }
}
