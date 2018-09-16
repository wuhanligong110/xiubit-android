package com.v5ent.xiubit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.toobei.common.entity.CheckMobileRegisterEntity;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.StringUtil;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.HeaderLayout;
import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.MyBaseActivity;
import com.v5ent.xiubit.R;
import com.umeng.analytics.MobclickAgent;

import org.xsl781.ui.view.ClearEditText;
import org.xsl781.utils.SimpleTextWatcher;

/**
 * 公司: tophlc
 * 类说明：Activity-注册界面  填写手机号界面
 *
 * @date 2015-12-25
 */
public class RegisterPhone extends MyBaseActivity implements OnClickListener {

    public static RegisterPhone activity;
    private ClearEditText cedtPhone;
    private String strcedtPhone;
    private Button btnNext;
    private MySimpleTextWatcher textWatcher;
    private TextView textServicephone, textLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_register_phone);
        initView();

        String phoneStr = getIntent().getStringExtra("strcedtPhone");
        if(!TextUtil.isEmpty(phoneStr)) {
            cedtPhone.setText(phoneStr);
            cedtPhone.setSelection(phoneStr.length());
        }
    }

    public void initView() {

        textServicephone = (TextView) findViewById(R.id.text_regist_serviceTelephone);
        textLogin = (TextView) findViewById(R.id.text_login);
        textLogin.setOnClickListener(this);

        textServicephone.setText("客服电话：" + MyApp.getInstance().getDefaultSp().getServiceTelephone());
        btnNext = (Button) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);
        btnNext.setEnabled(false);
        cedtPhone = (ClearEditText) findViewById(R.id.cedt_phone);
        textWatcher = new MySimpleTextWatcher();
        cedtPhone.addTextChangedListener(textWatcher);
        initTopView();
    }

    private void initTopView() {
        headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
        headerLayout.showTitle(R.string.register);
        headerLayout.showLeftBackButton();
        setTranslucentStatus(true);
        headerLayout.setHeadViewCoverStateBar();
    }

    private void sendVcode(final String phone) {
        new MyNetAsyncTask(ctx, true) {
            LoginResponseEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().userSendVcode(null, phone, "1");
            }

            @Override
            protected void onPost(Exception e) {
                // 登录成功
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        Intent intent = new Intent(RegisterPhone.this, RegisterPwdNew.class);
                        intent.putExtra("phone", phone);
                        showActivity(RegisterPhone.this, intent);
                    } else {
                        com.toobei.common.utils.ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    com.toobei.common.utils.ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
                btnNext.setEnabled(true);
            }
        }.execute();
    }

    private void userCheckMobile(final String phone) {
        new MyNetAsyncTask(ctx, true) {
            CheckMobileRegisterEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().userCheckMobile(phone, "");
            }

            @Override
            protected void onPost(Exception e) {
                // 登录成功
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        if (response.getData().getRegFlag().equals("0")) {
                            sendVcode(strcedtPhone);
                        } else if (response.getData().getRegFlag().equals("1")) {
                            // 有限制注册，则对话框提示
                            if (response.getData().getRegLimit().equals("true")) {
                                /*PromptDialog dialog = new PromptDialog(ctx, response.getData()
                                        .getRegLimitMsg(), "确定", null);
								dialog.setListener(new DialogBtnOnClickListener() {

									@Override
									public void onClicked(PromptDialog dialog, boolean isCancel) {
										if (!isCancel) {
											//跳到激活界面 

										}
									}
								});
								dialog.show();*/
                                com.toobei.common.utils.ToastUtil.showCustomToast(response.getData().getRegLimitMsg());
                            } else {
                                Intent intent = new Intent(RegisterPhone.this, RegisterActive.class);
                                intent.putExtra("phone", strcedtPhone);
                                intent.putExtra("regSource", response.getData().getRegSource());
                                showActivity(RegisterPhone.this, intent);
                                dialog.dismiss();
                                finish();
                            }
                        } else if (response.getData().getRegFlag().equals("2")) {
                            ToastUtil.showCustomToast("该号码已注册");
                            finish();
                        }
                    } else {
                        com.toobei.common.utils.ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    com.toobei.common.utils.ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
                btnNext.setEnabled(true);
            }
        }.execute();
    }

    private class MySimpleTextWatcher extends SimpleTextWatcher {
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (cedtPhone.getText().toString().length() == 11) {
                btnNext.setEnabled(true);
            } else {
                btnNext.setEnabled(false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                MobclickAgent.onEvent(ctx,"Q_2_2");
                btnNext.setEnabled(false);
                strcedtPhone = cedtPhone.getText().toString();
                if (!StringUtil.checkPhoneNum(strcedtPhone)) {
                    com.toobei.common.utils.ToastUtil.showCustomToast("亲，请确定你输入的是手机号码！");
                    return;
                }
                userCheckMobile(strcedtPhone);
			/*//测试用
			Intent intent = new Intent(ResetLoginPwdPhone.this, ResetLoginPwdNew.class);
			intent.putExtra("phone", strcedtPhone);
			showActivity(ResetLoginPwdPhone.this,intent);*/
                break;
            case R.id.text_login:
                showActivity(ctx,LoginActivity.class);
                finish();
                break;
        }
    }
}
