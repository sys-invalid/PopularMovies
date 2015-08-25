package com.tutorials.udacity.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tutorials.udacity.popularmovies.Fragments.MovieListFragment;
import com.tutorials.udacity.popularmovies.Models.Movie;
import com.tutorials.udacity.popularmovies.R;
import com.tutorials.udacity.popularmovies.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    List<Movie> movieList;
    Context mContext;
    MovieListFragment.IMovieClickListener movieClickListener;

    int sortPreference;

    public void setMovieList(List<Movie> movieList,int sortPreference) {
        this.movieList = movieList;
        this.sortPreference = sortPreference;
    }


    public MovieListAdapter(List<Movie> pMovies, MovieListFragment.IMovieClickListener clickListener, int sortPreference) {
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
        }
    }

    @Override
    public int getItemCount() {
        return movieList == null ? 0 : movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPopularity;
        ImageView ivVotes;
        ImageView ivMovie;
        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPopularity = (TextView) itemView.findViewById(R.id.tvPopularity);
            tvTitle = (TextView) itemView.findViewById(R.id.tvMovieName);
          //  tvVotes = (TextView) itemView.findViewById(R.id.tvVotesAverage);
            ivMovie = (ImageView) itemView.findViewById(R.id.ivMoviePoster);
            ivVotes = (ImageView)itemView.findViewById(R.id.ivIcon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(movieClickListener != null){
                        int position = getAdapterPosition();

                        if(movieClickListener != null && movieList.size() > position && position >= 0) {
                            Movie item = movieList.get(position);
                            movieClickListener.onMovieItemClicked(item,position,ivMovie);
                        }
                    }
                }
            });
        }

        public void bindMovie(Movie movie) {
            if(sortPreference == Constants.SORT_POPULARITY) {
                double roundOff = Math.round(movie.Popularity * 100.0) / 100.0;
                tvPopularity.setText(roundOff + "%");
                ivVotes.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_action_toggle_star_half));
            }else if(sortPreference == Constants.SORT_RATING){
               tvPopularity.setText(movie.VoteCount + " count");
                ivVotes.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_action_editor_insert_emoticon));
            }
          //  tvVotes.setText(movie.VoteAvg + "");
            tvTitle.setText(movie.Title);

            if (movie.PosterPath != null && movie.PosterPath != "") {
                Picasso.with(mContext).load(movie.getThumbnailUrl("w185")).into(ivMovie);
            }
        }
    }
}
