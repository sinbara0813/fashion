package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.GroupListBean;
import com.d2cmall.buyer.bean.ProductDetailBean;
import com.d2cmall.buyer.holder.ProductHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/12 11:04
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductRecommend1Adapter extends DelegateAdapter.Adapter<ProductHolder> {

    private Context context;
    private List<ProductDetailBean.DataBean.RecommendProductsBean> datas;
    private int itemWidth;
    private int count;
    private long id;
    private String name;
    private boolean isCollage;

    public ProductRecommend1Adapter(Context context, List<ProductDetailBean.DataBean.RecommendProductsBean> datas, int itemWidth, int count, long id, String name){
        this.context=context;
        this.datas =datas;
        this.itemWidth=itemWidth;
        this.count=count;
        this.id=id;
        this.name=name;
    }

    public void setCollageData(List<GroupListBean.DataBean.CollageListBean.ListBean> list){
        isCollage=true;
        int size=list.size();
        datas =new ArrayList<>();
        for (int i=0;i<size;i++){
            ProductDetailBean.DataBean.RecommendProductsBean product=new ProductDetailBean.DataBean.RecommendProductsBean();
            product.setImg(list.get(i).getProduct().getImg());
            product.setPrice(list.get(i).getProduct().getCollagePrice());
            product.setBrand(list.get(i).getProduct().getBrand());
            product.setDesigner(list.get(i).getProduct().getDesigner());
            product.setStore(list.get(i).getProduct().getStore());
            product.setId(list.get(i).getProduct().getId());
            product.setName(list.get(i).getProduct().getName());
            product.setOriginalPrice(list.get(i).getProduct().getMinPrice());
            product.setCategoryId(list.get(i).getId());
            datas.add(product);
        }
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setAutoExpand(false);
        gridLayoutHelper.setPaddingLeft(ScreenUtil.dip2px(16));
        gridLayoutHelper.setPaddingRight(ScreenUtil.dip2px(16));
        gridLayoutHelper.setHGap(ScreenUtil.dip2px(16));
        return gridLayoutHelper;
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_product_item,parent,false);
        return new ProductHolder(view,itemWidth);
    }

    @Override
    public void onBindViewHolder(ProductHolder productHolder, final int position) {
        final ProductDetailBean.DataBean.RecommendProductsBean data=datas.get(position);
        int height = (int) (itemWidth* ((float) 1558 / 1000));
        UniversalImageLoader.displayImage(context, Util.getD2cProductPicUrl(data.getImg(),itemWidth,height),productHolder.productImage, R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
        productHolder.productName.setText(data.getName());
        if (!Util.isEmpty(data.getProductTradeType())) {
            if (data.getProductTradeType().equals("CROSS")) {//跨境商品
                productHolder.tvGlobalTag.setVisibility(View.VISIBLE);
            } else {
                productHolder.tvGlobalTag.setVisibility(View.GONE);
            }
        } else {
            productHolder.tvGlobalTag.setVisibility(View.GONE);
        }
        if(data.getPromotionId()>0) {
            if (data.getSalePrice() > data.getMinPrice()) {
                productHolder.productPrice.setText(Util.getProductPrice(data.getMinPrice(),data.getSalePrice()));
            } else {
                productHolder.productPrice.setText(Util.getProductPrice(data.getMinPrice()));
            }
        }else{
            if (data.getOriginalPrice() > data.getMinPrice()) {
                productHolder.productPrice.setText(Util.getProductPrice(data.getMinPrice(),data.getOriginalPrice()));
            } else {
                productHolder.productPrice.setText(Util.getProductPrice(data.getMinPrice()));
            }
        }
        if(data.getStore()>0){
            productHolder.tvNoStore.setVisibility(View.GONE);
            productHolder.viewNoStore.setVisibility(View.GONE);
        }else{
            productHolder.tvNoStore.setVisibility(View.VISIBLE);
            productHolder.viewNoStore.setVisibility(View.VISIBLE);
        }
        productHolder.tagLl.removeAllViews();
        if (data.getFlashPromotionId() != null && data.getFlashPromotionId() > 0) {
            addTag(productHolder.tagLl,1,"限时购");
        } else if (!Util.isEmpty(data.getPromotionTypeName())) {
            addTag(productHolder.tagLl,1,data.getPromotionTypeName());
        } else if (!Util.isEmpty(data.getOrderPromotionTypeName())) {
            addTag(productHolder.tagLl,2,data.getOrderPromotionTypeName());
        }else {
            productHolder.tagLl.setVisibility(View.GONE);
        }
        productHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id", data.getId());
                if (isCollage){
                    intent.putExtra("collageId", data.getCategoryId());
                }
                context.startActivity(intent);
            }
        });
    }

    /**
     *
     * @param type 1 是商品活动 2 是订单活动
     * @return
     */
    private TextView getTagTextView(int type, String text){
        TextView textView=new TextView(context);
        textView.setPadding(ScreenUtil.dip2px(3),0,ScreenUtil.dip2px(3),0);
        textView.setGravity(Gravity.CENTER);
        if (type==1){
            textView.setBackgroundColor(context.getResources().getColor(R.color.color_black));
        }else {
            textView.setBackgroundColor(context.getResources().getColor(R.color.color_red));
        }
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10);
        textView.setText(text);
        return textView;
    }

    /**
     * 添加商品标签
     * @param tagLl
     * @param type
     */
    private void addTag(LinearLayout tagLl, int type, String text){
        tagLl.setVisibility(View.VISIBLE);
        TextView textView=getTagTextView(type,text);
        LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(-2,ScreenUtil.dip2px(15));
        ll.setMargins(0,0,ScreenUtil.dip2px(4),0);
        tagLl.addView(textView,ll);
    }

    @Override
    public int getItemCount() {
        if (count>0){
            return count;
        }else {
            return datas.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return 30;
    }
}
