package com.d2cmall.buyer;

import android.app.Application;
import android.content.Context;

import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.SrictModeUtil;
import com.d2cmall.buyer.util.Util;

/**
 * Created by zhangzz on 2017/5/8.
 *
 */

public class AppProfile {
    private static Context context;

    public void onCreate(Application context) {
        this.context = context;
        ScreenUtil.GetInfo(context);
        Util.getPageVersionName(context);
        if (BuildConfig.DEBUG) {
            SrictModeUtil.startStrictMode();
        }
    }

    public static final Context getContext() {
        return context;
    }
}
