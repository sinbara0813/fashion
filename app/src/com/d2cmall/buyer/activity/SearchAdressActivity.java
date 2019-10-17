package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.BasicRecylerViewAdapter;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.AddressEntity;
import com.d2cmall.buyer.util.LoctionUtil;
import com.d2cmall.buyer.widget.ClearEditText;
import com.d2cmall.buyer.widget.MyRecyclerView;
import com.d2cmall.buyer.widget.RecylerViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.d2cmall.buyer.Constants.RequestCode.SEARCHADDRESS_ACTIVITY_REQUESTCODE;
import static com.d2cmall.buyer.R.id.listView;
import static com.d2cmall.buyer.util.LoctionUtil.LOCATIONPERMISSON_REQUESTCODE;
import static com.zxinsight.MWConfiguration.getContext;

/**
 * 搜索地址列表
 * Author: YH
 * Date: 2017/07/20 10:02
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class SearchAdressActivity extends BaseActivity implements BasicRecylerViewAdapter.ViewBinder<AddressEntity>
        , PoiSearch.OnPoiSearchListener {
    @Bind(R.id.et_search)
    ClearEditText etSearch;

    @Bind(listView)
    MyRecyclerView mRecyclerView;
    private BasicRecylerViewAdapter<AddressEntity> adapter;
    private List<AddressEntity> mList;
    private LoctionUtil mLoctionUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchadress);
        ButterKnife.bind(this);
        LoctionUtil.checklocationpermiss(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mList = new ArrayList<>();
        adapter = new BasicRecylerViewAdapter(this, mList, R.layout.list_item_address1);
        adapter.setViewBinder(this);
        mRecyclerView.setAdapter(adapter);


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0) {
                    mLoctionUtil.query(SearchAdressActivity.this, editable.toString(), SearchAdressActivity.this);
                }
            }
        });
    }


    @Override
    protected void onStart() {
        mLoctionUtil = LoctionUtil.getLoctionUtil(this);
        mLoctionUtil.setIhasCity(new LoctionUtil.IhasCity() {
            @Override
            public String getAddress(String province, String cityName, String street) {
                if (mList.size() < 2 && etSearch.getText().toString().length() == 0) {
                    street = "";
                    mLoctionUtil.query(SearchAdressActivity.this, street, SearchAdressActivity.this);
                }
                return null;
            }
        });
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        mLoctionUtil.destroyLocation();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        mLoctionUtil.stopLocation();
        super.onStop();
    }

    public static String ADDRESS = "address";

    @Override
    public void setViewValue(final RecylerViewHolder holder, final AddressEntity addressEntity, final int position) {
        int color1 = getResources().getColor(R.color.color_black2);
        int color2 = getResources().getColor(R.color.color_black3);
        int visible = View.GONE;
        if (addressEntity.isSelected) {
            color1 = getResources().getColor(R.color.colorAccent);
            color2 = getResources().getColor(R.color.colorAccent);
            visible = View.VISIBLE;
        }
        holder.getImageView(R.id.img_check).setVisibility(visible);
        holder.getTextView(R.id.tv_name).setTextColor(color1);
        holder.getTextView(R.id.tv_address).setTextColor(color2);
        holder.setText(R.id.tv_name, addressEntity.addressName);
        if (position == 0) {
            holder.getView(R.id.tv_address).setVisibility(View.GONE);
            holder.getTextView(R.id.tv_address).setPadding(0, 20, 0, 20);
            holder.getTextView(R.id.tv_address).setTypeface(Typeface.DEFAULT);
            holder.getTextView(R.id.tv_address).setTextSize(getResources().getDimension(R.dimen.small_text_size));
        }
        holder.setText(R.id.tv_address, addressEntity.address);
        holder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (AddressEntity entity : mList) {
                    entity.isSelected = false;
                }
                Intent it = new Intent();
                it.putExtra(ADDRESS, addressEntity);
                setResult(SEARCHADDRESS_ACTIVITY_REQUESTCODE, it);
                addressEntity.isSelected = true;
                adapter.notifyDataSetChanged();
                finish();
            }
        });
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        ArrayList<PoiItem> list = poiResult.getPois();
        if (list != null) {
            mList.clear();
            mList.add(new AddressEntity(getString(R.string.label_sift), true));
            String address = getIntent().getStringExtra(ADDRESS);
            for (PoiItem poiItem : list) {
                AddressEntity addressEntity = new AddressEntity();
                addressEntity.addressName = poiItem.getTitle();
                addressEntity.address = poiItem.getSnippet();
                addressEntity.lat = poiItem.getLatLonPoint().getLatitude();
                addressEntity.lon = poiItem.getLatLonPoint().getLongitude();
                addressEntity.cityName = poiItem.getCityName();
                if (poiItem.getTitle().equals(address)) {
                    addressEntity.isSelected = true;
                    mList.get(0).isSelected = false;
                }
                mList.add(addressEntity);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @OnClick({R.id.btn_cancel})
    public void mclick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                finish();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {
            if (permissions[i].equals("android.permission.ACCESS_COARSE_LOCATION") &&
                    grantResults[i] == -1) {
                Uri packageURI = Uri.parse("package:" + "com.d2cmall.buyer");
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                startActivityForResult(intent, LOCATIONPERMISSON_REQUESTCODE);

            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
