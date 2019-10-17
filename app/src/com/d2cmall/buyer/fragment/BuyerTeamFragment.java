package com.d2cmall.buyer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.d2cmall.buyer.activity.BuyerListActivity;
import com.d2cmall.buyer.activity.PerformanceSummaryActivity;
import com.d2cmall.buyer.activity.VisitorListActivity;
import com.d2cmall.buyer.api.BuyerListApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.BuyerListBean;
import com.d2cmall.buyer.bean.BuyerTeamBean;
import com.d2cmall.buyer.bean.BuyerVisitorBean;
import com.d2cmall.buyer.bean.PartnerMemberBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2018/4/13.
 */

public class BuyerTeamFragment extends BaseFragment {

    @Bind(R.id.tv_more_buyer_all)
    TextView tvMoreBuyerAll;
    @Bind(R.id.ll_buyer_title)
    LinearLayout llBuyerTitle;
    @Bind(R.id.tv_all_num)
    TextView tvAllNum;
    @Bind(R.id.ll_all)
    LinearLayout llAll;
    @Bind(R.id.tv_today_add_num)
    TextView tvTodayAddNum;
    @Bind(R.id.ll_today_add)
    LinearLayout llTodayAdd;
    @Bind(R.id.tv_try_period_num)
    TextView tvTryPeriodNum;
    @Bind(R.id.ll_try_period)
    LinearLayout llTryPeriod;
    @Bind(R.id.ll_top_buyer_detail)
    LinearLayout llTopBuyerDetail;
    @Bind(R.id.rl_buyer_detail)
    RelativeLayout rlBuyerDetail;
    @Bind(R.id.tv_more_dm_all)
    TextView tvMoreDmAll;
    @Bind(R.id.ll_dm_title)
    LinearLayout llDmTitle;
    @Bind(R.id.tv_dm_all_num)
    TextView tvDmAllNum;
    @Bind(R.id.ll_dm_all_num)
    LinearLayout llDmAllNum;
    @Bind(R.id.tv_dm_today_add_num)
    TextView tvDmTodayAddNum;
    @Bind(R.id.ll_dm_today_add)
    LinearLayout llDmTodayAdd;
    @Bind(R.id.ll_dm_detail)
    LinearLayout llDmDetail;
    @Bind(R.id.tv_more_sell_rank)
    TextView tvMoreSellRank;
    @Bind(R.id.ll_buyer_sell_rank_title)
    LinearLayout llBuyerSellRankTitle;
    @Bind(R.id.ll_sell_rank_head)
    LinearLayout llSellRankHead;
    @Bind(R.id.tv_more_invite_rank)
    TextView tvMoreInviteRank;
    @Bind(R.id.ll_buyer_invite_rank_title)
    LinearLayout llBuyerInviteRankTitle;
    @Bind(R.id.ll_invite_rank_head)
    LinearLayout llInviteRankHead;
    @Bind(R.id.tv_more_contribution_rank)
    TextView tvMoreContributionRank;
    @Bind(R.id.ll_buyer_contribution_rank_title)
    LinearLayout llBuyerContributionRankTitle;
    @Bind(R.id.ll_contribution_rank_head)
    LinearLayout llContributionRankHead;
    @Bind(R.id.rl_content)
    RelativeLayout rlContent;
    @Bind(R.id.tv_close)
    TextView tvClose;

    private PartnerMemberBean.DataBean.PartnerBean partnerBean;


/*           'createDate|desc':'按加入时间',
             'payCount|desc':'按订单数',
             'payAmount|desc':'按销售额',
             'inviteNum|desc':'按邀请人数',
             'upperRebates|desc':'按收益贡献',
             'consumeDate|desc':'按最后销售时间'*/

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buyer_team, container, false);
    }

    @Override
    public void prepareView() {
        partnerBean = Session.getInstance().getPartnerFromFile(mContext);
        loadTeamData();//拉取团队的买手和DM数据
        loadRankData();
    }

    private void loadRankData() {
        BuyerListApi api = new BuyerListApi();
        api.p = 1;
        if (partnerBean.getLevel() == 0) {//AM
            api.masterId = (long) partnerBean.getId();
        } else {
            api.parentId = (long) partnerBean.getId();
        }
        loadSellRank(api);//按销售额排行
        loadInviteRank(api);//按邀请人数排行
        loadContributionRank(api);//按贡献排行
    }

    private void loadContributionRank(BuyerListApi api) {
        api.sort = "upperRebates|desc";
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BuyerListBean>() {
            @Override
            public void onResponse(BuyerListBean response) {
                List<BuyerListBean.ListBean> list = response.getList();
                if (list != null && list.size() > 0) {
                    addHeanPic(list, llContributionRankHead);
                } else {
                    addEmptyView(llContributionRankHead);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                addEmptyView(llContributionRankHead);
            }
        });
    }

    private int getImage(int position) {
        switch (position) {
            case 0:
                return R.mipmap.icon_one;
            case 1:
                return R.mipmap.icon_two;
            case 2:
                return R.mipmap.icon_three;
        }
        return R.mipmap.icon_one;
    }

    private void addEmptyView(LinearLayout parent) {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setGravity(Gravity.CENTER);

        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(R.mipmap.pic_nocustomer);

        TextView textView = new TextView(mContext);
        textView.setText(R.string.buyer_no_rank);
        textView.setTextColor(mContext.getResources().getColor(R.color.trans_30_color_black));
        textView.setPadding(ScreenUtil.dip2px(4), 0, 0, 0);

        linearLayout.addView(imageView);
        linearLayout.addView(textView);
        parent.addView(linearLayout);
    }

    private void addHeanPic(List<BuyerListBean.ListBean> list, LinearLayout linearLayout) {
        List<BuyerListBean.ListBean> userList = new ArrayList<>();
        if (list.size() > 3) {
            userList.addAll(list.subList(0, 3));
        } else {
            userList.addAll(list);
        }
        for (int i = 0; i < userList.size(); i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_buyer_rank_head, linearLayout, false);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.weight = 1f;
            view.setLayoutParams(params);
            RoundedImageView imageView = (RoundedImageView) view.findViewById(R.id.img_avatar);
            TextView name = (TextView) view.findViewById(R.id.tv_name);
            ImageView imageView1 = (ImageView) view.findViewById(R.id.iv_honor);
            imageView1.setImageResource(getImage(i));
            TextView tvBottom = (TextView) view.findViewById(R.id.tv_bottom);
            if (linearLayout == llSellRankHead) {//销售额
                tvBottom.setText("¥" + wipePoint(userList.get(i).getPayAmount()));
            } else if (linearLayout == llInviteRankHead) {//邀请人数
                tvBottom.setText(userList.get(i).getInviteNum() + "人");
            } else if (linearLayout == llContributionRankHead) {//贡献
                tvBottom.setText("¥" + wipePoint(userList.get(i).getUpperRebates()));
            }
            UniversalImageLoader.displayRoundImage(mContext, userList.get(i).getHeadPic(), imageView, R.mipmap.ic_default_avatar);
            if(!Util.isEmpty(userList.get(i).getName())){
                name.setText(userList.get(i).getName());
            }else{
                name.setText("匿名_"+userList.get(i).getMemberId());
            }

            linearLayout.addView(view);
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

    private void loadInviteRank(BuyerListApi api) {
        api.sort = "inviteNum|desc";
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BuyerListBean>() {
            @Override
            public void onResponse(BuyerListBean response) {
                List<BuyerListBean.ListBean> list = response.getList();
                if (list != null && list.size() > 0) {
                    addHeanPic(list, llInviteRankHead);
                } else {
                    addEmptyView(llInviteRankHead);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                addEmptyView(llInviteRankHead);
            }
        });
    }

    private void loadSellRank(BuyerListApi api) {
        api.sort = "payAmount|desc";
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BuyerListBean>() {
            @Override
            public void onResponse(BuyerListBean response) {
                List<BuyerListBean.ListBean> list = response.getList();
                if (list != null && list.size() > 0) {
                    addHeanPic(list, llSellRankHead);
                } else {
                    addEmptyView(llSellRankHead);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                addEmptyView(llSellRankHead);
            }
        });
    }

    private void loadTeamData() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.BUYER_CHILDREN_DATA);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BuyerTeamBean>() {
            @Override
            public void onResponse(BuyerTeamBean response) {
                List<BuyerTeamBean.DataBean.BuyerDataBean> useList = new ArrayList<>();
                List<BuyerTeamBean.DataBean.TodayDataBean> todayList = new ArrayList<>();

                if (response.getData().getAmData() != null) {//AM数据
                    useList.addAll(response.getData().getAmData());
                } else if(response.getData().getDmData() != null ){//DM数据
                    useList.addAll(response.getData().getDmData());
                }
                if(response.getData().getTodayData() != null){
                    todayList.addAll(response.getData().getTodayData());
                }


                //关店的买手
                int closeBuyerCount = 0;
                for (int i = 0; i < useList.size(); i++) {
                    BuyerTeamBean.DataBean.BuyerDataBean bean = useList.get(i);
                    if (bean.getStatusX() == -1 && bean.getLevel() == 2) {
                        closeBuyerCount = closeBuyerCount + bean.getCount();
                    }
                }

                tvClose.setText(String.valueOf(closeBuyerCount));

                //试用期的买手
                int tryBuyerCount = 0;
                for (int i = 0; i < useList.size(); i++) {
                    BuyerTeamBean.DataBean.BuyerDataBean bean = useList.get(i);
                    if (bean.getStatusX() == 0 && bean.getLevel() == 2) {
                        tryBuyerCount = tryBuyerCount + bean.getCount();
                    }
                }
                tvTryPeriodNum.setText(String.valueOf(tryBuyerCount));

                //今日新增买手
                int todayAddBuyerCount = 0;
                for (int i = 0; i < todayList.size(); i++) {
                    BuyerTeamBean.DataBean.TodayDataBean bean = todayList.get(i);
                    if (bean.getLevel() == 2) {
                        todayAddBuyerCount = bean.getCount();
                    }
                }
                tvTodayAddNum.setText(String.valueOf(todayAddBuyerCount));

                //总数买手
                int totalBuyerCount = 0;
                for (int i = 0; i < useList.size(); i++) {
                    BuyerTeamBean.DataBean.BuyerDataBean bean = useList.get(i);
                    if (bean.getLevel() == 2 && bean.getStatusX() != -9) {
                        totalBuyerCount = totalBuyerCount + bean.getCount();
                    }
                }
                tvAllNum.setText(String.valueOf(totalBuyerCount));

                //今日新增DM
                int todayAddDmCount = 0;
                for (int i = 0; i < todayList.size(); i++) {
                    BuyerTeamBean.DataBean.TodayDataBean bean = todayList.get(i);
                    if (bean.getLevel() == 1) {
                        todayAddDmCount = bean.getCount();
                    }
                }
                tvDmTodayAddNum.setText(String.valueOf(todayAddDmCount));

                //DM总数
                int totalDmCount = 0;
                for (int i = 0; i < useList.size(); i++) {
                    BuyerTeamBean.DataBean.BuyerDataBean bean = useList.get(i);
                    if (bean.getLevel() == 1 && bean.getStatusX() != -9) {
                        totalDmCount = totalDmCount + bean.getCount();
                    }
                }
                tvDmAllNum.setText(String.valueOf(totalDmCount));


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }

    @Override
    public void doBusiness() {
    }

    @OnClick({R.id.tv_more_buyer_all, R.id.tv_today_add_num, R.id.tv_more_dm_all, R.id.tv_more_sell_rank, R.id.tv_more_invite_rank, R.id.tv_more_contribution_rank})
    public void onViewClicked(View view) {
        Intent intent = new Intent(mContext, BuyerListActivity.class);
        switch (view.getId()) {
            case R.id.tv_more_buyer_all:
                intent.putExtra("level", 2);
                mContext.startActivity(intent);
                break;
            case R.id.tv_today_add_num:
                break;
            case R.id.tv_more_dm_all:
                intent.putExtra("level", 1);
                mContext.startActivity(intent);
                break;
            case R.id.tv_more_sell_rank:
                intent.putExtra("sortData", "payAmount|desc");
                mContext.startActivity(intent);
                break;
            case R.id.tv_more_invite_rank:
                intent.putExtra("sortData", "inviteNum|desc");
                mContext.startActivity(intent);
                break;
            case R.id.tv_more_contribution_rank:
                intent.putExtra("sortData", "upperRebates|desc");
                mContext.startActivity(intent);
                break;
        }
    }
}
