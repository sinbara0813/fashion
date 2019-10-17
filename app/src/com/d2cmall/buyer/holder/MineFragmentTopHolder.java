package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.LineGridView;
import com.d2cmall.buyer.widget.RoundedImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/9/7.
 * Description : MineFragmentTopHolder
 */

public class MineFragmentTopHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.iv_user_head_pic)
    public RoundedImageView ivUserHeadPic;
    @Bind(R.id.iv_identify)
    public ImageView ivIdentify;
    @Bind(R.id.rl_head_pic)
    public RelativeLayout rlHeadPic;
    @Bind(R.id.tv_user_name)
    public TextView tvUserName;
    @Bind(R.id.iv_level)
    public ImageView ivLevel;
    @Bind(R.id.tv_level)
    public TextView tvLevel;
    @Bind(R.id.ll_level)
    public LinearLayout llLevel;
    @Bind(R.id.iv_buyer_level)
    public ImageView ivBuyerLevel;
    @Bind(R.id.tv_enter_partner)
    public TextView tvEnterPartner;
    @Bind(R.id.rl_user_info)
    public RelativeLayout rlUserInfo;
    @Bind(R.id.tv_collect_num)
    public TextView tvCollectNum;
    @Bind(R.id.ll_my_collect)
    public LinearLayout llMyCollect;
    @Bind(R.id.tv_follow_num)
    public TextView tvFollowNum;
    @Bind(R.id.ll_my_follow)
    public LinearLayout llMyFollow;
    @Bind(R.id.tv_footmark_num)
    public TextView tvFootmarkNum;
    @Bind(R.id.ll_my_footmark)
    public LinearLayout llMyFootmark;
    @Bind(R.id.rl_user_all)
    public RelativeLayout rlUserAll;
    @Bind(R.id.tv_buyer_count)
    public TextView tvBuyerCount;
    @Bind(R.id.iv_go_buyer_center)
    public ImageView ivGoBuyerCenter;
    @Bind(R.id.rl_buyer_top)
    public RelativeLayout rlBuyerTop;
    @Bind(R.id.ll_survey)
    public LinearLayout llSurvey;
    @Bind(R.id.ll_brand)
    public LinearLayout llBrand;
    @Bind(R.id.ll_sell)
    public LinearLayout llSell;
    @Bind(R.id.ll_deposit)
    public  LinearLayout llDeposit;
    @Bind(R.id.ll_buyer_button)
    public LinearLayout llBuyerButton;
    @Bind(R.id.rl_buyer_content)
    public RelativeLayout rlBuyerContent;
    @Bind(R.id.rl_buyer)
    public RelativeLayout rlBuyer;
    @Bind(R.id.tv_all_order)
    public TextView tvAllOrder;
    @Bind(R.id.title_order)
    public RelativeLayout titleOrder;
    @Bind(R.id.order_line)
    public View orderLine;
    @Bind(R.id.iv_un_pay)
    public ImageView ivUnPay;
    @Bind(R.id.iv_un_deliver)
    public ImageView ivUnDeliver;
    @Bind(R.id.iv_wait_deliver)
    public ImageView ivWaitDeliver;
    @Bind(R.id.iv_un_review)
    public ImageView ivUnReview;
    @Bind(R.id.iv_after_buy)
    public  ImageView ivAfterBuy;
    @Bind(R.id.ad_view)
    public ImageView adView;
    @Bind(R.id.iv_coupon)
    public ImageView ivCoupon;
    @Bind(R.id.iv_wallet)
    public ImageView ivWallet;
    @Bind(R.id.ll_wallet)
    public LinearLayout llWallet;
    @Bind(R.id.iv_integral)
    public ImageView ivIntegral;
    @Bind(R.id.iv_rebate)
    public ImageView ivRebate;
    @Bind(R.id.tv_tool)
    public TextView tvTool;
    @Bind(R.id.grid_layout)
    public LineGridView gridLayout;
    @Bind(R.id.recommend_title)
    public LinearLayout recommendTitle;
    @Bind(R.id.ll_enter_partner)
    public LinearLayout llEnterPartner;
    @Bind(R.id.ll_mine_top_bg)
    public LinearLayout llMineTopBg;
    @Bind(R.id.iv_setting)
    public ImageView ivSetting;
    @Bind(R.id.tv_red_point)
    public TextView tvRedPoint;
    @Bind(R.id.iv_cart)
    public  ImageView ivCart;
    @Bind(R.id.top_title)
    public RelativeLayout topTitle;


    public MineFragmentTopHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
