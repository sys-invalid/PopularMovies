package com.tutorials.udacity.popularmovies.Providers;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.tutorials.udacity.popularmovies.MovieApp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by asharma on 8/31/15.
 */
public class SharedPreferenceProvider {

    static SharedPreferenceProvider mInstance;

    public synchronized static SharedPreferenceProvider get() {
        if (mInstance == null) {
            mInstance = new SharedPreferenceProvider();
        }
        return mInstance;
    }

    public void save(String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MovieApp.getInstance());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void save(String key, boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MovieApp.getInstance());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void saveToList(String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MovieApp.getInstance());
        Set<String> existingKeys = preferences.getStringSet(key, new HashSet<String>());
        existingKeys.add(value);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(key, existingKeys);
        editor.commit();
    }

    public void saveToList(String key, long value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MovieApp.getInstance());
        Set<String> existingKeys = preferences.getStringSet(key, new HashSet<String>());
        existingKeys.add((String.valueOf(value)));
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(key, existingKeys);
        editor.commit();
    }

    public void removeFromList(String key,String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MovieApp.getInstance());
        Set<String> existingKeys = preferences.getStringSet(key, new HashSet<String>());
        existingKeys.remove(value);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(key, existingKeys);
        editor.commit();
    }

    public void remove(String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MovieApp.getInstance());
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.commit();
    }

    public Set<String> get(String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MovieApp.getInstance());
        return preferences.getStringSet(key, new HashSet<String>());
    }

    public boolean get(String key, boolean  defaultVal) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MovieApp.getInstance());
        return preferences.getBoolean(key,  defaultVal);
    }

    public void clearAll(String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MovieApp.getInstance());
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

}
