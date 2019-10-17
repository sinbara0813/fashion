package com.d2cmall.buyer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.listener.OnItemClickListener;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;


/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/27 16:38
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class Banner extends DefineBaseIndicatorBanner<String,Banner> {

    private OnItemClickListener itemClickListener;
    private int loadingId;
    private int bottomPadding;
    private boolean crop;
    private RequestManager requestManager;

    public Banner(Context context) {
        this(context,null);
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Banner(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
    }

    @Override
    public View onCreateItemView(final int position) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.layout_banner_item,null);
        if (position<mDatas.size()){
            ImageView imageView=(ImageView) view;
            if (crop){
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }
            if (requestManager!=null){
                requestManager.load(Util.getD2cPicUrl(mDatas.get(position))).placeholder(loadingId).error(loadingId).into(imageView);
            }else {
                UniversalImageLoader.displayImage(mContext,mDatas.get(position),imageView,loadingId,loadingId);
            }
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener!=null){
                        itemClickListener.itemClick(v,position);
                    }
                }
            });
        }
        return view;
    }

    public Banner setLoadingPic(int id){
        this.loadingId=id;
        return this;
    }

    public Banner setOnItemClickListener(OnItemClickListener listener){
        this.itemClickListener=listener;
        return this;
    }

    public Banner setScaleType(boolean is){
        this.crop=is;
        return this;
    }

    public Banner setRequestManager(RequestManager requestManager) {
        this.requestManager = requestManager;
        return this;
    }
}
