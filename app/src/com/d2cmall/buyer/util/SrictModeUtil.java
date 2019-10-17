package com.d2cmall.buyer.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;

/**
 * Created by zhangzz on 2017/5/8.
 */

public class SrictModeUtil {
    public static final void startStrictMode() {
        if (Build.VERSION.SDK_INT > 9) {
            startThreadStrictMode();
            startVmStrictMode();
        }
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private static final void startThreadStrictMode() {

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().permitDiskWrites().permitDiskReads().penaltyLog().penaltyDialog().build());

    }


    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private static final void startVmStrictMode() {

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());

    }
}
