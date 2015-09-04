package com.tutorials.udacity.popularmovies.Providers;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.tutorials.udacity.popularmovies.Extensions.GsonRequest;
import com.tutorials.udacity.popularmovies.Interfaces.ICallbackListener;
import com.tutorials.udacity.popularmovies.Interfaces.IMovieProvider;
import com.tutorials.udacity.popularmovies.Models.Movie;
import com.tutorials.udacity.popularmovies.Models.MovieException;
import com.tutorials.udacity.popularmovies.Models.MovieResponse;
import com.tutorials.udacity.popularmovies.Models.ReviewResponse;
import com.tutorials.udacity.popularmovies.Models.Trailers;
import com.tutorials.udacity.popularmovies.MovieApp;
import com.tutorials.udacity.popularmovies.Utils.Constants;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MovieProvider implements IMovieProvider {
    @Override
    public void getMovies(int pSortCriteria, final ICallbackListener<List<Movie>> pCallback) {
        String sortUrl = "";
        if (pSortCriteria == Constants.SORT_POPULARITY) {
            sortUrl = String.format(Constants.API_DISCOVER_URL, Constants.POPULARITY_PARAM, Constants.API_KEY);
        } else if (pSortCriteria == Constants.SORT_RATING) {
            sortUrl = String.format(Constants.API_DISCOVER_URL, Constants.RATING_PARAM, Constants.API_KEY);
        }else if(pSortCriteria ==  Constants.SORT_SAVE){

//            Iterator<Movie> savedMovies = Movie.findAll(Movie.class);
//            if(pCallback!=null){
//                List<Movie> movies = new ArrayList<>();
//                while (savedMovies.hasNext()){
//                    movies.add(savedMovies.next());
//                }
//                pCallback.onComplete(movies);
//            }
            return;
        }
        else {
            pCallback.onError(new MovieException(String.format("Sort type %s is not supported ", pSortCriteria)));
            return;
        }

        Type listType = new TypeToken<MovieResponse>() {
        }.getType();
        Request<MovieResponse> request = new GsonRequest<>(sortUrl, listType, null, new Response.Listener<MovieResponse>() {
            @Override
            public void onResponse(MovieResponse response) {
                if(response !=null) {
                    pCallback.onComplete(response.Movies);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pCallback.onError(new MovieException("OOPs! Something went wrong"));
            }
        });
        MovieApp.addRequest(request, "movielist_" + pSortCriteria);
    }

    @Override
    public Movie getMovieDetail(String id) {
        return null;
    }

    @Override
    public void getMovieTrailer(String id,final ICallbackListener<List<Trailers>> pCallback) {
        String url = String.format(Constants.API_TRAILER_URL,id,Constants.API_KEY);
        Type listType = new TypeToken<List<Trailers>>() {
        }.getType();
        Request<List<Trailers>> request = new GsonRequest<>(url, listType, null, new Response.Listener<List<Trailers>>() {
            @Override
            public void onResponse(List<Trailers> response) {
                if(response !=null) {
                    pCallback.onComplete(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pCallback.onError(new MovieException("OOPs! Something went wrong"));
            }
        });
        MovieApp.addRequest(request, "movietrailer_" + id);
    }

    @Override
    public void getReviews(String id,final ICallbackListener<ReviewResponse> pCallback) {
        String url = String.format(Constants.API_TRAILER_URL,id,Constants.API_KEY);
         Type listType = new TypeToken<ReviewResponse>() {
        }.getType();
        Request<ReviewResponse> request = new GsonRequest<>(url, listType, null, new Response.Listener<ReviewResponse>() {
            @Override
            public void onResponse(ReviewResponse response) {
                if(response !=null) {
                    pCallback.onComplete(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pCallback.onError(new MovieException("OOPs! Something went wrong"));
            }
        });
        MovieApp.addRequest(request, "moviereviews_" + id);
    }


}
