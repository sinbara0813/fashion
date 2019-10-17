package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.CountryAdapter;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.CountryBean;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.headerview.StickyListHeadersListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择国家和地区页面
 * Author: rookie
 * Copyright (c) 2017 d2cmall. All rights reserved.
 */
public class CountryCodeActivity extends BaseActivity implements CountryAdapter.OnItemClickListener {

    @Bind(R.id.listView)
    StickyListHeadersListView listView;
    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    private CountryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country1_code);
        ButterKnife.bind(this);
        doBusiness();
    }
    public void doBusiness() {
        initTitle();
        adapter = new CountryAdapter(this);
        listView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        getCountryFile();
    }

    private void initTitle() {
        nameTv.setText(getResources().getString(R.string.label_country_code));
    }

    private void getCountryFile() {
        String jsonStr = Util.readStreamToString(getResources().openRawResource(R.raw.country));
        Gson gson = new Gson();
        List<CountryBean> countryBeanList = gson.fromJson(jsonStr, new TypeToken<List<CountryBean>>() {
        }.getType());
        adapter.refresh(countryBeanList);
    }

    @Override
    public void OnItemClickListener(CountryBean countryBean, int position) {
        if (countryBean != null) {
            Intent intent = getIntent();
            intent.putExtra("country", countryBean);
            setResult(RESULT_OK, intent);
            super.onBackPressed();
        }
    }


    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }
}
