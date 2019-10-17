package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.activity.WebActivity;
import com.d2cmall.buyer.bean.OpenOrderListBean;
import com.d2cmall.buyer.bean.PartnerNoticeBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.holder.HighIncomeHolder;
import com.d2cmall.buyer.holder.PartnerNoticeHolder;
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
public class PartnerNoticeAdapter extends DelegateAdapter.Adapter {

    private Context mContext;
    private List<PartnerNoticeBean.DataBean.ArticleListBean.ListBean> datas;
    public PartnerNoticeAdapter(Context context, List<PartnerNoticeBean.DataBean.ArticleListBean.ListBean> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper=new LinearLayoutHelper();
                linearLayoutHelper.setPaddingTop(ScreenUtil.dip2px(16));
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_partner_notice_item, new LinearLayout(mContext), false);
                return new PartnerNoticeHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        PartnerNoticeHolder partnerNoticeHoler = (PartnerNoticeHolder) holder;
        final PartnerNoticeBean.DataBean.ArticleListBean.ListBean listBean = datas.get(position);
        UniversalImageLoader.displayImage(mContext, listBean.getPicture(), partnerNoticeHoler.ivNotice);
        partnerNoticeHoler.tvDate.setText(DateUtil.ConverToString(listBean.getPublishDate()));
        partnerNoticeHoler.tvTitle.setText(listBean.getTitle());
        partnerNoticeHoler.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebActivity.class);
                String url = mContext.getString(R.string.msg_partner_notice_url,listBean.getId());
                intent.putExtra("type", 0);
                intent.putExtra("url", url);
                mContext.startActivity(intent);
            }
        });

}





    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


}
