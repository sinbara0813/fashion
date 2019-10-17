package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.CollectActivity;
import com.d2cmall.buyer.activity.WebActivity;
import com.d2cmall.buyer.bean.PartnerMemberBean;
import com.d2cmall.buyer.bean.SaleSchoolListBean;
import com.d2cmall.buyer.bean.SaleSchoolTagsBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.holder.PartnerSaleSchoolHolder;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;

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
public class SchoolThemeAdapter extends DelegateAdapter.Adapter {

    private Context mContext;
    private List<SaleSchoolListBean.DataBean.ThemesBean.ListBean> datas;
    private VirtualLayoutManager mLayoutManager;
    private DelegateAdapter delegateAdapter;

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
    private PartnerMemberBean.DataBean.PartnerBean partnerBean;
    private List<SaleSchoolTagsBean.DataBean.ThemeTagsBean> partnerTags;
    private List<SaleSchoolTagsBean.DataBean.ThemeTagsBean> buyerTags;
    private List<SaleSchoolTagsBean.DataBean.ThemeTagsBean> themeTags;
    private int count=0;

    public SchoolThemeAdapter(Context context, ArrayList<SaleSchoolListBean.DataBean.ThemesBean.ListBean> datas) {
        this.mContext = context;
        this.datas = datas;
        partnerTags=new ArrayList<>();
        buyerTags=new ArrayList<>();
        partnerBean = Session.getInstance().getPartnerFromFile(mContext);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setPaddingTop(ScreenUtil.dip2px(6));
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if(viewType==11){
//            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_main_brand_top, new LinearLayout(mContext), false);
//            return new ViewHolderTop(view);
//        }else if(viewType==12){
//            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_main_brand_top, new LinearLayout(mContext), false);
//            return new ViewHolderTop(view);
//        }else{
//            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_sale_school_item, new LinearLayout(mContext), false);
//            return new PartnerSaleSchoolHolder(view);
//        }
        if(viewType==11){
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_sale_school_theme_top, new LinearLayout(mContext), false);
            return new ViewHolderTop(view);
        }else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_sale_school_item, new LinearLayout(mContext), false);
            return new PartnerSaleSchoolHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)){
            case 11:
                ViewHolderTop viewHolderTop = (ViewHolderTop) holder;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
                viewHolderTop.mBrandRecycleViewTop.setLayoutManager(gridLayoutManager);
                SaleSchoolTopAdapter saleSchoolTopAdapter = new SaleSchoolTopAdapter(mContext,themeTags);
                viewHolderTop.mBrandRecycleViewTop.setAdapter(saleSchoolTopAdapter);
                break;
//            case 12:
//                ViewHolderTop viewHolderTop1 = (ViewHolderTop) holder;
//                LinearLayoutManager linearManager1 = new LinearLayoutManager(mContext);
//                linearManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
//                viewHolderTop1.mBrandRecycleViewTop.setLayoutManager(linearManager1);
//                if(user!=null){
//                    if(user.getPartnerLevel()==1){
//                        viewHolderTop1.mBrandRecycleViewTop.setPadding(ScreenUtil.dip2px(16),ScreenUtil.dip2px(16),ScreenUtil.dip2px(16),0);
//                    }else {
//                        viewHolderTop1.mBrandRecycleViewTop.setPadding(ScreenUtil.dip2px(16),0,ScreenUtil.dip2px(16),0);
//                    }
//                }
//
//                SaleSchoolTopAdapter bigCardTopAdapter1 = new SaleSchoolTopAdapter(mContext,buyerTags);
//                viewHolderTop1.mBrandRecycleViewTop.setAdapter(bigCardTopAdapter1);
//                break;
            default:
                PartnerSaleSchoolHolder viewHolder = (PartnerSaleSchoolHolder) holder;
                final SaleSchoolListBean.DataBean.ThemesBean.ListBean listBean = datas.get(position-count);
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
                break;
        }
    }


    @Override
    public int getItemCount() {
        count = 0;
        if(partnerBean!=null){
            if(partnerBean.getLevel()==1 || partnerBean.getLevel()==0){
//                if(buyerTags!=null && buyerTags.size() >0){
//                    count++;
//                }
//                if(partnerTags!=null && partnerTags.size() >0){
//                    count++;
//                }
                if((buyerTags!=null && buyerTags.size() >0)||(partnerTags!=null && partnerTags.size() >0)){
                    count++;
                }
            } else if(partnerBean.getLevel()==2){
                if(buyerTags!=null && buyerTags.size() >0){
                    count++;
                }
            }
        }
        return datas == null ? count : datas.size()+ count;
    }

    @Override
    public int getItemViewType(int position) {
        if(partnerBean!=null){
          if(partnerBean.getLevel()==1 || partnerBean.getLevel()==0){
              if(position==0 && ((partnerTags.size()>0)||(buyerTags.size()>0))){
                  return 11;
              }
          } else if(partnerBean.getLevel()==2){
              if(position==0 && buyerTags.size()>0){
                  return 11;
              }
          }
        }
        return super.getItemViewType(position);
    }

    public void setTags(List<SaleSchoolTagsBean.DataBean.ThemeTagsBean> themeTags) {
        this.themeTags=themeTags;
        filterList(themeTags);
    }

    static class ViewHolderTop extends RecyclerView.ViewHolder{
        @Bind(R.id.brand_recycle_view_top)
        RecyclerView mBrandRecycleViewTop;

        ViewHolderTop(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    private void filterList(List<SaleSchoolTagsBean.DataBean.ThemeTagsBean> themeTags) {
            for (int i = 0; i < themeTags.size(); i++) {
                if (themeTags.get(i).getFix() == 0) {
                    partnerTags.add(themeTags.get(i));
                }else{
                    buyerTags.add(themeTags.get(i));
                }
            }
            notifyDataSetChanged();
    }
}
