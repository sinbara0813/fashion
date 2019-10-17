package com.d2cmall.buyer.util;

import android.net.Uri;

/**
 * Name: VideoEdit-master
 * Anthor: hrb
 * Date: 2017/7/7 16:31
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public interface OnTrimVideoListener {

    void onTrimStarted();

    void getResult(final Uri uri);

    void cancelAction();

    void onError(final String message);
}
