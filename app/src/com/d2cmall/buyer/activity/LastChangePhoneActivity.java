package com.d2cmall.buyer.activity;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.BuildConfig;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.ChangePhoneApi;
import com.d2cmall.buyer.api.CheckPhoneExistApi;
import com.d2cmall.buyer.api.SendAudioApi;
import com.d2cmall.buyer.api.SendCodeApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.CountryBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ClearEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2017/11/21.
 */

public class LastChangePhoneActivity extends BaseActivity {

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
    @Bind(R.id.btn_next)
    Button btnNext;
    @Bind(R.id.tv_get_voice)
    TextView tvGetVoice;
    private CountryBean countryBean;
    private boolean isActivityFinish = false;
    private TimeDown timeDown;
    private String getSecurityPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_bind_last_layout);
        ButterKnife.bind(this);
        nameTv.setText("修改绑定手机号");
        initDefaultCountry();
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
                    btnNext.setEnabled(false);
                    btnNext.setBackgroundColor(Color.parseColor("#61DDDDDD"));
                } else {
                    btnNext.setEnabled(true);
                    btnNext.setBackgroundColor(Color.parseColor("#DE232427"));
                }
            }
        });
    }

    @OnClick({R.id.back_iv, R.id.register_security_tv, R.id.btn_next, R.id.tv_nation_code, R.id.tv_get_voice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.register_security_tv:
                sendCode(registerSecurityTv);
                break;
            case R.id.btn_next:
                changeBind();
                break;
            case R.id.tv_nation_code:
                Intent intent = new Intent(this, CountryCodeActivity.class);
                startActivityForResult(intent, Constants.RequestCode.COUNTRY_CODE);
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

    private void changeBind() {
        final String newMobile = etBindPhoneNumber.getText().toString().trim();
        String code = etBindSecurityCode.getText().toString().trim();
        String nationCode = countryBean.getMobileCode();
        if (!newMobile.equals(getSecurityPhoneNumber)) {
            Util.showToast(this, R.string.msg_account_differ);
            etBindPhoneNumber.requestFocus();
            return;
        }
        ChangePhoneApi api = new ChangePhoneApi();
        api.setNewMobile(newMobile);
        api.setCode(code);
        api.setNationCode(nationCode);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<UserBean>() {
            @Override
            public void onResponse(UserBean userBean) {
                Util.showToast(LastChangePhoneActivity.this, "修改绑定手机号成功!");
                D2CApplication.mSharePref.putSharePrefString(SharePrefConstant.TOKEN,
                        userBean.getData().getMember().getUserToken());
                Session.getInstance().saveUserToFile(LastChangePhoneActivity.this, userBean.getData().getMember());
                setResult(RESULT_OK);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(LastChangePhoneActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void sendAudio(final TextView codeTv) {
        hideKeyboard(null);
        final String phoneNumber = etBindPhoneNumber.getText().toString().trim();
        getSecurityPhoneNumber = phoneNumber;
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
        registerSecurityTv.setEnabled(true);
        CheckPhoneExistApi api = new CheckPhoneExistApi();
        api.setLoginCode(phoneNumber);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                Util.showToast(LastChangePhoneActivity.this, "该手机号已经注册过啦!");
                timeDown.cancel();
                codeTv.setEnabled(true);
                codeTv.setText(R.string.label_get_code);
                registerSecurityTv.setEnabled(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorText = Util.checkErrorType(error);
                if (errorText.contains("未注册")) {
                    SendAudioApi api1 = new SendAudioApi();
                    api1.setMobile(phoneNumber);
                    api1.setSource(phoneNumber);
                    api1.setType("MemberMobile");
                    api1.setNationCode(countryBean.getMobileCode());
                    D2CApplication.httpClient.loadingRequest(api1, new BeanRequest.SuccessListener<BaseBean>() {
                        @Override
                        public void onResponse(BaseBean baseBean) {
                            Util.showToast(LastChangePhoneActivity.this, R.string.msg_send_code_ok);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Util.showToast(LastChangePhoneActivity.this, Util.checkErrorType(error));
                        }
                    });
                } else {
                    Util.showToast(LastChangePhoneActivity.this, errorText);
                }
            }
        });

    }

    //发送验证码
    private void sendCode(final TextView codeTv) {
        hideKeyboard(null);
        final String phoneNumber = etBindPhoneNumber.getText().toString().trim();
        getSecurityPhoneNumber = phoneNumber;
        if (Util.isEmpty(phoneNumber)) {
            Util.showToast(this, R.string.msg_phone_empty);
            return;
        }
        timeDown = new TimeDown(60 * 1000, 1000);
        timeDown.tv = codeTv;
        timeDown.start();
        tvGetVoice.setEnabled(false);
        tvGetVoice.setTextColor(Color.parseColor("#61000000"));
        Drawable nav_dowm = getResources().getDrawable(R.mipmap.icon_login_novoice);
        nav_dowm.setBounds(0, 0, nav_dowm.getMinimumWidth(), nav_dowm.getMinimumHeight());
        tvGetVoice.setCompoundDrawables(nav_dowm, null, null, null);
        codeTv.setEnabled(false);
        CheckPhoneExistApi api = new CheckPhoneExistApi();
        api.setLoginCode(phoneNumber);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                Util.showToast(LastChangePhoneActivity.this, "该手机号已经注册过啦!");
                timeDown.cancel();
                codeTv.setEnabled(true);
                if (codeTv == tvGetVoice) {
                    codeTv.setText(R.string.label_try_code);
                } else {
                    codeTv.setText(R.string.label_get_code);
                }

                backStatus();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorText = Util.checkErrorType(error);
                if (errorText.contains("未注册")) {
                    SendCodeApi api1 = new SendCodeApi();
                    api1.setMobile(phoneNumber);
                    api1.setSource(phoneNumber);
                    api1.setType("MemberMobile");
                    api1.setNationCode(countryBean.getMobileCode());
                    D2CApplication.httpClient.loadingRequest(api1, new BeanRequest.SuccessListener<BaseBean>() {
                        @Override
                        public void onResponse(BaseBean baseBean) {
                            Util.showToast(LastChangePhoneActivity.this, R.string.msg_send_code_ok);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Util.showToast(LastChangePhoneActivity.this, Util.checkErrorType(error));
                        }
                    });
                } else {
                    Util.showToast(LastChangePhoneActivity.this, errorText);
                }
            }
        });

    }

    private void backStatus() {
        tvGetVoice.setEnabled(true);
        tvGetVoice.setTextColor(Color.parseColor("#FFFDC33E"));
        Drawable nav_dowm = getResources().getDrawable(R.mipmap.icon_login_voice);
        nav_dowm.setBounds(0, 0, nav_dowm.getMinimumWidth(), nav_dowm.getMinimumHeight());
        tvGetVoice.setCompoundDrawables(nav_dowm, null, null, null);
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

    @Override
    protected void onDestroy() {
        isActivityFinish = true;
        if (timeDown != null) {
            timeDown.cancel();
            timeDown = null;
        }
        super.onDestroy();
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
