package com.tutorials.udacity.popularmovies.Models;


import com.google.gson.annotations.SerializedName;

public class Movie {
    @SerializedName("title")
    public String Title;
    @SerializedName("id")
    public String Id;
    @SerializedName("genre_ids")
    public String[] GenreIds;
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

    public String getThumbnailUrl() {
        if (this.PosterPath != null && this.PosterPath != "") {
            return String.format("http://image.tmdb.org/t/p/%s%s", "w185", PosterPath);
        }
        return "";
    }

}
