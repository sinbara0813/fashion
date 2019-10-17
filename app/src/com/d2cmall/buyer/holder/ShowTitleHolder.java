package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2017/8/24.
 */

public class ShowTitleHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_text)
    public TextView tvText;

    public ShowTitleHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
