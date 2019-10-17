package com.d2cmall.buyer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.ExchangeProgressAdapter;
import com.d2cmall.buyer.adapter.RefundProgressAdapter;
import com.d2cmall.buyer.adapter.ReshipProgressAdapter;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.ExchangeProductDetialBean;
import com.d2cmall.buyer.bean.RefundBeanData;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AfterSaleProgressDetailActivity extends BaseActivity {

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
    @Bind(R.id.tv_after_sale_type)
    TextView tvAfterSaleType;
    @Bind(R.id.rvTrace)
    RecyclerView rvTrace;
    @Bind(R.id.activity_main)
    RelativeLayout activityMain;
    @Bind(R.id.tv_refund_title)
    TextView tvRefundTitle;
    @Bind(R.id.rvTrace_bottom)
    RecyclerView rvTraceBottom;
    private int id;
    private String title;
    private List<RefundBeanData.DataBean.RefundLogsBean> refundLogs;
    private List<RefundBeanData.DataBean.ReshipLogsBean> reshipLogs;
    private List<ExchangeProductDetialBean.DataBean.ExchangeLogsBean> exchangeLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sale_progress_detail);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        nameTv.setText("进度详情");
        id = getIntent().getIntExtra("id", 0);
        title = getIntent().getStringExtra("title");
        refundLogs = (List<RefundBeanData.DataBean.RefundLogsBean>) getIntent().getSerializableExtra("refundLogs");
        reshipLogs = (List<RefundBeanData.DataBean.ReshipLogsBean>) getIntent().getSerializableExtra("reshipLogs");
        exchangeLogs = (List<ExchangeProductDetialBean.DataBean.ExchangeLogsBean>) getIntent().getSerializableExtra("exchangeLogs");
        if(refundLogs!=null && reshipLogs==null){
            RefundProgressAdapter refundProgressAdapter = new RefundProgressAdapter(this, false, refundLogs);
            rvTrace.setAdapter(refundProgressAdapter);
            rvTrace.setLayoutManager(new LinearLayoutManager(this));
        }else{
            if(refundLogs!=null){
                tvRefundTitle.setVisibility(View.VISIBLE);
                rvTraceBottom.setVisibility(View.VISIBLE);
                RefundProgressAdapter refundProgressAdapter = new RefundProgressAdapter(this, false,  refundLogs);
                rvTraceBottom.setAdapter(refundProgressAdapter);
                rvTraceBottom.setLayoutManager(new LinearLayoutManager(this));
            }
            if(reshipLogs !=null){
                ReshipProgressAdapter exchangeProgressAdapter = new ReshipProgressAdapter(this, false, reshipLogs);
                rvTrace.setAdapter(exchangeProgressAdapter);
                rvTrace.setLayoutManager(new LinearLayoutManager(this));
            }
            if(exchangeLogs!= null){
                ExchangeProgressAdapter exchangeProgressAdapter = new ExchangeProgressAdapter(this, false, exchangeLogs);
                rvTrace.setAdapter(exchangeProgressAdapter);
                rvTrace.setLayoutManager(new LinearLayoutManager(this));
            }
        }

        tvAfterSaleType.setText(title);
    }
//    //排序
//    public class ExchangeSortUtil implements Comparator {
//        @Override
//        public int compare(Object o1, Object o2) {
//            ExchangeProductDetialBean.DataBean.ExchangeLogsBean collectBean = (ExchangeProductDetialBean.DataBean.ExchangeLogsBean) o1;
//            ExchangeProductDetialBean.DataBean.ExchangeLogsBean collectBean1 = (ExchangeProductDetialBean.DataBean.ExchangeLogsBean) o2;
//            int flag = String.valueOf(collectBean.getCreateDate()).compareTo(String.valueOf(collectBean1.getCreateDate()));
//            return flag;
//        }
//    }

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }
}
