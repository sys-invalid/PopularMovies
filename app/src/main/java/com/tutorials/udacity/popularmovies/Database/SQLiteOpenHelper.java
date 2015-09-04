
package com.tutorials.udacity.popularmovies.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {

    private static String LOG_TAG = SQLiteOpenHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;
    private static final int LAST_DATABASE_VERSION = 0;

    private boolean mIsClosed;

    public SQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public final void onCreate(SQLiteDatabase db)
    {
            MoviesTable.createSaveTable(db);
    }

    @Override
    public final void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < LAST_DATABASE_VERSION) {
            onCreate(db);
        } else {

        }
    }




//    public void delete(Movie movie) {
//        assetNotClosed();
//
//        SQLiteDatabase db = null;
//
//        try {
//            db = getWritableDatabase();
//            db.beginTransaction();
//            final DatabaseCompartment dbc = cupboard().withDatabase(db);
//            dbc.delete(movie);
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//
//        } finally {
//            if (db != null) {
//                db.endTransaction();
//            }
//        }
//    }


    public synchronized void close() {
        mIsClosed = true;
        super.close();
    }


    public boolean isClosed() {
        return mIsClosed;
    }


    private void assetNotClosed() {
    }

//    private List<Movie> queryMovies(String selection, String... selectionArgs) {
//        assetNotClosed();
//        QueryResultIterable<Movie> itr = null;
//
//        try {
//            itr = cupboard().withDatabase(getReadableDatabase()).query(Movie.class)
//                    .withSelection(selection, selectionArgs)
//                    .query();
//        } finally {
//            if (itr != null) {
//                itr.close();
//                itr = null;
//            }
//        }
//
//        return itr != null ? itr.list() : null;
//    }
}
