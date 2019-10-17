package com.d2cmall.buyer.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.util.HttpUtils;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.tendcloud.tenddata.TCAgent;
import com.zxinsight.MLink;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

public class SplashActivity extends Activity {

    @Bind(R.id.bg)
    ImageView bg;
    @Bind(R.id.jump)
    TextView jump;
    private TimeDown timeDown;
    private Uri mvUri;
    private String clickUrl;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        boolean isFirst = D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.IS_FIRST, true);
        if (isFirst) {
            Intent intent = new Intent(SplashActivity.this, GuideViewActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(android.R.anim.fade_in, R.anim.fade_out);
        } else {
            String picUrls = D2CApplication.mSharePref.getSharePrefString(SharePrefConstant.SPLASH_URL, "");
            if (!Util.isEmpty(picUrls)) {
                String urls[] = picUrls.split(",");
                if (urls.length > 0) {
                    Random random = new Random();
                    int position = random.nextInt(urls.length);
                    clickUrl = D2CApplication.mSharePref.getSharePrefString(SharePrefConstant.SPLASH, "");
                    String picUrl = urls[position];
                    Glide.with(this).load(Util.getD2cPicUrl(picUrl)).skipMemoryCache(true).into(bg);
                    bg.setVisibility(View.VISIBLE);
                    jump.setVisibility(View.VISIBLE);
                    timeDown = new TimeDown(3 * 1000 + 500, 1000);
                    timeDown.start();
                }
            } else {
                enterHome();
            }
        }
    }

    @OnClick({R.id.bg,R.id.jump})
    public void click(View view){
        switch (view.getId()){
            case R.id.bg:
                if (!Util.isEmpty(clickUrl)) {
                    StatService.onEvent(SplashActivity.this, "V3闪屏",  clickUrl);
                    TCAgent.onEvent(SplashActivity.this, "V3闪屏", clickUrl);

                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    intent.putExtra("url", clickUrl);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                }
                break;
            case R.id.jump:
                enterHome();
                break;
        }
    }

    @Subscribe
    public void onEvent(GlobalTypeBean bean) {
        if (bean.getType() == Constants.GlobalType.MMLINK) {
            mvUri = (Uri) bean.getValue("uri");
            enterHome();
        }
    }

    private void upLoadPartnerVisitorEvent(String tempUrl, int parentId) { //买手访问行为
        String url = Constants.API_URL + Constants.POST_BEHAVIOR_EVENT_URL;
        JSONObject tmpObj = null;
        tmpObj = new JSONObject();
        try {
            tmpObj.put("event", "买手店-访问");
            UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(this);
            if (user != null) {
                if (parentId == 0 || user.getPartnerId() == parentId) {
                    return;
                }
                tmpObj.put("targetId", parentId);
                tmpObj.put("targetPath", tempUrl);
            } else {
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String personInfos = tmpObj.toString(); // 将JSONArray转换得到String
        if (Util.isEmpty(personInfos)) {
            return;
        }
        Charset chrutf = Charset.forName("UTF-8");
        String params = new String(personInfos.getBytes(), chrutf);
        try {
            HttpUtils.doPostAsyn(url, params, new HttpUtils.CallBack() {
                @Override
                public void onRequestComplete(final String result) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class TimeDown extends CountDownTimer {

        public TimeDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            if (timeDown != null) {
                timeDown.cancel();
                timeDown = null;
            }
            enterHome();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int secUntilFinished = (int) (millisUntilFinished / 1000);
            StringBuilder builder=new StringBuilder();
            builder.append(String.valueOf(secUntilFinished)).append(" ");
            builder.append("跳过");
            SpannableString sb=new SpannableString(builder.toString());
            RelativeSizeSpan sizeSpan=new RelativeSizeSpan(0.9F);
            sb.setSpan(sizeSpan,0,1,Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            jump.setText(sb);
        }
    }

    private void enterHome() {
        String url = null;

        if (!Util.isEmpty(getIntent().getDataString()) && getIntent().getDataString().contains("d2cmall://")) {
            String dataStrng = getIntent().getDataString();
            try {
                dataStrng = URLDecoder.decode(dataStrng, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            int index = dataStrng.indexOf("url=");
            int index1 = dataStrng.indexOf("&");
            if (index1 > index + 4) {
                url = dataStrng.substring(index + 4, index1);
            } else {
                url = dataStrng.substring(dataStrng.indexOf("url=") + 4, dataStrng.length());
            }
        }

        if (Util.isEmpty(url)&&mvUri==null) {
            //魔窗微信打开app(应用宝通道YYB)
            MLink.getInstance(this).checkYYB(this, null);
        }

        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        if (mvUri != null && mvUri.toString().contains("d2cmall://")) {
            String dataStrng = mvUri.toString();
            try {
                dataStrng = URLDecoder.decode(dataStrng, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            int index = dataStrng.indexOf("url=");
            int index1 = dataStrng.indexOf("&");
            if (index1 > index + 4) {
                url = dataStrng.substring(index + 4, index1);
            } else {
                url = dataStrng.substring(dataStrng.indexOf("url=") + 4, dataStrng.length());
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        mvUri = null;
        if (!Util.isEmpty(url)) {
            intent.putExtra("url", url);
            Matcher matcher = Pattern.compile("\\S+parent_id=(\\d+)").matcher(url);
            if (matcher.find()) {
                try {
                    int parent_id = Integer.parseInt(matcher.group(1));
                    D2CApplication.mSharePref.putSharePrefInteger(SharePrefConstant.PARENT_ID, parent_id);
                    upLoadPartnerVisitorEvent(url, parent_id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
    }


    @Override
    protected void onDestroy() {
        if (timeDown != null) {
            timeDown.cancel();
            timeDown = null;
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
