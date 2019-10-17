package com.d2cmall.buyer.widget.calendarview.listener;

import android.view.View;

import com.d2cmall.buyer.widget.calendarview.bean.DateBean;


/**
 * 日期点击接口
 */
public interface OnSingleChooseListener {
    /**
     * @param view
     * @param date
     */
    void onSingleChoose(View view, DateBean date);
}
