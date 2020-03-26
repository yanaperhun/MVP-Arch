package com.qati.cats.domain.presenter;

import com.qati.cats.data.models.Cat;
import com.qati.cats.domain.interactor.GetAllCatsInteractor;
import com.qati.cats.domain.interactor.LikeCatInteractor;
import com.qati.cats.domain.interactor.RefreshCatsInteractor;
import com.qati.cats.view.CatListView;

public class AllCatsPresenter extends BasePresenter<CatListView> {

    private final GetAllCatsInteractor allCatsInteractor;
    private final RefreshCatsInteractor refreshAllCatsInteractor;
    private final LikeCatInteractor likeCatInteractor;

    public AllCatsPresenter(GetAllCatsInteractor i1, RefreshCatsInteractor i2, LikeCatInteractor i3) {

        this.allCatsInteractor = i1;
        this.refreshAllCatsInteractor = i2;
        this.likeCatInteractor = i3;

    }

    public void getCats() {
        compositeDisposable.add(
                applyStandard(allCatsInteractor.getCats())
                        .subscribe(getView()::onCatsLoaded, getView()::onError));
    }

    public void refresh() {
        compositeDisposable.add(
                applyStandard(refreshAllCatsInteractor.refresh())
                        .subscribe(getView()::onCatsLoaded, getView()::onError));
    }

    public void onLikedClicked(Cat cat, boolean isLiked, int posInList) {
        likeCatInteractor.like(cat, isLiked, posInList);
    }
}
