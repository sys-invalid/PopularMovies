package com.tutorials.udacity.popularmovies.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.tutorials.udacity.popularmovies.Adapters.ReviewAdapter;
import com.tutorials.udacity.popularmovies.Adapters.TrailerListAdapter;
import com.tutorials.udacity.popularmovies.Database.FavoriteColumns;
import com.tutorials.udacity.popularmovies.Database.FavoriteContentProvider;
import com.tutorials.udacity.popularmovies.Extensions.CustomLayoutManager;
import com.tutorials.udacity.popularmovies.Interfaces.ICallbackListener;
import com.tutorials.udacity.popularmovies.Interfaces.IMovieListFragmentListener;
import com.tutorials.udacity.popularmovies.Interfaces.IMovieProvider;
import com.tutorials.udacity.popularmovies.Models.Movie;
import com.tutorials.udacity.popularmovies.Models.MovieException;
import com.tutorials.udacity.popularmovies.Models.ReviewResponse;
import com.tutorials.udacity.popularmovies.Models.TrailerResponse;
import com.tutorials.udacity.popularmovies.Providers.MovieProvider;
import com.tutorials.udacity.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailFragment extends Fragment {

    Movie movie;
    private ShareActionProvider mShareActionProvider;

    MenuItem shareMenuItem;
    TrailerResponse.Trailer firstTrailer;

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
    @Nullable
    @Bind(R.id.ivMoviePoster)
    ImageView ivMoviePoster;

    @Bind(R.id.lvTrailers)
    RecyclerView lvTrailers;

    @Bind(R.id.lvReviews)
    RecyclerView lvReviews;

    @Bind(R.id.tvTrailers)
    TextView tvTrailers;
    @Bind(R.id.tvReviews)
    TextView tvReviews;


    @Bind(R.id.fab)
    FloatingActionButton fab;

    ReviewAdapter mReviewAdapter;
    TrailerListAdapter mTrailerAdapter;

    public void setListener(IMovieListFragmentListener mListener) {
        this.mListener = mListener;
    }

    IMovieListFragmentListener mListener;


    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public static MovieDetailFragment getInstance(Movie pMovie, IMovieListFragmentListener mListener) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setMovie(pMovie);
        fragment.setListener(mListener);
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
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, rootView);
        CustomLayoutManager trailerLayoutManager = new CustomLayoutManager(getActivity());
        lvTrailers.setLayoutManager(trailerLayoutManager);

        CustomLayoutManager reviewsLayoutManager = new CustomLayoutManager(getActivity());
        lvReviews.setLayoutManager(reviewsLayoutManager);


        return rootView;
    }


    @OnClick(R.id.fab)
    public void onSaveClicked() {
        try {
            if (isMovieAlreadyFavorited()) {
                // long cursorId =  c.getLong(0);
                getActivity().getContentResolver().delete(FavoriteContentProvider.Favorites.CONTENT_URI,
                        FavoriteColumns.COLUMN_NAME_MOVIEID + "= ?",
                        new String[]{String.valueOf(movie.Id)});
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_unfav));
                if (mListener != null) {
                    mListener.onMovieRemoved(movie);
                }
            } else {
                getActivity().getContentResolver().insert(FavoriteContentProvider.Favorites.CONTENT_URI,
                        movie.toContentValues());
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav));
                if (mListener != null) {
                    mListener.onMovieAdded(movie);
                }
            }
        } catch (Exception e) {
            //TODO log or send details to remote server
            new MovieException(getString(R.string.error_save)).showToast(Toast.LENGTH_LONG);
        }
    }


    private boolean isMovieAlreadyFavorited() {
        Cursor c = getActivity().getContentResolver().query(FavoriteContentProvider.Favorites.CONTENT_URI,
                new String[]{FavoriteColumns.COLUMN_NAME_MOVIEID},
                FavoriteColumns.COLUMN_NAME_MOVIEID + "= ?",
                new String[]{String.valueOf(movie.Id)}, null);
        boolean isfavorited = c.getCount() > 0;
        c.close();
        return isfavorited;
    }

    @Override
    public void onResume() {
        super.onResume();
        bind();
    }

    public void bind() {
        if (movie != null) {
            if (movie.PosterPath != null && movie.PosterPath != "" && ivMoviePoster != null) {
                Picasso.with(getActivity()).load(movie.getThumbnailUrl("w342")).transform(transformation).into(ivMoviePoster);
            }
            double roundOff = Math.round(movie.Popularity * 100.0) / 100.0;
            tvPopularity.setText(roundOff + "%");
            tvTitle.setText(movie.Title);
            tvAverage.setText(movie.VoteAvg + "");
            tvOverView.setText(movie.OverView);
            tvReleaseDate.setText(movie.ReleaseDate);
            if (isMovieAlreadyFavorited()) {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_fav));
            } else {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_unfav));
            }
            //get reviews
            getReviews();
            getTrailers();

        }
    }

    private void getReviews() {
        IMovieProvider provider = new MovieProvider();
        provider.getReviews(movie.Id, new ICallbackListener<ReviewResponse>() {
            @Override
            public void onComplete(ReviewResponse result) {
                if (result != null && result.Results != null && result.Results.size() > 0) {
                    if (mReviewAdapter != null) {
                        mReviewAdapter.setReviews(result.Results);
                        mReviewAdapter.notifyDataSetChanged();
                    } else {
                        mReviewAdapter = new ReviewAdapter(result.Results);

                    }
                    lvReviews.setAdapter(mReviewAdapter);
                    tvReviews.setText(R.string.review);
                    lvReviews.setVisibility(View.VISIBLE);
                } else {
                    tvReviews.setText(R.string.noreviews);
                    mReviewAdapter = new ReviewAdapter(new ArrayList<ReviewResponse.Review>());
                    lvReviews.setAdapter(mReviewAdapter);
                    lvReviews.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(MovieException exception) {
                Toast.makeText(getActivity(), "Error getting reviews", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void getTrailers() {
        IMovieProvider provider = new MovieProvider();
        provider.getMovieTrailer(movie.Id, new ICallbackListener<TrailerResponse>() {
            @Override
            public void onComplete(final TrailerResponse result) {
                if (result != null && result.Trailers != null && result.Trailers.size() > 0) {
                    if (mTrailerAdapter != null) {
                        mTrailerAdapter.setTrailers(result.Trailers);
                        mTrailerAdapter.notifyDataSetChanged();
                    } else {
                        mTrailerAdapter = new TrailerListAdapter(result.Trailers);

                    }
                    lvTrailers.setAdapter(mTrailerAdapter);
                    tvTrailers.setText(R.string.trailers);
                    if (shareMenuItem != null) {
                        shareMenuItem.setVisible(true);
                    }
                    lvTrailers.setVisibility(View.VISIBLE);
                    firstTrailer = result.Trailers.get(0);
                    setShareIntent();
                } else {
                    tvTrailers.setText(R.string.notrailer);
                    mTrailerAdapter = new TrailerListAdapter(new ArrayList<TrailerResponse.Trailer>());
                    lvTrailers.setAdapter(mTrailerAdapter);
                    lvTrailers.setVisibility(View.GONE);
                    if (shareMenuItem != null) {
                        shareMenuItem.setVisible(false);
                    }
                    firstTrailer = null;
                }
            }

            @Override
            public void onError(MovieException exception) {
                Toast.makeText(getActivity(), "Error getting trailers", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movie_detail, menu);
        // Locate MenuItem with ShareActionProvider
        shareMenuItem = menu.findItem(R.id.menu_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareMenuItem);
        setShareIntent();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        setShareIntent();
    }

    // Call to update the share intent
    private Intent setShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        if(mShareActionProvider != null) {
            // Get the currently selected item, and retrieve it's share intent
            shareIntent.setType("text/plain");
            String title = movie.Title;
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Watch trailer for : " + title);
            if (firstTrailer != null)
                shareIntent.putExtra(Intent.EXTRA_TEXT, firstTrailer.getTrailerUrl());
            mShareActionProvider.setShareIntent(shareIntent);
        }
        return shareIntent;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }


    Transformation transformation = new Transformation() {

        @Override
        public Bitmap transform(Bitmap source) {
            int targetWidth = ivMoviePoster.getMeasuredWidth();
            double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            int targetHeight = (int) (targetWidth * aspectRatio);
            if (targetHeight > 0 && targetWidth > 0) {
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
