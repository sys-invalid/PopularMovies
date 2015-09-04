package com.tutorials.udacity.popularmovies.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import com.tutorials.udacity.popularmovies.Models.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asharma on 6/26/15.
 */
public class MoviesTable {

    final  Context mContext;
    public MoviesTable(Context pContext){
        mContext = pContext;
    }

    public static abstract class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_Title = "title";
        public static final String COLUMN_NAME_GENREIDS = "genres";
        public static final String COLUMN_NAME_POSTERPATH = "poster";
        public static final String COLUMN_NAME_POPULARITY = "popularity";
        public static final String COLUMN_NAME_VOTEAVG = "voteavg";
        public static final String COLUMN_NAME_VOTECOUNT = "votecount";
        public static final String COLUMN_NAME_OVERVIEW = "overview";
        public static final String COLUMN_NAME_RELEASEDATE = "releasedate";
    }


    private static final String TEXT_TYPE = " TEXT";
    private static final String REAL_TYPE = " REAL";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + MovieEntry.TABLE_NAME + "(" +
            MovieEntry._ID + " INTEGER PRIMARY KEY," +
            MovieEntry.COLUMN_NAME_ID + TEXT_TYPE + COMMA_SEP +
            MovieEntry.COLUMN_NAME_Title + TEXT_TYPE + COMMA_SEP +
            MovieEntry.COLUMN_NAME_GENREIDS + TEXT_TYPE + COMMA_SEP +
            MovieEntry.COLUMN_NAME_POSTERPATH + TEXT_TYPE + COMMA_SEP +
            MovieEntry.COLUMN_NAME_POPULARITY + REAL_TYPE + COMMA_SEP +
            MovieEntry.COLUMN_NAME_VOTEAVG + REAL_TYPE + COMMA_SEP +
            MovieEntry.COLUMN_NAME_VOTECOUNT + INTEGER_TYPE + COMMA_SEP +
            MovieEntry.COLUMN_NAME_OVERVIEW + TEXT_TYPE + COMMA_SEP +
            MovieEntry.COLUMN_NAME_RELEASEDATE + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME;

    public static void createSaveTable(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public  List<Movie> getFavoriteMovies() {
        SQLiteDatabase db = new SQLiteOpenHelper(mContext).getReadableDatabase();
        List<String> savedMovies = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();
        Cursor c = db.query(
                MovieEntry.TABLE_NAME,  // The table to query
                null,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        while (c.moveToNext()) {
            Movie movie = new Movie();
            movie.OverView = c.getString(c.getColumnIndex(MovieEntry.COLUMN_NAME_OVERVIEW));
            movie.Title = c.getString(c.getColumnIndex(MovieEntry.COLUMN_NAME_Title));
            movie.Popularity = c.getFloat(c.getColumnIndex(MovieEntry.COLUMN_NAME_POPULARITY));
            movie.PosterPath = c.getString(c.getColumnIndex(MovieEntry.COLUMN_NAME_POSTERPATH));
            movie.VoteAvg = c.getFloat(c.getColumnIndex(MovieEntry.COLUMN_NAME_VOTEAVG));
            movie.VoteCount = c.getInt(c.getColumnIndex(MovieEntry.COLUMN_NAME_VOTECOUNT));
            movie.Id = c.getString(c.getColumnIndex(MovieEntry.COLUMN_NAME_ID));
            movie.ReleaseDate = c.getString(c.getColumnIndex(MovieEntry.COLUMN_NAME_RELEASEDATE));
            movies.add(movie);
        }
        return movies;
    }


    public  Cursor getFavoriteMoviesCursor() {
        SQLiteDatabase db = new SQLiteOpenHelper(mContext).getReadableDatabase();
        List<String> savedMovies = new ArrayList<>();
        List<Movie> movies = new ArrayList<>();
        Cursor c = db.query(
                MovieEntry.TABLE_NAME,  // The table to query
                null,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        return c;
    }

    public long saveMovie(ContentValues cv){
        SQLiteDatabase db = new SQLiteOpenHelper(mContext).getWritableDatabase();
        long id = db.insert(MovieEntry.TABLE_NAME,"",cv);
        return id;
    }

    public  void clearAll() {
        SQLiteDatabase db = new SQLiteOpenHelper(mContext).getReadableDatabase();
        db.delete(MovieEntry.TABLE_NAME, null, null);
    }

}
