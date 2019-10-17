package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.ShowPopImageView;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayer;
import com.d2cmall.buyer.widget.ninegrid.NineGridView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/4 15:38
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductShowHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.img_avatar)
    public ImageView imgAvatar;
    @Bind(R.id.img_tag)
    public ImageView imgTag;
    @Bind(R.id.name_tv)
    public TextView nameTv;
    @Bind(R.id.time_tv)
    public TextView timeTv;
    //开启消息推送行为节点
    @Bind(R.id.iv_focus)
    public ShowPopImageView ivFocus;
    @Bind(R.id.content_tv)
    public TextView contentTv;
    @Bind(R.id.nineGrid)
    public NineGridView nineGrid;
    @Bind(R.id.nice_video_player)
    public NiceVideoPlayer niceVideoPlayer;
    @Bind(R.id.video_layout)
    public RelativeLayout videoLayout;
    @Bind(R.id.tv_date)
    public TextView tvDate;
    @Bind(R.id.tv_location)
    public TextView tvLocation;
    @Bind(R.id.like_num)
    public TextView likeNum;
    @Bind(R.id.comment_num)
    public TextView commentNum;
    @Bind(R.id.info_tv)
    public TextView infoTv;

    public ProductShowHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
