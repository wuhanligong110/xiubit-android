package com.toobei.tb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.igexin.sdk.PushManager;
import com.toobei.common.TopApp;
import com.toobei.common.entity.LoginResponseData;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.entity.UserInfo;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.StringUtil;
import com.toobei.common.utils.ToastUtil;
import com.toobei.tb.MyApp;
import com.toobei.tb.MyBaseActivity;
import com.toobei.tb.R;
import com.toobei.tb.event.RefreshMineUiEvent;

import org.greenrobot.eventbus.EventBus;
import org.xsl781.ui.view.ClearEditText;
import org.xsl781.utils.SimpleTextWatcher;

public class LoginActivity extends MyBaseActivity implements OnClickListener {

    public static LoginActivity activity;
    private ClearEditText cedtPhone, cedtPasswd;
    private String strcedtPhone;
    private String strPasswd;
    private boolean isShowPassword = true;
    private ImageView showPassword;
    private Button btnLogin;
    private MySimpleTextWatcher textWatcher;
    private TextView text_login_serviceTelephone;
    private int errorCount = 0;
    private boolean isLogin;
    private boolean isLoginFormMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        tranToLogin = false;
        setContentView(R.layout.activity_login);
        initView();
        initData();
        isLoginFormMainActivity = getIntent().getBooleanExtra("isLoginFormMainActivity", false);
    }

    public void initView() {
        setTranslucentStatus(true);
        initHeadFaceView();

        findViewById(R.id.register_bt).setOnClickListener(this);
        findViewById(R.id.query_passwd_bt).setOnClickListener(this);
        text_login_serviceTelephone = (TextView) findViewById(R.id.text_login_serviceTelephone);
        btnLogin = (Button) findViewById(R.id.btn_login);
        findViewById(R.id.img_cancle).setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnLogin.setEnabled(false);
        cedtPhone = (ClearEditText) findViewById(R.id.cedt_phone);
        cedtPasswd = (ClearEditText) findViewById(R.id.cedt_passwd);
        showPassword = (ImageView) findViewById(R.id.password_show);
        textWatcher = new MySimpleTextWatcher();
        cedtPhone.addTextChangedListener(textWatcher);
        cedtPasswd.addTextChangedListener(textWatcher);
        showPassword.setOnClickListener(this);
        MyApp.getInstance().getLoginService().curPhone = MyApp.getInstance().getDefaultSp().getCurUserPhone();
        if (MyApp.getInstance().getLoginService().curPhone != null) {
            cedtPhone.setText(MyApp.getInstance().getLoginService().curPhone);
            cedtPhone.setSelection(cedtPhone.getText().length());
        }

        String serviceTelephone = MyApp.getInstance().getDefaultSp().getServiceTelephone();
        if (!TextUtils.isEmpty(serviceTelephone)) {
            text_login_serviceTelephone.setText("客服电话  " + serviceTelephone);
        }

        cedtPhone.addTextChangedListener(new SimpleTextWatcher() {

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                super.onTextChanged(charSequence, i, i2, i3);
                strcedtPhone = cedtPhone.getText().toString();
            }
        });
        cedtPhone.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // 此处为失去焦点时的处理内容
                    if (!TextUtils.isEmpty(strcedtPhone) && strcedtPhone.length() < 11) {
                        ToastUtil.showCustomToast("手机号码长度为11位，请检查");
                    }
                }
            }
        });
    }

    private void initHeadFaceView() {
//        View view = findViewById(R.id.login_head_logo_ll);
//        view.getLayoutParams().height = PixelUtil.dip2px(ctx, 250);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            view.getLayoutParams().height += SystemTool.getStatusBarHeight(ctx);
//            view.setPadding(0, SystemTool.getStatusBarHeight(ctx), 0, 0);
//        }
    }

    public void initData() {

    }

    /*
     * 登录
     */
    private void login(final String phone, final String passwd) {
        new MyNetAsyncTask(ctx, true) {
            LoginResponseEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().userLogin(phone, passwd, PushManager.getInstance().getClientid(ctx));
            }

            @Override
            protected void onPost(Exception e) {
                // 登录成功
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {

                        LoginResponseData data = response.getData();
                        UserInfo userInfo = new UserInfo();
                        userInfo.setMobile(phone);
                        TopApp.getInstance().getLoginService().setCurUser(userInfo);
                        //	System.out.println("data===>" + data.toString());
                        // 保存token,设置缓存登录信息，更新用户数据
                        MyApp.getInstance().getLoginService().cachLoginInfo(data.getToken(), phone);
                        initCurUserFromServer();
                        MyApp.getInstance().getDefaultSp().setIsLcdsJumpToken(false);
//                        // 2017/1/4 0004   登录成功刷新MainActivity 不然MainActivity不走onCreate无法设置手势密码
//                        ActivityStack.getInstance().finishOthersActivity(ActivityStack.getInstance().topActivity().getClass());
//                        skipActivity(LoginActivity.this, MainActivity.class);
                        EventBus.getDefault().post(new RefreshMineUiEvent(true));
                        finish();
                        /*MyApp.getInstance()
                                .getLoginService()
								.checkUserHasLcs(LoginActivity.this, true,
										new UpdateViewCallBack<String>() {

											@Override
											public void updateView(Exception e, String object) {
												if (object == null) {
													//异常数据
													ToastUtil.showCustomToast("登录失败，请重试！");
												} else if (object != null && object.equals("false")) {
													// 没有理财师 跳出问卷界面
													skipActivity(LoginActivity.this,
															MyQuestionnaireActivity.class);
												} else if (object != null && object.equals("true")) {
													isLogin=true;
													if(isLoginFormMainActivity)
														finish();
													skipActivity(LoginActivity.this,
															MainActivity.class);
												}
											}
										});*/

                    } else {
//                        errorCount++;
//                        if (errorCount <= 3) {
                            com.toobei.common.utils.ToastUtil.showCustomToast(response.getErrorsMsgStr());
//                        } else {
//                            com.toobei.common.utils.ToastUtil.showCustomToast("密码又错了，可点忘记密码找回来");
//                        }
                    }
                } else {
                    com.toobei.common.utils.ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }

    /**
     * 缓存用户数据，登录成功后调用
     */
    public void initCurUserFromServer() {
        if (TopApp.getInstance().getLoginService().getCurUser() == null || TopApp.getInstance().getLoginService().getCurUser().getEasemobAcct() == null) {
            new MyNetAsyncTask(this, false) {
                @Override
                protected void doInBack() throws Exception {
                    UserInfo userInfo = TopApp.getInstance().getUserService().getCurUserByTokenFromMyServer();
                    if (userInfo != null) {
                        TopApp.getInstance().getLoginService().setCurUser(userInfo);
                        MyApp.getInstance().getDefaultSp().setCurUserId(""+userInfo.getUserId());
                        TopApp.getInstance().getUserService().saveAndCache(userInfo);
                        Log.e("easemobAcct", userInfo.getEasemobAcct());
                        Log.e("easemobPassword", userInfo.getEasemobPassword());
                    }
                }

                @Override
                protected void onPost(Exception e) {
                    if (e != null && TopApp.getInstance().getLoginService().getCurUser() != null) {
                    }
                    TopApp.getInstance().getLoginService().initLoginEM();
                }
            }.execute();
        } else {
            TopApp.getInstance().getLoginService().initLoginEM();
        }
    }

    private class MySimpleTextWatcher extends SimpleTextWatcher {
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (cedtPasswd.getText().toString().length() >= 6 && cedtPhone.getText().toString().length() == 11) {
                btnLogin.setEnabled(true);
            } else {
                btnLogin.setEnabled(false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.password_show:
                if (isShowPassword) {
                    // 显示密码
                    cedtPasswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showPassword.setImageResource(R.drawable.img_login_pwd_eye_open);
                } else {
                    // 不显示密码
                    cedtPasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showPassword.setImageResource(R.drawable.img_login_pwd_eye_close);
                }
                cedtPasswd.setSelection(cedtPasswd.getText().length());
                isShowPassword = !isShowPassword;
                break;
            case R.id.register_bt:
                startActivity(new Intent(this, RegisterPhone.class));
                break;
            case R.id.img_cancle:
                finish();
                break;

            case R.id.btn_login:
                strcedtPhone = cedtPhone.getText().toString();
                if (!StringUtil.checkPhoneNum(strcedtPhone)) {
                    com.toobei.common.utils.ToastUtil.showCustomToast("亲，请确定你输入的是手机号码");
                    return;
                }
                // 检查密码
                strPasswd = cedtPasswd.getText().toString();
                if (!StringUtil.checkPasswd(strPasswd)) {
                    com.toobei.common.utils.ToastUtil.showCustomToast("密码错误，请重新输入");
                    cedtPasswd.setText("");
                    return;
                }
                login(strcedtPhone, strPasswd);
                break;

            case R.id.query_passwd_bt: // 忘记密码 ，重置密码
                cedtPasswd.setText("");
//			showActivity(this, ResetLoginPwdPhone.class);
                strcedtPhone = cedtPhone.getText().toString();
                Intent intent = new Intent(activity, ResetLoginPwdPhone.class);
                if (StringUtil.checkPhoneNum(strcedtPhone)) {
                    intent.putExtra("strcedtPhone", strcedtPhone);
                }
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();

        if (isLogin) {
            setResult(RESULT_OK);
        }
    }
}
