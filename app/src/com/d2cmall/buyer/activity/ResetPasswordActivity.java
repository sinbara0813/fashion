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
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.CheckPhoneExistApi;
import com.d2cmall.buyer.api.ResetPasswordApi;
import com.d2cmall.buyer.api.SendAudioApi;
import com.d2cmall.buyer.api.SendCodeApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.CountryBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.http.HttpError;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ClearEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dreamtobe.kpswitch.util.KeyboardUtil;

import static com.d2cmall.buyer.Constants.RESET_PASSWORD_URL;

/**
 * Created by rookie on 2017/8/21.
 * 忘记密码页面
 */

public class ResetPasswordActivity extends BaseActivity {

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
    @Bind(R.id.iv_finish)
    Button ivFinish;
    @Bind(R.id.tv_get_voice)
    TextView tvGetVoice;
    @Bind(R.id.tv_tip)
    TextView tvTip;

    private boolean isActivityFinish = false;
    private CountryBean countryBean;
    private TimeDown timeDown;
    private Dialog loadingDialog;
    private boolean loginPassWordSee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);
        loadingDialog = DialogUtil.createLoadingDialog(this);
        String number = getIntent().getStringExtra("account");
        if (!Util.isEmpty(number)) {
            etBindPhoneNumber.setText(number);
            etBindPhoneNumber.setSelection(number.length());
        }
        doBusiness();
        etBindPhoneNumber.postDelayed(new Runnable() {
            @Override
            public void run() {
                KeyboardUtil.showKeyboard(etBindPhoneNumber);
            }
        },500);
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

    public void doBusiness() {
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
                    //tv.setBackgroundResource(R.drawable.counter_dark);
                    tv.setText(getString(R.string.label_reload_security,
                            secUntilFinished > 9 ? secUntilFinished
                                    : ("0" + secUntilFinished)));
                }
            }
        }
    }


    @OnClick({R.id.tv_get_voice, R.id.back_iv, R.id.tv_nation_code, R.id.iv_clear, R.id.register_security_tv, R.id.password_see, R.id.iv_finish})
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
        }
    }


    private void finishInput() {
        hideKeyboard(null);
        final String password = etPassword.getText().toString().trim();
        final String phone = etBindPhoneNumber.getText().toString().trim();
        final String code = etBindSecurityCode.getText().toString().trim();
        tvTip.setTextColor(Color.parseColor("#c8c8cc"));
        tvTip.setText(R.string.label_password_tip1);
        if (Util.isEmpty(password)) {
            Util.showToast(this, R.string.msg_password_empty);
            return;
        }
        if(password.length()<8||password.length()>20){
            tvTip.setTextColor(getResources().getColor(R.color.color_red));
            tvTip.setText(R.string.label_password_tip2);
            return;
        }
        if (!Util.checkPw(password)) {
            tvTip.setTextColor(getResources().getColor(R.color.color_red));
            tvTip.setText(R.string.label_password_tip2);
            return;
        }
        loadingDialog.show();
        ResetPasswordApi api1 = new ResetPasswordApi();
        api1.setCode(code);
        api1.setInterPath(RESET_PASSWORD_URL);
        api1.setLoginCode(phone);
        api1.setPassword(password);
        api1.setConfirmPassword(password);
        D2CApplication.httpClient.loadingRequest(api1, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                loadingDialog.dismiss();
                Util.showToast(ResetPasswordActivity.this, R.string.msg_set_password_ok);
                Intent intent = new Intent();
                intent.putExtra("type", 1);
                intent.putExtra("account", phone);
                intent.putExtra("password", password);
                setResult(RESULT_OK, intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                Util.showToast(ResetPasswordActivity.this, Util.checkErrorType(error));
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
        if (phoneNumber.length() != 11 && countryBean.getMobileCode().equals("86")) {
            Util.showToast(this, R.string.msg_phone_error);
            return;
        }
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
                SendAudioApi api1 = new SendAudioApi();
                api1.setMobile(phoneNumber);
                api1.setSource(phoneNumber);
                api1.setType("RETRIEVEPASSWORD");
                api1.setNationCode(countryBean.getMobileCode());
                D2CApplication.httpClient.loadingRequest(api1, new BeanRequest.SuccessListener<BaseBean>() {
                    @Override
                    public void onResponse(BaseBean baseBean) {
                        Util.showToast(ResetPasswordActivity.this, R.string.msg_send_code_ok);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        timeDown.cancel();
                        codeTv.setEnabled(true);
                        codeTv.setText(R.string.label_get_code);
                        registerSecurityTv.setEnabled(false);
                        Util.showToast(ResetPasswordActivity.this, Util.checkErrorType(error));
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                timeDown.cancel();
                codeTv.setEnabled(true);
                codeTv.setText(R.string.label_get_code);
                registerSecurityTv.setEnabled(false);
                if (error instanceof HttpError) {
                    HttpError httpError = (HttpError) error;
                    int status = httpError.getStatus();
                    if (status == -1) {
                        Util.showToast(ResetPasswordActivity.this, R.string.msg_no_register);
                    }
                } else {
                    Util.showToast(ResetPasswordActivity.this, Util.checkErrorType(error));
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

    private void sendCode(final TextView codeTv) {
        hideKeyboard(null);
        final String phoneNumber = etBindPhoneNumber.getText().toString().trim();
        if (Util.isEmpty(phoneNumber)) {
            Util.showToast(this, R.string.msg_phone_empty);
            return;
        }
        if (phoneNumber.length() != 11 && countryBean.getMobileCode().equals("86")) {
            Util.showToast(this, R.string.msg_phone_error);
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
                SendCodeApi api1 = new SendCodeApi();
                api1.setMobile(phoneNumber);
                api1.setSource(phoneNumber);
                api1.setType("RETRIEVEPASSWORD");
                api1.setNationCode(countryBean.getMobileCode());
                D2CApplication.httpClient.loadingRequest(api1, new BeanRequest.SuccessListener<BaseBean>() {
                    @Override
                    public void onResponse(BaseBean baseBean) {
                        if(etBindSecurityCode.isFocused()){
                            etBindSecurityCode.clearFocus();
                        }
                        KeyboardUtil.showKeyboard(etBindSecurityCode);
                        Util.showToast(ResetPasswordActivity.this, R.string.msg_send_code_ok);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        timeDown.cancel();
                        codeTv.setEnabled(true);
                        codeTv.setText(R.string.label_get_code);
                        backStatus();
                        Util.showToast(ResetPasswordActivity.this, Util.checkErrorType(error));
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                timeDown.cancel();
                codeTv.setEnabled(true);
                codeTv.setText(R.string.label_get_code);
                backStatus();
                if (error instanceof HttpError) {
                    HttpError httpError = (HttpError) error;
                    int status = httpError.getStatus();
                    if (status == -1) {
                        Util.showToast(ResetPasswordActivity.this, R.string.msg_no_register);
                    }
                } else {
                    Util.showToast(ResetPasswordActivity.this, Util.checkErrorType(error));
                }
            }
        });
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
