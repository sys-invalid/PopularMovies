package com.tutorials.udacity.popularmovies.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tutorials.udacity.popularmovies.Activities.MainActivity;
import com.tutorials.udacity.popularmovies.Adapters.MovieListAdapter;
import com.tutorials.udacity.popularmovies.Models.Movie;
import com.tutorials.udacity.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListFragment extends Fragment {
    RecyclerView lvMovie;
    List<Movie> movieList = new ArrayList<>();
    MovieListAdapter movieListAdapter;
    int sortPreference;

    public void setMovieClickListener(IMovieClickListener movieClickListener) {
        this.movieClickListener = movieClickListener;
    }

    IMovieClickListener movieClickListener;


    public void setMovieList(List<Movie> movieList, int sort) {
        this.movieList = movieList;
        this.sortPreference = sort;

    }


    public MovieListFragment() {
        // Required empty public constructor
    }

    public static MovieListFragment newInstance(List<Movie> pMovies, IMovieClickListener pClickListener, int sort) {
        MovieListFragment movieListFragment = new MovieListFragment();
        movieListFragment.setMovieList(pMovies,sort);
        movieListFragment.setMovieClickListener(pClickListener);
        return movieListFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(getActivity() instanceof  MainActivity) {
            setMovieClickListener((MainActivity) getActivity());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        lvMovie = (RecyclerView) rootView.findViewById(R.id.lstMovies);
        RecyclerView.LayoutManager mLayoutManager;
        if (getResources().getBoolean(R.bool.isPhone)) {
            mLayoutManager = new GridLayoutManager(getActivity(), 3);
        } else {
            mLayoutManager = new LinearLayoutManager(getActivity());
        }
        lvMovie.setLayoutManager(mLayoutManager);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        bind();

    }

    public void bind() {
        if (movieListAdapter == null) {
            movieListAdapter = new MovieListAdapter(movieList, movieClickListener,sortPreference);
            lvMovie.setAdapter(movieListAdapter);
        } else {

            movieListAdapter.setMovieList(movieList,sortPreference);
            if (lvMovie.getAdapter() == null) {
                lvMovie.setAdapter(movieListAdapter);
            } else {
                movieListAdapter.notifyDataSetChanged();
            }
        }

    }

    public interface IMovieClickListener {
        void onMovieItemClicked(Movie pMovie, int position, View imageview);
    }

}
