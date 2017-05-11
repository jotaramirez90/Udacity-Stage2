package com.jota.udacity.project2.ui.features.details.presenter;

import android.os.AsyncTask;
import com.jota.udacity.project2.model.ReviewModel;
import com.jota.udacity.project2.model.VideoModel;
import com.jota.udacity.project2.repository.Repository;
import com.jota.udacity.project2.ui.features.BasePresenter;
import com.jota.udacity.project2.ui.features.details.view.DetailsActivity;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailsPresenter extends BasePresenter<DetailsActivity> {

  public void getDetailsMovie(String movieId) {
    getVideos(movieId);
    getReviews(movieId);
  }

  private void getVideos(String movieId) {
    new VideosTask().execute(Repository.getVideosMovie(movieId));
  }

  private void getReviews(String movieId) {
    new ReviewsTask().execute(Repository.getReviewsMovie(movieId));
  }

  private class VideosTask extends AsyncTask<URL, Void, String> {

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
        ArrayList<VideoModel> videoModels = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray(Repository.PARAM_RESULTS);
        for (int i = 0; i < jsonArray.length(); i++) {
          JSONObject object = (JSONObject) jsonArray.get(i);
          VideoModel videoModel = new VideoModel();
          videoModel.setId(object.getString(Repository.PARAM_ID));
          videoModel.setKey(object.getString(Repository.PARAM_KEY));
          videoModel.setName(object.getString(Repository.PARAM_NAME));
          videoModel.setSite(object.getString(Repository.PARAM_SITE));
          videoModel.setType(object.getString(Repository.PARAM_TYPE));
          videoModels.add(videoModel);
        }
        mView.setVideos(videoModels);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
  }

  private class ReviewsTask extends AsyncTask<URL, Void, String> {

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
        ArrayList<ReviewModel> reviewModels = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray(Repository.PARAM_RESULTS);
        for (int i = 0; i < jsonArray.length(); i++) {
          JSONObject object = (JSONObject) jsonArray.get(i);
          ReviewModel reviewModel = new ReviewModel();
          reviewModel.setId(object.getString(Repository.PARAM_ID));
          reviewModel.setAuthor(object.getString(Repository.PARAM_AUTHOR));
          reviewModel.setContent(object.getString(Repository.PARAM_CONTENT));
          reviewModels.add(reviewModel);
        }
        mView.setReviews(reviewModels);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
  }
}
