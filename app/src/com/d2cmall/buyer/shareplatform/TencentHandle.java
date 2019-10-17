/**
 *
 */
package com.d2cmall.buyer.shareplatform;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.shareplatform.tencent.TencentToken;
import com.d2cmall.buyer.util.Util;
import com.tencent.connect.UserInfo;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author hrb
 * @Description: qq登陆 分享辅助类
 * @date 2015-8-4 下午2:17:33
 */
public class TencentHandle {
    private final String TAG = TencentHandle.class.getSimpleName();
    private Tencent mTencent;

    /**
     * 初始化 mtencent
     *
     * @param context
     * @return
     */
    public TencentHandle initQQ(Context context) {
        if (mTencent == null) {
            mTencent = Tencent.createInstance(Constants.QQKEY, context.getApplicationContext());
        }
        return this;
    }

    /**
     * 某些低配置的机子用此方法 同时在 onActivityResult方法中回调Tencent.handleResultData(intent, IUiListener);
     *
     * @param context
     * @param listener
     */
    public void login(Activity context, IUiListener listener) {
        if (mTencent == null) {
            throw new NullPointerException("mTencent can not be null");
        }
        if (!isSessionValid()) {
            mTencent.login(context, "all", listener);
        }
    }

    /**
     * qq登出
     *
     * @param context
     */
    public void loginOut(Context context) {
        if (mTencent == null) {
            throw new NullPointerException("mTencent can not be null");
        }
        if (isSessionValid()) {
            mTencent.logout(context);
        }
    }

    /**
     * 登陆回调
     *
     * @author hrb
     * @date 2015-8-6 下午3:36:22
     */
    private class Baselistener implements IUiListener {
        private Context context;

        public Baselistener(Context context) {
            this.context = context;
        }

        @Override
        public void onCancel() {
            Log.d(TAG, "TencentHandle>>>login>>>onCancel");
        }

        @Override
        public void onComplete(Object response) {
            JSONObject json = (JSONObject) response;
            if (json == null || json.length() == 0) {
                Log.d(TAG, "TencentHandle>>>login>>>response is null");
                return;
            }
            doComplete(json);
        }

        public void doComplete(JSONObject json) {
            Log.d("han", json.toString());
            try {
                String token = json.getString("access_token");
                String expires = json.getString("expires_in");
                String openId = json.getString("openid");
                TencentToken tokener = new TencentToken();
                tokener.setAccess_token(token);
                tokener.setOpenid(openId);
                tokener.setExpires_in(expires);
                TokenKeeper.writeQQToken(context, tokener);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError arg0) {
            Log.d(TAG, "TencentHandle>>>login>>>onError");
        }
    }

    /**
     * 获取用户信息
     *
     * @param context
     * @param listener
     */
    public void getUserInfo(final Activity context, final IUiListener listener) {
        loginOut(context);
        if (!isSessionValid()) {
            login(context, new Baselistener(context) {
                @Override
                public void doComplete(JSONObject json) {
                    super.doComplete(json);
                    getQQUserInfo(context, listener);
                }

                @Override
                public void onCancel() {
                    super.onCancel();
                    listener.onCancel();
                }

                @Override
                public void onError(UiError arg0) {
                    super.onError(arg0);
                    listener.onError(arg0);
                }
            });
        } else {
            getQQUserInfo(context, listener);
        }
    }

    public void getQQUserInfo(Activity context, IUiListener listener) {
        UserInfo info = new UserInfo(context, mTencent.getQQToken());
        info.getUserInfo(listener);
    }

    /**
     * 分享到qq
     */
    public void shareToQQ(Activity activity, Bundle params) {
        if (mTencent == null) {
            throw new NullPointerException("mTencent can not be null");
        }

        mTencent.shareToQQ(activity, params, shareListener);
    }

    public void shareToQQ(Activity activity, Bundle params, IUiListener listener) {
        if (mTencent == null) {
            throw new NullPointerException("mTencent can not be null");
        }

        mTencent.shareToQQ(activity, params, listener);
    }

    /**
     * 分享到qq空间
     */
    public void shareToQzone(Activity activity, Bundle params) {
        if (mTencent == null) {
            throw new NullPointerException("mTencent can not be null");
        }
        mTencent.shareToQzone(activity, params, shareListener);
    }

    /**
     *
     * @param localPath 本地图片路径
     * @return
     */
    public Bundle getParamToQQImage(String localPath){
        Bundle params=new Bundle();
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, localPath);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "D2C");
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        return params;
    }

    /**
     *
     * @param title 标题
     * @param description 描述
     * @param webUrl 点击跳转的web地址
     * @param imageUrl 图片的地址
     * @return
     */
    public Bundle getParam(String title,String description,String webUrl,String imageUrl){
        Bundle params=new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
                QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        if (Util.isEmpty(description)) {
            description = "";
        }
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, description);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, webUrl);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "D2C");
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,
                QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        return params;
    }

    /**
     *  qq空间不支持纯图片分享
     * @param title 标题
     * @param description 描述
     * @param webUrl 点击跳转的web地址
     * @param imageUrl 图片的地址
     * @return
     */
    public Bundle getParamToZoneImage(String title,String description,String webUrl,String imageUrl){
        Bundle bundle=new Bundle();
        bundle.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,
                QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
        bundle.putString(QzoneShare.SHARE_TO_QQ_SUMMARY,
                description);
        bundle.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,
                webUrl);
        ArrayList<String> imageUrls = new ArrayList<>();
        imageUrls.add(imageUrl);
        bundle.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL,
                imageUrls);
        bundle.putString(QzoneShare.SHARE_TO_QQ_APP_NAME, "D2C");
        return bundle;
    }

    /**
     * 分享到qq空间
     */
    public void shareToQzone(Activity activity, Bundle params, IUiListener listener) {
        if (mTencent == null) {
            throw new NullPointerException("mTencent can not be null");
        }
        mTencent.shareToQzone(activity, params, listener);
    }

    /**
     * 分享回调
     */
    public IUiListener shareListener = new IUiListener() {

        @Override
        public void onError(UiError arg0) {
            Log.d(TAG, "TencentHandle>>>share>>>onError");
        }

        @Override
        public void onComplete(Object arg0) {
            Log.d(TAG, "TencentHandle>>>share>>>onComplete");
        }

        @Override
        public void onCancel() {
            Log.d(TAG, "TencentHandle>>>share>>>onCancel");
        }
    };

    /**
     * qq实例是否有效
     *
     * @return
     */
    public boolean isSessionValid() {
        return mTencent.isSessionValid();
    }

    public Tencent getTencent() {
        return mTencent;
    }
}
