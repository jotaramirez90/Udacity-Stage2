package com.jota.udacity.project2.model;

import android.os.Parcel;
import android.os.Parcelable;

public class VideoModel implements Parcelable {

  private String id;
  private String key;
  private String name;
  private String site;
  private String type;

  public VideoModel() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSite() {
    return site;
  }

  public void setSite(String site) {
    this.site = site;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(id);
    parcel.writeString(key);
    parcel.writeString(name);
    parcel.writeString(site);
    parcel.writeString(type);
  }

  protected VideoModel(Parcel in) {
    id = in.readString();
    key = in.readString();
    name = in.readString();
    site = in.readString();
    type = in.readString();
  }

  public static final Creator<VideoModel> CREATOR = new Creator<VideoModel>() {
    @Override public VideoModel createFromParcel(Parcel in) {
      return new VideoModel(in);
    }

    @Override public VideoModel[] newArray(int size) {
      return new VideoModel[size];
    }
  };
}
