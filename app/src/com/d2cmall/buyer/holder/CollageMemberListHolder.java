package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.RoundedImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/6/22.
 * Description : CollageMemberListHolder
 */

public class CollageMemberListHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_memeber_head)
        public RoundedImageView ivMemeberHead;
        @Bind(R.id.tv_name)
        public TextView tvName;
        @Bind(R.id.tv_time)
        public TextView tvTime;
        @Bind(R.id.iv_colonel)
        public ImageView ivColonel;
        @Bind(R.id.tv_status)
        public TextView tvStatus;
        @Bind(R.id.line_layout)
        View lineLayout;

        public CollageMemberListHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

}
