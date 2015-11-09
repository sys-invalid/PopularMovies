package com.tutorials.udacity.popularmovies.Database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.DefaultValue;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by asharma on 11/3/15.
 */
public interface FavoriteColumns {
    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement
    public static final String _ID ="_id";

    @DataType(DataType.Type.INTEGER)
    @NotNull
    public static final String COLUMN_NAME_MOVIEID = "id";

    @DataType(DataType.Type.TEXT)
    public static final String COLUMN_NAME_TITLE = "title";

    @DataType(DataType.Type.TEXT)
   public static final String COLUMN_NAME_GENREIDS = "genres";

    @DataType(DataType.Type.TEXT)
    public static final String COLUMN_NAME_POSTERPATH = "poster";

    @DataType(DataType.Type.REAL)
    public static final String COLUMN_NAME_POPULARITY = "popularity";


    @DataType(DataType.Type.REAL)
    public static final String COLUMN_NAME_VOTEAVG = "voteavg";


    @DataType(DataType.Type.REAL)
    public static final String COLUMN_NAME_VOTECOUNT = "votecount";


    @DataType(DataType.Type.TEXT)
    public static final String COLUMN_NAME_OVERVIEW = "overview";


    @DataType(DataType.Type.TEXT)
    public static final String COLUMN_NAME_RELEASEDATE = "releasedate";


}



