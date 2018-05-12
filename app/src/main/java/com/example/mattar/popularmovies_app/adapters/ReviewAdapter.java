package com.example.mattar.popularmovies_app.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mattar.popularmovies_app.R;
import com.example.mattar.popularmovies_app.models.movieDetails.Review;
import com.example.mattar.popularmovies_app.models.movieDetails.Reviews;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mattar on 5/12/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Reviews mReviews;

    public ReviewAdapter(Reviews mReviews) {
        this.mReviews = mReviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_list_item, parent, false);

        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

        Review review = mReviews.getResults().get(position);

        holder.mTvReviewAuthor.setText(review.getAuthor());
        holder.mTvReviewContent.setText(review.getContent());


    }

    @Override
    public int getItemCount() {
        return mReviews.getResults().size();
    }


    public class ReviewViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvReviewAuthor)
        public TextView mTvReviewAuthor;
        @BindView(R.id.tvReviewContent)
        public TextView mTvReviewContent;

        public ReviewViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);

        }
    }

}
