package com.d2cmall.buyer.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.AnchorInfoApi;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.FollowApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.ClickFollowBean;
import com.d2cmall.buyer.bean.ExploreTopInfoBean;
import com.d2cmall.buyer.bean.LiveListBean;
import com.d2cmall.buyer.bean.PersonInfoBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.CrowdRecommendGoodPop;
import com.d2cmall.buyer.widget.MyMediaController;
import com.d2cmall.buyer.widget.SharePop;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * 录播视频
 * Author: hrb
 * Date: 2016/12/30 18:03
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class VideoActivity extends BaseActivity {

    @Bind(R.id.videoView)
    PLVideoView videoView;
    @Bind(R.id.preview_layout)
    RelativeLayout previewLayout;
    @Bind(R.id.img_avatar)
    ImageView imgAvatar;
    @Bind(R.id.tv_care)
    ImageView tvCare;
    @Bind(R.id.title_left)
    LinearLayout titleLeft;
    @Bind(R.id.img_share)
    ImageView imgShare;
    @Bind(R.id.img_close)
    ImageView imgClose;
    @Bind(R.id.progress)
    ProgressBar progress;
    @Bind(R.id.img_buy)
    GifImageView imgBuy;
    @Bind(R.id.tv_anchor_name)
    TextView tvAnchorName;
    @Bind(R.id.tv_view_count)
    TextView tvViewCount;
    private boolean isSelf = false;
    private CrowdRecommendGoodPop listPop;
    private LiveListBean.DataBean.LivesBean.ListBean bean;
    private GifDrawable gifFromAssets = null;

    private static final String TAG = "VideoActivity";
    private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";
    private String VIDEO_URL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    private int mSeekPosition;
    private Dialog hostDialog;
    private PersonInfoBean exploreTopInfoBean;
    private MyMediaController mediaController;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            setProgress();
            handler.sendMessageDelayed(Message.obtain(), 1000);
        }
    };

    private PLMediaPlayer.OnErrorListener onErrorListener=new PLMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(PLMediaPlayer plMediaPlayer, int errorCode) {
            switch (errorCode) {
                case PLMediaPlayer.ERROR_CODE_IO_ERROR:
                    /**
                     * SDK will do reconnecting automatically
                     */
                    Log.e(TAG, "IO Error!");
                    return false;
                case PLMediaPlayer.ERROR_CODE_OPEN_FAILED:
                    Util.showToast(VideoActivity.this,"打开播放器失败");
                    finish();
                    break;
                case PLMediaPlayer.ERROR_CODE_SEEK_FAILED:
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_video1);
        ButterKnife.bind(this);
        bean = (LiveListBean.DataBean.LivesBean.ListBean) getIntent().getSerializableExtra("bean");
        UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(this);
        if (user != null) {
            if (user.getMemberId() == bean.getMemberId()) {
                isSelf = true;
                tvCare.setVisibility(View.GONE);
            }
        }
        initData();
        initPlayer();
    }

    private void initData() {
        requestInWatch();
        requestHostInfo();
        tvViewCount.setText(String.valueOf(bean.getVcount()+bean.getVfans())+"人");
    }

    private void requestInWatch() {
        SimpleApi api=new SimpleApi();
        api.setMethod(BaseApi.Method.POST);
        api.setInterPath(String.format(Constants.LIVE_RECORD_IN,bean.getId()));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }


    private void setProgress() {
        if (progress != null) {
            long current = videoView.getCurrentPosition();
            long duration = videoView.getDuration();
            if(duration==0){
                return;
            }
            long pos = 1000L * current / duration;
            progress.setProgress((int) pos);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new ContextWrapper(newBase) {
            @Override
            public Object getSystemService(String name) {
                if (Context.AUDIO_SERVICE.equals(name))
                    return getApplicationContext().getSystemService(name);
                return super.getSystemService(name);
            }
        });
    }

    private void initPlayer() {
        VIDEO_URL = bean.getReplayUrl();
        tvAnchorName.setText(bean.getNickname());
        mediaController = new MyMediaController(this);
        videoView.setCoverView(previewLayout);
        videoView.setBufferingIndicator(previewLayout);
        setVideoAVOptions();
        videoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_PAVED_PARENT);
        videoView.setVideoPath(VIDEO_URL);
        videoView.setMediaController(mediaController);
        videoView.setOnErrorListener(onErrorListener);
        UniversalImageLoader.displayRoundImage(this, Util.getD2cPicUrl(bean.getHeadPic()), imgAvatar, R.mipmap.ic_default_avatar);
        mediaController.setOnHiddenListener(new MyMediaController.OnHiddenListener() {
            @Override
            public void onHidden() {
                progress.setVisibility(View.VISIBLE);
                imgBuy.setVisibility(View.VISIBLE);
                imgShare.setVisibility(View.VISIBLE);
            }
        });
        mediaController.setOnShownListener(new MyMediaController.OnShownListener() {
            @Override
            public void onShown() {
                progress.setVisibility(View.GONE);
                imgBuy.setVisibility(View.GONE);
                imgShare.setVisibility(View.GONE);
            }
        });
        try {
            gifFromAssets = new GifDrawable(getResources(), R.mipmap.buy);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgBuy.setImageDrawable(gifFromAssets);
        handler.sendMessage(Message.obtain());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.stopPlayback();
        handler.removeCallbacks(null);
    }

    @OnClick({R.id.img_share, R.id.img_close, R.id.img_avatar, R.id.img_buy, R.id.tv_care})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_care:
                focus();
                break;
            case R.id.img_buy:
                if (bean == null) {
                    return;
                }
                listPop = new CrowdRecommendGoodPop(VideoActivity.this, bean.getId());
                listPop.show(imgAvatar);
                break;
            case R.id.img_share:
                videoView.pause();
                if (bean == null) {
                    return;
                }
                SharePop sharePop = new SharePop(this);
                sharePop.setTitle(getString(R.string.live_share_txt,
                        bean.getTitle()));
                String title=bean.getNickname()+getResources().getString(R.string.live_share_new_txt);
                sharePop.setDescription(title);
                sharePop.setWebUrl(Constants.SHARE_URL + "/live/" + bean.getId());
                sharePop.show(getWindow().getDecorView());
                break;
            case R.id.img_close:
                onBackPressed();
                break;
            case R.id.img_avatar:
                //requestHostInfo();
                break;
        }
    }

    private void focus() {
        if(exploreTopInfoBean!=null&&exploreTopInfoBean.getData()!=null){
            if (exploreTopInfoBean.getData().getFollow() == 0) {
                FollowApi api = new FollowApi();
                api.setToMemberId(exploreTopInfoBean.getData().getMember().getMemberId());
                api.setInterPath(Constants.INSERT_MY_FOLLOWS_URL);
                D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                    @Override
                    public void onResponse(BaseBean response) {
                        Util.showToast(VideoActivity.this, R.string.msg_focus_ok);
                        tvCare.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Util.showToast(VideoActivity.this, Util.checkErrorType(error));
                    }
                });
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState Position=" + videoView.getCurrentPosition());
        outState.putInt(SEEK_POSITION_KEY, mSeekPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        mSeekPosition = outState.getInt(SEEK_POSITION_KEY);
        Log.d(TAG, "onRestoreInstanceState Position=" + mSeekPosition);
    }

    private class FocusClickListener implements View.OnClickListener {

        private long focusMemberId;
        private int type;
        private TextView tvHostFocus;

        public FocusClickListener(int type, long focusMemberId) {
            super();
            this.type = type;
            this.focusMemberId = focusMemberId;
        }

        public FocusClickListener(int type, TextView tvHostFocus, long focusMemberId) {
            super();
            this.type = type;
            this.tvHostFocus = tvHostFocus;
            this.focusMemberId = focusMemberId;
        }

        @Override
        public void onClick(View v) {
            if (Util.loginChecked(VideoActivity.this, Constants.Login.VIDEO)) {
                focusComplete();
            }
        }

        private void focusComplete() {
            //差关注数据
            if (type == 1) {
                if (1 > 0) {
                    tvCare.setVisibility(View.VISIBLE);
                    FollowApi api1 = new FollowApi();
                    api1.setToMemberId(focusMemberId);
                    api1.setInterPath(Constants.DELETE_MY_FOLLOWS_URL);
                    D2CApplication.httpClient.loadingRequest(api1, new BeanRequest.SuccessListener<BaseBean>() {
                        @Override
                        public void onResponse(BaseBean baseBean) {
                            //bean.setFollow(0);
                            Util.showToast(VideoActivity.this, R.string.msg_focus_canceled);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Util.showToast(VideoActivity.this, Util.checkErrorType(error));
                        }
                    });
                } else {
                    tvCare.setVisibility(View.GONE);
                    FollowApi api2 = new FollowApi();
                    api2.setToMemberId(focusMemberId);
                    api2.setInterPath(Constants.FOLLOW_MEMBER_URL);
                    D2CApplication.httpClient.loadingRequest(api2, new BeanRequest.SuccessListener<ClickFollowBean>() {
                        @Override
                        public void onResponse(ClickFollowBean clickFollowBean) {
                            //bean.setFollow(clickFollowBean.getData().getFollow());
                            Util.showToast(VideoActivity.this, R.string.msg_focus_ok);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Util.showToast(VideoActivity.this, Util.checkErrorType(error));
                        }
                    });
                }
            } else if (type == 2) {
                if (tvCare.getVisibility() == View.GONE) {
                    tvCare.setVisibility(View.VISIBLE);
                    tvHostFocus.setText(R.string.label_plus_focus);
                    FollowApi api3 = new FollowApi();
                    api3.setToMemberId(focusMemberId);
                    api3.setInterPath(Constants.UNFOLLOW_MEMBER_URL);
                    D2CApplication.httpClient.loadingRequest(api3, new BeanRequest.SuccessListener<BaseBean>() {
                        @Override
                        public void onResponse(BaseBean baseBean) {
                            exploreTopInfoBean.getData().setFollow(0);
                            Util.showToast(VideoActivity.this, R.string.msg_focus_canceled);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Util.showToast(VideoActivity.this, Util.checkErrorType(error));
                        }
                    });
                } else {
                    tvCare.setVisibility(View.GONE);
                    tvHostFocus.setText(R.string.label_beliked);
                    FollowApi api4 = new FollowApi();
                    api4.setToMemberId(focusMemberId);
                    api4.setInterPath(Constants.FOLLOW_MEMBER_URL);
                    D2CApplication.httpClient.loadingRequest(api4, new BeanRequest.SuccessListener<ClickFollowBean>() {
                        @Override
                        public void onResponse(ClickFollowBean clickFollowBean) {
                            Util.showToast(VideoActivity.this, R.string.msg_focus_ok);
                            exploreTopInfoBean.getData().setFollow(clickFollowBean.getData().getFollow());
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Util.showToast(VideoActivity.this, Util.checkErrorType(error));
                        }
                    });
                }
            }
        }
    }

    private void setFollowsData() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.NEW_EXPLORE_INFO_URL, bean.getMemberId()));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ExploreTopInfoBean>() {
            @Override
            public void onResponse(ExploreTopInfoBean bean) {
                UserBean.DataEntity.MemberEntity memberEntity = Session.getInstance().getUserFromFile(VideoActivity.this);
                if (bean.getData().getFollow() == 0) {
                    if (!(memberEntity != null && memberEntity.getMemberId() == bean.getData().getMember().getMemberId())) {
                        tvCare.setVisibility(View.VISIBLE);
                    }
                } else {
                    tvCare.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constants.Login.VIDEO:
                    Intent intent = new Intent(this, PersonInfoActivity.class);
                    intent.putExtra("memberId", bean.getMemberId());
                    startActivityForResult(intent, Constants.RequestCode.GO_TO_EXPORT_INFO);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    private void setVideoAVOptions() {
        AVOptions options = new AVOptions();
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, 0);
        videoView.setAVOptions(options);
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.start();
    }

    //请求主播信息
    private void requestHostInfo() {
        imgAvatar.setEnabled(false);
        AnchorInfoApi api = new AnchorInfoApi();
        api.setInterPath(String.format(Constants.LIVE_ANCHOR_INFO, bean.getMemberId()));
        api.setLiveId(bean.getId());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<PersonInfoBean>() {
            @Override
            public void onResponse(PersonInfoBean bean) {
                exploreTopInfoBean = bean;
                if(exploreTopInfoBean.getData().getFollow()==0&&!isSelf){
                    tvCare.setVisibility(View.VISIBLE);
                }
                imgAvatar.setEnabled(true);
                //showHostInfoDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                imgAvatar.setEnabled(true);
            }
        });
    }

    private void showHostInfoDialog() {
        if (exploreTopInfoBean == null) {
            return;
        }
        hostDialog = new Dialog(this, R.style.bubble_dialog);
        Point point = Util.getDeviceSize(this);
        View dialogView = getLayoutInflater().inflate(R.layout.layout_member_info_dialog, null);
        ImageView imgAvatar = (ImageView) dialogView.findViewById(R.id.img_avatar);
        UniversalImageLoader.displayRoundImage(this, Util.getD2cPicUrl(exploreTopInfoBean.getData().getMember().getHead()), imgAvatar, R.mipmap.ic_default_avatar);
        TextView tvNick = (TextView) dialogView.findViewById(R.id.tv_nick);
        TextView tvBrandName = (TextView) dialogView.findViewById(R.id.tv_brand_name);
        TextView tvFansCount = (TextView) dialogView.findViewById(R.id.tv_fans_count);
        if (exploreTopInfoBean.getData().getFansTotalCount() > 10000) {
            tvFansCount.setText(String.valueOf(exploreTopInfoBean.getData().getFansTotalCount() / 10000 + "万"));
        } else {
            tvFansCount.setText(String.valueOf(exploreTopInfoBean.getData().getFansTotalCount()));
        }
        TextView tvGiveCount = (TextView) dialogView.findViewById(R.id.tv_give_count);
//        if (exploreTopInfoBean.getData().getGiveAmount() > 10000) {
//            tvGiveCount.setText((int) exploreTopInfoBean.getData().getGiveAmount() / 10000 + "万");
//        } else {
//            tvGiveCount.setText(Util.getNumberFormat(exploreTopInfoBean.getData().getGiveAmount(), 1));
//        }
        TextView tvFocusCount = (TextView) dialogView.findViewById(R.id.tv_focus_count);
        tvFocusCount.setText(String.valueOf(exploreTopInfoBean.getData().getFollowsTotalCount()));
        TextView tvReceiveCount = (TextView) dialogView.findViewById(R.id.tv_receive_count);
//        if (exploreTopInfoBean.getData().getPresentAmount() > 10000) {
//            tvReceiveCount.setText((int) exploreTopInfoBean.getData().getPresentAmount() / 10000 + "万");
//        } else {
//            tvReceiveCount.setText(Util.getNumberFormat(exploreTopInfoBean.getData().getPresentAmount(), 1));
//        }
        tvNick.setText(exploreTopInfoBean.getData().getMember().getNickname());
        ImageView imgType = (ImageView) dialogView.findViewById(R.id.img_type);
        ImageView imgSex = (ImageView) dialogView.findViewById(R.id.img_sex);
        imgType.setVisibility(exploreTopInfoBean.getData().getMember().getType() > 1 ? View.VISIBLE : View.GONE);
        if (exploreTopInfoBean.getData().getMember().getSex().equals("女")) {
            imgSex.setImageResource(R.mipmap.ic_mark_female);
        } else {
            imgSex.setImageResource(R.mipmap.ic_mark_male);
        }
        tvBrandName.setText(getString(R.string.label_brand, exploreTopInfoBean.getData().getMember().getBrandName()));
        dialogView.findViewById(R.id.img_dialog_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hostDialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.tv_explore_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoActivity.this, PersonInfoActivity.class);
                intent.putExtra("memberId", exploreTopInfoBean.getData().getMember().getMemberId());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                hostDialog.dismiss();
            }
        });
        TextView tvHostFocus = (TextView) dialogView.findViewById(R.id.tv_host_focus);
        if (isSelf) {
            tvHostFocus.setVisibility(View.GONE);
        } else {
            tvHostFocus.setVisibility(View.VISIBLE);
            if (tvCare.getVisibility() == View.VISIBLE) {
                tvHostFocus.setText(getString(R.string.label_plus_focus));
            } else {
                tvHostFocus.setText(getString(R.string.label_beliked));
            }
            tvHostFocus.setOnClickListener(new FocusClickListener(2, tvHostFocus, bean.getMemberId()));
        }
        hostDialog.setContentView(dialogView);
        Window win = hostDialog.getWindow();
        ViewGroup.LayoutParams params = win.getAttributes();
        params.width = Math.round(point.x * 4 / 5);
        win.setGravity(Gravity.CENTER);
        if (!isFinishing()) {
            hostDialog.show();
        }
    }
}