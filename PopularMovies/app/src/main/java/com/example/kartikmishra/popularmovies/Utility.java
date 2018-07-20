package com.example.kartikmishra.popularmovies;

import android.content.Context;
import android.database.Cursor;

import com.example.kartikmishra.popularmovies.data.MoviesContract;

public class Utility {



    public static int isFavorited(Context context, int id) {
        Cursor cursor = context.getContentResolver().query(
                MoviesContract.MoviesEntry.CONTENT_URI,
                null,   // projection
                MoviesContract.MoviesEntry.COLUMN__MOVIES_ID + " = ?", // selection
                new String[] { Integer.toString(id) },   // selectionArgs
                null    // sort order
        );
        int numRows = cursor.getCount();
        cursor.close();
        return numRows;
    }

    public static String buildImageUrl(int width, String fileName) {
        return "http://image.tmdb.org/t/p/w" + Integer.toString(width) + fileName;
    }
}
