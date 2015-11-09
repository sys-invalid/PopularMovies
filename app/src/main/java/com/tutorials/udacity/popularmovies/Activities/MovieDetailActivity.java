package com.tutorials.udacity.popularmovies.Activities;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.facebook.stetho.Stetho;
import com.squareup.picasso.Picasso;
import com.tutorials.udacity.popularmovies.Fragments.MovieDetailFragment;
import com.tutorials.udacity.popularmovies.Models.Movie;
import com.tutorials.udacity.popularmovies.R;
import com.tutorials.udacity.popularmovies.Utils.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    @Nullable
    @Bind(R.id.ivMoviePoster)
    ImageView ivMoviePoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_movie_detail);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            Movie movie = (Movie) b.get(Constants.BUNDLE_MOVIE);
            MovieDetailFragment detailFragment = MovieDetailFragment.getInstance(movie,null);
            getSupportFragmentManager().beginTransaction().replace(R.id.listContainer, detailFragment).commit();
            if(getResources().getBoolean(R.bool.isPhone)){
                setTitle(movie.Title);
            }
        }


        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return false;
    }
}
