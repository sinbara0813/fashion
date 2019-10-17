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
import com.d2cmall.buyer.adapter.ReopenStoreAdapter;
import com.d2cmall.buyer.api.ReopenStoreApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.GoodsBean;
import com.d2cmall.buyer.binder.ScrollEndBinder;
import com.d2cmall.buyer.holder.DefaultHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class ReopenStoreActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
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
    @Bind(R.id.title_back_iv)
    ImageView titleBackIv;
    @Bind(R.id.text_title_name)
    TextView textTitleName;
    @Bind(R.id.title)
    RelativeLayout title;

    private ReopenStoreAdapter reopenStoreAdapter;
    private int currentPage = 1;
    private boolean hasNext = false;
    private ArrayList<GoodsBean.DataBean.ProductsBean.ListBean> goodList = new ArrayList<GoodsBean.DataBean.ProductsBean.ListBean>();
    private VirtualLayoutManager mLayoutManager;
    private DelegateAdapter delegateAdapter;
    private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志
    private float lastAlpha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reopen_store);
        ButterKnife.bind(this);
        initListener();
        doBusiness();
    }

    private void doBusiness() {
        //改背景色
        mLayoutManager = new VirtualLayoutManager(ReopenStoreActivity.this);
        reopenStoreAdapter = new ReopenStoreAdapter(this, goodList);
        recycleView.setLayoutManager(mLayoutManager);
        delegateAdapter = new DelegateAdapter(mLayoutManager, true);
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(0, 2);
        recycleView.setRecycledViewPool(recycledViewPool);
        recycleView.setAdapter(delegateAdapter);
        delegateAdapter.addAdapter(reopenStoreAdapter);
        progressBar.setVisibility(View.VISIBLE);
        requestProductList();
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
                requestProductList();
            }
        });
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = mLayoutManager.findLastVisibleItemPosition();
                        if (last > reopenStoreAdapter.getItemCount() - 3 && hasNext) {
                            currentPage++;
                            requestProductList();
                        }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisPosition = mLayoutManager.findLastVisibleItemPosition();
                int itemCount = mLayoutManager.getItemCount();
                float alpha = mLayoutManager.getOffsetToStart() / (float) ScreenUtil.dip2px(150);
                changeAlpha(alpha);
                if (lastVisPosition >= itemCount - 3 && !hasNext && currentPage > 1) {
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
        });
    }

    public void changeAlpha(float alpha) {
        if(alpha>1){
            alpha=1;
        }
        title.setAlpha(alpha);
        backIv.setAlpha(1-alpha);
        lastAlpha = alpha;
    }

    private void requestProductList() {
        ReopenStoreApi api = new ReopenStoreApi();
        api.setPageNumber(currentPage);
        api.setPageSize(20);
        api.setStatus(-1);
        api.setInterPath(Constants.PRODUCTS_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<GoodsBean>() {
            @Override
            public void onResponse(GoodsBean goodsBean) {
                if (isFinishing() || progressBar == null) {
                    return;
                }
                ptr.refreshComplete();
                btnReload.setVisibility(View.GONE);
                imgHint.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                if (currentPage == 1) {
                    goodList.clear();
                }
                int size = goodsBean.getData().getProducts().getList().size();
                if (size > 0) {
                    goodList.addAll(goodsBean.getData().getProducts().getList());
                } else {
                    setEmptyView(Constants.NO_DATA);
                }
                hasNext = goodsBean.getData().getProducts().isNext();
                if (reopenStoreAdapter != null) {
                    reopenStoreAdapter.notifyDataSetChanged();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ptr.refreshComplete();
                progressBar.setVisibility(View.GONE);
                Util.showToast(ReopenStoreActivity.this, Util.checkErrorType(error));
                setEmptyView(Constants.NET_DISCONNECT);
            }
        });
    }

    private void setEmptyView(int type) {
        recycleView.setBackgroundColor(getResources().getColor(R.color.color_white));
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

    @OnClick({R.id.back_iv, R.id.btn_reload,R.id.title_back_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
            case R.id.title_back_iv:
                finish();
                break;
            case R.id.btn_reload:
                currentPage = 1;
                requestProductList();
                break;
        }
    }
}
