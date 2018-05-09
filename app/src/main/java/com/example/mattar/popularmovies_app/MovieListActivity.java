package com.example.mattar.popularmovies_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mattar.popularmovies_app.networkUtils.api.MoviesApiManagerSingleton;

public class MovieListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MoviesApiManagerSingleton.getInstance();
    }
}
