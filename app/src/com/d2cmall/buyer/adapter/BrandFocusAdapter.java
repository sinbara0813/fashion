package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.BrandDetailActivity;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.BrandFollowApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.BrandUpdateListBean;
import com.d2cmall.buyer.bean.ClickFollowBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.holder.BrandUpdateHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.OpenMsgPushPop;
import com.d2cmall.buyer.widget.ShowPopImageView;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2018/1/3 15:29
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class BrandFocusAdapter extends DelegateAdapter.Adapter {

    private Context mContext;
    private List<BrandUpdateListBean.DataBean.BrandsBean.ListBean> list;
    private UserBean.DataEntity.MemberEntity user;

    public BrandFocusAdapter(Context context,List<BrandUpdateListBean.DataBean.BrandsBean.ListBean> list){
        this.mContext=context;
        this.list=list;
        user = Session.getInstance().getUserFromFile(context);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.layout_brand_update_item,parent,false);
        return new BrandUpdateHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final BrandUpdateHolder updateHolder= (BrandUpdateHolder) holder;
        UniversalImageLoader.displayImage(mContext,list.get(position).getHeadPic(),updateHolder.imgAvatar);
        updateHolder.nameTv.setText(list.get(position).getName());
        updateHolder.imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toBrandDetailActivity(list.get(position).getId());
            }
        });
        updateHolder.nameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toBrandDetailActivity(list.get(position).getId());
            }
        });
        updateHolder.num.setText(list.get(position).getCount()+"款上新");
        if (user != null) {
            if (list.get(position).getId() == user.getMemberId()) {
                updateHolder.focusIv.setVisibility(View.GONE);
            }
        }
        switch (list.get(position).getAttentioned()) {
            case 0:
                updateHolder.focusIv.setImageResource(R.mipmap.button_fashion_care);
                break;
            case 1:
                updateHolder.focusIv.setImageResource(R.mipmap.button_fashion_cared);
                break;
            case 2:
                updateHolder.focusIv.setImageResource(R.mipmap.button_fashion_mutualcare);
                break;
        }
        updateHolder.focusIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) mContext, 999)) {
                    focusComplete(list.get(position), updateHolder.focusIv);
                }
            }
        });
        int size=list.get(position).getProducts().size();
        updateHolder.image1.setVisibility(View.GONE);
        updateHolder.image2.setVisibility(View.GONE);
        updateHolder.image3.setVisibility(View.GONE);
        if (size>0){
            updateHolder.image1.setVisibility(View.VISIBLE);
            UniversalImageLoader.displayImage(mContext,list.get(position).getProducts().get(0).getImg(),updateHolder.image1);
            updateHolder.image1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toDetailActivity(list.get(position).getProducts().get(0).getId());
                }
            });
            checkPromotion(list.get(position).getProducts().get(0),updateHolder.text1);
            if (size>1){
                updateHolder.image2.setVisibility(View.VISIBLE);
                UniversalImageLoader.displayImage(mContext,list.get(position).getProducts().get(1).getImg(),updateHolder.image2);
                updateHolder.image2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toDetailActivity(list.get(position).getProducts().get(1).getId());
                    }
                });
                checkPromotion(list.get(position).getProducts().get(1),updateHolder.text2);
                if (size>2){
                    updateHolder.image3.setVisibility(View.VISIBLE);
                    UniversalImageLoader.displayImage(mContext,list.get(position).getProducts().get(2).getImg(),updateHolder.image3);
                    updateHolder.image3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            toDetailActivity(list.get(position).getProducts().get(2).getId());
                        }
                    });
                    checkPromotion(list.get(position).getProducts().get(2),updateHolder.text3);
                }
            }
        }
    }

    private void checkPromotion(BrandUpdateListBean.DataBean.BrandsBean.ListBean.ProductsBean bean,TextView textView){
        if (bean.getPromotionId()>0){
            if (bean.getMinPrice() < bean.getSalePrice()){
                setPrice(bean.getMinPrice(),bean.getSalePrice(),textView);
            }else {
                setPrice(bean.getMinPrice(),textView);
            }
        }else {
            if (bean.getMinPrice() < bean.getOriginalPrice()){
                setPrice(bean.getMinPrice(),bean.getOriginalPrice(),textView);
            }else {
                setPrice(bean.getMinPrice(),textView);
            }
        }
    }

    private void setPrice(double firstPrice, double secondPrice, TextView tv){
        StringBuilder builder=new StringBuilder();
        builder.append("¥").append(Util.getNumberFormat(firstPrice))
                .append("  ¥").append(Util.getNumberFormat(secondPrice));
        int third=builder.toString().lastIndexOf("¥");
        SpannableString spannableString = new SpannableString(builder.toString());

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#61000000"));
        spannableString.setSpan(colorSpan, third, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spannableString.setSpan(strikethroughSpan, third, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv.setText(spannableString);
    }

    private void setPrice(double price,TextView tv){
        StringBuilder builder=new StringBuilder();
        builder.append("¥").append(Util.getNumberFormat(price));
        tv.setText(builder.toString());
    }

    private void toBrandDetailActivity(int id){
        Intent intent=new Intent(mContext, BrandDetailActivity.class);
        intent.putExtra("id",id);
        mContext.startActivity(intent);
    }

    private void focusComplete(final BrandUpdateListBean.DataBean.BrandsBean.ListBean listBean, final ShowPopImageView imageView) {
        BrandFollowApi api = new BrandFollowApi();
        api.setBrandId(listBean.getId());
        if (listBean.getAttentioned() > 0) {
            api.setInterPath(Constants.FOLLOW_BRAND_DELETE);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                @Override
                public void onResponse(BaseBean response) {
                    listBean.setAttentioned(0);
                    Util.showToast(mContext, "取消关注成功");
                    imageView.setImageResource(R.mipmap.button_fashion_care);
                }
            });
        } else {
            SimpleApi api1 = new SimpleApi();
            api1.setMethod(BaseApi.Method.POST);
            api1.setInterPath(String.format(Constants.FOLLOW_BRAND_URL, listBean.getId()));
            D2CApplication.httpClient.loadingRequest(api1, new BeanRequest.SuccessListener<ClickFollowBean>() {
                @Override
                public void onResponse(ClickFollowBean clickFollowBean) {
                    listBean.setAttentioned(1);
                    imageView.setImageResource(R.mipmap.button_fashion_cared);
                    Util.showToast(mContext, "关注成功");
                    imageView.showMsgPop((Activity) mContext,mContext.getString(R.string.label_pop_focus_brand));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Util.showToast(mContext, Util.checkErrorType(error));
                }
            });
        }

    }

    private void toDetailActivity(long id){
        Intent intent=new Intent(mContext, ProductDetailActivity.class);
        intent.putExtra("id",id);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
