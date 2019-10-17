package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.FlashBrandAdapter;
import com.d2cmall.buyer.adapter.FlashHeadAdapter;
import com.d2cmall.buyer.adapter.FlashProductAdapter;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.FlashApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.AdBean;
import com.d2cmall.buyer.bean.FlashBrandListBean;
import com.d2cmall.buyer.bean.FlashProductListBean;
import com.d2cmall.buyer.bean.FlashSessionListBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.FlashNoticePop;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;
import com.d2cmall.buyer.widget.SharePop;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/12/12 10:16
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FlashPromotionActivity extends BaseActivity implements FlashHeadAdapter.CallBack,FlashProductAdapter.CallBack {

    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout ptr;
    @Bind(R.id.img_hint)
    ImageView imgHint;
    @Bind(R.id.btn_reload)
    TextView btnReload;
    @Bind(R.id.empty_hint_layout)
    LinearLayout emptyHintLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.flash_tv)
    ImageView flashTv;
    @Bind(R.id.brand_tv)
    ImageView brandTv;
    @Bind(R.id.bottom_ll)
    LinearLayout bottomLl;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.img_share)
    ImageView imgShare;

    private VirtualLayoutManager layoutManage;
    private DelegateAdapter delegateAdapter;

    private FlashHeadAdapter flashHeadAdapter;
    private AdBean productAd;
    private AdBean brandAd;
    private List<FlashSessionListBean.DataBean.ListBean> productSessionList;
    private int productSessionId;
    private List<FlashSessionListBean.DataBean.ListBean> brandSessionList;
    private int brandSessionId;

    private FlashProductAdapter flashProductAdapter;
    private List<FlashProductListBean.DataBean.ProductsBean.ListBean> list;

    private FlashBrandAdapter flashBrandAdapter;
    private List<FlashBrandListBean.DataBean.BrandsBean> brandList;

    private boolean isRefresh;
    private int currentType=1; //1 是限时购 2 是大牌
    private boolean hasNext;
    private int p=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_promotion);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        initView();
        list = new ArrayList<>();
        brandList=new ArrayList<>();
        ptr.setHeadLabel(getString(R.string.label_d2c_go));
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refresh();
            }
        });
        layoutManage = new VirtualLayoutManager(this);
        recycleView.setLayoutManager(layoutManage);
        delegateAdapter = new DelegateAdapter(layoutManage, false);

        flashHeadAdapter = new FlashHeadAdapter(this);
        flashHeadAdapter.setType(1);
        flashHeadAdapter.setCallBack(this);
        delegateAdapter.addAdapter(flashHeadAdapter);

        flashProductAdapter = new FlashProductAdapter(this, list);
        flashProductAdapter.setCallBack(this);
        delegateAdapter.addAdapter(flashProductAdapter);

        flashBrandAdapter=new FlashBrandAdapter(this,brandList);

        recycleView.setAdapter(delegateAdapter);

        progressBar.setVisibility(View.GONE);
        loadBanner(currentType);

        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = layoutManage.findLastVisibleItemPosition();
                        if (last > delegateAdapter.getItemCount() - 3 && hasNext) {
                            p++;
                            if (currentType==1){ //商品
                                loadProduct(productSessionId);
                            }else if (currentType==2){ //品牌
                                //loadBrand(brandSessionId);
                            }
                        }
                }
            }
        });
    }

    private void initView() {
        tvTitle.setText("D2C快抢");
        flashTv.setSelected(true);
    }

    /**
     * 获取广告图
     *
     * @param type 1是商品广告 2是品牌广告
     */
    private void loadBanner(final int type) {
        SimpleApi api = new SimpleApi();
        if (type == 1) {
            api.setInterPath("/v3/api/ad/FLASHPROMOTION/FLASHPRODUCT");
        } else {
            api.setInterPath("/v3/api/ad/FLASHPROMOTION/FLASHBRAND");
        }
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<AdBean>() {
            @Override
            public void onResponse(AdBean adBean) {
                if (isFinishing()){
                    return;
                }
                if (isRefresh){
                    isRefresh=false;
                    ptr.refreshComplete();
                }
                progressBar.setVisibility(View.GONE);
                if (type == 1) {
                    productAd = adBean;
                } else {
                    brandAd = adBean;
                }
                flashHeadAdapter.setType(type);
                flashHeadAdapter.setAdBean(adBean);
                flashHeadAdapter.notifyDataSetChanged();
                getSession(type);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (isFinishing()){
                    return;
                }
                if (isRefresh){
                    isRefresh=false;
                    ptr.refreshComplete();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 获取场次
     *
     * @param type 1 是商品活动场次 2 是品牌活动场次
     */
    private void getSession(final int type) {
        SimpleApi api = new SimpleApi();
        if (type == 1) {
            api.setInterPath("/v3/api/flashpromotion/product/session");
        } else {
            api.setInterPath("/v3/api/flashpromotion/brand/session");
        }
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<FlashSessionListBean>() {
            @Override
            public void onResponse(FlashSessionListBean flashSessionListBean) {
                if (flashSessionListBean != null) {
                    flashHeadAdapter.setList(flashSessionListBean.getData().getList());
                    flashHeadAdapter.setDefaultId(flashSessionListBean.getData().getCurrentId());
                    flashHeadAdapter.setCurrentId(flashSessionListBean.getData().getCurrentId());
                    flashHeadAdapter.notifyDataSetChanged();
                }
                if (type == 1) {
                    productSessionList=flashSessionListBean.getData().getList();
                    productSessionId=flashSessionListBean.getData().getCurrentId();
                    p=1;
                    loadProduct(flashSessionListBean.getData().getCurrentId());
                } else {
                    brandSessionList=flashSessionListBean.getData().getList();
                    brandSessionId=flashSessionListBean.getData().getCurrentId();
                    p=1;
                    loadBrand(flashSessionListBean.getData().getCurrentId());
                }
            }
        });
    }

    /**
     * 通过活动id拉取商品列表
     *
     * @param promotionId
     */
    private void loadProduct(int promotionId) {
        FlashApi api = new FlashApi();
        api.setInterPath("/v3/api/flashpromotion/products/list");
        api.promotionId = promotionId;
        api.setPageNumber(p);
        api.setPageSize(10);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<FlashProductListBean>() {
            @Override
            public void onResponse(FlashProductListBean flashProductListBean) {
                if (flashProductListBean != null) {
                    hasNext=flashProductListBean.getData().getProducts().isNext();
                    if (p==1){
                        list.clear();
                    }
                    if (flashProductListBean.getData().getProducts().getList().size() > 0) {
                        list.addAll(flashProductListBean.getData().getProducts().getList());
                        if (flashProductListBean.getData().getBrandFlashPromotions()!=null&&flashProductListBean.getData().getBrandFlashPromotions().size()>0){
                            flashProductAdapter.setBrandFlashPromotions(flashProductListBean.getData().getBrandFlashPromotions());
                        }else {
                            flashProductAdapter.setBrandFlashPromotions(null);
                        }
                        if (flashProductListBean.getData().getFlashPromotion().getStartTimeStamp()<System.currentTimeMillis()){
                            flashProductAdapter.setType(1);
                        }else {
                            flashProductAdapter.setType(0);
                        }
                    }
                    flashProductAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 通过活动id拉取品牌列表
     *
     * @param promotionId
     */
    private void loadBrand(int promotionId) {
        FlashApi api = new FlashApi();
        api.setInterPath("/v3/api/flashpromotion/brand/list");
        api.promotionId = promotionId;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<FlashBrandListBean>() {
            @Override
            public void onResponse(FlashBrandListBean flashBrandListBean) {
                if (flashBrandListBean!=null&&flashBrandListBean.getData().getBrands()!=null){
                    if (p==1){
                        brandList.clear();
                    }
                    brandList.addAll(flashBrandListBean.getData().getBrands());
                    flashBrandAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @OnClick({R.id.brand_tv, R.id.flash_tv,R.id.img_back,R.id.img_share})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.brand_tv:
                currentType=2;
                brandTv.setSelected(true);
                flashTv.setSelected(false);
                if (brandAd!=null&&brandSessionList!=null){
                    flashHeadAdapter.setType(currentType);
                    flashHeadAdapter.setAdBean(brandAd);
                    flashHeadAdapter.setList(brandSessionList);
                    flashHeadAdapter.setCurrentId(brandSessionId);
                    flashHeadAdapter.notifyDataSetChanged();
                }else {
                    loadBanner(currentType);
                }
                delegateAdapter.removeAdapter(flashProductAdapter);
                delegateAdapter.addAdapter(flashBrandAdapter);
                break;
            case R.id.flash_tv:
                currentType=1;
                brandTv.setSelected(false);
                flashTv.setSelected(true);
                if (productAd!=null&&productSessionList!=null){
                    flashHeadAdapter.setType(currentType);
                    flashHeadAdapter.setAdBean(productAd);
                    flashHeadAdapter.setList(productSessionList);
                    flashHeadAdapter.setCurrentId(productSessionId);
                    flashHeadAdapter.notifyDataSetChanged();
                }else {
                    loadBanner(currentType);
                }
                delegateAdapter.addAdapter(flashProductAdapter);
                delegateAdapter.removeAdapter(flashBrandAdapter);
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.img_share:
                share();
                break;
        }
    }

    private void share(){
        SharePop sharePop = new SharePop(FlashPromotionActivity.this);
        sharePop.setTitle("D2C快抢 限时秒杀");
        sharePop.setDescription("全球好设计  整点限时限量秒杀，先到先得！");
        sharePop.setImage(Util.getD2cPicUrl("/app/a/17/12/18/eb5fc0716a069ba9d9d788dc44a410b3", 100, 100), false);
        sharePop.setImage(Util.getD2cPicUrl("/app/a/17/12/18/eb5fc0716a069ba9d9d788dc44a410b3", 360, 500), true);
        if (currentType==1){
            sharePop.setWebUrl(Constants.SHARE_URL+"/flashpromotion/product/session");
        }else {
            sharePop.setWebUrl(Constants.SHARE_URL+"/flashpromotion/brand/session");
        }
        sharePop.show(getWindow().getDecorView());
    }
    private void refresh() {
        isRefresh=true;
        loadBanner(currentType);
    }

    private void notice(long id){
        SimpleApi api=new SimpleApi();
        api.setInterPath("/v3/api/flashpromotion/remind/"+id);
        api.setMethod(BaseApi.Method.POST);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                FlashNoticePop noticePop = new FlashNoticePop(FlashPromotionActivity.this);
                noticePop.show(getWindow().getDecorView());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError){
                    Intent intent=new Intent(FlashPromotionActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else {
                    Util.showToast(FlashPromotionActivity.this,Util.checkErrorType(error));
                }
            }
        });
    }

    private void toDetailActivity(long id){
        Intent intent=new Intent(this,ProductDetailActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    @Override
    public void selectSession(int type, int promotionId) {
        if (type == 1) {
            productSessionId=promotionId;
            p=1;
            loadProduct(promotionId);
        } else {
            brandSessionId=promotionId;
            p=1;
            loadBrand(promotionId);
        }
    }

    @Override
    protected void onDestroy() {
        if (flashHeadAdapter!=null){
            flashHeadAdapter.recycle();
        }
        super.onDestroy();
    }

    /**
     *
     * @param type 0 是提醒我 1是跳商品详情页
     * @param id
     */
    @Override
    public void notice(int type, long id) {
        if (type==0){
            notice(id);
        }else {
            toDetailActivity(id);
        }
    }
}
