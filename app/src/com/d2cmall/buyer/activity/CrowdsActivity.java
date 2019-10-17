package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.ObjectBindAdapter;
import com.d2cmall.buyer.api.LogApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.CrowdBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.LogDB;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.util.WebHandler;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;
import com.d2cmall.buyer.widget.SharePop;
import com.d2cmall.buyer.widget.StaggeredGridView;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.Subscribe;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 预售列表
 * Author: Blend
 * Date: 16/5/16 15:21
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CrowdsActivity extends BaseActivity implements
        ObjectBindAdapter.ViewBinder<CrowdBean.DataEntity.CrowdItemsEntity.ListEntity>,
        AbsListView.OnScrollListener, AdapterView.OnItemClickListener {

    @Bind(R.id.gridView)
    StaggeredGridView gridView;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout ptr;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.web_container)
    FrameLayout webContainer;
    BridgeWebView webView;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.img_finish)
    ImageView imgFinish;
    @Bind(R.id.img_share)
    ImageView imgShare;
    private boolean isEnd;
    private boolean isLoad;
    private int currentPage = 1;
    private ArrayList<CrowdBean.DataEntity.CrowdItemsEntity.ListEntity> listEntities;
    private ObjectBindAdapter<CrowdBean.DataEntity.CrowdItemsEntity.ListEntity> adapter;
    private View headView;
    private TextView tvTime;
    private View endView;
    private View loadView;
    private int productImgWidth;
    private long id;
    private String url;
    private CrowdBean.DataEntity.CrowdEntity crowdEntity;
    private Handler handler;
    private String token = "";
    private String log;
    private WebHandler webHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crowd);
        ButterKnife.bind(this);
        webView = new BridgeWebView(getApplicationContext());
        webContainer.addView(webView);
        id = getIntent().getLongExtra("id", -1);
        url = getIntent().getStringExtra("url");
        log = getIntent().getStringExtra("log");
        Point point = Util.getDeviceSize(this);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        productImgWidth = Math.round((point.x - 15 * dm.density) / 2);
        int productItemMargin = Math.round(5 * dm.density);
        listEntities = new ArrayList<>();
        adapter = new ObjectBindAdapter<>(this, listEntities, R.layout.list_item_product);
        headView = getLayoutInflater().inflate(R.layout.list_head_crowd, null);
        tvTime = (TextView) headView.findViewById(R.id.tv_time);
        View footView = getLayoutInflater().inflate(R.layout.list_foot_no_more2, null);
        endView = footView.findViewById(R.id.no_more);
        loadView = footView.findViewById(R.id.loading);
        gridView.setItemMargin(productItemMargin);
        gridView.addHeaderView(headView);
        gridView.addFooterView(footView);
        adapter.setViewBinder(this);
        gridView.setOnScrollListener(this);
        gridView.setOnItemClickListener(this);
        gridView.setAdapter(adapter);
        if (listEntities.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            requestCrowdsTask();
        }
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (!isLoad) {
                    currentPage = 1;
                    requestCrowdsTask();
                }
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        String ua = webView.getSettings().getUserAgentString();
        webView.getSettings().setUserAgentString(ua + String.format(Constants.USER_AGENT_URL,
                Util.getPageVersionName(this), Util.getNetType(this)));
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        handler = new Handler();
        webView.setWebViewClient(new MyWebViewClient(webView));
        webHandler=new WebHandler(this);
        webView.registerHandler("d2cinit", webHandler);
        if (!Util.isEmpty(log)) {
            log += "V_CROWD:" + id;
            postAppLog(log);
        }
    }

    private void postAppLog(String log) {
        LogApi api = new LogApi();
        String string = System.currentTimeMillis() + "," + log;
        api.setLog(string);
        if (Session.getInstance().getUserFromFile(this)==null){
            LogDB.getInstance(this).insert(string);
            return;
        }
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {

            }
        });
    }

    class MyWebViewClient extends BridgeWebViewClient {

        MyWebViewClient(BridgeWebView webView) {
            super(webView);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            tvTitle.setText(view.getTitle());
            imgShare.setEnabled(true);
            imgFinish.setVisibility(webView.canGoBack() ? View.VISIBLE : View.INVISIBLE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            webView.stopLoading();
            view.loadUrl("file:///android_asset/www/error.html");
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String mUrl) {
            if (mUrl.startsWith("yy://")) {
                return super.shouldOverrideUrlLoading(view, mUrl);
            } else {
                return Util.urlAction(CrowdsActivity.this, mUrl, Constants.Login.WEB_LOGIN, true, log + ",");
            }
        }

    }

    public void onShare(View v) {
        int index = url.indexOf("isLogin=1");
        if (index > 0 && !Util.loginChecked(this, 0)) return;
        /*SharePop sharePop = SharePop.getInstance(CrowdsActivity.this);
        sharePop.setTitle(tvTitle.getText().toString());
        sharePop.setDes("D2C这个活动太赞了,现在分享给你,赶紧来看看吧!");
        sharePop.setWebUrl(Constants.SHARE_URL + url);
        sharePop.show(getWindow().getDecorView(),this);*/
        SharePop sharePop1=new SharePop(this);
        sharePop1.setTitle(tvTitle.getText().toString());
        sharePop1.setDescription("D2C这个活动太赞了,现在分享给你,赶紧来看看吧!");
        sharePop1.setWebUrl(Constants.SHARE_URL+url);
        sharePop1.show(getWindow().getDecorView());
    }

    public void onBackPressed(View v) {
        if (webView.getVisibility() == View.VISIBLE && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webView.getVisibility() == View.VISIBLE && (keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onFinishThis(View v) {
        super.onBackPressed();
    }

    private void requestCrowdsTask() {
        SimpleApi api = new SimpleApi();
        api.setP(currentPage);
        api.setPageSize(20);
        api.setInterPath(String.format(Constants.CROWD_URL, id));
        isLoad = true;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CrowdBean>() {
            @Override
            public void onResponse(CrowdBean crowdBean) {
                ptr.refreshComplete();
                if (currentPage == 1) {
                    listEntities.clear();
                }
                if (crowdBean.getData().getCrowdItems() != null) {
                    ptr.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    webContainer.setVisibility(View.GONE);
                    crowdEntity = crowdBean.getData().getCrowd();
                    showTimeDown();
                    tvTitle.setText(crowdBean.getData().getCrowd().getName());
                    int size = crowdBean.getData().getCrowdItems().getList().size();
                    if (size > 0) {
                        listEntities.addAll(crowdBean.getData().getCrowdItems().getList());
                    }
                    adapter.notifyDataSetChanged();
                    if (!crowdBean.getData().getCrowdItems().isNext()) {
                        isEnd = true;
                        loadView.setVisibility(View.GONE);
                    } else {
                        isEnd = false;
                        loadView.setVisibility(View.INVISIBLE);
                    }
                    isLoad = false;
                    setEmptyView(Constants.NO_DATA);
                    imgShare.setVisibility(View.VISIBLE);
                } else {
                    ptr.setVisibility(View.GONE);
                    webContainer.setVisibility(View.VISIBLE);
                    loadUrl();
                    imgShare.setVisibility(View.VISIBLE);
                    imgShare.setEnabled(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                ptr.refreshComplete();
                setEmptyView(Constants.NET_DISCONNECT);
            }
        });
    }

    private void setEmptyView(int type) {
        if (listEntities.isEmpty()) {
            View emptyView = gridView.getEmptyView();
            if (emptyView == null) {
                emptyView = this.findViewById(R.id.empty_hint_layout);
                gridView.setEmptyView(emptyView);
            }
            emptyView.setVisibility(View.VISIBLE);
            ImageView imgHint = (ImageView) emptyView.findViewById(R.id.img_hint);
            imgHint.setVisibility(View.VISIBLE);
            if (type == Constants.NO_DATA) {
                imgHint.setImageResource(R.mipmap.ic_no_data);
            } else {
                imgHint.setImageResource(R.mipmap.ic_no_net);
            }
        }
    }

    @Override
    public void setViewValue(View view, final CrowdBean.DataEntity.CrowdItemsEntity.ListEntity listEntity, int position) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder(view);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.imgProduct.getLayoutParams();
            params.width = productImgWidth;
            params.height = Math.round(productImgWidth * 1558 / 1000);
            ViewGroup.MarginLayoutParams params1 = (ViewGroup.MarginLayoutParams) holder.sale_ll.getLayoutParams();
            params1.height = params.height;
            view.setTag(holder);
        }
        UniversalImageLoader.displayImage(this,Util.getD2cPicUrl(listEntity.getImg(), productImgWidth), holder.imgProduct
                , R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
        if (listEntity.getStore() <= 0) {
            holder.sale_ll.setVisibility(View.VISIBLE);
        } else {
            holder.sale_ll.setVisibility(View.GONE);
        }
        holder.tvTitle.setText(listEntity.getName());
        holder.tvPrice.setText(Util.getNumberFormat(listEntity.getCurrentPrice()));
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case SCROLL_STATE_IDLE:
                if (view.getLastVisiblePosition() >= (view.getCount() - 5) && !isEnd && !isLoad) {
                    loadView.setVisibility(View.VISIBLE);
                    currentPage++;
                    requestCrowdsTask();
                } else {
                    break;
                }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        if ((totalItemCount > visibleItemCount) && isEnd) {
            endView.setVisibility(View.VISIBLE);
        } else {
            endView.setVisibility(View.GONE);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CrowdBean.DataEntity.CrowdItemsEntity.ListEntity listEntity = (CrowdBean.DataEntity.CrowdItemsEntity.ListEntity)
                parent.getAdapter().getItem(position);
        if (listEntity != null) {
            Intent intent = new Intent(this, ProductDetailActivity.class);
            intent.putExtra("id", listEntity.getProductId());
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
        }
    }

    static class ViewHolder {
        @Bind(R.id.img_product)
        ImageView imgProduct;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.img_saleout)
        ImageView imgSaleout;
        @Bind(R.id.sale_ll)
        LinearLayout sale_ll;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private void showTimeDown() {
        if (crowdEntity != null) {
            Date date = new Date();
            Date beginCrowd = Util.getDate(crowdEntity.getBeginCrowd());
            Date endCrowd = Util.getDate(crowdEntity.getEndCrowd());
            if (beginCrowd != null && endCrowd != null) {
                headView.setVisibility(View.VISIBLE);
                if (beginCrowd.before(date) && endCrowd.after(date)) {//在售
                    long millisUntil = endCrowd.getTime() - date.getTime();
                    long leftMillis;
                    int days = (int) (millisUntil / (1000 * 60 * 60 * 24));
                    leftMillis = millisUntil % (1000 * 60 * 60 * 24);
                    int hours = (int) (leftMillis / (1000 * 60 * 60));
                    leftMillis %= 1000 * 60 * 60;
                    int minutes = (int) (leftMillis / (1000 * 60));
                    leftMillis %= 1000 * 60;
                    int seconds = (int) (leftMillis / 1000);
                    tvTime.setVisibility(View.VISIBLE);
                    tvTime.setText(String.format(getString(R.string.label_crowd_timeout), days, hours / 10, hours % 10, minutes / 10,
                            minutes % 10, seconds / 10, seconds % 10));
                } else {//已结束
                    headView.setVisibility(View.GONE);
                }
            } else {
                headView.setVisibility(View.GONE);
            }
        }
    }

    private final Runnable runnable = new Runnable() {
        public void run() {
            if (crowdEntity != null && !Util.isEmpty(crowdEntity.getBeginCrowd())
                    && !Util.isEmpty(crowdEntity.getEndCrowd())) {
                showTimeDown();
            }
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onResume() {
        handler.post(runnable);
        Util.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        Util.onPause(this);
        super.onPause();
    }

    private void loadUrl() {
        UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(this);
        if (user != null) {
            token = user.getUserToken();
        } else {
            token = "";
        }
        webView.loadUrl(String.format(Constants.INVOKE_URL, token, Util.toURLEncode(url)));
    }

    @Override
    protected void onDestroy() {
        webContainer.removeAllViews();
        webView.destroy();
        webView=null;
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(GlobalTypeBean bean) {
        if (bean.getType() == Constants.GlobalType.LOGIN_OK) {
            loadUrl();
            Map<String, String> map = new HashMap<>();
            map.put("token", token);
            map.put("type", "login");
            Gson gson = new Gson();
            webView.callHandler("b_login", gson.toJson(map), new CallBackFunction() {
                @Override
                public void onCallBack(String data) {

                }
            });
        } else if (bean.getType() == Constants.GlobalType.SHARE_OK) {
            Object func= bean.getValue("func");
            Map<String, String> map = new HashMap<>();
            map.put("token", token);
            map.put("type", "share");
            map.put("status", "1");
            Gson gson = new Gson();
            webView.callHandler((func!=null)?(String)func:"b_share", gson.toJson(map), new CallBackFunction() {
                @Override
                public void onCallBack(String data) {
                }
            });
        }
    }
}