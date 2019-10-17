package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.ProductRelationBean;
import com.d2cmall.buyer.holder.ProductHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/12 18:11
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductRelationAdapter extends DelegateAdapter.Adapter<ProductHolder> {

    private Context context;
    private List<ProductRelationBean.DataBean.RelationProductsBean> relationList;
    private int itemWidth;

    public ProductRelationAdapter(Context context,List<ProductRelationBean.DataBean.RelationProductsBean> relationList){
        this.context=context;
        this.relationList=relationList;
        itemWidth=(ScreenUtil.getDisplayWidth()-ScreenUtil.dip2px(48))/2;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setAutoExpand(false);
        gridLayoutHelper.setPaddingLeft(ScreenUtil.dip2px(16));
        gridLayoutHelper.setPaddingRight(ScreenUtil.dip2px(16));
        gridLayoutHelper.setHGap(ScreenUtil.dip2px(16));
        return gridLayoutHelper;
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.layout_product_item,parent,false);
        return new ProductHolder(view,itemWidth);
    }

    @Override
    public void onBindViewHolder(ProductHolder productHolder, final int position) {
        final ProductRelationBean.DataBean.RelationProductsBean data=relationList.get(position);
        int height = (int) (itemWidth* ((float) 1558 / 1000));
        UniversalImageLoader.displayImage(context,Util.getD2cProductPicUrl(relationList.get(position).getPic(),itemWidth,height),productHolder.productImage,R.mipmap.ic_logo_empty5,R.mipmap.ic_logo_empty5);
        productHolder.productName.setText(relationList.get(position).getName());
        if (!Util.isEmpty(relationList.get(position).getProductTradeType())) {
            if (data.getProductTradeType().equals("CROSS")) {//跨境商品
                productHolder.tvGlobalTag.setVisibility(View.VISIBLE);
            } else {
                productHolder.tvGlobalTag.setVisibility(View.GONE);
            }
        } else {
            productHolder.tvGlobalTag.setVisibility(View.GONE);
        }
        if(relationList.get(position).getStore()>0){
            productHolder.tvNoStore.setVisibility(View.GONE);
            productHolder.viewNoStore.setVisibility(View.GONE);
        }else{
            productHolder.tvNoStore.setVisibility(View.VISIBLE);
            productHolder.viewNoStore.setVisibility(View.VISIBLE);
        }
        productHolder.productPrice.setText(Util.getProductPrice(relationList.get(position).getPromotionPrice()));
        productHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go2ProductDetail(relationList.get(position).getProductId());
            }
        });
    }

    private void go2ProductDetail(long id){
        Intent intent=new Intent(context, ProductDetailActivity.class);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return relationList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 20;
    }
}
