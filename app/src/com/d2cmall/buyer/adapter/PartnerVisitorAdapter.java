package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.PartnerCustomerListBean;
import com.d2cmall.buyer.bean.PartnerVisitorBean;
import com.d2cmall.buyer.holder.PartnerMemberHolder;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;

/**
 * Fixme
 * Author: LWJ
 * desc:   我的收藏Adapter
 * Date: 2017/09/06 19:20
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class PartnerVisitorAdapter extends DelegateAdapter.Adapter {
    private Context mContext;
    private ArrayList<PartnerVisitorBean.ListBean> visitorList;
    int itemWidth;
    private int type;//0是我的访客 1是我的客户

    public PartnerVisitorAdapter(Context context, ArrayList<PartnerVisitorBean.ListBean> visitorList) {
        mContext = context;
        this.visitorList = visitorList;
    }


    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper layoutHelper = new LinearLayoutHelper();
        return layoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_partner_team_item, parent, false);
        return new PartnerMemberHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PartnerVisitorBean.ListBean listBean = visitorList.get(position);
        PartnerMemberHolder partnerMemberHolder = (PartnerMemberHolder) holder;
        if(!Util.isEmpty(listBean.getNickname())){
            partnerMemberHolder.tvFansNickName.setText(listBean.getNickname());
        }else{
            partnerMemberHolder.tvFansNickName.setText("匿名_"+listBean.getNickname());
        }
        partnerMemberHolder.tvFansShow.setText(mContext.getString(R.string.msg_visitor_date,DateUtil.ConverToString(listBean.getVisitDate())));//访问时间
        partnerMemberHolder.tvPosition.setVisibility(View.GONE);//布局复用
        UniversalImageLoader.displayRoundImage(mContext, listBean.getHeadImg(), partnerMemberHolder.ivPersonHead, R.mipmap.ic_default_avatar);
    }


    @Override
    public int getItemCount() {
        return visitorList ==null?0: visitorList.size();
    }
}
