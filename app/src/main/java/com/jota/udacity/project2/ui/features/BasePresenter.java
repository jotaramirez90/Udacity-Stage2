package com.jota.udacity.project2.ui.features;

public abstract class BasePresenter<T extends View> {

  protected T mView;

  public void attachView(T view) {
    mView = view;
  }

  public void create() {
  }

  public void resume() {
  }

  public void pause() {
  }

  public void destroy() {
  }
}
