package com.jota.udacity.project2.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MovieProvider extends ContentProvider {

  public static final int CODE_MOVIE = 100;
  public static final int CODE_MOVIE_WITH_ID = 101;

  private static final UriMatcher sUriMatcher = buildUriMatcher();
  private MovieDbHelper mMovieDbHelper;

  public static UriMatcher buildUriMatcher() {
    final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    final String authority = MovieContract.CONTENT_AUTHORITY;
    matcher.addURI(authority, MovieContract.PATH_MOVIE, CODE_MOVIE);
    matcher.addURI(authority, MovieContract.PATH_MOVIE + "/#", CODE_MOVIE_WITH_ID);

    return matcher;
  }

  @Override public boolean onCreate() {
    mMovieDbHelper = new MovieDbHelper(getContext());
    return true;
  }

  @Nullable @Override public Cursor query(@NonNull Uri uri, String[] projection, String selection,
      String[] selectionArgs, String sortOrder) {
    Cursor cursor;
    
    switch (sUriMatcher.match(uri)) {
      case CODE_MOVIE:
        cursor = mMovieDbHelper.getReadableDatabase()
            .query(MovieContract.MovieEntry.TABLE_NAME, projection, selection, selectionArgs, null,
                null, sortOrder);
        break;
      case CODE_MOVIE_WITH_ID:
        String movieId = uri.getLastPathSegment();
        String[] selectionArguments = new String[] { movieId };
        cursor = mMovieDbHelper.getReadableDatabase()
            .query(MovieContract.MovieEntry.TABLE_NAME, projection,
                MovieContract.MovieEntry._ID + " = ? ", selectionArguments, null, null, sortOrder);
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

    cursor.setNotificationUri(getContext().getContentResolver(), uri);
    return cursor;
  }

  @Nullable @Override public String getType(@NonNull Uri uri) {
    throw new RuntimeException("We are not implementing getType in Movies.");
  }

  @Nullable @Override public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
    final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();
    Uri returnUri;

    switch (sUriMatcher.match(uri)) {
      case CODE_MOVIE:
        long id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, contentValues);
        if (id > 0) {
          returnUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, id);
        } else {
          throw new android.database.SQLException("Failed to insert row into " + uri);
        }
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

    getContext().getContentResolver().notifyChange(uri, null);

    return returnUri;
  }

  @Override public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
    final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();
    int taskDeleted;
    switch (sUriMatcher.match(uri)) {
      case CODE_MOVIE_WITH_ID:
        String movieId = uri.getLastPathSegment();
        taskDeleted =
            db.delete(MovieContract.MovieEntry.TABLE_NAME, "_id=?", new String[] { movieId });
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

    if (taskDeleted != 0) {
      getContext().getContentResolver().notifyChange(uri, null);
    }

    return taskDeleted;
  }

  @Override
  public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s,
      @Nullable String[] strings) {
    throw new RuntimeException("We are not implementing update in Movies");
  }
}
