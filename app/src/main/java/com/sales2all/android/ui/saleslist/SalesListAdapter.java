package com.sales2all.android.ui.saleslist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sales2all.android.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Yahor_Fralou on 4/13/2016 16:09.
 */
public class SalesListAdapter extends RecyclerView.Adapter<SalesListAdapter.ViewHolder> {
    private String[] items;
    private Context ctx;

    public SalesListAdapter(Context context, String[] items) {
        this.ctx = context;
        this.items = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_sale, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        vh.textName.setText(items[position]);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
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
}
