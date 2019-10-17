package com.d2cmall.buyer.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.PersonInfoAdapter;
import com.d2cmall.buyer.adapter.PersonShopAdapter;
import com.d2cmall.buyer.api.FollowApi;
import com.d2cmall.buyer.api.MemberIdApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.api.UpdateAccountApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.ClickFollowBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.LiveListBean;
import com.d2cmall.buyer.bean.PersonContentBean;
import com.d2cmall.buyer.bean.PersonInfoBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.fragment.FashionSubFragment;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.InitializeService;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.RoundedImageView;
import com.d2cmall.buyer.widget.SharePop;
import com.d2cmall.buyer.widget.ShowPopImageView;
import com.d2cmall.buyer.widget.SocialPop;
import com.d2cmall.buyer.widget.VideoPop;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayerManager;
import com.d2cmall.buyer.widget.ninegrid.ImageInfo;
import com.upyun.library.common.Params;
import com.upyun.library.common.UploadManager;
import com.upyun.library.listener.SignatureListener;
import com.upyun.library.listener.UpCompleteListener;
import com.upyun.library.utils.UpYunUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by rookie on 2017/8/10.
 * 个人详情页面
 */

public class PersonInfoActivity extends BaseActivity {

    @Bind(R.id.iv_person_back)
    ImageView ivPersonBack;
    @Bind(R.id.tv_big_name)
    TextView tvBigName;
    //开启消息推送行为节点
    @Bind(R.id.iv_big_focus)
    ShowPopImageView ivBigFocus;
    @Bind(R.id.tv_big_show_num)
    TextView tvBigShowNum;
    @Bind(R.id.ll_big_show)
    LinearLayout llBigShow;
    @Bind(R.id.tv_big_fans_num)
    TextView tvBigFansNum;
    @Bind(R.id.ll_big_fans)
    LinearLayout llBigFans;
    @Bind(R.id.tv_big_focus_num)
    TextView tvBigFocusNum;
    @Bind(R.id.ll_big_focus)
    LinearLayout llBigFocus;
    @Bind(R.id.ll_person_info)
    LinearLayout llPersonInfo;
    @Bind(R.id.rv_many_shop)
    RecyclerView rvManyShop;
    @Bind(R.id.iv_one_shop)
    ImageView ivOneShop;
    @Bind(R.id.tv_one_shop_product_num)
    TextView tvOneShopProductNum;
    @Bind(R.id.ll_just_one_shop)
    LinearLayout llJustOneShop;
    @Bind(R.id.ll_own_shop)
    LinearLayout llOwnShop;
    @Bind(R.id.card_view)
    CardView cardView;
    @Bind(R.id.iv_big_head_pic)
    RoundedImageView ivBigHeadPic;
    @Bind(R.id.iv_person_head)
    RoundedImageView ivPersonHead;
    @Bind(R.id.tv_person_name)
    TextView tvPersonName;
    @Bind(R.id.show_num)
    TextView showNum;
    @Bind(R.id.fans_num)
    TextView fansNum;
    @Bind(R.id.focus_num)
    TextView focusNum;
    @Bind(R.id.ll_person_title)
    LinearLayout llPersonTitle;
    @Bind(R.id.app_bar)
    AppBarLayout appBar;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.person_recycler)
    RecyclerView personRecycler;
    @Bind(R.id.start_title)
    View startTitle;
    @Bind(R.id.iv_start_back)
    ImageView ivStartBack;
    @Bind(R.id.iv_start_share)
    ImageView ivStartShare;
    @Bind(R.id.iv_camera)
    ImageView ivCamera;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.iv_release)
    ImageView ivRelease;
    @Bind(R.id.iv_delete)
    ImageView ivDelete;
    @Bind(R.id.ll_release)
    LinearLayout llRelease;
    @Bind(R.id.upload_progress)
    ProgressBar uploadProgress;
    @Bind(R.id.tv_progress)
    TextView tvProgress;
    @Bind(R.id.progress_ll)
    LinearLayout progressLl;
    @Bind(R.id.ll_progress)
    LinearLayout llProgress;
    @Bind(R.id.iv_identify)
    ImageView ivIdentify;
    @Bind(R.id.rl_head_pic)
    RelativeLayout rlHeadPic;
    @Bind(R.id.error_image)
    ImageView errorImage;
    @Bind(R.id.view_state)
    View view_state;
    public PersonInfoBean.DataBean.MemberBean memberEntity;
    private Dialog loadingDialog;
    private long memberId;
    private int follow;
    private List<LiveListBean.DataBean.LivesBean.ListBean> liveBeanList;
    private List<PersonContentBean.DataBean.MemberSharesBean.ListBean> showBeanList;
    private List<Object> listEntities;
    private UserBean.DataEntity.MemberEntity user;
    private int screenWidth;
    private int shareTotalCount;
    private PersonShopAdapter personShopAdapter;
    private int currentPage = 1;
    private PersonInfoAdapter personInfoAdapter;
    private SocialPop socialPop;
    Animation mShowAction, mHiddenAction;
    //private boolean isAnimationFinish = true;
    //private boolean isToolHide;

    private int state;
    private int EXPANDED = 1995;
    private int COLLAPSED = 11;
    private int INTERNEDIATE = 23;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        ButterKnife.bind(this);
        listEntities = new ArrayList<>();
        liveBeanList = new ArrayList<>();
        showBeanList = new ArrayList<>();
        loadingDialog = DialogUtil.createLoadingDialog(this);
        memberId = getIntent().getLongExtra("memberId", -1);
        user = Session.getInstance().getUserFromFile(this);
        Point point = Util.getDeviceSize(this);
        screenWidth = point.x;
        loadingDialog.show();
        doBusiness();
        requestExploreTopInfoTask();
        requestExploreInfoTask(true);
        mShowAction = new AlphaAnimation(0f, 1f);
        mShowAction.setDuration(350);
//        mShowAction.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//                isAnimationFinish = false;
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                isAnimationFinish = true;
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//                isAnimationFinish = false;
//            }
//        });
        mHiddenAction = new AlphaAnimation(1f, 0f);
        mHiddenAction.setDuration(350);
//        mHiddenAction.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//                isAnimationFinish = false;
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                isAnimationFinish = true;
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//                isAnimationFinish = false;
//            }
//        });

    }

//    private void showView() {
//        if (isAnimationFinish) {
//            ivCamera.startAnimation(mShowAction);
//        }
//        ivCamera.setVisibility(View.VISIBLE);
//        isToolHide = false;
//    }
//
//    private void hideView() {
//        if (isAnimationFinish) {
//            ivCamera.startAnimation(mHiddenAction);
//        }
//        ivCamera.setVisibility(View.GONE);
//        isToolHide = true;
//    }

    private void requestExploreInfoTask(final boolean isFirst) {
        MemberIdApi api = new MemberIdApi();
        if (!(user != null && user.getId() == memberId)) {
            api.setMemberId(memberId);
        }
        api.setInterPath(Constants.EXPLORE_INFO_URL);
        api.setP(currentPage);
        api.setPageSize(30);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<PersonContentBean>() {
            @Override
            public void onResponse(PersonContentBean exploreInfoBean) {
                if (currentPage == 1) {
                    listEntities.clear();
                }
                if (isFirst) {
                    if (exploreInfoBean.getData().getLives() != null&&exploreInfoBean.getData().getLives().getList().size() != 0 ) {
                        liveBeanList.clear();
                        if (exploreInfoBean.getData().getLives().getList().size() > 3) {
                            liveBeanList.add(exploreInfoBean.getData().getLives().getList().get(0));
                            liveBeanList.add(exploreInfoBean.getData().getLives().getList().get(1));
                            liveBeanList.add(exploreInfoBean.getData().getLives().getList().get(2));
                        } else {
                            liveBeanList.addAll(exploreInfoBean.getData().getLives().getList());
                        }
                        listEntities.add(1);
                    }
                }
                listEntities.addAll(exploreInfoBean.getData().getMemberShares().getList());
                hasNext = exploreInfoBean.getData().getMemberShares().isNext();
                if (listEntities.size() == 0) {
                    errorImage.setVisibility(View.VISIBLE);
                    personRecycler.setVisibility(View.GONE);
                    errorImage.setImageResource(R.mipmap.ic_empty_move);
                } else if (isFirst) {
                    personInfoAdapter = new PersonInfoAdapter(PersonInfoActivity.this, listEntities, liveBeanList);
                    personRecycler.setAdapter(personInfoAdapter);
                } else {
                    if (personInfoAdapter != null) {
                        personInfoAdapter.notifyDataSetChanged();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (isFirst) {
                    errorImage.setVisibility(View.VISIBLE);
                    errorImage.setImageResource(R.mipmap.ic_no_net);
                    personRecycler.setVisibility(View.GONE);
                } else {
                    Util.showToast(PersonInfoActivity.this, Util.checkErrorType(error));
                }
            }
        });
    }

    public void showPop() {
        PackageManager pkgManager = getPackageManager();
        boolean cameraPermission =
                pkgManager.checkPermission(Manifest.permission.CAMERA, getPackageName()) == PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT >= 23 && !cameraPermission) {
            requestPermission();
        } else {
            choosePic();
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                Constants.RequestCode.REQUEST_PERMISSION);
    }

    private void choosePic() {
        Matisse.from(PersonInfoActivity.this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                .theme(R.style.Matisse_Dracula)
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.d2cmall.buyer.fileprovider"))
                .maxSelectable(1)
                .gridExpectedSize(ScreenUtil.dip2px(120))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(456);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Constants.RequestCode.REQUEST_PERMISSION) {
            if ((grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                choosePic();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void uploadFile(String filePath) {
        UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(this);
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        final Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(Params.BUCKET, Constants.UPYUN_SPACE);
        paramsMap.put(Params.SAVE_KEY, Util.getUPYunSavePath(user.getId(), Constants.TYPE_AVATAR));
        paramsMap.put(Params.RETURN_URL, "httpbin.org/post");
        UpCompleteListener completeListener = new UpCompleteListener() {
            @Override
            public void onComplete(boolean isSuccess, String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String backgroud = jsonObject.optString("url");
                    requestUpdateAccountTask(backgroud);
                } catch (JSONException e) {
                }
            }
        };
        SignatureListener signatureListener = new SignatureListener() {
            @Override
            public String getSignature(String raw) {
                return UpYunUtils.md5(raw + Constants.UPYUN_KEY);
            }
        };
        UploadManager.getInstance().blockUpload(file, paramsMap, signatureListener, completeListener, null);
    }

    private void requestUpdateAccountTask(final String background) {
        UpdateAccountApi api = new UpdateAccountApi();
        api.setBackground(background);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(PersonInfoActivity.this);
                if (!Util.isEmpty(background)) {
                    user.setBackgroud(background);
                }
                Session.getInstance().saveUserToFile(PersonInfoActivity.this, user);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(PersonInfoActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void setFollowsData() {
        if (memberEntity == null) {
            return;
        }
        if (follow == 0) {
            ivBigFocus.setImageResource(R.mipmap.button_fashion_care);
        } else if (follow == 1) {
            ivBigFocus.setImageResource(R.mipmap.button_fashion_cared);
        } else {
            ivBigFocus.setImageResource(R.mipmap.button_fashion_mutualcare);
        }
        ivBigFocus.setOnClickListener(new FocusClickListener());
    }

    private void requestExploreTopInfoTask() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.NEW_EXPLORE_INFO_URL, memberId));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<PersonInfoBean>() {
            @Override
            public void onResponse(final PersonInfoBean exploreTopInfoBean) {
                loadingDialog.dismiss();
                follow = exploreTopInfoBean.getData().getFollow();
                memberEntity = exploreTopInfoBean.getData().getMember();
                if (user != null && user.getId() == memberId) {
                    ivCamera.setVisibility(View.VISIBLE);
                    ivBigFocus.setVisibility(View.GONE);
                } else {
                    ivCamera.setVisibility(View.GONE);
                    ivBigFocus.setVisibility(View.VISIBLE);
                    setFollowsData();
                }
                if (memberEntity != null) {
                    UniversalImageLoader.displayImage(PersonInfoActivity.this, Util.getD2cPicUrl(Util.isEmpty(memberEntity.getHead()) ? memberEntity.getThirdPic() : memberEntity.getHead()),
                            ivBigHeadPic, R.mipmap.ic_default_avatar);
                    UniversalImageLoader.displayImage(PersonInfoActivity.this, Util.getD2cPicUrl(Util.isEmpty(memberEntity.getHead()) ? memberEntity.getThirdPic() : memberEntity.getHead()),
                            ivPersonHead, R.mipmap.ic_default_avatar);
                    UniversalImageLoader.displayImage(PersonInfoActivity.this, Util.getD2cPicUrl(memberEntity.getBackgroud(), screenWidth),
                            ivPersonBack, R.mipmap.back);
                    tvBigName.setText(memberEntity.getNickname());
                    tvPersonName.setText(memberEntity.getNickname());
                    shareTotalCount = exploreTopInfoBean.getData().getSharesTotalCount();
                    tvBigShowNum.setText(String.valueOf(shareTotalCount));
                    showNum.setText("动态 " + String.valueOf(shareTotalCount));
                    tvBigFansNum.setText(String.valueOf(exploreTopInfoBean.getData().getFansTotalCount()));
                    fansNum.setText("粉丝 " + String.valueOf(exploreTopInfoBean.getData().getFansTotalCount()));
                    tvBigFocusNum.setText(String.valueOf(exploreTopInfoBean.getData().getFollowsTotalCount()));
                    focusNum.setText("关注 " + String.valueOf(exploreTopInfoBean.getData().getFollowsTotalCount()));
                    if (memberEntity.getType() == 2 || (memberEntity.getDesignerId() > 0 && memberEntity.getType() != 3)) {//设计师账号
                        //设计师账户
                        int brandNum = exploreTopInfoBean.getData().getBrands().size();
                        ivIdentify.setVisibility(View.VISIBLE);
                        if (brandNum > 0) {
                            llOwnShop.setVisibility(View.VISIBLE);
                            if (brandNum == 1) {
                                final PersonInfoBean.DataBean.BrandsBean brand = exploreTopInfoBean.getData().getBrands().get(0);
                                llJustOneShop.setVisibility(View.VISIBLE);
                                rvManyShop.setVisibility(View.GONE);
                                UniversalImageLoader.displayImage(PersonInfoActivity.this, brand.getHeadPic(), ivOneShop, R.mipmap.ic_logo_empty1);
                                ivOneShop.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(PersonInfoActivity.this, BrandDetailActivity.class);
                                        intent.putExtra("id", brand.getId());
                                        startActivity(intent);
                                    }
                                });
                                tvOneShopProductNum.setText("共" + brand.getSalesProductCount() + "件商品");
                            } else {
                                llJustOneShop.setVisibility(View.GONE);
                                rvManyShop.setVisibility(View.VISIBLE);
                                rvManyShop.setLayoutManager(new LinearLayoutManager(PersonInfoActivity.this, LinearLayoutManager.HORIZONTAL, false));
                                personShopAdapter = new PersonShopAdapter(PersonInfoActivity.this, exploreTopInfoBean.getData().getBrands());
                                rvManyShop.setAdapter(personShopAdapter);
                            }
                        } else {
                            llOwnShop.setVisibility(View.GONE);
                        }
                    } else if (memberEntity.getType() == 3) {//D2C官方账号
                        llOwnShop.setVisibility(View.GONE);
                        ivIdentify.setVisibility(View.VISIBLE);
                        ivIdentify.setImageResource(R.mipmap.icon_all_d2c);
                    } else if (memberEntity.getType() == 5) {//达人账号
                        llOwnShop.setVisibility(View.GONE);
                        ivIdentify.setVisibility(View.VISIBLE);
                        ivIdentify.setImageResource(R.mipmap.icon_all_fashion);
                    } else {//普通会员
                        ivIdentify.setVisibility(View.GONE);
                        llOwnShop.setVisibility(View.GONE);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                Util.showToast(PersonInfoActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private class FocusClickListener implements View.OnClickListener {


        public FocusClickListener() {
            super();
        }

        @Override
        public void onClick(View v) {
            if (memberId == -1) {
                return;
            }
            if (Util.loginChecked(PersonInfoActivity.this, Constants.Login.EXPLORE_INFO_LOGIN)) {
                focusComplete();
            }
        }

        private void focusComplete() {
            if (Util.loginChecked(PersonInfoActivity.this, 123)) {
                if (follow > 0) {
                    follow = 0;
                    FollowApi api = new FollowApi();
                    api.setToMemberId(memberId);
                    api.setInterPath(Constants.DELETE_MY_FOLLOWS_URL);
                    D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                        @Override
                        public void onResponse(BaseBean baseBean) {
                            ivBigFocus.setImageResource(R.mipmap.button_fashion_care);
                            EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.LIKETHIS_LIST_CHANGE));
                            Util.showToast(PersonInfoActivity.this, "取消关注成功");
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Util.showToast(PersonInfoActivity.this, Util.checkErrorType(error));
                        }
                    });
                } else {
                    FollowApi api = new FollowApi();
                    api.setToMemberId(memberId);
                    api.setInterPath(Constants.INSERT_MY_FOLLOWS_URL);
                    D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ClickFollowBean>() {
                        @Override
                        public void onResponse(ClickFollowBean clickFollowBean) {
                            EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.LIKETHIS_LIST_CHANGE));
                            Util.showToast(PersonInfoActivity.this, R.string.msg_focus_ok);
                            follow = clickFollowBean.getData().getFollow();
                            if (follow == 0) {
                                ivBigFocus.setImageResource(R.mipmap.button_fashion_care);
                            } else if (follow == 1) {
                                ivBigFocus.setImageResource(R.mipmap.button_fashion_cared);
                            } else if (follow == 2) {
                                ivBigFocus.setImageResource(R.mipmap.button_fashion_mutualcare);
                            }
                            ivBigFocus.showMsgPop( PersonInfoActivity.this,getString(R.string.label_pop_focus_people));
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Util.showToast(PersonInfoActivity.this, Util.checkErrorType(error));
                        }
                    });
                }
            }
        }
    }

    private boolean hasNext;

    public void doBusiness() {
        final VirtualLayoutManager linearLayoutManager = new VirtualLayoutManager(this);
        personRecycler.setLayoutManager(linearLayoutManager);
        if (user != null && user.getId() == memberId) {
            ivCamera.setVisibility(View.VISIBLE);
            personRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                    if (dy < 0) {//上滑
//                        if (isToolHide)
//                            showView();
//                    } else {//下滑
//                        if (!isToolHide)
//                            hideView();
//                    }
                    super.onScrolled(recyclerView, dx, dy);
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    switch (newState) {
                        case RecyclerView.SCROLL_STATE_IDLE:
                            int last = linearLayoutManager.findLastVisibleItemPosition();
                            if (last > personInfoAdapter.getItemCount() - 3 && hasNext) {
                                currentPage++;
                                requestExploreInfoTask(false);
                            }
                    }
                    super.onScrollStateChanged(recyclerView, newState);
                }
            });
        }
        mShowAction = new AlphaAnimation(0f, 1f);
        mShowAction.setDuration(350);
        mHiddenAction = new AlphaAnimation(1f, 0f);
        mHiddenAction.setDuration(350);
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset == 0) {
                    if (state != EXPANDED) {
                        state = EXPANDED;//修改状态标记为展开
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != COLLAPSED) {
                        llPersonTitle.startAnimation(mShowAction);
                        llPersonTitle.setVisibility(View.VISIBLE);//隐藏标题栏
                        startTitle.startAnimation(mHiddenAction);
                        startTitle.setVisibility(View.GONE);
                        cardView.setVisibility(View.INVISIBLE);
                        ivPersonBack.setVisibility(View.INVISIBLE);
                        state = COLLAPSED;//修改状态标记为折叠
                    }
                } else {
                    if (state != INTERNEDIATE) {
                        if (state == COLLAPSED) {
                            llPersonTitle.startAnimation(mHiddenAction);
                            llPersonTitle.setVisibility(View.GONE);//由折叠变为中间状态时隐藏标题栏
                            startTitle.startAnimation(mShowAction);
                            startTitle.setVisibility(View.VISIBLE);
                            cardView.setVisibility(View.VISIBLE);
                            ivPersonBack.setVisibility(View.VISIBLE);
                        }
                        state = INTERNEDIATE;//修改状态标记为中间
                    }
                }
            }
        });

    }

    private String url;
    private String content;
    private long duration;

    @Subscribe
    public void onEvent(GlobalTypeBean bean) {
        if (bean.getType() == Constants.GlobalType.UPLOAD_FAIL) {//失败
            url = (String) bean.getValue("url");
            content = (String) bean.getValue("content");
            duration = (long) bean.getValue("duration");
            progressLl.setVisibility(View.GONE);
            llRelease.setVisibility(View.VISIBLE);
        } else if (bean.getType() == Constants.GlobalType.UPLOAD_PROGRESS) {
            int progress = (int) bean.getValue("progress");
            String url = (String) bean.getValue("url");
            llRelease.setVisibility(View.GONE);
            llProgress.setVisibility(View.VISIBLE);
            progressLl.setVisibility(View.VISIBLE);
            uploadProgress.setProgress(progress);
            if (bitmap == null) {
                bitmap = ThumbnailUtils.createVideoThumbnail(url, MediaStore.Video.Thumbnails.MINI_KIND);
                int bitmapWidth = bitmap.getWidth();
                int bitmapHeight = bitmap.getHeight();
                int scanWidth = ScreenUtil.dip2px(20);
                int scale = Math.min(bitmapWidth / scanWidth, bitmapHeight / scanWidth);
                Bitmap scalBitmap = BitmapUtils.getScaleBitmap(bitmap, scale, scale);
                bitmap.recycle();
                if (scalBitmap != null) {
                    image.setVisibility(View.VISIBLE);
                    image.setBackground(new BitmapDrawable(scalBitmap));
                    image.setTag(url);
                }
            }
            tvProgress.setText(progress + "%");
            if (progress == 100) {
                llProgress.setVisibility(View.GONE);
                currentPage = 1;
                requestExploreInfoTask(true);
            }
        } else if (bean.getType() == Constants.GlobalType.PUBLISHBACK) {
            currentPage = 1;
            requestExploreInfoTask(true);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 456 && resultCode == RESULT_OK) {
            List<String> path = Matisse.obtainPathResult(data);
            ivPersonBack.setImageBitmap(BitmapUtils.getBackBitmap(path.get(0)));
            uploadFile(path.get(0));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        // 很重要，在Activity和Fragment的onStop方法中一定要调用，释放的播放器。
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
        super.onDestroy();
    }

    // 按下Home键暂停播放，回到界面继续播放。
    @Override
    protected void onStop() {
        NiceVideoPlayerManager.instance().pauseNiceVideoPlayer();
        super.onStop();
    }

    @Override
    protected void onRestart() {
        NiceVideoPlayerManager.instance().restartNiceVideoPlayer();
        super.onRestart();
    }

    // 按返回键
    // 当前是全屏或小窗口，需要先退出全屏或小窗口。
    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) {
            return;
        }
        super.onBackPressed();
    }


    @OnClick({R.id.iv_big_head_pic, R.id.iv_start_back, R.id.iv_start_share, R.id.iv_camera, R.id.iv_release, R.id.iv_delete, R.id.iv_person_back, R.id.iv_big_focus, R.id.ll_big_show, R.id.ll_big_fans, R.id.ll_big_focus, R.id.iv_one_shop, R.id.tv_one_shop_product_num, R.id.show_num, R.id.fans_num, R.id.focus_num})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_big_head_pic:
                ArrayList<ImageInfo> imageInfos1 = new ArrayList<>();
                ImageInfo info1 = new ImageInfo();
                info1.setSingleUrl(Util.getD2cPicUrl(memberEntity.getHead()));//单张图
                info1.setFourUrl(Util.getD2cPicUrl(memberEntity.getHead()));//四张图
                info1.setThumbnailUrl(Util.getD2cPicUrl(memberEntity.getHead()));//多张缩略图
                info1.setBigImageUrl(Util.getD2cPicUrl(memberEntity.getHead()));//大图
                imageInfos1.add(info1);
                intent = new Intent(this, ImagePreviewActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfos1);
                bundle1.putInt(ImagePreviewActivity.CURRENT_ITEM, 0);
                intent.putExtras(bundle1);
                startActivity(intent);
                break;
            case R.id.iv_start_back:
                finish();
                break;
            case R.id.iv_start_share:
                if (memberEntity == null) {
                    return;
                }
                SharePop sharePop = new SharePop(this, false);
                if (memberEntity != null && memberEntity.getNickname() != null) {
                    sharePop.setTitle(String.format(getString(R.string.label_member_share_person), memberEntity.getNickname()));
                    sharePop.setWebUrl(Constants.SHARE_URL + "/membershare/mine?memberId=" + memberEntity.getMemberId());
                }
                if (memberEntity.getHead() != null) {
                    sharePop.setImage(Util.getD2cPicUrl(memberEntity.getHead(), 100, 100), false);
                    sharePop.setImage(Util.getD2cPicUrl(memberEntity.getHead(), 360, 500), true);
                }
                sharePop.show(PersonInfoActivity.this.getWindow().getDecorView());
                break;
            case R.id.iv_camera:
                if (user != null) {
                    if (user.getHasNickName()) {
                        if (user.getDesignerId() > 0 || user.getType() == 3) {
                            socialPop = new SocialPop(PersonInfoActivity.this, true);
                            socialPop.show(ivCamera);
                        } else {
                            if (FashionSubFragment.isUpload) {
                                VideoPop videoPop = new VideoPop(PersonInfoActivity.this);
                                videoPop.show(getWindow().getDecorView());
                            }else{
                                intent = new Intent(PersonInfoActivity.this, VideoRecordActivity.class);
                                intent.putExtra("channel","social");
                                startActivity(intent);
                            }
                        }
                    } else {
                        Intent intent1 = new Intent(PersonInfoActivity.this, CompleteInfoActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.slide_in_up, R.anim.activity_anim_default);
                    }
                } else {
                    Intent intent1 = new Intent(PersonInfoActivity.this, LoginActivity.class);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.activity_anim_default);
                }
                break;
            case R.id.iv_release:
                intent = new Intent(this, InitializeService.class);
                intent.putExtra("url", url);
                intent.putExtra("content", content);
                intent.putExtra("duration", duration);
                intent.setAction("upload");
                startService(intent);
                break;
            case R.id.iv_delete:
                llProgress.setVisibility(View.GONE);
                break;
            case R.id.iv_person_back:
                if (user == null) {
                    ArrayList<ImageInfo> imageInfos = new ArrayList<>();
                    ImageInfo info = new ImageInfo();
                    info.setSingleUrl(Util.getD2cPicUrl(memberEntity.getBackgroud()));//单张图
                    info.setFourUrl(Util.getD2cPicUrl(memberEntity.getBackgroud()));//四张图
                    info.setThumbnailUrl(Util.getD2cPicUrl(memberEntity.getBackgroud()));//多张缩略图
                    info.setBigImageUrl(Util.getD2cPicUrl(memberEntity.getBackgroud()));//大图
                    String pic = Util.getD2cPicUrl(memberEntity.getBackgroud());
                    imageInfos.add(info);
                    intent = new Intent(this, ImagePreviewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfos);
                    bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, 0);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    if (user.getMemberId() == memberId) {
                        showPop();
                    } else {
                        ArrayList<ImageInfo> imageInfos = new ArrayList<>();
                        ImageInfo info = new ImageInfo();
                        info.setSingleUrl(Util.getD2cPicUrl(memberEntity.getBackgroud()));//单张图
                        info.setFourUrl(Util.getD2cPicUrl(memberEntity.getBackgroud()));//四张图
                        info.setThumbnailUrl(Util.getD2cPicUrl(memberEntity.getBackgroud()));//多张缩略图
                        info.setBigImageUrl(Util.getD2cPicUrl(memberEntity.getBackgroud()));//大图
                        String pic = Util.getD2cPicUrl(memberEntity.getBackgroud());
                        imageInfos.add(info);
                        intent = new Intent(this, ImagePreviewActivity.class);
                        Bundle bundle = new Bundle();

                        bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfos);
                        bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, 0);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.iv_big_focus:

                break;
            case R.id.ll_big_show:
                break;
            case R.id.ll_big_fans:
                //跳转粉丝界面
                intent = new Intent(PersonInfoActivity.this, FansActivity.class);
                intent.putExtra("memberId", memberId);
                startActivity(intent);
                break;
            case R.id.ll_big_focus:
                long id = memberId;
                intent = new Intent(this, MyFollowsActivity.class);
                intent.putExtra("memberId", id);
                startActivity(intent);
                break;
            case R.id.iv_one_shop:
                break;
            case R.id.tv_one_shop_product_num:
                break;
            case R.id.show_num:
                personRecycler.scrollToPosition(0);
                appBar.setExpanded(true, true);
                break;
            case R.id.fans_num:
                intent = new Intent(PersonInfoActivity.this, FansActivity.class);
                intent.putExtra("memberId", memberId);
                startActivity(intent);
                break;
            case R.id.focus_num:
                long id2 = memberId;
                intent = new Intent(this, MyFollowsActivity.class);
                intent.putExtra("memberId", id2);
                startActivity(intent);
                break;
        }
    }
}
