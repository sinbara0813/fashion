package com.d2cmall.buyer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.base.BaseTabFragment;
import com.d2cmall.buyer.bean.FlashSessionListBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.FlashTabView;
import com.d2cmall.buyer.widget.LimitViewPager;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 作者:Created by sinbara on 2019/9/11.
 * 邮箱:hrb940258169@163.com
 */
public class AloneFragment extends BaseTabFragment {

    @Bind(R.id.horizontal_scroll_view)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    LimitViewPager viewPager;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout ptr;

    private FlashSessionListBean flashSessionListBean;
    private List<FlashSessionListBean.DataBean.ListBean> list;
    List<AloneSubFragment> fragmentList;

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alone, container, false);
    }

    @Override
    public void doBusiness() {
        viewPager.setCanScroll(false);
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                boolean is=false;
                if (fragmentList!=null&&fragmentList.size()>viewPager.getCurrentItem()){
                    is=fragmentList.get(viewPager.getCurrentItem()).canRefresh();
                }
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header)&&is;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                list.clear();
                loadSession();
            }
        });
        loadSession();
    }

    private void loadSession(){
        SimpleApi api=new SimpleApi();
        api.interPath="/v3/api/flashpromotion/orphan/session";
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<FlashSessionListBean>() {
            @Override
            public void onResponse(FlashSessionListBean response) {
                ptr.refreshComplete();
                if (response != null) {
                    flashSessionListBean = response;
                    list = response.getData().getList();
                    addTab(response.getData().getList());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ptr.refreshComplete();

            }
        });
    }

    private void addTab(List<FlashSessionListBean.DataBean.ListBean> data){
        if (fragmentList!=null){
            fragmentList.clear();
        }
        fragmentList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            AloneSubFragment flashProductSubFragment = AloneSubFragment.newInstance(data.get(i).getId(), 1,data.get(i).getStartTimeStamp(),data.get(i).getEndTimeStamp());
            fragmentList.add(flashProductSubFragment);
        }
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new MyTabAdapter(getChildFragmentManager(), fragmentList));
        int nowItem = 0;
        for (int i = 0; i < data.size(); i++) {
            FlashTabView flashTabView = new FlashTabView(mContext);
            if (data.size() > 2) {
                if (i == (data.size() - 1)) {
                    flashTabView.setLineVisible(false, false);
                } else {
                    flashTabView.setLineVisible(false, true);
                }

            }
            if (data.size() == 2) {
                //两个场次的时候tab的间距会宽一些,这里通过设置每个tab的宽度来增加间距
                flashTabView.setWidth(ScreenUtil.dip2px(167));
            }
            String topText = list.get(i).getSession();
            if (flashSessionListBean.getData().getCurrentId() == data.get(i).getId()) {
                nowItem = i;
            }
            String bottom;
            if (DateUtil.compareToday(list.get(i).getStartTimeStamp()) == 0) {
                bottom = "";
            } else if (DateUtil.compareToday(list.get(i).getStartTimeStamp()) < 0) {
                bottom = "昨日";
            } else {
                bottom = "明日";
            }
            if (!Util.isEmpty(list.get(i).getSessionName())) {
                bottom = bottom + list.get(i).getSessionName();
            } else {
                bottom = bottom + list.get(i).getStatusName();
            }
            flashTabView.setText(topText, bottom);

            tabLayout.getTabAt(i).setCustomView(flashTabView);
        }
        viewPager.setCurrentItem(nowItem);
    }

    class MyTabAdapter extends FragmentPagerAdapter {

        private List<AloneSubFragment> data;


        public MyTabAdapter(FragmentManager fm, List<AloneSubFragment> data) {
            super(fm);
            this.data = data;
        }

        @Override
        public long getItemId(int position) {
            return flashSessionListBean.getData().getList().get(position).getId();
        }

        @Override
        public Fragment getItem(int position) {
            return data.get(position);
        }

        @Override
        public int getCount() {
            return data.size();
        }
    }
}
