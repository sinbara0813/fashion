package com.d2cmall.buyer.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.ApplyCashApi;
import com.d2cmall.buyer.api.MonthCashedApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.MinWithdrawBean;
import com.d2cmall.buyer.bean.MonthTaxBean;
import com.d2cmall.buyer.bean.PartnerMemberBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.CashTypePop;
import com.d2cmall.buyer.widget.ClearEditText;
import com.d2cmall.buyer.widget.MeasuredLayout;
import com.d2cmall.buyer.widget.TaxRulePop;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

//分销申请提现
public class ApplyCashActivity extends BaseActivity implements MeasuredLayout.OnKeyboardHideListener, CashTypePop.ChoseTypeListener {
    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.tv_cash_num)
    TextView tvCashNum;
    @Bind(R.id.tv_cashed_num)
    TextView tvCashedNum;
    @Bind(R.id.rl_person_info)
    RelativeLayout rlPersonInfo;
    @Bind(R.id.tv_cash_info_change)
    TextView tvCashInfoChange;
    @Bind(R.id.ll_cash_info)
    LinearLayout llCashInfo;
    @Bind(R.id.et_cash_num)
    ClearEditText etCashNum;
    @Bind(R.id.tv_input_remind)
    TextView tvInputRemind;
    @Bind(R.id.tv_apply)
    TextView tvApply;
    @Bind(R.id.tv_cash_remind)
    TextView tvCashRemind;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.iv_question)
    ImageView ivQuestion;
    @Bind(R.id.ll_cashing)
    LinearLayout llCashing;
    @Bind(R.id.rl_root)
    RelativeLayout rlRoot;
    @Bind(R.id.tv_cash_type_change)
    TextView tvCashTypeChange;
    @Bind(R.id.ll_cash_type)
    LinearLayout llCashType;
    private double cashAmount;//可提现总金额
    private double minWithdraw;
    private Double applyCashNum;
    private PartnerMemberBean.DataBean.PartnerBean mPartnerInfoBean;
    private TaxRulePop taxRulePop;
    private boolean isKeyboardHide = true;
    private CashTypePop cashTypePop;
    private double bankMonthCashed;
    private double walletMonthCashed;
    private double bankMonthTax;
    private double walletMonthTax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        cashAmount = getIntent().getDoubleExtra("cashAmount", 0);
        tvCashNum.setText(Util.getNumberFormat(cashAmount));
        tvCashTypeChange.setText("银行卡");
        initListener();
        loadPartnerInfo();
        loadMonthCashed(null);
        loadMonthCashed(0);
        loadMonthCashed(1);
        requestMinWithdraw();
    }

    private void loadPartnerInfo() {
        progressBar.setVisibility(View.VISIBLE);
        SimpleApi simpleApi = new SimpleApi();
        simpleApi.setInterPath(Constants.GET_PARTNER_CENTER_URL);
        D2CApplication.httpClient.loadingRequest(simpleApi, new BeanRequest.SuccessListener<PartnerMemberBean>() {
            @Override
            public void onResponse(PartnerMemberBean partnerInfoBean) {
                progressBar.setVisibility(View.GONE);
                mPartnerInfoBean = partnerInfoBean.getData().getPartner();
                if (cashAmount == 0) {
                    cashAmount = mPartnerInfoBean.getTotalAmount() - mPartnerInfoBean.getCashAmount() - mPartnerInfoBean.getApplyAmount();
                    tvCashNum.setText(Util.getNumberFormat(cashAmount));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(ApplyCashActivity.this, Util.checkErrorType(error));
            }
        });
    }

    public View getContentView() {
        MeasuredLayout measuredLayout = new MeasuredLayout(this, null, R.layout.activity_apply_cash);
        measuredLayout.setOnKeyboardHideListener(this);
        return measuredLayout;
    }

    //0表示普通,1表示钱包,不传就是全部,后台为了兼容老版本,所以这个接口要拉三次
    private void loadMonthCashed(Integer taxType) {
        progressBar.setVisibility(View.VISIBLE);
        final MonthCashedApi api = new MonthCashedApi();
        api.setTaxType(taxType);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MonthTaxBean>() {
            @Override
            public void onResponse(MonthTaxBean monthTaxBean) {
                progressBar.setVisibility(View.GONE);
                if (api.getTaxType() == null) {
                    tvCashedNum.setText(Util.getNumberFormat(monthTaxBean.getData().getMonthWithdraw()));
                } else if (api.getTaxType() == 0) {//0表示普通
                    bankMonthCashed = monthTaxBean.getData().getMonthWithdraw();
                    bankMonthTax = monthTaxBean.getData().getTaxAmount();
                } else {//1表示钱包
                    walletMonthCashed = monthTaxBean.getData().getMonthWithdraw();
                    walletMonthTax = monthTaxBean.getData().getTaxAmount();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(ApplyCashActivity.this, Util.checkErrorType(error));
            }
        });
    }


    private void requestMinWithdraw() {
        progressBar.setVisibility(View.VISIBLE);
        SimpleApi simpleApi = new SimpleApi();
        simpleApi.setInterPath(Constants.GET_PARTNER_MIN_WITHDRAW_URL);
        D2CApplication.httpClient.loadingRequest(simpleApi, new BeanRequest.SuccessListener<MinWithdrawBean>() {
            @Override
            public void onResponse(MinWithdrawBean minWithdrawbean) {
                progressBar.setVisibility(View.GONE);
                minWithdraw = minWithdrawbean.getData().getMinWithdraw();
                tvInputRemind.setTextColor(getResources().getColor(R.color.color_black60));
                tvInputRemind.setText(getString(R.string.msg_partner_min_withdraw, minWithdraw));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(ApplyCashActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void initListener() {
        rlRoot.setOnTouchListener(new View.OnTouchListener() {//点击界面其它地方EditText失去焦点,隐藏软键盘,计算税
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                rlRoot.setFocusable(true);
                rlRoot.setFocusableInTouchMode(true);
                rlRoot.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etCashNum.getWindowToken(), 0);
                calcTax();
                return false;
            }
        });

        TextWatcher textWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkApplyEnable();
            }
        };
        etCashNum.addTextChangedListener(textWatcher);

    }

    //在软键盘收起时计算税,提现到钱包的税费显示先注释
    private void calcTax() {
        String cashNum = etCashNum.getText().toString().trim();
        if (!Util.isEmpty(cashNum)) {
            if (cashNum.contains(",")) {
                cashNum = cashNum.replaceAll(",", "");
            }
            if (Double.valueOf(cashNum) > cashAmount) {
                Util.showToast(this, "输入金额大于可提现金额");
                etCashNum.setText(Util.getNumberFormat(cashAmount));
                cashNum = String.valueOf(cashAmount);
            }
            double currentCashAmount;
            if ("银行卡".equals(tvCashTypeChange.getText().toString())) {
                currentCashAmount = bankMonthCashed + Double.valueOf(cashNum);
            } else {
                currentCashAmount = walletMonthCashed + Double.valueOf(cashNum);
            }


            double tax = 0;
            //计算本月已提现+本次提现的总税
            if ("银行卡".equals(tvCashTypeChange.getText().toString())) {
//                1、当月第一次提现金额不超过3w，按照0.6%计算，且本月最高提现不能超过3w；2、当月第一次提现超过3w（含），按照3.96%计算，后面也按照该税费计算
                if (bankMonthCashed > 0) {
                    if (bankMonthCashed >= 30000) {
                        tax = (currentCashAmount - bankMonthCashed) * 0.0396;
                    } else {
                        if (currentCashAmount >= 30000) {
                            tvInputRemind.setText("本月提现到银行卡累计金额已超出3w，请重新输入提现金额，可查看提现税费说明");
                            return;
                        } else {
                            tax = (currentCashAmount - bankMonthCashed) * 0.006;
                        }
                    }
                } else {
                    if (currentCashAmount >= 30000) {
                        tax = (currentCashAmount) * 0.0396;
                    } else {
                        tax = (currentCashAmount) * 0.006;
                    }
                }
            } else {//钱包不扣税
//                if (currentCashAmount <= 5000) {
//                    tax = 0;
//                } else if (currentCashAmount <= 8000) {
//                    tax = (currentCashAmount - 5000) * 0.03;
//                } else if (currentCashAmount <= 17000) {
//                    tax = (currentCashAmount - 5000) * 0.1 - 210;
//                } else if (currentCashAmount <= 30000) {
//                    tax = (currentCashAmount - 5000) * 0.2 - 1410;
//                } else if (currentCashAmount <= 40000) {
//                    tax = (currentCashAmount - 5000) * 0.25 - 2660;
//                }else if (currentCashAmount <= 60000) {
//                    tax = (currentCashAmount - 5000) * 0.3 - 4410;
//                }else if (currentCashAmount <= 85000) {
//                    tax = (currentCashAmount - 5000) * 0.35 - 7160;
//                }else {
//                    tax = (currentCashAmount - 5000) * 0.45 - 15160;
//                }
            }

            applyCashNum = Double.valueOf(cashNum);
            if (applyCashNum > cashAmount) {
                applyCashNum = cashAmount;
                etCashNum.setText(String.valueOf(applyCashNum));
            }
            if ("银行卡".equals(tvCashTypeChange.getText().toString())) {
                tvInputRemind.setText(getString(R.string.cash_tax_tip, "本月银行卡累计提现金额为" + Util.getNumberFormat(Double.valueOf(applyCashNum) + bankMonthCashed), Util.getNumberFormat(tax), Util.getNumberFormat(applyCashNum - tax)));
//                if( applyCashNum + bankMonthCashed > 5000){
//                    //显示的是本月扣税为本月已提现+本次提现应交税费-已交税费,实际到账金额应为本次提现金额-总税+已交税
//                    tvInputRemind.setText(getString(R.string.cash_tax_tip, "本月银行卡累计提现金额已大于5000", Util.getNumberFormat(tax - bankMonthTax), Util.getNumberFormat(applyCashNum - tax + bankMonthTax)));
//                }else{
//                    //已提现和本次提现<5000,税展示0
//
//                }
                tvInputRemind.setVisibility(View.VISIBLE);
            } else if (("钱包".equals(tvCashTypeChange.getText().toString()))) {
                tvInputRemind.setVisibility(View.GONE);
//                if(applyCashNum + walletMonthCashed > 5000){
//                    //显示的是本月扣税为本月已提现+本次提现应交税费-已交税费,实际到账金额应为本次提现金额-总税+已交税
//                    tvInputRemind.setText(getString(R.string.cash_tax_tip, "本月钱包累计提现金额已大于5000", Util.getNumberFormat(tax - walletMonthTax), Util.getNumberFormat(applyCashNum - tax + walletMonthTax)));
//                }else{
//                    //已提现和本次提现<5000,税展示0
//                    tvInputRemind.setText(getString(R.string.cash_tax_tip, "本月钱包累计提现金额为" + Util.getNumberFormat(Double.valueOf(applyCashNum) + walletMonthCashed), Util.getNumberFormat(tax), Util.getNumberFormat(applyCashNum - tax)));
//                }
//                tvInputRemind.setVisibility(View.GONE);
            }
//            if ("银行卡".equals(tvCashTypeChange.getText().toString()) && tax - bankMonthTax < 0) {//有情况提更多的钱会交更少的税,修改一下展示文案
//                tvInputRemind.setText("本月银行卡累计提现金额已大于5000,会收取一定税费。实际到账金额为" + Util.getNumberFormat(applyCashNum - tax + bankMonthTax));
//            }

        } else {//当输入框为空的时候展示最低提现金额
            tvInputRemind.setText(getString(R.string.msg_partner_min_withdraw, minWithdraw));
            applyCashNum = 0.0;
        }
    }

    private void checkApplyEnable() {
        if (!Util.isEmpty(etCashNum.getText().toString().trim())) {
            if (mPartnerInfoBean == null) {
                return;
            }
            if (Util.isEmpty(mPartnerInfoBean.getRealName()) || Util.isEmpty(mPartnerInfoBean.getIdentityCard()) || Util.isEmpty(mPartnerInfoBean.getBankSn()) || Util.isEmpty(mPartnerInfoBean.getBank())) {
                tvApply.setBackgroundColor(getResources().getColor(R.color.gray_color));
                tvApply.setEnabled(false);
            } else {
                tvApply.setBackgroundResource(R.mipmap.btn_kaidian_nor);
                tvApply.setEnabled(true);
            }

        } else {
            tvInputRemind.setText(getString(R.string.msg_partner_min_withdraw, minWithdraw));
            tvApply.setBackgroundColor(getResources().getColor(R.color.gray_color));
            tvApply.setEnabled(false);
        }
    }


    @OnClick({R.id.back_iv, R.id.tv_apply, R.id.tv_cash_info_change, R.id.iv_question, R.id.ll_cash_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.ll_cash_type://提现方式
                if (mPartnerInfoBean == null) {
                    return;
                }
                if (cashTypePop == null) {
                    cashTypePop = new CashTypePop(this);
                    cashTypePop.setChoseTypeListener(this);
                }
                String bankSn = mPartnerInfoBean.getBankSn();
                if (!Util.isEmpty(bankSn) && bankSn.length() > 4) {
                    bankSn = bankSn.substring(bankSn.length() - 4);
                }
                String bankInfo = getString(R.string.label_bank_info, mPartnerInfoBean.getBankType(), bankSn);
                cashTypePop.setBankCode(bankInfo);
                cashTypePop.show(getWindow().getDecorView());
                break;
            case R.id.tv_apply:
                if (!isKeyboardHide) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etCashNum.getWindowToken(), 0);
                    return;
                }
                if (mPartnerInfoBean.getContract() == 0) {
                    startActivityForResult(new Intent(ApplyCashActivity.this, PartnerCashIdentificationActivity.class), Constants.RequestCode.CHANGE_CASH_INFO_SUCCESS);
                    return;
                }
                if ("银行卡".equals(tvCashTypeChange.getText().toString())) {
                    applyCash();
                } else {
                    new AlertDialog.Builder(this)
                            .setMessage("当前提现操作会提现到钱包，提现到钱包的金额不可提现、转赠，只能用于D2C平台购物消费，确定提现到钱包？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    applyCash();
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                }

                break;
            case R.id.tv_cash_info_change:
                //编辑提现信息
                startActivityForResult(new Intent(ApplyCashActivity.this, PartnerCashIdentificationActivity.class), Constants.RequestCode.CHANGE_CASH_INFO_SUCCESS);
                break;
            case R.id.iv_question://问号
                if (taxRulePop == null) {
                    taxRulePop = new TaxRulePop(ApplyCashActivity.this);
                }
                taxRulePop.show(getWindow().getDecorView());
                break;
        }
    }


    private void applyCash() {
        llCashType.setEnabled(false);
        if (bankMonthCashed > 0 && bankMonthCashed<30000 && applyCashNum + bankMonthCashed >= 30000) {
            Util.showToast(this, "本月提现到银行卡累计金额已超出3w，请重新输入提现金额，可查看提现税费说明");
            return;
        }
        if (Util.isEmpty(etCashNum.getText().toString().trim())) {
            Util.showToast(this, "请输入提现金额");
            return;
        } else if (applyCashNum <= 0) {
            Util.showToast(this, "请输入正确的提现金额");
            return;
        } else if (applyCashNum < minWithdraw) {
            Util.showToast(this, "提现金额小于最低提现金额");
            return;
        } else if (applyCashNum > cashAmount) {
            Util.showToast(this, "提现金额大于可提现金额");
            return;
        } else {
            ApplyCashApi applyCashApi = new ApplyCashApi();
            applyCashApi.setApplyAmount(applyCashNum);
            if ("银行卡".equals(tvCashTypeChange.getText().toString())) {
                applyCashApi.setPayType("bank");
            } else {
                applyCashApi.setPayType("wallet");
            }
            progressBar.setVisibility(View.VISIBLE);
            D2CApplication.httpClient.loadingRequest(applyCashApi, new BeanRequest.SuccessListener<BaseBean>() {
                @Override
                public void onResponse(BaseBean response) {
                    if (isFinishing() || progressBar == null) {
                        return;
                    }
                    progressBar.setVisibility(View.GONE);
                    llCashType.setEnabled(true);
                    if ("银行卡".equals(tvCashTypeChange.getText().toString())) {
                        Util.showToast(ApplyCashActivity.this, "申请成功,请耐心等待审核~");
                        D2CApplication.mSharePref.putSharePrefString("lastCashWay", "bank");
                    } else {
                        Util.showToast(ApplyCashActivity.this, "提现成功");
                        D2CApplication.mSharePref.putSharePrefString("lastCashWay", "wallet");
                    }

                    EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.PARTNER_APPLY_CASH));
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (progressBar == null) {
                        return;
                    }
                    progressBar.setVisibility(View.GONE);
                    llCashType.setEnabled(true);
                    Util.showToast(ApplyCashActivity.this, Util.checkErrorType(error));
                }
            });

        }

    }

    @Override
    public void onKeyboardHide(boolean hide) {
        isKeyboardHide = hide;
        if (hide) {
            rlRoot.setFocusable(true);
            rlRoot.setFocusableInTouchMode(true);
            rlRoot.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etCashNum.getWindowToken(), 0);
            calcTax();
        }

    }

    @Override
    public void choseType(int type) {
        if (type == 1) {
            tvCashTypeChange.setText("银行卡");
            llCashing.setVisibility(View.GONE);
            calcTax();
        } else if (type == 2) {
            tvCashTypeChange.setText("钱包");
            llCashing.setVisibility(View.VISIBLE);
            tvCashRemind.setText(R.string.label_cash_wallet_tip);
            calcTax();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.RequestCode.CHANGE_CASH_INFO_SUCCESS) {
                loadPartnerInfo();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
