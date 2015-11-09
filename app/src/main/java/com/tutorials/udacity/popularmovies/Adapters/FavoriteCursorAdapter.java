package com.tutorials.udacity.popularmovies.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.tutorials.udacity.popularmovies.Interfaces.IMovieListFragmentListener;
import com.tutorials.udacity.popularmovies.R;

public class FavoriteCursorAdapter extends CursorRecyclerViewAdapter<FavoriteCursorAdapter.ViewHolder>
{
    ViewHolder mVh;
    Context mContext;
    IMovieListFragmentListener movieClickListener;

    public FavoriteCursorAdapter(Context context, Cursor cursor){
        super(context, cursor);
        mContext = context;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvPopularity;
        ImageView ivVotes;
        ImageView ivMovie;
        TextView tvTitle;
        public ViewHolder(View view){
            super(view);
            tvPopularity = (TextView) itemView.findViewById(R.id.tvPopularity);
            tvTitle = (TextView) itemView.findViewById(R.id.tvMovieName);
            //  tvVotes = (TextView) itemView.findViewById(R.id.tvVotesAverage);
            ivMovie = (ImageView) itemView.findViewById(R.id.ivMoviePoster);
            ivVotes = (ImageView) itemView.findViewById(R.id.ivIcon);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (movieClickListener != null) {
//                        int position = getAdapterPosition();
//
//                        if (movieClickListener != null && movieList.size() > position && position >= 0) {
//                            Movie item = movieList.get(position);
//                            movieClickListener.onMovieItemClicked(item, position, ivMovie);
//                        }
//                    }
//                }
//            });
        }

        public void bindMovie(Cursor cursor){

        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor){
        viewHolder.bindMovie(cursor);
        DatabaseUtils.dumpCursor(cursor);
    }





}
