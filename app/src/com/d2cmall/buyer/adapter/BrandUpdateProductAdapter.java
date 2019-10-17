package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.FlashProductListBean;
import com.d2cmall.buyer.holder.FlashProductHolder;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/12/28 11:30
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class BrandUpdateProductAdapter extends DelegateAdapter.Adapter {

    private Context context;
    private List<FlashProductListBean.DataBean.ProductsBean.ListBean> list;

    public BrandUpdateProductAdapter(Context context,List<FlashProductListBean.DataBean.ProductsBean.ListBean> list){
        this.context=context;
        this.list=list;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper=new LinearLayoutHelper();
        linearLayoutHelper.setBgColor(Color.WHITE);
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_flash_product,parent,false);
        return new FlashProductHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        FlashProductHolder productHolder= (FlashProductHolder) holder;
        productHolder.productNoticeTv.setVisibility(View.GONE);
        RelativeLayout.LayoutParams rl= (RelativeLayout.LayoutParams) productHolder.productPrice.getLayoutParams();
        rl.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        FlashProductHolder flashProductViewHolder= (FlashProductHolder) holder;
        UniversalImageLoader.displayImage(context,list.get(position).getImg(),flashProductViewHolder.productIv,R.mipmap.ic_logo_empty5,R.mipmap.ic_logo_empty5);
        flashProductViewHolder.productInfoTv.setText(list.get(position).getName());
        flashProductViewHolder.productSubName.setText(list.get(position).getSubTitle());
        if (list.get(position).getStore()<=0){
            flashProductViewHolder.saleOutTag.setVisibility(View.VISIBLE);
            flashProductViewHolder.productNoticeTv.setText("去看看");
        }else {
            flashProductViewHolder.saleOutTag.setVisibility(View.GONE);
        }
        if (list.get(position).getPromotionId()>0){
            if (list.get(position).getMinPrice() < list.get(position).getSalePrice()){
                setPrice(list.get(position).getSalePrice(),list.get(position).getMinPrice(),flashProductViewHolder.productPrice);
            }else {
                setPrice(list.get(position).getMinPrice(),flashProductViewHolder.productPrice);
            }
        }else {
            if (list.get(position).getMinPrice() < list.get(position).getOriginalPrice()){
                setPrice(list.get(position).getOriginalPrice(),list.get(position).getMinPrice(),flashProductViewHolder.productPrice);
            }else {
                setPrice(list.get(position).getMinPrice(),flashProductViewHolder.productPrice);
            }
        }

        flashProductViewHolder.productIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDetailActivity(list.get(position).getId());
            }
        });
        flashProductViewHolder.productInfoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDetailActivity(list.get(position).getId());
            }
        });
    }

    private void setPrice(double firstPrice, double secondPrice, TextView tv){
        StringBuilder builder=new StringBuilder();
        builder.append("¥").append(Util.getNumberFormat(firstPrice))
                .append("  ¥").append(Util.getNumberFormat(secondPrice));
        int first=builder.toString().indexOf("¥");
        int third=builder.toString().lastIndexOf("¥");
        SpannableString spannableString = new SpannableString(builder.toString());

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#DE000000"));
        spannableString.setSpan(colorSpan, third, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.5f);
        spannableString.setSpan(sizeSpan, third+1, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        StyleSpan styleSpan  = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(styleSpan, third+1, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spannableString.setSpan(strikethroughSpan, first+1, first+1+Util.getNumberFormat(firstPrice).length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv.setText(spannableString);
    }

    private void setPrice(double price,TextView tv){
        StringBuilder builder=new StringBuilder();
        builder.append("¥").append(Util.getNumberFormat(price));
        int first=builder.toString().indexOf("¥");
        SpannableString spannableString = new SpannableString(builder.toString());

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#DE000000"));
        spannableString.setSpan(colorSpan, 0, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.5f);
        spannableString.setSpan(sizeSpan, first+1, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        StyleSpan styleSpan  = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(styleSpan, first+1, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        tv.setText(spannableString);
    }

    private void toDetailActivity(long id){
        Intent intent=new Intent(context,ProductDetailActivity.class);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }
}
