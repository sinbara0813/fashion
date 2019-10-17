package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.RoundedImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2018/1/2.
 */

public class LiveHeadHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.iv_head)
    public RoundedImageView ivHead;

    public LiveHeadHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
