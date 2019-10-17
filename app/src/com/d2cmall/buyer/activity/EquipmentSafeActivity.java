package com.d2cmall.buyer.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.PasswordLoginApi;
import com.d2cmall.buyer.api.SendAudioApi;
import com.d2cmall.buyer.api.SendCodeApi;
import com.d2cmall.buyer.api.SendDeviceApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.CartCountBean;
import com.d2cmall.buyer.bean.CountryBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ClearEditText;
import com.igexin.sdk.PushManager;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.YSFUserInfo;
import com.tendcloud.appcpa.TalkingDataAppCpa;
import com.zamplus.businesstrack.ZampAppAnalytics;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

import static com.d2cmall.buyer.Constants.RequestCode.COUNTRY_CODE_FAST_LOGIN;

/**
 * Created by rookie on 2018/3/21.
 */

public class EquipmentSafeActivity extends BaseActivity {

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
    @Bind(R.id.tv_notice)
    TextView tvNotice;
    @Bind(R.id.tv_number_notice)
    TextView tvNumberNotice;
    @Bind(R.id.et_bind_security_code)
    ClearEditText etBindSecurityCode;
    @Bind(R.id.register_security_tv)
    TextView registerSecurityTv;
    @Bind(R.id.register_security_rl)
    RelativeLayout registerSecurityRl;
    @Bind(R.id.tv_get_voice)
    TextView tvGetVoice;
    @Bind(R.id.ll_notice)
    LinearLayout llNotice;
    @Bind(R.id.btn_next)
    Button btnNext;
    @Bind(R.id.select_country)
    TextView selectCountry;
    private TimeDown timeDown;
    private String loginCode, nationCode, password, loginDevice;
    private boolean isActivityFinish = false;
    private boolean isSafe = false;
    private CountryBean countryBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_safe);
        ButterKnife.bind(this);
        loginCode = getIntent().getStringExtra("loginCode");
        nationCode = getIntent().getStringExtra("nationCode");
        password = getIntent().getStringExtra("passWord");
        loginDevice = getIntent().getStringExtra("deviceId");
        nameTv.setText("安全验证");
        tvNumberNotice.setText(Util.hidePhoneNum(loginCode));
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
        sendCode(registerSecurityTv);
    }


    @Override
    protected void onDestroy() {
        if (timeDown != null) {
            timeDown = null;
        }
        super.onDestroy();
    }

    private void sendAudio(final TextView codeTv) {
        hideKeyboard(null);
        timeDown = new TimeDown(60 * 1000, 1000);
        timeDown.tv = codeTv;
        timeDown.start();
        codeTv.setEnabled(false);
        registerSecurityTv.setEnabled(false);
        SendAudioApi api = new SendAudioApi();
        api.setMobile(loginCode);
        api.setSource(loginCode);
        api.setType("MemberMobile");
        api.setNationCode(nationCode);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                Util.showToast(EquipmentSafeActivity.this, R.string.msg_send_code_ok);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                timeDown.cancel();
                codeTv.setEnabled(true);
                codeTv.setText(R.string.label_get_code);
                registerSecurityTv.setEnabled(true);
                Util.showToast(EquipmentSafeActivity.this, Util.checkErrorType(error));
            }
        });
    }

    //发送验证码
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
        api.setMobile(loginCode);
        api.setSource(loginCode);
        api.setType("MemberMobile");
        api.setNationCode(nationCode);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                Util.showToast(EquipmentSafeActivity.this, R.string.msg_send_code_ok);
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
                tvGetVoice.setText(R.string.label_voice_code);
                Util.showToast(EquipmentSafeActivity.this, Util.checkErrorType(error));
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
                    tv.setText(R.string.label_voice_code);
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
                tvGetVoice.setText(R.string.label_voice_code);
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
                    tv.setText(getString(R.string.label_reload_security,
                            secUntilFinished > 9 ? secUntilFinished
                                    : ("0" + secUntilFinished)));
                }
            }
        }
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
                Toast toast = Toast.makeText(EquipmentSafeActivity.this, Util.checkErrorType(error), Toast.LENGTH_SHORT);
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

    private void loginSucceed(UserBean userBean) {
        //登录成功刷新未读消息数
        userBean.getData().getMember().setDanger(userBean.getData().getDanger());
        EventBus.getDefault().post(new ActionBean(Constants.ActionType.MESSAGE_ALL_UNREANED_CHANGE));
        //EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.LOGIN_OK));
        sendClientId(userBean);
        getCartNum();
        publishThird(userBean);
        setQiyuUserInfo(userBean);//设置七鱼用户信息
        //全局发登录成功通知
//            EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.LOGIN_OK));
//            EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.REFRESH_CART));
//            EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.CARTNUM, 1));
        setResult(RESULT_OK);
        finish();
    }

    private void next() {
        PasswordLoginApi api = new PasswordLoginApi();
        api.setLoginCode(loginCode);
        api.setLoginDevice(loginDevice);
        api.setPassword(password);
        api.setNationCode(nationCode);
        api.setCode(etBindSecurityCode.getText().toString().trim());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<UserBean>() {
            @Override
            public void onResponse(UserBean response) {
                btnNext.setEnabled(true);
                stat("V3登录/注册", "安全验证", null);
                D2CApplication.mSharePref.putSharePrefString(SharePrefConstant.TOKEN,
                        response.getData().getMember().getUserToken());
                Session.getInstance().saveUserToFile(EquipmentSafeActivity.this, response.getData().getMember());
                loginSucceed(response);
                Util.showToast(EquipmentSafeActivity.this, "验证成功!");
                setResult(RESULT_OK);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                btnNext.setEnabled(true);
                Util.showToast(EquipmentSafeActivity.this, Util.checkErrorType(error));
            }
        });

//
//        CheckCodeApi api = new CheckCodeApi();
//        api.setCode(etBindSecurityCode.getText().toString().trim());
//        api.setMobile(loginCode);
//        api.setNationCode(nationCode);
//        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
//            @Override
//            public void onResponse(BaseBean response) {
//                stat("登录和注册", "安全验证");
//                isSafe = true;
//                Util.showToast(EquipmentSafeActivity.this, "验证成功!");
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Util.showToast(EquipmentSafeActivity.this, Util.checkErrorType(error));
//            }
//        });
    }

    @OnClick({R.id.back_iv, R.id.register_security_tv, R.id.tv_get_voice, R.id.btn_next,R.id.select_country})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
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
            case R.id.btn_next:
                btnNext.setEnabled(false);
                next();
                break;
            case R.id.select_country:
                Intent intent = new Intent(this, CountryCodeActivity.class);
                startActivityForResult(intent, COUNTRY_CODE_FAST_LOGIN);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == Constants.RequestCode.COUNTRY_CODE_FAST_LOGIN) {
            countryBean = (CountryBean) data.getSerializableExtra("country");
            nationCode = countryBean.getMobileCode();
            selectCountry.setText(getString(R.string.label_login_nation_code, countryBean.getMobileCode()));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
