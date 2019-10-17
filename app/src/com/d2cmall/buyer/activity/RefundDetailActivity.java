package com.d2cmall.buyer.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.CancelRefundApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.RefundBeanData;
import com.d2cmall.buyer.bean.RefundInfoBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.TimeLineView;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

//退款详情页

public class RefundDetailActivity extends BaseActivity {

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
    @Bind(R.id.timeLineView)
    TimeLineView timeLineView;
    @Bind(R.id.ll_after_sale_state)
    LinearLayout llAfterSaleState;
    @Bind(R.id.line_layout)
    View lineLayout;
    @Bind(R.id.tv_change_logist)
    TextView tvChangeLogist;
    @Bind(R.id.tv_action_cancle)
    TextView tvActionCancle;
    @Bind(R.id.rl_cancle)
    RelativeLayout rlCancle;
    @Bind(R.id.img_product)
    ImageView imgProduct;
    @Bind(R.id.tv_product_title)
    TextView tvProductTitle;
    @Bind(R.id.tv_product_style)
    TextView tvProductStyle;
    @Bind(R.id.tv_num_desc)
    TextView tvNumDesc;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.ll_product_num)
    LinearLayout llProductNum;
    @Bind(R.id.tv_money_desc)
    TextView tvMoneyDesc;
    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.ll_product_money)
    LinearLayout llProductMoney;
    @Bind(R.id.tv_back_money_desc)
    TextView tvBackMoneyDesc;
    @Bind(R.id.tv_back_way)
    TextView tvBackWay;
    @Bind(R.id.ll_back_money_way)
    LinearLayout llBackMoneyWay;
    @Bind(R.id.tv_back_code_desc)
    TextView tvBackCodeDesc;
    @Bind(R.id.tv_back_code)
    TextView tvBackCode;
    @Bind(R.id.tv_back_reason_desc)
    TextView tvBackReasonDesc;
    @Bind(R.id.tv_back_reason)
    TextView tvBackReason;
    @Bind(R.id.tv_request_time_desc)
    TextView tvRequestTimeDesc;
    @Bind(R.id.tv_request_time)
    TextView tvRequestTime;
    @Bind(R.id.tv_question_desc)
    TextView tvQuestionDesc;
    @Bind(R.id.tv_question)
    TextView tvQuestion;
    @Bind(R.id.tv_img_desc)
    TextView tvImgDesc;
    @Bind(R.id.progress_detail)
    LinearLayout progressDetail;
    @Bind(R.id.tv_status_name)
    TextView tvStatusName;
    @Bind(R.id.tv_custerm_msg)
    TextView tvCustermMsg;
    @Bind(R.id.iv_icon_logistics)
    ImageView ivIconLogistics;
    @Bind(R.id.tv_back_title)
    TextView tvBackTitle;
    @Bind(R.id.tv_back_address)
    TextView tvBackAddress;
    @Bind(R.id.rl_company_info)
    RelativeLayout rlCompanyInfo;
    @Bind(R.id.ll_custerm_msg)
    LinearLayout llCustermMsg;
    @Bind(R.id.iv_status)
    ImageView ivStatus;
    @Bind(R.id.ll_logistics_alert)
    LinearLayout llLogisticsAlert;
    @Bind(R.id.tv_logistics_info)
    TextView tvLogisticsInfo;
    @Bind(R.id.ll_logistics_info)
    LinearLayout llLogisticsInfo;
    @Bind(R.id.tv_logistics_address)
    TextView tvLogisticsAddress;
    @Bind(R.id.ll_logistics_address)
    LinearLayout llLogisticsAddress;
    @Bind(R.id.ll_evidences)
    LinearLayout llEvidences;
    @Bind(R.id.img_hint)
    ImageView imgHint;
    @Bind(R.id.btn_reload)
    TextView btnReload;
    @Bind(R.id.empty_hint_layout)
    LinearLayout emptyHintLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.rl_root_view)
    RelativeLayout rlRootView;
    @Bind(R.id.tv_action_copy)
    TextView tvActionCopy;
    @Bind(R.id.sv_root)
    ScrollView svRoot;
    @Bind(R.id.tv_money_ratify_desc)
    TextView tvMoneyRatifyDesc;
    @Bind(R.id.tv_money_ratify)
    TextView tvMoneyRatify;
    @Bind(R.id.ll_product_ratify_money)
    LinearLayout llProductRatifyMoney;
    @Bind(R.id.iv_custerm_dot)
    ImageView ivCustermDot;
    @Bind(R.id.ll_user_memo)
    LinearLayout llUserMemo;
    private long refundId;
    private RefundBeanData refundBeanData;
    private List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sale_detail);
        ButterKnife.bind(this);
        refundId = getIntent().getLongExtra("refundId", 0);
        init();
        loadData();
    }

    private void init() {
        nameTv.setText("退款详情");
        titleRight.setBackgroundResource(R.mipmap.tab_all_service);
    }

    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.GET_REFUND_DETAIL_URL, refundId));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<RefundBeanData>() {
            @Override
            public void onResponse(RefundBeanData data) {
                svRoot.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                refundBeanData = data;
                setDataToView(refundBeanData);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(RefundDetailActivity.this, Util.checkErrorType(error));
                progressBar.setVisibility(View.GONE);
                setEmptyView(Constants.NET_DISCONNECT);
                Util.checkErrorType(error);
            }
        });

    }


    private void setDataToView(RefundBeanData refundBeanData) {
        initTimeLine(refundBeanData);
        tvChangeLogist.setVisibility(View.GONE);
        llProductNum.setVisibility(View.GONE);
        tvStatusName.setText(refundBeanData.getData().getRefund().getStatusName());
        UniversalImageLoader.displayImage(this, Util.getD2cPicUrl(refundBeanData.getData().getRefund().getProductImg()), imgProduct
                , R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
        tvProductTitle.setText(refundBeanData.getData().getRefund().getProductName());
        tvProductStyle.setText(getString(R.string.label_product_style, refundBeanData.getData().getRefund().getProductColor(),
                refundBeanData.getData().getRefund().getProductSize()));
        tvBackCodeDesc.setText("退款编号:");
        tvBackReasonDesc.setText("退款原因:");
        tvBackCode.setText(refundBeanData.getData().getRefund().getRefundSn());
        tvBackReason.setText(refundBeanData.getData().getRefund().getRefundReason());
        tvRequestTime.setText(DateUtil.ConverToString(DateUtil.strToDateLong(refundBeanData.getData().getRefund().getCreateDate())));
        if (!Util.isEmpty(refundBeanData.getData().getRefund().getMemo())) {
            tvQuestion.setText(refundBeanData.getData().getRefund().getMemo());
        }else{
            llUserMemo.setVisibility(View.GONE);
        }
        tvMoneyRatify.setText(String.valueOf(refundBeanData.getData().getRefund().getTotalAmount()));
        tvMoney.setText(String.valueOf(refundBeanData.getData().getRefund().getApplyAmount()));
        tvBackAddress.setText(getString(R.string.msg_back_adress
                , refundBeanData.getData().getRefund().getBackAddress()
                , refundBeanData.getData().getRefund().getBackConsignee()
                , refundBeanData.getData().getRefund().getBackMobile()));
        if (Util.isEmpty(refundBeanData.getData().getRefund().getCustomerMemo())) {
            llCustermMsg.setVisibility(View.GONE);
        } else {
            llCustermMsg.setVisibility(View.VISIBLE);
            tvCustermMsg.setText(refundBeanData.getData().getRefund().getCustomerMemo());
        }
        List<String> evidences = refundBeanData.getData().getRefund().getEvidences();
        if (evidences != null && evidences.size() > 0) {
            for (int i = 0; i < evidences.size(); i++) {
                ImageView iv = new ImageView(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtil.dip2px(48), ScreenUtil.dip2px(48));
                if (i < evidences.size() - 1) {
                    params.rightMargin = ScreenUtil.dip2px(8);
                }
                if (i == 0) {
                    params.leftMargin = ScreenUtil.dip2px(8);
                }
                UniversalImageLoader.displayImage(this, Util.getD2cPicUrl(evidences.get(i)), iv
                        , R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
                iv.setLayoutParams(params); //设置布局
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                llEvidences.addView(iv);
            }
        } else {
            llEvidences.setVisibility(View.GONE);
        }
        checkRefundStatus();
    }


    private void checkRefundStatus() {
//        CREATE(0, "正在申请退款"), APPLY(1, "客服正在确认"), WAITFORPAYMENT(4, "财务正在审核"), WAITFORNOTIFY(5, "财务正在退款"),
//                MEMBERCLOSE(-2, "用户取消退款"), CLOSE(-1, "商家拒绝退款"), SUCCESS(8, "退款成功");
        switch (refundBeanData.getData().getRefund().getStatusCode()) {
            case -1:
                ivStatus.setImageResource(R.mipmap.icon_service_fail);
                rlCancle.setVisibility(View.GONE);
                timeLineView.setPointStrings(data, (float) 1.6);
                llProductRatifyMoney.setVisibility(View.GONE);
                llCustermMsg.setVisibility(View.VISIBLE);
                tvCustermMsg.setText(refundBeanData.getData().getRefundLogs().get(0).getInfo());
                break;
            case -2:
                if (refundBeanData.getData().getRefund().getCloseDate() != null) {
                    llCustermMsg.setVisibility(View.VISIBLE);
                    tvCustermMsg.setText(getString(R.string.msg_refund_close_date, DateUtil.ConverToString(refundBeanData.getData().getRefund().getCloseDate())));
                } else {
                    llCustermMsg.setVisibility(View.GONE);
                }
                ivStatus.setImageResource(R.mipmap.icon_service_fail);
                rlCancle.setVisibility(View.GONE);
                timeLineView.setPointStrings(data, 1);
                llProductRatifyMoney.setVisibility(View.GONE);
                break;
            case 0:
                llProductRatifyMoney.setVisibility(View.GONE);
                tvActionCancle.setText(R.string.label_cancel_after_sale);
                timeLineView.setPointStrings(data, 1);
                rlCancle.setVisibility(View.VISIBLE);
                tvChangeLogist.setVisibility(View.GONE);
                break;
            case 1:
                llCustermMsg.setVisibility(View.GONE);
                llProductRatifyMoney.setVisibility(View.GONE);
                timeLineView.setPointStrings(data, (float) 1.6);
                rlCancle.setVisibility(View.VISIBLE);
                break;
            case 4:
                timeLineView.setPointStrings(data, 2);
                rlCancle.setVisibility(View.GONE);
                break;
            case 5:
                timeLineView.setPointStrings(data, 2);
                rlCancle.setVisibility(View.GONE);
                break;
            case 8:
                ivStatus.setImageResource(R.mipmap.icon_service_success);
                timeLineView.setPointStrings(data, 3);
                rlCancle.setVisibility(View.GONE);
                if (refundBeanData.getData().getRefund().getPayDate() != null) {
                    llCustermMsg.setVisibility(View.VISIBLE);
                    ivCustermDot.setVisibility(View.INVISIBLE);
                    tvCustermMsg.setText(getString(R.string.msg_refund_success
                            , refundBeanData.getData().getRefund().getPaySn()
                            , DateUtil.ConverToString(refundBeanData.getData().getRefund().getPayDate())
                            , String.valueOf(refundBeanData.getData().getRefund().getPayMoney())));
                }
                break;
        }
    }


    private void initTimeLine(RefundBeanData refundBeanData) {
        data = new ArrayList<>();
        data.add("申请退款");
        data.add("D2C审核");
        data.add("商家退款");

    }


    @OnClick({R.id.back_iv, R.id.progress_detail, R.id.tv_change_logist, R.id.tv_action_cancle, R.id.btn_reload, R.id.tv_action_copy, R.id.title_right})
    public void onViewClicked(View view) {
        ClipboardManager cm = null;
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.btn_reload:
                loadData();
                break;
            case R.id.title_right: //跳转客服
                if (Util.loginChecked(this, 999)) {
                    String title = "线上客服";
                    String url = "http://www.d2cmall.com";
                    ConsultSource source = new ConsultSource(url, title, "售后详情");
                    source.groupId = Constants.QIYU_AF_GROUP_ID;
                    source.robotFirst = true;
                    Unicorn.openServiceActivity(this, "D2C客服", source);
                    //合力亿捷
//                    Intent intent = new Intent(this,CustomServiceActivity.class);
//                    intent.putExtra("skillGroupId", Constants.HLYJ_BF_AF_GROUP_ID);
//                    startActivity(intent);
                }
                break;
            case R.id.tv_action_copy:
                cm = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setPrimaryClip(ClipData.newPlainText("url", tvBackAddress.getText()));
                Util.showToast(this, R.string.msg_copy_ok);
                break;
            case R.id.progress_detail:
                //跳转进度详情
                startActivity(new Intent(RefundDetailActivity.this, AfterSaleProgressDetailActivity.class)
                        .putExtra("title", "退款进度")
                        .putExtra("refundLogs", (Serializable) refundBeanData.getData().getRefundLogs())
                        .putExtra("id", refundBeanData.getData().getRefund().getId()));

                break;
            case R.id.tv_change_logist:

                break;
            case R.id.tv_action_cancle:
                new AlertDialog.Builder(RefundDetailActivity.this)
                        .setMessage(R.string.msg_cancel_after_sale)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestCancelRefundTask(refundBeanData.getData().getRefund().getId());
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
        }
    }

    private void requestCancelRefundTask(int id) {
        CancelRefundApi api = new CancelRefundApi();
        api.setInterPath(Constants.CANCEL_REFUND_URL);
        api.setRefundId(id);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<RefundInfoBean>() {
            @Override
            public void onResponse(RefundInfoBean refundInfoBean) {
                Util.showToast(RefundDetailActivity.this, R.string.msg_cancel_after_sale_ok);
                EventBus.getDefault().post(refundInfoBean.getData().getRefund());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(RefundDetailActivity.this, Util.checkErrorType(error));
            }
        });
    }


    private void setEmptyView(int type) {
        rlRootView.setBackgroundColor(getResources().getColor(R.color.color_white));
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

}
