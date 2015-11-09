package com.tutorials.udacity.popularmovies.Adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tutorials.udacity.popularmovies.Models.TrailerResponse;
import com.tutorials.udacity.popularmovies.R;
import com.tutorials.udacity.popularmovies.Utils.Constants;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by asharma on 9/2/15.
 */
public class TrailerListAdapter extends RecyclerView.Adapter<TrailerListAdapter.ViewHolder> {

    public void setTrailers(List<TrailerResponse.Trailer> mTrailers) {
        this.mTrailers = mTrailers;
    }

    List<TrailerResponse.Trailer> mTrailers;
    Context mContext;

    public TrailerListAdapter(List<TrailerResponse.Trailer> pTrailers) {
        this.mTrailers = pTrailers;
    }

    @Override
    public TrailerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_trailer_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerListAdapter.ViewHolder holder, int position) {
        holder.BindHolder(position);
    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvTrailer)
        TextView tvTrailer;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    TrailerResponse.Trailer trailer = mTrailers.get(position);
                    if (trailer.Site.equalsIgnoreCase("youtube")) {
                        watchYoutubeVideo(trailer.Key);
                    }
                }
            });
        }


        public void BindHolder(int position) {
            TrailerResponse.Trailer trailer = mTrailers.get(position);
            tvTrailer.setText(trailer.Name);
        }


        private void watchYoutubeVideo(String id) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
                // Verify it resolves
                PackageManager packageManager = mContext.getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
                boolean isIntentSafe = activities.size() > 0;

                // Start an activity if it's safe
                if (isIntentSafe) {
                    mContext.startActivity(intent);
                }else{
                    intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.youtube.com/watch?v=" + id));
                    mContext.startActivity(intent);
                }


            } catch (ActivityNotFoundException ex) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + id));
                mContext.startActivity(intent);
            }
        }
    }


}
