package com.zyh.ZyhG1.network;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpHelper {
    public String post(String urlStr, String param) {
        try
        {
            OkHttpClient client = new OkHttpClient
                    .Builder()
                    .connectTimeout(10, TimeUnit.MINUTES)
                    .readTimeout(10, TimeUnit.MINUTES)
                    .writeTimeout(10, TimeUnit.MINUTES)
                    .build();
            RequestBody body = RequestBody.create(
                    param.getBytes(StandardCharsets.UTF_8),
                    MediaType.parse("application/json; charset=UTF-8"));
            Request request = new Request.Builder()
                    .url(urlStr)
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (response.body() != null) {
                    return response.body().string();
                }
                return "-1";
            }
        } catch (Exception ex) {
            return "-1";
        } finally {
        }
    }
    public String get(String urlStr) {
        try
        {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(urlStr)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (response.body() != null) {
                    return response.body().string();
                }
                return "-1";
            }
        } catch (Exception ex) {
            return "-1";
        } finally {
        }
    }
}
