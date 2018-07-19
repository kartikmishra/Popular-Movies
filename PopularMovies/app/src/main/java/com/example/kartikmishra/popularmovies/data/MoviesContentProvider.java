package com.example.kartikmishra.popularmovies.data;

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

import com.example.kartikmishra.popularmovies.models.Movies;

public class MoviesContentProvider extends ContentProvider {


    public static final int MOVIES = 100;

    private MoviesDbHelper mMoviesDbHelper;


    private static final UriMatcher uriMatcher = builtUriMatcher();

    public static UriMatcher builtUriMatcher(){

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MoviesContract.CONTENT_AUTHORITY, MoviesContract.PATH_MOVIE, MOVIES);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {

        Context context = getContext();
        mMoviesDbHelper = new MoviesDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,  String[] projection,  String selection,  String[] selectionArgs,  String sortOrder) {

        final SQLiteDatabase db = mMoviesDbHelper.getReadableDatabase();

        Cursor retCursor;
        switch (builtUriMatcher().match(uri)) {

            case MOVIES:

                retCursor = db.query(MoviesContract.MoviesEntry.TABLE_NAME, null,
                        null, null, null, null, null);
                break;
            default:
                throw new UnsupportedOperationException();
        }

        retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = builtUriMatcher().match(uri);

        switch (match) {
            case MOVIES:
                return MoviesContract.MoviesEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri,  ContentValues values) {

        final SQLiteDatabase db = mMoviesDbHelper.getWritableDatabase();

        Uri returnUri;
        switch (builtUriMatcher().match(uri)){

            case MOVIES:

                long _id = db.insert(MoviesContract.MoviesEntry.TABLE_NAME,null,values);
                if(_id<0){
                    throw  new android.database.SQLException("Failed to insert row into "+ uri);
                }else {

                    returnUri = ContentUris.withAppendedId(MoviesContract.MoviesEntry.CONTENT_URI,_id);
                }
                break;
                default:
                    throw new UnsupportedOperationException();
        }

        getContext().getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri,  String selection,  String[] selectionArgs) {

        final SQLiteDatabase database = mMoviesDbHelper.getWritableDatabase();

        int rowsDeleted;
        if (null == selection) selection = "1";
        switch (builtUriMatcher().match(uri)){
            case MOVIES:

                rowsDeleted = database.delete(MoviesContract.MoviesEntry.TABLE_NAME,selection,selectionArgs);
                break;
                default:
                    throw new UnsupportedOperationException();
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        final SQLiteDatabase db = mMoviesDbHelper.getWritableDatabase();
        int rowsUpdated;

        switch (builtUriMatcher().match(uri)) {
            case MOVIES:
                rowsUpdated = db.update(MoviesContract.MoviesEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }

}
