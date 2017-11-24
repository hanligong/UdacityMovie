package com.udacitymovie.action.contentProvider;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bluelinelabs.logansquare.annotation.JsonField;

/**
 * Created by hanyuezi on 17/11/21.
 */

public class MovieDbHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "movie.db";

    public static final int TABLE_VERSION = 1;

    public MovieDbHelper(Context context){
        super(context, DATABASE_NAME, null, TABLE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table " + MovieContract.MovieEntry.TABLE_NAME + " ( " +
                MovieContract.MovieEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieContract.MovieEntry.COLUMN_VOTE_COUNT + " INTEGER, " +
                MovieContract.MovieEntry.COLUMN_VIDEO + " INTEGER, " +
                MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE + " DOUBLE, " +
                MovieContract.MovieEntry.COLUMN_TITLE + " REAL , " +
                MovieContract.MovieEntry.COLUMN_POPULARITY + " DOUBLE , " +
                MovieContract.MovieEntry.COLUMN_POSTER_PATH + " REAL UNIQUE , " +
                MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE + " REAL , " +
                MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE + " REAL , " +
                MovieContract.MovieEntry.COLUMN_GENRE_IDS + " REAL , " +
                MovieContract.MovieEntry.COLUMN_BACKDROP_PATH + " REAL , " +
                MovieContract.MovieEntry.COLUMN_ADULT + " INTEGER , " +
                MovieContract.MovieEntry.COLUMN_OVERVIEW + " REAL , " +
                MovieContract.MovieEntry.COLUMN_RELEASE_DATE + " REAL " +
                " );";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "drop table " + DATABASE_NAME + "if exists ";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}
