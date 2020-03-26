package com.qati.cats.di;

import android.content.Context;

import com.qati.cats.di.components.AppComponent;
import com.qati.cats.di.components.DaggerAppComponent;
import com.qati.cats.di.modules.AppModule;

public class ComponentManager {

    private AppComponent appComponent;

    public void init(Context context) {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(context))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

}