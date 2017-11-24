package com.udacitymovie.action.contentProvider;

import android.net.Uri;
import android.provider.BaseColumns;

import com.bluelinelabs.logansquare.annotation.JsonField;

/**
 * Created by hanyuezi on 17/11/21.
 */

public class MovieContract {

    public static final String PATH_MOVIE = "movie";

    public static class MovieEntry implements BaseColumns {

        public static Uri getContentUri(){
            return BASE_URI.buildUpon().appendPath(TABLE_NAME).build();
        }

        public static final String AUTHOR = "com.udacitymovie.action";

        public static final Uri BASE_URI = Uri.parse("content://" + AUTHOR);

        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_VOTE_COUNT = "vote_count";
        public static final String COLUMN_VIDEO = "video";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_ORIGINAL_LANGUAGE = "original_language";
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_GENRE_IDS = "genre_ids";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_ADULT = "adult";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";

    }
}
