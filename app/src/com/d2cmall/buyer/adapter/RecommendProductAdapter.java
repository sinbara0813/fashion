package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.ProductDetailBean;
import com.d2cmall.buyer.holder.ProductHolder;
import com.d2cmall.buyer.holder.RecommendProductHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.tendcloud.tenddata.TCAgent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车设配器
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/2 13:15
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class RecommendProductAdapter extends DelegateAdapter.Adapter {

    private Context context;
    private List<ProductDetailBean.DataBean.RecommendProductsBean> list;
    private int itemWidth;

    public RecommendProductAdapter(Context context, List<ProductDetailBean.DataBean.RecommendProductsBean> list, int itemWidth){
        this.context=context;
        this.list =list;
        this.itemWidth=itemWidth;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_product_item,new LinearLayout(context),false);
        return new ProductHolder(view,itemWidth);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ProductHolder productHolder= (ProductHolder) holder;
        int height = (int) (itemWidth* ((float) 1558 / 1000));
        ProductDetailBean.DataBean.RecommendProductsBean data=list.get(position);
        UniversalImageLoader.displayImage(context, Util.getD2cProductPicUrl(data.getImg(),itemWidth,height),productHolder.productImage, R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
        productHolder.productName.setText(data.getName());
        if (!Util.isEmpty(data.getProductTradeType())) {
            if (data.getProductTradeType().equals("CROSS")) {//跨境商品
                productHolder.tvGlobalTag.setVisibility(View.VISIBLE);
            } else {
                productHolder.tvGlobalTag.setVisibility(View.GONE);
            }
        } else {
            productHolder.tvGlobalTag.setVisibility(View.GONE);
        }
        if (data.getStore()<=0){
            productHolder.tvNoStore.setVisibility(View.VISIBLE);
            productHolder.viewNoStore.setVisibility(View.VISIBLE);
        }else {
            productHolder.tvNoStore.setVisibility(View.GONE);
            productHolder.viewNoStore.setVisibility(View.GONE);
        }
        //显示价格
        long promotionId = data.getPromotionId();
        if(promotionId>0) {
            if (data.getSalePrice() > data.getMinPrice()) {
                productHolder.productPrice.setText(Util.getProductPrice(data.getMinPrice(),data.getSalePrice()));
            } else {
                productHolder.productPrice.setText(Util.getProductPrice(data.getMinPrice()));
            }
        }else{
            if (data.getOriginalPrice() > data.getMinPrice()) {
                productHolder.productPrice.setText(Util.getProductPrice(data.getMinPrice(),data.getOriginalPrice()));
            } else {
                productHolder.productPrice.setText(Util.getProductPrice(data.getMinPrice()));
            }
        }

        productHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDetailActivity(position,list.get(position).getId());
            }
        });
    }

    private void stat(String event, String label, int position,long id){
        Map<String,String> map=new HashMap<>();
        map.put(label+"_位置","位置"+String.valueOf(position+1));
        map.put(label+"_url","product/"+id);
        TCAgent.onEvent(context,event,label,map);
    }

    /**
     * 设置吊牌价
     * @param textView
     * @param dropPrice
     */
    private void setDropPrice(TextView textView,double dropPrice){
        textView.setVisibility(View.VISIBLE);
        textView.setText("¥"+ Util.getNumberFormat(dropPrice));
        textView.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
    }

    /**
     * 跳转商品详情
     * @param id
     */
    private void toDetailActivity(int position,long id){
        stat("V3购物车","为你推荐",position,id);
        Intent intent=new Intent(context, ProductDetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return   list==null?0:list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 10;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setAutoExpand(false);
        gridLayoutHelper.setPaddingLeft(ScreenUtil.dip2px(16));
        gridLayoutHelper.setPaddingRight(ScreenUtil.dip2px(16));
        gridLayoutHelper.setPaddingBottom(ScreenUtil.dip2px(16));
        gridLayoutHelper.setHGap(ScreenUtil.dip2px(16));
        return gridLayoutHelper;
    }
}
