package com.qati.cats.domain.interactor;

import com.qati.cats.data.models.Cat;
import com.qati.cats.data.repository.ICatRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class GetFavouritesInteractor {

    private final ICatRepository rep;

    @Inject
    public GetFavouritesInteractor(ICatRepository repository) {
        this.rep = repository;
    }

    public Single<List<Cat>> getCats() {
        return rep.getFavorites();
    }

}
