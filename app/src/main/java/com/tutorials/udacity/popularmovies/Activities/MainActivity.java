package com.tutorials.udacity.popularmovies.Activities;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.tutorials.udacity.popularmovies.Fragments.MovieDetailFragment;
import com.tutorials.udacity.popularmovies.Fragments.MovieListFragment;
import com.tutorials.udacity.popularmovies.Interfaces.ICallbackListener;
import com.tutorials.udacity.popularmovies.Models.Movie;
import com.tutorials.udacity.popularmovies.Models.MovieException;
import com.tutorials.udacity.popularmovies.Providers.MovieProvider;
import com.tutorials.udacity.popularmovies.R;
import com.tutorials.udacity.popularmovies.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieListFragment.IMovieClickListener {

    FrameLayout listContainer;
    MovieListFragment movieListFragment;
    List<Movie> movieList = new ArrayList<>();
    MovieDetailFragment movieDetailFragment;
    static final String BUNDLE_SORTPREFERENCE = "sortPreference";
    int sortPreference=  Constants.SORT_POPULARITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listContainer = (FrameLayout) findViewById(R.id.listContainer);
        if (savedInstanceState == null) {
            initializeListFragment();
            loadData();
        }
        else
        {
            sortPreference= savedInstanceState.getInt(BUNDLE_SORTPREFERENCE);
        }

    }

    private void initializeListFragment() {
        movieListFragment = (MovieListFragment) getSupportFragmentManager().findFragmentByTag(Constants.FRAGMENT_MOVIE_LIST_TAG);
        if (movieListFragment == null) {
            //initialize with blank list
            movieListFragment = MovieListFragment.newInstance(movieList, this,sortPreference);
            getSupportFragmentManager().beginTransaction().replace(R.id.listContainer, movieListFragment, Constants.FRAGMENT_MOVIE_LIST_TAG).commit();
        } else {
            movieListFragment.setMovieList(movieList,sortPreference);
        }

    }


    private void initializeDetailFragment(Movie pMovie) {
        movieDetailFragment = (MovieDetailFragment) getSupportFragmentManager().findFragmentByTag(Constants.FRAGMENT_MOVIE_DETAIL_TAG);
        if (movieDetailFragment == null) {
            movieDetailFragment = MovieDetailFragment.getInstance(pMovie);
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.detailContainer, movieDetailFragment, Constants.FRAGMENT_MOVIE_DETAIL_TAG).commit();
        } else {
            movieDetailFragment.setMovie(pMovie);
            movieDetailFragment.bind();
        }

    }

    private void loadData() {
        new MovieProvider().getMovies(sortPreference, new ICallbackListener<List<Movie>>() {
            @Override
            public void onComplete(List<Movie> result) {
                movieList = result;
                if (movieListFragment == null)
                    initializeListFragment();
                else {
                    movieListFragment.setMovieList(movieList,sortPreference);
                }
                movieListFragment.bind();
                if (!getResources().getBoolean(R.bool.isPhone)) {
                    initializeDetailFragment(movieList.get(0));
                }
            }

            @Override
            public void onError(MovieException exception) {
                exception.showToast(Toast.LENGTH_LONG);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id== R.id.action_popularity){
            sortPreference = Constants.SORT_POPULARITY;
            loadData();
            return true;
        }else if(id == R.id.action_votes){
            sortPreference = Constants.SORT_RATING;
            loadData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieItemClicked(Movie pMovie, int position, View pImageView) {
        if (pMovie != null) {
            if (getResources().getBoolean(R.bool.isPhone)) {
                Intent intent = new Intent(this, MovieDetailActivity.class);
                intent.putExtra(Constants.BUNDLE_MOVIE, pMovie);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(this, (View) pImageView, "profile");
                ActivityCompat.startActivity(this, intent, options.toBundle());
            } else {
                initializeDetailFragment(pMovie);
                movieDetailFragment.bind();

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_SORTPREFERENCE,sortPreference);
    }
}
