package com.sales2all.android.data.loader;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

/**
 * Created by Yahor_Fralou on 7/26/2016 11:54.
 */
public interface FileDownloadService {
    @Streaming
    @GET("/sale/{saleId}/image/{fileName}")
    Call<ResponseBody> downloadFile(@Path("saleId") Long saleId, @Path("fileName") String fileName);

}
