package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.LineGridView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2018/3/23.
 */

public class ClassifyScreenHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_name)
    public TextView tvName;
    @Bind(R.id.iv_arrow)
    public ImageView ivArrow;
    @Bind(R.id.ll_title)
    public LinearLayout llTitle;
    @Bind(R.id.item_classify_grid)
    public LineGridView itemClassifyGrid;

    public ClassifyScreenHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
