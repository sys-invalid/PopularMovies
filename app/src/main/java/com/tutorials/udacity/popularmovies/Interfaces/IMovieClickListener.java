package com.tutorials.udacity.popularmovies.Interfaces;

import android.view.View;

import com.tutorials.udacity.popularmovies.Models.Movie;

/**
 * Created by asharma on 9/1/15.
 */
public interface IMovieClickListener {
    void onMovieItemClicked(Movie pMovie, int position, View imageview);
}
