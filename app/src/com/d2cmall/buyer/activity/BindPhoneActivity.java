package com.d2cmall.buyer.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.BindPhoneApi;
import com.d2cmall.buyer.api.SendAudioApi;
import com.d2cmall.buyer.api.SendCodeApi;
import com.d2cmall.buyer.api.SendDeviceApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.CountryBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ClearEditText;
import com.d2cmall.buyer.widget.MeasuredLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.igexin.sdk.PushManager;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.YSFUserInfo;
import com.tendcloud.appcpa.TalkingDataAppCpa;
import com.tendcloud.tenddata.TCAgent;
import com.zamplus.businesstrack.ZampAppAnalytics;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 第三方登录绑定手机号页面
 * Created by rookie on 2017/8/16.
 */

public class BindPhoneActivity extends BaseActivity implements MeasuredLayout.OnKeyboardHideListener {
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
    @Bind(R.id.tv_remind)
    TextView tvRemind;
    @Bind(R.id.tv_nation_code)
    TextView tvNationCode;
    @Bind(R.id.et_bind_phone_number)
    ClearEditText etBindPhoneNumber;
    @Bind(R.id.et_bind_security_code)
    ClearEditText etBindSecurityCode;
    @Bind(R.id.register_security_tv)
    TextView registerSecurityTv;
    @Bind(R.id.register_security_rl)
    RelativeLayout registerSecurityRl;
    @Bind(R.id.et_password)
    ClearEditText etPassword;
    @Bind(R.id.et_confirm)
    ClearEditText etConfirm;
    @Bind(R.id.ll_password)
    LinearLayout llPassword;
    @Bind(R.id.tv_bind_button)
    Button tvBindButton;
    @Bind(R.id.tv_get_voice)
    TextView tvGetVoice;
    private TimeDown timeDown;
    private CountryBean countryBean;
    private int flag;
    private boolean isActivityFinish = false;
    private Dialog loadingDialog;
    private String getSecurityPhoneNumber;
    private boolean hasBindPhone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind1_phone);
        ButterKnife.bind(this);
        loadingDialog = DialogUtil.createLoadingDialog(this);
        doBusiness();
    }


    public void doBusiness() {
        flag = getIntent().getIntExtra("flag", 0);
        tvBindButton.setEnabled(false);
        tvBindButton.setBackgroundColor(Color.parseColor("#61232427"));
        nameTv.setText("");
        initDefaultCountry();
        //绑定按钮的状态监听
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
                if (str.equals("")) {
                    tvBindButton.setEnabled(false);
                    tvBindButton.setBackgroundColor(Color.parseColor("#61232427"));
                } else {
                    tvBindButton.setEnabled(true);
                    tvBindButton.setBackgroundColor(Color.parseColor("#DE232427"));
                }
            }
        });
    }

    //未绑定手机号退出页面需要清除登录信息
    private void notBind() {
        isActivityFinish = true;
        if (!hasBindPhone) {
            UserBean.DataEntity.MemberEntity userBean = Session.getInstance().getUser();
            if (userBean != null) {
                PushManager.getInstance().unBindAlias(this, String.valueOf(userBean.getMemberId()), true);
            }
            Session.getInstance().logout(this);
            if (flag == 0) {
                setResult(RESULT_OK);
            }
        }
    }


    @OnClick({R.id.back_iv, R.id.tv_nation_code, R.id.tv_bind_button, R.id.register_security_tv, R.id.tv_get_voice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.tv_nation_code:
                Intent intent = new Intent(this, CountryCodeActivity.class);
                startActivityForResult(intent, Constants.RequestCode.COUNTRY_CODE);
                break;
            case R.id.tv_bind_button:
                bindSure();
                break;
            case R.id.register_security_tv:
                sendCode(registerSecurityTv);
                break;
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

    private void publishThird(UserBean bean) {
        TalkingDataAppCpa.onLogin(String.valueOf(bean.getData().getMember().getId()));
        ZampAppAnalytics.onLogin(this, String.valueOf(bean.getData().getMember().getMemberId()), false);
    }

    private void setQiyuUserInfo(UserBean userBean) {
        YSFUserInfo userInfo = new YSFUserInfo();
        userInfo.userId = String.valueOf(userBean.getData().getMember().getId());
        userInfo.data = Util.getUserInfoData(userBean.getData().getMember().getNickname(), userBean.getData().getMember().getLoginCode(), String.valueOf(userBean.getData().getMember().getId()), userBean.getData().getMember().getEmail(), userBean.getData().getMember().getSex());
        D2CApplication.ysfOptions.uiCustomization.rightAvatar = Util.getD2cPicUrl(userBean.getData().getMember().getHead());
        Unicorn.setUserInfo(userInfo);
    }

    //绑定手机号操作
    private void bindSure() {
        final String phone = etBindPhoneNumber.getText().toString().trim();
        String code = etBindSecurityCode.getText().toString().trim();
        if (Util.isEmpty(phone)) {
            Util.showToast(this, R.string.msg_phone_empty);
            return;
        }
        if (!phone.equals(getSecurityPhoneNumber)) {
            Util.showToast(this, R.string.msg_account_differ);
            return;
        }
        if (!Util.isMobileNo(countryBean.getMobileCode() + phone, countryBean.getRe())) {
            Util.showToast(this, R.string.msg_phone_error);
            return;
        }
        if (Util.isEmpty(code)) {
            Util.showToast(this, R.string.hint_msg_security);
            return;
        }
        BindPhoneApi api = new BindPhoneApi();
        api.setLoginCode(getSecurityPhoneNumber);
        api.setCode(code);
        api.setPassword1(etPassword.getText().toString().trim());
        api.setPassword2(etConfirm.getText().toString().trim());
        api.setNationCode(countryBean.getMobileCode());
        loadingDialog.show();
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<UserBean>() {
            @Override
            public void onResponse(UserBean userBean) {
                stat("V3登录/注册", "绑定手机号成功",null);
                Constants.isBingWChatOK = true;
                hasBindPhone = true;
                loadingDialog.dismiss();
                Util.showToast(BindPhoneActivity.this, R.string.msg_bind_phone_ok);
                D2CApplication.mSharePref.putSharePrefString(SharePrefConstant.TOKEN,
                        userBean.getData().getMember().getUserToken());
                Session.getInstance().saveUserToFile(BindPhoneActivity.this, userBean.getData().getMember());
                userBean.getData().getMember().setDanger(userBean.getData().getDanger());
                if (flag == 2) {
                    sendClientId(userBean);
                    publishThird(userBean);
                    setQiyuUserInfo(userBean);//设置七鱼用户信息
                    EventBus.getDefault().post(new ActionBean(Constants.ActionType.MESSAGE_ALL_UNREANED_CHANGE));
                    //全局发登录成功通知
                    EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.LOGIN_OK));
                    EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.REFRESH_CART));
                    EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.CARTNUM, 1));
                }

                setResult(RESULT_OK);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                Util.showToast(BindPhoneActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void sendClientId(UserBean userBean) {
        PushManager.getInstance().bindAlias(this, String.valueOf(userBean.getData().getMember().getMemberId()));
        //登录成功回传，并启动打开个推服务
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
                Util.showToast(BindPhoneActivity.this, Util.checkErrorType(error));
            }
        });
    }

    public void hideKeyboard(View v) {
        if (this.getCurrentFocus() != null) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
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
        timeDown = new TimeDown(60 * 1000, 1000);
        timeDown.tv = codeTv;
        timeDown.start();
        codeTv.setEnabled(false);
        registerSecurityTv.setEnabled(false);
        SendAudioApi api = new SendAudioApi();
        api.setNationCode(countryBean.getMobileCode());
        api.setMobile(phoneNumber);
        api.setSource(phoneNumber);
        api.setType("MemberMobile");
        api.setNationCode(countryBean.getMobileCode());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                getSecurityPhoneNumber = phoneNumber;
                Util.showToast(BindPhoneActivity.this, R.string.msg_send_code_ok);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                timeDown.cancel();
                codeTv.setEnabled(true);
                registerSecurityTv.setEnabled(true);
                codeTv.setText(R.string.label_get_code);
                Util.showToast(BindPhoneActivity.this, Util.checkErrorType(error));
            }
        });
    }

    //发送验证码
    private void sendCode(final TextView codeTv) {
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
        timeDown = new TimeDown(60 * 1000, 1000);
        timeDown.tv = codeTv;
        timeDown.start();
        codeTv.setEnabled(false);
        tvGetVoice.setEnabled(false);
        tvGetVoice.setTextColor(Color.parseColor("#61000000"));
        Drawable nav_dowm = getResources().getDrawable(R.mipmap.icon_login_novoice);
        nav_dowm.setBounds(0, 0, nav_dowm.getMinimumWidth(), nav_dowm.getMinimumHeight());
        tvGetVoice.setCompoundDrawables(nav_dowm, null, null, null);
        SendCodeApi api = new SendCodeApi();
        api.setNationCode(countryBean.getMobileCode());
        api.setMobile(phoneNumber);
        api.setSource(phoneNumber);
        api.setType("MemberMobile");
        api.setNationCode(countryBean.getMobileCode());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                getSecurityPhoneNumber = phoneNumber;
                Util.showToast(BindPhoneActivity.this, R.string.msg_send_code_ok);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                timeDown.cancel();
                codeTv.setEnabled(true);
                codeTv.setText(R.string.label_get_code);
                tvGetVoice.setEnabled(true);
                tvGetVoice.setTextColor(Color.parseColor("#FFFDC33E"));
                Drawable nav_dowm = getResources().getDrawable(R.mipmap.icon_login_voice);
                nav_dowm.setBounds(0, 0, nav_dowm.getMinimumWidth(), nav_dowm.getMinimumHeight());
                tvGetVoice.setCompoundDrawables(nav_dowm, null, null, null);
                Util.showToast(BindPhoneActivity.this, Util.checkErrorType(error));
            }
        });

    }


    @Override
    public void onKeyboardHide(boolean hide) {

    }

    @Override
    protected void onDestroy() {
        notBind();
        if (timeDown != null) {
            timeDown = null;
        }
        super.onDestroy();
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
                tvGetVoice.setTextColor(Color.parseColor("#FFFDC33E"));
                Drawable nav_dowm = getResources().getDrawable(R.mipmap.icon_login_voice);
                nav_dowm.setBounds(0, 0, nav_dowm.getMinimumWidth(), nav_dowm.getMinimumHeight());
                tvGetVoice.setCompoundDrawables(nav_dowm, null, null, null);
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
                    tv.setText(getString(R.string.label_reload_code,
                            secUntilFinished > 9 ? secUntilFinished
                                    : ("0" + secUntilFinished)));
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constants.RequestCode.COUNTRY_CODE:
                    countryBean = (CountryBean) data.getSerializableExtra("country");
                    tvNationCode.setText(getString(R.string.label_plus, countryBean.getMobileCode()));
                    break;
            }
        }
    }

}
