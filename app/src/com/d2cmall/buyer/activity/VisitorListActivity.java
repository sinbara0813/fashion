package com.d2cmall.buyer.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.d2cmall.buyer.adapter.VisitorListAdapter;
import com.d2cmall.buyer.api.BuyerVisitorApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.BuyerVisitorBean;
import com.d2cmall.buyer.bean.PartnerVisitorBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.TitleUtil;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Dec: D2CNEW
 * Author: hrb
 * Date: 2018/4/18 10:40
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class VisitorListActivity extends BaseActivity {

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

    private VirtualLayoutManager layoutManager;
    private DelegateAdapter delegateAdapter;

    private int p=1;
    private int type; //1是访客记录2是零售客户
    private VisitorListAdapter listAdapter;
    private ArrayList<PartnerVisitorBean.ListBean> visitorList=new ArrayList<>();
    private ArrayList<BuyerVisitorBean.DataBean.CustomersBean.ListBean> list=new ArrayList<>();
    private boolean hasNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_recyclerview);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        type=getIntent().getIntExtra("type",1);
        TitleUtil.setBack(this);
        layoutManager = new VirtualLayoutManager(this);
        recycleView.setLayoutManager(layoutManager);
        delegateAdapter = new DelegateAdapter(layoutManager, true);
        recycleView.setAdapter(delegateAdapter);
        listAdapter=new VisitorListAdapter(this);
        delegateAdapter.addAdapter(listAdapter);
        if (type==1){
            TitleUtil.setTitle(this,"访问记录");
            listAdapter.setData(visitorList,type);
        }else {
            TitleUtil.setTitle(this,"零售客户");
            listAdapter.setData(list,type);
        }
        recycleView.setAdapter(listAdapter);
        if (type==1){
            loadVisitorData();//拉访客记录
        }else {
            loadCustomerData();//拉零售客户
        }
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
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisPosition=layoutManager.findLastVisibleItemPosition();
                int itemCount=layoutManager.getItemCount();
                if (lastVisPosition>=itemCount-3&&hasNext){
                    p++;
                    if (type==1){
                        loadVisitorData();
                    }else {
                        loadCustomerData();
                    }
                }
            }
        });
    }

    private void refresh() {
        p=1;
        if (type==1){
            loadVisitorData();
        }else {
            loadCustomerData();
        }
    }

    private void loadVisitorData() {
        BuyerVisitorApi api=new BuyerVisitorApi();
        api.p=p;
        api.pageSize=20;
        api.isJsonContentType=true;
        api.setInterPath(Constants.GET_PARTNER_VISITOR_URL);
        if (Session.getInstance().getUserFromFile(this)!=null){
            api.fieldValue= Session.getInstance().getUserFromFile(this).getPartnerId();
        }
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<PartnerVisitorBean>() {
            @Override
            public void onResponse(PartnerVisitorBean response) {
                if (ptr != null)
                    ptr.refreshComplete();
                if (response != null && response.getList() != null && response.getList().size() > 0) {
                    if (p == 1) {
                        visitorList.clear();
                    }
                    hasNext = response.isNext();
                    visitorList.addAll(response.getList());
                    listAdapter.notifyDataSetChanged();
                } else {
                    imgHint.setVisibility(View.VISIBLE);
                    imgHint.setImageResource(R.mipmap.none);
                    btnReload.setVisibility(View.VISIBLE);
                    btnReload.setBackgroundColor(Color.WHITE);
                    btnReload.setText("没有记录");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (ptr!=null)
                    ptr.refreshComplete();
            }
        });
    }

    private void loadCustomerData(){
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.BUYER_VISITOR_DATA);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BuyerVisitorBean>() {
            @Override
            public void onResponse(BuyerVisitorBean response) {
                if (ptr != null)
                    ptr.refreshComplete();
                if (response != null && response.getData().getCustomers() != null && response.getData().getCustomers().getList().size() > 0) {
                    listAdapter.setTotal(response.getData().getCustomers().getTotal());
                    if (p == 1) {
                        list.clear();
                    }
                    hasNext = response.getData().getCustomers().isNext();
                    list.addAll(response.getData().getCustomers().getList());
                    listAdapter.notifyDataSetChanged();
                } else {
                    imgHint.setVisibility(View.VISIBLE);
                    imgHint.setImageResource(R.mipmap.none);
                    btnReload.setVisibility(View.VISIBLE);
                    btnReload.setBackgroundColor(Color.WHITE);
                    btnReload.setText("没有数据");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (ptr!=null)
                    ptr.refreshComplete();
            }
        });
    }
}
