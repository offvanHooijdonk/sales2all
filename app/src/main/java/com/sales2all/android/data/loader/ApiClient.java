package com.sales2all.android.data.loader;

import android.content.Context;

import com.sales2all.android.helper.PreferenceHelper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Yahor_Fralou on 7/26/2016 11:56.
 */
public class ApiClient {
    public static String baseUrl;
    private static Retrofit retrofit = null;

    public static void readBaseUrl(Context ctx) {
        baseUrl = PreferenceHelper.getServerUrl(ctx);
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
