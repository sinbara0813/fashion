package com.d2cmall.buyer.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.VisitorListActivity;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.BuyerVisitorApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.bean.BuyerVisitorBean;
import com.d2cmall.buyer.bean.PartBillSummaryBean;
import com.d2cmall.buyer.bean.PartnerDayVisitorBean;
import com.d2cmall.buyer.bean.PartnerMemberBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static com.d2cmall.buyer.Constants.GET_PARTNER_VISITOR_YESTERDAY_TODAY_URL;

/**
 * Created by rookie on 2018/4/13.
 */

public class BuyerShopFragment extends com.d2cmall.buyer.base.BaseFragment {


    @Bind(R.id.tv_more_visitor_all)
    TextView tvMoreVisitorAll;
    @Bind(R.id.ll_visitor_title)
    LinearLayout llVisitorTitle;
    @Bind(R.id.tv_today_num)
    TextView tvTodayNum;
    @Bind(R.id.ll_today)
    LinearLayout llToday;
    @Bind(R.id.tv_yesterday_num)
    TextView tvYesterdayNum;
    @Bind(R.id.ll_yesterday)
    LinearLayout llYesterday;
    @Bind(R.id.tv_thirty_num)
    TextView tvThirtyNum;
    @Bind(R.id.ll_thirty_days)
    LinearLayout llThirtyDays;
    @Bind(R.id.ll_top_income_detail)
    LinearLayout llTopIncomeDetail;
    @Bind(R.id.rl_income_detail)
    RelativeLayout rlIncomeDetail;
    @Bind(R.id.tv_more_retail_all)
    TextView tvMoreRetailAll;
    @Bind(R.id.ll_retail_title)
    LinearLayout llRetailTitle;
    @Bind(R.id.ll_retail_statistics)
    LinearLayout llRetailStatistics;
    @Bind(R.id.tv_more_retail_client_all)
    TextView tvMoreRetailClientAll;
    @Bind(R.id.ll_retail_client_title)
    LinearLayout llRetailClientTitle;
    @Bind(R.id.ll_head)
    LinearLayout llHead;
    @Bind(R.id.rl_content)
    RelativeLayout rlContent;

    private int textWidth;
    private int textSpace;
    private int width;
    private PartnerMemberBean.DataBean.PartnerBean partnerBean;
    private PartBillSummaryBean billSummaryBean;

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buyer_shop, container, false);
    }

    @Override
    public void prepareView() {
        partnerBean = Session.getInstance().getPartnerFromFile(mContext);
        loadVisitorData();//零售用户卡片数据
        loadVisitorTextData();//访客数据
        width = ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(32);
        loadBillSummary();
    }

    private void loadBillSummary() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.PARTNER_BILL_SUMMARY);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<PartBillSummaryBean>() {
            @Override
            public void onResponse(PartBillSummaryBean response) {
                if (response != null && response.getData() != null) {
                    billSummaryBean = response;

                    countData();
                }
            }

        });
    }

    private void countData() {
        if (billSummaryBean == null) {
            return;
        }
        int orderGetCount = 0;//已到账订单数
        int orderIngCount = 0;//交易中订单数
        int orderCloseCount = 0;//已关闭订单数

        double payAmoutGet = 0;//已到账销售额
        double payAmoutIng = 0;//交易中销售额
        double payAmoutClose = 0;//已关闭销售额

        double rebateAmoutGet = 0;//已到账预计收益
        double rebateAmoutIng = 0;//交易中预计收益
        double rebateAmoutClose = 0;//已关闭预计收益

        //-1 已关闭 1 待到账 8 已到账
//        if (billSummaryBean.getData().getMasterData() != null) {//AM
//            int size = billSummaryBean.getData().getMasterData().size();
//            for (int i = 0; i < size; i++) {
//                PartBillSummaryBean.DataBean.PartnerDataBean partnerDataBean = billSummaryBean.getData().getMasterData().get(i);
//                if (partnerDataBean.getStatusX() == -1) {//已关闭
//                    orderCloseCount += partnerDataBean.getCount();
//                    payAmoutClose += partnerDataBean.getAmount();
//                    rebateAmoutClose += partnerDataBean.getRebate();
//                } else if (partnerDataBean.getStatusX() == 1) {//待到账
//                    orderIngCount += partnerDataBean.getCount();
//                    payAmoutIng += partnerDataBean.getAmount();
//                    rebateAmoutIng += partnerDataBean.getRebate();
//                } else if (partnerDataBean.getStatusX() == 8) {//已到账
//                    orderGetCount += partnerDataBean.getCount();
//                    payAmoutGet += partnerDataBean.getAmount();
//                    rebateAmoutGet += partnerDataBean.getRebate();
//                }
//            }
//        }

        if (billSummaryBean.getData().getPartnerData() != null) {
            int size = billSummaryBean.getData().getPartnerData().size();
            for (int i = 0; i < size; i++) {
                PartBillSummaryBean.DataBean.PartnerDataBean partnerDataBean = billSummaryBean.getData().getPartnerData().get(i);
                if (partnerDataBean.getStatusX() == -1) {//已关闭
                    orderCloseCount += partnerDataBean.getCount();
                    payAmoutClose += partnerDataBean.getAmount();
                    rebateAmoutClose += partnerDataBean.getRebate();
                } else if (partnerDataBean.getStatusX() == 1) {//待到账
                    orderIngCount += partnerDataBean.getCount();
                    payAmoutIng += partnerDataBean.getAmount();
                    rebateAmoutIng += partnerDataBean.getRebate();
                } else if (partnerDataBean.getStatusX() == 8) {//已到账
                    orderGetCount += partnerDataBean.getCount();
                    payAmoutGet += partnerDataBean.getAmount();
                    rebateAmoutGet += partnerDataBean.getRebate();
                }
            }
        }

//        if (billSummaryBean.getData().getSuperData() != null) {
//            int size = billSummaryBean.getData().getSuperData().size();
//            for (int i = 0; i < size; i++) {
//                PartBillSummaryBean.DataBean.PartnerDataBean partnerDataBean = billSummaryBean.getData().getSuperData().get(i);
//                if (partnerDataBean.getStatusX() == -1) {//已关闭
//                    orderCloseCount += partnerDataBean.getCount();
//                    payAmoutClose += partnerDataBean.getAmount();
//                    rebateAmoutClose += partnerDataBean.getRebate();
//                } else if (partnerDataBean.getStatusX() == 1) {//待到账
//                    orderIngCount += partnerDataBean.getCount();
//                    payAmoutIng += partnerDataBean.getAmount();
//                    rebateAmoutIng += partnerDataBean.getRebate();
//                } else if (partnerDataBean.getStatusX() == 8) {//已到账
//                    orderGetCount += partnerDataBean.getCount();
//                    payAmoutGet += partnerDataBean.getAmount();
//                    rebateAmoutGet += partnerDataBean.getRebate();
//                }
//            }
//        }

//        if (billSummaryBean.getData().getParentData() != null) {
//            int size = billSummaryBean.getData().getParentData().size();
//            for (int i = 0; i < size; i++) {
//                PartBillSummaryBean.DataBean.PartnerDataBean partnerDataBean = billSummaryBean.getData().getParentData().get(i);
//                if (partnerDataBean.getStatusX() == -1) {//已关闭
//                    orderCloseCount += partnerDataBean.getCount();
//                    payAmoutClose += partnerDataBean.getAmount();
//                    rebateAmoutClose += partnerDataBean.getRebate();
//                } else if (partnerDataBean.getStatusX() == 1) {//待到账
//                    orderIngCount += partnerDataBean.getCount();
//                    payAmoutIng += partnerDataBean.getAmount();
//                    rebateAmoutIng += partnerDataBean.getRebate();
//                } else if (partnerDataBean.getStatusX() == 8) {//已到账
//                    orderGetCount += partnerDataBean.getCount();
//                    payAmoutGet += partnerDataBean.getAmount();
//                    rebateAmoutGet += partnerDataBean.getRebate();
//                }
//            }
//        }

        int orderAllCount = orderGetCount + orderIngCount;//总计订单数
        double payAmountAll = payAmoutGet + payAmoutIng;//总计销售额
        double rebateAll = rebateAmoutGet + rebateAmoutIng;//总计预计收益

        List<String> receved = new ArrayList<>();
        receved.add(String.valueOf(orderGetCount));
        receved.add(String.valueOf(wipePoint(payAmoutGet)));
        receved.add(String.valueOf(wipePoint(rebateAmoutGet)));
        List<String> delling = new ArrayList<>();
        delling.add(String.valueOf(orderIngCount));
        delling.add(String.valueOf(wipePoint(payAmoutIng)));
        delling.add(String.valueOf(wipePoint(rebateAmoutIng)));
        List<String> closed = new ArrayList<>();
        closed.add(String.valueOf(orderCloseCount));
        closed.add(String.valueOf(wipePoint(payAmoutClose)));
        closed.add(String.valueOf(wipePoint(0)));
        List<String> all = new ArrayList<>();
        all.add(String.valueOf(orderAllCount));
        all.add(String.valueOf(wipePoint(payAmountAll)));
        all.add(String.valueOf(wipePoint(rebateAll)));
        addOrderView(receved, delling, closed, all);
    }



    private void loadVisitorTextData() {
        BuyerVisitorApi api = new BuyerVisitorApi();
        api.event = "买手店-访问";
        api.fieldName = "targetId";
        api.method = BaseApi.Method.POST;
        UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(mContext);
        api.fieldValue = user.getPartnerId();
        api.isJsonContentType = true;
        api.setInterPath(GET_PARTNER_VISITOR_YESTERDAY_TODAY_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<PartnerDayVisitorBean>() {
            @Override
            public void onResponse(PartnerDayVisitorBean partnerDayVisitorBean) {
                String today = String.format(getString(R.string.buyer_people_unit), String.valueOf(partnerDayVisitorBean.getList().get(0).getTodayUv()));
                peopleText(tvTodayNum, today);
                String yesterday = String.format(getString(R.string.buyer_people_unit), String.valueOf(partnerDayVisitorBean.getList().get(0).getLastDayUv()));
                peopleText(tvYesterdayNum, yesterday);
                String thirty = String.format(getString(R.string.buyer_people_unit), String.valueOf(partnerDayVisitorBean.getList().get(0).getUv()));
                peopleText(tvThirtyNum, thirty);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }

    private void peopleText(TextView textView, String text) {
        StringBuilder builder = new StringBuilder(text);
        int yuan = builder.toString().indexOf("人");
        if (yuan < 0)
            return;
        SpannableString spannableString = new SpannableString(builder.toString());

        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(Color.parseColor("#ffffff"));
        spannableString.setSpan(colorSpan1, 0, yuan + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        RelativeSizeSpan sizeSpan1 = new RelativeSizeSpan(1.0f);
        spannableString.setSpan(sizeSpan1, 0, yuan, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        RelativeSizeSpan sizeSpan2 = new RelativeSizeSpan(0.5f);
        spannableString.setSpan(sizeSpan2, yuan, yuan + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        textView.setText(spannableString);
    }

    private void loadVisitorData() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.BUYER_VISITOR_DATA);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BuyerVisitorBean>() {
            @Override
            public void onResponse(BuyerVisitorBean response) {
                if(!isVisibleToUser || llHead==null){
                   return;
                }
                llHead.removeAllViews();
                List<BuyerVisitorBean.DataBean.CustomersBean.ListBean> list = response.getData().getCustomers().getList();
                List<BuyerVisitorBean.DataBean.CustomersBean.ListBean> userList = new ArrayList<>();
                if (list != null && list.size() > 0) {//有数据的时候
                    if (list.size() > 5) {
                        userList.addAll(list.subList(0, 5));
                    } else {
                        userList.addAll(list);
                    }
                    for (int i = 0; i < userList.size(); i++) {
                        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_buyer_head_name_item, llHead, false);
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
                        if (i != userList.size() - 1) {
                            params.rightMargin = ScreenUtil.dip2px(10);
                        }
                        view.setLayoutParams(params);
                        RoundedImageView imageView = (RoundedImageView) view.findViewById(R.id.img_avatar);
                        TextView name = (TextView) view.findViewById(R.id.tv_buyer_name);
                        UniversalImageLoader.displayRoundImage(mContext, userList.get(i).getHead(), imageView, R.mipmap.ic_default_avatar);
                        if(!Util.isEmpty(userList.get(i).getNickname())){
                            name.setText(userList.get(i).getNickname());
                        }else{
                            name.setText("匿名_"+userList.get(i).getMemberId());
                        }

                        llHead.addView(view);
                    }
                } else {//没数据的时候
                    addEmptyView();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                addEmptyView();
            }
        });
    }

    private void addEmptyView() {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setGravity(Gravity.CENTER);

        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(R.mipmap.pic_nocustomer);

        TextView textView = new TextView(mContext);
        textView.setText(R.string.buyer_no_visitor);
        textView.setTextColor(mContext.getResources().getColor(R.color.trans_30_color_black));
        textView.setPadding(ScreenUtil.dip2px(4), 0, 0, 0);

        linearLayout.addView(imageView);
        linearLayout.addView(textView);
        llHead.addView(linearLayout);
    }

    private void calculateLocation() {
        if (textWidth == 0 || textSpace == 0) {
            TextPaint paint = new TextPaint();
            paint.setTextSize(Util.dip2px(getActivity(), 10));
            Rect rect = new Rect();
            paint.getTextBounds("已到账", 0, 3, rect);
            textWidth = rect.width();
            textSpace = (width - ScreenUtil.dip2px(92) - 4 * textWidth) / 3;
        }
    }

    private void addOrderView(List<String> receved, List<String> delling, List<String> closed, List<String> all) {
        llRetailStatistics.removeAllViews();
        calculateLocation();
        for (int i = 0; i < 4; i++) {
            LinearLayout linearLayout = new LinearLayout(mContext);
            if (i == 0) {
                linearLayout.setPadding(0, 0, 0, ScreenUtil.dip2px(12));
                TextView textView = new TextView(mContext);
                textView.setText("已到账");
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                textView.setTextColor(getResources().getColor(R.color.color_black38));
                textView.setPadding(ScreenUtil.dip2px(76), 0, 0, 0);
                linearLayout.addView(textView);

                TextView textView1 = new TextView(mContext);
                textView1.setText("交易中");
                textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                textView1.setTextColor(getResources().getColor(R.color.color_black38));
                textView1.setPadding(textSpace * 3 / 4, 0, 0, 0);
                linearLayout.addView(textView1);

                TextView textView2 = new TextView(mContext);
                textView2.setText("已关闭");
                textView2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                textView2.setTextColor(getResources().getColor(R.color.color_black38));
                textView2.setPadding(textSpace * 3 / 4, 0, 0, 0);
                linearLayout.addView(textView2);

                TextView textView3 = new TextView(mContext);
                textView3.setGravity(Gravity.RIGHT);
                textView3.setLines(1);
                textView3.setText("总计");
                textView3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                textView3.setTextColor(getResources().getColor(R.color.color_black38));
                textView3.setPadding(textSpace * 6 / 4 + ScreenUtil.dip2px(5), 0, 0, 0);
                linearLayout.addView(textView3);
            } else {
                if (i != 3) {
                    linearLayout.setPadding(0, 0, 0, ScreenUtil.dip2px(12));
                }
                for (int j = 0; j < 5; j++) {
                    TextView textView = new TextView(mContext);
                    if (j == 0) {
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                        textView.setTextColor(getResources().getColor(R.color.color_black38));
                        switch (i) {
                            case 1:
                                textView.setText("订单数/单");
                                break;
                            case 2:
                                textView.setText("销售额/元");
                                break;
                            case 3:
                                textView.setText("预计收益/元");
                                break;
                        }
                        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ScreenUtil.dip2px(76), -1);
                        linearLayout.addView(textView, ll);
                    } else {
                        TextView textView1 = new TextView(mContext);
                        textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                        textView.setTextColor(getResources().getColor(R.color.color_black87));
                        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(textWidth + textSpace * 3 / 4, -1);
                        switch (j) {
                            case 1:
                                textView1.setText(receved.get(i - 1) + "");
                                //ll.setMargins(0, 0, textSpace, 0);
                                break;
                            case 2:
                                textView1.setText(delling.get(i - 1) + "");
                                //ll.setMargins(0, 0, textSpace, 0);
                                break;
                            case 3:
                                textView1.setText(closed.get(i - 1) + "");
                                //ll.setMargins(0, 0, textSpace, 0);
                                break;
                            case 4:
                                textView1.setText(all.get(i - 1) + "");
                                textView1.setGravity(Gravity.RIGHT);
                                break;
                        }
                        linearLayout.addView(textView1, ll);
                    }
                }
            }
            llRetailStatistics.addView(linearLayout);
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

    @Override
    public void doBusiness() {
    }

    @OnClick({R.id.tv_more_visitor_all, R.id.tv_more_retail_all, R.id.tv_more_retail_client_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_more_visitor_all:
                Intent intent1 = new Intent(mContext, VisitorListActivity.class);
                intent1.putExtra("type", 1);
                mContext.startActivity(intent1);
                break;
            case R.id.tv_more_retail_all:
                break;
            case R.id.tv_more_retail_client_all:
                Intent intent2 = new Intent(mContext, VisitorListActivity.class);
                intent2.putExtra("type", 2);
                mContext.startActivity(intent2);
                break;
        }
    }
}
