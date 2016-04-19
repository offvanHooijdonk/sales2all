package com.sales2all.android.ui.saleslist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sales2all.android.R;
import com.sales2all.android.ui.views.AspectRatioImageView;

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
        vh.textName.setText(items[position]);

        int resImage = ((position * position * 5) % 6) > 2 ? R.drawable.sample_photo : R.drawable.sample_photo_coins;
        Drawable dr = ctx.getDrawable(resImage);
        assert dr != null;
        vh.imagePhoto.setImageDrawable(dr);
        vh.imagePhoto.setAspectRatio(((float) dr.getIntrinsicHeight()) / dr.getIntrinsicWidth());
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
}
