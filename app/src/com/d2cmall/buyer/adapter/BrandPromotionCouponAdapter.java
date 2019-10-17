package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.BrandDetailPromotionBean;
import com.d2cmall.buyer.holder.BrandPromotionCouponHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Created by Administrator on 2018/2/2.
 * Description : SaleSchoolAdapter
 */

public class BrandPromotionCouponAdapter extends DelegateAdapter.Adapter {
    private List<BrandDetailPromotionBean.DataBean.CouponDefsBean> couponDefsBeans;
    private Context mContext;
    public BrandPromotionCouponAdapter(Context mContext, List<BrandDetailPromotionBean.DataBean.CouponDefsBean> couponDefsBeans) {
        this.mContext = mContext;
        this.couponDefsBeans = couponDefsBeans;
    }


    @Override
    public BrandPromotionCouponHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_brand_promotion_coupon_item, parent, false);
        return new BrandPromotionCouponHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final BrandPromotionCouponHolder brandPromotionCouponHolder = (BrandPromotionCouponHolder) holder;
        final BrandDetailPromotionBean.DataBean.CouponDefsBean couponDefsBean = couponDefsBeans.get(position);
        if (couponDefsBean.getType().equals("DISCOUNT")) {
            double amount = (double) couponDefsBean.getAmount() / 10;
            //字体不一样大
            String str = mContext.getString(R.string.label_discount, Util.getNumberFormat(amount));
            int length = str.length();
            SpannableString textSpan = new SpannableString(str);
            textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(14)), 0, length - 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(20)), length - 1, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            brandPromotionCouponHolder.tvPrice.setText(textSpan);
        } else {
            double amount =  couponDefsBean.getAmount();
            String str = mContext.getString(R.string.label_price, Util.getNumberFormat(amount));
            int length = str.length();
            SpannableString textSpan = new SpannableString(str);
            textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(14)), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(20)), 1, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            brandPromotionCouponHolder.tvPrice.setText(textSpan);
        }
             brandPromotionCouponHolder.tvLimit.setText(String.format(mContext.getString(R.string.label_need_amount1), couponDefsBean.getNeedAmount()));

        brandPromotionCouponHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(couponDefsBean.getFree()==1){//免费
                    receiveCoupon(brandPromotionCouponHolder.itemView,couponDefsBean.getId());
                }else{//付费
                    ///coupondef/buynow?defId=xxx
                    Util.urlAction(mContext,"/coupondef/buynow?defId="+couponDefsBean.getId());
                }

            }
        });
    }

    private void receiveCoupon(final View itemView, long id) {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.LEAD_COUPON_URL, id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                itemView.setEnabled(true);
                Util.showToast(mContext, "领取成功");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                itemView.setEnabled(true);
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(couponDefsBeans ==null || couponDefsBeans.size()==0){
            return 0;
        }else{
            return couponDefsBeans.size();
        }

    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return linearLayoutHelper;
    }



}
