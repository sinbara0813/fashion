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
import com.d2cmall.buyer.activity.AllBuyerSaleActivity;
import com.d2cmall.buyer.adapter.PartnerBillAdapter;
import com.d2cmall.buyer.api.PartnerSaleListApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.PartnerMemberBean;
import com.d2cmall.buyer.bean.PartnerSaleListBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Session;
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
 * Date: 2017/7/21 13:39
 * Copyright (c) 2016 d2cmall. All rights reserved.
 * 售货明细
 */
public class BuyerSaleFragment extends BaseFragment implements SaleDetailFragment.ChangeOrderTypeListener {

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
    //零售 partnerId，买手parentId，DM superId，AM masterid
    //level=2，只有零售订单
    //level=1 ，零售，买手，DM
    //level=0，零售，团队（AM)
    private long partnerId;
    private long parentId;
    private long masterid;
    private long superId;
    List<PartnerSaleListBean.DataBean.PartnerBillBean.ListBean> billList;
    private PartnerBillAdapter partnerBillAdapter;

    private int lastPosition = -1;
    private int lastOffer;

    private long type; //订单状态
    private boolean fromActivity; //Activity跳转过来还是Fragment
    private int p = 1;
    private boolean hasNext; //是否有下一页数据
    private boolean isFresh;

    private boolean hasSetAdapter; //是否设置过设配器
    private PartnerMemberBean.DataBean.PartnerBean partnerBean;
    private int[] orderTempType =new int[1];      //记录当前选择的订单类型
    //private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志

    public static BuyerSaleFragment newInstance(long type,boolean fromActivity) {
        BuyerSaleFragment buyerSaleFragment = new BuyerSaleFragment();
        Bundle args = new Bundle();
        args.putLong("type", type);
        args.putBoolean("fromActivity", fromActivity);
        buyerSaleFragment.setArguments(args);
        return buyerSaleFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getLong("type");
            fromActivity = getArguments().getBoolean("fromActivity",false);
        }
        billList = new ArrayList<>(); //需要缓存的数据在此处实例
        partnerBean = Session.getInstance().getPartnerFromFile(mContext);
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
        recycledViewPool.setMaxRecycledViews(0,2);
        recycleView.setRecycledViewPool(recycledViewPool);
        partnerBillAdapter = new PartnerBillAdapter(getActivity(), billList,false);
        delegateAdapter.addAdapter(partnerBillAdapter);
        recycleView.setLayoutManager(virtualLayoutManager);
        recycleView.setAdapter(delegateAdapter);
        if (lastPosition >= 0) {
            virtualLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
        }
        initListener();
        hasSetAdapter = true;
        if(billList !=null &&billList.size()>0){
            if(isAdded()){
                recycleView.setBackgroundColor(getResources().getColor(R.color.bg_color));
            }
        }
    }

    private void initListener(){
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisPosition=virtualLayoutManager.findLastVisibleItemPosition();
                int itemCount=virtualLayoutManager.getItemCount();
                if (lastVisPosition > delegateAdapter.getItemCount() - 3 && hasNext) {
                    p++;
                    if(fromActivity){
                        loadBillList(((AllBuyerSaleActivity)getActivity()).tempType);
                    }else{
                        loadBillList(((SaleDetailFragment)getParentFragment()).tempType);
                    }
                }
                if (lastVisPosition>=itemCount-3 && !hasNext && p>1){
                    /*if (endAdapter==null){
                        ScrollEndBinder endBinder = new ScrollEndBinder();
                        endAdapter = new BaseVirtualAdapter<>(endBinder,100);
                        delegateAdapter.addAdapter(endAdapter);
                    }*/
                }else {


                }
            }

               @Override
               public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                   getLastLocation();
                 super.onScrollStateChanged(recyclerView, newState);
                 }
               }
        );
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgHint.setVisibility(View.GONE);
                btnReload.setVisibility(View.GONE);
                loadBillList(((SaleDetailFragment)getParentFragment()).tempType);
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
        if (billList.size() == 0) {
            if(fromActivity){
                loadBillList(((AllBuyerSaleActivity)getActivity()).tempType);
            }else{
                loadBillList(((SaleDetailFragment)getParentFragment()).tempType);
            }
            if(partnerBillAdapter!=null){
                if(fromActivity){
                    partnerBillAdapter.setOrderType(((AllBuyerSaleActivity)getActivity()).tempType);
                }else{
                    partnerBillAdapter.setOrderType(((SaleDetailFragment)getParentFragment()).tempType);
                }
            }
            if(!hasSetAdapter){
                delegateAdapter.addAdapter(partnerBillAdapter);
                partnerBillAdapter.notifyDataSetChanged();
                hasSetAdapter = true;
            }
        } else {
            if(fromActivity){
                if(((AllBuyerSaleActivity)getActivity()).tempType!= orderTempType[0]){
                    if(partnerBillAdapter!=null){
                        partnerBillAdapter.setOrderType(((AllBuyerSaleActivity)getActivity()).tempType);
                    }
                    p=1;
                    recycleView.setVisibility(View.GONE);
                    if(!hasSetAdapter){
                        delegateAdapter.addAdapter(partnerBillAdapter);
                        partnerBillAdapter.notifyDataSetChanged();
                        hasSetAdapter = true;
                    }
                    if(fromActivity){
                        loadBillList(((AllBuyerSaleActivity)getActivity()).tempType);
                    }else{
                        loadBillList(((SaleDetailFragment)getParentFragment()).tempType);
                    }
                    return;
                }
            }else{
                if(((SaleDetailFragment)getParentFragment()).tempType!= orderTempType[0]){
                    if(partnerBillAdapter!=null){
                        partnerBillAdapter.setOrderType(((SaleDetailFragment)getParentFragment()).tempType);
                    }
                    p=1;
                    recycleView.setVisibility(View.GONE);
                    if(!hasSetAdapter){
                        delegateAdapter.addAdapter(partnerBillAdapter);
                        partnerBillAdapter.notifyDataSetChanged();
                        hasSetAdapter = true;
                    }
                    if(fromActivity){
                        loadBillList(((AllBuyerSaleActivity)getActivity()).tempType);
                    }else{
                        loadBillList(((SaleDetailFragment)getParentFragment()).tempType);
                    }
                    return;
                }
            }

            if (!hasSetAdapter) {
                delegateAdapter.addAdapter(partnerBillAdapter);
                partnerBillAdapter.notifyDataSetChanged();
                if (lastPosition >= 0) {
                    virtualLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
                }
                hasSetAdapter = true;
            }
        }
    }


    private void loadBillList(final int orderType) {
        progressBar.setVisibility(View.VISIBLE);
        PartnerSaleListApi partnerSaleListApi = new PartnerSaleListApi();
        partnerSaleListApi.setIndex((int) type);
        if(partnerBean!=null && partnerBean.getLevel()==1){         //DM
            if(orderType==0){                                        //DM零售
                partnerSaleListApi.setPartnerId(String.valueOf(partnerBean.getId()));
            }else if(orderType==1){                                  //DM团队
                partnerSaleListApi.setParentId(String.valueOf(partnerBean.getId()));
            }else if(orderType==2){                                 //DM的下级DM
                partnerSaleListApi.setSuperId(String.valueOf(partnerBean.getId()));
            }
        }else if(partnerBean!=null && partnerBean.getLevel()==0){
            if(orderType==0){                                        //AM的零售
                partnerSaleListApi.setPartnerId(String.valueOf(partnerBean.getId()));
            }else if(orderType==1){                                      //AM的团队
                partnerSaleListApi.setMasterid(String.valueOf(partnerBean.getId()));
            }
        }else if(partnerBean!=null){    //买手零售
            partnerSaleListApi.setPartnerId(String.valueOf(partnerBean.getId()));
        }
        partnerSaleListApi.setP(p);
        partnerSaleListApi.setPageSize(20);
        D2CApplication.httpClient.loadingRequest(partnerSaleListApi, new BeanRequest.SuccessListener<PartnerSaleListBean>() {
            @Override
            public void onResponse(PartnerSaleListBean partnerSaleListBean) {
                if (progressBar==null){
                    return;
                }
                progressBar.setVisibility(View.GONE);
                if (isFresh){
                    ptr.refreshComplete();
                }
                if (p == 1) {
                    billList.clear();
                }
                hasNext = partnerSaleListBean.getData().getPartnerBill().isNext();
                if(partnerSaleListBean.getData().getPartnerBill()!=null&&partnerSaleListBean.getData().getPartnerBill().getList().size()>0) {
                    if(isAdded()){
                        recycleView.setBackgroundColor(getResources().getColor(R.color.bg_color));
                    }
                    if(partnerBillAdapter!=null){
                        partnerBillAdapter.setExpireDay(partnerSaleListBean.getData().getExpireDay());
                    }
                    imgHint.setVisibility(View.GONE);
                    imgHint.setImageResource(R.mipmap.ic_no_net);
                    recycleView.setVisibility(View.VISIBLE);
                    btnReload.setVisibility(View.GONE);
                    billList.addAll(partnerSaleListBean.getData().getPartnerBill().getList());
                }else if(p==1){
                    setEmptyView(Constants.NO_DATA);
                }
                partnerBillAdapter.notifyDataSetChanged();
                if(fromActivity){
                    orderTempType[0]=((AllBuyerSaleActivity)getActivity()).tempType;
                }else{
                    orderTempType[0]=((SaleDetailFragment)getParentFragment()).tempType;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressBar==null){
                    return;
                }
                progressBar.setVisibility(View.GONE);
                if (isFresh){
                    ptr.refreshComplete();
                }
                setEmptyView(Constants.NET_DISCONNECT);
            }
        });
    }
    private void setEmptyView(int type) {
        recycleView.setVisibility(View.GONE);
        if (type == Constants.NO_DATA) {
            imgHint.setVisibility(View.VISIBLE);
            imgHint.setImageResource(R.mipmap.icon_empty_default);
            btnReload.setVisibility(View.VISIBLE);
            btnReload.setText("暂无数据");
            btnReload.setBackgroundColor(getResources().getColor(R.color.transparent));
        } else {
            btnReload.setText("重新加载");
            btnReload.setBackgroundResource(R.drawable.sp_line);
            btnReload.setVisibility(View.VISIBLE);
            imgHint.setVisibility(View.VISIBLE);
            imgHint.setImageResource(R.mipmap.ic_no_net);
        }
    }


    public void refresh() {
        p = 1;
        if(fromActivity){
            loadBillList(((AllBuyerSaleActivity)getActivity()).tempType);
        }else{
            loadBillList(((SaleDetailFragment)getParentFragment()).tempType);
        }
        isFresh=true;
    }

    @Override
    public void releaseOnInVisible() {
        if (delegateAdapter != null) {
            delegateAdapter.removeAdapter(partnerBillAdapter);
            hasSetAdapter = false;
        }
    }



    @Override
    public void onDestroyView() {
        partnerBillAdapter = null;
        delegateAdapter = null;
        super.onDestroyView();
    }

    @Override
    public void orderTypeChange(int type) {
        if(!isVisibleToUser){
            return;
        }
        imgHint.setVisibility(View.GONE);
        btnReload.setVisibility(View.GONE);
        if(partnerBillAdapter!=null){
            partnerBillAdapter.setOrderType(type);
        }
        p=1;
        switch (type){
            case 0:             //零售
                loadBillList(0);
                break;
            case 1:              //团队
                loadBillList(1);
                break;
            case 2:                 //DM
                loadBillList(2);
                break;
        }
    }
}
