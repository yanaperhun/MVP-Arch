package com.qati.cats.di.components;


import com.qati.cats.di.modules.AppModule;
import com.qati.cats.ui.fragment.AllCatsFragment;
import com.qati.cats.ui.fragment.FavouriteCatsFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {


    void inject(AllCatsFragment allCatsFragment);

    void inject(FavouriteCatsFragment favouriteCatsFragment);

}