package com.d2cmall.buyer.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.GDMapActivity;
import com.d2cmall.buyer.activity.PhysicalStoreActivity;
import com.d2cmall.buyer.bean.PhysicalStoreBean;
import com.d2cmall.buyer.holder.PhysicalStoreHolder;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Created by rookie on 2017/8/23.
 */

public class PhysicalStoreAdapter extends DelegateAdapter.Adapter<PhysicalStoreHolder> {

    private LayoutHelper layoutHelper;
    private Context context;
    private List<PhysicalStoreBean.DataBean.StoresBean.ListBean> list;
    private LatLonPoint latLng;
    private final double EARTH_RADIUS = 6378137.0;
    private LatLonPoint mAim;
    private LatLng mStart;
    private LatLng mEnd;

    public PhysicalStoreAdapter(Context context, LayoutHelper layoutHelper, List<PhysicalStoreBean.DataBean.StoresBean.ListBean> list, LatLonPoint latLng) {
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.list = list;
        this.latLng = latLng;
    }

    public void setLatLng(LatLonPoint latLng){
        this.latLng=latLng;
    }


    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public PhysicalStoreHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_physical_store_item, parent, false);
        return new PhysicalStoreHolder(view);
    }

    @Override
    public void onBindViewHolder(final PhysicalStoreHolder holder, int position) {
        final PhysicalStoreBean.DataBean.StoresBean.ListBean data = list.get(position);
        UniversalImageLoader.displayImage(context, data.getPic(), holder.ivStore);
        holder.tvAddress.setText(data.getAddress());
        holder.tvLookMap.setOnClickListener(new View.OnClickListener() {

            private LatLonPoint mMapPoint;

            @Override
            public void onClick(View v) {

                if (data.getXy() != null && !Util.isEmpty(data.getXy())) {
                    String[] xy;
                    if (data.getXy().contains(",")) {
                        xy = data.getXy().split(",");
                    } else {
                        xy = data.getXy().split("，");
                    }
                    if (xy.length > 1) {
                        float x = Float.valueOf(xy[0]);
                        float y = Float.valueOf(xy[1]);
                        mMapPoint = new LatLonPoint(x, y);
                        new LatLng(mMapPoint.getLongitude(), mMapPoint.getLatitude());
                    }
                }
                Intent intent = new Intent(context, GDMapActivity.class);
                if (latLng == null) {
                    Util.showToast(context, "未定位到信息");
                }
                double[] point = new double[]{latLng.getLatitude(), latLng.getLongitude(), mMapPoint.getLongitude(), mMapPoint.getLatitude()};
                intent.putExtra("point", point);
                context.startActivity(intent);
            }
        });
        holder.tvStoreName.setText(data.getName());
        if (data.getTel() != null && !Util.isEmpty(data.getTel())) {
            holder.tvTel.setText(data.getTel());
        } else {
            holder.tvTel.setText("暂无");
        }
        if (data.getWeixin() != null && !Util.isEmpty(data.getWeixin())) {
            holder.wxLL.setVisibility(View.VISIBLE);
            holder.tvWechat.setText(data.getTel());
        } else {
            holder.wxLL.setVisibility(View.INVISIBLE);
        }
        holder.llPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.isEmpty(data.getTel())) {
                    return;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((PhysicalStoreActivity) context, new String[]{Manifest.permission.CALL_PHONE},
                                Constants.RequestCode.PERMISSION);
                    } else {
                        call(data.getTel().trim());
                    }
                } else {
                    call(data.getTel().trim());
                }
            }
        });

        if (data.getResult() > 0) {
            holder.tvDistance.setVisibility(View.VISIBLE);
            holder.tvUnit.setVisibility(View.VISIBLE);
            holder.tvDistance.setText(data.getResult() + "");
        } else {
            holder.tvDistance.setVisibility(View.GONE);
            holder.tvUnit.setVisibility(View.GONE);
        }
    }

    private void call(String number) {
        Intent call = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + number);
        call.setData(data);
        context.startActivity(call);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
