package com.d2cmall.buyer.binder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.HomeActivity;
import com.d2cmall.buyer.activity.MyOrderActivity;
import com.d2cmall.buyer.base.BaseViewBinder;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.holder.PaySuccessTopHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.GuideLayout;

import de.greenrobot.event.EventBus;

/**
 * 作者:Created by sinbara on 2018/10/29.
 * 邮箱:hrb940258169@163.com
 */

public class PaySuccessTopBinder implements BaseViewBinder<PaySuccessTopHolder> {

    private String ad;
    private String url;
    private Context mContext;

    public PaySuccessTopBinder(Context context){
        mContext=context;
    }

    @Override
    public PaySuccessTopHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.layout_pay_success_top,parent,false);
        return new PaySuccessTopHolder(view);
    }

    @Override
    public void onBindViewHolder(PaySuccessTopHolder topHolder, int position) {
        if (!Util.isEmpty(ad)){
            //topHolder.ll.setBackgroundColor(mContext.getResources().getColor(R.color.gray_line));
            topHolder.adImage.setVisibility(View.VISIBLE);
            topHolder.viewDivider.setVisibility(View.GONE);
            Glide.with(mContext).load(Util.getD2cPicUrl(ad)).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    topHolder.adImage.getLayoutParams().height=resource.getHeight()* ScreenUtil.getDisplayWidth()/resource.getWidth();
                    topHolder.adImage.setImageBitmap(resource);
                }
            });
            if (!Util.isEmpty(url)){
                topHolder.adImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Util.urlAction(mContext,url);
                    }
                });
            }
        }
        topHolder.llBackMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //除homeactivity所有activity要关闭
                GuideLayout.back();
                EventBus.getDefault().post(new ActionBean(Constants.ActionType.CLEAR_ALL_ACTIVITY));
                Intent intent=new Intent(mContext,HomeActivity.class);
                mContext.startActivity(intent);
                ActionBean actionBean=new ActionBean(Constants.ActionType.CHANGE_PAGE);
                actionBean.put("firstIndex",1);
                EventBus.getDefault().post(actionBean);
            }
        });
        topHolder.llLookOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuideLayout.back();
                EventBus.getDefault().post(new ActionBean(Constants.ActionType.CLEAR_ALL_ACTIVITY));
                Intent intent=new Intent(mContext, MyOrderActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void onBindViewHolderWithOffer(PaySuccessTopHolder paySuccessTopHolder, int position, int offsetTotal) {

    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
