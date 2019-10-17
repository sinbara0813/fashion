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
import com.d2cmall.buyer.adapter.NormalCustomerCenterAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.NormalCustomerBean;
import com.d2cmall.buyer.bean.NormalCustomerLikeBean;
import com.d2cmall.buyer.holder.DefaultHolder;
import com.d2cmall.buyer.http.BeanRequest;
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

//普通客户(非买手)的个人中心界面
public class NormalCustomerCenterActivity extends BaseActivity {

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
    private VirtualLayoutManager mLayoutManager;
    private DelegateAdapter delegateAdapter;
    private BaseVirtualAdapter<DefaultHolder> endAdapter;
    private NormalCustomerCenterAdapter normalCustomerCenterAdapter;
    private long memberId;
    List<NormalCustomerLikeBean.ListBean> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_recyclerview);
        ButterKnife.bind(this);
        memberId = getIntent().getLongExtra("memberId", 0);
        if(memberId==0){
            setEmptyView(Constants.NO_DATA);
        }
        nameTv.setText("客户中心");
        doBusiness();

    }



    private void doBusiness() {
        mLayoutManager = new VirtualLayoutManager(this);
        productList=new ArrayList<>();
        normalCustomerCenterAdapter = new NormalCustomerCenterAdapter(this,productList);
        recycleView.setLayoutManager(mLayoutManager);
        delegateAdapter = new DelegateAdapter(mLayoutManager, true);
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(0,2);
        recycledViewPool.setMaxRecycledViews(1,2);
        recycleView.setRecycledViewPool(recycledViewPool);
        recycleView.setAdapter(delegateAdapter);
        delegateAdapter.addAdapter(normalCustomerCenterAdapter);
        progressBar.setVisibility(View.VISIBLE);
        initLinstener();
        loadHeadInfo();
        loadLikeList();
    }

    private void initLinstener() {
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadHeadInfo();
                loadLikeList();
            }
        });

    }

    private void loadLikeList() {
        SimpleApi api = new SimpleApi();
        if(memberId==0){
            return;
        }
        api.setInterPath(String.format(Constants.NORMAL_CUSTOMER_LIKE_LIST,memberId));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<NormalCustomerLikeBean>() {
                    @Override
                    public void onResponse(NormalCustomerLikeBean normalCustomerLikeBean) {
                        if(isFinishing() || progressBar==null){
                            return;
                        }
                        progressBar.setVisibility(View.GONE);
                        ptr.refreshComplete();
                        productList.clear();
                        productList.addAll(normalCustomerLikeBean.getList());
                        normalCustomerCenterAdapter.notifyDataSetChanged();
                        imgHint.setVisibility(View.GONE);
                        btnReload.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        ptr.refreshComplete();
                        setEmptyView(Constants.NET_DISCONNECT);
                        Util.showToast(NormalCustomerCenterActivity.this,Util.checkErrorType(error));
                    }
                }

        );
    }

    private void loadHeadInfo() {
        SimpleApi api = new SimpleApi();
        if(memberId==0){
            return;
        }
        api.setInterPath(String.format(Constants.NORMAL_CUSTOMER_INFO,memberId));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<NormalCustomerBean>() {
                    @Override
                    public void onResponse(NormalCustomerBean normalCustomerBean) {
                        if(isFinishing() || progressBar==null){
                            return;
                        }
                        progressBar.setVisibility(View.GONE);
                        ptr.refreshComplete();
                        if(normalCustomerCenterAdapter!=null){
                            normalCustomerCenterAdapter.setHeadBean(normalCustomerBean);
                        }
                        imgHint.setVisibility(View.GONE);
                        btnReload.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        ptr.refreshComplete();
                        setEmptyView(Constants.NET_DISCONNECT);
                        Util.showToast(NormalCustomerCenterActivity.this,Util.checkErrorType(error));
                    }
                }

        );

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
    @OnClick({R.id.back_iv, R.id.btn_reload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.btn_reload:
                imgHint.setVisibility(View.GONE);
                btnReload.setVisibility(View.GONE);
                imgHint.setImageResource(R.mipmap.ic_no_net);
                recycleView.setVisibility(View.VISIBLE);
                loadHeadInfo();
                loadLikeList();
                break;
        }
    }
}
