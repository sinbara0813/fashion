package com.d2cmall.buyer.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.listener.OnItemClickListener;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/27 18:52
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class BaseVirtualAdapter<T extends RecyclerView.ViewHolder> extends DelegateAdapter.Adapter{

    private LayoutHelper mLayoutHelper;
    private VirtualLayoutManager.LayoutParams mLayoutParams;
    private BaseViewBinder<T> binder;
    private OnItemClickListener onItemClickListener;
    private int mCount = 0;
    private int type;

    public BaseVirtualAdapter(BaseViewBinder<T> binder,int type){
        this.binder=binder;
        this.mLayoutHelper=new LinearLayoutHelper();
        this.mCount=1;
        this.type=type;
    }

    public BaseVirtualAdapter(BaseViewBinder<T> binder,LayoutHelper layoutHelper,int type){
        this.binder=binder;
        this.mLayoutHelper=layoutHelper;
        this.mCount=1;
        this.type=type;
    }

    public BaseVirtualAdapter(BaseViewBinder<T> binder, LayoutHelper layoutHelper, @NonNull VirtualLayoutManager.LayoutParams layoutParams, int count){
        this.binder=binder;
        this.mLayoutHelper=layoutHelper;
        this.mLayoutParams=layoutParams;
        this.mCount=count;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return binder.onCreateViewHolder(parent,viewType);
    }

    public void setItemClickListerner(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        if (mLayoutParams!=null){
            holder.itemView.setLayoutParams(mLayoutParams);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.itemClick(v,position);
                }
            }
        });
        binder.onBindViewHolder((T)holder,position);
    }

    public void setCount(int count){
        this.mCount=count;
    }

    @Override
    protected void onBindViewHolderWithOffset(RecyclerView.ViewHolder holder, int position, int offsetTotal) {//offsetTotal 表示此布局上面有多少个布局
        binder.onBindViewHolderWithOffer((T)holder,position,offsetTotal);
    }

    @Override
    public int getItemCount() {
        return mCount;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }
}
