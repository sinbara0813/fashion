package com.d2cmall.buyer.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.Util;

/**
 * 提现方式pop
 * Date: 2016/11/05 12:59
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class CashTypePop implements TransparentPop.InvokeListener {
    public TextView mTvBank;
    private Context context;
    private TransparentPop mPop;
    private View rootView;
    private ImageView mivDismiss;
    private LinearLayout llWallte;

    public void setChoseTypeListener(ChoseTypeListener choseTypeListener) {
        this.choseTypeListener = choseTypeListener;
    }

    private ChoseTypeListener choseTypeListener;

    public CashTypePop(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_cash_type_pop, new RelativeLayout(context), false);
        mPop = new TransparentPop(context, this);
        mPop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //mPop.setBackGroundResource(R.color.transparent);
        mPop.setFocusable(true);
        mPop.setOutsideTouchable(true);

        mPop.dismissFromOut();
        mTvBank = (TextView) rootView.findViewById(R.id.tv_bank);
        mivDismiss= (ImageView) rootView.findViewById(R.id.iv_close);
        llWallte = (LinearLayout) rootView.findViewById(R.id.ll_wallet);
        mTvBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choseTypeListener!=null){
                    choseTypeListener.choseType(1);
                }
                dismiss();
            }
        });
        llWallte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choseTypeListener!=null){
                    choseTypeListener.choseType(2);
                }
                dismiss();
            }
        });
        mivDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        v.setGravity(Gravity.BOTTOM);
        v.addView(rootView);
    }

    public void setBankCode(String code){
        if(Util.isEmpty(code)){
            mTvBank.setText("银行卡");
        }else{
            mTvBank.setText(code);
        }

    }

    @Override
    public void releaseView(LinearLayout v) {

    }
    public interface ChoseTypeListener{
        void choseType(int type);//1是银行,2是钱包
    }
    public void setDismissListener(TransparentPop.DismissListener dismissListener) {
        mPop.setDismissListener(dismissListener);
    }
}
