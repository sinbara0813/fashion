package com.d2cmall.buyer.util;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.activity.SearchAdressActivity;
import com.d2cmall.buyer.api.ExplorePublishApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.AddressEntity;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.TopicBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.shareplatform.WxHandle;
import com.qiyukf.unicorn.api.Unicorn;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.tencent.smtt.sdk.QbSdk;
import com.tendcloud.appcpa.TalkingDataAppCpa;
import com.tendcloud.tenddata.TCAgent;
import com.upyun.library.common.Params;
import com.upyun.library.common.UploadEngine;
import com.upyun.library.listener.UpCompleteListener;
import com.upyun.library.listener.UpProgressListener;
import com.upyun.library.utils.Base64Coder;
import com.upyun.library.utils.UpYunUtils;
import com.zamplus.businesstrack.ZampAppAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/6/15 14:22
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class InitializeService extends IntentService {
    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.anly.githubapp.service.action.INIT";
    private AddressEntity address;
    private TopicBean.DataBean.TopicsBean.ListBean topicBean;

    public InitializeService() {
        super("InitializeService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
                performInit();
            } else if ("upload".equals(intent.getAction())) {
                final String path = intent.getStringExtra("url");
                String content = intent.getStringExtra("content");
                if (intent.hasExtra("topicBean")) {
                    topicBean = (TopicBean.DataBean.TopicsBean.ListBean) intent.getSerializableExtra("topicBean");
                }
                if (intent.hasExtra(SearchAdressActivity.ADDRESS)) {
                    address = (AddressEntity) intent.getSerializableExtra(SearchAdressActivity.ADDRESS);
                }
                long duration = intent.getLongExtra("duration", 0);
                String selectList = intent.getStringExtra("selectList");
                upload(selectList, path, content, duration);
            }
        }
    }

    public void upload(final String select, final String path, final String content, final long duration) {
        final UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(this);
        if (user == null) {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(path);
        int index = path.lastIndexOf(".");
        String suffix = path.substring(index, path.length());
        if (!file.exists()) {
            return;
        }
        final Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(Params.BUCKET, Constants.UPYUN_SPACE);
        paramsMap.put(Params.SAVE_KEY, Util.getUPYunSavePath(user.getId(), Constants.TYPE_VIDEO) + suffix);
        paramsMap.put(Params.EXPIRATION, getExpiration());
        paramsMap.put(Params.NOTIFY_URL, Constants.API_URL + "/upyun/callback");
        paramsMap.put("apps", getApps());
        UpCompleteListener completeListener = new UpCompleteListener() {
            @Override
            public void onComplete(boolean isSuccess, String result) {
                try {
                    if (isSuccess) {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray array = jsonObject.optJSONArray("task_ids");
                        ExplorePublishApi api = new ExplorePublishApi();
                        api.setInterPath(Constants.EXPLORE_PUBLISH_URL);
                        api.description = content;
                        if (!Util.isEmpty(select)) {
                            api.setProductIds(select);
                        }
                        api.setType(1);//普通类型传1，体验分享传3
                        if (address != null && address.cityName != null) {
                            api.setX("" + address.lat);
                            api.setY("" + address.lon);
                            api.setCity(address.cityName);
                            api.setStreet(address.cityName + address.addressName);
                        }
                        if (topicBean != null) {
                            api.setTopicId((long) topicBean.getId());
                        }
                        String videoUrl = jsonObject.getString("url");
                        api.video = videoUrl;
                        if (!Util.isEmpty(videoUrl)) {
                            int index = videoUrl.lastIndexOf(".");
                            if (index > 0) {
                                videoUrl = videoUrl.substring(0, index);
                            }
                            api.setPic(videoUrl + "_fpng_n1_oneTrue.png");
                        }
                        api.timeLength = duration / 1000;
                        if (array != null && array.length() > 0) {
                            int length = array.length();
                            StringBuilder stringBuilder = new StringBuilder();
                            for (int i = 0; i < length; i++) {
                                stringBuilder.append(array.opt(i).toString());
                                if (i < length - 1) {
                                    stringBuilder.append(",");
                                }
                            }
                            api.taskIds = stringBuilder.toString();
                        }
                        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                            @Override
                            public void onResponse(BaseBean response) {

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), Util.checkErrorType(error), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        GlobalTypeBean globalTypeBean = new GlobalTypeBean(Constants.GlobalType.UPLOAD_FAIL);
                        globalTypeBean.putValue("url", path);
                        globalTypeBean.putValue("content", content);
                        globalTypeBean.putValue("duration", duration);
                        if (topicBean != null) {
                            globalTypeBean.putValue("topicBean", topicBean);
                        }
                        if (address != null) {
                            globalTypeBean.putValue(SearchAdressActivity.ADDRESS, address);
                        }
                        EventBus.getDefault().post(globalTypeBean);
                    }
                } catch (JSONException e) {
                    GlobalTypeBean globalTypeBean = new GlobalTypeBean(Constants.GlobalType.UPLOAD_FAIL);
                    globalTypeBean.putValue("url", path);
                    globalTypeBean.putValue("content", content);
                    globalTypeBean.putValue("duration", duration);
                    if (topicBean != null) {
                        globalTypeBean.putValue("topicBean", topicBean);
                    }
                    if (address != null) {
                        globalTypeBean.putValue(SearchAdressActivity.ADDRESS, address);
                    }
                    EventBus.getDefault().post(globalTypeBean);
                    //Toast.makeText(InitializeService.this,"又拍云上传失败",Toast.LENGTH_LONG).show();
                }
            }
        };
        UpProgressListener progressListener = new UpProgressListener() {
            @Override
            public void onRequestProgress(long bytesWrite, long contentLength) {
                //state = ProcessImageView.UploadState.uploading;
                int progress = (int) (bytesWrite * 100 / contentLength);
                GlobalTypeBean globalTypeBean = new GlobalTypeBean(Constants.GlobalType.UPLOAD_PROGRESS);
                globalTypeBean.putValue("progress", progress);
                globalTypeBean.putValue("url", path);
                EventBus.getDefault().post(globalTypeBean);
            }
        };
        UploadEngine.getInstance().formUpload(file, paramsMap, "han", UpYunUtils.md5("han123456"), completeListener, progressListener);
    }

    public JSONArray getApps() {
        JSONArray array = new JSONArray();
        Map<String, Object> appItem = new HashMap<>();
        appItem.put("name", "naga");
        appItem.put("type", "video");
        appItem.put("avopts", "/r/25/vcodec/libx264/acodec/libfdk_aac");
        JSONObject object = new JSONObject(appItem);
        array.put(object);
        Map<String, Object> appItem1 = new HashMap<>();
        appItem1.put("name", "naga");
        appItem1.put("type", "thumbnail");
        appItem1.put("avopts", "/o/true/n/1/f/png");
        JSONObject object1 = new JSONObject(appItem1);
        array.put(object1);
        return array;
    }

    public String getTask() {
        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("type", "video");
        taskMap.put("avopts", "/s/720p(16:9)/as/true/r/25/sp/auto/vcodec/libx264");
        JSONObject obj = new JSONObject(taskMap);
        JSONArray array = new JSONArray();
        array.put(obj);
        Log.d("upyun", array.toString());
        String task = Base64Coder.encodeString(array.toString());
        return task;
    }

    public long getExpiration() {
        Date date = new Date();
        Date date2 = new Date(date.getTime() + 30 * 60 * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return (simpleDateFormat.parse(simpleDateFormat.format(date2))).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void performInit() {
        ZampAppAnalytics.setLogStatus(false);
        ZampAppAnalytics.init(getApplicationContext());
        TCAgent.init(getApplicationContext());
        Unicorn.init(getApplicationContext(), Constants.QIYU_KEY, D2CApplication.ysfOptions, new UILImageLoader(this));
        TalkingDataAppCpa.setVerboseLogDisable();
        TalkingDataAppCpa.init(getApplicationContext(), Constants.TALKINGDATA_AD_KEY,
                Util.getMetaValue(getApplicationContext(), "TD_CHANNEL_ID"));
        //((D2CApplication)getApplication()).initTu();
        WxHandle.getInstance(this);
        AuthInfo mAuthInfo = new AuthInfo(this, Constants.WEIBOKEY, Constants.WEIBO_REDIRECT_URL, Constants.WEIBO_SCOPE);
        WbSdk.install(this, mAuthInfo);
        ((D2CApplication) getApplication()).initLiveSDK();
        QbSdk.initX5Environment(getApplicationContext(), null);
        Util.getDeviceSize(getApplicationContext());
    }
}
