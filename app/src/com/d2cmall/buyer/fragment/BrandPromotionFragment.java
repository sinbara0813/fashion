package com.d2cmall.buyer.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.d2cmall.buyer.adapter.BrandPromotionAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.BrandDetailPromotionBean;
import com.d2cmall.buyer.bean.ProductDetailBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import static com.d2cmall.buyer.Constants.NET_DISCONNECT;
import static com.d2cmall.buyer.Constants.NO_DATA;

/**
 * Created by zhangzz on 2018/8/7.
 * 店铺活动Fragment
 */

public class BrandPromotionFragment extends BaseFragment {


    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout ptr;
    @Bind(R.id.img_hint)
    ImageView imgHint;
    @Bind(R.id.btn_reload)
    TextView btnReload;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.rl_root)
    RelativeLayout rlRoot;
    private VirtualLayoutManager virtualLayoutManager;
    private DelegateAdapter delegateAdapter;
    private BrandPromotionAdapter brandPromotionAdapter;
    private BrandDetailPromotionBean mPoromotionBean;
    private long id;

    private boolean hasSetAdapter;
    private List<BrandDetailPromotionBean.DataBean.ArticlesBean> articles;
    private List<BrandDetailPromotionBean.DataBean.CouponDefsBean> couponDefs;
    private List promotions;

    public static BrandPromotionFragment newInstance(long id) {
        BrandPromotionFragment brandFashionFragment = new BrandPromotionFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        brandFashionFragment.setArguments(bundle);
        return brandFashionFragment;
    }


    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_coupon, container, false);
        if (getArguments() != null) {
            id = getArguments().getLong("id");
        }
        return view;
    }

    @Override
    public void prepareView() {
        rlRoot.setBackgroundColor(Color.parseColor("#FFFFFF"));
        articles = new ArrayList<>();
        promotions = new ArrayList<>();
        couponDefs = new ArrayList<>();
        ptr.setHeadLabel(getString(R.string.label_d2c_go));
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return false;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refresh();
            }
        });
        virtualLayoutManager = new VirtualLayoutManager(mContext);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);
        recycleView.setLayoutManager(virtualLayoutManager);
        recycleView.setAdapter(delegateAdapter);
    }

    private void loadPromotionData() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.BRAND_POMOTION, id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BrandDetailPromotionBean>() {

            @Override
            public void onResponse(BrandDetailPromotionBean promotionBean) {
                if(!isVisibleToUser){
                    return;
                }
                if( ptr!=null){
                    ptr.refreshComplete();
                }
                recycleView.setVisibility(View.VISIBLE);
                imgHint.setVisibility(View.GONE);

                if (promotionBean == null || ((promotionBean.getData().getPromotions() == null || promotionBean.getData().getPromotions().size()==0 ) && (promotionBean.getData().getCouponDefs() == null || promotionBean.getData().getCouponDefs().size()==0) && (promotionBean.getData().getArticles() == null || promotionBean.getData().getArticles().size()==0) ) ) {
                    //当前店铺没有优惠券、文章、活动，则推荐显示平台其它活动有关联品牌图片字段的活动
                    if(promotionBean.getData().getOtherPromotions()==null ||promotionBean.getData().getOtherPromotions().size()==0 ){
                        setEmptyView(NO_DATA);
                    }else{
                        mPoromotionBean=promotionBean;
                        if(promotions.size()>0){
                            promotions.clear();
                        }
                        promotions.addAll(promotionBean.getData().getOtherPromotions());
                        if(!hasSetAdapter){
                            hasSetAdapter=true;
                            brandPromotionAdapter = new BrandPromotionAdapter(mContext,articles,couponDefs,promotions);
                            brandPromotionAdapter.setNoData(true);
                            delegateAdapter.addAdapter(brandPromotionAdapter);
                        }
                        brandPromotionAdapter.notifyDataSetChanged();
                    }

                } else {
                    mPoromotionBean=promotionBean;
                    if(articles.size()>0){
                        articles.clear();
                    }
                    if(couponDefs.size()>0){
                        couponDefs.clear();
                    }
                    if(promotions.size()>0){
                        promotions.clear();
                    }
                    if(promotionBean.getData().getArticles()!=null && promotionBean.getData().getArticles().size()>0){
                        articles .addAll(promotionBean.getData().getArticles());
                    }
                    if(promotionBean.getData().getCouponDefs()!=null && promotionBean.getData().getCouponDefs().size()>0){
                        couponDefs .addAll(promotionBean.getData().getCouponDefs()) ;
                    }
                    if(promotionBean.getData().getPromotions()!=null && promotionBean.getData().getPromotions().size()>0){
                        promotions.addAll(promotionBean.getData().getPromotions()) ;
                    }
                    if(!hasSetAdapter){
                        hasSetAdapter=true;
                        brandPromotionAdapter = new BrandPromotionAdapter(mContext,articles,couponDefs,promotions);
                        delegateAdapter.addAdapter(brandPromotionAdapter);
                    }
                }
                if(brandPromotionAdapter!=null){
                    brandPromotionAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (ptr != null) {
                    ptr.refreshComplete();
                }
                setEmptyView(NET_DISCONNECT);
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }

    public void refresh() {
    }

    @Override
    public void doBusiness() {
        if (mPoromotionBean==null){
            loadPromotionData();
        }
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
