package com.d2cmall.buyer.binder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.base.BaseViewBinder;
import com.d2cmall.buyer.bean.CombProductBean;
import com.d2cmall.buyer.holder.CombProductInfoHolder;
import com.d2cmall.buyer.holder.ProductInfoHolder;
import com.d2cmall.buyer.util.RoundBgSpan1;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/28 17:15
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CombProductInfoBinder implements BaseViewBinder<CombProductInfoHolder> {

    private Context mContext;
    private CombProductBean combProductBean;
    private int lineCount;
    private String code="D2C8001";

    public CombProductInfoBinder(Context context, CombProductBean detailBean){
        this.mContext=context;
        this.combProductBean =detailBean;
    }

    @Override
    public CombProductInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.layout_comb_product_info,parent,false);
        //View view= LayoutInflater.from(mContext).inflate(R.layout.layout_product_info,null); //这种方式获取layoutParam的宽高为负数
        return new CombProductInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(CombProductInfoHolder productInfoHolder, final int position) {
        setData(productInfoHolder);
    }

    @Override
    public void onBindViewHolderWithOffer(CombProductInfoHolder productInfoHolder, int position, int offsetTotal) {

    }

    public void setData(final CombProductInfoHolder productInfoHolder){
        productInfoHolder.productName.setText(combProductBean.getData().getProductComb().getName());
        setPrice(productInfoHolder.productPrice,combProductBean.getData().getProductComb().getPrice(),combProductBean.getData().getProductComb().getOriginalCost());
    }

    private void setPrice(TextView textView,double price,double orgPrice){
        StringBuilder builder=new StringBuilder();
        builder.append("¥").append(Util.getNumberFormat(price));
        SpannableString spannableString;
        if (orgPrice>0 && orgPrice>price){
            builder.append(" ¥").append(Util.getNumberFormat(orgPrice));
            spannableString = new SpannableString(builder.toString());
            int first=Util.getNumberFormat(price).length();
            int second=Util.getNumberFormat(orgPrice).length()+1;

            RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.1f);
            spannableString.setSpan(sizeSpan, 1, first+1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            RelativeSizeSpan sizeSpan1 = new RelativeSizeSpan(0.6f);
            spannableString.setSpan(sizeSpan1, builder.toString().length()-second, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#61000000"));
            spannableString.setSpan(colorSpan, builder.toString().length()-second, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
            spannableString.setSpan(strikethroughSpan, builder.toString().length()-second, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        }else {
            spannableString = new SpannableString(builder.toString());
            RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.1f);
            spannableString.setSpan(sizeSpan, 1, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        }
        textView.setText(spannableString);
    }



    public void setProductPrice(TextView textView,double price) {
        textView.setText(Util.getNumberFormat(price));
    }


}
