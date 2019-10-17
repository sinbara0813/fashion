package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.CouponsBean;
import com.d2cmall.buyer.shareplatform.WxHandle;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.CouponBgSpan;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2018/7/10.
 */

public class ShareCouponPop extends PopupWindow {

    @Bind(R.id.tv_main_title)
    TextView tvMainTitle;
    @Bind(R.id.tv_rule)
    TextView tvRule;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_discount)
    TextView tvDiscount;
    @Bind(R.id.tv_limit)
    TextView tvLimit;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.buy_price_tv)
    TextView buyPriceTv;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.tv_sign_can_give)
    TextView tvSignCanGive;
    @Bind(R.id.img_arrow)
    ImageView imgArrow;
    @Bind(R.id.iv_state)
    ImageView ivState;
    @Bind(R.id.rl_main)
    RelativeLayout rlMain;
    @Bind(R.id.tv_give_friend)
    TextView tvGiveFriend;
    @Bind(R.id.tv_describe)
    TextView tvDescribe;
    @Bind(R.id.ll_remind)
    LinearLayout llRemind;
    @Bind(R.id.view_bottom)
    View viewBottom;
    @Bind(R.id.rl_top)
    RelativeLayout rlTop;
    @Bind(R.id.ll_wechat)
    LinearLayout llWechat;
    @Bind(R.id.ll_circle)
    LinearLayout llCircle;
    private Context context;
    private View rootView;

    private String miniPath, description, miniWebUrl;
    private byte[] miniPicData,circleData;

    public ShareCouponPop(final Context context) {
        this.context = context;
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_share_coupon_pop, null);
        ButterKnife.bind(this, rootView);
        this.setContentView(rootView);
        this.setWidth(ScreenUtil.screenWidth - ScreenUtil.dip2px(0));
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setTouchable(true);
        ColorDrawable cd = new ColorDrawable();
        this.setBackgroundDrawable(cd);
        this.setOutsideTouchable(true);
        this.update();
        setAnimationStyle(R.style.showPopupAnimation);
        tvRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转转赠优惠券规则
                Util.urlAction(context, "/page/couponsendrule");
            }
        });

        llCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //朋友圈
                WxHandle.getInstance(context).setTitle(description);
                WxHandle.getInstance(context).setDes(description);
                WxHandle.getInstance(context).setWebUrl(miniWebUrl);
                WxHandle.getInstance(context).sendShare(context, miniPicData, SendMessageToWX.Req.WXSceneTimeline);
                dismiss();
            }
        });

        llWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //微信好友
                WxHandle.getInstance(context).shareMiniProject(context, miniPath, description, miniPicData, miniWebUrl,false);
                dismiss();
            }
        });
    }

    public void setMiniPath(String miniPath) {
        this.miniPath = miniPath;
    }

    public void setCircleData(byte[] circleData){
        this.circleData=circleData;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMiniWebUrl(String miniWebUrl) {
        this.miniWebUrl = miniWebUrl;
    }

    public void setMiniPicData(byte[] miniPicData) {
        this.miniPicData = miniPicData;
    }

    public void setData(CouponsBean.DataEntity.MyCouponsEntity.ListEntity listEntity) {
        llRemind.setBackgroundResource(R.mipmap.bg_coupon_newmiddle);
        if (listEntity.getType().equals("DISCOUNT")) {
            rlMain.setBackgroundResource(R.mipmap.bg_coupon_newtop2);
            double amount = (double) listEntity.getAmount() / 10;
            //字体不一样大
            String str = context.getString(R.string.label_discount, Util.getNumberFormat(amount));
            int length = str.length();
            SpannableString textSpan = new SpannableString(str);
            textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(34)), 0, length - 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(18)), length - 1, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            tvPrice.setText(textSpan);
        } else {
            rlMain.setBackgroundResource(R.mipmap.bg_coupon_newtop);
            tvPrice.setText(String.valueOf(listEntity.getAmount()));
        }
        tvLimit.setText(String.format(context.getString(R.string.label_need_amount), listEntity.getNeedAmount()));
        if (listEntity.getPrice() != null) {
            StringBuilder stringBuilder = new StringBuilder();
            String text = "¥" + listEntity.getPrice() + "元购买";
            stringBuilder.append(text).append(listEntity.getName());
            SpannableString spannableString = new SpannableString(stringBuilder.toString());
            spannableString.setSpan(new CouponBgSpan(), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvTitle.setText(spannableString);
        } else {
            tvTitle.setText(listEntity.getName());
        }
        tvSignCanGive.setVisibility(View.VISIBLE);
        tvDate.setText(String.format(context.getString(R.string.label_pozhe), listEntity.getEnabledate(), listEntity.getExpiredate()));
        tvDescribe.setText(listEntity.getRemark());
    }

}
