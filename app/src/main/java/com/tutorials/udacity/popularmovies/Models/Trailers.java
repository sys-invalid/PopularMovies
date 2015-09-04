package com.tutorials.udacity.popularmovies.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asharma on 9/2/15.
 */
public class Trailers {

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

}
