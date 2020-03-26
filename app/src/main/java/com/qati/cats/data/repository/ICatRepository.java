package com.qati.cats.data.repository;

import com.qati.cats.data.models.Cat;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public interface ICatRepository {

    Single<List<Cat>> getAllCats();
    Single<List<Cat>> getFavorites();
    Single<List<Cat>> refreshAllCats();

    void addToFavourite(Cat cat);
    void removeFromFavourite(Cat cat);
}
