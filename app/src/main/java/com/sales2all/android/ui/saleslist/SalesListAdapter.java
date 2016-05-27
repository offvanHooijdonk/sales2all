package com.sales2all.android.ui.saleslist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sales2all.android.R;
import com.sales2all.android.helper.FSHelper;
import com.sales2all.android.ui.views.AspectRatioImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yahor_Fralou on 4/13/2016 16:09.
 */
public class SalesListAdapter extends RecyclerView.Adapter<SalesListAdapter.ViewHolder> {
    private String[] items;
    private Context ctx;
    private OnSaleItemClickedListener listener;

    public SalesListAdapter(Context context, String[] items, OnSaleItemClickedListener l) {
        this.ctx = context;
        this.items = items;
        this.listener = l;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_sale, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        String filePath = FSHelper.getPicturesPath(ctx) + items[position];
        vh.textName.setText(items[position]);

        /*int resImage = ((position * position * 5) % 6) > 2 ? R.drawable.sample_photo : R.drawable.sample_photo_coins;
        Drawable dr = ctx.getDrawable(resImage);
        assert dr != null;
        vh.imagePhoto.setImageDrawable(dr);
        vh.imagePhoto.setAspectRatio(((float) dr.getIntrinsicHeight()) / dr.getIntrinsicWidth());*/
        final View transitionView = vh.imagePhoto;

        vh.imagePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onSaleItemClicked(position, transitionView);
                }
            }
        });
    }

    private void setImageFromFS(ViewHolder vh, String filePath) {
        Drawable dr = Drawable.createFromPath(filePath);

        assert dr != null;
        vh.imagePhoto.setImageDrawable(dr);
        vh.imagePhoto.setAspectRatio(((float) dr.getIntrinsicHeight()) / dr.getIntrinsicWidth());
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imagePhoto)
        AspectRatioImageView imagePhoto;
        @Bind(R.id.textDiscount)
        TextView textDiscount;
        @Bind(R.id.textName)
        TextView textName;
        @Bind(R.id.textPrice)
        TextView textPrice;

        public ViewHolder(View v) {
            super(v);

            ButterKnife.bind(ViewHolder.this, v);
        }
    }

    public interface OnSaleItemClickedListener {
        void onSaleItemClicked(int position, View transitionView);
    }

    private class DownloadImage extends AsyncTask<String, Void, Boolean> {
        private static final int BATCH_SIZE = 4096;
        private static final String HOST_URL = "http://lorempixel.com/";
        private static final int IMAGE_WIDTH = 640;
        private static final int IMAGE_HEIGHT = 480;
        private static final int IMAGE_HEIGHT_NARROW = 360;

        private ViewHolder vh;

        public DownloadImage(ViewHolder vh) {
            this.vh = vh;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String fileSavePath = params[0];

            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;

            int height = (Math.random() * 2) > 1 ? IMAGE_HEIGHT : IMAGE_HEIGHT_NARROW;
            String urlString = new StringBuilder(HOST_URL).append(IMAGE_WIDTH).append("/").append(height).append("/technics").toString();
            try {
                URL url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Toast.makeText(ctx, "Response code" + connection.getResponseCode() + "returned!", Toast.LENGTH_LONG).show();
                    return false;
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
                        return false;
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

                if (connection != null)
                    connection.disconnect();
            }


            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);


        }
    }
}
