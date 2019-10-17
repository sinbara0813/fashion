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
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.DCoinShopAdapter;
import com.d2cmall.buyer.adapter.DCoinShopProductAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.DCionProductListBean;
import com.d2cmall.buyer.bean.DCionShopBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.MyPointBean;
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
import de.greenrobot.event.Subscribe;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Administrator on 2018/8/6.
 * Description : D币商城
 */

public class DCoinShopActivity extends BaseActivity {
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
    private boolean hasNext =true;
    private ArrayList<DCionProductListBean.DataBean.ListBeanX.ListBean> listBeans = new ArrayList<>();
    private VirtualLayoutManager mLayoutManager;
    private DelegateAdapter delegateAdapter;
    private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志
    private DCoinShopAdapter dCoinShopAdapter;
    private DCoinShopProductAdapter dCoinShopProductAdapter;
    boolean hasSetTopAdapter=false;
    DCionShopBean mDCionShopBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_recyclerview);
        ButterKnife.bind(this);
        doBusiness();
        initListener();
    }

    @Override
    protected void onResume() {
        loadMyPoint();
        super.onResume();
    }


    //D币的字段在member里登录才会刷,不实时,和积分明细对不上,所以这个接口查自己的D币
    private void loadMyPoint() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.DCION_MY_POINT);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MyPointBean>() {
            @Override
            public void onResponse(MyPointBean myPointBean) {
                if(dCoinShopAdapter!=null && myPointBean!=null){
                    dCoinShopAdapter.setPoint(myPointBean.getData().getPoint());
                }
            }
        });
    }

    private void doBusiness() {
        nameTv.setText("积分商城");
        mLayoutManager = new VirtualLayoutManager(DCoinShopActivity.this);
        int itemWidth = (ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(48)) / 2;
        recycleView.setLayoutManager(mLayoutManager);
        GridLayoutHelper layoutHelper = new GridLayoutHelper(2);
        layoutHelper.setGap(ScreenUtil.dip2px(16));
        layoutHelper.setAutoExpand(false);
        dCoinShopAdapter=new DCoinShopAdapter(DCoinShopActivity.this);
        dCoinShopProductAdapter = new DCoinShopProductAdapter(this, listBeans, itemWidth);
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(0,1);
        recycledViewPool.setMaxRecycledViews(1,2);
        recycleView.setRecycledViewPool(recycledViewPool);
        delegateAdapter = new DelegateAdapter(mLayoutManager, true);
        recycleView.setAdapter(delegateAdapter);
        delegateAdapter.addAdapter(dCoinShopAdapter);
        delegateAdapter.addAdapter(dCoinShopProductAdapter);
        requestTopInfo();
        requestProductsData();
    }

    private void initListener() {
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
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = mLayoutManager.findLastVisibleItemPosition();
                        if (last > dCoinShopProductAdapter.getItemCount() - 3 && hasNext) {
                            currentPage++;
                            requestProductsData();
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
    private void requestTopInfo() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.DCION_SHOP_URL,"MY","POINTPRODUCT"));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<DCionShopBean>() {
            @Override
            public void onResponse(DCionShopBean dCionShopBean) {
                    mDCionShopBean=dCionShopBean;
                    if(dCoinShopAdapter!=null && dCionShopBean!=null){
                        dCoinShopAdapter.setdCionShopBean(dCionShopBean);
                        dCoinShopAdapter.notifyDataSetChanged();
                    }
            }
        });

    }
    private void requestProductsData() {
        progressBar.setVisibility(View.VISIBLE);
        SimpleApi api = new SimpleApi();
        api.setP(currentPage);
        api.setPageSize(20);
        api.setInterPath(Constants.DCION_PROCUCT_LIST_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<DCionProductListBean>() {
            @Override
            public void onResponse(DCionProductListBean dCionProductListBean) {
                if(isFinishing() || progressBar==null){
                    return;
                }
                ptr.refreshComplete();
                btnReload.setVisibility(View.GONE);
                imgHint.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                if (currentPage == 1) {
                    listBeans.clear();
                }
                recycleView.setVisibility(View.VISIBLE);
                int size = dCionProductListBean.getData().getList().getList().size();
                if (size > 0) {
                    List<DCionProductListBean.DataBean.ListBeanX.ListBean> collectList = dCionProductListBean.getData().getList().getList();
                    listBeans.addAll(collectList);
                } else {
                    setEmptyView(Constants.NO_DATA);
                }
                if (dCoinShopProductAdapter != null) {
                    dCoinShopProductAdapter.notifyDataSetChanged();
                }
                hasNext = dCionProductListBean.getData().getList().isNext();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ptr.refreshComplete();
                progressBar.setVisibility(View.GONE);
                Util.showToast(DCoinShopActivity.this, Util.checkErrorType(error));
                setEmptyView(Constants.NET_DISCONNECT);
            }
        });
    }

    @OnClick({R.id.back_iv, R.id.btn_reload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.btn_reload:
                refresh();
                break;
        }
    }

    private void refresh() {
        currentPage=1;
        requestProductsData();
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
}
