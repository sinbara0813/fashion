package com.d2cmall.buyer.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import com.d2cmall.buyer.adapter.MyAddressAdapter;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.AddressListBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.OnAddressDeleteClickListener;
import com.d2cmall.buyer.listener.OnAddressEditClickListener;
import com.d2cmall.buyer.listener.OnItemClickListener;
import com.d2cmall.buyer.util.DialogClickListener;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.TipPop;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.Subscribe;

import static com.d2cmall.buyer.R.layout.activity_receive_address;

/**
 * Created by rookie on 2017/8/30.
 */

public class ReceiveAddressActivity extends BaseActivity implements TipPop.CallBack, OnAddressEditClickListener, OnAddressDeleteClickListener, OnItemClickListener {

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
    @Bind(R.id.tv_wanna_join)
    TextView tvWannaJoin;
    @Bind(R.id.img_hint)
    ImageView imgHint;
    @Bind(R.id.btn_reload)
    TextView btnReload;
    @Bind(R.id.empty_hint_layout)
    LinearLayout emptyHintLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.divide)
    View divide;
    private List<AddressListBean.DataEntity.AddressesEntity.ListEntity> listEntities;
    private AddressListBean.DataEntity.AddressesEntity.ListEntity currentData;
    private int type;//0收货地址列表; 1支付的时候选择收货地址后返回
    private int currentPage = 1;
    private MyAddressAdapter myAddressAdapter;
    private DelegateAdapter delegateAdapter;
    private VirtualLayoutManager virtualLayoutManager;
    private boolean isEnd;
    private boolean isLoad;
    private View contentView;
    private Dialog sureDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_address);
        ButterKnife.bind(this);
        contentView = LayoutInflater.from(this).inflate(activity_receive_address, null);
        if (Util.isLowThanAndroid5()) {
            divide.setVisibility(View.VISIBLE);
        }
        type = getIntent().getIntExtra("type", 0);
        nameTv.setText("收货地址");
        listEntities = new ArrayList<>();
        myAddressAdapter = new MyAddressAdapter(this, listEntities, type, this, this, this);
        virtualLayoutManager = new VirtualLayoutManager(this);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager,true);
        recycleView.setLayoutManager(virtualLayoutManager);
        delegateAdapter.addAdapter(myAddressAdapter);
        recycleView.setAdapter(delegateAdapter);
        progressBar.setVisibility(View.VISIBLE);
        currentPage = 1;
        requestAddressesTask();
    }

    @Override
    public void itemClick(View v, int position) {
        AddressListBean.DataEntity.AddressesEntity.ListEntity listEntity = listEntities.get(position);
        if (listEntity.isdefault()) {
            return;
        }
        for (AddressListBean.DataEntity.AddressesEntity.ListEntity le : listEntities) {
            le.setIsdefault(false);
        }
        listEntity.setIsdefault(true);
        myAddressAdapter.notifyDataSetChanged();

        SimpleApi api = new SimpleApi();
        api.setMethod(BaseApi.Method.POST);
        api.setInterPath(String.format(Constants.DEFAULT_ADDRESS_URL, listEntity.getId()));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                Util.showToast(ReceiveAddressActivity.this, R.string.msg_set_ok);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(ReceiveAddressActivity.this, Util.checkErrorType(error));
            }
        });
    }

    @Override
    public void click(View v, int position) {
        Intent intent = new Intent(ReceiveAddressActivity.this, EditAddressActivity.class);
        intent.putExtra("type", 1);
        intent.putExtra("address", listEntities.get(position));
        startActivity(intent);
    }

    @Override
    public void clickAll(View v, int position) {
        if (type == 1) {//下单那儿
            Intent intent = new Intent();
            intent.putExtra("name", listEntities.get(position).getName());
            intent.putExtra("phone", listEntities.get(position).getMobile());
            intent.putExtra("address", getString(R.string.label_address_format, listEntities.get(position).getProvince(), listEntities.get(position).getCity(),
                    listEntities.get(position).getDistrict(), listEntities.get(position).getStreet()));
            intent.putExtra("province",listEntities.get(position).getProvince());
            intent.putExtra("city",listEntities.get(position).getCity());
            intent.putExtra("district",listEntities.get(position).getDistrict());
            intent.putExtra("street",listEntities.get(position).getStreet());
            intent.putExtra("id", listEntities.get(position).getId());
            intent.putExtra("isDefault", listEntities.get(position).isdefault());
            if (listEntities.get(position).getLatitude()!=null){
                intent.putExtra("lat",listEntities.get(position).getLatitude().doubleValue());
            }
            if (listEntities.get(position).getLongitude()!=null){
                intent.putExtra("lon",listEntities.get(position).getLongitude().doubleValue());
            }
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void clickDelete(View v, int position) {
        AddressListBean.DataEntity.AddressesEntity.ListEntity listEntity = listEntities.get(position);
        currentData = listEntity;
        sureDialog = DialogUtil.showMsgDialog(this, "确定要删除该地址吗?", "确定", R.color.color_black60, "取消", R.color.color_black60, true, new DialogClickListener() {
            @Override
            public void onConfirm() {
                listEntities.remove(currentData);
                myAddressAdapter.notifyDataSetChanged();
                setEmptyView(Constants.NO_DATA);
                requestDeleteAddressTask();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onDismiss() {

            }
        });
        sureDialog.show();
    }

    @Override
    public void sure() {
        listEntities.remove(currentData);
        myAddressAdapter.notifyDataSetChanged();
        setEmptyView(Constants.NO_DATA);
        requestDeleteAddressTask();
    }

    @Override
    public void cancel() {

    }

    private void requestDeleteAddressTask() {
        SimpleApi api = new SimpleApi();
        api.setMethod(BaseApi.Method.POST);
        api.setInterPath(String.format(Constants.DELETE_ADDRESS_URL, currentData.getId()));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                Util.showToast(ReceiveAddressActivity.this, R.string.msg_delete_ok);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(ReceiveAddressActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void requestAddressesTask() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.ADDRESSES_URL);
        api.setP(currentPage);
        isLoad = true;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<AddressListBean>() {
            @Override
            public void onResponse(AddressListBean addressListBean) {
                progressBar.setVisibility(View.GONE);
                if (currentPage == 1) {
                    listEntities.clear();
                }
                int size = addressListBean.getData().getAddresses().getList().size();
                if (size > 0) {
                    emptyHintLayout.setVisibility(View.GONE);
                    listEntities.addAll(addressListBean.getData().getAddresses().getList());
                    recycleView.setVisibility(View.VISIBLE);
                    tvWannaJoin.setVisibility(View.VISIBLE);
                } else {
                    setEmptyView(Constants.NO_DATA);
                    tvWannaJoin.setVisibility(View.VISIBLE);
                }
                myAddressAdapter.notifyDataSetChanged();
                if (!addressListBean.getData().getAddresses().isNext()) {
                    isEnd = true;
                } else {
                    isEnd = false;
                }
                isLoad = false;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                setEmptyView(Constants.NET_DISCONNECT);
                tvWannaJoin.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setEmptyView(int type) {
        if (listEntities.isEmpty()) {
            emptyHintLayout.setVisibility(View.VISIBLE);
            imgHint.setVisibility(View.VISIBLE);
            if (type == Constants.NO_DATA) {
                imgHint.setImageResource(R.mipmap.ic_empty_address);
            } else {
                imgHint.setImageResource(R.mipmap.ic_no_net);
            }
        }
    }

    @OnClick({R.id.back_iv, R.id.tv_wanna_join, R.id.btn_reload, R.id.title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.tv_wanna_join:
                Intent intent = new Intent(this, EditAddressActivity.class);
                if (type==1){//从下单界面进来新增地址 新增完成直接回下单界面
                    intent.putExtra("type",2);
                    startActivityForResult(intent,Constants.RequestCode.ADDRESS);
                }else {
                    intent.putExtra("type", 0);
                    startActivity(intent);
                }
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                break;
            case R.id.btn_reload:
                break;
            case R.id.title_right:
//                Intent intent = new Intent(this, EditAddressActivity.class);
//                intent.putExtra("type", 0);
//                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(AddressListBean.DataEntity.AddressesEntity.ListEntity address) {
        boolean exist = false;
        int i = 0;
        for (; i < listEntities.size(); i++) {
            AddressListBean.DataEntity.AddressesEntity.ListEntity le = listEntities.get(i);
            if (le.getId() == address.getId()) {
                exist = true;
                break;
            }
        }
        if (address.isIsdefault()) {
            for (AddressListBean.DataEntity.AddressesEntity.ListEntity le : listEntities) {
                le.setIsdefault(false);
            }
        }
        if (exist) {
            listEntities.set(i, address);
        }
        myAddressAdapter.notifyDataSetChanged();
        setEmptyView(Constants.NO_DATA);
    }

    @Subscribe
    public void onEvent(GlobalTypeBean globalTypeBean) {
        if (globalTypeBean.getType() == Constants.GlobalType.REFRESH_ADDRESSES) {
            progressBar.setVisibility(View.VISIBLE);
            currentPage = 1;
            requestAddressesTask();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK&&requestCode==Constants.RequestCode.ADDRESS){
            Intent intent = new Intent();
            intent.putExtra("name", data.getStringExtra("name"));
            intent.putExtra("phone", data.getStringExtra("phone"));
            intent.putExtra("address", data.getStringExtra("address"));
            intent.putExtra("id", data.getLongExtra("id", 0));
            intent.putExtra("isDefault", data.getBooleanExtra("isDefault",false));
            intent.putExtra("lat",data.getDoubleExtra("lat",0));
            intent.putExtra("lon",data.getDoubleExtra("lon",0));
            intent.putExtra("province",data.getStringExtra("province"));
            intent.putExtra("city",data.getStringExtra("city"));
            intent.putExtra("district",data.getStringExtra("district"));
            intent.putExtra("street",data.getStringExtra("street"));
            setResult(RESULT_OK, intent);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
