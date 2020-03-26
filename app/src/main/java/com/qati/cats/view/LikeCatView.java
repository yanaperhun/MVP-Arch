package com.qati.cats.view;

import com.qati.cats.data.models.Cat;

public interface LikeCatView {

    int TYPE_ALL = 0;
    int TYPE_FAV = 1;

    void processLikeClick(Cat cat, boolean isLiked, int pos);

    void updateLikedUI(Cat cat, boolean isLiked);

    int getCatListType();
}
