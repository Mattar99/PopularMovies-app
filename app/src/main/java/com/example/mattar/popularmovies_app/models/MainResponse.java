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

public class MainResponse implements Parcelable {

    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("total_results")
    @Expose
    private int totalResults;
    @SerializedName("total_pages")
    @Expose
    private int totalPages;
    @SerializedName("results")
    @Expose
    private List<Movie> results = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public MainResponse() {
        this.page = 0;
        this.results = new ArrayList<>();
    }

    /**
     *
     * @param results         // array list of movies on this page number
     * @param totalResults    // the total available results/Movies
     * @param page            // the page number currently in
     * @param totalPages      // the total page number available
     */
    public MainResponse(int page, int totalResults, int totalPages, List<Movie> results) {
        super();
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public void appendMovies(MainResponse movies) {
        this.page = movies.getPage();
        this.results.addAll(movies.getResults());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeInt(this.totalResults);
        dest.writeInt(this.totalPages);
        dest.writeTypedList(this.results);
    }

    protected MainResponse(Parcel in) {
        this.page = in.readInt();
        this.totalResults = in.readInt();
        this.totalPages = in.readInt();
        this.results = in.createTypedArrayList(Movie.CREATOR);
    }

    public static final Parcelable.Creator<MainResponse> CREATOR = new Parcelable.Creator<MainResponse>() {
        @Override
        public MainResponse createFromParcel(Parcel source) {
            return new MainResponse(source);
        }

        @Override
        public MainResponse[] newArray(int size) {
            return new MainResponse[size];
        }
    };
}