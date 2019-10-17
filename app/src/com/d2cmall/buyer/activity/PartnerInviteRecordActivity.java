package com.d2cmall.buyer.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.PartnerInviteRecordAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.PartnerInviteBean;
import com.d2cmall.buyer.bean.PartnerMemberBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.binder.ScrollEndBinder;
import com.d2cmall.buyer.holder.DefaultHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PartnerInvitePop;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;
import com.d2cmall.buyer.widget.SharePop;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class PartnerInviteRecordActivity extends BaseActivity {

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
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout ptr;
    @Bind(R.id.tv_join)
    TextView tvJoin;
    @Bind(R.id.img_hint)
    ImageView imgHint;
    @Bind(R.id.btn_reload)
    TextView btnReload;
    @Bind(R.id.empty_hint_layout)
    LinearLayout emptyHintLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private Bitmap headBitmap;
    private PartnerInviteRecordAdapter partnerTeamAdapter;
    private int currentPage = 1;
    private boolean hasNext =true;
    private ArrayList<PartnerInviteBean.DataBean.InvitesBean.ListBean> partnerList = new ArrayList<>();
    private VirtualLayoutManager mLayoutManager;
    private DelegateAdapter delegateAdapter;
    private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志
    private PartnerInvitePop partnerInvitePop;
    private PartnerMemberBean.DataBean.PartnerBean partner;
    public static final int IMAGE_SIZE = 32768;
    private float scale=0.8F;
    private UserBean.DataEntity.MemberEntity user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_team);
        ButterKnife.bind(this);
        init();
        initListener();
    }

    private void init() {
        nameTv.setText("邀请记录");
        mLayoutManager = new VirtualLayoutManager(PartnerInviteRecordActivity.this);
        partnerTeamAdapter = new PartnerInviteRecordAdapter(this, partnerList);
        recycleView.setLayoutManager(mLayoutManager);
        delegateAdapter = new DelegateAdapter(mLayoutManager, true);
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(0,2);
        recycleView.setRecycledViewPool(recycledViewPool);
        recycleView.setAdapter(delegateAdapter);
        delegateAdapter.addAdapter(partnerTeamAdapter);
        progressBar.setVisibility(View.VISIBLE);
        loadPartnerInfo();
        loadData();
    }


    private void loadPartnerInfo() {
        SimpleApi simpleApi = new SimpleApi();
        simpleApi.setInterPath(Constants.GET_PARTNER_CENTER_URL);
        D2CApplication.httpClient.loadingRequest(simpleApi, new BeanRequest.SuccessListener<PartnerMemberBean>() {

            @Override
            public void onResponse(PartnerMemberBean partnerBean) {
                    partner = partnerBean.getData().getPartner();

            }
        });

    }

    private void loadData() {
        SimpleApi simpleApi = new SimpleApi();
        simpleApi.setP(currentPage);
        simpleApi.setPageSize(20);
        simpleApi.setInterPath(Constants.GET_PARTNER_CUSTOMER_URL);

        D2CApplication.httpClient.loadingRequest(simpleApi, new BeanRequest.SuccessListener<PartnerInviteBean>() {
            @Override
            public void onResponse(PartnerInviteBean partnerMemberListBean) {
                ptr.refreshComplete();
                btnReload.setVisibility(View.GONE);
                imgHint.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                if (currentPage == 1) {
                    partnerList.clear();
                }
                int size = partnerMemberListBean.getData().getInvites().getList().size();
                if (size > 0) {
                    List<PartnerInviteBean.DataBean.InvitesBean.ListBean> listBeanList = partnerMemberListBean.getData().getInvites().getList();
                    partnerList.addAll(listBeanList);
                } else {
                    setEmptyView(Constants.NO_DATA);
                }
                if (partnerTeamAdapter != null) {
                    partnerTeamAdapter.notifyDataSetChanged();
                    hasNext = partnerMemberListBean.getData().getInvites().isNext();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ptr.refreshComplete();
                progressBar.setVisibility(View.GONE);
                Util.showToast(PartnerInviteRecordActivity.this, Util.checkErrorType(error));
                setEmptyView(Constants.NET_DISCONNECT);
            }
        });
    }

    private void setEmptyView(int type) {
        if (type == Constants.NO_DATA) {
            imgHint.setVisibility(View.VISIBLE);
            imgHint.setImageResource(R.mipmap.icon_empty_default);
            btnReload.setVisibility(View.VISIBLE);
            btnReload.setText("暂无数据");
            btnReload.setBackgroundColor(getResources().getColor(R.color.transparent));
        } else {
            btnReload.setText("重新加载");
            btnReload.setBackgroundResource(R.drawable.sp_line);
            btnReload.setVisibility(View.VISIBLE);
            imgHint.setVisibility(View.VISIBLE);
            imgHint.setImageResource(R.mipmap.ic_no_net);
        }

    }

    private void initListener() {
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                currentPage = 1;
                loadData();
            }
        });
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = mLayoutManager.findLastVisibleItemPosition();
                        if (last > partnerTeamAdapter.getItemCount() - 3 && hasNext) {
                            currentPage++;
                            loadData();
                        }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisPosition=mLayoutManager.findLastVisibleItemPosition();
                int itemCount=mLayoutManager.getItemCount();
                if (lastVisPosition>=itemCount-3 && !hasNext && currentPage>1){
                    if (endAdapter==null){
                        ScrollEndBinder endBinder = new ScrollEndBinder();
                        endAdapter = new BaseVirtualAdapter<>(endBinder,100);
                        delegateAdapter.addAdapter(endAdapter);
                    }
                }else {
                    if (endAdapter!=null){
                        delegateAdapter.removeAdapter(endAdapter);
                        endAdapter=null;
                    }
                }
            }
        });
    }

    @OnClick({R.id.back_iv, R.id.tv_join,R.id.btn_reload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.tv_join:
                if (Util.loginChecked(this, 999)) {
                    getInviteUrlToWeb();
//                    inviteBuyer();

                }
//                loadHeadAndShare("http://img.d2c.cn/app/a/18/05/08/mini.png",0);
//                if (partner!=null && partner.getLevel() == 0 ) {
//                    showInviteBuyerPop();
//                } else {
//
//                }
                break;
            case R.id.btn_reload:
                imgHint.setVisibility(View.GONE);
                btnReload.setVisibility(View.GONE);
                refresh();
                break;
        }
    }

    private void showInviteBuyerPop() { //邀请买手
        if(partnerInvitePop ==null){
            partnerInvitePop = new PartnerInvitePop(this);
        }
        partnerInvitePop.show(getWindow().getDecorView());
    }


    private void refresh() {
        currentPage=1;
        loadData();
    }
    private void getInviteUrlToWeb() {
        if (user == null) {
            user = Session.getInstance().getUserFromFile(this);
        }
        String avatar;
        String name = Util.toURLEncode(user.getNickname() == null ? "匿名_" + user.getMemberId() : user.getNickname());
        if (user != null && user.getHead() != null) {
            avatar = Util.toURLEncode(getD2cPicUrl(user.getHead()));
        } else {
            avatar = Util.toURLEncode("http://d2c-app.b0.upaiyun.com/img/logo/android_default_avatar.png");
        }
        toWebActivity("/partner/joinbuyer?parent_id=" + user.getPartnerId() + "&name=" + name + "&avatar=" + avatar,true);
    }
    //返回D2C图片路径
    public  String getD2cPicUrl(String url) {
        if (Util.isEmpty(url)) {
            return null;
        } else if (url.startsWith("http://") || url.startsWith("https://")) {
            return url;
        }  else {
            return "https://img.d2c.cn" + url;
        }
    }
    private void toWebActivity(String weburl, boolean isInvoke) {
        Intent intent = new Intent(this, WebActivity.class);
        String url = null;
        if (isInvoke) {
            intent.putExtra("type", 0);
            url = weburl;
        } else {
            url = Constants.SHARE_URL + weburl;
            intent.putExtra("type", 1);
        }
        intent.putExtra("url", url);
        intent.putExtra("isShareGone", true);
        startActivity(intent);
    }


    private void inviteBuyer() {

        SharePop sharePop1 = new SharePop(this);
        sharePop1.show(((Activity) this).getWindow().getDecorView());
        sharePop1.setTitle("邀你成为D2C时尚买手，分享好物，轻松赚钱");
        sharePop1.setShareMini(true);
        sharePop1.setDescription("邀你成为D2C时尚买手，分享好物，轻松赚钱");
        sharePop1.setImage("http://img.d2c.cn/app/a/18/05/08/mini.png", true);
        if (user == null) {
            user = Session.getInstance().getUserFromFile(this);
        }
        String avatar;
        String name = Util.toURLEncode(user.getNickname() == null ? "匿名_" + user.getMemberId() : user.getNickname());
        if (user != null && user.getHead() != null) {
            avatar = Util.toURLEncode(Constants.IMG_HOST + user.getHead());
        } else {
            avatar = Util.toURLEncode("http://d2c-app.b0.upaiyun.com/img/logo/android_default_avatar.png");
        }
        //设置小程序页面
        sharePop1.setMiniProjectPath("/pages/intro/joinRecommender?parent_id=" + user.getPartnerId() + "&name=" + name + "&avatar=" + avatar);
        //设置小程序低版本兼容网页
        sharePop1.setMiniWebUrl("/partner/joinbuyer?parent_id=" + user.getPartnerId() + "&name=" + name + "&avatar=" + avatar);
        //设置分享链接(短链)
        sharePop1.setWebUrl("/partner/joinbuyer?parent_id=" + user.getPartnerId() + "&name=" + name + "&avatar=" + avatar);
        //加载小程序分享的图片
        Glide.with(this).load(Util.getD2cPicUrl("http://img.d2c.cn/app/a/18/05/08/mini.png", ScreenUtil.getDisplayWidth(), ScreenUtil.getDisplayHeight())).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                byte[] bitmapData = BitmapUtils.getBitmapData(BitmapUtils.getScaleBitmap(resource, scale, scale));
                while (bitmapData.length > IMAGE_SIZE) {
                    scale -= 0.1;
                    Bitmap scaleBitmap = BitmapUtils.getScaleBitmap(resource, scale, scale);
                    bitmapData = BitmapUtils.getBitmapData(scaleBitmap);
                    scaleBitmap.recycle();
                }
                sharePop1.setMiniPicData(bitmapData);
                sharePop1.show(PartnerInviteRecordActivity.this.getWindow().getDecorView());
            }
        });
    }
}
