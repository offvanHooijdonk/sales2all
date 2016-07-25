package com.sales2all.android.data.loader;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
        String fileSavePath = params[0];

        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;

        int height = (Math.random() * 2) > 1 ? IMAGE_HEIGHT : IMAGE_HEIGHT_NARROW;
        String urlString = HOST_URL + IMAGE_WIDTH + "/" + height + "/technics";
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Toast.makeText(ctx, "Response code" + connection.getResponseCode() + "returned!", Toast.LENGTH_LONG).show();
                return null;
            }

            input = connection.getInputStream();
            File f = new File(fileSavePath);
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();

            output = new FileOutputStream(f);

            byte data[] = new byte[BATCH_SIZE];
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling on exit
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null) {
                connection.disconnect();
            }
        }


        return fileSavePath;
    }

    @Override
    protected void onPostExecute(String filePath) {
        super.onPostExecute(filePath);

        listener.onCompleted(filePath);
    }

    public interface DownloadListener {
        void onCompleted(String pathToImage);
    }
}
