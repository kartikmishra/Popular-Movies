package com.example.kartikmishra.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MoviesDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =8;
    static final String DATABASE_NAME = "movies.db";

    public MoviesDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE  "+ MoviesContract.MoviesEntry.TABLE_NAME + "(" +
                MoviesContract.MoviesEntry._ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MoviesContract.MoviesEntry.COLUMN__MOVIES_ID + " INTEGER NOT NULL, " +
                MoviesContract.MoviesEntry.COLUMN__VOTE_AVERAGE + " DOUBLE NOT NULL, " +
                MoviesContract.MoviesEntry.COLUMN__POSTER_PATH + " VARCHAR NOT NULL, " +
                MoviesContract.MoviesEntry.COLUMN__TITLE + " VARCHAR NOT NULL, "+
                MoviesContract.MoviesEntry.COLUMN__OVERVIEW + " TEXT, "+
                MoviesContract.MoviesEntry.COLUMN_RELEASE_DATE + " VARCHAR ); ";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ MoviesContract.MoviesEntry.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
    }
}
