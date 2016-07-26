package com.sales2all.android.helper;

import android.content.Context;

import com.sales2all.android.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yahor_Fralou on 7/26/2016 15:02.
 */
public class ColorHelper {
    private static final Map<Integer, Integer> DISCOUNT_COLORS = new HashMap<>();
    private static Integer[] DISCOUNT_KEYS_SORTED;

    public static void initDiscountColors(Context ctx) {
        String[] discInfoList = ctx.getResources().getStringArray(R.array.discount_colors);

        for (String discString : discInfoList) {
            String[] splitted = discString.split("[|]");
            Integer discountValue = Integer.valueOf(splitted[0]);
            int discountColorId = ctx.getResources().getIdentifier(splitted[1], "color", ctx.getPackageName());
            Integer discountColor = ctx.getResources().getColor(discountColorId);

            DISCOUNT_COLORS.put(discountValue, discountColor);
        }

        DISCOUNT_KEYS_SORTED = new Integer[DISCOUNT_COLORS.keySet().size()];
        DISCOUNT_COLORS.keySet().toArray(DISCOUNT_KEYS_SORTED);
        Arrays.sort(DISCOUNT_KEYS_SORTED);
    }

    public static int getColorForDiscount(int discountAmount) {
        int colorPrev = DISCOUNT_COLORS.get(DISCOUNT_KEYS_SORTED[0]);
        for (int discKey: DISCOUNT_KEYS_SORTED) {
            if (discountAmount < discKey) {
                break;
            }
            colorPrev = DISCOUNT_COLORS.get(discKey);
        }

        return colorPrev;
    }
}
