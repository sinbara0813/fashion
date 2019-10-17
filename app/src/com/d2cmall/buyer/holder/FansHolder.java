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
 * desc:       我的fans holder
 * Date: 2017/09/06 17:53
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class FansHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_fans_head_pic)
        public ImageView mIvFansHeadPic;
        @Bind(R.id.tv_fans_nickName)
        public TextView mTvFansNickName;
        @Bind(R.id.tv_fans_show)
        public TextView mTvFansShow;
        //开启消息推送行为节点
        @Bind(R.id.iv_focus_type)
        public ShowPopImageView mIvFocusType;
        @Bind(R.id.dividing)
        public View mDividing;

        public FansHolder(View view) {
        super(view);
            ButterKnife.bind(this, view);
        }
}
