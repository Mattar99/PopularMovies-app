package com.example.mattar.popularmovies_app.networkUtils.api;

/**
 * Created by Mattar on 5/11/2018.
 */

public interface MoviesApiCallback<T> {

    void onResponse(T result);

    void onCancel();
}
