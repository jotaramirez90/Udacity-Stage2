package com.jota.udacity.project2.ui.features.main.presenter;

import android.os.AsyncTask;
import com.jota.udacity.project2.model.MovieModel;
import com.jota.udacity.project2.repository.Repository;
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
      try {
        ArrayList<MovieModel> movieModels = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray(Repository.PARAM_RESULTS);
        for (int i = 0; i < jsonArray.length(); i++) {
          JSONObject object = (JSONObject) jsonArray.get(i);
          MovieModel movieModel = new MovieModel();
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
    }
  }
}