package com.v5ent.xiubit.activity;

import android.content.Intent;
import android.text.TextUtils;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopMineInfoActivity;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.entity.MsgResponseData;
import com.toobei.common.entity.MsgResponseEntity;
import com.toobei.common.entity.UserInfo;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.util.PrefDao;

/**
 * 公司: tophlc
 * 类说明：Activity-个人设置
 *
 * @date 2016-5-23
 */
public class MineInfoActivity extends TopMineInfoActivity {

    @Override
    protected Intent getImageSelectActivityIntent() {
        return new Intent(this, ImageSelectActivity.class);
    }

    @Override
    protected Intent getCardManagerAddSuccessIntent() {
        return new Intent(this, CardManagerAddSuccess.class);
    }

    @Override
    protected void showCardManagerAdd() {
        showActivity(this, CardManagerAdd.class);
    }

    @Override
    protected void showPwdManager() { //密码管理
        showActivity(this, PwdManager.class);
    }

    @Override
    protected void showMsgManagerActivity() {  // 点击消息免打扰开关
        PrefDao curUserSp = MyApp.getInstance().getCurUserSp();
        if (curUserSp == null) {
            return;
        }
        String platformflag = curUserSp.getPlatformflag();

        if (!TextUtils.isEmpty(platformflag) && !TextUtils.isEmpty(platformflag)) {
            if (platformflag.equals("0")) {
                setMsgPush(MyApp.getInstance().getLoginService().token, "1");
            } else {
                setMsgPush(MyApp.getInstance().getLoginService().token, "0");
            }
        }
    }

    @Override
    protected Intent getGestureActivityIntent(TopBaseActivity activity) {
        return new Intent(this, GestureActivity.class);
    }

    @Override
    protected void refreshFaceView() {
        PrefDao curUserSp = MyApp.getInstance().getCurUserSp();
        if (curUserSp == null) {
            return;
        }
        String platformflag = curUserSp.getPlatformflag();
        String bindCardflag = curUserSp.getBindCardflag();
        textBindCard.setText("0".equals(bindCardflag) ? "未绑定" : "已绑定");
        UserInfo curUser = MyApp.getInstance().getLoginService().getCurUser();
        if (curUser != null) {
            MyApp.getInstance().getUserService().displayUserFace(curUser.getHeadImage(), avatarView, false);
        }
        if (TextUtils.isEmpty(platformflag) || TextUtils.isEmpty(platformflag)) {
            queryMsgPushSet(MyApp.getInstance().getLoginService().token);
        } else {
            updateUI(platformflag);
        }
    }

    @Override
    public void EventCount() {

    }


    /*
     * 查询消息设置
     */
    private void queryMsgPushSet(final String token) {
        new MyNetAsyncTask(ctx, true) {
            MsgResponseEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().msgQueryMsgPushSet(token);

            }

            @Override
            protected void onPost(Exception e) {
                // 登录成功
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {

                        MsgResponseData data = response.getData();
                        if (data == null) {
                            return;
                        }
                        String platformflag = data.getPlatformflag();
                        MyApp.getInstance().getCurUserSp().setPlatformflag(platformflag);
                        updateUI(platformflag);
                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }

    /*
     * 更新UI
     */
    private void updateUI(String platformflag) {
        if (platformflag.equals("0")) {
            infoIv.setBackgroundResource(R.drawable.btn_notification_unselected);
        } else {
            infoIv.setBackgroundResource(R.drawable.btn_notification_selected);
        }
    }

    /*
     * 设置消息
     * 0关闭
     * 1开启
     */
    private void setMsgPush(final String token, final String platformflag) {
        new MyNetAsyncTask(ctx, false) {
            LoginResponseEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().msgSetMsgPush(token, platformflag);

            }

            @Override
            protected void onPost(Exception e) {
                // 登录成功
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {

                        if (platformflag.equals("0")) {
                            infoIv.setBackgroundResource(R.drawable.btn_notification_unselected);
                            MyApp.getInstance().getCurUserSp().setPlatformflag("0"); // 不设置消息免打扰

                        } else {
                            infoIv.setBackgroundResource(R.drawable.btn_notification_selected);
                            MyApp.getInstance().getCurUserSp().setPlatformflag("1");//设置消息免打扰

                        }


                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }


}
