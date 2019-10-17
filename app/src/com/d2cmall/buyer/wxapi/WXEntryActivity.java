package com.d2cmall.buyer.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.BindPhoneActivity;
import com.d2cmall.buyer.api.SendDeviceApi;
import com.d2cmall.buyer.api.ShareOutApi;
import com.d2cmall.buyer.api.ThirdLoginApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Base64;
import com.d2cmall.buyer.util.HttpUtils;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.Util;
import com.igexin.sdk.PushManager;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.YSFUserInfo;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tendcloud.appcpa.TalkingDataAppCpa;
import com.tendcloud.tenddata.TCAgent;
import com.zamplus.businesstrack.ZampAppAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    public static String webUrl;
    public static String channel="Weixin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weixin);
        api = WXAPIFactory.createWXAPI(this, Constants.WEIXINAPPID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onResp(final BaseResp resp) {
        if (resp instanceof SendAuth.Resp) {
            SendAuth.Resp result = (SendAuth.Resp) resp;
            switch (resp.errCode){
                case BaseResp.ErrCode.ERR_OK://用户同意
                    String code = result.code;
                    String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" +
                            Constants.WEIXINAPPID + "&secret=" + Constants.WEIXINSECRET + "&code=" + code + "&grant_type=authorization_code";
                    HttpUtils.doGetAsyn(url, new HttpUtils.CallBack() {
                        @Override
                        public void onRequestComplete(String result) {
                            try {
                                JSONObject response = new JSONObject(result);
                                final String token = response.optString("access_token");
                                String openId = response.optString("openid");
                                String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openId;
                                HttpUtils.doGetAsyn(url, new HttpUtils.CallBack() {
                                    @Override
                                    public void onRequestComplete(String result) {
                                        try {
                                            JSONObject response = new JSONObject(result);
                                            final ThirdLoginApi api = new ThirdLoginApi();
                                            api.setInterPath(String.format(Constants.THIRD_LOGIN_URL, "WECHAT"));
                                            api.setAccessToken(token);
                                            api.setOpenId(response.optString("openid"));
                                            api.setUsername(response.optString("nickname"));
                                            api.setThirdHeadPic(response.optString("headimgurl"));
                                            api.setSex(response.optInt("sex") == 1 ? "男" : "女");
                                            api.setUnionId(response.optString("unionid"));
                                            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<UserBean>() {
                                                @Override
                                                public void onResponse(UserBean userBean) {
                                                    D2CApplication.mSharePref.putSharePrefString(SharePrefConstant.TOKEN,
                                                            userBean.getData().getMember().getUserToken());
                                                    Session.getInstance().saveUserToFile(WXEntryActivity.this, userBean.getData().getMember());
                                                    userBean.getData().getMember().setDanger(userBean.getData().getDanger());
                                                    stat("V3登录/注册","微信");
                                                    if (!userBean.getData().getMember().isD2c()) {
                                                        EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.WXBIND));
                                                    } else {
                                                        sendClientId(userBean);
                                                        publishThird(userBean);
                                                        setQiyuUserInfo(userBean);
                                                        EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.CARTNUM, 1));
                                                        EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.LOGIN_OK));
                                                    }

                                                    finish();
                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                }
                                            });
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                    finish();
                    break;
            }
        } else {
            if (resp.errCode == 0) {
                Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
                shareOut();
            } else {
                Toast.makeText(this, "发送失败", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

    private void shareOut(){
        if (Session.getInstance().getUserFromFile(this) != null) {
            if (Util.isEmpty(webUrl))
                return;
            long memberId=0;
            if (Session.getInstance().getUserFromFile(this)!=null){
                memberId=Session.getInstance().getUserFromFile(this).getId();
            }
            if (webUrl.equals(Constants.detailWebUrl)) {
                String param = "memberId=" + memberId + "&device=android&channel=" + channel + "&url=" + Constants.detailWebUrl;
                sendShareOut(param);
            } else {
                int index1 = webUrl.indexOf(".com");
                String url = "";
                if (index1 + 4 < webUrl.length()) {
                    url = webUrl.substring(index1 + 4, webUrl.length());
                }
                String param = "memberId=" + memberId + "&device=android&channel=" + channel + "&url=" + url;
                sendShareOut(param);
            }
        }
    }

    private void sendShareOut(String param) {
        ShareOutApi api = new ShareOutApi();
        api.setInterPath(Constants.SHARE_OUT_URL);
        api.setParam(Base64.encode(param.getBytes()));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.REFRESH_WEB));
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

    private void sendClientId(UserBean userBean) {
        //登录成功回传，并启动打开个推服务
        PushManager.getInstance().bindAlias(this,String.valueOf(userBean.getData().getMember().getMemberId()));
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
            }
        });
    }

    private void stat(String event,String label){
        StatService.onEvent(this,event,label);
        TCAgent.onEvent(this,event,label);
    }

}
