package com.d2cmall.buyer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.FashionFocusAdapter;
import com.d2cmall.buyer.api.HotApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.UnFollowBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/16 18:37
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FashionUnFocusChildFragment extends BaseFragment {

    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout ptr;

    private VirtualLayoutManager layoutManager;
    private DelegateAdapter delegateAdapter;

    private List<UnFollowBean.DataBean.ActiveMemberBean.ListBean> unFollowList;
    private FashionFocusAdapter adapter;

    private int lastPosition = -1;
    private int lastOffer;

    private boolean hasSetAdapter;
    private boolean hasNext;
    private int p = 1;
    private String type;

    public static FashionUnFocusChildFragment newInstance(String type) {
        FashionUnFocusChildFragment fragment = new FashionUnFocusChildFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_refresh_recycleview, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString("type");
        }
        unFollowList = new ArrayList<>();
    }

    @Override
    public void prepareView() {
        ptr.setHeadLabel(getString(R.string.label_d2c_go));
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadHot();
            }
        });
        adapter = new FashionFocusAdapter(getActivity(), unFollowList);
        layoutManager = new VirtualLayoutManager(getActivity());
        recycleView.setLayoutManager(layoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recycleView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        delegateAdapter = new DelegateAdapter(layoutManager,true);
        recycleView.setAdapter(delegateAdapter);
        delegateAdapter.addAdapter(adapter);
        if (lastPosition >= 0) {
            layoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
        }
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                getLastLocation();
                if (hasNext && layoutManager.findLastVisibleItemPosition() > 0 && layoutManager.findLastVisibleItemPosition() < layoutManager.getItemCount() - 3) {
                    //TODO
                }
            }
        });
        hasSetAdapter = true;
    }

    @Override
    public void doBusiness() {
        if (unFollowList.size() == 0) {
            loadHot();
        } else {
            if (!hasSetAdapter) {
                delegateAdapter.addAdapter(adapter);
                if (lastPosition >= 0) {
                    layoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
                }
                hasSetAdapter = true;
            }
        }
    }

    private void loadHot() {
        HotApi api = new HotApi();
        api.type = type;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<UnFollowBean>() {
            @Override
            public void onResponse(UnFollowBean response) {
                ptr.refreshComplete();
                if (p == 1) {
                    unFollowList.clear();
                }
                if (response.getData().getActiveMember().getList() != null && response.getData().getActiveMember().getList().size() > 0) {
                    unFollowList.addAll(response.getData().getActiveMember().getList());
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ptr.refreshComplete();
            }
        });
    }

    private void getLastLocation() {
        View topView = layoutManager.getChildAt(0);
        if (topView != null) {
            //获取与该view的顶部的偏移量
            lastOffer = topView.getTop();
            //得到该View的数组位置
            lastPosition = layoutManager.getPosition(topView);
        }
    }

    @Override
    public void releaseOnInVisible() {
        if (delegateAdapter != null) {
            delegateAdapter.removeAdapter(adapter);
            hasSetAdapter = false;
        }
    }
}
