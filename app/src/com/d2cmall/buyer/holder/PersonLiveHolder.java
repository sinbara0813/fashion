package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2017/9/5.
 */

public class PersonLiveHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_more_live)
    public TextView tvMoreLive;
    @Bind(R.id.iv_one)
    public ImageView ivOne;
    @Bind(R.id.tv_status_one)
    public TextView tvStatusOne;
    @Bind(R.id.tv_title_one)
    public TextView tvTitleOne;
    @Bind(R.id.rl_one)
    public RelativeLayout rlOne;
    @Bind(R.id.iv_two)
    public ImageView ivTwo;
    @Bind(R.id.tv_status_two)
    public TextView tvStatusTwo;
    @Bind(R.id.tv_title_two)
    public TextView tvTitleTwo;
    @Bind(R.id.rl_two)
    public RelativeLayout rlTwo;
    @Bind(R.id.iv_three)
    public ImageView ivThree;
    @Bind(R.id.tv_status_three)
    public TextView tvStatusThree;
    @Bind(R.id.tv_title_three)
    public TextView tvTitleThree;
    @Bind(R.id.rl_three)
    public RelativeLayout rlThree;
    @Bind(R.id.ll_three)
    public LinearLayout llThree;
    @Bind(R.id.iv_single)
    public ImageView ivSingle;
    @Bind(R.id.iv_live)
    public ImageView ivLive;
    @Bind(R.id.tv_status_single)
    public TextView tvStatusSingle;
    @Bind(R.id.tv_num_look)
    public TextView tvNumLook;
    @Bind(R.id.rl_single_live)
    public RelativeLayout rlSingleLive;
    @Bind(R.id.tv_describe)
    public TextView tvDescribe;
    @Bind(R.id.ll_single_boss)
    public LinearLayout llSingleBoss;
    @Bind(R.id.image)
    public ImageView image;
    @Bind(R.id.img_avatar)
    public ImageView imgAvatar;
    @Bind(R.id.name)
    public TextView name;
    @Bind(R.id.info)
    public TextView info;
    @Bind(R.id.time_tv)
    public TextView timeTv;
    @Bind(R.id.bg_rl)
    public RelativeLayout bgRl;

    public PersonLiveHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
