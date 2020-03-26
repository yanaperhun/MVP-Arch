package com.qati.cats.data.source.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qati.cats.data.source.IRemoteSource;
import com.qati.cats.data.models.Cat;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient implements IRemoteSource {

    private CatServiceApi service;

    @Inject
    public RetrofitClient() {
        Gson gson = new Gson();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
//                .addInterceptor(new ErrorInterceptor())
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConst.URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(CatServiceApi.class);
    }


    @Override
    public Single<List<Cat>> getCats() {
        return service.getCats().firstOrError();
    }
}
