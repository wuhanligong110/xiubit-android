package com.v5ent.xiubit.service;

import android.app.Activity;
import android.content.Intent;

import com.toobei.common.TopApp;
import com.toobei.common.entity.BundBankcardDataEntity;
import com.toobei.common.entity.ExistInPlatformEntity;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.entity.OrgProductUrlEntity;
import com.toobei.common.entity.OrgProductUrlEntityData;
import com.toobei.common.entity.OrgUserCenterUrlData;
import com.toobei.common.entity.OrgUserCenterUrlEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.dialog.PromptDialogCommon;
import com.toobei.common.view.dialog.PromptForThirdBindDialog;
import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.activity.CardManagerAdd;
import com.v5ent.xiubit.activity.LoginActivity;
import com.v5ent.xiubit.activity.ThirdOrgBindFaileActivity;
import com.v5ent.xiubit.activity.ThirdOrgWebActivity;
import com.v5ent.xiubit.entity.IsBindOrgAcctEntity;
import com.v5ent.xiubit.event.BindThirdAccountSuccessEvent;

import org.greenrobot.eventbus.EventBus;
import org.xsl781.utils.Logger;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/6/20
 */

public class JumpThirdPartyService {

    private final int jumpType;
    public static final int JUMP_TYPE_BUY_PRODUCT = 1;   //跳转到第三方产品详情
    public static final int JUMP_TYPE_USER_CENTER = 2;  //跳转到第三方用户中心
    private final Activity activity;
    private final String orgName;
    private final String orgNum;
    private final String productId;
    private final boolean needBindCard;

    public JumpThirdPartyService(int jumpType, boolean needBindCard ,Activity activity, String orgName, String orgNum, String productId) {
        this.jumpType = jumpType;
        this.activity = activity;
        this.orgName = orgName;
        this.orgNum = orgNum;
        this.productId = productId;
        this.needBindCard = needBindCard;
        Logger.d("skipNeedBindCard:"+needBindCard);
    }

    public void run() {
        if (("OPEN_JIUFUQINGZHOU_WEB").equals(orgNum) && jumpType == 2) { //玖富特殊处理
            new JumpJiuhuService(needBindCard,"","",orgName,activity).skipPersonCenter();
        }else {
            if (!TextUtil.isEmpty(MyApp.getInstance().getLoginService().token)) {
                if (needBindCard) {
                    checkBoundCard();
                }else {
                    checkisIsBindOrgAcct();
                }

            } else {
                TopApp.loginAndStay = true;
                activity.startActivity(new Intent(activity, LoginActivity.class));
            }
        }


    }

    /**
     * 检查绑卡
     */
    private void checkBoundCard() {
        new MyNetAsyncTask(activity, false) {
            BundBankcardDataEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = TopApp.getInstance().getHttpService().accountGetBankCardStatus(MyApp.getInstance().getLoginService().token);
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        if (response.getData().isBundBankcard()) {
                            checkisIsBindOrgAcct();
                        } else {  //未绑卡
                            PromptDialogCommon promptDialogCommon = new PromptDialogCommon(activity, activity.getString(R.string.dialog_title_boundCard_remind), activity.getString(R.string.dialog_content_boundCard_remind), "确定");
                            promptDialogCommon.setBtnPositiveClickListener(new PromptDialogCommon.PositiveClicklistener() {
                                @Override
                                public void onPositiveClick() { //点击绑定,跳转绑卡页面
                                    activity.startActivity(new Intent(activity, CardManagerAdd.class));
                                }
                            });
                            promptDialogCommon.show();

                        }
                    } else {
                        com.toobei.common.utils.ToastUtil.showCustomToast(ctx.getString(com.toobei.common.R.string.pleaseCheckNetwork));
                    }
                }
            }
        }.execute();
    }

    /**
     * 检查用户是否是第三方老用户
     */
    private void checkisExistInPlatform() {
        new MyNetAsyncTask(activity, false) {
            ExistInPlatformEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().isExistInPlatform(MyApp.getInstance().getLoginService().token, orgNum);
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        if (response.getData().isIsExist()) {
//                            final PromptDialogCommon promptDialogCommon = new PromptDialogCommon(activity, "您已有" + orgName + "账号", "通过貅比特投资不能享受佣金、津贴、红包等奖励，建议您购买其他平台产品。", null, "好的");
//                            promptDialogCommon.show();

                            Intent intent = new Intent(ctx, ThirdOrgBindFaileActivity.class);
                            intent.putExtra("orgName",orgName);
                            activity.startActivity(intent);
                        } else {
                            //绑定第三方账户
                            bindOrgAccount();
                        }

                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(activity.getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }

    /**
     * 检查是否已经开通了第三方账户
     */
    private void checkisIsBindOrgAcct() {
        new MyNetAsyncTask(activity, false) {
            IsBindOrgAcctEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().isBindOrgAcct(MyApp.getInstance().getLoginService().token, orgNum);
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        if (response.getData().getIsBind()) {
                            switch (jumpType) {
                                case JUMP_TYPE_BUY_PRODUCT:
                                    jumpThridPartProductInfo();//跳转第三方产品详情页
                                    break;
                                case JUMP_TYPE_USER_CENTER:
                                    JumpThirdUserCenter();  //跳转第三方用户中心
                                    break;
                            }

                        } else {
                            PromptForThirdBindDialog promptDialogCommon = new PromptForThirdBindDialog(activity);
                            promptDialogCommon.setListener(new PromptForThirdBindDialog.DialogBtnOnClickListener() {

                                @Override
                                public void onClicked(PromptForThirdBindDialog dialog, boolean isCancel) {
                                    checkisExistInPlatform();  //检查是否是老用户
                                }

                            });
                            promptDialogCommon.show();
                        }

                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(activity.getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }

    /**
     * 绑定机构账户
     */
    public void bindOrgAccount() {
        new MyNetAsyncTask(activity, true) {
            LoginResponseEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().bindOrgAcct(MyApp.getInstance().getLoginService().token, orgNum);
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        EventBus.getDefault().post(new BindThirdAccountSuccessEvent());
                        switch (jumpType) {
                            case JUMP_TYPE_BUY_PRODUCT:
                                jumpThridPartProductInfo();      //跳转第三方产品详情页
                                break;
                            case JUMP_TYPE_USER_CENTER:
                                JumpThirdUserCenter();   //跳转第三方用户中心
                                break;
                        }
                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(activity.getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }


    /**
     * 跳转第三方产品详情页
     */
    private void jumpThridPartProductInfo() {
        new MyNetAsyncTask(activity, true) {
            OrgProductUrlEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().getOrgProductUrl(MyApp.getInstance().getLoginService().token, orgNum, productId);
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        OrgProductUrlEntityData data = response.getData();
                        String orgProductUrl = data.getOrgProductUrl();
                        ThirdOrgWebActivity.showThisActivityForPost(activity, orgProductUrl, getProductPostDate(data).getBytes(), orgName);
                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(activity.getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }


    /**
     * 绑定机构账户并跳转合作平台的个人中心
     */
    public void JumpThirdUserCenter() {

        new MyNetAsyncTask(activity, false) {
            OrgUserCenterUrlEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().getOrgUserCenterUrl(MyApp.getInstance().getLoginService().token, orgNum);
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        OrgUserCenterUrlData data = response.getData();
                        String userCenterUrl = data.getOrgUsercenterUrl();
                        ThirdOrgWebActivity.showThisActivityForPost(activity, userCenterUrl, getUserCenterPostDate(data).getBytes(), orgName);
                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(activity.getString(R.string.pleaseCheckNetwork));
                }
            }

        }.execute();
    }


    /**
     * 拼接跳转合作机构用户中心参数拼接
     *
     * @param data
     * @return
     */
    private static String getUserCenterPostDate(OrgUserCenterUrlData data) {
        return new StringBuffer("").append("orgAccount=" + data.getOrgAccount() + "&")
                .append("orgKey=" + data.getOrgKey() + "&")
                .append("orgNumber=" + data.getOrgNumber() + "&")
                .append("requestFrom=" + data.getRequestFrom() + "&")
                .append("sign=" + data.getSign() + "&")
                .append("timestamp=" + data.getTimestamp())
                .toString();
    }

    /**
     * 跳转机构产品页
     * 拼接post请求参数
     *
     * @param data
     */
    private String getProductPostDate(OrgProductUrlEntityData data) {
        return new StringBuffer("").append("orgAccount=" + data.getOrgAccount() + "&")
                .append("orgKey=" + data.getOrgKey() + "&")
                .append("orgNumber=" + data.getOrgNumber() + "&")
                .append("requestFrom=" + data.getRequestFrom() + "&")
                .append("sign=" + data.getSign() + "&")
                .append("timestamp=" + data.getTimestamp()+"&")
                .append("thirdProductId=" + data.getThirdProductId()+"&")
                .append("txId=" + data.getTxId())
                .toString();
    }

}
