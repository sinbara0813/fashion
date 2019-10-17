package com.d2cmall.buyer.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.d2cmall.buyer.BuildConfig;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.loopview.LoopView;
import com.d2cmall.buyer.widget.loopview.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 预约时间选择
 * Author: hrb
 * Date: 2017/03/16 11:29
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class SelectTimePop implements TransparentPop.InvokeListener {

    @Bind(R.id.close_btn)
    ImageView closeBtn;
    @Bind(R.id.select_hour)
    LoopView selectHour;
    @Bind(R.id.select_minute)
    LoopView selectMinute;
    @Bind(R.id.select_month)
    LoopView selectMonth;
    @Bind(R.id.select_day)
    LoopView selectDay;
    private Context mContext;
    private View bottomView;
    private TransparentPop mPop;
    private Animation inAnimation;
    private Animation outAnimation;
    private String dateStr;
    String[] allDays;
    ArrayList<String> months;
    ArrayList<String> days;
    ArrayList<String> hours;
    ArrayList<String> minutes;
    public int currentYear;
    public int currentMonth;
    public int currentDay;
    public int month = 1;
    public int day = 1;
    public int hour;
    public int minute;
    public boolean isVal;
    public boolean isMonthEnd;
    public int lastDayIndex;

    public SelectTimePop(Context context) {
        this.mContext = context;
        init();
    }

    private void init() {
        bottomView = LayoutInflater.from(mContext).inflate(R.layout.selert_time_pop, new LinearLayout(mContext), false);
        ButterKnife.bind(this, bottomView);
        mPop = new TransparentPop(mContext, this);
        inAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_up);
        outAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_down);
        mPop.setInAnimation(inAnimation);
        mPop.setOutAnimation(outAnimation);
        mPop.dismissFromOut();
        initListener();
        initData();
    }

    private void initData() {
        months = new ArrayList<>();
        days = new ArrayList<>();
        hours = new ArrayList<>();
        minutes = new ArrayList<>();
        allDays = mContext.getResources().getStringArray(R.array.days);
        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH) + 1;
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= 12; i++) {
            months.add(i + "月");
        }
        getDay(currentMonth);
        for (int i = 0; i < 24; i++) {
            hours.add(i + "");
        }
        for (int i = 0; i < 60; i++) {
            minutes.add(i + "");
        }
        selectMonth.setItems(months);
        selectMonth.setCurrentPosition(currentMonth - 1);
        month = currentMonth;
        selectDay.setItems(days);
        selectDay.setCurrentPosition(currentDay - 1);
        day = currentDay;
        lastDayIndex = currentDay - 1;
        selectHour.setItems(hours);
        selectMinute.setItems(minutes);
    }

    private void getDay(int month) {
        int count = 0;
        if (month == 2) {
            if ((currentYear % 4 == 0 && currentYear % 100 != 0) || (currentYear % 400 == 0)) {
                count = 29;
            } else {
                count = 28;
            }
        } else {
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    count = 31;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    count = 30;
                    break;
            }
        }
        days.clear();
        for (int i = 0; i < count; i++) {
            days.add(allDays[i] + "日");
        }
    }

    private void initListener() {
        selectMonth.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                month = Integer.valueOf(months.get(index).substring(0, months.get(index).length() - 1));
                getDay(month);
                selectDay.setItems(days);
                if (lastDayIndex + 1 >= days.size()) {
                    lastDayIndex = days.size() - 1;
                }
                if (lastDayIndex != 0) {
                    day = Integer.valueOf(days.get(lastDayIndex).substring(0, days.get(lastDayIndex).length() - 1));
                }
                selectDay.setCurrentPosition(lastDayIndex);
            }
        });

        selectDay.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                lastDayIndex = index;
                day = Integer.valueOf(days.get(index).substring(0, days.get(index).length() - 1));
            }
        });

        selectHour.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                hour = Integer.valueOf(hours.get(index));
            }
        });

        selectMinute.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                minute = Integer.valueOf(minutes.get(index));
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
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
        v.addView(bottomView);
    }

    @Override
    public void releaseView(LinearLayout v) {

    }

    public void setDismissCallBack(TransparentPop.DismissListener dismissListener) {
        mPop.setDismissListener(dismissListener);
    }

    public String getDateStr() {
        if (!BuildConfig.DEBUG) {
            if (month < currentMonth || month == currentMonth && day <= currentDay) {
                Util.showToast(mContext, "预约时间最早是次日零点!");
                isVal = false;
                return getNextDay(currentMonth, currentDay) + addZero(0) + ":" + addZero(0);
            }
        }
        isVal = true;
        return month + "月" + day + "日 " + addZero(hour) + ":" + addZero(minute);
    }

    private String getNextDay(int month, int day) {
        int count = 0;
        if (month == 2) {
            if ((currentYear % 4 == 0 && currentYear % 100 != 0) || (currentYear % 400 == 0)) {
                count = 29;
            } else {
                count = 28;
            }
        } else {
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    count = 31;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    count = 30;
                    break;
            }
        }
        if (day + 1 > count) { //现在是月底最后一天
            isMonthEnd = true;
            return (currentMonth + 1) + "月" + 1 + "日";
        } else {
            isMonthEnd = false;
            return currentMonth + "月" + (currentDay + 1) + "日";
        }
    }

    private String addZero(int number) {
        if (number < 10) {
            return "0" + number;
        } else {
            return number + "";
        }
    }
}
