package com.d2cmall.buyer.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.HttpErrorBean;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class BeanRequest<T extends BaseBean> extends Request<T> {
    private final SuccessListener<T> mSuccessListener;
    private Map<String, String> mPostParamMap;
    private OnFinishListener mFinishListener;
    private boolean isJson;

    public interface OnFinishListener {
        void onFinish();
    }

    /**
     * Callback interface for delivering parsed responses.
     */
    public static abstract class SuccessListener<T> {
        private final Type type;

        protected SuccessListener() {
            Type superClass = getClass().getGenericSuperclass();
            type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        }

        public Type getType() {
            return type;
        }

        /**
         * Called when a response is received.
         */
        public abstract void onResponse(T response);
    }

    public BeanRequest(Context context, int method, String url, Map<String, Object> postParamMap, SuccessListener<T> successListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);

        mSuccessListener = successListener;
        if (postParamMap != null) {
            mPostParamMap = new HashMap<>(postParamMap.size());
            for (Map.Entry<String, Object> entry : postParamMap.entrySet()) {
                mPostParamMap.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
    }

    @Override
    public String getBodyContentType() {
        if (isJson){
            return "application/json; charset="+getParamsEncoding();
        }else {
            return super.getBodyContentType();
        }
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (isJson) {
            try {
                Gson gson = new Gson();
                Map<String, Object> params = getRealParam(getParams());
                if (params != null && params.size() > 0) {
                    return gson.toJson(params).toString().getBytes(getParamsEncoding());
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Encoding not supported: " + getParamsEncoding(), e);
            }
        } else {
            return super.getBody();
        }
        return null;
    }

    private Map<String,Object> getRealParam(Map<String,String> map){
        Map<String,Object> result=new HashMap<>();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            try {
                int intValue=Integer.valueOf(value.toString());
                result.put(key.toString(),intValue);
            }catch (Exception e){
                result.put(key.toString(),value.toString());
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            byte[] data = response.data;
            String encoding = response.headers.get("Content-Encoding");
            if (encoding != null && encoding.equals("gzip")) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                GZIPInputStream in = new GZIPInputStream(new ByteArrayInputStream(data));
                byte[] buffer = new byte[2048];
                int n;
                while ((n = in.read(buffer)) >= 0) {
                    out.write(buffer, 0, n);
                }
                in.close();
                data = out.toByteArray();
            }
            String jsonString = new String(data, "UTF-8");
            Type type = mSuccessListener.getType();
            Gson gson =bulidGson();
            T bean = gson.fromJson(jsonString, type);
            if (!bean.isLogin()) {
                return Response.error(new AuthFailureError(bean.getMsg()));
            }
            if (bean.getStatus() != 1) {
                return Response.error(new HttpError(new HttpErrorBean(bean.getStatus(), bean.getMsg(), jsonString)));
            }
            Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);
            if (entry == null) {
                entry = new Cache.Entry();
                entry.data = response.data;
            }
            return Response.success(bean, entry);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (Exception je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        if (mFinishListener != null)
            mFinishListener.onFinish();
        mSuccessListener.onResponse(response);
    }

    /**
     * Returns a Map of parameters to be used for a POST or PUT request.  Can throw
     * {@link com.android.volley.AuthFailureError} as authentication may be required to provide these values.
     * <p/>
     * <p>Note that you can directly override {@link #getBody()} for custom data.</p>
     *
     * @throws com.android.volley.AuthFailureError in the event of auth failure
     */
    protected Map<String, String> getParams() throws AuthFailureError {
        return mPostParamMap;
    }

    @Override
    public void deliverError(VolleyError error) {
        if (mFinishListener != null)
            mFinishListener.onFinish();
        super.deliverError(error);
    }

    public void setFinishListener(OnFinishListener mFinishListener) {
        this.mFinishListener = mFinishListener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        String token = D2CApplication.mSharePref.getSharePrefString(SharePrefConstant.TOKEN, "");
        String deviceId = D2CApplication.mSharePref.getSharePrefString(SharePrefConstant.DEVICE_IMEI, "");//设备唯一标示
        if (!Util.isEmpty(token)) {
            headers.put("accesstoken", token);
        }
        if(!Util.isEmpty(deviceId)){
            headers.put("udid", deviceId);
        }
        return headers;
    }

    @Override
    public String getCacheKey() {
        String temp = super.getCacheKey();
        for (Map.Entry<String, String> entry : mPostParamMap.entrySet()) {
            temp += entry.getKey() + "=" + entry.getValue();
        }
        return temp;
    }

    public Gson bulidGson(){
        Gson gson = null;
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new DateAdapter());
        gson = builder.create();
        return gson;
    }

    public class DateAdapter implements JsonDeserializer<Date> {
        public Date deserialize(JsonElement arg0, Type arg1,
                                JsonDeserializationContext arg2) throws JsonParseException {
            try {
                if (arg0.getAsLong()>0){
                    Date date=new Date();
                    date.setTime(arg0.getAsLong());
                    return date;
                }
            }catch (Exception e){
                return DateUtil.toDate(arg0.getAsString());
            }
            return null;
        }
    }

    public void setJson(boolean json) {
        isJson = json;
    }
}
