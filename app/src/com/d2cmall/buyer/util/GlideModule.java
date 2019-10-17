package com.d2cmall.buyer.util;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;

import java.io.InputStream;

/**
 * 设置Glide 加载参数
 * Author: YH
 * Date: 2017/07/06 11:13
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class GlideModule implements com.bumptech.glide.module.GlideModule{

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        /*MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();
        Log.e("han",defaultBitmapPoolSize+"---"+defaultMemoryCacheSize);

        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));*/

        //builder.setMemoryCache(new LruResourceCache(8*1024*1024));
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, 50*1024*1024));
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        builder.setDecodeFormat(activityManager.isLowRamDevice() ?
                DecodeFormat.PREFER_RGB_565 : DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        ModelLoaderFactory factory=new ModelLoaderFactory() {
            @Override
            public ModelLoader build(Context context, GenericLoaderFactory factories) {
                return new GlideModelLoader(context);
            }

            @Override
            public void teardown() {

            }
        };
        glide.register(String.class, InputStream.class,factory);
    }
}
