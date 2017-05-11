package com.jota.udacity.project2.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.jota.udacity.project2.data.Repository;

public class MovieModel implements Parcelable {

  private String id;
  private String title;
  private String poster;
  private String synopsis;
  private String rating;
  private String date;

  public MovieModel() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getNormalPoster() {
    return Repository.BASE_IMAGE_URL + poster;
  }

  public String getBigPoster() {
    return Repository.BASE_BIG_IMAGE_URL + poster;
  }

  public void setPoster(String poster) {
    this.poster = poster;
  }

  public String getPoster() {
    return poster;
  }

  public String getSynopsis() {
    return synopsis;
  }

  public void setSynopsis(String synopsis) {
    this.synopsis = synopsis;
  }

  public String getRating() {
    return rating;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(id);
    parcel.writeString(title);
    parcel.writeString(poster);
    parcel.writeString(synopsis);
    parcel.writeString(rating);
    parcel.writeString(date);
  }

  protected MovieModel(Parcel in) {
    id = in.readString();
    title = in.readString();
    poster = in.readString();
    synopsis = in.readString();
    rating = in.readString();
    date = in.readString();
  }

  public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
    @Override public MovieModel createFromParcel(Parcel in) {
      return new MovieModel(in);
    }

    @Override public MovieModel[] newArray(int size) {
      return new MovieModel[size];
    }
  };
}
