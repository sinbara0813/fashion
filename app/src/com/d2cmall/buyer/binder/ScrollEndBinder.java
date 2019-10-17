package com.d2cmall.buyer.binder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseViewBinder;
import com.d2cmall.buyer.holder.DefaultHolder;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/11/16 14:14
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ScrollEndBinder implements BaseViewBinder {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_over,parent,false);
        return new DefaultHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

    }

    @Override
    public void onBindViewHolderWithOffer(RecyclerView.ViewHolder viewHolder, int position, int offsetTotal) {

    }
}
