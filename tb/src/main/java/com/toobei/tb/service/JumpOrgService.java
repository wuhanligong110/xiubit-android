package com.toobei.tb.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.BundBankcardDataEntity;
import com.toobei.common.entity.ExistInPlatformEntity;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.entity.OrgProductUrlEntity;
import com.toobei.common.entity.OrgProductUrlEntityData;
import com.toobei.common.entity.OrgUserCenterUrlData;
import com.toobei.common.entity.OrgUserCenterUrlEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.MyWebView;
import com.toobei.common.view.OnMyWebViewListener;
import com.toobei.common.view.dialog.JumpProgressDialog;
import com.toobei.common.view.dialog.PromptDialogCommon;
import com.toobei.tb.MyApp;
import com.toobei.tb.R;
import com.toobei.tb.activity.CardManagerAdd;
import com.toobei.tb.activity.LoginActivity;
import com.toobei.tb.activity.MyInvestOrgActivity;
import com.toobei.tb.activity.OrgProductWebActivity;
import com.toobei.tb.entity.IsBindOrgAcctEntity;

import org.apache.http.util.EncodingUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 提供跳转第三方机构产品详情或者个人中心
 * Created by hasee-pc on 2016/12/27.
 */

public class JumpOrgService {
    private TopBaseActivity mActivity;
    private String mOrgNo; //机构编码
    private String mProductId; //产品id
    private MyWebView mMyWebViewX5;
    private String mOrgName;

    public JumpOrgService() {

    }

    public void checkAndJumpToBuyProduct(TopBaseActivity activity, String orgNo, String productId, MyWebView myWebViewX5, String orgName) {
        mActivity = activity;
        mOrgNo = orgNo;
        mProductId = productId;
        mMyWebViewX5 = myWebViewX5;
        mOrgName = orgName;
        checkLogin(); 
    }

    /**
     * 检查登录状态
     */
    private void checkLogin() {
//        Log.e("JumpOrgService","checkLogin");
        if (!MyApp.getInstance().getLoginService().isCachPhoneExist()) { //未登录
            mActivity.startActivity(new Intent(mActivity, LoginActivity.class));
        } else {  //已登录
            checkBoundCard();
        }

    }

    /**
     * 检查绑卡状态
     */
    private void checkBoundCard() {

        Log.e("JumpOrgService", "checkBoundCard");
        new MyNetAsyncTask(mActivity, false) {
            BundBankcardDataEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = TopApp.getInstance().getHttpService().accountGetBankCardStatus(MyApp.getInstance().getLoginService().token);
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        if (response.getData().isBundBankcard()) { //已绑卡
                            checkisIsBindOrgAcct();  //判断是否已绑定第三方机构
                        } else {  //未绑卡
                            // 弹窗银行卡绑卡提示
                            final PromptDialogCommon promptDialogCommon = new PromptDialogCommon(mActivity, mActivity.getString(R.string.dialog_title_boundCard_remind), mActivity.getString(R.string.dialog_content_boundCard_remind), "立即绑定");
                            promptDialogCommon.setBtnPositiveClickListener(new PromptDialogCommon.PositiveClicklistener() {
                                @Override
                                public void onPositiveClick() { //点击绑定,跳转绑卡页面
                                    mActivity.startActivity(new Intent(mActivity, CardManagerAdd.class));
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
     * 检查账号是否已经绑定第三方平台
     */
    private void checkisIsBindOrgAcct() {
        new MyNetAsyncTask(mActivity, false) {
            IsBindOrgAcctEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().isBindOrgAcct(MyApp.getInstance().getLoginService().token, mOrgNo);
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        if (response.getData().getIsBind()) { //已绑定第三方平台
                            //直接绑定产品并跳转
                            bindOrgAndLoadWeb();
                        } else { //未绑定第三方平台
                            checkisExistInPlatform(); //检查是否是第三方平台老用户
                        }

                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(mActivity.getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }

    /**
     * 检查账号是否已经存在于第三方平台
     */
    private void checkisExistInPlatform() {
//        Log.e("JumpOrgService","checkisExistInPlatform");
        new MyNetAsyncTask(mActivity, false) {
            ExistInPlatformEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().isExistInPlatform(MyApp.getInstance().getLoginService().token, mOrgNo);
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        if (response.getData().isIsExist()) { //是第三方平台老用户
                            ToastUtil.showCustomToast("您是" + mOrgName + "老用户,通过T呗投资不能享受红包等奖励，建议购买其他平台产品", Toast.LENGTH_LONG);
                        } else { //不是第三方老用户
                            //弹窗第三方平台绑定提示
//                            ToastUtil.showCustomToast("不存在");
                            PromptDialogCommon promptDialogCommon = new PromptDialogCommon(mActivity, mActivity.getString(R.string.dialog_title_boundOrg_remind), mActivity.getString(R.string.dialog_content_boundOrg_remind), "立即绑定");
                            promptDialogCommon.setBtnPositiveClickListener(new PromptDialogCommon.PositiveClicklistener() {
                                @Override
                                public void onPositiveClick() { //点击绑定,发起绑定第三方平台网络请求
                                    bindOrgAccount(mOrgNo, mActivity);
                                }
                            });
                            promptDialogCommon.show();
                        }

                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(mActivity.getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }


    /**
     * 绑定机构账户
     *
     * @param orgNo
     */
    public void bindOrgAccount(final String orgNo, final Context context) {
        new MyNetAsyncTask(context, true) {
            LoginResponseEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().bindOrgAcct(MyApp.getInstance().getLoginService().token, orgNo);
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        bindOrgAndLoadWeb();
                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(mActivity.getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }

    /**
     * 绑定机构完成并获跳转产品页
     */
    private void bindOrgAndLoadWeb() {
//        Log.e("JumpOrgService","bindOrgAndLoadWeb");
        new MyNetAsyncTask(mActivity, true) {
            OrgProductUrlEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().getOrgProductUrl(MyApp.getInstance().getLoginService().token, mOrgNo, mProductId);
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        OrgProductUrlEntityData data = response.getData();
                        String orgProductUrl = data.getOrgProductUrl();
                        OrgProductWebActivity.showThisActivityForPost(mActivity, orgProductUrl, EncodingUtils.getBytes(getProductPostDate(data), "base64"), mOrgName);
                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(mActivity.getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }


    /**
     * 开通账户
     * 开通平台流程
     * 1、判断是否绑卡
     * 2、if（已绑定）{ 是否已绑定第三方平台/platfrom/isBindOrgAcct }else{ 绑卡操作 }
     * 3、if (已经绑定第三方平台){ 弹出提示框提示老用户 }else{ 是否已经存在于第三方平台/platfrom/isExistInPlatform }
     * 4、if (已经注册过第三方平台){弹出提示框提示老用户 }else{ 绑定平台操作/platfrom/bindOrgAcct }
     *
     * @param orgNo
     * @param context
     */
    public void openOrgAccount(final String orgName, final String orgNo, final Context context) {
//        检查绑卡
        new MyNetAsyncTask(context, false) {
            BundBankcardDataEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = TopApp.getInstance().getHttpService().accountGetBankCardStatus(MyApp.getInstance().getLoginService().token);
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        if (response.getData().isBundBankcard()) { //已绑卡
                            checkOrgAccount(orgName,orgNo,context);  //判断是否已绑定第三方机构
                        } else {  //未绑卡
                            // 弹窗银行卡绑卡提示
                            final PromptDialogCommon promptDialogCommon = new PromptDialogCommon((Activity) context, context.getString(R.string.dialog_title_boundCard_remind), context.getString(R.string.dialog_content_boundCard_remind), "立即绑定");
                            promptDialogCommon.setBtnPositiveClickListener(new PromptDialogCommon.PositiveClicklistener() {
                                @Override
                                public void onPositiveClick() { //点击绑定,跳转绑卡页面
                                    context.startActivity(new Intent(context, CardManagerAdd.class));
                                }
                            });
                            promptDialogCommon.show();
                        }
                    } else {
                        com.toobei.common.utils.ToastUtil.showCustomToast(context.getString(com.toobei.common.R.string.pleaseCheckNetwork));
                    }
                }
            }
        }.execute();


    }

    public void checkOrgAccount(final String orgName, final String orgNo, final Context context){
        //检查第三方账户是否存在，老用户不用绑定
        new MyNetAsyncTask(context, true) {
            ExistInPlatformEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().isExistInPlatform(MyApp.getInstance().getLoginService().token, orgNo);
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        if (response.getData().isIsExist()) { //是第三方平台老用户
                            ToastUtil.showCustomToast("您是" + orgName + "老用户,通过T呗投资不能享受红包等奖励，建议购买其他平台产品", Toast.LENGTH_LONG);
                        } else { //不是第三方老用户
                            bindOrgAccountAndRefreshUi(orgNo, context);
                        }

                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(context.getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }

    /**
     * 绑定账户并刷新Ui-我的投资开通账户
     *
     * @param orgNo
     */
    public void bindOrgAccountAndRefreshUi(final String orgNo, final Context context) {
        new MyNetAsyncTask(context, false) {
            LoginResponseEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().bindOrgAcct(MyApp.getInstance().getLoginService().token, orgNo);
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        ToastUtil.showCustomToast("开通成功");
                        if (context instanceof MyInvestOrgActivity) {
                            MyInvestOrgActivity activity = (MyInvestOrgActivity) context;
                            activity.refreshUI();
                        }
                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(context.getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }


    /**
     * 绑定机构账户并跳转合作平台的个人中心
     *
     * @param orgNo
     * @param context
     */
    public static void bindOrgAccountAndJumpUserCenter(final String orgNo, final Context context, final MyWebView webView, String orgName) {
        final String diaLogText = "即将离开T呗，为您跳转至 " + orgName;
        final JumpProgressDialog jumpProgressDialog = new JumpProgressDialog(context, diaLogText);
        jumpProgressDialog.show();
        new MyNetAsyncTask(context, false) {
            OrgUserCenterUrlEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().getOrgUserCenterUrl(MyApp.getInstance().getLoginService().token, orgNo);
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        OrgUserCenterUrlData data = response.getData();
                        String userCenterUrl = data.getOrgUsercenterUrl();
                        webView.setOnMyWebViewListener(new OnMyWebViewListener() {
                            @Override
                            public void onReceivedTitle(String title) {

                            }

                            @Override
                            public void onUrlRedirectCallBack(boolean isRedirectUsable, String url) {
                                
                            }

                            @Override
                            public void onUrlLoading(boolean isRedirectUsable, String url) {

                            }

                            @Override
                            public void onPageFinished(String url) {
                                jumpProgressDialog.dismiss();
                            }
                        });
                        webView.postUrl(userCenterUrl, EncodingUtils.getBytes(getUserCenterPostDate(data), "base64"));

                    } else {
                        jumpProgressDialog.dismiss();
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    jumpProgressDialog.dismiss();
                    ToastUtil.showCustomToast(context.getString(R.string.pleaseCheckNetwork));
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
        String orgAccount = data.getOrgAccount();
        String orgKey = data.getOrgKey();
        String orgNumber = data.getOrgNumber();
        String requestFrom = data.getRequestFrom();
        String sign = data.getSign();
        String timestamp = data.getTimestamp();
        Map<String, String> UrlMap = new HashMap<String, String>();
        UrlMap.put("orgAccount", orgAccount);
        UrlMap.put("orgKey", orgKey);
        UrlMap.put("orgNumber", orgNumber);
        UrlMap.put("requestFrom", requestFrom);
        UrlMap.put("sign", sign);
        UrlMap.put("timestamp", timestamp);
        Set<String> keySet = UrlMap.keySet();
        StringBuffer postDate = new StringBuffer("");
        for (String s : keySet) {
            postDate.append(s + "=" + UrlMap.get(s) + "&");
        }
        postDate.deleteCharAt(postDate.length() - 1);
//        Log.e("postDate",postDate.toString());
        return postDate.toString();
    }

    /**
     * 跳转机构产品页
     * 拼接post请求参数
     *
     * @param data
     */
    private String getProductPostDate(OrgProductUrlEntityData data) {
        String orgAccount = data.getOrgAccount();
        String orgKey = data.getOrgKey();
        String orgNumber = data.getOrgNumber();
        String requestFrom = data.getRequestFrom();
        String sign = data.getSign();
        String thirdProductId = data.getThirdProductId();
        String timestamp = data.getTimestamp();
        String txId = data.getTxId();
        Map<String, String> UrlMap = new HashMap<String, String>();
        UrlMap.put("orgAccount", orgAccount);
        UrlMap.put("orgKey", orgKey);
        UrlMap.put("orgNumber", orgNumber);
        UrlMap.put("requestFrom", requestFrom);
        UrlMap.put("sign", sign);
        UrlMap.put("thirdProductId", thirdProductId);
        UrlMap.put("timestamp", timestamp);
        UrlMap.put("txId", txId);
        Set<String> keySet = UrlMap.keySet();
        StringBuffer postDate = new StringBuffer("");
        for (String s : keySet) {
            postDate.append(s + "=" + UrlMap.get(s) + "&");
        }
        postDate.deleteCharAt(postDate.length() - 1);
//        Log.e("postDate",postDate.toString());
        return postDate.toString();
    }
}
