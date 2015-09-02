package com.tutorials.udacity.popularmovies.Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tutorials.udacity.popularmovies.Models.Movie;
import com.tutorials.udacity.popularmovies.R;
import com.tutorials.udacity.popularmovies.Utils.Constants;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieTabFragment extends Fragment  {

    public MovieTabFragment() {
        // Required empty public constructor
    }
    MoviePagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;
    private String[] tabs = { "Popular", "Top Rated", "Favorites" };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_movie_tab, container, false);
        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mDemoCollectionPagerAdapter =
                new MoviePagerAdapter(this.getFragmentManager());
        mViewPager = (ViewPager)rootView.findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.sliding_tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(mViewPager);
        return rootView;
    }




    // Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
    public class MoviePagerAdapter extends FragmentStatePagerAdapter {
        public MoviePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int index) {
            Fragment fragment;
            switch (index) {
                case 0:
                    return  MovieListFragment.newInstance(Constants.SORT_POPULARITY);
                case 1:
                    return MovieListFragment.newInstance(Constants.SORT_RATING);
                case 2:
                    return MovieListFragment.newInstance(Constants.SORT_POPULARITY);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabs[position];
        }


    }

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            float translationX;
            float scale;
            float alpha;

            int pageWidth = view.getWidth();

            if (position >= 1 || position <= -1) {
                // Fix for https://code.google.com/p/android/issues/detail?id=58918
                translationX = 0;
                scale = 1;
                alpha = 1;

//            if (position < -1) { // [-Infinity,-1)
//                // This page is way off-screen to the left.
//                view.setAlpha(0);
                view.setAlpha(alpha);
                view.setTranslationX(translationX);
                view.setScaleX(scale);
                view.setScaleY(scale);
            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}
