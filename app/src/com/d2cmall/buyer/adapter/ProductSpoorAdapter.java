package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.FixLayoutHelper;
import com.alibaba.android.vlayout.layout.FloatLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.FootMarkActivity;
import com.d2cmall.buyer.holder.ProductSpoorHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2018/3/6 10:36
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductSpoorAdapter extends DelegateAdapter.Adapter {

    private Context context;
    private View.OnClickListener onClickListener;

    public ProductSpoorAdapter(Context context, View.OnClickListener listener){
        this.context=context;
        this.onClickListener=listener;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        FloatLayoutHelper layoutHelper = new FloatLayoutHelper();
        layoutHelper.setAlignType(FixLayoutHelper.BOTTOM_RIGHT);
        layoutHelper.setDefaultLocation(ScreenUtil.dip2px(16), ScreenUtil.dip2px(16));
        return layoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.float_layout,parent,false);
        return new ProductSpoorHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProductSpoorHolder productSpoorHolder= (ProductSpoorHolder) holder;
        productSpoorHolder.zuIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) context,0)){
                    Intent intent=new Intent(context, FootMarkActivity.class);
                    context.startActivity(intent);
                }
            }
        });
        if (onClickListener!=null){
            productSpoorHolder.dingIv.setOnClickListener(onClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
