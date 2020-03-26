package com.qati.cats.data.repository;

import android.text.TextUtils;

import com.qati.cats.data.models.Cat;
import com.qati.cats.data.source.ILocalSource;
import com.qati.cats.data.source.IRemoteSource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class CatRepositoryImpl implements ICatRepository {

    private static final String TAG = "CatRepositoryImpl";

    private IRemoteSource remoteSource;
    private ILocalSource dbSource;

    private List<Cat> allCats = new ArrayList<>();
    private List<Cat> favouriteCats = new ArrayList<>();

    private Single<List<Cat>> allCatsRemoteSource;
    private Single<List<Cat>> favoritesCatsDBSource;

    public CatRepositoryImpl(IRemoteSource remoteSource, ILocalSource dbSource) {
        this.remoteSource = remoteSource;
        this.dbSource = dbSource;
        initSource();
    }

    private void initSource() {
        allCatsRemoteSource = remoteSource.getCats()
                .map(cats -> {
                    Iterator<Cat> it = cats.iterator();
                    while (it.hasNext()) {
                        Cat currentCat = it.next();
                        if (favouriteCats.contains(currentCat)) {
                            currentCat.setFavourite(true);
                        }
                        if (TextUtils.isEmpty(currentCat.getUrl())) {
                            it.remove();
                        }
                    }
                    return cats;
                })
                .doOnSuccess(cats -> CatRepositoryImpl.this.allCats = cats);


        favoritesCatsDBSource = dbSource.getCats()
                .doOnSuccess(cats -> {
                    CatRepositoryImpl.this.favouriteCats = cats;
                    for (Cat cat : allCats) {
                        if (favouriteCats.contains(cat)) {
                            cat.setFavourite(true);
                        }
                    }
                });
    }


    @Override
    public Single<List<Cat>> getAllCats() {
        return Maybe.just(allCats)
                .filter(cats -> !cats.isEmpty())
                .switchIfEmpty(allCatsRemoteSource);
    }

    @Override
    public Single<List<Cat>> getFavorites() {
        return Maybe.just(favouriteCats)
                .filter(cats -> !cats.isEmpty())
                .switchIfEmpty(favoritesCatsDBSource);
    }

    @Override
    public Single<List<Cat>> refreshAllCats() {
        return allCatsRemoteSource;
    }

    @Override
    public void addToFavourite(Cat cat) {
        favouriteCats.add(cat);

        Single.fromCallable(() -> {
            dbSource.saveCat(cat);
            return cat;
        })
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Override
    public void removeFromFavourite(Cat cat) {

        favouriteCats.remove(cat);
        allCats.get(allCats.indexOf(cat)).setFavourite(false);

        Single.fromCallable(() -> {
            dbSource.removeCat(cat);
            return cat;
        })
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
