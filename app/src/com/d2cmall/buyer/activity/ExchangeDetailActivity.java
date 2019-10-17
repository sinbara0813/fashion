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
import com.d2cmall.buyer.api.CancleExchangeApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.ExchangeInfoBean;
import com.d2cmall.buyer.bean.ExchangeProductDetialBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DateUtil;
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
//换货详情页

public class ExchangeDetailActivity extends BaseActivity {

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
    @Bind(R.id.iv_status)
    ImageView ivStatus;
    @Bind(R.id.tv_status_name)
    TextView tvStatusName;
    @Bind(R.id.ll_after_sale_state)
    LinearLayout llAfterSaleState;
    @Bind(R.id.tv_custerm_msg)
    TextView tvCustermMsg;
    @Bind(R.id.ll_custerm_msg)
    LinearLayout llCustermMsg;
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
    @Bind(R.id.iv_icon_logistics)
    ImageView ivIconLogistics;
    @Bind(R.id.tv_back_title)
    TextView tvBackTitle;
    @Bind(R.id.tv_back_address)
    TextView tvBackAddress;
    @Bind(R.id.rl_company_info)
    RelativeLayout rlCompanyInfo;
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
    @Bind(R.id.line_layout)
    View lineLayout;
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
    @Bind(R.id.ll_evidences)
    LinearLayout llEvidences;
    @Bind(R.id.progress_detail)
    LinearLayout progressDetail;
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
    @Bind(R.id.ll_user_memo)
    LinearLayout llUserMemo;
    private long id;
    private List<String> data;
    ExchangeProductDetialBean exchangeBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sale_detail);
        ButterKnife.bind(this);
        id = getIntent().getLongExtra("exchangeId", 0);
        init();
        loadData();

    }

    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.GET_EXCHANGE_DETAIL_URL, id));

        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ExchangeProductDetialBean>() {
            @Override
            public void onResponse(ExchangeProductDetialBean data) {
                svRoot.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                exchangeBean = data;
                initTimeLine();
                setDataToView(data);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(ExchangeDetailActivity.this, Util.checkErrorType(error));
                setEmptyView(Constants.NET_DISCONNECT);
            }
        });
    }

    private void init() {
        nameTv.setText("换货详情");
        llProductNum.setVisibility(View.GONE);
        llProductMoney.setVisibility(View.GONE);
        llBackMoneyWay.setVisibility(View.GONE);
        titleRight.setBackgroundResource(R.mipmap.tab_all_service);
    }

    private void setDataToView(ExchangeProductDetialBean dataBean) {


        tvChangeLogist.setVisibility(View.GONE);
        llProductNum.setVisibility(View.GONE);
        llProductMoney.setVisibility(View.GONE);
        llProductRatifyMoney.setVisibility(View.GONE);
        tvStatusName.setText(dataBean.getData().getExchange().getStatusName());
        UniversalImageLoader.displayImage(this, Util.getD2cPicUrl(dataBean.getData().getExchange().getProductImg()), imgProduct
                , R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
        tvProductTitle.setText(dataBean.getData().getExchange().getProductName());
        tvProductStyle.setText(getString(R.string.label_kongge, dataBean.getData().getExchange().getProductColor(),
                dataBean.getData().getExchange().getProductSize()));
        tvBackCodeDesc.setText("换货编号:");
        tvBackReasonDesc.setText("换货原因:");
        tvBackCode.setText(dataBean.getData().getExchange().getExchangeSn());
        tvBackReason.setText(dataBean.getData().getExchange().getRefundReason());
        tvRequestTime.setText(DateUtil.ConverToString(DateUtil.strToDateLong(dataBean.getData().getExchange().getCreateDate())));
        if (!Util.isEmpty(dataBean.getData().getExchange().getMemo())) {
            tvQuestion.setText(dataBean.getData().getExchange().getMemo());
        }else{
            llUserMemo.setVisibility(View.GONE);
        }
        tvMoney.setText(String.valueOf(dataBean.getData().getExchange().getTotalAmount()));
        llEvidences.setVisibility(View.GONE);
        tvBackAddress.setText(getString(R.string.msg_back_adress
                , dataBean.getData().getExchange().getBackAddress().getAddress()
                , dataBean.getData().getExchange().getBackAddress().getConsignee()
                , dataBean.getData().getExchange().getBackAddress().getMobile()));
        checkExchangeStatus(dataBean);
    }

    private void checkExchangeStatus(ExchangeProductDetialBean dataBean) {
//        APPLY(0, "正在申请换货"), APPROVE(1, "商家同意换货"),
//                WAITFORRECEIVE(2, "仓库正在收货"), WAITDELIVERED(3, "等待商家换出"), DELIVERED(4, "等待用户收货"),
//                REFUSE(-3, "商家拒绝换货"), MEMBERCLOSE(-2, "用户取消换货"), CLOSE(-1, "商家关闭换货"), SUCCESS(8, "换货成功");
        switch (dataBean.getData().getExchange().getStatusCode()) {
            case -1:
                ivStatus.setImageResource(R.mipmap.icon_service_fail);
                rlCancle.setVisibility(View.GONE);
                timeLineView.setPointStrings(data, 1);
                llCustermMsg.setVisibility(View.VISIBLE);
                tvCustermMsg.setText(exchangeBean.getData().getExchangeLogs().get(0).getInfo());
                break;
            case -2:
                if (exchangeBean.getData().getExchange().getCloseDate() != null) {
                    llCustermMsg.setVisibility(View.VISIBLE);
                    tvCustermMsg.setText(getString(R.string.msg_refund_close_date, DateUtil.ConverToString(exchangeBean.getData().getExchange().getCloseDate())));
                } else {
                    llCustermMsg.setVisibility(View.GONE);
                }
                ivStatus.setImageResource(R.mipmap.icon_service_fail);
                rlCancle.setVisibility(View.GONE);
                timeLineView.setPointStrings(data, (float) 1.6);
                break;
            case -3:
                ivStatus.setImageResource(R.mipmap.icon_service_fail);
                rlCancle.setVisibility(View.GONE);
                timeLineView.setPointStrings(data, (float) 1.6);
                llCustermMsg.setVisibility(View.VISIBLE);
                tvCustermMsg.setText(exchangeBean.getData().getExchangeLogs().get(0).getInfo());
                break;
            case 0:
                llCustermMsg.setVisibility(View.GONE);
                timeLineView.setPointStrings(data, (float) 1.6);
                rlCancle.setVisibility(View.VISIBLE);
                tvChangeLogist.setVisibility(View.GONE);
                break;
            case 1:
                rlCompanyInfo.setVisibility(View.VISIBLE);
                timeLineView.setPointStrings(data, 2);
                llCustermMsg.setVisibility(View.VISIBLE);
                tvCustermMsg.setText(getString(R.string.msg_exchange_info
                        , exchangeBean.getData().getExchange().getProductSn()
                        , exchangeBean.getData().getExchange().getColor()
                        , exchangeBean.getData().getExchange().getSize()));
                rlCancle.setVisibility(View.VISIBLE);
                tvChangeLogist.setVisibility(View.VISIBLE);
                tvChangeLogist.setText(R.string.label_write_logistical);
                break;
            case 2:
                llCustermMsg.setVisibility(View.GONE);
                llLogisticsAddress.setVisibility(View.VISIBLE);
                llLogisticsInfo.setVisibility(View.VISIBLE);
                tvBackAddress.setVisibility(View.VISIBLE);
                tvChangeLogist.setVisibility(View.GONE);
                tvLogisticsInfo.setText(getString(R.string.msg_back_delivery
                        , dataBean.getData().getExchange().getDeliverySn()
                        , dataBean.getData().getExchange().getDeliveryCorpName()));
                tvLogisticsAddress.setText(getString(R.string.msg_back_adress
                        , dataBean.getData().getExchange().getBackAddress().getAddress()
                        , dataBean.getData().getExchange().getBackAddress().getConsignee()
                        , dataBean.getData().getExchange().getBackAddress().getMobile()));
                timeLineView.setPointStrings(data, (float) 2.6);
                tvChangeLogist.setText(R.string.label_change_logistical);
                tvActionCancle.setText(R.string.label_show_logistical);
                rlCancle.setVisibility(View.VISIBLE);
                break;
            case 3:
                llCustermMsg.setVisibility(View.GONE);
                timeLineView.setPointStrings(data, 3);
                rlCancle.setVisibility(View.GONE);
                break;
            case 4:
                llCustermMsg.setVisibility(View.GONE);
                tvLogisticsAddress.setVisibility(View.GONE);
                llLogisticsInfo.setVisibility(View.VISIBLE);
                tvChangeLogist.setVisibility(View.GONE);
                timeLineView.setPointStrings(data, 4);
                tvActionCancle.setText(R.string.label_show_logistical);
                rlCancle.setVisibility(View.VISIBLE);
                tvLogisticsInfo.setText(getString(R.string.msg_back_delivery
                        , exchangeBean.getData().getExchange().getExchangeDeliverySn()
                        , exchangeBean.getData().getExchange().getExchangeDeliveryCorp()));
                break;
            case 8:
                llCustermMsg.setVisibility(View.GONE);
                ivStatus.setImageResource(R.mipmap.icon_service_success);
                timeLineView.setPointStrings(data, 5);
                rlCancle.setVisibility(View.GONE);
                break;
        }
    }


    private void initTimeLine() {
        data = new ArrayList<>();
        data.add("申请退款");
        data.add("寄回商品");
        data.add("D2C收货");
        data.add("寄出商品");
        data.add("换货成功");

    }

    @OnClick({R.id.back_iv, R.id.tv_change_logist, R.id.tv_action_cancle, R.id.btn_reload, R.id.title_right, R.id.progress_detail, R.id.tv_action_copy})
    public void onViewClicked(View view) {
        ClipboardManager cm = null;
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.btn_reload:
                imgHint.setVisibility(View.GONE);
                btnReload.setVisibility(View.GONE);
                loadData();
                break;
            case R.id.progress_detail:
                //跳转进度详情
                startActivity(new Intent(ExchangeDetailActivity.this, AfterSaleProgressDetailActivity.class)
                        .putExtra("title", "退款退货进度")
                        .putExtra("exchangeLogs", (Serializable) exchangeBean.getData().getExchangeLogs())
                        .putExtra("id", exchangeBean.getData().getExchange().getId()));
                break;
            case R.id.title_right: //跳转客服
                if (Util.loginChecked(this, 999)) {
                    String title = "线上客服";
                    String url = "http://www.d2cmall.com";
                    ConsultSource source = new ConsultSource(url, title, "售后");
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
            case R.id.tv_change_logist:
                startActivityForResult(new Intent(ExchangeDetailActivity.this, ChangeLogisticsInfoActivity.class)
                        .putExtra("id", exchangeBean.getData().getExchange().getId())
                        .putExtra("img", exchangeBean.getData().getExchange().getProductImg())
                        .putExtra("name", exchangeBean.getData().getExchange().getProductName())
                        .putExtra("color", exchangeBean.getData().getExchange().getProductColor())
                        .putExtra("action", tvChangeLogist.getText())
                        .putExtra("type", 1)
                        .putExtra("size", exchangeBean.getData().getExchange().getProductSize()), Constants.RequestCode.WRITE_LOGSTICS);
                break;
            case R.id.tv_action_cancle:
                if ("查看物流".equals(tvActionCancle.getText())) {
                    //查看物流详情
                    if (exchangeBean.getData().getExchange().getStatusCode() == 4) {
                        Util.urlAction(ExchangeDetailActivity.this, String.format(Constants.WULIU_URL,
                                exchangeBean.getData().getExchange().getExchangeDeliverySn(), exchangeBean.getData().getExchange().getExchangeDeliveryCode(), exchangeBean.getData().getExchange().getProductImg()));

                    } else {
                        Util.urlAction(ExchangeDetailActivity.this, String.format(Constants.WULIU_URL,
                                exchangeBean.getData().getExchange().getDeliverySn(), exchangeBean.getData().getExchange().getDeliveryCorpCode(), exchangeBean.getData().getExchange().getProductImg()));
                    }


                } else if ("取消申请".equals(tvActionCancle.getText())) {
                    new AlertDialog.Builder(ExchangeDetailActivity.this)
                            .setMessage(R.string.msg_cancel_after_sale)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestCancelAfterSaleTask();
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                }
                break;
        }
    }

    private void requestCancelAfterSaleTask() {
        progressBar.setVisibility(View.VISIBLE);
        CancleExchangeApi api = new CancleExchangeApi();
        api.setExchangeId((int) exchangeBean.getData().getExchange().getId());
        api.setInterPath(Constants.CANCEL_EXCHANGE_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ExchangeInfoBean>() {
            @Override
            public void onResponse(ExchangeInfoBean exchangeInfoBean) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(ExchangeDetailActivity.this, R.string.msg_cancel_after_sale_ok);
                EventBus.getDefault().post(exchangeInfoBean.getData().getExchange());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(ExchangeDetailActivity.this, Util.checkErrorType(error));
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
                llCustermMsg.setVisibility(View.GONE);
                llLogisticsAlert.setVisibility(View.GONE);
                llEvidences.removeAllViews();
                rlCompanyInfo.setVisibility(View.GONE);
                loadData();
                if (exchangeBean != null) {
                    EventBus.getDefault().post(exchangeBean.getData().getExchange());
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
