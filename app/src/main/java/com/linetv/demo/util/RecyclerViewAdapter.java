package com.linetv.demo.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.linetv.demo.R;
import com.linetv.demo.data.db.DramasTable;
import com.linetv.demo.ui.dramas.DramasDetailsActivity;
import com.linetv.demo.ui.dramas.MainActivity;

import java.util.List;

import static com.linetv.demo.Constants.KEY_EXTRA;

public class RecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    public static final int EMPTY = 0;
    public static final int NORMAL = 1;
    private final List<DramasTable> mList;
    private Context mContext;

    private MainActivity.Callback mCallback;

    public RecyclerViewAdapter(Context mContext,List<DramasTable> mList) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public void setCallback(MainActivity.Callback callback) {
        mCallback = callback;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseViewHolder holder;
        View view;
        switch (viewType) {
            case NORMAL:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dramas_card,parent,false);
                holder = new ViewHolder(view);
                break;
            case EMPTY:
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_view, parent, false);
                holder = new EmptyViewHolder(view);
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if (mList != null && mList.size() > 0) {
            return NORMAL;
        } else {
            return EMPTY;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends BaseViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            imageName = itemView.findViewById(R.id.txtName);
            txtCreated_at = itemView.findViewById(R.id.txtCreated_at);
            txtRating = itemView.findViewById(R.id.txtRating);
            parentLayout = itemView.findViewById(R.id.layout_card);
            buttonRetry = itemView.findViewById(R.id.buttonRetry);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            Glide.with(mContext)
                    .asBitmap()
                    .load(mList.get(position).getThumb_url())
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            Bitmap tmpbitmap = resource;
                            if(!Util.isNetworkAvailable(mContext)){
                                tmpbitmap = Util.getPic(mContext,mList.get(position).getDrama_id() + ".jpg");
                            }else{
                                Util.savePic(mContext,tmpbitmap,mList.get(position).getDrama_id() + ".jpg");
                            }
                            imageView.setImageBitmap(tmpbitmap);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }

                        @Override
                        public void onLoadStarted(@Nullable Drawable placeholder) {
                            super.onLoadStarted(placeholder);
                            imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_sentiment_dissatisfied));
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_no_image));
                        }
                    });

            imageName.setText(mList.get(position).getName());
            txtCreated_at.setText(mList.get(position).getCreated_at());
            txtRating.setText(String.valueOf(mList.get(position).getRating()));
            parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, DramasDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra(KEY_EXTRA, mList.get(position).getDrama_id());
                    mContext.startActivity(intent);
                }
            });
            if(position == (mList.size() -1) && !Util.isNetworkAvailable(mContext)){
                parentLayout.findViewById(R.id.net_status).setVisibility(View.VISIBLE);
                buttonRetry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("onEmptyViewRetryClick");
                        if(mCallback!=null){
                            mCallback.onEmptyViewRetryClick();
                        }
                    }
                });
            }else{
                parentLayout.findViewById(R.id.net_status).setVisibility(View.GONE);
            }
        }
    }

    public class EmptyViewHolder extends BaseViewHolder {
        TextView buttonRetry;
        EmptyViewHolder(View itemView) {
            super(itemView);
            buttonRetry = itemView.findViewById(R.id.buttonRetry);
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            buttonRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("onEmptyViewRetryClick");
                    mCallback.onEmptyViewRetryClick();
                }
            });
        }
    }
}
