package com.uny.crysdip.network;

import android.content.Context;
import android.util.Log;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.List;

import retrofit.BaseUrl;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;


/**
 * Created by root on 30/01/16.
 */
public class CrysdipService {
    private RetrofitApi retrofitApi;

    public CrysdipService(final Context context){
        BaseUrl baseUrl = new BaseUrl() {
            @Override
            public HttpUrl url() {
                String url = "use api address";
                return HttpUrl.parse(url);
            }
        };

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        OkHttpClient client = new OkHttpClient();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("RetrofitLog", message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.interceptors().add(loggingInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        retrofitApi = retrofit.create(RetrofitApi.class);
    }




    private interface  RetrofitApi{
        @GET("/utilities/cities")
        Call<GenericResponse> listLocation();
    }

    private static class GenericResponse {
        String status;
        JsonElement data;
        String message;
    }


}
