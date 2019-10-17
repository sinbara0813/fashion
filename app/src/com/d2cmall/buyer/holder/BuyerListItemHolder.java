package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.RoundedImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2018/4/17.
 */

public class BuyerListItemHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.head_iv)
    public RoundedImageView headIv;
    @Bind(R.id.tv_name)
    public TextView tvName;
    @Bind(R.id.iv_identity)
    public ImageView ivIdentity;
    @Bind(R.id.iv_try)
    public ImageView ivTry;
    @Bind(R.id.ll_name)
    public LinearLayout llName;
    @Bind(R.id.tv_grade)
    public TextView tvGrade;
    @Bind(R.id.tv_join_time)
    public TextView tvJoinTime;
    @Bind(R.id.tv_sell_time)
    public TextView tvSellTime;
    @Bind(R.id.rl_content)
    public RelativeLayout rlContent;
    @Bind(R.id.rl_top)
    public RelativeLayout rlTop;
    @Bind(R.id.tv_left_data)
    public TextView tvLeftData;
    @Bind(R.id.tv_notice_left)
    public TextView tvNoticeLeft;
    @Bind(R.id.tv_sell_right)
    public TextView tvSellRight;
    @Bind(R.id.tv_notice_right)
    public TextView tvNoticeRight;
    @Bind(R.id.ll_data)
    public LinearLayout llData;
    @Bind(R.id.tv_sign)
    public TextView tvSign;
    @Bind(R.id.iv_closed)
    public ImageView ivClose;

    public BuyerListItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
