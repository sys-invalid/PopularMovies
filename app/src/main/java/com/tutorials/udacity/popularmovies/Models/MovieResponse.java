package com.tutorials.udacity.popularmovies.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asharma on 8/22/15.
 */
public class MovieResponse {
    @SerializedName("page")
    public int Page;
    @SerializedName("results")
    public List<Movie> Movies;
    @SerializedName("total_pages")
    public int TotalPages;

}
