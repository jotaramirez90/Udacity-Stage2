package com.jota.udacity.project2.ui.features.main.presenter;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import com.jota.udacity.project2.data.MovieContract;
import com.jota.udacity.project2.data.Repository;
import com.jota.udacity.project2.model.MovieModel;
import com.jota.udacity.project2.ui.features.BasePresenter;
import com.jota.udacity.project2.ui.features.main.view.MainActivity;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainPresenter extends BasePresenter<MainActivity> {

  public void getPopularMovies() {
    new MoviesTask().execute(Repository.getPopularMovies());
  }

  public void getTopMovies() {
    new MoviesTask().execute(Repository.getTopMovies());
  }

  public void getFavorites(Context context) {
    Cursor cursor = context.getContentResolver()
        .query(MovieContract.MovieEntry.CONTENT_URI, null, null, null, null);
    ArrayList<MovieModel> movieModels = new ArrayList<>();
    while (cursor.moveToNext()) {
      int idIndex = cursor.getColumnIndex(MovieContract.MovieEntry._ID);
      int titleIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE);
      int posterIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER);
      int synopsisIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_SYNOPSIS);
      int ratingIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RATING);
      int dateIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_DATE);
      MovieModel movieModel = new MovieModel();
      movieModel.setId(cursor.getString(idIndex));
      movieModel.setTitle(cursor.getString(titleIndex));
      movieModel.setPoster(cursor.getString(posterIndex));
      movieModel.setSynopsis(cursor.getString(synopsisIndex));
      movieModel.setRating(cursor.getString(ratingIndex));
      movieModel.setDate(cursor.getString(dateIndex));
      movieModels.add(movieModel);
    }
    mView.setMovies(movieModels);
  }

  private class MoviesTask extends AsyncTask<URL, Void, String> {

    @Override protected String doInBackground(URL... url) {
      HttpURLConnection urlConnection = null;
      try {
        urlConnection = (HttpURLConnection) url[0].openConnection();
        InputStream in = urlConnection.getInputStream();
        Scanner scanner = new Scanner(in);
        scanner.useDelimiter("\\A");
        boolean hasInput = scanner.hasNext();
        if (hasInput) {
          return scanner.next();
        } else {
          return null;
        }
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        urlConnection.disconnect();
      }
      return null;
    }

    @Override protected void onPostExecute(String response) {
      super.onPostExecute(response);
      if (response != null) {
        try {
          ArrayList<MovieModel> movieModels = new ArrayList<>();
          JSONObject jsonObject = new JSONObject(response);
          JSONArray jsonArray = jsonObject.getJSONArray(Repository.PARAM_RESULTS);
          for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = (JSONObject) jsonArray.get(i);
            MovieModel movieModel = new MovieModel();
            movieModel.setId(object.getString(Repository.PARAM_ID));
            movieModel.setTitle(object.getString(Repository.PARAM_TITLE));
            movieModel.setSynopsis(object.getString(Repository.PARAM_SYNOPSIS));
            movieModel.setPoster(object.getString(Repository.PARAM_POSTER));
            movieModel.setDate(object.getString(Repository.PARAM_DATE));
            movieModel.setRating(object.getString(Repository.PARAM_RATING));
            movieModels.add(movieModel);
          }
          mView.setMovies(movieModels);
        } catch (JSONException e) {
          e.printStackTrace();
        }
      } else {
        mView.showError();
      }
    }
  }
}