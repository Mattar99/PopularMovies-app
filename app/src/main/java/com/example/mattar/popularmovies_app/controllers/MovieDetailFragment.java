package com.example.mattar.popularmovies_app.controllers;


import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mattar.popularmovies_app.R;
import com.example.mattar.popularmovies_app.adapters.ReviewAdapter;
import com.example.mattar.popularmovies_app.adapters.VideosAdapter;
import com.example.mattar.popularmovies_app.data.MoviesContract;
import com.example.mattar.popularmovies_app.models.movieDetails.Genre;
import com.example.mattar.popularmovies_app.models.movieDetails.MovieDetails;
import com.example.mattar.popularmovies_app.models.movieDetails.Video;
import com.example.mattar.popularmovies_app.utils.ImageUtils;
import com.example.mattar.popularmovies_app.utils.SpacingItemDecoration;
import com.nex3z.flowlayout.FlowLayout;
import com.squareup.picasso.Picasso;

import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;


public class MovieDetailFragment extends Fragment {

    public static String EXTRA_MOVIE_KEY = "extra_movie";


    @BindView(R.id.svDetailsContainer)
    public NestedScrollView mSvDetailsContainer;
    private MovieDetails mMovie;
    @BindView(R.id.ivMovie)
    public ImageView mIvMovie;
    @BindView(R.id.tvTitle)
    public TextView mTvTitle;
    @BindView(R.id.tvReleaseDateValue)
    public TextView mTvReleaseDateValue;
    @BindView(R.id.tvDurationValue)
    public TextView mTvDurationValue;
    @BindView(R.id.tvVoteValue)
    public TextView mTvVoteValue;
    @BindView(R.id.tvPlotValue)
    public TextView mTvPlotValue;
    @BindView(R.id.ratingBar)
    public MaterialRatingBar mRatingBar;
    @BindView(R.id.genresContainer)
    public FlowLayout mGenresContainer;
    @BindView(R.id.rvVideos)
    public RecyclerView mRvVideos;
    @BindView(R.id.tvReviewsTitle)
    public TextView mTvReviewsTitle;
    @BindView(R.id.rvReviews)
    public RecyclerView mRvReviews;
    @BindView(R.id.fbLike)
    public FloatingActionButton mFbLike;

    private boolean mIsFavourite;

    private static final String TAG = MovieDetailFragment.class.getName();
    public MovieDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(EXTRA_MOVIE_KEY)) {
            mMovie = getArguments().getParcelable(EXTRA_MOVIE_KEY);
        }


        mFbLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFavouriteStatus();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie_detail_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share_action) {
            shareTrailer(mMovie.getVideos().getResults().get(0));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        ButterKnife.bind(this,rootView);



        mIsFavourite = isFavouriteMovie();

        if (mMovie != null) {
            populateUI();

        } else {
            closeOnError();
        }
        return rootView;
    }


    private void populateUI() {
        CollapsingToolbarLayout appBarLayout = getActivity().findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(mMovie.getTitle());
        }

        switchFabStyle();

        final ImageView backDropImageView = getActivity().findViewById(R.id.backDropImage);
        if (backDropImageView != null) {
            backDropImageView.post(new Runnable() {
                @Override
                public void run() {
                    Picasso.get()
                            .load(ImageUtils.buildBackdropImageUrl(mMovie.getBackdropPath(), backDropImageView.getWidth()))
                            .into(backDropImageView);
                }
            });
        }

        mTvTitle.setText(mMovie.getTitle());
        mTvReleaseDateValue.setText(mMovie.getReleaseDateLocalized());
        mTvDurationValue.setText(mMovie.getDuration());
        mTvVoteValue.setText(String.valueOf(mMovie.getVoteAverage()));
        mRatingBar.setRating((float) (mMovie.getVoteAverage() / 2));
        mTvPlotValue.setText(mMovie.getOverview());

        for (Genre genre : mMovie.getGenres()) {
            TextView textView = new TextView(getActivity());
            textView.setBackground(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.label_bg));
            textView.setText(genre.getName());
            textView.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorLightGrey));
            mGenresContainer.addView(textView);
        }

        mIvMovie.post(new Runnable() {
            public void run() {
                Picasso.get()
                        .load(ImageUtils.buildPosterImageUrl(mMovie.getPosterPath(), mIvMovie.getWidth()))
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(mIvMovie);
            }
        });


        setUpVideosRecyclerView();
        setUpReviewsRecyclerView();
    }

    private void setUpVideosRecyclerView() {
        if (mMovie.getVideos().getResults().size() > 0) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mRvVideos.getContext(), LinearLayoutManager.HORIZONTAL, false);
            mRvVideos.setLayoutManager(layoutManager);
            mRvVideos.setHasFixedSize(true);
            mRvVideos.addItemDecoration(new SpacingItemDecoration((int) getResources().getDimension(R.dimen.video_list_items_margin), SpacingItemDecoration.HORIZONTAL));

            mRvVideos.setAdapter(new VideosAdapter(mRvVideos.getContext(), mMovie.getVideos()));
            mRvVideos.setVisibility(View.VISIBLE);

            mRvVideos.setVisibility(View.VISIBLE);
            setHasOptionsMenu(true);
        }
    }

    private void setUpReviewsRecyclerView() {
        if (mMovie.getReviews().getResults().size() > 0) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mRvVideos.getContext(), LinearLayoutManager.VERTICAL, false);
            mRvReviews.setLayoutManager(layoutManager);
            mRvReviews.setHasFixedSize(false);
            mRvReviews.addItemDecoration(new SpacingItemDecoration((int) getResources().getDimension(R.dimen.movie_list_items_margin), SpacingItemDecoration.VERTICAL));

            mRvReviews.setAdapter(new ReviewAdapter(mMovie.getReviews()));

            mTvReviewsTitle.setVisibility(View.VISIBLE);
            mRvReviews.setVisibility(View.VISIBLE);
        }
    }

    private void closeOnError() {
        Toast.makeText(getActivity().getApplicationContext(), R.string.movie_detail_error_message, Toast.LENGTH_SHORT).show();

        // Not in TwoPane Mode so close the MovieDetailActivity
        if (!areOnTwoPaneMove()) {
            getActivity().finish();
        } else {
            mSvDetailsContainer.setVisibility(View.GONE);
        }
    }

    private boolean areOnTwoPaneMove() {
        return (getActivity() instanceof MovieListActivity);
    }

    private void shareTrailer(Video video) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mMovie.getTitle() + " - " + video.getName());
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "http://www.youtube.com/watch?v=" + video.getKey());
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_trailer)));
    }

    private boolean isFavouriteMovie() {

        final Cursor cursor;
        cursor = getContext().getContentResolver().query(MoviesContract.MoviesEntry.CONTENT_URI, null, "movie_id=?", new String[]{String.valueOf(mMovie.getId())}, null);

        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    private void switchFavouriteStatus() {

        if (mIsFavourite) {
            Uri uri = MoviesContract.MoviesEntry.CONTENT_URI;
            uri = uri.buildUpon().appendPath(String.valueOf(mMovie.getId())).build();
            int returnUri = getContext().getContentResolver().delete(uri, null, null);
            getContext().getContentResolver().notifyChange(uri, null);

            mIsFavourite = !mIsFavourite;
            switchFabStyle();
            Toast.makeText(getActivity().getApplicationContext(), mMovie.getTitle() + " " + getString(R.string.removed_from_favourite), Toast.LENGTH_SHORT).show();
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MoviesContract.MoviesEntry.COLUMN_ID, mMovie.getId());
            contentValues.put(MoviesContract.MoviesEntry.COLUMN_TITLE, mMovie.getTitle());
            contentValues.put(MoviesContract.MoviesEntry.COLUMN_OVERVIEW, mMovie.getOverview());
            contentValues.put(MoviesContract.MoviesEntry.COLUMN_POSTER_PATH, mMovie.getPosterPath());
            contentValues.put(MoviesContract.MoviesEntry.COLUMN_BACKDROP_PATH, mMovie.getBackdropPath());
            contentValues.put(MoviesContract.MoviesEntry.COLUMN_RELEASE_DATE, mMovie.getReleaseDate());
            contentValues.put(MoviesContract.MoviesEntry.COLUMN_RUNTIME, mMovie.getRuntime());
            contentValues.put(MoviesContract.MoviesEntry.COLUMN_VOTE_AVERAGE, mMovie.getVoteAverage());

            Uri uri = getContext().getContentResolver().insert(MoviesContract.MoviesEntry.CONTENT_URI, contentValues);
            if (uri != null) {
                mIsFavourite = !mIsFavourite;
                switchFabStyle();
                Toast.makeText(getActivity().getApplicationContext(), mMovie.getTitle() + " " + getString(R.string.added_to_favourite), Toast.LENGTH_SHORT).show();
            } else {
                Log.i(TAG,"uri is null");
            }
        }
    }

    private void switchFabStyle() {
        if (mIsFavourite) {
            mFbLike.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_twotone_favorite_border_24px));
            mFbLike.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorDarkGrey)));
        } else {
            mFbLike.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_twotone_favorite_border_24px));
            mFbLike.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.red_fav)));
        }
    }






}
