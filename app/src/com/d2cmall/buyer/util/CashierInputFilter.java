package com.d2cmall.buyer.util;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 金额格式
 * Author: Blend
 * Date: 2016/08/26 21:37
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class CashierInputFilter implements InputFilter {

    Pattern mPattern1= Pattern.compile("([0-9]|\\.)*");
    private static final int MAX_VALUE = Integer.MAX_VALUE;
    //小数点后的位数
    private static final int POINTER_LENGTH = 2;
    private static final String POINTER = ".";
    private static final String ZERO = "0";

    public CashierInputFilter() {}

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String sourceText = source.toString();
        String destText = dest.toString();

        //验证删除等按键
        if (Util.isEmpty(sourceText)) {
            return "";
        }

        Matcher matcher = mPattern1.matcher(source);
        //已经输入小数点的情况下，只能输入数字
        if (destText.contains(POINTER)) {
            if (!matcher.matches()) {
                return "";
            } else {
                if (POINTER.equals(source)) {  //只能输入一个小数点
                    return "";
                }
            }

            //验证小数点精度，保证小数点后只能输入两位
            int index = destText.indexOf(POINTER);
            int length = dend - index;

            if (length > POINTER_LENGTH) {
                return dest.subSequence(dstart, dend);
            }
        } else {
            //没有输入小数点的情况下，只能输入小数点和数字，但首位不能输入小数点
            if (!matcher.matches()) {
                return "";
            } else {
                if (POINTER.equals(source) && Util.isEmpty(destText)) {
                    return "";
                }
            }
        }

        //验证输入金额的大小
        double sumText = Double.parseDouble(destText + sourceText);
        if (sumText > MAX_VALUE) {
            return dest.subSequence(dstart, dend);
        }

        return dest.subSequence(dstart, dend) + sourceText;
    }
}
