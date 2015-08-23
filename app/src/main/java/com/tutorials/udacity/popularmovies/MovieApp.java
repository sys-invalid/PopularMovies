package com.tutorials.udacity.popularmovies;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by asharma on 8/22/15.
 */
public class MovieApp extends Application {

    private static MovieApp mInstance;

    private RequestQueue mRequestQueue;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }


    public static MovieApp getInstance() {
        return mInstance;
    }

    public RequestQueue getVolleyRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this);
        }
        return mRequestQueue;
    }

    private static void addRequest(Request<?> request) {
        getInstance().getVolleyRequestQueue().add(request);
    }

    public static void addRequest(Request<?> request, String tag) {
        request.setTag(tag);
        addRequest(request);
    }

    public static void cancelAllRequests(String tag) {
        if (getInstance().getVolleyRequestQueue() != null) {
            getInstance().getVolleyRequestQueue().cancelAll(tag);
        }
    }


}
