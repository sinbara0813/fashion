package com.d2cmall.buyer.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.ProductConsultAdapter;
import com.d2cmall.buyer.api.InsertConsultApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.ConsultListBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.TitleUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BuyConsultActivity extends BaseActivity {

    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.edit)
    EditText editText;
    @Bind(R.id.sure_tv)
    TextView sureTv;

    private VirtualLayoutManager virtualLayoutManager;
    private DelegateAdapter delegateAdapter;
    private List<ConsultListBean.DataEntity.ConsultsEntity.ListEntity> list=new ArrayList<>();
    private ProductConsultAdapter consultAdapter;
    private Dialog loadingDialog;
    private long productId;
    private String productName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_consult);
        ButterKnife.bind(this);
        initTitle();
        init();
    }

    private void initTitle() {
        TitleUtil.setBack(this);
        TitleUtil.setTitle(this, R.string.label_buy_ask);
    }

    private void loadData() {
        SimpleApi api = new SimpleApi();
        api.setP(1);
        api.setPageSize(20);
        api.setInterPath(String.format(Constants.GET_PRODUCT_CONSULT_LIST, productId));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ConsultListBean>() {
            @Override
            public void onResponse(ConsultListBean response) {
                //hasNext=response.getData().getConsults().isNext();
                if (response.getData().getConsults().getList().size() > 0) {
                    list.addAll(response.getData().getConsults().getList());
                    consultAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(BuyConsultActivity.this,Util.checkErrorType(error));
            }
        });
    }

    private void init() {
        productId = getIntent().getLongExtra("id", 0);
        productName=getIntent().getStringExtra("name");
        virtualLayoutManager = new VirtualLayoutManager(this);
        recycleView.setLayoutManager(virtualLayoutManager);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);
        recycleView.setAdapter(delegateAdapter);
        consultAdapter = new ProductConsultAdapter(this, list);
        delegateAdapter.addAdapter(consultAdapter);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Util.isEmpty(s.toString())) {
                    sureTv.setEnabled(false);
                    sureTv.setBackgroundColor(Color.parseColor("#c8c8c8"));
                } else {
                    sureTv.setEnabled(true);
                    sureTv.setBackgroundColor(Color.parseColor("#262626"));
                }
            }
        });
        sureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publish();
            }
        });
        loadingDialog = DialogUtil.createLoadingDialog(this);
        loadData();
    }


    public void publish() {
        if (Util.isEmpty(editText.getText().toString())) {
            Util.showToast(this, R.string.msg_buy_consult);
            return;
        }
        InsertConsultApi api = new InsertConsultApi();
        api.setProductId(productId);
        api.setProductName(productName);
        api.setQuestion(editText.getText().toString());
        loadingDialog.show();
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                editText.setText("");
                loadingDialog.dismiss();
                Util.showToast(BuyConsultActivity.this, R.string.msg_send_consult_ok);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                Util.showToast(BuyConsultActivity.this, Util.checkErrorType(error));
            }
        });
    }

    @Override
    protected void onResume() {
        Util.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        Util.onPause(this);
        super.onPause();
    }
}
