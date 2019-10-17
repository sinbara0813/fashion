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
import com.d2cmall.buyer.adapter.HighIncomeAdapter;
import com.d2cmall.buyer.api.OpenOrderApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.OpenOrderListBean;
import com.d2cmall.buyer.binder.ScrollEndBinder;
import com.d2cmall.buyer.holder.DefaultHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
//开单利器,高收益商品

public class HighIncomeProductListActivity extends BaseActivity {
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
    private VirtualLayoutManager virtualLayoutManager;
    private DelegateAdapter delegateAdapter;

    List<OpenOrderListBean.DataBean.ProductsBean.ListBean> productList;
    private HighIncomeAdapter highIncomeAdapter;

    private int lastPosition = -1;
    private int lastOffer;

    private long id; //买家秀的tagId
    private int p = 1;
    private boolean hasNext; //是否有下一页数据
    private boolean isFresh;

    private boolean hasSetAdapter; //是否设置过设配器
    private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志
    private String title;
    private boolean hasSetGlobalRatio=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_recyclerview_general);
        ButterKnife.bind(this);
        title = getIntent().getStringExtra("title");
        productList = new ArrayList<>();
        init();
        loadBillList();
    }

    private void init() {
        nameTv.setText(title);
        recycleView.setBackgroundColor(getResources().getColor(R.color.color_white));
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
        virtualLayoutManager = new VirtualLayoutManager(this);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(0, 2);
        recycleView.setRecycledViewPool(recycledViewPool);
        highIncomeAdapter = new HighIncomeAdapter(this, productList);
        delegateAdapter.addAdapter(highIncomeAdapter);
        recycleView.setLayoutManager(virtualLayoutManager);
        recycleView.setAdapter(delegateAdapter);
        if (lastPosition >= 0) {
            virtualLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
        }
        initListener();
        hasSetAdapter = true;
    }

    private void initListener() {
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisPosition = virtualLayoutManager.findLastVisibleItemPosition();
                int itemCount = virtualLayoutManager.getItemCount();
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
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = virtualLayoutManager.findLastVisibleItemPosition();
                        if (last > delegateAdapter.getItemCount() - 3 && hasNext) {
                            p++;
                            loadBillList();
                        }
                }
            }
        });
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgHint.setVisibility(View.GONE);
                btnReload.setVisibility(View.GONE);
                loadBillList();
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

    private void loadBillList() {
        progressBar.setVisibility(View.VISIBLE);
        OpenOrderApi api = new OpenOrderApi();
        if ("开单利器".equals(title)) {//开单利器
            api.setTagId(1207);
        } else {
            api.setTagId(1209);   //高收益商品
        }
        api.setPageNumber(p);
        api.setPageSize(20);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OpenOrderListBean>() {
            @Override
            public void onResponse(OpenOrderListBean openOrderListBean) {
                progressBar.setVisibility(View.GONE);
                if (isFresh) {
                    ptr.refreshComplete();
                }
                if (p == 1) {
                    productList.clear();
                }
                double ratio = Double.valueOf(openOrderListBean.getData().getRatio());
                if(highIncomeAdapter!=null && !hasSetGlobalRatio){
                    highIncomeAdapter.setGlobalRadio(ratio);
                    hasSetGlobalRatio=true;
                }
                hasNext = openOrderListBean.getData().getProducts().isNext();
                if (openOrderListBean.getData().getProducts() != null && openOrderListBean.getData().getProducts().getList().size() > 0) {
                    imgHint.setVisibility(View.GONE);
                    imgHint.setImageResource(R.mipmap.ic_no_net);
                    recycleView.setVisibility(View.VISIBLE);
                    btnReload.setVisibility(View.GONE);
                    productList.addAll(openOrderListBean.getData().getProducts().getList());
                    highIncomeAdapter.notifyDataSetChanged();
                } else {
                    setEmptyView(Constants.NO_DATA);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                if (isFresh) {
                    ptr.refreshComplete();
                }
                setEmptyView(Constants.NET_DISCONNECT);
            }
        });
    }

    public void refresh() {
        hasSetGlobalRatio=false;
        p = 1;
        loadBillList();
        isFresh = true;
    }

    private void setEmptyView(int type) {
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
                refresh();
                break;
        }
    }
}
