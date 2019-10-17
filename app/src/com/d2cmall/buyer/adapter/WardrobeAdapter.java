package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.bumptech.glide.Glide;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.WardrobeItemDetialActivity;
import com.d2cmall.buyer.bean.WardrobeListItemBean;
import com.d2cmall.buyer.holder.WardrobeListItemHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Fixme
 * Author: LWJ
 * desc:   我的衣橱列表Adapter
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class WardrobeAdapter extends DelegateAdapter.Adapter {
    private Context mContext;
    int itemWidth;
    private List<WardrobeListItemBean.DataBean.MyWardrobesBean.ListBean> wardrobeList;

    public WardrobeAdapter(Context context, int itemWidth, List<WardrobeListItemBean.DataBean.MyWardrobesBean.ListBean> wardrobeList) {
        mContext = context;
        this.wardrobeList = wardrobeList;
        this.itemWidth = itemWidth;
    }


    @Override
    public LayoutHelper onCreateLayoutHelper() {
        GridLayoutHelper layoutHelper = new GridLayoutHelper(2);
        layoutHelper.setGap(ScreenUtil.dip2px(8));
        layoutHelper.setPaddingBottom(ScreenUtil.dip2px(16));
        layoutHelper.setPaddingTop(ScreenUtil.dip2px(16));
        layoutHelper.setAutoExpand(false);
        return layoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.list_item_only_image, parent, false);
        return new WardrobeListItemHolder(itemView, itemWidth);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WardrobeListItemHolder productHolder = (WardrobeListItemHolder) holder;
        int height = (int) (itemWidth * ((float) 238 / 168));
        Glide.with(mContext)
                .load(Util.getD2cProductPicUrl(wardrobeList.get(position).getPic(), itemWidth, height))
                .placeholder(R.mipmap.ic_logo_empty5)
                .override(itemWidth, height)
                .dontAnimate()
                .error(R.mipmap.ic_logo_empty5)
                .into(productHolder.ivImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转详情页
                mContext.startActivity(new Intent(mContext, WardrobeItemDetialActivity.class).putExtra("wardrobeBean", wardrobeList.get(position)));
            }
        });
    }


    @Override
    public int getItemCount() {
        return wardrobeList == null ? 0 : wardrobeList.size();
    }

}
