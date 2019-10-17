package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fixme
 * Author: LWJ
 * desc:    反馈消息holder
 * Date: 2017/09/12 15:20
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class MessageFeedBackHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.text_view)
        public  TextView mTextView;
        @Bind(R.id.detailtext_view)
        public  TextView mDetailtextView;

    public MessageFeedBackHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
