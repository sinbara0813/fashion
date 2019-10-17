package com.d2cmall.buyer.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.PartnerGiftsBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.holder.PartnerBillHolder;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/31 15:52
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class PartnerGiftsAdapter extends DelegateAdapter.Adapter {

    private Context mContext;
    public boolean hasPadding;
    public int padTop;
    private List<PartnerGiftsBean.DataBean.GiftsBean.ListBean>  datas;
    private UserBean.DataEntity.MemberEntity user;


    public PartnerGiftsAdapter(Context context, List<PartnerGiftsBean.DataBean.GiftsBean.ListBean>  datas) {
        this.mContext = context;
        this.datas = datas;
        user = Session.getInstance().getUserFromFile(context);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setPaddingTop(ScreenUtil.dip2px(6));
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_buyer_order_item, new LinearLayout(mContext), false);
        return new PartnerBillHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final PartnerBillHolder billHolder = (PartnerBillHolder) holder;
        billHolder.tvLookBuyer.setVisibility(View.GONE);
        billHolder.ivLookBuyer.setVisibility(View.GONE);
        PartnerGiftsBean.DataBean.GiftsBean.ListBean listBean = datas.get(position);
        UniversalImageLoader.displayImage(mContext, Util.getD2cProductPicUrl(listBean.getProductPic(),ScreenUtil.dip2px(70),ScreenUtil.dip2px(105)), billHolder.ivProduct,R.mipmap.ic_logo_empty5);
        billHolder.tvProductName.setText(listBean.getProductName());
        String str = mContext.getString(R.string.label_order_rebeat, Util.getNumberFormat(listBean.getInviteRebate()));
        int length = str.length();
        SpannableString textSpan = new SpannableString(str);
        textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(14)),0,6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(12)),6,7, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(18)),7,length,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        billHolder.tvRebeatDesc.setText(textSpan);
        billHolder.tvRebeatDesc.getPaint().setFakeBoldText(true);
        billHolder.llExpectTip.setVisibility(View.GONE);
        if (listBean.getCreateDate() != null) {
            billHolder.tvDate.setText(DateUtil.ConverToString(listBean.getCreateDate()));
        } else {
            billHolder.tvDate.setText("");
        }
        billHolder.tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isEmpty(listBean.getOrderSn())) {
                    ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(ClipData.newPlainText("orderId", listBean.getOrderSn()));
                    Util.showToast(mContext, R.string.label_order_id_copy);
                }
            }
        });
        billHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext,ProductDetailActivity.class).putExtra("id",listBean.getProductId()));
            }
        });
        if(user!=null && listBean.getInviteId()==user.getPartnerId()){
            billHolder.tvSelfFlag.setVisibility(View.VISIBLE);
            billHolder.tvSelfFlag.setBackgroundColor(Color.BLACK);
        }else{
            billHolder.tvSelfFlag.setVisibility(View.GONE);
        }
        billHolder.tvOrderCode.setText(mContext.getString(R.string.label_order_id_simple, listBean.getOrderSn()));
        billHolder.tvPhone.setText(mContext.getString(R.string.label_order_buyer_code, listBean.getLoginCode()));

    }




    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }




}
