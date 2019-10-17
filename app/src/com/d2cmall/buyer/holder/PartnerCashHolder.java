package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fixme
 * Author: LWJ
 * desc:    商品报告holder
 * Date: 2017/10/12 14:43
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class PartnerCashHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.iv_account_type)
        public  ImageView ivAccountType;
        @Bind(R.id.tv_account_type)
        public  TextView tvAccountType;
        @Bind(R.id.tv_account_code)
        public  TextView tvAccountCode;
        @Bind(R.id.tv_account_date)
        public  TextView tvAccountDate;
        @Bind(R.id.tv_account_money)
        public  TextView tvAccountMoney;
        @Bind(R.id.tv_pay_time)
        public  TextView tvPayTime;
        @Bind(R.id.tv_pay_code)
        public  TextView tvPayCode;
        @Bind(R.id.tv_pay_account)
        public  TextView tvPayAccount;
        @Bind(R.id.ll_bottom)
        public  LinearLayout llBottom;
        @Bind(R.id.line_layout)
        public View lineLayout;
        @Bind(R.id.tv_refuse_reason)
        public TextView tvRefuseReason;
        @Bind(R.id.ll_refuse_reason)
        public LinearLayout llRefuseReason;
        @Bind(R.id.ll_tax)
        public LinearLayout llTax;
        @Bind(R.id.tv_apply_amount)
        public  TextView tvApplyAmount;
        @Bind(R.id.tv_tax_amount)
        public  TextView tvTaxAmount;
        @Bind(R.id.tv_pay_type)
        public  TextView tvPayType;

        public PartnerCashHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
}
