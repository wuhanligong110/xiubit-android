package com.v5ent.xiubit.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.igexin.sdk.PushManager;
import com.toobei.common.TopApp;
import com.toobei.common.entity.LoginResponseData;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.entity.UserInfo;
import com.toobei.common.event.RefreshAfterLoginEvent;
import com.toobei.common.utils.KeyboardVisibilityEvent;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.StatusBarUtil;
import com.toobei.common.utils.StringUtil;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.MyBaseActivity;
import com.v5ent.xiubit.R;

import org.greenrobot.eventbus.EventBus;
import org.xsl781.ui.ActivityStack;
import org.xsl781.ui.view.ClearEditText;
import org.xsl781.utils.Logger;
import org.xsl781.utils.SimpleTextWatcher;
import org.xsl781.utils.SystemTool;

/**
 * Activity-登录页
 */
public class LoginActivity extends MyBaseActivity implements OnClickListener {

    private ClearEditText cedtPhone, cedtPasswd;
    private String strcedtPhone;
    private String strPasswd;
    private boolean isShowPassword = true;
    private ImageView showPassword;
    private Button btnLogin;
    private MySimpleTextWatcher textWatcher;
    private TextView telTv;
    private ScrollView scrollView;
    private View inputViewGroup;
    private ImageView imgLoginLogo;
    private View mRootView;
    //    private KeyboardUtil mKeyboardUtil;
    private View mEdLine1;
    private View mEdLine2;
    private TextView mDefaultPhone;
    private TextView mSwitchAccount;
    private View mTopStautLl;
    private boolean is2LoginOtherAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        is2LoginOtherAccount = getIntent().getBooleanExtra("is2LoginOtherAccount", false);
        super.onCreate(savedInstanceState);
        tranToLogin = false;
        //	new SystemBarTintManager(this).setStatusBarTintEnabled(true);
        mRootView = LayoutInflater.from(this).inflate(R.layout.activity_login, null, false);
        setContentView(mRootView);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        initView();
//        initMoveKeyBoard(); //自定义安全键盘
    }

    private void initMoveKeyBoard() {
//        mKeyboardUtil = new KeyboardUtil(this, mRootView, scrollView);
//        mKeyboardUtil.setOtherEdittext(cedtPhone);
////        // monitor the KeyBarod state
//        mKeyboardUtil.setKeyBoardStateChangeListener(new KeyboardUtil.KeyBoardStateChangeListener() {
//            @Override
//            public void KeyBoardStateChange(int state, EditText editText) {
//                switch (state){
//                    case 1:  //键盘弹出
//                        changeScrollView();
//                        break;
//                    case 2:  //键盘收起
//                        break;
//                        
//                        
//                }
//            }
//        });
////        // monitor the finish or next Key
//        mKeyboardUtil.setInputOverListener(new KeyboardUtil.InputFinishListener() {
//            @Override
//            public void inputHasOver(int onclickType, EditText editText) {
//                Logger.e("onclickType" + onclickType);
//            }
//        });
//        cedtPasswd.setOnTouchListener(new KeyboardTouchListener(mKeyboardUtil, KeyboardUtil.INPUTTYPE_ABC, -1));
    }

    @Override
    protected void initStatusBarStyle() {
        super.initStatusBarStyle();
        StatusBarUtil.StatusBarLightMode(this);
    }

    @Override
    protected void onStart() {
        cedtPasswd.setText("");
        super.onStart();
    }

    public void initView() {
        setTranslucentStatus(true);
        mTopStautLl = findViewById(R.id.topStautLl);
        setHeadViewCoverStateBar(mTopStautLl);
        mDefaultPhone = (TextView) findViewById(R.id.defaultPhone);
        imgLoginLogo = (ImageView) findViewById(R.id.img_login_logo);
        inputViewGroup = findViewById(R.id.inputViewGroup); //整个输入区域
        telTv = (TextView) findViewById(R.id.text_login_serviceTelephone);
        mEdLine1 = findViewById(R.id.edLine1);
        mEdLine2 = findViewById(R.id.edLine2);
        findViewById(R.id.registerTv).setOnClickListener(this);
        findViewById(R.id.query_passwd_bt).setOnClickListener(this);
        findViewById(R.id.closedIv).setOnClickListener(this);
        mSwitchAccount = (TextView) findViewById(R.id.switchAccount);
        mSwitchAccount.setOnClickListener(this);
        btnLogin = (Button) findViewById(R.id.btn_login);
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
        if (is2LoginOtherAccount || TextUtil.isEmpty(MyApp.getInstance().getLoginService().curPhone)){
            imgLoginLogo.setImageResource(R.drawable.img_login_logo);
            mDefaultPhone.setVisibility(View.GONE);
            cedtPhone.setVisibility(View.VISIBLE);
            cedtPhone.clearFocus();
            cedtPhone.setText("");
            mEdLine1.setVisibility(View.VISIBLE);
            mSwitchAccount.setVisibility(View.GONE);
        }else {
            mDefaultPhone.setText(MyApp.getInstance().getLoginService().curPhone.substring(0, 3) + "****" + MyApp.getInstance().getLoginService().curPhone.substring(7, 11));
            mDefaultPhone.setVisibility(View.VISIBLE);

            cedtPhone.setVisibility(View.GONE);
            cedtPhone.setText(MyApp.getInstance().getLoginService().curPhone);  //虽然它隐藏掉了，为了方便逻辑处理，还是给他赋值
            cedtPhone.setSelection(cedtPhone.getText().length());
            mEdLine1.setVisibility(View.GONE);
            imgLoginLogo.setImageResource(R.drawable.login_default_head_img);
            mSwitchAccount.setVisibility(View.VISIBLE);
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
                    mEdLine1.setBackgroundColor(ContextCompat.getColor(ctx, R.color.line_common));
                    if (!TextUtils.isEmpty(strcedtPhone) && strcedtPhone.length() < 11) {
                        ToastUtil.showCustomToast((Activity) ctx, "手机号码长度为11位，请检查");
                    }
                } else {
                    mEdLine1.setBackgroundColor(ContextCompat.getColor(ctx, R.color.text_blue_common));
                }

            }
        });
        cedtPasswd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    // 此处为失去焦点时的处理内容
                    mEdLine2.setBackgroundColor(ContextCompat.getColor(ctx, R.color.line_common));
                } else {
                    mEdLine2.setBackgroundColor(ContextCompat.getColor(ctx, R.color.text_blue_common));
                }
            }
        });
        //输入框点击，键盘弹出，布局上移
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        setupUIListenerAndCloseKeyboard(scrollView);  //注册监听，点击输入框外区域时收回键盘
        KeyboardVisibilityEvent.setEventListener(this, new KeyboardVisibilityEvent.KeyboardVisibilityEventListener() {
            @Override
            public void onVisibilityChanged(boolean isOpen) {
                if (isOpen) {
                    changeScrollView();
                    Logger.e("KeyboardIsOpen" + isOpen);
                }
            }
        });

//        telTv.setText("客服电话：" + MyApp.getInstance().getDefaultSp().getServiceTelephone());
    }

    /**
     * 使ScrollView指向底部
     */
    private void changeScrollView() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollTo(0, scrollView.getHeight());
            }
        });
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
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    com.toobei.common.utils.KeyboardUtil.hideKeyboard(LoginActivity.this);
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


    //java.lang.ClassCastException: com.google.gson.internal.LinkedTreeMap cannot be cast to com.tophlc.lcs.entity.LoginResponseData

    //params.setMobile("15989368346");
    //params.setPassword("123456");
    private void login(final String phone, final String passwd) {
        new MyNetAsyncTask(ctx, true) {
            LoginResponseEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().userLogin(phone, passwd, PushManager.getInstance().getClientid(getApplicationContext()));
            }

            @Override
            protected void onPost(Exception e) {
                //登录成功
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        LoginResponseData data = response.getData();

                        // 保存token,设置缓存登录信息，更新用户数据
                        MyApp.getInstance().getLoginService().curPhone = phone;
                        MyApp.getInstance().getLoginService().isServerLogin = true;
                        MyApp.getInstance().getLoginService().token = data.getToken();
                        MyApp.getInstance().getCurUserSp().setToken(data.getToken());
                        MyApp.getInstance().getDefaultSp().setCurUserPhone(phone);
                        initCurUserFromServer();
                        TopApp.loginAndStay = true;  //从所有页面登陆后统一回到原有页面
                        if (TopApp.loginAndStay && MainActivity.isAlive) {
                            EventBus.getDefault().post(new RefreshAfterLoginEvent());
                            if (!TopApp.getInstance().getLoginService().isCurUserGesturePasswdExist()) {
                                //手势不存在，进入设置手势环节
                                skipGestureSetActivity();
                            } else {
                                TopApp.loginAndStay = false;
                                LoginActivity.this.finish();
                            }

                        } else {
//                            MyApp.getInstance().getIsNewUserAndSave(new UpdateViewCallBack<String>() { //是否新用户
//                                @Override
//                                public void updateView(Exception e, String object) {
//                                    Logger.e("729login_upview");
                            // 2017/1/4 0004   登录成功刷新MainActivity 不然MainActivity不走onCreate无法设置手势密码
                            ActivityStack.getInstance().finishOthersActivity(ActivityStack.getInstance().topActivity().getClass());
                            skipActivity(LoginActivity.this, MainActivity.class);
//                                }
//                            });
                        }
                    } else {
//                        errorCount++;
//                        if (errorCount <= 3) {
                        ToastUtil.showCustomToast((Activity) ctx, response.getErrorsMsgStr());
//                        } else {
//
//                            if (response.getCode().equals("140005")) {  // 判断是否是冻结账号
//                                ToastUtil.showCustomToast((Activity) ctx, response.getErrorsMsgStr());
//                            } else {
//
//                                ToastUtil.showCustomToast((Activity) ctx, "密码又错了，可点忘记密码找回来");
//                            }
//                        }
                    }
                } else {
                    ToastUtil.showCustomToast((Activity) ctx, getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }

    private void skipGestureSetActivity() {
        skipActivity(this, GestureSetActivity.class);
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

    public void initCurUserFromServer() {
        if (TopApp.getInstance().getLoginService().getCurUser() == null || TopApp.getInstance().getLoginService().getCurUser().getEasemobAcct() == null) {
            new MyNetAsyncTask(this, false) {
                @Override
                protected void doInBack() throws Exception {
                    UserInfo userInfo = TopApp.getInstance().getUserService().getCurUserByTokenFromMyServer();
                    if (userInfo != null) {
                        TopApp.getInstance().getLoginService().setCurUser(userInfo);
                        MyApp.getInstance().getDefaultSp().setCurUserId("" + userInfo.getUserId());
                        TopApp.getInstance().getUserService().saveAndCache(userInfo);
                        Logger.e("initCurUserFromServer userInfo ====", userInfo.toString());

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.password_show:
                if (isShowPassword) {
                    //显示密码
                    cedtPasswd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showPassword.setImageResource(R.drawable.img_eye_open);
                } else {
                    //不显示密码
                    cedtPasswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showPassword.setImageResource(R.drawable.img_eye_close);
                }
                cedtPasswd.setSelection(cedtPasswd.getText().length());
                isShowPassword = !isShowPassword;
                break;
            case R.id.registerTv: //重新注册
                Intent intent = new Intent(this, RegisterPhone.class);
                intent.putExtra("strcedtPhone", cedtPhone.getText().toString());
                showActivity(this, intent);

                break;

            case R.id.btn_login:
                strcedtPhone = cedtPhone.getText().toString();
                if (!StringUtil.checkPhoneNum(strcedtPhone)) {
                    com.toobei.common.utils.ToastUtil.showCustomToast((Activity) ctx, "亲，请确定你输入的是手机号码");
                    return;
                }
                // 检查密码
                strPasswd = cedtPasswd.getText().toString();
                if (!StringUtil.checkPasswd(strPasswd)) {
                    com.toobei.common.utils.ToastUtil.showCustomToast((Activity) ctx, "密码无效，请重新输入！");
                    cedtPasswd.setText("");
                    return;
                }
                login(strcedtPhone, strPasswd);
                break;

            case R.id.query_passwd_bt: // 忘记密码 ，重置密码
                Intent intent2 = new Intent(this, ResetLoginPwdPhone.class);
                intent2.putExtra("strcedtPhone", cedtPhone.getText().toString());
                showActivity(this, intent2);
                break;
            case R.id.closedIv:
                TopApp.loginAndStay = false;
                finish();
                break;
            case R.id.switchAccount:
                imgLoginLogo.setImageResource(R.drawable.img_login_logo);
                mDefaultPhone.setVisibility(View.GONE);
                cedtPhone.setVisibility(View.VISIBLE);
                cedtPhone.setText("");
                cedtPasswd.clearFocus();
                mEdLine1.setVisibility(View.VISIBLE);
                mSwitchAccount.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    public void setHeadViewCoverStateBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            view.getLayoutParams().height = PixelUtil.dip2px(this, 44) + SystemTool.getStatusBarHeight(this);
            view.getLayoutParams().height = SystemTool.getStatusBarHeight(getApplicationContext());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
        if (ActivityStack.getInstance().getAliveActivityNum() <= 1) {
            startActivity(new Intent(ctx,MainActivity.class));
        }
        super.finish();
    }
}
