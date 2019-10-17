package com.d2cmall.buyer.shareplatform;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.ShareBean;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.Util;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXFileObject;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;

/**
 * @author hrb
 * @Description: 微信登陆 分享 辅助类
 * @date 2015-8-4 下午5:02:56
 */
public class WxHandle {
    private static WxHandle instance;
    private IWXAPI mApp;
    private String defaultWebUrl = "http://www.d2cmall.com/";
    private String webUrl = defaultWebUrl;
    private String title;
    private String des;
    private static byte[] lock = new byte[1];

    public static WxHandle getInstance(Context context) {
        if (instance == null) {
            synchronized (lock) {
                instance = new WxHandle(context);
            }
        }
        return instance;
    }

    private WxHandle(Context context) {
        mApp = WXAPIFactory.createWXAPI(context, Constants.WEIXINAPPID, false);
        mApp.registerApp(Constants.WEIXINAPPID);
    }

    /**
     * 微信登陆
     *
     * @param context
     */
    public void login(BaseActivity context) {
        if (!isAppInstalled()) {
            Util.showToast(context, "未安装微信");
            return;
        }
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_login";
        mApp.sendReq(req);
    }

    /**
     * 分享到微信
     */
    public void sendShare(Context context, Bitmap bitmap, int scene) {
        if (!isAppInstalled()) {
            Util.showToast(context, "请先安装微信");
            return;
        }
        WXWebpageObject webObject = new WXWebpageObject();
        webObject.webpageUrl = webUrl;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = webObject;
        msg.setThumbImage(bitmap);
        msg.description = des;
        msg.title = title;
        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene = scene;
        // 调用api接口发送数据到微信
        if (scene == SendMessageToWX.Req.WXSceneTimeline) {
            if (isSupportAPI()) {
                mApp.sendReq(req);
            }
        } else {
            mApp.sendReq(req);
        }
    }

    /**
     * 图文分享到微信
     */
    public void sendShare(Context context, byte[] data, int scene) {
        if (!isAppInstalled()) {
            Toast.makeText(context, "请先安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        WXMediaMessage msg = new WXMediaMessage();
        WXWebpageObject webObject = new WXWebpageObject();
        webObject.webpageUrl = webUrl;
        msg.mediaObject = webObject;
        msg.thumbData = data;
        msg.title = title;
        msg.description = des;
        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("D2C"); // transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene = scene;
        // 调用api接口发送数据到微信
        if (scene == SendMessageToWX.Req.WXSceneTimeline) {
            if (isSupportAPI()) {
                mApp.sendReq(req);
            }
        } else {
            mApp.sendReq(req);
        }
    }

    /**
     * 分享小程序邀请成为买手
     *
     * @param context
     * @param data
     * @param id
     */
    public void shareMiniApps(Context context, byte[] data, String title, String dec, long id, int type,String avatar) {//type=0是买手 type=1是DM
        if (!isAppInstalled()) {
            Toast.makeText(context, "请先安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.userName = "gh_4104e9b4b64c";     // 小程序原始id
        if (type == 0) {
            miniProgramObj.path = "/pages/intro/joinRecommender?parent_id=" + id+"&avatar="+avatar;            //小程序页面路径买手
            miniProgramObj.webpageUrl = "https://m.d2cmall.com/partner/joinbuyer?parent_id=" + id+"&avatar="+avatar; // 兼容低版本的网页链接买手
        } else {
            miniProgramObj.path = "/pages/intro/joinPartner?parent_id=" + id+"&avatar="+avatar;       //小程序页面路径DM
            miniProgramObj.webpageUrl = "https://m.d2cmall.com/partner/joinpartner?parent_id=" + id+"&avatar="+avatar; // 兼容低版本的网页链接DM
        }


        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = title;                    // 小程序消息title
        msg.description = dec;               // 小程序消息desc
        msg.thumbData = data;                      // 小程序消息封面图片，小于128k

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前支持会话
        mApp.sendReq(req);
    }

    /**
     * 分享小程序邀请新人
     *
     * @param context
     * @param data
     * parent_id=1&name=&avatar=

     */
    public void miniAppInviteNew(Context context, byte[] data, String title, String dec, String params) {
        if (!isAppInstalled()) {
            Toast.makeText(context, "请先安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.userName = "gh_849add8a27d0";     // 小程序原始id
        miniProgramObj.path = "pages/invite/index?"+params;
        miniProgramObj.webpageUrl = "https://m.d2cmall.com/page/xinren?" +params; // 兼容低版本的网页链接


        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = title;                    // 小程序消息title
        msg.description = dec;               // 小程序消息desc
        msg.thumbData = data;                      // 小程序消息封面图片，小于128k

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前支持会话
        mApp.sendReq(req);
    }

    /**
     * 分享小程序商品详情
     *
     * @param context
     * @param data
     * @param id
     */
    public void shareProductMiniApps(Context context, byte[] data, String title, String dec, long id, int type) {//type=0是买手 type=1是DM
        if (!isAppInstalled()) {
            Toast.makeText(context, "请先安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.userName = "gh_4104e9b4b64c";     // 小程序原始id
        miniProgramObj.path = "/pages/product/detail?id=" + id;            //小程序页面路径买手

        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = title;                    // 小程序消息title
        msg.description = dec;               // 小程序消息desc
        msg.thumbData = data;                      // 小程序消息封面图片，小于128k

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前支持会话
        mApp.sendReq(req);
    }

    /**
     * 分享小程序拼团详情
     *
     * @param context
     * @param data
     */
    public void shareCollageProductMiniApps(Context context, byte[] data, String title, String dec,  String url) {
        if (!isAppInstalled()) {
            Toast.makeText(context, "请先安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.userName = "gh_4104e9b4b64c";     // 小程序原始id
        miniProgramObj.path = "url";            //小程序页面路径买手

        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = title;                    // 小程序消息title
        msg.description = dec;               // 小程序消息desc
        msg.thumbData = data;                      // 小程序消息封面图片，小于128k

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前支持会话
        mApp.sendReq(req);
    }

    /**
     * 分享小程序店铺详情
     *
     * @param context
     * @param data
     * @param id
     */
    public void shareBrandMiniApps(Context context, byte[] data, String title, String dec, long id, int type) {//type=0是买手 type=1是DM
        if (!isAppInstalled()) {
            Toast.makeText(context, "请先安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.userName = "gh_4104e9b4b64c";     // 小程序原始id
        miniProgramObj.path = "/pages/brand/detail?id=" + id;            //小程序页面路径


        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = title;                    // 小程序消息title
        msg.description = dec;               // 小程序消息desc
        msg.thumbData = data;                      // 小程序消息封面图片，小于128k

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前支持会话
        mApp.sendReq(req);
    }

    /**
     * 分享小程序
     *
     * @param context
     * @param path  小程序路径
     * @param dec   分享小程序的描述
     * @param data  分享小程序的图片byte数组
     */
    public void shareMiniProject(Context context,String path,String dec,byte[] data,String webUrl,boolean isbuyer) {//isbuyer: appId是买手的还是 D2C
        if (!isAppInstalled()) {
            Toast.makeText(context, "请先安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.userName = isbuyer?"gh_4104e9b4b64c":"gh_849add8a27d0";     // 小程序原始id
        miniProgramObj.path = path;

        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = dec;                    // 小程序消息title
        msg.description = dec;               // 小程序消息desc
        msg.thumbData = data;                      // 小程序消息封面图片，小于32K
        miniProgramObj.webpageUrl = webUrl;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前支持会话
        mApp.sendReq(req);
    }

    /**
     * 纯图片分享到微信
     */
    public void sendPicShare(Context context, byte[] data, int scene) {
        if (!isAppInstalled()) {
            Toast.makeText(context, "请先安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        WXMediaMessage msg = new WXMediaMessage();
        WXImageObject wxImageObject = new WXImageObject(data);
        msg.mediaObject = wxImageObject;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene = scene;
        // 调用api接口发送数据到微信
        if (scene == SendMessageToWX.Req.WXSceneTimeline) {
            if (isSupportAPI()) {
                mApp.sendReq(req);
            }
        } else {
            mApp.sendReq(req);
        }
    }

    /**
     * 分享到微信
     */
    public void sendPicShare(Context context, WXMediaMessage msg, int scene) {
        if (!isAppInstalled()) {
            Util.showToast(context, "请先安装微信");
            return;
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene = scene;
        // 调用api接口发送数据到微信
        if (scene == SendMessageToWX.Req.WXSceneTimeline) {
            if (isSupportAPI()) {
                mApp.sendReq(req);
            }
        } else {
            mApp.sendReq(req);
        }
    }

    //分享视频
    public void shareVideo(Context context, String vedioUrl) {
        if (!isAppInstalled()) {
            Util.showToast(context, "请先安装微信");
            return;
        }

        WXVideoObject videoObject = new WXVideoObject();
//        2、设置视频url
        videoObject.videoUrl = vedioUrl;
//        3、创建WXMediaMessage对象，
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = videoObject;
        msg.title = "内涵段子";
        msg.description = "很搞笑哦";
//        4、设置缩略图
//        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), android.R.drawable.dialog_frame);
//        msg.thumbData = bitmapToByteArray(bitmap, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.transaction = buildTransaction("video");
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        mApp.sendReq(req);

    }

    public void shareLocalVideo(Context context, File path) {
        if (!isAppInstalled()) {
            Util.showToast(context, "请先安装微信");
            return;
        }
        Log.d("doInBackground", "doInBackground" + path);
        WXFileObject textObject = new WXFileObject();
        textObject.filePath = path.getAbsolutePath();
        textObject.fileData = null;
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObject;
        msg.title = "a";
        msg.description = "b";
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        req.message = msg;
        mApp.sendReq(req);
    }

    public boolean isAppInstalled() {
        return mApp.isWXAppInstalled();
    }

    public boolean isSupportAPI() {
        return mApp.isWXAppSupportAPI();
    }

    private String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        if (Util.isEmpty(webUrl)) {
            this.webUrl = defaultWebUrl;
        } else {
            this.webUrl = webUrl;
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void detach() {
        if (mApp != null) {
            mApp.detach();
        }
    }
}
