package com.d2cmall.buyer.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.CartActivity;
import com.d2cmall.buyer.activity.HomeActivity;
import com.d2cmall.buyer.activity.ReopenStoreActivity;
import com.d2cmall.buyer.activity.SettingActivity;
import com.d2cmall.buyer.adapter.MainLikeAdapter;
import com.d2cmall.buyer.adapter.MineFragmentTopAdapter;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.CouponApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.AdShareBean;
import com.d2cmall.buyer.bean.AfterSaleCountBean;
import com.d2cmall.buyer.bean.CartRecommendBean;
import com.d2cmall.buyer.bean.CouponsBean;
import com.d2cmall.buyer.bean.FootMarkCountBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.OrderCountBean;
import com.d2cmall.buyer.bean.PartnerMemberBean;
import com.d2cmall.buyer.bean.ProductDetailBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.binder.ScrollEndBinder;
import com.d2cmall.buyer.holder.DefaultHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.CartGuidePop;
import com.d2cmall.buyer.widget.D2CFrameLayout;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;
import com.d2cmall.buyer.widget.SimpleDialog;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import q.rorbin.badgeview.QBadgeView;

import static com.d2cmall.buyer.Constants.AFTER_SALE_COUNT;
import static com.d2cmall.buyer.Constants.GET_FOOTMARK_PRODUCT_LIST_COUNT;
import static com.d2cmall.buyer.util.SharePrefConstant.HAS_SHOW_BUYER_DIALOG;

/**
 * Created by rookie on 2018/5/23.
 */

public class MineNewFragment extends MineSuperFragment implements   MineFragmentTopAdapter.ClickToPartnerListener {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout ptr;
    @Bind(R.id.iv_top)
    ImageView ivTop;
    @Bind(R.id.ll_button)
    LinearLayout llButton;
    @Bind(R.id.iv_setting)
    ImageView ivSetting;
    @Bind(R.id.tv_red_point)
    TextView tvRedPoint;
    @Bind(R.id.tv_user_name)
    TextView tvUserName;
    @Bind(R.id.iv_cart)
    ImageView ivCart;
    @Bind(R.id.top_title)
    RelativeLayout topTitle;

    private boolean isPrepared;
    private boolean isFirst=true;
    public boolean isLogin = false;
    private UserBean.DataEntity.MemberEntity user;
    private boolean isUserInfoIn = false;
    private QBadgeView cartNum;
    private boolean isVisible = false;
    private SimpleDialog simpleDialog;
    private VirtualLayoutManager layoutManager;
    private DelegateAdapter delegateAdapter;
    private MineFragmentTopAdapter mineFragmentTopAdapter;
    private List<ProductDetailBean.DataBean.RecommendProductsBean> recommendProductList = new ArrayList<>();
    private MainLikeAdapter recommendProductAdapter;
    private boolean isCanTopVisible;
    private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志
    private float lastAlpha;
    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine_new, container, false);
    }

    @Override
    public void doBusiness() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (simpleDialog == null) {
            simpleDialog = new SimpleDialog(mContext);
        }
        isPrepared = true;
        layoutManager = new VirtualLayoutManager(getContext());
        delegateAdapter = new DelegateAdapter(layoutManager, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(delegateAdapter);
        mineFragmentTopAdapter = new MineFragmentTopAdapter(mContext);
        mineFragmentTopAdapter.setClickToPartnerListener(this);
//        mineNewFragmentTopStickAdapter = new MineNewFragmentTopStickAdapter(mContext);
//        mineNewFragmentTopStickAdapter.setHasToOtherActivityListener(this);
//        delegateAdapter.addAdapter(mineNewFragmentTopStickAdapter);
        delegateAdapter.addAdapter(mineFragmentTopAdapter);
        initListener();
        loadRecommend();
        loadTopBG();
        setCartNum();
        user = Session.getInstance().getUserFromFile(mContext);
        if (mineFragmentTopAdapter != null) {
            mineFragmentTopAdapter.setUserInfo();
        }
        setUserInfo();
        if (user == null) {
            //showCartGuide();
        }
    }


    private void initListener() {
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //切换买手中心
                user = Session.getInstance().getUserFromFile(mContext);
                if (user == null || user.getPartnerId() == 0) {
                    ptr.refreshComplete();
                    return;
                }
                if (user != null && user.getPartnerId() > 0) {
                    if (changeFragmentListener != null) {
                        changeFragmentListener.chaneFragment(0);
                    }
                }
                ptr.refreshComplete();
            }
        });
        D2CFrameLayout header = ptr.getHeader();
        ArrayList<String> strings = new ArrayList<>();
        strings.add("下拉进入买手中心...");
        strings.add("释放进入买手中心...");
        strings.add("进入买手中心...");
        header.setRefreshLable(strings, "#FF1C1D1D");
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int last = layoutManager.findLastVisibleItemPosition();
                if (last > 4) {
                    if (!isCanTopVisible) {
                        llButton.animate().translationY(-ScreenUtil.dip2px(56)).setDuration(350).start();
                        isCanTopVisible = true;
                    }
                } else if (last < 4) {
                    if (isCanTopVisible) {
                        llButton.animate().translationY(ScreenUtil.dip2px(0)).setDuration(350).start();
                        isCanTopVisible = false;
                    }
                }
                if (user == null || user.getPartnerId() == 0) {
                    ptr.setEnabled(false);
                } else {
                    ptr.setEnabled(true);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (recommendProductAdapter != null) {
                    if (recommendProductAdapter.getLongClickPosition() != -1) {
                        recommendProductList.get(recommendProductAdapter.getLongClickPosition()).setLongClick(false);
                        recommendProductAdapter.setLongClickPosition(-1);
                        recommendProductAdapter.notifyDataSetChanged();
                    }
                }
                float alpha = layoutManager.getOffsetToStart() / (float) ScreenUtil.dip2px(44);
                changeAlpha(alpha);
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public void changeAlpha(float alpha) {
        topTitle.setAlpha(alpha);
        lastAlpha = alpha;
    }


    @Override
    public void show() {
        super.show();
        if (mContext!=null){
            setUserInfo();
            user=Session.getInstance().getUserFromFile(mContext);
            //登录且身份为买手ptr切换界面可用
            if (user == null || user.getPartnerId() == 0) {
                ptr.setEnabled(false);
            } else {
                ptr.setEnabled(true);
            }
        }
        /*if(isPrepared && mContext!=null){
            setUserInfo();
            if (mineFragmentTopAdapter != null) {
                mineFragmentTopAdapter.setUserInfo();
            }
            user=Session.getInstance().getUserFromFile(mContext);
            //登录且身份为买手ptr切换界面可用
            if (user == null || user.getPartnerId() == 0) {
                ptr.setEnabled(false);
            } else {
                ptr.setEnabled(true);
            }
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirst&&isVisibleToUser&&mContext!=null){
            setUserInfo();
        }
        if (isFirst){
            isFirst=false;
        }
    }

    private void showBuyerDialog() {
        boolean hasBuyerCountDown = D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.HAS_BUYER_COUNT, false);
        if (hasBuyerCountDown) {
            PartnerMemberBean.DataBean.PartnerBean partnerBean = Session.getInstance().getPartnerFromFile(mContext);
            if (partnerBean == null) {
                return;
            }
            long offer = DateUtil.strToDateLong(partnerBean.getExpiredDate()).getTime() - System.currentTimeMillis();
            if (offer <= 0) { //倒计时结束
                if (!D2CApplication.mSharePref.getSharePrefBoolean(HAS_SHOW_BUYER_DIALOG, false)) {
                    simpleDialog.show();
                    simpleDialog.setLeftClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            simpleDialog.dismiss();
                        }
                    }).setRightClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mContext, ReopenStoreActivity.class);
                            mContext.startActivity(intent);
                        }
                    }).setTitle("关店通知").setTvMessage(mContext.getResources().getString(R.string.buyer_close_notice))
                            .setRightText("重新开店").setLeftText("我知道了");
                    D2CApplication.mSharePref.putSharePrefBoolean(HAS_SHOW_BUYER_DIALOG, true);
                }
                D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.HAS_BUYER_COUNT, false);
            }
        }

    }

    //背景图,数据类型为广告类型
    private void loadTopBG() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.MY_TOP_BACK);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<AdShareBean>() {
            @Override
            public void onResponse(AdShareBean response) {
                if (response.getData().getAdResource() != null && !Util.isEmpty(response.getData().getAdResource().getPic())) {
                    if (mineFragmentTopAdapter != null) {
                        if (response.getData().getAdResource().getPic().contains(",")) {
                            String substring = response.getData().getAdResource().getPic().substring(0, response.getData().getAdResource().getPic().indexOf(","));
                            mineFragmentTopAdapter.setTopBg(substring);
                        } else {
                            mineFragmentTopAdapter.setTopBg(response.getData().getAdResource().getPic());
                        }
                    }
                }
            }
        });
    }

    /**
     * 猜你喜欢
     */
    private void loadRecommend() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.GET_MAIN_LIKE_LIST, 100));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CartRecommendBean>() {
            @Override
            public void onResponse(CartRecommendBean response) {
                if (!isVisibleToUser) {
                    return;
                }
                recommendProductList.clear();
                if (response.getList() != null && response.getList().size() > 0) {
                    recommendProductList.addAll(response.getList());
                    if (mineFragmentTopAdapter != null) {
                        mineFragmentTopAdapter.setRecommendTitleVisiable();
                    }
                }
                if (recommendProductAdapter == null) {
                    int itemWidth = (ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(48)) / 2;
                    recommendProductAdapter = new MainLikeAdapter(getActivity(), recommendProductList, itemWidth, "猜你喜欢", "我的精选");
                    delegateAdapter.addAdapter(recommendProductAdapter);
                    if (endAdapter == null) {
                        ScrollEndBinder endBinder = new ScrollEndBinder();
                        endAdapter = new BaseVirtualAdapter<>(endBinder, 100);
                        delegateAdapter.addAdapter(endAdapter);
                    }
                } else {
                    recommendProductAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void showCartGuide() {
        boolean hasShow = D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.GUIDE_CART, false);
        if (!hasShow) {
            CartGuidePop guidePop = new CartGuidePop(mContext);
            guidePop.show(getActivity().getWindow().getDecorView());
            D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.GUIDE_CART, true);
        }
    }




    private void requestOrderCountTask() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.ORDER_COUNT_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<OrderCountBean>() {
            @Override
            public void onResponse(OrderCountBean orderCountBean) {
                if (!isVisibleToUser) {
                    return;
                }
                if (mineFragmentTopAdapter != null) {
                    mineFragmentTopAdapter.setOrderCount(orderCountBean);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }

    private void requestCouponCountTask() {
        CouponApi api = new CouponApi();
        api.setPageSize(20);
        api.setStatus("CLAIMED");
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CouponsBean>() {
            @Override
            public void onResponse(CouponsBean couponCountsBean) {

                if (mineFragmentTopAdapter != null && mContext != null) {
                    mineFragmentTopAdapter.setCouponCount(couponCountsBean);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }

    public void setCartNum() {
        if(cartNum==null){
            cartNum = (QBadgeView) new QBadgeView(mContext).bindTarget(ivCart).setBadgeTextSize(9, true).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeBackgroundColor(Color.parseColor("#fff23365")).setGravityOffset(2, 2, true);
        }
        if (HomeActivity.count > 0 && ivCart != null) {
            if (ivCart == null) {
                ivCart = (ImageView) new QBadgeView(mContext).bindTarget(ivCart).setBadgeTextSize(10, true).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeBackgroundColor(Color.parseColor("#fff23365")).setGravityOffset(2, 2, true);
            }
            if (HomeActivity.count > 9) {
                cartNum.setBadgeText("9+");
            } else {
                cartNum.setBadgeNumber(HomeActivity.count);
            }
        } else {
            if (cartNum != null) {
                ViewParent parent = cartNum.getParent();
                if (parent != null) {
                    ((ViewGroup) parent).removeView(cartNum);
                    cartNum = null;
                }
            }
        }
    }

    public void removeCartNum() {
        if(cartNum!=null){
            ViewParent parent = cartNum.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(cartNum);
                cartNum = null;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEvent(GlobalTypeBean bean) {
        if (bean.getType() == Constants.GlobalType.CARTNUM) {
            removeCartNum();
            if (mineFragmentTopAdapter != null) {
                mineFragmentTopAdapter.removeCartNum();
            }
        } else if (bean.getType() == Constants.GlobalType.LOGOUT) {
            if (mContext == null) {
                return;
            }
            if (mineFragmentTopAdapter != null) {
                mineFragmentTopAdapter.setUserInfo();
            }
            setUserInfo();
            ptr.setEnabled(false);
        } else if (bean.getType() == Constants.GlobalType.LOGIN_OK) {
            if (mContext == null) {
                return;
            }
            if (changeFragmentListener != null) {
                user = Session.getInstance().getUserFromFile(mContext);
                if (user != null && user.getPartnerId() > 0) {
                    changeFragmentListener.chaneFragment(0);
                }
            }
        }
    }

    @Subscribe
    public void onEvent(ActionBean bean) {
        if (bean.type == Constants.ActionType.PARTENER_USER_INFO_CHANGE) {
            if (mineFragmentTopAdapter != null) {
                mineFragmentTopAdapter.requestPartnerInfo();
            }
        }
    }

    private void setUserInfo() {
        user = Session.getInstance().getUserFromFile(mContext);
        if (mineFragmentTopAdapter != null) {
            mineFragmentTopAdapter.setUserInfo();
        }
        if (user != null) {
            isLogin = true;
            isUserInfoIn = true;
            if (user.getHasNickName() && !Util.isEmpty(user.getRealHead()) && user.isDanger() == 0 && user.isPayDanger() == 0) {
                tvRedPoint.setVisibility(View.GONE);
            } else {
                tvRedPoint.setVisibility(View.VISIBLE);
            }
            tvUserName.setText(user.getNickname());
            //关店重开
//            showBuyerDialog();
            setCartNum();
            requestCouponCountTask();
            requestOrderCountTask();
            requestAfterCountTask();
            requestFootMarkNum();
            loadRecommend();
        }
    }


    private void requestFootMarkNum() {
        SimpleApi api = new SimpleApi();
        api.setMethod(BaseApi.Method.GET);
        api.setInterPath(GET_FOOTMARK_PRODUCT_LIST_COUNT);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<FootMarkCountBean>() {
            @Override
            public void onResponse(FootMarkCountBean response) {
                if (!isVisibleToUser) {
                    return;
                }
                if (mineFragmentTopAdapter != null) {
                    mineFragmentTopAdapter.setFootMarkNum(response);
                }
            }
        });
    }

    private void requestAfterCountTask() {
        SimpleApi api = new SimpleApi();
        api.setMethod(BaseApi.Method.GET);
        api.setInterPath(AFTER_SALE_COUNT);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<AfterSaleCountBean>() {
            @Override
            public void onResponse(AfterSaleCountBean response) {
                if (!isVisibleToUser) {
                    return;
                }
                if (mineFragmentTopAdapter != null) {
                    mineFragmentTopAdapter.setAfterCount(response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }



    @Override
    public void onDestroyView() {
        layoutManager = null;
        delegateAdapter = null;
        if (mineFragmentTopAdapter != null) {
            mineFragmentTopAdapter.destoryHandle();
        }
        if (ivTop != null) {
            ivTop.clearAnimation();
        }
        changeFragmentListener=null;
        super.onDestroyView();
        ButterKnife.unbind(this);
    }



    @OnClick({R.id.iv_setting, R.id.iv_cart,R.id.iv_top})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_setting:
                intent = new Intent(mContext, SettingActivity.class);
                mContext.startActivity(intent);
                stat("V3我的", "设置");
                break;
            case R.id.iv_cart:
                if (Util.loginChecked((Activity) mContext, 999)) {
                    stat("V3购物车入口", "我的购物车");
                     intent = new Intent(mContext, CartActivity.class);
                    mContext.startActivity(intent);
                }
                break;
            case R.id.iv_top:
                recyclerView.smoothScrollToPosition(0);
                break;
        }

    }

    private void stat(String event, String lable) {
        StatService.onEvent(mContext, event, lable);
        TCAgent.onEvent(mContext, event, lable);
    }
    @Override
    public void clickToPartner() {
        //切换我的个人中心
        if (changeFragmentListener != null) {
            changeFragmentListener.chaneFragment(0);
        }
    }

}
