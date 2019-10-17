package com.d2cmall.buyer.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
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
import com.d2cmall.buyer.adapter.GridViewAdapter;
import com.d2cmall.buyer.adapter.LiveHeanAdapter;
import com.d2cmall.buyer.adapter.RongMessageListAdapter;
import com.d2cmall.buyer.api.AnchorInfoApi;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.FollowApi;
import com.d2cmall.buyer.api.HostInfoApi;
import com.d2cmall.buyer.api.LiveCouponApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.ClickFollowBean;
import com.d2cmall.buyer.bean.LiveAnimationBean;
import com.d2cmall.buyer.bean.LiveAudienceInBean;
import com.d2cmall.buyer.bean.LiveCouponNewBean;
import com.d2cmall.buyer.bean.LiveListBean;
import com.d2cmall.buyer.bean.LivePresentsBean;
import com.d2cmall.buyer.bean.PersonInfoBean;
import com.d2cmall.buyer.bean.PresentMessage;
import com.d2cmall.buyer.bean.RongTokenBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.bean.WatchInfoBean;
import com.d2cmall.buyer.bean.WebMessage;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DialogClickListener;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.CrowdRecommendGoodPop;
import com.d2cmall.buyer.widget.GiftLayout.CustormAnim;
import com.d2cmall.buyer.widget.GiftLayout.GiftControl;
import com.d2cmall.buyer.widget.GuideLayout;
import com.d2cmall.buyer.widget.LiveRelativeLayout;
import com.d2cmall.buyer.widget.RoundedImageView;
import com.d2cmall.buyer.widget.SharePop;
import com.d2cmall.buyer.widget.ShowPopImageView;
import com.d2cmall.buyer.widget.TransparentPop;
import com.d2cmall.buyer.widget.WrapLineGridView;
import com.d2cmall.buyer.widget.WrapViewPager;
import com.d2cmall.buyer.widget.heartLayout.PeriscopeLayout;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.pili.pldroid.player.widget.PLVideoView;

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
import cn.dreamtobe.kpswitch.widget.KPSwitchRootFrameLayout;
import de.greenrobot.event.Subscribe;
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

/**
 * Created by rookie on 2017/12/29.
 */

public class LiveAudienceActivity extends BaseActivity {


    @Bind(R.id.VideoView)
    PLVideoTextureView videoView;
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
    TextView priceTv;
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
    @Bind(R.id.img_cover_off)
    ImageView imgCoverOff;
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
    @Bind(R.id.iv_cover)
    ImageView ivCover;
    @Bind(R.id.iv_cover_close)
    ImageView ivCoverClose;
    @Bind(R.id.cover_view)
    RelativeLayout coverView;
    @Bind(R.id.iv_loading)
    ImageView ivLoading;
    @Bind(R.id.loading_view)
    RelativeLayout loadingView;
    @Bind(R.id.tv_over)
    TextView tvOver;
    @Bind(R.id.kps)
    KPSwitchRootFrameLayout kpSwitchRootFrameLayout;
    @Bind(R.id.xin)
    View xin;
    public static final String JOINCHARROOM = "joinCharRoom";

    public static final String QUITCHARROOM = "quitCharRoom";

    public static final String AUTHOREXIT = "authorExit";

    public static final String BUY = "BUY";

    public static final String ADDCART = "ADDCART";

    public static final String SHARE = "SHARE";

    public static final String FOLLOW = "FOLLOW";

    public final String DANMU = "Danmaku";

    private LiveListBean.DataBean.LivesBean.ListBean bean;
    private PersonInfoBean AnchorInfoBean;
    private String url;
    protected UserBean.DataEntity.MemberEntity user;
    protected int[] hearts = new int[]{R.mipmap.icon_zhibo_xin_bao, R.mipmap.icon_zhibo_xin_fen,
            R.mipmap.icon_zhibo_xin_huang, R.mipmap.icon_zhibo_xin_kouhong, R.mipmap.icon_zhibo_xin_kuzi, R.mipmap.icon_zhibo_xin_lan, R.mipmap.icon_zhibo_xin_qunzi, R.mipmap.icon_zhibo_xin_yifu, R.mipmap.icon_zhibo_xin_zi};
    private String mChannel;
    protected List<Message> messages;
    protected RongMessageListAdapter messageAdapter;
    private GifDrawable gifFromAssets = null;
    private long firstTime;
    private boolean sendStrSuccess;
    private ArrayList<LiveAudienceInBean.DataBean.RealtimeBean.HeadersBean> headBeanArrayList;
    private LiveHeanAdapter liveHeanAdapter;
    protected CrowdRecommendGoodPop crowdRecommendGoodPop;
    private int lastCount = 0;
    public GuideLayout guideLayout;
    protected List<LivePresentsBean.DataBean.PresentsBean> presentsBeanList;
    protected List<GridView> presentPagerList;
    protected int currentPresentPosition;
    protected int oldPresentPosition;
    protected int currentPageIndex = 0;
    protected List<LiveAnimationBean> liveAnimationBeanList;
    private GiftControl giftControl;
    protected Dialog hostDialog;
    private float scaleRate;
    private String lastGiftName;
    private Long lastSendTime;
    private int giftCount = 1;
    private boolean isJustCoupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_audience_layout);
        ButterKnife.bind(this);
        liveAnimationBeanList = new ArrayList<>();
        bean = (LiveListBean.DataBean.LivesBean.ListBean) getIntent().getSerializableExtra("bean");
        mChannel = bean.getStreamId();
        scaleRate = (ScreenUtil.getDisplayHeight() - ScreenUtil.dip2px(400)) / (float) ScreenUtil.getDisplayHeight();
        Glide.with(this).load(Util.getD2cPicUrl(bean.getCover()))
                .error(R.mipmap.ic_logo_empty5)
                .bitmapTransform(new BlurTransformation(this))
                .into(ivCover);
        Glide.with(this).load(Util.getD2cPicUrl(bean.getCover()))
                .error(R.mipmap.ic_logo_empty5)
                .bitmapTransform(new BlurTransformation(this))
                .into(ivLoading);
        heartLayout.setDrawables(hearts);
        imgSetting.setVisibility(View.GONE);
        imgGift.setVisibility(View.VISIBLE);
        giftControl = new GiftControl(this);
        giftControl.setGiftLayout(giftLayout, 2)
                .setHideMode(false)
                .setCustormAnim(new CustormAnim());//这里可以自定义礼物动画
        initBaseViews();
        initListener();
        initLiveInfo();
        initVideoView();
    }

    private void scaleWindow() {
        ObjectAnimator animY = ObjectAnimator.ofFloat(videoView, "scaleY", 1f, scaleRate);
        animY.setInterpolator(new AccelerateDecelerateInterpolator());
        animY.setDuration(500);
        ObjectAnimator animX = ObjectAnimator.ofFloat(videoView, "scaleX", 1f, scaleRate);
        animX.setDuration(500);
        animX.setInterpolator(new AccelerateDecelerateInterpolator());
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animX, animY);
        set.start();
    }

    private void windowReset() {
        ObjectAnimator animY = ObjectAnimator.ofFloat(videoView, "scaleY", scaleRate, 1f);
        animY.setDuration(500);
        ObjectAnimator animX = ObjectAnimator.ofFloat(videoView, "scaleX", scaleRate, 1f);
        animX.setDuration(500);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animX, animY);
        set.start();
    }


    private void initLiveInfo() {
        AnchorInfoApi api = new AnchorInfoApi();
        api.setInterPath(String.format(Constants.LIVE_ANCHOR_INFO, bean.getMemberId()));
        api.setLiveId(bean.getId());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<PersonInfoBean>() {
            @Override
            public void onResponse(PersonInfoBean response) {
                if (response != null) {
                    AnchorInfoBean = response;
                }
                if (response.getData() != null && response.getData().getFollow() == 0) {
                    ivCare.setVisibility(View.VISIBLE);
                } else {
                    ivCare.setVisibility(View.GONE);
                }
                tvAnchorName.setText(response.getData().getMember().getNickname());
                if (!isFinishing()) {
                    UniversalImageLoader.displayRoundImage(LiveAudienceActivity.this, response.getData().getMember().getHead(), imgAvatar, R.mipmap.ic_default_avatar);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        ivCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FollowApi api = new FollowApi();
                api.setToMemberId(bean.getMemberId());
                api.setInterPath(Constants.INSERT_MY_FOLLOWS_URL);
                D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                    @Override
                    public void onResponse(BaseBean response) {
                        Util.showToast(LiveAudienceActivity.this, "关注成功");
                        ivCare.showMsgPop( LiveAudienceActivity.this,getString(R.string.label_pop_focus_people));
                        AnchorInfoBean.getData().setFollow(1);
                        ivCare.setVisibility(View.GONE);
                        sendFollowMessage(FOLLOW);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Util.showToast(LiveAudienceActivity.this, Util.checkErrorType(error));
                    }
                });
            }
        });
    }

    private void sendFollowMessage(String type) {
        if (user == null) return;
        InformationNotificationMessage informationNotificationMessage = new InformationNotificationMessage(type);
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

    //请求弹窗里的信息
    private void requestDialogInfo(final long memberId, final ImageView view) {
        view.setEnabled(false);
        HostInfoApi api = new HostInfoApi();
        api.setInterPath(String.format(Constants.NEW_EXPLORE_INFO_URL, memberId));
        api.setLiveId(bean.getId());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<PersonInfoBean>() {
            @Override
            public void onResponse(PersonInfoBean topInfoBean) {
                AnchorInfoBean = topInfoBean;
                view.setEnabled(true);
                showInfoDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.setEnabled(true);
            }
        });
    }

    private void focusOver(final TextView view) {
        FollowApi api = new FollowApi();
        api.setToMemberId(AnchorInfoBean.getData().getMember().getMemberId());
        if (AnchorInfoBean.getData().getFollow() > 0) {
            api.setInterPath(Constants.DELETE_MY_FOLLOWS_URL);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                @Override
                public void onResponse(BaseBean response) {
                    AnchorInfoBean.getData().setFollow(0);
                    view.setText("关注");
                    ivCare.setVisibility(View.VISIBLE);
                    Util.showToast(LiveAudienceActivity.this, "取消关注成功");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Util.showToast(LiveAudienceActivity.this, Util.checkErrorType(error));
                }
            });
        } else {
            api.setInterPath(Constants.INSERT_MY_FOLLOWS_URL);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ClickFollowBean>() {
                @Override
                public void onResponse(ClickFollowBean clickFollowBean) {
                    AnchorInfoBean.getData().setFollow(clickFollowBean.getData().getFollow());
                    view.setText("已关注");
                    ivCare.setVisibility(View.GONE);
                    sendFollowMessage(FOLLOW);
                    Util.showToast(LiveAudienceActivity.this, "关注成功");
                    ivCare.showMsgPop( LiveAudienceActivity.this,getString(R.string.label_pop_focus_people));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Util.showToast(LiveAudienceActivity.this, Util.checkErrorType(error));
                }
            });
        }
    }

    private void showInfoDialog() {
        if (AnchorInfoBean == null) {
            return;
        }
        hostDialog = new Dialog(this, R.style.bubble_dialog);
        Point point = Util.getDeviceSize(this);
        View dialogView = getLayoutInflater().inflate(R.layout.layout_live_info_dialog, null);
        RoundedImageView imgAvatar = (RoundedImageView) dialogView.findViewById(R.id.iv_head);
        if (!isFinishing()) {
            UniversalImageLoader.displayRoundImage(this, Util.getD2cPicUrl(AnchorInfoBean.getData().getMember().getHead()), imgAvatar, R.mipmap.ic_default_avatar);
        }
        TextView tvNick = (TextView) dialogView.findViewById(R.id.tv_nick);
        TextView tvBrandName = (TextView) dialogView.findViewById(R.id.tv_brand_name);
        TextView tvFansCount = (TextView) dialogView.findViewById(R.id.tv_fans);
        if (AnchorInfoBean.getData().getFansTotalCount() > 10000) {
            tvFansCount.setText(String.valueOf(AnchorInfoBean.getData().getFansTotalCount() / 10000 + "万"));
        } else {
            tvFansCount.setText(String.valueOf(AnchorInfoBean.getData().getFansTotalCount()));
        }
        TextView tvFocusCount = (TextView) dialogView.findViewById(R.id.tv_follow);
        tvFocusCount.setText(String.valueOf(AnchorInfoBean.getData().getFollowsTotalCount()));
        tvNick.setText(AnchorInfoBean.getData().getMember().getNickname());
        ImageView imgSex = (ImageView) dialogView.findViewById(R.id.img_sex);
        if (AnchorInfoBean.getData().getMember().getSex().equals("女")) {
            imgSex.setImageResource(R.mipmap.icon_zhibo_nv);
        } else {
            imgSex.setImageResource(R.mipmap.icon_zhibo_nan);
        }
        StringBuilder brand = new StringBuilder();
        if (AnchorInfoBean.getData().getBrands() != null) {
            for (int i = 0; i < AnchorInfoBean.getData().getBrands().size(); i++) {
                if (i < 3) {
                    brand.append(AnchorInfoBean.getData().getBrands().get(i).getName() + ",");
                }
            }
        }
        if (!Util.isEmpty(brand.toString())) {
            tvBrandName.setVisibility(View.VISIBLE);
            if (AnchorInfoBean.getData().getBrands().size() > 3) {
                tvBrandName.setText(getString(R.string.label_brands, brand.deleteCharAt(brand.length() - 1).toString()));
            } else {
                tvBrandName.setText(getString(R.string.label_brand, brand.deleteCharAt(brand.length() - 1).toString()));
            }
        } else {
            tvBrandName.setVisibility(View.GONE);
        }
        if(AnchorInfoBean.getData().getMember().isD2c()){
            tvBrandName.setVisibility(View.VISIBLE);
            tvBrandName.setText("D2C全球好设计");
        }
        dialogView.findViewById(R.id.tv_explore_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smallWindowPlay();
                Intent intent = new Intent(LiveAudienceActivity.this, PersonInfoActivity.class);
                intent.putExtra("memberId", AnchorInfoBean.getData().getMember().getMemberId());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                hostDialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.img_dialog_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hostDialog.dismiss();
            }
        });
        final TextView tvHostFocus = (TextView) dialogView.findViewById(R.id.tv_host_focus);
        if (AnchorInfoBean.getData().getFollow() == 1 || AnchorInfoBean.getData().getFollow() == 2) {
            tvHostFocus.setText("已关注");
        } else {
            tvHostFocus.setText("关注");
        }
        tvHostFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                focusOver(tvHostFocus);
            }
        });
        hostDialog.setContentView(dialogView);
        Window win = hostDialog.getWindow();
        ViewGroup.LayoutParams params = win.getAttributes();
        params.width = Math.round(point.x * 3 / 4);
        win.setGravity(Gravity.CENTER);
        if (!isFinishing()) {
            hostDialog.show();
        }
    }


    @OnClick({R.id.xin, R.id.img_avatar, R.id.presents_top_view, R.id.tv_free_send, R.id.img_gift, R.id.img_setting, R.id.setting_pop_layout, R.id.setting_container_layout,
            R.id.img_buy, R.id.tv_send, R.id.toggleButton,
            R.id.recommend_good_layout, R.id.img_close, R.id.img_close_tag, R.id.tv_msg, R.id.img_share, R.id.heart_layout})
    public void anchorSetting(View view) {
        switch (view.getId()) {
            case R.id.img_avatar:
                requestDialogInfo(bean.getMemberId(), imgAvatar);
                break;
            case R.id.tv_free_send:
                sendPresentMessage2Rong();
                presentsLayout.setVisibility(View.GONE);
                break;
            case R.id.presents_top_view:
                presentsLayout.setVisibility(View.GONE);
                break;
            case R.id.img_gift:
                presentsLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.img_share:
                SharePop sharePop = new SharePop(this);
                long id = 0;
                if (bean != null) {//游客
                    id = bean.getId();
                    String title = bean.getNickname() + getResources().getString(R.string.live_share_new_txt);
                    sharePop.setDescription(title);
                    sharePop.setTitle(getString(R.string.live_share_txt, bean.getTitle()));
                    if (!Util.isEmpty(bean.getHeadPic())) {
                        sharePop.setImage(Util.getD2cPicUrl(bean.getHeadPic(), 100, 100), false);
                        sharePop.setImage(Util.getD2cPicUrl(bean.getCover(), 360, 500), true);
                    }
                }
                sharePop.setWebView(true);
                sharePop.setWebUrl(Constants.SHARE_URL + "/live/" + id);
                sharePop.show(getWindow().getDecorView());
                sendFollowMessage(SHARE);
                break;
            case R.id.tv_msg:
                //弹出软键盘开始聊天
                settingContainerLayout.setVisibility(View.GONE);
                inputLayout.setVisibility(View.VISIBLE);
                bottomLayout.setVisibility(View.GONE);
                KeyboardUtil.showKeyboard(etMessage);
                break;
            case R.id.img_close:
                logout();
                break;
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
                createPop();
                scaleWindow();
                crowdRecommendGoodPop.show(imgBuy);
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
                smallWindowPlay();
                Intent intent = new Intent(LiveAudienceActivity.this, ProductDetailActivity.class);
                intent.putExtra("id", (long) view.getTag());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                break;
            case R.id.heart_layout:
                heartLayout.addHeart();
                CommandNotificationMessage message = CommandNotificationMessage.obtain(":DIANZAN", "");
                RongIMClient.getInstance().sendMessage(Conversation.ConversationType.CHATROOM, mChannel, message, null, null, new IRongCallback.ISendMessageCallback() {
                    @Override
                    public void onAttached(Message message) {
                    }

                    @Override
                    public void onSuccess(Message message) {
                        Log.d("", "");
                    }

                    @Override
                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                    }
                });
                break;
            case R.id.xin:
                heartLayout.addHeart();
                CommandNotificationMessage message2 = CommandNotificationMessage.obtain(":DIANZAN", "");
                RongIMClient.getInstance().sendMessage(Conversation.ConversationType.CHATROOM, mChannel, message2, null, null, new IRongCallback.ISendMessageCallback() {
                    @Override
                    public void onAttached(Message message) {
                    }

                    @Override
                    public void onSuccess(Message message) {
                        Log.d("", "");
                    }

                    @Override
                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                    }
                });
                break;
        }
    }

    private void sendPresentMessage2Rong() {
        if (presentsBeanList == null) {
            return;
        }
        LivePresentsBean.DataBean.PresentsBean presentsBean = presentsBeanList.get(currentPresentPosition);
        long currentTime = System.currentTimeMillis();
        if (!Util.isEmpty(lastGiftName)) {
            if (lastSendTime == null) {
                giftCount = 1;
            } else {
                if (currentTime - lastSendTime < 8000 && lastGiftName.equals(presentsBean.getName())) {
                    giftCount++;
                } else {
                    giftCount = 1;
                }
            }
        } else {
            giftCount = 1;
        }
        PresentMessage presentMessage = new PresentMessage(
                String.valueOf(presentsBean.getId()), presentsBean.getName(), presentsBean.getPic(), giftCount);
        lastSendTime = currentTime;
        lastGiftName = presentsBean.getName();
        UserInfo info = new UserInfo(String.valueOf(user.getId()), user.getNickname(),
                !Util.isEmpty(user.getHead()) ? Uri.parse(user.getHead()) : null);
        presentMessage.setUserInfo(info);
        RongIMClient.getInstance().sendMessage(Conversation.ConversationType.CHATROOM, mChannel, presentMessage,
                null, null, new IRongCallback.ISendMessageCallback() {
                    @Override
                    public void onAttached(Message message) {
                    }

                    @Override
                    public void onSuccess(Message message) {
                        showMessageList(message);
                    }

                    @Override
                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                    }
                });
    }

    private String getJsonStr(long id, String productName) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append("\"productId\":");
        builder.append(id);
        builder.append(",");
        builder.append("\"productName\":");
        builder.append("\"");
        builder.append(productName);
        builder.append("\"");
        builder.append("}");
        return builder.toString();
    }

    protected void sendMessage(String action, String json) {
        if (user == null) return;
        final InformationNotificationMessage notificationMessage = new InformationNotificationMessage(action);
        if (!Util.isEmpty(json)) {
            notificationMessage.setExtra(json);
        }
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
                Message message1 = new Message();
                message1.setContent(notificationMessage);
                messages.add(message1);
                messageAdapter.notifyDataSetChanged();
                chatListview.setSelection(messages.size() - 1);
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
            }
        });
    }


    private void createPop() {
        crowdRecommendGoodPop = new CrowdRecommendGoodPop(LiveAudienceActivity.this, bean.getId());
        crowdRecommendGoodPop.setBackCoverColor(R.color.transparent);
        crowdRecommendGoodPop.setDissBack(new TransparentPop.DismissListener() {
            @Override
            public void dismissStart() {
                windowReset();
            }

            @Override
            public void dismissEnd() {

            }
        });
        crowdRecommendGoodPop.setBuyBack(new CrowdRecommendGoodPop.BuyBack() {

            @Override
            public void liveAddCartBack(long id, String productName) {
                sendMessage(ADDCART, getJsonStr(id, productName));
            }

            @Override
            public void liveBuyBack(long id, String productName) {
                sendMessage(BUY, getJsonStr(id, productName));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        smallWindowPlay();
                    }
                }, 500);
            }

            @Override
            public void toOtherBack() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        smallWindowPlay();
                    }
                }, 500);
            }

            @Override
            public void hasRecommendData(boolean is) {
                if (!is) {
                    tvRedPoint.setVisibility(View.GONE);
                }
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
            if (!user.isD2c()) {
                DialogUtil.showMsgDialog(LiveAudienceActivity.this, getString(R.string.msg_no_open_wallet), getString(R.string.label_goto_open_wallet),
                        R.color.color_blue5, null, R.color.color_blue5, false,
                        new DialogClickListener() {
                            @Override
                            public void onConfirm() {
                                //smallWindowPlay();
                                Intent intent2 = new Intent(LiveAudienceActivity.this, BindPhoneActivity.class);
                                startActivityForResult(intent2, Constants.RequestCode.BIND_PHONE);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                            }

                            @Override
                            public void onCancel() {
                            }

                            @Override
                            public void onDismiss() {

                            }
                        });
            } else {
//                if (accountEntity == null) {
//                    return;
//                }
//                if (accountEntity.isSetPassword()) {
//                    if (barrageAmount > 0) {
//                        if (barrageAmount > accountEntity.getAvailableAmount()) {
//                            DialogUtil.showMsgDialog(LiveAudienceActivity.this, getString(R.string.msg_no_money), getString(R.string.label_goto_money),
//                                    R.color.color_blue5, null, R.color.color_blue5, false,
//                                    new DialogClickListener() {
//                                        @Override
//                                        public void onConfirm() {
//                                            //smallWindowPlay();
//                                            Intent intent2 = new Intent(LiveAudienceActivity.this, RechargeActivity.class);
//                                            startActivityForResult(intent2, Constants.RequestCode.DEPOSIT);
//                                            overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
//                                        }
//
//                                        @Override
//                                        public void onCancel() {
//                                        }
//
//                                        @Override
//                                        public void onDismiss() {
//                                        }
//                                    });
//                        } else {
//                            //收费弹幕，弹出支付密码窗口
//                            //showPayPasswordDialog(1, content);
//                        }
//                    } else {
//                        sendTextMessage(content, true);
//                        hideKeyboard(null);
//                        inputLayout.setVisibility(View.GONE);
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                bottomLayout.setVisibility(View.VISIBLE);
//                            }
//                        }, 200);
//                    }
//                } else {
//                    DialogUtil.showMsgDialog(LiveAudienceActivity.this, getString(R.string.msg_no_set_paypwd), getString(R.string.label_goto_set),
//                            R.color.color_blue5, null, R.color.color_blue5, false,
//                            new DialogClickListener() {
//                                @Override
//                                public void onConfirm() {
//                                    //smallWindowPlay();
//                                    Intent intent = new Intent(LiveAudienceActivity.this, SendCodeChangePayPsdActivity.class);
//                                    intent.putExtra("type", 4);
//                                    String bindedMobile=user.getMobile();
//                                    intent.putExtra("bindedAccount", bindedMobile);
//                                    startActivity(intent);
//                                    overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
//                                }
//
//                                @Override
//                                public void onCancel() {
//                                }
//
//                                @Override
//                                public void onDismiss() {
//
//                                }
//                            });
//                }
            }
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

    private PLMediaPlayer.OnErrorListener mOnErrorListener = new PLMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(PLMediaPlayer mp, int errorCode) {
            Log.e("live", "Error happened, errorCode = " + errorCode);
            switch (errorCode) {
                case PLMediaPlayer.ERROR_CODE_IO_ERROR:
                    /**
                     * SDK will do reconnecting automatically
                     */
                    int netType = Util.getAPNType(LiveAudienceActivity.this);
                    switch (netType) {
                        case -1://没有网络
                            Util.showToast(LiveAudienceActivity.this, "您的网络不通畅哦~");
                            break;
                        case 1://wifi网络
                            //Util.showToast(LiveAudienceActivity.this,"您当前正在使用Wifi网络");
                            break;
                        case 2://移动网络
                            //Util.showToast(LiveAudienceActivity.this,"您当前正在使用移动网络");
                            break;
                    }
                    Log.e("live", "IO Error!");
                    return false;
                case PLMediaPlayer.ERROR_CODE_OPEN_FAILED:
                    Log.e("live", "failed to open player !");
                    break;
                case PLMediaPlayer.ERROR_CODE_SEEK_FAILED:
                    Log.e("live", "failed to seek !");
                    break;
                case PLMediaPlayer.ERROR_CODE_PLAYER_DESTROYED:
                    Log.e("live", "播放器已被销毁 !");
                    break;
                default:
                    Log.e("live", "unknown error !");
                    break;
            }
            return true;
        }
    };

    private void initListener() {
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
//                if (anchorRecommendGoodPop != null && anchorRecommendGoodPop.isShow()) {
//                    return;
//                }
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

    private void addHeadPic(String id, String headPic) {
        if (Util.isEmpty(id)) {
            return;
        }
        if (Long.valueOf(id) == AnchorInfoBean.getData().getMember().getMemberId()) {
            return;
        }
        LiveAudienceInBean.DataBean.RealtimeBean.HeadersBean bean = new LiveAudienceInBean.DataBean.RealtimeBean.HeadersBean();
        //int count = Integer.valueOf(tvViewCount.getText().toString().replace("人", "")) + 1;
        //tvViewCount.setText(count + "人");
        bean.setHeadPic(headPic);
        bean.setMemberId(Long.valueOf(id));
        headBeanArrayList.add(0, bean);
        liveHeanAdapter.notifyDataSetChanged();
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
                                    //添加头像
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
                        showLiveOffLayout();
                        if (guideLayout.isAddView()) {
                            guideLayout.hide();
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
//                    if (crowdRecommendGoodPop != null) {
//                        crowdRecommendGoodPop.stick(id);
//                    }
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
                            lastCount = watchCount;
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
                public void run() {
                    PresentMessage presentMessage = (PresentMessage) message.getContent();
                    if (presentMessage.getUserInfo() != null) {
                        String headUrl = null;
                        if (presentMessage.getUserInfo().getPortraitUri() != null) {
                            headUrl = Util.getD2cPicUrl(presentMessage.getUserInfo().getPortraitUri().toString());
                        }
                        LiveAnimationBean liveAnimationBean = new LiveAnimationBean(headUrl, presentMessage.getUserInfo().getName(),
                                presentMessage.getPresentName(), presentMessage.getPresentUrl());
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

//    protected void startPresentAnimation(LiveAnimationBean bean) {
//        if (!giftChildLayout1.isShowing()) {
//            sendPresentAnimation(giftChildLayout1, bean);
//        } else if (!giftChildLayout2.isShowing()) {
//            sendPresentAnimation(giftChildLayout2, bean);
//        } else {
//            liveAnimationBeanList.add(bean);
//        }
//    }

//    private void sendPresentAnimation(final GiftFrameLayout view, LiveAnimationBean bean) {
//        view.setModel(bean);
//        AnimatorSet animatorSet = view.startAnimation();
//        animatorSet.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                synchronized (liveAnimationBeanList) {
//                    if (liveAnimationBeanList.size() > 0) {
//                        view.startAnimation();
//                        liveAnimationBeanList.remove(liveAnimationBeanList.size() - 1);
//                    }
//                }
//            }
//        });
//    }

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
        priceTv.setText("￥" + Util.getNumberFormat(price));
        recommendGoodPic.setVisibility(View.VISIBLE);
        UniversalImageLoader.displayImage(this, url, recommendGoodPic, R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
        recommendGoodLayout.setTag(id);
        mHander.removeMessages(1);
        mHander.sendEmptyMessageDelayed(1, 8000);
    }

    private void removeHeadPic(String userId) {
        //int count = Integer.valueOf(tvViewCount.getText().toString().replace("人", "")) - 1;
        //tvViewCount.setText(count + "人");
        for (int i = 0; i < headBeanArrayList.size(); i++) {
            if (Long.valueOf(userId) == headBeanArrayList.get(i).getMemberId()) {
                headBeanArrayList.remove(i);
                liveHeanAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (crowdRecommendGoodPop != null && crowdRecommendGoodPop.isShow()) {
            crowdRecommendGoodPop.isFinish = true;
            crowdRecommendGoodPop.dismiss();
        } else {
            logout();
        }
    }

    @Subscribe
    public void onEventMainThread(ActionBean bean) {
        if (bean.type == Constants.ActionType.CLEAR_ALL_ACTIVITY) {
            if (crowdRecommendGoodPop != null && crowdRecommendGoodPop.isShow()) {
                crowdRecommendGoodPop.isFinish = true;
                crowdRecommendGoodPop.dismiss();
            }
            logout();
        }
    }

    protected void logout() {
        if (loadingLayout.getVisibility() == View.VISIBLE || liveOffLayout.getVisibility() == View.VISIBLE
                || notNetworkLayout.getVisibility() == View.VISIBLE) {
            anchorOrWatcherLogout();
            finish();
        } else {
            //观众退出
            anchorOrWatcherLogout();
            finish();

        }
    }

    protected void anchorOrWatcherLogout() {
        //游客退出房间
        SimpleApi api = new SimpleApi();
        api.setMethod(BaseApi.Method.POST);
        api.setInterPath(String.format(Constants.LIVE_CUSTOM_OUT, bean.getId()));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<LiveAudienceInBean>() {
            @Override
            public void onResponse(LiveAudienceInBean baseBean) {
                //if (baseBean.getData().getRealTime() != null) {
                //int watchCount = baseBean.getData().getRealTime().getWatchingCount();
                //tvViewCount.setText(getString(R.string.label_live_watching, watchCount));
                //}
                StringBuilder builder = new StringBuilder();
                builder.append("{");
                builder.append("\"watchCount\":");
                builder.append(baseBean.getData().getRealtime().getCount());
                builder.append("}");
                CommandNotificationMessage message = CommandNotificationMessage.obtain(":TC", builder.toString());
                RongIMClient.getInstance().sendMessage(Conversation.ConversationType.CHATROOM, mChannel, message, null, null, new IRongCallback.ISendMessageCallback() {
                    @Override
                    public void onAttached(Message message) {
                    }

                    @Override
                    public void onSuccess(Message message) {
                        quitCharRoom();//退出融云
                    }

                    @Override
                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        //通知页面刷新
        //EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.REFRESH_LIVE));
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

    protected void showLiveOffLayout() {
//        if (hostDialog != null && hostDialog.isShowing()) {
//            hostDialog.dismiss();
//        }
//        if (pwdDialog != null && pwdDialog.isShowing()) {
//            pwdDialog.dismiss();
//        }
        hideKeyboard(null);
        if (AnchorInfoBean == null) {
            return;
        }
        liveOffLayout.setVisibility(View.VISIBLE);
        imgCloseOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        if (!isFinishing()) {
            Glide.with(this).load(R.mipmap.icon_zhibo_beijing)
                    .error(R.mipmap.ic_default_cover)
                    .bitmapTransform(new BlurTransformation(this))
                    .into(imgCoverOff);
            UniversalImageLoader.displayRoundImage(this, Util.getD2cPicUrl(bean.getHeadPic()),
                    imgHeadOff, R.mipmap.ic_default_avatar);
        }
        tvAnchorNameOff.setText(bean.getNickname());
        tvTitle.setText(bean.getTitle());
        tvCountFans.setText(String.valueOf(AnchorInfoBean.getData().getFansTotalCount()));
        requestInWatchedCount();//请求累计观看人数
        if (ivCare.getVisibility() == View.GONE) {
            btnFocus.setImageResource(R.mipmap.icon_zhibo_yiguanzhu);
        } else {
            btnFocus.setImageResource(R.mipmap.icon_zhibo_over_guanzhu);
        }
        btnFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FollowApi api = new FollowApi();
                api.setToMemberId(bean.getMemberId());
                if (AnchorInfoBean.getData().getFollow() == 0) {
                    api.setInterPath(Constants.INSERT_MY_FOLLOWS_URL);
                    D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                        @Override
                        public void onResponse(BaseBean response) {
                            AnchorInfoBean.getData().setFollow(1);
                            btnFocus.setImageResource(R.mipmap.icon_zhibo_yiguanzhu);
                            Util.showToast(LiveAudienceActivity.this, "关注成功");
                            ivCare.showMsgPop( LiveAudienceActivity.this,getString(R.string.label_pop_focus_people));
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Util.showToast(LiveAudienceActivity.this, Util.checkErrorType(error));
                        }
                    });
                } else {
                    api.setInterPath(Constants.DELETE_MY_FOLLOWS_URL);
                    D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                        @Override
                        public void onResponse(BaseBean response) {
                            AnchorInfoBean.getData().setFollow(0);
                            btnFocus.setImageResource(R.mipmap.icon_zhibo_over_guanzhu);
                            Util.showToast(LiveAudienceActivity.this, "已取消关注");
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Util.showToast(LiveAudienceActivity.this, Util.checkErrorType(error));
                        }
                    });
                }
            }
        });
    }

    private void requestInWatchedCount() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.LIVE_STATUS_INFO, bean.getId()));
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


    private void notifyMessage(Message message) {
        messages.add(message);
        messageAdapter.notifyDataSetChanged();
        chatListview.setSelection(messages.size() - 1);
    }

    private void getRongToken() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.RONG_NEW_TOKEN_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<RongTokenBean>() {
            @Override
            public void onResponse(RongTokenBean response) {
                if (!Util.isEmpty(response.getData().getToken())) {
                    user.setImToken(response.getData().getToken());
                    Session.getInstance().saveUserToFile(LiveAudienceActivity.this, user);
                    connect2Rong(response.getData().getToken());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }

    protected void initBaseViews() {
        user = Session.getInstance().getUserFromFile(this);
        if (user != null) {
            if (!Util.isEmpty(user.getImToken())) {
                connect2Rong(user.getImToken());
            } else {
                getRongToken();
            }
        }
        headBeanArrayList = new ArrayList<>();
        liveHeanAdapter = new LiveHeanAdapter(headBeanArrayList, this);
        picLayout.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        picLayout.setAdapter(liveHeanAdapter);
        messages = new ArrayList<>();
        videoView.setPivotX(ScreenUtil.getDisplayWidth() / 2);
        videoView.setPivotY(1);
        messageAdapter = new RongMessageListAdapter(this, messages);
        chatListview.setAdapter(messageAdapter);
        guideLayout = GuideLayout.getInstance(getApplicationContext());
        guideLayout.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guideLayout.hide();
                Intent intent = new Intent(LiveAudienceActivity.this, LiveAudienceActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("bean", bean);
                LiveAudienceActivity.this.startActivity(intent);
                LiveAudienceActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
            }
        });
        guideLayout.setClostListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guideLayout.hide();
            }
        });
        //liveAnimationBeanList = new ArrayList<>();
        presentPagerList = new ArrayList<>();
        try {
            gifFromAssets = new GifDrawable(getResources(), R.mipmap.buy);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgBuy.setImageDrawable(gifFromAssets);
        requestPresentList();
    }

    private void requestPresentList() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.LIVE_PRESENTS_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<LivePresentsBean>() {
            @Override
            public void onResponse(final LivePresentsBean livePresentsBean) {
                presentsBeanList = livePresentsBean.getData().getPresents();
                chatListview.post(new Runnable() {
                    @Override
                    public void run() {
                        int pageCount = (int) Math.ceil(presentsBeanList.size() * 1.0 / 8);
                        for (int i = 0; i < pageCount; i++) {
                            WrapLineGridView gridView = (WrapLineGridView) LayoutInflater.from(
                                    LiveAudienceActivity.this).inflate(R.layout.layout_gridview, null);
                            gridView.setAdapter(new GridViewAdapter(LiveAudienceActivity.this, presentsBeanList, i, 8));
                            presentPagerList.add(gridView);
                            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    oldPresentPosition = currentPresentPosition;
                                    currentPresentPosition = position + currentPageIndex * 8;
                                    setRefreshAdapter();
                                    setButtonStatus();
                                }
                            });
                        }
                        viewPager.setAdapter(new ViewPagerAdapter(presentPagerList));
                        setPresentsOvalLayout(pageCount);
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("tag","错误了"+Util.checkErrorType(error));
            }
        });
    }

    private void setButtonStatus() {
        tvFreeSend.setVisibility(View.VISIBLE);
    }

    private void setPresentsOvalLayout(int pageCount) {
        for (int i = 0; i < pageCount; i++) {
            dotLayout.addView(LayoutInflater.from(this).inflate(R.layout.layout_presents_dots, null));
        }
        dotLayout.getChildAt(0).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dot_selected);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //圆点未选中
                dotLayout.getChildAt(currentPageIndex).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dot_normal);
                //圆点选中
                dotLayout.getChildAt(position).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dot_selected);
                currentPageIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setRefreshAdapter() {
        for (int i = 0; i < presentPagerList.size(); i++) {
            GridViewAdapter adapter = (GridViewAdapter) presentPagerList.get(i).getAdapter();
            if (i == currentPageIndex) {
                adapter.setSelectedPosition(currentPageIndex, currentPresentPosition, true);
            } else {
                adapter.setSelectedPosition(i, currentPresentPosition, false);
            }
        }
    }


    private class ViewPagerAdapter extends PagerAdapter {
        private List<GridView> mViewList;

        public ViewPagerAdapter(List<GridView> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public int getCount() {
            if (mViewList == null) {
                return 0;
            }
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private void initVideoView() {
        AVOptions options = new AVOptions();
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, 1);
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_MEDIACODEC,AVOptions.MEDIA_CODEC_AUTO);
        url = bean.getRtmpUrl();
        videoView.setOnErrorListener(mOnErrorListener);
        videoView.setAVOptions(options);
        videoView.setBufferingIndicator(loadingView);
        videoView.setCoverView(coverView);
        videoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_PAVED_PARENT);
        videoView.setVideoPath(url);
        Log.e("han","播放器地址:"+url);
        if (guideLayout != null) {
            guideLayout.setVideoUrl(url);
        }
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
        watcherInBack();
        getCoupon();
    }

    private void getCoupon() {
        LiveCouponApi api = new LiveCouponApi();
        api.setLiveId(String.valueOf(bean.getId()));
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
                                Util.showToast(LiveAudienceActivity.this, "领取成功");
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Util.showToast(LiveAudienceActivity.this, Util.checkErrorType(error));
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

    private void watcherInBack() {
        //游客进入房间
        SimpleApi api = new SimpleApi();
        api.setMethod(BaseApi.Method.POST);
        api.setInterPath(String.format(Constants.LIVE_CUSTOM_IN, bean.getId()));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<LiveAudienceInBean>() {
            @Override
            public void onResponse(LiveAudienceInBean baseBean) {
                //barrageAmount = baseBean.getData().getBarrageAmount();
                if (baseBean.getData() != null) {
                    int watchCount = baseBean.getData().getRealtime().getCount();
                    lastCount = watchCount;
                    tvViewCount.setText(getString(R.string.label_live_watching, watchCount));
                    for (int i = 0; i < baseBean.getData().getRealtime().getHeaders().size(); i++) {
                        if (bean.getMemberId() == baseBean.getData().getRealtime().getHeaders().get(i).getMemberId()) {
                            baseBean.getData().getRealtime().getHeaders().remove(i);
                        }
                    }
                    headBeanArrayList.addAll(0, baseBean.getData().getRealtime().getHeaders());
                    liveHeanAdapter.notifyDataSetChanged();
                    StringBuilder builder = new StringBuilder();
                    builder.append("{");
                    builder.append("\"watchCount\":");
                    builder.append(watchCount);
                    builder.append("}");
                    CommandNotificationMessage message = CommandNotificationMessage.obtain(":TC", builder.toString());
                    RongIMClient.getInstance().sendMessage(Conversation.ConversationType.CHATROOM, mChannel, message, null, null, new IRongCallback.ISendMessageCallback() {
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
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(LiveAudienceActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void smallWindowPlay() {
        guideLayout.sendMessage();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videoView != null) {
            videoView.start();
        }
        if (guideLayout.isAddView()) {
            guideLayout.hide();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.stopPlayback();
        RongIMClient.setOnReceiveMessageListener(null);
        guideLayout.setClickListener(null);
        guideLayout.setClostListener(null);
        guideLayout = null;
    }

}
