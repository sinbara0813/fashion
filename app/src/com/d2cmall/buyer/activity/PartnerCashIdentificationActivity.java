package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.ElectricSignApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.api.UpDateGongmaoApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.ElectricSignBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.PartnerMemberBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.AddressPop;
import com.d2cmall.buyer.widget.ClearEditText;
import com.d2cmall.buyer.widget.ProvinceCityPop;
import com.d2cmall.buyer.widget.SingleSelectPop;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

//买手提现认证
public class PartnerCashIdentificationActivity extends BaseActivity implements AddressPop.CallBack, ProvinceCityPop.CallBack, SingleSelectPop.CallBack {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.tag)
    View tag;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.et_name)
    ClearEditText etName;
    @Bind(R.id.et_idcard_code)
    ClearEditText etIdcardCode;
    @Bind(R.id.tv_bank_name)
    TextView tvBankName;
    @Bind(R.id.tv_bank_address)
    TextView tvBankAddress;
    @Bind(R.id.et_bank_account_sub)
    ClearEditText etBankAccountSub;
    @Bind(R.id.et_bank_account)
    ClearEditText etBankAccount;
    @Bind(R.id.tv_apply)
    TextView tvApply;
    @Bind(R.id.scroll_view)
    ScrollView scrollView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    PartnerMemberBean.DataBean.PartnerBean partnerBean;
    private String provinceStrs;
    private ProvinceCityPop mPop;
    private String provinceName;
    private String cityName;
    private String countyName;
    private SingleSelectPop singleSelectPop;
    private boolean isChecked;
    private boolean isOpendGongmao;//是否打开过工猫
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_partner_cash_identification);
        ButterKnife.bind(this);
        nameTv.setText("提现认证");
        String[] titles = getResources().getStringArray(R.array.banks);
        singleSelectPop = new SingleSelectPop(this, titles);
        singleSelectPop.setCallBack(this);
        loadData();
    }
    //用户在电签之前修改或者填写信息回到这个界面的时候刷新一下
    private void refreshPartnerInfo() {
        SimpleApi simpleApi = new SimpleApi();
        simpleApi.setInterPath(Constants.GET_PARTNER_CENTER_URL);
        D2CApplication.httpClient.loadingRequest(simpleApi, new BeanRequest.SuccessListener<PartnerMemberBean>() {
            @Override
            public void onResponse(PartnerMemberBean partnerInfoBean) {
                if(isFinishing()){
                    return;
                }
                isOpendGongmao=false;
                partnerBean = partnerInfoBean.getData().getPartner();
                Session.getInstance().savePartnerToFile(PartnerCashIdentificationActivity.this, partnerBean);
                initCashInfo(partnerBean);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(PartnerCashIdentificationActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);
        SimpleApi simpleApi = new SimpleApi();
        simpleApi.setInterPath(Constants.GET_PARTNER_CENTER_URL);
        D2CApplication.httpClient.loadingRequest(simpleApi, new BeanRequest.SuccessListener<PartnerMemberBean>() {
            @Override
            public void onResponse(PartnerMemberBean partnerInfoBean) {
                progressBar.setVisibility(View.GONE);
                partnerBean = partnerInfoBean.getData().getPartner();
                initCashInfo(partnerBean);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(PartnerCashIdentificationActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void initCashInfo(PartnerMemberBean.DataBean.PartnerBean partnerInfoBean) {
        if(partnerInfoBean.getContract()==1){   //如果用户已电签不许修改身份证和姓名
            etIdcardCode.setFocusable(false);
            etIdcardCode.setEnabled(false);
            etName.setFocusable(false);
            etName.setEnabled(false);
        }else{
            etIdcardCode.setFocusable(true);
            etIdcardCode.setEnabled(true);
            etName.setFocusable(true);
            etName.setEnabled(true);
        }
        if (!Util.isEmpty(partnerInfoBean.getRealName())) {
            etName.setText(partnerInfoBean.getRealName());
        }
        if (!Util.isEmpty(partnerInfoBean.getIdentityCard())) {
            etIdcardCode.setText(partnerInfoBean.getIdentityCard());
        }
        if (!Util.isEmpty(partnerInfoBean.getBankSn())) {
            etBankAccount.setText(partnerInfoBean.getBankSn());
        }
        if (!Util.isEmpty(partnerInfoBean.getBank())) {
            etBankAccountSub.setText(partnerInfoBean.getBank());
        }
        if (!Util.isEmpty(partnerInfoBean.getRegion())) {
            tvBankAddress.setText(partnerInfoBean.getRegion());
        }
        if (!Util.isEmpty(partnerInfoBean.getBankType())) {
            tvBankName.setText(partnerInfoBean.getBankType());
        }
    }

    @Override
    protected void onResume() { //读取省市区信息
        if(isOpendGongmao){//用户打开过工猫电签网页刷新下用户信息,防止银行卡等关键信息显示错误
            refreshPartnerInfo();
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    InputStream input = getResources().openRawResource
                            (R.raw.area);
                    provinceStrs = Util.readStreamToString(input);
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        Util.onResume(this);
        super.onResume();
    }

    @OnClick({R.id.back_iv, R.id.tv_bank_name, R.id.tv_bank_address, R.id.tv_apply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.tv_bank_name:
                hideKeyboard(null);
                singleSelectPop.show(getWindow().getDecorView(), tvBankName);
                break;
            case R.id.tv_bank_address:
                hideKeyboard(null);
                if (mPop == null) {
                    mPop = new ProvinceCityPop(this, provinceStrs);
                    mPop.setCallBack(this);
                    if (!Util.isEmpty(provinceName) && !Util.isEmpty(cityName)) {
                        mPop.refreshDataByProvinceCity(provinceName, cityName);
                    }
                }
                mPop.show(getWindow().getDecorView());
                break;
            case R.id.tv_apply:
                if(partnerBean!=null && partnerBean.getContract()==0){
                    requestSubmitPartnerTask(); //未电签,填写信息,并跳网页电签
                }else{
                    requestUpdatePartnerTask();//已电签修改用户银行卡等信息
                }
                break;
        }
    }

    private void requestUpdatePartnerTask() {
        if(Util.isEmpty(tvBankAddress.getText().toString().trim())){
            Util.showToast(this,"请填写开户银行地区");
            return;
        }else  if(Util.isEmpty(etBankAccount.getText().toString().trim())){
            Util.showToast(this,"请填写银行卡号");
            return;
        }else  if(Util.isEmpty(etBankAccountSub.getText().toString().trim())){
            Util.showToast(this,"请填写支行名称");
            return;
        }else  if(Util.isEmpty(tvBankName.getText().toString().trim())){
            Util.showToast(this,"请选择银行名称");
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        UpDateGongmaoApi api = new UpDateGongmaoApi();
        api.setRegion(tvBankAddress.getText().toString().trim());
        api.setRealName(etName.getText().toString().trim());
        api.setIdentityCard(etIdcardCode.getText().toString().trim());
        api.setBankSn(etBankAccount.getText().toString().trim());
        api.setBank(etBankAccountSub.getText().toString().trim());//后台定义的bank是支行
        api.setBankType(tvBankName.getText().toString().trim());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ElectricSignBean>() {
            @Override
            public void onResponse(ElectricSignBean electricSignBean) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(PartnerCashIdentificationActivity.this,"修改成功");
                setResult(RESULT_OK);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(PartnerCashIdentificationActivity.this, Util.checkErrorType(error));
            }
        });
    }


    private void requestSubmitPartnerTask() {
        if(Util.isEmpty(etName.getText().toString().trim())){
            Util.showToast(this,"请填写姓名");
            return;
        }else  if(Util.isEmpty(tvBankAddress.getText().toString().trim())){
            Util.showToast(this,"请填写开户银行地区");
            return;
        }else  if(Util.isEmpty(etIdcardCode.getText().toString().trim())){
            Util.showToast(this,"请填写身份证信息");
            return;
        }else  if(Util.isEmpty(etBankAccount.getText().toString().trim())){
            Util.showToast(this,"请填写银行卡号");
            return;
        }else  if(Util.isEmpty(etBankAccountSub.getText().toString().trim())){
            Util.showToast(this,"请填写支行名称");
            return;
        }else  if(Util.isEmpty(tvBankName.getText().toString().trim())){
            Util.showToast(this,"请选择银行名称");
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        ElectricSignApi api = new ElectricSignApi();
        api.setRegion(tvBankAddress.getText().toString().trim());
        api.setRealName(etName.getText().toString().trim());
        api.setIdentityCard(etIdcardCode.getText().toString().trim());
        api.setBankSn(etBankAccount.getText().toString().trim());
        api.setBank(etBankAccountSub.getText().toString().trim());//后台定义的bank是支行
        api.setBankType(tvBankName.getText().toString().trim());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ElectricSignBean>() {
            @Override
            public void onResponse(ElectricSignBean electricSignBean) {
                progressBar.setVisibility(View.GONE);
                String url = electricSignBean.getData().getUrl();
                try {
                    String signUrl = URLDecoder.decode(url, "utf-8");
                    if(signUrl!=null){
                        Intent intent = new Intent(PartnerCashIdentificationActivity.this, WebActivity.class);
                        intent.putExtra("type", 1);
                        intent.putExtra("url", signUrl);
                        intent.putExtra("isShareGone", true);
                        startActivity(intent);
                        isOpendGongmao=true;
                        EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.PARTNER_OPEN_GONGMAO));
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(PartnerCashIdentificationActivity.this, Util.checkErrorType(error));
            }
        });
    }

    @Override
    public void onEventMainThread(ActionBean bean) {
        super.onEventMainThread(bean);
    }

    @Override
    public void callback(String provinceName, int provinceCode, String cityName, int cityCode, String districtName, int districtCode) {
        provinceName = provinceName;
        cityName = cityName;
        tvBankAddress.setText(getString(R.string.label_address_format3, provinceName, cityName));
    }

    @Override
    public void callback(View trigger, int index, String value) {
        tvBankName.setText(value);
    }
}
