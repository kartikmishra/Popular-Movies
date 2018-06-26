package com.example.kartikmishra.popularmovies;


import android.os.Parcel;
import android.os.Parcelable;

public class Movies implements Parcelable {

  long movie_ID;
  String video;
  Double vote_average;
  int vote_count;
  String title;
  String poster_path;
  String overView;
  String release_Date;
  String language;

    public Movies() {
    }

    public Movies(long movie_ID, String video, Double vote_average, int vote_count, String title, String poster_path, String overView, String release_Date, String language) {
        this.movie_ID = movie_ID;
        this.video = video;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.title = title;
        this.poster_path = poster_path;
        this.overView = overView;
        this.release_Date = release_Date;
        this.language = language;
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

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    protected Movies(Parcel in) {
        movie_ID = in.readLong();
        video = in.readString();
        if (in.readByte() == 0) {
            vote_average = null;
        } else {
            vote_average = in.readDouble();
        }
        vote_count = in.readInt();
        title = in.readString();

        poster_path = in.readString();
        overView = in.readString();
        release_Date = in.readString();
        language = in.readString();
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
        dest.writeString(video);
        if (vote_average == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(vote_average);
        }
        dest.writeInt(vote_count);
        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeString(overView);
        dest.writeString(release_Date);
        dest.writeString(language);
    }
}
