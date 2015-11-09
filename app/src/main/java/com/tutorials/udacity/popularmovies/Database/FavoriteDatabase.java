package com.tutorials.udacity.popularmovies.Database;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by asharma on 11/3/15.
 */

@Database(version = FavoriteDatabase.VERSION)
public final class FavoriteDatabase {
    private FavoriteDatabase() {
    }

    public static final int VERSION = 2;

    @Table(FavoriteColumns.class)
    public static final String FAVORITES = "favorite";


}