package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/09/12 16:44
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class MessagePreviewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.text_view)
    public TextView mTextView;
    @Bind(R.id.detailtext_view)
    public TextView mDetailtextView;
    @Bind(R.id.text_seemore)
    public TextView mTextSeemore;

    public MessagePreviewHolder(View itemView) {
        super(itemView);
    }



}
