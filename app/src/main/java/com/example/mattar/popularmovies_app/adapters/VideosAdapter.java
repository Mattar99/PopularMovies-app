package com.example.mattar.popularmovies_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mattar.popularmovies_app.R;
import com.example.mattar.popularmovies_app.models.movieDetails.Video;
import com.example.mattar.popularmovies_app.models.movieDetails.Videos;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mattar on 5/12/2018.
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoViewHolder> {


    private Context mContext;
    private Videos mVideoResults;

    public VideosAdapter(Context mContext, Videos mVideoResults) {
        this.mContext = mContext;
        this.mVideoResults = mVideoResults;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_list_item, parent, false);

        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {

        final Video video = mVideoResults.getResults().get(position);

        Picasso.get().load(buildThumbnailUrl(video.getKey()))
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.mIvVideoThumb);

        holder.mTvVideoTitle.setText(video.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=".concat(video.getKey())));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mVideoResults.getResults().size();
    }


    // using youtube api to get video thumbnail
    private String buildThumbnailUrl(String videoId) {
        return "https://img.youtube.com/vi/" + videoId + "/hqdefault.jpg";
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.cvVideo)
        public ImageView mIvVideoThumb;
        @BindView(R.id.tvVideoTitle)
        public TextView mTvVideoTitle;

        public VideoViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }
    }
}
