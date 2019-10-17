package com.d2cmall.buyer.widget;

import android.content.Context;
import android.view.View;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.Util;

public class BirthdayPop implements BottomPop.CallBack, BottomPop.SecondPickChangeListener, BottomPop.FirstPickChangeListener {
    Context mContext;
    BottomPop pop;
    String[] years;
    String[] months;
    String[] allDays;
    String[] days;
    String defaultYear;
    String defaultMonth;
    String defaultDay;
    int yearIndex;
    int monthIndex;
    int dayIndex;

    public BirthdayPop(Context context) {
        this.mContext = context;
        init();
    }

    public BirthdayPop(Context context,String year,String month,String day) {
        this.mContext = context;
        this.defaultYear=year;
        this.defaultMonth=month;
        this.defaultDay=day;
        init();
    }

    private void init() {
        String[][] values = new String[3][];
        years = mContext.getResources().getStringArray(R.array.years);
        months = mContext.getResources().getStringArray(R.array.months);
        allDays = mContext.getResources().getStringArray(R.array.days);
        values[0] = years;
        values[1] = months;
        values[2] = allDays;
        checkIndex();
        pop = new BottomPop(mContext, values,new int[]{yearIndex,monthIndex,dayIndex});
        pop.setCallBack(this);
        pop.setSecondChangeListener(this);
        pop.setFirstChangeListener(this);
    }

    private void checkIndex(){
        if (Util.isEmpty(defaultDay)&&Util.isEmpty(defaultMonth)&&Util.isEmpty(defaultDay)){
            return;
        }
        int year = Integer.valueOf(defaultYear);
        int month = Integer.valueOf(defaultMonth);
        int day=Integer.valueOf(defaultDay);
        if (month==2){
            if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)){
                days=new String[29];
                for (int i=0;i<29;i++){
                    days[i]=allDays[i];
                }
            }else {
                days=new String[28];
                for (int i=0;i<28;i++){
                    days[i]=allDays[i];
                }
            }
        }else {
            switch (month){
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    days=new String[31];
                    for (int i=0;i<31;i++){
                        days[i]=allDays[i];
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    days=new String[30];
                    for (int i=0;i<30;i++){
                        days[i]=allDays[i];
                    }
                    break;
            }
        }
        for (int i=0;i<years.length;i++){
            if (years[i].equals(defaultYear)){
                yearIndex=i;
                break;
            }
        }
        monthIndex=month-1;
        dayIndex=day-1;
    }

    public void show(View view, View res) {
        pop.show(view, res);
    }

    @Override
    public void callback(View trigger, int[] index, String[] value) {
        if (callBack != null) {
            callBack.callback(trigger, index, value);
        }
    }

    @Override
    public void onSecondValueChange(String oldStr, String newStr) {
        int month = Integer.valueOf(newStr);
        String[] values = allDays.clone();
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                String[] newStrs = new String[30];
                for (int i = 0; i < 30; i++) {
                    newStrs[i] = values[i];
                }
                values = newStrs;
                break;
            case 2:
                int year = getYear();
                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {//闰年
                    String[] newStr1 = new String[29];
                    for (int i = 0; i < 29; i++) {
                        newStr1[i] = values[i];
                    }
                    values = newStr1;
                } else {
                    String[] newStr2 = new String[28];
                    for (int i = 0; i < 28; i++) {
                        newStr2[i] = values[i];
                    }
                    values = newStr2;
                }
                break;
        }
        pop.setThirdValue(values);
    }

    private int getYear() {
        String year = pop.getCurrentFirstValue();
        return Integer.valueOf(year);
    }

    private int getMonth() {
        String month = pop.getCurrentSecondValue();
        return Integer.valueOf(month);
    }

    @Override
    public void onFirstValueChange(String oldStr, String newStr) {
        int year = getYear();
        int oldYear = Integer.valueOf(oldStr);
        int month = getMonth();
        if (((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) && month == 2) {
            String[] values = allDays.clone();
            String[] newStr1 = new String[29];
            for (int i = 0; i < 29; i++) {
                newStr1[i] = values[i];
            }
            values = newStr1;
            pop.setThirdValue(values);
        } else if (((oldYear % 4 == 0 && oldYear % 100 != 0) || (oldYear % 400 == 0)) && month == 2) {
            String[] values = allDays.clone();
            String[] newStr2 = new String[28];
            for (int i = 0; i < 28; i++) {
                newStr2[i] = values[i];
            }
            values = newStr2;
            pop.setThirdValue(values);
        }
    }

    public interface CallBack {
        void callback(View trigger, int[] index, String[] value);
    }

    private CallBack callBack;

    public void setCallBack(CallBack callback) {
        this.callBack = callback;
    }
}
