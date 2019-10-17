package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.LiveInfoJson;
import com.d2cmall.buyer.bean.MonthSummaryBean;
import com.d2cmall.buyer.holder.PerfomanceItemHolder;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;

import java.util.List;

/**
 * Created by rookie on 2018/4/17.
 */

public class PerformanceAdapter extends DelegateAdapter.Adapter<PerfomanceItemHolder> {

    private Context context;
    private List<MonthSummaryBean.ListBean> listBeans;

    public PerformanceAdapter(Context context, List<MonthSummaryBean.ListBean> listBeans) {
        this.context = context;
        this.listBeans = listBeans;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setMarginBottom(ScreenUtil.dip2px(16));
        return linearLayoutHelper;
    }

    @Override
    public PerfomanceItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_summary_item, parent, false);
        return new PerfomanceItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PerfomanceItemHolder holder, int position) {
        MonthSummaryBean.ListBean bean = listBeans.get(position);

        int allOrderCount = 0;
        double allPayAmount = 0;
        double allPayRebate = 0;
        if (bean.getSaleStat() != null) {
            allOrderCount = allOrderCount + bean.getSaleStat().getCount();
            allPayAmount = allPayAmount + bean.getSaleStat().getPayAmount();
            allPayRebate = allPayRebate + bean.getSaleStat().getPartnerRebates();
        }
        if (bean.getSaleStatDM() != null) {
            allOrderCount = allOrderCount + bean.getSaleStatDM().getCount();
            allPayAmount = allPayAmount + bean.getSaleStatDM().getPayAmount();
            allPayRebate = allPayRebate + bean.getSaleStatDM().getParentRebates();
        }
        if (bean.getSaleStatSDM() != null) {
            allOrderCount = allOrderCount + bean.getSaleStatSDM().getCount();
            allPayAmount = allPayAmount + bean.getSaleStatSDM().getPayAmount();
            allPayRebate = allPayRebate + bean.getSaleStatSDM().getSuperRebates();
        }
        if (bean.getSaleStatAM() != null) {
            allOrderCount = allOrderCount + bean.getSaleStatAM().getCount();
            allPayAmount = allPayAmount + bean.getSaleStatAM().getPayAmount();
            allPayRebate = allPayRebate + bean.getSaleStatAM().getMasterRebates();
        }
        holder.tvSell.setText(String.valueOf(wipePoint(allPayAmount)));
        holder.tvSellEarning.setText(String.valueOf(wipePoint(allPayRebate)));
        holder.tvOrder.setText(String.valueOf(allOrderCount));
        holder.tvMonthNumber.setText(getNumberMonth(bean.getMonth()));
        holder.tvMonthEng.setText(getEngMonth(getNumberMonth(bean.getMonth())));
        if (bean.isFirst) {
            holder.tvMonthNumber.setBackground(context.getResources().getDrawable(R.drawable.month_circle_back));
            holder.tvMonthNumber.setTextColor(Color.parseColor("#ffffffff"));
        } else {
            holder.tvMonthNumber.setBackground(context.getResources().getDrawable(R.drawable.month_circle_back_no_use));
            holder.tvMonthNumber.setTextColor(Color.parseColor("#4c000000"));
        }
    }

    private String wipePoint(double amount) {
        String s = String.valueOf(amount);
        int index = s.indexOf(".");
        if (index > 0 && s.length() - index > 3) {
            int a = (int) (amount * 100 + 0.5);
            return String.valueOf((double) a / 100);
        } else {
            return String.valueOf(amount);
        }
    }

    private String getNumberMonth(String date) {
        String month = date.substring(5, date.length());
        return month;
    }

    private String getEngMonth(String numMonth) {
        switch (numMonth) {
            case "01":
                return "JAN";
            case "02":
                return "FEB";
            case "03":
                return "MAR";
            case "04":
                return "APR";
            case "05":
                return "MAY";
            case "06":
                return "JUNE";
            case "07":
                return "JULY";
            case "08":
                return "AUG";
            case "09":
                return "SEP";
            case "10":
                return "OCT";
            case "11":
                return "NOV";
            case "12":
                return "DEC";
        }
        return "MONTH";
    }

    @Override
    public int getItemCount() {
        return listBeans.size();
    }
}
