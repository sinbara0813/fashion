package com.d2cmall.buyer.binder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseViewBinder;
import com.d2cmall.buyer.bean.CombProductBean;
import com.d2cmall.buyer.holder.CombProductInfoHolder;
import com.d2cmall.buyer.util.Util;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/28 17:15
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CombProductItemBinder implements BaseViewBinder<CombProductInfoHolder> {

    private Context mContext;
    private CombProductBean comdityDetailBean;
    private int lineCount;
    private String code="D2C8001";

    public CombProductItemBinder(Context context, CombProductBean detailBean){
        this.mContext=context;
        this.comdityDetailBean=detailBean;
    }

    @Override
    public CombProductInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.layout_comb_product_item,parent,false);
        //View view= LayoutInflater.from(mContext).inflate(R.layout.layout_product_info,null); //这种方式获取layoutParam的宽高为负数
        return new CombProductInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(CombProductInfoHolder productInfoHolder, int position) {
        setData(productInfoHolder);
    }

    @Override
    public void onBindViewHolderWithOffer(CombProductInfoHolder productInfoHolder, int position, int offsetTotal) {

    }

    public void setData(final CombProductInfoHolder productInfoHolder){
    }



    public void setProductPrice(TextView textView,double price) {
        textView.setText(Util.getNumberFormat(price));
    }



    public void setOpenListener(final TextView iv, final TextView tv){
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.setVisibility(View.GONE);
                final int startValue=tv.getHeight();
                final int deltaValue=tv.getLineHeight()*lineCount-startValue;
                Animation animation = new Animation() {
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        tv.setHeight((int) (startValue + deltaValue * interpolatedTime));
                        if (interpolatedTime==1){
                            tv.setMaxLines(lineCount);
                        }
                    }
                };
                animation.setDuration(350);
                animation.setFillAfter(true);
                tv.startAnimation(animation);
            }
        });
    }

}
