package com.qati.cats.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;

import com.qati.cats.App;
import com.qati.cats.data.models.Cat;
import com.qati.cats.domain.presenter.AllCatsPresenter;
import com.qati.cats.view.CatListView;

import javax.inject.Inject;

public class AllCatsFragment extends BaseCatsFragment implements SwipeRefreshLayout.OnRefreshListener {

    @Inject AllCatsPresenter presenter;

    @Override
    public void init() {
        super.init();
        presenter.getCats();
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void processLikeClick(Cat cat, boolean isLiked, int pos) {
        presenter.onLikedClicked(cat, isLiked, pos);
    }

    @Override
    public int getCatListType() {
        return TYPE_ALL;
    }

    @Override
    public AllCatsPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void injectDI() {
        App.getDagger().getAppComponent().inject(this);
    }

    @Override
    public void clearDI() {
        // TODO: 14/10/2018 clear sub components
    }

    @Override
    public void onLoadingEnable(boolean isEnable) {
        super.onLoadingEnable(isEnable);
        refreshLayout.setEnabled(!isEnable);
        if (!isEnable) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
        presenter.refresh();
        refreshLayout.setRefreshing(true);
    }
}
