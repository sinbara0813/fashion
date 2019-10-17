package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.ShowPopImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/28 17:13
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductInfoHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.product_price)
    public TextView productPrice;
    @Bind(R.id.promotion_iv)
    public ImageView promotionIv;
    @Bind(R.id.promotion_tv)
    public TextView promotionTv;
    @Bind(R.id.flash_price)
    public TextView flashPrice;
    @Bind(R.id.time_tag)
    public TextView timeTag;
    @Bind(R.id.time_hour)
    public TextView timeHour;
    @Bind(R.id.time_hour_tag)
    public TextView timeHourTag;
    @Bind(R.id.time_minute)
    public TextView timeMinute;
    @Bind(R.id.time_minute_tag)
    public TextView timeMinuteTag;
    @Bind(R.id.time_mouse)
    public TextView timeMouse;
    @Bind(R.id.time_mouse_tag)
    public TextView timeMouseTag;
    @Bind(R.id.promotion_time_ll)
    public LinearLayout promotionTimeLl;
    @Bind(R.id.promotion_start_time)
    public TextView promotionStartTime;
    @Bind(R.id.product_flash_sale)
    public LinearLayout productFlashSale;
    @Bind(R.id.product_name)
    public TextView productName;
    @Bind(R.id.product_collect_iv)
    public ShowPopImageView productCollectIv;
    @Bind(R.id.remark)
    public TextView remark;
    @Bind(R.id.country_pic)
    public ImageView countryPic;
    @Bind(R.id.country_name)
    public TextView countryName;
    @Bind(R.id.country_ll)
    public LinearLayout countryLl;
    @Bind(R.id.fx_tv)
    public TextView fxTv;
    @Bind(R.id.fx_share)
    public TextView fxShare;
    @Bind(R.id.fx_qr)
    public TextView fxQr;
    @Bind(R.id.fx_matter)
    public TextView fxMatter;
    @Bind(R.id.fx_rl)
    public RelativeLayout fxRl;
    @Bind(R.id.product_info_tv)
    public TextView productInfoTv;
    @Bind(R.id.product_open)
    public TextView productOpen;
    @Bind(R.id.product_info_tv_Ll)
    public RelativeLayout productInfoTvLl;
    @Bind(R.id.product_shili_ll)
    public LinearLayout productShiliLl;
    @Bind(R.id.coupon_line)
    public View couponLine;
    @Bind(R.id.product_coupon_content_ll)
    public LinearLayout productCouponContentLl;
    @Bind(R.id.product_coupon_ll)
    public LinearLayout productCouponLl;
    @Bind(R.id.promotion_line)
    public View promotionLine;
    @Bind(R.id.product_promotion_content_ll)
    public LinearLayout productPromotionContentLl;
    @Bind(R.id.product_promotion_ll)
    public LinearLayout productPromotionLl;
    @Bind(R.id.middle_ll)
    public LinearLayout middleLl;
    @Bind(R.id.select_standard_tv)
    public TextView selectStandardTv;
    @Bind(R.id.standard_more)
    public ImageView standardMore;
    @Bind(R.id.standard_ll)
    public LinearLayout standardLl;
    @Bind(R.id.product_server_content_ll)
    public LinearLayout productServerContentLl;
    @Bind(R.id.product_designer_iv)
    public ImageView productDesignerIv;
    @Bind(R.id.product_designer_name)
    public TextView productDesignerName;
    @Bind(R.id.product_design_info)
    public TextView productDesignInfo;
    @Bind(R.id.design_valid_tag)
    public ImageView designValidTag;
    @Bind(R.id.product_designer_ll)
    public LinearLayout productDesignerLl;
    @Bind(R.id.product_design_recommend)
    public RecyclerView productDesignRecommend;
    @Bind(R.id.product_design)
    public RelativeLayout productDesign;
    @Bind(R.id.h_price)
    public TextView hPrice;
    @Bind(R.id.h_iv)
    public ImageView hIv;
    @Bind(R.id.h_tv)
    public TextView hTv;
    @Bind(R.id.h_hour)
    public TextView hHour;
    @Bind(R.id.h_minute)
    public TextView hMinute;
    @Bind(R.id.h_mouse)
    public TextView hMouse;
    @Bind(R.id.h_flash_ll)
    public LinearLayout hFlashLL;
    @Bind(R.id.h_day)
    public TextView hDay;
    @Bind(R.id.h_day_tag)
    public TextView hDayTag;
    @Bind(R.id.time_day)
    public TextView timeDay;
    @Bind(R.id.time_day_tag)
    public TextView timeDayTag;
    @Bind(R.id.top_line)
    public  View topLine;
    @Bind(R.id.title_name)
    public TextView titleName;
    @Bind(R.id.title_more)
    public TextView titleMore;
    @Bind(R.id.rl_title)
    public RelativeLayout rlTitle;
    @Bind(R.id.tv_comb_title)
    public TextView tvCombTitle;
    @Bind(R.id.tv_comb_price)
    public  TextView tvCombPrice;
    @Bind(R.id.tv_origin_price)
    public TextView tvOriginPrice;
    @Bind(R.id.tv_gap_price)
    public  TextView tvGapPrice;
    @Bind(R.id.product_comb_rv)
    public  RecyclerView productCombRv;
    @Bind(R.id.ll_comb)
    public  LinearLayout llComb;
    @Bind(R.id.bottom_line)
    public  View combBottomLine;
    @Bind(R.id.boss_chat)
    public  TextView bossChat;
    @Bind(R.id.boss_contact)
    public TextView bossContact;
    @Bind(R.id.global_iv)
    public ImageView globalIv;
    @Bind(R.id.ad_iv)
    public ImageView adIv;
    @Bind(R.id.tv_caomei)
    public TextView tvCaoMei;


    public ProductInfoHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
