package com.d2cmall.buyer.activity;

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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.CollectAdapter;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.MyCollectProductBean;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.binder.ScrollEndBinder;
import com.d2cmall.buyer.holder.DefaultHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;
import com.zmxy.ZMCertification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/21 13:39
 * Copyright (c) 2016 d2cmall. All rights reserved.
 * 我的收藏
 */
public class CollectActivity extends BaseActivity{

    @Bind(R.id.back_iv)
    ImageView mBackIv;
    @Bind(R.id.name_tv)
    TextView mNameTv;
    @Bind(R.id.title_right)
    TextView mTitleRight;
    @Bind(R.id.title_layout)
    RelativeLayout mTitleLayout;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.rl_collect)
    RelativeLayout mLlCollect;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout mPtr;
    @Bind(R.id.img_hint)
    ImageView mImgHint;
    @Bind(R.id.btn_reload)
    TextView mBtnReload;
    @Bind(R.id.empty_hint_layout)
    LinearLayout mEmptyHintLayout;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;

    private CollectAdapter collectAdapter;
    private int currentPage = 1;
    private boolean hasNext =true;
    private ArrayList<MyCollectProductBean.DataBean.MyCollectionsBean.ListBean> collectProductList = new ArrayList<>();
    private VirtualLayoutManager mLayoutManager;
    private DelegateAdapter delegateAdapter;
    private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志
    private ZMCertification zmCertification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        initTitle();
        doBusiness();
        initListener();
    }

    private void doBusiness() {
        //改背景色
        mLlCollect.setBackgroundColor(getResources().getColor(R.color.bg_color));
        mLayoutManager = new VirtualLayoutManager(CollectActivity.this);
        int itemWidth = (ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(48)) / 2;
        collectAdapter = new CollectAdapter(this, collectProductList, itemWidth);
        collectAdapter.setCollect(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        delegateAdapter = new DelegateAdapter(mLayoutManager, true);
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(0,2);
        mRecyclerView.setRecycledViewPool(recycledViewPool);
        mRecyclerView.setAdapter(delegateAdapter);
        delegateAdapter.addAdapter(collectAdapter);
        mProgressBar.setVisibility(View.VISIBLE);
        requestCollectProductTask();
    }

    private void requestCollectProductTask() {
        SimpleApi api = new SimpleApi();
        api.setMethod(BaseApi.Method.GET);
        api.setP(currentPage);
        api.setPageSize(20);
        api.setInterPath(String.format(Constants.MY_COLLECT_URL));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MyCollectProductBean>() {
            @Override
            public void onResponse(MyCollectProductBean myCollectProductBean) {
                if(isFinishing() || mProgressBar==null){
                    return;
                }
                mPtr.refreshComplete();
                mLlCollect.setBackgroundColor(getResources().getColor(R.color.bg_color));
                mBtnReload.setVisibility(View.GONE);
                mImgHint.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);
                if (currentPage == 1) {
                    collectProductList.clear();
                }
                mRecyclerView.setVisibility(View.VISIBLE);
                int size = myCollectProductBean.getData().getMyCollections().getList().size();
                if (size > 0) {
                    List<MyCollectProductBean.DataBean.MyCollectionsBean.ListBean> collectList = myCollectProductBean.getData().getMyCollections().getList();
                    CollectStoreSortUtil collectStoreSortUtil = new CollectStoreSortUtil();
                    CollectMarkSortUtil collectMarkSortUtil = new CollectMarkSortUtil();
                    collectProductList.addAll(collectList);
                    //售罄排序
                    Collections.sort(collectProductList,collectStoreSortUtil);
                    //下架排序
                    Collections.sort(collectProductList,collectMarkSortUtil);
                } else {
                    setEmptyView(Constants.NO_DATA);
                }
                if (collectAdapter != null) {
                    collectAdapter.notifyDataSetChanged();
                    hasNext = myCollectProductBean.getData().getMyCollections().isNext();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mPtr.refreshComplete();
                mProgressBar.setVisibility(View.GONE);
                Util.showToast(CollectActivity.this, Util.checkErrorType(error));
                setEmptyView(Constants.NET_DISCONNECT);
            }
        });
    }

    private void initListener() {
        mPtr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                currentPage = 1;
                requestCollectProductTask();
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (collectAdapter.getLongClickPosition() != -1) {
                    (collectProductList.get(collectAdapter.getLongClickPosition())).setLongClick(false);
                    collectAdapter.setLongClickPosition(-1);
                    collectAdapter.notifyDataSetChanged();
                }
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = mLayoutManager.findLastVisibleItemPosition();
                        if (last > collectAdapter.getItemCount() - 3 && hasNext) {
                            currentPage++;
                            requestCollectProductTask();
                        }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisPosition=mLayoutManager.findLastVisibleItemPosition();
                int itemCount=mLayoutManager.getItemCount();
                if (lastVisPosition>=itemCount-3 && !hasNext && currentPage>1){
                    if (endAdapter==null){
                        ScrollEndBinder endBinder = new ScrollEndBinder();
                        endAdapter = new BaseVirtualAdapter<>(endBinder,100);
                        delegateAdapter.addAdapter(endAdapter);
                    }
                }else {
                    if (endAdapter!=null){
                        delegateAdapter.removeAdapter(endAdapter);
                        endAdapter=null;
                    }
                }
            }
        });
    }
    private void setEmptyView(int type) {
        mLlCollect.setBackgroundColor(getResources().getColor(R.color.color_white));
        mRecyclerView.setVisibility(View.GONE);
        if (type == Constants.NO_DATA) {
            mImgHint.setVisibility(View.VISIBLE);
            mImgHint.setImageResource(R.mipmap.ic_empty_collect);
        } else {
            mBtnReload.setVisibility(View.VISIBLE);
            mImgHint.setVisibility(View.VISIBLE);
            mImgHint.setImageResource(R.mipmap.ic_no_net);
        }

    }
    private void initTitle() {
        mNameTv.setText(R.string.my_collect);
    }

    @OnClick({R.id.back_iv, R.id.btn_reload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;

            case R.id.btn_reload:
                mProgressBar.setVisibility(View.VISIBLE);
                requestCollectProductTask();
                break;
        }
    }

    //是否售罄排序
    public class CollectStoreSortUtil implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            MyCollectProductBean.DataBean.MyCollectionsBean.ListBean collectBean = (MyCollectProductBean.DataBean.MyCollectionsBean.ListBean) o1;
            MyCollectProductBean.DataBean.MyCollectionsBean.ListBean collectBean1 = (MyCollectProductBean.DataBean.MyCollectionsBean.ListBean) o2;
            int flag = String.valueOf(collectBean.getStore()).compareTo(String.valueOf(collectBean1.getStore()));
            return -flag;
        }
    }
    //是否下架排序
    public class CollectMarkSortUtil implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            MyCollectProductBean.DataBean.MyCollectionsBean.ListBean collectBean = (MyCollectProductBean.DataBean.MyCollectionsBean.ListBean) o1;
            MyCollectProductBean.DataBean.MyCollectionsBean.ListBean collectBean1 = (MyCollectProductBean.DataBean.MyCollectionsBean.ListBean) o2;
            int flag = String.valueOf(collectBean.getProductMark()).compareTo(String.valueOf(collectBean1.getProductMark()));
            return -flag;
        }
    }
    
    
}
