package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.ninegrid.NineGridView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fixme
 * Author: LWJ
 * desc:    商品报告holder
 * Date: 2017/10/12 14:43
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class ProductReportHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.img_avatar)
    public ImageView mImgAvatar;
    @Bind(R.id.img_tag)
    public ImageView mImgTag;
    @Bind(R.id.name_tv)
    public TextView mNameTv;
    @Bind(R.id.tv_check_statue)
    public TextView mTvCheckStatue;
    @Bind(R.id.content_tv)
    public TextView mContentTv;
    @Bind(R.id.nineGrid)
    public NineGridView mNineGrid;
    @Bind(R.id.tv_report_time)
    public TextView mTvReportTime;
    @Bind(R.id.tv_cancle_commit)
    public TextView mTvCancleCommit;
    @Bind(R.id.rl_user_action)
    public RelativeLayout mRlUserAction;
    @Bind(R.id.tv_reason)
    public TextView mTvReason;
    @Bind(R.id.tv_reason_desc)
    public TextView mTvReasonDesc;
    @Bind(R.id.rl_result)
    public RelativeLayout mRlResult;
    @Bind(R.id.rl_title)
    public RelativeLayout mRlTitle;
    @Bind(R.id.interval)
    public View viewInterval;

    public ProductReportHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
