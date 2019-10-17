package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.widget.GuideLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by rookie on 2018/1/30.
 */

public class FinishDeleteAccountActivity extends BaseActivity {

    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.text)
    TextView text;
    @Bind(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_delete_account);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        GuideLayout.back();
        EventBus.getDefault().post(new ActionBean(Constants.ActionType.CLEAR_ALL_ACTIVITY));
        Intent intent=new Intent(this,HomeActivity.class);
        startActivity(intent);
        ActionBean actionBean=new ActionBean(Constants.ActionType.CHANGE_PAGE);
        actionBean.put("firstIndex",1);
        EventBus.getDefault().post(actionBean);
    }
}
