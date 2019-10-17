package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;
import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.FindSimilarActivity;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.GoodsBean;
import com.d2cmall.buyer.bean.ProductDetailBean;
import com.d2cmall.buyer.holder.ProductHolder;
import com.d2cmall.buyer.holder.RecommendProductHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.tendcloud.tenddata.TCAgent;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/12 17:20
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainLikeAdapter extends DelegateAdapter.Adapter {
    private Context context;
    private List<ProductDetailBean.DataBean.RecommendProductsBean> datas;
    private int itemWidth;
    private String name;
    private String tag;
    private int longClickPosition = -1;
    private AnimationSet set;
    private Animation alphaAnimation, scale;

    public MainLikeAdapter(Context context, List<ProductDetailBean.DataBean.RecommendProductsBean> datas, int itemWidth, String name, String tag){
        this.context=context;
        this.datas=datas;
        this.itemWidth=itemWidth;
        this.name=name;
        this.tag=tag;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_product_item,parent,false);
        return new ProductHolder(view,itemWidth);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ProductDetailBean.DataBean.RecommendProductsBean data=datas.get(position);
        ProductHolder productHolder= (ProductHolder) holder;
        int height = (int) (itemWidth* ((float) 1558 / 1000));
        UniversalImageLoader.displayImage(context, Util.getD2cProductPicUrl(data.getImg(),itemWidth,height),productHolder.productImage, R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
        productHolder.productName.setText(data.getName());
        long promotionId = data.getPromotionId();
        productHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                data.setLongClick(true);
                if (longClickPosition != -1 && longClickPosition!=position) {
                    datas.get(longClickPosition).setLongClick(false);
                }
                longClickPosition = position;
                notifyDataSetChanged();
                return true;
            }
        });
        //显示价格()
        if(promotionId>0) {
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

        if (!Util.isEmpty(data.getProductTradeType())) {
            if (data.getProductTradeType().equals("CROSS")) {//跨境商品
                productHolder.tvGlobalTag.setVisibility(View.VISIBLE);
            } else {
                productHolder.tvGlobalTag.setVisibility(View.GONE);
            }
        } else {
            productHolder.tvGlobalTag.setVisibility(View.GONE);
        }

        if (data.isLongClick()) {
            initAnimation();
            productHolder.viewFind.getLayoutParams().height=productHolder.rootRl.getHeight();
            productHolder.viewFind.setVisibility(View.VISIBLE);
            productHolder.viewFind.startAnimation(alphaAnimation);
            productHolder.ivFind.setVisibility(View.VISIBLE);
            productHolder.ivFind.startAnimation(set);
        } else {
            productHolder.viewFind.setVisibility(View.GONE);
            productHolder.ivFind.setVisibility(View.GONE);
        }

        productHolder.ivFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FindSimilarActivity.class);
                intent.putExtra("id", data.getId());
                GoodsBean.DataBean.ProductsBean.ListBean data2=new GoodsBean.DataBean.ProductsBean.ListBean();
                data2.setId(data.getId());
                data2.setName(data.getName());
                data2.setImg(data.getImg());
                data2.setPromotionId(data.getPromotionId());
                data2.setMinPrice(data.getMinPrice());
                data2.setSalePrice(data.getSalePrice());
                data2.setOriginalPrice(data.getOriginalPrice());
                intent.putExtra("data", data2);
                context.startActivity(intent);
                if (longClickPosition != -1) {
                    datas.get(longClickPosition).setLongClick(false);
                    longClickPosition = -1;
                }
                notifyDataSetChanged();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id",datas.get(position).getId());
                context.startActivity(intent);
                if (longClickPosition != -1) {
                    datas.get(longClickPosition).setLongClick(false);
                    longClickPosition = -1;
                    notifyDataSetChanged();
                }
            }
        });
    }

    private void initAnimation(){
        if (alphaAnimation==null){
            alphaAnimation = new AlphaAnimation(0f, 1.0f);
            alphaAnimation.setDuration(350);
            scale = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scale.setDuration(350);
            set = new AnimationSet(true);
            set.addAnimation(alphaAnimation);
            set.addAnimation(scale);
            set.setDuration(350);
            set.setFillAfter(true);
            set.setInterpolator(new OvershootInterpolator());
        }
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
        return datas.size();
    }

    public int getLongClickPosition() {
        return longClickPosition;
    }

    public void setLongClickPosition(int longClickPosition) {
        this.longClickPosition = longClickPosition;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        StaggeredGridLayoutHelper staggeredGridLayoutHelper = new StaggeredGridLayoutHelper(2);
        staggeredGridLayoutHelper.setPaddingLeft(ScreenUtil.dip2px(16));
        staggeredGridLayoutHelper.setPaddingRight(ScreenUtil.dip2px(16));
        staggeredGridLayoutHelper.setPaddingBottom(ScreenUtil.dip2px(16));
        staggeredGridLayoutHelper.setHGap(ScreenUtil.dip2px(16));
        return staggeredGridLayoutHelper;
    }

    @Override
    public int getItemViewType(int position) {
        return 30;
    }
}
