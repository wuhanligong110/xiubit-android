package com.toobei.common.activity;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.os.Bundle;
import android.text.Editable;
import android.widget.TextView;

import com.jungly.gridpasswordview.GridPasswordView;
import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.event.PayPwdSetSuccessEvent;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.view.HeaderLayout;

import org.greenrobot.eventbus.EventBus;

/**
 * 公司: tophlc
 * 类说明：初始化交易密码 基类
 *
 * @date 2016-3-22
 */
public abstract class TopPwdManagerInitPay extends TopBaseActivity {
    private GridPasswordView gridPwd;
    private String firstPwd;
    private TextView textTitle;
    private int inputNum = 1;//是否为第一次输入
    private String resetPayPwdToken;
    private TopPwdManagerInitPay activity;
    private KeyboardView keyboard_view;
    private TextView errorTv;
    private int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        resetPayPwdToken = getIntent().getStringExtra("resetPayPwdToken");
        setContentView(R.layout.activity_init_pay_pwd);
        findView();
        initTopTitle();
        showSoftInputView();
    }

    private void initTopTitle() {
        headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
        headerLayout.showTitle(TopApp.getInstance().getString(R.string.init_pay_pwd));
        headerLayout.showLeftBackButton();
        setTranslucentStatus(true);
        headerLayout.setHeadViewCoverStateBar();

        errorTv = findViewById(R.id.error_remind_tv);
        changeView();
    }

    private void findView() {
        textTitle = (TextView) findViewById(R.id.alter_pay_pwd_title);
        gridPwd = (GridPasswordView) findViewById(R.id.gridpwd_pay);
        gridPwd.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onChanged(String psw) {
                errorTv.setText("");
                if (psw.length() == 6 && inputNum == 1) {
                    firstPwd = psw;
                    inputNum = 2;
                    gridPwd.clearPassword();
                    changeView();
                } else if (psw.length() == 6 && inputNum == 2) {
                    if (psw.equals(firstPwd)) {
                        if (resetPayPwdToken != null) {
                            //重置
                            userResetPayPwd(firstPwd);
                        } else {
                            //初始化
                            userInitPayPwd(firstPwd);
                        }
                    } else {
                        inputNum = 1;
                        errorTv.setText("交易密码不一致，请重新设置");
                        gridPwd.clearPassword();
                        changeView();
                    }
                }
            }

            @Override
            public void onMaxLength(String psw) {
            }
        });


        keyboard_view = (KeyboardView) findViewById(R.id.keyboard_view);
        Keyboard k2 = new Keyboard(ctx, R.xml.symbols);
        keyboard_view.setKeyboard(k2);
        keyboard_view.setEnabled(true);
        keyboard_view.setPreviewEnabled(false);
        keyboard_view.setOnKeyboardActionListener(new OnKeyboardActionListener() {

            @Override
            public void swipeUp() {

            }

            @Override
            public void swipeRight() {

            }

            @Override
            public void swipeLeft() {

            }

            @Override
            public void swipeDown() {

            }

            @Override
            public void onText(CharSequence arg0) {

            }

            @Override
            public void onRelease(int arg0) {

            }

            @Override
            public void onPress(int arg0) {

            }

            @Override
            public void onKey(int primaryCode, int[] arg1) {
                num = gridPwd.getPassWord().length();
                Editable editable = gridPwd.getInputView().getText();
                int start = gridPwd.getInputView().getSelectionStart();
                if (num == 0) {
                    if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                        if (editable != null && editable.length() > 0) {
                            if (editable.length() > 0) {
                                editable.delete(start - 1, start);
                            }
                        }
                    } else if (primaryCode == -2) {
                        if (editable != null && editable.length() > 0) {
                            if (start > 0) {
                                editable.append(".");
                            }
                        }
                    } else {
                        editable.insert(start, Character.toString((char) primaryCode));
                    }
                } else if (num == 1) {
                    if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                        if (editable != null && editable.length() > 0) {
                            if (editable.length() > 0) {
                                editable.delete(start - 1, start);
                            }
                        }
                        gridPwd.getPasswordArr()[0] = null;
                    } else if (primaryCode == -2) {
                    } else {
                        gridPwd.setText(1, Character.toString((char) primaryCode));
                    }
                } else if (num == 2) {
                    if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                        gridPwd.setText(1, null);
                    } else if (primaryCode == -2) {
                    } else {
                        gridPwd.setText(2, Character.toString((char) primaryCode));
                    }
                } else if (num == 3) {
                    if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                        gridPwd.setText(2, null);
                    } else if (primaryCode == -2) {
                    } else {
                        gridPwd.setText(3, Character.toString((char) primaryCode));
                    }
                } else if (num == 4) {
                    if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                        gridPwd.setText(3, null);
                    } else if (primaryCode == -2) {
                    } else {
                        gridPwd.setText(4, Character.toString((char) primaryCode));
                    }
                } else if (num == 5) {
                    if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                        gridPwd.setText(4, null);
                    } else if (primaryCode == -2) {
                    } else {
                        gridPwd.setText(5, Character.toString((char) primaryCode));
//						userVerifyPayPwd(gridPwd.getPassWord());
                        if (inputNum == 1) {
                            firstPwd = gridPwd.getPassWord();
                            inputNum = 2;
                            gridPwd.clearPassword();
                            changeView();
                        } else {
                            if (gridPwd.getPassWord().equals(firstPwd)) {
                                if (resetPayPwdToken != null) {
                                    //重置
                                    userResetPayPwd(firstPwd);
                                } else {
                                    //初始化
                                    userInitPayPwd(firstPwd);
                                }
                            } else {
                                inputNum = 1;
                                errorTv.setText("交易密码不一致，请重新设置");
                                gridPwd.clearPassword();
                                changeView();
                            }
                        }
                    }
                }
            }
        });
    }

    private void changeView() {
        if (inputNum == 1) {
            textTitle.setText("设置交易密码，用于提现");
        } else {
            textTitle.setText(R.string.input_new_pay_second);
        }
    }

    private void userInitPayPwd(final String pwd) {
        new MyNetAsyncTask(ctx, true) {
            LoginResponseEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = TopApp.getInstance().getHttpService()
                        .userInitPayPwd(TopApp.getInstance().getLoginService().token, pwd);

            }

            @Override
            protected void onPost(Exception e) {
                inputNum = 1;
                gridPwd.clearPassword();
                changeView();
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        //修改支付 密码成功
                        EventBus.getDefault().post(new PayPwdSetSuccessEvent());
                        TopApp.getInstance().getAccountService().setIsHasPayPwd("true");
                        com.toobei.common.utils.ToastUtil
                                .showCustomToast(getString(R.string.set_success));
                        activity.finish();
                    } else {
                        com.toobei.common.utils.ToastUtil.showCustomToast(response
                                .getErrorsMsgStr());
                        EventBus.getDefault().post(new PayPwdSetSuccessEvent());
                        TopApp.getInstance().getAccountService().setIsHasPayPwd("true");
                        com.toobei.common.utils.ToastUtil
                                .showCustomToast(getString(R.string.set_success));
                        activity.finish();
                    }
                } else {
                    com.toobei.common.utils.ToastUtil
                            .showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }

    private void userResetPayPwd(final String pwd) {
        new MyNetAsyncTask(ctx, true) {
            LoginResponseEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = TopApp
                        .getInstance()
                        .getHttpService()
                        .userResetPayPwd(TopApp.getInstance().getLoginService().token,
                                resetPayPwdToken, pwd);

            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        EventBus.getDefault().post(new PayPwdSetSuccessEvent());
                        //修改支付 密码成功
                        TopApp.getInstance().getAccountService().setIsHasPayPwd("true");
                        finish();
                        com.toobei.common.utils.ToastUtil
                                .showCustomToast(getString(R.string.set_success));
                    } else {
                        com.toobei.common.utils.ToastUtil.showCustomToast(response
                                .getErrorsMsgStr());
                    }
                } else {
                    com.toobei.common.utils.ToastUtil
                            .showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
                inputNum = 1;
                gridPwd.clearPassword();
                changeView();
            }
        }.execute();
    }

}
