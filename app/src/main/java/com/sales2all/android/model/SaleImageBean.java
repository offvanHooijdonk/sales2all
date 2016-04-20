package com.sales2all.android.model;

import com.orm.SugarRecord;

/**
 * Created by Yahor_Fralou on 4/20/2016 12:20.
 */
public class SaleImageBean extends SugarRecord {
    private long saleId;
    private boolean primaryImage;
    private String uri;

    public SaleImageBean() {
    }

    public SaleImageBean(long saleId, boolean primaryImage, String uri) {
        this.saleId = saleId;
        this.primaryImage = primaryImage;
        this.uri = uri;
    }

    public long getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public boolean isPrimaryImage() {
        return primaryImage;
    }

    public void setPrimaryImage(boolean primaryImage) {
        this.primaryImage = primaryImage;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
