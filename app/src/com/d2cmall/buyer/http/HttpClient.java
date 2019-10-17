package com.d2cmall.buyer.http;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.d2cmall.buyer.BuildConfig;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.base.BaseBean;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.net.ssl.SSLSocketFactory;

public class HttpClient {
    private static RequestQueue requestQueue;
    private final Context context;
    private static AtomicInteger counter = new AtomicInteger(0);
    private boolean useCache = false;

    private HttpClient(Context context) {
        this.context = context;
    }

    public synchronized static HttpClient newInstance(Context context) {
        if (requestQueue == null) {
            InputStream inputStream = context.getResources().openRawResource(R.raw.public_key_online);
            SSLSocketFactory sslSocketFactory = HttpsManager.buildSSLSocketFactory(context, inputStream);
            HttpsStack httpsStack = new HttpsStack(null, sslSocketFactory);
            requestQueue = Volley.newRequestQueue(context.getApplicationContext(), BuildConfig.DEBUG ? null : null);
        }
        return new HttpClient(context);
    }

    public void clearCache() {
        requestQueue.getCache().clear();
    }

    public void setUseCache(boolean useCache) {
        this.useCache = useCache;
    }

    public <T extends BaseBean> BeanRequest<T> request(BaseApi api) {
        return request(api, null, getErrorListener());
    }

    public <T extends BaseBean> BeanRequest<T> request(BaseApi api, BeanRequest.SuccessListener<T> successListener) {
        return request(api, successListener, getErrorListener());
    }

    public void addRequest(Request req) {
        requestQueue.add(req);
    }

    public <T extends BaseBean> BeanRequest<T> request(BaseApi api, BeanRequest.SuccessListener<T> successListener, Response.ErrorListener errorListener) {
        return loadingRequest(api, successListener, errorListener, null, null);
    }

    public <T extends BaseBean> BeanRequest<T> loadingRequest(BaseApi api) {
        return loadingRequest(api, null, getErrorListener(), null, null);
    }

    public <T extends BaseBean> BeanRequest<T> loadingRequest(BaseApi api, BeanRequest.SuccessListener<T> successListener) {
        return loadingRequest(api, successListener, getErrorListener(), null, null);
    }

    public <T extends BaseBean> BeanRequest<T> loadingRequest(BaseApi api, BeanRequest.SuccessListener<T> successListener, Response.ErrorListener errorListener) {
        return loadingRequest(api, successListener, errorListener, null, null);
    }

    public <T extends BaseBean> BeanRequest<T> loadingRequest(BaseApi api, BeanRequest.SuccessListener<T> successListener, Response.ErrorListener errorListener, DefaultRetryPolicy retryPolicy) {
        return loadingRequest(api, successListener, errorListener, null, retryPolicy);
    }

    public <T extends BaseBean> BeanRequest<T> loadingRequest(BaseApi api, BeanRequest.SuccessListener<T> successListener, Response.ErrorListener errorListener, String loadingText) {
        return loadingRequest(api, successListener, errorListener, loadingText, null);
    }

    public <T extends BaseBean> BeanRequest<T> loadingRequest(BaseApi api, BeanRequest.SuccessListener<T> successListener, Response.ErrorListener errorListener, String loadingText, DefaultRetryPolicy retryPolicy) {
        String url = api.getUrl();
        TreeMap<String, Object> params = api.handleParams(context, api.getParams());
        if (api.requestMethod() == BaseApi.Method.POST) {
            return loadingPost(context, url, params, successListener, errorListener, loadingText, retryPolicy,api.isJsonContentType);
        } else {
            if (!params.isEmpty()) {
                String pm = mapToQueryString(params);
                url += "?" + pm.substring(0, pm.length() - 1);
            }
            return loadingGet(context, url, successListener, errorListener, loadingText, retryPolicy);
        }
    }

    public <T extends BaseBean> BeanRequest<T> get(Context context, String url, BeanRequest.SuccessListener<T> successListener, Response.ErrorListener errorListener) {
        BeanRequest<T> request = new BeanRequest<T>(context, Request.Method.GET, url, null, successListener, errorListener);
        addRequest(request, false, null, null);
        return request;
    }

    public void get(String url, Response.Listener listener) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, listener, null);
        requestQueue.add(request);
    }

    public void post(String url, String requestBody, Response.Listener listener) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody, listener, null);
        requestQueue.add(request);
    }

    public <T extends BaseBean> BeanRequest<T> post(Context context, String url, Map<String, Object> postParamMap, BeanRequest.SuccessListener<T> successListener, Response.ErrorListener errorListener) {
        BeanRequest<T> request = new BeanRequest<>(context, Request.Method.POST, url, postParamMap, successListener, errorListener);
        addRequest(request, false, null, null);
        return request;
    }

    public <T extends BaseBean> BeanRequest<T> loadingGet(Context context, String url, BeanRequest.SuccessListener<T> successListener, Response.ErrorListener errorListener, String loadingText, DefaultRetryPolicy retryPolicy) {
        BeanRequest<T> request = new BeanRequest<>(context, Request.Method.GET, url, null, successListener, errorListener);
        addRequest(request, true, loadingText, retryPolicy);
        return request;
    }

    public <T extends BaseBean> BeanRequest<T> loadingPost(Context context, String url, Map<String, Object> postParamMap, BeanRequest.SuccessListener<T> successListener, Response.ErrorListener errorListener, String loadingText, DefaultRetryPolicy retryPolicy) {
        BeanRequest<T> request = new BeanRequest<>(context, Request.Method.POST, url, postParamMap, successListener, errorListener);
        addRequest(request, true, loadingText, retryPolicy);
        return request;
    }

    public <T extends BaseBean> BeanRequest<T> loadingPost(Context context, String url, Map<String, Object> postParamMap, BeanRequest.SuccessListener<T> successListener, Response.ErrorListener errorListener, String loadingText, DefaultRetryPolicy retryPolicy,boolean isJson) {
        BeanRequest<T> request = new BeanRequest<>(context, Request.Method.POST, url, postParamMap, successListener, errorListener);
        request.setJson(isJson);
        addRequest(request, true, loadingText, retryPolicy);
        return request;
    }

    private <T extends BaseBean> void addRequest(BeanRequest<T> request, boolean hasLoading, String loadingText, DefaultRetryPolicy retryPolicy) {
        String tag = getTagAndCount();

        if (hasLoading) {
            startLoading(tag, loadingText);
        }

        request.setTag(tag);
        request.setShouldCache(useCache);
        if (null != retryPolicy)
            request.setRetryPolicy(retryPolicy);

        if (hasLoading) {
            addLoadingListenerToRequest(request);
        }
        requestQueue.add(request);
    }

    public void cancelRequest(BeanRequest request) {
        requestQueue.cancelAll(request.getTag());
    }

    private String mapToQueryString(Map<String, Object> params) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() == null || entry.getValue() instanceof File)
                    continue;
                encodedParams.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8"));
                encodedParams.append('&');
            }
            return encodedParams.toString();
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: UTF-8", uee);
        }
    }

    private String getTagAndCount() {
        int num = counter.getAndIncrement();
        return "HttpRequest-" + num;
    }

    public Response.ErrorListener getErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        };
    }

    private void startLoading(final String tag, String loadingText) {
    }

    private <T extends BaseBean> void addLoadingListenerToRequest(BeanRequest<T> request) {
        request.setFinishListener(new BeanRequest.OnFinishListener() {
            @Override
            public void onFinish() {
            }
        });
    }
}
