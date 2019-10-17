package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.GoodsBean;
import com.d2cmall.buyer.bean.RecommendListBean;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fixme
 * Author: hrb
 * Date: 2016/10/26 18:26
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class RecommentAdapter extends BaseAdapter {

    private Context mContext;
    private List<GoodsBean.DataBean.ProductsBean.ListBean> recommendList;
    private int width;

    public RecommentAdapter(Context context, List<GoodsBean.DataBean.ProductsBean.ListBean> data) {
        this.mContext = context;
        this.recommendList = data;
        Point point = Util.getDeviceSize(mContext);
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        width = Math.round((point.x - 15 * dm.density) / 2);
    }

    @Override
    public int getCount() {
        return recommendList == null ? 0 : recommendList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView == null) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_recommend_good, parent, false);
            holder=new ViewHolder(v);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.image.getLayoutParams();
            params.width = width;
            params.height = Math.round(width * 1400 / 1000);
            convertView=v;
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        UniversalImageLoader.displayImage(mContext,Util.getD2cPicUrl(recommendList.get(position).getImg()), holder.image, R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
        holder.tv.setText(recommendList.get(position).getName());
        holder.price.setText(mContext.getString(R.string.label_price, Util.getNumberFormat(recommendList.get(position).getMinPrice())));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra("id", recommendList.get(position).getId());
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.image)
        ImageView image;
        @Bind(R.id.tv)
        TextView tv;
        @Bind(R.id.price)
        TextView price;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
