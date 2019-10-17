package com.d2cmall.buyer.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.d2cmall.buyer.Constants;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class UniversalImageLoader {

    public static void displayImage(Context context, String imageUrl, ImageView imageView) {
        displayImage(context, imageUrl, imageView, 0, 0, null,0);
    }

    public static void displayImage(Fragment context, String imageUrl, ImageView imageView) {
        displayImage(context, imageUrl, imageView, 0, 0, null,0);
    }

    public static void displayImage(Context context, String url, ImageView imageView, int loadingResId, int failOrEmptyResId) {
        displayImage(context, url, imageView, loadingResId, failOrEmptyResId, null,0);
    }

    public static void displayImage(Fragment context, String url, ImageView imageView, int loadingResId, int failOrEmptyResId) {
        displayImage(context, url, imageView, loadingResId, failOrEmptyResId, null,0);
    }

    public static void  displayImage(Context context, String url, ImageView imageView
            , RequestListener requestListener) {
        displayImage(context, url, imageView, 0, 0, requestListener,0);
    }

    public static void  displayImage(Fragment context, String url, ImageView imageView
            , RequestListener requestListener) {
        displayImage(context, url, imageView, 0, 0, requestListener,0);
    }

    public static void displayImage(Context context, String url, ImageView imageView, int failOrEmptyResId) {
        displayImage(context, url, imageView, 0, failOrEmptyResId, null,0);
    }

    public static void displayImage(Fragment context, String url, ImageView imageView, int failOrEmptyResId) {
        displayImage(context, url, imageView, 0, failOrEmptyResId, null,0);
    }

    public static void displayImage(Context context, String url, ImageView imageView, int loadingResId, int failOrEmptyResId
            , RequestListener requestListener,int repeatCount) {
        if (imageView == null ) {
            return;
        }
        if (requestListener != null) {
            if (isGif(url)){
                if (repeatCount>0){
                    Glide.with(context).load(Util.getD2cPicUrl(url)).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(loadingResId)
                            .error(failOrEmptyResId).into(new GlideDrawableImageViewTarget(imageView, 1));
                }else {
                    Glide.with(context)
                            .load(Util.getD2cPicUrl(url))
                            .asGif()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .placeholder(loadingResId)
                            .error(failOrEmptyResId)
                            .listener(requestListener)
                            .into(imageView);
                }
            }else {
                Glide.with(context)
                        .load(Util.getD2cPicUrl(url))
                        .placeholder(loadingResId)
                        .crossFade()
                        .error(failOrEmptyResId)
                        .listener(requestListener)
                        .into(imageView);
            }
        } else {
            if (isGif(url)){
                if (repeatCount>0){
                    Glide.with(context).load(Util.getD2cPicUrl(url)).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(loadingResId)
                            .error(failOrEmptyResId).into(new GlideDrawableImageViewTarget(imageView, 1));
                }else {
                    Glide.with(context)
                            .load(Util.getD2cPicUrl(url))
                            .asGif()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE )
                            .placeholder(loadingResId)
                            .error(failOrEmptyResId)
                            .into(imageView);
                }
            }else {
                Glide.with(context)
                        .load(Util.getD2cPicUrl(url))
                        .placeholder(loadingResId)
                        .crossFade()
                        .error(failOrEmptyResId)
                        .into(imageView);
            }
        }
    }

    public static void displayImage(Fragment context, String url, ImageView imageView, int loadingResId, int failOrEmptyResId
            , RequestListener requestListener, int repeatCount) {
        if (imageView == null ) {
            return;
        }
        if (requestListener != null) {
            if (isGif(url)){
                if (repeatCount>0){
                    Glide.with(context).load(Util.getD2cPicUrl(url)).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(loadingResId)
                            .error(failOrEmptyResId).into(new GlideDrawableImageViewTarget(imageView, 1));
                }else {
                    Glide.with(context)
                            .load(Util.getD2cPicUrl(url))
                            .asGif()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .placeholder(loadingResId)
                            .error(failOrEmptyResId)
                            .listener(requestListener)
                            .into(imageView);
                }
            }else {
                Glide.with(context)
                        .load(Util.getD2cPicUrl(url))
                        .placeholder(loadingResId)
                        .crossFade()
                        .error(failOrEmptyResId)
                        .listener(requestListener)
                        .into(imageView);
            }
        } else {
            if (isGif(url)){
                if (repeatCount>0){
                    Glide.with(context).load(Util.getD2cPicUrl(url)).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(loadingResId)
                            .error(failOrEmptyResId).into(new GlideDrawableImageViewTarget(imageView, 1));
                }else {
                    Glide.with(context)
                            .load(Util.getD2cPicUrl(url))
                            .asGif()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE )
                            .placeholder(loadingResId)
                            .error(failOrEmptyResId)
                            .into(imageView);
                }
            }else {
                Glide.with(context)
                        .load(Util.getD2cPicUrl(url))
                        .placeholder(loadingResId)
                        .crossFade()
                        .error(failOrEmptyResId)
                        .into(imageView);
            }
        }
    }

    public static void displayRoundImage(Context context,String url,ImageView imageView,int error){
        url=Util.getD2cPicUrl(url);
        Glide.with(context).load(url).bitmapTransform(new CropCircleTransformation(context)).error(error).crossFade().into(imageView);
    }

    public static void displayRoundedCornerImage(Context context,String url,ImageView imageView,int radius,int margin,int error){
        url=Util.getD2cPicUrl(url);
        Glide.with(context).load(url).bitmapTransform(new RoundedCornersTransformation(context,radius,margin)).error(error).crossFade().into(imageView);
    }

    public static void displayBlurImage(Context context,String url,ImageView imageView,int radius){
        url=Util.getD2cPicUrl(url);
        /*RequestOptions requestOptions=new RequestOptions();
        requestOptions.transform(new BlurTransformation(context,radius));
        TransitionOptions transitionOptions=new DrawableTransitionOptions().crossFade();*/
        Glide.with(context).load(url).bitmapTransform(new BlurTransformation(context,radius)).crossFade().into(imageView);
    }

    public static void displayImageByPb(Context context, String url,int loadingId, final ImageView imageView, final ProgressBar progressBar){
        url=url+Constants.MY_SUFFIX;
        Glide.with(context).load(url).asBitmap().placeholder(loadingId).error(loadingId).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                progressBar.setVisibility(View.GONE);
                imageView.setImageBitmap(resource);
            }
        });
    }

    private static boolean isGif(String url) {
        if (Util.isEmpty(url)){
            return false;
        }
        boolean is = false;
        int index = url.lastIndexOf(".");
        int length = url.length();
        if (index > 0&&(index+4)<=length) {
            String suffix = url.substring((index + 1), (index + 4));
            if (suffix.equals("gif")) {
                return true;
            }
        }
        return is;
    }

    /*public static void displayImage(String url, final ImageView iv, final ProgressBar progressBar) {
        Glide.with(iv.getContext())
                .load(Util.getD2cPicUrl(url)).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                progressBar.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(iv);
    }*/

    /**
     * 加载本地图片
     *
     * @param context
     * @param imageView
     * @param source    图片源文件
     */
    public static void displayImage(Context context, ImageView imageView, Object source) {
        if (imageView == null || source == null) {
            return;
        }
        Glide.with(context).load(source).into(imageView);
    }

/*    public static File getFile(Context context, String url) {
        File file = null;
        try {
            file = Glide.with(context).asFile()
                    .load(url)
                    .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return file;
    }*/

}
