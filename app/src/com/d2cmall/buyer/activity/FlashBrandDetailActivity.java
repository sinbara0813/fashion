package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.FlashBuyAdapter;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.FlashApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.FlashProductListBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.FlashNoticePop;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by rookie on 2018/5/4.
 */

public class FlashBrandDetailActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;
    @Bind(R.id.tag)
    TextView tag;
    @Bind(R.id.flash_hour_tv)
    TextView flashHourTv;
    @Bind(R.id.flash_minute_tv)
    TextView flashMinuteTv;
    @Bind(R.id.flash_mouse_tv)
    TextView flashMouseTv;
    @Bind(R.id.flash_time_ll)
    RelativeLayout flashTimeLl;
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout ptr;
    @Bind(R.id.iv_brand)
    ImageView ivBrand;
    @Bind(R.id.activity_main)
    CoordinatorLayout activityMain;
    @Bind(R.id.app_bar)
    AppBarLayout appBarLayout;
    @Bind(R.id.tv_remark)
    TextView tvRemark;
    private int promotionId;
    private List<Object> list;
    private int p = 1;
    private boolean isRefresh;
    private VirtualLayoutManager layoutManager;
    private DelegateAdapter delegateAdapter;
    private FlashBuyAdapter flashBuyAdapter;
    private boolean hasNext;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setTime();
        }
    };
    private FlashProductListBean dataBean;
    private String picUrl;
    private boolean canReflash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_brand_detail);
        ButterKnife.bind(this);
        String title = getIntent().getStringExtra("title");
        promotionId = getIntent().getIntExtra("promotionId", 0);
        picUrl = getIntent().getStringExtra("pic");
        tvTitle.setText(title);
        progressBar.setVisibility(View.VISIBLE);
        recycleView.setVisibility(View.GONE);
        init();
        loadData();
    }

    private void setTime() {
        long startTime = dataBean.getData().getFlashPromotion().getStartTimeStamp();
        long endTime = dataBean.getData().getFlashPromotion().getEndTimeStamp();
        if (startTime - System.currentTimeMillis() > 0) {//未开始
            endTime = startTime;
            tag.setText("距开始");
        } else {
            tag.setText("距结束");
        }
        setPromotionTime(endTime, flashHourTv, flashMinuteTv, flashMouseTv);
        handler.sendEmptyMessageDelayed(1, 1000);
    }

    private void setPromotionTime(long endTime, TextView hourTv, TextView minuteTv, TextView mouseTv) {
        long offer = endTime - System.currentTimeMillis();
        long hour = offer / (60 * 60 * 1000);
        long minute = (offer / (60 * 1000)) % 60;
        long mouse = (offer / 1000) % 60;
        hourTv.setText(addZero((int) hour));
        minuteTv.setText(addZero((int) minute));
        mouseTv.setText(addZero((int) mouse));
    }

    private String addZero(int num) {
        if (num < 10) {
            return "0" + num;
        } else {
            return String.valueOf(num);
        }
    }

    private void init() {
        UniversalImageLoader.displayImage(this, picUrl, ivBrand, R.mipmap.ic_logo_empty2);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    canReflash = true;
                } else {
                    canReflash = false;
                }
            }
        });
        list = new ArrayList<>();
        ptr.setHeadLabel(getString(R.string.label_d2c_go));
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return canReflash;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refresh();
            }
        });
        layoutManager = new VirtualLayoutManager(this);
        recycleView.setLayoutManager(layoutManager);
        delegateAdapter = new DelegateAdapter(layoutManager, false);
        flashBuyAdapter = new FlashBuyAdapter(list, this, true);
        flashBuyAdapter.setCallBack(new FlashBuyAdapter.FlashProductRemindCallBack() {
            @Override
            public void notice(long id) {
                noticeOn(id);
            }
        });
        delegateAdapter.addAdapter(flashBuyAdapter);
        recycleView.setAdapter(delegateAdapter);
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = layoutManager.findLastVisibleItemPosition();
                        if (last > delegateAdapter.getItemCount() - 3 && hasNext) {
                            p++;
                            loadData();
                        }
                }
            }
        });
    }

    private void noticeOn(long id) {
        SimpleApi api = new SimpleApi();
        api.setInterPath("/v3/api/flashpromotion/remind/" + id);
        api.setMethod(BaseApi.Method.POST);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                FlashNoticePop noticePop = new FlashNoticePop(FlashBrandDetailActivity.this);
                noticePop.show(FlashBrandDetailActivity.this.getWindow().getDecorView());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    Intent intent = new Intent(FlashBrandDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Util.showToast(FlashBrandDetailActivity.this, Util.checkErrorType(error));
                }
            }
        });
    }

    private void loadData() {
        FlashApi api = new FlashApi();
        api.setInterPath("/v3/api/flashpromotion/products/list");
        api.promotionId = promotionId;
        api.setPageNumber(p);
        api.setPageSize(20);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<FlashProductListBean>() {
            @Override
            public void onResponse(FlashProductListBean flashProductListBean) {
                dataBean = flashProductListBean;
                if (isRefresh) {
                    isRefresh = false;
                    ptr.refreshComplete();
                }
                tvRemark.setText(flashProductListBean.getData().getFlashPromotion().getSessionRemark());
                hasNext = flashProductListBean.getData().getProducts().isNext();
                progressBar.setVisibility(View.GONE);
                if (flashProductListBean.getData().getProducts().getList().size() > 0) {
                    if (p == 1) {
                        list.clear();
                        setTime();
                    }
                    list.addAll(flashProductListBean.getData().getProducts().getList());
                    if (flashProductListBean.getData().getFlashPromotion().getStartTimeStamp() < System.currentTimeMillis()) {
                        flashBuyAdapter.setStart(false);
                    } else {
                        flashBuyAdapter.setStart(true);
                    }
                }
                progressBar.setVisibility(View.GONE);
                recycleView.setVisibility(View.VISIBLE);
                flashBuyAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (p == 1) {
                    progressBar.setVisibility(View.GONE);
                    recycleView.setVisibility(View.VISIBLE);
                }
                Util.showToast(FlashBrandDetailActivity.this, Util.checkErrorType(error));
            }
        });
    }

    public void refresh() {
        isRefresh = true;
        p = 1;
        loadData();
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        handler = null;
    }
}
