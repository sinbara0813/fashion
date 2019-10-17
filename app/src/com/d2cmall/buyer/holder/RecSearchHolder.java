package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2017/8/18.
 */

public class RecSearchHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_promotion_filter)
    public TextView tvPromotionFilter;
    @Bind(R.id.empty_image)
    public ImageView emptyImage;

    public RecSearchHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
