package com.d2cmall.buyer.widget;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.DCoinRecordConversionActivity;
import com.d2cmall.buyer.activity.MyCouponsActivity;
import com.d2cmall.buyer.bean.DCionProductBean;
import com.d2cmall.buyer.util.Util;

/**
 * 积分商品兑换成功pop
 * Date: 2016/11/05 12:59
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class ConversionSuccessPop implements TransparentPop.InvokeListener {
    private Context context;
    private TextView mTvDesc;
    private TextView mTvCode;
    private TextView mTvCopy;
    private TextView mTvLook;
    private TransparentPop mPop;
    private ImageView ivDsmiss;
    private View rootView;
    private DCionProductBean dCionProductBean;

    public ConversionSuccessPop(Context context, DCionProductBean dCionProductBean) {
        this.context = context;
        this.dCionProductBean=dCionProductBean;
        init();
    }

    private void init() {
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_conversion_success_pop, new RelativeLayout(context), false);
        mPop = new TransparentPop(context, this);
        mPop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //mPop.setBackGroundResource(R.color.transparent);
        mPop.setFocusable(true);
        mPop.setOutsideTouchable(true);

        mPop.dismissFromOut();
        mTvDesc= (TextView) rootView.findViewById(R.id.tv_desc);
        mTvCode= (TextView) rootView.findViewById(R.id.tv_code);
        mTvCopy= (TextView) rootView.findViewById(R.id.tv_copy);
        mTvLook= (TextView) rootView.findViewById(R.id.tv_look);
        ivDsmiss= (ImageView) rootView.findViewById(R.id.iv_dismiss);

        if(dCionProductBean==null){
            return;
        }
        if("COUPON".equals(dCionProductBean.getData().getPointProduct().getType())){
            mTvDesc.setText("可到我的优惠券列表查看");
            mTvLook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context,MyCouponsActivity.class));
                }
            });
        }else{
            mTvDesc.setText("可到我的兑换记录列表查看");
            mTvLook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context,DCoinRecordConversionActivity.class));
                }
            });
        }
        if(dCionProductBean.getData().getCard()!=null && !Util.isEmpty(dCionProductBean.getData().getCard().getCode())){
            mTvCode.setVisibility(View.VISIBLE);
            mTvCode.setText(dCionProductBean.getData().getCard().getCode());
            mTvCopy.setVisibility(View.VISIBLE);
            mTvCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(ClipData.newPlainText("code", dCionProductBean.getData().getCard().getCode()));
                    Util.showToast(context, "复制成功");
                }
            });
        }else{
            mTvCode.setVisibility(View.INVISIBLE);
            mTvCopy.setVisibility(View.INVISIBLE);
        }
        ivDsmiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //空方法拦截事件

            }
        });
    }

    public void show(View parent) {
        mPop.show(parent, true);
    }
    public void dismiss() {
        if (mPop != null) {
            mPop.dismiss(true);
        }
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.CENTER);
        v.addView(rootView);
    }


    @Override
    public void releaseView(LinearLayout v) {

    }
    public void setDismissListener(TransparentPop.DismissListener dismissListener) {
        mPop.setDismissListener(dismissListener);
    }
}
