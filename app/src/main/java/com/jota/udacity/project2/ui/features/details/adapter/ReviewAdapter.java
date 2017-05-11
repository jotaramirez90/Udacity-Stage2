package com.jota.udacity.project2.ui.features.details.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jota.udacity.project2.R;
import com.jota.udacity.project2.model.ReviewModel;
import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {

  private ArrayList<ReviewModel> mReviews;

  public ReviewAdapter() {
  }

  @Override public ReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ReviewAdapter.ReviewAdapterViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false));
  }

  @Override public void onBindViewHolder(ReviewAdapterViewHolder holder, int position) {
    ReviewModel reviewModel = mReviews.get(position);
    holder.mNameText.setText(reviewModel.getAuthor());
    holder.mContentText.setText(reviewModel.getContent());
  }

  @Override public int getItemCount() {
    return mReviews.size();
  }

  public void setReviews(ArrayList<ReviewModel> reviews) {
    this.mReviews = reviews;
    notifyDataSetChanged();
  }

  public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {

    public final TextView mNameText;
    public final TextView mContentText;

    public ReviewAdapterViewHolder(View itemView) {
      super(itemView);
      mNameText = (TextView) itemView.findViewById(R.id.tv_name);
      mContentText = (TextView) itemView.findViewById(R.id.tv_content);
    }
  }
}
