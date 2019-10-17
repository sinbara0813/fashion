package com.d2cmall.buyer.activity;

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
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.SchoolThemeAdapter;
import com.d2cmall.buyer.api.SaleSchoolApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.PartnerMemberBean;
import com.d2cmall.buyer.bean.SaleSchoolListBean;
import com.d2cmall.buyer.bean.SaleSchoolTagsBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.binder.ScrollEndBinder;
import com.d2cmall.buyer.holder.DefaultHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class SaleSchoolActivity extends BaseActivity {

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
    @Bind(R.id.img_hint)
    ImageView imgHint;
    @Bind(R.id.btn_reload)
    TextView btnReload;
    @Bind(R.id.empty_hint_layout)
    LinearLayout emptyHintLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private SchoolThemeAdapter themeAdapter;
    private int currentPage = 1;
    private boolean hasNext = true;
    private ArrayList<SaleSchoolListBean.DataBean.ThemesBean.ListBean> listBeen = new ArrayList<>();
    private VirtualLayoutManager mLayoutManager;
    private DelegateAdapter delegateAdapter;
    private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志
    private int id;
    private String title;
    private PartnerMemberBean.DataBean.PartnerBean partnerBean;
    private List<SaleSchoolTagsBean.DataBean.ThemeTagsBean> themeTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_school);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra("id", 0);
        title = getIntent().getStringExtra("title");
        partnerBean = Session.getInstance().getPartnerFromFile(this);
        themeTags=new ArrayList<>();
        init();
        initListener();
        loadSaleSchool();
    }


    private void init() {
        nameTv.setText("商学院");
        mLayoutManager = new VirtualLayoutManager(SaleSchoolActivity.this);
        themeAdapter = new SchoolThemeAdapter(this, listBeen);
        recycleView.setLayoutManager(mLayoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recycleView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        delegateAdapter = new DelegateAdapter(mLayoutManager, true);
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(0, 2);
        recycleView.setRecycledViewPool(recycledViewPool);
        recycleView.setAdapter(delegateAdapter);
        delegateAdapter.addAdapter(themeAdapter);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void loadSaleSchool() {
        progressBar.setVisibility(View.VISIBLE);
        SaleSchoolApi saleSchoolApi = new SaleSchoolApi();
        saleSchoolApi.setType("WECHAT");
        D2CApplication.httpClient.loadingRequest(saleSchoolApi, new BeanRequest.SuccessListener<SaleSchoolTagsBean>() {
            @Override
            public void onResponse(SaleSchoolTagsBean saleSchoolTagsBean) {
                progressBar.setVisibility(View.GONE);
                if (themeTags.size() > 0) {
                    themeTags.clear();
                }
                themeTags.addAll(saleSchoolTagsBean.getData().getThemeTags());
                if(themeAdapter!=null){
                    themeAdapter.setTags(themeTags);
                    if (themeTags.size() > 0) {
                        themeAdapter.setTitle(themeTags.get(0).getName());
                    }
                    themeAdapter.notifyDataSetChanged();
                }
                loadData();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
            }
        });
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
                        if (last > themeAdapter.getItemCount() - 3 && hasNext) {
                            currentPage++;
                            loadData();
                        }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastVisPosition = mLayoutManager.findLastVisibleItemPosition();
                int itemCount = mLayoutManager.getItemCount();
                if (lastVisPosition >= itemCount - 3 && !hasNext && currentPage > 1) {
                    if (endAdapter == null) {
                        ScrollEndBinder endBinder = new ScrollEndBinder();
                        endAdapter = new BaseVirtualAdapter<>(endBinder, 100);
                        delegateAdapter.addAdapter(endAdapter);
                    }
                } else {
                    if (endAdapter != null) {
                        delegateAdapter.removeAdapter(endAdapter);
                        endAdapter = null;
                    }
                }
            }
        });
    }

    private void loadData() {
        SimpleApi simpleApi = new SimpleApi();
        simpleApi.setP(currentPage);
        simpleApi.setPageSize(20);
        setId();
        simpleApi.setInterPath(String.format(Constants.GET_PARTNER_SALE_SCHOOL_LIST_URL, id));
        choseFirstId(simpleApi);
        D2CApplication.httpClient.loadingRequest(simpleApi, new BeanRequest.SuccessListener<SaleSchoolListBean>() {
            @Override
            public void onResponse(SaleSchoolListBean saleSchoolListBean) {
                recycleView.setBackgroundColor(getResources().getColor(R.color.bg_color));
                ptr.refreshComplete();
                btnReload.setVisibility(View.GONE);
                imgHint.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                if (currentPage == 1) {
                    listBeen.clear();
                }
                int size = saleSchoolListBean.getData().getThemes().getList().size();
                if (size > 0) {
                    List<SaleSchoolListBean.DataBean.ThemesBean.ListBean> list = saleSchoolListBean.getData().getThemes().getList();
                    listBeen.addAll(list);
                } else if(themeTags.size()>0){

                }else{
                    setEmptyView(Constants.NO_DATA);
                }
                if (themeAdapter != null) {
                    themeAdapter.notifyDataSetChanged();
                    hasNext = saleSchoolListBean.getData().getThemes().isNext();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ptr.refreshComplete();
                progressBar.setVisibility(View.GONE);
                Util.showToast(SaleSchoolActivity.this, Util.checkErrorType(error));
                setEmptyView(Constants.NET_DISCONNECT);
            }
        });
    }

    private void choseFirstId(SimpleApi simpleApi) {
        if(themeTags!=null && themeTags.size()>0){
            simpleApi.setInterPath(String.format(Constants.GET_PARTNER_SALE_SCHOOL_LIST_URL, themeTags.get(0).getId()));
            if(partnerBean!=null && partnerBean.getLevel()==2){
                for (int i=0; i<themeTags.size();i++){
                    if(themeTags.get(i).getFix()==1){
                        simpleApi.setInterPath(String.format(Constants.GET_PARTNER_SALE_SCHOOL_LIST_URL, themeTags.get(i).getId()));
                        return;
                    }
                }
            }
        }

    }

    private void setId(){
            if(partnerBean!=null){
          if(partnerBean.getLevel()==1 || partnerBean.getLevel()==0){
                    themeTags.get(0).getId();
           }else if(partnerBean.getLevel()==2){
              for (int i=0; i<themeTags.size(); i++){
                  if(themeTags.get(i).getFix()==1){
                        id=themeTags.get(i).getId();
                        return;
                     }
                  }
             }
             }
        }

    private void setEmptyView(int type) {
        recycleView.setBackgroundColor(getResources().getColor(R.color.color_white));
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


    @OnClick({R.id.back_iv, R.id.btn_reload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.btn_reload:
                imgHint.setVisibility(View.GONE);
                btnReload.setVisibility(View.GONE);
                refresh();
                break;
        }
    }

    private void refresh() {
        currentPage = 1;
        loadData();
    }
}
