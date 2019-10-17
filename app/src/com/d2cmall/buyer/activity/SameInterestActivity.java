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
import com.d2cmall.buyer.adapter.SameInterestAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.MainSpecailBean2;
import com.d2cmall.buyer.binder.ScrollEndBinder;
import com.d2cmall.buyer.holder.DefaultHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;
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
 * 同兴趣的人购买
 * Author: LWJ
 * Date: 17/9/6 16:55
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class SameInterestActivity extends BaseActivity {

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
    @Bind(R.id.rl_collect)
    RelativeLayout mRlCollect;

    private SameInterestAdapter mSameInterestAdapter;
    private int currentPage = 1;
    private boolean hasNext;
    private VirtualLayoutManager mLayoutManager;
    private List<MainSpecailBean2.DataBean.RecentlySalesProductBean> mRecentlySalesProductBeanList = new ArrayList<>();
    private DelegateAdapter delegateAdapter;
    private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        initTitle();
        doBusiness();
        initListener();
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void doBusiness() {
        mLayoutManager = new VirtualLayoutManager(SameInterestActivity.this);
        int itemWidth = (ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(48)) / 2;
        mSameInterestAdapter = new SameInterestAdapter(this, mRecentlySalesProductBeanList, itemWidth);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 2);
        delegateAdapter = new DelegateAdapter(mLayoutManager, true);
        mRecyclerView.setAdapter(delegateAdapter);
        delegateAdapter.addAdapter(mSameInterestAdapter);
        requestCollectProductTask();
    }

    private void requestCollectProductTask() {
        SimpleApi api = new SimpleApi();
        api.setInterPath("/v3/api/page/recently/sales");
        api.setPageSize(50);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MainSpecailBean2>() {
            @Override
            public void onResponse(MainSpecailBean2 mainSpecailBean2) {
                mPtr.refreshComplete();
                mBtnReload.setVisibility(View.GONE);
                mImgHint.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);
                List<MainSpecailBean2.DataBean.RecentlySalesProductBean> recentlySalesProductBeanList = mainSpecailBean2.getData().getRecentlySalesProduct();
                if (recentlySalesProductBeanList != null && recentlySalesProductBeanList.size() > 0) {
                    mRecentlySalesProductBeanList.clear();
                    mRecentlySalesProductBeanList.addAll(recentlySalesProductBeanList);
                    mSameInterestAdapter.notifyDataSetChanged();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressBar.setVisibility(View.GONE);
                Util.showToast(SameInterestActivity.this, Util.checkErrorType(error));
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
                requestCollectProductTask();
                currentPage = 1;
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = mLayoutManager.findLastVisibleItemPosition();
                        if (last > mSameInterestAdapter.getItemCount() - 3 && hasNext) {
                            currentPage++;
                            requestCollectProductTask();
                        }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisPosition=mLayoutManager.findLastVisibleItemPosition();
                int itemCount=mLayoutManager.getItemCount();
                if (lastVisPosition>=itemCount-3 ){
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

    private void initTitle() {
        mNameTv.setText(R.string.label_same_interest_buy);
        mRlCollect.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
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

    private void setEmptyView(int type) {
        if (type == Constants.NO_DATA) {
            mImgHint.setVisibility(View.VISIBLE);
            mImgHint.setImageResource(R.mipmap.ic_empty_collect);
        } else {
            mBtnReload.setVisibility(View.VISIBLE);
            mImgHint.setVisibility(View.VISIBLE);
            mImgHint.setImageResource(R.mipmap.ic_no_net);
        }

    }
}
