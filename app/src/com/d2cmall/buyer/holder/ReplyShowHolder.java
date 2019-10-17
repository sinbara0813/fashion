package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2017/8/24.
 */

public class ReplyShowHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.iv_user_head_pic)
    public ImageView ivUserHeadPic;
    @Bind(R.id.tv_user_name)
    public TextView tvUserName;
    @Bind(R.id.tv_post_time)
    public TextView tvPostTime;
    @Bind(R.id.tv_like_num)
    public TextView tvLikeNum;
    @Bind(R.id.tv_reply_click)
    public TextView tvReplyClick;
    @Bind(R.id.ll_user_info)
    public LinearLayout llUserInfo;
    @Bind(R.id.tv_reply_content)
    public TextView tvReplyContent;
    @Bind(R.id.tv_reply_original_content)
    public TextView tvReplyOriginalContent;

    public ReplyShowHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
