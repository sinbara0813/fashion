package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.FlashProductActivity;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.FlashBrandListBean;
import com.d2cmall.buyer.bean.FlashProductListBean;
import com.d2cmall.buyer.holder.FlashBrandHolder;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/12/13 15:54
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FlashBrandAdapter extends DelegateAdapter.Adapter {

    private Context context;
    private List<FlashBrandListBean.DataBean.BrandsBean> list;

    public FlashBrandAdapter(Context context, List<FlashBrandListBean.DataBean.BrandsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_flash_brand, parent, false);
        return new FlashBrandHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        FlashBrandHolder flashBrandHolder = (FlashBrandHolder) holder;
        UniversalImageLoader.displayImage(context, list.get(position).getBrandPic(), flashBrandHolder.brandIv);
        MyAdapter mainBrandProductAdapter = new MyAdapter(list.get(position).getProducts());
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(context);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        flashBrandHolder.recycleView.setLayoutManager(layoutManager1);
        flashBrandHolder.recycleView.setAdapter(mainBrandProductAdapter);
        flashBrandHolder.brandIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FlashProductActivity.class);
                intent.putExtra("promotionId", list.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    class MyAdapter extends RecyclerView.Adapter<FlashBrandAdapter.MyAdapter.MyViewHolder> {

        private List<FlashProductListBean.DataBean.ProductsBean.ListBean> list2;

        public MyAdapter(List<FlashProductListBean.DataBean.ProductsBean.ListBean> list) {
            this.list2 = list;
        }


        @Override
        public FlashBrandAdapter.MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_main_brand_product_item, parent, false);
            return new FlashBrandAdapter.MyAdapter.MyViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return list2.size();
        }

        @Override
        public void onBindViewHolder(FlashBrandAdapter.MyAdapter.MyViewHolder holder, final int position) {
            UniversalImageLoader.displayImage(context, list2.get(position).getImg(), holder.image);
            if (list2.get(position).getSalePrice() > list2.get(position).getFlashPrice()) {
                StringBuilder builder = new StringBuilder();
                double price;
                if (list2.get(position).getPromotionId() > 0) {
                    price = list2.get(position).getSalePrice();
                } else {
                    price = list2.get(position).getSalePrice();
                }
                builder.append("¥").append(Util.getNumberFormat(list2.get(position).getFlashPrice()))
                        .append("  ¥").append(Util.getNumberFormat(price));
                int third = builder.toString().lastIndexOf("¥");
                SpannableString spannableString = new SpannableString(builder.toString());

                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#61000000"));
                spannableString.setSpan(colorSpan, third, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

                RelativeSizeSpan sizeSpan = new RelativeSizeSpan(0.85f);
                spannableString.setSpan(sizeSpan, third, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

                StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
                spannableString.setSpan(strikethroughSpan, third, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                holder.price.setText(spannableString);
            } else {
                holder.price.setText("¥" + Util.getNumberFormat(list2.get(position).getFlashPrice()));
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra("id", list2.get(position).getId());
                    context.startActivity(intent);
                }
            });
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            public ImageView image;
            public TextView price;

            public MyViewHolder(View itemView) {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.image);
                price = (TextView) itemView.findViewById(R.id.price);
            }
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 4;
    }
}
