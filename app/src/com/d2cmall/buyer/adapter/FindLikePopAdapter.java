package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.bumptech.glide.Glide;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.GoodsBean;
import com.d2cmall.buyer.holder.FindLikeProductHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Created by rookie on 2017/8/18.
 */

public class FindLikePopAdapter extends DelegateAdapter.Adapter {
    private Context context;
    private LayoutHelper layoutHelper;
    private List<GoodsBean.DataBean.ProductsBean.ListBean> list;
    private DisplayMetrics dm;
    private Point point;
    private int itemWidth;
    private int longClickPosition = -1;
    private RecyclerView recyclerView;
    private AnimationSet set;
    private Animation alphaAnimation, scale;
    private TextView findSimilarityTipTextView=null;
    private boolean hasLongClick =false;

    public FindLikePopAdapter(Context context, LayoutHelper layoutHelper, List<GoodsBean.DataBean.ProductsBean.ListBean> list) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.list = list;
        dm = context.getResources().getDisplayMetrics();
        point = Util.getDeviceSize(context);
        itemWidth = (ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(48)) / 2;
    }


    private void initAnimation() {
        if (alphaAnimation == null) {
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

    public int getLongClickPosition() {
        return longClickPosition;
    }

    public void setLongClickPosition(int longClickPosition) {
        this.longClickPosition = longClickPosition;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }


    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_find_like_product_item, parent, false);
            return new FindLikeProductHolder(itemView, itemWidth);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final GoodsBean.DataBean.ProductsBean.ListBean data = (GoodsBean.DataBean.ProductsBean.ListBean) list.get(position);
            final FindLikeProductHolder findLikeProductHolder = (FindLikeProductHolder) holder;
                if (!Util.isEmpty(data.getProductTradeType())) {
                    if (data.getProductTradeType().equals("CROSS")) {//跨境商品
                        findLikeProductHolder.tvGlobalTag.setVisibility(View.VISIBLE);
                    } else {
                        findLikeProductHolder.tvGlobalTag.setVisibility(View.GONE);
                    }
                } else {
                    findLikeProductHolder.tvGlobalTag.setVisibility(View.GONE);
                }
                if (data.getSoonPromotion() != null && data.getSoonPromotion().getSoonPromotionDate() != null) {//提前显示活动价
                    if (System.currentTimeMillis() < data.getSoonPromotion().getSoonPromotionDate()) {//还没开始了
                        findLikeProductHolder.rlDiscount.setVisibility(View.VISIBLE);
                        findLikeProductHolder.tvDiscountPrice.setText("¥" + data.getSoonPromotion().getSoonPromotionPrice());
                        findLikeProductHolder.tvDiscountName.setText(data.getSoonPromotion().getSoonPromotionPrefix());
                    }else {
                        findLikeProductHolder.rlDiscount.setVisibility(View.GONE);
                    }
                } else {
                    findLikeProductHolder.rlDiscount.setVisibility(View.GONE);
                }
                findLikeProductHolder.designerName.setText(data.getDesigner());
                findLikeProductHolder.productName.setText(data.getName());
                int height = (int) (itemWidth * (float) 1558 / 1000);
                Glide.with(context)
                        .load(Util.getD2cProductPicUrl(data.getImg(), itemWidth, height))
                        .placeholder(R.mipmap.ic_logo_empty5)
                        .override(itemWidth, height)
                        .dontAnimate()
                        .error(R.mipmap.ic_logo_empty5)
                        .into(findLikeProductHolder.productImage);
                findLikeProductHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ProductDetailActivity.class);
                        intent.putExtra("id", data.getId());
                        context.startActivity(intent);
                        if (longClickPosition != -1) {
                            ((GoodsBean.DataBean.ProductsBean.ListBean) list.get(longClickPosition)).setLongClick(false);
                            longClickPosition = -1;
                            notifyDataSetChanged();
                        }
                    }
                });

                if (data.getMark() == 0) {
                    findLikeProductHolder.tvNoStore.setText("已下架");
                    findLikeProductHolder.tvNoStore.setVisibility(View.VISIBLE);
                    findLikeProductHolder.viewNoStore.setVisibility(View.VISIBLE);
                    findLikeProductHolder.productPrice.setTextColor(Color.parseColor("#61000000"));
                    findLikeProductHolder.productPrice.setTextSize(14);
                    findLikeProductHolder.productPrice.setText("暂无报价");
                    return;
                }
                findLikeProductHolder.productPrice.setText(Util.getNumberFormat(data.getMinPrice()));
                if (data.getStore() < 1) {
                    findLikeProductHolder.tvNoStore.setVisibility(View.VISIBLE);
                    findLikeProductHolder.viewNoStore.setVisibility(View.VISIBLE);
                } else {
                    findLikeProductHolder.tvNoStore.setVisibility(View.GONE);
                    findLikeProductHolder.viewNoStore.setVisibility(View.GONE);
                }

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

}
