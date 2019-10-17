package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/09/25 17:06
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class MessageSquareViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.image_head)
    public ImageView mImageHead;
    @Bind(R.id.titletext_view)
    public TextView mTitletextView;
    @Bind(R.id.contenttext_view)
    public TextView mContenttextView;
    @Bind(R.id.image_pic)
    public ImageView mImagePic;
    @Bind(R.id.timetext_view)
    public TextView mTimetextView;
    @Bind(R.id.titletext_action_type)
    public TextView tvActionType;

    public MessageSquareViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

}
