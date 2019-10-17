package com.d2cmall.buyer.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.LiveHeanAdapter;
import com.d2cmall.buyer.adapter.RongMessageListAdapter;
import com.d2cmall.buyer.api.AnchorInfoApi;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.CloseLiveApi;
import com.d2cmall.buyer.api.LiveCouponApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.LiveAnimationBean;
import com.d2cmall.buyer.bean.LiveAudienceInBean;
import com.d2cmall.buyer.bean.LiveCouponNewBean;
import com.d2cmall.buyer.bean.LiveListBean;
import com.d2cmall.buyer.bean.PersonInfoBean;
import com.d2cmall.buyer.bean.PresentMessage;
import com.d2cmall.buyer.bean.RongTokenBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.bean.WatchInfoBean;
import com.d2cmall.buyer.bean.WebMessage;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DialogClickListener;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.AnchorRecommendGoodPop;
import com.d2cmall.buyer.widget.CrowdRecommendGoodPop;
import com.d2cmall.buyer.widget.GiftLayout.CustormAnim;
import com.d2cmall.buyer.widget.GiftLayout.GiftControl;
import com.d2cmall.buyer.widget.LiveRelativeLayout;
import com.d2cmall.buyer.widget.SharePop;
import com.d2cmall.buyer.widget.ShowPopImageView;
import com.d2cmall.buyer.widget.TransparentPop;
import com.d2cmall.buyer.widget.WrapViewPager;
import com.d2cmall.buyer.widget.heartLayout.PeriscopeLayout;
import com.qiniu.pili.droid.streaming.AVCodecType;
import com.qiniu.pili.droid.streaming.CameraStreamingSetting;
import com.qiniu.pili.droid.streaming.MediaStreamingManager;
import com.qiniu.pili.droid.streaming.StreamingProfile;
import com.qiniu.pili.droid.streaming.StreamingSessionListener;
import com.qiniu.pili.droid.streaming.StreamingState;
import com.qiniu.pili.droid.streaming.StreamingStateChangedListener;
import com.qiniu.pili.droid.streaming.widget.AspectFrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import cn.dreamtobe.kpswitch.widget.KPSwitchPanelRelativeLayout;
import de.greenrobot.event.EventBus;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.CommandNotificationMessage;
import io.rong.message.InformationNotificationMessage;
import io.rong.message.TextMessage;
import jp.wasabeef.glide.transformations.BlurTransformation;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import static com.d2cmall.buyer.Constants.LEAD_COUPON_URL;
import static com.d2cmall.buyer.Constants.LIVE_COUPON_GROUP_GET;
import static com.qiniu.pili.droid.streaming.CameraStreamingSetting.FOCUS_MODE_CONTINUOUS_VIDEO;

/**
 * Created by rookie on 2017/12/28.
 */

public class LiveAnchorActivity extends BaseActivity implements StreamingStateChangedListener, StreamingSessionListener {

    @Bind(R.id.cameraPreview_surfaceView)
    GLSurfaceView cameraPreviewSurfaceView;
    @Bind(R.id.cameraPreview_afl)
    AspectFrameLayout cameraPreviewAfl;
    @Bind(R.id.img_close_tag)
    ImageView imgCloseTag;
    @Bind(R.id.img_logo_tag)
    ImageView imgLogoTag;
    @Bind(R.id.logo_tag_ll)
    LinearLayout logoTagLl;
    @Bind(R.id.panel_layout)
    KPSwitchPanelRelativeLayout panelLayout;
    @Bind(R.id.img_loading1)
    ImageView imgLoading1;
    @Bind(R.id.not_network_layout)
    RelativeLayout notNetworkLayout;
    @Bind(R.id.img_avatar)
    ImageView imgAvatar;
    @Bind(R.id.tv_anchor_name)
    TextView tvAnchorName;
    @Bind(R.id.tv_view_count)
    TextView tvViewCount;
    //开启消息推送行为节点
    @Bind(R.id.iv_care)
    ShowPopImageView ivCare;
    @Bind(R.id.avatar_layout)
    LinearLayout avatarLayout;
    @Bind(R.id.pic_layout)
    RecyclerView picLayout;
    @Bind(R.id.img_close)
    ImageView imgClose;
    @Bind(R.id.top_layout)
    RelativeLayout topLayout;
    @Bind(R.id.tv_live_quality)
    TextView tvLiveQuality;
    @Bind(R.id.top_live_layout)
    LinearLayout topLiveLayout;
    @Bind(R.id.heart_layout)
    PeriscopeLayout heartLayout;
    @Bind(R.id.img_buy)
    GifImageView imgBuy;
    @Bind(R.id.tv_red_point)
    TextView tvRedPoint;
    @Bind(R.id.left_rl)
    RelativeLayout leftRl;
    @Bind(R.id.tv_msg)
    TextView tvMsg;
    @Bind(R.id.img_share)
    ImageView imgShare;
    @Bind(R.id.img_setting)
    ImageView imgSetting;
    @Bind(R.id.img_gift)
    ImageView imgGift;
    @Bind(R.id.right_ll)
    LinearLayout rightLl;
    @Bind(R.id.bottom_layout)
    RelativeLayout bottomLayout;
    @Bind(R.id.chat_listview)
    ListView chatListview;
    @Bind(R.id.tv_coupon_label)
    TextView tvCouponLabel;
    @Bind(R.id.tv_coupon_rmb)
    TextView tvCouponRmb;
    @Bind(R.id.tv_coupon_amount)
    TextView tvCouponAmount;
    @Bind(R.id.tv_coupon_limit)
    TextView tvCouponLimit;
    @Bind(R.id.coupon_content_layout)
    LinearLayout couponContentLayout;
    @Bind(R.id.coupon_line_view)
    View couponLineView;
    @Bind(R.id.btn_get_coupon)
    TextView btnGetCoupon;
    @Bind(R.id.coupone_normal_layout)
    LinearLayout couponeNormalLayout;
    @Bind(R.id.btn_timeout)
    TextView btnTimeout;
    @Bind(R.id.coupon_timeout_layout)
    LinearLayout couponTimeoutLayout;
    @Bind(R.id.coupon_container_layout)
    RelativeLayout couponContainerLayout;
    @Bind(R.id.gift_layout)
    LinearLayout giftLayout;
    @Bind(R.id.price)
    TextView tvPrice;
    @Bind(R.id.recommend_good_pic)
    ImageView recommendGoodPic;
    @Bind(R.id.recommend_good_layout)
    RelativeLayout recommendGoodLayout;
    @Bind(R.id.presents_top_view)
    View presentsTopView;
    @Bind(R.id.viewPager)
    WrapViewPager viewPager;
    @Bind(R.id.dot_layout)
    LinearLayout dotLayout;
    @Bind(R.id.tv_accout_money)
    TextView tvAccoutMoney;
    @Bind(R.id.tv_status_bind_phone)
    TextView tvStatusBindPhone;
    @Bind(R.id.tv_status_despoit)
    TextView tvStatusDespoit;
    @Bind(R.id.tv_status_despoit_nopwd)
    TextView tvStatusDespoitNopwd;
    @Bind(R.id.tv_free_send)
    ImageView tvFreeSend;
    @Bind(R.id.tv_present_send)
    TextView tvPresentSend;
    @Bind(R.id.tv_present_send_unenable)
    TextView tvPresentSendUnenable;
    @Bind(R.id.tv_setting_pay_pwd)
    TextView tvSettingPayPwd;
    @Bind(R.id.account_bottom_layout)
    LinearLayout accountBottomLayout;
    @Bind(R.id.presents_layout)
    LinearLayout presentsLayout;
    @Bind(R.id.img_loading)
    ImageView imgLoading;
    @Bind(R.id.img_loading_logo)
    ImageView imgLoadingLogo;
    @Bind(R.id.img_loading_logo_tv)
    TextView imgLoadingLogoTv;
    @Bind(R.id.img_loading_close)
    ImageView imgLoadingClose;
    @Bind(R.id.loading_layout)
    RelativeLayout loadingLayout;
    @Bind(R.id.seekbar_polish)
    SeekBar seekbarPolish;
    @Bind(R.id.seekbar_white)
    SeekBar seekbarWhite;
    @Bind(R.id.seekbar_red)
    SeekBar seekbarRed;
    @Bind(R.id.tb_light)
    Switch tbLight;
    @Bind(R.id.tb_front)
    Switch tbFront;
    @Bind(R.id.setting_pop_layout)
    LinearLayout settingPopLayout;
    @Bind(R.id.setting_container_layout)
    RelativeLayout settingContainerLayout;
    @Bind(R.id.img_close_off)
    ImageView imgCloseOff;
    @Bind(R.id.img_head_off)
    ImageView imgHeadOff;
    @Bind(R.id.tv_anchor_name_off)
    TextView tvAnchorNameOff;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_count_fans)
    TextView tvCountFans;
    @Bind(R.id.tv_count_watched)
    TextView tvCountWatched;
    @Bind(R.id.btn_focus)
    ImageView btnFocus;
    @Bind(R.id.live_off_layout)
    RelativeLayout liveOffLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.input_top_layout)
    View inputTopLayout;
    @Bind(R.id.toggleButton)
    ToggleButton toggleButton;
    @Bind(R.id.et_message)
    EditText etMessage;
    @Bind(R.id.tv_send)
    TextView tvSend;
    @Bind(R.id.input_panel_layout)
    LinearLayout inputPanelLayout;
    @Bind(R.id.input_layout)
    RelativeLayout inputLayout;
    @Bind(R.id.tv_reload)
    TextView tvReload;
    @Bind(R.id.not_network_layout1)
    LinearLayout notNetworkLayout1;
    @Bind(R.id.container_layout)
    LiveRelativeLayout containerLayout;
    @Bind(R.id.iv_gif)
    ImageView ivGif;
    @Bind(R.id.img_cover_off)
    ImageView imgCoverOff;

    public static final String JOINCHARROOM = "joinCharRoom";

    public static final String QUITCHARROOM = "quitCharRoom";

    public static final String AUTHOREXIT = "authorExit";

    public static final String BUY = "BUY";

    public static final String ADDCART = "ADDCART";

    public static final String SHARE = "SHARE";

    public static final String FOLLOW = "FOLLOW";

    public final String DANMU = "Danmaku";

    protected String mPublishTitle;

    protected String mPublishStreamID;

    protected String mChannel;

    private boolean sendStrSuccess;

    //是否前置摄像头
    protected boolean mEnableFrontCam = true;

    private boolean isOutTime = false;

    //polishStep磨皮系数,whiteStep美白系数,redStep红润系数
    protected float polishStep = 0, whiteStep = 0, redStep = 0;

    //闪光灯是否打开
    protected boolean isLightOn = false;

    //推流清晰度
    private int videoQuantity = 0;

    protected UserBean.DataEntity.MemberEntity user;

    protected List<Message> messages;

    protected RongMessageListAdapter messageAdapter;

    private GifDrawable gifFromAssets = null;

    private long firstTime;

    protected Handler handler;

    private boolean isJustCoupon;

    private MediaStreamingManager mMediaStreamingManager;
    private StreamingProfile mProfile;

    protected LiveListBean.DataBean.LivesBean.ListBean liveBean;//创建推流的相关信息

    protected CrowdRecommendGoodPop crowdRecommendGoodPop;
    protected AnchorRecommendGoodPop anchorRecommendGoodPop;
    private CameraStreamingSetting setting;
    protected int[] hearts = new int[]{R.mipmap.icon_zhibo_xin_bao, R.mipmap.icon_zhibo_xin_fen,
            R.mipmap.icon_zhibo_xin_huang, R.mipmap.icon_zhibo_xin_kouhong, R.mipmap.icon_zhibo_xin_kuzi, R.mipmap.icon_zhibo_xin_lan, R.mipmap.icon_zhibo_xin_qunzi, R.mipmap.icon_zhibo_xin_yifu, R.mipmap.icon_zhibo_xin_zi};

    private ArrayList<LiveAudienceInBean.DataBean.RealtimeBean.HeadersBean> headBeanArrayList;
    private LiveHeanAdapter liveHeanAdapter;
    private boolean isFirstTimeStreaming = true;
    private Animation mShowAction, mHiddenAction;
    private PersonInfoBean AnchorInfoBean;
    private GiftControl giftControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_anchor_activity);
        ButterKnife.bind(this);
        heartLayout.setDrawables(hearts);
        //拿到开启直播页面设置的数据
        initExtraData(savedInstanceState);
        initAnimation();
        //初始化相关操作
        initBaseViews();
        //设置主播信息
        initAnchorInfo();
        //推流初始化
        initManager();
        //头像
        AnchorInBack();
        //融云消息监听器
        initListener();
    }

    private void AnchorInBack() {
        Log.e("live", "AnchorInBack");
        //主播进入房间
        SimpleApi api = new SimpleApi();
        api.setMethod(BaseApi.Method.POST);
        api.setInterPath(String.format(Constants.LIVE_CUSTOM_IN, liveBean.getId()));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<LiveAudienceInBean>() {
            @Override
            public void onResponse(LiveAudienceInBean baseBean) {
                if (baseBean.getData() != null) {
                    int watchCount = baseBean.getData().getRealtime().getCount();
                    tvViewCount.setText(getString(R.string.label_live_watching, watchCount));
                    for (int i = 0; i < baseBean.getData().getRealtime().getHeaders().size(); i++) {
                        if (liveBean.getMemberId() == baseBean.getData().getRealtime().getHeaders().get(i).getMemberId()) {
                            baseBean.getData().getRealtime().getHeaders().remove(i);
                        }
                    }
                    headBeanArrayList.addAll(0, baseBean.getData().getRealtime().getHeaders());
                    liveHeanAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("PUBLISH_TITLE", mPublishTitle);
        outState.putBoolean("ENABLE_FRONT_CAM", mEnableFrontCam);
        outState.putBoolean("ENABLE_LIGHT_ON", isLightOn);
        outState.putFloat("WHITE_STEP", whiteStep);
        outState.putFloat("POLISH_STEP", polishStep);
        outState.putFloat("RED_STEP", redStep);
        outState.putInt("VIDEO_QUALITY", videoQuantity);
        outState.putSerializable("LIVE_BEAN", liveBean);
    }


    private void initAnimation() {
        Log.e("live", "initAnimation");
        mShowAction = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mShowAction.setDuration(350);
        mHiddenAction = new ScaleAnimation(1, 0, 1, 0,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mHiddenAction.setDuration(350);
    }


    //liveTitle:直播标题,isFront:摄像头是否前置,isLightOn:闪光灯是否打开,
    // whiteStep:美白系数,polishStep:磨皮系数,redStep:红润系数,videoQuality:推流清晰度,liveBean:创建推流相关信息
    public static void actionStart(Activity activity, String liveTitle,
                                   boolean isFront, boolean isLightOn, float whiteStep, float polishStep,
                                   float redStep, int videoQuality, LiveListBean.DataBean.LivesBean.ListBean liveBean) {
        Intent intent = new Intent(activity, LiveAnchorActivity.class);
        intent.putExtra("PUBLISH_TITLE", liveTitle);
        intent.putExtra("ENABLE_FRONT_CAM", isFront);
        intent.putExtra("ENABLE_LIGHT_ON", isLightOn);
        intent.putExtra("WHITE_STEP", whiteStep);
        intent.putExtra("POLISH_STEP", polishStep);
        intent.putExtra("RED_STEP", redStep);
        intent.putExtra("VIDEO_QUALITY", videoQuality);
        intent.putExtra("LIVE_BEAN", liveBean);
        activity.startActivity(intent);
    }

    private void initAnchorInfo() {
        Log.e("live", "initAnchorInfo");
        initLiveInfo();
        ivCare.setVisibility(View.GONE);
        imgSetting.setVisibility(View.VISIBLE);
        imgBuy.setVisibility(View.VISIBLE);
        imgGift.setVisibility(View.GONE);
        tvRedPoint.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 10);
            }
        }
        if (liveBean == null) {
            return;
        }
        UniversalImageLoader.displayRoundImage(this, Util.getD2cPicUrl(liveBean.getHeadPic()),
                imgAvatar, R.mipmap.ic_default_avatar);
        tvAnchorName.setText(liveBean.getNickname());
        getCoupon();
    }

    protected void sendTextMessage(final String content, final boolean isDanMu) {
        sendStrSuccess = false;
        TextMessage textMessage = new TextMessage(content);
        Uri uri = null;
        String head = user.getHead();
        if (!Util.isEmpty(head)) {
            uri = Uri.parse(Util.getD2cPicUrl(head));
        }
        UserInfo info = new UserInfo(String.valueOf(user.getId()), user.getNickname(), uri);
        textMessage.setUserInfo(info);
        if (isDanMu) {
            textMessage.setExtra(DANMU);
        }
        RongIMClient.getInstance().sendMessage(Conversation.ConversationType.CHATROOM, mChannel, textMessage, null, null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
            }

            @Override
            public void onSuccess(Message message) {
                if (!sendStrSuccess) {
                    sendStrSuccess = true;
                    etMessage.setText("");
                    if (isDanMu) {
                        if (System.currentTimeMillis() - firstTime > 3000) {
                            firstTime = System.currentTimeMillis();
                        }
                    } else {
                        showMessageList(message);
                        hideKeyboard(null);
                        inputLayout.setVisibility(View.GONE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                bottomLayout.setVisibility(View.VISIBLE);
                            }
                        }, 200);
                    }
                }
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
            }
        });
    }

    private void getCoupon() {
        LiveCouponApi api = new LiveCouponApi();
        api.setLiveId(String.valueOf(liveBean.getId()));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<LiveCouponNewBean>() {
            @Override
            public void onResponse(final LiveCouponNewBean response) {
                if (response.getData().getCoupon() != null) {
                    isJustCoupon = true;
                    if (response.getData().getCoupon().isIsClaim()) {
                        couponContainerLayout.setVisibility(View.VISIBLE);
                    } else {
                        couponContainerLayout.setVisibility(View.GONE);
                    }
                } else if (response.getData().getCouponGroup() != null) {
                    isJustCoupon = false;
                    if (response.getData().getCouponGroup().isClaim()) {
                        couponContainerLayout.setVisibility(View.VISIBLE);
                    } else {
                        couponContainerLayout.setVisibility(View.GONE);
                    }
                }
                couponContainerLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SimpleApi api = new SimpleApi();
                        api.setMethod(BaseApi.Method.POST);
                        if (isJustCoupon) {
                            api.setInterPath(String.format(LEAD_COUPON_URL, response.getData().getCoupon().getId()));
                        } else {
                            api.setInterPath(String.format(LIVE_COUPON_GROUP_GET, response.getData().getCouponGroup().getId()));
                        }
                        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                            @Override
                            public void onResponse(BaseBean response) {
                                Util.showToast(LiveAnchorActivity.this, "领取成功");
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Util.showToast(LiveAnchorActivity.this, Util.checkErrorType(error));
                            }
                        });
                    }
                });
                if (isJustCoupon) {
                    if (response.getData().getCoupon().getType().equals("DISCOUNT")) {
                        tvCouponAmount.setText(response.getData().getCoupon().getAmount() / 10 + "折");
                    } else {
                        tvCouponAmount.setText("¥" + String.valueOf(response.getData().getCoupon().getAmount()));
                    }
                } else {
                    tvCouponAmount.setText("礼券包");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void sendMessage() {
        if (toggleButton.isChecked()) {
            //发送弹幕
            String content = etMessage.getText().toString().trim();
            if (Util.isEmpty(content)) {
                Util.showToast(this, R.string.msg_input_empty);
                return;
            }
            try {
                byte[] byte_num = content.getBytes("utf8");
                if (byte_num.length > 100) {
                    Util.showToast(this, R.string.msg_input_too_long2);
                    return;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return;
            }
            sendTextMessage(content, true);
        } else {
            //发送文字
            String content = etMessage.getText().toString().trim();
            if (Util.isEmpty(content)) {
                Util.showToast(this, R.string.msg_input_empty);
                return;
            }
            try {
                byte[] byte_num = content.getBytes("utf8");
                if (byte_num.length > 160) {
                    Util.showToast(this, R.string.msg_input_too_long);
                    return;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return;
            }
            sendTextMessage(content, false);
        }
        hideKeyboard(null);
        inputLayout.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                bottomLayout.setVisibility(View.VISIBLE);
            }
        }, 200);
    }

    private void initSetting() {
        //初始化设置pop
        seekbarPolish.setProgress((int) polishStep * 10);
        seekbarPolish.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //磨皮
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                CameraStreamingSetting.FaceBeautySetting fbSetting = setting.getFaceBeautySetting();
                fbSetting.beautyLevel = (float) (seekBar.getProgress()) / 10;
                polishStep = fbSetting.beautyLevel;
                mMediaStreamingManager.updateFaceBeautySetting(fbSetting);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        seekbarRed.setProgress((int) redStep * 10);
        seekbarRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                CameraStreamingSetting.FaceBeautySetting fbSetting = setting.getFaceBeautySetting();
                fbSetting.redden = (float) (seekBar.getProgress()) / 10;
                redStep = fbSetting.redden;
                mMediaStreamingManager.updateFaceBeautySetting(fbSetting);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekbarWhite.setProgress((int) whiteStep * 10);
        seekbarWhite.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //美白
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                CameraStreamingSetting.FaceBeautySetting fbSetting = setting.getFaceBeautySetting();
                fbSetting.whiten = (float) (seekBar.getProgress()) / 10;
                whiteStep = fbSetting.whiten;
                mMediaStreamingManager.updateFaceBeautySetting(fbSetting);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        tbLight.setChecked(isLightOn);
        tbLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mMediaStreamingManager != null) {
                    if (isChecked) {
                        mMediaStreamingManager.turnLightOn();
                    } else {
                        mMediaStreamingManager.turnLightOff();
                    }
                }
            }
        });
        tbFront.setChecked(!mEnableFrontCam);
        tbFront.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mMediaStreamingManager != null) {
                    mMediaStreamingManager.switchCamera();
                }
            }
        });
    }

    private void notifyMessage(Message message) {
        messages.add(message);
        messageAdapter.notifyDataSetChanged();
        chatListview.setSelection(messages.size() - 1);
    }

    protected void showMessageList(final Message message) {
        if (message.getContent() instanceof InformationNotificationMessage) {
            final InformationNotificationMessage notificationMessage = (InformationNotificationMessage) message.getContent();
            if (JOINCHARROOM.equals(notificationMessage.getMessage())) {//进入聊天室
                if (notificationMessage.getUserInfo() != null && !Util.isEmpty(notificationMessage.getUserInfo().getName())) {
                    chatListview.post(new Runnable() {
                        @Override
                        public void run() {
                            if (notificationMessage.getUserInfo() != null) {
                                if (notificationMessage.getUserInfo().getPortraitUri() == null) {
                                    addHeadPic(notificationMessage.getUserInfo().getUserId(), null);
                                } else {
                                    addHeadPic(notificationMessage.getUserInfo().getUserId(), Util.getD2cPicUrl(notificationMessage.getUserInfo().getPortraitUri().toString()));
                                }
                                notifyMessage(message);
                            }
                        }
                    });
                }
            } else if (QUITCHARROOM.equals(notificationMessage.getMessage())) { //退出聊天室
                chatListview.post(new Runnable() {
                    @Override
                    public void run() {
                        if (notificationMessage.getUserInfo() != null) {
                            removeHeadPic(notificationMessage.getUserInfo().getUserId());
                        }
                    }
                });
            } else if (BUY.equals(notificationMessage.getMessage())) { //去购买
                chatListview.post(new Runnable() {
                    @Override
                    public void run() {
                        if (notificationMessage.getUserInfo() != null) {
                            notifyMessage(message);
                        }
                    }
                });
            } else if (ADDCART.equals(notificationMessage.getMessage())) { //加入购物车
                chatListview.post(new Runnable() {
                    @Override
                    public void run() {
                        if (notificationMessage.getUserInfo() != null) {
                            notifyMessage(message);
                        }
                    }
                });
            } else if (SHARE.equals(notificationMessage.getMessage())) { //分享
                chatListview.post(new Runnable() {
                    @Override
                    public void run() {
                        if (notificationMessage.getUserInfo() != null) {
                            notifyMessage(message);
                        }
                    }
                });
            } else if (FOLLOW.equals(notificationMessage.getMessage())) { //关注
                chatListview.post(new Runnable() {
                    @Override
                    public void run() {
                        if (notificationMessage.getUserInfo() != null) {
                            notifyMessage(message);
                        }
                    }
                });
            }
        } else if (message.getContent() instanceof CommandNotificationMessage) {
            CommandNotificationMessage commandNotificationMessage = (CommandNotificationMessage) message.getContent();
            if (":QL".equals(commandNotificationMessage.getName()) && "OFF".equals(commandNotificationMessage.getData())) {
                getWindow().getDecorView().post(new Runnable() {
                    @Override
                    public void run() {
                        if (crowdRecommendGoodPop != null && crowdRecommendGoodPop.isShow()) {
                            crowdRecommendGoodPop.dismiss();
                        }
                        logoTagLl.setVisibility(View.GONE);
                    }
                });
            } else if (":RG".equals(commandNotificationMessage.getName())) {
                tvRedPoint.setVisibility(View.VISIBLE);
                final String json = commandNotificationMessage.getData();
                final String url;
                final long id;
                final double price;
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    url = jsonObject.optString("productImg");
                    id = jsonObject.optLong("productId");
                    price = jsonObject.optDouble("productPrice");
                    getWindow().getDecorView().post(new Runnable() {
                        @Override
                        public void run() {
                            showRecommendGood(url, id, price);
                        }
                    });
                    if (crowdRecommendGoodPop != null) {
                        crowdRecommendGoodPop.stick(id);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (":TC".equals(commandNotificationMessage.getName())) {
                final String json = commandNotificationMessage.getData();
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    final int watchCount = jsonObject.optInt("watchCount");
                    getWindow().getDecorView().post(new Runnable() {
                        @Override
                        public void run() {
                            tvViewCount.setText(getString(R.string.label_live_watching, watchCount));
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (":DIANZAN".equals(commandNotificationMessage.getName())) {
                heartLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        heartLayout.addHeart();
                    }
                });
            }
        } else if (message.getContent() instanceof PresentMessage) {
            chatListview.post(new Runnable() {
                @Override
                public void run() {//送礼物
                    PresentMessage presentMessage = (PresentMessage) message.getContent();
                    if (presentMessage.getUserInfo() != null) {
                        String headUrl = null;
                        if (presentMessage.getUserInfo().getPortraitUri() != null) {
                            headUrl = Util.getD2cPicUrl(presentMessage.getUserInfo().getPortraitUri().toString());
                        }
                        LiveAnimationBean liveAnimationBean = new LiveAnimationBean(headUrl, presentMessage.getUserInfo().getName(),
                                presentMessage.getPresentName(), presentMessage.getPresentUrl());
                        Log.e("live", "count:" + presentMessage.getCount());
                        liveAnimationBean.count = 1;
                        giftControl.loadGift(liveAnimationBean);
                        notifyMessage(message);
                    }
                }
            });
        } else if (message.getContent() instanceof WebMessage) {
            chatListview.post(new Runnable() {
                @Override
                public void run() {
                    WebMessage webMessage = (WebMessage) message.getContent();
                    if (webMessage.getType() == 4) {
                        //移除头像
                        removeHeadPic(webMessage.getUserId());
                    } else if (webMessage.getType() == 3) {
                        addHeadPic(webMessage.getUserId(), webMessage.getUserPic());
                    }
                    notifyMessage(message);
                }
            });
        } else {
            chatListview.post(new Runnable() {
                @Override
                public void run() {
                    if (message.getContent() instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message.getContent();
                        if (DANMU.equals(textMessage.getExtra()) && textMessage.getUserInfo() != null) {
                        } else {
                            notifyMessage(message);
                        }
                    }
                }
            });
        }
    }

    private Handler mHander = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            recommendGoodLayout.setVisibility(View.INVISIBLE);
            recommendGoodPic.setVisibility(View.INVISIBLE);
        }
    };

    public void showRecommendGood(String url, final long id, double price) {
        recommendGoodLayout.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in_from_left);
        recommendGoodLayout.startAnimation(animation);
        tvPrice.setText("¥" + Util.getNumberFormat(price));
        recommendGoodPic.setVisibility(View.VISIBLE);
        UniversalImageLoader.displayImage(this, url, recommendGoodPic, R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
        recommendGoodLayout.setTag(id);
        mHander.removeMessages(1);
        mHander.sendEmptyMessageDelayed(1, 8000);
    }

    private void addHeadPic(String userId, String d2cPicUrl) {
        if (Util.isEmpty(userId)) {
            return;
        }
        //添加用户头像,更新观看人数
        if (Long.valueOf(userId) == liveBean.getMemberId()) {
            return;
        }
        LiveAudienceInBean.DataBean.RealtimeBean.HeadersBean bean = new LiveAudienceInBean.DataBean.RealtimeBean.HeadersBean();
        bean.setHeadPic(d2cPicUrl);
        bean.setMemberId(Long.valueOf(userId));
        headBeanArrayList.add(0, bean);
        liveHeanAdapter.notifyDataSetChanged();
    }

    private void removeHeadPic(String userId) {
        //移除退出的用户头像,更新观看人数
        for (int i = 0; i < headBeanArrayList.size(); i++) {
            if (Long.valueOf(userId) == headBeanArrayList.get(i).getMemberId()) {
                headBeanArrayList.remove(i);
                liveHeanAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    private void initListener() {
        Log.e("live", "initListener");
        //融云设置消息回调监听
        RongIMClient.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                showMessageList(message);
                return false;
            }
        });
        KeyboardUtil.attach(this, panelLayout, new KeyboardUtil.OnKeyboardShowingListener() {
            @Override
            public void onKeyboardShowing(boolean isShowing) {
                if (anchorRecommendGoodPop != null && anchorRecommendGoodPop.isShow()) {
                    return;
                }
                if (isShowing) {
                    inputLayout.setVisibility(View.VISIBLE);
                    etMessage.setFocusable(true);
                    etMessage.setFocusableInTouchMode(true);
                    etMessage.requestFocus();
                    bottomLayout.setVisibility(View.GONE);
                } else {
                    inputLayout.setVisibility(View.GONE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bottomLayout.setVisibility(View.VISIBLE);
                        }
                    }, 200);
                }
            }
        });
    }

    protected void quitCharRoom() {

        RongIMClient.getInstance().quitChatRoom(mChannel, new RongIMClient.OperationCallback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
            }
        });

        if (user == null) return;
        InformationNotificationMessage informationNotificationMessage = new InformationNotificationMessage(QUITCHARROOM);
        // Uri uri=Uri.parse(user.getHead()); 用户头像
        Uri uri = null;
        String head = user.getHead();
        if (!Util.isEmpty(head)) {
            uri = Uri.parse(Util.getD2cPicUrl(head));
        }
        UserInfo info = new UserInfo(String.valueOf(user.getId()), user.getNickname(), uri);
        informationNotificationMessage.setUserInfo(info);
        RongIMClient.getInstance().sendMessage(Conversation.ConversationType.CHATROOM, mChannel, informationNotificationMessage, null, null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
            }

            @Override
            public void onSuccess(Message message) {
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
            }
        });
    }

    protected void anchorExit() {
        InformationNotificationMessage informationNotificationMessage = new InformationNotificationMessage(AUTHOREXIT);
        UserInfo info = new UserInfo(String.valueOf(user.getId()), user.getNickname(), null);
        informationNotificationMessage.setUserInfo(info);
        RongIMClient.getInstance().sendMessage(Conversation.ConversationType.CHATROOM, mChannel, informationNotificationMessage, null, null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
            }

            @Override
            public void onSuccess(Message message) {
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
            }
        });
    }


    private void initLiveInfo() {
        AnchorInfoApi api = new AnchorInfoApi();
        api.setInterPath(String.format(Constants.LIVE_ANCHOR_INFO, liveBean.getMemberId()));
        api.setLiveId(liveBean.getId());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<PersonInfoBean>() {
            @Override
            public void onResponse(PersonInfoBean response) {
                if (response != null) {
                    AnchorInfoBean = response;
                }
                tvAnchorName.setText(response.getData().getMember().getNickname());
                UniversalImageLoader.displayRoundImage(LiveAnchorActivity.this, response.getData().getMember().getHead(), imgAvatar, R.mipmap.ic_default_avatar);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    protected void showLiveOffLayout() {
//        if (hostDialog != null && hostDialog.isShowing()) {
//            hostDialog.dismiss();
//        }
//        if (pwdDialog != null && pwdDialog.isShowing()) {
//            pwdDialog.dismiss();
//        }
        if (mMediaStreamingManager != null) {
            mMediaStreamingManager.stopStreaming();
        }
        hideKeyboard(null);
        liveOffLayout.setVisibility(View.VISIBLE);
        imgCloseOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Glide.with(this).load(R.mipmap.icon_zhibo_beijing)
                .error(R.mipmap.ic_default_cover)
                .bitmapTransform(new BlurTransformation(this))
                .into(imgCoverOff);
        UniversalImageLoader.displayRoundImage(this, Util.getD2cPicUrl(liveBean.getHeadPic()),
                imgHeadOff, R.mipmap.ic_default_avatar);
        tvAnchorNameOff.setText(liveBean.getNickname());
        tvTitle.setText(liveBean.getTitle());
        tvCountFans.setText(String.valueOf(AnchorInfoBean.getData().getFansTotalCount()));
        requestInWatchedCount();//请求累计观看人数
        btnFocus.setVisibility(View.GONE);
    }

    private void requestInWatchedCount() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.LIVE_STATUS_INFO, liveBean.getId()));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<WatchInfoBean>() {
            @Override
            public void onResponse(WatchInfoBean response) {
                if (response == null || response.getData() == null) {
                    return;
                }
                tvCountWatched.setText(String.valueOf(response.getData().getLive().getVcount() + response.getData().getLive().getVfans()));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvCountWatched.setText(String.valueOf(0));
            }
        });
    }

    protected void anchorOrWatcherLogout(final boolean isRestart) {
        //主播退出直播
        CloseLiveApi api = new CloseLiveApi();
        api.setId(liveBean.getId());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                quitCharRoom();//退出融云
                anchorExit();
                if (isRestart) {
                    Intent intent = new Intent(LiveAnchorActivity.this, StartLiveActivity.class);
                    LiveAnchorActivity.this.startActivity(intent);
                    finish();
                } else {
                    EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.REFRESH_LIVE));
                    showLiveOffLayout();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(LiveAnchorActivity.this, Util.checkErrorType(error));
                showLiveOffLayout();
            }
        });
    }

    private void logout() {
        //主播退出直播判断(就是一个弹窗的区别)
        if (loadingLayout.getVisibility() == View.VISIBLE || liveOffLayout.getVisibility() == View.VISIBLE
                || notNetworkLayout.getVisibility() == View.VISIBLE) {
            anchorOrWatcherLogout(false);
        } else {
            DialogUtil.showMsgDialog(LiveAnchorActivity.this, R.string.msg_close_live, new DialogClickListener() {
                @Override
                public void onConfirm() {
                    //主播退出
                    anchorOrWatcherLogout(false);
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onDismiss() {

                }
            });

        }
    }

    @OnClick({R.id.img_setting, R.id.setting_pop_layout, R.id.setting_container_layout,
            R.id.img_buy, R.id.tv_send, R.id.img_share, R.id.toggleButton, R.id.recommend_good_layout, R.id.img_close, R.id.img_close_tag, R.id.tv_msg})
    public void anchorSetting(View view) {
        switch (view.getId()) {
            case R.id.img_share:
                SharePop sharePop = new SharePop(this);
                long id = 0;
                if (liveBean != null) {
                    id = liveBean.getId();
                    String title = liveBean.getNickname() + getResources().getString(R.string.live_share_new_txt);
                    sharePop.setDescription(title);
                    sharePop.setTitle(getString(R.string.live_share_txt, liveBean.getTitle()));
                    if (!Util.isEmpty(liveBean.getHeadPic())) {
                        sharePop.setImage(Util.getD2cPicUrl(liveBean.getHeadPic(), 100, 100), false);
                        sharePop.setImage(Util.getD2cPicUrl(liveBean.getCover(), 360, 500), true);
                    }
                }
                sharePop.setWebView(true);
                sharePop.setWebUrl(Constants.SHARE_URL + "/live/" + id);
                sharePop.show(getWindow().getDecorView());
                break;
            case R.id.tv_msg:
                //弹出软键盘开始聊天
                settingContainerLayout.setVisibility(View.GONE);
                inputLayout.setVisibility(View.VISIBLE);
                bottomLayout.setVisibility(View.GONE);
                KeyboardUtil.showKeyboard(etMessage);
                break;
            case R.id.img_close:
            case R.id.img_close_tag:
                //关闭直播
                logout();
                break;
            case R.id.img_setting:
                //直播设置
                if (settingContainerLayout.getVisibility() == View.GONE) {
                    settingContainerLayout.setVisibility(View.VISIBLE);
                } else {
                    settingContainerLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.setting_pop_layout:
                //点击设置框内的空白处，不处理
                break;
            case R.id.setting_container_layout:
                //点击设置框外面的空白处
                if (settingContainerLayout.getVisibility() == View.VISIBLE) {
                    settingContainerLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.img_buy:
                anchorRecommendGoodPop = new AnchorRecommendGoodPop(LiveAnchorActivity.this, liveBean.getId()) {
                    @Override
                    public void releaseView(LinearLayout v) {

                    }

                    @Override
                    public void recommend(String url, long id, double price) {
                        tvRedPoint.setVisibility(View.VISIBLE);
                        showRecommendGood(url, id, price);
                    }

                    @Override
                    public void toOtherActivity() {
                    }
                };
                anchorRecommendGoodPop.setLiveId(liveBean.getId());
                anchorRecommendGoodPop.setDissBack(new TransparentPop.DismissListener() {

                    @Override
                    public void dismissStart() {

                    }

                    @Override
                    public void dismissEnd() {
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    }
                });
                anchorRecommendGoodPop.show(imgBuy);
                anchorRecommendGoodPop.setChannel(mChannel);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                break;
            case R.id.tv_send:
                sendMessage();
                break;
            case R.id.toggleButton:
                if (toggleButton.isChecked()) {
                    etMessage.setHint(R.string.hint_dan_input_host);
                } else {
                    etMessage.setHint(R.string.hint_live_input);
                }
                break;
            case R.id.recommend_good_layout:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //监听返回键,调关闭推流操作接口
        logout();
    }


    private void initManager() {
        Log.e("live", "initManager");
        //初始化推流的Manager
        cameraPreviewAfl.setShowMode(AspectFrameLayout.SHOW_MODE.FULL);
        try {
            //推流的相关设置
            mProfile = new StreamingProfile();
            mProfile.setVideoQuality(videoQuantity)//推流图像质量设置
                    .setAudioQuality(StreamingProfile.AUDIO_QUALITY_HIGH2)//推流声音质量设置
                    .setEncodingSizeLevel(StreamingProfile.VIDEO_ENCODING_HEIGHT_720)
                    .setEncoderRCMode(StreamingProfile.EncoderRCModes.QUALITY_PRIORITY)//码率优先,尽量保持流畅度
                    .setBitrateAdjustMode(StreamingProfile.BitrateAdjustMode.Auto);//设置根据当前网速自动调节码率
            mProfile.setPublishUrl(liveBean.getPushUrl());//设置推流地址
            Log.e("han","推流地址:"+liveBean.getPushUrl());
            setting = new CameraStreamingSetting();
            if (mEnableFrontCam) {//设置是否前置摄像头
                setting.setCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT);
            } else {
                setting.setCameraId(Camera.CameraInfo.CAMERA_FACING_BACK);
            }
            //初始化美颜设置
            CameraStreamingSetting.FaceBeautySetting beautySetting = new CameraStreamingSetting.FaceBeautySetting(polishStep, whiteStep, redStep);
            //摄像头相关设置
            setting.setRecordingHint(false)
                    .setContinuousFocusModeEnabled(true)
                    .setBuiltInFaceBeautyEnabled(true)//设置使用内置美颜
                    .setFaceBeautySetting(beautySetting)//设置美颜具体数值
                    .setVideoFilter(CameraStreamingSetting.VIDEO_FILTER_TYPE.VIDEO_FILTER_BEAUTY)//设置使用美颜(怎么有两个？因为文档这么写的=.=)
                    .setFocusMode(FOCUS_MODE_CONTINUOUS_VIDEO)
                    .setCameraPrvSizeLevel(CameraStreamingSetting.PREVIEW_SIZE_LEVEL.MEDIUM)
                    .setCameraPrvSizeRatio(CameraStreamingSetting.PREVIEW_SIZE_RATIO.RATIO_16_9);
            mMediaStreamingManager = new MediaStreamingManager(this, cameraPreviewAfl, cameraPreviewSurfaceView, AVCodecType.SW_VIDEO_WITH_SW_AUDIO_CODEC);  // soft codec
            mMediaStreamingManager.prepare(setting, mProfile);
            //设置相关的监听回调
            mMediaStreamingManager.setStreamingStateListener(this);
            mMediaStreamingManager.setStreamingSessionListener(this);
            if (isLightOn) {//闪光灯是否开启
                mMediaStreamingManager.turnLightOn();
            } else {
                mMediaStreamingManager.turnLightOff();
            }
        } catch (Exception e) {
            //上边的初始化操作发生错误则提示且退出页面
            Log.e("live", e.getMessage());
            Util.showToast(this, "推流初始化错误,请稍后再试~");
            finish();
            e.printStackTrace();
        }
        initSetting();//将推流设置设置进去
    }

    protected void initExtraData(Bundle savedInstanceState) {
        Log.e("live", "initExtraData");
        //拿到推流相关设置
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            mPublishTitle = intent.getStringExtra("PUBLISH_TITLE");
            mEnableFrontCam = intent.getBooleanExtra("ENABLE_FRONT_CAM", false);
            isLightOn = intent.getBooleanExtra("ENABLE_LIGHT_ON", false);
            whiteStep = intent.getFloatExtra("WHITE_STEP", 0);
            polishStep = intent.getFloatExtra("POLISH_STEP", 0);
            redStep = intent.getFloatExtra("RED_STEP", 0);
            videoQuantity = intent.getIntExtra("VIDEO_QUALITY", StreamingProfile.VIDEO_QUALITY_HIGH3);
            liveBean = (LiveListBean.DataBean.LivesBean.ListBean) intent.getSerializableExtra("LIVE_BEAN");
            if (liveBean != null) {
                mChannel = liveBean.getStreamId();
                mPublishStreamID = String.valueOf(liveBean.getId());
            }
        } else {
            mPublishTitle = savedInstanceState.getString("PUBLISH_TITLE");
            mEnableFrontCam = savedInstanceState.getBoolean("ENABLE_FRONT_CAM", false);
            isLightOn = savedInstanceState.getBoolean("ENABLE_LIGHT_ON", false);
            whiteStep = savedInstanceState.getFloat("WHITE_STEP", 0);
            polishStep = savedInstanceState.getFloat("POLISH_STEP", 0);
            redStep = savedInstanceState.getFloat("RED_STEP", 0);
            videoQuantity = savedInstanceState.getInt("VIDEO_QUALITY", StreamingProfile.VIDEO_QUALITY_HIGH3);
            liveBean = (LiveListBean.DataBean.LivesBean.ListBean) savedInstanceState.getSerializable("LIVE_BEAN");
            isFirstTimeStreaming = false;
            if (liveBean != null) {
                mChannel = liveBean.getStreamId();
                mPublishStreamID = String.valueOf(liveBean.getId());
            }
        }
    }

    private void getRongToken() {
        //获取融云Token
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.RONG_NEW_TOKEN_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<RongTokenBean>() {
            @Override
            public void onResponse(RongTokenBean response) {
                if (!Util.isEmpty(response.getData().getToken())) {
                    user.setImToken(response.getData().getToken());
                    Session.getInstance().saveUserToFile(LiveAnchorActivity.this, user);
                    connect2Rong(response.getData().getToken());
                }
            }
        });
    }

    protected void initBaseViews() {
        Log.e("live", "initBaseViews");
        //判断user是否有融云Token,有则直接连接聊天室,没有则申请Token再连接
        user = Session.getInstance().getUserFromFile(this);
        if (user != null) {
            if (!Util.isEmpty(user.getImToken())) {
                connect2Rong(user.getImToken());
            } else {
                getRongToken();
            }
        }
        giftControl = new GiftControl(this);
        giftControl.setGiftLayout(giftLayout, 2)
                .setHideMode(false)
                .setCustormAnim(new CustormAnim());//这里可以自定义礼物动画
        //头像的RecyclerView初始化
        headBeanArrayList = new ArrayList<>();
        liveHeanAdapter = new LiveHeanAdapter(headBeanArrayList, this);
        picLayout.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        picLayout.setAdapter(liveHeanAdapter);
        //聊天消息ListView相关初始化
        messages = new ArrayList<>();
        messageAdapter = new RongMessageListAdapter(this, messages);
        chatListview.setAdapter(messageAdapter);
        //边买边看动图展示
        try {
            gifFromAssets = new GifDrawable(getResources(), R.mipmap.buy);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgBuy.setImageDrawable(gifFromAssets);
        etMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendMessage();
                    return true;
                }
                return false;
            }
        });
        handler = new Handler();
    }

    private void connect2Rong(String token) {
        RongIMClient.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.e("d2c", "rong>>>connect>>>onTokenIncorrect");
            }

            @Override
            public void onSuccess(String s) {
                joinCharRoom();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
            }
        });
    }

    /**
     * 0 为拉取消息数量
     */
    private void joinCharRoom() {
        RongIMClient.getInstance().joinChatRoom(mChannel, -1, new RongIMClient.OperationCallback() {
            @Override
            public void onSuccess() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        intoCharRoom();
                    }
                }, 1000);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMediaStreamingManager != null) {
            mMediaStreamingManager.pause();
        }
    }

    @Override
    protected void onResume() {
        if (mMediaStreamingManager != null) {
            mMediaStreamingManager.resume();
        }
        super.onResume();
    }

    private void intoCharRoom() {
        if (user == null) {
            return;
        }
        final InformationNotificationMessage notificationMessage = new InformationNotificationMessage(JOINCHARROOM);
        Uri uri = null;
        String head = user.getHead();
        if (!Util.isEmpty(head)) {
            uri = Uri.parse(Util.getD2cPicUrl(head));
        }
        UserInfo info = new UserInfo(String.valueOf(user.getId()), user.getNickname(), uri);
        notificationMessage.setUserInfo(info);
        RongIMClient.getInstance().sendMessage(Conversation.ConversationType.CHATROOM, mChannel, notificationMessage, null, null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
            }

            @Override
            public void onSuccess(Message message) {
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
            }
        });
    }


    @Override
    public void onStateChanged(StreamingState streamingState, Object o) {
        switch (streamingState) {
            case UNKNOWN:
                Log.e("live", "UNKNOWN");
                break;
            case PREPARING:
                Log.e("live", "PREPARING");
                break;
            case READY:
                Log.e("live", "READY");
                // start streaming when READY
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (mMediaStreamingManager != null) {
                            if (!mMediaStreamingManager.startStreaming()) {
                                isOutTime = true;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (Util.checkNetwork(LiveAnchorActivity.this)) {
                                            if (isFirstTimeStreaming) {
                                                new AlertDialog.Builder(LiveAnchorActivity.this)
                                                        .setMessage("您上一次直播已过期,是否重新开播?")
                                                        .setPositiveButton("重新开播", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                anchorOrWatcherLogout(true);
                                                            }
                                                        })
                                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                anchorOrWatcherLogout(false);
                                                            }
                                                        })
                                                        .show();
                                            }
                                        } else {
                                            Util.showToast(LiveAnchorActivity.this, R.string.label_live_net_error);
                                        }
                                    }
                                });
                            } else {
                                if (isFirstTimeStreaming) {
                                    isFirstTimeStreaming = false;
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ivGif.setVisibility(View.VISIBLE);
                                            ivGif.startAnimation(mShowAction);
                                            Glide.with(LiveAnchorActivity.this)
                                                    .load(R.drawable.live_count)
                                                    .asGif()
                                                    .into(ivGif);
                                            ivGif.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ivGif.startAnimation(mHiddenAction);
                                                    ivGif.setVisibility(View.GONE);
                                                }
                                            }, 3000);
                                        }
                                    });
                                }
                            }
                        }
                    }
                }).start();
                break;
            case CONNECTING:
                Log.e("live", "连接中");
                break;
            case STREAMING:
                Log.e("live", "The av packet had been sent.");
                // The av packet had been sent.
                break;
            case SHUTDOWN:
                Log.e("live", "推流结束");
                //finish();
                // The streaming had been finished.
                break;
            case IOERROR:
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mMediaStreamingManager != null) {
                            if (mMediaStreamingManager.startStreaming()) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        int netType = Util.getAPNType(LiveAnchorActivity.this);
                                        switch (netType) {
                                            case -1://没有网络
                                                break;
                                            case 1://wifi网络
                                                Util.showToast(LiveAnchorActivity.this, "您当前正在使用Wifi网络");
                                                break;
                                            case 2://移动网络
                                                Util.showToast(LiveAnchorActivity.this, "您当前正在使用移动网络");
                                                break;
                                        }
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!isOutTime) {
                                            Util.showToast(LiveAnchorActivity.this, R.string.label_live_net_error);
                                        }
                                    }
                                });
                            }
                        }
                    }
                }, 2000);
                // Network connect error.
                break;
            case OPEN_CAMERA_FAIL:
                Util.showToast(LiveAnchorActivity.this, "相机开启失败,请重新开启");
                anchorOrWatcherLogout(true);
                // Failed to open camera.
                break;
            case DISCONNECTED:
                Log.e("live", "推流失去连接");
                // The socket is broken while streaming
                break;
        }
    }

    @Override
    public boolean onRecordAudioFailedHandled(int i) {
        return false;
    }

    @Override
    public boolean onRestartStreamingHandled(int i) {
        if (mMediaStreamingManager != null) {
            mMediaStreamingManager.startStreaming();
        }
        return true;
    }

    @Override
    public Camera.Size onPreviewSizeSelected(List<Camera.Size> list) {
        return null;
    }

    @Override
    public int onPreviewFpsSelected(List<int[]> list) {
        return 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaStreamingManager != null) {
            mMediaStreamingManager.destroy();
        }
        RongIMClient.setOnReceiveMessageListener(null);
    }
}
