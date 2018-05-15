package com.example.mattar.popularmovies_app.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.logging.Logger;

import static com.example.mattar.popularmovies_app.data.MoviesContract.MoviesEntry.TABLE_NAME;

/**
 * Created by Mattar on 5/15/2018.
 */

public class MoviesProvider extends ContentProvider {

    private MoviesDbHelper mOpenHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static final int MOVIES = 100;
    public static final int MOVIE_WITH_ID = 101;



    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MoviesContract.CONTENT_AUTHORITY, MoviesContract.PATH_MOVIES, MOVIES);
        uriMatcher.addURI(MoviesContract.CONTENT_AUTHORITY, MoviesContract.PATH_MOVIES + "/#", MOVIE_WITH_ID);

        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        mOpenHelper = new MoviesDbHelper(context);
        return true;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor cursor;

        switch (match){

            case MOVIES:
                cursor = db.query(TABLE_NAME,
                        projection,
                        selection, selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        /*The setNotificationUri() method simply places a watch on the caller's content resolver such that
         if the data changes and the caller has a registered change watcher, they'll be notified. Here we just use the same URI.*/


        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Uri resultUri;

        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                long id = mOpenHelper.getWritableDatabase().insert(TABLE_NAME, null, values);
                if (id > 0) {
                    resultUri = ContentUris.withAppendedId(MoviesContract.MoviesEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        int numRowsDeleted;

        switch (sUriMatcher.match(uri)) {
            case MOVIE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                numRowsDeleted = mOpenHelper.getWritableDatabase().delete(TABLE_NAME, "movie_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new RuntimeException("We are not implementing update in Popular Movies.");
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new RuntimeException("We are not implementing getType in Popular Movies.");
    }

    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
