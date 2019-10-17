package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.MainSpecailBean2;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.tendcloud.tenddata.TCAgent;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/11 16:52
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainInterestAdapter extends RecyclerView.Adapter<MainInterestAdapter.ComdityRecommendViewHolder> {

    private List<MainSpecailBean2.DataBean.RecentlySalesProductBean> recommends;
    private Context context;

    public MainInterestAdapter(Context context, List<MainSpecailBean2.DataBean.RecentlySalesProductBean> data) {
        this.context = context;
        recommends = data;
    }

    @Override
    public MainInterestAdapter.ComdityRecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_recommend_product_item, parent, false);
        return new MainInterestAdapter.ComdityRecommendViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MainInterestAdapter.ComdityRecommendViewHolder holder, final int position) {
        UniversalImageLoader.displayImage(context, Util.getD2cPicUrl(recommends.get(position).getImg()), holder.image, R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
        holder.productName.setText(recommends.get(position).getName());
        holder.designName.setText(recommends.get(position).getBrand());
        MainSpecailBean2.DataBean.RecentlySalesProductBean recentlySalesProductBean = recommends.get(position);
        long promotionId = recentlySalesProductBean.getPromotionId();
        //显示价格()
        if (promotionId > 0) {
            holder.productPrice.setText("¥" + Util.getNumberFormat(recentlySalesProductBean.getPrice()));
            if (recentlySalesProductBean.getSalePrice() > recentlySalesProductBean.getPrice()) {
                holder.productDropPrice.setVisibility(View.VISIBLE);
                holder.productDropPrice.setText("¥" + Util.getNumberFormat(recentlySalesProductBean.getSalePrice()));
                holder.productDropPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
        } else {
            if (recentlySalesProductBean.getPrice() >= recentlySalesProductBean.getOriginalPrice()) {
                holder.productDropPrice.setVisibility(View.GONE);
            } else {
                holder.productDropPrice.setVisibility(View.VISIBLE);
                holder.productDropPrice.setText("¥" + Util.getNumberFormat(recentlySalesProductBean.getOriginalPrice()));
                holder.productDropPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
            holder.productPrice.setText("¥" + Util.getNumberFormat(recentlySalesProductBean.getPrice()));
        }
        holder.llPromotionType.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id", recommends.get(position).getId());
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recommends == null ? 0 : recommends.size();
    }

    class ComdityRecommendViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.image)
        ImageView image;
        @Bind(R.id.view_no_store)
        View viewNoStore;
        @Bind(R.id.tv_no_store)
        TextView tvNoStore;
        @Bind(R.id.design_name)
        TextView designName;
        @Bind(R.id.product_name)
        TextView productName;
        @Bind(R.id.product_price)
        TextView productPrice;
        @Bind(R.id.product_drop_price)
        TextView productDropPrice;
        @Bind(R.id.ll_promotion_type)
        LinearLayout llPromotionType;

        public ComdityRecommendViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            RelativeLayout.LayoutParams rl=new RelativeLayout.LayoutParams(-2,-2);
            rl.setMargins(0,0,ScreenUtil.dip2px(16),0);
            itemView.setLayoutParams(rl);
            int width = (ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(48)) / 3;
            int height = width * 1558 / 1000;
            RelativeLayout.LayoutParams ll = (RelativeLayout.LayoutParams) image.getLayoutParams();
            ll.width = width;
            ll.height = height;
        }
    }
}
