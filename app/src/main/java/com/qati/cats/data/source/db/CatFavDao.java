package com.qati.cats.data.source.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.qati.cats.data.models.Cat;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface CatFavDao {

    @Query("SELECT * FROM cat")
    Single<List<Cat>> getAll();

    @Query("DELETE FROM cat")
    void clearAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addToFav(Cat cat);

    @Delete
    void deleteCatFromFav(Cat cat);
}
