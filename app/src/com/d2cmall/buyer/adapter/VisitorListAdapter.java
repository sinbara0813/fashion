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
import com.d2cmall.buyer.activity.NormalCustomerCenterActivity;
import com.d2cmall.buyer.bean.BuyerVisitorBean;
import com.d2cmall.buyer.bean.PartnerVisitorBean;
import com.d2cmall.buyer.holder.VisitorItemHolder;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Dec: D2CNEW
 * Author: hrb
 * Date: 2018/4/18 14:13
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class VisitorListAdapter extends DelegateAdapter.Adapter {

    private Context mContext;
    private ArrayList<PartnerVisitorBean.ListBean> visitorList;
    private List<BuyerVisitorBean.DataBean.CustomersBean.ListBean> list;
    private int total;
    private int type;

    public VisitorListAdapter(Context context) {
        mContext = context;
    }

    public void setData(ArrayList list, int type) {
        this.type = type;
        if (type == 1) {
            this.visitorList = list;
        } else {
            this.list = list;
        }
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper layoutHelper = new LinearLayoutHelper();
        return layoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_visitor_item_view, parent, false);
        return new VisitorItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        VisitorItemHolder itemHolder = (VisitorItemHolder) holder;
        if (position == 0) {
            itemHolder.tag.setVisibility(View.VISIBLE);
            if (type == 1) {
                itemHolder.tag.setText("最近30天的访客");
            } else {
                itemHolder.tag.setText("共计" + total + "名零售客户");
            }
            itemHolder.contentRl.setPadding(ScreenUtil.dip2px(16), 0, ScreenUtil.dip2px(16), ScreenUtil.dip2px(16));
        } else {
            itemHolder.tag.setVisibility(View.GONE);
            itemHolder.contentRl.setPadding(ScreenUtil.dip2px(16), ScreenUtil.dip2px(15), ScreenUtil.dip2px(16), ScreenUtil.dip2px(16));
        }
        if (type==1){
            UniversalImageLoader.displayRoundImage(mContext, visitorList.get(position).getHeadImg(), itemHolder.headIv, R.mipmap.ic_default_avatar);
            itemHolder.name.setText(Util.isEmpty(visitorList.get(position).getNickname())  ? "匿名_"+ visitorList.get(position).getMemberId(): visitorList.get(position).getNickname());
            itemHolder.time.setText(DateUtil.toString(visitorList.get(position).getVisitDate()));
            itemHolder.target.setText("访问页面:" + visitorList.get(position).getData().getTargetName());
        }else {
            UniversalImageLoader.displayRoundImage(mContext, list.get(position).getHead(), itemHolder.headIv, R.mipmap.ic_default_avatar);
            itemHolder.name.setText(Util.isEmpty(list.get(position).getNickname())  ? "匿名_"+ list.get(position).getMemberId(): list.get(position).getNickname());
            itemHolder.target.setText(list.get(position).getMobile());
        }
        if(type==2){
            itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, NormalCustomerCenterActivity.class).putExtra("memberId",list.get(position).getMemberId()));
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        if (type==1){
            return visitorList == null ? 0 : visitorList.size();
        }else {
            return list==null?0:list.size();
        }
    }
}
