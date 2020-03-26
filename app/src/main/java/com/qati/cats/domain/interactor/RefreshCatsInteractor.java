package com.qati.cats.domain.interactor;

import com.qati.cats.data.models.Cat;
import com.qati.cats.data.repository.ICatRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class RefreshCatsInteractor {

    private final ICatRepository repository;

    @Inject
    public RefreshCatsInteractor(ICatRepository repository) {
        this.repository = repository;
    }

    public Single<List<Cat>> refresh() {
        return repository.refreshAllCats();
    }
}
