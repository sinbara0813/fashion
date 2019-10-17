package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.LooksDetailActivity;
import com.d2cmall.buyer.activity.VideoRecordActivity;
import com.d2cmall.buyer.bean.MyWearCollocationBean;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.RoundLayout;
import com.d2cmall.buyer.widget.RoundedImageView;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayer;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayerManager;
import com.d2cmall.buyer.widget.nicevideo.TxVideoPlayerController;

import java.util.List;

/**
 * Created by Administrator on 2018/11/19.
 * Description : WardrobeTopVpAdapter
 */

public class WardrobeTopVpAdapter extends PagerAdapter {
    private Context mContext;
    private RoundedImageView mImageView;
    private TextView mTvDate;
    private TextView mTvTime;
    private NiceVideoPlayer niceVideoPlayer;

    private List<MyWearCollocationBean.DataBean.MyWardrobeCollocationsBean.ListBean> myWearList;
    private AlertDialog alertDialog;
    private RoundLayout roundLayout;

    public WardrobeTopVpAdapter(Context context, List<MyWearCollocationBean.DataBean.MyWardrobeCollocationsBean.ListBean> myWearList) {
        this.mContext=context;
        this.myWearList=myWearList;
    }

    @Override
    public int getCount() {
        if(myWearList==null || myWearList.size()<=0){
            return 0;
        }else {
            return myWearList.size();
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_wardrobe_vp_item, container, false);
        mImageView = (RoundedImageView) view.findViewById(R.id.image);
        mTvDate = (TextView) view.findViewById(R.id.tv_date);
        mTvTime = (TextView) view.findViewById(R.id.tv_time);
        niceVideoPlayer = (NiceVideoPlayer) view.findViewById(R.id.nice_video_player);
        roundLayout = view.findViewById(R.id.round);
        MyWearCollocationBean.DataBean.MyWardrobeCollocationsBean.ListBean listBean = myWearList.get(position);
        String month = listBean.getTransactionTime().substring(listBean.getTransactionTime().indexOf("-")+1, listBean.getTransactionTime().lastIndexOf("-"));
        String day = listBean.getTransactionTime().substring(listBean.getTransactionTime().lastIndexOf("-")+1, listBean.getTransactionTime().indexOf(" "));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(month);
            stringBuilder.append("/");
            stringBuilder.append(day);
            String s = stringBuilder.toString();
            int length = s.length();
            //字体大小不一样
            SpannableString textSpan = new SpannableString(s);
            textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(24)), 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(12)), 2, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            mTvDate.setText(textSpan);
            mTvDate.setVisibility(View.VISIBLE);

        if(listBean.getCurDayTotal()==0){
            mTvTime.setVisibility(View.GONE);
        }else{
            mTvTime.setVisibility(View.VISIBLE);
            mTvTime.setText(listBean.getCurDayTotal()+"p");
        }
        niceVideoPlayer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                NiceVideoPlayerManager.instance().stopNiceVideoPlayer();
                return false;
            }
        });
        if(!Util.isEmpty(listBean.getVideo())){
            niceVideoPlayer.setVisibility(View.VISIBLE);
            roundLayout.setVisibility(View.VISIBLE);
            mImageView.setVisibility(View.GONE);
            String videoUrl = listBean.getVideo();
            if (!videoUrl.startsWith("http")) {
                videoUrl = Constants.IMG_HOST + videoUrl;
            }
            TxVideoPlayerController txVideoPlayerController = new TxVideoPlayerController(mContext);
            txVideoPlayerController.setBanFullScreen(true);
            niceVideoPlayer.setController(txVideoPlayerController);
            if (listBean.getPics() != null && listBean.getPics().size() > 0) {
                UniversalImageLoader.displayImage(mContext, Util.getD2cPicUrl(listBean.getPics().get(0)), txVideoPlayerController.getImage());
            }
            niceVideoPlayer.setUp(videoUrl, null);
        }else{
            roundLayout.setVisibility(View.GONE);
            niceVideoPlayer.setVisibility(View.GONE);
            mImageView.setVisibility(View.VISIBLE);
        }
        if(listBean.getPics()!=null && listBean.getPics().size()>0){
            UniversalImageLoader.displayImage(mContext, Util.getD2cPicUrl(listBean.getPics().get(0),255,365),mImageView,R.mipmap.pic_mywear_none);
        }else {
            UniversalImageLoader.displayImage(mContext,mImageView,R.mipmap.pic_mywear_none);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listBean.getPics()!=null && listBean.getPics().size()>0){
                    Intent intent=new Intent(mContext, LooksDetailActivity.class);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(listBean.getTransactionTime().substring(0, listBean.getTransactionTime().indexOf(" ") + 1));
                    stringBuilder.append("00:00:00");
                    intent.putExtra("date",stringBuilder.toString());
                    mContext.startActivity(intent);
                }else {
                    boolean hasShowTakePhotoGuide = D2CApplication.mSharePref.getSharePrefBoolean("hasShowTakePhotoGuide", false);
                    if(!hasShowTakePhotoGuide){
                        showDialog(listBean,position);
                        D2CApplication.mSharePref.putSharePrefBoolean("hasShowTakePhotoGuide", true);
                    }else {
                        Intent intent=new Intent(mContext, VideoRecordActivity.class);
                        intent.putExtra("transactionTime",listBean.getTransactionTime());
                        intent.putExtra("position",position);
                        intent.putExtra("channel","wardrobe");
                        mContext.startActivity(intent);
                    }

                }
            }
        });
        container.addView(view);
        return view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    private void showDialog(MyWearCollocationBean.DataBean.MyWardrobeCollocationsBean.ListBean listBean, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = ((Activity)mContext).getLayoutInflater().inflate(R.layout.layout_guide_take_photo, null);
        TextView tvType = view.findViewById(R.id.tv_type);
        ImageView ivGuide = view.findViewById(R.id.iv_guide);
        TextView tvStep = view.findViewById(R.id.tv_step);
        TextView tvDismiss = view.findViewById(R.id.tv_dismiss);
        TextView tvNext = view.findViewById(R.id.tv_next);
        LinearLayout llAction = view.findViewById(R.id.ll_action);
        TextView tvTakePhoto = view.findViewById(R.id.tv_take_photo);
        tvDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog != null && alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
            }
        });
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llAction.setVisibility(View.GONE);
                tvTakePhoto.setVisibility(View.VISIBLE);
                ivGuide.setImageResource(R.mipmap.pic_pszn02);
                tvStep.setText("2/2");
                tvType.setText("背景整洁");
            }
        });
        tvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, VideoRecordActivity.class);
                intent.putExtra("transactionTime",listBean.getTransactionTime());
                intent.putExtra("position",position);
                intent.putExtra("channel","wardrobe");
                mContext.startActivity(intent);
                if (alertDialog != null ) {
                    alertDialog.dismiss();
                }
            }
        });
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
