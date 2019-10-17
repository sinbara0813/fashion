package com.d2cmall.buyer.util;

import android.os.Handler;
import android.os.Message;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.bean.UpdateBean;
import com.d2cmall.buyer.bean.UpdateInfo;
import com.d2cmall.buyer.http.BeanRequest;
import com.google.gson.Gson;


public class UpdateManager {

    private UpdateInfo info;
    private Handler mHandler;
    private Back back;

    public void checkHomeUpdate() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 200:
                        if (back != null) {
                            if (info != null) {
                                back.back(info.getInfo(), info.isMust().equals("1"),info.getUrl());
                            }else {
                                back.noUpdate();
                            }
                        }
                        break;
                    case 202:
                        break;
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                requestHomeUpdateInfo();
            }
        }).start();
    }

    private void requestHomeUpdateInfo() {
        SimpleApi api = new SimpleApi();
        //channel
        api.setInterPath(Constants.UPDATE_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<UpdateBean>() {
            @Override
            public void onResponse(UpdateBean response) {
                if (response.getData().getIsUpgrade() == 1) {
                    String lastVersion = D2CApplication.mSharePref.getSharePrefString(SharePrefConstant.LAST_UPDATE_VERSION, "");
                    String second = response.getData().getValue();
                    Gson json = new Gson();
                    try {
                        info = json.fromJson(second, UpdateInfo.class);
                    } catch (Exception e) {
                        return;
                    }
                    if (info != null && info.getLasted() != null) {
                        D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.SHOW_UPDATE_TAG, true);
                        if (!lastVersion.equals(info.getLasted())) {
                            D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.UPDATE, true);
                        }
                        D2CApplication.mSharePref.putSharePrefString(SharePrefConstant.LAST_UPDATE_VERSION, info.getLasted());
                        D2CApplication.mSharePref.putSharePrefString(SharePrefConstant.UPDATE_CONTENT, info.getInfo());
                    } else {
                        D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.SHOW_UPDATE_TAG, false);
                    }
                } else {
                    D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.SHOW_UPDATE_TAG, false);
                }
                mHandler.sendEmptyMessage(200);
            }
        });
    }

    public void addBack(Back back) {
        this.back = back;
    }

    public interface Back {
        void back(String name, boolean is,String url);
        void noUpdate();
    }
}
