package com.example.mattar.popularmovies_app.networkUtils.api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.mattar.popularmovies_app.EventBusMessages.MessageResponseEvent;
import com.example.mattar.popularmovies_app.models.MainResponse;
import com.example.mattar.popularmovies_app.networkUtils.Constants;

import org.greenrobot.eventbus.EventBus;

import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mattar on 5/9/2018.
 */

// Efficiency Declaring a class final tells JVM that it can’t be subclassed further, it may improve efficiency at run time.

public final class MoviesApiManagerSingleton {

    private static MoviesApiManagerSingleton mInstance = null;

    private MovieApiService movieApiService;

    private static Retrofit retrofit  = null ;


    private MoviesApiManagerSingleton(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(Constants.TMDB_API_URL)
                      .addConverterFactory(GsonConverterFactory.create()).build();
        }

        movieApiService = retrofit.create(MovieApiService.class);
    }

    public static MoviesApiManagerSingleton getInstance() {
        if (mInstance == null) {
            synchronized (MoviesApiManagerSingleton.class) {
                if (mInstance == null) mInstance = new MoviesApiManagerSingleton();
            }
        }

        return mInstance;
    }


    public enum SortBy {
        MostPopular(0),
        TopRated(1),
        Favourite(2);

        private int mValue;

        SortBy(int value) {
            this.mValue = value;
        } // Constructor

        public int id() {
            return mValue;
        }  // Return enum index
    }






    public void getMovies(SortBy sortBy, int page) {

        switch (sortBy) {
            case MostPopular:
                getPopularMovies(page);
                break;
            case TopRated:
                getTopRatedMovies(page);
                break;
            default:
                throw new UnsupportedOperationException("Wrong enum input");
        }

    }



    private void getTopRatedMovies(int page) {
        movieApiService.getTopRatedMovies(Constants.TMDB_API_KEY, page).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                EventBus.getDefault().post(new MessageResponseEvent<>(response.body()));
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {

            }
        });

    }

    private void getPopularMovies(int page) {
        movieApiService.getPopularMovies(Constants.TMDB_API_KEY, page).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                EventBus.getDefault().post(new MessageResponseEvent<>(response.body()));
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {

            }
        });

    }















}
