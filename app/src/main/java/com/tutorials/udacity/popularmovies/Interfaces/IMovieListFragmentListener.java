package com.tutorials.udacity.popularmovies.Interfaces;

import android.view.View;

import com.tutorials.udacity.popularmovies.Models.Movie;

import java.util.List;

/**
 * Created by asharma on 9/1/15.
 */
public interface IMovieListFragmentListener {
    void onMovieItemClicked(Movie pMovie, int position, View imageview);
    void MovieListLoaded(List<Movie> pMovie);
    void onMovieRemoved(Movie pMovie);
    void onMovieAdded(Movie pMovie);
}
