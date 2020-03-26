package com.qati.cats.data.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Cat {
    @PrimaryKey
    @NonNull
    public String id;
    public String url;
    public boolean isFavourite;

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Cat && id.equals(((Cat) obj).id);
    }

    @Override
    public String toString() {
        String format = "id : %s; is fav : %s; ";
        return String.format(format, id, isFavourite);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }


}
