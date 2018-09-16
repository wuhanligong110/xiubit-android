package com.toobei.tb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.toobei.tb.activity.LoginActivity;
import com.toobei.tb.activity.MainActivity;
import com.toobei.tb.activity.MineMsgCenter;
import com.toobei.tb.activity.MineRedPacketsActivity;
import com.toobei.tb.activity.MyAccountAmountActivity;
import com.toobei.tb.activity.MyInvestDetailActivity;
import com.toobei.tb.activity.MyInviteHistoryListActivity;
import com.toobei.tb.activity.OrgInfoDetailActivity;
import com.toobei.tb.activity.ProductInfoWebActivity;
import com.toobei.tb.activity.WebActivityCommon;
import com.toobei.tb.activity.WithdrawList;

import org.json.JSONException;
import org.json.JSONObject;
import org.xsl781.ui.ActivityStack;
import org.xsl781.utils.Logger;

public class PushGetReceiver extends BroadcastReceiver {

    /**
     * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView == null)
     */
    public static StringBuilder payloadData = new StringBuilder();

    @Override
    public void onReceive(Context context, Intent intent0) {
        Bundle bundle = intent0.getExtras();
        Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));

        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
                // 获取透传数据
                // String appid = bundle.getString("appid");
                byte[] payload = bundle.getByteArray("payload");

                String taskid = bundle.getString("taskid");
                String messageid = bundle.getString("messageid");


                // smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
                boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
                System.out.println("第三方回执接口调用" + (result ? "成功" : "失败"));

                if (payload != null) {
                    String data = new String(payload);
                    Log.d("GetuiSdkDemo", "receiver payload : " + data);
                    try {
                        Intent intent = null;
                        JSONObject json = new JSONObject(data);
                        String key = json.getString("customUrlKey");
                        /*
                         * payment:我的投资—定期投资-已回款页面
						 * cusDetail:我的投资页面
						 */
                        if (TextUtils.isEmpty(MyApp.getInstance().getLoginService().token)) {
                            intent = new Intent(context, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            return;
                        }
                        if (ActivityStack.getInstance().getCount() == 0) {
                            //如果栈为0，则无activity,跳入主页中转
                            intent = new Intent(context, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("pushdata", data);
                            context.startActivity(intent);
                        } else {
                            //存在activity,直接跳入页面
                            /*提现记录: withdrawRecord
                            我的红包：myRedpacket
							邀请记录：invitationRecord
							产品详情：productDtl 对应传参：跳转链接地址－productDetailUrl 产品ID－productId
							平台详情：platformDtl 对应传参：平台ID－orgNo
							我的投资明细：investRecord 对应参数：status（状态:1=募集中|2=回款中3=回款完成）
							T呗奖励余额：rewardBalance 对应参数：typeValue（账户类型：1=全部明细|3=活动奖励|4=红包|5=其他）
							个人消息中心：notification*/
                            if (!TextUtils.isEmpty(key)) {

                                Logger.e("推送 :json==" + json.toString());
                                Logger.e("推送 :key==" + key.toString());

                                if (key.equals("withdrawRecord")) { //提现记录: withdrawRecord
                                    intent = new Intent(context, WithdrawList.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);

                                } else if (key.equals("myRedpacket")) {
                                    intent = new Intent(context, MineRedPacketsActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);

                                }  else if (key.equals("productDtl")) {  //	产品详情：productDtl 对应传参：跳转链接地址－productDetailUrl 产品ID－productId
                                    intent = new Intent(context, ProductInfoWebActivity.class);
                                    String productDetailUrl = json.getString("productDetailUrl");
                                    String productId = json.getString("productId");
                                    String orgName = json.getString("orgName");
                                    intent.putExtra("url", productDetailUrl);
                                    intent.putExtra("productId", productId);
                                    intent.putExtra("orgName", orgName);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);

                                } else if (key.equals("platformDtl")) { //	平台详情：platformDtl 对应传参：平台ID－orgNo
                                    intent = new Intent(context, OrgInfoDetailActivity.class);
                                    String orgNumber = json.getString("orgNo");
                                    intent.putExtra("orgNumber", orgNumber);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);

                                } else if (key.equals("invitationRecord")) {  //邀请记录：invitationRecord
                                    intent = new Intent(context, MyInviteHistoryListActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                } else if (key.equals("investRecord")) { //我的投资明细：investRecord 对应参数：status（状态:1=募集中|2=回款中3=回款完成）
                                    intent = new Intent(context, MyInvestDetailActivity.class);
                                    String status = json.getString("status");
                                    intent.putExtra("status", status);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                } else if (key.equals("rewardBalance")) { //	T呗奖励余额：rewardBalance 对应参数：typeValue（账户类型：1=全部明细|3=活动奖励|4=红包|5=其他）
                                    intent = new Intent(context, MyAccountAmountActivity.class);
                                    String typeValue = json.getString("typeValue");
                                    intent.putExtra("typeValue", typeValue);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
//                                    lcsSysActivityRelease    activityUrl
//                                    谢阳阳
//                                            阳阳
//                                    lcsSysNoticeRelease     msgUrl
                                } else if (key.equals("lcsSysActivityRelease")) {
                                    intent = new Intent(context, WebActivityCommon.class);
                                    String activityUrl = json.getString("activityUrl");
                                    intent.putExtra("url", activityUrl);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);

                                } else if (key.equals("lcsSysNoticeRelease")) {
                                    intent = new Intent(context, WebActivityCommon.class);
                                    String msgUrl = json.getString("msgUrl");
                                    intent.putExtra("url", msgUrl);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);

                                } else if (key.equals("notification")) {
                                    intent = new Intent(context, MineMsgCenter.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);

                                } else {
                                    intent = new Intent(context, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);

                                }
                            } else {
                                intent = new Intent(context, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case PushConsts.GET_CLIENTID:
                // 获取ClientID(CID)
                // 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
                String cid = bundle.getString("clientid");

                break;

            case PushConsts.THIRDPART_FEEDBACK:
                /*
                 * String appid = bundle.getString("appid"); String taskid =
                 * bundle.getString("taskid"); String actionid = bundle.getString("actionid");
                 * String result = bundle.getString("result"); long timestamp =
                 * bundle.getLong("timestamp");
                 * 
                 * Log.d("GetuiSdkDemo", "appid = " + appid); Log.d("GetuiSdkDemo", "taskid = " +
                 * taskid); Log.d("GetuiSdkDemo", "actionid = " + actionid); Log.d("GetuiSdkDemo",
                 * "result = " + result); Log.d("GetuiSdkDemo", "timestamp = " + timestamp);
                 */
                break;

            default:
                break;
        }
    }
}
