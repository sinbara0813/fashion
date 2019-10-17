package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.FindSimilarActivity;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.GoodsBean;
import com.d2cmall.buyer.holder.ProductHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Created by rookie on 2017/9/7.
 */

public class BrandProductAdapter extends DelegateAdapter.Adapter<ProductHolder> {
    private Context context;
    private List<GoodsBean.DataBean.ProductsBean.ListBean> list;
    private LayoutHelper layoutHelper;
    private int itemWidth;
    private AnimationSet set;
    private Animation alphaAnimation, scale;
    private int longClickPosition = -1;

    public BrandProductAdapter(Context context, List<GoodsBean.DataBean.ProductsBean.ListBean> list, LayoutHelper layoutHelper, int itemWidth) {
        this.context = context;
        this.list = list;
        this.layoutHelper = layoutHelper;
        this.itemWidth = itemWidth;
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

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_product_item, parent, false);
        return new ProductHolder(view, itemWidth);
    }

    @Override
    public void onBindViewHolder(ProductHolder productHolder, final int position) {
        final GoodsBean.DataBean.ProductsBean.ListBean listBean = list.get(position);
        if (listBean.getSoonPromotion() != null && listBean.getSoonPromotion().getSoonPromotionDate() != null) {//提前显示活动价
            if (System.currentTimeMillis() < listBean.getSoonPromotion().getSoonPromotionDate()) {//还没开始了
                productHolder.rlDiscount.setVisibility(View.VISIBLE);
                productHolder.tvDiscountPrice.setText("¥" + listBean.getSoonPromotion().getSoonPromotionPrice());
                productHolder.tvDiscountName.setText(listBean.getSoonPromotion().getSoonPromotionPrefix());
            }else{
                productHolder.rlDiscount.setVisibility(View.GONE);
            }
        } else {
            productHolder.rlDiscount.setVisibility(View.GONE);
        }

        if (!Util.isEmpty(listBean.getProductTradeType())) {
            if (listBean.getProductTradeType().equals("CROSS")) {//跨境商品
                productHolder.tvGlobalTag.setVisibility(View.VISIBLE);
            } else {
                productHolder.tvGlobalTag.setVisibility(View.GONE);
            }
        } else {
            productHolder.tvGlobalTag.setVisibility(View.GONE);
        }

        productHolder.tagLl.removeAllViews();
        if (listBean.getFlashPromotionId() != null && listBean.getFlashPromotionId() > 0) {
            addTag(productHolder.tagLl,1,"限时购");
        } else if (!Util.isEmpty(listBean.getPromotionTypeName())) {
            addTag(productHolder.tagLl,1,listBean.getPromotionTypeName());
        }else if (!Util.isEmpty(listBean.getOrderPromotionTypeName())){
            addTag(productHolder.tagLl,2,listBean.getOrderPromotionTypeName());
        }else {
            productHolder.tagLl.setVisibility(View.GONE);
        }
        productHolder.productName.setText(listBean.getName());
        int height = (int) (itemWidth * (float) 1558 / 1000);
        Glide.with(context)
                .load(Util.getD2cProductPicUrl(listBean.getImg(), itemWidth, height))
                .placeholder(R.mipmap.ic_logo_empty5)
                .override(itemWidth, height)
                .dontAnimate()
                .error(R.mipmap.ic_logo_empty5)
                .into(productHolder.productImage);
        productHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id", listBean.getId());
                context.startActivity(intent);
                if (longClickPosition != -1) {
                    list.get(longClickPosition).setLongClick(false);
                    longClickPosition = -1;
                    notifyDataSetChanged();
                }

            }
        });
        productHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listBean.setLongClick(true);
                if (longClickPosition != -1 && longClickPosition!=position) {
                    list.get(longClickPosition).setLongClick(false);
                }
                longClickPosition = position;
                notifyDataSetChanged();
                return true;
            }
        });
        productHolder.ivFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FindSimilarActivity.class);
                intent.putExtra("id", listBean.getId());
                intent.putExtra("data", listBean);
                context.startActivity(intent);
                if (longClickPosition != -1) {
                    list.get(longClickPosition).setLongClick(false);
                    longClickPosition = -1;
                }
                notifyDataSetChanged();
            }
        });
        if (listBean.isLongClick()) {
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
        if (listBean.getMark() == 0) {
            productHolder.tvNoStore.setText("已下架");
            productHolder.tvNoStore.setVisibility(View.VISIBLE);
            productHolder.viewNoStore.setVisibility(View.VISIBLE);
            productHolder.productPrice.setTextColor(Color.parseColor("#61000000"));
            productHolder.productPrice.setTextSize(14);
            productHolder.productPrice.setText("暂无报价");
            return;
        }
        Integer promotionId = listBean.getPromotionId();
        if (promotionId == null) {
            promotionId = 0;
        }
        //显示价格()
        if (promotionId <= 0) {
            if (listBean.getOriginalPrice() > listBean.getMinPrice()) {
                productHolder.productPrice.setText(Util.getProductPrice(listBean.getMinPrice(),listBean.getOriginalPrice()));
            } else {
                productHolder.productPrice.setText(Util.getProductPrice(listBean.getMinPrice()));
            }
        } else {
            if (listBean.getSalePrice() > listBean.getMinPrice()) {
                productHolder.productPrice.setText(Util.getProductPrice(listBean.getMinPrice(),listBean.getSalePrice()));
            } else {
                productHolder.productPrice.setText(Util.getProductPrice(listBean.getMinPrice()));
            }
        }

        if (list.get(position).getStore() < 1) {
            productHolder.tvNoStore.setVisibility(View.VISIBLE);
            productHolder.viewNoStore.setVisibility(View.VISIBLE);
        } else {
            productHolder.tvNoStore.setVisibility(View.GONE);
            productHolder.viewNoStore.setVisibility(View.GONE);
        }
        if (list.get(position).isIsSpot()) {
            UniversalImageLoader.displayImage(context, "https://static.d2c.cn/img/promo/icon_mark_bigspring.png", productHolder.yearTag);
            productHolder.yearTag.setVisibility(View.VISIBLE);
        } else {
            productHolder.yearTag.setVisibility(View.GONE);
        }
    }

    /**
     *
     * @param type 1 是商品活动 2 是订单活动
     * @return
     */
    private TextView getTagTextView(int type,String text){
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
    private void addTag(LinearLayout tagLl,int type,String text){
        tagLl.setVisibility(View.VISIBLE);
        TextView textView=getTagTextView(type,text);
        LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(-2,ScreenUtil.dip2px(15));
        ll.setMargins(0,0,ScreenUtil.dip2px(4),0);
        tagLl.addView(textView,ll);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
