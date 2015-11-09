package com.tutorials.udacity.popularmovies.Fragments;


import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
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

import com.tutorials.udacity.popularmovies.Activities.MainActivity;
import com.tutorials.udacity.popularmovies.Activities.MovieDetailActivity;
import com.tutorials.udacity.popularmovies.Adapters.MovieListAdapter;
import com.tutorials.udacity.popularmovies.CustomViews.ExtendedRecyclerView;
import com.tutorials.udacity.popularmovies.Database.FavoriteColumns;
import com.tutorials.udacity.popularmovies.Database.FavoriteContentProvider;
import com.tutorials.udacity.popularmovies.Interfaces.ICallbackListener;
import com.tutorials.udacity.popularmovies.Interfaces.IMovieListFragmentListener;
import com.tutorials.udacity.popularmovies.Models.Movie;
import com.tutorials.udacity.popularmovies.Models.MovieException;
import com.tutorials.udacity.popularmovies.R;
import com.tutorials.udacity.popularmovies.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListFragment extends Fragment {
    @Bind(R.id.lstMovies)
    ExtendedRecyclerView lvMovie;
    MovieListAdapter movieListAdapter;
    static final String BUNDLE_SELECTED_POSITION = "selectedPosition";

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    int selectedPosition = 0;


    public IMovieListFragmentListener getMoviesLoadedListener() {
        return moviesLoadedListener;
    }

    public void setMoviesLoadedListener(IMovieListFragmentListener moviesLoadedListener) {
        this.moviesLoadedListener = moviesLoadedListener;
    }

    IMovieListFragmentListener moviesLoadedListener;
    int sortPreference;


    public void setSort(int sort) {
        this.sortPreference = sort;

    }


    public MovieListFragment() {
        // Required empty public constructor
    }

    public static MovieListFragment newInstance(int sort, IMovieListFragmentListener pMovieLoadedListener) {
        MovieListFragment movieListFragment = new MovieListFragment();
        movieListFragment.setSort(sort);
        movieListFragment.setMoviesLoadedListener(pMovieLoadedListener);
        return movieListFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, rootView);
        RecyclerView.LayoutManager mLayoutManager;
        if (getResources().getBoolean(R.bool.isPhone)) {
            mLayoutManager = new GridLayoutManager(getActivity(), 2);
        } else {
            mLayoutManager = new LinearLayoutManager(getActivity());
        }
        lvMovie.setLayoutManager(mLayoutManager);
        movieListAdapter = new MovieListAdapter(moviesLoadedListener, sortPreference);
        lvMovie.setAdapter(movieListAdapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        bindData();


    }

    public void bindData() {
        if (sortPreference != Constants.SORT_SAVE) {
            lvMovie.loadData(sortPreference, new ICallbackListener<List<Movie>>() {
                @Override
                public void onComplete(List<Movie> movieList) {
                    if (movieListAdapter == null) {
                        movieListAdapter = new MovieListAdapter(movieList, moviesLoadedListener, sortPreference);
                        movieListAdapter.setSelectedItems(selectedPosition);
                        lvMovie.setAdapter(movieListAdapter);
                    } else {
                        movieListAdapter.setMovieList(movieList, sortPreference);
                        movieListAdapter.setSelectedItems(selectedPosition);
                        if (lvMovie.getAdapter() == null) {
                            lvMovie.setAdapter(movieListAdapter);
                        } else {
                            movieListAdapter.notifyDataSetChanged();
                        }
                    }
                    if (moviesLoadedListener != null) {
                        moviesLoadedListener.MovieListLoaded(movieList);
                    }
                }

                @Override
                public void onError(MovieException exception) {

                }
            });
        } else {
            new AsyncTask<Void, Void, List<Movie>>() {
                @Override
                protected List<Movie> doInBackground(Void... params) {
                    List<Movie> movies = new ArrayList<Movie>();
                    Cursor cursor = null;
                    try {
                        cursor = getActivity().getContentResolver().query(FavoriteContentProvider.Favorites.CONTENT_URI,
                                null,
                                null,
                                null, null);
                        List<Long> savedMovies = new ArrayList<Long>();
                        while (cursor.moveToNext()) {
                            Movie movie = new Movie();
                            movie.OverView = cursor.getString(cursor.getColumnIndex(FavoriteColumns.COLUMN_NAME_OVERVIEW));
                            movie.Title = cursor.getString(cursor.getColumnIndex(FavoriteColumns.COLUMN_NAME_TITLE));
                            movie.Popularity = cursor.getFloat(cursor.getColumnIndex(FavoriteColumns.COLUMN_NAME_POPULARITY));
                            movie.PosterPath = cursor.getString(cursor.getColumnIndex(FavoriteColumns.COLUMN_NAME_POSTERPATH));
                            movie.VoteAvg = cursor.getFloat(cursor.getColumnIndex(FavoriteColumns.COLUMN_NAME_VOTEAVG));
                            movie.VoteCount = cursor.getInt(cursor.getColumnIndex(FavoriteColumns.COLUMN_NAME_VOTECOUNT));
                            movie.Id = cursor.getLong(cursor.getColumnIndex(FavoriteColumns.COLUMN_NAME_MOVIEID));
                            movie.ReleaseDate = cursor.getString(cursor.getColumnIndex(FavoriteColumns.COLUMN_NAME_RELEASEDATE));
                            movies.add(movie);
                        }
                    } catch (Exception e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "Error getting saved movies", Toast.LENGTH_LONG).show();
                            }
                        });

                    } finally {
                        // close the cursor
                        if (cursor != null && !cursor.isClosed()) {
                            cursor.close();
                        }
                    }
                    return movies;
                }

                @Override
                protected void onPostExecute(List<Movie> movieList) {
                    super.onPostExecute(movieList);
                    if (movieListAdapter == null) {
                        movieListAdapter = new MovieListAdapter(movieList, moviesLoadedListener, sortPreference);
                        lvMovie.setAdapter(movieListAdapter);
                    } else {
                        movieListAdapter.setMovieList(movieList, sortPreference);
                        if (lvMovie.getAdapter() == null) {
                            lvMovie.setAdapter(movieListAdapter);
                        } else {
                            movieListAdapter.notifyDataSetChanged();
                        }
                    }

                    if (moviesLoadedListener != null) {
                        moviesLoadedListener.MovieListLoaded(movieList);
                    }
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }


}
