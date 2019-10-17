package com.d2cmall.buyer.binder;

import android.view.ViewGroup;

import com.d2cmall.buyer.base.BaseViewBinder;
import com.d2cmall.buyer.holder.RecommendProductHolder;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/2 12:02
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class RecommendProductBinder implements BaseViewBinder<RecommendProductHolder> {


    @Override
    public RecommendProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==0){

        }else {

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecommendProductHolder recommendProductHolder, int position) {

    }

    @Override
    public void onBindViewHolderWithOffer(RecommendProductHolder recommendProductHolder, int position, int offsetTotal) {

    }
}
