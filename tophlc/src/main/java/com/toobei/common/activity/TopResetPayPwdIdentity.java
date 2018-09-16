package com.toobei.common.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.lipy.keyboard.library.SecureKeyBoard;
import com.lipy.keyboard.library.keyboard.KeyboardParams;
import com.lipy.keyboard.library.keyboard.KeyboardType;
import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.CheckResponseEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.view.HeaderLayout;

import org.xsl781.ui.view.ClearEditText;
import org.xsl781.utils.SimpleTextWatcher;

/**
 * 公司: tophlc
 * 类说明：重置交易密码 填写用户名和身份证界面 基类
 *
 * @date 2016-3-23
 */
public abstract class TopResetPayPwdIdentity extends TopBaseActivity implements OnClickListener {

    private ClearEditText cedtRealName, cedtIdentityCard;
    private Button btnNext;
    private MySimpleTextWatcher textWatcher;
    protected String forWhat = "";
    private SecureKeyBoard keyBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        forWhat = getIntent().getStringExtra("forWhat");
        setContentView(R.layout.activity_reset_pay_pwd_identity);
        initView();
    }

    public void initView() {
        btnNext = (Button) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);
        btnNext.setEnabled(false);
        cedtRealName = (ClearEditText) findViewById(R.id.cedt_real_name);
        cedtIdentityCard = (ClearEditText) findViewById(R.id.cedt_identity_card);
        textWatcher = new MySimpleTextWatcher();
        cedtRealName.addTextChangedListener(textWatcher);
        cedtIdentityCard.addTextChangedListener(textWatcher);
        keyBoard = new SecureKeyBoard(this,
                new KeyboardParams(this, KeyboardType.IDCARD), cedtIdentityCard);
        cedtIdentityCard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                keyBoard.show();
                return false;
            }
        });
        cedtIdentityCard.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) keyBoard.dismiss();
            }
        });
        initTopView();


    }

    private void initTopView() {
        headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
        TextView remindTv = findViewById(R.id.text_bank_info);
        headerLayout.showTitle("验证身份");
        if ("forSetPayPwd".equals(forWhat)) {
            remindTv.setText("设置交易密码前，请验证身份");
        }
        headerLayout.showLeftBackButton();
        setTranslucentStatus(true);
        headerLayout.setHeadViewCoverStateBar();
    }

    private void userVerifyIdCard(final String name, final String idCardNo) {
        new MyNetAsyncTask(ctx, true) {
            CheckResponseEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = TopApp
                        .getInstance()
                        .getHttpService()
                        .userVerifyIdCard(TopApp.getInstance().getLoginService().token, name,
                                idCardNo);
            }

            @Override
            protected void onPost(Exception e) {
                //登录成功
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        if (response.getData().getRlt().equals("true")) {
                            onVerifyIdCardSuccess();
                        } else {
                            com.toobei.common.utils.ToastUtil
                                    .showCustomToast(getString(R.string.reset_pay_pwd_verify_idcard));
                        }
                    } else {
                        com.toobei.common.utils.ToastUtil.showCustomToast(response
                                .getErrorsMsgStr());
                    }
                } else {
                    com.toobei.common.utils.ToastUtil
                            .showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
                btnNext.setEnabled(true);
            }
        }.execute();
    }

    protected abstract void onVerifyIdCardSuccess();

    private class MySimpleTextWatcher extends SimpleTextWatcher {
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (cedtIdentityCard.getText().toString().length() >= 17
                    && cedtRealName.getText().toString().length() > 0) {
                btnNext.setEnabled(true);
            } else {
                btnNext.setEnabled(false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_next) {
            btnNext.setEnabled(false);
            userVerifyIdCard(cedtRealName.getText().toString(), cedtIdentityCard.getText()
                    .toString());
        }
    }
}
