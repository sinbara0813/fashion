package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.MainManAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.MainBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.TitleUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import q.rorbin.badgeview.QBadgeView;

/**
 * 活动列表
 * Author: hrb
 * Date: 2016/08/19 13:55
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class PromotionListActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.tag)
    View tag;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.img_hint)
    ImageView imgHint;
    @Bind(R.id.btn_reload)
    TextView btnReload;
    @Bind(R.id.empty_hint_layout)
    LinearLayout emptyHintLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout ptr;
    @Bind(R.id.btn_cart)
    FloatingActionButton cartIv;
    private QBadgeView cartBade;
    private VirtualLayoutManager layoutManage;
    private DelegateAdapter delegateAdapter;

    private List<MainBean.DataEntity.ContentEntity> dataList;
    private MainManAdapter adapter;

    private int id;
    private int currentPage = 1;
    private boolean isRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_list);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        TitleUtil.setBack(this);
        TitleUtil.setTitle(this, R.string.label_promotions);
        id = getIntent().getIntExtra("id", 0);
        dataList = new ArrayList<>();

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

        adapter = new MainManAdapter(this, dataList);
        layoutManage = new VirtualLayoutManager(this);
        recycleView.setLayoutManager(layoutManage);
        delegateAdapter = new DelegateAdapter(layoutManage, false);
        recycleView.setAdapter(delegateAdapter);
        delegateAdapter.addAdapter(adapter);
        loadData();
        initListener();
    }

    private void initListener() {
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dataList.size() > 0 && layoutManage.findLastVisibleItemPosition() > 0 && layoutManage.findLastVisibleItemPosition() > layoutManage.getItemCount() - 3) {
                    currentPage++;
                    loadData();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void loadData() {
        SimpleApi api = new SimpleApi();
        api.setPageSize(20);
        api.setP(currentPage);
        api.setInterPath(String.format(Constants.GET_MAIN_PROMOTION_LIST, id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MainBean>() {
            @Override
            public void onResponse(MainBean mainBean) {
                if (isFinishing()) {
                    return;
                }
                if (isRefresh) {
                    ptr.refreshComplete();
                    isRefresh = false;
                }
                if (mainBean.getData().getContent().size() > 0) {
                    if (currentPage == 1 && dataList.size() > 0) {
                        dataList.clear();
                    }
                    checkList(mainBean.getData().getContent());
                    dataList.addAll(mainBean.getData().getContent());
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (isRefresh) {
                    ptr.refreshComplete();
                    isRefresh = false;
                }
                Util.showToast(PromotionListActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void checkList(List<MainBean.DataEntity.ContentEntity> dataList) {
        int size = dataList.size();
        for (int i = 0; i < size; i++) {
            MainBean.DataEntity.ContentEntity bean = dataList.get(i);
            bean.setVisible(0);
            bean.setMore(0);
        }
    }

    private void refresh() {
        isRefresh = true;
        currentPage = 1;
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    @OnClick(R.id.btn_cart)
    public void onViewClicked() {
        if (Util.loginChecked(this, 100)) {
            stat("V3购物车入口", "活动购物车",null);
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        }
    }

}
