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
import com.tutorials.udacity.popularmovies.Fragments.MovieTabFragment;
import com.tutorials.udacity.popularmovies.Fragments.Tablet.TabletMovieListFragment;
import com.tutorials.udacity.popularmovies.Interfaces.ICallbackListener;
import com.tutorials.udacity.popularmovies.Models.Movie;
import com.tutorials.udacity.popularmovies.Models.MovieException;
import com.tutorials.udacity.popularmovies.Providers.MovieProvider;
import com.tutorials.udacity.popularmovies.R;
import com.tutorials.udacity.popularmovies.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    FrameLayout listContainer;
    //MovieListFragment movieListFragment;
    TabletMovieListFragment movieListFragment;
    List<Movie> movieList = new ArrayList<>();
    MovieDetailFragment movieDetailFragment;
    static final String BUNDLE_SORTPREFERENCE = "sortPreference";
    int sortPreference=  Constants.SORT_POPULARITY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if(getResources().getBoolean(R.bool.isPhone)){
            getSupportFragmentManager().beginTransaction().replace(R.id.listContainer, new MovieTabFragment(), Constants.FRAGMENT_MOVIE_LIST_TAG).commit();

        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.listContainer, new TabletMovieListFragment(), Constants.FRAGMENT_MOVIE_LIST_TAG).commit();
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putInt(BUNDLE_SORTPREFERENCE,sortPreference);
    }
}
