package com.tutorials.udacity.popularmovies.Fragments;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.tutorials.udacity.popularmovies.Models.Movie;
import com.tutorials.udacity.popularmovies.Providers.FavoriteProvider;
import com.tutorials.udacity.popularmovies.Providers.SharedPreferenceProvider;
import com.tutorials.udacity.popularmovies.R;
import com.tutorials.udacity.popularmovies.Utils.Constants;

import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailFragment extends Fragment {

    Movie movie;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.tvOverView)
    TextView tvOverView;
    @Bind(R.id.tvReleaseDate)
    TextView tvReleaseDate;
    @Bind(R.id.tvPopularity)
    TextView tvPopularity;
    @Bind(R.id.tvRatings)
    TextView tvAverage;
    @Bind(R.id.ivMoviePoster)
    ImageView ivMoviePoster;
    @Bind(R.id.ivSaveFavorite)
    ImageView ivSaveFavorite;

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public static MovieDetailFragment getInstance(Movie pMovie) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setMovie(pMovie);

        return fragment;
    }


    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.ivSaveFavorite)
    public void onSaveClicked() {
        Set<String> savedMovies = SharedPreferenceProvider.get().get(Constants.SHARED_PREF_FAV);
        if (savedMovies.contains(movie.Id)) {
            SharedPreferenceProvider.get().removeFromList(Constants.SHARED_PREF_FAV, movie.Id);
            ivSaveFavorite.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_add));

        } else {
            SharedPreferenceProvider.get().saveToList(Constants.SHARED_PREF_FAV, movie.Id);
            ivSaveFavorite.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_delete));
           getActivity().getContentResolver().insert(FavoriteProvider.MOVIE_URI, movie.toContentValues());
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        bind();
    }

    public void bind() {
        if (movie != null) {
            if (movie.PosterPath != null && movie.PosterPath != "") {
                Picasso.with(getActivity()).load(movie.getThumbnailUrl("w342")).transform(transformation).into(ivMoviePoster);
            }
            double roundOff = Math.round(movie.Popularity * 100.0) / 100.0;
            tvPopularity.setText(roundOff + "%");
            tvTitle.setText(movie.Title);
            tvAverage.setText(movie.VoteAvg + "");
            tvOverView.setText(movie.OverView);
            tvReleaseDate.setText(movie.ReleaseDate);
            // tvPopularity.setText(movie.Popularity + "");
            Set<String> savedMovies = SharedPreferenceProvider.get().get(Constants.SHARED_PREF_FAV);
            if (savedMovies.contains(movie.Id)) {
                ivSaveFavorite.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_delete));
            } else {
                ivSaveFavorite.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_add));
            }
        }
    }

    Transformation transformation = new Transformation() {

        @Override
        public Bitmap transform(Bitmap source) {
            int targetWidth = ivMoviePoster.getMeasuredWidth();
            double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            int targetHeight = (int) (targetWidth * aspectRatio);
            if(targetHeight > 0 && targetWidth > 0) {
                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }
           return source;
        }

        @Override
        public String key() {
            return "transformation" + " desiredWidth";
        }
    };
}
