package com.sales2all.android.helper;

import java.text.NumberFormat;

/**
 * Created by Yahor_Fralou on 7/26/2016 14:58.
 */
public class FormatHelper {
    private static final NumberFormat PRICE_FORMAT = NumberFormat.getCurrencyInstance();

    public static String formatPrice(float price) {
        return PRICE_FORMAT.format(price);
    }
}
