package com.tutorials.udacity.popularmovies.Models;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tutorials.udacity.popularmovies.MovieApp;

/**
 * Created by asharma on 8/22/15.
 */
public class MovieException {
    String mMessage;

    public MovieException(String message) {
        this.mMessage = message;
    }

    public void showToast(int duration) {
        Toast.makeText(MovieApp.getInstance(), mMessage, duration).show();
    }

    public void showSnackBar(View pLayout, int length, String action, View.OnClickListener actionListener) {
        if (pLayout != null && pLayout.getContext() != null) {
            Snackbar mSnackBar = Snackbar.make(pLayout, mMessage, length);
            if (action != null && action != "" && actionListener != null) {
                mSnackBar.setAction(action, actionListener);
            }
            View snackBarView = mSnackBar.getView();
            //TextView tv = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
            mSnackBar.show();

        }
    }
}
