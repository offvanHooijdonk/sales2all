package com.sales2all.android.data.loader;

import com.sales2all.android.model.SaleBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Yahor_Fralou on 8/4/2016 19:11.
 */
public interface SaleService {
    @GET("/sale")
    Call<List<SaleBean>> getAll();
}
