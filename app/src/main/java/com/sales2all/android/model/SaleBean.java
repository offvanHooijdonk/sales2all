package com.sales2all.android.model;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yahor_Fralou on 4/20/2016 12:10.
 */

public class SaleBean extends SugarRecord {
    private String name;
    private List<SaleImageBean> images = new ArrayList<>();
    private int discount;
    private float priceDiscount;
    private float priceOrigin;

    public SaleBean() {
    }

    public SaleBean(String name, int discount, float priceDiscount, float priceOrigin) {
        this.name = name;
        this.discount = discount;
        this.priceDiscount = priceDiscount;
        this.priceOrigin = priceOrigin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SaleImageBean> getImages() {
        if (images.isEmpty()) {
            return images = SaleImageBean.find(SaleImageBean.class, "sale_Id = ?", new String[]{String.valueOf(this.getId())}, null, "primary_Image desc, uri", null);
        } else {
            return images;
        }
    }

    public void setImages(List<SaleImageBean> images) {
        this.images = images;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public float getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(float priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    public float getPriceOrigin() {
        return priceOrigin;
    }

    public void setPriceOrigin(float priceOrigin) {
        this.priceOrigin = priceOrigin;
    }
}
