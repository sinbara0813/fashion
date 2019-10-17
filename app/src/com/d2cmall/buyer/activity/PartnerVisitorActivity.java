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
import com.d2cmall.buyer.adapter.PartnerVisitorAdapter;
import com.d2cmall.buyer.api.VisitorApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.PartnerVisitorBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.binder.ScrollEndBinder;
import com.d2cmall.buyer.holder.DefaultHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.HttpUtils;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


//我的访客
public class PartnerVisitorActivity extends BaseActivity {

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
    private PartnerVisitorAdapter partnerVisitorAdapter;
    private int currentPage = 1;
    private boolean hasNext = true;
    private ArrayList<PartnerVisitorBean.ListBean> visitorList = new ArrayList<>();
    private VirtualLayoutManager mLayoutManager;
    private DelegateAdapter delegateAdapter;
    private BaseVirtualAdapter<DefaultHolder> endAdapter; //列表结束标志
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_recyclerview);
        ButterKnife.bind(this);
        title = getIntent().getStringExtra("title");
        init();
        initListener();
    }

    private void init() {
        nameTv.setText(title);
        mLayoutManager = new VirtualLayoutManager(PartnerVisitorActivity.this);
            partnerVisitorAdapter = new PartnerVisitorAdapter(this, visitorList);
        recycleView.setLayoutManager(mLayoutManager);
        delegateAdapter = new DelegateAdapter(mLayoutManager, true);
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(0, 2);
        recycleView.setRecycledViewPool(recycledViewPool);
        recycleView.setAdapter(delegateAdapter);
        delegateAdapter.addAdapter(partnerVisitorAdapter);
        loadData();
    }


    private void loadData() {//访客数据
        progressBar.setVisibility(View.VISIBLE);
        String url = Constants.API_URL + Constants.GET_PARTNER_VISITOR_URL;
        JSONObject tmpObj = null;
        tmpObj=new JSONObject();
        try {
            tmpObj.put("event" , "买手店-访问");
            tmpObj.put("fieldName" , "targetId");
            tmpObj.put("pageCount" , currentPage);
            tmpObj.put("pageSize" , 20);
            UserBean.DataEntity.MemberEntity  user = Session.getInstance().getUserFromFile(this);
            if(user!=null){
                tmpObj.put("fieldValue" , user.getPartnerId());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String personInfos = tmpObj.toString(); // 将JSONArray转换得到String
        Charset chrutf = Charset.forName("UTF-8");
        String params = new String(personInfos.getBytes(), chrutf);
        try {
            HttpUtils.doPostAsyn(url,params, new HttpUtils.CallBack() {
                @Override
                public void onRequestComplete(final String result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ptr.refreshComplete();
                            btnReload.setVisibility(View.GONE);
                            imgHint.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            Gson gson = bulidGson();
                            PartnerVisitorBean partnerVisitorBean = gson.fromJson(result, PartnerVisitorBean.class);
                            if (currentPage == 1) {
                                visitorList.clear();
                            }
                            int size = partnerVisitorBean.getList().size();
                            if (size > 0) {
                                List<PartnerVisitorBean.ListBean> listBeanList = partnerVisitorBean.getList();
                                visitorList.addAll(listBeanList);
                            } else {
                                setEmptyView(Constants.NO_DATA);
                            }
                            if (partnerVisitorAdapter != null) {
                                partnerVisitorAdapter.notifyDataSetChanged();
                                hasNext = partnerVisitorBean.isNext();
                            }

                        }
                    });

                }
            });
        } catch (Exception e) {
            ptr.refreshComplete();
            progressBar.setVisibility(View.GONE);
            setEmptyView(Constants.NET_DISCONNECT);
        }
    }
    public Gson bulidGson(){
        Gson gson = null;
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new DateAdapter());
        gson = builder.create();
        return gson;
    }
    public class DateAdapter implements JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonElement arg0, Type arg1,
                                JsonDeserializationContext arg2) throws JsonParseException {
            try {
                if (arg0.getAsLong()>0){
                    Date date=new Date();
                    date.setTime(arg0.getAsLong());
                    return date;
                }
            }catch (Exception e){
                return DateUtil.toDate(arg0.getAsString());
            }
            return null;
        }
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
                        if (last > partnerVisitorAdapter.getItemCount() - 3 && hasNext) {
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

    @OnClick({R.id.back_iv,R.id.btn_reload})
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
        currentPage=1;
        loadData();
    }
}
