package com.d2cmall.buyer.activity;

import android.os.Bundle;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/21 13:39
 * Copyright (c) 2016 d2cmall. All rights reserved.
 * 高德地图展示实体店地点
 */
public class GDMapActivity extends BaseActivity {


    @Bind(R.id.map)
    com.amap.api.maps2d.MapView mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gdmap);
        ButterKnife.bind(this);
        MapView mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        AMap aMap = mapView.getMap();
        double[] points = getIntent().getDoubleArrayExtra("point");
//        绘制marker
        Marker marker = aMap.addMarker(new MarkerOptions()
                .position(new LatLng(points[2], points[3]))
                .draggable(true));
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(points[2], points[3]), 19));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(points[2], points[3]));
        markerOptions.visible(true);
        aMap.addMarker(markerOptions);


    }
}
