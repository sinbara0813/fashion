package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayer;
import com.d2cmall.buyer.widget.ninegrid.NineGridView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Dec: D2CNEW
 * Author: hrb
 * Date: 2018/8/9 15:59
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class ProductCommendHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.img_avatar)
    public ImageView imgAvatar;
    @Bind(R.id.img_tag)
    public ImageView imgTag;
    @Bind(R.id.name_tv)
    public TextView nameTv;
    @Bind(R.id.like_num)
    public TextView likeNum;
    @Bind(R.id.comment_num)
    public TextView commentNum;
    @Bind(R.id.content_tv)
    public TextView contentTv;
    @Bind(R.id.time_tv)
    public TextView timeTv;
    @Bind(R.id.nineGrid)
    public NineGridView nineGrid;
    @Bind(R.id.nice_video_player)
    public NiceVideoPlayer niceVideoPlayer;
    @Bind(R.id.video_layout)
    public RelativeLayout videoLayout;
    @Bind(R.id.reply)
    public TextView reply;
    @Bind(R.id.add_review_time)
    public TextView addReviewTime;
    @Bind(R.id.add_review_content_tv)
    public TextView addReviewContentTv;
    @Bind(R.id.add_review_nineGrid)
    public NineGridView addReviewNineGrid;
    @Bind(R.id.add_review_reply)
    public TextView addReviewReply;
    @Bind(R.id.add_review_ll)
    public LinearLayout addReviewLl;

    public ProductCommendHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
