package com.tutorials.udacity.popularmovies.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.tutorials.udacity.popularmovies.R;



public class CustomFontTextView extends TextView {
    String ttfName;
    int mTextStyle;

    public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if(!isInEditMode())
        init(attrs);
    }

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if(!isInEditMode())
        init(attrs);

    }

    public CustomFontTextView(Context context) {
        super(context);
        if(!isInEditMode())
        init(null);
    }

    private void init(AttributeSet attrs) {
        if (attrs!=null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomFontTextView);
            String fontName = a.getString(R.styleable.CustomFontTextView_fontName);
            if (fontName!=null) {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "font/"+fontName);
                setTypeface(myTypeface);
            }
            a.recycle();
        }
    }

}
