package com.qati.cats.ui.fragment;

import com.qati.cats.App;
import com.qati.cats.data.models.Cat;
import com.qati.cats.domain.presenter.FavouriteCatsPresenter;
import com.qati.cats.view.CatListView;

import javax.inject.Inject;

public class FavouriteCatsFragment extends BaseCatsFragment {

    @Inject FavouriteCatsPresenter presenter;

    @Override
    public void injectDI() {
        App.getDagger().getAppComponent().inject(this);
    }

    @Override
    public void init() {
        super.init();
        presenter.getFavorites();
        refreshLayout.setEnabled(false);
    }

    @Override
    public FavouriteCatsPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void processLikeClick(Cat cat, boolean isLiked, int pos) {
        presenter.onLikedClicked(cat, isLiked, pos);
    }

    @Override
    public int getCatListType() {
        return TYPE_FAV;
    }

}
