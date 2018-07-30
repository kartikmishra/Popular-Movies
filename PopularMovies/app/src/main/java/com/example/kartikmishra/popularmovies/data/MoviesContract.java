package com.example.kartikmishra.popularmovies.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class MoviesContract {


    public static final String CONTENT_AUTHORITY = "com.example.kartikmishra.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIE = "movies";


    public static final class MoviesEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String TABLE_NAME = "movies";

        public static final String COLUMN__MOVIES_ID = "moviesId";
        public static final String COLUMN__VOTE_AVERAGE = "vote_average";
        public static final String COLUMN__POSTER_PATH = "poster_path";
        public static final String COLUMN__TITLE = "title";
        public static final String COLUMN__OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
        public static String COLUMN_ISFAV="isFav";
        public static final String COLUMN__REVIEW_AUTHOR_NAME = "review_author_name";
        public static final String COLUMN_REVIEW_CONTENT = "review_content";
        public static final String COLUMN_TRAILER_KEY = "trailer_key";

    }
}
