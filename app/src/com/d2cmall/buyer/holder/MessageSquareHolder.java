package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;

/**
 * Fixme
 * Author: LWJ
 * desc:    消息广场动态holder
 * Date: 2017/09/12 15:20
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class MessageSquareHolder extends RecyclerView.ViewHolder {

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
    public MessageSquareHolder(View itemView) {
        super(itemView);
    }



}
