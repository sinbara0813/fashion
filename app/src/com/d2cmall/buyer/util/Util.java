package com.d2cmall.buyer.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.baidu.mobstat.StatService;
import com.cheilpengtai.supertshirt16library.communicator.ble.RequestError;
import com.d2cmall.buyer.AppProfile;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.AuctionPayActivity;
import com.d2cmall.buyer.activity.BrandDetailActivity;
import com.d2cmall.buyer.activity.BrandListActivity;
import com.d2cmall.buyer.activity.BrandUpdateActivity;
import com.d2cmall.buyer.activity.CartActivity;
import com.d2cmall.buyer.activity.CashierActivity;
import com.d2cmall.buyer.activity.CombProductActivity;
import com.d2cmall.buyer.activity.ComplainBackDetialActivity;
import com.d2cmall.buyer.activity.ConsultDetailActivity;
import com.d2cmall.buyer.activity.CrowdsActivity;
import com.d2cmall.buyer.activity.DCoinProductDetaiActivity;
import com.d2cmall.buyer.activity.DCoinShopActivity;
import com.d2cmall.buyer.activity.ExchangeDetailActivity;
import com.d2cmall.buyer.activity.FansActivity;
import com.d2cmall.buyer.activity.FashionMatchActivity;
import com.d2cmall.buyer.activity.FlashProductActivity;
import com.d2cmall.buyer.activity.FlashPromotion1Activity;
import com.d2cmall.buyer.activity.GroupBuyActivity;
import com.d2cmall.buyer.activity.HomeActivity;
import com.d2cmall.buyer.activity.LiveRouterActivity;
import com.d2cmall.buyer.activity.LoginActivity;
import com.d2cmall.buyer.activity.MyCouponsActivity;
import com.d2cmall.buyer.activity.MyOrderActivity;
import com.d2cmall.buyer.activity.OrderDetailActivity;
import com.d2cmall.buyer.activity.PartnerAccountDetailActivity;
import com.d2cmall.buyer.activity.PartnerCashActivity;
import com.d2cmall.buyer.activity.PartnerCenterActivity1;
import com.d2cmall.buyer.activity.PersonInfoActivity;
import com.d2cmall.buyer.activity.PhysicalStoreActivity;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.activity.ProductListActivity;
import com.d2cmall.buyer.activity.PromotionListActivity;
import com.d2cmall.buyer.activity.PromotionsActivity;
import com.d2cmall.buyer.activity.RedPacketActivity;
import com.d2cmall.buyer.activity.RefundDetailActivity;
import com.d2cmall.buyer.activity.ReshipDetailActivity;
import com.d2cmall.buyer.activity.ShowDetailActivity;
import com.d2cmall.buyer.activity.TopicDetailActivity;
import com.d2cmall.buyer.activity.TshirtActivity;
import com.d2cmall.buyer.activity.VideoListActivity;
import com.d2cmall.buyer.activity.WalletActivity;
import com.d2cmall.buyer.activity.WebActivity;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.PushBean;
import com.d2cmall.buyer.bean.Size;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.bean.YSFItemBean;
import com.d2cmall.buyer.http.ErrorCode;
import com.d2cmall.buyer.http.HttpError;
import com.d2cmall.buyer.widget.DefineToast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.igexin.sdk.PushManager;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;
import com.tendcloud.tenddata.TCAgent;
import com.zamplus.businesstrack.ZampAppAnalytics;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.greenrobot.event.EventBus;

import static com.cheilpengtai.supertshirt16library.communicator.ble.RequestError.NO_CONNECTION;

public class Util {

    private static Point deviceSize;
    private static DefineToast defineToast;
    public static int LIMITED = 1; //有限网
    public static int INFINITE = 2; //无限网

    public static void updateVideo(String url) {
        File file = new File(url);
        //获取ContentResolve对象，来操作插入视频
        ContentResolver localContentResolver = AppProfile.getContext().getContentResolver();
        //ContentValues：用于储存一些基本类型的键值对
        ContentValues localContentValues = getVideoContentValues(AppProfile.getContext(), file, System.currentTimeMillis());
        //insert语句负责插入一条新的纪录，如果插入成功则会返回这条记录的id，如果插入失败会返回-1。
        Uri localUri = localContentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, localContentValues);
    }

    //再往数据库中插入数据的时候将，将要插入的值都放到一个ContentValues的实例当中
    public static ContentValues getVideoContentValues(Context paramContext, File paramFile, long paramLong) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("title", paramFile.getName());
        localContentValues.put("_display_name", paramFile.getName());
        localContentValues.put("mime_type", "video/3gp");
        localContentValues.put("datetaken", Long.valueOf(paramLong));
        localContentValues.put("date_modified", Long.valueOf(paramLong));
        localContentValues.put("date_added", Long.valueOf(paramLong));
        localContentValues.put("_data", paramFile.getAbsolutePath());
        localContentValues.put("_size", Long.valueOf(paramFile.length()));
        return localContentValues;
    }

    public static void UpdatePic(Context context,File file){
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }


    public static int getFakeDownLoadNum(int fakeSeeNum, int realDownloadNUm) {
        int fake = (int) ((fakeSeeNum / 149.3222) + (realDownloadNUm * 13.7654));
        return fake;
    }

    public static int getFakeShareNum(int fakeSeeNum, int realShareNUm) {
        int fake = (int) ((fakeSeeNum / 129.3222) + (realShareNUm * 7.6548));
        return fake;
    }


    public static void onResume(Activity activity) {
        //if (!BuildConfig.DEBUG) {
        StatService.onResume(activity);
        ZampAppAnalytics.onResume(activity);
        // }
    }

    public static void onPause(Activity activity) {
        // if (!BuildConfig.DEBUG) {
        StatService.onPause(activity);
        ZampAppAnalytics.onPause(activity);
        //}
    }

    public static void onPageStart(Context context,String pageName){
        TCAgent.onPageStart(context,pageName);
    }

    public static void onPageEnd(Context context,String pageName){
        TCAgent.onPageEnd(context,pageName);
    }

    public static long getFakeWatchCount(Date time) {
        long fakeCount = (long) (Math.log(DateUtil.getMinutesNum(time) + 1) * 800);
        return fakeCount;
    }

    public static String checkErrorType(VolleyError error) {
        String str = "";
        if (error instanceof NoConnectionError) {
            str = ErrorCode.IS_NOT_NETWORK;
        } else if (error instanceof AuthFailureError) {
            UserBean.DataEntity.MemberEntity userBean = Session.getInstance().getUser();
            if (userBean != null) {
                Session.getInstance().logout(AppProfile.getContext());
                EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.LOGOUT));
                PushManager.getInstance().unBindAlias(AppProfile.getContext(), String.valueOf(userBean.getMemberId()), true);
            }
            str = ErrorCode.AUTH_FAILED;
        } else if (error instanceof TimeoutError) {
            str = ErrorCode.CONNECTION_TIMEOUT;
        } else if (error instanceof ParseError) {
            str = ErrorCode.PARSE_DATA_ERROR;
        } else if (error instanceof ServerError) {
            str = ErrorCode.SERVER_ERROR;
        } else if (error instanceof HttpError) {
            HttpError httpError = (HttpError) error;
            str = httpError.getMessage();
            if (isEmpty(str)) {
                str = ErrorCode.REQUEST_ERROR;
            }
        } else {
            str = ErrorCode.REQUEST_ERROR;
        }
        return str;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.equals("") || str.equals("null");
    }

    public static String getMD5(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(str.getBytes());
            byte[] bytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                int temp = 0xff & b;
                if (temp <= 0x0F) {
                    sb.append("0").append(Integer.toHexString(temp));
                } else {
                    sb.append(Integer.toHexString(temp));
                }
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean loginChecked(Activity activity, int requestCode) {
        UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(activity);
        if (user != null && user.getId() > 0) {
            return true;
        } else {
            Intent intent = new Intent(activity, LoginActivity.class);
            if (requestCode > 0) {
                activity.startActivityForResult(intent, requestCode);
            } else {
                activity.startActivity(intent);
            }
            activity.overridePendingTransition(R.anim.slide_in_up, R.anim.activity_anim_default);
            return false;
        }
    }

    public static Point getDeviceSize(Context context) {
        if (deviceSize == null || deviceSize.x == 0 || deviceSize.y == 0) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            deviceSize = new Point();
            display.getSize(deviceSize);
        }
        return deviceSize;
    }

    /**
     * 判断android SDK 版本是否小于5.0
     *
     * @return
     */
    public static boolean isLowThanAndroid5() {

        return Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP;
    }

    public static String readStreamToString(InputStream is) {
        return new String(readStreamToByteArray(is));
    }

    public static byte[] readStreamToByteArray(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = -1;
        byte[] data = null;
        try {
            while ((length = is.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            baos.flush();
            data = baos.toByteArray();
            is.close();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static Date getDate(String date) {
        try {
            return DateUtil.parseDate(date.contains("Z") ? (date + "+00:00") : date,
                    Constants.DATE_TIME_FORMAT_LONG, Constants.DATE_FORMAT_LONG, Constants.DATE_TIME_FORMAT_LONG2, Constants.DATE_TIME_FORMAT_LONG3,
                    Constants.DATE_TIME_FORMAT_LONG4, Constants.DATE_TIME_FORMAT_LONG5, Constants.DATE_TIME_FORMAT_LONG6, Constants.DATE_FORMAT_SHORT);
        } catch (ParseException e) {
            try {
                return DateUtil.parseDate(date.contains("Z") ? (date + "+00:00") : date,
                        Constants.DATE_TIME_FORMAT_LONG7, Constants.DATE_FORMAT_LONG1, Constants.DATE_TIME_FORMAT_LONG8, Constants.DATE_TIME_FORMAT_LONG9,
                        Constants.DATE_TIME_FORMAT_LONG10, Constants.DATE_TIME_FORMAT_LONG11, Constants.DATE_TIME_FORMAT_LONG12, Constants.DATE_FORMAT_SHORT1);
            } catch (ParseException e1) {
                return null;
            }
        }
    }

    //返回D2C图片路径
    public static String getD2cPicUrl(String url) {
        if (Util.isEmpty(url)) {
            return null;
        } else if (url.startsWith("http://") || url.startsWith("https://")) {
            return url;
        } else if (url.startsWith("/storage/") || url.startsWith("/system/")) {
            return url;//"file://" + url;
        } else {
            return Constants.IMG_HOST + url;
        }
    }

    public static String getD2cProductPicUrl(String url,int w,int h){
        if (Util.isEmpty(url)) {
            return null;
        } else if (url.startsWith("http://") || url.startsWith("https://")) {
            return url;
        } else if (url.startsWith("/storage/") || url.startsWith("/system/")) {
            return url;//"file://" + url;
        } else {
            return Constants.IMG_HOST + url + "!/canvas/"+w+"x"+h+"/fw/"+w+Constants.WEBP;
        }
    }

    //返回D2C图片路径视频封面路径
    public static String getVideoPicUrl(String url) {
        if (Util.isEmpty(url)) {
            return null;
        } else if (url.startsWith("http://")) {
            return url;
        } else if (url.startsWith("/storage/") || url.startsWith("/system/")) {
            return url;//"file://" + url;
        } else {
            return Constants.VIDEO_HOST + url;
        }
    }

    //返回D2C视频截图地址
    public static String getD2CVideoPicUrl(String url) {
        if (isEmpty(url)) {
            return null;
        } else if (url.startsWith("http://")) {
            return url + Constants.VIDEO_PIC_URL;
        } else {
            if (isGif(url)) {
                return Constants.IMG_HOST + url;
            } else {
                return Constants.IMG_HOST + url +
                        String.format(Constants.VIDEO_PIC_URL, Util.deviceSize.x);
                //Constants.VIDEO_PIC_URL;
            }
        }
    }

    public static String getD2cPicUrl(String url, int width) {
        if (Util.isEmpty(url)) {
            return null;
        } else if (url.startsWith("http://")) {
            return url;
        } else if (url.startsWith("/storage/")) {
            return url;//"file://" + url;
        } else {
            if (isGif(url)) {
                return Constants.IMG_HOST + url;
            } else {
                return Constants.IMG_HOST + url + String.format(Constants.PHOTO_URL2, width);
            }
        }
    }

    public static String getD2cPicUrl(String url, int width, int height) {
        if (Util.isEmpty(url)) {
            return null;
        } else if (url.startsWith("http://")) {
            return url;
        } else if (url.startsWith("/storage/emulated")) {
            return url;//"file://" + url;
        } else {
            if (isGif(url)) {
                return Constants.IMG_HOST + url;
            } else {
                return Constants.IMG_HOST + url + String.format(Constants.PHOTO_URL, width, height);
            }
        }
    }

    private static boolean isGif(String url) {
        boolean is = false;
        int index = url.lastIndexOf(".");
        int length = url.length();
        if (index > 0) {
            String suffix = url.substring((index + 1), (index + 4));
            if (suffix.equals("gif")) {
                return true;
            }
        }
        return is;
    }

    public static int getOrientation(String path) {
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return 90;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return 180;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return 270;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static SpannableString getProductPrice(double price,double originalPrice){
        if (originalPrice<=0){
            return null;
        }
        StringBuilder builder=new StringBuilder();
        builder.append("¥").append(Util.getNumberFormat(price))
            .append("  ").append("¥").append(Util.getNumberFormat(originalPrice));
        SpannableString sp=new SpannableString(builder.toString());
        int index=builder.toString().lastIndexOf("¥");
        RelativeSizeSpan sizeSpan=new RelativeSizeSpan((float)0.75);
        sp.setSpan(sizeSpan,index,builder.toString().length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ForegroundColorSpan colorSpan=new ForegroundColorSpan(Color.parseColor("#80000000"));
        sp.setSpan(colorSpan,index,builder.toString().length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        StrikethroughSpan strikethroughSpan=new StrikethroughSpan();
        sp.setSpan(strikethroughSpan,index,builder.toString().length(),Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return sp;
    }

    public static String getProductPrice(double price){
        return "¥"+Util.getNumberFormat(price);
    }

    public static SpannableString getProductTitle(String text,int count){
        StringBuilder builder=new StringBuilder();
        builder.append(text).append(" ").append("共").append(count).append("条");
        SpannableString sp=new SpannableString(builder.toString());
        int index=builder.toString().lastIndexOf("共");
        RelativeSizeSpan sizeSpan=new RelativeSizeSpan((float)0.75);
        sp.setSpan(sizeSpan,index,builder.toString().length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        ForegroundColorSpan colorSpan=new ForegroundColorSpan(Color.parseColor("#80000000"));
        sp.setSpan(colorSpan,index,builder.toString().length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return sp;
    }

    public static void showToast(Context context, String string, int duration) {
        if (defineToast != null) {
            defineToast = null;
        }
        defineToast = new DefineToast(context, string, duration);
        defineToast.show();
    }

    public static void showToast(Context context, String string) {
        showToast(context, string, false);
    }

    public static void showToast(Context context, String string, boolean needDefine) {
        if (needDefine) {
            if (defineToast != null) {
                defineToast = null;
            }
            defineToast = new DefineToast(context, string, 1);
            defineToast.show();
        } else {
            Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showToast(Context context, int stringId) {
        showToast(context, stringId, false);
    }

    public static void showToast(Context context, int stringId, boolean needDefine) {
        String text = context.getString(stringId);
        if (needDefine) {
            if (defineToast != null) {
                defineToast = null;
            }
            defineToast = new DefineToast(context, text, 1);
            defineToast.show();
        } else {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }
    }

    public static String getSomeContent(CharSequence c, double number) {
        //截取前n位中文或2n个英文，再加...
        double len = 0;
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < c.length(); i++) {
            int tmp = (int) c.charAt(i);
            if (tmp > 0 && tmp < 127) {//英文
                if (len < number) {//没到达长度
                    buffer.append(c.charAt(i));
                } else if (c.length() > number) {
                    buffer.append("...");
                    break;
                }
                len++;
            } else {
                if (len < number) {
                    buffer.append(c.charAt(i));
                } else if (c.length() > number) {
                    buffer.append("...");
                    break;
                }
                len = len + 2;
            }
        }
        return buffer.toString();
    }

    //去掉-的随机数
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    //又拍云服务器储存路径
    public static String getUPYunSavePath(long userId, String type) {
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
        return "/app/" + type + "/" + sdf.format(new Date()) + "/" + getMD5(String.valueOf(userId) + uuid());
    }

    public static String getNumberFormat(double value) {
        return getNumberFormat(value, true, false);
    }

    public static String getNumberFormat(double value, boolean isGroupingUsed) {
        return getNumberFormat(value, isGroupingUsed, false);
    }

    public static String getNumberFormat(double value, boolean isGroupingUsed, boolean isMinFraction) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        if (isMinFraction) {
            nf.setMinimumFractionDigits(2);
        }
        nf.setRoundingMode(RoundingMode.HALF_UP);
        nf.setGroupingUsed(isGroupingUsed);
        return nf.format(value);
    }

    public static String getNumberFormat(double value, int minNumber) {
        if (value % 1 == 0) {
            return (int) value + "";
        }
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(minNumber);
        nf.setMinimumFractionDigits(minNumber);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        nf.setGroupingUsed(true);
        return nf.format(value);
    }

    /**
     *
     * @param value
     * @param patten 例 #,###.00
     * @return
     */
    public static String getNumberFormat(double value,String patten){
        DecimalFormat format=new DecimalFormat(patten);
        return format.format(value);
    }

    public static boolean isMobileNo(String mobile, String re) {
        return !isEmpty(mobile) && mobile.matches(re);
    }

    public static boolean isCnMobileNo(String mobile) {
        return !isEmpty(mobile) && mobile.matches("[1][34578]\\d{9}");
    }

    public static boolean isPayPassword(String payPassword) {
        return !isEmpty(payPassword) && payPassword.matches("(?!^\\d+$)(?!^[a-zA-Z]+$)[a-zA-Z0-9]{8,20}");
    }

    public static boolean isMail(String mail) {
        String mailRegex = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$";
        return !isEmpty(mail) && mail.matches(mailRegex);
    }

    //身份证正则
    public static boolean isIdCardNumber(String idNumber) {
        String idCardNumberRegex = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$)";
        return !isEmpty(idNumber) && idNumber.matches(idCardNumberRegex);
    }


    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public static int getStatusHeightOther(Context context) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        ((Activity) context).getWindow().getDecorView()
                .getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass
                        .getField("status_bar_height").get(localObject)
                        .toString());
                statusHeight = context.getResources().getDimensionPixelSize(i5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    public static String toURLEncode(String paramString) {
        if (isEmpty(paramString)) {
            return "";
        }
        try {
            String str = new String(paramString.getBytes(), "UTF-8");
            str = URLEncoder.encode(str, "UTF-8");
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean urlAction(Context context, String url) {
        return urlAction(context, url, null);
    }

    public static boolean urlAction(Context context, String url, String title) {
        return urlAction(context, url, 0, title, false, false, null);
    }

    public static boolean urlAction(Context context, String url, String title, String log) {
        return urlAction(context, url, 0, title, false, false, log);
    }

    public static boolean urlAction(Context context, String url, boolean isShareGone) {
        return urlAction(context, url, 0, null, isShareGone, false, null);
    }

    public static boolean urlAction(Context context, String url, int requestCode) {
        return urlAction(context, url, requestCode, null, false, false, null);
    }

    public static boolean urlAction(Context context, String url, int requestCode, boolean isWeb) {
        return urlAction(context, url, requestCode, null, false, isWeb, null);
    }

    public static boolean urlAction(Context context, String url, int requestCode, boolean isWeb, String log) {
        return urlAction(context, url, requestCode, null, false, isWeb, log);
    }

    public static boolean urlAction(Context context, String url, int requestCode, String title,
                                    boolean isShareGone, boolean isWeb, String log) {
        if (Util.isEmpty(url)) {
            return true;
        }
        url = url.trim();//去掉左右两边空格
        Matcher mat0 = Pattern.compile("(invoked=1|appToWap|m.kuaidi100.com)").matcher(url);
        if (mat0.find()) {//有invoke或appToWap或kuaidi100的，不拦截
            return false;
        }
        Matcher mat1 = Pattern.compile("/product/list(\\?.*)").matcher(url);
        // //商品列表  "/product/list?t=1&k=baobaoxiasile";   "/product/list?t=4&c=1640"   "/product/list?t=1&c=1692"
        if (mat1.find()) {//跳转商品列表
            Intent intent = new Intent(context, ProductListActivity.class);
            Matcher mMatcher = Pattern.compile("k=(.+?)((?=&)|$)").matcher(mat1.group(1));
            if(mMatcher.find()){
                intent.putExtra("keyword", Utf8Decode(mMatcher.group(1)));
            }
            intent.putExtra("url", mat1.group(1));
            intent.putExtra("type", 0);
            intent.putExtra("log", log);
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
            return true;
        }
        Matcher mat2 = Pattern.compile("(?<!/auction)/product/(\\d+)(\\?parent_id=(\\d+))?").matcher(url);
        if (mat2.find()) {//跳转商品详情
            long productId = Long.parseLong(mat2.group(1));
            int parentId = 0;
            if (!Util.isEmpty(mat2.group(3))) {
                try {
                    parentId = Integer.parseInt(mat2.group(3));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (productId > 0) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id", productId);
                if (parentId > 0) {
                    intent.putExtra("parentId", parentId);
                }
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(
                        R.anim.slide_in_right, R.anim.activity_anim_default);
                return true;
            }
        }
        Matcher mat3 = Pattern.compile("(/showroom/)+designer/(\\d+)").matcher(url);
        if (mat3.find()) {//跳转设计师详情
            int designerId = Integer.parseInt(mat3.group(2));
            if (designerId > 0) {
                Intent intent = new Intent(context, BrandDetailActivity.class);
                intent.putExtra("id", designerId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(
                        R.anim.slide_in_right, R.anim.activity_anim_default);
                return true;
            }
        }
        Matcher mat4 = Pattern.compile("((?<=d2cmall.com)|^)(/promotion/(\\d+))").matcher(url);
        if (mat4.find()) {//跳转活动页面
            long promotionId = Long.parseLong(mat4.group(3));
            if (promotionId > 0) {
                Intent intent = new Intent(context, PromotionsActivity.class);
                intent.putExtra("id", promotionId);
                intent.putExtra("url", mat4.group(2));
                intent.putExtra("log", log);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(
                        R.anim.slide_in_right, R.anim.activity_anim_default);
                return true;
            }
        }
        Matcher mat5 = Pattern.compile("((?<=d2cmall.com)|^)(/crowds/crowditemlist/(\\d+))").matcher(url);
        if (mat5.find()) {//跳转预售页面
            long crowdId = Long.parseLong(mat5.group(3));
            if (crowdId > 0) {
                Intent intent = new Intent(context, CrowdsActivity.class);
                intent.putExtra("id", crowdId);
                intent.putExtra("url", mat5.group(2));
                intent.putExtra("log", log);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(
                        R.anim.slide_in_right, R.anim.activity_anim_default);
                return true;
            }
        }
        Matcher mat6 = Pattern.compile("/member/order").matcher(url);
        if (mat6.find()) {//跳转订单列表
            Intent intent = new Intent(context, MyOrderActivity.class);
            intent.putExtra("position", 0);
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
            return true;
        }
        Matcher mat7 = Pattern.compile("/coupon/memberCoupon").matcher(url);
        if (mat7.find()) {//跳转优惠券列表
            Intent intent = new Intent(context, MyCouponsActivity.class);
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
            return true;
        }
        Matcher mat8 = Pattern.compile("/member/login").matcher(url);
        if (mat8.find()) {//跳转登录
            Intent intent = new Intent(context, LoginActivity.class);
            if (requestCode > 0) {
                ((Activity) context).startActivityForResult(intent, requestCode);
            } else {
                context.startActivity(intent);
            }
            ((Activity) context).overridePendingTransition(R.anim.slide_in_up, R.anim.activity_anim_default);
            return true;
        }
        Matcher mat9 = Pattern.compile("/cart/list").matcher(url);
        if (mat9.find()) {//跳转购物车
            Intent intent = new Intent(context, CartActivity.class);
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
            return true;
        }
        Matcher mat10 = Pattern.compile("/productComb/(\\d+)").matcher(url);
        if (mat10.find()) {//跳转组合商品
            long productCombId = Long.parseLong(mat10.group(1));
            if (productCombId > 0) {
                Intent intent = new Intent(context, CombProductActivity.class);
                intent.putExtra("log", log);
                intent.putExtra("productCombId", productCombId);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                return true;
            }
        }
        Matcher mat11 = Pattern.compile("/(details/share|membershare)/(\\d+)").matcher(url);
        if (mat11.find()) {//跳转买家秀详情-消息来源或H5页面来源
            long shareId = Long.parseLong(mat11.group(2));
            if (shareId > 0) {
                Intent intent = new Intent(context, ShowDetailActivity.class);
                intent.putExtra("id", (long) shareId);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                return true;
            }
        }
        Matcher mat12 = Pattern.compile("/myInterest/myFans").matcher(url);
        if (mat12.find()) {//跳转我的粉丝
            Intent intent = new Intent(context, FansActivity.class);
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
            return true;
        }
        Matcher mat13 = Pattern.compile("/details/order/(\\w+)").matcher(url);
        if (mat13.find()) {//跳转订单详情
            String orderSn = mat13.group(1);
            if (!Util.isEmpty(orderSn)) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("orderSn", orderSn);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                return true;
            }
        }
        Matcher mat14 = Pattern.compile("/membershare/mine\\?memberId=(\\d+)").matcher(url);
        if (mat14.find()) {//跳转个人主页-H5页面来源
            long memberId = Long.parseLong(mat14.group(1));
            if (memberId > 0) {
                Intent intent = new Intent(context, PersonInfoActivity.class);
                intent.putExtra("memberId", memberId);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                return true;
            }
        }
        Matcher mat15 = Pattern.compile("/payment/prepare/.*param=(\\S+)&?").matcher(url);
        if (mat15.find()) {
            String auctionUrl = mat15.group(1);
            if (!Util.isEmpty(auctionUrl)) {
                Intent intent = new Intent(context, AuctionPayActivity.class);
                intent.putExtra("url", auctionUrl);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                return true;
            }
        }
        Matcher mat16 = Pattern.compile("/member/t_shirt").matcher(url);
        if (mat16.find()) {//跳转TShirt设置
            Intent intent = new Intent(context, TshirtActivity.class);
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
            return true;
        }
        Matcher mat17 = Pattern.compile("/live/list").matcher(url);
        if (mat17.find()) {//跳转直播列表
            Intent intent = new Intent(context, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            EventBus.getDefault().post(new ActionBean(Constants.ActionType.LIVE_LIST));
            return true;
        }
        Matcher mat18 = Pattern.compile("/member/account/info").matcher(url);
        if (mat18.find()) {
            UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(context);
            if (user == null) {//跳登录页面
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.slide_in_up, R.anim.activity_anim_default);
            } else {//跳转充值页面
                Intent intent = new Intent(context, WalletActivity.class);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
            }
            return true;
        }
        Matcher mat19 = Pattern.compile("/live/(\\d+)").matcher(url);
        if (mat19.find()) {
            UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(context);
            if (user == null) {//跳登录页面
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.slide_in_up, R.anim.activity_anim_default);
                return true;
            } else {//跳转观看直播或往期回顾或恰好直播结束转码中
                long liveId = Long.parseLong(mat19.group(1));
                if (liveId > 0) {
                    Intent intent = new Intent(context, LiveRouterActivity.class);
                    intent.putExtra("id", liveId);
                    context.startActivity(intent);
                    ((Activity) context).overridePendingTransition(0, 0);
                    return true;
                }
            }
        }
        Matcher mat20 = Pattern.compile("/customer/service").matcher(url);
        if (mat20.find()) {//跳转连接客服
            if (Util.loginChecked((Activity) context, 999)) {
                ConsultSource source = new ConsultSource("http://www.d2cmall.com", "线上客服", "其它");
                source.groupId = Constants.QIYU_AF_GROUP_ID;
                source.robotFirst = true;
                Unicorn.openServiceActivity(context, "D2C客服", source);
                //合力亿捷
//                Intent intent = new Intent(context,CustomServiceActivity.class);
//                intent.putExtra("skillGroupId", Constants.HLYJ_BF_AF_GROUP_ID);
//                context.startActivity(intent);
            }
            return true;
        }
        Matcher mat21 = Pattern.compile("tel:(\\d+-?\\d+)").matcher(url);
        if (mat21.find()) {
            Intent call = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + mat21.group(0));
            call.setData(data);
            context.startActivity(call);
            return true;
        }
        Matcher matcher22 = Pattern.compile("/page/physicalStores").matcher(url);
        if (matcher22.find()) {
            Intent intent = new Intent(context, PhysicalStoreActivity.class);
            context.startActivity(intent);
            return true;
        }
        Matcher matcher33 = Pattern.compile("/designer/list\\?tagId=(\\d+)&type=native").matcher(url);
        if (matcher33.find()) {
            Intent intent = new Intent(context, BrandUpdateActivity.class);
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
            return true;
        }
        Matcher matcher23 = Pattern.compile("/designer/list\\?tagId=(\\d+)").matcher(url);
        if (matcher23.find()) {
            int id = Integer.parseInt(matcher23.group(1));
            if (id > 0) {
                Intent intent = new Intent(context, BrandListActivity.class);
                intent.putExtra("type", 0);
                intent.putExtra("id", id);
                context.startActivity(intent);
                return true;
            }
        }
        Matcher matcher24 = Pattern.compile("/membershare/list\\?tagId=(\\d+)").matcher(url);
        if (matcher24.find()) {
            int id = Integer.parseInt(matcher24.group(1));
            Intent intent = new Intent(context, VideoListActivity.class);
            if (id == 1) {
                intent.putExtra("title", "推荐");
            } else if (id > 0) {
                intent.putExtra("title", "视频");
            }
            intent.putExtra("id", id);
            context.startActivity(intent);
            return true;
        }
        Matcher matcher25 = Pattern.compile("/membershare/topic/(\\d+)").matcher(url);
        if (matcher25.find()) {
            long id = Integer.parseInt(matcher25.group(1));
            Intent intent = new Intent(context, TopicDetailActivity.class);
            if (id > 0) {
                intent.putExtra("id", id);
            }
            context.startActivity(intent);
            return true;
        }
        Matcher matcher26 = Pattern.compile("/promotion/items\\?\\S*sectionId=(\\d+)").matcher(url);
        if (matcher26.find()) {
            int id = Integer.parseInt(matcher26.group(1));
            Intent intent = new Intent(context, PromotionListActivity.class);
            intent.putExtra("id", id);
            context.startActivity(intent);
            return true;
        }
        Matcher matcher27 = Pattern.compile("/check/redPacket").matcher(url);
        if (matcher27.find()) {
            UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(context);
            if (user == null) {
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.slide_in_up, R.anim.activity_anim_default);
                return true;
            } else {
                Intent intent = new Intent(context, RedPacketActivity.class);
                context.startActivity(intent);
                return true;
            }
        }
        Matcher mat28 = Pattern.compile("/feedback/(\\d+)").matcher(url);
        if (mat28.find()) {//跳转反馈回复详情
            Intent intent = new Intent(context, ComplainBackDetialActivity.class);
            intent.putExtra("id", Long.parseLong(mat28.group(1)));
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
            return true;
        }
        Matcher mat29 = Pattern.compile("/coupon/myCoupon").matcher(url);
        if (mat29.find()) {//跳转优惠券列表
            Intent intent = new Intent(context, MyCouponsActivity.class);
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
            return true;
        }
        Matcher matcher30 = Pattern.compile("/flashpromotion/\\S+/session\\S*").matcher(url);
        if (matcher30.find()) { //跳转限时购
            Intent intent = new Intent(context, FlashPromotion1Activity.class);
            if (url.contains("alone")){
                intent.putExtra("index",1);
            }
            if (url.contains("brand")){
                intent.putExtra("index",2);
            }
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
            return true;
        }
        Matcher matcher31 = Pattern.compile("/flashpromotion/products/list\\?id=(\\d+)").matcher(url);
        if (matcher31.find()) { //跳转品牌限时购
            Intent intent = new Intent(context, FlashProductActivity.class);
            String id = matcher31.group(1);
            intent.putExtra("promotionId", Integer.valueOf(id));
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
            return true;
        }
        Matcher matcher32 = Pattern.compile("/detail/consult/(\\d+)").matcher(url);
        if (matcher32.find()) {
            Intent intent = new Intent(context, ConsultDetailActivity.class);
            intent.putExtra("id", Integer.valueOf(matcher32.group(1)));
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
            return true;
        }
        Matcher mat34 = Pattern.compile("/member/reship/(\\d+)").matcher(url);
        if (mat34.find()) {//跳转退货退款详情
            long reshipId = Long.parseLong(mat34.group(1));
            if (reshipId > 0) {
                Intent intent = new Intent(context, ReshipDetailActivity.class);
                intent.putExtra("reshipId", reshipId);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(
                        R.anim.slide_in_right, R.anim.activity_anim_default);
                return true;
            }
        }
        Matcher mat35 = Pattern.compile("/member/refund/(\\d+)").matcher(url);
        if (mat35.find()) {//跳转退款详情
            long reshipId = Long.parseLong(mat35.group(1));
            if (reshipId > 0) {
                Intent intent = new Intent(context, RefundDetailActivity.class);
                intent.putExtra("refundId", reshipId);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(
                        R.anim.slide_in_right, R.anim.activity_anim_default);
                return true;
            }
        }
        Matcher mat36 = Pattern.compile("/member/exchange/(\\d+)").matcher(url);
        if (mat36.find()) {//跳转换货详情
            long reshipId = Long.parseLong(mat36.group(1));
            if (reshipId > 0) {
                Intent intent = new Intent(context, ExchangeDetailActivity.class);
                intent.putExtra("exchangeId", reshipId);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(
                        R.anim.slide_in_right, R.anim.activity_anim_default);
                return true;
            }
        }
        Matcher matcher37 = Pattern.compile("/selfservice/buyer").matcher(url);
        if (matcher37.find()) {
            if (Util.loginChecked((Activity) context, 999)) {
                Intent intent = new Intent(context, PartnerCenterActivity1.class)
                        .putExtra("isTabGone", true);
                context.startActivity(intent);
            }
            return true;
        }
        Matcher matcher38 = Pattern.compile("/selfservice/partner").matcher(url);
        if (matcher38.find()) {
            if (Util.loginChecked((Activity) context, 999)) {
                Intent intent = new Intent(context, PartnerCenterActivity1.class)
                        .putExtra("isTabGone", true);
                context.startActivity(intent);
            }
            return true;
        }
        Matcher matcher41 = Pattern.compile("/to/partner/cash").matcher(url);
        if (matcher41.find()) {     //提现列表界面
            if (Util.loginChecked((Activity) context, 999)) {
                Intent intent = new Intent(context, PartnerCashActivity.class);
                context.startActivity(intent);
            }
            return true;
        }

        Matcher matcher42 = Pattern.compile("/to/partner/team").matcher(url);
        if (matcher42.find()) {     //我的团队
            if (Util.loginChecked((Activity) context, 999)) {
                Intent intent = new Intent(context, PartnerCenterActivity1.class)
                        .putExtra("subPosition",2)
                        .putExtra("isTabGone", true);
                context.startActivity(intent);
            }
            return true;
        }

        Matcher matcher43 = Pattern.compile("/to/partner/sales").matcher(url);
        if (matcher43.find()) {     //售货明细
            if (Util.loginChecked((Activity) context, 999)) {
                Intent intent = new Intent(context, PartnerCenterActivity1.class)
                        .putExtra("position",2)
                        .putExtra("isTabGone", true);
                context.startActivity(intent);
            }
            return true;
        }

        Matcher matcher44 = Pattern.compile("/to/partner/account/item").matcher(url);
        if (matcher44.find()) {     //账号明细
            if (Util.loginChecked((Activity) context, 999)) {
                Intent intent = new Intent(context, PartnerAccountDetailActivity.class);
                context.startActivity(intent);
            }
            return true;
        }

        Matcher matcher45 = Pattern.compile("/to/partner/mine").matcher(url);
        if (matcher45.find()) {     //我的买手
            if (Util.loginChecked((Activity) context, 999)) {
                Intent intent = new Intent(context, PartnerCenterActivity1.class)
                        .putExtra("position",3)
                        .putExtra("isTabGone", true);
                context.startActivity(intent);
            }
            return true;
        }
        ///coupondef/buynow?defId=xxx
        Matcher matcher46 = Pattern.compile("/coupondef/buynow\\?defId=(\\d+)").matcher(url);
        if (matcher46.find()){
            if (Util.loginChecked((Activity) context,999)){
                int couponId = Integer.parseInt(matcher46.group(1));
                Intent intent=new Intent(context, CashierActivity.class);
                intent.putExtra("isCouponPay",true);
                intent.putExtra("payType","coupon");
                intent.putExtra("couponId",couponId);
                context.startActivity(intent);
            }
            return true;
        }

        //拼团商城
        Matcher matcher47=Pattern.compile("/collage/products/list").matcher(url);
        if (matcher47.find()){
            Intent intent=new Intent(context, GroupBuyActivity.class);
            context.startActivity(intent);
            return true;
        }

        Matcher matcher48 = Pattern.compile("/to/partner/store").matcher(url);
        if (matcher48.find()) {     //买手店铺
            if (Util.loginChecked((Activity) context, 999)) {
                Intent intent = new Intent(context, PartnerCenterActivity1.class)
                        .putExtra("subPosition",1)
                        .putExtra("isTabGone", true);
                context.startActivity(intent);
            }
            return true;
        }

        Matcher matcher49 = Pattern.compile("/mycollage/list").matcher(url);
        if (matcher49.find()) {     //我的拼团列表
            if (Util.loginChecked((Activity) context, 999)) {
                Intent intent = new Intent(context, GroupBuyActivity.class).putExtra("position",1);
                context.startActivity(intent);
            }
            return true;
        }

       //   /collage/{promotionId}/{productId}
        Matcher matcher50 = Pattern.compile("/collage/(\\d+)/(\\d+)").matcher(url);
        if (matcher50.find()) {     //拼团商品详情页
                int collageId = Integer.valueOf(matcher50.group(1));
                long productId = Long.parseLong(matcher50.group(2));
                Intent intent = new Intent(context, ProductDetailActivity.class).putExtra("id",productId).putExtra("collageId",collageId);
                context.startActivity(intent);
            return true;
        }

        //   /point/shop
        Matcher matcher51 = Pattern.compile("/point/shop").matcher(url);
        if (matcher51.find()) {     //积分商城
            Intent intent = new Intent(context, DCoinShopActivity.class);
            context.startActivity(intent);
            return true;
        }

        //   /dcion/shop/product/xxx
        Matcher matcher52 = Pattern.compile("/dcion/shop/productpoint/(\\d+)").matcher(url);
        if (matcher52.find()) {     //积分商品详情
            int dCproductId = Integer.valueOf(matcher52.group(1));
            Intent intent = new Intent(context, DCoinProductDetaiActivity.class).putExtra("id",dCproductId);
            context.startActivity(intent);
            return true;
        }

        Matcher matcher53 = Pattern.compile("/dress/wear").matcher(url);
        if (matcher53.find()) {     //积分商品详情
            Intent intent = new Intent(context, FashionMatchActivity.class);
            context.startActivity(intent);
            return true;
        }

        if (!isWeb) {
            Matcher mat110 = Pattern.compile("^ (.*d2cmall.com)?(.*)$").matcher(url);
            //调invoke请求
            if (mat110.matches() && !Util.isEmpty(mat110.group(1))) {
                url = mat110.group(2);
            }
            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra("url", url);
            intent.putExtra("type", 0);//type=0调invoke接口，type=1保持url原样
            intent.putExtra("isShareGone", isShareGone);
            intent.putExtra("log", log);
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
            return true;
        }
        return false;
    }

    public static String hidePhoneNum(String number) {
        if (!Util.isEmpty(number) && number.length() >= 5) {
            int size = number.length();
            StringBuilder buffer = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                buffer.append(number.charAt(i));
            }
            buffer.append("****");
            for (int i = size - 4; i < size; i++) {
                buffer.append(number.charAt(i));
            }
            return buffer.toString();
        }
        return number;
    }

    public static String hideLastFour(String number) {
        if (!Util.isEmpty(number)) {
            int size = number.length();
            StringBuilder buffer = new StringBuilder();
            for (int i = 0; i < size; i++) {
                if (i > size - 5 && i < size) {
                    buffer.append("*");
                } else {
                    buffer.append(number.charAt(i));
                }
            }
            return buffer.toString();
        }
        return number;
    }

    public static Size getImageSizeFromPath(String path) {
        Size size = new Size();
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, opts);
        int degree = getOrientation(path);
        if (degree % 180 != 0) {
            size.setHeight(opts.outWidth);
            size.setWidth(opts.outHeight);
            return size;
        }
        size.setHeight(opts.outHeight);
        size.setWidth(opts.outWidth);
        return size;
    }

    public static String getPageVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static float px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxValue / scale);
    }

    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    //获取当前的网络状态  -1：没有网络  1：WIFI网络2：移动网络
    public static int getAPNType(Context context) {

        int netType = -1;

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }

        int nType = networkInfo.getType();

        if (nType == ConnectivityManager.TYPE_MOBILE) {

            netType = 2;

        } else if (nType == ConnectivityManager.TYPE_WIFI) {

            netType = 1;

        }

        return netType;

    }

    public static boolean checkNetwork(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static String getIp(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    public static String getNetType(Context context) {
        String netType = "NONE";
        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                netType = "WIFI";
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                switch (info.getSubtype()) {
                    case Constants.NETWORK_TYPE.NETWORK_TYPE_GPRS:
                    case Constants.NETWORK_TYPE.NETWORK_TYPE_EDGE:
                    case Constants.NETWORK_TYPE.NETWORK_TYPE_CDMA:
                    case Constants.NETWORK_TYPE.NETWORK_TYPE_1xRTT:
                    case Constants.NETWORK_TYPE.NETWORK_TYPE_IDEN:
                        netType = "CELL-2";
                        break;
                    case Constants.NETWORK_TYPE.NETWORK_TYPE_UMTS:
                    case Constants.NETWORK_TYPE.NETWORK_TYPE_EVDO_0:
                    case Constants.NETWORK_TYPE.NETWORK_TYPE_EVDO_A:
                    case Constants.NETWORK_TYPE.NETWORK_TYPE_HSDPA:
                    case Constants.NETWORK_TYPE.NETWORK_TYPE_HSUPA:
                    case Constants.NETWORK_TYPE.NETWORK_TYPE_HSPA:
                    case Constants.NETWORK_TYPE.NETWORK_TYPE_EVDO_B:
                    case Constants.NETWORK_TYPE.NETWORK_TYPE_EHRPD:
                    case Constants.NETWORK_TYPE.NETWORK_TYPE_HSPAP:
                        netType = "CELL-3";
                        break;
                    case Constants.NETWORK_TYPE.NETWORK_TYPE_LTE:
                        netType = "CELL-4";
                        break;
                    default:
                        netType = "CELL";
                        break;
                }
            }
        }
        return netType;
    }

    public static String Utf8Decode(String strUtf8) {
        try {
            return URLDecoder.decode(strUtf8, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            if (ai != null) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        return apiKey;
    }

    //计算字符串的长度，英文当一字符，中文当两字符
    public static long calculateLength(CharSequence c) {
        double len = 0;
        for (int i = 0; i < c.length(); i++) {
            int tmp = (int) c.charAt(i);
            if (tmp > 0 && tmp < 127) {
                len++;
            } else {
                len = len + 2;
            }
        }
        return Math.round(len);
    }

    public static void showPush(final Context context, final View pushView, final PushBean pushBean) {
        pushView.startAnimation(AnimUtil.getAnimationToastIn(context));
        pushView.setVisibility(View.VISIBLE);
        ImageView imgHead = (ImageView) pushView.findViewById(R.id.img_head);
        TextView tvContent = (TextView) pushView.findViewById(R.id.tv_content);
        UniversalImageLoader.displayImage(context, pushBean.getMessageContent().getHeadPic(), imgHead, R.mipmap.ic_default_avatar);
        tvContent.setText(Html.fromHtml(context.getString(R.string.label_show_toast,
                pushBean.getMessageContent().getProvince(), pushBean.getMessageContent().getMinutes(),
                getSomeContent(pushBean.getMessageContent().getDesignerName(), 8))));

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    pushView.startAnimation(AnimUtil.getAnimationToastOut(context));
                    pushView.setVisibility(View.GONE);
                }
            }
        };
        pushView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.urlAction(context, pushBean.getMessageContent().getUrl());
            }
        });
        handler.sendEmptyMessageDelayed(new Message().what = 1, 5000);
    }

    public static String getDeviceModel() {
        return Build.MANUFACTURER + "-" + Build.MODEL;
    }

    //获取设备唯一标识
    public synchronized static String getDeviceId(Context mContext) {
        String ANDROID_ID = Settings.System.getString(mContext.getContentResolver(), Settings.System.ANDROID_ID);
        return ANDROID_ID;
    }

    /**
     * 获取手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机系统版本号
     */
    public static String getDeviceVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取当前手机系统语言。
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前时区
     */
    public static String getCurrentTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        String strTz = tz.getDisplayName(false, TimeZone.SHORT);
        return strTz;
    }

    /**
     * 获取手机MAC地址
     */
    public static String getDeviceMAC() {
        String macSerial = null;
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);


            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }
        return macSerial;
    }

    public static String getBlueteethMessagerError(RequestError requestError) {
        String msg = "请求失败";
        if (requestError == NO_CONNECTION) {
            msg = "未连接到设备";
        } else if (requestError == RequestError.TIMEOUT) {
            msg = "请求超时";
        } else if (requestError == RequestError.ERROR_SEND) {
            msg = "发送失败";
        } else if (requestError == RequestError.ERROR_RESPONSE) {
            msg = "读取设备数据错误";
        } else if (requestError == RequestError.CONTEXT_RECYCLED) {
            msg = "上下文(Context)被回收";
        } else if (requestError == RequestError.UNSUPPORTED_DEVICE) {
            msg = "不支持的设备";
        } else if (requestError == RequestError.UNKNOWN) {
            msg = "请求失败";
        }
        return msg;
    }

    public static String getUserInfoData(String nickName, String phone, String id, String email, String sex) {
        List<YSFItemBean> list = new ArrayList<>();
        YSFItemBean bean = new YSFItemBean();
        bean.key = "real_name";
        if (!isEmpty(nickName)) {
            bean.value = nickName;
        } else {
            bean.value = "匿名_" + id;
        }
        list.add(bean);
        if (!isEmpty(phone)) {
            bean = new YSFItemBean();
            bean.key = "mobile_phone";
            bean.value = phone;
            list.add(bean);
        }
        if (!isEmpty(id)) {
            bean = new YSFItemBean();
            bean.key = "account";
            bean.index = 0;
            bean.label = "账号";
            bean.value = id;
            list.add(bean);
        }
        if (!isEmpty(email)) {
            bean = new YSFItemBean();
            bean.key = "email";
            bean.value = email;
            list.add(bean);
        }
        if (!isEmpty(sex)) {
            bean = new YSFItemBean();
            bean.key = "sex";
            bean.index = 1;
            bean.label = "性别";
            bean.value = sex.equals("男") ? "先生" : "女士";
            list.add(bean);
        }
        bean = new YSFItemBean();
        bean.key = "device";
        bean.index = 2;
        bean.label = "登录设备";
        bean.value = Build.MODEL;
        list.add(bean);

        bean = new YSFItemBean();
        bean.key = "version";
        bean.index = 3;
        bean.label = "版本号";
        bean.value = Util.getPageVersionName(AppProfile.getContext());
        list.add(bean);

        Gson gson = new Gson();
        String data = gson.toJson(list, new TypeToken<List<YSFItemBean>>() {
        }.getType());
        return data;
    }

    public static String extractPathWithoutSeparator(String url) {
        return url.substring(0, url.lastIndexOf("/"));
    }

    public static double calculate(int payAmount, int count, double rate) {
        BigDecimal eachPrin = BigDecimal.valueOf(payAmount).divide(new BigDecimal(count), BigDecimal.ROUND_DOWN);//本金
        BigDecimal totalFeeInDecimal = BigDecimal.valueOf(payAmount).multiply(BigDecimal.valueOf(rate));
        long totalFeeInLong = totalFeeInDecimal.setScale(0, BigDecimal.ROUND_HALF_EVEN).longValue();
        BigDecimal eachFee = BigDecimal.valueOf(totalFeeInLong).divide(new BigDecimal(count), BigDecimal.ROUND_DOWN);
        BigDecimal prinAndFee = eachFee.add(eachPrin);
        return prinAndFee.doubleValue() / 100;//每期费用
    }

    /**
     * 获取当前网络状态 判断是否是无限网
     *
     * @param context
     * @return
     */
    public static int getNetWorkType(Context context) {
        int type = 0;
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getType() == ConnectivityManager.TYPE_ETHERNET || info.getType() == ConnectivityManager.TYPE_WIMAX || info.getType() == ConnectivityManager.TYPE_WIFI) {
                    type = INFINITE;
                } else {
                    type = LIMITED;
                }
            }
        }
        return type;
    }

    public static String formatUrlMap(Map<String, Object> paraMap,
                                      boolean urlEncode, boolean keyToLower) {
        String buff = "";
        Map<String, Object> tmpMap = paraMap;
        try {
            List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(
                    tmpMap.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds,
                    new Comparator<Map.Entry<String, Object>>() {

                        @Override
                        public int compare(Map.Entry<String, Object> o1,
                                           Map.Entry<String, Object> o2) {
                            return (o1.getKey()).toString().compareTo(
                                    o2.getKey());
                        }
                    });
            // 构造URL 键值对的格式
            StringBuilder buf = new StringBuilder();
            for (Map.Entry<String, Object> item : infoIds) {
                if (item.getKey() != null) {
                    String key = item.getKey();
                    Object val = item.getValue();
                    if (urlEncode && val instanceof String) {
                        val = Uri.encode((String) val, "utf-8");
                    }
                    if (keyToLower) {
                        buf.append(key.toLowerCase() + "=" + val);
                    } else {
                        buf.append(key + "=" + val);
                    }
                    buf.append("&");
                }

            }
            buff = buf.toString();
            if (buff.isEmpty() == false) {
                buff = buff.substring(0, buff.length() - 1);
            }
        } catch (Exception e) {
            return null;
        }
        return buff;
    }

    public static String join(Object[] array, String separator) {
        return array == null ? null : join(array, separator, 0, array.length);
    }

    public static String join(Object[] array, String separator, int startIndex, int endIndex) {
        if (array == null) {
            return null;
        } else {
            if (separator == null) {
                separator = "";
            }

            int noOfItems = endIndex - startIndex;
            if (noOfItems <= 0) {
                return "";
            } else {
                StringBuilder buf = new StringBuilder(noOfItems * 16);

                for (int i = startIndex; i < endIndex; ++i) {
                    if (i > startIndex) {
                        buf.append(separator);
                    }

                    if (array[i] != null) {
                        buf.append(array[i]);
                    }
                }

                return buf.toString();
            }
        }
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        String REGEX_ID_CARD = "(^\\d{17}(\\d|X)$)|(^\\d{15}$)";
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }


    public static boolean checkPw(String pw){
        if (isEmpty(pw)){
            return false;
        }
        if (check1(pw)){
            return true;
        }
        return false;
    }

    /**
     * 初步检查位数和是否纯数字纯字母
     * @param pw
     * @return
     */
    public static boolean check1(String pw){
        if (pw.length()<8||pw.length()>20) {
            return false;
        }
        Matcher matcher=Pattern.compile("[^a-zA-Z0-9]+[a-zA-Z0-9]+|[a-zA-Z0-9]+[^a-zA-Z0-9]+").matcher(pw);
        if (!matcher.find()) {
            matcher=Pattern.compile("\\d+\\D+|\\D+\\d+").matcher(pw);
            if (!matcher.find()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否存在3个相同字符或连续字符
     * @return
     */
    public static boolean check3(String pw) {
        boolean result=true;
        char[] chars=pw.toCharArray();
        int size=chars.length;
        int[] num=new int[size];
        for (int i = 0; i < size; i++) {
            char c = chars[i];
            num[i]=(int)c;
        }
        for (int i = 0; i < size-2; i++) {
            if (num[i]==num[i+1]&&num[i+1]==num[i+2]) {
                result=false;
                break;
            }
            if (num[i]==num[i+1]-1&&num[i+1]==num[i+2]-1) {
                result=false;
                break;
            }
        }
        return result;
    }

    /**
     * 是否存在键盘连续字符
     * @param pw
     * @return
     */
    public static boolean check2(String pw) {
        Matcher matcher=Pattern.compile("qwe|asd|zxc").matcher(pw);
        if (matcher.find()) {
            return false;
        }else {
            return true;
        }
    }

    public static void getRunningAppProcessInfo(Context context) {

        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        //获得系统里正在运行的所有进程

        List<ActivityManager.RunningAppProcessInfo> runningAppProcessesList = mActivityManager.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcessesList) {          // 进程ID号

            int pid = runningAppProcessInfo.pid;

            // 用户ID          i

            int uid = runningAppProcessInfo.uid;

            // 进程名

            String processName = runningAppProcessInfo.processName;

// 占用的内存

            int[] pids = new int[]{pid};

            Debug.MemoryInfo[] memoryInfo = mActivityManager.getProcessMemoryInfo(pids);

            int memorySize = memoryInfo[0].dalvikPrivateDirty;
            Log.d("han","processName=" + processName + ",pid=" + pid + ",uid=" + uid + ",memorySize=" + memorySize + "kb="+memorySize/1024+"Mb");
        }
    }

    //获得独一无二的Psuedo ID
    public static String getUniquePsuedoID() {
        String serial = null;

        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                Build.USER.length() % 10; //13 位

        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();
            //API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            //serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        }
        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }
}