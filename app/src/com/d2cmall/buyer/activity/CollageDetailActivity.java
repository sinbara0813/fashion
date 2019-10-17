package com.d2cmall.buyer.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.CheckCollageTermApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.CheckCollageTermErrorBean;
import com.d2cmall.buyer.bean.CollageDetailBean;
import com.d2cmall.buyer.bean.GroupListBean;
import com.d2cmall.buyer.bean.Poster;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.http.HttpError;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.CollageMemberListPop;
import com.d2cmall.buyer.widget.FlashNoticePop;
import com.d2cmall.buyer.widget.RoundedImageView;
import com.d2cmall.buyer.widget.SharePop;
import com.d2cmall.buyer.widget.ShoppingPop;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

//拼团详情界面
public class CollageDetailActivity extends BaseActivity {

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
    @Bind(R.id.iv_head)
    RoundedImageView ivHead;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_collage_num)
    TextView tvCollageNum;
    @Bind(R.id.tv_time_down)
    TextView tvTimeDown;
    @Bind(R.id.iv_product)
    ImageView ivProduct;
    @Bind(R.id.tv_product_name)
    TextView tvProductName;
    @Bind(R.id.tv_sub_title)
    TextView tvSubTitle;
    @Bind(R.id.tv_old_price)
    TextView tvOldPrice;
    @Bind(R.id.tv_collage_price)
    TextView tvCollagePrice;
    @Bind(R.id.tv_team_status)
    TextView tvTeamStatus;
    @Bind(R.id.ll_mermber_container)
    LinearLayout llMermberContainer;
    @Bind(R.id.ll_collage_rule)
    LinearLayout llCollageRule;
    @Bind(R.id.tv_invite)
    TextView tvInvite;
    @Bind(R.id.line_layout)
    View lineLayout;
    @Bind(R.id.tv_more_team)
    TextView tvMoreTeam;
    @Bind(R.id.ll_collages_container)
    LinearLayout llCollagesContainer;
    @Bind(R.id.scroll_view)
    ScrollView scrollView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.img_hint)
    ImageView imgHint;
    @Bind(R.id.btn_reload)
    TextView btnReload;
    @Bind(R.id.empty_hint_layout)
    LinearLayout emptyHintLayout;
    @Bind(R.id.tv_more_team2)
    TextView tvMoreTeam2;
    @Bind(R.id.tv_tip)
    TextView tvTip;
    @Bind(R.id.tv_head_more)
    TextView tvHeadMore;
    @Bind(R.id.tv_refund_tip)
    TextView tvRefundTip;


    private CollageMemberListPop collageMemberListPop;
    private Handler mHandler;
    private CollageDetailBean mCollageDetailBean;
    private FlashNoticePop noticePop;
    private int collageId;
    public static final int IMAGE_SIZE = 32768;
    private float scale = 1.0F;
    private ShoppingPop shoppingPop;
    private String subject;
    private String payType;
    private String payStyle;
    private String id;
    private boolean isOpenCashierActivity;//是否跳转收银台
    private boolean isOver;
    private UserBean.DataEntity.MemberEntity user;
    private double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage_detail);
        ButterKnife.bind(this);
        nameTv.setText("拼团详情");
        collageId = getIntent().getIntExtra("collageId", 0);
        //拼团订单的数据
        subject = getIntent().getStringExtra("subject");
        payType = getIntent().getStringExtra("payType");
        payStyle = getIntent().getStringExtra("payStyle");
        price = getIntent().getDoubleExtra("price", 0);
        //待支付的订单sn
        id = getIntent().getStringExtra("id");
        //订单是否超时关闭
        isOver = getIntent().getBooleanExtra("isOver", false);
        user = Session.getInstance().getUserFromFile(this);
//        if(collageId==0){
//            setEmptyView(Constants.NO_DATA);
//            return;
//        }
        init();
    }

    private void init() {
        titleRight.setBackgroundResource(R.mipmap.icon_share);
        titleRight.setEnabled(false);
        loadCollageDetial();
    }

    @Override
    protected void onResume() {
        if (isOpenCashierActivity) {//待支付订单跳收银台回来刷界面
            isOpenCashierActivity = false;
            loadCollageDetial();
        }
        super.onResume();
    }

    //加载详情
    private void loadCollageDetial() {
        progressBar.setVisibility(View.VISIBLE);
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.GROUP_DETAIL_URL, collageId));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CollageDetailBean>() {

            @Override
            public void onResponse(CollageDetailBean collageDetailBean) {
                if (isFinishing() || progressBar == null) {
                    return;
                }
                scrollView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                if (collageDetailBean == null || collageDetailBean.getData()==null) {
                    setEmptyView(Constants.NO_DATA);
                    return;
                }
                mCollageDetailBean = collageDetailBean;
                titleRight.setEnabled(true);
                loadMoreCollages();
                UniversalImageLoader.displayImage(CollageDetailActivity.this, collageDetailBean.getData().getProduct().getImg(), ivProduct, R.mipmap.ic_logo_empty5);
                tvProductName.setText(collageDetailBean.getData().getProduct().getName());
                tvSubTitle.setText(collageDetailBean.getData().getProduct().getSubTitle());
                tvOldPrice.setText("原价: ¥" + Util.getNumberFormat(collageDetailBean.getData().getProduct().getMinPrice()));
                tvCollagePrice.setText(collageDetailBean.getData().getCollageGroup().getMemberCount() + "人团: ¥" + Util.getNumberFormat(collageDetailBean.getData().getProduct().getCollagePrice()));
                UniversalImageLoader.displayImage(CollageDetailActivity.this, collageDetailBean.getData().getCollageGroup().getHeadPic(), ivHead, R.mipmap.ic_default_avatar);
                tvName.setText(collageDetailBean.getData().getCollageGroup().getInitiatorName());
                if (collageDetailBean.getData().getCollageGroup().getMemberCount() - collageDetailBean.getData().getCollageGroup().getCurrentMemberCount() > 0) {
                    tvTeamStatus.setText("还差" + (collageDetailBean.getData().getCollageGroup().getMemberCount() - collageDetailBean.getData().getCollageGroup().getCurrentMemberCount()) + "人");
                }else{
                    tvTeamStatus.setText("已满员");
                }
                tvCollageNum.setText("发起了" + collageDetailBean.getData().getCollageGroup().getMemberCount() + "人拼团购");
                setButtonsStatus();
                addMember();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(CollageDetailActivity.this, Util.checkErrorType(error));
                setEmptyView(Constants.NET_DISCONNECT);
            }
        });

    }

    //根据团和订单信息改变文本和按钮状态
    private void setButtonsStatus() {
        tvRefundTip.setVisibility(View.GONE);
        //拼团中
        if ( mCollageDetailBean.getData().getCollageGroup().getStatusX() == 4 || "4".equals(groupHasMe(2))) {

           if (mHandler != null) {
                mHandler.removeCallbacksAndMessages(null);
            }
            tvInvite.setText("我要参团");
            tvMoreTeam2.setText("查看更多拼团商品");
            tvMoreTeam2.setVisibility(View.VISIBLE);
            if (!Util.isEmpty(groupHasMe(1))) {
                tvRefundTip.setVisibility(View.VISIBLE);
                tvInvite.setText("邀请好友拼团");
            }
            //订单点进来 ,订单关闭,界面按订单状态显示保持一致
            if(!Util.isEmpty(id) && isOver==true){
                tvTimeDown.setText("超时关闭");
                tvInvite.setText("查看更多拼团商品");
                tvMoreTeam2.setVisibility(View.GONE);
                return;
            }

            //是否为待支付
            if ( "1".equals(groupHasMe(2))) {
                tvTimeDown.setText("待支付");
                tvInvite.setText("去支付");
                tvMoreTeam2.setVisibility(View.VISIBLE);
                tvMoreTeam2.setText("查看更多拼团商品");
                return;
            }
            //检查库存状态
           if(!checkSku()){
               return;
           }
           //检查拼团时间 后台的逻辑是当前团的时间已结束有人待付款,团的状态会有段时间保持为(进行中 status=4)
           if (System.currentTimeMillis() > mCollageDetailBean.getData().getCollageGroup().getEndDate().getTime()) {
               tvTimeDown.setText("拼团时间已结束");
               tvInvite.setText("查看更多拼团商品");
               tvMoreTeam2.setVisibility(View.GONE);
               if ("1".equals(groupHasMe(2))) {
                   tvTimeDown.setText("待支付");
                   tvInvite.setText("去支付");
                   tvMoreTeam2.setVisibility(View.VISIBLE);
                   tvMoreTeam2.setText("查看更多拼团商品");
                   return;
               }
               return;
           }

            setTimeDown();
            if (mHandler == null) {
                mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        //更改时间
                        setTimeDown();
                        mHandler.sendEmptyMessageDelayed(1, 1000);
                    }
                };
                mHandler.sendEmptyMessageDelayed(1, 1000);
            }
            //未开始
        } else if (System.currentTimeMillis() < mCollageDetailBean.getData().getCollagePromotion().getBeginDate().getTime()) {
            tvTimeDown.setText("拼团尚未开始");
            tvInvite.setText("查看更多拼团商品");
            tvMoreTeam2.setVisibility(View.GONE);
            return;
        }
        if (mCollageDetailBean.getData().getCollageGroup().getStatusX() == 8 || "8".equals(groupHasMe(2))) {
            tvTimeDown.setText("该团已成功拼团");
            if (!Util.isEmpty(groupHasMe(1))) {
                tvMoreTeam2.setVisibility(View.VISIBLE);
                tvInvite.setText("查看更多拼团商品");
                tvMoreTeam2.setText("查看拼团订单");
            } else {
                tvInvite.setText("我要拼团");
                tvMoreTeam2.setVisibility(View.VISIBLE);
                tvMoreTeam2.setText("查看更多拼团商品");
            }
            tvTimeDown.setText("该团已成功拼团");
            tvTeamStatus.setText("已满员");
            return;
        }
        int statusX =0;
        //如果订单中有自己用订单的状态码去判断,如果没有用团的状态去判断
        if(Util.isEmpty(groupHasMe(2))){
            //    团的状态码                status：-8, 退款成功；-2, 超时关闭 ；-1, 待退款； ；1, 待支付；；4, "拼团中； 8,拼团成功；
            statusX=mCollageDetailBean.getData().getCollageGroup().getStatusX();
        }else if(!Util.isEmpty(groupHasMe(2))){
            //    订单的状态码    status：-8, 退款成功；-2, 超时关闭 ；-1, 待退款； ；1, 待支付；；4, "拼团中； 8,拼团成功；
            statusX=Integer.valueOf(groupHasMe(2));
        }

        switch (statusX) {
            case -8:
                tvTimeDown.setText("退款成功");
                tvInvite.setText("去别的团看看");
                tvMoreTeam2.setVisibility(View.VISIBLE);
                tvMoreTeam2.setText("查看更多拼团商品");
                break;
            case -2:
                tvTimeDown.setText("超时关闭");
                tvInvite.setText("查看更多拼团商品");
                tvMoreTeam2.setVisibility(View.GONE);
                break;
            case -1:
                if("-1".equals(groupHasMe(2))){
                    tvTimeDown.setText("拼团失败 待退款");
                }else{
                    tvTimeDown.setText("拼团失败");
                }
                tvInvite.setText("查看更多拼团商品");
                tvMoreTeam2.setVisibility(View.GONE);
                break;
            case 1:
                if ("1".equals(groupHasMe(2))) {
                    tvTimeDown.setText("待支付");
                    tvInvite.setText("去支付");
                    tvMoreTeam2.setVisibility(View.VISIBLE);
                    tvMoreTeam2.setText("查看更多拼团商品");
                }else{
                    tvInvite.setText("我要参团");
                    tvMoreTeam2.setText("查看更多拼团商品");
                    tvMoreTeam2.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }


        //待支付的订单sn
        if (!Util.isEmpty(id) && "1".equals(groupHasMe(2))) {
            tvTimeDown.setText("待支付");
            tvInvite.setText("去支付");
            tvMoreTeam2.setVisibility(View.VISIBLE);
            tvMoreTeam2.setText("查看更多拼团商品");
        }

    }

    //检查商品是否还有库存
    private boolean checkSku() {
            if(mCollageDetailBean==null || mCollageDetailBean.getData() ==null){
                return true;
            }

        if(!Util.isEmpty(groupHasMe(2)) && Integer.valueOf(groupHasMe(2)) > 1 ){//不管有没有库存,拼团订单中有我的订单切状态码大于1(代付款)不显示库存状况
                return true;
            }
        List<CollageDetailBean.DataBean.SkuListBean> skuList = mCollageDetailBean.getData().getSkuList();
        if (skuList != null && skuList.size() > 0) {
            int size = skuList.size();
//            flash=0 冻结=0，已售空
//            flash=0，冻结>0 暂无库存
            int totalFlashStore = 0;
            int totalFreezeStore = 0;
            for (int i = 0; i < size; i++) {
                totalFlashStore += skuList.get(i).getFlashStore();
                totalFreezeStore += skuList.get(i).getFreezeStore();
            }
            if (totalFlashStore == 0 && totalFreezeStore == 0) {
                tvInvite.setText("查看更多拼团商品");
                tvMoreTeam2.setVisibility(View.GONE);
                tvTimeDown.setText("商品已售空,拼团失败");
                tvTip.setVisibility(View.VISIBLE);
                tvTip.setText("您喜欢的商品已经售空 去看看别的吧!");
                return false;
            } else if (totalFlashStore == 0 && totalFreezeStore > 0) {
                tvInvite.setText("去别的团看看");
                tvMoreTeam2.setVisibility(View.VISIBLE);
                tvMoreTeam2.setText("查看更多拼团商品");
                tvTimeDown.setText("暂无库存,拼团失败");
                tvTip.setVisibility(View.VISIBLE);
                tvTip.setText("暂无库存,有其他用户参团失败后,您仍可以参团购买");
                return false;
            }
        }
        return true;
    }
    //添加群成员头像
    private void addMember() {
        llMermberContainer.removeAllViews();
        if(mCollageDetailBean==null || mCollageDetailBean.getData()==null){
            return;
        }
        List<CollageDetailBean.DataBean.CollageGroupBean.CollageOrder> collageOrders = mCollageDetailBean.getData().getCollageGroup().getCollageOrders();
        if (collageOrders == null || collageOrders.size() == 0) {
            return;
        }
        for (int i = 0; i < collageOrders.size(); i++) {
            if (collageOrders.get(i).getStatus() < 0) {
                collageOrders.remove(i);
            }
        }
        for (int i = 0; i < (collageOrders.size()>5?5:collageOrders.size()); i++) {
            View itemView = getLayoutInflater().inflate(R.layout.layout_collage_member_item, llMermberContainer, false);
            RoundedImageView itemIvHead = (RoundedImageView) itemView.findViewById(R.id.iv_memeber_head);
            UniversalImageLoader.displayImage(this, collageOrders.get(i).getHeadPic(), itemIvHead, R.mipmap.ic_default_avatar);
            ImageView itemIvColonel = (ImageView) itemView.findViewById(R.id.iv_colonel);
            if (collageOrders.get(i).getType() == 1) {
                itemIvColonel.setVisibility(View.VISIBLE);
            }
            if(collageOrders.size()>5){
                tvHeadMore.setVisibility(View.VISIBLE);
            }
            llMermberContainer.addView(itemView);
        }

    }


    //加载更多正在拼团的商品列表
    private void loadMoreCollages() {
        progressBar.setVisibility(View.VISIBLE);
        SimpleApi api = new SimpleApi();
        api.setP(1);
        api.setPageSize(20);
        api.setMethod(BaseApi.Method.GET);
        api.setInterPath(Constants.GROUP_BUY_PRODUCT_LIST);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<GroupListBean>() {
            @Override
            public void onResponse(GroupListBean groupListBean) {
                if (isFinishing() || progressBar == null) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                setDataToContainer(groupListBean);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (isFinishing() || progressBar == null) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                Util.showToast(CollageDetailActivity.this, Util.checkErrorType(error));
            }
        });
    }

    //addView的方式添加拼团商品,去掉了未开始的,只保留活动进行中的
    private void setDataToContainer(final GroupListBean groupListBean) {
        if (groupListBean.getData().getCollageList().getList() == null || groupListBean.getData().getCollageList().getList().size() == 0) {
            return;
        }
        llCollagesContainer.setVisibility(View.VISIBLE);
        int childCount = llCollagesContainer.getChildCount();
        //llCollagesContainer原本有三个View,上面两个下面一个,中间用代码添加拼团Item进去,添加之前将index=0,1和最后一个View保留,其它移除
        if (childCount > 3) {
            for (int i = 0; i < childCount; i++) {
                if (i > 1 && i != childCount - 1) {
                    llCollagesContainer.removeViewAt(childCount - i);
                }
            }
        }
        int size = groupListBean.getData().getCollageList().getList().size();
        int offer=2;
        for (int i = 0; i < (size > 10 ? 10 : size); i++) {
             if (System.currentTimeMillis() < groupListBean.getData().getCollageList().getList().get(i).getBegainDate()) {
                 offer-=1;
                 continue;
            }
            View itemView = getLayoutInflater().inflate(R.layout.layout_collage_item, llCollagesContainer, false);
            ImageView itemIvProduct = (ImageView) itemView.findViewById(R.id.iv_product);
            TextView itemTvProductName = (TextView) itemView.findViewById(R.id.tv_product_name);
            TextView itemTvSubTitle = (TextView) itemView.findViewById(R.id.tv_sub_title);
            TextView itemTvOldPrice = (TextView) itemView.findViewById(R.id.tv_old_price);
            UniversalImageLoader.displayImage(this, groupListBean.getData().getCollageList().getList().get(i).getProduct().getImg(), itemIvProduct, R.mipmap.ic_logo_empty5);
            itemTvProductName.setText(groupListBean.getData().getCollageList().getList().get(i).getProduct().getName());
            itemTvSubTitle.setText(groupListBean.getData().getCollageList().getList().get(i).getProduct().getSubTitle());
            itemTvOldPrice.setText("原价: ¥" + Util.getNumberFormat(groupListBean.getData().getCollageList().getList().get(i).getProduct().getMinPrice()));
            double price = groupListBean.getData().getCollageList().getList().get(i).getProduct().getCollagePrice();
            int memberCount = groupListBean.getData().getCollageList().getList().get(i).getMemberCount();
            String str = new String(memberCount + "人团: ¥" + price);
            TextView itemTvCollagePrice = (TextView) itemView.findViewById(R.id.tv_collage_price);
            final TextView itemTvToCollage = (TextView) itemView.findViewById(R.id.tv_to_collage);
            SpannableString sb = new SpannableString(str);
            sb.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(12)), 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            sb.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(18)), 6, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            sb.setSpan(new StyleSpan(Typeface.BOLD), 6, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //粗体
            sb.setSpan(new ForegroundColorSpan(Color.RED), 5, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            itemTvCollagePrice.setText(sb);
            itemTvToCollage.setText("去拼团");
            final int finalI = i;
            itemIvProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(CollageDetailActivity.this, ProductDetailActivity.class)
                            .putExtra("id", Long.valueOf(groupListBean.getData().getCollageList().getList().get(finalI).getId()))
                            .putExtra("collageId", groupListBean.getData().getCollageList().getList().get(finalI).getProduct().getCollagePromotionId())
                    );
                }
            });
            itemTvToCollage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("去拼团".equals(itemTvToCollage.getText().toString())) {
                        startActivity(new Intent(CollageDetailActivity.this, ProductDetailActivity.class)
                                .putExtra("id", Long.valueOf(groupListBean.getData().getCollageList().getList().get(finalI).getId()))
                                .putExtra("collageId", groupListBean.getData().getCollageList().getList().get(finalI).getProduct().getCollagePromotionId())
                        );
                    } else if ("提醒我".equals(itemTvToCollage.getText().toString())) {
                        requestRemind(groupListBean.getData().getCollageList().getList().get(finalI).getProduct().getCollagePromotionId());
                    }
                }
            });
            //offer相当于llCollagesContainer头部View的数量(2)和未开始的团Item的数量求差
            llCollagesContainer.addView(itemView, i + offer);
        }
    }
    private void requestRemind(int id) {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.GROUP_BUY_PRODUCT_REMIND, id));
        api.setMethod(BaseApi.Method.POST);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                FlashNoticePop noticePop = new FlashNoticePop(CollageDetailActivity.this);
                noticePop.show(getWindow().getDecorView());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    Intent intent = new Intent(CollageDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Util.showToast(CollageDetailActivity.this, Util.checkErrorType(error));
                }
            }
        });
    }


    @OnClick({R.id.back_iv, R.id.title_right, R.id.iv_product, R.id.ll_mermber_container, R.id.ll_collage_rule, R.id.tv_invite
            , R.id.tv_more_team, R.id.btn_reload, R.id.tv_more_team2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.title_right://分享
                share();
                break;
            case R.id.iv_product:  //商品详情
                if(mCollageDetailBean==null){
                    return;
                }
                startActivity(new Intent(CollageDetailActivity.this, ProductDetailActivity.class)
                        .putExtra("id", Long.valueOf(mCollageDetailBean.getData().getProduct().getId()))
                        .putExtra("collageId",mCollageDetailBean.getData().getCollageGroup().getPromotionId()));
                break;
            case R.id.ll_mermber_container: //团员
                if(mCollageDetailBean==null){
                    return;
                }
                if (collageMemberListPop == null) {
                    collageMemberListPop = new CollageMemberListPop(this, mCollageDetailBean.getData().getCollageGroup().getCollageOrders());
                }
                collageMemberListPop.show(getWindow().getDecorView());
                break;
            case R.id.ll_collage_rule:  //拼团规则/page/Collagerule
                toWebActivity("/page/Collagerule", true);
                break;
            case R.id.tv_invite://拼团邀请
                Intent intent;
                if(mCollageDetailBean==null){
                    return;
                }
                if ("去支付".equals(tvInvite.getText().toString())) {
                    if (Util.loginChecked(CollageDetailActivity.this, 999)) {
                        Intent intent1 = new Intent(CollageDetailActivity.this, CashierActivity.class);
                        intent1 .putExtra("payType","collage")
                                .putExtra("subject",mCollageDetailBean.getData().getProduct().getName())
                                .putExtra("payStyle", groupHasMe(5))
                                .putExtra("id", groupHasMe(1))
                                .putExtra("orderId", groupHasMe(6));
                        if(!Util.isEmpty(groupHasMe(3))){
                            intent1.putExtra("skuId",  Long.valueOf(groupHasMe(3)));
                        }
                        startActivityForResult(intent1, Constants.RequestCode.ORDER);
                    }


                } else if ("邀请好友拼团".equals(tvInvite.getText().toString())) {
                    share();
                } else if ("我要参团".equals(tvInvite.getText().toString())) {
                    if (Util.loginChecked(CollageDetailActivity.this, 999)) {
                            checkTerm();

                    }

                } else if ("我要拼团".equals(tvInvite.getText().toString())) {
//                    拼团下单 传字段isPack值为true 用startActivityForResult跳转 原来的canUseCoupon废弃
//                    回调用字段success字段判断是否支付成功 0是失败1是成功
                    //                （1）productCreatedLimit<=currentCount开团上限
                    //                 （2）两个拼团团队中差的人数是groupList里的 memberCount-currentMemberCount
//                    if(mCollageDetailBean!=null && mCollageDetailBean.getData().getCollagePromotion().getCurrentCount()>= mCollageDetailBean.getData().getPromotion().getMemberCount() ){
//                        Util.showToast(this,"拼团数已达上限");
//                        return;
//                    }
                    if(mCollageDetailBean==null){
                        return;
                    }
                    if (Util.loginChecked(this, 999)) {
                        intent = new Intent(CollageDetailActivity.this, ProductDetailActivity.class)
                                .putExtra("id", Long.valueOf(mCollageDetailBean.getData().getProduct().getId()))
                                .putExtra("collageId", mCollageDetailBean.getData().getCollagePromotion().getId());
                        startActivity(intent);
                    }

                } else if ("去别的团看看".equals(tvInvite.getText().toString())) {
                    if(mCollageDetailBean==null){
                        return;
                    }
                    intent = new Intent(CollageDetailActivity.this, ProductDetailActivity.class)
                            .putExtra("id", Long.valueOf(mCollageDetailBean.getData().getProduct().getId()))
                            .putExtra("collageId", mCollageDetailBean.getData().getCollagePromotion().getId());
                    startActivity(intent);
                } else if ("查看更多拼团商品".equals(tvInvite.getText().toString())) {
                    intent = new Intent(CollageDetailActivity.this, GroupBuyActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_more_team://更多拼团
                intent = new Intent(CollageDetailActivity.this, GroupBuyActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_more_team2://更多拼团
                if ("查看拼团订单".equals(tvMoreTeam2.getText().toString())) {
                    if (Util.loginChecked(this, 999)) {
                        if (user == null) {
                            user = Session.getInstance().getUserFromFile(this);
                        }
                        //遍历团中的订单信息,取memberId=自己的那个跳转
                        if (!Util.isEmpty(groupHasMe(1))) {
                            intent = new Intent(CollageDetailActivity.this, OrderDetailActivity.class);
                            intent.putExtra("orderSn", groupHasMe(1));
                            startActivity(intent);
                        }

                    }
                } else if ("查看更多拼团商品".equals(tvMoreTeam2.getText().toString())) {
                    intent = new Intent(CollageDetailActivity.this, GroupBuyActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.btn_reload:
                btnReload.setVisibility(View.GONE);
                imgHint.setVisibility(View.GONE);
                loadCollageDetial();
                break;
        }
    }
    //判断是否满足参团条件,如果不成功在请求失败中返回 status=-1
    //     code:1001：你已参加该商品其他团，是否去该团详情"
    //          1002：该拼团活动开团数已到上限，看看其他已经开的团吧",
    //          1003：你已超过该商品最大成团数
    private void checkTerm() {
        CheckCollageTermApi checkCollageTermApi = new CheckCollageTermApi();
        checkCollageTermApi.setGroupId(mCollageDetailBean.getData().getCollageGroup().getId());
        checkCollageTermApi.setCollageId(mCollageDetailBean.getData().getCollagePromotion().getId());
        D2CApplication.httpClient.loadingRequest(checkCollageTermApi, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                //满足参团条件
                //单规格不弹SKU选择POP
                if(mCollageDetailBean.getData().getSkuList() != null && mCollageDetailBean.getData().getSkuList().size()==1){
                    Intent intent1 = new Intent(CollageDetailActivity.this, OrderBalanceActivity.class)
                            .putExtra("isPack", true)       //isPack是否是拼团
                            .putExtra("groupId", mCollageDetailBean.getData().getCollageGroup().getId())
                            .putExtra("collageId", mCollageDetailBean.getData().getCollagePromotion().getId())
                            .putExtra("skuId", (long)mCollageDetailBean.getData().getSkuList().get(0).getId())
                            .putExtra("num", 1);
                    startActivityForResult(intent1, Constants.RequestCode.ORDER);
                    return;
                }
                if (shoppingPop == null) {
                    shoppingPop = new ShoppingPop(CollageDetailActivity.this);
                    shoppingPop.setData(mCollageDetailBean.getData().getProduct());
                }
                shoppingPop.showNumLayout(false);
                shoppingPop.setPrice(mCollageDetailBean.getData().getProduct().getCollagePrice());
                shoppingPop.setCollage(true);
                shoppingPop.show(3, getWindow().getDecorView());
                shoppingPop.setBackListener(new ShoppingPop.CallBackListener() {
                    @Override
                    public void sure(long skuId) {
                        Intent intent1 = new Intent(CollageDetailActivity.this, OrderBalanceActivity.class)
                                .putExtra("isPack", true)       //packBuy是否是拼团
                                .putExtra("groupId", mCollageDetailBean.getData().getCollageGroup().getId())
                                .putExtra("collageId", mCollageDetailBean.getData().getCollagePromotion().getId())
                                .putExtra("skuId", skuId)
                                .putExtra("num", 1);
                        startActivityForResult(intent1, Constants.RequestCode.ORDER);
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                try {
                    if (error instanceof HttpError) {
                        HttpError httpError = (HttpError) error;
                        if (httpError.getStatus() == -1) {
                            String jsonData = httpError.getErrorBean().getJsonData();
                            Gson gson = new GsonBuilder().create();
                            CheckCollageTermErrorBean checkCollageTermErrorBean = null;
                            checkCollageTermErrorBean = gson.fromJson(jsonData, CheckCollageTermErrorBean.class);
                            if (checkCollageTermErrorBean != null && "1001".equals(checkCollageTermErrorBean.getData().getCode())) {
                                //满足参团条件不满足条件
                                final CheckCollageTermErrorBean finalCheckCollageTermErrorBean = checkCollageTermErrorBean;
                                new AlertDialog.Builder(CollageDetailActivity.this)
                                        .setMessage(error.getMessage())
                                        .setPositiveButton("点击查看我的团", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(new Intent(CollageDetailActivity.this, CollageDetailActivity.class).putExtra("collageId", finalCheckCollageTermErrorBean.getData().getCollageOrder().getGroupId()));
                                            }
                                        })
                                        .setNegativeButton("取消", null)
                                        .show();
                            } else if(checkCollageTermErrorBean != null && ("1003".equals(checkCollageTermErrorBean.getData().getCode()) || "1002".equals(checkCollageTermErrorBean.getData().getCode()))){
                                new AlertDialog.Builder(CollageDetailActivity.this)
                                        .setMessage(error.getMessage())
                                        .setPositiveButton("看看其它", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(new Intent(CollageDetailActivity.this, GroupBuyActivity.class));
                                            }
                                        })
                                        .setNegativeButton("取消", null)
                                        .show();
                            } else {
                                Util.showToast(CollageDetailActivity.this, Util.checkErrorType(error));
                            }

                        }
                    }
                } catch (Exception je) {
                    Util.showToast(CollageDetailActivity.this, Util.checkErrorType(error));
                }
            }

        });

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
        intent.putExtra("isShareGone", false);
        startActivity(intent);
    }

    //设置试用期倒计时
    private void setTimeDown() {
        if (mCollageDetailBean == null) {
            return;
        }
        long offer = mCollageDetailBean.getData().getCollageGroup().getEndDate().getTime() - System.currentTimeMillis();
        long day = offer / (24 * 60 * 60 * 1000);
        long hour = offer / (60 * 60 * 1000);
        long minute = (offer / (60 * 1000)) % 60;
        long mouse = (offer / 1000) % 60;
        if(offer<=0){
            if(mHandler!=null){
                mHandler.removeCallbacksAndMessages(null);
            }
            return;
        }
        String s = new String("距结束: " + addZero(hour, false) + addZero(minute, true) + addZero(mouse, true));
        tvTimeDown.setText(s);
    }

    private String addZero(long num, boolean addSign) {//天数不加冒号
        if (num < 0) {
            return "00";
        }
        if (num < 10) {
            if (addSign) {
                return ":0" + num;
            } else {
                return "0" + num;
            }
        } else {
            if (addSign) {
                return ":" + String.valueOf(num);
            } else {
                return String.valueOf(num);
            }
        }
    }

    //订单中是否有我,未登录或没有为null 有的话返回我的订单Sn
    private String groupHasMe(int type) {//type=1返回我的订单Sn type=2返回我的订单状态码 type=3返回我的skuID type=4返回我是不是团长 1是团长 type=5返回payParam  type=6返回id
        if (mCollageDetailBean == null || user == null || mCollageDetailBean.getData().getCollageGroup().getCollageOrders() == null || mCollageDetailBean.getData().getCollageGroup().getCollageOrders().size() == 0) {
            return null;
        }
        for (int i = 0; i < mCollageDetailBean.getData().getCollageGroup().getCollageOrders().size(); i++) {
            if (mCollageDetailBean.getData().getCollageGroup().getCollageOrders().get(i).getMemberId() == user.getMemberId()) {
                if (type == 1) {
                    return mCollageDetailBean.getData().getCollageGroup().getCollageOrders().get(i).getOrderSn();
                } else if(type == 2){
                    return String.valueOf(mCollageDetailBean.getData().getCollageGroup().getCollageOrders().get(i).getStatus());
                }else if(type == 3){
                    return String.valueOf(mCollageDetailBean.getData().getCollageGroup().getCollageOrders().get(i).getSkuId());
                }else if(type == 4){
                    return String.valueOf(mCollageDetailBean.getData().getCollageGroup().getCollageOrders().get(i).getType());
                }else if(type == 5){
                    return String.valueOf(mCollageDetailBean.getData().getCollageGroup().getCollageOrders().get(i).getPayParams());
                } else if(type == 6){
                    return String.valueOf(mCollageDetailBean.getData().getCollageGroup().getCollageOrders().get(i).getId());
                }
            }
        }
        return null;
    }

    private void share() {
        if(mCollageDetailBean==null || mCollageDetailBean.getData()==null){
            return;
        }
        SharePop sharePop = new SharePop(this);
        sharePop.setTitle(mCollageDetailBean.getData().getProduct().getName());
        sharePop.setDescription("D2C拼团-一起拼更省钱!快来"+Util.getNumberFormat(mCollageDetailBean.getData().getProduct().getCollagePrice())+"元抢"+mCollageDetailBean.getData().getCollagePromotion().getName());
        sharePop.setPromotionLink(true, true);
        if (!Util.isEmpty(mCollageDetailBean.getData().getProduct().getImg())) {
            sharePop.setImage(Util.getD2cPicUrl(mCollageDetailBean.getData().getProduct().getImg(), 100, 100), false);
            sharePop.setImage(Util.getD2cPicUrl(mCollageDetailBean.getData().getProduct().getImg(), 360, 500), true);
        }
        sharePop.setPoster(getPosterView(getMiniAppRQUrl(), sharePop));
        sharePop.setBgImageUrl("http://static.d2c.cn/img/other/bg-collage.jpg");
        sharePop.setMiniWebUrl("/collage/" + collageId);
        sharePop.show(getWindow().getDecorView());
//        拼团列表 pages/groupon/list  参数parent_id
//        拼团商品 pages/groupon/product  参数id=拼团活动id&parent_id=1
//        拼团详情 pages/groupon/detail 参数id=拼团订单id&parent_id=1
    }
        //海报View
    private Poster getPosterView(String url, final SharePop sharePop) {
        final Poster poster = new Poster();
        View view = LayoutInflater.from(this).inflate(R.layout.layout_poster_view, new RelativeLayout(this), false);
        final LinearLayout ll = (LinearLayout) view.findViewById(R.id.product_ll);
        final ImageView bgIv = (ImageView) view.findViewById(R.id.bg_iv);
        final ImageView rq = (ImageView) view.findViewById(R.id.rq);
        final ImageView avatar = (ImageView) view.findViewById(R.id.avatar);
        Glide.with(this).load(Util.getD2cPicUrl("http://static.d2c.cn/img/other/bg-collage.jpg")).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                poster.baseMap = true;
                RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) ll.getLayoutParams();
                rl.width = resource.getWidth() * 13 / 18;
                rl.height = resource.getWidth() * 14 / 9;
                RelativeLayout.LayoutParams rql = (RelativeLayout.LayoutParams) rq.getLayoutParams();
                rql.width = resource.getWidth() * 5 / 18;
                rql.setMargins(0,0,ScreenUtil.dip2px(16),0);
                rql.height = resource.getWidth() * 5 / 18;
                rq.setLayoutParams(rql);
                LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) avatar.getLayoutParams();
                ll.width = resource.getWidth() / 9;
                ll.height = resource.getWidth() / 9;
                avatar.setLayoutParams(ll);
                bgIv.setImageBitmap(resource);
            }
        });
        Glide.with(this).load(Util.getD2cPicUrl(mCollageDetailBean.getData().getCollageGroup().getHeadPic())).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Bitmap bitmap = BitmapUtils.getScaleBitmap(resource, (float) ScreenUtil.dip2px(40) / resource.getWidth(), (float) ScreenUtil.dip2px(40) / resource.getHeight());
                avatar.setImageBitmap(BitmapUtils.getCircleBitmap(bitmap));
            }
        });
        //UniversalImageLoader.displayRoundImage(this,Session.getInstance().getPosterBean().pic,avatar,R.mipmap.ic_default_avatar);
        final ImageView productImage = (ImageView) view.findViewById(R.id.product_image);
        Glide.with(this).load(Util.getD2cPicUrl(mCollageDetailBean.getData().getProduct().getImg(),550,825)).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                poster.productMap = true;
                productImage.setImageBitmap(resource);
                sharePop.setMiniImage(Util.getD2cPicUrl(mCollageDetailBean.getData().getProduct().getImg(),250,250),false,0);
            }
        });
        LinearLayout llProductInfo = (LinearLayout) view.findViewById(R.id.ll_product_info);
        llProductInfo.setGravity(Gravity.CENTER_VERTICAL);
        llProductInfo.setPadding(ScreenUtil.dip2px(10),0,ScreenUtil.dip2px(10),0);
        TextView text = (TextView) view.findViewById(R.id.text);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setTextSize(14);
        TextView tvAction = (TextView) view.findViewById(R.id.tv_little_text);
        tvAction.setTextSize(12);
        tvAction.setText("长按小程序码 和好友一起拼");
        TextPaint paint = tvTitle.getPaint();
        paint.setFakeBoldText(true);
        tvTitle.setText("尖货好物 一起拼才省钱");
        text.setText(mCollageDetailBean.getData().getProduct().getName());
        text.setTextSize(14);
        TextView priceTv = (TextView) view.findViewById(R.id.price_tv);
        priceTv.setTextSize(12);
        priceTv.setText(getPriceTv());
        Glide.with(this).load(getMiniAppRQUrl()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                rq.setImageBitmap(resource);
            }
        });
        sharePop.setMiniProjectPath(getMiniAppPath());
        poster.posterView = view;
        return poster;
    }

    //小程序拼团详情url
    private String getMiniAppPath() {
        if (user != null && user.getPartnerId() > 0) {
            return "pages/groupon/detail?id=" + mCollageDetailBean.getData().getCollageGroup().getId() + "&parent_id=" + user.getPartnerId();
        } else {
            return "pages/groupon/detail?id=" + mCollageDetailBean.getData().getCollageGroup().getId();
        }
    }

    //小程序拼团详情码url
    private String getMiniAppRQUrl() {
        if (user != null && user.getPartnerId() > 0) {
            String param="id="+mCollageDetailBean.getData().getCollageGroup().getId() + "&parent_id=" + user.getPartnerId();
            return "https://appserver.d2cmall.com/v3/api/weixin/code?appId=wx58eb0484ce91f38f&content="+ Util.toURLEncode(param) + "&page=pages/groupon/detail";
        } else {
            String param="id="+mCollageDetailBean.getData().getCollageGroup().getId();
            return "https://appserver.d2cmall.com/v3/api/weixin/code?appId=wx58eb0484ce91f38f&content="+Util.toURLEncode(param) + "&page=pages/groupon/detail";
        }
    }

    private SpannableString getPriceTv() {
        StringBuilder builder = new StringBuilder();
        builder.append("拼团价：").append("¥").append(Util.getNumberFormat(mCollageDetailBean.getData().getProduct().getCollagePrice()))
                .append("  原价：").append(Util.getNumberFormat(mCollageDetailBean.getData().getProduct().getOriginalPrice()));
        SpannableString sb = new SpannableString(builder.toString());
        int one = builder.toString().indexOf("¥");
        int two = builder.toString().indexOf("原");

        ForegroundColorSpan span1 = new ForegroundColorSpan(Color.parseColor("#fff23365"));
        sb.setSpan(span1, one, two, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        ForegroundColorSpan span2 = new ForegroundColorSpan(Color.parseColor("#4D000000"));
        sb.setSpan(span2, two, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        RelativeSizeSpan span3 = new RelativeSizeSpan((float) 1.8);
        sb.setSpan(span3, one + 1, two, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        return sb;
    }

    private void setEmptyView(int type) {
        scrollView.setVisibility(View.GONE);
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

    @Override
    protected void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == Constants.RequestCode.ORDER) {
            loadCollageDetial();
        }
        if (resultCode == RESULT_OK && requestCode == 999) {
            user = Session.getInstance().getUserFromFile(this);
            loadCollageDetial();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
