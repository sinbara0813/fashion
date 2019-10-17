package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Dec: D2CNEW
 * Author: hrb
 * Date: 2018/6/22 13:15
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class PackProductInfoHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.price)
    public TextView price;
    @Bind(R.id.num)
    public TextView num;
    @Bind(R.id.time)
    public TextView time;
    @Bind(R.id.product_name)
    public TextView productName;
    @Bind(R.id.product_collect_iv)
    public ImageView productCollectIv;
    @Bind(R.id.fx_tv)
    public TextView fxTv;
    @Bind(R.id.fx_share)
    public TextView fxShare;
    @Bind(R.id.fx_qr)
    public TextView fxQr;
    @Bind(R.id.fx_matter)
    public TextView fxMatter;
    @Bind(R.id.fx_rl)
    public RelativeLayout fxRl;
    @Bind(R.id.pack_content_ll)
    public LinearLayout packContentLl;
    @Bind(R.id.more_standard)
    public HorizontalScrollView moreStandard;
    @Bind(R.id.more_standard_ll)
    public LinearLayout moreStandardLl;
    @Bind(R.id.store_num_tv)
    public TextView storeNumTv;
    @Bind(R.id.single_standard)
    public LinearLayout singleStandard;
    @Bind(R.id.pack_ll)
    public LinearLayout packLl;
    @Bind(R.id.collage_role)
    public LinearLayout collageRole;
    @Bind(R.id.store_tag_tv)
    public TextView storeTagTv;
    @Bind(R.id.tv_more_collages)
    public TextView tvMoreCollages;


    public PackProductInfoHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
