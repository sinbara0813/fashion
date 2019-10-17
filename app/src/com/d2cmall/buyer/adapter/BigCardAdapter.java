package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.BrandDetailActivity;
import com.d2cmall.buyer.bean.MainBrandBean;
import com.d2cmall.buyer.util.FullyLinearLayoutManager;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/09/12 20:55
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class BigCardAdapter extends DelegateAdapter.Adapter {
    private int TOP_TYPE = 1;
    private MainBrandBean mainBrandBean;
    public void setMainBrandBean(MainBrandBean mainBrandBean) {
        this.mainBrandBean = mainBrandBean;
    }



    public BigCardAdapter(Context context) {
        mContext = context;
    }

    private Context mContext;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TOP_TYPE) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_main_brand_top, parent, false);
            return new ViewHolderTop(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_main_brand_sub, parent, false);
            return new BigCardViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(position==0) {
            ViewHolderTop viewHolderTop = (ViewHolderTop) holder;
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 5);
            viewHolderTop.mBrandRecycleViewTop.setLayoutManager(gridLayoutManager);
            BigCardTopAdapter bigCardTopAdapter = new BigCardTopAdapter(mContext, mainBrandBean.getData().getRecommendBrandPic());
            viewHolderTop.mBrandRecycleViewTop.setPadding(ScreenUtil.dip2px(16), 0, 0, 0);
            viewHolderTop.mBrandRecycleViewTop.setAdapter(bigCardTopAdapter);
        }else{
            BigCardViewHolder viewHolderSup = (BigCardViewHolder) holder;
            UniversalImageLoader.displayImage(mContext, mainBrandBean.getData().getUpMarketBrands().get(position-1).getBrand().getIntroPic(), viewHolderSup.mFirstImage);
            UniversalImageLoader.displayImage(mContext, mainBrandBean.getData().getUpMarketBrands().get(position-1).getBrand().getHeadPic(), viewHolderSup.mFirstBrandHead);
            MainBrandProductAdapter mainBrandProductAdapter = new MainBrandProductAdapter(mContext, mainBrandBean.getData().getUpMarketBrands().get(position-1).getProducts());
            FullyLinearLayoutManager layoutManager1 = new FullyLinearLayoutManager(mContext);
            layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
            viewHolderSup.mFirstBrandRecycleView.setLayoutManager(layoutManager1);
            viewHolderSup.mFirstBrandRecycleView.setAdapter(mainBrandProductAdapter);
            viewHolderSup.mFirstBrandHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
              int id = mainBrandBean.getData().getUpMarketBrands().get(position-1).getBrand().getId();
                Intent intent = new Intent(mContext, BrandDetailActivity.class);
                intent.putExtra("id", id);
                mContext.startActivity(intent);
                }
            });
            viewHolderSup.mFirstImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = mainBrandBean.getData().getUpMarketBrands().get(position-1).getBrand().getId();
                    Intent intent = new Intent(mContext, BrandDetailActivity.class);
                    intent.putExtra("id", id);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //
        return mainBrandBean==null?0:mainBrandBean.getData().getUpMarketBrands().size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            //顶部折叠图标
            return TOP_TYPE;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return linearLayoutHelper;
    }

    static class BigCardViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.first_image)
        ImageView mFirstImage;
        @Bind(R.id.first_brand_head)
        ImageView mFirstBrandHead;
        @Bind(R.id.first_brand_name)
        TextView mFirstBrandName;
        @Bind(R.id.first_brand_recycle_view)
        RecyclerView mFirstBrandRecycleView;

        BigCardViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderTop extends RecyclerView.ViewHolder{
        @Bind(R.id.brand_recycle_view_top)
        RecyclerView mBrandRecycleViewTop;

        ViewHolderTop(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
