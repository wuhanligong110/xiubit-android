package com.toobei.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.AddressCity;
import com.toobei.common.entity.AddressCityDatasDataEntity;
import com.toobei.common.entity.AddressProvince;
import com.toobei.common.entity.AddressProvinceDatasDataEntity;
import com.toobei.common.entity.BankCardInfo;
import com.toobei.common.entity.BankCardInfoEntity;
import com.toobei.common.entity.BankInfo;
import com.toobei.common.entity.BankInfoDatasDataEntity;
import com.toobei.common.entity.ErrorResponse;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.utils.KeyboardUtil;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.StringUtil;
import com.toobei.common.utils.TextDecorator;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.utils.UpdateViewCallBack;
import com.toobei.common.view.HeaderLayout;
import com.toobei.common.view.SelectWheelAdapter;
import com.toobei.common.view.popupwindow.PayPasswordPopupWindow;
import com.toobei.common.view.popupwindow.PayPasswordPopupWindow.CallBack;
import com.toobei.common.view.popupwindow.SelectWheelPopup;
import com.toobei.common.view.timeselector.Utils.TextUtil;

import org.xsl781.ui.view.ClearEditText;
import org.xsl781.utils.SimpleTextWatcher;

import java.util.Date;
import java.util.List;

/**
 * 公司: tophlc
 * 类说明： 提现 界面 基类
 *
 * @date 2016-4-8
 */
public abstract class TopWithdraw extends TopBaseActivity implements OnClickListener {

    //	private KeyboardPopupWindow keyboardPopupWindow;
    private Button btnCommit;
    private TextView textAccountBalance, textBankCardInfo, textToAccountTime, textCanWithdrawCost, infoTv;
    private TextView textBankName;//09-13添加 开户银行
    private ClearEditText cedtInputMoney, cedtAddress;
    private String strInputMoney, amount, bankCode, bankName, strCity, strBranch;
    private float balance = -1;
    private String strBankCardInfo = "", strToAccountTime = "", strCost = "0";
    private boolean isHasFee;
    /**
     * 输入框小数的位数
     */
    private static final int DECIMAL_DIGITS = 2;
    private TextView textProvince, textCity;
    private List<AddressProvince> addressProvinces;
    private List<AddressCity> addressCities;
    private String strProvinces;
    private AddressProvince selectProvince;
    private AddressCity selectCity;
    private boolean isSelectProviceChanaged = false;//选择的省份是否已经改变
    private SelectWheelPopup provinceSelectWheelPopup, citySelectWheelPopup;
    private View rootView;
    private String needInputBranck = null;//是否需要输入支行，为空时，表示未刷到数据
    private ViewGroup vgBankBranchLL;
    private String withdrawalInfo = "";
    private int limitTimes;
    private PayPasswordPopupWindow popupWindow;
    private SelectWheelPopup selectWheelPopup;
    private List<BankInfo> bankInfos;
    private BankInfo selectBankInfo;
    private String selectedbank;
    private ViewGroup vgBankNameLL;
    private TextView allWithdrawOutTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //	activity = this;
        balance = getIntent().getFloatExtra("accountBalance", -1.0f);
        rootView = LayoutInflater.from(ctx).inflate(R.layout.activity_withdraw, null);
        setContentView(rootView);
        initView();
        hideSoftInputView();
        getWithdrawBankCard();
    }

    protected abstract void skipCardManagerAdd(TopBaseActivity activity);

    protected abstract void showWithdrawList(TopBaseActivity activity);

    protected abstract Intent getIntentWithdrawSuccess(TopBaseActivity activity);

    public void initView() {
        textAccountBalance = (TextView) findViewById(R.id.text_account_balance);
        textAccountBalance.setTextColor(ContextCompat.getColor(ctx, setTextColor()));
        if (balance >= 0) {
            textAccountBalance.setText(StringUtil.formatNumber(balance + ""));
        }
        infoTv = (TextView) findViewById(R.id.infoTv);
        textBankCardInfo = (TextView) findViewById(R.id.text_bank_card_info);
        textBankName = (TextView) findViewById(R.id.text_bank_name);
        textBankName.setOnClickListener(this);
        textToAccountTime = (TextView) findViewById(R.id.text_to_account_time);
        textCanWithdrawCost = (TextView) findViewById(R.id.text_can_withdraw_cost);
        cedtInputMoney = (ClearEditText) findViewById(R.id.cedt_input_money);
        allWithdrawOutTv = (TextView) findViewById(R.id.allWithdrawOutTv);
        allWithdrawOutTv.setOnClickListener(this);
        cedtInputMoney.addTextChangedListener(new SimpleTextWatcher() {

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                super.onTextChanged(charSequence, i, i2, i3);
                strInputMoney = cedtInputMoney.getText().toString();
                if (TextUtil.isEmpty(strInputMoney)) strInputMoney = "0";
                float moneyNum = Float.parseFloat(strInputMoney);
                if (strInputMoney.length() > 0 && moneyNum >= 20) {
                    btnCommit.setEnabled(true);
                    btnCommit.setText("提交");
                } else {
                    btnCommit.setEnabled(false);
                    btnCommit.setText("余额及提现金额需 ≥ 20元");
                }
            }
        });
        cedtInputMoney.setFilters(new InputFilter[]{lengthfilter});
//		cedtInputMoney.setInputType(InputType.TYPE_NULL);

        btnCommit = (Button) findViewById(R.id.btn_commit);
        btnCommit.setOnClickListener(this);
        btnCommit.setEnabled(false);
        btnCommit.setBackgroundResource(setButtonDrawable());
        textProvince = (TextView) findViewById(R.id.text_province);
        textCity = (TextView) findViewById(R.id.text_city);
        textProvince.setOnClickListener(this);
        textCity.setOnClickListener(this);
        cedtAddress = (ClearEditText) findViewById(R.id.cedt_address);
        initTopView();

        vgBankBranchLL = (ViewGroup) findViewById(R.id.bank_branch_ll);
        vgBankNameLL = (ViewGroup) findViewById(R.id.bank_name_ll);
        String telstr = TopApp.getInstance().getDefaultSp().getServiceTelephone();
        if (!TextUtils.isEmpty(telstr)) {
            String info = ctx.getString(R.string.warm_prompt_bank_add) + telstr;
            TextDecorator.decorate(infoTv, info).setTextColor(R.color.text_link_blue, telstr).build();
        }

        KeyboardUtil.setupUIListenerAndCloseKeyboard(rootView, this);

    }

    /**
     * 设置小数位数控制
     */
    InputFilter lengthfilter = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            // 删除等特殊字符，直接返回
            if ("".equals(source.toString())) {
                return null;
            }
            String dValue = dest.toString();
            String[] splitArray = dValue.split("\\.");
            if (splitArray.length > 1) {
                String dotValue = splitArray[1];
                int diff = dotValue.length() + 1 - DECIMAL_DIGITS;
                if (diff > 0) {
                    return source.subSequence(start, end - diff);
                }
            }
            return null;
        }
    };

    private void initTopView() {
        headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
        headerLayout.showTitle(R.string.withdraw);
        headerLayout.showLeftBackButton();
        headerLayout.showRightTextButton(R.string.withdraw_record, new OnClickListener() {

            @Override
            public void onClick(View v) {
                showWithdrawList(ctx);
            }
        });
        setTranslucentStatus(true);
        headerLayout.setHeadViewCoverStateBar();
    }

    private void accountUserWithdrawRequest() {
        new MyNetAsyncTask(ctx, true) {
            LoginResponseEntity response2;

            @Override
            protected void doInBack() throws Exception {
                if ("true".equals(needInputBranck)) {
                    response2 = TopApp.getInstance().getHttpService().accountUserWithdrawRequest(TopApp.getInstance().getLoginService().token, amount, bankCode, selectedbank, textCity.getText().toString(), strBranch);
                } else {
                    response2 = TopApp.getInstance().getHttpService().accountUserWithdrawRequest(TopApp.getInstance().getLoginService().token, amount, bankCode, bankName, strCity, strBranch);
                }
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response2 != null) {
                    if ("0".equals(response2.getCode())) {
                        //	Intent intent = new Intent(TopWithdraw.this, WithdrawSuccess.class);
                        Intent intent = getIntentWithdrawSuccess(TopWithdraw.this);
                        intent.putExtra("strBankCardInfo", strBankCardInfo);
                        intent.putExtra("strToAccountTime", strToAccountTime);
                        intent.putExtra("strMoney", amount);
                        intent.putExtra("withdrawalInfo", withdrawalInfo);
                        intent.putExtra("needInputBranck", needInputBranck);
                        intent.putExtra("inputBranck", strProvinces + strCity + " " + strBranch);
                        skipActivity(TopWithdraw.this, intent);

                    } else {
                        ToastUtil.showCustomToast(response2.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }

    private void getWithdrawBankCard() {

        new MyNetAsyncTask(ctx, true) {
            BankCardInfoEntity response;

            @Override
            protected void doInBack() throws Exception {
                if (balance < 0) {
                    balance = TopApp.getInstance().getAccountService().getMyAccountBlance();
                }
                response = TopApp.getInstance().getHttpService().accountGetWithdrawBankCardInfo(TopApp.getInstance().getLoginService().token);
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if ("0".equals(response.getCode())) {

                        BankCardInfo data = response.getData();
                        String cardNo = data.getBankCard();
                        if (cardNo.length() > 6) {
                            cardNo = cardNo.substring(0, 4) + "***********" + cardNo.substring(cardNo.length() - 4);
                            strBankCardInfo = data.getBankName() + "(尾号" + cardNo.substring(cardNo.length() - 4) + ")";
                        }
                        boolean needkaiHuHang = data.isNeedkaiHuHang(); // 是否需要开户行
                        if (needkaiHuHang) {
                            vgBankNameLL.setVisibility(View.VISIBLE);
                            textBankCardInfo.setText(cardNo);
                        } else {
                            vgBankNameLL.setVisibility(View.GONE);

                            textBankCardInfo.setText(strBankCardInfo);
                        }

                        Date now = new Date();
                        //设置预计到账 时间字段
                        strToAccountTime = data.getPaymentDate();
                        textToAccountTime.setText(strToAccountTime);

                        String strWithdrawCost = "";
                        isHasFee = data.isHasFee();
                        strCost = data.getFee();
                        limitTimes = Integer.valueOf(data.getLimitTimes());
                        if (isHasFee && limitTimes <= 0) {
                            //有手续费
                            String money = StringUtil.getIntStr(strCost);
                            strWithdrawCost = "本次" + money + "元";
                            withdrawalInfo = "本次提现手续费" + money + "元";
                            TextDecorator.decorate(textCanWithdrawCost,strWithdrawCost).setTextColor(R.color.text_blue_common,money).build();
//                            textCanWithdrawCost.setText(strWithdrawCost);
                        } else {
                            //没有手续费
                            String s1 = "本月还可以免费提现 ";
                            String s2 = TopWithdraw.this.limitTimes + "";
                            String s3 = " 次";
                            strWithdrawCost = s1 + s2 + s3;
                            SpannableString spannableString = new SpannableString(strWithdrawCost);
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(ctx, R.color.text_black_common)), 0, s1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(ctx, TopApp.getInstance().getHttpService().isLCDS() ? R.color.text_blue_common : R.color.text_red_common)), s1.length(), s1.length() + s2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(ctx, R.color.text_black_common)), s1.length() + s2.length(), s1.length() + s2.length() + s3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            withdrawalInfo = "本次免费";
                            textCanWithdrawCost.setText(spannableString);
                        }
//                        textCanWithdrawCost.setText(strWithdrawCost);
                        textAccountBalance.setText(StringUtil.formatNumber(balance + ""));

                        bankCode = data.getBankCard();
                        bankName = data.getBankName();
                        strCity = data.getCity();
                        strBranch = data.getKaiHuHang();

                        if (needkaiHuHang) {
                            needInputBranck = "true";
                            vgBankBranchLL.setVisibility(View.VISIBLE);
                        } else {
                            needInputBranck = "false";
                        }
                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                        List<ErrorResponse> errors = response.getErrors();
                        if (errors != null && errors.size() > 0) {
                            for (ErrorResponse error : errors) {
                                if ("noBindCard".equals(error.getCode())) {
                                    TopApp.getInstance().getAccountService().setIsBounded("false");
                                    ToastUtil.showCustomToast("请先绑定银行卡");
                                    skipCardManagerAdd(TopWithdraw.this);
                                    //	TopWithdraw.this.showActivity(TopWithdraw.this,
                                    //			CardManagerAdd.class);
                                }
                            }
                        }

                    }
                } else {
                    ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }

    private void getAddressProvinces(final UpdateViewCallBack<List<AddressProvince>> callBack) {
        if (addressProvinces != null) {
            callBack.updateView(null, addressProvinces);
            return;
        }
        new MyNetAsyncTask(ctx, true) {
            AddressProvinceDatasDataEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = TopApp.getInstance().getHttpService().accountQueryAllProvince(TopApp.getInstance().getLoginService().token);
            }

            @Override
            protected void onPost(Exception e) {
                //登录成功
                if (e == null && response != null) {
                    if ("0".equals(response.getCode())) {
                        addressProvinces = response.getData().getDatas();
                        callBack.updateView(null, addressProvinces);
                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }


    private void getAllBank(final UpdateViewCallBack<List<BankInfo>> callBack) {
        if (bankInfos != null) {
            callBack.updateView(null, bankInfos);
            return;
        }

        new MyNetAsyncTask(ctx, true) {
            BankInfoDatasDataEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = TopApp.getInstance().getHttpService().accountQueryAllBank(TopApp.getInstance().getLoginService().token);
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if ("0".equals(response.getCode())) {
                        bankInfos = response.getData().getDatas();
                        callBack.updateView(null, bankInfos);
                    } else {
                        com.toobei.common.utils.ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    com.toobei.common.utils.ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }

    private void getAddressCitys(final UpdateViewCallBack<List<AddressCity>> callBack, final String provinceId) {
        if (addressCities != null) {
            callBack.updateView(null, addressCities);
            return;
        }
        new MyNetAsyncTask(ctx, true) {
            AddressCityDatasDataEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = TopApp.getInstance().getHttpService().accountQueryCityByProvince(TopApp.getInstance().getLoginService().token, provinceId);
            }

            @Override
            protected void onPost(Exception e) {
                //登录成功
                if (e == null && response != null) {
                    if ("0".equals(response.getCode())) {
                        addressCities = response.getData().getDatas();
                        callBack.updateView(null, addressCities);
                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.allWithdrawOutTv){
            cedtInputMoney.setText(StringUtil.formatNumber(balance + ""));
        }else if (v.getId() == R.id.btn_commit) {
//            if (!StringUtils.isDouble(strInputMoney.trim())) {
//                ToastUtil.showCustomToast("数字格式错误！");
//                return;
//            }

            float f = Float.parseFloat(strInputMoney);
            if (f < 0.1) {
                ToastUtil.showCustomToast("亲，提现金额不能小于0.1元哦~");
                return;
            }

            if (isHasFee && limitTimes <= 0) {
                //有手续费
                if (balance <= Float.parseFloat(strCost) + 0.01 || f > balance - Float.parseFloat(strCost)) {
                    ToastUtil.showCustomToast("余额不足，无法提现。");
                    return;
                }
            } else {
                //没有手续费
                if (f > balance) {
                    ToastUtil.showCustomToast("余额不足，无法提现。");
                    return;
                }
            }

            amount = f + "";

            if ("true".equals(needInputBranck)) {
                if (selectBankInfo == null || StringUtil.isEmpty(selectedbank)) {
                    ToastUtil.showCustomToast("请选择开户银行");
                    return;
                }
                if (selectCity == null || StringUtil.isEmpty(selectCity.getCityName())) {
                    ToastUtil.showCustomToast("请选择开户行城市");
                    return;
                }
                strBranch = cedtAddress.getText().toString();
                if (StringUtil.isEmpty(strBranch)) {
                    ToastUtil.showCustomToast("请填写支行名称");
                    return;
                }
            }

            //验证交易密码
            if (popupWindow == null) {

                popupWindow = new PayPasswordPopupWindow(ctx,amount ,Float.parseFloat(strCost),new CallBack() {

                    @Override
                    public void onBtnForgeLoginPasswdClick() {
                        forgetPayPwd();
//						showActivity(RechargeActivity.this, PwdManagerPay.class);
                    }

                    @Override
                    public void OnPayCompleted(boolean isPassed) {
                        if (isPassed) {
                            accountUserWithdrawRequest();
                        }
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });
            }
            popupWindow.showPopupWindow(btnCommit.getRootView());
//			PromptDialogInputPayPwd dialog = new PromptDialogInputPayPwd(ctx, amount, this);
//			dialog.show();
        }
        else if (v.getId() == R.id.text_province) {
            hideSoftInputView();
            getAddressProvinces(new UpdateViewCallBack<List<AddressProvince>>() {

                @Override
                public void updateView(Exception e, final List<AddressProvince> addresses) {

                    if (addresses == null) return;
                    if (provinceSelectWheelPopup == null) {
                        SelectWheelAdapter adapter = new SelectWheelAdapter(ctx, addresses) {
                            @Override
                            public CharSequence getItemText(int index) {
                                return addresses.get(index).getProvinceName();
                            }
                        };
                        provinceSelectWheelPopup = new SelectWheelPopup(ctx, addresses, adapter);
                        provinceSelectWheelPopup.setOnSelectWheelPopupCompletedListener(new SelectWheelPopup.OnSelectWheelPopupCompletedListener() {
                            @Override
                            public void onSelectWheelPopupCompleted(SelectWheelPopup selectWheelPopup, Object mCurAddress) {
                                isSelectProviceChanaged = true;
                                addressCities = null;
                                selectCity = null;
                                strCity = null;
                                textCity.setText("选择城市");
                                textCity.setTextColor(ContextCompat.getColor(ctx, R.color.text_color_hint_common));

                                selectProvince = (AddressProvince) mCurAddress;

                                if (selectProvince != null) { //避免没有数据报空异常
                                    strProvinces = selectProvince.getProvinceName();
                                    textProvince.setText(strProvinces);
                                    textProvince.setTextColor(ContextCompat.getColor(ctx, R.color.BLACK));
                                }

                            }
                        });
                    }
                    provinceSelectWheelPopup.showAtLocation(rootView);
                }
            });
        } else if (v.getId() == R.id.text_city) {
            if (selectProvince == null) {
                ToastUtil.showCustomToast("请先选择省份");
                return;
            }
            hideSoftInputView();

            getAddressCitys(new UpdateViewCallBack<List<AddressCity>>() {

                @Override
                public void updateView(Exception e, final List<AddressCity> addresses) {
                    if (addresses == null) return;
                    if (citySelectWheelPopup == null || isSelectProviceChanaged) {

                        SelectWheelAdapter adapter = new SelectWheelAdapter(ctx, addresses) {
                            @Override
                            public CharSequence getItemText(int index) {
                                return addresses.get(index).getCityName();
                            }
                        };
                        citySelectWheelPopup = new SelectWheelPopup(ctx, addresses, adapter);
                        citySelectWheelPopup.setOnSelectWheelPopupCompletedListener(new SelectWheelPopup.OnSelectWheelPopupCompletedListener() {
                            @Override
                            public void onSelectWheelPopupCompleted(SelectWheelPopup selectWheelPopup, Object mCurAddress) {
                                isSelectProviceChanaged = false;
                                selectCity = (AddressCity) mCurAddress;
                                strCity = selectCity.getCityName();
                                textCity.setText(strCity);
                                textCity.setTextColor(ContextCompat.getColor(ctx, R.color.BLACK));
                            }
                        });
                    }
                    citySelectWheelPopup.showAtLocation(rootView);
                }
            }, selectProvince.getProvinceId());
        } else if (v.getId() == R.id.text_bank_name) { //选择开户银行

            //popu银行列表
            getAllBank(new UpdateViewCallBack<List<BankInfo>>() {
                @Override
                public void updateView(Exception e, final List objects) {
                    if (selectWheelPopup == null) {
                        SelectWheelAdapter adapter = new SelectWheelAdapter(ctx, bankInfos) {
                            @Override
                            public CharSequence getItemText(int index) {
                                return bankInfos.get(index).getBankName();
                            }
                        };
                        selectWheelPopup = new SelectWheelPopup(ctx, objects, adapter);
                        selectWheelPopup.setOnSelectWheelPopupCompletedListener(new SelectWheelPopup.OnSelectWheelPopupCompletedListener() {
                            @Override
                            public void onSelectWheelPopupCompleted(SelectWheelPopup selectWheelPopup, Object mCurAddress) {
                                selectBankInfo = (BankInfo) mCurAddress;
                                selectedbank = selectBankInfo.getBankName();
                                textBankName.setText(selectedbank);
                                textBankName.setTextColor(ContextCompat.getColor(ctx, R.color.text_black_common));
                            }
                        });
                    }
                    selectWheelPopup.showAtLocation(rootView);
                }
            });
        }
    }

    protected abstract int setTextColor();

    protected abstract void forgetPayPwd();

    protected abstract int setButtonDrawable();

}