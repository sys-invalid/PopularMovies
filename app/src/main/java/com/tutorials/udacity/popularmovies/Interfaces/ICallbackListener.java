package com.tutorials.udacity.popularmovies.Interfaces;

import com.tutorials.udacity.popularmovies.Models.MovieException;

/**
 * Created by asharma on 8/22/15.
 */
public interface ICallbackListener<T> {
    void onComplete(T result);
    void onError(MovieException exception);
}
