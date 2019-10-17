package com.d2cmall.buyer.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.SimplePlayActivity;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/23 18:48
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class VideoBanner extends DefineBaseIndicatorBanner<String,VideoBanner>  {

    private int loadingId;
    private String videoUrl;
    private Context context;
    private boolean showNewYear;
    private ImageView.ScaleType scaleType;

    public VideoBanner(Context context) {
        this(context,null);
    }

    public VideoBanner(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VideoBanner(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        this.context=context;
    }

    @Override
    public View onCreateItemView(final int position) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.layout_banner_pic_item,null);
        if (position<mDatas.size()){
            ImageView imageView= (ImageView) view.findViewById(R.id.image);
            final ImageView tag= (ImageView) view.findViewById(R.id.image_tag);
            ImageView imageView1= (ImageView) view.findViewById(R.id.image_new_year);
            if (!Util.isEmpty(videoUrl)){
                if (position==0){
                    tag.setVisibility(VISIBLE);
                }else {
                    tag.setVisibility(GONE);
                }
            }
            if (showNewYear){
                if (position==0){
                    UniversalImageLoader.displayImage(mContext,"https://static.d2c.cn/img/promo/icon_mark_bigspring.png",imageView1);
                    imageView1.setVisibility(VISIBLE);
                }else {
                    imageView1.setVisibility(GONE);
                }
            }
            if(scaleType!=null){
                imageView.setScaleType(scaleType);
            }
            UniversalImageLoader.displayImage(mContext,mDatas.get(position),imageView
                    ,loadingId,loadingId);
            tag.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SimplePlayActivity.class);
                    intent.putExtra("url", videoUrl);
                    context.startActivity(intent);
                }
            });
        }
        return view;
    }

    public VideoBanner setLoadingPic(int id){
        this.loadingId=id;
        return this;
    }

    public VideoBanner setVideoUrl(String videoUrl){
        this.videoUrl=videoUrl;
        return this;
    }

    public VideoBanner setScaleType(ImageView.ScaleType scaleType){
        this.scaleType=scaleType;
        return this;
    }

    public VideoBanner setNewYear(boolean is){
        this.showNewYear=is;
        return this;
    }

}
