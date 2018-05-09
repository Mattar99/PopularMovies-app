package com.example.mattar.popularmovies_app.networkUtils.api;


import com.example.mattar.popularmovies_app.models.MainResponse;
import com.example.mattar.popularmovies_app.models.movieDetails.MovieDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Mattar on 5/8/2018.
 */

public interface MovieApiService {


    @GET("movie/top_rated")
    Call<MainResponse> getTopRatedMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/popular")
    Call<MainResponse> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/{movieId}?append_to_response=videos,reviews")
    Call<MovieDetails> getMovie(@Path("movieId") int movieId, @Query("api_key") String apiKey);

}
