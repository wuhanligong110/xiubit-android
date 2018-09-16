package com.toobei.tb.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.igexin.sdk.PushManager;
import com.toobei.common.entity.LoginResponseData;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.StringUtil;
import com.toobei.common.view.HeaderLayout;
import com.toobei.tb.MyApp;
import com.toobei.tb.MyBaseActivity;
import com.toobei.tb.R;

import org.xsl781.ui.view.ClearEditText;
import org.xsl781.utils.SimpleTextWatcher;
import org.xsl781.utils.StringUtils;

/**
 * 公司: tophlc 类说明： 注册 填密码界面
 *
 * @date 2015-12-29
 */
public class RegisterPwdNew extends MyBaseActivity implements OnClickListener {

    public static RegisterPwdNew activity;
    private ClearEditText cedtVcode, passwordEt, surePasswordEt;
    private String phone;
    private String strPasswd1, strPasswd2;
    private boolean isShowPassword1 = true;
    private boolean isShowPassword2 = true;
    private ImageView eye1Iv, eye2Iv;
    private Button btnComplete, btnResend;
    private MySimpleTextWatcher textWatcher;
    private TextView textTitlePrompt;
    private ImageView checkIv;
    private TextView protocalTv;
    private boolean checkFlag = true;

    protected static final int GET_RESET_SMS_CODE = 0x001;

    private int seconds = 60;
    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_RESET_SMS_CODE:
                    seconds--;
                    if (seconds <= 0) { // 恢复验证码输入
                        seconds = 60;
                        btnResend.setEnabled(true);
                        btnResend.setTextColor(ContextCompat.getColor(ctx,R.color.text_red_common));
                    } else {
                        btnResend.setEnabled(false);
                        btnResend.setTextColor(ContextCompat.getColor(ctx,R.color.text_gray_common));
                        mHandler.sendEmptyMessageDelayed(GET_RESET_SMS_CODE, 1000);
                    }
                    btnResend.setText("重新获取(" + seconds + "s)");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_register_pwd_new);
        mHandler.sendEmptyMessageDelayed(GET_RESET_SMS_CODE, 1000);
        phone = getIntent().getStringExtra("phone");
        initView();
        initData();
    }

    public void initView() {
        protocalTv = (TextView) findViewById(R.id.protocalTv);
        btnComplete = (Button) findViewById(R.id.btn_complete);
        btnComplete.setOnClickListener(this);
        btnComplete.setEnabled(false);
        btnComplete.setBackgroundResource(R.drawable.btn_big_blue_selector);
        btnResend = (Button) findViewById(R.id.btn_resend_sms_vcode);
        btnResend.setOnClickListener(this);
        btnResend.setEnabled(false);
        btnResend.setTextColor(ContextCompat.getColor(ctx,R.color.text_gray_common));
        btnResend.setText("重新获取(60s)");

        cedtVcode = (ClearEditText) findViewById(R.id.cedt_vcode);
        passwordEt = (ClearEditText) findViewById(R.id.passwordEt);
        surePasswordEt = (ClearEditText) findViewById(R.id.surePasswordEt);
        eye1Iv = (ImageView) findViewById(R.id.eye1Iv);
        eye2Iv = (ImageView) findViewById(R.id.eye2Iv);

        textWatcher = new MySimpleTextWatcher();
        cedtVcode.addTextChangedListener(textWatcher);
        passwordEt.addTextChangedListener(textWatcher);
        surePasswordEt.addTextChangedListener(textWatcher);
        eye1Iv.setOnClickListener(this);
        eye2Iv.setOnClickListener(this);
        protocalTv.setOnClickListener(this);

        textTitlePrompt = (TextView) findViewById(R.id.reset_login_pwd_title);
        textTitlePrompt.setText(getString(R.string.sended_sms_vcode)
                + StringUtil.formatPhone(phone));

        checkIv = (ImageView) findViewById(R.id.checkIv);
        checkIv.setOnClickListener(this);
        initTopView();
    }

    private void initTopView() {
        headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
        headerLayout.showTitle(R.string.register);
        headerLayout.showLeftBackButton();
        setTranslucentStatus(true);
        headerLayout.setHeadViewCoverStateBar();
    }

    public void initData() {

    }

    /*
     * 用户注册
     */
    private void userRegister(final String phone, final String password, final String vcode) {
        new MyNetAsyncTask(ctx, true) {
            LoginResponseEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp
                        .getInstance()
                        .getHttpService()
                        .userRegister(phone, password, vcode, PushManager.getInstance().getClientid(ctx));

            }

            @Override
            protected void onPost(Exception e) {
                // 成功
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        LoginResponseData data = response.getData();

                        // 保存token,设置缓存登录信息，更新用户数据
                        MyApp.getInstance().getLoginService().cachLoginInfo(data.getToken(), phone);
                        if (LoginActivity.activity != null) {
                            LoginActivity.activity.finish();
                        }
                        if (RegisterPhone.activity != null) {
                            RegisterPhone.activity.finish();
                        }
                        skipActivity(RegisterPwdNew.this, GestureSetActivity.class);
                    } else {
                        com.toobei.common.utils.ToastUtil.showCustomToast(response
                                .getErrorsMsgStr());
                    }
                } else {
                    com.toobei.common.utils.ToastUtil
                            .showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
                btnComplete.setEnabled(true);
            }
        }.execute();
    }

    /*
     * 发送验证码
     */
    private void sendVcode(final String phone) {
        new MyNetAsyncTask(ctx, false) {
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
                        // 成功
                        mHandler.sendEmptyMessageDelayed(GET_RESET_SMS_CODE, 1000);
                    } else {
                        com.toobei.common.utils.ToastUtil.showCustomToast(response
                                .getErrorsMsgStr());
                        btnResend.setEnabled(true);
                        btnResend.setTextColor(ContextCompat.getColor(ctx,R.color.text_red_common));
                    }
                } else {
                    com.toobei.common.utils.ToastUtil
                            .showCustomToast(getString(R.string.pleaseCheckNetwork));
                    btnResend.setEnabled(true);
                    btnResend.setTextColor(ContextCompat.getColor(ctx,R.color.text_red_common));
                }
            }
        }.execute();
    }

    private class MySimpleTextWatcher extends SimpleTextWatcher {
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (passwordEt.getText().toString().length() >= 6
                    && surePasswordEt.getText().toString().length() >= 6
                    && cedtVcode.getText().toString().length() >= 4 && checkFlag) {
                btnComplete.setEnabled(true);
            } else {
                btnComplete.setEnabled(false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.eye1Iv:
                if (isShowPassword1) {
                    // 显示密码
                    passwordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    eye1Iv.setBackgroundResource(R.drawable.img_login_pwd_eye_open);
                } else {
                    // 不显示密码
                    passwordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    eye1Iv.setBackgroundResource(R.drawable.img_login_pwd_eye_close);
                }
                passwordEt.setSelection(passwordEt.getText().length());
                isShowPassword1 = !isShowPassword1;
                break;
            case R.id.eye2Iv:
                if (isShowPassword2) {
                    // 显示密码
                    surePasswordEt.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());
                    eye2Iv.setBackgroundResource(R.drawable.img_login_pwd_eye_open);
                } else {
                    // 不显示密码
                    surePasswordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    eye2Iv.setBackgroundResource(R.drawable.img_login_pwd_eye_close);
                }
                surePasswordEt.setSelection(surePasswordEt.getText().length());
                isShowPassword2 = !isShowPassword2;
                break;
            case R.id.btn_complete:
                String strcedtVcode = cedtVcode.getText().toString();
                if (strcedtVcode.length() < 4) {
                    com.toobei.common.utils.ToastUtil.showCustomToast("验证码错误，请检查短信验证码重新输入");
                    btnComplete.setEnabled(true);
                    return;
                }
                // 检查密码
                strPasswd1 = passwordEt.getText().toString();
                strPasswd2 = surePasswordEt.getText().toString();
//                if (!StringUtils.isTopicPassword(strPasswd1)) {
//                    com.toobei.common.utils.ToastUtil.showCustomToast("密码错误，请重新输入");
//                    passwordEt.setText("");
//                    btnComplete.setEnabled(true);
//                    return;
//                }
                if (!StringUtils.isTopicPassword(strPasswd2)) {
                    com.toobei.common.utils.ToastUtil.showCustomToast("确认密码错误，请重新输入");
                    surePasswordEt.setText("");
                    btnComplete.setEnabled(true);
                    return;
                }
                if (!strPasswd1.equals(strPasswd2)) {
                    com.toobei.common.utils.ToastUtil.showCustomToast("两次密码不一样，请重新输入");
                    return;
                }
                if (!checkFlag) {
                    com.toobei.common.utils.ToastUtil.showCustomToast("注册领会，需勾选服务协议");
                    return;
                }

                userRegister(phone, strPasswd1, strcedtVcode);
                break;

            case R.id.btn_resend_sms_vcode:
                btnResend.setEnabled(false);
                btnResend.setTextColor(ContextCompat.getColor(ctx,R.color.text_gray_common));
                cedtVcode.setText("");
                sendVcode(phone);
                break;
            case R.id.checkIv:
                if (checkFlag) {
                    checkIv.setBackgroundResource(R.drawable.iv_uncheck);
                    checkFlag = false;
                    btnComplete.setEnabled(false);
                } else {
                    checkIv.setBackgroundResource(R.drawable.iv_check);
                    checkFlag = true;

                    if (passwordEt.getText().toString().length() >= 6
                            && surePasswordEt.getText().toString().length() >= 6
                            && cedtVcode.getText().toString().length() >= 4 && checkFlag) {
                        btnComplete.setEnabled(true);
                    }
                }
                break;
            case R.id.protocalTv:
                String protocalUrl = "https://www.toobei.com/app/pages/agreement/userAgreement.html";
                WebActivityCommon.showThisActivity(ctx, protocalUrl, "T呗用户服务协议");
                break;
            default:
                break;
        }
    }
}
