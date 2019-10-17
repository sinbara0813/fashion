package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.GoodsBean;
import com.d2cmall.buyer.holder.ReopenStoreHolder;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;

/**
 * Fixme
 * Author: LWJ
 * desc:   重新开店
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class ReopenStoreAdapter extends DelegateAdapter.Adapter {
    private ArrayList<GoodsBean.DataBean.ProductsBean.ListBean> goodList;
    private Context mContext;
    private GoodsBean.DataBean.ProductsBean.ListBean listBean;


    public ReopenStoreAdapter(Context context, ArrayList<GoodsBean.DataBean.ProductsBean.ListBean> productList) {
        this.mContext = context;
        goodList = productList;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_reopen_store_item, parent, false);
        ReopenStoreHolder holder = new ReopenStoreHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ReopenStoreHolder reopenStoreHolder = (ReopenStoreHolder) holder;
        if (position == 0) {
            reopenStoreHolder.ivHead.setVisibility(View.VISIBLE);
        } else {
            reopenStoreHolder.ivHead.setVisibility(View.GONE);
        }
        listBean = goodList.get(position);
        UniversalImageLoader.displayImage(mContext, listBean.getImg(), reopenStoreHolder.ivProduct, R.mipmap.ic_logo_empty5);
        reopenStoreHolder.tvProductName.setText(listBean.getName());
        reopenStoreHolder.tvProductPrice.setText("¥" + Util.getNumberFormat(listBean.getPrice()));
        reopenStoreHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ProductDetailActivity.class).putExtra("id", goodList.get(position).getId()));
            }
        });
        reopenStoreHolder.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ProductDetailActivity.class).putExtra("id", goodList.get(position).getId()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return goodList == null ? 0 : goodList.size();
    }

}
