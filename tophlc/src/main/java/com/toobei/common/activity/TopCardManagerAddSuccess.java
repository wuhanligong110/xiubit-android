package com.toobei.common.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.BankCardInfo;
import com.toobei.common.entity.BankCardInfoEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.TextDecorator;
import com.toobei.common.view.HeaderLayout;
import com.toobei.common.view.dialog.PromptDialogCalTel;

import org.xsl781.utils.Logger;

import static com.toobei.common.utils.C.MY_PERMISSIONS_REQUEST_DIAL;

/**
 * 公司: tophlc
 * 类说明：添加银行卡 绑定成功
 *
 * @date 2015-10-13
 */
public abstract class TopCardManagerAddSuccess extends TopBaseActivity implements OnClickListener {

    private Button btnSetting;
    private TextView textBankCardNumber, textBankInfo, textName, textIdentityCardNum;
    //	private String realName, identityCard, cardNo, bankNameAndType;
    //	private boolean isSetPayPwd = true;//设置交易密码
    private String isHasPayPwd;
    private BankCardInfo bankCardInfo;
    private TextView mPhoneNumTv;
    private String mTelstr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    /*	realName = getIntent().getStringExtra("realName");
        identityCard = getIntent().getStringExtra("identityCard");
		cardNo = getIntent().getStringExtra("cardNo");
		bankNameAndType = getIntent().getStringExtra("bankNameAndType");*/
        //	isSetPayPwd = getIntent().getBooleanExtra("isSetPayPwd", true);
//        bankCardInfo = (BankCardInfo) getIntent().getSerializableExtra("bankCardInfo");
        setContentView(R.layout.activity_card_manager_success);
        initView();
        if (bankCardInfo == null) {
            getUserBindCard();
        } else {
            TopApp.getInstance().getAccountService().setMineNeedRefresh(true);
            refreshView();
        }
    }

    protected abstract void skipPwdManagerInitPay();

    public void initView() {
        textBankCardNumber = (TextView) findViewById(R.id.text_bank_card_number);
        textBankInfo = (TextView) findViewById(R.id.text_bank_info);
        textName = (TextView) findViewById(R.id.text_name);
        textIdentityCardNum = (TextView) findViewById(R.id.text_identity_card);
        mPhoneNumTv = (TextView) findViewById(R.id.phoneNumTv);
        btnSetting = (Button) findViewById(R.id.btn_setting_pay_pwd);
        btnSetting.setOnClickListener(this);
        /*if (isSetPayPwd) {
            btnSetting.setVisibility(View.VISIBLE);
		} else {
		}*/
        initTopView();
        //V4.1.1 取消交易密码入口
//        TopApp.getInstance().getAccountService()
//                .checkInitPayPwd(ctx, new UpdateViewCallBack<String>() {
//
//                    @Override
//                    public void updateView(Exception e, String isHasPayPwd) {
//                        if (isHasPayPwd != null && isHasPayPwd.equals("false")) {
//                            btnSetting.setVisibility(View.VISIBLE);
//                            isHasPayPwd = "false";
//                        } else {
//                            btnSetting.setVisibility(View.GONE);
//                            headerLayout.showLeftBackButton();
//                        }
//                    }
//                });

        TextView text_warm_prompt_bank_add = (TextView) findViewById(R.id.text_warm_prompt_bank_add);
        if (text_warm_prompt_bank_add != null) {
            mTelstr = TopApp.getInstance().getDefaultSp().getServiceTelephone();
            if (!TextUtils.isEmpty(mTelstr)) {
                String info = ctx.getString(R.string.warm_prompt_bank_add) + mTelstr;
                TextDecorator.decorate(text_warm_prompt_bank_add,info)
                        .makeTextClickable(new TextDecorator.OnTextClickListener() {
                            @Override
                            public void onClick(View view, String text) {
                                callPhone();
                            }
                        },false, mTelstr)
                        .setTextColor(R.color.text_blue_common,mTelstr)
                .build();
            }
        }

    }

    // 打电话权限
    public void callPhone() {
        int hasCallPhonePermission = ContextCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE);
        Logger.e("hasReadContact获取打电话权限sPermission===>" + hasCallPhonePermission);
        if (hasCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(ctx, Manifest.permission.CALL_PHONE)) {

                ActivityCompat.requestPermissions(ctx, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_DIAL);

            }
            Logger.e("hasReadContact获取打电话权限sPermission===2222222>" + hasCallPhonePermission);
            ActivityCompat.requestPermissions(ctx, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_DIAL);
            return;
        } else {
            PromptDialogCalTel dialog = new PromptDialogCalTel(ctx, false, "确认拨打客服电话?", mTelstr);
            dialog.setBtnPositiveColor(R.color.text_blue_common);
            dialog.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_DIAL:

                Logger.e(permissions.toString());
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    PromptDialogCalTel dialog = new PromptDialogCalTel(ctx, false, "确认拨打客服电话?", mTelstr);
                    dialog.setBtnPositiveColor(R.color.text_blue_common);
                    dialog.show();
                    Logger.e("user granted the permission!");

                } else {
                    Logger.e("user denied the permission!");
                }
                break;
        }

        return;

    }

    private void initTopView() {
        headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
        headerLayout.showTitle("实名认证及银行卡管理");
        headerLayout.showLeftBackButton();
        setTranslucentStatus(true);
        headerLayout.setHeadViewCoverStateBar();
    }

    private void getUserBindCard() {
        new MyNetAsyncTask(ctx, true) {
            BankCardInfoEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = TopApp.getInstance().getHttpService()
                        .accountGetBankCardInfo(TopApp.getInstance().getLoginService().token);
            }

            @Override
            protected void onPost(Exception e) {
                //登录成功
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        //成功
                    /*	realName = response.getData().getUserName();
						identityCard = response.getData().getIdCard();
						cardNo = response.getData().getBankCard();
						bankNameAndType = response.getData().getBankName();*/
                        bankCardInfo = response.getData();
                        refreshView();
                    } else {
                        com.toobei.common.utils.ToastUtil.showCustomToast(response
                                .getErrorsMsgStr());
                    }
                } else {
                    com.toobei.common.utils.ToastUtil
                            .showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }

    private void refreshView() {
        if (bankCardInfo == null) {
            return;
        }
        textBankCardNumber.setText(com.toobei.common.utils.StringUtil.formatBankCard(bankCardInfo.getBankCard()));
        textBankInfo.setText(bankCardInfo.getBankName());
        textName.setText("*" + bankCardInfo.getUserName().substring(1,bankCardInfo.getUserName().length()));
        textIdentityCardNum.setText(com.toobei.common.utils.StringUtil.formatIdCard(bankCardInfo.getIdCard()));
        mPhoneNumTv.setText(bankCardInfo.getMobile().substring(0,3) + "****" + bankCardInfo.getMobile().substring(7, 11));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 按下键盘上返回按钮
        if (keyCode == KeyEvent.KEYCODE_BACK && isHasPayPwd != null && isHasPayPwd.equals("false")) {
            com.toobei.common.utils.ToastUtil.showCustomToast("请设置交易密码");
            //exitBy2Click();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_setting_pay_pwd) {
            skipPwdManagerInitPay();
            //	skipActivity(CardManagerAddSuccess.this, PwdManagerInitPay.class);
        }
    }
}
