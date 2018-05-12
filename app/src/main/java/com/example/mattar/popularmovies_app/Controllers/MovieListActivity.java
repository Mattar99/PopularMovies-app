package com.example.mattar.popularmovies_app.Controllers;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mattar.popularmovies_app.R;
import com.example.mattar.popularmovies_app.adapters.MoviesAdapter;
import com.example.mattar.popularmovies_app.models.MainResponse;
import com.example.mattar.popularmovies_app.networkUtils.api.MoviesApiCallback;
import com.example.mattar.popularmovies_app.networkUtils.api.MoviesApiManagerSingleton;

public class MovieListActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MoviesAdapter mAdapter;
    GridLayoutManager mGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list_activity);

        MoviesApiManagerSingleton Manager = MoviesApiManagerSingleton.getInstance();

        mRecyclerView = findViewById(R.id.recyclerView);
        mGridLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        final Context context = this;

        Manager.getMovies(MoviesApiManagerSingleton.SortBy.MostPopular, 1, new MoviesApiCallback<MainResponse>() {
            @Override
            public void onResponse(MainResponse result) {
                mAdapter = new MoviesAdapter((MovieListActivity) context,result,false);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancel() {

            }
        });



    }
}
