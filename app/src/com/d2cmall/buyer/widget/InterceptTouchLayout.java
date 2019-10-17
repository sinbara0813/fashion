package com.d2cmall.buyer.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.LoginActivity;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.util.Session;

/**
 * 拦截点击事件的layout
 * Author: Blend
 * Date: 2016/12/26 16:20
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class InterceptTouchLayout extends RelativeLayout {

    private Context context;
    private OnSwitchFragmentListener onSwitchFragmentListener;

    public InterceptTouchLayout(Context context) {
        super(context);
    }

    public InterceptTouchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InterceptTouchLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(context);
                if (user == null) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    ((Activity) context).startActivityForResult(intent, Constants.Login.BUYCAR_LOGIN);
                    ((Activity) context).overridePendingTransition(R.anim.slide_in_up, R.anim.activity_anim_default);
                    return true;
                } else {
                    if (onSwitchFragmentListener != null) {
                        onSwitchFragmentListener.onSwitchFragment();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                return true;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    public interface OnSwitchFragmentListener {
        void onSwitchFragment();
    }

    public void setIfLoginListener(Context context, OnSwitchFragmentListener onSwitchFragmentListener) {
        this.context = context;
        this.onSwitchFragmentListener = onSwitchFragmentListener;
    }
}
