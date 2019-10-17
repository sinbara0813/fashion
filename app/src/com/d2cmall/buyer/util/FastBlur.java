package com.d2cmall.buyer.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.commit451.nativestackblur.NativeStackBlur;

public class FastBlur {

    public static Bitmap getBlurBackgroundDrawer(Activity activity) {
        Bitmap bmp = takeScreenShot(activity);
        return startBlurBackground(bmp);
    }

    private static Bitmap startBlurBackground(Bitmap bkg) {
        long startMs = System.currentTimeMillis();
        Bitmap overlay = NativeStackBlur.process(small(bkg), 10);
        Log.i("NativeBlur", "==blur time:" + (System.currentTimeMillis() - startMs));
        return big(overlay);
    }

    private static Bitmap takeScreenShot(Activity activity) {
        //View view = activity.getWindow().getDecorView();
        View view = activity.getWindow().getDecorView().findViewById(Window.ID_ANDROID_CONTENT);
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        // 获取屏幕长和高
        int width = activity.getResources().getDisplayMetrics().widthPixels;
        int height = activity.getResources().getDisplayMetrics().heightPixels;
        /*Bitmap bmp = Bitmap.createBitmap(b1, 0, Util.getStatusHeight(activity),
                width, height - Util.getStatusHeight(activity));*/
        Bitmap bmp = b1.copy(Bitmap.Config.ARGB_8888,true);
        view.destroyDrawingCache();
        return bmp;
    }

    /**
     * 放大图片
     *
     * @param bitmap 需要放大的图片
     * @return 放大的图片
     */
    private static Bitmap big(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(5f, 5f);
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    /**
     * 缩小图片
     *
     * @param bitmap 需要缩小的图片
     * @return 缩小的图片
     */
    private static Bitmap small(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(0.2f, 0.2f);
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

}
