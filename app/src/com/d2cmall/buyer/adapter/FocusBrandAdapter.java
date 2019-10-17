package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import com.d2cmall.buyer.bean.FocusBrandBean1;
import com.d2cmall.buyer.holder.FocusBrandHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.OpenMsgPushPop;
import com.d2cmall.buyer.widget.ShowPopImageView;

import java.util.List;

/**
 * Fixme
 * Author: LWJ
 * desc:关注品牌的适配器
 * Date: 2017/09/06 11:05
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class FocusBrandAdapter extends DelegateAdapter.Adapter {

    private Context mContext;
    private List<FocusBrandBean1.DataBean.MyAttentionsBean.ListBean>  focusBranList;

    public FocusBrandAdapter(Context context, List<FocusBrandBean1.DataBean.MyAttentionsBean.ListBean>  focusBranList) {
        mContext = context;
        this.focusBranList=focusBranList;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_focus_brand_item, parent, false);
        return new FocusBrandHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final FocusBrandHolder focusBrandHolder = (FocusBrandHolder) holder;
        final FocusBrandBean1.DataBean.MyAttentionsBean.ListBean listBean = focusBranList.get(position);
        focusBrandHolder.mFocusIv.setVisibility(View.VISIBLE);
        focusBrandHolder.tvUse.setVisibility(View.GONE);
        List<FocusBrandBean1.DataBean.MyAttentionsBean.ListBean.ProductsBean> products = listBean.getProducts();
        UniversalImageLoader.displayImage(mContext,listBean.getDesignerPic(),focusBrandHolder.mImgAvatar,R.mipmap.ic_default_avatar);
        focusBrandHolder.mNameTv.setText(listBean.getDesignerName());
        UniversalImageLoader.displayImage(mContext,focusBrandHolder.mFocusIv,R.mipmap.button_fashion_cared);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        FocusBrandItemAdapter focusBrandItemAdapter = new FocusBrandItemAdapter(mContext,products);
        focusBrandHolder.mItemRecyclerview.setLayoutManager(layoutManager);
        focusBrandHolder.mItemRecyclerview.setAdapter(focusBrandItemAdapter);
        //开启消息推送行为节点
        focusBrandHolder.mFocusIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFocus(listBean,focusBrandHolder.mFocusIv);
            }
        });
        focusBrandHolder.mImgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToBrandDetial(listBean);
            }
        });
        focusBrandHolder.mNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToBrandDetial(listBean);
            }
        });
    }

    private void jumpToBrandDetial(FocusBrandBean1.DataBean.MyAttentionsBean.ListBean listBean) {
        //跳转到品牌详情
        int id = listBean.getDesignerId();
        Intent intent=new Intent(mContext, BrandDetailActivity.class);
        intent.putExtra("id",id);
        mContext.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return focusBranList.size();
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setPaddingTop(32);
        return linearLayoutHelper;
    }
    private void changeFocus(final FocusBrandBean1.DataBean.MyAttentionsBean.ListBean listBean, final ShowPopImageView focusIv){
        BrandFollowApi api = new BrandFollowApi();
        if (listBean.isAttentioned() ==true) {//关注了
            api.setInterPath(Constants.FOLLOW_BRAND_DELETE);
            api.setBrandId(listBean.getDesignerId());
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                @Override
                public void onResponse(BaseBean response) {
                    focusIv.setImageResource(R.mipmap.button_fashion_care);
                    listBean.setAttentioned(false);
                    Util.showToast(mContext, "取消关注成功");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Util.showToast(mContext, Util.checkErrorType(error));
                }
            });
        } else {
            SimpleApi api1=new SimpleApi();
            api1.setMethod(BaseApi.Method.POST);
            api1.setInterPath(String.format(Constants.FOLLOW_BRAND_URL, listBean.getDesignerId()));
            D2CApplication.httpClient.loadingRequest(api1, new BeanRequest.SuccessListener<BaseBean>() {
                @Override
                public void onResponse(BaseBean response) {
                    focusIv.setImageResource(R.mipmap.button_fashion_cared);
                    listBean.setAttentioned(true);
                    Util.showToast(mContext, "关注成功");
                    focusIv.showMsgPop((Activity) mContext,mContext.getString(R.string.label_pop_focus_brand));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Util.showToast(mContext, Util.checkErrorType(error));
                }
            });

        }
    }

}
