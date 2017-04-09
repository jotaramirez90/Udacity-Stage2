package com.jota.udacity.project2.repository;

import android.net.Uri;
import java.net.MalformedURLException;
import java.net.URL;

public class Repository {

  private static final String API_KEY = "YOUR_API_KEY";
  private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
  public static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w342/";
  public static final String BASE_BIG_IMAGE_URL = "http://image.tmdb.org/t/p/w500/";
  private static final String PATH_POPULAR = "popular";
  private static final String PATH_TOP = "top_rated";
  private static final String PARAM_KEY = "api_key";
  public static final String PARAM_RESULTS = "results";
  public static final String PARAM_TITLE = "original_title";
  public static final String PARAM_POSTER = "poster_path";
  public static final String PARAM_SYNOPSIS = "overview";
  public static final String PARAM_DATE = "release_date";
  public static final String PARAM_RATING = "vote_average";

  public static URL getPopularMovies() {
    return getUrl(PATH_POPULAR);
  }

  public static URL getTopMovies() {
    return getUrl(PATH_TOP);
  }

  private static URL getUrl(String path) {
    Uri uri = Uri.parse(BASE_URL)
        .buildUpon()
        .appendPath(path)
        .appendQueryParameter(PARAM_KEY, API_KEY)
        .build();
    URL url = null;
    try {
      url = new URL(uri.toString());
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return url;
  }
}
