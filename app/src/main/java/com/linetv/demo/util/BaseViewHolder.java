package com.linetv.demo.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView imageName,txtCreated_at,txtRating;
    LinearLayout parentLayout;
    TextView buttonRetry;
    private int mCurrentPosition;
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public void onBind(int position) {
        mCurrentPosition = position;
    }
    public int getCurrentPosition() {
        return mCurrentPosition;
    }

}
