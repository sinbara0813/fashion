package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.d2cmall.buyer.adapter.ProductAdapter;
import com.d2cmall.buyer.adapter.ProductSimpleAdapter;
import com.d2cmall.buyer.api.ArrivalNoticeApi;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.ProductCollectApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.CartRecommendBean;
import com.d2cmall.buyer.bean.Poster;
import com.d2cmall.buyer.bean.ProductDetailBean;
import com.d2cmall.buyer.bean.PushBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.fragment.ProductCommendFragment;
import com.d2cmall.buyer.fragment.ProductFragment;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.AliasImageView;
import com.d2cmall.buyer.widget.FlashNoticePop;
import com.d2cmall.buyer.widget.LimitViewPager;
import com.d2cmall.buyer.widget.OpenMsgPushPop;
import com.d2cmall.buyer.widget.SharePop;
import com.d2cmall.buyer.widget.ShoppingPop;
import com.d2cmall.buyer.widget.TransparentPop;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayerManager;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.ProductDetail;
import com.qiyukf.unicorn.api.Unicorn;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import q.rorbin.badgeview.QBadgeView;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/26 10:33
 * Copyright (c) 2016 d2cmall. All rights reserved.
 * 商品详情页
 */
public class ProductDetailActivity extends BaseActivity {

    @Bind(R.id.view_pager)
    LimitViewPager viewPager;
    @Bind(R.id.first_title_ll)
    LinearLayout firstTitleLl;
    @Bind(R.id.second_title_ll)
    LinearLayout secondTitleLl;
    @Bind(R.id.product_tv)
    TextView productTv;
    @Bind(R.id.detail_tv)
    TextView detailTv;
    @Bind(R.id.comment_tv)
    TextView commentTv;
    @Bind(R.id.match_tv)
    TextView matchTv;
    @Bind(R.id.img_cursor)
    ImageView imgCursor;
    @Bind(R.id.product_tab_ll)
    LinearLayout productTabLl;
    @Bind(R.id.bottom_cart_iv)
    ImageView bottomCartIv;
    @Bind(R.id.bottom_cart_tv)
    TextView bottomCartTv;
    @Bind(R.id.bottom_cart_ll)
    LinearLayout bottomCartLl;
    @Bind(R.id.bottom_service_ll)
    LinearLayout bottomServiceLl;
    @Bind(R.id.bottom_shop_iv)
    ImageView bottomShopIv;
    @Bind(R.id.bottom_shop_tv)
    TextView bottomShopTv;
    @Bind(R.id.bottom_shop_ll)
    LinearLayout bottomShopLl;
    @Bind(R.id.bottom_add_cart)
    TextView bottomAddCart;
    @Bind(R.id.bottom_buy)
    TextView bottomBuy;
    @Bind(R.id.bottom_ll)
    RelativeLayout bottomLl;
    @Bind(R.id.cache_image)
    AliasImageView cacheImage;
    @Bind(R.id.parent_view)
    RelativeLayout parentView;
    @Bind(R.id.push_toast)
    LinearLayout pushToast;
    @Bind(R.id.product_sale_ll)
    LinearLayout productSaleLl;
    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.handle_iv)
    ImageView handleIv;
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.trans_view)
    View transView;
    @Bind(R.id.pack_tv)
    TextView packTv;
    @Bind(R.id.pack_rl)
    LinearLayout packRl;

    private ProductAdapter productAdapter;

    private long id; //商品id
    private int collageId; //拼团活动id;
    private int parentId; //买手id
    private long crowId;
    private int brandId;
    private boolean isCod;
    private boolean isSecKill;
    private boolean isFitting;
    private boolean isCollect;
    private ProductDetailBean.DataBean.ProductBean productBean;

    private int itemWidth;
    private int currentIndex;

    private float lastAlpha;

    private ShoppingPop shoppingPop;
    private SharePop sharePop;
    private Handler handler;
    private long endTime;

    private QBadgeView cartCount;
    private int height;
    private float scale = 0.8f;
    private HashMap<String,String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
        doBusiness();
    }

    public void doBusiness() {
        id = getIntent().getLongExtra("id", 0);
        collageId = getIntent().getIntExtra("collageId", 0);
        parentId = getIntent().getIntExtra("parentId", parentId);
        viewPager.setCanScroll(false);
        viewPager.setOffscreenPageLimit(3);//緩存viewpager存活數量 對內存不利需要優化
        productAdapter = new ProductAdapter(getSupportFragmentManager(), id, collageId);
        viewPager.setAdapter(productAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            public void onPageSelected(int position) {
                changePage(position);
            }

            public void onPageScrollStateChanged(int state) {

            }
        });
        transView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                openSimple();
                return true;
            }
        });
        initCursor();
        if (HomeActivity.count > 0 && collageId == 0) {
            if (cartCount == null) {
                cartCount = (QBadgeView) new QBadgeView(this).bindTarget(bottomCartIv).setBadgeTextSize(8, true).setBadgeGravity(Gravity.END | Gravity.TOP).setGravityOffset(2, 0, true);
            }
            if (HomeActivity.count > 9) {
                cartCount.setBadgeText("9+");
            } else {
                cartCount.setBadgeNumber(HomeActivity.count);
            }
        }
        if (Session.getInstance().getUser()!=null&&Session.getInstance().getUser().getPartnerId()>0) {
            commentTv.setText("素材");
        }
    }


    public void selectPage(int page) {
        viewPager.setCurrentItem(page);
    }

    @OnClick({R.id.back_b, R.id.back_w, R.id.share_b, R.id.share_w, R.id.product_tv, R.id.detail_tv, R.id.comment_tv, R.id.match_tv, R.id.bottom_add_cart,
            R.id.bottom_cart_ll, R.id.bottom_service_ll, R.id.bottom_shop_ll, R.id.handle_iv, R.id.bottom_buy, R.id.pack_rl})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.product_tv:
                if (secondTitleLl.getAlpha() > 0.1) {
                    viewPager.setCurrentItem(0);
                    if (!bottomAddCart.isEnabled() || bottomAddCart.getText().equals("到货通知")) {
                        productSaleLl.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.detail_tv:
                if (secondTitleLl.getAlpha() > 0.1) {
                    viewPager.setCurrentItem(1);
                    productSaleLl.setVisibility(View.GONE);
                }
                break;
            case R.id.comment_tv:
                if (secondTitleLl.getAlpha() > 0.1) {
                    viewPager.setCurrentItem(2);
                    productSaleLl.setVisibility(View.GONE);
                }
                break;
            case R.id.match_tv:
                if (secondTitleLl.getAlpha() > 0.1) {
                    viewPager.setCurrentItem(3);
                    productSaleLl.setVisibility(View.GONE);
                }
                break;
            case R.id.bottom_add_cart:
                if (isCod) {
                    showDialog();
                } else if (collageId > 0) {//拼团单独购买
                    joinShoppingCart(true, false, false);
                } else {
                    joinShoppingCart(false, false, false);
                }
                break;
            case R.id.bottom_buy:
                if (collageId > 0) {//拼团下单
                    if (productBean.getPromotionStatus() == 0) {
                        remindMe();
                    } else {
                        joinShoppingCart(true, false, true);
                    }
                } else {
                    joinShoppingCart(true, false, false);
                }
                break;
            case R.id.bottom_cart_ll:
                if (Util.loginChecked(this, 100)) {
                    if (collageId > 0) {//跳拼团商城
                        Intent intent = new Intent(this, GroupBuyActivity.class);
                        startActivity(intent);
                    } else {
                        stat("购物车入口", "商品详情页购物车",getMap("商品详情页购物车"));
                        Intent intent = new Intent(this, CartActivity.class);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.bottom_service_ll:
                if (Util.loginChecked(this, 777)) {
                    serviceChat();
                }
                break;
            case R.id.bottom_shop_ll:
                if (productBean == null) {
                    return;
                }
                if (isFitting) {
                    if (Util.loginChecked(ProductDetailActivity.this, 999)) {
                        if (shoppingPop == null) {
                            shoppingPop = new ShoppingPop(this);
                            shoppingPop.setData(productBean);
                        }
                        shoppingPop.showNumLayout(false);
                        shoppingPop.show(2, getWindow().getDecorView());
                    }
                } else if (isCollect) {
                    if (Util.loginChecked(ProductDetailActivity.this, 0)) {
                        collect(v);
                    }
                } else {
                    stat("商品详情", "店铺",getMap("店铺"));
                    Intent intent1 = new Intent(this, BrandDetailActivity.class);
                    intent1.putExtra("id", brandId);
                    startActivity(intent1);
                }
                break;
            case R.id.back_b:
            case R.id.back_w:
                finish();
                break;
            case R.id.share_b:
            case R.id.share_w:
                share();
                break;
            case R.id.handle_iv:
            case R.id.tv:
                if (recycleView.getVisibility() == View.VISIBLE) {
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(handleIv, "rotation", 180f, 0f);
                    ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(productSaleLl, "translationY", 0, height);
                    List<Animator> list = new ArrayList<>();
                    list.add(objectAnimator);
                    list.add(objectAnimator1);
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.setDuration(250);
                    animatorSet.playTogether(list);
                    animatorSet.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            recycleView.setVisibility(View.INVISIBLE);
                            transView.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    animatorSet.start();
                } else {
                    recycleView.setVisibility(View.VISIBLE);
                    transView.setVisibility(View.VISIBLE);
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(handleIv, "rotation", 0f, 180f);
                    ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(productSaleLl, "translationY", height, 0);
                    List<Animator> list = new ArrayList<>();
                    list.add(objectAnimator);
                    list.add(objectAnimator1);
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.setDuration(250);
                    animatorSet.playTogether(list);
                    animatorSet.start();
                }
                break;
            case R.id.pack_rl:
                Intent intent = new Intent(this, GroupBuyActivity.class);
                startActivity(intent);
                break;
        }
    }

    private Map<String,String> getMap(String label){
        if (map==null){
            map=new HashMap<>();
        }
        if (!map.containsKey("商品id")){
            map.put(label+"_商品id",id+"");
        }
        return map;
    }

    private void remindMe() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.GROUP_BUY_PRODUCT_REMIND, collageId));
        api.setMethod(BaseApi.Method.POST);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                FlashNoticePop noticePop = new FlashNoticePop(ProductDetailActivity.this);
                noticePop.show(ProductDetailActivity.this.getWindow().getDecorView());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    Intent intent = new Intent(ProductDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Util.showToast(ProductDetailActivity.this, Util.checkErrorType(error));
                }
            }
        });
    }

    private void showDialog() {
        arrivalNotice();
    }

    private void arrivalNotice() {
        ArrivalNoticeApi api = new ArrivalNoticeApi();
        api.setInterPath(String.format(Constants.ARRIVAL_NOTICE, id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                Util.showToast(ProductDetailActivity.this, "到货会给您推送哦");
                bottomAddCart.setEnabled(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {  //以防万一代码
                    Intent intent = new Intent(ProductDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Util.showToast(ProductDetailActivity.this, Util.checkErrorType(error), false);
                }
            }
        });
    }

    private void share() {
        if (productBean == null) {
            return;
        }
        if (Util.loginChecked(this, 999)) {

            stat("商品详情", "分享",getMap("分享"));
            sharePop = new SharePop(this);
            sharePop.setDescription(productBean.getName());
            sharePop.setProductShare(true);
            if (collageId > 0) {//说明当前是拼团商品详情页
                sharePop.setDescription("D2C拼团-一起拼更省钱!快来" + Util.getNumberFormat(productBean.getCollagePrice()) + "元抢" + productBean.getCollageName());
                sharePop.setPromotionLink(true, true);
                UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(this);
                sharePop.setBigImageUrl(productBean.getId() + ".png");
                sharePop.setPoster(getPosterView("", sharePop, user));
                sharePop.setMiniWebUrl("/collage/list");
            }
            sharePop.setMiniImage(Util.getD2cPicUrl(productBean.getImg(), 250, 250), false, 0);
            if (Session.getInstance().getUserFromFile(this) != null && Session.getInstance().getUserFromFile(this).getPartnerId() != 0) {
                sharePop.setMiniProjectPath("/pages/product/detail?id=" + id + "&parent_id=" + Session.getInstance().getUserFromFile(this).getPartnerId());
            } else {
                sharePop.setMiniProjectPath("/pages/product/detail?id=" + id);
            }
            sharePop.setMiniWebUrl(Constants.SHARE_URL + "/product/" + id);
            sharePop.setTitle(productBean.getName());
            if (productBean.getImgs().size() > 0) {
                sharePop.setImage(Util.getD2cPicUrl(productBean.getImgs().get(0), 100, 100), false);
                sharePop.setImage(Util.getD2cPicUrl(productBean.getImgs().get(0), 360, 500), true);
            }
            sharePop.setWebUrl(Constants.SHARE_URL + "/product/" + id);
            bottomBuy.postDelayed(new Runnable() {
                @Override
                public void run() {
                    sharePop.show(getWindow().getDecorView());
                }
            }, 500);

        }

    }

    private Poster getPosterView(String url, final SharePop sharePop, UserBean.DataEntity.MemberEntity user) {
        final Poster poster = new Poster();
        View view = LayoutInflater.from(this).inflate(R.layout.layout_poster_view, new RelativeLayout(this), false);
        final LinearLayout ll = (LinearLayout) view.findViewById(R.id.product_ll);
        final ImageView bgIv = (ImageView) view.findViewById(R.id.bg_iv);
        final ImageView rq = (ImageView) view.findViewById(R.id.rq);
        TextView littleText = (TextView) view.findViewById(R.id.tv_little_text);
        littleText.setText("长按二维码,帮我拼团");
        littleText.setTextSize(12);
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
                rql.height = resource.getWidth() * 5 / 18;
                LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) avatar.getLayoutParams();
                ll.width = resource.getWidth() / 9;
                ll.height = resource.getWidth() / 9;
                bgIv.setImageBitmap(resource);
            }
        });
        Glide.with(this).load(Util.getD2cPicUrl(user.getHead())).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Bitmap bitmap = BitmapUtils.getScaleBitmap(resource, (float) ScreenUtil.dip2px(40) / resource.getWidth(), (float) ScreenUtil.dip2px(40) / resource.getHeight());
                avatar.setImageBitmap(BitmapUtils.getCircleBitmap(bitmap));
            }
        });
        //UniversalImageLoader.displayRoundImage(this,Session.getInstance().getPosterBean().pic,avatar,R.mipmap.ic_default_avatar);
        final ImageView productImage = (ImageView) view.findViewById(R.id.product_image);
        Glide.with(this).load(Util.getD2cPicUrl(productBean.getImg())).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                poster.productMap = true;
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(resource.getByteCount());
                resource.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                byte[] bytes = outputStream.toByteArray();
                while (bytes.length > 32768) {
                    scale -= 0.1;
                    Bitmap scaleBitmap = BitmapUtils.getScaleBitmap(resource, scale, scale);
                    bytes = BitmapUtils.getBitmapData(scaleBitmap);
                    scaleBitmap.recycle();
                }
                sharePop.setMiniPicData(bytes);
                productImage.setImageBitmap(resource);
            }
        });
        TextView text = (TextView) view.findViewById(R.id.text);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvTitle.setTextSize(14);
        TextPaint paint = tvTitle.getPaint();
        paint.setFakeBoldText(true);
        tvTitle.setText("尖货好物 一起拼才省钱");
        text.setMaxWidth(ScreenUtil.dip2px(150));
        text.setText(productBean.getName());
        TextView priceTv = (TextView) view.findViewById(R.id.price_tv);
        priceTv.setText(getPriceTv());
        Glide.with(this).load(Util.getD2cPicUrl(getMiniAppRQUrl())).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                rq.setImageBitmap(resource);
            }
        });
        sharePop.setMiniProjectPath(getMiniAppPath());
        poster.posterView = view;
        return poster;
    }

    private String getMiniAppPath() {
        if (Session.getInstance().getUserFromFile(this) != null && Session.getInstance().getUserFromFile(this).getPartnerId() > 0) {
            return "pages/groupon/product?id=" + collageId + "&parent_id=" + Session.getInstance().getUserFromFile(this).getPartnerId();
        } else {
            return "pages/groupon/product?id=" + collageId;
        }
    }

    private String getMiniAppRQUrl() {
        if (Session.getInstance().getUserFromFile(this) != null && Session.getInstance().getUserFromFile(this).getPartnerId() > 0) {
            String params = Util.toURLEncode("id=" + collageId + "&parent_id=" + Session.getInstance().getUserFromFile(this).getPartnerId());
            return "https://appserver.d2cmall.com/v3/api/weixin/code?appId=wx58eb0484ce91f38f&content=" + params + "&page=pages/groupon/product";
        } else {
            String params = Util.toURLEncode("id=" + collageId);
            return "https://appserver.d2cmall.com/v3/api/weixin/code?appId=wx58eb0484ce91f38f&content=" + params + "&page=pages/groupon/product";
        }
    }

    private SpannableString getPriceTv() {
        StringBuilder builder = new StringBuilder();
        builder.append("拼团价：").append("¥").append(Util.getNumberFormat(productBean.getCollagePrice()))
                .append("  原价：").append(Util.getNumberFormat(productBean.getOriginalPrice()));
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

    private void serviceChat() {
        if (productBean == null) {
            return;
        }
        stat("商品详情", "客服",getMap("客服"));
        String url = "http://m.d2cmall.com" + "/product/" + id;
        String title = "商品咨询";
        ProductDetail.Builder builder = new ProductDetail.Builder().setTitle(productBean.getName());
        if (productBean.getImgs().size() >= 1) {
            String shareImage = Util.getD2cPicUrl(productBean.getImgs().get(0), 100, 100);
            if (!Util.isEmpty(shareImage)) {
                builder.setPicture(shareImage);
                builder.setUrl(url);
                builder.setShow(1);
                builder.setNote("¥" + Util.getNumberFormat(productBean.getMinPrice()));
                builder.setAlwaysSend(true);
            }
        }
        ConsultSource source = new ConsultSource(url, title, "商品详情");
        source.productDetail = builder.create();
        source.groupId = Constants.QIYU_BF_GROUP_ID;
        source.robotFirst = true;
        Unicorn.openServiceActivity(this, "D2C客服", source);

        //合力亿捷
//        String url = "http://m.d2cmall.com" + "/product/" + id;
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("{productId:");
//        stringBuilder.append(id);
//        stringBuilder.append("}");
//        Intent intent = new Intent(this,CustomServiceActivity.class);
//        intent.putExtra("businessParam",stringBuilder.toString());
//        intent.putExtra("skillGroupId",Constants.HLYJ_BF_AF_GROUP_ID);
//        startActivity(intent);
    }

    @Subscribe
    public void onEvent(ActionBean bean) {
        if (bean.type == Constants.ActionType.ADD_CART_SUCCESS) {
            if (collageId > 0) {
                return;
            }
            if (cartCount == null) {
                cartCount = (QBadgeView) new QBadgeView(this).bindTarget(bottomCartIv).setBadgeTextSize(8, true).setBadgeGravity(Gravity.END | Gravity.TOP).setGravityOffset(2, 0, true);
            }
            if (HomeActivity.count + 1 > 9) {
                cartCount.setBadgeText("9+");
            } else {
                cartCount.setBadgeNumber(HomeActivity.count + 1);
            }
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    long offer = endTime - System.currentTimeMillis();
                    if (offer <= 0) {
                        bottomCartTv.setTextColor(getResources().getColor(R.color.color_black60));
                        bottomCartTv.setText("购物车");
                    } else {
                        int allMouth = (int) (offer / 1000);
                        int minute = allMouth / 60;
                        int mouth = allMouth % 60;
                        bottomCartTv.setTextColor(getResources().getColor(R.color.color_red));
                        bottomCartTv.setText(addZero(minute) + ":" + addZero(mouth));
                        handler.sendEmptyMessageDelayed(1, 1000);
                    }
                }
            };
            endTime = System.currentTimeMillis() + 20 * 60 * 1000;
            D2CApplication.mSharePref.removeKey(SharePrefConstant.PRODUCT_ID);
            D2CApplication.mSharePref.putSharePrefString(SharePrefConstant.PRODUCT_ID, id + "," + endTime);
            handler.sendEmptyMessage(1);
            NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
            boolean isOpened = manager.areNotificationsEnabled();
            Boolean isMsgPushOpen = D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.IS_REMIND_OPEN_MSG, false);
            if (!isMsgPushOpen && !isOpened) {
                D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.IS_REMIND_OPEN_MSG, true);
                OpenMsgPushPop openMsgPushPop = new OpenMsgPushPop(ProductDetailActivity.this);
                openMsgPushPop.setContent(getString(R.string.label_pop_focus_brand));
                openMsgPushPop.show(getWindow().getDecorView());
            }
        } else if (bean.type == Constants.ActionType.CHANGE_PRODUCT_PAGE) {
            int position = (int) bean.get("position");
            if (bean.hasKey("second")) {
                ProductCommendFragment commendFragment = (ProductCommendFragment) productAdapter.getItem(2);
                commendFragment.setPage((int) bean.get("second"));
            }
            viewPager.setCurrentItem(position);
        } else if (bean.type == Constants.ActionType.STANDARDMORE) {
            long id = (long) bean.get("id");
            if (id == productBean.getId()) {
                joinShoppingCart(false, true, false);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventMainThread(PushBean pushBean) {
        if (pushBean != null && !Util.isEmpty(pushBean.getMessageContent().getUrl())) {
            Util.showPush(this, pushToast, pushBean);
        }
    }

    private String addZero(int num) {
        if (num < 9) {
            return "0" + num;
        } else {
            return num + "";
        }
    }

    private void initShoppingCart() {
        if (shoppingPop == null) {
            shoppingPop = new ShoppingPop(this);
            if (parentId > 0) {
                shoppingPop.setParentId(parentId);
            }
            shoppingPop.setCrowItemId(crowId);
            shoppingPop.setData(productBean);
            shoppingPop.setDismissListener(new TransparentPop.DismissListener() {
                @Override
                public void dismissStart() {
                }

                @Override
                public void dismissEnd() {
                    ActionBean actionBean = new ActionBean(Constants.ActionType.SELECTSTANDARD);
                    String selectColorStr = shoppingPop.getSelectColorStr();
                    String selectSizeStr = shoppingPop.getSelectSizeStr();
                    if (productBean.getColors().size() == 0 && !Util.isEmpty(selectSizeStr)) {
                        actionBean.put("standard", selectSizeStr);
                        EventBus.getDefault().post(actionBean);
                        return;
                    }
                    if (productBean.getSizes().size() == 0 && !Util.isEmpty(selectColorStr)) {
                        actionBean.put("standard", selectColorStr);
                        EventBus.getDefault().post(actionBean);
                        return;
                    }
                    if (!Util.isEmpty(selectSizeStr) && !Util.isEmpty(selectColorStr)) {
                        actionBean.put("standard", selectColorStr + " " + selectSizeStr);
                        EventBus.getDefault().post(actionBean);
                    } else {
                        if (!Util.isEmpty(selectSizeStr)) {
                            actionBean.put("standard", "选择 " + productBean.getColors().get(0).getName());
                            EventBus.getDefault().post(actionBean);
                        } else if (!Util.isEmpty(selectColorStr)) {
                            actionBean.put("standard", "选择 " + productBean.getSizes().get(0).getName());
                            EventBus.getDefault().post(actionBean);
                        }
                    }
                }
            });
        }
    }

    private void joinShoppingCart(boolean isBuy, boolean isSelectStandard, boolean isPack) {
        if (productBean == null) {
            return;
        }
        if (isBuy){
            stat("商品详情","立即购买",getMap("立即购买"));
        }else {
            stat("商品详情","加入购物车",getMap("加入购物车"));
        }
        if (shoppingPop == null) {
            shoppingPop = new ShoppingPop(this);
            if (parentId > 0) {
                shoppingPop.setParentId(parentId);
            }
            shoppingPop.setCrowItemId(crowId);
            shoppingPop.setData(productBean);
            shoppingPop.setDismissListener(new TransparentPop.DismissListener() {
                @Override
                public void dismissStart() {
                }

                @Override
                public void dismissEnd() {
                    ActionBean actionBean = new ActionBean(Constants.ActionType.SELECTSTANDARD);
                    String selectColorStr = shoppingPop.getSelectColorStr();
                    String selectSizeStr = shoppingPop.getSelectSizeStr();
                    if (productBean.getColors().size() == 0 && !Util.isEmpty(selectSizeStr)) {
                        actionBean.put("standard", selectSizeStr);
                        EventBus.getDefault().post(actionBean);
                        return;
                    }
                    if (productBean.getSizes().size() == 0 && !Util.isEmpty(selectColorStr)) {
                        actionBean.put("standard", selectColorStr);
                        EventBus.getDefault().post(actionBean);
                        return;
                    }
                    if (!Util.isEmpty(selectSizeStr) && !Util.isEmpty(selectColorStr)) {
                        actionBean.put("standard", selectColorStr + " " + selectSizeStr);
                        EventBus.getDefault().post(actionBean);
                    } else {
                        if (!Util.isEmpty(selectSizeStr)) {
                            actionBean.put("standard", "选择 " + productBean.getColors().get(0).getName());
                            EventBus.getDefault().post(actionBean);
                        } else if (!Util.isEmpty(selectColorStr)) {
                            actionBean.put("standard", "选择 " + productBean.getSizes().get(0).getName());
                            EventBus.getDefault().post(actionBean);
                        }
                    }
                }
            });
        }
        shoppingPop.isSecKill = isSecKill;
        boolean addCart;
        if (productBean.getProductStatus() == 5 || productBean.getIsCart() == 0) {
            addCart = false;
        } else {
            addCart = true;
        }
        shoppingPop.isSelectStandard(isSelectStandard, addCart);
        if (isPack) {
            shoppingPop.setActionType(5);
            shoppingPop.setPrice(productBean.getCollagePrice());
        } else if (isSecKill || isBuy) {
            shoppingPop.setActionType(4);
            shoppingPop.setPrice(productBean.getMinPrice());
        } else {
            shoppingPop.setActionType(1);
            shoppingPop.setPrice(productBean.getMinPrice());
        }
        if (productBean.getColors().size() > 1 || productBean.getSizes().size() > 1 || isSelectStandard) {
            shoppingPop.show(getWindow().getDecorView());
        } else {
            shoppingPop.shortCut();
        }
    }

    private void initCursor() {
        int screenX = ScreenUtil.getDisplayWidth();
        itemWidth = (int) ((screenX - ScreenUtil.dip2px(116)) * 1.0 / 4.0);
        RectShape shape = new RectShape();
        ShapeDrawable drawable = new ShapeDrawable(shape);
        drawable.getPaint().setColor(getResources().getColor(R.color.color_black_bg));
        drawable.getPaint().setStyle(Paint.Style.FILL);
        drawable.setIntrinsicWidth(itemWidth-ScreenUtil.dip2px(30));
        drawable.setIntrinsicHeight(ScreenUtil.dip2px(2));
        imgCursor.setImageDrawable(drawable);
        imgCursor.setPadding(ScreenUtil.dip2px(15),0,ScreenUtil.dip2px(15),0);
        changeTvColor();
    }

    private void changePage(int position) {
        String tabName = "";
        switch (position) {
            case 0:
                tabName = "商品";
                break;
            case 1:
                tabName = "详情";
                break;
            case 2:
                tabName = "晒单";
                break;
            case 3:
                tabName = "搭配";
                break;
        }
        stat("商品详情", tabName,getMap(tabName));
        if (position == 0) {
            secondTitleLl.setAlpha(lastAlpha);
            productTabLl.setAlpha(lastAlpha);
        } else {
            secondTitleLl.setAlpha(1);
            productTabLl.setAlpha(1);
        }
        if (secondTitleLl.getAlpha() < 0.1) {
            viewPager.setCanScroll(false);
        } else {
            viewPager.setCanScroll(true);
        }
        selectCursor(position);
        changeTvColor();
    }

    private void selectCursor(int index) {
        TranslateAnimation animation = new TranslateAnimation(currentIndex * itemWidth, index * itemWidth, 0, 0);
        currentIndex = index;
        animation.setFillAfter(true);
        animation.setDuration(200);
        imgCursor.startAnimation(animation);
    }

    private void changeTvColor() {
        switch (currentIndex) {
            case 0:
                productTv.setTextColor(getResources().getColor(R.color.color_black87));
                detailTv.setTextColor(getResources().getColor(R.color.color_black60));
                commentTv.setTextColor(getResources().getColor(R.color.color_black60));
                matchTv.setTextColor(getResources().getColor(R.color.color_black60));
                break;
            case 1:
                productTv.setTextColor(getResources().getColor(R.color.color_black60));
                detailTv.setTextColor(getResources().getColor(R.color.color_black87));
                commentTv.setTextColor(getResources().getColor(R.color.color_black60));
                matchTv.setTextColor(getResources().getColor(R.color.color_black60));
                break;
            case 2:
                productTv.setTextColor(getResources().getColor(R.color.color_black60));
                detailTv.setTextColor(getResources().getColor(R.color.color_black60));
                commentTv.setTextColor(getResources().getColor(R.color.color_black87));
                matchTv.setTextColor(getResources().getColor(R.color.color_black60));
                break;
            case 3:
                productTv.setTextColor(getResources().getColor(R.color.color_black60));
                detailTv.setTextColor(getResources().getColor(R.color.color_black60));
                commentTv.setTextColor(getResources().getColor(R.color.color_black60));
                matchTv.setTextColor(getResources().getColor(R.color.color_black87));
                break;
        }

    }

    public void changeAlpha(float alpha) {
        secondTitleLl.setAlpha(alpha);
        productTabLl.setAlpha(alpha);
        lastAlpha = alpha;
        if (lastAlpha < 0.1) {
            viewPager.setCanScroll(false);
        } else {
            viewPager.setCanScroll(true);
        }
    }

    public void changeBottom(boolean is) {
        if (is) {
            isFitting = true;
            bottomShopIv.setImageResource(R.mipmap.icon_shiyi);
            bottomShopTv.setText("试衣");
        } else {//变成收藏按钮
            isCollect = true;
            if (productBean != null && productBean.getCollectioned().equals("1")) {//已收藏
                bottomShopIv.setImageResource(R.mipmap.icon_sc_select);
                bottomShopTv.setText("取消收藏");
            } else {
                bottomShopIv.setImageResource(R.mipmap.icon_sc);
                bottomShopTv.setText("收藏");
            }
        }
    }

    private void collect(final View view) {
        view.setEnabled(false);
        final boolean is;
        ProductCollectApi api = new ProductCollectApi();
        api.productId = productBean.getId();
        if ("0".equals(productBean.getCollectioned())) {
            api.setInterPath(Constants.COLLECT_PRODUCT_URL);
            is = true;
        } else {
            api.setInterPath(Constants.CANCEL_COLLECT_URL);
            is = false;
        }
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                view.setEnabled(true);
                if (is) {
                    bottomShopIv.setImageResource(R.mipmap.icon_sc_select);
                    bottomShopTv.setText("已收藏");
                    productBean.setCollectioned("1");
                    Util.showToast(ProductDetailActivity.this, "收藏成功");
                    stat("商品详情","收藏",getMap("收藏"));
                } else {
                    bottomShopIv.setImageResource(R.mipmap.icon_sc);
                    bottomShopTv.setText("收藏");
                    productBean.setCollectioned("0");
                    Util.showToast(ProductDetailActivity.this, "取消收藏成功");
                }
                ((ProductFragment) productAdapter.getItem(0)).changeCollectState();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                view.setEnabled(true);
                Util.showToast(ProductDetailActivity.this, Util.checkErrorType(error));
            }
        });
    }

    public void changeCollectState() {
        if (isCollect) {
            if (productBean != null && productBean.getCollectioned().equals("1")) {//已收藏
                bottomShopIv.setImageResource(R.mipmap.icon_sc_select);
                bottomShopTv.setText("已收藏");
            } else {
                bottomShopIv.setImageResource(R.mipmap.icon_sc);
                bottomShopTv.setText("收藏");
            }
        }
    }

    public void checkStatus(int productStatus) {
        bottomLl.setVisibility(View.VISIBLE);
        if (productBean.getMark() == 0) { //商品下架
            isSecKill = false;
            bottomBuy.setVisibility(View.GONE);
            bottomAddCart.getLayoutParams().width = ScreenUtil.dip2px(160);
            bottomAddCart.setText("已下架");
            bottomAddCart.setEnabled(false);
            bottomAddCart.setBackgroundColor(getResources().getColor(R.color.gray_color));
            productSaleLl.setVisibility(View.VISIBLE);
            tv.setText(getString(R.string.msg_sold_out));
            loadSampleProduct();
        } else { //mark=1
            if (collageId > 0) {
                bottomShopLl.setVisibility(View.GONE);
                bottomCartIv.setImageResource(R.mipmap.icon_shop);
                bottomCartTv.setText("拼团商城");
                bottomAddCart.getLayoutParams().width = ScreenUtil.dip2px(112);
                bottomBuy.getLayoutParams().width = ScreenUtil.dip2px(112);
                bottomAddCart.setBackgroundColor(Color.parseColor("#262626"));
                bottomBuy.setBackgroundColor(Color.parseColor("#f21a1a"));
                bottomBuy.setTextColor(Color.WHITE);
                bottomAddCart.setText("¥" + Util.getNumberFormat(productBean.getMinPrice()) + "\n" + "单独购买");
                bottomAddCart.setTextColor(Color.WHITE);
                //显示拼团价
                if (productBean.getPromotionStatus() == 0) {
                    bottomBuy.setText("提醒我");
                } else {
                    bottomBuy.setText("¥" + Util.getNumberFormat(productBean.getCollagePrice()) + "\n" + (int) productBean.getCollageNum() + "人团");
                }
                if (productBean.getStore() <= 0) {
                    packRl.setVisibility(View.VISIBLE);
                    packTv.setText(getResources().getString(R.string.label_collage_no_store));
                    bottomAddCart.setBackgroundColor(Color.parseColor("#B2B2B2"));
                    bottomAddCart.setEnabled(false);
                    bottomBuy.setBackgroundColor(Color.parseColor("#c8c8c8"));
                    bottomBuy.setEnabled(false);
                } else if (productBean.getCollageStore() <= 0) {
                    packRl.setVisibility(View.VISIBLE);
                    if (productBean.isHasGroup()) {
                        packTv.setText(getResources().getString(R.string.label_collage_no_store1));
                    } else {
                        packTv.setText(getResources().getString(R.string.label_collage_no_store));
                    }
                    bottomBuy.setBackgroundColor(Color.parseColor("#c8c8c8"));
                    bottomBuy.setEnabled(false);
                }
            } else {
                if (productBean.getStore() <= 0 || productStatus == 3) { //商品售罄或者是到货通知
                    isSecKill = false;
                    isCod = true;
                    bottomBuy.setVisibility(View.GONE);
                    bottomAddCart.getLayoutParams().width = ScreenUtil.dip2px(160);
                    bottomAddCart.setText("到货通知");
                    bottomAddCart.setTextColor(Color.WHITE);
                    bottomAddCart.setEnabled(true);
                    bottomAddCart.setBackgroundColor(getResources().getColor(R.color.color_black_bg));
                    productSaleLl.setVisibility(View.VISIBLE);
                    tv.setText(getString(R.string.msg_sell_out));
                    loadSampleProduct();
                } else {
                    if (productStatus == 5 || productBean.getIsCart() == 0) { //秒杀商品或不能加购物车
                        if (productStatus == 5) {
                            isSecKill = true;
                        }
                        productSaleLl.setVisibility(View.GONE);
                        bottomAddCart.setVisibility(View.GONE);
                        bottomBuy.getLayoutParams().width = ScreenUtil.dip2px(160);
                    }
                }
            }
        }
    }

    public void openSimple() {
        if (recycleView.getVisibility() == View.VISIBLE) {
            recycleView.setVisibility(View.GONE);
            transView.setVisibility(View.GONE);
            handleIv.setImageResource(R.mipmap.ic_arrow_up);
        }
    }

    private void loadSampleProduct() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.GET_PRODUCT_MATCH_LIST, id, 6));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CartRecommendBean>() {
            @Override
            public void onResponse(CartRecommendBean response) {
                if (response.getList() != null && response.getList().size() > 0) {
                    ProductSimpleAdapter adapter = new ProductSimpleAdapter(ProductDetailActivity.this, response.getList());
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ProductDetailActivity.this);
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    recycleView.setLayoutManager(layoutManager);
                    recycleView.setAdapter(adapter);
                    recycleView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            height = recycleView.getHeight();
                            if (height > 0) {
                                recycleView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (shoppingPop != null && shoppingPop.isShow()) {
                shoppingPop.dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setProduct(ProductDetailBean.DataBean.ProductBean product, int id, long crowId) {
        this.productBean = product;
        checkStatus(product.getProductStatus());
        this.brandId = id;
        this.crowId = crowId;
        if (productBean.getPromotionStatus() == -1) {
            packRl.setVisibility(View.VISIBLE);
            packTv.setText(getResources().getString(R.string.label_collage_end));
            bottomBuy.setBackgroundColor(Color.parseColor("#c8c8c8"));
            bottomBuy.setEnabled(false);
        }
        if (productBean.getColors().size() <= 1 && productBean.getSizes().size() <= 1) {
            initShoppingCart();
        }
    }

    public String getProductSn(){
        if (productBean!=null){
            return productBean.getSn();
        }
        return "";
    }

    @Override
    protected void onResume() {
        String cartInfo = D2CApplication.mSharePref.getSharePrefString(SharePrefConstant.PRODUCT_ID, "");
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        if (collageId == 0) {
            if (!Util.isEmpty(cartInfo)) {
                String[] info = cartInfo.split(",");
                final long endTime = Long.valueOf(info[1]);
                if (Session.getInstance().getUserFromFile(this) != null && endTime > System.currentTimeMillis()) {
                    handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            long offer = endTime - System.currentTimeMillis();
                            if (offer <= 0) {
                                bottomCartTv.setTextColor(getResources().getColor(R.color.color_black60));
                                bottomCartTv.setText("购物车");
                            } else {
                                int allMouth = (int) (offer / 1000);
                                int minute = allMouth / 60;
                                int mouth = allMouth % 60;
                                bottomCartTv.setTextColor(getResources().getColor(R.color.color_red));
                                bottomCartTv.setVisibility(View.VISIBLE);
                                bottomCartTv.setText(addZero(minute) + ":" + addZero(mouth));
                                handler.sendEmptyMessageDelayed(1, 1000);
                            }
                        }
                    };
                    handler.sendEmptyMessage(1);
                }
            } else {
                bottomCartTv.setTextColor(getResources().getColor(R.color.color_black60));
                bottomCartTv.setText("购物车");
            }
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

    @Override
    public void onWbShareSuccess() {
        super.onWbShareSuccess();
        if (sharePop != null) {
            sharePop.shareOut();
        }
    }
}
