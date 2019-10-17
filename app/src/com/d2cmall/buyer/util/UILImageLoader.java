package com.d2cmall.buyer.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.qiyukf.unicorn.api.ImageLoaderListener;
import com.qiyukf.unicorn.api.UnicornImageLoader;

import java.io.File;

/**
 * UILImageLoader
 * Author: Blend
 * Date: 2016/10/25 11:17
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class UILImageLoader implements UnicornImageLoader {
    private Context context;
    public UILImageLoader(Context context){
        this.context=context;
    }
    @Override
    public Bitmap loadImageSync(String uri, int width, int height) {
        return null;
    }

    @Override
    public void loadImage(String uri, int width, int height, final ImageLoaderListener listener) {
        if (uri.startsWith("file://")){
            uri=uri.substring(7,uri.length());
            Glide.with(context).load(Uri.fromFile(new File(uri))).asBitmap().into(new SimpleTarget<Bitmap>(width, height) {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    if (listener != null) {
                        listener.onLoadComplete(resource);
                    }
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    if (listener != null) {
                        listener.onLoadFailed(e);
                    }
                }
            });
        }else {
            SimpleTarget target=null;
            if (width>0||height>0){
                target=new SimpleTarget<Bitmap>(width, height) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super
                            Bitmap> glideAnimation) {
                        if (listener != null) {
                            listener.onLoadComplete(resource);
                        }
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        if (listener != null) {
                            listener.onLoadFailed(e);
                        }
                    }
                };
            }else {
                target=new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super
                            Bitmap> glideAnimation) {
                        if (listener != null) {
                            listener.onLoadComplete(resource);
                        }
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        if (listener != null) {
                            listener.onLoadFailed(e);
                        }
                    }
                };
            }
            Glide.with(context).load(uri).asBitmap().into(target);
        }
    }
}

