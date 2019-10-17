package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.PartnerAccountListBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.holder.PartnerBillHolder;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/31 15:52
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class PartnerAccountLogAdapter extends DelegateAdapter.Adapter {

    private Context mContext;
    private List<PartnerAccountListBean.DataBean.PartnerLogBean.ListBean> datas;
    private UserBean.DataEntity.MemberEntity user;
    private long status;

    public PartnerAccountLogAdapter(Context context, List<PartnerAccountListBean.DataBean.PartnerLogBean.ListBean> datas) {
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
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_buyer_account_item, new LinearLayout(mContext), false);
        return new PartnerAccountHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        PartnerAccountHolder partnerAccountHolder = (PartnerAccountHolder) holder;
        PartnerAccountListBean.DataBean.PartnerLogBean.ListBean listBean = datas.get(position);
        if("CASH".equals(listBean.getSourceType())){
            partnerAccountHolder.ivAccountType.setImageResource(R.mipmap.icon_mine_cash);
        }else if("INVITE".equals(listBean.getSourceType())){
            partnerAccountHolder.ivAccountType.setImageResource(R.mipmap.icon_mine_reward);
        }else{
            partnerAccountHolder.ivAccountType.setImageResource(R.mipmap.icon_mine_back);
        }
        partnerAccountHolder.tvAccountType.setText(listBean.getTypeName());
        if(!Util.isEmpty(listBean.getSourceSn())){
            partnerAccountHolder.tvAccountCode.setVisibility(View.VISIBLE);
            partnerAccountHolder.tvAccountCode.setText("编号"+listBean.getSourceSn());
        }else{
            partnerAccountHolder.tvAccountCode.setVisibility(View.GONE);
        }

        partnerAccountHolder.tvAccountDate.setText(DateUtil.ConverToString(listBean.getCreateDate()));
        if(listBean.getDirection()==-1 ){
            partnerAccountHolder.tvAccountMoney.setText("-"+String.valueOf(listBean.getAmount()));
        }else{
            partnerAccountHolder.tvAccountMoney.setText("+"+String.valueOf(listBean.getAmount()));
        }
    }


    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    static class PartnerAccountHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.iv_account_type)
        ImageView ivAccountType;
        @Bind(R.id.tv_account_type)
        TextView tvAccountType;
        @Bind(R.id.tv_account_code)
        TextView tvAccountCode;
        @Bind(R.id.tv_account_date)
        TextView tvAccountDate;
        @Bind(R.id.tv_account_money)
        TextView tvAccountMoney;

        PartnerAccountHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
