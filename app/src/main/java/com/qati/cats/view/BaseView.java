package com.qati.cats.view;

public interface BaseView {

    void onError(Throwable error);

    void onLoadingEnable(boolean isEnable);
}
