package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.GoodsBean;
import com.d2cmall.buyer.holder.RecommendProductHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/5 17:19
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductRecommendAdapter extends DelegateAdapter.Adapter {

    private Context context;
    private List<GoodsBean.DataBean.ProductsBean.ListBean> datas;
    private int itemWidth;
    private int count;

    public ProductRecommendAdapter(Context context, List<GoodsBean.DataBean.ProductsBean.ListBean> datas, int itemWidth,int count){
        this.context=context;
        this.datas=datas;
        this.itemWidth=itemWidth;
        this.count=count;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_recommend_product_item,new LinearLayout(context),false);
        return new RecommendProductHolder(view,itemWidth);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecommendProductHolder recommendProductHolder= (RecommendProductHolder) holder;
        GoodsBean.DataBean.ProductsBean.ListBean listBean = datas.get(position);
        int height = (int) (itemWidth* ((float) 1558 / 1000));
        UniversalImageLoader.displayImage(context, Util.getD2cProductPicUrl(datas.get(position).getImg(),itemWidth,height),recommendProductHolder.image, R.mipmap.ic_logo_empty6, R.mipmap.ic_logo_empty6);
        recommendProductHolder.productName.setText(datas.get(position).getName());
        if(listBean.getPromotionPrice()!=0) {
            if(listBean.getPromotionPrice()>=listBean.getOriginalPrice()) {
                recommendProductHolder.productDropPrice.setVisibility(View.GONE);
            }else{
                recommendProductHolder.productDropPrice.setVisibility(View.VISIBLE);
                recommendProductHolder.productDropPrice.setText("¥"+Util.getNumberFormat(listBean.getOriginalPrice()));
                recommendProductHolder.productDropPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
            }
            recommendProductHolder.productPrice.setText(Util.getNumberFormat(listBean.getPromotionPrice()));
        }else{
            if(listBean.getMinPrice()==listBean.getOriginalPrice()) {
                recommendProductHolder.productDropPrice.setVisibility(View.GONE);
            }else{
                recommendProductHolder.productDropPrice.setVisibility(View.VISIBLE);
                recommendProductHolder.productDropPrice.setText("¥"+Util.getNumberFormat(listBean.getOriginalPrice()));
                recommendProductHolder.productDropPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
            }
            if(listBean.getMinPrice()!=0) {
                recommendProductHolder.productPrice.setText(Util.getNumberFormat(listBean.getMinPrice()));
            }else{
                recommendProductHolder.productPrice.setText(Util.getNumberFormat(listBean.getPrice()));
            }

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id",Long.valueOf(datas.get(position).getId()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (count>0){
            return count;
        }else {
            return datas.size();
        }
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setAutoExpand(false);
        gridLayoutHelper.setPaddingLeft(ScreenUtil.dip2px(16));
        gridLayoutHelper.setPaddingRight(ScreenUtil.dip2px(16));
        gridLayoutHelper.setPaddingBottom(ScreenUtil.dip2px(16));
        gridLayoutHelper.setGap(ScreenUtil.dip2px(16));
        return gridLayoutHelper;
    }
}
