package com.toobei.common.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lipy.keyboard.library.SecureKeyBoard;
import com.lipy.keyboard.library.keyboard.KeyboardParams;
import com.lipy.keyboard.library.keyboard.KeyboardType;
import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopNetBaseActivity;
import com.toobei.common.entity.BankCardInfo;
import com.toobei.common.entity.BankCardInfoEntity;
import com.toobei.common.event.CardBindSuccessEvent;
import com.toobei.common.event.CardScanEvent;
import com.toobei.common.event.UpCardScanDataEvent;
import com.toobei.common.manage.CardScanHelper;
import com.toobei.common.utils.CheckUtil;
import com.toobei.common.utils.KeyboardUtil;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.RegexUtils;
import com.toobei.common.utils.TextDecorator;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.dialog.PromptDialog;
import com.toobei.common.view.dialog.PromptDialogAddCardError;
import com.toobei.common.view.dialog.PromptDialogCalTel;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xsl781.ui.view.ClearEditText;
import org.xsl781.utils.Logger;
import org.xsl781.utils.SimpleTextWatcher;

import static com.toobei.common.utils.C.MY_PERMISSIONS_REQUEST_DIAL;

/**
 * 公司: tophlc
 * 类说明：添加银行卡 界面
 *
 * @date 2015-10-13
 */
public abstract class TopCardManagerAdd extends TopNetBaseActivity implements OnClickListener {
    public final static int TYPE_IDENTITY = 1;//身份证
    public final static int TYPE_BANKCARD = 2;//银行卡
    private static final int REST_BTN_ACTIVE = 001;
    public static TopCardManagerAdd activity;
    private ClearEditText cedtRealName, cedtIdentityCard,
//            cedtPhone,
            cedtBankCard;
    private Button btnNext;
    private String bankCard = "", realName = "", identityCard = "", phone = "", bank = "";
    protected View rootView;
    private TextView mBottomTv;
    private String mServerNum = "400-888-6987";
    private TextView mErrorRemindTv;
    private boolean[] checkSuccess = new boolean[4];
    private ImageView mQuestIv;
    private View bandScanEntry;
    private View identityScanEntry;
    private SecureKeyBoard keyBoard;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_card_manager_add;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobclickAgent.onEvent(ctx,"W_10_1");
        activity = this;
        initView();
        showSoftInputView();
    }

    protected abstract void skipCardManageAddSuccess(BankCardInfo bankCardInfo);

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REST_BTN_ACTIVE:
                    btnNext.setEnabled(true);
                    btnNext.setBackgroundResource(setBtnColor());
                    break;
            }
        }
    };

    public void initView() {
        headerLayout.showLeftBackButton();
        headerLayout.showTitle("填写银行卡信息");
        rootView = findViewById(R.id.root_view);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);
        btnNext.setBackgroundResource(setBtnColor());

        cedtRealName = (ClearEditText) findViewById(R.id.cedt_real_name);
        cedtIdentityCard = (ClearEditText) findViewById(R.id.cedt_identity_card);
        cedtBankCard = (ClearEditText) findViewById(R.id.cedt_bank_card_number);
//        cedtPhone = (ClearEditText) findViewById(R.id.cedt_phone);
        mQuestIv = (ImageView) findViewById(R.id.quest_iv);
        bandScanEntry = findViewById(R.id.bandScanEntry);
        identityScanEntry = findViewById(R.id.identityScanEntry);
        //扫描识别
        bandScanEntry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(ctx,"W_10_2");
                skipCardScan(TYPE_BANKCARD);
            }
        });
        identityScanEntry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(ctx,"W_10_2");
                skipCardScan(TYPE_IDENTITY);
            }
        });
        mQuestIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new PromptDialog(ctx, "银行预留手机说明"
                        , "银行预留手机号时办理该银行卡时所填写的手机号码。没有预留、手机号忘记或者已停用，请连写银行客服更新处理。"
                        , "知道了", "").show();
            }
        });
        //监听焦点变化
        MyOnFocusChangeListener focusChangeListener = new MyOnFocusChangeListener();
        cedtRealName.setOnFocusChangeListener(focusChangeListener);
        cedtIdentityCard.setOnFocusChangeListener(focusChangeListener);
        cedtBankCard.setOnFocusChangeListener(focusChangeListener);
//        cedtPhone.setOnFocusChangeListener(focusChangeListener);

        //监听输入变化
        cedtRealName.addTextChangedListener(new MySimpleTextWatcher(cedtRealName));
        cedtIdentityCard.addTextChangedListener(new MySimpleTextWatcher(cedtIdentityCard));
        cedtBankCard.addTextChangedListener(new MySimpleTextWatcher(cedtBankCard));
//        cedtPhone.addTextChangedListener(new MySimpleTextWatcher(cedtPhone));

        mBottomTv = (TextView) findViewById(R.id.bottomTv);
        mErrorRemindTv = (TextView) findViewById(R.id.error_remind_tv);

        TextDecorator.decorate(mBottomTv, getResources().getString(R.string.card_bind_bottom_remind))
                .makeTextClickable(new TextDecorator.OnTextClickListener() {
                    @Override
                    public void onClick(View view, String text) {
                        callPhone();
                    }
                }, false, "400-888-6987")
                .setTextColor(R.color.text_blue_common, "400-888-6987")
                .build();

        KeyboardUtil.setupUIListenerAndCloseKeyboard(rootView, this);

        keyBoard = new SecureKeyBoard(this,
                new KeyboardParams(this, KeyboardType.IDCARD), cedtIdentityCard);
        cedtIdentityCard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                keyBoard.show();
                return false;
            }
        });

    }

    protected abstract void skipCardScan(int type_scan);



    private class MySimpleTextWatcher extends SimpleTextWatcher {

        private final ClearEditText editText;

        public MySimpleTextWatcher(ClearEditText editText) {
            this.editText = editText;
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            String s = charSequence.toString();
            if (editText.getId() == R.id.cedt_bank_card_number) {
                String str = s.toString().trim().replace(" ", "");
                String result = "";
                if (str.length() >= 4) {
                    editText.removeTextChangedListener(this);
                    for (int i = 0; i < str.length(); i++) {
                        result += str.charAt(i);
                        if ((i + 1) % 4 == 0) {
                            result += " ";
                        }
                    }
                    if (result.endsWith(" ")) {
                        result = result.substring(0, result.length() - 1);
                    }
                    editText.setText(result);
                    editText.addTextChangedListener(this);
                    editText.setSelection(editText.getText().toString().length());//焦点到输入框最后位置
                }
            }


            btnNext.setEnabled(checkName(false) && checkIdentityCard(false) && checkBankCard(false));

        }
    }

    private class MyOnFocusChangeListener implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            //姓名校验
            if (v.getId() == R.id.cedt_real_name) {
                if (!hasFocus) {
                    checkName(true);
                } else {
                    cedtRealName.setTextColor(ContextCompat.getColor(ctx, R.color.text_gray_common_title));
                }
            }
            //身份证校验
            if (v.getId() == R.id.cedt_identity_card) {
                if (!hasFocus) {
                    checkIdentityCard(true);
                } else {
                    cedtIdentityCard.setTextColor(ContextCompat.getColor(ctx, R.color.text_gray_common_title));
                }

            }
            //银行卡校验
            if (v.getId() == R.id.cedt_bank_card_number) {
                if (!hasFocus) {
                    keyBoard.dismiss();
                    checkBankCard(true);
                } else {
                    cedtBankCard.setTextColor(ContextCompat.getColor(ctx, R.color.text_gray_common_title));
                }
            }
//            //手机号校验
//            if (v.getId() == R.id.cedt_phone) {
//                if (!hasFocus) {
//                    checkPhone(true);
//                } else {
//                    cedtPhone.setTextColor(ContextCompat.getColor(ctx, R.color.text_gray_common_title));
//                }
//            }
        }
    }

    public boolean checkAll() {
        return (checkName(true)
                && checkIdentityCard(true)
                && checkBankCard(true));
//                && checkPhone(true));
    }

//    private boolean checkPhone(boolean showError) {
//        phone = cedtPhone.getText().toString();
//        if (phone.length() < 11 && phone.length() != 0) {
//            if (showError) {
//                cedtPhone.setTextColor(ContextCompat.getColor(this, R.color.text_red_common));
//                setErrorRemind("很抱歉，手机号码有误。");
//            }
//            return checkSuccess[3] = false;
//        } else {
//            setErrorRemind("");
//            cedtPhone.setTextColor(ContextCompat.getColor(this, R.color.text_gray_common_title));
//            return checkSuccess[3] = true;
//        }
//    }

    private boolean checkBankCard(boolean showError) {
        bankCard = cedtBankCard.getText().toString().replaceAll(" ", "");
        int length = bankCard.length();
        Logger.e("BankCardl ==>" + length);
        if ((length < 16 || length > 19) && length != 0) {
            if (showError) {
                cedtBankCard.setTextColor(ContextCompat.getColor(this, R.color.text_red_common));
                setErrorRemind("很抱歉，银行卡号码有误。");
            }
            return checkSuccess[2] = false;
        } if (length == 0){
            setErrorRemind("");
            cedtBankCard.setTextColor(ContextCompat.getColor(this, R.color.text_gray_common_title));
            return checkSuccess[2] = false;
        }else {
            setErrorRemind("");
            cedtBankCard.setTextColor(ContextCompat.getColor(this, R.color.text_gray_common_title));
            return checkSuccess[2] = true;
        }
    }

    private boolean checkIdentityCard(boolean showError) {
        identityCard = cedtIdentityCard.getText().toString();
        int length = identityCard.length();
        Logger.e("IdentityCardl ==>" + length);

        if (!RegexUtils.isIDCard18(identityCard)) {
            if (showError) {
                cedtIdentityCard.setTextColor(ContextCompat.getColor(this, R.color.text_red_common));
                setErrorRemind("请输入正确的身份证号码");
            }
            return checkSuccess[1] = false;
        } else {
            setErrorRemind("");
            cedtIdentityCard.setTextColor(ContextCompat.getColor(this, R.color.text_gray_common_title));
            return checkSuccess[1] = true;
        }
    }

    private boolean checkName(boolean showError) {
        realName = cedtRealName.getText().toString();
        int length = realName.length();
        Logger.e("Name ==>" + length);

        if (!CheckUtil.isValidHan(cedtRealName.getText().toString()) || length == 0) {
            if (showError) {
                cedtRealName.setTextColor(ContextCompat.getColor(this, R.color.text_red_common));
                setErrorRemind("很抱歉，姓名输入有误。");
            }
            return checkSuccess[0] = false;
        } else {
            setErrorRemind("");
            cedtRealName.setTextColor(ContextCompat.getColor(this, R.color.text_gray_common_title));
            return checkSuccess[0] = true;
        }
    }

    public void setErrorRemind(String error) {
        if (mErrorRemindTv == null) return;
        mErrorRemindTv.setText(error);
        mErrorRemindTv.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_next) {
           if (checkAll()) {
               BankCardInfo bankCardInfo = new BankCardInfo();
               bankCardInfo.setBankCard(bankCard);
               bankCardInfo.setIdCard(identityCard);
               bankCardInfo.setUserName(realName);
               goNext(bankCardInfo);

//               accountAddBankCard(bankCard, identityCard, phone, realName);
           }
        }
    }



    private void accountAddBankCard(final String bankCard, final String idCard, final String mobile, final String userName) {

        new MyNetAsyncTask(ctx, true) {
            BankCardInfoEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = TopApp
                        .getInstance()
                        .getHttpService()
                        .accountAddBankCard(
                                TopApp.getInstance().getLoginService().token, bankCard, idCard, mobile, userName);
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {

                        BankCardInfo bankCardInfo = new BankCardInfo();
                        bankCardInfo.setBankCard(bankCard);
                        bankCardInfo.setRemark(response.getData().getRemark());
                        bankCardInfo.setHaveBind(response.getData().getHaveBind());
                        bankCardInfo.setIdCard(idCard);
                        bankCardInfo.setUserName(userName);
                        TopApp.getInstance().getCurUserSp().setBoundedBankCard("true");
                        EventBus.getDefault().post(new CardBindSuccessEvent());
                        skipCardManageAddSuccess(bankCardInfo);
                        setResult(RESULT_OK);

                    } else {
                        btnNext.setEnabled(false);
                        mHandler.sendEmptyMessageAtTime(REST_BTN_ACTIVE, 3000); //3 秒后激活按钮
                        if ("100006".equals(response.getCode())) {
                            //弹出提示框
                            String[] split = response.getErrorsMsgStr().split("=");
                            if (split.length == 2) {
                                new PromptDialogAddCardError(activity, "很抱歉", split[0], split[1]).show();
                            } else {
                                new PromptDialogAddCardError(activity, "很抱歉", split[0], "").show();
                            }
                        } else {
                            ToastUtil.showCustomToastWithStayTime(response.getErrorsMsgStr(), 3000);
                        }

                    }
                } else {
                    com.toobei.common.utils.ToastUtil
                            .showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }


    protected abstract int setBtnColor();

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
            PromptDialogCalTel dialog = new PromptDialogCalTel(ctx, false, "确认拨打客服电话?", mServerNum);
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

                    PromptDialogCalTel dialog = new PromptDialogCalTel(ctx, false, "确认拨打客服电话?", mServerNum);
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

    protected abstract void goNext(BankCardInfo bankCardInfo);

    /**
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUI(CardScanEvent event) {
        if (event.getIdcard() != null){
            cedtRealName.setText(event.getIdcard().getName());
            cedtIdentityCard.setText(event.getIdcard().getIdCard());
        }

        if (event.getBankCard() != null){
            cedtBankCard.setText(event.getBankCard().getBankCard());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUI(UpCardScanDataEvent event) {
        showLoadProgress(true);
        CardScanHelper.INSTANCE.scan(event.getFile(),event.getScanType(), new CardScanHelper.CallBack() {
            @Override
            public void onSuccess() {
                showLoadContent();
            }

            @Override
            public void onFailed() {
                showLoadContent();
            }
        });
    }

    /*
      * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void cardBindSuccess(CardBindSuccessEvent event) {
       finish();
    }

}
