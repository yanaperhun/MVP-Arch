package com.qati.cats.domain.presenter;

import com.qati.cats.data.models.Cat;
import com.qati.cats.domain.interactor.GetFavouritesInteractor;
import com.qati.cats.domain.interactor.LikeCatInteractor;
import com.qati.cats.view.CatListView;

public class FavouriteCatsPresenter extends BasePresenter<CatListView> {

    private final GetFavouritesInteractor favouritesInteractor;
    private final LikeCatInteractor likedInteractor;

    public FavouriteCatsPresenter(GetFavouritesInteractor i1, LikeCatInteractor i2) {
        this.favouritesInteractor = i1;
        this.likedInteractor = i2;
    }

    public void getFavorites() {
        compositeDisposable.add(applyStandard(
                favouritesInteractor.getCats())
                .subscribe(getView()::onCatsLoaded, getView()::onError));
    }

    public void onLikedClicked(Cat cat, boolean isLiked, int posInList) {
        likedInteractor.like(cat, isLiked, posInList);
    }
}
