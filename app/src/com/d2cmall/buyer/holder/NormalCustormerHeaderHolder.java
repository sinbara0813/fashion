package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.RoundedImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/19.
 * Description : NormalCustormerHeaderHolder
 */

public class NormalCustormerHeaderHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_buyer_head)
        public  RoundedImageView ivBuyerHead;
        @Bind(R.id.tv_buyer_name)
        public  TextView tvBuyerName;
        @Bind(R.id.tv_buyer_code)
        public TextView tvBuyerCode;
        @Bind(R.id.tv_enter_date)
        public TextView tvEnterDate;
        @Bind(R.id.tv_income_money)
        public  TextView tvIncomeMoney;

        public NormalCustormerHeaderHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
}
