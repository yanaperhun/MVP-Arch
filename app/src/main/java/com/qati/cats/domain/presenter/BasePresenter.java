package com.qati.cats.domain.presenter;

import com.qati.cats.view.BaseView;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public abstract  class BasePresenter<V extends BaseView> {
    private V  view;

    protected CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void onCreate(V view) {
        this.view = view;
        compositeDisposable = new CompositeDisposable();
    }

    public void onDestroy() {
        compositeDisposable.dispose();
    }

    public V getView() {
        return view;
    }

    public <S> Single<S> applyStandard(Single<S> single) {

        return single
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription -> getView().onLoadingEnable(true))
                .doAfterTerminate(() -> getView().onLoadingEnable(false));
    }
}