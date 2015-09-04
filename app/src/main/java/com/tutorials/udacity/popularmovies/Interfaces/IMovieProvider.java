package com.tutorials.udacity.popularmovies.Interfaces;

import android.support.annotation.NonNull;

import com.tutorials.udacity.popularmovies.Models.Movie;
import com.tutorials.udacity.popularmovies.Models.ReviewResponse;
import com.tutorials.udacity.popularmovies.Models.Trailers;

import java.util.List;


public interface IMovieProvider {
    void getMovies(int pSortCriteria,@NonNull ICallbackListener<List<Movie>> pCallback);
    Movie getMovieDetail(String id);
    void getMovieTrailer(String id,ICallbackListener<List<Trailers>> pCallback);
    void getReviews(String id,ICallbackListener<ReviewResponse> pCallback);

}
