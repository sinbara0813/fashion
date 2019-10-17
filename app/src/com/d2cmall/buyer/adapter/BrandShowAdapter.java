package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.BrandDetailActivity;
import com.d2cmall.buyer.activity.PersonInfoActivity;
import com.d2cmall.buyer.activity.ShowDetailActivity;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.BrandShowBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.holder.ShowItemHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.nicevideo.TxVideoPlayerController;
import com.d2cmall.buyer.widget.ninegrid.ImageInfo;
import com.d2cmall.buyer.widget.ninegrid.NineGridView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.d2cmall.buyer.Constants.DELETE_SHARE_LIKE;
import static com.d2cmall.buyer.Constants.LIKE_SHARE_URL;

/**
 * Created by rookie on 2017/9/8.
 */

public class BrandShowAdapter extends DelegateAdapter.Adapter {
    private Context mContext;
    private List<BrandShowBean.DataBean.MembersharesBean.ListBean> datas;

    public BrandShowAdapter(Context mContext,List<BrandShowBean.DataBean.MembersharesBean.ListBean> datas){
        this.mContext=mContext;
        this.datas=datas;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper layoutHelper=new LinearLayoutHelper();
        layoutHelper.setMarginTop(ScreenUtil.dip2px(16));
        layoutHelper.setMarginBottom(ScreenUtil.dip2px(16));
        return layoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.layout_show_item,new LinearLayout(mContext),false);
        return new ShowItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final BrandShowBean.DataBean.MembersharesBean.ListBean listEntity=datas.get(position);
        final ShowItemHolder showItemHolder= (ShowItemHolder) holder;
        UserBean.DataEntity.MemberEntity user= Session.getInstance().getUserFromFile(mContext);
        if(user!=null){
            if(listEntity.getMemberId()==user.getMemberId()){
                showItemHolder.ivFocus.setVisibility(View.GONE);
            }
        }
        if (listEntity.getVerifyDate()==null) {
            showItemHolder.infoTv.setText(String.valueOf(listEntity.getWatchCount()));
        } else {
            if (Util.getFakeWatchCount(listEntity.getVerifyDate()) + listEntity.getWatchCount() > 10000) {
                DecimalFormat df = new DecimalFormat("0.0");
                showItemHolder.infoTv.setText(df.format(((int)Util.getFakeWatchCount(listEntity.getVerifyDate()) + listEntity.getWatchCount()) / (float)10000.0)+"万");
            } else {
                showItemHolder.infoTv.setText(String.valueOf(Util.getFakeWatchCount(listEntity.getVerifyDate()) + listEntity.getWatchCount()));
            }
        }
        showItemHolder.timeTv.setText(DateUtil.getFriendlyTime2(listEntity.getCreateDate()));
        showItemHolder.imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PersonInfoActivity.class);
                intent.putExtra("memberId",(long)listEntity.getMemberId());
                mContext.startActivity(intent);
            }
        });
        showItemHolder.tvDate.setText(DateUtil.getFriendlyTime2(listEntity.getCreateDate()));
        showItemHolder.commentNum.setText(String.valueOf(listEntity.getCommentCount()));
//        switch (listEntity.getFollow()){
//            case 0:
//                showItemHolder.ivFocus.setImageResource(R.mipmap.button_fashion_care);
//                break;
//            case 1:
//                showItemHolder.ivFocus.setImageResource(R.mipmap.button_fashion_cared);
//                break;
//            case 2:
//                showItemHolder.ivFocus.setImageResource(R.mipmap.button_fashion_mutualcare);
//                break;
//        }
        showItemHolder.ivFocus.setVisibility(View.GONE);
//        showItemHolder.ivFocus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                focusComplete(listEntity,showItemHolder);
//            }
//        });
        showItemHolder.likeNum.setText(String.valueOf(listEntity.getLikeMeCount()));
        if(listEntity.getLiked()>0){
            Drawable nav_up=mContext.getResources().getDrawable(R.mipmap.icon_fashion_liked);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            showItemHolder.likeNum.setCompoundDrawables(nav_up, null, null, null);
        }else {
            Drawable nav_up=mContext.getResources().getDrawable(R.mipmap.icon_fashion_like);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            showItemHolder.likeNum.setCompoundDrawables(nav_up, null, null, null);
        }
        showItemHolder.likeNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeShow(listEntity,showItemHolder);
            }
        });
        UniversalImageLoader.displayRoundImage(mContext, Util.getD2cPicUrl(listEntity.getMemberHead()),showItemHolder.imgAvatar, R.mipmap.ic_default_avatar);
        showItemHolder.nameTv.setText(listEntity.getMemberName());
        if (!Util.isEmpty(listEntity.getDescription())) {
            showItemHolder.contentTv.setVisibility(View.VISIBLE);
            showItemHolder.contentTv.setText(listEntity.getDescription());
        } else {
            showItemHolder.contentTv.setVisibility(View.GONE);
        }

        String videoUrl1 = listEntity.getVideo();
        List<String> imgList = listEntity.getPics();
        if(Util.isEmpty(videoUrl1)) {
            showItemHolder.nineGrid.setVisibility(View.VISIBLE);
            setPictures(listEntity,showItemHolder.nineGrid,imgList);
            showItemHolder.videoLayout.setVisibility(View.GONE);
        }else {
            if (!videoUrl1.startsWith("http")) {
                videoUrl1 = Constants.IMG_HOST + videoUrl1;
            }
                    /*TxVideoPlayerController txVideoPlayerController=new TxVideoPlayerController(mContext);
                    showItemHolder.niceVideoPlayer.setController(txVideoPlayerController);*/
            TxVideoPlayerController txVideoPlayerController=new TxVideoPlayerController(mContext);
            showItemHolder.niceVideoPlayer.setController(txVideoPlayerController);
            if (imgList != null && imgList.size() > 0) {
                UniversalImageLoader.displayImage(mContext,Util.getD2cPicUrl(imgList.get(0)),txVideoPlayerController.getImage());
            }
            showItemHolder.niceVideoPlayer.setUp(videoUrl1,null);
            showItemHolder.videoLayout.setVisibility(View.VISIBLE);
            showItemHolder.nineGrid.setVisibility(View.GONE);
            LinearLayout.LayoutParams rl= (LinearLayout.LayoutParams) showItemHolder.videoLayout.getLayoutParams();
            rl.height= ScreenUtil.getDisplayWidth()*200/355;
            int timeLong = listEntity.getTimeLength();
            if (timeLong > 0) {
                int m = timeLong / 60 % 60;
                int s = timeLong % 60;
                StringBuffer sb = new StringBuffer();

                sb.append(String.format("%2d:", m));

                sb.append(String.format("%02d", s));
                txVideoPlayerController.setTime(sb.toString());
            }
        }

        showItemHolder.tvDate.setText(DateUtil.getFriendlyTime2(listEntity.getCreateDate()));
        if (!Util.isEmpty(listEntity.getStreet())) {
            showItemHolder.tvLocation.setVisibility(View.VISIBLE);
            showItemHolder.tvLocation.setText(listEntity.getStreet());
        } else {
            showItemHolder.tvLocation.setVisibility(View.GONE);
        }
        showItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, ShowDetailActivity.class);
                intent.putExtra("id",(long)listEntity.getId());
                mContext.startActivity(intent);
            }
        });
    }

    private void likeShow(final BrandShowBean.DataBean.MembersharesBean.ListBean data, final ShowItemHolder holder) {
        if (Util.loginChecked((BrandDetailActivity)mContext, Constants.Login.EXPLORE_DETAIL_LOGIN)) {
            if (data != null) {
                if (data.getLiked() > 0) {
                    SimpleApi api = new SimpleApi();
                    api.setMethod(BaseApi.Method.POST);
                    api.setInterPath(String.format(DELETE_SHARE_LIKE, data.getId()));
                    final Drawable nav_dowm=mContext.getResources().getDrawable(R.mipmap.icon_fashion_like);
                    nav_dowm.setBounds(0, 0, nav_dowm.getMinimumWidth(), nav_dowm.getMinimumHeight());
                    D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                        @Override
                        public void onResponse(BaseBean response) {
                            holder.likeNum.setText(String.valueOf(Integer.valueOf(holder.likeNum.getText().toString()) - 1));
                            holder.likeNum.setCompoundDrawables(nav_dowm, null, null, null);
                            data.setLiked(0);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Util.showToast(mContext,Util.checkErrorType(error));
                        }
                    });
                } else {
                    final Drawable nav_up=mContext.getResources().getDrawable(R.mipmap.icon_fashion_liked);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    SimpleApi api=new SimpleApi();
                    api.setMethod(BaseApi.Method.POST);
                    api.setInterPath(String.format(LIKE_SHARE_URL,data.getId()));
                    D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                        @Override
                        public void onResponse(BaseBean response) {
                            //holder.setImageResource(R.mipmap.icon_fashion_liked);
                            holder.likeNum.setText(String.valueOf(Integer.valueOf(holder.likeNum.getText().toString()) + 1));
                            holder.likeNum.setCompoundDrawables(nav_up, null, null, null);
                            data.setLiked(1);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                }
            }
        }
    }

    private void setPictures(BrandShowBean.DataBean.MembersharesBean.ListBean listEntity, NineGridView nineGridView, List<String> imgList) {

        ArrayList<ImageInfo> imageInfos = new ArrayList<>();
        if (imgList != null) {
            for (String picUrl : imgList) {
                ImageInfo info = new ImageInfo();
                info.setSingleUrl(Util.getD2cPicUrl(picUrl));//单张图
                info.setFourUrl(Util.getD2cPicUrl(picUrl));//四张图
                info.setThumbnailUrl(Util.getD2cPicUrl(picUrl));//多张缩略图
                info.setBigImageUrl(Util.getD2cPicUrl(picUrl));//大图
                String pic = Util.getD2cPicUrl(picUrl);
                imageInfos.add(info);
            }
        }
        nineGridView.setAdapter(new ClickNineGridViewAdapter(mContext, imageInfos));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
