package com.qati.cats.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.qati.cats.domain.presenter.BasePresenter;
import com.qati.cats.view.BaseView;

public abstract class MvpFragment<V extends BaseView> extends Fragment {

    private Toast toast;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDI();
        getPresenter().onCreate(getMvpView());
        if (!(this instanceof BaseView)) {
            throw new ClassCastException("MvpFragment must implement BaseView");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPresenter().onDestroy();
        clearDI();
    }


    private V getMvpView() {
        return (V) this;
    }

    public abstract BasePresenter<V> getPresenter();

    public abstract void injectDI();

    public void clearDI() {
    }

    public void showErrorMessage(String error) {
        if (toast != null) toast.cancel();
        toast = Toast.makeText(getContext(), error, Toast.LENGTH_SHORT);
        toast.show();
    }


}
