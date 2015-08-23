package com.tutorials.udacity.popularmovies.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tutorials.udacity.popularmovies.Models.Movie;
import com.tutorials.udacity.popularmovies.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailFragment extends Fragment {

    Movie movie;
    TextView tvTitle;
    TextView tvOverView;
    TextView tvReleaseDate;
    TextView tvPopularity;
    TextView tvAverage;
    ImageView ivMoviePoster;
    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public static MovieDetailFragment getInstance(Movie pMovie){
        MovieDetailFragment  fragment = new MovieDetailFragment();
        fragment.setMovie(pMovie);
        return fragment;
    }


    public MovieDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_movie_detail, container, false);
        tvTitle = (TextView)rootView.findViewById(R.id.tvTitle);
        tvOverView = (TextView)rootView.findViewById(R.id.tvOverView);
        tvReleaseDate = (TextView)rootView.findViewById(R.id.tvReleaseDate);
        tvPopularity = (TextView)rootView.findViewById(R.id.tvPopularity);
        tvAverage = (TextView)rootView.findViewById(R.id.tvRatings);
        ivMoviePoster = (ImageView)rootView.findViewById(R.id.ivMoviePoster);

        if(movie != null){
            Picasso.with(getActivity()).load(movie.getThumbnailUrl("w500")).into(ivMoviePoster);
            tvTitle.setText(movie.Title);
            tvAverage.setText(movie.VoteAvg +"");
            tvOverView.setText(movie.OverView);
            tvReleaseDate.setText(movie.ReleaseDate);
            tvPopularity.setText(movie.Popularity+"");
        }

        return rootView;
    }
}
