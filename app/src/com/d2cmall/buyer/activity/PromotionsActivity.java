package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.PromotionAdapter;
import com.d2cmall.buyer.adapter.PromotionFilterAdapter;
import com.d2cmall.buyer.adapter.PromotionNoDataAdpater;
import com.d2cmall.buyer.adapter.PromotionTopAdapter;
import com.d2cmall.buyer.api.LogApi;
import com.d2cmall.buyer.api.PromotionsApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.GoodsBean;
import com.d2cmall.buyer.bean.Poster;
import com.d2cmall.buyer.bean.PromotionBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.binder.ScrollEndBinder;
import com.d2cmall.buyer.holder.DefaultHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.HttpUtils;
import com.d2cmall.buyer.util.LogDB;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.util.WebHandler;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;
import com.d2cmall.buyer.widget.ScreenNewPop;
import com.d2cmall.buyer.widget.SharePop;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.Subscribe;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 活动列表
 * Author: Blend
 * Date: 16/5/16 15:21
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class PromotionsActivity extends BaseActivity implements PromotionFilterAdapter.FilterClickListener {

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
    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.action_layout)
    RelativeLayout actionLayout;
    @Bind(R.id.img_hint)
    ImageView imgHint;
    @Bind(R.id.empty_hint_layout)
    LinearLayout emptyHintLayout;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.btn_reload)
    TextView btnReload;
    @Bind(R.id.btn_cart)
    FloatingActionButton btnCart;
    @Bind(R.id.back_b)
    ImageView backB;
    @Bind(R.id.share_b)
    ImageView shareB;
    @Bind(R.id.second_title_ll)
    LinearLayout secondTitleLl;
    private boolean isEnd;
    private boolean isLoad;
    private ArrayList<GoodsBean.DataBean.ProductsBean.ListBean> listEntities;
    private int productImgWidth;
    private long id;
    private String token = "";
    private String log;
    private String picture;//分享图片
    private String content;//分享内容
    private WebHandler webHandler;
    private VirtualLayoutManager mLayoutManager;
    private int currentPage = 1;
    private boolean hasNext;
    private PromotionAdapter promotionAdapter;
    private PromotionTopAdapter promotionTopAdapter;
    private PromotionBean mPromotionBean;
    private DelegateAdapter delegateAdapter;
    private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志
    private int lastPosition = -1;
    private int lastOffer;
    private float lastAlpha;
    private PromotionFilterAdapter promotionFilterAdapter;
    private String sortValue = "";
    private ScreenNewPop newPop;
    private String url;
    private String subName;
    private int type;
    private String categoryId;
    private int min = -1;
    private int max = -1;
    private Integer subscribe;
    private String productSellType = "";
    private int topId = -1;
    private String desiners;
    private boolean isafter;
    private Boolean hasPromotion;
    private PromotionNoDataAdpater promotionNoDataAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);
        ButterKnife.bind(this);
        webView = new BridgeWebView(getApplicationContext());
        webContainer.addView(webView);
        id = getIntent().getLongExtra("id", -1);
        log = getIntent().getStringExtra("log");
        url = getIntent().getStringExtra("url");
        listEntities = new ArrayList<>();
        doBusiness();
        initListener();
        if (listEntities.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            requestPromotionsTask();
        }
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

        webView.setWebViewClient(new MyWebViewClient(webView));
        webHandler = new WebHandler(this);
        webView.registerHandler("d2cinit", webHandler);
        if (!Util.isEmpty(log)) {
            log += "V_PROMOTION:" + id;
            postAppLog(log);
        }
    }

    private void doBusiness() {
        mLayoutManager = new VirtualLayoutManager(PromotionsActivity.this);
        int itemWidth = (ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(48)) / 2;
        promotionAdapter = new PromotionAdapter(this, listEntities, itemWidth);
        promotionFilterAdapter = new PromotionFilterAdapter(this);
        promotionFilterAdapter.setFilterClickListener(this);
        recycler.setLayoutManager(mLayoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recycler.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        delegateAdapter = new DelegateAdapter(mLayoutManager, false);
        recycler.setAdapter(delegateAdapter);
        promotionTopAdapter = new PromotionTopAdapter(this);
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(0, 2);
        recycledViewPool.setMaxRecycledViews(1, 10);
        recycler.setRecycledViewPool(recycledViewPool);
        delegateAdapter.addAdapter(promotionTopAdapter);    //活动头部UI(倒计时,banner等Adapter)
        delegateAdapter.addAdapter(promotionFilterAdapter); //活动筛选的Adapter(要吸顶,所以没写在TopAdapter里)
        delegateAdapter.addAdapter(promotionAdapter);       //活动商品列表的Adapter
        progressBar.setVisibility(View.VISIBLE);

    }

    private void initListener() {
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                currentPage = 1;
                requestPromotionsTask();
            }
        });
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (promotionAdapter.getLongClickPosition() != -1) {
                    listEntities.get(promotionAdapter.getLongClickPosition()).setLongClick(false);
                    promotionAdapter.setLongClickPosition(-1);
                    promotionAdapter.notifyDataSetChanged();
                }
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = mLayoutManager.findLastVisibleItemPosition();
                        if (last > promotionAdapter.getItemCount() - 3 && hasNext) {
                            currentPage++;
                            requestPromotionsTask();
                        }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisPosition = mLayoutManager.findLastVisibleItemPosition();
                int itemCount = mLayoutManager.getItemCount();
                //有wapbanner有上滑手势交互
                if (mPromotionBean != null && mPromotionBean.getData().getWapBanner() != null) {
                    getLastLocation();
                    float alpha = mLayoutManager.getOffsetToStart() / (float) ScreenUtil.dip2px(150);
                    changeAlpha(alpha);
                }


                if (lastVisPosition >= itemCount - 3 && !hasNext && currentPage > 1) {
                    if (endAdapter == null) {
                        ScrollEndBinder endBinder = new ScrollEndBinder();
                        endAdapter = new BaseVirtualAdapter<>(endBinder, 100);
                        delegateAdapter.addAdapter(endAdapter);
                    }
                } else {
                    if (endAdapter != null) {
                        delegateAdapter.removeAdapter(endAdapter);
                        endAdapter = null;
                    }
                }
            }
        });
    }

    private void getLastLocation() {
        View topView = mLayoutManager.getChildAt(0);
        if (topView != null) {
            //获取与该view的顶部的偏移量
            lastOffer = topView.getTop();
            //得到该View的数组位置
            lastPosition = mLayoutManager.getPosition(topView);
        }
    }

    public void changeAlpha(float alpha) {
        secondTitleLl.setAlpha(alpha);
        lastAlpha = alpha;
    }

    private void postAppLog(String log) {
        LogApi api = new LogApi();
        String string = System.currentTimeMillis() + "," + log;
        api.setLog(string);
        if (Session.getInstance().getUserFromFile(this) == null) {
            LogDB.getInstance(this).insert(string);
            return;
        }
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {

            }
        });
    }

    @OnClick({R.id.img_back, R.id.btn_reload, R.id.btn_cart, R.id.back_b, R.id.share_b, R.id.img_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_reload:
                progressBar.setVisibility(View.VISIBLE);
                currentPage = 1;
                requestPromotionsTask();
                break;
            case R.id.btn_cart:
                if (Util.loginChecked(PromotionsActivity.this, 100)) {
                    stat("V3购物车入口", "活动购物车",null);
                    Intent intent = new Intent(PromotionsActivity.this, CartActivity.class);
                    PromotionsActivity.this.startActivity(intent);
                }
                break;
            case R.id.back_b:
                finish();
                break;
            case R.id.share_b:
                onShare();
                break;
            case R.id.img_share:
                onShare();
                break;

        }
    }

    @Override
    public void filterListener(int type, String sortValue) {
        if (mPromotionBean==null)return;
        this.sortValue = sortValue;
        switch (type) {
            case 0://综合
                currentPage = 1;
                requestPromotionsTask();
                break;
            case 1://最新
                currentPage = 1;
                requestPromotionsTask();
                break;
            case 2://热销
                currentPage = 1;
                requestPromotionsTask();
                break;
            case 3://价格
                currentPage = 1;
                requestPromotionsTask();
                break;
            case 4://筛选
                if (newPop == null) {
                    if(mPromotionBean.getData().getPromotion().getPromotionScope()==0){
                        newPop = new ScreenNewPop(this, 0, "", "", -1, id, false);
                    }else{
                        newPop = new ScreenNewPop(this, 0, "", "", -1, id, true);
                    }
                    newPop.setOnConFirmClickListner(new ScreenNewPop.OnConFirmClickListner() {
                        @Override
                        public void callBack(Boolean backHasPromotion, Integer backSubscribe, Boolean backAfter, String backProductSellType, String backCategoryId, String backDesignerIds, Integer minPrice, Integer maxPrice) {
                            subscribe = backSubscribe;
                            min = minPrice;
                            max = maxPrice;
                            hasPromotion = backHasPromotion;
                            categoryId = backCategoryId;
                            productSellType = backProductSellType;
                            desiners = backDesignerIds;
                            isafter = backAfter;
                            currentPage = 1;
                            requestPromotionsTask();
                        }
                    });
                }
                newPop.show(getWindow().getDecorView());
                break;
        }
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
            upLoadBehaviorEvent(mPromotionBean, view, url);//用户行为埋点
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
                return Util.urlAction(PromotionsActivity.this, mUrl, Constants.Login.WEB_LOGIN, true, log + ",");
            }
        }
    }

    public void onShare() {
        int index = url.indexOf("isLogin=1");
        if (index > 0 && !Util.loginChecked(this, 0)) {
            return;
        }
        SharePop sharePop = new SharePop(PromotionsActivity.this);
        sharePop.setTitle(tvTitle.getText().toString());
        String des = "D2C这个活动太赞了,现在分享给你,赶紧来看看吧!";
        if (content != null && content.length() > 0) {
            des = content;
        }
        if (picture != null && picture.length() > 0) {
            sharePop.setImage(Util.getD2cPicUrl(picture, 100, 100), false);
            sharePop.setImage(Util.getD2cPicUrl(picture, 360, 500), true);
        }
        sharePop.setDescription(des);
        sharePop.setWebUrl(Constants.SHARE_URL + url);
        if (!Util.isEmpty(mPromotionBean.getData().getPromotion().getBackgroundUrl())){
            sharePop.setPoster(getPosterView());
            sharePop.setBgImageUrl(mPromotionBean.getData().getPromotion().getBackgroundUrl());
            sharePop.addPoster();
        }
        sharePop.show(getWindow().getDecorView());
    }

    private Poster getPosterView(){
        final Poster poster=new Poster();
        View view= LayoutInflater.from(this).inflate(R.layout.layout_page_poster_view,new RelativeLayout(this),false);
        final ImageView bgIv= (ImageView) view.findViewById(R.id.bg_iv);
        final RelativeLayout relativeLayout= (RelativeLayout) view.findViewById(R.id.relativeLayout);
        Glide.with(this).load(Util.getD2cPicUrl(mPromotionBean.getData().getPromotion().getBackgroundUrl())).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                poster.baseMap=true;
                RelativeLayout.LayoutParams rl= (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
                rl.width=resource.getWidth();
                bgIv.setImageBitmap(resource);
            }
        });
        poster.productMap=true;
        ImageView rq= (ImageView) view.findViewById(R.id.rq);
        rq.setImageBitmap(BitmapUtils.createWhiteQRImage(Constants.SHARE_URL + url, ScreenUtil.dip2px(62), ScreenUtil.dip2px(62), 0));
        poster.posterView=view;
        return poster;
    }

    public void onBackPressed(View v) {
        if (webView.getVisibility() == View.VISIBLE && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public void onFinishThis(View v) {
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webView.getVisibility() == View.VISIBLE && (keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void requestPromotionsTask() {
        if(isLoad){
            return;
        }
        isLoad=true;
        if(promotionNoDataAdpater!=null){
            delegateAdapter.removeAdapter(promotionNoDataAdpater);
            delegateAdapter.notifyDataSetChanged();
        }
        recycler.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        PromotionsApi api = new PromotionsApi();
        api.setP(currentPage);
        api.setPageSize(20);
        api.setO(sortValue);
        if (!Util.isEmpty(productSellType)) {
            api.setProductSellType(productSellType);
        }
        if (subscribe != null && subscribe != 0) {
            api.setSubscribe(subscribe);
        }
        if (hasPromotion != null && hasPromotion) {
            api.setHasPromotion(hasPromotion);
        }
        if (isafter) {
            api.setAfter(isafter);
        }
//        if (topId > -1) {
//            api.setTopId(topId);
//        }
        if (!Util.isEmpty(categoryId)) {
            api.setCategoryId(categoryId);
        }
        if (min > -1) {
            api.setMin(min);
        }
        if (max > -1) {
            api.setMax(max);
        }
        if (!Util.isEmpty(desiners)) {
            api.setDesigners(desiners);
        }
        api.setInterPath(String.format(Constants.PROMOTION_URL, id));
        isLoad = true;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<PromotionBean>() {
            @Override
            public void onResponse(PromotionBean promotionBean) {
                ptr.refreshComplete();
                progressBar.setVisibility(View.GONE);
                imgHint.setVisibility(View.GONE);
                btnReload.setVisibility(View.GONE);
                isLoad=false;
                mPromotionBean = promotionBean;
                if (!promotionBean.getData().getPromotion().getStatus()) {//活动下架就不展示活动内容了,展示空白页
                    setEmptyView(2);
                    Util.showToast(PromotionsActivity.this, "活动已下架~");
                    return;
                }
                if (Util.isEmpty(promotionBean.getData().getWapBanner())) { //没有banner显示默认的titleLayout
                    actionLayout.setVisibility(View.VISIBLE);
                } else {                 //有banner显示可交互的(滑动改变透明度)的titleLayout
                    actionLayout.setVisibility(View.GONE);
                }
                if (Util.isEmpty(promotionBean.getData().getWapBanner()) && promotionBean.getData().getPromotion() != null && promotionBean.getData().getPromotion().getAdvance() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        actionLayout.setElevation(0);
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        actionLayout.setElevation(4);
                    }
                }

                if (currentPage == 1) {
                    listEntities.clear();
                }
                if (promotionTopAdapter != null) {
                    promotionTopAdapter.setPromotionBean(mPromotionBean);
                    promotionTopAdapter.notifyDataSetChanged();
                }
                if (promotionAdapter != null) {
                    promotionAdapter.setPromotionBean(promotionBean);
                    promotionAdapter.notifyDataSetChanged();
                }
                if (promotionBean.getData().getProducts() == null ) {
                    setEmptyView(2);
                }else if(promotionBean.getData().getProducts().getList()==null || promotionBean.getData().getProducts().getList().size() == 0){
                    setEmptyView(Constants.NO_DATA);
                }

                if (promotionBean.getData().getProducts() != null) {
                    hasNext = promotionBean.getData().getProducts().isNext();
                    ptr.setVisibility(View.VISIBLE);
                    webContainer.setVisibility(View.GONE);
                    tvTitle.setText(promotionBean.getData().getName());
                    picture = promotionBean.getData().getPicture();
                    content = promotionBean.getData().getContent();
                    int size = promotionBean.getData().getProducts().getList().size();
                    if (size > 0) {
                        listEntities.addAll(promotionBean.getData().getProducts().getList());
                        promotionAdapter.notifyDataSetChanged();
                    }
                    if (!promotionBean.getData().getProducts().isNext()) {
                        isEnd = true;
                    } else {
                        isEnd = false;
                    }
                    isLoad = false;
                    imgShare.setVisibility(View.VISIBLE);
                    upLoadBehaviorEvent(promotionBean, null, null);//用户行为埋点
                } else {
                    if (url != null) {
                        loadUrl();
                        webContainer.setVisibility(View.VISIBLE);
                        recycler.setVisibility(View.GONE);
                        imgHint.setVisibility(View.GONE);
                        btnReload.setVisibility(View.GONE);
                        ptr.setVisibility(View.GONE);
                        imgShare.setVisibility(View.VISIBLE);
                        imgShare.setEnabled(false);
                    } else {
                        setEmptyView(Constants.NO_DATA);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                isLoad=false;
                ptr.refreshComplete();
                setEmptyView(Constants.NET_DISCONNECT);
//                if (error instanceof HttpError) {
//                    HttpError httpError = (HttpError) error;
//                    if (httpError.getStatus() == -1) {
//                        setEmptyView(Constants.NO_DATA);
//                        Util.showToast(PromotionsActivity.this, httpError.getMessage());
//                    }
//                } else {
//                    setEmptyView(Constants.NET_DISCONNECT);
//                }
            }
        });
    }

    private void upLoadBehaviorEvent(PromotionBean detailBean, WebView view, String wapUrl) { //普通的点击行为
        String requestUrl = Constants.API_URL + Constants.POST_BEHAVIOR_EVENT_URL;
        JSONObject tmpObj = null;
        tmpObj = new JSONObject();
        JSONObject data = null;
        data = new JSONObject();
        try {
            tmpObj.put("event", "打开活动");
            data.put("targetId", id);
            if (view == null) {
                data.put("targetName", detailBean.getData().getPromotion().getPromotionTypeName());
                data.put("targetPath", detailBean.getData().getPromotion().getPromotionUrl());
            } else {
                data.put("targetName", view.getTitle());
                data.put("targetPath", url);
            }
            tmpObj.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String personInfos = tmpObj.toString(); // 将JSONArray转换得到String
        Charset chrutf = Charset.forName("UTF-8");
        String params = new String(personInfos.getBytes(), chrutf);
        try {
            HttpUtils.doPostAsyn(requestUrl, params, new HttpUtils.CallBack() {
                @Override
                public void onRequestComplete(final String result) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setEmptyView(int type) {
        if (type == Constants.NO_DATA) {    //网络请求成功但只有商品的list为空
            if(promotionNoDataAdpater==null){
                promotionNoDataAdpater = new PromotionNoDataAdpater(this);  //当活动的商品list为空时(筛选时候会出现)充当空界面
            }
            if(delegateAdapter!=null){
                delegateAdapter.addAdapter(promotionNoDataAdpater);
            }
        } else if(type == 2){      //网络请求成功但返回结果为空
            recycler.setVisibility(View.GONE);
            imgHint.setVisibility(View.VISIBLE);
            imgHint.setImageResource(R.mipmap.icon_empty_default);
            btnReload.setVisibility(View.VISIBLE);
            btnReload.setText("暂无数据");
            btnReload.setBackgroundColor(getResources().getColor(R.color.transparent));
        }else{      //网络请求失败
            recycler.setVisibility(View.GONE);
            btnReload.setText("重新加载");
            btnReload.setBackgroundResource(R.drawable.sp_line);
            btnReload.setVisibility(View.VISIBLE);
            imgHint.setVisibility(View.VISIBLE);
            imgHint.setImageResource(R.mipmap.ic_no_net);
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

    @Override
    protected void onResume() {
        Util.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        Util.onPause(this);
        super.onPause();
    }

    private void loadUrl() {
        if (url == null) {
            return;
        }
        UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(this);
        if (user != null) {
            token = user.getUserToken();
        } else {
            token = "";
        }
        if (webView != null) {
            actionLayout.setVisibility(View.VISIBLE);
            btnCart.setVisibility(View.GONE);
            webView.loadUrl(String.format(Constants.INVOKE_URL, token, Util.toURLEncode(url)));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.Login.WEB_LOGIN && resultCode == RESULT_OK) {
            loadUrl();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        if(promotionTopAdapter!=null){
            promotionTopAdapter.releaseResource();
        }
        webContainer.removeAllViews();
        webView.destroy();
        webView = null;
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
            Object func = bean.getValue("func");
            Map<String, String> map = new HashMap<>();
            map.put("token", token);
            map.put("type", "share");
            map.put("status", "1");
            Gson gson = new Gson();
            webView.callHandler((func != null) ? (String) func : "b_share", gson.toJson(map), new CallBackFunction() {
                @Override
                public void onCallBack(String data) {
                }
            });
        } else if (bean.getType() == Constants.GlobalType.LOGOUT) {
            loadUrl();
        }
    }
}