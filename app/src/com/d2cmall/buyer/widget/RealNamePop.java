package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.AddCertificationApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;

/**
 * Created by rookie on 2018/6/7.
 */

public class RealNamePop extends PopupWindow {
    private Context context;
    private int height;
    private View rootView;
    private EditText editName, editNumber;
    private ImageView imageView;
    private Button btn;
    public boolean isRealnameSucceed = false;

    public RealNamePop(Context context) {
        this.context=context;
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_real_name_pop, null);
        editName = (EditText) rootView.findViewById(R.id.edit_name);
        editNumber = (EditText) rootView.findViewById(R.id.edit_number);
        imageView = (ImageView) rootView.findViewById(R.id.iv_close);
        btn = (Button) rootView.findViewById(R.id.btn_commit);
        this.setContentView(rootView);
        this.setWidth(ScreenUtil.screenWidth);
        this.setHeight(ScreenUtil.dip2px(290));
        ColorDrawable cd = new ColorDrawable();
        this.setBackgroundDrawable(cd);
        setAnimationStyle(R.style.showPopupAnimation);
        setFocusable(true);
        this.setOutsideTouchable(false);
        init();
    }


    private void init() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString().trim();
                String number = editNumber.getText().toString().trim();
                AddCertificationApi api=new AddCertificationApi();
                api.setRealName(name);
                api.setIsdefault(1);
                api.setIdentityCard(number);
                D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                    @Override
                    public void onResponse(BaseBean response) {
                        Util.showToast(context,"认证成功");
                        dismiss();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Util.showToast(context,Util.checkErrorType(error));
                    }
                });
            }
        });
    }
}
