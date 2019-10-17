package com.d2cmall.buyer.widget;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.d2cmall.buyer.widget.nicevideo.TxVideoPlayerController;

/**
 * Fixme
 * Author: YH
 * Date: 2017/07/10 09:57
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class RecylerViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private TxVideoPlayerController mController;

    public RecylerViewHolder(View view) {
        super(view);
        mViews = new SparseArray<>();
        itemView.setTag(this);
    }

    private void addChildView(int viewId) {
        if (mViews.get(viewId) == null) {
            mViews.put(viewId, itemView.findViewById(viewId));
        }
    }

    public void setController(TxVideoPlayerController mController) {
        this.mController = mController;
    }

    public TxVideoPlayerController getController() {
        return mController;
    }

    public TextView getTextView(int viewId) {
        addChildView(viewId);
        return (TextView) mViews.get(viewId);
    }

    public void setText(int textId, int stringId) {
        addChildView(textId);
        ((TextView) mViews.get(textId)).setText(stringId);
    }

    public void setText(int textId, String content) {
        addChildView(textId);
        if (content != null)
            ((TextView) mViews.get(textId)).setText(content);
    }

    public void setImage(int imageViewId, int resId) {
        ImageView imageView = getImageView(imageViewId);
        imageView.setImageResource(resId);
    }

    public void setImage(int imageViewId, String imageUrl) {
        Glide.with(itemView.getContext())
                .load(imageUrl)
                .into(getImageView(imageViewId));
    }

    public ImageView getImageView(int imageViewId) {
        addChildView(imageViewId);
        return (ImageView) mViews.get(imageViewId);
    }

    public RelativeLayout getRelativeLayout(int relativeLayoutId) {
        addChildView(relativeLayoutId);
        return (RelativeLayout) mViews.get(relativeLayoutId);
    }

    public LinearLayout getLinearLayout(int linearLayoutId) {
        addChildView(linearLayoutId);
        return (LinearLayout) mViews.get(linearLayoutId);
    }

    public FrameLayout getFrameLayout(int frameLayoutId) {
        addChildView(frameLayoutId);
        return (FrameLayout) mViews.get(frameLayoutId);
    }

    public void setImage(int imageViewId, Bitmap bitmap) {
        if (bitmap != null)
            getImageView(imageViewId).setImageBitmap(bitmap);
    }


    public View getView(int viewId) {
        addChildView(viewId);
        return mViews.get(viewId);
    }

    public View getRootView() {
        return itemView;
    }

}
