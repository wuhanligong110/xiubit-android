package com.toobei.common.service;

import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.AccountHomeEntity;
import com.toobei.common.entity.BundBankcardDataEntity;
import com.toobei.common.entity.CheckResponseEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.UpdateViewCallBack;
import com.toobei.common.view.timeselector.Utils.TextUtil;

import org.xsl781.utils.Logger;

public abstract class TopAccountService {
    private String isBounded = null;//是否绑卡
    private String isHasPayPwd;
    private boolean isMineNeedRefresh = false;//是否需要更新我的界面,有购买，充值，提现动作，刷新界面


    /**
     * 功能：跳转到绑卡界面 CardManagerAdd
     *
     * @param activity
     */
    public abstract void showCardManagerAdd(TopBaseActivity activity);

    /**
     * 功能：跳转到初始化交易密码界面
     *
     * @param activity
     */
    public abstract void showPwdManagerInitPay(TopBaseActivity activity);

    public void clearAccoutService() {
        isBounded = null;
        isHasPayPwd = null;
    }

    /**
     * 功能：判断是否绑卡，是否初始化交易 密码，并进行相应的跳转,如果均有，则回调
     *
     * @param activity
     * @param callBack 为true表示已经绑卡和有交易密码
     */
    public void checkBoundedCardAndInitPayAndSkip(final TopBaseActivity activity, final UpdateViewCallBack<Boolean> callBack) {
        checkBoundedCard(activity, new UpdateViewCallBack<String>() {

            @Override
            public void updateView(Exception e, String bindFlag) {
                if (bindFlag != null && bindFlag.equalsIgnoreCase("true")) {
                    checkInitPayPwd(activity, new UpdateViewCallBack<String>() {

                        @Override
                        public void updateView(Exception e, String isHasPayPwd) {
                            if (isHasPayPwd != null && isHasPayPwd.equalsIgnoreCase("false")) {
                                showPwdManagerInitPay(activity);
                            } else if (isHasPayPwd != null && isHasPayPwd.equalsIgnoreCase("true")) {
                                if (callBack != null) {
                                    callBack.updateView(null, true);
                                }
                            }
                        }
                    });

                } else {
                    com.toobei.common.utils.ToastUtil.showCustomToast("亲，还没有绑定银行卡，先绑定银行卡吧~");
                    showCardManagerAdd(activity);
                }
            }
        });
    }

    public void checkBoundedCard(TopBaseActivity activity, UpdateViewCallBack<String> callBack) {

        isBounded = TopApp.getInstance().getCurUserSp().isBoundedBankCard();  // TopApp.getInstance().getCurUserSp().setBoundedBankCard("false");
        if (isBounded == null) {
            Logger.d("isBounded == null accountGetUserBindCard==");
            accountGetUserBindCard(activity, callBack);
        } else if (isBounded.equalsIgnoreCase("false")) {
            if (callBack != null) {
                Logger.e("isBounded equalsIgnoreCase(\"false\")");
                callBack.updateView(null, "false");
            }
        } else if (isBounded.equalsIgnoreCase("true")) {
            if (callBack != null) {
                Logger.e("isBounded == null equalsIgnoreCase(\"true\")");
                callBack.updateView(null, "true");
            }
        }
    }

    public void checkInitPayPwd(TopBaseActivity activity, UpdateViewCallBack<String> callBack) {
        if (isHasPayPwd == null) {
            isHasPayPwd = TopApp.getInstance().getCurUserSp().isHasPayPwd();
        }
        if (isHasPayPwd == null) {
            verifyPayPwdState(activity, callBack);
        } else if (isHasPayPwd.equals("true")) {
            if (callBack != null) {
                callBack.updateView(null, "true");
            }
        } else {
            if (callBack != null) {
                callBack.updateView(null, "false");
            }
            //	com.tophlc.common.utils.ToastUtil.showCustomToast("请先设置交易密码");
            //跳转到初始化交易 密码
            //	showPwdManagerInitPay(activity);
        }
    }

    /**
     * 功能：初始化用户绑卡信息
     *
     * @param activity
     */
    public void accountGetUserBindCard(TopBaseActivity activity, final UpdateViewCallBack<String> callBack) {
        new MyNetAsyncTask(activity, false) {
            BundBankcardDataEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = TopApp.getInstance().getHttpService().accountGetBankCardStatus(TopApp.getInstance().getLoginService().token);
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0") && response.getData().isBundBankcard()) {
                        isBounded = "true";
                        TopApp.getInstance().getCurUserSp().setBoundedBankCard("true");
                        Logger.d("===>>isBundBankcard==" + response.getData().isBundBankcard());
                        if (callBack != null) {
                            callBack.updateView(null, "true");
                        }
                        return;
                        //	changeView();
                    } else {
                        isBounded = "false";
                        TopApp.getInstance().getCurUserSp().setBoundedBankCard("false");
                        if (callBack != null) {
                            callBack.updateView(null, "false");
                        }
                    }
                } else {
                    com.toobei.common.utils.ToastUtil.showCustomToast(ctx.getString(R.string.pleaseCheckNetwork));
                }
                if (callBack != null) {
                    callBack.updateView(e, null);
                }
            }
        }.execute();
    }

    public void verifyPayPwdState(TopBaseActivity activity, final UpdateViewCallBack<String> callBack) {
        if (TextUtil.isEmpty(TopApp.getInstance().getLoginService().token)) return;
        new MyNetAsyncTask(activity, false) {
            CheckResponseEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = TopApp.getInstance().getHttpService().userVerifyPayPwdState(TopApp.getInstance().getLoginService().token);
            }

            @Override
            protected void onPost(Exception e) {
                //登录成功
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        if (response.getData().getRlt().equals("true")) {
                            TopApp.getInstance().getCurUserSp().setHasPayPwd("true");
                            if (callBack != null) {
                                callBack.updateView(null, "true");
                            }
                        } else {
                            TopApp.getInstance().getCurUserSp().setHasPayPwd("false");
                            if (callBack != null) {
                                callBack.updateView(null, "false");
                            }
                        }
                        return;
                    } else {
                        com.toobei.common.utils.ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    com.toobei.common.utils.ToastUtil.showCustomToast(ctx.getString(R.string.pleaseCheckNetwork));
                }
                if (callBack != null) {
                    callBack.updateView(e, null);
                }
            }
        }.execute();
    }

    /**
     * 功能：得到我的账户余额
     *
     * @return
     */
    public float getMyAccountBlance() {
        try {
            AccountHomeEntity response = TopApp.getInstance().getHttpService().accountMyAccountHome(TopApp.getInstance().getLoginService().token);
            if (response != null && response.getCode().equals("0")) {
                return Float.parseFloat(response.getData().getTotalAmount());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getIsBounded() {
        return isBounded;
    }

    public String getIsHasPayPwd() {
        return isHasPayPwd;
    }

    public void setIsBounded(String isBounded) {
        if (isBounded == null || isBounded.equalsIgnoreCase("true") || isBounded.equalsIgnoreCase("false")) {
            TopApp.getInstance().getCurUserSp().setBoundedBankCard(isBounded);
            this.isBounded = isBounded;
        }
    }

    public void setIsHasPayPwd(String isHasPayPwd) {
        if (isHasPayPwd == null || isHasPayPwd.equalsIgnoreCase("true") || isHasPayPwd.equalsIgnoreCase("false")) {
            TopApp.getInstance().getCurUserSp().setHasPayPwd("true");
            this.isHasPayPwd = isHasPayPwd;
        }
    }

    public boolean isMineNeedRefresh() {
        return isMineNeedRefresh;
    }

    public void setMineNeedRefresh(boolean isMineNeedRefresh) {
        this.isMineNeedRefresh = isMineNeedRefresh;
    }


}
