package com.d2cmall.buyer.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.CouponRelationAdapter;
import com.d2cmall.buyer.adapter.ProductListAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.CouponRangeBean;
import com.d2cmall.buyer.bean.GoodsBean;
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

//优惠券关联商品界面
public class CouponRelationProductListActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView mBackIv;
    @Bind(R.id.name_tv)
    TextView mNameTv;
    @Bind(R.id.title_right)
    TextView mTitleRight;
    @Bind(R.id.title_layout)
    RelativeLayout mTitleLayout;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.rl_collect)
    RelativeLayout mLlCollect;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout mPtr;
    @Bind(R.id.img_hint)
    ImageView mImgHint;
    @Bind(R.id.btn_reload)
    TextView mBtnReload;
    @Bind(R.id.empty_hint_layout)
    LinearLayout mEmptyHintLayout;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    private ProductListAdapter productListAdapter;
    private ArrayList<Object> mProductBeans = new ArrayList<>();
    private VirtualLayoutManager mLayoutManager;
    private DelegateAdapter delegateAdapter;
    private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志
    private long couponId;
    private int currentPage = 1;
    private boolean hasNext =true;
    private StaggeredGridLayoutHelper staggerLayoutHelper;
    private CouponRelationAdapter couponRelationAdapter;
    private boolean hasSetAdapter=false;
    private CouponRangeBean.DataBean.CouponBean coupon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);
        mRecyclerView.setPadding(0,0,0,0);
        couponId = getIntent().getLongExtra("couponId", -1);
        String couponName = getIntent().getStringExtra("couponName");
        if(!Util.isEmpty(couponName)){
            mNameTv.setText(couponName);
            mNameTv.setLines(1);
            mNameTv.setPadding(0,0, ScreenUtil.dip2px(16),0);
            mNameTv.setEllipsize(TextUtils.TruncateAt.END);
        }
        if(couponId==-1){
            setEmptyView(Constants.NO_DATA);
            return;
        }
        doBusiness();
        initListener();
    }

    private void doBusiness() {
        mLayoutManager = new VirtualLayoutManager(this);
        staggerLayoutHelper = new StaggeredGridLayoutHelper(2);
        staggerLayoutHelper.setHGap(ScreenUtil.dip2px(16));
        staggerLayoutHelper.setPaddingLeft(ScreenUtil.dip2px(16));
        staggerLayoutHelper.setPaddingRight(ScreenUtil.dip2px(16));
        staggerLayoutHelper.setMarginTop(ScreenUtil.dip2px(16));
        productListAdapter = new ProductListAdapter(this, staggerLayoutHelper, mProductBeans);
        mRecyclerView.setLayoutManager(mLayoutManager);
        delegateAdapter = new DelegateAdapter(mLayoutManager, false);
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(0,2);
        mRecyclerView.setRecycledViewPool(recycledViewPool);
        mRecyclerView.setAdapter(delegateAdapter);
        delegateAdapter.addAdapter(productListAdapter);
        mProgressBar.setVisibility(View.VISIBLE);
        requestProductsTask();
    }

    private void requestProductsTask() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.COUPONS_PRODUCTS_RANGE,couponId));
        api.setP(currentPage);
        api.setPageSize(50);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CouponRangeBean>() {
            @Override
            public void onResponse(CouponRangeBean couponRangeBean) {
                if(isFinishing() || mProgressBar==null){
                    return;
                }
                mPtr.refreshComplete();
                mBtnReload.setVisibility(View.GONE);
                mImgHint.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                if (currentPage == 1) {
                    mProductBeans.clear();
                }
                int size = couponRangeBean.getData().getProductsX().getList().size();
                if (size > 0) {
                    List<GoodsBean.DataBean.ProductsBean.ListBean> productBeans = couponRangeBean.getData().getProductsX().getList();
                    mProductBeans.addAll(productBeans);
                } else {
                    setEmptyView(Constants.NO_DATA);
                }
                coupon = couponRangeBean.getData().getCoupon();
                if(!hasSetAdapter){
                    hasSetAdapter=true;
                    couponRelationAdapter=new CouponRelationAdapter(CouponRelationProductListActivity.this,couponRangeBean.getData().getCoupon());
                    delegateAdapter.addAdapter(0,couponRelationAdapter);
                }
                if (productListAdapter != null) {
                    productListAdapter.notifyDataSetChanged();
                    hasNext = couponRangeBean.getData().getProductsX().isNext();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mPtr.refreshComplete();
                mProgressBar.setVisibility(View.GONE);
                Util.showToast(CouponRelationProductListActivity.this, Util.checkErrorType(error));
                setEmptyView(Constants.NET_DISCONNECT);
            }
        });
    }

    private void initListener() {
        mPtr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                currentPage = 1;
                requestProductsTask();
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (productListAdapter.getLongClickPosition() != -1) {
                    if (mProductBeans.get(productListAdapter.getLongClickPosition()) instanceof GoodsBean.DataBean.ProductsBean.ListBean) {
                        ((GoodsBean.DataBean.ProductsBean.ListBean) mProductBeans.get(productListAdapter.getLongClickPosition())).setLongClick(false);
                        productListAdapter.setLongClickPosition(-1);
                        productListAdapter.notifyDataSetChanged();
                    }
                }
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = mLayoutManager.findLastVisibleItemPosition();
                        if (last > productListAdapter.getItemCount() - 3 && hasNext) {
                            currentPage++;
                            requestProductsTask();
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
    private void setEmptyView(int type) {
        mRecyclerView.setVisibility(View.GONE);
        if (type == Constants.NO_DATA) {
            mImgHint.setVisibility(View.VISIBLE);
            mImgHint.setImageResource(R.mipmap.icon_empty_default);
            mBtnReload.setVisibility(View.VISIBLE);
            mBtnReload.setText("暂无数据");
            mBtnReload.setBackgroundColor(getResources().getColor(R.color.transparent));
        } else {
            mBtnReload.setText("重新加载");
            mBtnReload.setBackgroundResource(R.drawable.sp_line);
            mBtnReload.setVisibility(View.VISIBLE);
            mImgHint.setVisibility(View.VISIBLE);
            mImgHint.setImageResource(R.mipmap.ic_no_net);
        }

    }

    @OnClick({R.id.back_iv, R.id.btn_reload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.btn_reload:
                mProgressBar.setVisibility(View.VISIBLE);
                currentPage=1;
                requestProductsTask();
                break;
        }
    }

}
