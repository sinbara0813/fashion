package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.PersonInfoActivity;
import com.d2cmall.buyer.activity.ShowDetailActivity;
import com.d2cmall.buyer.api.FollowApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.ClickFollowBean;
import com.d2cmall.buyer.bean.UnFollowBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.holder.FashionFocusItemHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ShowPopImageView;

import java.util.List;

/**
 * 作者:Created by sinbara on 2019/1/14.
 * 邮箱:hrb940258169@163.com
 */

public class FashionHotAdapter extends DelegateAdapter.Adapter  {

    private Context mContext;
    private List<UnFollowBean.DataBean.ActiveMemberBean.ListBean> list;
    private UserBean.DataEntity.MemberEntity user;
    private int designSize;

    public FashionHotAdapter(Context context, List<UnFollowBean.DataBean.ActiveMemberBean.ListBean> list) {
        this.mContext = context;
        this.list = list;
        user = Session.getInstance().getUserFromFile(context);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 3) {
            View view=LayoutInflater.from(mContext).inflate(R.layout.layout_textview1,parent,false);
            return new HFRecyclerViewAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_focus_item, parent, false);
            return new FashionFocusItemHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) != 3) {
            final FashionFocusItemHolder fashionFocusItemHolder = (FashionFocusItemHolder) holder;
            final UnFollowBean.DataBean.ActiveMemberBean.ListBean listBean = list.get(position - 1);
            if (position==1||position==designSize+1){
                fashionFocusItemHolder.llTitle.setVisibility(View.VISIBLE);
                if (position==1){
                    fashionFocusItemHolder.tvTitle.setText("设计师");
                    fashionFocusItemHolder.tvTag.setText("DESIGNER");
                }else {
                    fashionFocusItemHolder.tvTitle.setText("最IN达人");
                    fashionFocusItemHolder.tvTag.setText("TALENT");
                }
            }else {
                fashionFocusItemHolder.llTitle.setVisibility(View.GONE);
            }
            UniversalImageLoader.displayRoundImage(mContext, listBean.getHeadPic(), fashionFocusItemHolder.imgAvatar, R.mipmap.ic_default_avatar);
            fashionFocusItemHolder.nameTv.setText(listBean.getNickName());
            fashionFocusItemHolder.imgAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PersonInfoActivity.class);
                    intent.putExtra("memberId", (long) listBean.getMemberId());
                    mContext.startActivity(intent);
                }
            });
            if (user != null) {
                if (listBean.getMemberId() == user.getMemberId()) {
                    fashionFocusItemHolder.focusIv.setVisibility(View.GONE);
                }
            }
            switch (listBean.getFollow()) {
                case 0:
                    fashionFocusItemHolder.focusIv.setImageResource(R.mipmap.button_fashion_care);
                    break;
                case 1:
                    fashionFocusItemHolder.focusIv.setImageResource(R.mipmap.button_fashion_cared);
                    break;
                case 2:
                    fashionFocusItemHolder.focusIv.setImageResource(R.mipmap.button_fashion_mutualcare);
                    break;
            }
            //开启消息推送行为节点
            fashionFocusItemHolder.focusIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Util.loginChecked((Activity) mContext, 999)) {
                        focusComplete(listBean, fashionFocusItemHolder.focusIv);
                    }
                }
            });
            int size = listBean.getShares().size() > 3 ? 3 : listBean.getShares().size();
            for (int i = 0; i < size; i++) {
                final UnFollowBean.DataBean.ActiveMemberBean.ListBean.SharesBean sharesBean = listBean.getShares().get(i);
                if (i == 0) {
                    fashionFocusItemHolder.image1.setVisibility(View.VISIBLE);
                    if (sharesBean.getPics() != null && sharesBean.getPics().size() > 0) {
                        UniversalImageLoader.displayImage(mContext, sharesBean.getPics().get(0), fashionFocusItemHolder.image1);
                    }
                    if (!Util.isEmpty(sharesBean.getVideo())) {
                        fashionFocusItemHolder.imageTag1.setVisibility(View.VISIBLE);
                    }
                    fashionFocusItemHolder.image1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            toActivity((long) sharesBean.getId());
                        }
                    });
                } else if (i == 1) {
                    fashionFocusItemHolder.image2.setVisibility(View.VISIBLE);
                    if (sharesBean.getPics() != null && sharesBean.getPics().size() > 0) {
                        UniversalImageLoader.displayImage(mContext, sharesBean.getPics().get(0), fashionFocusItemHolder.image2);
                    }
                    if (!Util.isEmpty(sharesBean.getVideo())) {
                        fashionFocusItemHolder.imageTag2.setVisibility(View.VISIBLE);
                    }
                    fashionFocusItemHolder.image2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            toActivity((long) sharesBean.getId());
                        }
                    });
                } else if (i == 2) {
                    fashionFocusItemHolder.image3.setVisibility(View.VISIBLE);
                    if (sharesBean.getPics() != null && sharesBean.getPics().size() > 0) {
                        UniversalImageLoader.displayImage(mContext, sharesBean.getPics().get(0), fashionFocusItemHolder.image3);
                    }
                    if (!Util.isEmpty(sharesBean.getVideo())) {
                        fashionFocusItemHolder.imageTag3.setVisibility(View.VISIBLE);
                    }
                    fashionFocusItemHolder.image3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            toActivity((long) sharesBean.getId());
                        }
                    });
                }
            }
        }
    }

    public void setDesignSize(int size){
        designSize=size;
    }

    private void focusComplete(final UnFollowBean.DataBean.ActiveMemberBean.ListBean listBean, final ShowPopImageView imageView) {
        FollowApi api = new FollowApi();
        api.setToMemberId((long) listBean.getMemberId());
        if (listBean.getFollow() > 0) {
            api.setInterPath(Constants.DELETE_MY_FOLLOWS_URL);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                @Override
                public void onResponse(BaseBean response) {
                    listBean.setFollow(0);
                    Util.showToast(mContext, "取消关注成功");
                    imageView.setImageResource(R.mipmap.button_fashion_care);
                }
            });
        } else {
            api.setInterPath(Constants.INSERT_MY_FOLLOWS_URL);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ClickFollowBean>() {
                @Override
                public void onResponse(ClickFollowBean clickFollowBean) {
                    listBean.setFollow(clickFollowBean.getData().getFollow());
                    if (clickFollowBean.getData().getFollow() == 0) {
                        imageView.setImageResource(R.mipmap.button_fashion_care);
                    } else if (clickFollowBean.getData().getFollow() == 1) {
                        imageView.setImageResource(R.mipmap.button_fashion_cared);
                    } else if (clickFollowBean.getData().getFollow() == 2) {
                        imageView.setImageResource(R.mipmap.button_fashion_mutualcare);
                    }
                    Util.showToast(mContext, "关注成功");
                    imageView.showMsgPop((Activity) mContext,mContext.getString(R.string.label_pop_focus_people));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Util.showToast(mContext, Util.checkErrorType(error));
                }
            });
        }

    }

    private void toActivity(long id) {
        Intent intent = new Intent(mContext, ShowDetailActivity.class);
        intent.putExtra("id", id);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 3;
        } else {
            return 4;
        }
    }
}
