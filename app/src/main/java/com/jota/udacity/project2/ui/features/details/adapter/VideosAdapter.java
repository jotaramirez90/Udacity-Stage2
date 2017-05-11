package com.jota.udacity.project2.ui.features.details.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.jota.udacity.project2.R;
import com.jota.udacity.project2.model.VideoModel;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideosAdapterViewHolder> {

  private static final String YOUTUBE_THUMBNAIL = "https://img.youtube.com/vi/%s/mqdefault.jpg";

  private VideosAdapterOnClickHandler mClickHandler;
  private ArrayList<VideoModel> mVideos;

  public VideosAdapter(VideosAdapterOnClickHandler clickHandler) {
    mClickHandler = clickHandler;
  }

  @Override public VideosAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new VideosAdapterViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false));
  }

  @Override public void onBindViewHolder(VideosAdapterViewHolder holder, int position) {
    VideoModel videoModel = mVideos.get(position);
    Picasso.with(holder.itemView.getContext())
        .load(String.format(YOUTUBE_THUMBNAIL, videoModel.getKey()))
        .into(holder.mPreviewImage);
    holder.mTitleText.setText(videoModel.getName());
    holder.mTypeText.setText(videoModel.getType());
  }

  @Override public int getItemCount() {
    return mVideos.size();
  }

  public void setVideos(ArrayList<VideoModel> videos) {
    this.mVideos = videos;
    notifyDataSetChanged();
  }

  public class VideosAdapterViewHolder extends RecyclerView.ViewHolder
      implements View.OnClickListener {

    public final ImageView mPreviewImage;
    public final TextView mTitleText;
    public final TextView mTypeText;

    public VideosAdapterViewHolder(View itemView) {
      super(itemView);
      mPreviewImage = (ImageView) itemView.findViewById(R.id.im_preview);
      mTitleText = (TextView) itemView.findViewById(R.id.tv_title);
      mTypeText = (TextView) itemView.findViewById(R.id.tv_type);
      itemView.setOnClickListener(this);
    }

    @Override public void onClick(View view) {
      int adapterPosition = getAdapterPosition();
      if (adapterPosition >= 0) {
        VideoModel videoModel = mVideos.get(adapterPosition);
        mClickHandler.onClick(videoModel.getKey());
      }
    }
  }

  public interface VideosAdapterOnClickHandler {
    void onClick(String keyMovie);
  }
}
