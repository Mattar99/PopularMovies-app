package com.example.mattar.popularmovies_app.networkUtils.api;

import android.support.annotation.NonNull;

import com.example.mattar.popularmovies_app.models.MainResponse;
import com.example.mattar.popularmovies_app.models.movieDetails.MovieDetails;
import com.example.mattar.popularmovies_app.networkUtils.Constants;


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

        public static SortBy fromId(int value) {
            for (SortBy svalue : values()) {
                if (svalue.mValue == value) {
                    return svalue;
                }
            }
            return MostPopular;
        }
    }






    public void getMovies(SortBy sortBy, int page, MoviesApiCallback<MainResponse> moviesApiCallback) {

        switch (sortBy) {
            case MostPopular:
                getPopularMovies(page, moviesApiCallback);
                break;
            case TopRated:
                getTopRatedMovies(page, moviesApiCallback);
                break;
            default: throw  new UnsupportedOperationException("Choice is not supported");
        }

    }



    private void getTopRatedMovies(int page, final MoviesApiCallback<MainResponse> moviesApiCallback) {
        movieApiService.getTopRatedMovies(Constants.TMDB_API_KEY, page).enqueue(new Callback<MainResponse>() {

            @Override
            public void onResponse(@NonNull Call<MainResponse> call, @NonNull Response<MainResponse> response) {
                moviesApiCallback.onResponse(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<MainResponse> call, @NonNull Throwable t) {
                if (call.isCanceled()) {
                    moviesApiCallback.onCancel();
                } else {
                    moviesApiCallback.onResponse(null);
                }
            }
        });
    }

    private void getPopularMovies(int page, final MoviesApiCallback<MainResponse> moviesApiCallback) {
        movieApiService.getPopularMovies(Constants.TMDB_API_KEY, page).enqueue(new Callback<MainResponse>() {

            @Override
            public void onResponse(@NonNull Call<MainResponse> call, @NonNull Response<MainResponse> response) {
                moviesApiCallback.onResponse(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<MainResponse> call, @NonNull Throwable t) {
                if (call.isCanceled()) {
                    moviesApiCallback.onCancel();
                } else {
                    moviesApiCallback.onResponse(null);
                }
            }

        });
    }



    public Call<MovieDetails> getMovie(int movieId, final MoviesApiCallback<MovieDetails> moviesApiCallback) {
        Call<MovieDetails> call = movieApiService.getMovie(movieId, Constants.TMDB_API_KEY);

        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(@NonNull Call<MovieDetails> call, @NonNull Response<MovieDetails> response) {
                moviesApiCallback.onResponse(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<MovieDetails> call, @NonNull Throwable t) {
                if (call.isCanceled()) {
                    moviesApiCallback.onCancel();
                } else {
                    moviesApiCallback.onResponse(null);
                }
            }
        });

        return call;
    }















}
