package com.d2cmall.buyer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.PartnerCashAdapter;
import com.d2cmall.buyer.api.PartnerCashListApi;
import com.d2cmall.buyer.api.PartnerSaleListApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.PartnerCashListBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.binder.ScrollEndBinder;
import com.d2cmall.buyer.holder.DefaultHolder;
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
 * Date: 2017/7/21 13:39
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class PartnerCashFragment extends BaseFragment {

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

    private VirtualLayoutManager virtualLayoutManager;
    private DelegateAdapter delegateAdapter;

    List<PartnerCashListBean.DataBean.PartnerCashBean.ListBean> cashList;
    private PartnerCashAdapter partnerCashAdapter;

    private int lastPosition = -1;
    private int lastOffer;
    private long id; //Fragment标示
    private int p = 1;
    private boolean hasNext; //是否有下一页数据
    private boolean isFresh;
    private boolean hasSetAdapter; //是否设置过设配器
    private UserBean.DataEntity.MemberEntity mUser;
    private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志

    public static PartnerCashFragment newInstance(long id) {
        PartnerCashFragment productReportFragment = new PartnerCashFragment();
        Bundle args = new Bundle();
        args.putLong("id", id);
        productReportFragment.setArguments(args);
        return productReportFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong("id");
        }
        cashList = new ArrayList<>(); //需要缓存的数据在此处实例
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_refresh_recycleview, container, false);
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
                refresh();
            }
        });
        virtualLayoutManager = new VirtualLayoutManager(getActivity());
        delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(0, 2);
        recycleView.setRecycledViewPool(recycledViewPool);
        partnerCashAdapter = new PartnerCashAdapter(getActivity(), cashList, id);
        delegateAdapter.addAdapter(partnerCashAdapter);
        recycleView.setLayoutManager(virtualLayoutManager);
        recycleView.setAdapter(delegateAdapter);
        if (lastPosition >= 0) {
            virtualLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
        }
        initListener();
        hasSetAdapter = true;
        if (cashList != null && cashList.size() > 0) {
            if (isAdded()) {
                recycleView.setBackgroundColor(getResources().getColor(R.color.bg_color));
            }
        }
    }

    private void initListener() {
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisPosition = virtualLayoutManager.findLastVisibleItemPosition();
                int itemCount = virtualLayoutManager.getItemCount();
                if (lastVisPosition > delegateAdapter.getItemCount() - 3 && hasNext) {
                    p++;
                    loadCashList();
                }
                if (lastVisPosition >= itemCount - 3 && !hasNext && p > 1) {
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

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                getLastLocation();
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgHint.setVisibility(View.GONE);
                btnReload.setVisibility(View.GONE);
                loadCashList();
            }
        });
    }

    private void getLastLocation() {
        View topView = virtualLayoutManager.getChildAt(0);
        if (topView != null) {
            //获取与该view的顶部的偏移量
            lastOffer = topView.getTop();
            //得到该View的数组位置
            lastPosition = virtualLayoutManager.getPosition(topView);
        }
    }

    @Override
    public void doBusiness() {
        if (cashList.size() == 0) {
            loadCashList();
        } else {
            if (!hasSetAdapter) {
                delegateAdapter.addAdapter(partnerCashAdapter);
                partnerCashAdapter.notifyDataSetChanged();
                if (lastPosition >= 0) {
                    virtualLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
                }
                hasSetAdapter = true;
            }
        }
    }


    private void loadCashList() {
        progressBar.setVisibility(View.VISIBLE);
        SimpleApi api = null;
        PartnerCashListApi partnerCashListApi = null;
        if (id == 0) {          //这个接口Status=0时拉不到数据所以用了两个api
            api = new SimpleApi();
            api.setInterPath(Constants.GET_PARTNER_CASH_URL);
            api.setP(p);
            api.setPageSize(20);
        } else {
            partnerCashListApi = new PartnerCashListApi();
            partnerCashListApi.setStatus((int) id);
            partnerCashListApi.setPageNumber(p);
            partnerCashListApi.setPageSize(20);
        }
        D2CApplication.httpClient.loadingRequest(api == null ? partnerCashListApi : api, new BeanRequest.SuccessListener<PartnerCashListBean>() {
            @Override
            public void onResponse(PartnerCashListBean partnerCashListBean) {
                if (progressBar == null) {
                    return;
                }
                if (isAdded()) {
                    recycleView.setBackgroundColor(getResources().getColor(R.color.bg_color));
                }
                progressBar.setVisibility(View.GONE);
                if (isFresh) {
                    ptr.refreshComplete();
                }
                if (p == 1) {
                    cashList.clear();
                }
                if (partnerCashListBean.getData().getPartnerCash() != null) {
                    hasNext = partnerCashListBean.getData().getPartnerCash().isNext();
                }

                if (partnerCashListBean.getData().getPartnerCash() != null && partnerCashListBean.getData().getPartnerCash().getList().size() > 0) {

                    if (isAdded()) {
                        recycleView.setBackgroundColor(getResources().getColor(R.color.bg_color));
                    }
                    imgHint.setVisibility(View.GONE);
                    imgHint.setImageResource(R.mipmap.ic_no_net);
                    recycleView.setVisibility(View.VISIBLE);
                    btnReload.setVisibility(View.GONE);
                    cashList.addAll(partnerCashListBean.getData().getPartnerCash().getList());
                } else if (p == 1) {
                    setEmptyView(Constants.NO_DATA);
                }
                partnerCashAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressBar == null) {
                    return;
                }
                if (isAdded()) {
                    recycleView.setBackgroundColor(getResources().getColor(R.color.color_white));
                }
                progressBar.setVisibility(View.GONE);
                if (isFresh) {
                    ptr.refreshComplete();
                }
                setEmptyView(Constants.NET_DISCONNECT);
            }
        });
    }

    private void setEmptyView(int type) {
        if (isAdded()) {
            recycleView.setBackgroundColor(getResources().getColor(R.color.color_white));
        }
        if (type == Constants.NO_DATA) {
            imgHint.setVisibility(View.VISIBLE);
            imgHint.setImageResource(R.mipmap.icon_empty_cash);
        } else {
            btnReload.setVisibility(View.VISIBLE);
            imgHint.setVisibility(View.VISIBLE);
            imgHint.setImageResource(R.mipmap.ic_no_net);
        }
    }

    public void refresh() {
        p = 1;
        loadCashList();
        isFresh = true;
    }

    @Override
    public void releaseOnInVisible() {
        if (delegateAdapter != null) {
            delegateAdapter.removeAdapter(partnerCashAdapter);
            hasSetAdapter = false;
        }
    }


    @Override
    public void onDestroyView() {
        partnerCashAdapter = null;
        delegateAdapter = null;
        super.onDestroyView();
    }

}
