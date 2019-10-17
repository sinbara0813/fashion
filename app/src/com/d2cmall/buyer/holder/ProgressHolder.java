package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/13 18:20
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProgressHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.image)
    public ImageView image;
    @Bind(R.id.iv_release)
    public ImageView ivRelease;
    @Bind(R.id.iv_delete)
    public ImageView ivDelete;
    @Bind(R.id.ll_release)
    public LinearLayout llRelease;
    @Bind(R.id.upload_progress)
    public ProgressBar uploadProgress;
    @Bind(R.id.tv_progress)
    public TextView tvProgress;
    @Bind(R.id.progress_ll)
    public LinearLayout progressLl;
    @Bind(R.id.ll_progress)
    public LinearLayout llProgress;

    public ProgressHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
