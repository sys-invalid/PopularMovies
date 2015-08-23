package com.tutorials.udacity.popularmovies.Interfaces;

import android.support.annotation.NonNull;

import com.tutorials.udacity.popularmovies.Models.Movie;

import java.util.List;

/**
 * Created by asharma on 8/22/15.
 */
public interface IMovieProvider {
    void getMovies(int pSortCriteria,@NonNull ICallbackListener<List<Movie>> pCallback);
    Movie getMovieDetail(String id);
}
