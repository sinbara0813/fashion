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
import com.d2cmall.buyer.adapter.ProductConsultAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.ConsultListBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/5 19:55
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductConsultFragment extends BaseFragment {

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

    private long id;
    private VirtualLayoutManager virtualLayoutManager;
    private DelegateAdapter delegateAdapter;
    private List<ConsultListBean.DataEntity.ConsultsEntity.ListEntity> list;
    private ProductConsultAdapter consultAdapter;

    private int lastPosition = -1;
    private int lastOffer;
    private boolean hasSetAdapter;
    private boolean isLoad;
    private boolean hasNext;
    private int p = 1;

    public static ProductConsultFragment newInstance(long id) {
        ProductConsultFragment consultFragment = new ProductConsultFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        consultFragment.setArguments(bundle);
        return consultFragment;
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_refresh_recycleview, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong("id");
        }
        list = new ArrayList<>();
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
        recycleView.setLayoutManager(virtualLayoutManager);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);
        recycleView.setAdapter(delegateAdapter);
        consultAdapter = new ProductConsultAdapter(getActivity(), list);
        delegateAdapter.addAdapter(consultAdapter);
        if (lastPosition >= 0) {
            virtualLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
        }
        initListener();
        hasSetAdapter = true;
    }

    private void initListener() {
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                getLastLocation();
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoad && hasNext&&virtualLayoutManager.findLastVisibleItemPosition() > 0 && virtualLayoutManager.findLastVisibleItemPosition() > virtualLayoutManager.getItemCount() - 3) {
                    p++;
                    loadData();
                }
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

    private void loadData() {
        isLoad = true;
        SimpleApi api = new SimpleApi();
        api.setP(p);
        api.setPageSize(10);
        api.setInterPath(String.format(Constants.GET_PRODUCT_CONSULT_LIST, id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ConsultListBean>() {
            @Override
            public void onResponse(ConsultListBean response) {
                if(getActivity().isFinishing()) {
                    return;
                }
                if (!isVisibleToUser){
                    return;
                }
                ptr.refreshComplete();
                isLoad = false;
                hasNext=response.getData().getConsults().isNext();
                if (response.getData().getConsults().getList().size() > 0) {
                    if (p == 1) {
                        list.clear();
                    }
                    list.addAll(response.getData().getConsults().getList());
                    imgHint.setVisibility(View.GONE);
                    recycleView.setVisibility(View.VISIBLE);
                    btnReload.setVisibility(View.GONE);
                    consultAdapter.notifyDataSetChanged();
                }else {
                    imgHint.setVisibility(View.GONE);
                    imgHint.setImageResource(R.mipmap.ic_empty_sun);
                    recycleView.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ptr.refreshComplete();
                Util.showToast(getActivity(),Util.checkErrorType(error));
                imgHint.setVisibility(View.VISIBLE);
                imgHint.setImageResource(R.mipmap.ic_no_net);
                btnReload.setVisibility(View.VISIBLE);
                recycleView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void doBusiness() {
        if (list.size() == 0) {
            loadData();
        } else {
            if (!hasSetAdapter) {
                delegateAdapter.addAdapter(consultAdapter);
                if (lastPosition >= 0) {
                    virtualLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
                }
                hasSetAdapter = true;
            }
        }
    }

    @Override
    public void releaseOnInVisible() {
        delegateAdapter.removeAdapter(consultAdapter);
        hasSetAdapter = false;
    }

    private void refresh() {
        p = 1;
        loadData();
    }

    @OnClick(R.id.btn_reload)
    public void onViewClicked() {
        refresh();
    }
}
