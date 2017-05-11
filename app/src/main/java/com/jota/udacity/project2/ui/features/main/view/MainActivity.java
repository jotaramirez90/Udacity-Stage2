package com.jota.udacity.project2.ui.features.main.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import com.jota.udacity.project2.R;
import com.jota.udacity.project2.app.BaseActivity;
import com.jota.udacity.project2.app.navigator.Navigator;
import com.jota.udacity.project2.model.MovieModel;
import com.jota.udacity.project2.ui.features.BasePresenter;
import com.jota.udacity.project2.ui.features.main.adapter.MainArrayAdapter;
import com.jota.udacity.project2.ui.features.main.presenter.MainPresenter;
import java.util.ArrayList;

public class MainActivity extends BaseActivity
    implements MainArrayAdapter.OnMovieItemClickListener {

  private static final String GRID_STATE_KEY = "gridStateKey";

  private MainPresenter mMainPresenter;
  private ProgressBar mMainProgressBar;
  private GridView mMainGridView;
  private MainArrayAdapter mMainArrayAdapter;

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, MainActivity.class);
  }

  @Override protected BasePresenter bindPresenter() {
    mMainPresenter = new MainPresenter();
    return mMainPresenter;
  }

  @Override protected int bindLayout() {
    return R.layout.activity_main;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mMainProgressBar = (ProgressBar) findViewById(R.id.pb_main);
    mMainGridView = (GridView) findViewById(R.id.rv_main);
    if (savedInstanceState != null) {
      ArrayList<MovieModel> movieModels = savedInstanceState.getParcelableArrayList(GRID_STATE_KEY);
      setMovies(movieModels);
    } else {
      mMainPresenter.getPopularMovies();
    }
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelableArrayList(GRID_STATE_KEY, mMainArrayAdapter.getMovieModels());
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    clearMovies();
    switch (item.getItemId()) {
      case R.id.action_popular:
        mMainPresenter.getPopularMovies();
        break;
      case R.id.action_top:
        mMainPresenter.getTopMovies();
        break;
      case R.id.action_favourite:
        mMainPresenter.getFavorites(this);
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  private void clearMovies() {
    mMainGridView.setVisibility(View.GONE);
    mMainProgressBar.setVisibility(View.VISIBLE);
  }

  public void setMovies(ArrayList<MovieModel> movieModels) {
    mMainProgressBar.setVisibility(View.GONE);
    mMainArrayAdapter = new MainArrayAdapter(this, movieModels);
    mMainGridView.setAdapter(mMainArrayAdapter);
    mMainGridView.setVisibility(View.VISIBLE);
  }

  @Override public void onItemClick(MovieModel movieModel) {
    Navigator.toDetails(this, movieModel);
  }
}
