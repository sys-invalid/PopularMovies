package com.tutorials.udacity.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tutorials.udacity.popularmovies.Models.Trailers;
import com.tutorials.udacity.popularmovies.R;

import java.util.List;

/**
 * Created by asharma on 9/2/15.
 */
public class TrailerListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<Trailers> mTrailers;
    Context mContext;
    public TrailerListAdapter(List<Trailers> pTrailers){
        this.mTrailers = pTrailers;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.trailer_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }



}
