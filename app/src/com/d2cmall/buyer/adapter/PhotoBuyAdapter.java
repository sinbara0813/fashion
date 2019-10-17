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
import com.bumptech.glide.Glide;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.GoodsBean;
import com.d2cmall.buyer.holder.ProductHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Created by rookie on 2018/4/18.
 */

public class PhotoBuyAdapter extends DelegateAdapter.Adapter<ProductHolder> {
    private Context context;
    private int itemWidth;
    private List<GoodsBean.DataBean.ProductsBean.ListBean> list;

    public PhotoBuyAdapter(Context context, int itemWidth, List<GoodsBean.DataBean.ProductsBean.ListBean> list) {
        this.context = context;
        this.itemWidth = itemWidth;
        this.list = list;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setAutoExpand(false);
        gridLayoutHelper.setHGap(ScreenUtil.dip2px(16));
        gridLayoutHelper.setPaddingRight(ScreenUtil.dip2px(16));
        gridLayoutHelper.setPaddingLeft(ScreenUtil.dip2px(16));
        gridLayoutHelper.setMarginTop(ScreenUtil.dip2px(16));
        return gridLayoutHelper;
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_product_item, parent, false);
        return new ProductHolder(view, itemWidth);
    }

    @Override
    public void onBindViewHolder(ProductHolder productHolder, int position) {
        final GoodsBean.DataBean.ProductsBean.ListBean listBean = list.get(position);

        if (listBean.getSoonPromotion() != null && listBean.getSoonPromotion().getSoonPromotionDate() != null) {//提前显示活动价
            if (System.currentTimeMillis() < listBean.getSoonPromotion().getSoonPromotionDate()) {//还没开始了
                productHolder.rlDiscount.setVisibility(View.VISIBLE);
                productHolder.tvDiscountPrice.setText("¥" + listBean.getSoonPromotion().getSoonPromotionPrice());
                productHolder.tvDiscountName.setText(listBean.getSoonPromotion().getSoonPromotionPrefix());
            }
        } else {
            productHolder.rlDiscount.setVisibility(View.GONE);
        }
        productHolder.tagLl.removeAllViews();
        if (listBean.getFlashPromotionId() != null && listBean.getFlashPromotionId() > 0) {
            addTag(productHolder.tagLl,1,"限时购");
        } else if (!Util.isEmpty(listBean.getPromotionTypeName())) {
            addTag(productHolder.tagLl,1,listBean.getPromotionTypeName());
        } else if (!Util.isEmpty(listBean.getOrderPromotionTypeName())) {
            addTag(productHolder.tagLl,2,listBean.getOrderPromotionTypeName());
        }else {
            productHolder.tagLl.setVisibility(View.GONE);
        }
        productHolder.productName.setText(listBean.getName());
        Glide.with(context)
                .load(Util.getD2cPicUrl(listBean.getImg()))
                .placeholder(R.mipmap.ic_logo_empty5)
                .error(R.mipmap.ic_logo_empty5)
                .crossFade()
                .into(productHolder.productImage);
        productHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id", listBean.getId());
                context.startActivity(intent);
            }
        });
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
        if (promotionId != null && promotionId > 0) {
            if (listBean.getSalePrice() > listBean.getMinPrice()) {
                productHolder.productPrice.setText(Util.getProductPrice(listBean.getMinPrice(),listBean.getSalePrice()));
            } else {
                productHolder.productPrice.setText(Util.getProductPrice(listBean.getMinPrice()));
            }
        } else {
            if (listBean.getOriginalPrice() > listBean.getMinPrice()) {
                productHolder.productPrice.setText(Util.getProductPrice(listBean.getMinPrice(),listBean.getOriginalPrice()));
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
    }

    /**
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
        return list.size();
    }
}
