package com.tutorials.udacity.popularmovies.CustomViews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.Toast;

import com.tutorials.udacity.popularmovies.Interfaces.ICallbackListener;
import com.tutorials.udacity.popularmovies.Models.Movie;
import com.tutorials.udacity.popularmovies.Models.MovieException;
import com.tutorials.udacity.popularmovies.Providers.MovieProvider;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by asharma on 9/1/15.
 */
public class ExtendedRecyclerView extends RecyclerView{
    public ExtendedRecyclerView(Context context) {
        super(context);
        ButterKnife.bind(this);
    }

    public ExtendedRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ButterKnife.bind(this);
    }

    public ExtendedRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ButterKnife.bind(this);
    }

    public void loadData(int sortPreference,ICallbackListener<List<Movie>> callbackListener){
        new MovieProvider().getMovies(sortPreference, callbackListener);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

}
