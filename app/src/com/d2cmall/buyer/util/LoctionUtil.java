package com.d2cmall.buyer.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.d2cmall.buyer.Constants;

/**
 * 高德地图util
 * Author: yh
 * Date: 2017/07/17 11:41
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class LoctionUtil implements AMapLocationListener {


    private AMapLocationClient mlocationClient;
    private static LoctionUtil mLoctionUtil;
    private double mLatitude;
    private double mLongitude;
    private String mCity;
    PoiSearch.Query mQuery;
    private IhasCity mIhasCity;
    public static int LOCATIONPERMISSON_REQUESTCODE = 100;

    public interface IhasCity {
        String getAddress(String provinceName, String cityName, String street);

    }

    public static void getLatLonPoint(Context context, String address, String city, GeocodeSearch.OnGeocodeSearchListener listener) {
        //参数一:上下文对象,参数二地址字符串,参数三为搜索城市的中文或者中文全拼，citycode、adcode  参数四为监听回调，在onGeocodeSearched中处理即可
        //LatLonPoint point = geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint();
        GeocodeSearch geocodeSearch = new GeocodeSearch(context);
        GeocodeQuery query = new GeocodeQuery(address, city);
        geocodeSearch.getFromLocationNameAsyn(query);
        geocodeSearch.setOnGeocodeSearchListener(listener);
    }

    public void setIhasCity(IhasCity mIhasCity) {
        this.mIhasCity = mIhasCity;
    }

    public static LoctionUtil getLoctionUtil(Context context) {
        if (mLoctionUtil == null) {
            mLoctionUtil = new LoctionUtil(context.getApplicationContext());
        }
        return mLoctionUtil;
    }

    private LoctionUtil() {
    }

    private LoctionUtil(Context context) {
        if (mlocationClient == null)
            initLocation(context);
    }

    private void initLocation(Context context) {
        //初始化client
        mlocationClient = new AMapLocationClient(context);
        //设置定位参数
        mlocationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        mlocationClient.setLocationListener(this);
        startLocation();
    }

    public void startLocation() {
        if (mlocationClient != null && !mlocationClient.isStarted()) {
            mlocationClient.setLocationOption(getDefaultOption());
            mlocationClient.startLocation();
        }
    }

    public void stopLocation() {
        if (mlocationClient != null && mlocationClient.isStarted()) {
            mlocationClient.stopLocation();
        }

    }

    public void destroyLocation() {
        if (null != mlocationClient) {
            mIhasCity=null;
            mlocationClient.onDestroy();
            mlocationClient = null;
        }
        if (mLoctionUtil != null) {
            mLoctionUtil = null;
        }
    }

    private PoiSearch poiSearch;

    public void query(Context context, String keyWord, PoiSearch.OnPoiSearchListener listener) {
        int pageSize = 50;
        //String type = "060101|060102|060103|061000|061001|061100|061101|170000";
        String type = "生活服务|餐饮服务|购物服务|公司企业|风景名胜|公共设施|住宿服务|地名地址信息";
        if (!Util.isEmpty(keyWord)) {
            type = "";
        }

        mQuery = new PoiSearch.Query(keyWord, type, mCity);

        mQuery.setPageSize(pageSize);
        mQuery.setPageNum(0);
        LatLonPoint lp = new LatLonPoint(mLatitude, mLongitude);
        poiSearch = new PoiSearch(context, mQuery);
        poiSearch.setOnPoiSearchListener(listener);
        poiSearch.setBound(new PoiSearch.SearchBound(lp, 5000));
        poiSearch.searchPOIAsyn();
        mQuery = new PoiSearch.Query(keyWord, type, mCity);


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

    @Override
    public void onLocationChanged(AMapLocation location) {
        if (location != null) {

//            StringBuffer sb = new StringBuffer();
            //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
            if (location.getErrorCode() == 0) {
                if (location.getLongitude() > 0) {
                    mLatitude = location.getLatitude();
                    mLongitude = location.getLongitude();
                }
                if (location.getCity() != null) {
                    mCity = location.getCity();
                }
                if (mIhasCity != null) {
                    if (location.getStreet() != null) {
                        mIhasCity.getAddress(location.getProvince(), location.getCity(), location.getStreet());
                    } else if (location.getPoiName() != null) {
                        mIhasCity.getAddress(location.getProvince(), location.getCity(), location.getPoiName());
                    }
                }
            }
//                sb.append("定位成功" + "\n");
//                sb.append("定位类型: " + location.getLocationType() + "\n");
//                sb.append("经    度    : " + location.getLongitude() + "\n");
//                sb.append("纬    度    : " + location.getLatitude() + "\n");
//                sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
//                sb.append("提供者    : " + location.getProvider() + "\n");
//
//                sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
//                sb.append("角    度    : " + location.getBearing() + "\n");
//                // 获取当前提供定位服务的卫星个数
//                sb.append("星    数    : " + location.getSatellites() + "\n");
//                sb.append("国    家    : " + location.getCountry() + "\n");
//                sb.append("省            : " + location.getProvince() + "\n");
//                sb.append("市            : " + location.getCity() + "\n");
//                sb.append("城市编码 : " + location.getCityCode() + "\n");
//                sb.append("区            : " + location.getDistrict() + "\n");
//                sb.append("区域 码   : " + location.getAdCode() + "\n");
//                sb.append("地    址    : " + location.getAddress() + "\n");
//                sb.append("兴趣点    : " + location.getPoiName() + "\n");
//                sb.append("街道名称 : " + location.getStreet() + "\n");
//                sb.append("aoi   : " + location.getAoiName() + "\n");
//                sb.append("街道牌号   : " + location.getStreetNum() + "\n");
//                //定位完成的时间
////                sb.append("定位时间: " + Utils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
//            } else {
//                //定位失败
//                sb.append("定位失败" + "\n");
//                sb.append("错误码:" + location.getErrorCode() + "\n");
//                sb.append("错误信息:" + location.getErrorInfo() + "\n");
//                sb.append("错误描述:" + location.getLocationDetail() + "\n");
//            }
//
//            String result = sb.toString();
            // Log.e("tag", "result" + result);

//        } else {
//          //  Log.e("tag", "定位失败，loc is null");
        }
    }

    public static boolean checklocationpermiss(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        Constants.RequestCode.PERMISSION);
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

}
