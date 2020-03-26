package com.qati.cats.data.source.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.qati.cats.data.models.Cat;
import com.qati.cats.data.source.ILocalSource;

import java.util.List;

import io.reactivex.Single;

@Database(entities = {Cat.class}, version = 1)
public abstract class DBSource extends RoomDatabase implements ILocalSource {
    public abstract CatFavDao catFavDao();

    @Override
    public Single<List<Cat>> getCats() {
        return catFavDao().getAll();
    }

    @Override
    public void saveCat(Cat cat) {
        catFavDao().addToFav(cat);
    }

    @Override
    public void removeCat(Cat cat) {
        catFavDao().deleteCatFromFav(cat);
    }
}
