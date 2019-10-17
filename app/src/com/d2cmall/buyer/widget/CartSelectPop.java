package com.d2cmall.buyer.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Dec: D2CNEW
 * Author: hrb
 * Date: 2018/6/11 10:46
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class CartSelectPop implements TransparentPop.InvokeListener {

    @Bind(R.id.global_check)
    CheckBox globalCheck;
    @Bind(R.id.common_check)
    CheckBox commonCheck;
    @Bind(R.id.cancel_tv)
    TextView cancelTv;
    @Bind(R.id.sure_tv)
    TextView sureTv;
    @Bind(R.id.global_check_ll)
    LinearLayout globalCheckLl;
    @Bind(R.id.common_check_ll)
    LinearLayout commonCheckLl;
    @Bind(R.id.global_tv)
    TextView globalTv;
    @Bind(R.id.common_tv)
    TextView commonTv;
    private TransparentPop mPop;
    private Context mContext;
    private View rootView;
    private boolean globalChecked;
    private boolean commonChecked;
    private SelectListener selectListener;
    private int globalSize;
    private int commonSize;

    public CartSelectPop(Context context, int globalSize, int commonSize, SelectListener listener) {
        this.mContext = context;
        this.globalSize = globalSize;
        this.commonSize = commonSize;
        this.selectListener = listener;
        init();
    }

    private void init() {
        rootView = LayoutInflater.from(mContext).inflate(R.layout.layout_cart_select, new LinearLayout(mContext), false);
        ButterKnife.bind(this, rootView);
        mPop = new TransparentPop(mContext, false, this);
        globalCheck.setBackgroundResource(R.mipmap.icon_shopcart_bselected, R.mipmap.icon_shopcart_unbselected);
        commonCheck.setBackgroundResource(R.mipmap.icon_shopcart_bselected, R.mipmap.icon_shopcart_unbselected);
        globalCheck.setEnabled(false);
        commonCheck.setEnabled(false);
        globalTv.setText("D2C全球购商品 "+"("+globalSize+"件)");
        commonTv.setText("其他商品 "+"("+commonSize+"件)");
        commonCheck.setChecked(true);
        commonChecked=true;
    }

    @OnClick({R.id.global_check_ll, R.id.common_check_ll, R.id.cancel_tv, R.id.sure_tv})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.global_check_ll:
                globalChecked = !globalChecked;
                globalCheck.setChecked(globalChecked);
                commonChecked = false;
                commonCheck.setChecked(commonChecked);
                break;
            case R.id.common_check_ll:
                commonChecked = !commonChecked;
                commonCheck.setChecked(commonChecked);
                globalChecked = false;
                globalCheck.setChecked(globalChecked);
                break;
            case R.id.cancel_tv:
                dismiss();
                break;
            case R.id.sure_tv:
                if (selectListener == null) {
                    return;
                }
                if (globalChecked) {
                    selectListener.selectGlobal();
                    dismiss();
                } else if (commonChecked) {
                    selectListener.selectCommon();
                    dismiss();
                }
                break;
        }
    }

    public void show(View parent) {
        mPop.show(parent, false);
    }

    public void dismiss() {
        if (mPop != null) {
            mPop.dismiss(false);
        }
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.CENTER);
        v.addView(rootView);
    }

    @Override
    public void releaseView(LinearLayout v) {
        v.removeAllViews();
        rootView = null;
    }

    public interface SelectListener {
        void selectGlobal();

        void selectCommon();
    }
}
