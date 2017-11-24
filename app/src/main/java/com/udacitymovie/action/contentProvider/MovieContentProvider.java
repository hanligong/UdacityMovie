package com.udacitymovie.action.contentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by hanyuezi on 17/11/21.
 */

public class MovieContentProvider extends ContentProvider{

    private MovieDbHelper movieDbHelper;

    public static final int CODE_MOVIE = 100;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.MovieEntry.AUTHOR;
        matcher.addURI(authority, MovieContract.PATH_MOVIE, CODE_MOVIE);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        movieDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        SQLiteDatabase sqLiteDatabase = movieDbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (sUriMatcher.match(uri)) {
            case CODE_MOVIE:
                cursor = sqLiteDatabase.query(MovieContract.MovieEntry.TABLE_NAME, strings, s, strings1,s1, null, null);
                break;
        }
        if (null != cursor) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        SQLiteDatabase sqLiteDatabase = movieDbHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {
            case CODE_MOVIE:
                sqLiteDatabase.beginTransaction();
                int rowsInserted = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = sqLiteDatabase.insert(MovieContract.MovieEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    sqLiteDatabase.setTransactionSuccessful();
                } finally {
                    sqLiteDatabase.endTransaction();
                }

                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;
        }
        return 0;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
