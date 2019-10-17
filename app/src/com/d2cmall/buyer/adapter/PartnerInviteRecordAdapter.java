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
import com.d2cmall.buyer.bean.PartnerInviteBean;
import com.d2cmall.buyer.bean.PartnerMemberListBean;
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

public class PartnerInviteRecordAdapter extends DelegateAdapter.Adapter {
    private Context mContext;
    private ArrayList<PartnerInviteBean.DataBean.InvitesBean.ListBean> partnerList;
    int itemWidth;


    public PartnerInviteRecordAdapter(Context context, ArrayList<PartnerInviteBean.DataBean.InvitesBean.ListBean> partnerList) {
        mContext = context;
        this.partnerList=partnerList;
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
        PartnerInviteBean.DataBean.InvitesBean.ListBean listBean = partnerList.get(position);
        PartnerMemberHolder partnerMemberHolder = (PartnerMemberHolder) holder;
        if(!Util.isEmpty(listBean.getToNickname())){
            partnerMemberHolder.tvFansNickName.setText(listBean.getToNickname());
        }else{
            partnerMemberHolder.tvFansNickName.setText("匿名_"+listBean.getToMemberId());
        }
        partnerMemberHolder.tvFansShow.setText(mContext.getString(R.string.msg_partner_invite_date, DateUtil.ConverToString(listBean.getCreateDate())));
        partnerMemberHolder.tvPosition.setText(String.valueOf(position+1));
        UniversalImageLoader.displayRoundImage(mContext, listBean.getToHeadPic(), partnerMemberHolder.ivPersonHead, R.mipmap.ic_default_avatar);
    }


    @Override
    public int getItemCount() {
        return partnerList.size();
    }
}
