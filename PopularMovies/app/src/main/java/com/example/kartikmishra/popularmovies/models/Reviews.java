package com.example.kartikmishra.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Reviews implements Parcelable{


    String author;
    String content;
    String reviewId;

    public Reviews() {
    }

    public Reviews(String author, String content, String reviewId) {
        this.author = author;
        this.content = content;
        this.reviewId = reviewId;
    }

    protected Reviews(Parcel in) {
        author = in.readString();
        content = in.readString();
        reviewId = in.readString();
    }

    public static final Parcelable.Creator<Reviews> CREATOR = new Parcelable.Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel in) {
            return new Reviews(in);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(content);
        dest.writeString(reviewId);
    }
}
