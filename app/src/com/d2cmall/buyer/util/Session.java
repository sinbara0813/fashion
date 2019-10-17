package com.d2cmall.buyer.util;


import android.content.Context;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.PartnerMemberBean;
import com.d2cmall.buyer.bean.PopBean;
import com.d2cmall.buyer.bean.PosterBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.shareplatform.TokenKeeper;
import com.google.gson.Gson;
import com.qiyukf.unicorn.api.Unicorn;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import de.greenrobot.event.EventBus;

import static com.d2cmall.buyer.shareplatform.TokenKeeper.PREFERENCES_RONG_USER;
import static com.d2cmall.buyer.shareplatform.TokenKeeper.PREFERENCES_SINA;
import static com.d2cmall.buyer.shareplatform.TokenKeeper.PREFERENCES_WX;

public class Session {

    private UserBean.DataEntity.MemberEntity user;
    private PartnerMemberBean.DataBean.PartnerBean partnerBean;
    private PosterBean posterBean;

    private Session() {
    }

    public static Session getInstance() {
        return SessionHolder.INSTANCE;
    }

    public UserBean.DataEntity.MemberEntity getUserFromFile(Context context) {
        if (user == null) {
            try {
                InputStream in = context.openFileInput(Constants.USER_FILE);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;

                while ((length = in.read(buffer)) != -1) {
                    stream.write(buffer, 0, length);
                }

                Gson gson = new Gson();
                user = gson.fromJson(stream.toString(), UserBean.DataEntity.MemberEntity.class);
                in.close();
                stream.close();
            } catch (IOException e) {
                return null;
            }
        }

        return user;
    }

    public UserBean.DataEntity.MemberEntity getUser() {
        return user;
    }

    public void saveUserToFile(Context context, UserBean.DataEntity.MemberEntity user) {
        if (user == null) {
            return;
        }
        this.user = user;
        Gson gson = new Gson();
        String personJson = gson.toJson(user, UserBean.DataEntity.MemberEntity.class);
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(Constants.USER_FILE, Context.MODE_PRIVATE);
            if (fileOutputStream != null) {
                OutputStreamWriter out = new OutputStreamWriter(fileOutputStream);
                out.write(personJson);
                out.flush();
                out.close();
                fileOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PartnerMemberBean.DataBean.PartnerBean getPartnerFromFile(Context context) {
        if (partnerBean == null) {
            try {
                InputStream in = context.openFileInput(Constants.PARTNER_FILE);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;

                while ((length = in.read(buffer)) != -1) {
                    stream.write(buffer, 0, length);
                }

                Gson gson = new Gson();
                partnerBean = gson.fromJson(stream.toString(), PartnerMemberBean.DataBean.PartnerBean.class);
                in.close();
                stream.close();
            } catch (IOException e) {
                return null;
            }
        }
        return partnerBean;
    }

    public void savePartnerToFile(Context context, PartnerMemberBean.DataBean.PartnerBean partnerBean) {
        if (user == null) {
            return;
        }
        this.partnerBean = partnerBean;
        Gson gson = new Gson();
        String personJson = gson.toJson(partnerBean, PartnerMemberBean.DataBean.PartnerBean.class);
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(Constants.PARTNER_FILE, Context.MODE_PRIVATE);
            if (fileOutputStream != null) {
                OutputStreamWriter out = new OutputStreamWriter(fileOutputStream);
                out.write(personJson);
                out.flush();
                out.close();
                fileOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PopBean getPopFromFile(Context context) {
        try {
            InputStream in = context.openFileInput(Constants.POP_FILE);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;

            while ((length = in.read(buffer)) != -1) {
                stream.write(buffer, 0, length);
            }

            Gson gson = new Gson();
            PopBean popBean = gson.fromJson(stream.toString(), PopBean.class);
            in.close();
            stream.close();
            return popBean;
        } catch (IOException e) {
            return null;
        }
    }

    public void savePopToFile(Context context, PopBean popBean) {
        Gson gson = new Gson();
        String personJson = gson.toJson(popBean, PopBean.class);
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(Constants.POP_FILE, Context.MODE_PRIVATE);
            if (fileOutputStream != null) {
                OutputStreamWriter out = new OutputStreamWriter(fileOutputStream);
                out.write(personJson);
                out.flush();
                out.close();
                fileOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.HAS_POP,true);
            EventBus.getDefault().post(new ActionBean(Constants.ActionType.POP));
        }
    }

    public void logout(Context context) {
        context.deleteFile(Constants.USER_FILE);
        //TokenKeeper.clearToken(context,PREFERENCES_QQ);
        TokenKeeper.clearToken(context, PREFERENCES_RONG_USER);
        TokenKeeper.clearToken(context, PREFERENCES_SINA);
        TokenKeeper.clearToken(context, PREFERENCES_WX);
        D2CApplication.mSharePref.removeKey(SharePrefConstant.TOKEN);
        D2CApplication.mSharePref.removeKey(SharePrefConstant.PAY_MODE);
        D2CApplication.mSharePref.removeKey(SharePrefConstant.NO_NOTICE);
        D2CApplication.mSharePref.removeKey(SharePrefConstant.MAIN_SEARCH_KEY);
        D2CApplication.mSharePref.removeKey(SharePrefConstant.MAIN_SEARCH_ID);
        D2CApplication.mSharePref.removeKey(SharePrefConstant.IS_SHOW_LOGIN_PW_TIP);
        D2CApplication.mSharePref.removeKey(SharePrefConstant.IS_SHOW_PAY_PW_TIP);
        D2CApplication.mSharePref.removeKey(SharePrefConstant.HAS_BUYER_COUNT);
        D2CApplication.mSharePref.removeKey(SharePrefConstant.HAS_SHOW_BUYER_DIALOG);
        UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(context);
//        if (user != null) {
//            D2CApplication.mSharePref.putSharePrefString(SharePrefConstant.USER_NAME, user.getLoginCode());
//        }
        context.deleteFile(Constants.USER_FILE);
        context.deleteFile(Constants.HISTORY_SEARCH_FILE);
        context.deleteFile(Constants.ORDER_HISTORY_SEARCH_FILE);
        context.deleteFile(Constants.READED_GLOBAL_MSG_FILE);
        context.deleteFile(Constants.DELETED_GLOBAL_MSG_FILE);
        context.deleteFile(Constants.PARTNER_FILE);
        D2CApplication.ysfOptions.uiCustomization.rightAvatar = null;
        Unicorn.setUserInfo(null);
        this.user = null;
        EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.CARTNUM, 0));
        EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.LOGOUT));
        this.partnerBean=null;
    }

    private static class SessionHolder {
        private static final Session INSTANCE = new Session();
    }

    public PosterBean getPosterBean() {
        return posterBean;
    }

    public void setPosterBean(PosterBean posterBean) {
        this.posterBean = posterBean;
    }

    public PartnerMemberBean.DataBean.PartnerBean getPartnerBean() {
        return partnerBean;
    }
}
