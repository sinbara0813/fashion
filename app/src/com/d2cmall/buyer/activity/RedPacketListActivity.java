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
import com.d2cmall.buyer.adapter.RedPacketAdapter;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.RedPacketBean;
import com.d2cmall.buyer.bean.RedPacketListBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by rookie on 2017/10/25.
 * 红包明细页面
 */

public class RedPacketListActivity extends BaseActivity {

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
    private ArrayList<RedPacketBean.DataBean.RedPacketsItemsBean.ListBean> listEntities;
    private RedPacketAdapter redPacketAdapter;
    private DelegateAdapter delegateAdapter;
    private VirtualLayoutManager virtualLayout;
    private boolean isLoad;
    private boolean isEnd;
    private int currentPage = 1;
    private int pageSize = 10;
    private boolean hasNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_recyclerview);
        ButterKnife.bind(this);
        nameTv.setText("收支明细");
        listEntities = new ArrayList<>();
        initRecyclerView();
    }

    private void initRecyclerView() {
        redPacketAdapter=new RedPacketAdapter(this,listEntities);
        virtualLayout=new VirtualLayoutManager(this);
        delegateAdapter=new DelegateAdapter(virtualLayout,true);
        recycleView.setLayoutManager(virtualLayout);
        delegateAdapter.addAdapter(redPacketAdapter);
        recycleView.setAdapter(delegateAdapter);
        if (listEntities.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            requestDepositDetailTask();
        }
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (!isLoad) {
                    currentPage = 1;
                    requestDepositDetailTask();
                }
            }
        });
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = virtualLayout.findLastVisibleItemPosition();
                        if (last > redPacketAdapter.getItemCount() - 3 && hasNext) {
                            currentPage++;
                            requestDepositDetailTask();
                        }
                }
            }
        });
    }

    private void requestDepositDetailTask() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.MY_RED_PACKET_LIST);
        api.setMethod(BaseApi.Method.GET);
        api.setP(currentPage);
        isLoad = true;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<RedPacketListBean>() {
            @Override
            public void onResponse(RedPacketListBean myPacketBean) {
                ptr.refreshComplete();
                progressBar.setVisibility(View.GONE);
                if (currentPage == 1) {
                    listEntities.clear();
                }
                int size = myPacketBean.getData().getRedPacketsItems().getList().size();
                if (size > 0) {
                    listEntities.addAll(myPacketBean.getData().getRedPacketsItems().getList());
                }
                hasNext=myPacketBean.getData().getRedPacketsItems().isNext();
                redPacketAdapter.notifyDataSetChanged();
                if (size < pageSize) {
                    isEnd = true;
                } else {
                    isEnd = false;
                }
                isLoad = false;
                setEmptyView(Constants.NO_DATA);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ptr.refreshComplete();
                progressBar.setVisibility(View.GONE);
                setEmptyView(Constants.NET_DISCONNECT);
                Util.showToast(RedPacketListActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void setEmptyView(int type) {
        if (listEntities.isEmpty()) {
            imgHint.setVisibility(View.VISIBLE);
            if (type == Constants.NO_DATA) {
                imgHint.setImageResource(R.mipmap.ic_empty_account);
            } else {
                imgHint.setImageResource(R.mipmap.ic_no_net);
            }
        }
    }

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }
}
