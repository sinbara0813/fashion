package com.d2cmall.buyer.shareplatform;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.d2cmall.buyer.shareplatform.tencent.TencentToken;
import com.d2cmall.buyer.shareplatform.wx.WXToken;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/**
 * @author hrb
 * @Description: token保存工具类
 * @date 2015-8-4 下午2:03:35
 */
public class TokenKeeper {

    public static final String PREFERENCES_QQ = "tencent_sp";
    public static final String PREFERENCES_WX = "wx_sp";
    public static final String PREFERENCES_SINA = "sian_sp";
    public static final String PREFERENCES_RONG_USER = "rong_sp";
    public static final String KEY_UID = "uid";
    public static final String KEY_OPENID = "openid";
    public static final String KEY_ACCESS_TOKEN = "access_token";
    public static final String KEY_EXPIRES_IN = "expires_in";
    public static final String KEY_REFRESH_TOKEN = "refresh_token";
    public static final String Key_RC_TOKEN = "rc_token";
    public static final String KEY_RC_ID = "rc_id";
    public static final String KEY_USER_PHOTO = "user_photo";
    public static final String KEY_USER_NICK = "user_nick";
    public static final String KEY_USER_ID = "user_id";

    /**
     * 保存 Token 对象到 SharedPreferences。
     *
     * @param context 应用程序上下文环境
     * @param token   Token 对象
     */
    public static void writeQQToken(Context context, TencentToken token) {
        if (null == context || null == token) {
            return;
        }

        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_QQ,
                Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putString(KEY_OPENID, token.getOpenid());
        editor.putString(KEY_ACCESS_TOKEN, token.getAccess_token());
        String expires_in = token.getExpires_in();
        editor.putLong(KEY_EXPIRES_IN,
                System.currentTimeMillis() + Long.parseLong(expires_in) * 1000);
        editor.apply();
    }

    /**
     * 从 SharedPreferences 读取 TencentQQToken 信息。
     *
     * @param context 应用程序上下文环境
     * @return 返回 TencentQQToken 对象
     */
    public static TencentToken readQQToken(Context context) {
        if (null == context) {
            return null;
        }
        TencentToken token = new TencentToken();
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_QQ,
                Context.MODE_APPEND);
        token.setOpenid(pref.getString(KEY_OPENID, ""));
        token.setAccess_token(pref.getString(KEY_ACCESS_TOKEN, ""));
        long expires_in = (pref.getLong(KEY_EXPIRES_IN, -1) - System
                .currentTimeMillis()) / 1000;
        token.setExpires_in(Long.toString(expires_in));
        return token;
    }

    /**
     * 保存 Token 对象到 SharedPreferences。
     *
     * @param context 应用程序上下文环境
     * @param token   Token 对象
     */
    public static void writeWXToken(Context context, WXToken token) {
        if (null == context || null == token) {
            return;
        }
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_WX,
                Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putString(KEY_OPENID, token.getOpenid());
        editor.putString(KEY_ACCESS_TOKEN, token.getAccess_token());
        editor.putString(KEY_REFRESH_TOKEN, token.getRefresh_token());
        String expires_in = token.getExpires_in();
        editor.putLong(KEY_EXPIRES_IN,
                System.currentTimeMillis() + Long.parseLong(expires_in) * 1000);
        editor.apply();
    }

    /**
     * 从 SharedPreferences 读取 WXToken 信息。
     *
     * @param context 应用程序上下文环境
     * @return 返回 WXToken 对象
     */
    public static WXToken readWXToken(Context context) {
        if (null == context) {
            return null;
        }
        WXToken token = new WXToken();
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_WX,
                Context.MODE_APPEND);
        token.setOpenid(pref.getString(KEY_OPENID, ""));
        token.setAccess_token(pref.getString(KEY_ACCESS_TOKEN, ""));
        token.setRefresh_token(pref.getString(KEY_REFRESH_TOKEN, ""));
        long expires_in = (pref.getLong(KEY_EXPIRES_IN, -1) - System
                .currentTimeMillis()) / 1000;
        token.setExpires_in(Long.toString(expires_in));
        return token;
    }

    /**
     * 保存 Token 对象到 SharedPreferences。
     *
     * @param context 应用程序上下文环境
     * @param token   Token 对象
     */
    public static void writeSinaToken(Context context, Oauth2AccessToken token) {
        if (null == context || null == token) {
            return;
        }
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_SINA, Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putString(KEY_UID, token.getUid());
        editor.putString(KEY_ACCESS_TOKEN, token.getToken());
        editor.putString(KEY_REFRESH_TOKEN, token.getRefreshToken());
        editor.putLong(KEY_EXPIRES_IN, token.getExpiresTime());
        editor.apply();
    }

    /**
     * 从 SharedPreferences 读取 Oauth2AccessToken 信息。
     *
     * @param context 应用程序上下文环境
     * @return 返回 Oauth2AccessToken 对象
     */
    public static Oauth2AccessToken readSinaToken(Context context) {
        if (null == context) {
            return null;
        }
        Oauth2AccessToken token = new Oauth2AccessToken();
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_SINA, Context.MODE_APPEND);
        token.setUid(pref.getString(KEY_UID, ""));
        token.setToken(pref.getString(KEY_ACCESS_TOKEN, ""));
        token.setRefreshToken(pref.getString(KEY_REFRESH_TOKEN, ""));
        token.setExpiresTime(pref.getLong(KEY_EXPIRES_IN, 0));
        return token;
    }

    /**
     * 读取用户token
     *
     * @param context
     * @return
     */
    /*public static UserInfo readUserToken(Context context){
        if (null == context) {
            return null;
        }
        UserInfo token=new UserInfo();
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_RONG_USER, Context.MODE_PRIVATE);
        token.setUserId(pref.getString(KEY_USER_ID, ""));
        token.setNickName(pref.getString(KEY_USER_NICK, ""));
        token.setPhoto(pref.getString(KEY_USER_PHOTO, ""));
        token.setToken(pref.getString(KEY_ACCESS_TOKEN, ""));
        token.setRcToken(pref.getString(Key_RC_TOKEN, ""));
        token.setRcId(pref.getString(KEY_RC_ID, ""));
        token.setRefreshToken(pref.getString(KEY_REFRESH_TOKEN, ""));
        long expires_in = (pref.getLong(KEY_EXPIRES_IN, -1) - System
				.currentTimeMillis()) / 1000;
		token.setExpires_in(expires_in);
		return token;
	}*/
    public static void clearToken(Context context, String name) {
        if (name == null) {
            return;
        }
        if (!name.equals(PREFERENCES_QQ) && !name.equals(PREFERENCES_RONG_USER) && !name.equals(PREFERENCES_SINA) && !name.equals(PREFERENCES_WX)) {
            return;
        }
        SharedPreferences pref = null;
        if (name.equals(PREFERENCES_QQ)) {
            pref = context.getSharedPreferences(PREFERENCES_QQ,
                    Context.MODE_APPEND);
        } else if (name.equals(PREFERENCES_SINA)) {
            pref = context.getSharedPreferences(PREFERENCES_SINA, Context.MODE_APPEND);
        } else if (name.equals(PREFERENCES_WX)) {
            pref = context.getSharedPreferences(PREFERENCES_WX, Context.MODE_APPEND);
        } else {
            pref = context.getSharedPreferences(PREFERENCES_RONG_USER, Context.MODE_APPEND);
        }
        Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }
}
