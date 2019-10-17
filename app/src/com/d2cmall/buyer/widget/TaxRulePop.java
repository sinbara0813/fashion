package com.d2cmall.buyer.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

/**
 * 买手提现税费规则
 * Copyright (c) 2018 d2c. All rights reserved.
 */
public class TaxRulePop implements TransparentPop.InvokeListener {
    private ImageView ivClose;
    private View viewTypeBank;
    private  LinearLayout llTypeBank;
    private TextView tvBankType;
    private TextView tvWalletType;
    private View viewTypeWallet;
    private  LinearLayout llTypeWallet;
    private TextView tv17000;
    private TextView tvRule17000;
    private LinearLayout ll17000;
    private TextView tv30000;
    private  TextView tvRule30000;
    private LinearLayout ll30000;
    private  LinearLayout ll40000;
    private LinearLayout ll60000;
    private TextView tvMore;
    private TextView tvMoreRule;
    private  LinearLayout llMore;
    private LinearLayout llTaxRule;
    private Context context;
    private TransparentPop mPop;
    private View rootView;
    private View line30000;
    private View line40000;
    private View line60000;
    private TextView tvBankTaxTip;

    public TaxRulePop(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_tax_rule_pop, new RelativeLayout(context), false);
        mPop = new TransparentPop(context, this);
        mPop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //mPop.setBackGroundResource(R.color.transparent);
        mPop.setFocusable(true);
        mPop.setOutsideTouchable(true);
        Animation inAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_up);
        inAnimation.setInterpolator(new DecelerateInterpolator());
        Animation outAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_down);
        outAnimation.setInterpolator(new AccelerateInterpolator());
        mPop.setInAnimation(inAnimation);
        mPop.setOutAnimation(outAnimation);
        mPop.dismissFromOut();
        llTaxRule=rootView.findViewById(R.id.ll_tax_rule);
        ivClose=rootView.findViewById(R.id.iv_close);
        viewTypeBank=rootView.findViewById(R.id.view_type_bank);
        llTypeBank=rootView.findViewById(R.id.ll_type_bank);
        viewTypeWallet=rootView.findViewById(R.id.view_type_wallet);
        llTypeWallet=rootView.findViewById(R.id.ll_type_wallet);
        tvBankType=rootView.findViewById(R.id.tv_bank_type);
        tvWalletType=rootView.findViewById(R.id.tv_wallet_type);
        tvBankTaxTip=rootView.findViewById(R.id.tv_bank_tax_tip);
        llTaxRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //空方法拦截
            }
        });
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        llTypeBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        llTypeWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提现到钱包的扣税规则,先注释
//                tvWalletType.setTextColor(Color.parseColor("#DA000000"));
//                tvWalletType.setTypeface(null, Typeface.BOLD);
//                tvBankType.setTypeface(null, Typeface.NORMAL);
//                tvBankType.setTextColor(Color.parseColor("#80000000"));
//                tvBankTaxTip.setVisibility(View.GONE);
//                ll30000.setVisibility(View.VISIBLE);
//                ll40000.setVisibility(View.VISIBLE);
//                ll60000.setVisibility(View.VISIBLE);
//                line30000.setVisibility(View.VISIBLE);
//                line40000.setVisibility(View.VISIBLE);
//                line60000.setVisibility(View.VISIBLE);
//                ll17000.setVisibility(View.VISIBLE);
//                tv17000.setText(context.getString(R.string.tax_level_17000));
//                tvMore.setText(context.getString(R.string.tax_level_more_85000));
//                tvMoreRule.setText(context.getString(R.string.tax_count_rule_more_85000));
//                viewTypeBank.setVisibility(View.GONE);
//                viewTypeWallet.setVisibility(View.VISIBLE);
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
