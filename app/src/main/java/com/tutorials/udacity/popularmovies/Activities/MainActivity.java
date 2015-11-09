package com.tutorials.udacity.popularmovies.Activities;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tutorials.udacity.popularmovies.Fragments.MovieDetailFragment;
import com.tutorials.udacity.popularmovies.Fragments.MovieListFragment;
import com.tutorials.udacity.popularmovies.Fragments.MovieTabFragment;
import com.tutorials.udacity.popularmovies.Interfaces.IMovieListFragmentListener;
import com.tutorials.udacity.popularmovies.Models.Movie;
import com.tutorials.udacity.popularmovies.Providers.SharedPreferenceProvider;
import com.tutorials.udacity.popularmovies.R;
import com.tutorials.udacity.popularmovies.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMovieListFragmentListener {

    MovieListFragment movieListFragment;
    MovieDetailFragment movieDetailFragment;
    static final String BUNDLE_SORTPREFERENCE = "sortPreference";
    int sortPreference = Constants.SORT_POPULARITY;
    static final String BUNDLE_SELECTED_POSITION = "selectedPosition";
    int selectedPosition = 0;
    MenuItem favoriteMenu;
    List<Movie> mMoviesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            if (getResources().getBoolean(R.bool.isPhone)) {
                getSupportFragmentManager().beginTransaction().replace(R.id.listContainer, new MovieTabFragment(), Constants.FRAGMENT_MOVIE_LIST_TAG).commit();
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.listContainer, MovieListFragment.newInstance(Constants.SORT_POPULARITY, this), Constants.FRAGMENT_MOVIE_LIST_TAG).commit();
            }
        } else {
            sortPreference = savedInstanceState.getInt(BUNDLE_SORTPREFERENCE);
            selectedPosition = savedInstanceState.getInt(BUNDLE_SELECTED_POSITION);
            movieListFragment = (MovieListFragment) getSupportFragmentManager().findFragmentByTag(Constants.FRAGMENT_MOVIE_LIST_TAG);
            if (movieListFragment != null) {
                movieListFragment.setSelectedPosition(selectedPosition);
            }

        }
        if(getResources().getBoolean(R.bool.isPhone)){
            setTitle("Movies");
        }
        else{
            if(SharedPreferenceProvider.get().get(Constants.SP_ISTIMEUSER,true)){
                Toast.makeText(this,R.string.firsttime,Toast.LENGTH_LONG).show();
                SharedPreferenceProvider.get().save(Constants.SP_ISTIMEUSER,false);
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(!getResources().getBoolean(R.bool.isPhone)) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            favoriteMenu = menu.findItem(R.id.action_fav);
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_popularity) {
            selectedPosition = 0;
            sortPreference = Constants.SORT_POPULARITY;
            initializeListFragment();
            return true;
        } else if (id == R.id.action_votes) {
            selectedPosition = 0;
            sortPreference = Constants.SORT_RATING;
            initializeListFragment();
            return true;
        } else if (id == R.id.action_fav) {
            selectedPosition = 0;
            sortPreference = Constants.SORT_SAVE;
            initializeListFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeListFragment() {
        movieListFragment = MovieListFragment.newInstance(sortPreference, this);
        getSupportFragmentManager().beginTransaction().replace(R.id.listContainer, movieListFragment, Constants.FRAGMENT_MOVIE_LIST_TAG).commit();
        //set the title of activity based on selected sort
        if (sortPreference == Constants.SORT_SAVE) {
           setTitle(R.string.favorite_settings);
        } else if (sortPreference == Constants.SORT_RATING) {
            setTitle(R.string.votes_settings);
        } else {
            setTitle(R.string.popular_settings);
        }
    }

    private void initializeDetailFragment(Movie pMovie) {
        movieDetailFragment = (MovieDetailFragment) getSupportFragmentManager().findFragmentByTag(Constants.FRAGMENT_MOVIE_DETAIL_TAG);
        if (movieDetailFragment == null) {
            movieDetailFragment = MovieDetailFragment.getInstance(pMovie, this);
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.detailContainer, movieDetailFragment, Constants.FRAGMENT_MOVIE_DETAIL_TAG).commit();
        } else {
            movieDetailFragment.setMovie(pMovie);
            movieDetailFragment.bind();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_SORTPREFERENCE, sortPreference);
        outState.putInt(BUNDLE_SELECTED_POSITION, selectedPosition);
    }


    @Override
    public void MovieListLoaded(List<Movie> pMovies) {
        mMoviesList = pMovies;
        if (!getResources().getBoolean(R.bool.isPhone)) {
            if (mMoviesList.size() > 0) {
                if (mMoviesList.size() < selectedPosition) {
                    selectedPosition = 0;
                }
                initializeDetailFragment(pMovies.get(selectedPosition));
            } else {
                //hide favorite menu
                if(favoriteMenu != null){
                    favoriteMenu.setVisible(false);
                }
                sortPreference = Constants.SORT_POPULARITY;
                Toast.makeText(this,"You have no more saved movies. Showing the Most Popular movies.", Toast.LENGTH_LONG).show();
                initializeListFragment();

            }
        }
    }

    @Override
    public void onMovieRemoved(Movie pMovie) {
        //rebind the list if sorted preference is favorite
        if (sortPreference == Constants.SORT_SAVE) {
            selectedPosition = 0;
            movieListFragment = (MovieListFragment) getSupportFragmentManager().findFragmentByTag(Constants.FRAGMENT_MOVIE_LIST_TAG);
            if (movieListFragment != null) {
                movieListFragment.bindData();
            } else {
                initializeListFragment();
            }
        }

    }

    @Override
    public void onMovieAdded(Movie pMovie) {
        if (!getResources().getBoolean(R.bool.isPhone)) {
            if (favoriteMenu != null) {
                favoriteMenu.setVisible(true);
            }
        }
    }

    @Override
    public void onMovieItemClicked(Movie pMovie, int position, View imageview) {
        selectedPosition = position;
        if (getResources().getBoolean(R.bool.isPhone)) {
            Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra(Constants.BUNDLE_MOVIE, pMovie);
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this, imageview, "profile");
            ActivityCompat.startActivity(this, intent, options.toBundle());
        } else {
            initializeDetailFragment(pMovie);
        }
    }


}
