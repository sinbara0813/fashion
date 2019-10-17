package com.d2cmall.buyer.shareplatform;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;

import java.util.List;
import java.util.Locale;


/**
 * @author hrb
 * @Description: 新浪微博登陆分享 辅助类
 * @date 2015-8-6 下午3:26:27
 */
public class SinaHandle {
    private final String TAG = SinaHandle.class.getSimpleName();
    private static SinaHandle mHandle;

    private SsoHandler mSsoHandler;
    private WbShareHandler shareHandler;
    private MyShareBackListener shareBackListener;
    private TextObject textObject;
    private ImageObject imageObject;
    private WebpageObject webpageObject;

    public SinaHandle() {
    }

    public static SinaHandle getInstance() {
        if (mHandle == null) {
            synchronized (SinaHandle.class) {
                mHandle = new SinaHandle();
            }
        }
        return mHandle;
    }

    public static boolean isWeiboInstalled(@NonNull Context context) {
        PackageManager pm;
        if ((pm = context.getApplicationContext().getPackageManager()) == null) {
            return false;
        }
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo info : packages) {
            String name = info.packageName.toLowerCase(Locale.ENGLISH);
            if ("com.sina.weibo".equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 微博登录
     *
     * @param activity
     * @param callBack
     */
    public void login(final Activity activity, final AuthCallBack callBack) {
        mSsoHandler = new SsoHandler(activity);
        WbAuthListener mAuthListener = new WbAuthListener(){
            @Override
            public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
                Log.e("han","成功授权");
                if (oauth2AccessToken.isSessionValid()){
                    callBack.success(oauth2AccessToken);
                }
                mSsoHandler=null;
            }

            @Override
            public void cancel() {
                Toast.makeText(activity,
                        "取消授权", Toast.LENGTH_LONG).show();
                mSsoHandler=null;
            }

            @Override
            public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
                Toast.makeText(activity,
                        "han" + wbConnectErrorMessage.getErrorMessage(), Toast.LENGTH_LONG).show();
                Log.e("Auth exception : ",wbConnectErrorMessage.getErrorMessage());
                mSsoHandler=null;
            }

        };
        if (isWeiboInstalled(activity)) {
            mSsoHandler.authorizeClientSso(mAuthListener);
        } else {
            mSsoHandler.authorizeWeb(mAuthListener);
        }
    }

    /**
     * 微博分享
     *
     * @param activity
     */
    public void share(Activity activity) {
        shareHandler = new WbShareHandler(activity);
        shareHandler.registerApp();
        sendMultiMessage();
    }

    public void sendMultiMessage() {
        if (textObject == null && imageObject == null) {
            return;
        }
        // 1. 初始化微博的分享消息
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        if (textObject != null) {
            weiboMessage.textObject = textObject;
        }
        if (imageObject != null) {
            weiboMessage.imageObject = imageObject;
        }
        if (webpageObject!=null){
            weiboMessage.mediaObject=webpageObject;
        }
        shareHandler.shareMessage(weiboMessage,false);//有客户端客户端分享没有则网页分享
    }

    /**
     * 登录需在当前activity的onActivityResult方法中调用此方法
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void authorizeCallBack(int requestCode, int resultCode, Intent data) {
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    public void doresultIntent(Intent intent){
        if (shareHandler!=null){
            if (shareBackListener==null){
                shareBackListener=new MyShareBackListener();
            }
            shareHandler.doResultIntent(intent,shareBackListener);
        }
    }

    public SinaHandle setTextObject(TextObject mTextObject) {
        this.textObject = mTextObject;
        return this;
    }

    public SinaHandle setImageObject(ImageObject mImageObject) {
        this.imageObject = mImageObject;
        return this;
    }

    public void setWebpageObject(WebpageObject webpageObject) {
        this.webpageObject = webpageObject;
    }

    public interface AuthCallBack {
        void success(Oauth2AccessToken token);
    }

    public class MyShareBackListener implements WbShareCallback {

        @Override
        public void onWbShareSuccess() {
            shareHandler=null;
        }

        @Override
        public void onWbShareCancel() {
            shareHandler=null;
        }

        @Override
        public void onWbShareFail() {
            shareHandler=null;
        }
    }
}
