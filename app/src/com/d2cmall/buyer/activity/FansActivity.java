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
import com.d2cmall.buyer.adapter.FansAdapter;
import com.d2cmall.buyer.api.FansApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.MineFansBean;
import com.d2cmall.buyer.binder.ScrollEndBinder;
import com.d2cmall.buyer.holder.DefaultHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 粉丝
 * Author: LWJ
 * Date: 17/9/6 16:55
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class FansActivity extends BaseActivity {

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
    @Bind(R.id.dividing)
    View mDividing;
    @Bind(R.id.img_hint)
    ImageView mImgHint;
    @Bind(R.id.btn_reload)
    TextView mBtnReload;
    @Bind(R.id.empty_hint_layout)
    LinearLayout mEmptyHintLayout;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout mPtr;
    private int currentPage = 1;
    private List<MineFansBean.DataBean.MyFansBean.ListBean> mFansList = new ArrayList<>();
    private DelegateAdapter mDelegateAdapter;
    private FansAdapter mFansAdapter;
    private boolean hasNext=true;
    private Long memberId;
    private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志
    private VirtualLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fans1);
        ButterKnife.bind(this);
        initTitle();
        doBusiness();
        mPtr.setEnabled(false);
    }

    private void doBusiness() {
        //
        memberId = getIntent().getLongExtra("memberId", -1);
        layoutManager = new VirtualLayoutManager(FansActivity.this);
        mFansAdapter = new FansAdapter(FansActivity.this, mFansList);
        mRecyclerView.setLayoutManager(layoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 2);
        mDelegateAdapter = new DelegateAdapter(layoutManager, true);
        mRecyclerView.setAdapter(mDelegateAdapter);
        mDelegateAdapter.addAdapter(mFansAdapter);
        addListener();
        requestFansTask();
    }

    private void requestFansTask() {

        FansApi fansApi = new FansApi();
        fansApi.setP(currentPage);
        fansApi.setPageSize(20);
        if (memberId != -1) {
            fansApi.setMemberId(memberId);
        }
        D2CApplication.httpClient.loadingRequest(fansApi, new BeanRequest.SuccessListener<MineFansBean>() {
            @Override
            public void onResponse(MineFansBean mineFansBean) {
                mPtr.refreshComplete();
                mBtnReload.setVisibility(View.GONE);
                mImgHint.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);
                if (currentPage == 1) {
                    mFansList.clear();
                }
                int size = mineFansBean.getData().getMyFans().getList().size();
                if (size > 0) {
                    mFansList.addAll(mineFansBean.getData().getMyFans().getList());
                }else{
                    setEmptyView(Constants.NO_DATA);
                    return;
                }
                if (mFansAdapter != null) {
                    mFansAdapter.notifyDataSetChanged();
                    hasNext = mineFansBean.getData().getMyFans().isNext();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mPtr.refreshComplete();
                mProgressBar.setVisibility(View.GONE);
                setEmptyView(Constants.NET_DISCONNECT);
                Util.showToast(FansActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void initTitle() {
        mNameTv.setText(R.string.label_fans);

    }

    @OnClick({R.id.back_iv, R.id.btn_reload})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.btn_reload:
                mProgressBar.setVisibility(View.VISIBLE);
                requestFansTask();
                break;
        }

    }

    private void addListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = layoutManager.findLastVisibleItemPosition();
                        if (last > mFansAdapter.getItemCount() - 3 && hasNext) {
                            currentPage++;
                            requestFansTask();
                        }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisPosition=layoutManager.findLastVisibleItemPosition();
                int itemCount=layoutManager.getItemCount();
                if (lastVisPosition>=itemCount-3 && !hasNext && currentPage>1){
                    if (endAdapter==null){
                        ScrollEndBinder endBinder = new ScrollEndBinder();
                        endAdapter = new BaseVirtualAdapter<>(endBinder,100);
                        mDelegateAdapter.addAdapter(endAdapter);
                    }
                }else {
                    if (endAdapter!=null){
                        mDelegateAdapter.removeAdapter(endAdapter);
                        endAdapter=null;
                    }
                }
            }
        });
    }
    private void setEmptyView(int type) {
        if (type == Constants.NO_DATA) {
            mImgHint.setVisibility(View.VISIBLE);
            mImgHint.setImageResource(R.mipmap.ic_empty_fans);
        } else {
            mBtnReload.setVisibility(View.VISIBLE);
            mImgHint.setVisibility(View.VISIBLE);
            mImgHint.setImageResource(R.mipmap.ic_no_net);
        }

    }

}
