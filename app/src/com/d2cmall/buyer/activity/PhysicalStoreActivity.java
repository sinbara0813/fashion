package com.d2cmall.buyer.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.PhysicalStoreAdapter;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.PhysicalStoreApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.PhysicalCityBean;
import com.d2cmall.buyer.bean.PhysicalStoreBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.OnItemClickListener;
import com.d2cmall.buyer.util.LoctionUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PhysicalPop;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import static com.d2cmall.buyer.Constants.PHYSICAL_CITY;
import static com.d2cmall.buyer.Constants.PHYSICAL_LIST_URL;

/**
 * Created by rookie on 2017/8/23.
 * 实体店页面
 */

public class PhysicalStoreActivity extends BaseActivity implements AMapLocationListener, OnItemClickListener {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.title_layout)
    LinearLayout titleLayout;
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout ptr;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.error_image)
    ImageView errorImage;
    @Bind(R.id.iv_hide_pop)
    ImageView ivHidePop;
    @Bind(R.id.ll_select_city)
    LinearLayout llSelectCity;
    @Bind(R.id.just_transparent)
    View justTransparent;

    private int popOutHeight = 112;
    private int position = 0;
    private List<String> titles;
    private String city = "";
    private PhysicalStoreAdapter physicalStoreAdapter;
    private List<PhysicalStoreBean.DataBean.StoresBean.ListBean> list;
    int page = 1;
    boolean hasNext;
    int count = 0;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    public AMapLocationClientOption mLocationOption = null;
    private final double EARTH_RADIUS = 6378137.0;
    LatLonPoint point;
    private boolean hasLocation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_store);
        ButterKnife.bind(this);
        recycleView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        titles = new ArrayList<>();
        titles.add(0, "全部");
        initListener();
        doBusiness();
    }

    private void startLocation() {
        //初始化client
        mLocationClient = new AMapLocationClient(this);
        //设置定位参数
        mLocationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        mLocationClient.setLocationListener(this);
        mLocationClient.startLocation();
    }

    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        // mOption.setLocationMode(AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(Integer.MAX_VALUE);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

    private void initListener() {
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (point != null) {
                    page = 1;
                    loadData();
                }
            }
        });
    }


    public void doBusiness() {
        list = new ArrayList<>();
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(PhysicalStoreActivity.this);
        recycleView.setLayoutManager(virtualLayoutManager);
        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);
        recycleView.setAdapter(delegateAdapter);
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setMarginTop(ScreenUtil.dip2px(16));
        linearLayoutHelper.setMarginBottom(ScreenUtil.dip2px(16));
        physicalStoreAdapter = new PhysicalStoreAdapter(PhysicalStoreActivity.this, linearLayoutHelper, list, point);
        LinkedList<DelegateAdapter.Adapter> adapters = new LinkedList<>();
        adapters.add(physicalStoreAdapter);
        delegateAdapter.setAdapters(adapters);
        loadCity();
        if (LoctionUtil.checklocationpermiss(this)){
            startLocation();
        }
        loadData();
    }

    private void loadCity() {
        SimpleApi api = new SimpleApi();
        api.setMethod(BaseApi.Method.GET);
        api.setInterPath(PHYSICAL_CITY);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<PhysicalCityBean>() {
            @Override
            public void onResponse(PhysicalCityBean response) {
                if (response.getData().getProvinces() != null && response.getData().getProvinces().size() > 0) {
                    titles.addAll(response.getData().getProvinces());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(PhysicalStoreActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void loadData() {
        PhysicalStoreApi api = new PhysicalStoreApi();
        api.setPageNumber(page);
        if (!Util.isEmpty(city)) {
            api.setProvince(city);
        }
        api.setInterPath(PHYSICAL_LIST_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<PhysicalStoreBean>() {
            @Override
            public void onResponse(PhysicalStoreBean response) {
                ptr.refreshComplete();
                if (page == 1) {
                    list.clear();
                }
                hasNext = response.getData().getStores().isNext();
                if (response.getData().getStores().getList() != null && response.getData().getStores().getList().size() > 0) {
                    if (hasLocation) {
                        list.addAll(checkList(response.getData().getStores().getList()));
                    } else {
                        list.addAll(response.getData().getStores().getList());
                    }
                }
                physicalStoreAdapter.notifyDataSetChanged();
                recycleView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                errorImage.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ptr.refreshComplete();
                recycleView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                errorImage.setVisibility(View.VISIBLE);
                errorImage.setImageResource(R.mipmap.ic_no_net);
                Util.showToast(PhysicalStoreActivity.this, Util.checkErrorType(error));
            }
        });
    }

    public List<PhysicalStoreBean.DataBean.StoresBean.ListBean> checkList(List<PhysicalStoreBean.DataBean.StoresBean.ListBean> list) {
        int size = list.size();
        LatLonPoint mAim;
        LatLng mStart;
        LatLng mEnd;
        for (int i = 0; i < size; i++) {
            if (list.get(i).getXy() != null && !Util.isEmpty(list.get(i).getXy())) {
                String[] xy;
                if (list.get(i).getXy().contains(",")) {
                    xy = list.get(i).getXy().split(",");
                } else {
                    xy = list.get(i).getXy().split("，");
                }
                if (xy.length > 1) {
                    float x = Float.valueOf(xy[0]);
                    float y = Float.valueOf(xy[1]);
                    mAim = new LatLonPoint(x, y);
                    double distance = gps2m(point.getLatitude(), point.getLongitude(), mAim.getLatitude(), mAim.getLongitude());
                    mStart = new LatLng(point.getLatitude(), point.getLongitude());
                    mEnd = new LatLng(mAim.getLongitude(), mAim.getLatitude());
                    distance = AMapUtils.calculateLineDistance(mStart, mEnd) / 1000;
                    int intValue = (int) ((distance + 0.05) * 10);
                    list.get(i).setResult((double) intValue / 10);
                }
            }
        }
        Collections.sort(list, new Comparator<PhysicalStoreBean.DataBean.StoresBean.ListBean>() {
            @Override
            public int compare(PhysicalStoreBean.DataBean.StoresBean.ListBean o1, PhysicalStoreBean.DataBean.StoresBean.ListBean o2) {
                return (int) (o1.getResult() - o2.getResult());
            }
        });
        return list;
    }

    private double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
        double radLat1 = (lat_a * Math.PI / 180.0);
        double radLat2 = (lat_b * Math.PI / 180.0);
        double a = radLat1 - radLat2;
        double b = (lng_a - lng_b) * Math.PI / 180.0;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s / 1000;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient!=null){
            mLocationClient.stopLocation();//停止定位
            mLocationClient.onDestroy();//销毁定位客户端。
        }
    }


    @OnClick({R.id.back_iv, R.id.title_right, R.id.iv_hide_pop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.title_right:
                PhysicalPop pop = new PhysicalPop(this, llSelectCity, 112, this, titleRight);
                pop.setData(titles, position);
                pop.setHeight(112);
                titleRight.setVisibility(View.GONE);
                llSelectCity.setVisibility(View.VISIBLE);
                pop.show(justTransparent);
                break;
            case R.id.iv_hide_pop:
                llSelectCity.setVisibility(View.GONE);
                titleRight.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                count++;
                //可在其中解析amapLocation获取相应内容。
                if (count <= 1) {
                    double x = aMapLocation.getLatitude();
                    double y = aMapLocation.getLongitude();
                    point = new LatLonPoint(x, y);
                    physicalStoreAdapter.setLatLng(point);
                    hasLocation=true;
                    loadData();
                    progressBar.setVisibility(View.GONE);
                }
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Constants.RequestCode.PERMISSION) {
            if (grantResults.length>0&&(grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                startLocation();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void itemClick(View v, int position2) {
        position = position2;
        if (titles.size() > 0) {
            if (titles.get(position2).equals("全部")) {
                titleRight.setText("全部");
                city = "";
                page = 1;
                titleRight.setVisibility(View.VISIBLE);
                loadData();
            } else {
                titleRight.setText(titles.get(position2));
                city = titles.get(position2);
                titleRight.setVisibility(View.VISIBLE);
                page = 1;
                loadData();
            }
        }
    }
}
