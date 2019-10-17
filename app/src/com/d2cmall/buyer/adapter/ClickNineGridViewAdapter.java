package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ImagePreviewActivity;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ninegrid.ImageInfo;
import com.d2cmall.buyer.widget.ninegrid.NineGridView;
import com.d2cmall.buyer.widget.ninegrid.NineGridViewAdapter;
import com.tendcloud.tenddata.TCAgent;

import java.io.Serializable;
import java.util.List;

public class ClickNineGridViewAdapter extends NineGridViewAdapter {
    public ClickNineGridViewAdapter setFromShow(boolean fromShow) {
        isFromShow = fromShow;
        return this;
    }

    private boolean isFromShow;

    public ClickNineGridViewAdapter(Context context, List<ImageInfo> imageInfo) {
        super(context, imageInfo);
    }

    @Override
    public void onDisplayImage(Context context, ImageView imageView, ImageInfo imageInfo, int childCount) {
        if (childCount == 1) {//单张图
            UniversalImageLoader.displayImage(context,imageInfo.singleUrl, imageView
                    , R.mipmap.ic_logo_empty4, R.mipmap.ic_logo_empty4);
        } else if (childCount == 4) {//四张图
            UniversalImageLoader.displayImage(context,imageInfo.fourUrl, imageView
                    , R.mipmap.ic_logo_empty4, R.mipmap.ic_logo_empty4);
        } else {
            UniversalImageLoader.displayImage(context,imageInfo.thumbnailUrl, imageView
                    , R.mipmap.ic_logo_empty4, R.mipmap.ic_logo_empty4);
        }
    }

    @Override
    protected void onImageItemClick(Context context, NineGridView nineGridView, int index, List<ImageInfo> imageInfo) {
        for (int i = 0; i < imageInfo.size(); i++) {
            ImageInfo info = imageInfo.get(i);
            View imageView;
            if (i < nineGridView.getMaxSize()) {
                imageView = nineGridView.getChildAt(i);
            } else {
                //如果图片的数量大于显示的数量，则超过部分的返回动画统一退回到最后一个图片的位置
                imageView = nineGridView.getChildAt(nineGridView.getMaxSize() - 1);
            }
            info.imageViewWidth = imageView.getWidth();
            info.imageViewHeight = imageView.getHeight();
            int[] points = new int[2];
            imageView.getLocationInWindow(points);
            info.imageViewX = points[0];
            info.imageViewY = points[1] - Util.getStatusHeight(context);
        }

        Intent intent = new Intent(context, ImagePreviewActivity.class);
        Bundle bundle = new Bundle();
        /*if (imageInfo.get(0).getShareId() != 0 && LocalDataUtil.getImageInfo(context, imageInfo.get(0).getShareId()).size() > 0) {
            imageInfo = LocalDataUtil.getImageInfo(context, imageInfo.get(0).getShareId());
        }*/
        bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfo);
        bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, index);
        intent.putExtras(bundle);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(0, 0);
    }

}
