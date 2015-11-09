package com.tutorials.udacity.popularmovies.Adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tutorials.udacity.popularmovies.Interfaces.IMovieListFragmentListener;
import com.tutorials.udacity.popularmovies.Models.Movie;
import com.tutorials.udacity.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setSelectedItems(int selectedItems) {
        this.selectedItems = selectedItems;
    }

    private int selectedItems = 0;

    List<Movie> movieList;
    Context mContext;
    IMovieListFragmentListener movieClickListener;

    int sortPreference;

    public void setMovieList(List<Movie> movieList, int sortPreference) {
        this.movieList = movieList;
        this.sortPreference = sortPreference;
    }

    public MovieListAdapter(IMovieListFragmentListener clickListener, int sortPreference) {
        this(new ArrayList<Movie>(), clickListener, sortPreference);

    }


    public MovieListAdapter(List<Movie> pMovies, IMovieListFragmentListener clickListener, int sortPreference) {
        this.movieList = pMovies == null ? new ArrayList<Movie>() : pMovies;
        this.movieClickListener = clickListener;
        this.sortPreference = sortPreference;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position < movieList.size()) {
            holder.bindMovie(movieList.get(position));
            if(position == selectedItems){
                holder.showHighlights();
            }else{
                holder.hideHightlights();
            }
        }
    }

    @Override
    public int getItemCount() {
        return movieList == null ? 0 : movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvPopularity)
        TextView tvPopularity;
        @Bind(R.id.ivIcon)
        ImageView ivVotes;
        @Bind(R.id.ivMoviePoster)
        ImageView ivMovie;
        @Bind(R.id.tvMovieName)
        TextView tvTitle;
        @Bind(R.id.tvRating)
        TextView tvRating;
        @Nullable
        @Bind(R.id.left_hightlight)
        View mLeftHighlight;
        @Nullable
        @Bind(R.id.right_hightlight)
        View mRightHighlight;
        @Nullable
        @Bind(R.id.bottom_hightlight)
        View mBottomHighlight;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            hideHightlights();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (movieClickListener != null) {
                        int position = selectedItems = getAdapterPosition();
                        //showHighlights();
                        if (movieClickListener != null && movieList.size() > position && position >= 0) {
                            Movie item = movieList.get(position);
                            movieClickListener.onMovieItemClicked(item, position, ivMovie);
                        }
                        notifyDataSetChanged();
                    }
                }
            });
        }

        public void bindMovie(Movie movie) {
            double roundOff = Math.round(movie.Popularity * 100.0) / 100.0;
            tvPopularity.setText(roundOff + "%");
            tvRating.setText(movie.VoteAvg + "");
            //  tvVotes.setText(movie.VoteAvg + "");
            tvTitle.setText(movie.Title);
            ivMovie.setImageBitmap(null);
            ivMovie.setBackgroundDrawable(null);
            if (movie.PosterPath != null && movie.PosterPath != "") {
                Picasso.with(mContext).load(movie.getThumbnailUrl("w185")).into(ivMovie);
            }
        }


        private void showHighlights() {
            if (mLeftHighlight != null && mBottomHighlight != null && mRightHighlight != null) {
                mLeftHighlight.setVisibility(View.VISIBLE);
                mRightHighlight.setVisibility(View.VISIBLE);
                mBottomHighlight.setVisibility(View.VISIBLE);
            }
        }

        private void hideHightlights() {
            if (mLeftHighlight != null && mBottomHighlight != null && mRightHighlight != null) {
                mLeftHighlight.setVisibility(View.GONE);
                mRightHighlight.setVisibility(View.GONE);
                mBottomHighlight.setVisibility(View.GONE);
            }
        }
    }
}
