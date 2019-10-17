package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.MainBean;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2018/1/16 15:48
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class Type11Adapter extends RecyclerView.Adapter<Type11Adapter.ViewHolder> {

    private Context context;
    private List<MainBean.DataEntity.ContentEntity.SectionValuesEntity> items;

    public Type11Adapter(Context context, List<MainBean.DataEntity.ContentEntity.SectionValuesEntity> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_banner_item,parent,false);
        return new Type11Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        UniversalImageLoader.displayImage(context,items.get(position+1).getFrontPic(),holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.urlAction(context,items.get(position+1).getUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size()-1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.image)
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            if (image.getLayoutParams()!=null){
                image.getLayoutParams().width=ScreenUtil.dip2px(155);
                image.getLayoutParams().height=ScreenUtil.dip2px(95);
            }
        }
    }
}
