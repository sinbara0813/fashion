package com.d2cmall.buyer.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/27 18:53
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public interface BaseViewBinder<T extends RecyclerView.ViewHolder>{

    T onCreateViewHolder(ViewGroup parent, int viewType);

    void onBindViewHolder(T t, int position);

    void onBindViewHolderWithOffer(T t, int position, int offsetTotal);

}
