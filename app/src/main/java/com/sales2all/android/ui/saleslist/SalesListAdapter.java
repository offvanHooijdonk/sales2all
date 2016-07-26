package com.sales2all.android.ui.saleslist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sales2all.android.R;
import com.sales2all.android.data.provider.ImageProvider;
import com.sales2all.android.helper.AnimationHelper;
import com.sales2all.android.helper.ColorHelper;
import com.sales2all.android.helper.FormatHelper;
import com.sales2all.android.model.SaleBean;
import com.sales2all.android.ui.views.AspectRatioImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yahor_Fralou on 4/13/2016 16:09.
 */
public class SalesListAdapter extends RecyclerView.Adapter<SalesListAdapter.ViewHolder> {
    /*private String[] items;*/
    private Context ctx;
    private OnSaleItemClickedListener listener;
    private List<SaleBean> items;

    public SalesListAdapter(Context context, List<SaleBean> items, OnSaleItemClickedListener l) {
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
    public void onBindViewHolder(final ViewHolder vh, final int position) {
        //String filePath = FSHelper.getPicturesPath(ctx) + items[position];
        SaleBean saleBean = items.get(position);
        vh.textName.setText(saleBean.getName());

        int discountColor = ColorHelper.getColorForDiscount(saleBean.getDiscount());

        vh.textDiscount.setText(String.valueOf(saleBean.getDiscount()));
        vh.textDiscount.setTextColor(discountColor);

        vh.textPrice.setText(FormatHelper.formatPrice(saleBean.getPriceDiscount()));

        vh.textPerCent.setTextColor(discountColor);

        final View transitionView = vh.imagePhoto;
        vh.imagePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onSaleItemClicked(position, transitionView);
                }
            }
        });

        ImageProvider.provideImageForSaleMain(ctx, saleBean, new ImageProvider.ImageProvideListener() {
            @Override
            public void onImageLoadStart() {
                SalesListAdapter.this.onImageLoadStart(vh);
            }
            @Override
            public void onImageReady(String imagePath) {
                setSaleImageFromFS(vh, imagePath);
            }
        });
    }

    private void onImageLoadStart(ViewHolder vh) {
        vh.progressImageLoad.setVisibility(View.VISIBLE);
    }

    private void setSaleImageFromFS(ViewHolder vh, String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            Drawable dr = Drawable.createFromPath(filePath);

            if (dr != null) {
                vh.imageStub.setVisibility(View.GONE);

                vh.imagePhoto.setVisibility(View.INVISIBLE);
                vh.imagePhoto.setImageDrawable(dr);
                vh.imagePhoto.setAspectRatio(((float) dr.getIntrinsicHeight()) / dr.getIntrinsicWidth());

                AnimationHelper.Fade.fade(vh.imagePhoto, true, null);
            }

            vh.progressImageLoad.setVisibility(View.GONE);
        }

        vh.progressImageLoad.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imagePhoto)
        AspectRatioImageView imagePhoto;
        @Bind(R.id.imageStub)
        ImageView imageStub;
        @Bind(R.id.progressImageLoad)
        ProgressBar progressImageLoad;
        @Bind(R.id.textDiscount)
        TextView textDiscount;
        @Bind(R.id.textName)
        TextView textName;
        @Bind(R.id.textPrice)
        TextView textPrice;
        @Bind(R.id.textPerCent)
        TextView textPerCent;

        public ViewHolder(View v) {
            super(v);

            ButterKnife.bind(ViewHolder.this, v);
        }
    }

    public interface OnSaleItemClickedListener {
        void onSaleItemClicked(int position, View transitionView);
    }

}
