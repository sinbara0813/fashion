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
import com.d2cmall.buyer.adapter.PartnerAccountLogAdapter;
import com.d2cmall.buyer.adapter.SchoolThemeListAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.SaleSchoolListBean;
import com.d2cmall.buyer.binder.ScrollEndBinder;
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

public class SaleSchoolListActivity extends BaseActivity {

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
    private SchoolThemeListAdapter saleSchoolListAdapter;
    private int currentPage = 1;
    private boolean hasNext = true;
    private ArrayList<SaleSchoolListBean.DataBean.ThemesBean.ListBean> listBeen = new ArrayList<>();
    private VirtualLayoutManager mLayoutManager;
    private DelegateAdapter delegateAdapter;
    private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志
    private int id;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_recyclerview);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra("id", 0);
        title = getIntent().getStringExtra("title");
        init();
        initListener();
    }


    private void init() {
        nameTv.setText(title);
        mLayoutManager = new VirtualLayoutManager(SaleSchoolListActivity.this);
        saleSchoolListAdapter = new SchoolThemeListAdapter(this, listBeen,title);
        recycleView.setLayoutManager(mLayoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recycleView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        delegateAdapter = new DelegateAdapter(mLayoutManager, true);
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(0, 2);
        recycleView.setRecycledViewPool(recycledViewPool);
        recycleView.setAdapter(delegateAdapter);
        delegateAdapter.addAdapter(saleSchoolListAdapter);
        progressBar.setVisibility(View.VISIBLE);
        loadData();
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
                loadData();
            }
        });
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = mLayoutManager.findLastVisibleItemPosition();
                        if (last > saleSchoolListAdapter.getItemCount() - 3 && hasNext) {
                            currentPage++;
                            loadData();
                        }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisPosition = mLayoutManager.findLastVisibleItemPosition();
                int itemCount = mLayoutManager.getItemCount();
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

    private void loadData() {
        SimpleApi simpleApi = new SimpleApi();
        simpleApi.setP(currentPage);
        simpleApi.setPageSize(20);
        simpleApi.setInterPath(String.format(Constants.GET_PARTNER_SALE_SCHOOL_LIST_URL,id));
        D2CApplication.httpClient.loadingRequest(simpleApi, new BeanRequest.SuccessListener<SaleSchoolListBean>() {
            @Override
            public void onResponse(SaleSchoolListBean saleSchoolListBean) {
                recycleView.setBackgroundColor(getResources().getColor(R.color.bg_color));
                ptr.refreshComplete();
                btnReload.setVisibility(View.GONE);
                imgHint.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                if (currentPage == 1) {
                    listBeen.clear();
                }
                int size = saleSchoolListBean.getData().getThemes().getList().size();
                if (size > 0) {
                    List<SaleSchoolListBean.DataBean.ThemesBean.ListBean> list = saleSchoolListBean.getData().getThemes().getList();
                    listBeen.addAll(list);
                } else {
                    setEmptyView(Constants.NO_DATA);
                }
                if (saleSchoolListAdapter != null) {
                    saleSchoolListAdapter.notifyDataSetChanged();
                    hasNext = saleSchoolListBean.getData().getThemes().isNext();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ptr.refreshComplete();
                progressBar.setVisibility(View.GONE);
                Util.showToast(SaleSchoolListActivity.this, Util.checkErrorType(error));
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


    @OnClick({R.id.back_iv,R.id.btn_reload})
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

    private void refresh() {
        currentPage=1;
        loadData();
    }
}
