package com.tutorials.udacity.popularmovies.Providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import com.tutorials.udacity.popularmovies.Database.MoviesTable;
import com.tutorials.udacity.popularmovies.Models.Movie;


/**
 * Created by asharma on 9/4/15.
 */
public class FavoriteProvider extends ContentProvider {
    public static String AUTHORITY = "com.tutorials.udacity.popularmovies.Providers";
    public static final Uri MOVIE_URI = Uri.parse("content://" + AUTHORITY + "/movie");
    // public static final Uri BOOKS_URI = Uri.parse("content://"+AUTHORITY+"/book");

    private MoviesTable mDatabaseHelper;
    private static UriMatcher sMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int MOVIE = 0;
    private static final int MOVIES = 1;
    private static final int AUTHOR = 2;


    private static final Class[] ENTITIES = new Class[]{Movie.class};

    static {
        sMatcher.addURI(AUTHORITY, "movie/#", MOVIE);
        sMatcher.addURI(AUTHORITY, "movie", MOVIES);
        sMatcher.addURI(AUTHORITY, "author/#", AUTHOR);
    }


    @Override
    public boolean onCreate() {
        mDatabaseHelper = new MoviesTable(getContext());
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
       switch (sMatcher.match(uri)) {
            case MOVIES:
                return  mDatabaseHelper.getFavoriteMoviesCursor();

        }
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Context context = getContext();
        Uri resultUri;
        switch (sMatcher.match(uri)) {
            case MOVIES:
                long id = mDatabaseHelper.saveMovie(values);
                resultUri = Uri.parse(uri+"/"+id);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return resultUri;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
//        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
//        int id;
//
//        switch (sMatcher.match(uri)) {
//            case MOVIE:
//                id = cupboard().withContext(getContext()).delete(MOVIE_URI,  selection, selectionArgs);
//                break;
//            default:
//                throw new IllegalArgumentException("Unknown URI " + uri);
//        }
//        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
//        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
//        int id;
//
//        switch (sMatcher.match(uri)) {
//            case MOVIE:
//                id = cupboard().withContext(getContext()).update(MOVIE_URI, values, selection, selectionArgs);
//                break;
//            default:
//                throw new IllegalArgumentException("Unknown URI " + uri);
//        }
//        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }
}
