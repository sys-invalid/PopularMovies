package com.tutorials.udacity.popularmovies.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tutorials.udacity.popularmovies.Models.ReviewResponse;
import com.tutorials.udacity.popularmovies.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by asharma on 11/7/15.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    public void setReviews(List<ReviewResponse.Review> mReviews) {
        this.mReviews = mReviews;
    }

    List<ReviewResponse.Review> mReviews;

    public ReviewAdapter(List<ReviewResponse.Review> reviews) {
        mReviews = reviews;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.movie_review_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindMovie(mReviews.get(position));
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvAuthor)
        TextView tvAuthor;
        @Bind(R.id.tvContent)
        TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        public void bindMovie(ReviewResponse.Review review) {
            tvAuthor.setText(review.Author);
            tvContent.setText(review.Content);

        }
    }
}
