package com.tutorials.udacity.popularmovies.Interfaces;

import com.tutorials.udacity.popularmovies.Models.MovieException;


public interface ICallbackListener<T> {
    void onComplete(T result);
    void onError(MovieException exception);
}
