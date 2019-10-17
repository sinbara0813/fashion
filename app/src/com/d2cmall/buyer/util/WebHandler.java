package com.d2cmall.buyer.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.Explore1PublishActivity;
import com.d2cmall.buyer.activity.LoginActivity;
import com.d2cmall.buyer.activity.PublishProductReportActivity;
import com.d2cmall.buyer.bean.PosterBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.shareplatform.WxHandle;
import com.d2cmall.buyer.widget.SharePop;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * WebHandler
 * Author: Blend
 * Date: 2016/11/30 16:19
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class WebHandler implements BridgeHandler {

    public Context context;
    public String functionName;
    public static final int IMAGE_SIZE = 32768;
    private float scale = 0.8F;
    private UserBean.DataEntity.MemberEntity user;
    private boolean isLoadding;

    public WebHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handler(String data, CallBackFunction function) {
        try {
            JSONObject myJsonObject = new JSONObject(data);
            functionName = myJsonObject.optString("handlefunc");
            if (!Util.isEmpty(functionName)) {
                switch (functionName) {
                    case "w_login"://调起登录页
                        UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(context);
                        if (user == null) {
                            Intent intent = new Intent(context, LoginActivity.class);
                            ((Activity) context).startActivityForResult(intent, Constants.Login.WEB_LOGIN);
                            ((Activity) context).overridePendingTransition(R.anim.slide_in_up, R.anim.activity_anim_default);
                        }
                        break;
                    case "w_share"://调起分享POP
                        String url = myJsonObject.optString("URL");
                        String desc = myJsonObject.optString("desc");
                        String title = myJsonObject.optString("title");
                        String pic = myJsonObject.optString("pic");
                        String func = myJsonObject.optString("func");
                        SharePop sharePop = new SharePop(context);
                        sharePop.setTitle(title);
                        sharePop.setDescription(desc);
                        sharePop.setWebUrl(url);
                        if (url.contains("bargain")) {
                            sharePop.setPromotionLink(true, false);
                        }
                        if (!Util.isEmpty(pic)) {
                            sharePop.setImage(Util.getD2cPicUrl(pic, 100, 100), false);
                            sharePop.setImage(Util.getD2cPicUrl(pic, 360, 500), true);
                        }
                        sharePop.setFunc(func);
                        sharePop.setWebView(true);
                        sharePop.show(((Activity) context).getWindow().getDecorView());
                        break;
                    case "w_publish"://调起发布
                        Intent intent = new Intent(context, Explore1PublishActivity.class);
                        context.startActivity(intent);
                        break;
                    case "w_report": //调起商品报告
                        UserBean.DataEntity.MemberEntity user1 = Session.getInstance().getUserFromFile(context);
                        if (user1 == null || user1.getId() <= 0) {
                            Intent intent2 = new Intent(context, LoginActivity.class);
                            context.startActivity(intent2);
                        } else {
                            Intent intent1 = new Intent(context, PublishProductReportActivity.class);
                            try {
                                String id = myJsonObject.optString("productId");
                                String productPic = myJsonObject.optString("productPic");
                                String name = myJsonObject.optString("productName");
                                intent1.putExtra("productId", Long.valueOf(id));
                                intent1.putExtra("productImg", productPic);
                                intent1.putExtra("productName", name);
                                context.startActivity(intent1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case "w_bill":
                        Gson gson = new Gson();
                        Session.getInstance().setPosterBean(gson.fromJson(data, PosterBean.class));
                        break;
                    case "w_page":
                        Gson gson1 = new Gson();
                        Session.getInstance().setPosterBean(gson1.fromJson(data, PosterBean.class));
                        break;
                    case "w_giftShare":
                        inviteBuyer();
                        break;
                    case "w_pyq":   //网页直接分享到朋友圈,不调sharePop
                        String img = myJsonObject.optString("pic");
                        String text = myJsonObject.optString("text");
                        String title1 = myJsonObject.optString("title");
                        String url1 = myJsonObject.optString("url");
                        shareToWX(img, 0, text, url1,title1);
                        break;
                    case "w_wx":    //网页直接分享到微信好友不调sharePop
                        String img1 = myJsonObject.optString("pic");
                        String text1 = myJsonObject.optString("text");
                        String url2 = myJsonObject.optString("url");
                        String title2 = myJsonObject.optString("title");
                        shareToWX(img1, 1, text1, url2,title2);
                        break;

                }
            }
        } catch (JSONException e) {
        }
    }

    private void inviteBuyer() {

        SharePop sharePop1 = new SharePop(context);
        sharePop1.show(((Activity) context).getWindow().getDecorView());
        sharePop1.setTitle("邀你成为D2C时尚买手，分享好物，轻松赚钱");
        sharePop1.setProductShare(true);
        sharePop1.setDescription("邀你成为D2C时尚买手，分享好物，轻松赚钱");
        sharePop1.setImage("http://img.d2c.cn/app/a/18/05/08/mini.png", true);
        if (user == null) {
            user = Session.getInstance().getUserFromFile(context);
        }
        String avatar;
        String name = Util.toURLEncode(user.getNickname() == null ? "匿名_" + user.getMemberId() : user.getNickname());
        if (user != null && user.getHead() != null) {
            avatar = Util.toURLEncode(Util.getD2cPicUrl(user.getHead()));
        } else {
            avatar = Util.toURLEncode("http://d2c-app.b0.upaiyun.com/img/logo/android_default_avatar.png");
        }
        //设置小程序页面
        sharePop1.setMiniProjectPath("/pages/intro/joinRecommender?parent_id=" + user.getPartnerId() + "&name=" + name + "&avatar=" + avatar);
        //设置小程序低版本兼容网页
        sharePop1.setMiniWebUrl("/partner/joinbuyer?parent_id=" + user.getPartnerId() + "&name=" + name + "&avatar=" + avatar);
        //设置分享链接(短链)
        sharePop1.setWebUrl("/partner/joinbuyer?name=" + name + "&avatar=" + avatar);
        //设置小程序appId
        sharePop1.setAppIdIsBuyer(true);
        //加载小程序分享的图片
        Glide.with(context).load(Util.getD2cPicUrl("http://static.d2c.cn/other/share_buyer.jpg", ScreenUtil.getDisplayWidth(), ScreenUtil.getDisplayHeight())).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                byte[] bitmapData = BitmapUtils.getBitmapData(BitmapUtils.getScaleBitmap(resource, scale, scale));
                while (bitmapData.length > IMAGE_SIZE) {
                    scale -= 0.1;
                    Bitmap scaleBitmap = BitmapUtils.getScaleBitmap(resource, scale, scale);
                    bitmapData = BitmapUtils.getBitmapData(scaleBitmap);
                    scaleBitmap.recycle();
                }
                sharePop1.setMiniPicData(bitmapData);
                sharePop1.show(((Activity) context).getWindow().getDecorView());
            }
        });
    }

    private void shareToWX(String pic, int channel, String text, String url,String title) { //channel=0分享到朋友圈,1分享到微信好友
        if (Util.isEmpty(pic)) {
            return;
        }
        Glide.with(context).load(Util.getD2cPicUrl(pic + Constants.MY_SUFFIX)).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Bitmap bitmap = resource.copy(Bitmap.Config.RGB_565, true);
                File file = (saveFile(bitmap));
                byte[] imageData= BitmapUtils.getBitmapData(resource);
                while (imageData.length > IMAGE_SIZE) {
                    scale -= 0.1;
                    Bitmap scaleBitmap = BitmapUtils.getScaleBitmap(resource, scale, scale);
                    imageData = BitmapUtils.getBitmapData(scaleBitmap);
                    scaleBitmap.recycle();
                }
                if (imageData == null || imageData.length == 0) {
                    imageData = BitmapUtils.getBitmapData(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_app));
                }
                if (channel == 0) {
                    try {
                        if (!Util.isEmpty(text) && !Util.isEmpty(url)) {//图文链接分享
                            WxHandle.getInstance(context).setWebUrl(url);
                            WxHandle.getInstance(context).setTitle(title);
                            WxHandle.getInstance(context).setDes(text);
                            WxHandle.getInstance(context).sendShare(context, imageData, SendMessageToWX.Req.WXSceneTimeline);
                        } else {//仅图片分享
                            Intent intent = new Intent();
                            ComponentName comp = new ComponentName("com.tencent.mm",
                                    "com.tencent.mm.ui.tools.ShareToTimeLineUI");
                            intent.setComponent(comp);
                            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                            intent.setType("image/*");

                            ArrayList<Uri> imageUris = new ArrayList<Uri>();
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                                imageUris.add(Uri.fromFile(file));
                            } else {
                                Uri uri = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), null));
                                imageUris.add(uri);
                            }
                            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                            context.startActivity(intent);
                        }

                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } finally {
                    }
                } else {
                    if (file == null) {
                        return;
                    }
                    if (!Util.isEmpty(text) && !Util.isEmpty(url)) {//图文链接分享
                        WxHandle.getInstance(context).setTitle(text);
                        WxHandle.getInstance(context).setWebUrl(url);
                        WxHandle.getInstance(context).setTitle(title);
                        WxHandle.getInstance(context).sendShare(context, imageData, SendMessageToWX.Req.WXSceneSession);
                    } else {//仅图片分享
                        WXImageObject wxImageObject = new WXImageObject();
                        wxImageObject.imagePath = file.getAbsolutePath();
                        WXMediaMessage msg = new WXMediaMessage();
                        msg.mediaObject = wxImageObject;
                        WxHandle.getInstance(context).sendPicShare(context, msg, SendMessageToWX.Req.WXSceneSession);
                    }

                }

            }
        });
    }

    private File saveFile(Bitmap bigBitmap) {
        if (bigBitmap.getByteCount() > 8485760) {
            float scale = 1;
            while (bigBitmap.getByteCount() > 8485760) {
                scale -= 0.2;
                bigBitmap = BitmapUtils.getScaleBitmap(bigBitmap, scale, scale);
            }
        }
        Canvas canvas = new Canvas(bigBitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        if (canvas.getWidth() <= 100) {
            bigBitmap.recycle();
            return null;
        }
        String root = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera";
        File file = null;
        file = new File(root, IMAGE_SIZE + ".jpg");
        if (file.exists()) {
            file.delete();
        }
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bigBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);

            FileOutputStream os = new FileOutputStream(file);
            os.write(stream.toByteArray());
            stream.flush();
            stream.close();
            os.flush();
            os.close();
            Util.UpdatePic(context, file);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (bigBitmap != null) {
                bigBitmap.recycle();
                bigBitmap = null;
            }
            canvas = null;
        }
        return file;
    }
}
