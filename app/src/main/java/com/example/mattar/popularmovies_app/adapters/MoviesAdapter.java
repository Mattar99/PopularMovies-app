package com.example.mattar.popularmovies_app.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mattar.popularmovies_app.MovieListActivity;
import com.example.mattar.popularmovies_app.R;
import com.example.mattar.popularmovies_app.models.MainResponse;
import com.example.mattar.popularmovies_app.models.movieDetails.MovieDetails;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * Created by Mattar on 5/10/2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private MovieListActivity mParentActivity;
    private MainResponse mMainResponse;
    private boolean mTwoPane;

    private Call<MovieDetails> callRequest;


    public MoviesAdapter(MovieListActivity parent, MainResponse mainResponse, boolean twoPane) {
        this.mParentActivity = parent;
        this.mMainResponse = mainResponse;
        this.mTwoPane = twoPane;
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mMainResponse.getResults().size();
    }





    public class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivPoster)
        public ImageView mIvPoster;

        @BindView(R.id.progressBarContainer)
        public LinearLayout mProgressBarContainer;

        public MovieViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);

        }

        public void showProgress(Boolean show) {
            mProgressBarContainer.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}
