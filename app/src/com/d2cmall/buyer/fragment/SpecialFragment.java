package com.d2cmall.buyer.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.CartActivity;
import com.d2cmall.buyer.activity.HomeActivity;
import com.d2cmall.buyer.activity.SearchTopicBrandActivity;
import com.d2cmall.buyer.adapter.TopicRecyclerViewAdapter;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.api.ThemeApi;
import com.d2cmall.buyer.base.BaseTabFragment;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.ThemeBean;
import com.d2cmall.buyer.bean.ThemeTagBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.OnItemClickListener;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.SelectSpecialPop;
import com.d2cmall.buyer.widget.TransparentPop;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import q.rorbin.badgeview.QBadgeView;

import static com.d2cmall.buyer.Constants.TOPIC_TAG_LIST;

/**
 * Created by Administrator on 2017/5/10.
 */

public class SpecialFragment extends BaseTabFragment {

    @Bind(R.id.select_special)
    TextView selectSpecial;
    @Bind(R.id.select_tag)
    ImageView selectTag;
    @Bind(R.id.special_search)
    ImageView specialSearch;
    @Bind(R.id.special_cart)
    ImageView specialCart;
    @Bind(R.id.title_layout)
    LinearLayout titleLayout;
    SelectSpecialPop pop;
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    private VirtualLayoutManager virtualLayoutManager;
    private DelegateAdapter delegateAdapter;
    private TopicRecyclerViewAdapter topicAdapter;
    private List<ThemeBean.DataBean.ThemesBean.ListBean> list = new ArrayList<>();
    List<ThemeTagBean.DataBean.CountTagsBean> tagList = new ArrayList<>();
    int page = 1;
    int id = 0;
    private int lastPosition = -1;
    private int lastOffer;
    private boolean hasSetAdapter;
    private boolean hasNext;
    private String name = "全部";
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void itemClick(View v, int position) {
            if (position > 0) {
                id = tagList.get(position).getId();
                name = tagList.get(position).getName();
            } else {
                id = 0;
                name = "全部";
            }
            selectSpecial.setText(tagList.get(position).getName());
            topicAdapter.setName(name);
            loadData();
            pop.dismiss();
        }
    };
    private QBadgeView cartNum;

    private void stat(String event, String label) {
        StatService.onEvent(mContext, event, label);
        TCAgent.onEvent(mContext, event, label);
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_special, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void doBusiness() {
        setCartNum();
        initRecyclerView();
    }

    private void initRecyclerView() {
        virtualLayoutManager = new VirtualLayoutManager(mContext);
        recycleView.setLayoutManager(virtualLayoutManager);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);
        recycleView.setAdapter(delegateAdapter);
        LinkedList<DelegateAdapter.Adapter> adapters = new LinkedList<>();
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setGap(ScreenUtil.dip2px(8));
        gridLayoutHelper.setMarginTop(ScreenUtil.dip2px(8));
        gridLayoutHelper.setAutoExpand(false);
        float[] weights = new float[]{50.0f, 50.0f};
        gridLayoutHelper.setWeights(weights);
        //gridLayoutHelper.setAspectRatio(0.6f);
        topicAdapter = new TopicRecyclerViewAdapter(mContext, gridLayoutHelper, list);
        topicAdapter.setName(name);
        adapters.add(topicAdapter);
        delegateAdapter.addAdapters(adapters);
        if (lastPosition >= 0) {
            virtualLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
        }
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                getLastLocation();
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = virtualLayoutManager.findLastVisibleItemPosition();
                        if (last > topicAdapter.getItemCount() - 3 && hasNext) {
                            page++;
                            loadData();
                        }
                }
            }
        });
        hasSetAdapter = true;
        loadData();
    }

    private void loadTagData() {
        SimpleApi api = new SimpleApi();
        api.setMethod(BaseApi.Method.GET);
        api.setInterPath(TOPIC_TAG_LIST);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ThemeTagBean>() {
            @Override
            public void onResponse(ThemeTagBean themeTagBean) {
                tagList.clear();
                tagList.addAll(themeTagBean.getData().getCountTags());
                if (pop != null && pop.isShow()) {
                    selectTag.clearAnimation();
                    selectTag.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.arrow_down));
                    pop.dismiss();
                    return;
                }
                selectTag.clearAnimation();
                selectTag.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.arrow_up));
                pop = new SelectSpecialPop(mContext, onItemClickListener, tagList, new TransparentPop.DismissListener() {
                    @Override
                    public void dismissStart() {
                        selectTag.clearAnimation();
                        selectTag.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.arrow_down));
                    }

                    @Override
                    public void dismissEnd() {

                    }
                });
                pop.show(titleLayout);
//                pop = new SelectSpecialPop(getActivity(), onItemClickListener, tagList,new TransparentPop.DismissListener() {
//                    @Override
//                    public void dismissStart() {
//                        selectTag.clearAnimation();
//                        selectTag.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.arrow_down));
//                    }
//
//                    @Override
//                    public void dismissEnd() {
//
//                    }
//                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }

    private void getLastLocation() {
        View topView = virtualLayoutManager.getChildAt(0);
        if (topView != null) {
            //获取与该view的顶部的偏移量
            lastOffer = topView.getTop();
            //得到该View的数组位置
            lastPosition = virtualLayoutManager.getPosition(topView);
        }
    }


    private void loadData() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        recycleView.setVisibility(View.GONE);
        ThemeApi api = new ThemeApi();
        api.setInterPath(Constants.TOPIC_LIST_URL);
        api.setPageNumber(page);
        if (id > 0) {
            api.setTagId(id);
        }
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ThemeBean>() {
            @Override
            public void onResponse(ThemeBean response) {
                if (page == 1) {
                    list.clear();
                }
                list.addAll(response.getData().getThemes().getList());
                hasNext = response.getData().getThemes().isNext();
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                    recycleView.setVisibility(View.VISIBLE);
                }
                topicAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                    recycleView.setVisibility(View.VISIBLE);
                }
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }

    @OnClick({R.id.select_special, R.id.select_tag, R.id.special_search, R.id.special_cart})
    public void specialClick(View view) {
        if (!isVisibleToUser) {
            return;
        }
        switch (view.getId()) {
            case R.id.select_special:
            case R.id.select_tag:
                loadTagData();
                break;
            case R.id.special_search:
                stat("V3搜索", "专题搜索");
                Intent intent2 = new Intent(mContext, SearchTopicBrandActivity.class);
                intent2.putExtra("type", "theme");
                startActivity(intent2);
                break;
            case R.id.special_cart:
                if (Util.loginChecked(getActivity(), 100)) {
                    stat("V3购物车入口", "专题购物车");
                    Intent intent = new Intent(mContext, CartActivity.class);
                    getActivity().startActivity(intent);
                }
                break;
        }

    }

    @Subscribe(priority = 100)
    public void onEvent(ActionBean bean) {
        if (bean.type == Constants.ActionType.KEY_BACK) {
            if (pop != null && pop.isShow()) {
                pop.dismiss();
                EventBus.getDefault().cancelEventDelivery(bean);
            }
        } else if (bean.type == Constants.ActionType.REFRESH_CART_COUNT) {
            setCartNum();
        } else if (bean.type == Constants.ActionType.SPECAIL_UP) {
            virtualLayoutManager.scrollToPositionWithOffset(0, 0);
        }
    }

    private void setCartNum() {
        if (HomeActivity.count>0){
            if (cartNum==null){
                cartNum = (QBadgeView) new QBadgeView(getActivity()).bindTarget(specialCart).setBadgeTextSize(10, true).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeBackgroundColor(Color.parseColor("#fff23365")).setGravityOffset(2, 2, true);
                specialCart.setId(R.id.special_cart);
            }
            if (HomeActivity.count > 9) {
                cartNum.setBadgeText("9+");
            } else {
                cartNum.setBadgeNumber(HomeActivity.count);
            }
        }else {
            if (cartNum !=null){
                ViewParent parent= cartNum.getParent();
                if (parent!=null){
                    ((ViewGroup) parent).removeView(cartNum);
                    cartNum =null;
                }
            }
        }
    }

    @Override
    public void hide() {
        if (topicAdapter != null && delegateAdapter != null) {
            delegateAdapter.removeAdapter(topicAdapter);
            hasSetAdapter = false;
        }
        if (pop != null && pop.isShow()) {
            pop.dismiss();
        }
        super.hide();
    }

    @Override
    public void show() {
        if (!hasSetAdapter && delegateAdapter != null && topicAdapter != null) {
            delegateAdapter.addAdapter(topicAdapter);
            if (lastPosition >= 0) {
                virtualLayoutManager.scrollToPositionWithOffset(lastPosition, lastOffer);
            }
            hasSetAdapter = true;
        }
//        pop = null;
//        tagList.clear();
//        loadTagData();
        super.show();
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        virtualLayoutManager=null;
        delegateAdapter=null;
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
