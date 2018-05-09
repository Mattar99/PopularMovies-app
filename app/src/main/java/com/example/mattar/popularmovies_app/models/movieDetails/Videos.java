package com.example.mattar.popularmovies_app.models.movieDetails;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mattar on 5/8/2018.
 */

public class Videos implements Parcelable {

    @SerializedName("results")
    @Expose
    private List<Video> results;

    /**
     * No args constructor for use in serialization
     *
     */
    public Videos() {
        this.results = new ArrayList<>();
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.results);
    }

    protected Videos(Parcel in) {
        this.results = in.createTypedArrayList(Video.CREATOR);
    }

    public static final Parcelable.Creator<Videos> CREATOR = new Parcelable.Creator<Videos>() {
        @Override
        public Videos createFromParcel(Parcel source) {
            return new Videos(source);
        }

        @Override
        public Videos[] newArray(int size) {
            return new Videos[size];
        }
    };


}