package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.PromotionBean;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Fixme
 * Author: LWJ
 * desc:   活动列表头部bannerAdapter
 * Date: 2017/09/06 19:20
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class PromotionTopAdapter extends DelegateAdapter.Adapter {
    private Context mContext;
    private Handler mHandler;

    public void setPromotionBean(PromotionBean promotionBean) {
        this.promotionBean = promotionBean;
        startTime= DateUtil.strToDateLong(promotionBean.getData().getPromotion().getStartTime()).getTime();
        endTime=DateUtil.strToDateLong(promotionBean.getData().getPromotion().getEndTime()).getTime();
    }

    private PromotionBean promotionBean;
    int itemWidth;
    private long startTime;
    private long endTime;
    public PromotionTopAdapter(Context context) {
        mContext = context;
    }


    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper layoutHelper = new LinearLayoutHelper();
        return layoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.list_head_promotion, parent, false);
        return new TopViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final TopViewHolder topViewHolder = (TopViewHolder) holder;
        if (promotionBean == null) {
            return;
        }
        if (!Util.isEmpty(promotionBean.getData().getWapBanner())) {
            topViewHolder.rlBanner.setVisibility(View.VISIBLE);
            UniversalImageLoader.displayImage(mContext, Util.getD2cPicUrl(promotionBean.getData().getWapBanner()), topViewHolder.imgBanner
                    , R.mipmap.ic_logo_empty7, R.mipmap.ic_logo_empty7);
        }else{
            topViewHolder.rlBanner.setVisibility(View.GONE);
        }
        //活动优惠的介绍
        if(!Util.isEmpty(promotionBean.getData().getPromotion().getPromotionTypeName())){
            topViewHolder.tvDiscount.setText(mContext.getString(R.string.label_promotion_name,promotionBean.getData().getPromotion().getPromotionTypeName()));
        }

        topViewHolder.tvDiscountDesc.setText(promotionBean.getData().getPromotion().getPromotionSulo());
        topViewHolder.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity)mContext;
                activity.finish();
            }
        });
        topViewHolder.llDiscount.setVisibility(View.VISIBLE);
         //活动是否结束,倒计时
        if(System.currentTimeMillis()<endTime){//活动未结束
            topViewHolder.productFlashSale.setVisibility(View.VISIBLE);
            topViewHolder.tvEndTag.setVisibility(View.GONE);
        }else if(System.currentTimeMillis()>endTime){//已结束
            topViewHolder.tvEndTag.setVisibility(View.VISIBLE);
            topViewHolder.productFlashSale.setVisibility(View.GONE);
        }
        if(System.currentTimeMillis()<startTime){
            topViewHolder.timeTag.setText("距开始");
        }else if(System.currentTimeMillis()>startTime && System.currentTimeMillis()<endTime){
            topViewHolder.timeTag.setText("距结束");
        }
        if (System.currentTimeMillis()<endTime && promotionBean.getData().getPromotion().getStatus()){ //活动未结束
            if (mHandler!=null){
                mHandler.removeCallbacksAndMessages(null);
            }
            //设置初始值
            setTime(topViewHolder.timeTag,topViewHolder.timeDay,topViewHolder.timeHour,topViewHolder.timeMinute,topViewHolder.timeMouse,topViewHolder.timeMs,topViewHolder.productFlashSale,topViewHolder.llDiscount,topViewHolder.tvEndTag);
            mHandler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    //更改时间
                    setTime(topViewHolder.timeTag,topViewHolder.timeDay,topViewHolder.timeHour,topViewHolder.timeMinute,topViewHolder.timeMouse,topViewHolder.timeMs,topViewHolder.productFlashSale,topViewHolder.llDiscount, topViewHolder.tvEndTag);
                    mHandler.sendEmptyMessageDelayed(1,100);
                }
            };
            mHandler.sendEmptyMessageDelayed(1,100);
        }
        if(!promotionBean.getData().getPromotion().getStatus()){
            topViewHolder.tvEndTag.setVisibility(View.VISIBLE);
            topViewHolder.productFlashSale.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return 1;
    }

    private void setTime(TextView timeTag, TextView dayTv, TextView hourTv, TextView minuteTv, TextView mouseTv, TextView msTv, LinearLayout llTime, LinearLayout llDiscount, TextView tvEndTag){
        long offer=startTime-System.currentTimeMillis();
        if(System.currentTimeMillis()<startTime){
            offer=startTime-System.currentTimeMillis();
            timeTag.setText("距开始");
        }else if(System.currentTimeMillis()>startTime && System.currentTimeMillis()<endTime){
            timeTag.setText("距结束");
            offer=endTime-System.currentTimeMillis();
        }else if( System.currentTimeMillis()>endTime){
            llTime.setVisibility(View.GONE);
            tvEndTag.setText("活动已结束");
            tvEndTag.setVisibility(View.VISIBLE);
        }

        long day=offer/(24*60*60*1000);
        long hour=offer/(60*60*1000)%24;
        long minute=(offer/(60*1000))%60;
        long mouse=(offer/1000)%60;
        long ms=(offer%1000)/100;
        dayTv.setText(addZero((int)day));
        hourTv.setText(addZero((int)hour));
        minuteTv.setText(addZero((int)minute));
        mouseTv.setText(addZero((int)mouse));
        msTv.setText(String.valueOf(ms));
    }

    private String addZero(int num) {
        if (num < 10) {
            return "0" + num;
        } else {
            return String.valueOf(num);
        }
    }

    public void releaseResource() {
        if(mHandler!=null){
            mHandler.removeCallbacksAndMessages(null);
            mHandler=null;
        }
    }


    static class TopViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.img_banner)
        ImageView imgBanner;
        @Bind(R.id.btn_back)
        ImageView btnBack;
        @Bind(R.id.time_tag)
        TextView timeTag;
        @Bind(R.id.time_day)
        TextView timeDay;
        @Bind(R.id.time_hour)
        TextView timeHour;
        @Bind(R.id.time_minute)
        TextView timeMinute;
        @Bind(R.id.time_mouse)
        TextView timeMouse;
        @Bind(R.id.time_ms)
        TextView timeMs;
        @Bind(R.id.product_flash_sale)
        LinearLayout productFlashSale;
        @Bind(R.id.tv_discount)
        TextView tvDiscount;
        @Bind(R.id.tv_discount_desc)
        TextView tvDiscountDesc;
        @Bind(R.id.rl_banner)
        RelativeLayout rlBanner;
        @Bind(R.id.ll_discount)
        LinearLayout llDiscount;
        @Bind(R.id.tv_end_tag)
        TextView tvEndTag;
        TopViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
