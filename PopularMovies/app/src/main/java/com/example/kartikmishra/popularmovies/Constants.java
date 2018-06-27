package com.example.kartikmishra.popularmovies;

public class Constants {


        public final static String MOVIE_TAG = "MOVIE";

        public final static class APIConstants{
            public static final String BASE_URL = "https://api.themoviedb.org/3/movie";
            public static final String APP_KEY_QUERY_PARAM = "api_key";
            public static final String SORT_POPULARITY = "popularity.desc";
            public static final String SORT_RATING = "vote_average.desc";
            public static final String SORT_FAV = "favourites";
            public final static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
            public final static String IMAGE_SMALL_SIZE = "w342";
            public final static String RATING_MAX = "10";
            public final static String YOUTUBE_PREFIX = "vnd.youtube:";

            public static final String API_POPULAR_MOVIES_BASE_URL = "https://api.themoviedb.org/3/movie/popular?";
            public static final String API_TOP_RATED_MOVIES_BASE_URL = "https://api.themoviedb.org/3/movie/top_rated?";
            public static final String API_POSTER_MOVIES_BASE_URL = "http://image.tmdb.org/t/p/";
            public static final String API_POSTER_SIZE = "w185/";
            public static final String API_KEY_PARAM = "api_key";
            public static final String THE_MOVIE_DB_API_KEY = "";
        }
}
