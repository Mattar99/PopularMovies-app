package com.example.mattar.popularmovies_app.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mattar on 5/7/2018.
 */

public class Movie implements Parcelable {


    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;


    @SerializedName("original_title")
    @Expose
    private String originalTitle;


    @SerializedName("overview")
    @Expose
    private String overview;

    public Movie() {
        this.id = 0;
        this.posterPath = "";
        this.originalTitle = "";
        this.overview = "";
    }

    public int getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.posterPath);
        dest.writeString(this.originalTitle);
        dest.writeString(this.overview);
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.posterPath = in.readString();
        this.originalTitle = in.readString();
        this.overview = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}




