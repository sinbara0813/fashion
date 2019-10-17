package com.d2cmall.buyer.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

/**
 * 作者:Created by sinbara on 2018/12/6.
 * 邮箱:hrb940258169@163.com
 */

public class ShowSkuColorActivity extends AppCompatActivity {

    @Bind(R.id.view_pager)
    ViewPager viewPager;
    public int position;
    public List<String> pics;
    public List<String> colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looks_detail);
        ButterKnife.bind(this);
        position=getIntent().getIntExtra("position",0);
        pics=getIntent().getStringArrayListExtra("pics");
        colors=getIntent().getStringArrayListExtra("colors");
        Adapter adapter=new Adapter();
        viewPager.setAdapter(adapter);
        if (pics!=null&&position<pics.size()){
            viewPager.setCurrentItem(position);
        }
    }

    class Adapter extends PagerAdapter{

        @Override
        public int getCount() {
            return pics==null?0:pics.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view= LayoutInflater.from(ShowSkuColorActivity.this).inflate(R.layout.layout_sku_color_item,container,false);
            ProgressBar pb=view.findViewById(R.id.pb);
            PhotoView pv=view.findViewById(R.id.pv);
            TextView tv=view.findViewById(R.id.tv);
            TextView pageIndex=view.findViewById(R.id.page_index);
            RelativeLayout rl=view.findViewById(R.id.rl);
            pb.setVisibility(View.VISIBLE);
            String url= Util.getD2cPicUrl(pics.get(position))+ Constants.MY_SUFFIX;
            Glide.with(ShowSkuColorActivity.this).load(url).asBitmap().placeholder(R.mipmap.ic_logo_empty5).error(R.mipmap.ic_logo_empty5).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    pb.setVisibility(View.GONE);
                    pv.setImageBitmap(resource);
                    rl.setVisibility(View.VISIBLE);
                }
            });
            tv.setText(colors.get(position));
            pageIndex.setText((position+1)+"/"+colors.size());
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,R.anim.pic_out);
    }
}
