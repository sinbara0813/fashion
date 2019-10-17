package com.d2cmall.buyer.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.FashionListApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.FashionMatchBean;
import com.d2cmall.buyer.bean.MatchDetailBean;
import com.d2cmall.buyer.fragment.ImageFragment;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者:Created by sinbara on 2018/11/20.
 * 邮箱:hrb940258169@163.com
 */

public class LooksDetailActivity extends BaseActivity {

    @Bind(R.id.view_pager)
    ViewPager viewPager;

    private List<ImageFragment> fragments;
    private Adapter adapter;
    private String date;
    private long memberId;
    private int id;
    private String leftDate,rightDate;
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looks_detail);
        ButterKnife.bind(this);
        date=getIntent().getStringExtra("date");
        memberId=getIntent().getLongExtra("memberId",0);
        id=getIntent().getIntExtra("id",0);
        if (Util.isEmpty(date)){
            date="2018-11-23 00:00:00";
        }
        fragments=new ArrayList<>();
        adapter=new Adapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position>=fragments.size()-2){
                    loadData(rightDate,DateUtil.getFutureDate(rightDate,3));
                    rightDate=DateUtil.getFutureDate(rightDate,3);
                }
                if (position==0){
                    loadData(DateUtil.getFutureDate(leftDate,-3),leftDate);
                    leftDate=DateUtil.getFutureDate(leftDate,-3);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
        leftDate=DateUtil.getFutureDate(date,-3);
        rightDate=DateUtil.getFutureDate(date,3);
        loadData(leftDate,rightDate);
    }


    private void loadData(String begin,String end){
        FashionListApi api=new FashionListApi();
        if (memberId!=0){
            api.memberId=memberId;
        }
        api.beginDate= begin;
        api.endDate=end;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<FashionMatchBean>() {
            @Override
            public void onResponse(FashionMatchBean response) {
                if (response.getData().getMyWardrobeCollocations()!=null&&response.getData().getMyWardrobeCollocations().getList()!=null
                        &&response.getData().getMyWardrobeCollocations().getList().size()>0){
                    Collections.sort(response.getData().getMyWardrobeCollocations().getList(), new Comparator<FashionMatchBean.DataBean.MyWardrobeCollocationsBean.ListBean>() {
                        @Override
                        public int compare(FashionMatchBean.DataBean.MyWardrobeCollocationsBean.ListBean o1, FashionMatchBean.DataBean.MyWardrobeCollocationsBean.ListBean o2) {
                            return o1.getTransTime()<o2.getTransTime()?-1:1;
                        }
                    });
                    addFragment(response.getData().getMyWardrobeCollocations().getList());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(LooksDetailActivity.this,Util.checkErrorType(error));
            }
        });
    }

    private void addFragment(List<FashionMatchBean.DataBean.MyWardrobeCollocationsBean.ListBean> listBean){
        List<MatchDetailBean> list=new ArrayList<>();
        int size=listBean.size();
        for (int i=0;i<size;i++){
            int imageNum=listBean.get(i).getPics().size();
            if (i!=0&&!listBean.get(i-1).getTransactionTime().substring(0,10).equals(listBean.get(i).getTransactionTime().substring(0,10))){
                ImageFragment imageFragment=ImageFragment.newInstance(list,memberId>0?false:true,id);
                fragments.add(imageFragment);
                list=new ArrayList<>();
                if (listBean.get(i).getTransactionTime().substring(0,10).equals(date.substring(0,10))){
                    currentIndex=fragments.size();
                }
            }
            MatchDetailBean detailBean;
            for (int j=0;j<imageNum;j++){
                detailBean=new MatchDetailBean();
                detailBean.temp=listBean.get(i).getTemp();
                detailBean.city=listBean.get(i).getCity();
                detailBean.createDate=listBean.get(i).getTransactionTime();
                detailBean.description=listBean.get(i).getDescription();
                detailBean.weather=listBean.get(i).getWeather();
                detailBean.pic=listBean.get(i).getPics().get(j);
                detailBean.id=listBean.get(i).getId();
                detailBean.video=listBean.get(i).getVideo();
                list.add(detailBean);
            }
            if (i==size-1){
                ImageFragment imageFragment=ImageFragment.newInstance(list,memberId>0?false:true,id);
                fragments.add(imageFragment);
            }
        }
        adapter.notifyDataSetChanged();
        viewPager.setCurrentItem(currentIndex);
    }

    public void refresh(){
        int size=fragments.size();
        int destroyIndex=-1;
        for (int i=0;i<size;i++){
            if (fragments.get(i).isDestroy){
                destroyIndex=i;
                break;
            }
        }
        if (destroyIndex>=0){
            fragments.remove(destroyIndex);
        }
        if (fragments.size()==0){
            finish();
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    public static class Adapter extends FragmentStatePagerAdapter{

        private List<ImageFragment> fragments;

        public Adapter(FragmentManager fm,List<ImageFragment> fragments) {
            super(fm);
            this.fragments=fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }

}
