package com.tutorials.udacity.popularmovies.Utils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Constants {
    public static final String API_DISCOVER_URL = "http://api.themoviedb.org/3/discover/movie?sort_by=%s&api_key=%s";
    public static final String API_TRAILER_URL = "http://api.themoviedb.org/3/movie/%s/reviews?api_key=%s";
    public static final String API_REVIEW_URL = "http://api.themoviedb.org/3/movie/%s/videos?api_key=%s";
    public static final String API_YOUTUBE_PREVIEW ="http://img.youtube.com/vi/%s/0.jpg";
    public static final String API_KEY ="46a39b3299e8297e6d61d1ec95d0863f";
    public static final int SORT_POPULARITY = 0;
    public static final int SORT_RATING =1;
    public static final int SORT_SAVE = 2;
    public static final String POPULARITY_PARAM ="popularity.desc";
    public static final String RATING_PARAM ="vote_average.desc";
    public static final String FRAGMENT_MOVIE_LIST_TAG ="movielist";
    public static final String FRAGMENT_MOVIE_DETAIL_TAG ="moviedetail";
    public static final String BUNDLE_MOVIE ="movie";
    public static final String SHARED_PREF_FAV ="shared_pref_favs";




}
