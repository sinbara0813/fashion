package com.d2cmall.buyer.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sina.weibo.sdk.utils.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;

public class BitmapUtils {

    public static Bitmap getEqualRatioThumbnailBitmap(String fullFilePath,
                                                      float maxWidth, float maxHeight) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fullFilePath, newOpts);// 此时返回bm为空

        // 原图宽高
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;

        // 缩放比。由于是固定比例缩小，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        int beWidth = 1;// 根据宽度固定大小缩小
        if (w > maxWidth) {
            beWidth = (int) (w / maxWidth) + 1;
        }
        int beHeight = 1;
        if (h > maxHeight) {// 根据宽度固定大小缩小
            beHeight = (int) (h / maxHeight) + 1;
        }
        be = Math.max(beWidth, beHeight);// 取压缩比大的进行压缩
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;

        newOpts.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(fullFilePath, newOpts);
    }

    public static Bitmap adjustPhotoRotation(Bitmap bitmap, int orientationDegree) {


        Matrix matrix = new Matrix();
        matrix.setRotate(orientationDegree, (float) bitmap.getWidth() / 2,
                (float) bitmap.getHeight() / 2);
        float targetX, targetY;
        if (orientationDegree == 90) {
            targetX = bitmap.getHeight();
            targetY = 0;
        } else {
            targetX = bitmap.getHeight();
            targetY = bitmap.getWidth();
        }


        final float[] values = new float[9];
        matrix.getValues(values);


        float x1 = values[Matrix.MTRANS_X];
        float y1 = values[Matrix.MTRANS_Y];


        matrix.postTranslate(targetX - x1, targetY - y1);


        Bitmap canvasBitmap = Bitmap.createBitmap(bitmap.getHeight()-1, bitmap.getWidth()-1,
                Bitmap.Config.RGB_565);


        Paint paint = new Paint();
        Canvas canvas = new Canvas(canvasBitmap);
        canvas.drawBitmap(bitmap, matrix, paint);


        return canvasBitmap;
    }

    public static Bitmap createQRImage(String url, final int width, final int height) {
        try {
            // 判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return null;
            }
            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url,
                    BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    } else {
                        pixels[y * width + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap createWhiteQRImage(String url, int width, int height, int whiteWidth) {
        try {
            // 判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return null;
            }
            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.MARGIN, 0);
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url,
                    BarcodeFormat.QR_CODE, width, height, hints);
            //1.1去白边
            int[] rec = bitMatrix.getEnclosingRectangle();
            int resWidth = rec[2] + 1;
            int resHeight = rec[3] + 1;
            BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
            resMatrix.clear();
            for (int i = 0; i < resWidth; i++) {
                for (int j = 0; j < resHeight; j++) {
                    if (bitMatrix.get(i + rec[0], j + rec[1])) {
                        resMatrix.set(i, j);
                    }
                }
            }
            width = resMatrix.getWidth();
            height = resMatrix.getHeight();
            if (whiteWidth > 0) {
                width += (2 * whiteWidth - 1);
                height += (2 * whiteWidth - 1);
            }
            int[] pixels = new int[width * height];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int realX = x - whiteWidth;
                    int realY = y - whiteWidth;
                    if (realX >= 0 && realY >= 0 && realX < resMatrix.getWidth() && realY < resMatrix.getHeight() && resMatrix.get(realX, realY)) {
                        pixels[y * width + x] = 0xff000000;
                    } else {
                        pixels[y * width + x] = 0xffffffff;
                    }
                }
            }
            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getAvareBitmap(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, 100, 100);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        return bitmap;
    }

    public static Bitmap getBackBitmap(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        return bitmap;
    }

    public static Bitmap getSmallBitmap(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        return bitmap;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int heightRatio = Math.round((float) height / (float) reqHeight);
            int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            float totalPixels = (float) (width * height);

            for (float totalReqPixelsCap = (float) (reqWidth * reqHeight * 2); totalPixels / (float) (inSampleSize * inSampleSize) > totalReqPixelsCap; ++inSampleSize) {
                ;
            }
        }

        return inSampleSize;
    }

    public static Bitmap getScaleBitmap(Bitmap bitmap, float wScale, float hScale) {
        Matrix matrix = new Matrix();
        matrix.postScale(wScale, hScale);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bmp;
    }

    // 获取圆型Bitmap
    public static Bitmap getCircleBitmap(Bitmap src) {
        int diameter = Math.min(src.getWidth(), src.getHeight());
        Bitmap dst = Bitmap.createBitmap(diameter, diameter,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(dst);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawCircle(diameter / 2, diameter / 2, diameter / 2, paint);
        PorterDuffXfermode xfermode = new PorterDuffXfermode(
                PorterDuff.Mode.SRC_IN);
        paint.setXfermode(xfermode);
        canvas.drawBitmap(src, 0, 0, paint);
        return dst;
    }

    public static byte[] getBitmapData(Bitmap bitmap) {
        byte[] imageData = null;
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, os);
            imageData = os.toByteArray();
        } catch (Exception var12) {
            var12.printStackTrace();
            LogUtil.e("Weibo.ImageObject", "put thumb failed");
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException var11) {
                var11.printStackTrace();
            }
        }
        return imageData;
    }

}
