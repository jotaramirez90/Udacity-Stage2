package com.jota.udacity.project2.ui.features.details.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.jota.udacity.project2.R;
import com.jota.udacity.project2.app.BaseActivity;
import com.jota.udacity.project2.model.MovieModel;
import com.jota.udacity.project2.model.ReviewModel;
import com.jota.udacity.project2.model.VideoModel;
import com.jota.udacity.project2.ui.features.BasePresenter;
import com.jota.udacity.project2.ui.features.details.adapter.ReviewAdapter;
import com.jota.udacity.project2.ui.features.details.adapter.VideosAdapter;
import com.jota.udacity.project2.ui.features.details.presenter.DetailsPresenter;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class DetailsActivity extends BaseActivity
    implements VideosAdapter.VideosAdapterOnClickHandler {

  public static final String PARAM_MOVIE = "movieParam";
  private static final String YOUTUBE_VIDEO = "https://www.youtube.com/watch?v=%s";

  private DetailsPresenter mDetailsPresenter;

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, DetailsActivity.class);
  }

  @Override protected BasePresenter bindPresenter() {
    mDetailsPresenter = new DetailsPresenter();
    return mDetailsPresenter;
  }

  @Override protected int bindLayout() {
    return R.layout.activity_details;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    MovieModel movieModel = getIntent().getParcelableExtra(PARAM_MOVIE);

    ImageView mPosterImageView = (ImageView) findViewById(R.id.iv_poster);
    TextView mDateTextView = (TextView) findViewById(R.id.tv_date);
    TextView mRatingTextView = (TextView) findViewById(R.id.tv_rating);
    TextView mSynopsisTextView = (TextView) findViewById(R.id.tv_synopsis);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle(movieModel.getTitle());
    Picasso.with(this).load(movieModel.getBigPoster()).into(mPosterImageView);
    mDateTextView.setText(movieModel.getDate());
    mRatingTextView.setText(movieModel.getRating());
    mSynopsisTextView.setText(movieModel.getSynopsis());
    mDetailsPresenter.getDetailsMovie(movieModel.getId());
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
  }

  @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }

  public void setVideos(ArrayList<VideoModel> videoModels) {
    ProgressBar mVideosProgressBar = (ProgressBar) findViewById(R.id.pb_videos);
    mVideosProgressBar.setVisibility(View.GONE);
    if (videoModels.size() >= 0) {
      RecyclerView mVideosRecyclerView = (RecyclerView) findViewById(R.id.rv_videos);
      mVideosRecyclerView.setLayoutManager(
          new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
      mVideosRecyclerView.setHasFixedSize(true);
      mVideosRecyclerView.setNestedScrollingEnabled(false);
      VideosAdapter adapter = new VideosAdapter(this);
      adapter.setVideos(videoModels);
      mVideosRecyclerView.setAdapter(adapter);
    } else {
      TextView mVideosEmptyText = (TextView) findViewById(R.id.tv_empty_videos);
      mVideosEmptyText.setVisibility(View.VISIBLE);
    }
  }

  public void setReviews(ArrayList<ReviewModel> reviewModels) {
    ProgressBar mVideosProgressBar = (ProgressBar) findViewById(R.id.pb_review);
    mVideosProgressBar.setVisibility(View.GONE);
    if (reviewModels.size() > 0) {
      RecyclerView mVideosRecyclerView = (RecyclerView) findViewById(R.id.rv_reviews);
      mVideosRecyclerView.setLayoutManager(
          new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
      mVideosRecyclerView.setHasFixedSize(true);
      mVideosRecyclerView.setNestedScrollingEnabled(false);
      ReviewAdapter adapter = new ReviewAdapter();
      adapter.setReviews(reviewModels);
      mVideosRecyclerView.setAdapter(adapter);
    } else {
      TextView mVideosEmptyText = (TextView) findViewById(R.id.tv_empty_reviews);
      mVideosEmptyText.setVisibility(View.VISIBLE);
    }
  }

  @Override public void onClick(String keyMovie) {
    startActivity(
        new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(YOUTUBE_VIDEO, keyMovie))));
  }
}
