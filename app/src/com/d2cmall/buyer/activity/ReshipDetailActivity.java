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
import com.d2cmall.buyer.api.CancleReshipApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.RefundBeanData;
import com.d2cmall.buyer.bean.ReshipInfoBean;
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

//退货退款详情页
public class ReshipDetailActivity extends BaseActivity {

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
    @Bind(R.id.tv_tip_write_logistics_info)
    TextView tvTipWriteLogisticsInfo;
    @Bind(R.id.ll_tip_write_logistic)
    LinearLayout llTipWriteLogistic;
    @Bind(R.id.tv_left_time)
    TextView tvLeftTime;
    private long refundId;
    private RefundBeanData refundBeanData;
    private List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sale_detail);
        ButterKnife.bind(this);
        refundId = getIntent().getLongExtra("reshipId", 0);
        init();
        loadData();
    }

    private void init() {
        nameTv.setText("退货退款详情");
        titleRight.setBackgroundResource(R.mipmap.tab_all_service);
    }

    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.GET_RESHIP_DETAIL_URL, refundId));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<RefundBeanData>() {
            @Override
            public void onResponse(RefundBeanData data) {
                if(isFinishing() || progressBar==null){
                    return;
                }
                progressBar.setVisibility(View.GONE);
                svRoot.setVisibility(View.VISIBLE);
                refundBeanData = data;
                setDataToView(refundBeanData);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(ReshipDetailActivity.this, Util.checkErrorType(error));
                progressBar.setVisibility(View.GONE);
                setEmptyView(Constants.NET_DISCONNECT);
            }
        });

    }


    private void setDataToView(RefundBeanData refundBeanData) {
        initTimeLine(refundBeanData);
        tvStatusName.setText(refundBeanData.getData().getReship().getStatusName());
        UniversalImageLoader.displayImage(this, Util.getD2cPicUrl(refundBeanData.getData().getReship().getProductImg()), imgProduct
                , R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
        tvProductTitle.setText(refundBeanData.getData().getReship().getProductName());
        tvNum.setText(String.valueOf(refundBeanData.getData().getReship().getActualQuantity()));
        tvProductStyle.setText(getString(R.string.label_product_style, refundBeanData.getData().getReship().getProductColor(),
                refundBeanData.getData().getReship().getProductSize()));
        tvBackCodeDesc.setText("退货编号:");
        tvBackReasonDesc.setText("退货原因:");
        tvMoneyRatify.setText(String.valueOf(refundBeanData.getData().getRefund().getTotalAmount()));//核定金额
        tvBackCode.setText(refundBeanData.getData().getReship().getReshipSn());
        tvBackReason.setText(refundBeanData.getData().getReship().getRefundReason());
        tvRequestTime.setText(DateUtil.ConverToString(refundBeanData.getData().getReship().getCreateDate()));
        if (!Util.isEmpty(refundBeanData.getData().getReship().getMemo())) {
            tvQuestion.setText(refundBeanData.getData().getReship().getMemo());
        } else {
            llUserMemo.setVisibility(View.GONE);
        }
        tvMoney.setText(String.valueOf(refundBeanData.getData().getRefund().getApplyAmount()));//申请金额
        tvBackAddress.setText(getString(R.string.msg_back_adress
                , refundBeanData.getData().getReship().getBackAddress().getAddress()
                , refundBeanData.getData().getReship().getBackAddress().getConsignee()
                , refundBeanData.getData().getReship().getBackAddress().getMobile()));
        ArrayList<String> evidences = refundBeanData.getData().getReship().getEvidences();

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
        checkReshipStatus();

    }


    private void checkReshipStatus() {
//        退货reship
//        APPLY(0, "正在申请退货"), APPROVE(2, "商家同意退货"), WAITFORRECEIVE(4, "仓库正在收货"),
//                REFUSE(-3, "商家拒绝退货"), MEMBERCLOSE(-2, "用户取消退货"), CLOSE(-1, "商家关闭退货"), SUCCESS(8, "财务处理中");
        switch (refundBeanData.getData().getReship().getStatusCode()) {
            case -1:
                timeLineView.setPointStrings(data, 1);
                ivStatus.setImageResource(R.mipmap.icon_service_fail);
                rlCancle.setVisibility(View.GONE);
                llCustermMsg.setVisibility(View.VISIBLE);
                tvCustermMsg.setText(refundBeanData.getData().getReshipLogs().get(0).getInfo());
                break;
            case -2:
                llProductRatifyMoney.setVisibility(View.GONE);
                if (refundBeanData.getData().getReship().getCloseDate() != null) {
                    llCustermMsg.setVisibility(View.VISIBLE);
                    tvCustermMsg.setText(getString(R.string.msg_refund_close_date, DateUtil.ConverToString(refundBeanData.getData().getReship().getCloseDate())));
                } else {
                    llCustermMsg.setVisibility(View.GONE);
                }
                timeLineView.setPointStrings(data, (float) 1.6);
                ivStatus.setImageResource(R.mipmap.icon_service_fail);
                rlCancle.setVisibility(View.GONE);
                break;
            case -3:
                llProductRatifyMoney.setVisibility(View.GONE);
                timeLineView.setPointStrings(data, (float) 1.6);
                ivStatus.setImageResource(R.mipmap.icon_service_fail);
                rlCancle.setVisibility(View.GONE);
                llCustermMsg.setVisibility(View.VISIBLE);
                tvCustermMsg.setText(refundBeanData.getData().getReshipLogs().get(0).getInfo());
                break;
            case 0:
                llProductRatifyMoney.setVisibility(View.GONE);
                timeLineView.setPointStrings(data, (float) 1.6);
                ivStatus.setImageResource(R.mipmap.icon_service_time);
                break;
            case 2:
                if (Util.isEmpty(refundBeanData.getData().getReship().getCustomerMemo())) {
                    llCustermMsg.setVisibility(View.GONE);
                } else {
                    llCustermMsg.setVisibility(View.VISIBLE);
                    tvCustermMsg.setText(refundBeanData.getData().getReship().getCustomerMemo());
                }
                if (refundBeanData.getData().getReship().getReceived() == 0) {
                    tvChangeLogist.setVisibility(View.GONE);
                } else {
                    tvChangeLogist.setText(R.string.label_write_logistical);

                    tvChangeLogist.setVisibility(View.VISIBLE);
                    rlCompanyInfo.setVisibility(View.VISIBLE);
                    llLogisticsAlert.setVisibility(View.VISIBLE);   //提示填写物流
                    if(refundBeanData.getData().getReship().getDeliveryExpiredDay()>0){
                        tvLeftTime.setText(getString(R.string.msg_reship_left_time,refundBeanData.getData().getReship().getDeliveryExpiredDay()));//还剩多久关闭售后单
                        llTipWriteLogistic.setVisibility(View.VISIBLE); //提示填写物流时限
                        tvLeftTime.setVisibility(View.VISIBLE);
                    }
                }
                timeLineView.setPointStrings(data, 2);
                break;
            case 4:
                timeLineView.setPointStrings(data, (float) 2.6);
                if (refundBeanData.getData().getReship().getReceived() == 0) {//用户申请退货时选择是否收到货
                    llLogisticsInfo.setVisibility(View.GONE);
                    llLogisticsAddress.setVisibility(View.GONE);
                    rlCancle.setVisibility(View.GONE);
                    tvChangeLogist.setVisibility(View.GONE);
                } else {
                    tvChangeLogist.setText(R.string.label_change_logistical);
                    llLogisticsInfo.setVisibility(View.VISIBLE);
                    tvBackAddress.setVisibility(View.VISIBLE);
                    tvChangeLogist.setVisibility(View.GONE);
                    tvLogisticsInfo.setText(getString(R.string.msg_back_delivery
                            , refundBeanData.getData().getReship().getDeliverySn()
                            , refundBeanData.getData().getReship().getDeliveryCorpName()));
                    if (refundBeanData.getData().getReship().getBackAddress() != null) {
                        tvLogisticsAddress.setText(getString(R.string.msg_back_adress
                                , refundBeanData.getData().getReship().getBackAddress().getAddress()
                                , refundBeanData.getData().getReship().getBackAddress().getConsignee()
                                , refundBeanData.getData().getReship().getBackAddress().getMobile()));
                        llLogisticsAddress.setVisibility(View.VISIBLE);
                    }
                    tvActionCancle.setText(R.string.label_show_logistical);
                }

                break;
            case 8://退货完成,进度变为退款进度
                tvStatusName.setText(refundBeanData.getData().getRefund().getStatusName());
                timeLineView.setPointStrings(data, (float) 3.6);
                if (Util.isEmpty(refundBeanData.getData().getRefund().getCustomerMemo())) { //显示退款状态的客服备注
                    llCustermMsg.setVisibility(View.GONE);
                } else {
                    llCustermMsg.setVisibility(View.VISIBLE);
                    tvCustermMsg.setText(refundBeanData.getData().getRefund().getCustomerMemo());
                }

                if (refundBeanData.getData().getRefund().getStatusCode() == -1 || refundBeanData.getData().getRefund().getStatusCode() == 1) {  //拒绝的取log的info
                    if (!Util.isEmpty(refundBeanData.getData().getRefundLogs().get(0).getInfo())) {
                        llCustermMsg.setVisibility(View.VISIBLE);
                        tvCustermMsg.setText(refundBeanData.getData().getRefundLogs().get(0).getInfo());
                    }

                }
                if (refundBeanData.getData().getRefund().getStatusCode() == 8) {
                    ivStatus.setImageResource(R.mipmap.icon_service_success);
                    timeLineView.setPointStrings(data, 4);
                    if (refundBeanData.getData().getRefund().getPayDate() != null) {
                        llCustermMsg.setVisibility(View.VISIBLE);
                        ivCustermDot.setVisibility(View.INVISIBLE);
                        tvCustermMsg.setText(getString(R.string.msg_refund_success
                                , refundBeanData.getData().getRefund().getPaySn()
                                , DateUtil.ConverToString(refundBeanData.getData().getRefund().getPayDate())
                                , String.valueOf(refundBeanData.getData().getRefund().getPayMoney())));
                    }
                }
                rlCancle.setVisibility(View.GONE);
                break;
        }

    }

    private void initTimeLine(RefundBeanData refundBeanData) {
        data = new ArrayList<>();
        data.add("申请退货");
        data.add("寄回商品");
        data.add("D2C收货");
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
                imgHint.setVisibility(View.GONE);
                btnReload.setVisibility(View.GONE);
                svRoot.setVisibility(View.VISIBLE);
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
                startActivity(new Intent(ReshipDetailActivity.this, AfterSaleProgressDetailActivity.class)
                        .putExtra("title", "退款退货进度")
                        .putExtra("refundLogs", (Serializable) refundBeanData.getData().getRefundLogs())
                        .putExtra("reshipLogs", (Serializable) refundBeanData.getData().getReshipLogs())
                        .putExtra("id", refundBeanData.getData().getReship().getId()));
                break;
            case R.id.tv_change_logist:
                //编辑物流信息
                Intent intent = new Intent(ReshipDetailActivity.this, ChangeLogisticsInfoActivity.class)
                        .putExtra("id", refundBeanData.getData().getReship().getId())
                        .putExtra("img", refundBeanData.getData().getReship().getProductImg())
                        .putExtra("name", refundBeanData.getData().getReship().getProductName())
                        .putExtra("color", refundBeanData.getData().getReship().getProductColor())
                        .putExtra("action", tvChangeLogist.getText())
                        .putExtra("type", 0)
                        .putExtra("size", refundBeanData.getData().getReship().getProductSize());
                startActivityForResult(intent, Constants.RequestCode.WRITE_LOGSTICS);
                break;
            case R.id.tv_action_cancle:
                if ("查看物流".equals(tvActionCancle.getText())) {
                    //查看物流详情
                    Util.urlAction(ReshipDetailActivity.this, String.format(Constants.WULIU_URL,
                            refundBeanData.getData().getReship().getDeliverySn(), refundBeanData.getData().getReship().getDeliveryCorpCode(), refundBeanData.getData().getReship().getProductImg()));
                } else if ("取消申请".equals(tvActionCancle.getText())) {
                    //取消申请
                    new AlertDialog.Builder(ReshipDetailActivity.this)
                            .setMessage(R.string.msg_cancel_after_sale)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestCancelReshipTask(refundBeanData.getData().getReship().getId());
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                }
                break;
        }
    }


    private void requestCancelReshipTask(int id) {
        progressBar.setVisibility(View.VISIBLE);
        CancleReshipApi api = new CancleReshipApi();
        api.setReshipId(id);
        api.setInterPath(Constants.CANCEL_RESHIP_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ReshipInfoBean>() {
            @Override
            public void onResponse(ReshipInfoBean reshipInfoBean) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(ReshipDetailActivity.this, R.string.msg_cancel_after_sale_ok);
                EventBus.getDefault().post(reshipInfoBean.getData().getReship());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(ReshipDetailActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void setEmptyView(int type) {
        rlRootView.setBackgroundColor(getResources().getColor(R.color.color_white));
        svRoot.setVisibility(View.GONE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.RequestCode.WRITE_LOGSTICS) {
                loadData();
                if (refundBeanData != null) {
                    EventBus.getDefault().post(refundBeanData.getData().getReship());
                }
                llCustermMsg.setVisibility(View.GONE);
                llLogisticsAlert.setVisibility(View.GONE);
                llEvidences.removeAllViews();
                rlCompanyInfo.setVisibility(View.GONE);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
