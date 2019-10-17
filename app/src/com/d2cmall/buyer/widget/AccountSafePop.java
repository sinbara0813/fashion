package com.d2cmall.buyer.widget;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ChangeLoginActivity;
import com.d2cmall.buyer.activity.SetPayPasswordActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Dec: D2CNEW
 * Author: hrb
 * Date: 2018/5/24 09:56
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class AccountSafePop implements TransparentPop.InvokeListener {

    @Bind(R.id.content_tv)
    TextView contentTv;

    private View rootView;
    private TransparentPop pop;
    private int type;
    private Context mContext;

    public AccountSafePop(Context context) {
        mContext=context;
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_account_tip, null);
        ButterKnife.bind(this, rootView);
        pop = new TransparentPop(context,false,this);
        pop.setFocusable(false);
    }

    @OnClick({R.id.tv_sure,R.id.close_iv})
    public void click(View view){
        switch (view.getId()){
            case R.id.tv_sure:
                Intent intent;
                if (type==1){
                    intent = new Intent(mContext, ChangeLoginActivity.class);
                }else {
                    intent = new Intent(mContext, SetPayPasswordActivity.class);
                    intent.putExtra("type", 1);
                }
                mContext.startActivity(intent);
                if (pop!=null){
                    pop.dismiss(false);
                }
                break;
            case R.id.close_iv:
                if (pop!=null){
                    pop.dismiss(false);
                }
                break;
        }
    }

    public void setContent(String content,int type){
        contentTv.setText(content);
        this.type=type;
    }

    public void show(View view) {
        pop.show(view, false);
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.CENTER);
        v.addView(rootView);
    }

    @Override
    public void releaseView(LinearLayout v) {
        v.removeAllViews();
        rootView=null;
    }
}
