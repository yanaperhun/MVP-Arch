package com.qati.cats.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.qati.cats.data.source.api.RetrofitClient;
import com.qati.cats.data.source.ILocalSource;
import com.qati.cats.data.source.IRemoteSource;
import com.qati.cats.data.repository.CatRepositoryImpl;
import com.qati.cats.data.repository.ICatRepository;
import com.qati.cats.data.source.db.DBSource;
import com.qati.cats.domain.interactor.GetAllCatsInteractor;
import com.qati.cats.domain.interactor.GetFavouritesInteractor;
import com.qati.cats.domain.interactor.LikeCatInteractor;
import com.qati.cats.domain.interactor.RefreshCatsInteractor;
import com.qati.cats.domain.presenter.AllCatsPresenter;
import com.qati.cats.domain.presenter.FavouriteCatsPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    IRemoteSource provideRemoteSource() {
        return new RetrofitClient();
    }

    @Provides
    @Singleton
    ILocalSource provideLocalSource() {
        return Room.databaseBuilder(context,
                DBSource.class, "cats.db").build();
    }

    @Provides
    @Singleton
    ICatRepository provideCatRepositoryImpl(IRemoteSource remoteSource, ILocalSource localSource) {
        return new CatRepositoryImpl(remoteSource, localSource);
    }

    @Provides
    AllCatsPresenter provideAllCatPresenter(GetAllCatsInteractor i1, RefreshCatsInteractor i2, LikeCatInteractor i3) {
        return new AllCatsPresenter(i1, i2, i3);
    }

    @Provides
    FavouriteCatsPresenter provideFavoriteCatPresenter(GetFavouritesInteractor i1, LikeCatInteractor i2) {
        return new FavouriteCatsPresenter(i1, i2);
    }


}
