package com.qati.cats.data.source;

import com.qati.cats.data.models.Cat;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public interface ILocalSource {

    Single<List<Cat>> getCats();
    void saveCat(Cat cat);
    void removeCat(Cat cat);
}
