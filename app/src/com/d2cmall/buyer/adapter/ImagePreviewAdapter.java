package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ImagePreviewActivity;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.util.Base64;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.FileUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.SharePop;
import com.d2cmall.buyer.widget.SwitchTypePop;
import com.d2cmall.buyer.widget.TagParentView;
import com.d2cmall.buyer.widget.ninegrid.ImageInfo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImagePreviewAdapter extends PagerAdapter implements PhotoViewAttacher.OnPhotoTapListener{

    private List<ImageInfo> imageInfo;
    private Context context;
    private View currentView;
    private Handler mHandle;
    private boolean downing;
    private SwitchTypePop switchTypePop;
    private SharePop sharePop1;

    public ImagePreviewAdapter(Context context, @NonNull List<ImageInfo> imageInfo) {
        super();
        this.imageInfo = imageInfo;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageInfo.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        currentView= (View) object;
    }

    public View getPrimaryItem() {
        return currentView;
    }

    public ImageView getPrimaryImageView() {
        return (ImageView) currentView.findViewById(R.id.pv);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_photoview, container, false);
        final ProgressBar pb = (ProgressBar) view.findViewById(R.id.pb);
        pb.setVisibility(View.VISIBLE);
        final PhotoView imageView = (PhotoView) view.findViewById(R.id.pv);
        final ImageView downTag = (ImageView) view.findViewById(R.id.down_tag);
        final TagParentView tagRL = (TagParentView) view.findViewById(R.id.tag_rl);
        LinearLayout addTagLL = (LinearLayout) view.findViewById(R.id.add_tag_ll);
        LinearLayout tagDownLL = (LinearLayout) view.findViewById(R.id.tag_down_ll);
        final ImageInfo info = this.imageInfo.get(position);
        imageView.setOnPhotoTapListener(this);
        UniversalImageLoader.displayImageByPb(context,info.bigImageUrl,R.mipmap.ic_logo_empty5,imageView,pb);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                switchTypePop = new SwitchTypePop(context, true, false,false);

                switchTypePop.setClickBack(new SwitchTypePop.ClickBack() {
                    @Override
                    public void clickBack(int type) {
                        switch (type) {
                            case 1: //保存图片
                                // downLoadPic(info.bigImageUrl);
                                saveWaterMarkFile(info.bigImageUrl,
                                        ((BitmapDrawable) imageView.getDrawable()).getBitmap(), info.isProduct, info.productUrl);
                                break;
                            case 2: //分享图片
                                if (imageView.getDrawable() != null) {
                                    /*if (((BitmapDrawable) imageView.getDrawable()).getBitmap().getByteCount()>8485760){
                                        Util.showToast(context,"图片太大不能分享!");
                                        return;
                                    }*/
                                    sharePop1=new SharePop(context);
                                    sharePop1.setPic(true);
                                    sharePop1.setDetail(info.isProduct);
                                    sharePop1.setBigImageUrl(info.bigImageUrl);
                                    sharePop1.setBigBitmap(((BitmapDrawable) imageView.getDrawable()).getBitmap());
                                    sharePop1.setProductUrl(info.productUrl);
                                    sharePop1.show(imageView);
                                }
                                break;
                            case 3: //添加标签
                                break;
                        }
                    }
                });
                switchTypePop.show(v);
                return true;
            }
        });
        container.addView(view);
        return view;
    }

    public void saveWaterMarkFile(String bigImageUrl, Bitmap bigBitmap, boolean isDetail, String detailUrl) {
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);
        if (!sdCardExist) {
            Util.showToast(context, "请确保要内存卡！");
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
            Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show();
            return;
        }
        Paint p = new Paint();
        p.setAntiAlias(true);
        UserBean.DataEntity.MemberEntity memberEntity= Session.getInstance().getUserFromFile(context);
        if (memberEntity!=null&&memberEntity.getDistributorId()>0){
            //经销商
        }else {
            if (isDetail&&memberEntity!=null&&memberEntity.getPartnerId()<=0) { //商品详情大图
                int scanWidth = canvas.getWidth() / 5;
                scanBitmap = BitmapUtils.createWhiteQRImage(detailUrl, scanWidth, scanWidth,10);
                canvas.drawBitmap(scanBitmap, canvas.getWidth() / 20-10, canvas.getHeight() - scanBitmap.getHeight() - canvas.getWidth() / 100, p);
                tagBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.sharebottom).copy(Bitmap.Config.ARGB_8888, true);
                tagBitmap = BitmapUtils.getScaleBitmap(tagBitmap, (float) canvas.getWidth() / tagBitmap.getWidth(), (float) canvas.getWidth() / tagBitmap.getWidth());
                canvas.drawBitmap(tagBitmap, canvas.getWidth() - tagBitmap.getWidth(), canvas.getHeight() - tagBitmap.getHeight(), p);
            } else {
                if (!isDetail){
                    tagBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.sharebottom2).copy(Bitmap.Config.ARGB_8888, true);
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
        Util.showToast(context, "保存成功!");
        if (file != null) {
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            context.sendBroadcast(intent);
        }
    }

    private void downLoadPic(final String url) {
        createHandle();
        if (downing) {
            Util.showToast(context, "正在下载中");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                downFile(url);
            }
        }).start();
    }

    private void downFile(String bigImageUrl) {
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);
        if (!sdCardExist) {
            mHandle.sendEmptyMessage(1);
            return;
        }
        int index = bigImageUrl.lastIndexOf("/");
        if (index > 0) {
            String name = bigImageUrl.substring(index + 1);
            if (name.indexOf(".") <= 0) {
                name = name + ".png";
            }
            URL url = null;
            try {
                url = new URL(bigImageUrl);
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConn.getInputStream();
                downing = true;
                int state = FileUtil.save2File(context, Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/D2C", name, inputStream);
                if (state == -1) {
                    mHandle.sendEmptyMessage(2);
                } else if (state == 1) {
                    mHandle.sendEmptyMessage(3);
                } else {
                    mHandle.sendEmptyMessage(4);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mHandle.sendEmptyMessage(5);
        }
    }

    private void createHandle() {
        mHandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int what = msg.what;
                switch (what) {
                    case 1:
                        Util.showToast(context, "没有内存卡");
                        break;
                    case 2:
                        Util.showToast(context, "保存失败");
                        downing = false;
                        break;
                    case 3:
                        Util.showToast(context, "已经保存");
                        downing = false;
                        break;
                    case 4:
                        Util.showToast(context, "保存成功");
                        downing = false;
                        break;
                    case 5:
                        Util.showToast(context, "读取失败");
                        break;
                    case 6:
                        Util.showToast(context, "正在下载中");
                        break;
                }
            }
        };
    }

    private PhotoView imageView;
    private ProgressBar pb;

    private void showExcessPic(ImageInfo img, final PhotoView imageView, final ProgressBar pb) {

        if (!img.isPublishPreview()) {

            if (imageInfo.size() == 1) {
                UniversalImageLoader.displayImage(context, img.getSingleUrl(), imageView, R.mipmap.ic_default_pic, R.mipmap.ic_default_pic, new RequestListener() {

                    @Override
                    public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                        pb.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                        pb.setVisibility(View.GONE);
                        return false;
                    }
                },0);
            } else if (imageInfo.size() == 4) {
                UniversalImageLoader.displayImage(context, img.getFourUrl(), imageView, R.mipmap.ic_default_pic, R.mipmap.ic_default_pic);
            } else {
                UniversalImageLoader.displayImage(context, img.getFourUrl(), imageView, R.mipmap.ic_default_pic, R.mipmap.ic_default_pic);
            }

        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void onPhotoTap(View view, float x, float y) {
        ((ImagePreviewActivity) context).finishActivityAnim();
    }

}
