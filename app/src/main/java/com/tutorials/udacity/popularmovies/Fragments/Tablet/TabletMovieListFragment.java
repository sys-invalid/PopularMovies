package com.tutorials.udacity.popularmovies.Fragments.Tablet;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tutorials.udacity.popularmovies.Activities.MovieDetailActivity;
import com.tutorials.udacity.popularmovies.Adapters.MovieListAdapter;
import com.tutorials.udacity.popularmovies.CustomViews.ExtendedRecyclerView;
import com.tutorials.udacity.popularmovies.Fragments.MovieListFragment;
import com.tutorials.udacity.popularmovies.Interfaces.ICallbackListener;
import com.tutorials.udacity.popularmovies.Interfaces.IMovieClickListener;
import com.tutorials.udacity.popularmovies.Models.Movie;
import com.tutorials.udacity.popularmovies.Models.MovieException;
import com.tutorials.udacity.popularmovies.Providers.MovieProvider;
import com.tutorials.udacity.popularmovies.R;
import com.tutorials.udacity.popularmovies.Utils.Constants;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabletMovieListFragment extends Fragment implements IMovieClickListener {

    //List view for movies
    @Bind(R.id.lstMoviesPopular)
    ExtendedRecyclerView lstMoviesPopular;

    //list  view for rated
    @Bind(R.id.lstMoviesRated)
    ExtendedRecyclerView lstMoviesRated;

    //List view for favorite movies
    @Bind(R.id.lstMoviesFavorite)
    ExtendedRecyclerView lstMoviesFavorite;

    RecyclerView.LayoutManager mLayoutManager;

    //Adapters for listview
    MovieListAdapter mPopularAdapter;
    MovieListAdapter mRatedAdapter;
    MovieListAdapter mSavedAdapter;


    public TabletMovieListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_movie_list_tablet, container, false);
        ButterKnife.bind(this, view);
        initlayoutmanager(lstMoviesFavorite);
        initlayoutmanager(lstMoviesPopular);
        initlayoutmanager(lstMoviesRated);
        mPopularAdapter = new MovieListAdapter(this,Constants.SORT_POPULARITY);
        mRatedAdapter = new MovieListAdapter(this,Constants.SORT_RATING);
        mSavedAdapter = new MovieListAdapter(this,Constants.SORT_POPULARITY);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(mPopularAdapter, lstMoviesPopular, Constants.SORT_POPULARITY);
        loadData(mRatedAdapter,lstMoviesRated,Constants.SORT_RATING);
        loadData(mSavedAdapter,lstMoviesFavorite,Constants.SORT_POPULARITY);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initlayoutmanager(ExtendedRecyclerView lvMovie){
        RecyclerView.LayoutManager  mLayoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.HORIZONTAL, false);
        lvMovie.setLayoutManager(mLayoutManager);
    }

    private void loadData(final MovieListAdapter movieListAdapter,final ExtendedRecyclerView lvMovie, final int sortPreference ) {
        lvMovie.loadData(sortPreference, new ICallbackListener<List<Movie>>() {
            @Override
            public void onComplete(List<Movie> movieList) {
                    movieListAdapter.setMovieList(movieList,sortPreference);
                    if (lvMovie.getAdapter() == null) {
                        lvMovie.setAdapter(movieListAdapter);
                    } else {
                        movieListAdapter.notifyDataSetChanged();
                    }
            }

            @Override
            public void onError(MovieException exception) {

            }
        });
    }

    @Override
    public void onMovieItemClicked(Movie pMovie, int position, View imageview) {
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra(Constants.BUNDLE_MOVIE, pMovie);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(), (View) imageview, "profile");
        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
    }
}
