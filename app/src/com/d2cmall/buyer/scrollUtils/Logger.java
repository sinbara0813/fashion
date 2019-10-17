package com.d2cmall.buyer.scrollUtils;

import android.util.Log;

/**
 * Name: nice
 * Anthor: hrb
 * Date: 2018/1/31 10:54
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class Logger {
    public Logger() {
    }

    public static int err(String TAG, String message) {
        return Log.e(TAG, attachThreadId(message));
    }

    public static int err(String TAG, String message, Throwable throwable) {
        return Log.e(TAG, attachThreadId(message), throwable);
    }

    public static int w(String TAG, String message) {
        return Log.w(TAG, attachThreadId(message));
    }

    public static int inf(String TAG, String message) {
        return Log.i(TAG, attachThreadId(message));
    }

    public static int d(String TAG, String message) {
        return Log.d(TAG, attachThreadId(message));
    }

    public static int v(String TAG, String message) {
        return Log.v(TAG, attachThreadId(message));
    }

    private static String attachThreadId(String str) {
        return "" + Thread.currentThread().getId() + " " + str;
    }
}
