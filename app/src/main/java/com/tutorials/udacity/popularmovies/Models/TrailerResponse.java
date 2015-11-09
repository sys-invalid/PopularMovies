package com.tutorials.udacity.popularmovies.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asharma on 9/2/15.
 */
public class TrailerResponse {

    @SerializedName("id")
    public String id;


    @SerializedName("results")
    public List<Trailer> Trailers;


    public class  Trailer {
        @SerializedName("id")
        public String id;

        @SerializedName("key")
        public String Key;

        @SerializedName("name")
        public String Name;

        @SerializedName("site")
        public String Site;

        @SerializedName("type")
        public String Type;

        public String getTrailerUrl(){
            return "http://www.youtube.com/watch?v=" + id;
        }
    }

}
