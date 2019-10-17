package com.d2cmall.buyer.activity;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.SendAudioApi;
import com.d2cmall.buyer.api.SendCodeApi;
import com.d2cmall.buyer.api.SetPayPwdApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ClearEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2017/9/4.
 * 修改支付密码
 */

public class SetPayPasswordActivity extends BaseActivity {

    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.tag)
    View tag;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.et_password)
    ClearEditText etPassword;
    @Bind(R.id.hint_confirm_pay_password)
    ClearEditText hintConfirmPayPassword;
    @Bind(R.id.line_layout)
    View lineLayout;
    @Bind(R.id.btn_next)
    Button btnNext;
    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.tv_hint)
    TextView tvHint;
    @Bind(R.id.edit_man_name)
    ClearEditText editManName;
    @Bind(R.id.btn_code)
    TextView btnCode;
    @Bind(R.id.tv_get_voice)
    TextView tvGetVoice;
    @Bind(R.id.password_see1)
    ImageView passwordSee1;
    @Bind(R.id.password_see2)
    ImageView passwordSee2;
    @Bind(R.id.tv_tip)
    TextView tvTip;
    private Dialog loadingDialog;
    private int type;
    private String bindedAccount;
    private TimeDown timeDown;
    private UserBean.DataEntity.MemberEntity user;
    private boolean isPassWordSee1, isPassWordSee2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pay_psd);
        ButterKnife.bind(this);
        nameTv.setText("修改支付密码");
        type = getIntent().getIntExtra("type", 0);
        if (type == 4) {
            nameTv.setText("设置支付密码");
        }
        user = Session.getInstance().getUserFromFile(this);
        bindedAccount = user.getLoginCode();
        tvHint.setText(String.format(getString(R.string.label_send_code_phone), Util.hidePhoneNum(bindedAccount)));
        loadingDialog = DialogUtil.createLoadingDialog(this);
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Util.isEmpty(etPassword.getText().toString()) && !Util.isEmpty(hintConfirmPayPassword.getText().toString())) {
                    btnNext.setEnabled(true);
                    btnNext.setBackgroundColor(getResources().getColor(R.color.color_black));
                } else {
                    btnNext.setEnabled(false);
                    btnNext.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        hintConfirmPayPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Util.isEmpty(etPassword.getText().toString()) && !Util.isEmpty(hintConfirmPayPassword.getText().toString())) {
                    btnNext.setEnabled(true);
                    btnNext.setBackgroundColor(getResources().getColor(R.color.color_black));
                } else {
                    btnNext.setEnabled(false);
                    btnNext.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.back_iv, R.id.title_right, R.id.btn_next, R.id.tv_get_voice, R.id.btn_code, R.id.password_see1, R.id.password_see2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.password_see1:
                if (!isPassWordSee1) {
                    passwordSee1.setImageResource(R.mipmap.icon_login_visible_b);
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    passwordSee1.setImageResource(R.mipmap.icon_login_unvisible_b);
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isPassWordSee1 = !isPassWordSee1;
                CharSequence text = etPassword.getText();
                if (text instanceof Spannable) {
                    Spannable spanText = (Spannable) text;
                    Selection.setSelection(spanText, text.length());
                }
                break;
            case R.id.password_see2:
                if (!isPassWordSee2) {
                    passwordSee2.setImageResource(R.mipmap.icon_login_visible_b);
                    hintConfirmPayPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    passwordSee2.setImageResource(R.mipmap.icon_login_unvisible_b);
                    hintConfirmPayPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isPassWordSee2 = !isPassWordSee2;
                CharSequence text2 = hintConfirmPayPassword.getText();
                if (text2 instanceof Spannable) {
                    Spannable spanText = (Spannable) text2;
                    Selection.setSelection(spanText, text2.length());
                }
                break;
            case R.id.btn_next:
                save();
                break;
            case R.id.back_iv:
                finish();
                break;
            case R.id.btn_code:
                sendCode(btnCode);
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

    private void sendCode(final TextView codeTv) {
        hideKeyboard(null);
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
        api.setMobile(bindedAccount);
        api.setSource(bindedAccount);
        api.setType("MemberMobile");
        api.setNationCode(user.getNationCode());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                Util.showToast(SetPayPasswordActivity.this, R.string.msg_send_code_ok);
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
                Util.showToast(SetPayPasswordActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void sendAudio(final TextView codeTv) {
        hideKeyboard(null);
        timeDown = new TimeDown(60 * 1000, 1000);
        timeDown.tv = codeTv;
        timeDown.start();
        codeTv.setEnabled(false);
        btnCode.setEnabled(false);
        SendAudioApi api = new SendAudioApi();
        api.setMobile(bindedAccount);
        api.setSource(bindedAccount);
        api.setType("MemberMobile");
        api.setNationCode(user.getNationCode());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                Util.showToast(SetPayPasswordActivity.this, R.string.msg_send_code_ok);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                timeDown.cancel();
                codeTv.setEnabled(true);
                codeTv.setText(R.string.label_get_code);
                btnCode.setEnabled(true);
                Util.showToast(SetPayPasswordActivity.this, Util.checkErrorType(error));
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
            if (!btnCode.isEnabled()) {
                btnCode.setEnabled(true);
                btnCode.setText(R.string.label_get_code);
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int secUntilFinished = (int) (millisUntilFinished / 1000);

            if (tv == btnCode) {
                tv.setText(getString(R.string.label_reload_security,
                        secUntilFinished > 9 ? secUntilFinished
                                : ("0" + secUntilFinished)));
            } else {
                tv.setText(getString(R.string.label_reload_code,
                        secUntilFinished > 9 ? secUntilFinished : ("0" + secUntilFinished)));
            }
        }
    }

    private void save() {
        hideKeyboard(null);
        String password = etPassword.getText().toString().trim();
        String code = editManName.getText().toString().trim();
        String confirmPassword = hintConfirmPayPassword.getText().toString().trim();
        tvTip.setTextColor(Color.parseColor("#c8c8cc"));
        tvTip.setText(R.string.label_password_tip1);
        if (Util.isEmpty(password)) {
            Util.showToast(this, R.string.msg_new_password_empty);
            return;
        }
        if (Util.isEmpty(confirmPassword)) {
            Util.showToast(this, R.string.msg_confirm_password_empty);
            return;
        }
        if (!confirmPassword.equals(password)) {
            Util.showToast(this, R.string.msg_user_password2);
            return;
        }
        if (confirmPassword.length() < 8 || confirmPassword.length() > 20) {
            tvTip.setTextColor(getResources().getColor(R.color.color_red));
            tvTip.setText(R.string.label_password_tip2);
            return;
        }
        if (!Util.checkPw(password)) {
            tvTip.setTextColor(getResources().getColor(R.color.color_red));
            tvTip.setText(R.string.label_password_tip2);
            return;
        }
        SetPayPwdApi api = new SetPayPwdApi();
        api.setMobile(bindedAccount);
        api.setNewPassword1(password);
        api.setCode(code);
        api.setNewPassword2(confirmPassword);
        loadingDialog.show();
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                loadingDialog.dismiss();
                user.setPayDanger(0);
                Util.showToast(SetPayPasswordActivity.this, R.string.msg_set_pay_password_ok);
                if (type == 1) {//跳回安全中心页面
                    Intent intent = new Intent(SetPayPasswordActivity.this, AccountSafeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else if (type == 2) {//跳回直播页面
                    setResult(RESULT_OK);
                } else if (type == 3) {//跳回确认下单页面
                    setResult(RESULT_OK);
                } else if (type == 4) {//跳回我的页面
                    setResult(RESULT_OK);
                } else if (type == 0) {
                    setResult(RESULT_OK);
                }
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                Util.showToast(SetPayPasswordActivity.this, Util.checkErrorType(error));
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
