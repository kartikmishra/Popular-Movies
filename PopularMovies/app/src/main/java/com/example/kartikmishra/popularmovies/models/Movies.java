package com.example.kartikmishra.popularmovies.models;


import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.kartikmishra.popularmovies.Constants;
import com.example.kartikmishra.popularmovies.UserInterface.MainActivity;
import com.example.kartikmishra.popularmovies.data.MoviesContract;

public class Movies implements Parcelable {

  long movie_ID;
  Double vote_average;
  String title;
  String poster_path;
  String overView;
  String release_Date;
  String reviews;
  int fav;

    public Movies() {
    }

    public Movies(long movie_ID, Double vote_average, String title, String poster_path, String overView, String release_Date, String reviews) {
        this.movie_ID = movie_ID;
        this.vote_average = vote_average;
        this.title = title;
        this.poster_path = poster_path;
        this.overView = overView;
        this.release_Date = release_Date;
        this.reviews = reviews;
    }

    public Movies(Cursor cursor){
        this.movie_ID = cursor.getLong(MainActivity.COL_MOVIE_ID);
        this.vote_average = cursor.getDouble(MainActivity.COL_RATING);
        this.title = cursor.getString(MainActivity.COL_TITLE);
        this.poster_path = cursor.getString(MainActivity.COL_IMAGE);
        this.overView = cursor.getString(MainActivity.COL_OVERVIEW);
        this.release_Date = cursor.getString(MainActivity.COL_DATE);
    }


    protected Movies(Parcel in) {
        movie_ID = in.readLong();
        if (in.readByte() == 0) {
            vote_average = null;
        } else {
            vote_average = in.readDouble();
        }
        title = in.readString();
        poster_path = in.readString();
        overView = in.readString();
        release_Date = in.readString();
        reviews = in.readString();
    }

    public static final Parcelable.Creator<Movies> CREATOR = new Parcelable.Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    public long getMovie_ID() {
        return movie_ID;
    }

    public void setMovie_ID(long movie_ID) {
        this.movie_ID = movie_ID;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getRelease_Date() {
        return release_Date;
    }

    public void setRelease_Date(String release_Date) {
        this.release_Date = release_Date;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }






    public String getImageFullURL(){

        return Constants.APIConstants.IMAGE_BASE_URL+Constants.APIConstants.IMAGE_SMALL_SIZE+getPoster_path();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(movie_ID);
        if (vote_average == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(vote_average);
        }
        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeString(overView);
        dest.writeString(release_Date);
        dest.writeString(reviews);
    }
}
