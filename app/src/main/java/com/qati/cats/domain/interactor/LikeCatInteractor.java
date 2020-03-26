package com.qati.cats.domain.interactor;

import com.qati.cats.data.models.Cat;
import com.qati.cats.data.repository.ICatRepository;

import javax.inject.Inject;

public class LikeCatInteractor {

    private final ICatRepository rep;

    @Inject
    public LikeCatInteractor(ICatRepository repository) {
        this.rep = repository;
    }

    public void like(Cat cat, boolean isLiked, int posInList) {
        cat.setFavourite(isLiked);
        if (isLiked) {
            rep.addToFavourite(cat);
        } else {
            rep.removeFromFavourite(cat);
        }
    }
}
