package com.v5ent.xiubit.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.igexin.sdk.PushManager;
import com.toobei.common.entity.LoginResponseData;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.event.FinishActivityEvent;
import com.toobei.common.event.RefreshAfterLoginEvent;
import com.toobei.common.utils.KeyboardUtil;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.view.HeaderLayout;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.MyBaseActivity;
import com.v5ent.xiubit.R;

import org.greenrobot.eventbus.EventBus;
import org.xsl781.ui.view.ClearEditText;
import org.xsl781.utils.PixelUtil;
import org.xsl781.utils.SimpleTextWatcher;
import org.xsl781.utils.StringUtils;
import org.xsl781.utils.SystemTool;

/**
 * 公司: tophlc
 * 类说明： Activity-注册 填密码界面
 *
 * @date 2015-12-29
 */
public class RegisterPwdNew extends MyBaseActivity implements OnClickListener {

    public static RegisterPwdNew activity;
    private ClearEditText cedtVcode, cedtPasswd;
    private ClearEditText cedtPasswd2;
    private String strcedtVcode, phone;
    private String strPasswd;
    // private boolean isShowPassword = true;
    // private ImageView showPassword;
    private Button btnComplete, btnResend;
    private MySimpleTextWatcher textWatcher;
    private TextView textTitlePrompt;
    private ImageView checkIV;
    private boolean isChecked = true;

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
                    } else {
                        btnResend.setEnabled(false);
                        mHandler.sendEmptyMessageDelayed(
                                GET_RESET_SMS_CODE, 1000);
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
        setContentView(R.layout.activity_register_pwd);
        mHandler.sendEmptyMessageDelayed(GET_RESET_SMS_CODE, 1000);
        phone = getIntent().getStringExtra("phone");
        initView();
    }

    public void initView() {
        View rootView = findViewById(R.id.root_view);
        btnComplete = (Button) findViewById(R.id.btn_complete);
        btnComplete.setOnClickListener(this);
        btnComplete.setEnabled(false);
        btnResend = (Button) findViewById(R.id.btn_resend_sms_vcode);
        btnResend.setOnClickListener(this);
        btnResend.setEnabled(false);
        btnResend.setText("60s重新获取");
        // 验证码
        cedtVcode = (ClearEditText) findViewById(R.id.cedt_vcode);
        // 密码1
        cedtPasswd = (ClearEditText) findViewById(R.id.cedt_passwd);
        cedtPasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
        cedtPasswd.setSelection(cedtPasswd.getText().length());
        cedtPasswd2 = (ClearEditText) findViewById(R.id.cedt_passwd2);
        cedtPasswd2.setTransformationMethod(PasswordTransformationMethod.getInstance());
        cedtPasswd2.setSelection(cedtPasswd2.getText().length());

        textWatcher = new MySimpleTextWatcher();
        cedtVcode.addTextChangedListener(textWatcher);
        cedtPasswd.addTextChangedListener(textWatcher);

        textTitlePrompt = (TextView) findViewById(R.id.reset_login_pwd_title);
        textTitlePrompt.setText(getString(R.string.sended_sms_vcode)
                + com.toobei.common.utils.StringUtil.formatPhone(phone));

        checkIV = (ImageView) findViewById(R.id.img_check);
        checkIV.setOnClickListener(this);
        findViewById(R.id.text_pay_agreement).setOnClickListener(this);
        initTopView();
        setupUIListenerAndCloseKeyboard(rootView); //点击输入框以外区域收回键盘
    }

    private void initTopView() {
        headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
        headerLayout.showTitle(R.string.register);
        headerLayout.showLeftBackButton();
        setTranslucentStatus(true);
        headerLayout.setHeadViewCoverStateBar();
    }

    /**
     * 找到指定根布局，然后让根布局自动找到其子组件，再递归注册监听器
     *
     * @param view 指定布局
     */
    public void setupUIListenerAndCloseKeyboard(View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    KeyboardUtil.hideKeyboard(RegisterPwdNew.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup && view.getId() != R.id.inputViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUIListenerAndCloseKeyboard(innerView);
            }
        }
    }

    private void userRegister(final String phone, final String password, final String vcode, final String recommendCode) {
        new MyNetAsyncTask(ctx, true) {
            LoginResponseEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService()
                        .userRegister(phone, password, vcode, PushManager.getInstance().getClientid(activity), recommendCode, SystemTool.getDeviceid(activity), SystemTool.getDeviceModel(), PixelUtil.getScreenH(activity) + "*" + PixelUtil.getScreenW(activity), SystemTool.getSystemVersion());
            }

            @Override
            protected void onPost(Exception e) {
                // 成功
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        LoginResponseData data = response.getData();

                        // 保存token,设置缓存登录信息，更新用户数据
                        MyApp.getInstance().getLoginService().curPhone = phone;
                        MyApp.getInstance().getLoginService().isServerLogin = true;
                        MyApp.getInstance().getLoginService().token = data.getToken();
                        MyApp.getInstance().getCurUserSp().setToken(data.getToken());
                        MyApp.getInstance().getDefaultSp().setCurUserPhone(phone);
//                        if (RegisterPhone.activity != null) {
//                            RegisterPhone.activity.finish();
//                            RegisterPhone.activity=null;
//                        }
//                        if (LoginActivity.activity != null) {
//                            LoginActivity.activity.finish();
//                            LoginActivity.activity=null;
//                        }
                        EventBus.getDefault().post(new FinishActivityEvent(RegisterPhone.class));
                        EventBus.getDefault().post(new FinishActivityEvent(LoginActivity.class));
                        MyApp.getInstance().getIsNewUserAndSave(null); //判断是否新用户
                        //跳转主页，弹出手势设置页面
                        EventBus.getDefault().post(new RefreshAfterLoginEvent());
                        skipActivity(RegisterPwdNew.this, MainActivity.class);
                    } else {
                        com.toobei.common.utils.ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                }
                btnComplete.setEnabled(true);
            }
        }.execute();
    }

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
                        com.toobei.common.utils.ToastUtil.showCustomToast(response.getErrorsMsgStr());
                        btnResend.setEnabled(true);
                    }
                } else {
                    com.toobei.common.utils.ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                    btnResend.setEnabled(true);
                }
            }
        }.execute();
    }

    private class MySimpleTextWatcher extends SimpleTextWatcher {
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (cedtPasswd.getText().toString().length() >= 6 && cedtVcode.getText().toString().length() >= 4
                    && isChecked) {
                // btnComplete.setBackgroundResource(R.drawable.btn_complete);
                btnComplete.setEnabled(true);
            } else {
                // btnComplete.setBackgroundResource(R.drawable.btn_grey_big_default);
                btnComplete.setEnabled(false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_check:

                isChecked = !isChecked;

                checkIV.setImageResource(isChecked ? R.drawable.img_checkbox_checked : R.drawable.img_checkbox_unchecked);
                if (isChecked && cedtPasswd.getText().toString().length() >= 6
                        && cedtVcode.getText().toString().length() >= 4) {
                    btnComplete.setEnabled(true);
                } else {
                    btnComplete.setEnabled(false);
                }

                // 不显示密码

                // case R.id.password_show:
                // if (isShowPassword) {
                // //显示密码
                // cedtPasswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                // showPassword.setImageResource(R.drawable.img_login_pwd_eye_open);
                // } else {
                // //不显示密码
                // cedtPasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                // showPassword.setImageResource(R.drawable.img_login_pwd_eye_close);
                // }
                // cedtPasswd.setSelection(cedtPasswd.getText().length());
                // isShowPassword = !isShowPassword;
                // break;
                break;
            case R.id.btn_complete:

                strcedtVcode = cedtVcode.getText().toString();
                if (strcedtVcode.length() < 4) {
                    com.toobei.common.utils.ToastUtil.showCustomToast("验证码错误，请重新输入！");
                    return;
                }
                // 检查密码
                strPasswd = cedtPasswd.getText().toString();
                if (!StringUtils.isTopicPassword(strPasswd)) {
                    com.toobei.common.utils.ToastUtil.showCustomToast("密码不符合规则，请重新输入！");
                    cedtPasswd.setText("");
                    cedtPasswd2.setText("");
                    return;
                }

                if (!cedtPasswd.getText().toString().equals(cedtPasswd2.getText().toString())) {
                    com.toobei.common.utils.ToastUtil.showCustomToast("两次密码不一致，请重新输入！");

                    // cedtPasswd.setText("");
                    cedtPasswd2.setText("");

                    return;
                }
                btnComplete.setEnabled(false);
                userRegister(phone, strPasswd, strcedtVcode, "");
                break;

            case R.id.btn_resend_sms_vcode:
                btnResend.setEnabled(false);
                cedtVcode.setText("");
                sendVcode(phone);
                break;
            case R.id.text_pay_agreement:// 点击协议
                // 跳转到 《貅比特用户服务协议》
                WebActivityCommon.showThisActivity(ctx, MyApp.getInstance().getDefaultSp().getUserService(), "貅比特用户服务协议");
                break;
            default:
                break;
        }
    }

    // @Override
    // public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    // this.isChecked = isChecked;
    // if (isChecked && cedtPasswd.getText().toString().length() >= 6
    // && cedtVcode.getText().toString().length() >= 4) {
    // btnComplete.setEnabled(true);
    // } else {
    // btnComplete.setEnabled(false);
    // }
    // }
}
