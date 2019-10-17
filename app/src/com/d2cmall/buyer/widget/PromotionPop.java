package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.Point;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.ProductDetailBean;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.Util;

import java.util.List;



/**
 * 活动Pop
 * Author: PengHong
 * Date: 2016/11/04 17:31
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class PromotionPop implements TransparentPop.InvokeListener {

    private List<ProductDetailBean.DataBean.PromotionsBean> promotions;
    private TransparentPop mPop;
    private Context mContext;
    private Animation inAnimation;
    private Animation outAnimation;
    private View rootView;
    private ListView mListView;
    private TextView mTextView;
    private TextView sureTv;

    public PromotionPop(Context context, List<ProductDetailBean.DataBean.PromotionsBean> promotions) {
        this.mContext = context;
        this.promotions = promotions;
        init();
    }

    private void init() {
        rootView = LayoutInflater.from(mContext).inflate(R.layout.coupon_pop, null);
        mTextView = (TextView) rootView.findViewById(R.id.pop_name);
        mTextView.setText(R.string.label_active);
        mListView = (ListView) rootView.findViewById(R.id.m_list_view);
        sureTv=rootView.findViewById(R.id.sure_tv);
        mPop = new TransparentPop(mContext, this);
        inAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_up);
        inAnimation.setInterpolator(new DecelerateInterpolator());
        outAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_down);
        outAnimation.setInterpolator(new AccelerateInterpolator());
        mPop.setInAnimation(inAnimation);
        mPop.setOutAnimation(outAnimation);
        mPop.dismissFromOut();
        mListView.setAdapter(new PromotionPop.MyAdapter());
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Util.urlAction(mContext, promotions.get(position).getPromotionUrl());
            }
        });
        sureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
       /* mPop.setOutAnimationListener(new TransparentPop.OutAnimationListener() {
            @Override
            public void startAnimation() {
                if (callBack != null) {
                    callBack.dissmissBack(false);
                }
            }
        });*/
    }

    public void show(View parent) {
        mPop.show(parent);
    }

    public void dismiss() {
        if (mPop != null) {
            mPop.dismiss(true);
        }
    }

    public boolean isShow() {
        return mPop.isShowing();
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.BOTTOM);
        if (rootView.getParent() != null) {
            ((ViewGroup) rootView.getParent()).removeAllViews();
        }
        Point p = Util.getDeviceSize(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, p.y * 2 / 3);
        v.addView(rootView, lp);
    }

    @Override
    public void releaseView(LinearLayout v) {
        ((ViewGroup)rootView).removeAllViews();
        rootView=null;
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return promotions == null ? 0 : promotions.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PromotionPop.MyAdapter.ViewHolder holder;
            if (convertView == null) {
                holder = new PromotionPop.MyAdapter.ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_promotion_pop, null);
                holder.tvName = (TextView) convertView.findViewById(R.id.promotion_name);
                holder.tvTag = (TextView) convertView.findViewById(R.id.promotion_tag);
                holder.tvInfo=convertView.findViewById(R.id.promotion_info);
                convertView.setTag(holder);
            } else {
                holder = (PromotionPop.MyAdapter.ViewHolder) convertView.getTag();
            }
            if (promotions.get(position).getPromotionScope()==0){
                holder.tvName.setText(promotions.get(position).getPromotionName());
            }else {
                holder.tvName.setText(promotions.get(position).getPromotionSulo());
            }
            holder.tvTag.setText(promotions.get(position).getPromotionTypeName());
            return convertView;
        }

        class ViewHolder {
            TextView tvName;
            TextView tvTag;
            TextView tvInfo;
        }

    }
}
