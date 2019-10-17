package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/09/25 17:06
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class MessagePropertyViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.text_view)
    public TextView mTextView;
    @Bind(R.id.detailtext_view)
    public TextView mDetailtextView;
    @Bind(R.id.tv_money_num)
    public ImageView mTvMoneyNum;

    public MessagePropertyViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

}
