package com.sales2all.android.helper;

import android.content.Context;

import com.sales2all.android.R;
import com.sales2all.android.model.SaleBean;
import com.sales2all.android.model.SaleImageBean;

import java.util.UUID;

/**
 * Created by Yahor_Fralou on 4/20/2016 15:04.
 */
public class DBHelper {
    public static void initData(Context ctx) {
        SaleImageBean.deleteAll(SaleImageBean.class);
        SaleBean.deleteAll(SaleBean.class);

        String[] names = ctx.getResources().getStringArray(R.array.sales_names);
        int position = 0;
        for (String name : names) {
            addSale(position, name, Math.round((float) Math.random() * 40) + 10, Math.round((float) Math.random() * 200) + 10 - 0.05f);
            position++;
        }
    }

    private static void addSale(int position, String name, int discount, float priceDiscount) {
        SaleBean sale = new SaleBean(name, discount, priceDiscount, Math.round(discount * priceDiscount) - 0.05f);
        sale.save();
        String imageUri = UUID.randomUUID().toString() + ".jpg";
        SaleImageBean saleImage = new SaleImageBean(sale.getId(), true, imageUri);
        saleImage.save();

        sale.getImages().add(saleImage);
        sale.save();
    }
}
