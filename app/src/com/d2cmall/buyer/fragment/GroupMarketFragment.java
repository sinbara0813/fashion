package com.d2cmall.buyer.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.d2cmall.buyer.activity.LoginActivity;
import com.d2cmall.buyer.adapter.GroupMarketAdapter;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.GroupListBean;
import com.d2cmall.buyer.bean.Poster;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.FlashNoticePop;
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

/**
 * Created by rookie on 2018/6/21.
 */

public class GroupMarketFragment extends Fragment {

    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout ptr;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.iv_error)
    ImageView ivError;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_share)
    ImageView ivShare;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;
    @Bind(R.id.rl_all)
    RelativeLayout rlAll;
    private Context mContext;
    private LinearLayoutManager linearLayoutManager;
    private GroupMarketAdapter groupMarketAdapter;//适配器
    private List<GroupListBean.DataBean.CollageListBean.ListBean> list;
    private int page = 1;
    private boolean hasNext;
    private Poster poster;
    private String imgUrl, miniPath = null;
    private byte[] miniData;

    @Override
    public void onAttach(Context context) {
        this.mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_group_market, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recycleView.setVisibility(View.GONE);
        ivError.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        list = new ArrayList<>();
        ptr.setHeadLabel(getString(R.string.label_d2c_go));
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refresh();
            }
        });

        groupMarketAdapter = new GroupMarketAdapter(mContext, list);
        groupMarketAdapter.setGroupBuyCallBack(new GroupMarketAdapter.GroupBuyCallBack() {
            @Override
            public void notice(int id) {
                remindMe(id);
            }
        });
        linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(groupMarketAdapter);
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int last = linearLayoutManager.findLastVisibleItemPosition();
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        if (last > groupMarketAdapter.getItemCount() - 3 && hasNext) {
                            page++;
                            loadData();
                        }
                }
            }
        });
        loadData();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    private void remindMe(int id) {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.GROUP_BUY_PRODUCT_REMIND, id));
        api.setMethod(BaseApi.Method.POST);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                FlashNoticePop noticePop = new FlashNoticePop(mContext);
                noticePop.show(getActivity().getWindow().getDecorView());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof AuthFailureError) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Util.showToast(mContext, Util.checkErrorType(error));
                }
            }
        });
    }

    private void refresh() {
        page = 1;
        loadData();
    }

    private void loadData() {
        SimpleApi api = new SimpleApi();
        api.setP(page);
        api.setPageSize(20);
        api.setMethod(BaseApi.Method.GET);
        api.setInterPath(Constants.GROUP_BUY_PRODUCT_LIST);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<GroupListBean>() {
            @Override
            public void onResponse(GroupListBean groupListBean) {
                if (ptr != null) {
                    ptr.refreshComplete();
                } else {
                    return;
                }
                hasNext = groupListBean.getData().getCollageList().isNext();
                if (page == 1) {
                    list.clear();
                }
                list.addAll(groupListBean.getData().getCollageList().getList());
                progressBar.setVisibility(View.GONE);
                if (list.size() == 0) {//没有数据
                    rlAll.setBackgroundColor(getResources().getColor(R.color.color_white));
                    recycleView.setVisibility(View.GONE);
                    ivError.setVisibility(View.VISIBLE);
                    ivError.setImageResource(R.mipmap.ic_empty_work);
                } else {
                    rlAll.setBackgroundColor(Color.parseColor("#f0f0f0"));
                    recycleView.setVisibility(View.VISIBLE);
                    ivError.setVisibility(View.GONE);
                }
                groupMarketAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (ptr != null) {
                    ptr.refreshComplete();
                } else {
                    return;
                }
                if (list.size() == 0) {
                    rlAll.setBackgroundColor(getResources().getColor(R.color.color_white));
                    recycleView.setVisibility(View.GONE);
                    ivError.setVisibility(View.VISIBLE);
                    ivError.setImageResource(R.mipmap.ic_no_net);
                }
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        mContext = null;
        super.onDestroy();
    }

    private void share() {
        if (Util.loginChecked(getActivity(), 999)) {
            initPoster();
            SharePop sharePop = new SharePop(mContext);
            sharePop.setPromotionLink(true, true);
            sharePop.setPoster(poster);
            sharePop.setBigImageUrl("shabi.png");
            sharePop.setDescription("D2C拼团-尖货好物一起拼!惊喜低价更省钱");
            sharePop.setMiniProjectPath(miniPath);
            sharePop.setMiniWebUrl("/collage/list");
            sharePop.setMiniImage(null,true,R.drawable.pic_group_mini_project);
            sharePop.show(getActivity().getWindow().getDecorView());
        }
    }

    private void initPoster() {
        poster = new Poster();
        UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(mContext);
        if (user.getPartnerId() > 0) {
            String param=Util.toURLEncode("parent_id=" + user.getPartnerId());
            imgUrl = "https://appserver.d2cmall.com/v3/api/weixin/code?appId=wx58eb0484ce91f38f&content="+param+"&page=pages/groupon/list";
            miniPath = "pages/groupon/list?parent_id=" + user.getPartnerId();
        } else {
            imgUrl = "https://appserver.d2cmall.com/v3/api/weixin/code?appId=wx58eb0484ce91f38f&&content=parent_id=&page=pages/groupon/list";
            miniPath = "pages/groupon/list";
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_group_wx_circle, new RelativeLayout(mContext), false);
        final ImageView imageView = (ImageView) view.findViewById(R.id.iv_mini_project);
        final ImageView ivBig = (ImageView) view.findViewById(R.id.iv_big);
        Glide.with(this).load("http://static.d2c.cn/img/other/ban-collage.jpg").asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                poster.productMap = true;
                ivBig.setImageBitmap(resource);
            }
        });
        Glide.with(this).load(Util.getD2cPicUrl(imgUrl)).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                poster.baseMap = true;
                imageView.setImageBitmap(resource);
            }
        });
        poster.posterView = view;
    }

    @OnClick({R.id.iv_back, R.id.iv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                getActivity().finish();
                break;
            case R.id.iv_share:
                share();
                break;
        }
    }
}
