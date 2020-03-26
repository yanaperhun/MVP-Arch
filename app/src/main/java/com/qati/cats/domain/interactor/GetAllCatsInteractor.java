package com.qati.cats.domain.interactor;

import com.qati.cats.data.models.Cat;
import com.qati.cats.data.repository.ICatRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class GetAllCatsInteractor {

    private final ICatRepository repository;

    @Inject
    public GetAllCatsInteractor(ICatRepository catRepository) {
        this.repository = catRepository;
    }

    public Single<List<Cat>> getCats() {
        return repository.getAllCats();
    }
}
