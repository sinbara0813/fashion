package com.d2cmall.buyer.activity;

import android.os.Bundle;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.PaySuccessAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.AdShareBean;
import com.d2cmall.buyer.bean.CartRecommendBean;
import com.d2cmall.buyer.bean.PartnerMemberBean;
import com.d2cmall.buyer.bean.ProductDetailBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.binder.PaySuccessTopBinder;
import com.d2cmall.buyer.holder.PaySuccessTopHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.AccountSafePop;
import com.d2cmall.buyer.widget.OpenMsgPushPop;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2017/8/29.
 */

public class PaySuccessActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;

    Animation mShowAction;
    Animation mHiddenAction;
    private DelegateAdapter delegateAdapter;
    private List<ProductDetailBean.DataBean.RecommendProductsBean> list;
    private BaseVirtualAdapter<PaySuccessTopHolder> paySuccessTopAdapter;
    private PaySuccessTopBinder binder;
    private PaySuccessAdapter paySuccessAdapter;

    private String orderId; //订单id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);
        ButterKnife.bind(this);
        doBusiness();
    }

    public void doBusiness() {
        ViewCompat.setElevation(titleLayout,0);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        D2CApplication.mSharePref.removeKey(SharePrefConstant.PRODUCT_ID);
        orderId = getIntent().getStringExtra("id");
        list=new ArrayList<>();
        ViewCompat.setElevation(titleLayout, 0);
        nameTv.setVisibility(View.GONE);
        nameTv.setText(getResources().getText(R.string.label_pay_success));
        initAnimation();
        final VirtualLayoutManager virtualLayoutManager=new VirtualLayoutManager(this);
        recycleView.setLayoutManager(virtualLayoutManager);
        delegateAdapter=new DelegateAdapter(virtualLayoutManager,true);
        recycleView.setAdapter(delegateAdapter);
        binder=new PaySuccessTopBinder(this);
        paySuccessTopAdapter=new BaseVirtualAdapter<PaySuccessTopHolder>(binder,1);
        delegateAdapter.addAdapter(paySuccessTopAdapter);
        paySuccessAdapter=new PaySuccessAdapter(this,list);
        delegateAdapter.addAdapter(paySuccessAdapter);
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(virtualLayoutManager.findFirstVisibleItemPosition() > 0){
                    if(nameTv.getVisibility()==View.GONE){
                        ViewCompat.setElevation(titleLayout, ScreenUtil.dip2px(4));
                        nameTv.startAnimation(mShowAction);
                        nameTv.setVisibility(View.VISIBLE);
                    }
                }else {
                    if(nameTv.getVisibility()==View.VISIBLE){
                        ViewCompat.setElevation(titleLayout, 0);
                        nameTv.startAnimation(mHiddenAction);
                        nameTv.setVisibility(View.GONE);
                    }
                }
            }
        });
        loadData();
        loadPartnerData();
        loadAd();
    }

    private void loadAd(){
        SimpleApi api=new SimpleApi();
        api.setInterPath(Constants.GET_ORDER_AD);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<AdShareBean>() {
            @Override
            public void onResponse(AdShareBean response) {
                if (response.getData().getAdResource() != null && !Util.isEmpty(response.getData().getAdResource().getPic())) {
                    if (binder!=null){
                        binder.setAd(response.getData().getAdResource().getPic());
                        binder.setUrl(response.getData().getAdResource().getUrl());
                        delegateAdapter.notifyItemChanged(0);
                    }
                }
            }
        });
    }

    //拉取分销信息如果有将id给member的partnerId(普通用户变分销)
    private void loadPartnerData() {
        UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(this);
        if(user==null || user.getPartnerId()>0){
            return;
        }
        SimpleApi simpleApi = new SimpleApi();
        simpleApi.setInterPath(Constants.GET_PARTNER_CENTER_URL);
        D2CApplication.httpClient.loadingRequest(simpleApi, new BeanRequest.SuccessListener<PartnerMemberBean>() {
            @Override
            public void onResponse(PartnerMemberBean partnerInfoBean) {
                if (!isFinishing()) {
                    return;
                }
                if(partnerInfoBean!=null && partnerInfoBean.getData().getPartner()!=null){
                    UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(PaySuccessActivity.this);
                    Session.getInstance().savePartnerToFile(PaySuccessActivity.this, partnerInfoBean.getData().getPartner());
                    if(user!=null){
                        user.setPartnerId(partnerInfoBean.getData().getPartner().getId());
                        Session.getInstance().saveUserToFile(PaySuccessActivity.this,user);
                    }
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {//在OonCreate,onResume方法中直接使用popWindow会有异常,activity获得焦点之后，证明加载完成了
        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        boolean isOpened = manager.areNotificationsEnabled();
        if(hasFocus){
            Boolean isMsgPushOpen = D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.IS_REMIND_OPEN_MSG, false);
            if(!isMsgPushOpen && !isOpened){
                if (Session.getInstance().getUserFromFile(this)!=null&&Session.getInstance().getUserFromFile(this).isPayDanger()==1){
                    showAccountTip();
                }else {
                    //开启消息推送行为节点
                    D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.IS_REMIND_OPEN_MSG,true);
                    OpenMsgPushPop openMsgPushPop = new OpenMsgPushPop(PaySuccessActivity.this);
                    openMsgPushPop.setContent(getString(R.string.label_pop_focus_logistics));
                    openMsgPushPop.show(getWindow().getDecorView());
                }
            }
        }
        super.onWindowFocusChanged(hasFocus);
    }

    private void showAccountTip(){
        boolean is=D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.IS_SHOW_PAY_PW_TIP,false);
        if (!is){
            D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.IS_SHOW_PAY_PW_TIP,true);
            AccountSafePop safePop=new AccountSafePop(this);
            safePop.setContent(getResources().getString(R.string.label_change_pay_pw),2);
            safePop.show(getWindow().getDecorView());
        }
    }

    private void loadData(){
        if (!Util.isEmpty(orderId)){
            SimpleApi api=new SimpleApi();
            api.setInterPath(String.format(Constants.GET_ORDER_RECOMMEND,orderId,20));
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CartRecommendBean>() {
                @Override
                public void onResponse(CartRecommendBean recommendListBean) {
                    if (recommendListBean.getList() != null && recommendListBean.getList().size() > 0) {
                        list.addAll(recommendListBean.getList());
                        paySuccessAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    private void initAnimation() {
        mShowAction = new AlphaAnimation(0f, 1f);
        mShowAction.setDuration(350);
        mHiddenAction = new AlphaAnimation(1f, 0f);
        mHiddenAction.setDuration(350);
    }

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }
}
