package com.example.kartikmishra.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Videos implements Parcelable {


    String key;
    String name;
    String video_id;
    String site;
    String type;


    public Videos() {
    }

    public Videos(String key, String name, String video_id, String site, String type) {
        this.key = key;
        this.name = name;
        this.video_id = video_id;
        this.site = site;
        this.type = type;
    }

    protected Videos(Parcel in) {
        key = in.readString();
        name = in.readString();
        video_id = in.readString();
        site = in.readString();
        type = in.readString();
    }

    public static final Parcelable.Creator<Videos> CREATOR = new Parcelable.Creator<Videos>() {
        @Override
        public Videos createFromParcel(Parcel in) {
            return new Videos(in);
        }

        @Override
        public Videos[] newArray(int size) {
            return new Videos[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(video_id);
        dest.writeString(site);
        dest.writeString(type);
    }
}
