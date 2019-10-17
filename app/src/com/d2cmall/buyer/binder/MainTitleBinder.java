package com.d2cmall.buyer.binder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseViewBinder;
import com.d2cmall.buyer.holder.MainItemTitleHolder;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/1 11:03
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainTitleBinder implements BaseViewBinder<MainItemTitleHolder> {

    private Context context;
    private String title;

    public MainTitleBinder(Context context,String title){
        this.context=context;
        this.title=title;
    }

    @Override
    public MainItemTitleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_main_item_title,parent,false);
        return new MainItemTitleHolder(view);
    }

    @Override
    public void onBindViewHolder(MainItemTitleHolder mainItemTitleHolder, int position) {
        mainItemTitleHolder.itemView.setVisibility(View.VISIBLE);
        mainItemTitleHolder.titleTv.setText(title);
    }

    @Override
    public void onBindViewHolderWithOffer(MainItemTitleHolder mainItemTitleHolder, int position, int offsetTotal) {

    }
}
