package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/31.
 * Description : PartnerNoticeHolder
 */

public class PartnerNoticeHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_title)
    public TextView tvTitle;
    @Bind(R.id.iv_notice)
    public ImageView ivNotice;
    @Bind(R.id.tv_date)
    public TextView tvDate;

    public PartnerNoticeHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
