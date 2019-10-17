package com.d2cmall.buyer.listener;

/**
 * Created by rookie on 2018/5/17.
 */

public interface ProgressCallBack {
    void setProgress(int progress,boolean isShare);
    void letProgressGone();
    void letProgressVisible();
}
