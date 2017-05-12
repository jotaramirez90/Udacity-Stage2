package com.jota.udacity.project2.ui.features.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.jota.udacity.project2.R;
import com.jota.udacity.project2.model.MovieModel;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class MainArrayAdapter extends ArrayAdapter<MovieModel> {

  private OnMovieItemClickListener mListener;
  private ArrayList<MovieModel> movieModels;

  public MainArrayAdapter(@NonNull Context context, @NonNull ArrayList<MovieModel> objects) {
    super(context, 0, objects);
    movieModels = objects;
    mListener = (OnMovieItemClickListener) context;
  }

  @NonNull @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    final MovieModel movieModel = getItem(position);
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie, parent, false);
    }
    ImageView posterImageView = (ImageView) convertView.findViewById(R.id.iv_poster);
    Picasso.with(getContext())
        .load(movieModel.getNormalPoster())
        .error(R.drawable.ph_movie)
        .into(posterImageView);
    convertView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        mListener.onItemClick(movieModel);
      }
    });
    return convertView;
  }

  public ArrayList<MovieModel> getMovieModels() {
    return movieModels;
  }

  public interface OnMovieItemClickListener {
    void onItemClick(MovieModel movieModel);
  }
}
