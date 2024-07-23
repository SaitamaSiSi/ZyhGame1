package com.zyh.ZyhG1.network;

import com.zyh.ZyhG1.model.OllamaRequest;
import com.zyh.ZyhG1.model.OllamaResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface AppService {
    // 仅示例
    @GET("Login")
    @Headers("Content-Type: application/json;charset=UTF-8")
    Call<OllamaResponse> example(@Query("u") String user, @Query("t") String token);

    @GET
    @Headers("Content-Type: application/json;charset=UTF-8")
    Call<ResponseBody> getData(@Url String endpoint);

    @POST("api/{path}")
    @Headers("Content-Type: application/json;charset=UTF-8")
    Call<OllamaResponse> getOllamaData(@Path("path") String path, @Body OllamaRequest request);

}
