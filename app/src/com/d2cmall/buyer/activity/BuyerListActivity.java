package com.d2cmall.buyer.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.BuyerListAdapter;
import com.d2cmall.buyer.api.BuyerListApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.BuyerListBean;
import com.d2cmall.buyer.bean.PartnerMemberBean;
import com.d2cmall.buyer.bean.SortWayBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.OnItemClickListener;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.SortPop;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2018/4/14.
 */

public class BuyerListActivity extends BaseActivity {


    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;
    @Bind(R.id.tv_sort)
    TextView tvSort;
    @Bind(R.id.iv_sort)
    ImageView ivSort;
    @Bind(R.id.ll_screen_sort)
    LinearLayout llScreenSort;
    @Bind(R.id.tv_level)
    TextView tvLevel;
    @Bind(R.id.iv_level)
    ImageView ivLevel;
    @Bind(R.id.ll_screen_level)
    LinearLayout llScreenLevel;
    @Bind(R.id.tv_condition)
    TextView tvCondition;
    @Bind(R.id.iv_condition)
    ImageView ivCondition;
    @Bind(R.id.ll_screen_condition)
    LinearLayout llScreenCondition;
    @Bind(R.id.ll_screen)
    LinearLayout llScreen;
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.iv_error)
    ImageView ivError;
    @Bind(R.id.ll_empty)
    LinearLayout ll_empty;
    @Bind(R.id.rl_content)
    RelativeLayout rl_content;
    private SortPop sortPopLeft, sortPopMiddle, sortPopRight;
    private VirtualLayoutManager virtualLayoutManager;
    private DelegateAdapter delegateAdapter;
    private List<BuyerListBean.ListBean> list;
    private BuyerListAdapter adapter;
    private int currentPage = 1;
    private boolean hasNext = false;
    private PartnerMemberBean.DataBean.PartnerBean partnerBean;
    private String sort = "createDate|desc";
    private int level = -1;
    private int status = 10;
    private int consume = 1;
    private long partnerId = -1;
    private boolean isJump;
    private boolean isAm;

/*           'createDate|desc':'按加入时间',
             'payCount|desc':'按订单数',
             'payAmount|desc':'按销售额',
             'inviteNum|desc':'按邀请人数',
             'upperRebates|desc':'按收益贡献',
             'consumeDate|desc':'按最后销售时间'*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_list);
        ButterKnife.bind(this);
        showToast("数据每两小时更新一次哦");
        virtualLayoutManager = new VirtualLayoutManager(this);
        partnerBean = Session.getInstance().getPartnerFromFile(this);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager);
        list = new ArrayList<>();
        level = getIntent().getIntExtra("level", -1);
        partnerId = getIntent().getLongExtra("partnerId", -1);
        isJump = getIntent().getBooleanExtra("isJump", true);
        isAm = getIntent().getBooleanExtra("isAm", false);
        adapter = new BuyerListAdapter(this, list);
        adapter.setIsJump(isJump);
        delegateAdapter.addAdapter(adapter);
        recycleView.setLayoutManager(virtualLayoutManager);
        recycleView.setAdapter(delegateAdapter);
        recycleView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int last = virtualLayoutManager.findLastVisibleItemPosition();
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        if (last > delegateAdapter.getItemCount() - 3 && hasNext) {
                            currentPage++;
                            loadBuyerData();
                        }
                }
            }
        });
        initLeftPop();
        initMiddlePop();
        initRightPop();
        String sortData = getIntent().getStringExtra("sortData");
        if (!Util.isEmpty(sortData)) {
            sort = sortData;
            switch (sortData) {
                case "payAmount|desc":
                    sortPopLeft.fakeClick(1);
                    break;
                case "inviteNum|desc":
                    sortPopLeft.fakeClick(5);
                    break;
                case "upperRebates|desc":
                    sortPopLeft.fakeClick(3);
                    break;
            }
        }
        if (level > 0) {
            if (level == 1) {
                sortPopMiddle.fakeClick(2);
            } else if (level == 2) {
                sortPopMiddle.fakeClick(1);
            }
        }
        loadBuyerData();
    }

    private void initRightPop() {

        sortPopRight = new SortPop(this, ScreenUtil.dip2px(127));
        sortPopRight.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ivCondition.setImageResource(R.mipmap.icon_jiantou_xiala);
                tvCondition.setTextColor(Color.parseColor("#666666"));
            }
        });
        final List<SortWayBean> list = new ArrayList<>();
        String[] titles = getResources().getStringArray(R.array.label_partner_sort_is_order);
        for (int i = 0; i < titles.length; i++) {
            SortWayBean bean = new SortWayBean();
            bean.setName(titles[i]);
            if (i == 0) {
                bean.setChoose(true);
            } else {
                bean.setChoose(false);
            }
            list.add(bean);
        }
        sortPopRight.setData(list);
        sortPopRight.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void itemClick(View v, int position) {
                switch (position) {
                    case 0:
                        consume = 1;
                        status = 10;
                        break;
                    case 1:
                        status = 0;
                        consume = 1;
                        break;
                    case 2:
                        status = 10;
                        consume = 0;
                        break;
                }
                tvCondition.setText(list.get(position).getName());
                currentPage = 1;
                loadBuyerData();
            }
        });
    }

    private void initMiddlePop() {
        sortPopMiddle = new SortPop(this, ScreenUtil.dip2px(127));
        sortPopMiddle.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ivLevel.setImageResource(R.mipmap.icon_jiantou_xiala);
                tvLevel.setTextColor(Color.parseColor("#666666"));
            }
        });
        final List<SortWayBean> list = new ArrayList<>();
        String[] titles = getResources().getStringArray(R.array.label_partner_sort_level);
        for (int i = 0; i < titles.length; i++) {
            SortWayBean bean = new SortWayBean();
            bean.setName(titles[i]);
            if (i == 0) {
                bean.setChoose(true);
            } else {
                bean.setChoose(false);
            }
            list.add(bean);
        }
        sortPopMiddle.setData(list);
        sortPopMiddle.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void itemClick(View v, int position) {
                switch (position) {
                    case 0:
                        level = -1;
                        break;
                    case 1:
                        level = 2;
                        break;
                    case 2:
                        level = 1;
                        break;
                }
                tvLevel.setText(list.get(position).getName());
                currentPage = 1;
                loadBuyerData();
            }
        });
    }

    private void initLeftPop() {
        sortPopLeft = new SortPop(this, ScreenUtil.dip2px(127));
        sortPopLeft.setOutsideTouchable(true);
        final List<SortWayBean> list = new ArrayList<>();
        sortPopLeft.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ivSort.setImageResource(R.mipmap.icon_jiantou_xiala);
                tvSort.setTextColor(Color.parseColor("#666666"));
            }
        });
        String[] titles = getResources().getStringArray(R.array.label_partner_sort_way);
        for (int i = 0; i < titles.length; i++) {
            SortWayBean bean = new SortWayBean();
            bean.setName(titles[i]);
            if (i == 0) {
                bean.setChoose(true);
            } else {
                bean.setChoose(false);
            }
            list.add(bean);
        }
        sortPopLeft.setData(list);
        sortPopLeft.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void itemClick(View v, int position) {
                switch (position) {
                    case 0:
                        sort = "createDate|desc";
                        break;
                    case 1:
                        sort = "payAmount|desc";
                        break;
                    case 2:
                        sort = "payCount|desc";
                        break;
                    case 3:
                        sort = "upperRebates|desc";
                        break;
                    case 4:
                        sort = "consumeDate|desc";
                        break;
                    case 5:
                        sort = "inviteNum|desc";
                        break;
                }
                tvSort.setText(list.get(position).getName());
                currentPage = 1;
                loadBuyerData();
            }
        });
    }

    private void loadBuyerData() {
        BuyerListApi api = new BuyerListApi();
        api.p = currentPage;
        api.sort = sort;
        if (partnerBean.getLevel() == 0) {//AM
            api.masterId = (long) partnerBean.getId();
        } else {
            api.parentId = (long) partnerBean.getId();
        }

        if (partnerBean.getLevel() == 0) {
            if (!Util.isEmpty(partnerBean.getMasterId())) {
                api.parentId = Long.valueOf(partnerBean.getMasterId());
            }
        }

        if (partnerId > 0) {//从客户中心点过来的
            if (isAm) {
                api.masterId = partnerId;
            } else {
                api.parentId = partnerId;
            }
        }

        if (status == 0) {
            api.status = 0;//试用期
        }
        if (consume == 0) {
            api.consume = 0;//未开单
        }
        if (level > 0) {
            api.level = level;//等级
        }
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BuyerListBean>() {
            @Override
            public void onResponse(BuyerListBean response) {
                if (currentPage == 1) {
                    list.clear();
                }
                list.addAll(response.getList());
                hasNext = response.isNext();
                adapter.notifyDataSetChanged();
                if (list.size() > 0) {
                    rl_content.setBackgroundColor(getResources().getColor(R.color.bg_color));
                    recycleView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    ll_empty.setVisibility(View.GONE);
                } else if (list.size() == 0) {
                    rl_content.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    recycleView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    ll_empty.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(BuyerListActivity.this, Util.checkErrorType(error));
                if (list.size() == 0) {
                    rl_content.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    recycleView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    ll_empty.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.ll_screen_sort, R.id.ll_screen_level, R.id.ll_screen_condition})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_screen_sort:
                if (sortPopLeft == null) {
                    initLeftPop();
                }
                tvSort.setTextColor(Color.parseColor("#f5a323"));
                ivSort.setImageResource(R.mipmap.icon_jiantou_shouqi);
                sortPopLeft.showAsDropDown(llScreenSort, 0, ScreenUtil.dip2px(1));
                break;
            case R.id.ll_screen_level:
                if (sortPopMiddle == null) {
                    initMiddlePop();
                }
                tvLevel.setTextColor(Color.parseColor("#f5a323"));
                ivLevel.setImageResource(R.mipmap.icon_jiantou_shouqi);
                sortPopMiddle.showAsDropDown(llScreenSort, 0, ScreenUtil.dip2px(1));
                break;
            case R.id.ll_screen_condition:
                if (sortPopRight == null) {
                    initRightPop();
                }
                tvCondition.setTextColor(Color.parseColor("#f5a323"));
                ivCondition.setImageResource(R.mipmap.icon_jiantou_shouqi);
                sortPopRight.showAsDropDown(llScreenSort, 0, ScreenUtil.dip2px(1));
                break;
        }
    }

    private void showToast(String text) {
        //LayoutInflater的作用：对于一个没有被载入或者想要动态载入的界面，都需要LayoutInflater.inflate()来载入，LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化
        LayoutInflater inflater = getLayoutInflater();//调用Activity的getLayoutInflater()
        View view = inflater.inflate(R.layout.layout_toast, null); //加載layout下的布局
        TextView title = (TextView) view.findViewById(R.id.textView);
        title.setText(text); //toast的标题
        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_SHORT);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
        toast.setView(view); //添加视图文件
        toast.show();
    }
}
