package com.tutorials.udacity.popularmovies.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MovieResponse {
    @SerializedName("page")
    public int Page;
    @SerializedName("results")
    public List<Movie> Movies;
    @SerializedName("total_pages")
    public int TotalPages;

}
