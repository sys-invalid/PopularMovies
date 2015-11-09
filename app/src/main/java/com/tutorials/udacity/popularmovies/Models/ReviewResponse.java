package com.tutorials.udacity.popularmovies.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asharma on 9/2/15.
 */
public class ReviewResponse {
    @SerializedName("id")
    public String Id;
    @SerializedName("page")
    public int Page;
    @SerializedName("total_pages")
    public int TotalPages;
    @SerializedName("total_results")
    public int TotalResults;

    @SerializedName("results")
    public List<Review> Results;

    public class Review{
        @SerializedName("id")
        public String Id;
        @SerializedName("author")
        public String Author;
        @SerializedName("content")
        public String Content;
        @SerializedName("url")
        public String Url;
    }
}
