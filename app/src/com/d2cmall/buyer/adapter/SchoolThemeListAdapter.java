package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.d2cmall.buyer.activity.WebActivity;
import com.d2cmall.buyer.bean.PartnerAccountListBean;
import com.d2cmall.buyer.bean.SaleSchoolListBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.holder.PartnerSaleSchoolHolder;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: d2c
 * Anthor: lwj
 * Date: 2017/7/31 15:52
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class SchoolThemeListAdapter extends DelegateAdapter.Adapter {

    private Context mContext;
    private List<SaleSchoolListBean.DataBean.ThemesBean.ListBean> datas;
    private String title;
    public SchoolThemeListAdapter(Context context, ArrayList<SaleSchoolListBean.DataBean.ThemesBean.ListBean> datas, String title) {
        this.mContext = context;
        this.datas = datas;
        this.title=title;
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
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_sale_school_item, new LinearLayout(mContext), false);
        return new PartnerSaleSchoolHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        PartnerSaleSchoolHolder viewHolder = (PartnerSaleSchoolHolder) holder;
        final SaleSchoolListBean.DataBean.ThemesBean.ListBean listBean = datas.get(position);
        UniversalImageLoader.displayImage(mContext, listBean.getPic(),viewHolder.image);
        viewHolder.tvDate.setText(DateUtil.ConverToYMDString(listBean.getCreateDate()));
        viewHolder.tvTitle.setText(listBean.getTitle());
        viewHolder.tvType.setText(title);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebActivity.class);
                String url = mContext.getString(R.string.msg_partner_theme_url,listBean.getId());
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
