package com.tutorials.udacity.popularmovies.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.tutorials.udacity.popularmovies.Fragments.MovieListFragment;
import com.tutorials.udacity.popularmovies.Interfaces.ICallbackListener;
import com.tutorials.udacity.popularmovies.Models.Movie;
import com.tutorials.udacity.popularmovies.Models.MovieException;
import com.tutorials.udacity.popularmovies.Providers.MovieProvider;
import com.tutorials.udacity.popularmovies.R;
import com.tutorials.udacity.popularmovies.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieListFragment.IMovieClickListener{

    FrameLayout listContainer;
    MovieListFragment movieListFragment;
    List<Movie> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listContainer = (FrameLayout) findViewById(R.id.listContainer);

        initializeFragment();
    }

    private void initializeFragment() {
        movieListFragment = (MovieListFragment) getSupportFragmentManager().findFragmentByTag(Constants.FRAGMENT_MOVIE_LIST_TAG);
        if (movieListFragment == null) {
            //initialize with blank list
            movieListFragment = MovieListFragment.newInstance(movieList,this);
            getSupportFragmentManager().beginTransaction().replace(R.id.listContainer, movieListFragment, Constants.FRAGMENT_MOVIE_LIST_TAG).commit();
        }else{
            movieListFragment.setMovieList(movieList);
        }

    }

    private void loadData(int sortCriteria){
        new MovieProvider().getMovies(sortCriteria, new ICallbackListener<List<Movie>>() {
            @Override
            public void onComplete(List<Movie> result) {
                movieList = result;
                initializeFragment();
                movieListFragment.bind();
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
        loadData(Constants.SORT_POPULARITY);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieItemClicked(Movie pMovie, int position) {
        if(pMovie !=null){
            Intent intent = new Intent(this,MovieDetailActivity.class);
            intent.putExtra(Constants.BUNDLE_MOVIE,pMovie);
            startActivity(intent);
        }
    }
}
