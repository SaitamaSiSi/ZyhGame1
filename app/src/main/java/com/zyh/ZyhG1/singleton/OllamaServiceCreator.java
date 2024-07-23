package com.zyh.ZyhG1.singleton;

import com.zyh.ZyhG1.network.OkHttpHelper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OllamaServiceCreator {
    private static final String BASE_URL = "http://192.168.100.198:11434/";
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpHelper.getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build();;

    public static <T>T create(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
