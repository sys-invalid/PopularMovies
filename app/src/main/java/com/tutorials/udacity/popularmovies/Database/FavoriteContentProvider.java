package com.tutorials.udacity.popularmovies.Database;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by asharma on 11/3/15.
 */

@ContentProvider(authority = FavoriteContentProvider.AUTHORITY, database = FavoriteDatabase.class)
public class FavoriteContentProvider {

    public static final String AUTHORITY = "com.tutorials.udacity.popularmovies.Database.FavoriteContentProvider";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path{
        String FAVORITES = "favorites";

    }

    private static Uri buildUri(String ... paths){
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths){
            builder.appendPath(path);
        }
        return builder.build();
    }
    @TableEndpoint(table = FavoriteDatabase.FAVORITES)
    public static class Favorites {
        @ContentUri(
                path = Path.FAVORITES,
                type = "vnd.android.cursor.dir/favorite",
                defaultSort = FavoriteColumns.COLUMN_NAME_MOVIEID + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.FAVORITES);

        @InexactContentUri(
                name = "FAVORITE_ID",
                path = Path.FAVORITES + "/#",
                type = "vnd.android.cursor.item/favorite",
                whereColumn = FavoriteColumns._ID,
                pathSegment = 1)
        public static Uri withId(long id){
            return buildUri(Path.FAVORITES, String.valueOf(id));
        }
    }


}
