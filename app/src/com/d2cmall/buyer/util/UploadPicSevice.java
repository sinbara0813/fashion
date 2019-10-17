package com.d2cmall.buyer.util;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.api.ExplorePublishApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.JsonPic;
import com.d2cmall.buyer.http.BeanRequest;
import com.upyun.library.common.Params;
import com.upyun.library.common.UploadManager;
import com.upyun.library.listener.SignatureListener;
import com.upyun.library.listener.UpCompleteListener;
import com.upyun.library.utils.UpYunUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by rookie on 2017/11/10.
 */

public class UploadPicSevice extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    private ArrayList<JsonPic> jsonPics;
    private int upLoadIndex;
    private ArrayList<String> imgUpyunPaths;
    private ExplorePublishApi api;
    private long id;

    public UploadPicSevice() {
        super("UploadPicSevice");
    }

    private void uploadFile() {
        JsonPic jsonPic = jsonPics.get(upLoadIndex);
        File file = new File(jsonPic.getMediaPath());
        if (!file.exists()) {
            return;
        }
        final Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(Params.BUCKET, Constants.UPYUN_SPACE);
        paramsMap.put(Params.SAVE_KEY, Util.getUPYunSavePath(id, Constants.TYPE_FRIEND));
        paramsMap.put(Params.RETURN_URL, "httpbin.org/post");
        UpCompleteListener completeListener = new UpCompleteListener() {
            @Override
            public void onComplete(boolean isSuccess, String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    imgUpyunPaths.add(jsonObject.optString("url"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                upLoadIndex++;
                if (upLoadIndex < jsonPics.size()) {
                    uploadFile();
                } else {
                    publisthShow(api);
                }
            }
        };
        SignatureListener signatureListener = new SignatureListener() {
            @Override
            public String getSignature(String raw) {
                return UpYunUtils.md5(raw + Constants.UPYUN_KEY);
            }
        };
        UploadManager.getInstance().blockUpload(file, paramsMap, signatureListener, completeListener, null);
    }

    private void publisthShow(ExplorePublishApi api) {
        api.setPic(Util.join(imgUpyunPaths.toArray(), ","));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean bean) {
                EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.PUBLISHBACK));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(UploadPicSevice.this, "发布失败");
                imgUpyunPaths.clear();
            }
        });
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        jsonPics = (ArrayList<JsonPic>) intent.getSerializableExtra("jsonPics");
        imgUpyunPaths = intent.getStringArrayListExtra("imgUpyunPaths");
        api = (ExplorePublishApi) intent.getSerializableExtra("api");
        api.setInterPath(Constants.EXPLORE_PUBLISH_URL);
        id = intent.getLongExtra("id", 0);
        upLoadIndex = 0;
        if (jsonPics == null || imgUpyunPaths == null || api == null) {
            return;
        }
        uploadFile();
    }
}
