package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.AddCertificationActivity;
import com.d2cmall.buyer.activity.ScanQrCodeActivity;
import com.d2cmall.buyer.activity.WebActivity;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.CertificationListBean;
import com.d2cmall.buyer.holder.CertificationHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.CheckStateChangeListener;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * 实名认证adapter
 * Created by Administrator on 2018/6/4.
 * Description : CertificationAdapter
 */

public class CertificationAdapter extends DelegateAdapter.Adapter<CertificationHolder> {

    private Activity mContext;
    private final List<CertificationListBean.DataBean.CertificationsBean.ListBean> listBeans;

    public CertificationAdapter(Activity mContext, List<CertificationListBean.DataBean.CertificationsBean.ListBean> listBeans) {
        this.mContext = mContext;
        this.listBeans = listBeans;
    }
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setPaddingTop(ScreenUtil.dip2px(16));
        return linearLayoutHelper;
    }

    @Override
    public CertificationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_certification_item, parent, false);
        return new CertificationHolder(view);
    }

    @Override
    public void onBindViewHolder(CertificationHolder holder, final int position) {

        holder.tvName.setText(listBeans.get(position).getRealName());
        holder.tvIdNumber.setText(listBeans.get(position).getIdentityCard());
        holder.checkBox.setCheckColorId(R.mipmap.icon_shopcart_bselected, R.mipmap.icon_shopcart_unbselected);
        if(listBeans.get(position).getIsdefault()==1){
            holder.llTop.setBackgroundResource(R.drawable.sp_roundtop4_8c9699);
            holder.tvName.setTextColor(mContext.getResources().getColor(R.color.color_white));
            holder.tvIdNumber.setTextColor(mContext.getResources().getColor(R.color.color_white));
            holder.checkBox.setChecked(true);
        }else{
            holder.llTop.setBackgroundResource(R.drawable.sp_roundtop4_white);
            holder.tvName.setTextColor(mContext.getResources().getColor(R.color.color_black85));
            holder.tvIdNumber.setTextColor(mContext.getResources().getColor(R.color.color_black85));
            holder.checkBox.setChecked(false);
        }
        if(listBeans.size()==1){
            holder.checkBox.setEnabled(false);
        }else{
            holder.checkBox.setEnabled(true);
        }
        if(position==listBeans.size()-1){
            holder.tvAddCertification.setVisibility(View.GONE);
        }else{
            holder.tvAddCertification.setVisibility(View.GONE);
        }
        holder.tvAddCertification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listBeans.size()<5){
                    mContext.startActivityForResult(new Intent(mContext, AddCertificationActivity.class),Constants.RequestCode.ADD_CERTIFICATION_SUCCESS);
                }else{
                    Util.showToast(mContext,"每人最多可添加5条实名信息");
                }
            }
        });
        holder.checkBox.setOnStatusChangedListener(new CheckStateChangeListener() {
            @Override
            public void onStatusChanged(View v, boolean isChecked) {
                if(isChecked){
                    requestDefault(listBeans.get(position).getId(),position);
                }
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setMessage("确定删除?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteCertification(listBeans.get(position).getId(),position);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddCertificationActivity.class)
                        .putExtra("certificationId", listBeans.get(position).getId())
                        .putExtra("idNumber", listBeans.get(position).getIdentityCard())
                        .putExtra("name", listBeans.get(position).getRealName())
                        .putExtra("isUpload", listBeans.get(position).getIsUpload());
                mContext.startActivityForResult(intent,Constants.RequestCode.ADD_CERTIFICATION_SUCCESS);
            }
        });
    }

    private void requestDefault(long id, final int position) {
        SimpleApi api = new SimpleApi();
        api.setMethod(BaseApi.Method.POST);
        api.setInterPath(String.format(Constants.CERTIFICATION_DEFAULT,id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                for (int i = 0; i <listBeans.size() ; i++) {
                    if(position==i){
                        listBeans.get(i).setIsdefault(1);
                    }else{
                        listBeans.get(i).setIsdefault(0);
                    }
                }
                notifyDataSetChanged();
                Util.showToast(mContext,"设置成功");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(mContext,Util.checkErrorType(error));
            }
        });
    }

    private void deleteCertification(long id, final int position) {
        SimpleApi api = new SimpleApi();
        api.setMethod(BaseApi.Method.POST);
        api.setInterPath(String.format(Constants.CERTIFICATION_DELETE,id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                Util.showToast(mContext,"删除成功");
                listBeans.remove(position);
                notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(mContext,Util.checkErrorType(error));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBeans==null?0:listBeans.size();
    }
}
