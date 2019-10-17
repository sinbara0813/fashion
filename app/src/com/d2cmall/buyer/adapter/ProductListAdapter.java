package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
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
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.bumptech.glide.Glide;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.FindSimilarActivity;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.GoodsBean;
import com.d2cmall.buyer.bean.ProductThemeBean;
import com.d2cmall.buyer.holder.ProductHolder;
import com.d2cmall.buyer.holder.TopicHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Created by rookie on 2017/8/18.
 */

public class ProductListAdapter extends DelegateAdapter.Adapter {
    private Context context;
    private LayoutHelper layoutHelper;
    private List<Object> list;
    private DisplayMetrics dm;
    private Point point;
    private int itemWidth;
    private int longClickPosition = -1;
    private RecyclerView recyclerView;
    private AnimationSet set;
    private Animation alphaAnimation, scale;
    private TextView findSimilarityTipTextView=null;
    private boolean hasLongClick =false;

    public ProductListAdapter(Context context, LayoutHelper layoutHelper, List<Object> list) {
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
        View itemView;
        if (viewType == 0) {
            itemView = LayoutInflater.from(context).inflate(R.layout.layout_product_item, parent, false);
            return new ProductHolder(itemView, itemWidth);
        } else {
            itemView = LayoutInflater.from(context).inflate(R.layout.layout_topic_item, parent, false);
            return new TopicHolder(itemView, itemWidth);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ProductHolder) {
            final GoodsBean.DataBean.ProductsBean.ListBean data = (GoodsBean.DataBean.ProductsBean.ListBean) list.get(position);
            final ProductHolder productHolder = (ProductHolder) holder;
                if (!Util.isEmpty(data.getProductTradeType())) {
                    if (data.getProductTradeType().equals("CROSS")) {//跨境商品
                        productHolder.tvGlobalTag.setVisibility(View.VISIBLE);
                    } else {
                        productHolder.tvGlobalTag.setVisibility(View.GONE);
                    }
                } else {
                    productHolder.tvGlobalTag.setVisibility(View.GONE);
                }
                if (data.getSoonPromotion() != null && data.getSoonPromotion().getSoonPromotionDate() != null) {//提前显示活动价
                    if (System.currentTimeMillis() < data.getSoonPromotion().getSoonPromotionDate()) {//还没开始了
                        productHolder.rlDiscount.setVisibility(View.VISIBLE);
                        productHolder.tvDiscountPrice.setText("¥" + data.getSoonPromotion().getSoonPromotionPrice());
                        productHolder.tvDiscountName.setText(data.getSoonPromotion().getSoonPromotionPrefix());
                    }else {
                        productHolder.rlDiscount.setVisibility(View.GONE);
                    }
                } else {
                    productHolder.rlDiscount.setVisibility(View.GONE);
                }
                productHolder.productName.setText(data.getName());
                productHolder.tagLl.removeAllViews();
                if (data.getFlashPromotionId() != null && data.getFlashPromotionId() > 0) {
                    addTag(productHolder.tagLl,1,"限时购");
                } else if (!Util.isEmpty(data.getPromotionTypeName())) {
                    addTag(productHolder.tagLl,1,data.getPromotionTypeName());
                }else if (!Util.isEmpty(data.getOrderPromotionTypeName())){
                    addTag(productHolder.tagLl,2,data.getOrderPromotionTypeName());
                } else {
                    productHolder.tagLl.setVisibility(View.GONE);
                }
                int height = (int) (itemWidth * (float) 1558 / 1000);
                Glide.with(context)
                        .load(Util.getD2cProductPicUrl(data.getImg(), itemWidth, height))
                        .placeholder(R.mipmap.ic_logo_empty5)
                        .override(itemWidth, height)
                        .dontAnimate()
                        .error(R.mipmap.ic_logo_empty5)
                        .into(productHolder.productImage);
                productHolder.itemView.setOnClickListener(new View.OnClickListener() {
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


                productHolder.ivFind.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, FindSimilarActivity.class);
                        intent.putExtra("id", data.getId());
                        intent.putExtra("data", data);
                        context.startActivity(intent);
                        if (longClickPosition != -1) {
                            ((GoodsBean.DataBean.ProductsBean.ListBean) list.get(longClickPosition)).setLongClick(false);
                            longClickPosition = -1;
                        }
                        notifyDataSetChanged();
                    }
                });

                if (data.getMark() == 0) {
                    productHolder.tvNoStore.setText("已下架");
                    productHolder.tvNoStore.setVisibility(View.VISIBLE);
                    productHolder.viewNoStore.setVisibility(View.VISIBLE);
                    productHolder.productPrice.setTextColor(Color.parseColor("#61000000"));
                    productHolder.productPrice.setTextSize(14);
                    productHolder.productPrice.setText("暂无报价");
                    return;
                }
                Integer promotionId = data.getPromotionId();
                //显示价格()
                if (promotionId != null && promotionId > 0) {
                    if (data.getSalePrice() > data.getMinPrice()) {
                        productHolder.productPrice.setText(Util.getProductPrice(data.getMinPrice(),data.getSalePrice()));
                    } else {
                        productHolder.productPrice.setText(Util.getProductPrice(data.getMinPrice()));
                    }
                } else {
                    if (data.getOriginalPrice() > data.getMinPrice()) {
                        productHolder.productPrice.setText(Util.getProductPrice(data.getMinPrice(),data.getOriginalPrice()));
                    } else {
                        productHolder.productPrice.setText(Util.getProductPrice(data.getMinPrice()));
                    }
                }
                if (data.getStore() < 1) {
                    productHolder.tvNoStore.setVisibility(View.VISIBLE);
                    productHolder.viewNoStore.setVisibility(View.VISIBLE);
                } else {
                    productHolder.tvNoStore.setVisibility(View.GONE);
                    productHolder.viewNoStore.setVisibility(View.GONE);
                }
                if (data.isIsSpot()) {
                    UniversalImageLoader.displayImage(context, "https://static.d2c.cn/img/promo/icon_mark_bigspring.png", productHolder.yearTag);
                    productHolder.yearTag.setVisibility(View.VISIBLE);
                } else {
                    productHolder.yearTag.setVisibility(View.GONE);
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
                //这个长按提示只会出现一次,只在首页和商品列表可能会出现,所以没有写进布局
            if(position==0 ){
                if(findSimilarityTipTextView==null && !D2CApplication.mSharePref.getSharePrefBoolean("findSimilarityTipProducts",false) && !hasLongClick){
                    D2CApplication.mSharePref.putSharePrefBoolean("findSimilarityTipProducts",true);
                    findSimilarityTipTextView = new TextView(context);
                    findSimilarityTipTextView.setGravity(Gravity.CENTER);
                    findSimilarityTipTextView.setTextSize(14);
                    findSimilarityTipTextView.setTextColor(context.getResources().getColor(R.color.color_white));
                    findSimilarityTipTextView.setText("长按可找相似");
                    findSimilarityTipTextView.setY((itemWidth * (float) 1558 / 2000));
                    findSimilarityTipTextView.setBackgroundResource(R.mipmap.pic_home_popover02);
                    findSimilarityTipTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            productHolder.rootRl.removeView(findSimilarityTipTextView);
                            findSimilarityTipTextView=null;
                        }
                    });
                    productHolder.rootRl.addView(findSimilarityTipTextView);
                }
                //长按任意item隐藏提示
                if(hasLongClick && findSimilarityTipTextView!=null){
                    productHolder.rootRl.removeView(findSimilarityTipTextView);
                    findSimilarityTipTextView=null;
                }
                if(findSimilarityTipTextView!=null){
                    findSimilarityTipTextView.setVisibility(View.VISIBLE);
                }
            }else if(findSimilarityTipTextView!=null){
                if(position>5){ //避免布局复用出现多个提示
                    findSimilarityTipTextView.setVisibility(View.GONE);
                }
            }

            productHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    data.setLongClick(true);
                    if (longClickPosition != -1 && longClickPosition!=position) {
                        ((GoodsBean.DataBean.ProductsBean.ListBean) list.get(longClickPosition)).setLongClick(false);
                    }
                    longClickPosition = position;
                    hasLongClick=true;
                    notifyDataSetChanged();
                    return true;
                }
            });

        } else {
            TopicHolder topicHolder = (TopicHolder) holder;
            final ProductThemeBean.DataBean.ThemesBean data = (ProductThemeBean.DataBean.ThemesBean) list.get(position);
            UniversalImageLoader.displayImage(context, data.getPic(), topicHolder.iv_topic);
            topicHolder.iv_topic.setPadding(0, ScreenUtil.dip2px(16), 0, 0);
            topicHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Util.urlAction(context, data.getUrl());
                }
            });
        }
    }

    /**
     *
     * @param type 1 是商品活动 2 是订单活动
     * @return
     */
    private TextView getTagTextView(int type, String text){
        TextView textView=new TextView(context);
        textView.setPadding(ScreenUtil.dip2px(3),0, ScreenUtil.dip2px(3),0);
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
        LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(-2, ScreenUtil.dip2px(15));
        ll.setMargins(0,0, ScreenUtil.dip2px(4),0);
        tagLl.addView(textView,ll);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof ProductThemeBean.DataBean.ThemesBean) {
            return 1;
        } else {
            return 0;
        }
    }
}
