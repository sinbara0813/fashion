package com.d2cmall.buyer.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
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
import com.d2cmall.buyer.api.LogApi;
import com.d2cmall.buyer.api.PasswordLoginApi;
import com.d2cmall.buyer.api.PhoneCodeLoginApi;
import com.d2cmall.buyer.api.SendAudioApi;
import com.d2cmall.buyer.api.SendCodeApi;
import com.d2cmall.buyer.api.SendDeviceApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.api.ThirdLoginApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.CartCountBean;
import com.d2cmall.buyer.bean.CountryBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.http.HttpError;
import com.d2cmall.buyer.shareplatform.TencentHandle;
import com.d2cmall.buyer.shareplatform.TokenKeeper;
import com.d2cmall.buyer.shareplatform.WxHandle;
import com.d2cmall.buyer.shareplatform.tencent.TencentToken;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.HttpUtils;
import com.d2cmall.buyer.util.LogDB;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ClearEditText;
import com.d2cmall.buyer.widget.MeasuredLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.igexin.sdk.PushManager;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.YSFUserInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.tendcloud.appcpa.TalkingDataAppCpa;
import com.zamplus.businesstrack.ZampAppAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

import static com.d2cmall.buyer.Constants.RequestCode.COUNTRY_CODE_FAST_LOGIN;
import static com.d2cmall.buyer.Constants.RequestCode.REGISTER_CODE;
import static com.d2cmall.buyer.Constants.RequestCode.RESET_PASSWORD;

//登录页面


public class LoginActivity extends BaseActivity implements MeasuredLayout.OnKeyboardHideListener {


    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_register)
    TextView tvRegister;
    @Bind(R.id.select_country)
    TextView selectCountry;
    @Bind(R.id.et_account)
    ClearEditText etAccount;
    @Bind(R.id.account_ll)
    LinearLayout accountLl;
    @Bind(R.id.account_line)
    View accountLine;
    @Bind(R.id.account_rl)
    RelativeLayout accountRl;
    @Bind(R.id.password_line)
    View passwordLine;
    @Bind(R.id.password_see)
    ImageView passwordSee;
    @Bind(R.id.et_password)
    ClearEditText etPassword;
    @Bind(R.id.password_rl)
    RelativeLayout passwordRl;
    @Bind(R.id.et_security_code)
    ClearEditText etSecurityCode;
    @Bind(R.id.login_security)
    TextView loginSecurity;
    @Bind(R.id.security_rl)
    RelativeLayout securityRl;
    @Bind(R.id.login_code_rl)
    LinearLayout loginCodeRl;
    @Bind(R.id.login_btn)
    TextView loginBtn;
    @Bind(R.id.fast_login)
    TextView fastLogin;
    @Bind(R.id.forget_password)
    TextView forgetPassword;
    @Bind(R.id.wx_login)
    ImageView wxLogin;
    @Bind(R.id.qq_login)
    ImageView qqLogin;
    @Bind(R.id.wb_login)
    ImageView wbLogin;
    @Bind(R.id.third_login)
    LinearLayout thirdLogin;
    @Bind(R.id.login_ll)
    RelativeLayout loginLl;
    @Bind(R.id.register_account_iv)
    ImageView registerAccountIv;
    @Bind(R.id.register_account_ll)
    LinearLayout registerAccountLl;
    @Bind(R.id.register_account_rl)
    RelativeLayout registerAccountRl;
    @Bind(R.id.register_security_tv)
    TextView registerSecurityTv;
    @Bind(R.id.register_security_rl)
    RelativeLayout registerSecurityRl;
    @Bind(R.id.register_password_line)
    View registerPasswordLine;
    @Bind(R.id.register_password_iv)
    ImageView registerPasswordIv;
    @Bind(R.id.register_password_see)
    ImageView registerPasswordSee;
    @Bind(R.id.register_password_rl)
    RelativeLayout registerPasswordRl;
    @Bind(R.id.agreement)
    TextView agreement;
    @Bind(R.id.security_line)
    View securityLine;


    private boolean isFastLogin;
    private boolean loginPassWordSee;
    private CountryBean countryBean;
    private String nationCode;
    private Dialog loadingDialog;
    private TimeDown timeDown;

    private final int TYPE_NORMAL_LOGIN = 1;
    private final int TYPE_THIRD_LOGIN = 2;
    private SsoHandler mSsoHandler;
    private String getSecurityPhoneNumber, getLoginSecurityPhoneNumber;
    private AnimatorSet animatorSet;
    private UserBean.DataEntity.MemberEntity user;
    private String nowLoginCode, nowPassword, nowNationCode, nowLoginDevice;

    @Override
    protected void onNewIntent(Intent intent) {
        int type = intent.getIntExtra("type", -1);
        if (type > -1) {
            if (type == 0) {//注册返回
                setResult(RESULT_OK);
                finish();
            } else {//设置密码返回
                String account = intent.getStringExtra("account");
                etAccount.setText(account);
                etAccount.setSelection(account.length());
                etPassword.requestFocus();
            }
        }
        super.onNewIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);
        ButterKnife.bind(this);
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                initDefaultCountry();
            }
        }, 1000);
        //setEditTextInhibitInputSpace(etRegisterPassword);
        //setEditTextInhibitInputSpeChat(etRegisterPassword);
        String loginCode = "";
        if (!Util.isEmpty(loginCode)) {
            etAccount.requestFocus();
            etAccount.setText(loginCode);
            etAccount.setSelection(loginCode.length());
        }
        loadingDialog = DialogUtil.createLoadingDialog(this);
        if (!WxHandle.getInstance(this).isAppInstalled()) {
            wxLogin.setVisibility(View.GONE);
        }
        initWatcher();
        etAccount.postDelayed(new Runnable() {
            @Override
            public void run() {
                KeyboardUtil.showKeyboard(etAccount);
            }
        }, 500);

    }

    private void initWatcher() {
        etAccount.addTextChangedListener(textWatcher);
        etSecurityCode.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);
        etAccount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    accountLine.setBackgroundColor(getResources().getColor(R.color.color_black_bg1));
                }else{
                    accountLine.setBackgroundColor(getResources().getColor(R.color.line));
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
        etSecurityCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    securityLine.setBackgroundColor(getResources().getColor(R.color.color_black_bg1));
                }else{
                    securityLine.setBackgroundColor(getResources().getColor(R.color.line));
                }
            }
        });

    }

    private void setLoginBtnColor() {
        if(isFastLogin){
            if (!Util.isEmpty(etAccount.getText().toString()) && !Util.isEmpty(etSecurityCode.getText().toString())) {
                loginBtn.setBackgroundColor(getResources().getColor(R.color.color_black_bg1));
            } else {
                loginBtn.setBackgroundColor(Color.parseColor("#cccccc"));
            }
        }else{
            if (!Util.isEmpty(etAccount.getText().toString()) && !Util.isEmpty(etPassword.getText().toString())) {
                loginBtn.setBackgroundColor(getResources().getColor(R.color.color_black_bg1));
            } else {
                loginBtn.setBackgroundColor(Color.parseColor("#cccccc"));
            }
        }

    }


    public View getContentView() {
        MeasuredLayout measuredLayout = new MeasuredLayout(this, null, R.layout.activity_login_new);
        measuredLayout.setOnKeyboardHideListener(this);
        return measuredLayout;
    }

    private void initDefaultCountry() {//默认中国大陆
        String jsonStr = Util.readStreamToString(getResources().openRawResource(R.raw.country));
        Gson gson = new Gson();
        List<CountryBean> countryBeanList = gson.fromJson(jsonStr, new TypeToken<List<CountryBean>>() {
        }.getType());
        countryBean = countryBeanList.get(0);
        nationCode = countryBean.getMobileCode();
        selectCountry.setText(getString(R.string.label_login_nation_code, countryBean.getMobileCode()));
    }


    //短信验证码快捷登录
    private void gotoPhoneCodeLogin(String phone, final String phoneCode) {
        PhoneCodeLoginApi api = new PhoneCodeLoginApi();
        api.setLoginCode(phone);
        api.setCode(phoneCode);
        api.setNationCode(nationCode);
        loadingDialog.show();
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<UserBean>() {
            @Override
            public void onResponse(UserBean userBean) {
                stat("V3登录/注册", "快捷登录", null);
                loginCallback(userBean, TYPE_NORMAL_LOGIN);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                Toast toast = Toast.makeText(LoginActivity.this, Util.checkErrorType(error), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    /**
     * 禁止EditText输入空格
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpace(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" "))
                    return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * 禁止EditText输入特殊字符
     *
     * @param editText
     */
    public static void setEditTextInhibitInputSpeChat(EditText editText) {

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) return "";
                else return null;
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }



    private TextWatcher textWatcher = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean phoneTyped = etAccount.getText().toString().trim().length() > 0;
            if (phoneTyped) {
                loginSecurity.setEnabled(true);
                loginSecurity.setBackgroundColor(getResources().getColor(R.color.color_black_bg1));
            } else {
                loginSecurity.setEnabled(false);
                loginSecurity.setBackgroundColor(Color.parseColor("#cccccc"));
            }

        }

        public void afterTextChanged(Editable s) {
            setLoginBtnColor();
        }
    };

    //普通密码登录
    private void gotoPasswordLogin(String account, final String password) {
        PasswordLoginApi api = new PasswordLoginApi();
        api.setLoginCode(account);
        String md5Password = Util.getMD5(password).toLowerCase();
        String deviceId = Util.getDeviceId(this);
        api.setLoginDevice(deviceId);
        api.setPassword(md5Password);
        api.setNationCode(nationCode);

        nowLoginCode = account;
        nowPassword = md5Password;
        nowNationCode = nationCode;
        nowLoginDevice = deviceId;

        loadingDialog.show();
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<UserBean>() {
            @Override
            public void onResponse(UserBean userBean) {
                stat("V3登录/注册", "密码登录", null);
                loginCallback(userBean, TYPE_NORMAL_LOGIN);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                if (error instanceof HttpError) {
                    HttpError httpError = (HttpError) error;
                    int status = httpError.getErrorBean().getStatus();
                    if (status == -3) {
                        Intent intent = new Intent(LoginActivity.this, EquipmentSafeActivity.class);
                        intent.putExtra("loginCode", nowLoginCode);
                        intent.putExtra("nationCode", nowNationCode);
                        intent.putExtra("passWord", nowPassword);
                        intent.putExtra("deviceId", nowLoginDevice);
                        startActivityForResult(intent, 666);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                    } else {
                        Toast toast = Toast.makeText(LoginActivity.this, Util.checkErrorType(error), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(LoginActivity.this, Util.checkErrorType(error), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

            }
        });
    }

    @OnClick({ R.id.tv_register, R.id.agreement, R.id.password_see, R.id.login_security, R.id.forget_password,
            R.id.fast_login, R.id.login_btn,
            R.id.qq_login, R.id.wx_login, R.id.wb_login, R.id.back_iv, R.id.select_country})
    public void click(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.select_country:
                intent = new Intent(this, CountryCodeActivity.class);
                startActivityForResult(intent, COUNTRY_CODE_FAST_LOGIN);
                break;
            case R.id.back_iv:
                finish();
                break;
            case R.id.forget_password: //忘记密码
                if(isFastLogin){
                    final String string1 = etAccount.getText().toString().trim();
                    if (!Util.isMobileNo(countryBean.getMobileCode() + string1, countryBean.getRe())) {
                        Util.showToast(LoginActivity.this, R.string.msg_phone_error);
                        return;
                    }
                    new AlertDialog.Builder(this)
                            .setMessage(R.string.msg_voice_code)
                            .setPositiveButton("现在接听", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    getAudioCode(forgetPassword, string1, "MemberMobile");
                                    forgetPassword.setTextColor(Color.parseColor("#C8C8C8"));
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                }else{
                    stat("V3登录/注册", "忘记密码", null);
                    intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    String account = etAccount.getText().toString().trim();
                    if (!Util.isEmpty(account)) {
                        intent.putExtra("account", account);
                    }
                    startActivityForResult(intent, RESET_PASSWORD);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                }
                setLoginBtnColor();
                break;
            case R.id.agreement: //跳去注册协议
                Util.urlAction(LoginActivity.this, "http://m.d2cmall.com/page/regagreement");
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
            case R.id.login_security: //快捷登录获取验证码
                String string = etAccount.getText().toString().trim();
//                if (!Util.isMobileNo(countryBean.getMobileCode() + string, countryBean.getRe())) {
//                    Util.showToast(LoginActivity.this, R.string.msg_phone_error);
//                    return;
//                }
                getLoginSecurityPhoneNumber = string;
                getSecurity(loginSecurity, string);
                break;
            case R.id.login_btn: //登录
                String account1 = etAccount.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String phoneCode = etSecurityCode.getText().toString().trim();
                if (Util.isEmpty(account1)) {
                    Util.showToast(LoginActivity.this, R.string.msg_account_empty);
                    etAccount.requestFocus();
                    return;
                }
                if (!isFastLogin) {
                    if (Util.isEmpty(password)) {
                        Util.showToast(LoginActivity.this, R.string.msg_password_empty);
                        etPassword.requestFocus();
                        return;
                    }
                    gotoPasswordLogin(account1, password);
                } else {
                    if (Util.isEmpty(phoneCode)) {
                        Util.showToast(LoginActivity.this, R.string.hint_msg_security2);
                        etSecurityCode.requestFocus();
                        return;
                    }
                    if (!account1.equals(getLoginSecurityPhoneNumber)) {
                        Util.showToast(LoginActivity.this, R.string.msg_account_differ);
                        return;
                    }
                    gotoPhoneCodeLogin(account1, phoneCode);
                }
                break;
            case R.id.tv_register:
                intent = new Intent(this, RegisterActivity.class);
                startActivityForResult(intent, REGISTER_CODE);
                break;
            case R.id.fast_login:
                isFastLogin = !isFastLogin;
                if(isFastLogin){
                    tvTitle.setText("短信快捷登录");
                    fastLogin.setText("密码登录");
                    forgetPassword.setText("收不到验证码");
                    etAccount.setInputType(InputType.TYPE_CLASS_NUMBER);
                    selectCountry.setVisibility(View.VISIBLE);
                    passwordRl.setVisibility(View.INVISIBLE);
                    securityRl.setVisibility(View.VISIBLE);
                    //语音
//                audioLL.setVisibility(View.VISIBLE);
                }else{
                    tvTitle.setText("密码登录");
                    fastLogin.setText("短信快捷登录");
                    forgetPassword.setText("忘记密码");
                    forgetPassword.setVisibility(View.VISIBLE);
                    etAccount.setInputType(InputType.TYPE_CLASS_TEXT);
                    selectCountry.setVisibility(View.GONE);
                    passwordRl.setVisibility(View.VISIBLE);
                    securityRl.setVisibility(View.GONE);
                    //语音
//                audioLL.setVisibility(View.GONE);
                    isFastLogin = false;
                }
                break;
            case R.id.qq_login:
                qqLogin();
                break;
            case R.id.wx_login:
                loadingDialog.show();
                WxHandle.getInstance(this).login(this);
                loadingDialog.dismiss();
                break;
            case R.id.wb_login:
                sinaLogin();
                break;
        }
    }


    private void qqLogin() {
        TencentHandle tencentHandle = new TencentHandle();
        tencentHandle.initQQ(this);
        tencentHandle.getUserInfo(LoginActivity.this, new IUiListener() {

            @Override
            public void onError(UiError error) {
            }

            @Override
            public void onComplete(Object response) {
                JSONObject json = (JSONObject) response;
                TencentToken token = TokenKeeper.readQQToken(LoginActivity.this);
                String access_token = token.getAccess_token();
                String open_id = token.getOpenid();
                ThirdLoginApi api = new ThirdLoginApi();
                api.setInterPath(String.format(Constants.THIRD_LOGIN_URL, "QQ"));
                api.setAccessToken(access_token);
                api.setOpenId(open_id);
                api.setUsername(json.optString("nickname"));
                api.setThirdHeadPic(json.optString("figureurl_qq_1"));
                api.setSex(json.optString("gender"));
                stat("V3登录/注册", "QQ", null);
                thirdLogin(api);
            }

            @Override
            public void onCancel() {
            }
        });
    }

    private void loginCallback(UserBean userBean, int type) {//登录回调
        userBean.getData().getMember().setDanger(userBean.getData().getDanger());
        loadingDialog.dismiss();
        D2CApplication.mSharePref.putSharePrefString(SharePrefConstant.TOKEN,
                userBean.getData().getMember().getUserToken());
        if (!userBean.getData().getMember().isD2c()) {
            if (type == TYPE_THIRD_LOGIN) {
                Intent intentBind = new Intent(LoginActivity.this, BindPhoneActivity.class);
                intentBind.putExtra("flag", 2);
                startActivityForResult(intentBind, Constants.RequestCode.BIND_PHONE);
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
            }
        } else {
            Session.getInstance().saveUserToFile(LoginActivity.this, userBean.getData().getMember());
            loginSucceed(userBean);
        }

        postLog();
    }

    private void loginSucceed(UserBean userBean) {
        //登录成功刷新未读消息数
        EventBus.getDefault().post(new ActionBean(Constants.ActionType.MESSAGE_ALL_UNREANED_CHANGE));
        //EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.LOGIN_OK));
        sendClientId(userBean);
        getCartNum();
        publishThird(userBean);
        setQiyuUserInfo(userBean);//设置七鱼用户信息
        //全局发登录成功通知
        EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.LOGIN_OK));
//            EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.REFRESH_CART));
//            EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.CARTNUM, 1));
        setResult(RESULT_OK);
        finish();
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
                Toast toast = Toast.makeText(LoginActivity.this, Util.checkErrorType(error), Toast.LENGTH_SHORT);
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

    private void thirdLogin(ThirdLoginApi api) {
        loadingDialog.show();
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<UserBean>() {
            @Override
            public void onResponse(UserBean userBean) {
                loginCallback(userBean, TYPE_THIRD_LOGIN);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                Toast toast = Toast.makeText(LoginActivity.this, Util.checkErrorType(error), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    private void sinaLogin() {
        mSsoHandler = new SsoHandler(this);
        mSsoHandler.authorize(new WbAuthListener() {
            @Override
            public void onSuccess(Oauth2AccessToken token) {
                String a_token = token.getToken();
                String uid = token.getUid();
                String url = "https://api.weibo.com/2/users/show.json?access_token=" + a_token + "&uid=" + uid;
                final ThirdLoginApi api = new ThirdLoginApi();
                api.setInterPath(String.format(Constants.THIRD_LOGIN_URL, "SINA"));
                api.setAccessToken(a_token);
                api.setUsername(token.getBundle().getString("userName"));
                if (Util.isEmpty(token.getBundle().getString("userName"))) {
                    api.setUsername(uid);
                }
                api.setUnionId(uid);
                api.setOpenId(uid);
                HttpUtils.doGetAsyn(url, new HttpUtils.CallBack() {
                    @Override
                    public void onRequestComplete(final String result) {
                        JSONObject response = null;
                        try {
                            response = new JSONObject(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String icon = response.optString("avatar_large");
                        String gender = response.optString("gender");
                        String name = response.optString("name");
                        if (gender.equals("f")) {
                            api.setSex("女");
                        } else {
                            api.setSex("男");
                        }
                        api.setUsername(name);
                        api.setThirdHeadPic(icon);
                        etPassword.post(new Runnable() {
                            @Override
                            public void run() {
                                stat("V3登录/注册", "微博", null);
                                thirdLogin(api);
                            }
                        });
                    }
                });
            }

            @Override
            public void cancel() {

            }

            @Override
            public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {

            }
        });
    }

    @Subscribe
    public void onEvent(GlobalTypeBean bean) {
        if (bean.getType() == Constants.GlobalType.CARTNUM) {
            setResult(RESULT_OK);
            finish();
        } else if (bean.getType() == Constants.GlobalType.WXBIND) {
            Intent intentBind = new Intent(LoginActivity.this, BindPhoneActivity.class);
            intentBind.putExtra("flag", 2);
            startActivityForResult(intentBind, Constants.RequestCode.BIND_PHONE);
            overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
        }
    }

    private void getSecurity(final TextView tv, final String mobile) {
        timeDown = new TimeDown(60 * 1000, 1000);
        timeDown.codeTv = tv;
        timeDown.start();
        tv.setEnabled(false);
        if (tv == loginSecurity) {
            forgetPassword.setEnabled(false);
        }
        SendCodeApi api = new SendCodeApi();
        api.setMobile(mobile);
        api.setSource(mobile);
        api.setType("MemberMobile");
        api.setNationCode(nationCode);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                if(etAccount.isFocused()){
                    etAccount.clearFocus();
                }
                KeyboardUtil.showKeyboard(etSecurityCode);
                Util.showToast(LoginActivity.this, R.string.msg_send_code_ok);
                etSecurityCode.requestFocus();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv.setEnabled(true);
                tv.setText(R.string.label_get_code);
                if (tv == loginSecurity) {
                    forgetPassword.setEnabled(true);
                }
                Toast toast = Toast.makeText(LoginActivity.this, Util.checkErrorType(error), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                etAccount.addTextChangedListener(textWatcher);
            }
        });
    }

    private void getAudioCode(final TextView tv, final String mobile, final String type) {
        timeDown = new TimeDown(60 * 1000, 1000);
        timeDown.codeTv = tv;
        timeDown.start();
        tv.setEnabled(false);
        if (tv == forgetPassword) {
            loginSecurity.setEnabled(false);
        }
        SendAudioApi api = new SendAudioApi();
        api.setMobile(mobile);
        api.setSource(mobile);
        api.setType(type);
        api.setNationCode(nationCode);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                if (type.equals("REGISTER")) {
                    getSecurityPhoneNumber = mobile;
                } else if (type.equals("MemberMobile")) {
                    getLoginSecurityPhoneNumber = mobile;
                }
                Util.showToast(LoginActivity.this, R.string.msg_send_code_ok);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tv.setEnabled(true);
                tv.setText(R.string.label_get_code);
                if (tv == forgetPassword) {
                    loginSecurity.setEnabled(true);
                }
                Toast toast = Toast.makeText(LoginActivity.this, Util.checkErrorType(error), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                etAccount.addTextChangedListener(textWatcher);
            }
        });
    }


    @Override
    public void onKeyboardHide(boolean hide) {
        if (hide) {
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            ObjectAnimator loginAnimator = ObjectAnimator.ofFloat(loginLl, "translationY", -ScreenUtil.dip2px(170), 0);
            List<Animator> animators = new ArrayList<>();
            animators.add(loginAnimator);
            animatorSet = new AnimatorSet();
            animatorSet.playTogether(animators);
            animatorSet.setDuration(200);
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animatorSet.start();
            thirdLogin.setVisibility(View.VISIBLE);
            agreement.setVisibility(View.VISIBLE);
        } else {
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            ObjectAnimator loginAnimator = ObjectAnimator.ofFloat(loginLl, "translationY", 0, -ScreenUtil.dip2px(170));
            List<Animator> animators = new ArrayList<>();
            animators.add(loginAnimator);
            animatorSet = new AnimatorSet();
            animatorSet.playTogether(animators);
            animatorSet.setDuration(200);
            animatorSet.start();
            thirdLogin.setVisibility(View.INVISIBLE);
            agreement.setVisibility(View.INVISIBLE);
        }
    }

    public class TimeDown extends CountDownTimer {

        public TextView codeTv;

        public TimeDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            if (codeTv != null) {
                codeTv.setEnabled(true);
                if (codeTv == forgetPassword) {
                    forgetPassword.setTextColor(getResources().getColor(R.color.color_black_bg1));
                    codeTv.setText(R.string.label_try_code);
                } else {
                    codeTv.setText(R.string.label_get_code);
                }
            }
            if (!forgetPassword.isEnabled()) {
                forgetPassword.setEnabled(true);
                forgetPassword.setTextColor(getResources().getColor(R.color.color_black_bg1));//#FFFDC33E
                forgetPassword.setText(R.string.label_try_code);
            }
            if (!loginSecurity.isEnabled()) {
                loginSecurity.setEnabled(true);
                loginSecurity.setText(R.string.label_get_code);
            }
            if (timeDown != null) {
                timeDown.cancel();
                timeDown = null;
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (codeTv != null) {
                codeTv.setEnabled(false);
                int secUntilFinished = (int) (millisUntilFinished / 1000);
                if(codeTv==forgetPassword){
                    codeTv.setText(getString(R.string.label_reload_code,
                            secUntilFinished > 9 ? secUntilFinished
                                    : ("0" + secUntilFinished)));
                }else{
                    codeTv.setText(getString(R.string.label_reload_security,
                            secUntilFinished > 9 ? secUntilFinished
                                    : ("0" + secUntilFinished)));
                }

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.RequestCode.BIND_PHONE:
                    setResult(RESULT_OK);
                    finish();
                    break;
                case Constants.RequestCode.COUNTRY_CODE_FAST_LOGIN:
                    countryBean = (CountryBean) data.getSerializableExtra("country");
                    nationCode = countryBean.getMobileCode();
                    selectCountry.setText(getString(R.string.label_login_nation_code, countryBean.getMobileCode()));
                    break;
                case RESET_PASSWORD:
                    String account = data.getStringExtra("account");
                    String password = data.getStringExtra("password");
                    gotoPasswordLogin(account, password);
                    break;
                case REGISTER_CODE:
                    finish();
                    break;
                case 666://验证成功,回来将用户数据存起来
                    setResult(RESULT_OK);
                    finish();
                    break;
            }
        }
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        if (timeDown != null) {
            timeDown.cancel();
            timeDown = null;
        }
        if (etAccount != null) {
            etAccount.removeTextChangedListener(textWatcher);
        }
        if (etPassword != null) {
            etPassword.removeTextChangedListener(textWatcher);
        }

        super.onDestroy();
    }
}