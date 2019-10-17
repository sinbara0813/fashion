package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.BrandDetailActivity;
import com.d2cmall.buyer.bean.MainBrandBean;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/09/13 11:20
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class BigCardTopAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<MainBrandBean.DataBean.RecommendBrandPicBean> recommendBrandPic;
    private List<MainBrandBean.DataBean.RecommendBrandPicBean> tempBrandPic=new ArrayList<>();
    private List<MainBrandBean.DataBean.RecommendBrandPicBean> lestBrandPic=new ArrayList<>();
    private boolean isOpen=false;
    //最初的集合长度
    private int originSize;
    public BigCardTopAdapter(Context context, List<MainBrandBean.DataBean.RecommendBrandPicBean> recommendBrandPic) {
        mContext = context;
        this.recommendBrandPic = recommendBrandPic;
        originSize=recommendBrandPic.size();
        initTempBrandPic();
    }

    private void initTempBrandPic() {
        int size = recommendBrandPic.size();
        if (size>5){
            for (int i=0; i<5; i++ ){
                lestBrandPic.add(recommendBrandPic.get(i));
            }
            for (int i=5; i<size; i++ ){
                tempBrandPic.add(recommendBrandPic.get(i));
            }
            toColseData();
        }

    }
    private void toColseData(){
        recommendBrandPic.clear();
        recommendBrandPic.addAll(lestBrandPic);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_big_card_icon_item, parent, false);
        return new MyViewHolder(view, ScreenUtil.dip2px(56));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        int size = recommendBrandPic.size();
        if (position<size){
            UniversalImageLoader.displayImage(mContext,recommendBrandPic.get(position).getHeadPic(),myViewHolder.mImage);
        }
        if (!isOpen && originSize>5 && position==4){
            UniversalImageLoader.displayImage(mContext,myViewHolder.mImage,R.mipmap.icon_home_morelogo);
        }
        if (isOpen && size>5 && position==size){
            UniversalImageLoader.displayImage(mContext,myViewHolder.mImage,R.mipmap.icon_home_uplogo);
        }



        myViewHolder.mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colseOrOpen(position);
            }
        });

    }

    private void colseOrOpen(int position) {
        if (!isOpen && originSize>5 && position==4){
            recommendBrandPic.addAll(tempBrandPic);
            isOpen=!isOpen;
            notifyDataSetChanged();
        }else if(isOpen && originSize>5 && position==originSize){
            toColseData();
            isOpen=!isOpen;
            notifyDataSetChanged();
        }else{
            int id = recommendBrandPic.get(position).getId();
            Intent intent=new Intent(mContext, BrandDetailActivity.class);
            intent.putExtra("id",id);
            mContext.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return recommendBrandPic.size()>5?recommendBrandPic.size()+1:recommendBrandPic.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.image)
        ImageView mImage;

        MyViewHolder(View view,int itemWidth) {
            super(view);
            ButterKnife.bind(this, view);
            LinearLayout.LayoutParams ll= new LinearLayout.LayoutParams(-2,-2);
            ll.width=itemWidth;
            ll.height=itemWidth;
            ll.topMargin=ScreenUtil.dip2px(16);
            mImage.setLayoutParams(ll);
        }
    }
}
