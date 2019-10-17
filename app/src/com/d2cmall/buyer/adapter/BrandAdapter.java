package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.BrandDetailActivity;
import com.d2cmall.buyer.bean.MainBrandBean;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/12 16:32
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ViewHolder> {

    private Context context;
    private List<MainBrandBean.DataBean.RecommendBrandPicBean> picList;
    private String tag;

    public BrandAdapter(Context context,List<MainBrandBean.DataBean.RecommendBrandPicBean> picList){
        this.context=context;
        if (picList.size()>6){
            this.picList=new ArrayList<>();
            for (int i=0; i<6; i++){
                this.picList.add(picList.get(i));
            }
        }else{
            this.picList=picList;
        }
    }

    @Override
    public BrandAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView imageView=new ImageView(context);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ScreenUtil.dip2px(60),ScreenUtil.dip2px(60));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(layoutParams);
        imageView.setBackgroundResource(R.drawable.sp_line);
        imageView.setPadding(ScreenUtil.dip2px((float) 0.5),ScreenUtil.dip2px((float) 0.5),ScreenUtil.dip2px((float) 0.5),ScreenUtil.dip2px((float) 0.5));
        layoutParams.setMargins(ScreenUtil.dip2px(16),0,0,0);
        return new ViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(BrandAdapter.ViewHolder holder, final int position) {
            UniversalImageLoader.displayImage(context,picList.get(position).getHeadPic(),holder.imageView);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = picList.get(position).getId();
                Intent intent=new Intent(context, BrandDetailActivity.class);
                intent.putExtra("id",id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return picList==null?0:picList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView;
        }
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
