package com.qati.cats.data.source.api;

import com.qati.cats.data.models.Cat;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface CatServiceApi {

    @Headers("x-api-key: 1963752d-a92c-46b9-86c4-afbaa9af377e")
    @GET("v1/images/?limit=10&page=0&order=DESC")
    Flowable<List<Cat>> getCats();

}
