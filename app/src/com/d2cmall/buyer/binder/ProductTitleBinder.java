package com.d2cmall.buyer.binder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseViewBinder;
import com.d2cmall.buyer.holder.ProductItemTitleHolder;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/5 17:27
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductTitleBinder implements BaseViewBinder<ProductItemTitleHolder> {

    private Context context;
    private String titleName;
    public boolean hasMore;
    public boolean hasTop=true;
    private View.OnClickListener onClickListener;

    public ProductTitleBinder(Context context,String titleName){
        this.context=context;
        this.titleName=titleName;
    }

    @Override
    public ProductItemTitleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.layout_product_item_title,parent,false);
        return new ProductItemTitleHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductItemTitleHolder productItemTitleHolder, int position) {
        productItemTitleHolder.titleName.setText(titleName);
        productItemTitleHolder.line.setVisibility(View.GONE);
        if (hasMore){
            productItemTitleHolder.titleMore.setVisibility(View.VISIBLE);
            if (onClickListener!=null){
                productItemTitleHolder.titleMore.setOnClickListener(onClickListener);
            }
        }else {
            productItemTitleHolder.titleMore.setVisibility(View.GONE);
        }
        if (!hasTop){
            productItemTitleHolder.topLine.setVisibility(View.GONE);
        }else {
            productItemTitleHolder.topLine.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBindViewHolderWithOffer(ProductItemTitleHolder productItemTitleHolder, int position, int offsetTotal) {

    }

    public void setClickMoreListener(View.OnClickListener clickMoreListener){
        this.onClickListener=clickMoreListener;
    }
}
