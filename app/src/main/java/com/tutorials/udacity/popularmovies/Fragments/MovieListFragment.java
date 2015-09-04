package com.tutorials.udacity.popularmovies.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import com.tutorials.udacity.popularmovies.Activities.MainActivity;
import com.tutorials.udacity.popularmovies.Activities.MovieDetailActivity;
import com.tutorials.udacity.popularmovies.Adapters.MovieListAdapter;
import com.tutorials.udacity.popularmovies.CustomViews.ExtendedRecyclerView;
import com.tutorials.udacity.popularmovies.Database.MoviesTable;
import com.tutorials.udacity.popularmovies.Interfaces.ICallbackListener;
import com.tutorials.udacity.popularmovies.Interfaces.IMovieClickListener;
import com.tutorials.udacity.popularmovies.Models.Movie;
import com.tutorials.udacity.popularmovies.Models.MovieException;
import com.tutorials.udacity.popularmovies.Providers.FavoriteProvider;
import com.tutorials.udacity.popularmovies.R;
import com.tutorials.udacity.popularmovies.Utils.Constants;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListFragment extends Fragment implements IMovieClickListener {
    @Bind(R.id.lstMovies)
    ExtendedRecyclerView lvMovie;
    MovieListAdapter movieListAdapter;
    int sortPreference;



    public void setSort(int sort) {
        this.sortPreference = sort;

    }


    public MovieListFragment() {
        // Required empty public constructor
    }

    public static MovieListFragment newInstance( int sort) {
        MovieListFragment movieListFragment = new MovieListFragment();
        movieListFragment.setSort(sort);
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
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        lvMovie.setLayoutManager(mLayoutManager);
        movieListAdapter = new MovieListAdapter(MovieListFragment.this,sortPreference);
        lvMovie.setAdapter(movieListAdapter);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        bindData();

    }

    private void bindData() {
        if(sortPreference !=  Constants.SORT_SAVE) {
            lvMovie.loadData(sortPreference, new ICallbackListener<List<Movie>>() {
                @Override
                public void onComplete(List<Movie> movieList) {
                    if (movieListAdapter == null) {
                        movieListAdapter = new MovieListAdapter(movieList, MovieListFragment.this, sortPreference);
                        lvMovie.setAdapter(movieListAdapter);
                    } else {
                        movieListAdapter.setMovieList(movieList, sortPreference);
                        if (lvMovie.getAdapter() == null) {
                            lvMovie.setAdapter(movieListAdapter);
                        } else {
                            movieListAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onError(MovieException exception) {

                }
            });
        }else{


            new AsyncTask<Void,Void,List<Movie>>(){

                @Override
                protected List<Movie> doInBackground(Void... params) {
                    List<Movie> movies = new ArrayList<Movie>();
                    Cursor cursor =null;
                    try {
                       cursor=  getActivity().getContentResolver().query(FavoriteProvider.MOVIE_URI, null, null, null, null);
                        while (cursor.moveToNext()) {
                            Movie movie = new Movie();
                            movie.OverView = cursor.getString(cursor.getColumnIndex(MoviesTable.MovieEntry.COLUMN_NAME_OVERVIEW));
                            movie.Title = cursor.getString(cursor.getColumnIndex(MoviesTable.MovieEntry.COLUMN_NAME_Title));
                            movie.Popularity = cursor.getFloat(cursor.getColumnIndex(MoviesTable.MovieEntry.COLUMN_NAME_POPULARITY));
                            movie.PosterPath = cursor.getString(cursor.getColumnIndex(MoviesTable.MovieEntry.COLUMN_NAME_POSTERPATH));
                            movie.VoteAvg = cursor.getFloat(cursor.getColumnIndex(MoviesTable.MovieEntry.COLUMN_NAME_VOTEAVG));
                            movie.VoteCount = cursor.getInt(cursor.getColumnIndex(MoviesTable.MovieEntry.COLUMN_NAME_VOTECOUNT));
                            movie.Id = cursor.getString(cursor.getColumnIndex(MoviesTable.MovieEntry.COLUMN_NAME_ID));
                            movie.ReleaseDate = cursor.getString(cursor.getColumnIndex(MoviesTable.MovieEntry.COLUMN_NAME_RELEASEDATE));
                            movies.add(movie);
                        }
                    } finally {
                        // close the cursor
                       if(cursor !=null && !cursor.isClosed()){
                           cursor.close();
                       }
                    }

                    return movies;
                }

                @Override
                protected void onPostExecute(List<Movie> movieList) {
                    super.onPostExecute(movieList);
                    if (movieListAdapter == null) {
                        movieListAdapter = new MovieListAdapter(movieList, MovieListFragment.this, sortPreference);
                        lvMovie.setAdapter(movieListAdapter);
                    } else {
                        movieListAdapter.setMovieList(movieList, sortPreference);
                        if (lvMovie.getAdapter() == null) {
                            lvMovie.setAdapter(movieListAdapter);
                        } else {
                            movieListAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
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
