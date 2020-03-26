package com.qati.cats.view;

import com.qati.cats.data.models.Cat;

import java.util.List;

public interface CatListView extends BaseView {

    void onCatsLoaded(List<Cat> cats);

}
