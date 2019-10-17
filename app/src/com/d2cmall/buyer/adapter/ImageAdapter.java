package com.d2cmall.buyer.adapter;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.DeleteMatchApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.MatchDetailBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.holder.ImageItemHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.DisposeListener;
import com.d2cmall.buyer.util.Base64;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.SharePop;
import com.d2cmall.buyer.widget.SwitchTypePop;
import com.d2cmall.buyer.widget.nicevideo.TxVideoPlayerController;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;

/**
 * 作者:Created by sinbara on 2018/11/21.
 * 邮箱:hrb940258169@163.com
 */

public class ImageAdapter extends DelegateAdapter.Adapter<ImageItemHolder> {

    private Context mContext;
    private List<MatchDetailBean> data;
    private DisposeListener listener;
    private boolean isSelf=true;
    private HashMap<Integer,Boolean> map=new HashMap<>();
    public ImageAdapter(Context context,DisposeListener listener,List<MatchDetailBean> list,boolean isSelf){
        this.mContext=context;
        this.listener=listener;
        this.data=list;
        this.isSelf=isSelf;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return linearLayoutHelper;
    }

    @Override
    public ImageItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.layout_match_item,parent,false);
        return new ImageItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageItemHolder holder, int position) {
        ((VirtualLayoutManager.LayoutParams)holder.itemView.getLayoutParams()).mAspectRatio= ScreenUtil.getDisplayWidth()*1.0f/(ScreenUtil.getDisplayHeight()-ScreenUtil.getStatusBarHeight(mContext));
        holder.cityTv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ((RelativeLayout.LayoutParams)holder.bg.getLayoutParams()).setMargins(0,holder.cityTv.getTop()-ScreenUtil.dip2px(30),0,0);
                holder.cityTv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        if(!Util.isEmpty(data.get(position).video)){
            holder.contentIv.setVisibility(View.GONE);
            holder.niceVideoPlayer.setVisibility(View.VISIBLE);
            String videoUrl = data.get(position).video;
            if (!videoUrl.startsWith("http")) {
                videoUrl = Constants.IMG_HOST + videoUrl;
            }
            TxVideoPlayerController txVideoPlayerController = new TxVideoPlayerController(mContext);
            txVideoPlayerController.setBanFullScreen(true);
            holder.niceVideoPlayer.setController(txVideoPlayerController);
            if(!Util.isEmpty(data.get(position).pic)){
                UniversalImageLoader.displayImage(mContext, Util.getD2cPicUrl(data.get(position).pic), txVideoPlayerController.getImage());
            }
            holder.niceVideoPlayer.setUp(videoUrl, null);
        }else{
            holder.niceVideoPlayer.setVisibility(View.GONE);
            holder.contentIv.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map.containsKey(position)){
                    boolean is=map.get(position);
                    map.put(position,!is);
                }else {
                    map.put(position,false);
                }
                checkViewState(holder,map.get(position));
            }
        });
        if (map.containsKey(position)){
            checkViewState(holder,map.get(position));
        }
        holder.pb.setVisibility(View.VISIBLE);
        UniversalImageLoader.displayImageByPb(mContext,Util.getD2cPicUrl(data.get(position).pic,ScreenUtil.getDisplayWidth()),R.mipmap.ic_logo_empty6,holder.contentIv,holder.pb);
        if (Util.isEmpty(data.get(position).city)){
            holder.cityTv.setText("搭配日志");
        }else {
            if (Util.isEmpty(data.get(position).temp)){
                holder.cityTv.setText(data.get(position).city);
            }else {
                StringBuilder builder=new StringBuilder();
                builder.append(data.get(position).city).append("·").append(data.get(position).weather).append("·").append(data.get(position).temp).append("°");
                int index=builder.toString().indexOf("·");
                RelativeSizeSpan sizeSpan=new RelativeSizeSpan(0.7f);
                SpannableString sb=new SpannableString(builder.toString());
                sb.setSpan(sizeSpan,index,index+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                sizeSpan=new RelativeSizeSpan(0.7f);
                int lastIndex=builder.toString().lastIndexOf("·");
                sb.setSpan(sizeSpan,lastIndex,lastIndex+1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)  ;
                holder.cityTv.setText(sb);
            }
        }
        holder.dateIv.setText(DateUtil.getChDate(data.get(position).createDate));
        holder.introduceTv.setText(data.get(position).description);
        holder.pageNumberTv.setText((position+1)+"/"+data.size());
        holder.backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof BaseActivity){
                    ((BaseActivity) mContext).finish();
                }
            }
        });
        holder.moreIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchTypePop pop;
                boolean isVideo = !Util.isEmpty(data.get(position).video);
                if (isSelf){
                    pop=new SwitchTypePop(mContext,false,true,isVideo);
                }else {
                    pop=new SwitchTypePop(mContext,false,false,isVideo);
                }
                pop.setClickBack(new SwitchTypePop.ClickBack() {
                    @Override
                    public void clickBack(int type) {
                        switch (type){
                            case 1:
                                if(!Util.isEmpty(data.get(position).video)){
                                    String videoUrl = data.get(position).video;
                                    if (!videoUrl.startsWith("http")) {
                                        videoUrl = Constants.IMG_HOST + videoUrl;
                                    }
                                    saveVideo(videoUrl);
                                }else{
                                    saveWaterMarkFile(data.get(position).pic,((BitmapDrawable)holder.contentIv.getDrawable()).getBitmap(),false,null);
                                }
                                break;
                            case 2:
                                if (holder.contentIv.getDrawable()!=null){
                                    SharePop sharePop1=new SharePop(mContext);
                                    sharePop1.setPic(true);
                                    sharePop1.setDetail(false);
                                    sharePop1.setBigImageUrl(data.get(position).pic);
                                    sharePop1.setBigBitmap(((BitmapDrawable) holder.contentIv.getDrawable()).getBitmap());
                                    sharePop1.show(v);
                                }
                                break;
                            case 3:
                                new AlertDialog.Builder(mContext)
                                        .setMessage("要删除这张图片吗?")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (data.size()==1){
                                                    deletePic(data.get(0).id);
                                                    if (listener!=null){
                                                        listener.dispose(true);
                                                    }
                                                }else {
                                                    MatchDetailBean detailBean=data.remove(position);
                                                    String pic=getPic(detailBean.id);
                                                    if (Util.isEmpty(pic)){
                                                        deletePic(detailBean.id);
                                                    }else {
                                                        updatePic(detailBean.id,pic);
                                                    }
                                                    notifyDataSetChanged();
                                                }
                                            }
                                        })
                                        .setNegativeButton("取消", null)
                                        .show();
                                break;
                        }
                    }
                });
                pop.show(v);
            }
        });
    }

    private void saveVideo(String videoUrl) {
        //创建下载任务,downloadUrl就是下载链接
        int index = videoUrl.lastIndexOf(".");
        String suffix = videoUrl.substring(index , videoUrl.length());
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(videoUrl));
        //指定下载路径和下载文件名
        request.setDestinationInExternalPublicDir("/DCIM/D2C", System.currentTimeMillis() + suffix);
        // 设置为可被媒体扫描器找到
        request.allowScanningByMediaScanner();
        // 设置为可见和可管理
        request.setVisibleInDownloadsUi(true);
        //获取下载管理器
        DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        //将下载任务加入下载队列，否则不会进行下载
        downloadManager.enqueue(request);
        // 获取此次下载的ID
         final long refernece = downloadManager.enqueue(request);
         // 注册广播接收器，当下载完成时自动安装
         IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
         BroadcastReceiver receiver = new BroadcastReceiver() {
             public void onReceive(Context context, Intent intent) {
                   long myDwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                   if (refernece == myDwonloadID) {
                       Util.showToast(mContext, "视频已保存至/DCIM/D2C");
                           }
                   }
        };
        mContext.registerReceiver(receiver, filter);
    }

    private void checkViewState(ImageItemHolder holder,boolean is){
        if (is){
            holder.bg.setVisibility(View.VISIBLE);
            holder.cityTv.setVisibility(View.VISIBLE);
            holder.dateIv.setVisibility(View.VISIBLE);
            holder.introduceTv.setVisibility(View.VISIBLE);
            holder.pageNumberTv.setVisibility(View.VISIBLE);
        }else {
            holder.bg.setVisibility(View.INVISIBLE);
            holder.cityTv.setVisibility(View.INVISIBLE);
            holder.dateIv.setVisibility(View.INVISIBLE);
            holder.introduceTv.setVisibility(View.INVISIBLE);
            holder.pageNumberTv.setVisibility(View.INVISIBLE);
        }
    }

    private void deletePic(int id){
        DeleteMatchApi api=new DeleteMatchApi();
        api.setInterPath(Constants.WARDROBE_DELETE);
        api.id=id;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
            }
        });
    }

    private void updatePic(int id,String pic){
        DeleteMatchApi api=new DeleteMatchApi();
        api.setInterPath(Constants.WARDROBE_UPDATE);
        api.id=id;
        api.pic=pic;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                Util.showToast(mContext,"更新成功");
            }
        });
    }

    private String getPic(int id){
        StringBuilder result=new StringBuilder();
        int size=data.size();
        for (int i=0;i<size;i++){
            if (data.get(i).id==id){
                if (!Util.isEmpty(result.toString())){
                    result.append(",");
                }
                result.append(data.get(i).pic);
            }
        }
        return result.toString();
    }

    public void saveWaterMarkFile(String bigImageUrl, Bitmap bigBitmap, boolean isDetail, String detailUrl) {
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);
        if (!sdCardExist) {
            Util.showToast(mContext, "请确保要内存卡！");
            return;
        }
        /*if (bigBitmap.getByteCount()>8485760){
            Util.showToast(context,"图片太大保存失败!");
            return;
        }*/
        String fileName = Base64.encode(bigImageUrl.getBytes());
        String foldStr = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/D2C";
        File folder = new File(foldStr);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        int index=bigImageUrl.lastIndexOf(".");
        int type=1;
        if (index>0&&index+2<bigImageUrl.length()){
            String s=bigImageUrl.substring(index+1,index+2);
            if (s.equals("j")){
                type=2;
            }
        }
        File file=null;
        if (type==1){
            file = new File(folder, fileName + ".png");
        }else {
            file = new File(folder, fileName + ".jpg");
        }
        if (file.exists()) {
            return;
        }
        if (bigBitmap.getByteCount()>8485760){
            bigBitmap = bigBitmap.copy(Bitmap.Config.RGB_565, true);
            float scale=1;
            while (bigBitmap.getByteCount()> 8485760) {
                scale -= 0.2;
                bigBitmap= BitmapUtils.getScaleBitmap(bigBitmap, scale, scale);
            }
        }else {
            bigBitmap = bigBitmap.copy(Bitmap.Config.ARGB_8888, true);
        }
        Bitmap tagBitmap = null;
        Bitmap scanBitmap = null;
        Canvas canvas = new Canvas(bigBitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        if (canvas.getWidth() <= 100) {
            bigBitmap.recycle();
            Toast.makeText(mContext, "保存失败", Toast.LENGTH_SHORT).show();
            return;
        }
        Paint p = new Paint();
        p.setAntiAlias(true);
        UserBean.DataEntity.MemberEntity memberEntity= Session.getInstance().getUserFromFile(mContext);
        if (memberEntity!=null&&memberEntity.getDistributorId()>0){
            //经销商
        }else {
            if (isDetail&&memberEntity!=null&&memberEntity.getPartnerId()<=0) { //商品详情大图
                int scanWidth = canvas.getWidth() / 5;
                scanBitmap = BitmapUtils.createWhiteQRImage(detailUrl, scanWidth, scanWidth,10);
                canvas.drawBitmap(scanBitmap, canvas.getWidth() / 20-10, canvas.getHeight() - scanBitmap.getHeight() - canvas.getWidth() / 100, p);
                tagBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.sharebottom).copy(Bitmap.Config.ARGB_8888, true);
                tagBitmap = BitmapUtils.getScaleBitmap(tagBitmap, (float) canvas.getWidth() / tagBitmap.getWidth(), (float) canvas.getWidth() / tagBitmap.getWidth());
                canvas.drawBitmap(tagBitmap, canvas.getWidth() - tagBitmap.getWidth(), canvas.getHeight() - tagBitmap.getHeight(), p);
            } else {
                if (!isDetail){
                    tagBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.sharebottom2).copy(Bitmap.Config.ARGB_8888, true);
                    tagBitmap = BitmapUtils.getScaleBitmap(tagBitmap, (float) canvas.getWidth() / tagBitmap.getWidth(), (float) canvas.getWidth() / tagBitmap.getWidth());
                    canvas.drawBitmap(tagBitmap, canvas.getWidth() - tagBitmap.getWidth(), canvas.getHeight() - tagBitmap.getHeight(), p);
                }
            }
        }

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bigBitmap.compress(Bitmap.CompressFormat.PNG, 75, stream);

            FileOutputStream os = new FileOutputStream(file);
            os.write(stream.toByteArray());
            stream.flush();
            stream.close();
            os.flush();
            os.close();
        } catch (Exception ex) {
            file = null;
        } finally {
            if (bigBitmap != null) {
                bigBitmap.recycle();
            }
            if (tagBitmap != null) {
                tagBitmap.recycle();
            }
            if (scanBitmap != null) {
                scanBitmap.recycle();
            }
            canvas = null;
        }
        Util.showToast(mContext, "保存成功!");
        if (file != null) {
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            mContext.sendBroadcast(intent);
        }
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }
}
