package com.example.mattar.popularmovies_app.adapters;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mattar.popularmovies_app.R;
import com.example.mattar.popularmovies_app.controllers.MovieDetailActivity;
import com.example.mattar.popularmovies_app.controllers.MovieDetailFragment;
import com.example.mattar.popularmovies_app.controllers.MovieListActivity;
import com.example.mattar.popularmovies_app.data.MoviesContract;
import com.example.mattar.popularmovies_app.models.movieDetails.Genre;
import com.example.mattar.popularmovies_app.models.movieDetails.MovieDetails;
import com.example.mattar.popularmovies_app.models.movieDetails.Reviews;
import com.example.mattar.popularmovies_app.models.movieDetails.Videos;
import com.example.mattar.popularmovies_app.networkUtils.api.MoviesApiCallback;
import com.example.mattar.popularmovies_app.networkUtils.api.MoviesApiManagerSingleton;
import com.example.mattar.popularmovies_app.utils.ImageUtils;
import com.example.mattar.popularmovies_app.utils.Misc;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Mattar on 5/16/2018.
 */


public class FavouriteMoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private MovieListActivity mParentActivity;
    private boolean mTwoPane;

    private List<MovieDetails> mFavouriteMovies;
    private Call<MovieDetails> callRequest;


    public FavouriteMoviesAdapter(MovieListActivity parent, Cursor cursor, boolean twoPane) {
        this.mParentActivity = parent;
        this.mTwoPane = twoPane;

        mFavouriteMovies = new ArrayList<>();
        loadMoviesFromCursor(cursor);

    }

    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);
        return new MoviesAdapter.MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MoviesAdapter.MovieViewHolder holder, int position) {
        final int pos = position;
        holder.mIvPoster.post(new Runnable() {
            @Override
            public void run() {
                Picasso.get()
                        .load(ImageUtils.buildPosterImageUrl(mFavouriteMovies.get(pos).getPosterPath(), holder.mIvPoster.getWidth()))
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(holder.mIvPoster);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callRequest != null) {
                    callRequest.cancel();
                }
                getMovieAndShowDetails(pos, holder);
            }
        });

        if (position == 0 && mTwoPane) {
            holder.itemView.callOnClick();
        }
    }

    @Override
    public int getItemCount() {
        return mFavouriteMovies.size();
    }


    private void getMovieAndShowDetails(final int position, final MoviesAdapter.MovieViewHolder movieViewHolder) {
        if (Misc.isNetworkAvailable(mParentActivity)) {
            movieViewHolder.showProgress(true);

            callRequest = MoviesApiManagerSingleton.getInstance().getMovie(mFavouriteMovies.get(position).getId(), new MoviesApiCallback<MovieDetails>() {
                @Override
                public void onResponse(MovieDetails result) {
                    if (result != null) {
                        showMovieDetails(result);
                    }
                    callRequest = null;
                    movieViewHolder.showProgress(false);
                }

                @Override
                public void onCancel() {
                    callRequest = null;
                    movieViewHolder.showProgress(false);
                }
            });
        } else {
            showMovieDetails(mFavouriteMovies.get(position));
        }


    }

    private void showMovieDetails(MovieDetails movie) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(MovieDetailFragment.EXTRA_MOVIE_KEY, movie);
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);
            mParentActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movieDetailContainer, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(mParentActivity, MovieDetailActivity.class);
            intent.putExtra(MovieDetailFragment.EXTRA_MOVIE_KEY, movie);

            mParentActivity.startActivity(intent);
        }
    }

    private void loadMoviesFromCursor(Cursor cursor) {
        for (int i = 0; i < cursor.getCount(); i++) {
            int movieIdIndex = cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_ID);
            int titleIndex = cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_TITLE);
            int overviewIndex = cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_OVERVIEW);
            int posterPathIndex = cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_POSTER_PATH);
            int backdropPathIndex = cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_BACKDROP_PATH);
            int releaseDateIndex = cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_RELEASE_DATE);
            int runtimeIndex = cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_RUNTIME);
            int voteAverageIndex = cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_VOTE_AVERAGE);

            cursor.moveToPosition(i);

            mFavouriteMovies.add(new MovieDetails(
                    cursor.getInt(movieIdIndex),
                    cursor.getString(titleIndex),
                    cursor.getString(overviewIndex),
                    cursor.getString(posterPathIndex),
                    cursor.getString(backdropPathIndex),
                    cursor.getString(releaseDateIndex),
                    cursor.getInt(runtimeIndex),
                    cursor.getInt(voteAverageIndex),
                    new Videos(),
                    new Reviews(),
                    new ArrayList<Genre>()
            ));
        }
    }


}
