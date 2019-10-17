package com.d2cmall.buyer.activity;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.util.Base64;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InvitePartnerActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.tag)
    View tag;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.tv_invite_buyer)
    TextView tvInviteBuyer;
    @Bind(R.id.bt_buyer)
    RadioButton btBuyer;
    @Bind(R.id.bt_partner)
    RadioButton btPartner;
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    @Bind(R.id.tv_code)
    TextView tvCode;
    @Bind(R.id.tv_copy_share)
    TextView tvCopyShare;
    private UserBean.DataEntity.MemberEntity user;
    String level="时尚买手";
    private int inviteLevel=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_partner);
        ButterKnife.bind(this);
        user = Session.getInstance().getUserFromFile(this);
        init();
    }

    private void init() {
        nameTv.setText("邀请加入");
        if(user!=null && user.getPartnerId()!=0){
//            if(user.getPartnerLevel()==1){
//                radioGroup.setVisibility(View.VISIBLE);
//                creatShareCode();
//            }else{
//                tvInviteBuyer.setVisibility(View.VISIBLE);
//                creatShareCode();
//            }
            //合伙人只能邀请买手
            tvInviteBuyer.setVisibility(View.VISIBLE);
                creatShareCode();
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(btBuyer.getId() == checkedId){
                    inviteLevel=0;
                    btBuyer.setBackgroundResource(R.drawable.back_invite_partner_button);
                    btPartner.setBackgroundColor(getResources().getColor(R.color.transparent));
                    level="时尚买手";
                    creatShareCode();
                }
                else if(btPartner.getId() == checkedId){
                    inviteLevel=1;
                    btPartner.setBackgroundResource(R.drawable.back_invite_partner_button);
                    btBuyer.setBackgroundColor(getResources().getColor(R.color.transparent));
                    level="合伙人";
                    creatShareCode();
                }
            }
        });
    }

    private void creatShareCode() {
        if(user!=null && user.getPartnerId()!=0){
            String shareCode=null;
            if(inviteLevel==0){
                shareCode = getString(R.string.msg_invite_buyer, user.getPartnerId());
            }else{
               shareCode = getString(R.string.msg_invite_partner, user.getPartnerId());
            }
            byte[] bytes = shareCode.getBytes();
            for (int i=0; i<bytes.length; i++){
                bytes[i]+=2;
            }
            shareCode = Base64.encode(bytes);
            if(inviteLevel==0){
                tvCode.setText(getString(R.string.msg_invite_buyer_content,user.getNickname(),level,shareCode));
            }else{
                tvCode.setText(getString(R.string.msg_invite_partner_content,user.getNickname(),level,shareCode));
            }

        }

    }

    @OnClick({R.id.back_iv, R.id.tv_copy_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.tv_copy_share:
                ClipboardManager cm = (ClipboardManager) InvitePartnerActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setPrimaryClip(ClipData.newPlainText("shareCode",tvCode.getText().toString().trim()));
                //弹窗提示是否跳转微信
                new AlertDialog.Builder(InvitePartnerActivity.this)
                        .setMessage("复制成功，是否打开微信邀请好友")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                jumpToWX();
                            }
                        })
                        .show();
                break;
        }
    }
    private void jumpToWX() {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");

            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            this.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Util.showToast(this, "抱歉您尚未安装微信");
        }
    }
}
