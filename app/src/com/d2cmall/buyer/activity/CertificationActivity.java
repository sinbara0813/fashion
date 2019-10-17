package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.CertificationAdapter;
import com.d2cmall.buyer.api.MyCollectProductBean;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.CertificationListBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.CertificationRulePop;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

//实名认证列表界面
public class CertificationActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout ptr;
    @Bind(R.id.img_hint)
    ImageView imgHint;
    @Bind(R.id.btn_reload)
    TextView btnReload;
    @Bind(R.id.empty_hint_layout)
    LinearLayout emptyHintLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.tv_add_certification)
    TextView tvAddCertification;
    @Bind(R.id.tv_learn_certification)
    TextView tvLearnCertification;
    @Bind(R.id.empty_certification)
    LinearLayout emptyCertification;
    private CertificationRulePop certificationRulePop;
    private VirtualLayoutManager mLayoutManager;
    private DelegateAdapter delegateAdapter;
    private CertificationAdapter certificationAdapter;
    private List<CertificationListBean.DataBean.CertificationsBean.ListBean> listBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerfitication);
        ButterKnife.bind(this);
        nameTv.setText(getString(R.string.label_certification));
        //CertificationAdapter
        doBusiness();
    }


    private void doBusiness() {
        listBeans=new ArrayList<>();
        mLayoutManager = new VirtualLayoutManager(this);
        certificationAdapter = new CertificationAdapter(this,listBeans);
        recycleView.setLayoutManager(mLayoutManager);
        delegateAdapter = new DelegateAdapter(mLayoutManager, true);
        recycleView.setAdapter(delegateAdapter);
        delegateAdapter.addAdapter(certificationAdapter);
        recycleView.setVisibility(View.VISIBLE);
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                requestCertificationList();
            }
        });
        requestCertificationList();
    }

    private void requestCertificationList() {
        progressBar.setVisibility(View.VISIBLE);
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.CERTIFICATION_LIST);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CertificationListBean>() {


            @Override
            public void onResponse(CertificationListBean certificationListBean) {
                if (isFinishing() || progressBar == null) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                ptr.refreshComplete();
                listBeans.clear();
                if (certificationListBean == null || certificationListBean.getData().getCertifications().getList().size() == 0) {
                    emptyCertification.setVisibility(View.VISIBLE);
                }else{
                    recycleView.setBackgroundColor(getResources().getColor(R.color.bg_color));
                    emptyCertification.setVisibility(View.GONE);
                    listBeans.addAll(certificationListBean.getData().getCertifications().getList());
                }
                if(certificationAdapter!=null){
                    certificationAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (isFinishing() || progressBar == null) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                ptr.refreshComplete();
                setEmptyView(Constants.NET_DISCONNECT);
                Util.showToast(CertificationActivity.this, Util.checkErrorType(error));
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
        super.onBackPressed();
    }

    @OnClick({R.id.back_iv, R.id.btn_reload, R.id.tv_add_certification, R.id.tv_learn_certification})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.btn_reload:
                imgHint.setVisibility(View.GONE);
                btnReload.setVisibility(View.GONE);
                requestCertificationList();
                break;
            case R.id.tv_add_certification://添加实名认证
                startActivityForResult(new Intent(this, AddCertificationActivity.class).putExtra("isDefault",1),Constants.RequestCode.ADD_CERTIFICATION_SUCCESS);
                break;
            case R.id.tv_learn_certification://查看实名认证
                if (certificationRulePop == null) {
                    certificationRulePop = new CertificationRulePop(this);
                }
                certificationRulePop.show(getWindow().getDecorView());
                break;
        }
    }
    private void setEmptyView(int type) {
        recycleView.setBackgroundColor(getResources().getColor(R.color.color_white));
        if (type == Constants.NO_DATA) {
            imgHint.setVisibility(View.VISIBLE);
            imgHint.setImageResource(R.mipmap.icon_empty_default);
        } else {
            btnReload.setVisibility(View.VISIBLE);
            imgHint.setVisibility(View.VISIBLE);
            imgHint.setImageResource(R.mipmap.ic_no_net);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            if(requestCode==Constants.RequestCode.ADD_CERTIFICATION_SUCCESS){
                requestCertificationList();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
