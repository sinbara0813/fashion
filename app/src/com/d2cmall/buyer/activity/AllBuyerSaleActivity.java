package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.TabPagerAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.PartnerMemberBean;
import com.d2cmall.buyer.fragment.BuyerSaleFragment;
import com.d2cmall.buyer.fragment.SaleDetailFragment;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AllBuyerSaleActivity extends BaseActivity {


    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.rb_direct)
    RadioButton rbDirect;
    @Bind(R.id.rb_indirect)
    RadioButton rbIndirect;
    @Bind(R.id.rb_dm)
    RadioButton rbDm;
    @Bind(R.id.radio_group)
    RadioGroup radioGroup;
    @Bind(R.id.rd_buttons)
    RelativeLayout rdButtons;
    @Bind(R.id.sliding_tab)
    SlidingTabLayout slidingTab;
    @Bind(R.id.top_ll)
    RelativeLayout topLl;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    private List<Fragment> fragments;
    private List<String> titles;
    private TabPagerAdapter tabPagerAdapter;
    private PartnerMemberBean.DataBean.PartnerBean partnerBean;
    public int tempType = 0;//在订单类型选择时用来判断选择结果是否与上次一样,一样则不用通知子Fragment

    private SaleDetailFragment.ChangeOrderTypeListener changeOrderTypeListener;
    private ArrayList<RadioButton> radioButtons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_sale_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
            partnerBean = Session.getInstance().getPartnerFromFile(this);
            radioButtons = new ArrayList<>();
            radioButtons.add(rbDirect);
            radioButtons.add(rbIndirect);
            radioButtons.add(rbDm);
            if (partnerBean == null) {
                loadPartnerData();
                return;
            }
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.rb_direct:    //零售
                            if (tempType != 0) {             //点击前后订单类型一致则不发
                                if (changeOrderTypeListener != null) {
                                    changeOrderTypeListener.orderTypeChange(0);
                                }
                                tempType = 0;
                                changeTextStatus(checkedId);
                            }
                            break;
                        case R.id.rb_indirect:  //团队
                            if (tempType != 1) {              //点击前后订单类型一直则不发
                                if (changeOrderTypeListener != null) {
                                    changeOrderTypeListener.orderTypeChange(1);
                                }
                                tempType = 1;
                                changeTextStatus(checkedId);
                            }
                            break;
                        case R.id.rb_dm:        //DM
                            if (tempType != 2) {                //点击前后订单类型一直则不发
                                if (changeOrderTypeListener != null) {
                                    changeOrderTypeListener.orderTypeChange(2);
                                }
                                tempType = 2;
                                changeTextStatus(checkedId);
                            }
                            break;
                    }
                }
            });
            changeTextStatus(rbDirect.getId());//初始化选中radiobutton的字体
            if (partnerBean != null && partnerBean.getLevel() == 2) {
                rbDirect.setText("售货明细");
            } else {          //初始化选择订单类型的pop
                if (partnerBean.getLevel() == 1) {
                    rbDm.setVisibility(View.VISIBLE);
                    rbIndirect.setVisibility(View.VISIBLE);
                    rbIndirect.setText("买手");
                } else if (partnerBean.getLevel() == 0) {
                    rbIndirect.setVisibility(View.VISIBLE);
                }
            }
            fragments = new ArrayList<>();
            titles = new ArrayList<>();
            titles.add("全部");
            titles.add("交易中");
            titles.add("已完成");
            titles.add("已关闭");
            fragments.add(BuyerSaleFragment.newInstance(0,true));//全部
            fragments.add(BuyerSaleFragment.newInstance(1,true));//交易中
            fragments.add(BuyerSaleFragment.newInstance(8,true));//已完成
            fragments.add(BuyerSaleFragment.newInstance(-1,true));//已关闭
        setChangeOrderTypeListener((BuyerSaleFragment) fragments.get(0));
        tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(tabPagerAdapter);
        slidingTab.setViewPager(viewPager);
        tabPagerAdapter.notifyDataSetChanged();
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                AllBuyerSaleActivity.this.setChangeOrderTypeListener((BuyerSaleFragment) fragments.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void loadPartnerData() {       //1.网络异常重新加载数据 2.去提现界面提现成功
        SimpleApi simpleApi = new SimpleApi();
        simpleApi.setInterPath(Constants.GET_PARTNER_CENTER_URL);
        D2CApplication.httpClient.loadingRequest(simpleApi, new BeanRequest.SuccessListener<PartnerMemberBean>() {
            @Override
            public void onResponse(PartnerMemberBean partnerInfoBean) {
                if (!isFinishing()) {
                    return;
                }
                partnerBean = partnerInfoBean.getData().getPartner();
                    Session.getInstance().savePartnerToFile(AllBuyerSaleActivity.this, partnerBean);
                    initView();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(AllBuyerSaleActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void changeTextStatus(int checkedId) {
        for (int i = 0; i <radioButtons.size() ; i++) {
            if(radioButtons.get(i).getId()==checkedId){
                radioButtons.get(i).setTextColor(getResources().getColor(R.color.color_black85));
                radioButtons.get(i).setTextSize(18);
            }else{
                radioButtons.get(i).setTextColor(getResources().getColor(R.color.color_black30));
                radioButtons.get(i).setTextSize(14);
            }
        }
    }
    public void setChangeOrderTypeListener(SaleDetailFragment.ChangeOrderTypeListener changeOrderTypeListener) {
        this.changeOrderTypeListener = changeOrderTypeListener;
    }

    public interface ChangeOrderTypeListener {
        void orderTypeChange(int type);
    }

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }
}
