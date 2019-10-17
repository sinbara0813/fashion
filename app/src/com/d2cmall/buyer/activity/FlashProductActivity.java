package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.FlashProductAdapter;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.FlashApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.FlashProductListBean;
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
 * Date: 2017/12/14 10:28
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FlashProductActivity extends BaseActivity implements FlashProductAdapter.CallBack{

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.img_share)
    ImageView imgShare;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
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
    @Bind(R.id.bottom_ll)
    LinearLayout bottomLl;
    @Bind(R.id.line)
    View line;

    private VirtualLayoutManager layoutManage;
    private DelegateAdapter delegateAdapter;

    private FlashProductAdapter flashProductAdapter;
    private List<FlashProductListBean.DataBean.ProductsBean.ListBean> list;

    private int promotionId;
    private boolean isRefresh;

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
        promotionId=getIntent().getIntExtra("promotionId",0);
        initView();
        list = new ArrayList<>();
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

        flashProductAdapter = new FlashProductAdapter(this, list);
        flashProductAdapter.setCallBack(this);
        delegateAdapter.addAdapter(flashProductAdapter);

        recycleView.setAdapter(delegateAdapter);

        progressBar.setVisibility(View.VISIBLE);
        loadProduct();

        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = layoutManage.findLastVisibleItemPosition();
                        if (last > delegateAdapter.getItemCount() - 3 && hasNext) {
                            p++;
                            loadProduct();
                        }
                }
            }
        });
    }

    private void initView(){
        RelativeLayout.LayoutParams rl= (RelativeLayout.LayoutParams) ptr.getLayoutParams();
        rl.setMargins(0,0,0,0);
        tvTitle.setText("D2C快抢");
        bottomLl.setVisibility(View.GONE);
        line.setVisibility(View.GONE);
    }

    @OnClick({R.id.img_back,R.id.img_share})
    public void click(View view){
        switch (view.getId()){
            case R.id.img_back:
                finish();
                break;
            case R.id.img_share:
                share();
                break;
        }
    }

    /**
     * 通过活动id拉取商品列表
     *
     */
    private void loadProduct() {
        FlashApi api = new FlashApi();
        api.setInterPath("/v3/api/flashpromotion/products/list");
        api.promotionId = promotionId;
        api.setPageNumber(p);
        api.setPageSize(10);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<FlashProductListBean>() {
            @Override
            public void onResponse(FlashProductListBean flashProductListBean) {
                if (isFinishing()){
                    return;
                }
                if (isRefresh) {
                    isRefresh=false;
                    ptr.refreshComplete();
                }
                progressBar.setVisibility(View.GONE);
                if (flashProductListBean != null) {
                    hasNext=flashProductListBean.getData().getProducts().isNext();
                    if (flashProductListBean.getData().getProducts().getList().size() > 0) {
                        if (p==1){
                            list.clear();
                        }
                        list.addAll(flashProductListBean.getData().getProducts().getList());
                        if (flashProductListBean.getData().getFlashPromotion().getStartTimeStamp()<System.currentTimeMillis()){
                            flashProductAdapter.setType(1);
                        }else {
                            flashProductAdapter.setType(0);
                        }
                        flashProductAdapter.setFlashPromotionBean(flashProductListBean.getData().getFlashPromotion());
                        if (flashProductListBean.getData().getBrandFlashPromotions()!=null&&flashProductListBean.getData().getBrandFlashPromotions().size()>0){
                            flashProductAdapter.setBrandFlashPromotions(flashProductListBean.getData().getBrandFlashPromotions());
                        }
                        flashProductAdapter.notifyDataSetChanged();
                    }
                }
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

    public void share(){
        SharePop sharePop = new SharePop(FlashProductActivity.this);
        sharePop.setTitle("D2C快抢 限时秒杀");
        sharePop.setDescription("全球好设计  整点限时限量秒杀，先到先得！");
        sharePop.setImage(Util.getD2cPicUrl("/app/a/17/12/18/eb5fc0716a069ba9d9d788dc44a410b3", 100, 100), false);
        sharePop.setImage(Util.getD2cPicUrl("/app/a/17/12/18/eb5fc0716a069ba9d9d788dc44a410b3", 360, 500), true);
        sharePop.setWebUrl(Constants.SHARE_URL+"/flashpromotion/products/list?id="+promotionId);
        sharePop.show(getWindow().getDecorView());
    }

    public void refresh(){
        isRefresh=true;
        p=1;
        loadProduct();
    }

    private void notice(long id){
        SimpleApi api=new SimpleApi();
        api.setInterPath("/v3/api/flashpromotion/remind/"+id);
        api.setMethod(BaseApi.Method.POST);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                FlashNoticePop noticePop = new FlashNoticePop(FlashProductActivity.this);
                noticePop.show(getWindow().getDecorView());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError){
                    Intent intent=new Intent(FlashProductActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else {
                    Util.showToast(FlashProductActivity.this,Util.checkErrorType(error));
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
    protected void onDestroy() {
        if (flashProductAdapter!=null){
            flashProductAdapter.recycle();
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
