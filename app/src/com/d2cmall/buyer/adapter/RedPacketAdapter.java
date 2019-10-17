package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.RedPacketBean;
import com.d2cmall.buyer.holder.RedPacketItemHolder;

import java.util.List;

/**
 * Created by rookie on 2017/11/1.
 */

public class RedPacketAdapter extends DelegateAdapter.Adapter<RedPacketItemHolder> {

    private Context context;
    private List<RedPacketBean.DataBean.RedPacketsItemsBean.ListBean> list;

    public RedPacketAdapter(Context context, List<RedPacketBean.DataBean.RedPacketsItemsBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @Override
    public RedPacketItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_income_detail, parent, false);
        return new RedPacketItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RedPacketItemHolder holder, int position) {
        RedPacketBean.DataBean.RedPacketsItemsBean.ListBean listBean = list.get(position);
        if (listBean.getAmount() > 0) {
            holder.tvMoney.setText("+" + String.valueOf(listBean.getAmount()));
        } else {
            holder.tvMoney.setText(String.valueOf(listBean.getAmount()));
        }
        holder.tvNameDetail.setText(listBean.getBusinessName());
        holder.tvTime.setText(listBean.getCreateDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
