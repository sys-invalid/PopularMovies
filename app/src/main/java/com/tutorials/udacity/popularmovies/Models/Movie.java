package com.tutorials.udacity.popularmovies.Models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Movie implements Parcelable {

    public Movie() {
        this.GenreIds = new ArrayList<>();
    }

    public Movie(Parcel in) {
        this.Id = in.readString();
        this.Title = in.readString();
        in.readStringList(this.GenreIds);
        this.PosterPath = in.readString();
        this.Popularity = in.readFloat();
        this.VoteAvg = in.readFloat();
        this.VoteCount = in.readInt();
        this.OverView = in.readString();
        this.ReleaseDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        this.GenreIds = this.GenreIds == null ? new ArrayList<String>():this.GenreIds;
        dest.writeString(this.Id);
        dest.writeString(this.Title);
        dest.writeStringList(this.GenreIds);
        dest.writeString(this.PosterPath);
        dest.writeFloat(this.Popularity);
        dest.writeFloat(this.VoteAvg);
        dest.writeInt(this.VoteCount);
        dest.writeString(this.OverView);
        dest.writeString(this.ReleaseDate);
    }


    @SerializedName("title")
    public String Title;
    @SerializedName("id")
    public String Id;
    @SerializedName("genre_ids")
    public List<String> GenreIds = new ArrayList<>();
    @SerializedName("poster_path")
    public String PosterPath;
    @SerializedName("release_date")
    public String ReleaseDate;
    @SerializedName("popularity")
    public float Popularity;
    @SerializedName("vote_count")
    public int VoteCount;
    @SerializedName("vote_average")
    public float VoteAvg;
    @SerializedName("overview")
    public String OverView;

    public String getThumbnailUrl(String pSize) {
        if (this.PosterPath != null && this.PosterPath != "") {
            return String.format("http://image.tmdb.org/t/p/%s%s", pSize, PosterPath);
        }
        return "";
    }



    @Override
    public int describeContents() {
        return 0;
    }


    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
