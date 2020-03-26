package com.qati.cats.data.source;

import com.qati.cats.data.models.Cat;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public interface IRemoteSource {
    Single<List<Cat>> getCats();
}
