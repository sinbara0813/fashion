package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/9/10.
 */

public class UpdateBrandHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.brand_intro_iv)
    public ImageView brandIntroIv;
    @Bind(R.id.brand_head_iv)
    public ImageView brandHeadIv;
    @Bind(R.id.brand_name)
    public TextView brandName;
    @Bind(R.id.brand_update_count_tv)
    public TextView brandUpdateCountTv;
    @Bind(R.id.content_ll)
    public LinearLayout contentLl;

    public UpdateBrandHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
