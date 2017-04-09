package com.jota.udacity.project2.app.navigator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.jota.udacity.project2.model.MovieModel;
import com.jota.udacity.project2.ui.features.details.view.DetailsActivity;
import com.jota.udacity.project2.ui.features.main.view.MainActivity;

import static com.jota.udacity.project2.ui.features.details.view.DetailsActivity.PARAM_MOVIE;

public class Navigator {

  public static void toMain(Context context) {
    if (context != null) {
      Intent intentToLaunch = MainActivity.getCallingIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  public static void toDetails(Context context, MovieModel movieModel) {
    if (context != null) {
      Intent intentToLaunch = DetailsActivity.getCallingIntent(context);
      Bundle params = new Bundle();
      params.putParcelable(PARAM_MOVIE, movieModel);
      intentToLaunch.putExtras(params);
      context.startActivity(intentToLaunch);
    }
  }
}
