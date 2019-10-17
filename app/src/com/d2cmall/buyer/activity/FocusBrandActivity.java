package com.d2cmall.buyer.activity;

import android.app.Dialog;
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
import com.d2cmall.buyer.adapter.FocusBrandAdapter;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.FocusBrandBean1;
import com.d2cmall.buyer.binder.ScrollEndBinder;
import com.d2cmall.buyer.holder.DefaultHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DialogUtil;
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

//        * Author: LWJ
//        * desc:关注品牌的activity
//        * Date: 2017/09/06 11:05
//        * Copyright (c) 2016 d2cmall. All rights reserved.
public class FocusBrandActivity extends BaseActivity {

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
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout mPtr;
    @Bind(R.id.img_hint)
    ImageView mImgHint;
    @Bind(R.id.btn_reload)
    TextView mBtnReload;
    @Bind(R.id.empty_hint_layout)
    LinearLayout mEmptyHintLayout;
    private Dialog mLoadingDialog;
    private VirtualLayoutManager mLayoutManager;
    private FocusBrandAdapter mFocusBrandAdapter;
    private int currentPage = 1;
    private boolean hasNext = true;
    private List<FocusBrandBean1.DataBean.MyAttentionsBean.ListBean> focusBranList = new ArrayList<>();
    private DelegateAdapter delegateAdapter;
    private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_brand1);
        ButterKnife.bind(this);
        doBusiness();
        mLoadingDialog = DialogUtil.createLoadingDialog(this);
        mProgressBar.setVisibility(View.VISIBLE);
        requestFocusBrandInfoTask();
        initListener();
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
                requestFocusBrandInfoTask();
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = mLayoutManager.findLastVisibleItemPosition();
                        if (last > mFocusBrandAdapter.getItemCount() - 3 && hasNext) {
                            currentPage++;
                            requestFocusBrandInfoTask();
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

    private void doBusiness() {
        mNameTv.setText(R.string.label_focus_brand);
        mProgressBar.setVisibility(View.GONE);
        mFocusBrandAdapter = new FocusBrandAdapter(FocusBrandActivity.this, focusBranList);
        mLayoutManager = new VirtualLayoutManager(FocusBrandActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 2);
        delegateAdapter = new DelegateAdapter(mLayoutManager, true);
        mRecyclerView.setAdapter(delegateAdapter);
        delegateAdapter.addAdapter(mFocusBrandAdapter);
    }


    private void requestFocusBrandInfoTask() {

        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.MY_FOCUS_BRAND_URL);
        api.setMethod(BaseApi.Method.POST);
        api.setP(currentPage);
        api.setPageSize(20);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<FocusBrandBean1>() {
            @Override
            public void onResponse(FocusBrandBean1 focusBrandBean) {
                mPtr.refreshComplete();
                mBtnReload.setVisibility(View.GONE);
                mImgHint.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);
                if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
                }
                if (currentPage == 1) {
                    focusBranList.clear();
                }
                int size = focusBrandBean.getData().getMyAttentions().getList().size();
                if (size > 0) {
                    focusBranList.addAll(focusBrandBean.getData().getMyAttentions().getList());
                } else {
                    setEmptyView(Constants.NO_DATA);
                }
                if (mFocusBrandAdapter != null) {
                    mFocusBrandAdapter.notifyDataSetChanged();
                    hasNext = focusBrandBean.getData().getMyAttentions().isNext();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mPtr.refreshComplete();
                mProgressBar.setVisibility(View.GONE);
                Util.showToast(FocusBrandActivity.this, Util.checkErrorType(error));
                setEmptyView(Constants.NET_DISCONNECT);
            }
        });
    }

    private void setEmptyView(int type) {
        if (type == Constants.NO_DATA) {
            mImgHint.setVisibility(View.VISIBLE);
            mImgHint.setImageResource(R.mipmap.ic_empty_follow);
        } else {
            mBtnReload.setVisibility(View.VISIBLE);
            mImgHint.setVisibility(View.VISIBLE);
            mImgHint.setImageResource(R.mipmap.ic_no_net);
        }

    }

    @OnClick({R.id.back_iv, R.id.btn_reload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.btn_reload:
                currentPage=1;
                mProgressBar.setVisibility(View.VISIBLE);
                requestFocusBrandInfoTask();
                break;
        }
    }
}
