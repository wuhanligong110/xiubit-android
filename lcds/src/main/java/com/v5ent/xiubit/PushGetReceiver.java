package com.v5ent.xiubit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.toobei.common.utils.TimeUtils;
import com.v5ent.xiubit.activity.CfgMemberDetialActivity;
import com.v5ent.xiubit.activity.ChatActivity;
import com.v5ent.xiubit.activity.CustomerMemberDetialActivity;
import com.v5ent.xiubit.activity.MainActivity;
import com.v5ent.xiubit.activity.MineMsgCenter;
import com.v5ent.xiubit.activity.MyIncomeDetailActivity;
import com.v5ent.xiubit.activity.WebActivityCommon;
import com.v5ent.xiubit.activity.WithdrawList;

import org.json.JSONException;
import org.json.JSONObject;
import org.xsl781.ui.ActivityStack;
import org.xsl781.utils.StringUtils;

public class PushGetReceiver extends BroadcastReceiver {

    /**
     * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView ==
     * null)
     */
    public static StringBuilder payloadData = new StringBuilder();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
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
             //   System.out.println("第三方回执接口调用" + (result ? "成功" : "失败"));

                if (payload != null) {
                    String data = new String(payload);
                    Log.d("GetuiSdkDemo", "receiver payload : " + data);

                    try {
                        if (!TextUtils.isEmpty(data)) {
                            JSONObject json = new JSONObject(data);
                            String key = json.getString("customUrlKey");
                        /*
                         * cusDetail:  客户详情页
						 * teamDetail:团队成员详情页 
						 * cusDetail2:客户详情页（切换到到期日程）
						 * myAccount:  我的账户页面
						 * withdraw:  提现记录页面
						 * notification:  绑定理财师（理财师）
						 *  lsjappcs :  理财师升级成功
						 * contactCtmService  :客服消息
						 * declarationFormList  :报单
						 * lcsSysNoticeRelease :公告详情 key：msgUrl
						 * lcsSysActivityRelease :活动详情 key：activityUrl
						 */

                         /*   if (TextUtils.isEmpty(MyApp.getInstance().getLoginService().token)) {
                                Intent Lintent = new Intent(context, LoginActivity.class);
                                Lintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(Lintent);
                                return;
                            }*/


                            Intent intent0;
                            //如果栈为0，则无activity,跳入主页中转
                            if (ActivityStack.getInstance().getCount() == 0) {
                                intent0 = new Intent(context, MainActivity.class);
                                intent0.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent0.putExtra("pushKey", "push");
                                intent0.putExtra("data", data);
                                context.startActivity(intent0);

                                //存在activity,直接跳入页面
                            } else {

                                if (!TextUtils.isEmpty(key)) {
                                    if (key.startsWith("cusDetai")) {  //客户详情页
                                        if (!MyApp.getInstance().getLoginService().isTokenExsit()) {
                                            MyApp.loginAndStay = true;
                                        }
                                        String customerId = json.getString("customerId");
                                        intent0 = new Intent(context, CustomerMemberDetialActivity.class);
                                        intent0.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent0.putExtra("userId", customerId);
                                        context.startActivity(intent0);

                                    } else if (key.equals("teamDetail")) { //团队成员详情页
                                        if (!MyApp.getInstance().getLoginService().isTokenExsit()) {
                                            MyApp.loginAndStay = true;
                                        }
                                        String userNumber = json.getString("userNumber");
                                        intent0 = new Intent(context, CfgMemberDetialActivity.class);
                                        intent0.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent0.putExtra("userId", userNumber);
                                        context.startActivity(intent0);

                                    }else if (key.equals("myAccount")) { //我的账户页面  (V2.0.2 后跳转本月收益界面)
                                        //  2016/12/16 0016 我的账户消息推送
                                        if (!MyApp.getInstance().getLoginService().isTokenExsit()) {
                                            MyApp.loginAndStay = true;
                                        }
                                        String month = json.getString("month");
                                        String profitType = json.getString("profitType");
                                        intent0 = new Intent(context, MyIncomeDetailActivity.class);
                                        intent0.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                        intent0.putExtra("date",month);
                                        intent0.putExtra("monthDesc", StringUtils.toInt(TimeUtils.getMonth(month))+"月收益");
                                        intent0.putExtra("intentProfixType", profitType);

                                        context.startActivity(intent0);

                                    } else if (key.equals("withdraw")) { //提现记录页面
                                        if (!MyApp.getInstance().getLoginService().isTokenExsit()) {
                                            MyApp.loginAndStay = true;
                                        }
                                        intent0 = new Intent(context, WithdrawList.class);
                                        intent0.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(intent0);

                                    } else if (key.equals("lsjappcs")) { //理财师升级成功  职级
                                        intent0 = new Intent(context, WebActivityCommon.class);
                                        intent0.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent0.putExtra("url", MyApp.getInstance().getDefaultSp().getURLMyLevelIntroduce());
                                        intent0.putExtra("title", context.getResources().getString(R.string.my_rank));
                                        context.startActivity(intent0);

                                    } else if (key.equals("contactCtmService")) { //客服消息
                                        if (!MyApp.getInstance().getLoginService().isTokenExsit()) {
                                            MyApp.loginAndStay = true;
                                        }
                                        intent0 = new Intent(context, ChatActivity.class);
                                        intent0.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent0.putExtra("toChatUsername", MyApp.getInstance().getUserService().getCallServiceEMId());
                                        context.startActivity(intent0);

                                    }else if (key.equals("notification")) { // 消息中心 —>个人消息中心页面
                                        if (!MyApp.getInstance().getLoginService().isTokenExsit()) {
                                            MyApp.loginAndStay = true;
                                        }
                                        intent0 = new Intent(context, MineMsgCenter.class);
                                        intent0.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(intent0);

                                    } else if (key.equals("lcsSysNoticeRelease")) { //消息中心->公告详情 跳轉web
                                        String msgUrl = json.getString("msgUrl");
                                        intent0 = new Intent(context, WebActivityCommon.class);
                                        intent0.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent0.putExtra("url",msgUrl);
                                        intent0.putExtra("title","");
                                        context.startActivity(intent0);

                                    } else if (key.equals("lcsSysActivityRelease")) { //活动详情 跳轉web
                                        String activityUrl = json.getString("activityUrl");
                                        intent0 = new Intent(context, WebActivityCommon.class);
                                        intent0.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent0.putExtra("url",activityUrl);
                                        intent0.putExtra("title","");
                                        context.startActivity(intent0);


                                    } else {
                                        intent0 = new Intent(context, MainActivity.class);
                                        intent0.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent0.putExtra("pushKey", "push");
                                        intent0.putExtra("data", data);
                                        context.startActivity(intent0);
                                    }
                                } else {  //存在activity,但是没有key跳入MainActivity
                                    intent0 = new Intent(context, MainActivity.class);
                                    intent0.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent0.putExtra("pushKey", "push");
                                    intent0.putExtra("data", data);
                                    context.startActivity(intent0);
                                }


                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // payloadData.append(data);
                    // payloadData.append("\n");

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
			 * bundle.getString("taskid"); String actionid =
			 * bundle.getString("actionid"); String result =
			 * bundle.getString("result"); long timestamp =
			 * bundle.getLong("timestamp");
			 * 
			 * Log.d("GetuiSdkDemo", "appid = " + appid); Log.d("GetuiSdkDemo",
			 * "taskid = " + taskid); Log.d("GetuiSdkDemo", "actionid = " +
			 * actionid); Log.d("GetuiSdkDemo", "result = " + result);
			 * Log.d("GetuiSdkDemo", "timestamp = " + timestamp);
			 */
                break;
            case PushConsts.GET_SDKONLINESTATE:
            /*
             * byte[] payload = bundle.getByteArray("payload");
			 * 
			 * String taskid = bundle.getString("taskid"); String messageid =
			 * bundle.getString("messageid");
			 * 
			 * // smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行 boolean
			 * result = PushManager.getInstance().sendFeedbackMessage(context,
			 * taskid, messageid, 90001); System.out.println("第三方回执接口调用" +
			 * (result ? "成功" : "失败"));
			 * 
			 * if (payload != null) { String data = new String(payload);
			 * 
			 * Log.d("GetuiSdkDemo", "receiver payload : " + data);
			 * 
			 * payloadData.append(data); payloadData.append("\n");
			 * 
			 * 
			 * }
			 */
                break;

            default:
                break;
        }
    }
}
