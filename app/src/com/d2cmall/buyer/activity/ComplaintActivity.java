package com.d2cmall.buyer.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.AdBean;
import com.d2cmall.buyer.fragment.Comlaint2Fragment;
import com.d2cmall.buyer.fragment.ComlaintFragment;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.UpdatePop;
import com.flyco.tablayout.SlidingTabLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.d2cmall.buyer.Constants.AD_QUESTION;

/**
 * Created by rookie on 2017/10/16.
 * 反馈投诉页面
 */

public class ComplaintActivity extends BaseActivity {

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
    @Bind(R.id.image_ad)
    ImageView imageAd;
    @Bind(R.id.sliding_tab)
    SlidingTabLayout slidingTab;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    private String[] title = {"反馈", "投诉"};
    private ComlaintFragment comlaintFragment;
    private Comlaint2Fragment comlaintFragment2;
    UpdatePop updatePop;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        ButterKnife.bind(this);
        nameTv.setText("反馈&投诉");
        loadAd();
        imageAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isEmpty(url)) {
                    Util.urlAction(ComplaintActivity.this, url);
                }
            }
        });
        comlaintFragment = new ComlaintFragment();
        comlaintFragment2 = new Comlaint2Fragment();
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);
        slidingTab.setViewPager(viewPager);
    }

    private void loadAd() {
        SimpleApi api = new SimpleApi();
        api.setMethod(BaseApi.Method.GET);
        api.setInterPath(AD_QUESTION);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<AdBean>() {
            @Override
            public void onResponse(AdBean response) {
                if (response.getData() != null && response.getData().getAdResource() != null) {
                    UniversalImageLoader.displayImage(ComplaintActivity.this, response.getData().getAdResource().getPic(), imageAd);
                    url = response.getData().getAdResource().getUrl();
                    imageAd.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                imageAd.setVisibility(View.GONE);
            }
        });
    }

    @OnClick({R.id.back_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
        }
    }

    class TabAdapter extends FragmentPagerAdapter {

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return comlaintFragment;
            } else {
                return comlaintFragment2;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
