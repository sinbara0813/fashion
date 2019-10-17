package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.HomeActivity;
import com.d2cmall.buyer.activity.MyOrderActivity;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.ProductDetailBean;
import com.d2cmall.buyer.holder.PaySuccessTopHolder;
import com.d2cmall.buyer.holder.RecommendProductHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.GuideLayout;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by rookie on 2017/8/29.
 */

public class PaySuccessAdapter extends DelegateAdapter.Adapter<RecommendProductHolder> {

    private Context context;
    private List<ProductDetailBean.DataBean.RecommendProductsBean> list;
    private int itemWidth;

    public PaySuccessAdapter(Context context, List<ProductDetailBean.DataBean.RecommendProductsBean> list){
        this.context=context;
        this.list=list;
        itemWidth = (ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(48)) / 2;
    }

    @Override
    public RecommendProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_recommend_product_item,new LinearLayout(context),false);
        return new RecommendProductHolder(view,itemWidth);
    }

    @Override
    public void onBindViewHolder(RecommendProductHolder recommendProductHolder, final int position) {
        int height = (int) (itemWidth* ((float) 1558 / 1000));
        UniversalImageLoader.displayImage(context, Util.getD2cProductPicUrl(list.get(position).getImg(),itemWidth,height),recommendProductHolder.image, R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
        if (!Util.isEmpty(list.get(position).getBrand())){
            recommendProductHolder.designName.setText(list.get(position).getBrand());
        }
        recommendProductHolder.productName.setText(list.get(position).getName());
        recommendProductHolder.productPrice.setText("¥"+Util.getNumberFormat(list.get(position).getPrice()));
        recommendProductHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id",list.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public LayoutHelper onCreateLayoutHelper() {
        GridLayoutHelper gridLayoutHelper=new GridLayoutHelper(2);
        gridLayoutHelper.setSpanSizeLookup(new GridLayoutHelper.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        gridLayoutHelper.setAutoExpand(false);
        gridLayoutHelper.setPaddingLeft(ScreenUtil.dip2px(16));
        gridLayoutHelper.setPaddingRight(ScreenUtil.dip2px(16));
        gridLayoutHelper.setPaddingBottom(ScreenUtil.dip2px(16));
        gridLayoutHelper.setGap(ScreenUtil.dip2px(16));
        return gridLayoutHelper;
    }

    @Override
    public int getItemViewType(int position) {
        return 2;
    }
}
