package com.d2cmall.buyer.binder;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.CollageDetailActivity;
import com.d2cmall.buyer.activity.SimpleImageActivity;
import com.d2cmall.buyer.api.ProductCollectApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.base.BaseViewBinder;
import com.d2cmall.buyer.bean.CollageProductDetailBean;
import com.d2cmall.buyer.holder.PackProductInfoHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.CollageListPop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Dec: D2CNEW
 * Author: hrb
 * Date: 2018/6/21 14:11
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class PackProductInfoBinder implements BaseViewBinder<PackProductInfoHolder> {

    private Context mContext;
    private CollageProductDetailBean detailBean;
    private Handler mHandler;
    private long startTime;
    private long endTime;
    private long parentId;
    public boolean hasSum;
    private int IMAGE_SIZE=0;
    private TextView firstTextView;
    private TextView secondTextView;

    public PackProductInfoBinder(Context context, CollageProductDetailBean detailBean) {
        this.mContext = context;
        this.detailBean=detailBean;
        if (Session.getInstance().getUserFromFile(context)!=null){
            parentId=Session.getInstance().getUserFromFile(context).getPartnerId();
        }
    }

    @Override
    public PackProductInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_pack_product_info, parent, false);
        return new PackProductInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(PackProductInfoHolder productInfoHolder, int position) {
        setData(productInfoHolder);
    }

    private void setData(PackProductInfoHolder productInfoHolder) {
        //设置价格
        setPrice(productInfoHolder);
        //设置商品名称和收藏状态
        setProductName(productInfoHolder);
        setCollect(productInfoHolder);
        //设置分销返利
        setRebate(productInfoHolder);
        //设置拼团分组信息
        setPackInfo(productInfoHolder);
        //设置库存
        setStore(productInfoHolder);
    }

    /**
     * 设置库存
     * @param productInfoHolder
     */
    private void setStore(PackProductInfoHolder productInfoHolder) {
        if (detailBean.getData().getSkuList()==null||detailBean.getData().getSkuList().size()==0){
            return;
        }
        int type=1;
        if (detailBean.getData().getSkuList().size()==1){
            type=3;
        }else if (detailBean.getData().getSkuList().get(0).getSizeId()==0||detailBean.getData().getSkuList().get(0).getColorId()==0){
            type=2;
        }else {
            type=1;
        }
        switch (type){
            case 1:
                doubleCompare(productInfoHolder);
                break;
            case 2:
                singleCompare(productInfoHolder);
                break;
            case 3:
                setOnlyStandard(productInfoHolder);
                break;
        }
    }

    private void singleCompare(PackProductInfoHolder productInfoBinder){
        if (detailBean.getData().getSkuList().get(0).getSizeId()==0){
            Collections.sort(detailBean.getData().getSkuList(), new Comparator<CollageProductDetailBean.DataBean.SkuListBean>() {
                @Override
                public int compare(CollageProductDetailBean.DataBean.SkuListBean o1, CollageProductDetailBean.DataBean.SkuListBean o2) {
                    return o1.getColorId()-o2.getColorId();
                }
            });
            setSingleStandard(productInfoBinder,true);
        }else {
            Collections.sort(detailBean.getData().getSkuList(), new Comparator<CollageProductDetailBean.DataBean.SkuListBean>() {
                @Override
                public int compare(CollageProductDetailBean.DataBean.SkuListBean o1, CollageProductDetailBean.DataBean.SkuListBean o2) {
                    return o1.getSizeId()-o2.getSizeId();
                }
            });
            setSingleStandard(productInfoBinder,false);
        }
    }

    private void doubleCompare(PackProductInfoHolder productInfoHolder){
        HashMap<Integer,List<CollageProductDetailBean.DataBean.SkuListBean>> map=new HashMap<>();
        int size=detailBean.getData().getSkuList().size();
        for (int i=0;i<size;i++){
            CollageProductDetailBean.DataBean.SkuListBean skuListBean=detailBean.getData().getSkuList().get(i);
            if (!map.containsKey(skuListBean.getColorId())){
                List<CollageProductDetailBean.DataBean.SkuListBean> list=new ArrayList<>();
                list.add(skuListBean);
                map.put(skuListBean.getColorId(),list);
            }else {
                List list=map.get(skuListBean.getColorId());
                list.add(skuListBean);
            }
        }
        List<Integer> temp=new ArrayList<>();
        int columnsSize=0;
        for (int key:map.keySet()){
            temp.add(key);
            if (columnsSize<map.get(key).size()){
                columnsSize=map.get(key).size();
            }
        }
        Collections.sort(temp, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.intValue()-o2.intValue();
            }
        });
        int tempSize=temp.size();
        List<String> rowList=new ArrayList<>();
        List<String> columnList=new ArrayList<>();
        String[][] stores=new String[tempSize][columnsSize];
        for (int i=0;i<tempSize;i++){
            List<CollageProductDetailBean.DataBean.SkuListBean> list=map.get(temp.get(i));
            int listSize=list.size();
            Collections.sort(list, new Comparator<CollageProductDetailBean.DataBean.SkuListBean>() {
                @Override
                public int compare(CollageProductDetailBean.DataBean.SkuListBean o1, CollageProductDetailBean.DataBean.SkuListBean o2) {
                    return o1.getSizeId()-o2.getSizeId();
                }
            });
            for (int j=0;j<listSize;j++){
                stores[i][j]=list.get(j).getFlashStore()+"";
                if (i==0){ 
                    columnList.add(list.get(j).getSize());
                }
                if (j==0){
                    rowList.add(list.get(j).getColor());
                }
            }
        }
        setDoubleStandard(productInfoHolder,tempSize,columnsSize,rowList,columnList,stores);
    }

    /**
     * 设置唯一规格库存
     * @param productInfoHolder
     */
    private void setOnlyStandard(PackProductInfoHolder productInfoHolder){
        productInfoHolder.singleStandard.setVisibility(View.VISIBLE);
        productInfoHolder.storeNumTv.setText(detailBean.getData().getSkuList().get(0).getFlashStore()+"件");
    }

    /**
     * 设置单规格库存
     * @param productInfoHolder
     */
    private void setSingleStandard(PackProductInfoHolder productInfoHolder,boolean isColor) {
        productInfoHolder.moreStandard.setVisibility(View.VISIBLE);
        productInfoHolder.storeTagTv.setVisibility(View.VISIBLE);
        int size=detailBean.getData().getSkuList().size();
        LinearLayout.LayoutParams textLL;
        if (size<=6){
            int itemWidth=(ScreenUtil.getDisplayWidth()-ScreenUtil.dip2px(32))/size;
            textLL=new LinearLayout.LayoutParams(itemWidth,-1);
        }else {
            textLL=new LinearLayout.LayoutParams(ScreenUtil.dip2px(50),-1);
        }
        LinearLayout.LayoutParams LL=new LinearLayout.LayoutParams(-1,ScreenUtil.dip2px(30));
        LinearLayout linearLayout=new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.gray_bg));
        for (int i=0;i<size;i++){
            TextView textView=new TextView(mContext);
            textView.setTextColor(mContext.getResources().getColor(R.color.color_black85));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10);
            if (isColor){
                textView.setText(detailBean.getData().getSkuList().get(i).getColor());
            }else {
                textView.setText(detailBean.getData().getSkuList().get(i).getSize());
            }
            textView.setGravity(Gravity.CENTER);
            linearLayout.addView(textView,textLL);
        }
        productInfoHolder.moreStandardLl.addView(linearLayout,LL);
        linearLayout=new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.color_white));
        for (int i=0;i<size;i++){
            TextView textView=new TextView(mContext);
            textView.setTextColor(mContext.getResources().getColor(R.color.color_black85));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10);
            textView.setText(detailBean.getData().getSkuList().get(i).getFlashStore()+"");
            textView.setGravity(Gravity.CENTER);
            linearLayout.addView(textView,textLL);
        }
        productInfoHolder.moreStandardLl.addView(linearLayout,LL);
    }

    /**
     * 设置双规格库存
     * @param productInfoHolder
     */
    private void setDoubleStandard(PackProductInfoHolder productInfoHolder,int rowSize,int columnsSize,List<String> rowList,List<String> columnList,String[][] stores) {
        productInfoHolder.moreStandard.setVisibility(View.VISIBLE);
        productInfoHolder.storeTagTv.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(-1,ScreenUtil.dip2px(30));
        LinearLayout.LayoutParams textLl;
        if (columnsSize<5){
            int itemWidth=(ScreenUtil.getDisplayWidth()-ScreenUtil.dip2px(104))/columnsSize;
            textLl=new LinearLayout.LayoutParams(itemWidth,-1);
        }else {
            textLl=new LinearLayout.LayoutParams(ScreenUtil.dip2px(50),-1);
        }
        boolean colorTag=true;
        for (int i=0;i<rowSize+1;i++){
            LinearLayout linearLayout=new LinearLayout(mContext);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            if (colorTag){
                linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.gray_bg));
            }else {
                linearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.color_white));
            }
            colorTag=!colorTag;
            for (int j=0;j<columnsSize+1;j++){
                TextView textView=new TextView(mContext);
                textView.setTextColor(mContext.getResources().getColor(R.color.color_black85));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10);
                textView.setGravity(Gravity.CENTER);
                if (j==0){
                    LinearLayout.LayoutParams firstLl=new LinearLayout.LayoutParams(ScreenUtil.dip2px(72),-1);
                    if (i==0){//不设置文字

                    }else {
                        textView.setText(rowList.get(i-1));
                    }
                    linearLayout.addView(textView,firstLl);
                }else {
                    if (i==0){
                        textView.setText(columnList.get(j-1));
                    }else {
                        textView.setText(stores[i-1][j-1]);
                    }
                    linearLayout.addView(textView,textLl);
                }
            }
            productInfoHolder.moreStandardLl.addView(linearLayout,ll);
        }
    }

    /**
     * 设置拼团分组信息
     * @param productInfoHolder
     */
    private void setPackInfo(PackProductInfoHolder productInfoHolder) {
        if (detailBean.getData().getGroupList()==null||detailBean.getData().getGroupList().size()==0||detailBean.getData().getCollagePromotion().getPromotionStatus()==-1){
            return;
        }
        productInfoHolder.packLl.setVisibility(View.VISIBLE);
        int size=detailBean.getData().getGroupList().size();
        LinearLayout.LayoutParams ll=new LinearLayout.LayoutParams(-1,-2);
        if (size==1){
            ll.setMargins(0,0,0,0);
        }else {
            ll.setMargins(0,0,0, ScreenUtil.dip2px(20));
        }
        View view=LayoutInflater.from(mContext).inflate(R.layout.layout_pack_item,new RelativeLayout(mContext),false);
        ImageView avatar= (ImageView) view.findViewById(R.id.avatar);
        UniversalImageLoader.displayRoundImage(mContext,detailBean.getData().getGroupList().get(0).getHeadPic(),avatar,R.mipmap.ic_default_avatar);
        TextView name= (TextView) view.findViewById(R.id.name);
        name.setText(detailBean.getData().getGroupList().get(0).getInitiatorName());
        TextView num= (TextView) view.findViewById(R.id.num);
        int offer=detailBean.getData().getGroupList().get(0).getMemberCount()-detailBean.getData().getGroupList().get(0).getCurrentMemberCount();
        StringBuilder builder=new StringBuilder();
        builder.append("还差").append(offer).append("人成团");
        SpannableString sb=new SpannableString(builder.toString());
        ForegroundColorSpan colorSpan=new ForegroundColorSpan(Color.parseColor("#fff23365"));
        sb.setSpan(colorSpan,2,4,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        num.setText(sb);
        firstTextView= (TextView) view.findViewById(R.id.time);
        TextView join= (TextView) view.findViewById(R.id.join);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCollageDetail(detailBean.getData().getGroupList().get(0).getId());
            }
        });
        productInfoHolder.packContentLl.addView(view,ll);
        if (size>1){
            productInfoHolder.tvMoreCollages.setVisibility(View.VISIBLE);
            productInfoHolder.tvMoreCollages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //拼团列表弹窗
                    CollageListPop collageListPop = new CollageListPop(mContext, detailBean.getData().getCollagePromotion().getId());
                    collageListPop.show(((Activity)mContext).getWindow().getDecorView());
                }
            });
            ll=new LinearLayout.LayoutParams(-1,-2);
            ll.setMargins(0,0,0,0);
            view=LayoutInflater.from(mContext).inflate(R.layout.layout_pack_item,new RelativeLayout(mContext),false);
            avatar= (ImageView) view.findViewById(R.id.avatar);
            UniversalImageLoader.displayRoundImage(mContext,detailBean.getData().getGroupList().get(1).getHeadPic(),avatar,R.mipmap.ic_default_avatar);
            name= (TextView) view.findViewById(R.id.name);
            name.setText(detailBean.getData().getGroupList().get(1).getInitiatorName());
            num= (TextView) view.findViewById(R.id.num);
            offer=detailBean.getData().getGroupList().get(1).getMemberCount()-detailBean.getData().getGroupList().get(1).getCurrentMemberCount();
            builder=new StringBuilder();
            builder.append("还差").append(offer).append("人成团");
            sb=new SpannableString(builder.toString());
            colorSpan=new ForegroundColorSpan(Color.parseColor("#fff23365"));
            sb.setSpan(colorSpan,2,4,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            num.setText(sb);
            secondTextView= (TextView) view.findViewById(R.id.time);
            join= (TextView) view.findViewById(R.id.join);
            join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toCollageDetail(detailBean.getData().getGroupList().get(1).getId());
                }
            });
            productInfoHolder.packContentLl.addView(view,ll);
        }
    }

    private void toCollageDetail(int collageId){
        Intent intent=new Intent(mContext, CollageDetailActivity.class);
        intent.putExtra("collageId",collageId);
        mContext.startActivity(intent);
    }

    /**
     * 设置返利
     * @param productInfoHolder
     */
    private void setRebate(final PackProductInfoHolder productInfoHolder) {
        if (detailBean.getData().getProduct().getMark()==1&&parentId>0&&detailBean.getData().getProduct().getSecondRatio()>0&&detailBean.getData().getProduct().getGrossRatio()>0){
            double system=1.0;
            try {
                system=Double.valueOf(detailBean.getData().getRatio());
            }catch (Exception e){
                e.printStackTrace();
            }
            double bi= detailBean.getData().getProduct().getSecondRatio()*detailBean.getData().getProduct().getGrossRatio()*system*100;
            String bis=Util.getNumberFormat(bi);
            bi=Double.valueOf(bis);
            bis=String.valueOf((int)(bi+0.5));
            productInfoHolder.fxRl.setVisibility(View.VISIBLE);
            if (hasSum){
                productInfoHolder.fxMatter.setVisibility(View.VISIBLE);
            }
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append("可获得实付").append(bis).append("%返利").append("\n").append("最高可返¥").append(Util.getNumberFormat((int)(bi+0.5)*detailBean.getData().getProduct().getCollagePrice()/100));
            SpannableString sb=new SpannableString(stringBuilder.toString());
            int index=stringBuilder.toString().indexOf("最");
            ForegroundColorSpan colorSpan=new ForegroundColorSpan(mContext.getResources().getColor(R.color.color_red));
            sb.setSpan(colorSpan,index,stringBuilder.toString().length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            RelativeSizeSpan sizeSpan = new RelativeSizeSpan(0.75f);
            sb.setSpan(sizeSpan, index, stringBuilder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            productInfoHolder.fxTv.setText(sb);
            productInfoHolder.fxMatter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Util.urlAction(mContext,"/product/detail/"+detailBean.getData().getProduct().getId()+"?type=summary&parent_id="+parentId);
                }
            });
            productInfoHolder.fxShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadPics();
                }
            });
            productInfoHolder.fxQr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toSimpleActivity(productInfoHolder.price.getText().toString());
                }
            });
        }
    }

    private void toSimpleActivity(CharSequence priceStr){
        String url=Constants.SHARE_URL +"/collage/"+detailBean.getData().getProduct().getCollageId()+"?parent_id="+parentId;
        Intent intent=new Intent(mContext, SimpleImageActivity.class);
        intent.putExtra("info",detailBean.getData().getProduct().getName());
        intent.putExtra("promotion","拼团购");
        intent.putExtra("price",priceStr);
        intent.putExtra("url",url);
        intent.putStringArrayListExtra("picUrl", (ArrayList<String>) detailBean.getData().getProduct().getImgs());
        mContext.startActivity(intent);
    }

    /**
     * 下载图片
     */
    private void loadPics(){
        IMAGE_SIZE=0;
        final Dialog dialog= DialogUtil.createLoadingDialog(mContext);
        dialog.show();
        int size=detailBean.getData().getProduct().getImgs().size();
        final List<File> files=new ArrayList<>();
        for (int i=0;i<size;i++){
            int index=detailBean.getData().getProduct().getImgs().get(i).lastIndexOf(".");
            int type=1;
            if (index+2<detailBean.getData().getProduct().getImgs().get(i).length()){
                String s=detailBean.getData().getProduct().getImgs().get(i).substring(index+1,index+2);
                if (s.equals("j")){
                    type=2;
                }
            }
            final int finalType = type;
            Glide.with(mContext).load(Util.getD2cPicUrl(detailBean.getData().getProduct().getImgs().get(i))).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    if (resource!=null){
                        IMAGE_SIZE++;
                    }
                    String url=null;
                    long partnerId=0;
                    if (Session.getInstance().getUserFromFile(mContext)!=null){
                        partnerId=Session.getInstance().getUserFromFile(mContext).getPartnerId();
                    }
                    if (partnerId>0){
                        url=Constants.SHARE_URL +"/product/"+detailBean.getData().getProduct().getId()+"?parent_id="+partnerId;
                    }else {
                        url = Constants.SHARE_URL + "/product/" + detailBean.getData().getProduct().getId();
                    }
                    Bitmap bitmap=resource.copy(Bitmap.Config.RGB_565,true);
                    files.add(saveFile(bitmap,url, finalType));
                    if (IMAGE_SIZE==detailBean.getData().getProduct().getImgs().size()){
                        dialog.dismiss();
                        try {
                            Intent intent = new Intent();
                            ComponentName comp = new ComponentName("com.tencent.mm",
                                    "com.tencent.mm.ui.tools.ShareToTimeLineUI");
                            intent.setComponent(comp);
                            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                            intent.setType("image/*");

                            ArrayList<Uri> imageUris = new ArrayList<Uri>();
                            for (File f : files) {
                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
                                    imageUris.add(Uri.fromFile(f));
                                }else {
                                    Uri uri =Uri.parse(android.provider.MediaStore.Images.Media.insertImage(mContext.getContentResolver(), f.getAbsolutePath(), f.getName(), null));
                                    imageUris.add(uri);
                                }
                            }
                            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                            String webUrl=Constants.SHARE_URL +"/product/"+detailBean.getData().getProduct().getId()+"?parent_id="+parentId;
                            intent.putExtra("Kdescription", detailBean.getData().getProduct().getName()+webUrl);
                            mContext.startActivity(intent);
                        }catch (Exception e){
                            Toast.makeText(mContext,"抱歉您尚未安装微信",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    /**
     * 添加二维码并保存到文件
     * @param bigBitmap
     * @param url
     * @return
     */
    private File saveFile(Bitmap bigBitmap,String url,int type){
        if (bigBitmap.getByteCount()>8485760){
            float scale=1;
            while (bigBitmap.getByteCount()> 8485760) {
                scale -= 0.2;
                bigBitmap= BitmapUtils.getScaleBitmap(bigBitmap, scale, scale);
            }
        }
        Bitmap scanBitmap = null;
        Canvas canvas = new Canvas(bigBitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        if (canvas.getWidth() <= 100) {
            bigBitmap.recycle();
            return null;
        }
        Paint p = new Paint();
        p.setAntiAlias(true);
        int scanWidth = canvas.getWidth() / 4;
        scanBitmap = BitmapUtils.createWhiteQRImage(url, scanWidth, scanWidth,10);
        canvas.drawBitmap(scanBitmap, canvas.getWidth()- ScreenUtil.dip2px(16)-scanBitmap.getWidth(), canvas.getHeight() - scanBitmap.getHeight() - canvas.getWidth() / 100, p);

        File root = mContext.getExternalCacheDir();
        File dir = new File(root, "wx");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file=null;
        if (type==1){
            file=new File(dir,IMAGE_SIZE+".png");
        }else {
            file=new File(dir,IMAGE_SIZE+".jpg");
        }
        if (file.exists()){
            file.delete();
        }
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            if (type==1){
                bigBitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
            }else {
                bigBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            }

            FileOutputStream os = new FileOutputStream(file);
            os.write(stream.toByteArray());
            stream.flush();
            stream.close();
            os.flush();
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (bigBitmap != null) {
                bigBitmap.recycle();
                bigBitmap=null;
            }
            if (scanBitmap != null) {
                scanBitmap.recycle();
                scanBitmap=null;
            }
            canvas = null;
        }
        return file;
    }

    /**
     * 设置商品名称
     * @param productInfoHolder
     */
    private void setProductName(PackProductInfoHolder productInfoHolder) {
        productInfoHolder.productName.setText(detailBean.getData().getProduct().getName());
        productInfoHolder.collageRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.urlAction(mContext,"/page/Collagerule");
            }
        });
    }

    /**
     * 设置收藏
     * @param productInfoHolder
     */
    private void setCollect(final PackProductInfoHolder productInfoHolder) {
        if ("1".equals(detailBean.getData().getProduct().getCollectioned())) {
            productInfoHolder.productCollectIv.setImageResource(R.mipmap.icon_collect_red);
        } else {
            productInfoHolder.productCollectIv.setImageResource(R.mipmap.icon_collect_black);
        }
        productInfoHolder.productCollectIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) mContext, 0)) {
                    collect(productInfoHolder.productCollectIv);
                }
            }
        });
    }

    private void collect(final ImageView imageView) {
        imageView.setEnabled(false);
        final boolean is;
        ProductCollectApi api = new ProductCollectApi();
        api.productId = detailBean.getData().getProduct().getId();
        if ("0".equals(detailBean.getData().getProduct().getCollectioned())) {
            api.setInterPath(Constants.COLLECT_PRODUCT_URL);
            is = true;
        } else {
            api.setInterPath(Constants.CANCEL_COLLECT_URL);
            is = false;
        }
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                imageView.setEnabled(true);
                if (is) {
                    imageView.setImageResource(R.mipmap.icon_collect_red);
                    detailBean.getData().getProduct().setCollectioned("1");
                    Util.showToast(mContext, "收藏成功");
                } else {
                    imageView.setImageResource(R.mipmap.icon_collect_black);
                    detailBean.getData().getProduct().setCollectioned("0");
                    Util.showToast(mContext, "取消收藏成功");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                imageView.setEnabled(true);
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }

    /**
     * 设置价格
     * @param productInfoHolder
     */
    private void setPrice(PackProductInfoHolder productInfoHolder) {
        if (detailBean.getData().getProduct().getMinPrice()>detailBean.getData().getProduct().getCollagePrice()){
            setPrice(productInfoHolder.price,detailBean.getData().getProduct().getCollagePrice(),detailBean.getData().getProduct().getMinPrice());
        }else {
            setPrice(productInfoHolder.price,detailBean.getData().getProduct().getCollagePrice(),0);
        }
        productInfoHolder.num.setText(detailBean.getData().getCollagePromotion().getMemberCount()+"人拼团购");
        setCollageTime(productInfoHolder);
    }

    private void setCollageTime(final PackProductInfoHolder productInfoHolder){
        if (detailBean.getData().getCollagePromotion().getPromotionStatus()==-1){
            productInfoHolder.time.setVisibility(View.VISIBLE);
            productInfoHolder.time.setText("活动已结束");
            return;
        }
        startTime=detailBean.getData().getCollagePromotion().getBeginDate();
        endTime=detailBean.getData().getCollagePromotion().getEndDate();
        if (startTime>0||endTime>0){
            //设置初始值
            long nowTime=System.currentTimeMillis();
            long startOffer=startTime-nowTime;
            long endOffer=endTime-nowTime;
            if (endOffer>0){
                if (startOffer>0){//活动还没开始
                    setPackTime(startTime,productInfoHolder.time,true);
                }else{  //活动还没结束
                    setPackTime(endTime,productInfoHolder.time,false);
                }
                if (mHandler != null) {
                    mHandler.removeCallbacksAndMessages(null);
                    mHandler=null;
                }
                mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        //更改时间
                        long nowTime=System.currentTimeMillis();
                        long startOffer=startTime-nowTime;
                        long endOffer=endTime-nowTime;
                        if (startOffer>0){//活动还没开始
                            setPackTime(startTime,productInfoHolder.time,true);
                        }else if (endOffer>0){  //活动还没结束
                            setPackTime(endTime,productInfoHolder.time,false);
                            if (firstTextView!=null){
                                setGroupTime(detailBean.getData().getGroupList().get(0).getEndDate(),firstTextView);
                            }
                            if (secondTextView!=null){
                                setGroupTime(detailBean.getData().getGroupList().get(1).getEndDate(),secondTextView);
                            }
                        }else { //活动已结束
                            if (mHandler!=null){
                                mHandler.removeCallbacksAndMessages(null);
                                mHandler=null;
                            }
                            productInfoHolder.time.setText("活动已结束");
                        }
                        if (mHandler!=null){
                            mHandler.sendEmptyMessageDelayed(1, 1000);
                        }
                    }
                };
                mHandler.sendEmptyMessageDelayed(1, 1000);
            }else {
                productInfoHolder.time.setText("活动已结束");
            }
        }
    }

    private void setGroupTime(long endTime,TextView textView){
        long offer=endTime-System.currentTimeMillis();
        if (offer<=0){
            textView.setText("已结束");
        }else {
            long hour = offer/(60*60*1000);
            long minute = (offer / (60 * 1000)) % 60;
            long mouse = (offer / 1000) % 60;
            StringBuilder builder=new StringBuilder();
            builder.append("仅剩 ");
            builder.append(addZero((int)hour)).append(":")
                    .append(addZero((int)minute)).append(":")
                    .append(addZero((int)mouse));
            textView.setText(builder.toString());
        }
    }

    private void setPackTime(long time, TextView textView,boolean isStart){
        long offer=time-System.currentTimeMillis();
        long hour = offer/(60*60*1000);
        long minute = (offer / (60 * 1000)) % 60;
        long mouse = (offer / 1000) % 60;
        StringBuilder builder=new StringBuilder();
        if (isStart){
            builder.append("距开始:");
        }else {
            builder.append("距结束:");
        }
        builder.append(addZero((int)hour)).append(":")
                .append(addZero((int)minute)).append(":")
                .append(addZero((int)mouse));
        textView.setText(builder.toString());
    }

    private String addZero(int num) {
        if (num < 10) {
            return "0" + num;
        } else {
            return String.valueOf(num);
        }
    }

    private void setPrice(TextView textView,double price,double orgPrice){
        StringBuilder builder=new StringBuilder();
        builder.append("¥").append(Util.getNumberFormat(price));
        SpannableString spannableString;
        if (orgPrice>0){
            builder.append(" ¥").append(Util.getNumberFormat(orgPrice));
            spannableString = new SpannableString(builder.toString());
            int first=Util.getNumberFormat(price).length();
            int second=Util.getNumberFormat(orgPrice).length()+1;

            RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.3f);
            spannableString.setSpan(sizeSpan, 1, first+1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            RelativeSizeSpan sizeSpan1 = new RelativeSizeSpan(0.6f);
            spannableString.setSpan(sizeSpan1, builder.toString().length()-second, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
            spannableString.setSpan(strikethroughSpan, builder.toString().length()-second, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            StyleSpan styleSpan=new StyleSpan(Typeface.BOLD);
            spannableString.setSpan(styleSpan, 0, first+1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }else {
            spannableString = new SpannableString(builder.toString());
            RelativeSizeSpan sizeSpan = new RelativeSizeSpan(1.3f);
            spannableString.setSpan(sizeSpan, 1, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            StyleSpan styleSpan=new StyleSpan(Typeface.BOLD);
            spannableString.setSpan(styleSpan, 0, builder.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        textView.setText(spannableString);
    }

    @Override
    public void onBindViewHolderWithOffer(PackProductInfoHolder productInfoHolder, int position, int offsetTotal) {

    }
}
