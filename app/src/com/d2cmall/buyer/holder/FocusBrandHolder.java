package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.ShowPopImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fixme
 * Author: LWJ
 * desc:        关注品牌holder
 * Date: 2017/09/06 15:39
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class FocusBrandHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_avatar)
        public ImageView mImgAvatar;
        @Bind(R.id.name_tv)
        public TextView mNameTv;
        //开启消息推送行为节点
        @Bind(R.id.focus_iv)
        public ShowPopImageView mFocusIv;
        @Bind(R.id.recycler_view_item)
        public RecyclerView mItemRecyclerview;
        @Bind(R.id.tv_use)
        public TextView tvUse;


    public FocusBrandHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

}
