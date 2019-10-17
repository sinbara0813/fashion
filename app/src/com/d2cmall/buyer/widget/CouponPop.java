package com.d2cmall.buyer.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.CouponRelationBrandsActivity;
import com.d2cmall.buyer.activity.CouponRelationProductListActivity;
import com.d2cmall.buyer.adapter.ObjectBindAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.bean.CouponSuccessBean;
import com.d2cmall.buyer.bean.CouponRangeBean;
import com.d2cmall.buyer.bean.ProductDetailBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.AnimUtil;
import com.d2cmall.buyer.util.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 优惠券
 * Author: hrb
 * Date: 2016/11/02 14:03
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CouponPop implements TransparentPop.InvokeListener, ObjectBindAdapter.ViewBinder<ProductDetailBean.DataBean.CouponsEntity> {

    private List<ProductDetailBean.DataBean.CouponsEntity> listEntities;
    private ObjectBindAdapter<ProductDetailBean.DataBean.CouponsEntity> adapter;
    private TransparentPop mPop;
    private Context mContext;
    private View rootView;
    private Animation inAnimation;
    private Animation outAnimation;
    private ListView mListView;
    private Map<Integer, Boolean> checkMap = new HashMap<>();
    private ProgressBar progressBar;
    private TextView sureTv;

    public CouponPop(Context context, List<ProductDetailBean.DataBean.CouponsEntity> couponsEntities) {
        this.mContext = context;
        this.listEntities = couponsEntities;
        init();
    }

    private void init() {
        rootView = LayoutInflater.from(mContext).inflate(R.layout.coupon_pop, null);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
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
        adapter = new ObjectBindAdapter<ProductDetailBean.DataBean.CouponsEntity>(mContext, listEntities, R.layout.layout_coupon_item, this);
        mListView.setAdapter(adapter);
        sureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        /*mPop.setOutAnimationListener(new TransparentPop.OutAnimationListener() {
            @Override
            public void startAnimation() {
                if (callBack != null) {
                    callBack.dissmissBack(false);
                }
            }
        });*/
    }

    @Override
    public void setViewValue(View view, final ProductDetailBean.DataBean.CouponsEntity listEntity, int position) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        holder.tvDescribe.setBackgroundResource(R.mipmap.bg_coupon_newmiddle);
        if (listEntity.getType().equals("DISCOUNT")) {
            double amount = (double) listEntity.getAmount() / 10;
            holder.tvPrice.setText(mContext.getString(R.string.label_discount, Util.getNumberFormat(amount, false)));
        } else {
            holder.tvPrice.setText(String.valueOf(listEntity.getAmount()));
        }
        holder.tvLimit.setText(String.format(mContext.getString(R.string.label_need_amount), listEntity.getNeedAmount()));
        holder.tvTitle.setText(listEntity.getName());
        holder.tvDate.setVisibility(View.GONE);
        holder.tvSignCanGive.setText(String.format(mContext.getString(R.string.label_pozhe), listEntity.getEnableDate(), listEntity.getExpireDate()));
        holder.tvDescribe.setText(listEntity.getRemark());
        holder.tvSignCanGive.setTextSize(10);
        holder.tvSignCanGive.setVisibility(View.VISIBLE);
        holder.tvSignCanGive.setCompoundDrawablesWithIntrinsicBounds(null,
                null, null, null);
        holder.tvSignCanGive.setGravity(Gravity.CENTER);
        holder.tvSignCanGive.setTextColor(mContext.getResources().getColor(R.color.trans_87_color_black));
//        if (!Util.isEmpty(listEntity.getUrl())) {
//            holder.arrowDotLayout.setVisibility(View.VISIBLE);
//        } else {
//            holder.arrowDotLayout.setVisibility(View.GONE);
//        }
        if (!Util.isEmpty(listEntity.getRemark())) {
            holder.imgArrow.setVisibility(View.VISIBLE);
            if (listEntity.isExpand) {
                holder.imgArrow.setImageResource(R.mipmap.ic_arrow_up);
            } else {
                holder.imgArrow.setImageResource(R.mipmap.ic_arrow_down);
            }
            setListener(listEntity, holder.llRemind, holder.imgArrow);
        } else {
            holder.imgArrow.setVisibility(View.GONE);
        }
        holder.tvGetCoupon.setVisibility(View.VISIBLE);
        //holder.tvGetCoupon.setTextColor(mContext.getResources().getColor(R.color.color_red));
        ViewHolder finalHolder = holder;
        holder.tvGetCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) mContext, 999)) {

                    if("立即使用".equals(finalHolder.tvGetCoupon.getText())){
                        if (listEntity != null && !Util.isEmpty(listEntity.getUrl())) {
                            Util.urlAction(mContext, listEntity.getUrl());
                        }else {//查看优惠券可使用的范围,跳转商品列表或店铺列表
                            loadCouponUseRange(listEntity.getMyCouponId(),listEntity.getName());
                        }
                    }else{
                        getCoupon((TextView) v, listEntity, finalHolder.tvGetCoupon);
                    }
                }
            }
        });
    }
    private void loadCouponUseRange(long couponId,String couponIdName) {
        if(couponId>0){
            SimpleApi api = new SimpleApi();
            api.setInterPath(String.format(Constants.COUPONS_PRODUCTS_RANGE,couponId));
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CouponRangeBean>() {
                @Override
                public void onResponse(CouponRangeBean couponRangeBean) {
                    if(progressBar==null){
                        return;
                    }
                    if(couponRangeBean!=null){
                        //全场或者(商品店铺均未绑定的优惠券)不跳
                        if(couponRangeBean.getData().getCoupon()!=null && Util.isEmpty(couponRangeBean.getData().getCoupon().getCheckAssociation())){
                            if("ALL".equals(couponRangeBean.getData().getCoupon().getCheckAssociation())){
                                return;
                            }
                        }
                        if(couponRangeBean.getData().getBrands()!=null && couponRangeBean.getData().getBrands().getList()!=null && couponRangeBean.getData().getBrands().getList().size()>0 && couponRangeBean.getData().getCoupon()!=null && couponRangeBean.getData().getCoupon().isToRedirect()){
                            mContext.startActivity(new Intent(mContext, CouponRelationBrandsActivity.class)
                                    .putExtra("couponName",couponIdName)
                                    .putExtra("couponId",couponId));
                        }else if(couponRangeBean.getData().getProductsX()!=null && couponRangeBean.getData().getProductsX().getList().size()>0 && couponRangeBean.getData().getCoupon()!=null && couponRangeBean.getData().getCoupon().isToRedirect()){
                            mContext.startActivity(new Intent(mContext, CouponRelationProductListActivity.class)
                                    .putExtra("couponId",couponId)
                                    .putExtra("couponName",couponIdName));
                        }
                    }

                }
            });

        }
    }

    private void setListener(final ProductDetailBean.DataBean.CouponsEntity listEntity, final LinearLayout ll, final ImageView arrow) {
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listEntity.isExpand) {
                    listEntity.setExpand(false);
                    ll.setVisibility(View.GONE);
                    arrow.startAnimation(AnimUtil.getAnimArrowDown(mContext));
                } else {
                    listEntity.setExpand(true);
                    ll.setVisibility(View.VISIBLE);
                    arrow.startAnimation(AnimUtil.getAnimArrowUp(mContext));
                }
            }
        });
    }

    private void getCoupon(final TextView v, ProductDetailBean.DataBean.CouponsEntity couponsEntity, TextView tvGetCoupon) {
        v.setEnabled(false);
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.LEAD_COUPON_URL, couponsEntity.getId()));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CouponSuccessBean>() {
            @Override
            public void onResponse(CouponSuccessBean response) {
                v.setEnabled(true);
                Util.showToast(mContext, "领取成功");
                tvGetCoupon.setText("立即使用");
                couponsEntity.setMyCouponId(response.getData().getCoupon().getId());
                tvGetCoupon.setBackgroundColor(Color.parseColor("#E6AA48"));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                v.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }

    private class DescribeClickListener implements View.OnClickListener {

        private ProductDetailBean.DataBean.CouponsEntity listEntity;
        private TextView tvLabel;
        private ImageView imgArrow;
        private View describeLayout;
        private int index;

        public DescribeClickListener(ProductDetailBean.DataBean.CouponsEntity listEntity, TextView tvLabel,
                                     ImageView imgArrow, View describeLayout, int position) {
            super();
            this.listEntity = listEntity;
            this.tvLabel = tvLabel;
            this.imgArrow = imgArrow;
            this.describeLayout = describeLayout;
            this.index = position;
        }

        @Override
        public void onClick(View v) {
            imgArrow.setRotation(0);
            if (imgArrow.getAnimation() != null && !imgArrow.getAnimation().hasEnded()) {
                return;
            }
            if (describeLayout.getVisibility() == View.VISIBLE) {
                tvLabel.setText(R.string.label_view_detail);
                imgArrow.startAnimation(AnimUtil.getAnimArrowDown(mContext));
                //describeLayout.setVisibility(View.GONE);
                checkMap.put(index, false);
            } else {
                tvLabel.setText(R.string.label_retract_detail);
                imgArrow.startAnimation(AnimUtil.getAnimArrowUp(mContext));
                //describeLayout.setVisibility(View.VISIBLE);
                checkMap.put(index, true);
            }
            reload(index);
        }
    }

    private void reload(int position) {
        adapter = new ObjectBindAdapter<ProductDetailBean.DataBean.CouponsEntity>(mContext, listEntities, R.layout.layout_coupon_item, this);
        mListView.setAdapter(adapter);
        mListView.setSelection(position);
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
        Point p = Util.getDeviceSize(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, p.y * 2 / 3);
        v.addView(rootView, lp);
    }

    @Override
    public void releaseView(LinearLayout v) {
        ((ViewGroup) rootView).removeAllViews();
        rootView = null;
    }

    static class ViewHolder {
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.tv_discount)
        TextView tvDiscount;
        @Bind(R.id.tv_limit)
        TextView tvLimit;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_date)
        TextView tvDate;
        @Bind(R.id.img_arrow)
        ImageView imgArrow;
        @Bind(R.id.tv_get)
        TextView tvGet;
        @Bind(R.id.iv_state)
        ImageView ivState;
        @Bind(R.id.rl_main)
        RelativeLayout rlMain;
        @Bind(R.id.tv_describe)
        TextView tvDescribe;
        @Bind(R.id.ll_remind)
        LinearLayout llRemind;
        @Bind(R.id.view_bottom)
        View viewBottom;
        @Bind(R.id.tv_get_coupon)
        TextView tvGetCoupon;
        @Bind(R.id.tv_sign_can_give)
        TextView tvSignCanGive;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
