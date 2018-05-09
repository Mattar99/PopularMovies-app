package com.example.mattar.popularmovies_app.models.movieDetails;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mattar on 5/8/2018.
 */

public class MovieDetails implements Parcelable {


    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    @SerializedName("genres")
    @Expose
    private List<Genre> genres;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("runtime")
    @Expose
    private int runtime;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("vote_average")
    @Expose
    private double voteAverage;

    @SerializedName("videos")
    @Expose
    private Videos videos;

    @SerializedName("reviews")
    @Expose
    private Reviews reviews;

    /**
     * No args constructor for use in serialization
     */
    public MovieDetails() {

        this.id = 0;
        this.title = "";
        this.overview = "";
        this.posterPath = "";
        this.backdropPath = "";
        this.releaseDate = "";
        this.runtime = 0;
        this.voteAverage = 0.0;
        this.videos = new Videos();
        this.reviews = new Reviews();
        this.genres = new ArrayList<>();
    }

    public MovieDetails(int id, String title, String overview, String posterPath, String backdropPath, String releaseDate, int runtime, double voteAverage, Videos videos, Reviews reviews, List<Genre> genres) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.voteAverage = voteAverage;
        this.videos = videos;
        this.reviews = reviews;
        this.genres = genres;
    }

    // setters and getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getReleaseDateLocalized() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(releaseDate);
        } catch (ParseException e) {
            return releaseDate;
        }
        return DateFormat.getDateInstance().format(date);
    }

    public int getRuntime() {
        return runtime;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public Videos getVideos() {
        return videos;
    }

    public Reviews getReviews() {
        return reviews;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public String getDuration() {
        return String.valueOf(runtime) + " min";

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.backdropPath);
        dest.writeTypedList(this.genres);
        dest.writeInt(this.id);
        dest.writeString(this.overview);
        dest.writeString(this.posterPath);
        dest.writeString(this.releaseDate);
        dest.writeInt(this.runtime);
        dest.writeString(this.title);
        dest.writeDouble(this.voteAverage);
        dest.writeParcelable(this.videos, flags);
        dest.writeParcelable(this.reviews, flags);
    }

    protected MovieDetails(Parcel in) {
        this.backdropPath = in.readString();
        this.genres = in.createTypedArrayList(Genre.CREATOR);
        this.id = in.readInt();
        this.overview = in.readString();
        this.posterPath = in.readString();
        this.releaseDate = in.readString();
        this.runtime = in.readInt();
        this.title = in.readString();
        this.voteAverage = in.readDouble();
        this.videos = in.readParcelable(Videos.class.getClassLoader());
        this.reviews = in.readParcelable(Reviews.class.getClassLoader());
    }

    public static final Parcelable.Creator<MovieDetails> CREATOR = new Parcelable.Creator<MovieDetails>() {
        @Override
        public MovieDetails createFromParcel(Parcel source) {
            return new MovieDetails(source);
        }

        @Override
        public MovieDetails[] newArray(int size) {
            return new MovieDetails[size];
        }
    };
}