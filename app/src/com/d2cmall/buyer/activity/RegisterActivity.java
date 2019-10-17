package com.d2cmall.buyer.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Html;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.CheckPhoneExistApi;
import com.d2cmall.buyer.api.LogApi;
import com.d2cmall.buyer.api.RegisterApi;
import com.d2cmall.buyer.api.SendAudioApi;
import com.d2cmall.buyer.api.SendCodeApi;
import com.d2cmall.buyer.api.SendDeviceApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.CartCountBean;
import com.d2cmall.buyer.bean.CountryBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.http.HttpError;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.LogDB;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ClearEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.igexin.sdk.PushManager;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.YSFUserInfo;
import com.tendcloud.appcpa.TalkingDataAppCpa;
import com.zamplus.businesstrack.ZampAppAnalytics;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import de.greenrobot.event.EventBus;

/**
 * Created by rookie on 2018/3/19.
 */

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.tv_nation_code)
    TextView tvNationCode;
    @Bind(R.id.iv_clear)
    ImageView ivClear;
    @Bind(R.id.et_bind_phone_number)
    ClearEditText etBindPhoneNumber;
    @Bind(R.id.et_bind_security_code)
    ClearEditText etBindSecurityCode;
    @Bind(R.id.register_security_tv)
    TextView registerSecurityTv;
    @Bind(R.id.register_security_rl)
    RelativeLayout registerSecurityRl;
    @Bind(R.id.password_line)
    View passwordLine;
    @Bind(R.id.password_see)
    ImageView passwordSee;
    @Bind(R.id.et_password)
    ClearEditText etPassword;
    @Bind(R.id.password_rl)
    RelativeLayout passwordRl;
    @Bind(R.id.tv_get_voice)
    TextView tvGetVoice;
    @Bind(R.id.iv_finish)
    Button ivFinish;
    @Bind(R.id.tv_tip)
    TextView tvTip;
    @Bind(R.id.account_line)
    View accountLine;
    @Bind(R.id.security_line)
    View securityLine;
    @Bind(R.id.tv_regagreement)
    TextView tvRegagreement;

    private boolean isActivityFinish = false;
    private CountryBean countryBean;
    private TimeDown timeDown;
    private Dialog loadingDialog;
    private boolean loginPassWordSee;
    private String getSecurityPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);
        nameTv.setText("注册账号");
        tvRegagreement.setVisibility(View.VISIBLE);
        tvRegagreement.setText(Html.fromHtml("点击注册代表您已阅读并同意<b>《用户协议》</b>"));
        loadingDialog = DialogUtil.createLoadingDialog(this);
        initDefaultCountry();
        intiListener();
        etBindPhoneNumber.postDelayed(new Runnable() {
            @Override
            public void run() {
                KeyboardUtil.showKeyboard(etBindPhoneNumber);
            }
        },500);
    }

    private void intiListener() {
        etBindSecurityCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = etBindSecurityCode.getText().toString().trim();
                String str2 = etPassword.getText().toString().trim();
                if ((Util.isEmpty(str2)) || (Util.isEmpty(str))) {
                    ivFinish.setEnabled(false);
                    ivFinish.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
                } else {
                    ivFinish.setEnabled(true);
                    ivFinish.setBackgroundColor(Color.parseColor("#DE232427"));
                }
            }
        });

        etBindPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = etBindSecurityCode.getText().toString().trim();
                String str2 = etPassword.getText().toString().trim();
                if ((Util.isEmpty(str2)) || (Util.isEmpty(str))) {
                    ivFinish.setEnabled(false);
                    ivFinish.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
                } else {
                    ivFinish.setEnabled(true);
                    ivFinish.setBackgroundColor(Color.parseColor("#DE232427"));
                }
            }
        });

        etBindPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    accountLine.setBackgroundColor(getResources().getColor(R.color.color_black_bg1));
                }else{
                    accountLine.setBackgroundColor(getResources().getColor(R.color.line));
                }

            }
        });
        etBindSecurityCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    securityLine.setBackgroundColor(getResources().getColor(R.color.color_black_bg1));
                }else{
                    securityLine.setBackgroundColor(getResources().getColor(R.color.line));
                }
            }
        });
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    passwordLine.setBackgroundColor(getResources().getColor(R.color.color_black_bg1));
                }else{
                    passwordLine.setBackgroundColor(getResources().getColor(R.color.line));
                }
            }
        });
    }

    @OnClick({R.id.tv_get_voice, R.id.back_iv, R.id.tv_nation_code, R.id.iv_clear, R.id.register_security_tv, R.id.password_see, R.id.iv_finish,R.id.tv_regagreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_voice:
                new AlertDialog.Builder(this)
                        .setMessage(R.string.msg_voice_code)
                        .setPositiveButton("现在接听", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendAudio(tvGetVoice);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.back_iv:
                finish();
                break;
            case R.id.tv_nation_code:
                Intent intent = new Intent(this, CountryCodeActivity.class);
                startActivityForResult(intent, Constants.RequestCode.COUNTRY_CODE);
                break;
            case R.id.iv_clear:
                etBindPhoneNumber.setText("");
                break;
            case R.id.register_security_tv:
                sendCode(registerSecurityTv);
                break;
            case R.id.password_see:
                if (!loginPassWordSee) {
                    passwordSee.setImageResource(R.mipmap.icon_login_eyeopen);
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    passwordSee.setImageResource(R.mipmap.icon_login_eyeclose);
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                loginPassWordSee = !loginPassWordSee;
                CharSequence text = etPassword.getText();
                if (text instanceof Spannable) {
                    Spannable spanText = (Spannable) text;
                    Selection.setSelection(spanText, text.length());
                }
                break;
            case R.id.iv_finish:
                finishInput();
                break;
            case R.id.tv_regagreement:
                Util.urlAction(RegisterActivity.this, "http://m.d2cmall.com/page/regagreement");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constants.RequestCode.COUNTRY_CODE:
                    countryBean = (CountryBean) data.getSerializableExtra("country");
                    tvNationCode.setText(getString(R.string.label_plus, countryBean.getMobileCode()));
                    break;
            }
        }
    }

    private void finishInput() {
        String registerAccount = etBindPhoneNumber.getText().toString().trim();
        String registerPassword = etPassword.getText().toString().trim();
        String registerPhoneCode = etBindSecurityCode.getText().toString().trim();
        tvTip.setTextColor(Color.parseColor("#c8c8cc"));
        tvTip.setText(R.string.label_password_tip1);
        if (Util.isEmpty(registerAccount)) {
            Util.showToast(RegisterActivity.this, R.string.msg_account_empty);
            etBindPhoneNumber.requestFocus();
            return;
        }
        if (!registerAccount.equals(getSecurityPhoneNumber)) {
            Util.showToast(RegisterActivity.this, R.string.msg_account_differ);
            etBindPhoneNumber.requestFocus();
            return;
        }
        if (Util.isEmpty(registerPhoneCode)) {
            Util.showToast(RegisterActivity.this, R.string.hint_msg_security2);
            etBindSecurityCode.requestFocus();
            return;
        }
        if (Util.isEmpty(registerPassword)) {
            Util.showToast(RegisterActivity.this, R.string.msg_password_empty);
            etPassword.requestFocus();
            return;
        }
        if (registerPassword.length() < 8 || registerPassword.length() > 20) {
            tvTip.setTextColor(getResources().getColor(R.color.color_red));
            tvTip.setText(R.string.label_password_tip2);
            return;
        }
        if (!Util.checkPw(registerPassword)) {
            tvTip.setTextColor(getResources().getColor(R.color.color_red));
            tvTip.setText(R.string.label_password_tip2);
            etPassword.requestFocus();
            return;
        }
        checkRegister(registerAccount, registerPhoneCode, registerPassword);
    }

    private void checkRegister(final String account, String code, final String password) {
        register(code, account, password);
    }

    private void sendClientId(UserBean userBean) {
        //登录成功回传，并启动打开个推服务
        if (userBean != null) {
            PushManager.getInstance().bindAlias(this, String.valueOf(userBean.getData().getMember().getMemberId()));
        }
        String clientId = D2CApplication.mSharePref.getSharePrefString(SharePrefConstant.CLIENT_ID, "");
        SendDeviceApi api = new SendDeviceApi();
        api.setMemberId(userBean.getData().getMember().getId());
        api.setClientId(clientId);
        api.setDeviceLabel(clientId);
        api.setDevice(Util.getPageVersionName(this));
        api.setPlatform(Util.getDeviceModel());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText(RegisterActivity.this, Util.checkErrorType(error), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    private void getCartNum() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.CART_COUNT_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CartCountBean>() {
            @Override
            public void onResponse(CartCountBean response) {
                HomeActivity.count = response.getData().getCartItemCount();
                EventBus.getDefault().post(new ActionBean(Constants.ActionType.REFRESH_CART_COUNT));
            }
        });
    }

    private void loginCallback(UserBean userBean) {//注册成功登录回调
        loadingDialog.dismiss();
        D2CApplication.mSharePref.putSharePrefString(SharePrefConstant.TOKEN,
                userBean.getData().getMember().getUserToken());
        Session.getInstance().saveUserToFile(RegisterActivity.this, userBean.getData().getMember());
        //登录成功刷新未读消息数
        EventBus.getDefault().post(new ActionBean(Constants.ActionType.MESSAGE_ALL_UNREANED_CHANGE));
        EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.LOGIN_OK));
        sendClientId(userBean);
        getCartNum();
        publishThird(userBean);
        setQiyuUserInfo(userBean);//设置七鱼用户信息
        setResult(RESULT_OK);
        finish();
        postLog();
    }

    private void postLog() {
        List<String> list = LogDB.getInstance(this).query();
        if (list != null && list.size() > 0) {
            int size = list.size();
            int count = size % 20 == 0 ? size / 20 : size / 20 + 1;
            for (int i = 0; i < count; i++) {
                StringBuilder builder = new StringBuilder();
                int length = i * 20 + 20 > size ? size : i * 20 + 20;
                for (int j = i * 20; j < length; j++) {
                    builder.append(list.get(j));
                    if (j != i * 20 + 19) {
                        builder.append("|");
                    }
                }
                LogApi api = new LogApi();
                api.setLog(builder.toString());
                D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                    @Override
                    public void onResponse(BaseBean response) {

                    }
                });
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (timeDown != null) {
            timeDown.cancel();
            timeDown = null;
        }
        super.onDestroy();
    }

    private void setQiyuUserInfo(UserBean userBean) {
        YSFUserInfo userInfo = new YSFUserInfo();
        userInfo.userId = String.valueOf(userBean.getData().getMember().getId());
        userInfo.data = Util.getUserInfoData(userBean.getData().getMember().getNickname(), userBean.getData().getMember().getLoginCode(), String.valueOf(userBean.getData().getMember().getId()), userBean.getData().getMember().getEmail(), userBean.getData().getMember().getSex());
        D2CApplication.ysfOptions.uiCustomization.rightAvatar = Util.getD2cPicUrl(userBean.getData().getMember().getHead());
        Unicorn.setUserInfo(userInfo);
    }

    private void publishThird(UserBean bean) {
        TalkingDataAppCpa.onLogin(String.valueOf(bean.getData().getMember().getId()));
        ZampAppAnalytics.onLogin(this, String.valueOf(bean.getData().getMember().getMemberId()), false);
    }

    private void register(String code, String account, String password) {
        RegisterApi api = new RegisterApi();
        api.setLoginCode(account);
        api.code = code;
        api.setPassword(password);
        api.setRePassword(password);
        api.setNationCode(countryBean.getMobileCode());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<UserBean>() {
            @Override
            public void onResponse(UserBean userBean) {
                stat("V3登录/注册", "注册", null);
                Util.showToast(RegisterActivity.this, "注册成功");
                setResult(RESULT_OK);
                loginCallback(userBean);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                Toast toast = Toast.makeText(RegisterActivity.this, Util.checkErrorType(error), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }


    private void sendCode(final TextView codeTv) {
        hideKeyboard(null);
        final String phoneNumber = etBindPhoneNumber.getText().toString().trim();
        if (Util.isEmpty(phoneNumber)) {
            Util.showToast(this, R.string.msg_phone_empty);
            return;
        }
        if (countryBean.getMobileCode().equals("86") && phoneNumber.length() != 11) {
            Util.showToast(this, R.string.msg_phone_error);
            return;
        }
        timeDown = new TimeDown(60 * 1000, 1000);
        timeDown.tv = codeTv;
        timeDown.start();
        tvGetVoice.setEnabled(false);
        codeTv.setEnabled(false);
        CheckPhoneExistApi api = new CheckPhoneExistApi();
        api.setLoginCode(phoneNumber);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                Util.showToast(RegisterActivity.this, "该手机号已经注册过啦!");
                timeDown.cancel();
                codeTv.setEnabled(true);
                codeTv.setText(R.string.label_get_code);
                registerSecurityTv.setEnabled(true);
                backStatus();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof HttpError) {
                    HttpError httpError = (HttpError) error;
                    String msg = httpError.getMessage();
                    if (msg.contains("未注册")) {
                        SendCodeApi api1 = new SendCodeApi();
                        api1.setMobile(phoneNumber);
                        api1.setSource(phoneNumber);
                        api1.setType("MEMBERMOBILE");
                        api1.setNationCode(countryBean.getMobileCode());
                        D2CApplication.httpClient.loadingRequest(api1, new BeanRequest.SuccessListener<BaseBean>() {
                            @Override
                            public void onResponse(BaseBean baseBean) {
                                getSecurityPhoneNumber = phoneNumber;
                                if(etBindPhoneNumber.isFocused()){
                                    etBindPhoneNumber.clearFocus();
                                }
                                KeyboardUtil.showKeyboard(etBindSecurityCode);
                                Util.showToast(RegisterActivity.this, R.string.msg_send_code_ok);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                    } else {
                        Util.showToast(RegisterActivity.this, Util.checkErrorType(error));
                    }
                }
            }
        });
    }

    private void backStatus() {
        tvGetVoice.setEnabled(true);
        tvGetVoice.setTextColor(getResources().getColor(R.color.color_black_bg1));
        tvGetVoice.setText(R.string.label_try_code);
    }

    public class TimeDown extends CountDownTimer {

        public TextView tv;

        public TimeDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            if (tv != null) {
                tv.setEnabled(true);
                if (tv == tvGetVoice) {
                    tv.setText(R.string.label_try_code);
                } else {
                    tv.setText(R.string.label_get_code);
                }
            }
            if (!tvGetVoice.isEnabled()) {
                tvGetVoice.setEnabled(true);
                tvGetVoice.setTextColor(getResources().getColor(R.color.color_black_bg1));
                tvGetVoice.setText(R.string.label_try_code);
            }
            if (!registerSecurityTv.isEnabled()) {
                registerSecurityTv.setEnabled(true);
                registerSecurityTv.setText(R.string.label_get_code);
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int secUntilFinished = (int) (millisUntilFinished / 1000);
            if (!isActivityFinish) {
                if (tv != null) {
                    //tv.setBackgroundResource(R.drawable.counter_dark);
                    if (tv == registerSecurityTv) {
                        tv.setText(getString(R.string.label_reload_security,
                                secUntilFinished > 9 ? secUntilFinished
                                        : ("0" + secUntilFinished)));
                    } else {
                        tv.setText(getString(R.string.label_reload_code,
                                secUntilFinished > 9 ? secUntilFinished
                                        : ("0" + secUntilFinished)));
                    }


                }
            }
        }
    }

    private void initDefaultCountry() {
        String jsonStr = Util.readStreamToString(getResources().openRawResource(R.raw.country));
        Gson gson = new Gson();
        List<CountryBean> countryBeanList = gson.fromJson(jsonStr, new TypeToken<List<CountryBean>>() {
        }.getType());
        countryBean = countryBeanList.get(0);
        if (tvNationCode != null) {
            tvNationCode.setText(getString(R.string.label_plus, countryBean.getMobileCode()));
        }
    }

    private void sendAudio(final TextView codeTv) {
        hideKeyboard(null);
        final String phoneNumber = etBindPhoneNumber.getText().toString().trim();
        if (Util.isEmpty(phoneNumber)) {
            Util.showToast(this, R.string.msg_phone_empty);
            return;
        }
        if (!Util.isMobileNo(countryBean.getMobileCode() + phoneNumber, countryBean.getRe())) {
            Util.showToast(this, R.string.msg_phone_error);
            return;
        }
        tvGetVoice.setTextColor(Color.parseColor("#C8C8C8"));
        timeDown = new TimeDown(60 * 1000, 1000);
        timeDown.tv = codeTv;
        timeDown.start();
        codeTv.setEnabled(false);
        registerSecurityTv.setEnabled(false);
        CheckPhoneExistApi api = new CheckPhoneExistApi();
        api.setLoginCode(phoneNumber);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                Util.showToast(RegisterActivity.this, "该手机号已经注册过啦!");
                timeDown.cancel();
                codeTv.setEnabled(true);
                codeTv.setText(R.string.label_try_code);
                codeTv.setTextColor(getResources().getColor(R.color.color_black_bg1));
                registerSecurityTv.setEnabled(true);
                backStatus();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                timeDown.cancel();
                codeTv.setEnabled(true);
                codeTv.setTextColor(getResources().getColor(R.color.color_black_bg1));
                codeTv.setText(R.string.label_try_code);
                registerSecurityTv.setEnabled(true);
                if (error instanceof HttpError) {
                    HttpError httpError = (HttpError) error;
                    String msg = httpError.getMessage();
                    if (msg.contains("未注册")) {
                        SendAudioApi api1 = new SendAudioApi();
                        api1.setMobile(phoneNumber);
                        api1.setSource(phoneNumber);
                        api1.setType("MEMBERMOBILE");
                        api1.setNationCode(countryBean.getMobileCode());
                        D2CApplication.httpClient.loadingRequest(api1, new BeanRequest.SuccessListener<BaseBean>() {
                            @Override
                            public void onResponse(BaseBean baseBean) {
                                getSecurityPhoneNumber = phoneNumber;
                                Util.showToast(RegisterActivity.this, R.string.msg_send_code_ok);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                    } else {
                        Util.showToast(RegisterActivity.this, Util.checkErrorType(error));
                    }
                } else {
                    Util.showToast(RegisterActivity.this, Util.checkErrorType(error));
                }
            }
        });
    }


}
