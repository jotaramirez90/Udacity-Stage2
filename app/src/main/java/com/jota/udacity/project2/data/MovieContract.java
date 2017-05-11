package com.jota.udacity.project2.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {

  public static final String CONTENT_AUTHORITY = "com.jota.udacity.project2";
  public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
  public static final String PATH_MOVIE = "movie";

  public static class MovieEntry implements BaseColumns {

    public static final Uri CONTENT_URI =
        BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

    public static final String TABLE_NAME = "movie";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_POSTER = "poster";
    public static final String COLUMN_SYNOPSIS = "synopsis";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_DATE = "date";
  }
}
