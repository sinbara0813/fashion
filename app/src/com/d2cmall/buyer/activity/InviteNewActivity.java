package com.d2cmall.buyer.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.NewPeopleCouponsApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.NewPeopleCouponsBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.shareplatform.WxHandle;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;

import java.io.ByteArrayOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

//邀请新人
public class InviteNewActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.ll_coupon)
    LinearLayout llCoupon;
    @Bind(R.id.btn_invite)
    ImageButton btnInvite;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private UserBean.DataEntity.MemberEntity user;
    public static final int IMAGE_SIZE = 32768;
    private float scale = 1.0F;
    private int totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_new);
        ButterKnife.bind(this);
        loadCouponList();
    }

    private void loadCouponList() {
        progressBar.setVisibility(View.VISIBLE);
        NewPeopleCouponsApi api = new NewPeopleCouponsApi();
        api.setGroupId(77);//id=77是新人有礼优惠券
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<NewPeopleCouponsBean>() {
            @Override
            public void onResponse(NewPeopleCouponsBean newPeopleCouponsBean) {
                if(isFinishing() || progressBar==null){
                    return;
                }
                progressBar.setVisibility(View.GONE);
                if (newPeopleCouponsBean == null || newPeopleCouponsBean.getData().getFixCoupons().getList().size() == 0) {
                    Util.showToast(InviteNewActivity.this, "优惠券组信息不存在");
                    return;
                }
                addCouponViews(newPeopleCouponsBean);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(InviteNewActivity.this, Util.checkErrorType(error));
                if(isFinishing() || progressBar==null){
                    return;
                }
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void addCouponViews(NewPeopleCouponsBean newPeopleCouponsBean) {
        int size = newPeopleCouponsBean.getData().getFixCoupons().getList().size();
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#C4A470"));
        for (int i = 0; i < size; i++) {
            NewPeopleCouponsBean.DataBean.FixCouponsBean.ListBean listBean = newPeopleCouponsBean.getData().getFixCoupons().getList().get(i);
            totalAmount += listBean.getAmount();
            View couponView = LayoutInflater.from(this).inflate(R.layout.layout_new_people_coupon_item, llCoupon, false);
            TextView tvCouponAmount = (TextView) couponView.findViewById(R.id.tv_coupon_amount);
            //字体大小不一样
            String str = "¥" + listBean.getAmount();
            int length = str.length();
            SpannableString textSpan = new SpannableString(str);
            textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(14)), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            textSpan.setSpan(colorSpan, 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            textSpan.setSpan(new StyleSpan(Typeface.NORMAL), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(24)), 1, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            textSpan.setSpan(new StyleSpan(Typeface.BOLD), 1, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE); //粗体
            tvCouponAmount.setText(textSpan);
            TextView tvCouponCondition = (TextView) couponView.findViewById(R.id.tv_coupon_condition);
            tvCouponCondition.setText(String.format(getString(R.string.label_need_amount), listBean.getNeedAmount()));
            TextView tvCouponType = (TextView) couponView.findViewById(R.id.tv_coupon_type);
            tvCouponType.setText("新人有礼优惠券");
            llCoupon.addView(couponView);
        }
    }

    @OnClick({R.id.back_iv, R.id.btn_invite})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.btn_invite:
                if (Util.loginChecked(this, 999)) {
                    invite();
                }
                break;
        }
    }

    private void invite() {
        progressBar.setVisibility(View.VISIBLE);
        if (user == null) {
            user = Session.getInstance().getUserFromFile(this);
        }
        Glide.with(this).load(Util.getD2cPicUrl("http://static.d2c.cn/other/invite_newuser_xcx.jpeg")).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(resource.getByteCount());
                resource.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                byte[] bytes = outputStream.toByteArray();
                while (bytes.length > IMAGE_SIZE) {
                    scale -= 0.1;
                    if (resource.getWidth() > 0 && resource.getHeight() > 0) {
                        Bitmap scaleBitmap = BitmapUtils.getScaleBitmap(resource, scale, scale);
                        bytes = BitmapUtils.getBitmapData(scaleBitmap);
                        scaleBitmap.recycle();
                    } else {
                        break;
                    }
                }
                if(progressBar!=null){
                    progressBar.setVisibility(View.GONE);
                }
                String nickName=Util.isEmpty(user.getNickname()) ? "匿名_" + user.getMemberId() : user.getNickname();
                String title = getString(R.string.label_invite_new_title,nickName, totalAmount);
                String paramsStr="";
                if(user!=null && user.getPartnerId()>0){
                    paramsStr="parent_id=" + user.getPartnerId() +"&name="+Util.toURLEncode(nickName)+"&avatar="+ Util.toURLEncode(Constants.IMG_HOST+user.getHead());
                }else{
                    paramsStr="name="+Util.toURLEncode(nickName)+"&avatar="+ Util.toURLEncode(Constants.IMG_HOST+user.getHead());
                }



                WxHandle.getInstance(InviteNewActivity.this).miniAppInviteNew(InviteNewActivity.this, bytes, title, title, paramsStr);

            }

        });
    }
}
