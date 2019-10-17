package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.BrandDetailActivity;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.BrandFollowApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.CouponRangeBean;
import com.d2cmall.buyer.bean.FocusBrandBean1;
import com.d2cmall.buyer.holder.FocusBrandHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ShowPopImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Fixme
 * Author: LWJ
 * desc:关注品牌的适配器
 * Date: 2017/09/06 11:05
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class CouponRelationBrandsAdapter extends DelegateAdapter.Adapter {

    private Context mContext;
    private List<CouponRangeBean.DataBean.BrandsBean.ListBean>  brandsBeans;

    public CouponRelationBrandsAdapter(Context context, ArrayList<CouponRangeBean.DataBean.BrandsBean.ListBean> brands) {
        mContext = context;
        this.brandsBeans=brands;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_coupon_relation_brand_item, parent, false);
        return new FocusBrandHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final FocusBrandHolder focusBrandHolder = (FocusBrandHolder) holder;
        final CouponRangeBean.DataBean.BrandsBean.ListBean listBean = brandsBeans.get(position);
        UniversalImageLoader.displayImage(mContext,listBean.getHeadPic(),focusBrandHolder.mImgAvatar,R.mipmap.ic_default_avatar);
        focusBrandHolder.mNameTv.setText(listBean.getName());
        UniversalImageLoader.displayImage(mContext,focusBrandHolder.mFocusIv,R.mipmap.button_fashion_cared);
        focusBrandHolder.mFocusIv.setVisibility(View.GONE);
        focusBrandHolder.tvUse.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        CouponRelationBrandsProductAdapter focusBrandItemAdapter = new CouponRelationBrandsProductAdapter(mContext,listBean.getProducts());
        focusBrandHolder.mItemRecyclerview.setLayoutManager(layoutManager);
        focusBrandHolder.mItemRecyclerview.setAdapter(focusBrandItemAdapter);
        focusBrandHolder.mImgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToBrandDetial(listBean);
            }
        });
        focusBrandHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToBrandDetial(listBean);
            }
        });
    }

    private void jumpToBrandDetial(CouponRangeBean.DataBean.BrandsBean.ListBean listBean) {
        //跳转到品牌详情
        int id = listBean.getId();
        Intent intent=new Intent(mContext, BrandDetailActivity.class);
        intent.putExtra("id",id);
        mContext.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return brandsBeans==null?0:brandsBeans.size();
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setPaddingLeft(ScreenUtil.dip2px(16));
        linearLayoutHelper.setPaddingRight(ScreenUtil.dip2px(16));
        linearLayoutHelper.setPaddingTop(32);
        return linearLayoutHelper;
    }

}
