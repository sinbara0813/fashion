package com.d2cmall.buyer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.d2cmall.buyer.adapter.CollectAdapter;
import com.d2cmall.buyer.adapter.PartnerBillAdapter;
import com.d2cmall.buyer.api.MyCollectProductBean;
import com.d2cmall.buyer.api.PartnerSaleListApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.PartnerSaleListBean;
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

//AM, DM查看买手零售单列表界面,只有零售
public class SellRetailListActivity extends BaseActivity {

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
    private int currentPage = 1;
    private boolean hasNext =false;
    private VirtualLayoutManager mLayoutManager;
    private DelegateAdapter delegateAdapter;
    private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志
    List<PartnerSaleListBean.DataBean.PartnerBillBean.ListBean> billList=new ArrayList<>();
    private PartnerBillAdapter partnerBillAdapter;
    private long partnerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_recyclerview);
        partnerId = getIntent().getLongExtra("partnerId", 0);
        ButterKnife.bind(this);
        nameTv.setText("零售订单");
        initListener();
        doBusiness();
    }
    private void doBusiness() {
        recycleView.setBackgroundColor(getResources().getColor(R.color.bg_color));
        mLayoutManager = new VirtualLayoutManager(this);
        partnerBillAdapter = new PartnerBillAdapter(this, billList,true);
        recycleView.setLayoutManager(mLayoutManager);
        delegateAdapter = new DelegateAdapter(mLayoutManager, true);
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(0,2);
        recycleView.setRecycledViewPool(recycledViewPool);
        recycleView.setAdapter(delegateAdapter);
        delegateAdapter.addAdapter(partnerBillAdapter);
        progressBar.setVisibility(View.VISIBLE);
        requestRetailListTask();

    }

    private void initListener() {
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                currentPage = 1;
                requestRetailListTask();
            }
        });
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = mLayoutManager.findLastVisibleItemPosition();
                        if (last > partnerBillAdapter.getItemCount() - 3 && hasNext) {
                            currentPage++;
                            requestRetailListTask();
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

    private void requestRetailListTask() { //加载的只有零售数据
        PartnerSaleListApi partnerSaleListApi = new PartnerSaleListApi();
        partnerSaleListApi.setPartnerId(String.valueOf(partnerId));
        partnerSaleListApi.setP(currentPage);
        partnerSaleListApi.setPageSize(20);
        D2CApplication.httpClient.loadingRequest(partnerSaleListApi, new BeanRequest.SuccessListener<PartnerSaleListBean>() {
            @Override
            public void onResponse(PartnerSaleListBean partnerSaleListBean) {
                if (isFinishing() || progressBar == null) {
                    return;
                }
                ptr.refreshComplete();
                progressBar.setVisibility(View.GONE);
                imgHint.setVisibility(View.GONE);
                btnReload.setVisibility(View.GONE);
                if(currentPage==1){
                    billList.clear();
                }
                if(partnerSaleListBean==null || partnerSaleListBean.getData().getPartnerBill().getList()==null || partnerSaleListBean.getData().getPartnerBill().getList().size()==0){
                    setEmptyView(Constants.NO_DATA);
                }
                hasNext=partnerSaleListBean.getData().getPartnerBill().isNext();
                billList.addAll(partnerSaleListBean.getData().getPartnerBill().getList());
                if(partnerBillAdapter!=null){
                    partnerBillAdapter.setExpireDay(partnerSaleListBean.getData().getExpireDay());
                    partnerBillAdapter.notifyDataSetChanged();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                ptr.refreshComplete();
                setEmptyView(Constants.NET_DISCONNECT);
                Util.showToast(SellRetailListActivity.this,Util.checkErrorType(error));
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
                requestRetailListTask();
                break;
        }
    }
}
