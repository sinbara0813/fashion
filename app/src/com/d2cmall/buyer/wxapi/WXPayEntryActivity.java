package com.d2cmall.buyer.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.ActionBean;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import de.greenrobot.event.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weixin);
        api = WXAPIFactory.createWXAPI(this, Constants.WEIXINAPPID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            ActionBean actionBean=new ActionBean(Constants.ActionType.WXPAYRESULT);
            switch (baseResp.errCode) {
                case 0:
                    Toast.makeText(this,"支付成功",Toast.LENGTH_SHORT).show();
                    actionBean.put("code",0);
                    break;
                case -1:
                    Toast.makeText(this,"支付失败",Toast.LENGTH_SHORT).show();
                    actionBean.put("code",-1);
                    break;
                case -2:
                    Toast.makeText(this,"支付取消",Toast.LENGTH_SHORT).show();
                    actionBean.put("code",-2);
                    break;
            }
            EventBus.getDefault().post(actionBean);
            finish();
        }
    }

}
