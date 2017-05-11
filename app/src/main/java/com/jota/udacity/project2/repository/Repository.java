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
  private static final String PATH_VIDEOS = "videos";
  private static final String PATH_REVIEWS = "reviews";
  private static final String PARAM_API_KEY = "api_key";
  public static final String PARAM_RESULTS = "results";
  public static final String PARAM_ID = "id";
  public static final String PARAM_TITLE = "original_title";
  public static final String PARAM_POSTER = "poster_path";
  public static final String PARAM_SYNOPSIS = "overview";
  public static final String PARAM_DATE = "release_date";
  public static final String PARAM_RATING = "vote_average";
  public static final String PARAM_KEY = "key";
  public static final String PARAM_NAME = "name";
  public static final String PARAM_SITE = "site";
  public static final String PARAM_TYPE = "type";
  public static final String PARAM_AUTHOR = "author";
  public static final String PARAM_CONTENT = "content";

  public static URL getPopularMovies() {
    return getUrl(PATH_POPULAR);
  }

  public static URL getTopMovies() {
    return getUrl(PATH_TOP);
  }

  public static URL getVideosMovie(String movieId) {
    return getUrl(movieId, PATH_VIDEOS);
  }

  public static URL getReviewsMovie(String movieId) {
    return getUrl(movieId, PATH_REVIEWS);
  }

  private static URL getUrl(String path) {
    Uri uri = Uri.parse(BASE_URL)
        .buildUpon()
        .appendPath(path)
        .appendQueryParameter(PARAM_API_KEY, API_KEY)
        .build();
    URL url = null;
    try {
      url = new URL(uri.toString());
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return url;
  }

  private static URL getUrl(String movieId, String path) {
    Uri uri = Uri.parse(BASE_URL)
        .buildUpon()
        .appendPath(movieId)
        .appendPath(path)
        .appendQueryParameter(PARAM_API_KEY, API_KEY)
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
