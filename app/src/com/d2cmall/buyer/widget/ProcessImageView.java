package com.d2cmall.buyer.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.bean.JsonPic;
import com.d2cmall.buyer.util.Util;
import com.upyun.library.common.Params;
import com.upyun.library.common.UploadEngine;
import com.upyun.library.common.UploadManager;
import com.upyun.library.listener.SignatureListener;
import com.upyun.library.listener.UpCompleteListener;
import com.upyun.library.listener.UpProgressListener;
import com.upyun.library.utils.UpYunUtils;

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

public class ProcessImageView extends AppCompatImageView {

    private Paint mPaint;// 画笔
    Context context = null;
    int progress = 0;
    String uploadUrl;
    JsonPic pic;
    public long userId;
    UploadState state = UploadState.none;

    public enum UploadState {
        none, fail, uploading, success
    }

    public ProcessImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProcessImageView(Context context, AttributeSet attrs,
                            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        mPaint = new Paint();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.FILL);


        mPaint.setColor(Color.parseColor("#70000000"));// 半透明
        canvas.drawRect(0, 0, getWidth() - getWidth() * progress / 100, getHeight(), mPaint);

        mPaint.setColor(Color.parseColor("#00000000"));// 全透明
        canvas.drawRect(getWidth() - getWidth() * progress / 100, 0,
                getWidth(), getHeight(), mPaint);

        mPaint.setTextSize(30);
        mPaint.setColor(Color.parseColor("#FFFFFF"));
        mPaint.setStrokeWidth(2);
        Rect rect = new Rect();
        if (progress < 100) {
            if (state == UploadState.fail) {
                mPaint.setColor(Color.parseColor("#FF0000"));
                mPaint.getTextBounds("上传失败", 0, "上传失败".length(), rect);
                canvas.drawText("上传失败", getWidth() / 2 - rect.width() / 2, getHeight() / 2, mPaint);
            } else {
                mPaint.getTextBounds("100%", 0, "100%".length(), rect);// 确定文字的宽度
                if(pic.isVideo()){
                    canvas.drawText(progress + "%", getWidth() / 2 - rect.width() / 2,
                            (float) (getHeight() * 0.8), mPaint);
                }else{
                    canvas.drawText(progress + "%", getWidth() / 2 - rect.width() / 2,
                            getHeight() / 2, mPaint);
                }

            }
        }
    }

    public void setProgress(int progress) {
        this.progress = progress;
        postInvalidate();
    }

    public void startUpload() {
        upload();
    }

    public void upload() {
        if(pic.isVideo()){
            uploadVideo();
        }else {
            if (pic == null || state == UploadState.success || state == UploadState.uploading) return;
            File file = new File(pic.getMediaPath());
            if (!file.exists()){
                return;
            }
            final Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put(Params.BUCKET, Constants.UPYUN_SPACE);
            paramsMap.put(Params.SAVE_KEY, Util.getUPYunSavePath(userId, Constants.TYPE_FRIEND));
            paramsMap.put(Params.RETURN_URL, "httpbin.org/post");
            UpCompleteListener completeListener = new UpCompleteListener() {
                @Override
                public void onComplete(boolean isSuccess, String result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        uploadUrl = jsonObject.optString("url");
                        if (isSuccess) {
                            state = UploadState.success;
                        } else {
                            state = UploadState.fail;
                        }
                        pic.setUploadPath(uploadUrl);
                        EventBus.getDefault().post(pic);
                    } catch (JSONException e) {
                        state = UploadState.fail;
                    }
                }
            };
            SignatureListener signatureListener = new SignatureListener() {
                @Override
                public String getSignature(String raw) {
                    return UpYunUtils.md5(raw + Constants.UPYUN_KEY);
                }
            };
            UpProgressListener progressListener = new UpProgressListener() {
                @Override
                public void onRequestProgress(long bytesWrite, long contentLength) {
                    state = UploadState.uploading;
                    int progress = (int) (bytesWrite * 100 / contentLength);
                    setProgress(progress);
                }
            };
            UploadManager.getInstance().blockUpload(file, paramsMap, signatureListener, completeListener, progressListener);
        }

    }

    private void uploadVideo(){
        File file = new File(pic.getMediaPath());
        int index = pic.getMediaPath().lastIndexOf(".");
        String suffix = pic.getMediaPath().substring(index, pic.getMediaPath().length());
        if (!file.exists()) {
            return;
        }
        final Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(Params.BUCKET, Constants.UPYUN_SPACE);
        paramsMap.put(Params.SAVE_KEY, Util.getUPYunSavePath(userId, Constants.TYPE_VIDEO) + suffix);
        paramsMap.put(Params.EXPIRATION, getExpiration());
        paramsMap.put(Params.NOTIFY_URL, Constants.API_URL + "/upyun/callback");
        paramsMap.put("apps", getApps());
        UpCompleteListener completeListener = new UpCompleteListener() {
            @Override
            public void onComplete(boolean isSuccess, String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    uploadUrl = jsonObject.optString("url");
                    JSONArray array = jsonObject.optJSONArray("task_ids");
                    if (array != null && array.length() > 0) {
                        int length = array.length();
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < length; i++) {
                            stringBuilder.append(array.opt(i).toString());
                            if (i < length - 1) {
                                stringBuilder.append(",");
                            }
                        }
                        pic.setTaskIds(stringBuilder.toString());
                    }
                    if (isSuccess) {
                        state = UploadState.success;
                    } else {
                        state = UploadState.fail;
                    }
                    pic.setUploadPath(uploadUrl);
                    if (!Util.isEmpty(uploadUrl)){
                        int index=uploadUrl.lastIndexOf(".");
                        if (index>0){
                            uploadUrl=uploadUrl.substring(0,index);
                        }
                        pic.setVideoPic(uploadUrl+"_fpng_n1_oneTrue.png");
                    }
                    EventBus.getDefault().post(pic);
                } catch (JSONException e) {
                    state = UploadState.fail;
                }
            }
        };
        UpProgressListener progressListener = new UpProgressListener() {
            @Override
            public void onRequestProgress(long bytesWrite, long contentLength) {
                state = UploadState.uploading;
                int progress = (int) (bytesWrite * 100 / contentLength);
                setProgress(progress);
            }
        };
        UploadEngine.getInstance().formUpload(file, paramsMap, "han", UpYunUtils.md5("han123456"), completeListener, progressListener);
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

    public void setJsonPic(JsonPic jsonPic) {
        if (pic != null && !pic.getMediaPath().equals(jsonPic.getMediaPath())) {
            state = UploadState.none;
            progress = 0;
        }
        this.pic = jsonPic;
    }
}
