package com.jota.udacity.project2.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.jota.udacity.project2.ui.features.BasePresenter;
import com.jota.udacity.project2.ui.features.View;

public abstract class BaseActivity extends AppCompatActivity implements View {

  private BasePresenter mPresenter;

  protected abstract BasePresenter bindPresenter();

  protected abstract int bindLayout();

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeActivity();
  }

  @Override protected void onResume() {
    super.onResume();
    mPresenter.resume();
  }

  @Override protected void onPause() {
    super.onPause();
    mPresenter.pause();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    mPresenter.destroy();
  }

  private void initializeActivity() {
    mPresenter = bindPresenter();
    mPresenter.attachView(this);
    mPresenter.create();
    setContentView(bindLayout());
  }
}
