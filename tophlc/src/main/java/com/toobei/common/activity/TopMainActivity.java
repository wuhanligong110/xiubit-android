package com.toobei.common.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.easemob.chat.EMMessage;
import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.LoginResponseData;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.entity.UserInfo;
import com.toobei.common.service.MsgListener;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.PathUtils;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.dialog.PromptDialog;
import com.toobei.common.view.dialog.PromptDialog.DialogBtnOnClickListener;
import com.toobei.common.view.dialog.PromptDialogProgressBar;
import com.toobei.common.view.timeselector.Utils.TextUtil;

import org.xsl781.http.HttpCallBack;
import org.xsl781.utils.Logger;
import org.xsl781.utils.SystemTool;
import org.xsl781.utils.Utils;

import java.io.File;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public abstract class TopMainActivity extends TopBaseActivity implements MsgListener {

    private static final String TAG = "TopMainActivity";
    private boolean isForceUpdate = false;
    private PromptDialog dialog1;//提示升级
    private PromptDialog dialog2;//强制升级
    private PromptDialogProgressBar downloadDialog; //下载弹窗
    protected boolean NoGestureSet; //true 禁止弹出手势设置 false 允许弹出手势设置
    protected boolean hasGetNewActivityDialogData;   // 判断有没有已经获取过首页弹窗数据，避免重复请求

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TopApp.getInstance().isLaunched = true;
//        setTranslucentStatus(true);
        // 判断是否有手势密码
        boolean isTokenExsit = TopApp.getInstance().getLoginService().isTokenExsit();
        if (!TopApp.getInstance().getLoginService().isCurUserGesturePasswdExist() && isTokenExsit && !NoGestureSet) {
            //手势不存在，进入设置手势环节
            skipGestureSetActivity();
            //	skipActivity(this, GestureSetActivity.class);
        }
        //	initParams();


        setSoftInputMode();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Utils.hideSoftInputView(this);
//        MobclickAgent.onResume(this);
//        MsgListenerService.getInstance().registerMsgListener(getClass().getName(), this);
        //手势不存在，进入设置手势环节
        String token = TopApp.getInstance().getLoginService().token;
        if (!TopApp.getInstance().getLoginService().isCurUserGesturePasswdExist() && !TextUtil.isEmpty(token) && !NoGestureSet) {
            skipGestureSetActivity();
        }
        if (!TextUtils.isEmpty(TopApp.getInstance().getLoginService().token)) {

            initCurUserFromServer();
            refreshIMUnReadCount();

        }
        if (TopApp.getInstance().getLoginService().isUpgrade() && !isShowing(downloadDialog)){
            initUpdate();
        }
        if (isInvestor()) {
            return;
        }
        //注: 因为投呗进入MainActivity时不一定是登录状态 所以用户数据的缓存在->我的 界面实现
        if (!TopApp.getInstance().getLoginService().isServerLogin) {
            //判断token 有效性 ，失效 跳到登录界面
            checkTokenValid();
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
//        MsgListenerService.getInstance().unregisterMsgListener(getClass().getName());
    }

    @Override
    protected void onDestroy() {
        //	unregisterReceiver(myBraodcast);
        super.onDestroy();
    }

    public abstract void skipGestureSetActivity();


    private void initUpdate() {
        if (TopApp.getInstance().appVersion == null || TopApp.getInstance().appVersion.getUpgrade().equals("0")) {
            isForceUpdate = false;
            if (!hasGetNewActivityDialogData) {
                initHomeFragmentDialog();  //初始化首页弹框

            }
            return;
        } else if (TopApp.getInstance().appVersion.getUpgrade().equals("1") ) {
            Logger.e("appVersion===>dialog1");
            if (isShowing(dialog1) || isShowing(downloadDialog)) return;
            isForceUpdate = false;
            //提示升级
                if (dialog1 == null) {
                    String updateHints = TopApp.getInstance().appVersion.getUpdateHints();
                    String[] split = null;
                    if (updateHints != null) {
                        split = updateHints.replace("；", "\n").replace(";", "\n").replace(" ", "").split("&");
                    }
                    if (split != null && split.length > 1) {
                        dialog1 = new PromptDialog(ctx, split[0].toString(), split[1].toString(), "更新", "取消");
                    } else {
                        dialog1 = new PromptDialog(ctx, "检查到有更新，是否现在升级软件\n", "更新", "取消");
                    }
                }
                dialog1.setOnCancelListener(new OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        TopApp.getInstance().getLoginService().setUpgrade(false);
                        if (!hasGetNewActivityDialogData) {
                            initHomeFragmentDialog();  //初始化首页弹框

                        }
                    }
                });
                dialog1.setCanceledOnTouchOutside(false);
                dialog1.setListener(new DialogBtnOnClickListener() {

                    @Override
                    public void onClicked(PromptDialog dialog, boolean isCancel) {
                        if (!isCancel) {
                            //确定
                            try {
                                TopApp.getInstance().getHttp().download(TopApp.getInstance().appVersion.getDownloadUrl(), new File(PathUtils.getChatFileDir() + "/" + TopApp.getInstance().appVersion.getVersion() + ".apk"), new ApkDownloadCallback());
                            } catch (Exception e) {
                                PathUtils.resetAppPath();
                                ToastUtil.showCustomToast("下载失败，请重试");
                            }
                        } else {
                            //取消
                            TopApp.getInstance().getLoginService().setUpgrade(false);
                            if (!hasGetNewActivityDialogData) {
                                initHomeFragmentDialog();  //初始化首页弹框

                            }
                        }
                    }
                });
            Logger.e("appVersion===>dialog1");
                dialog1.show();
        } else if (TopApp.getInstance().appVersion.getUpgrade().equals("2")) {
            Logger.e("appVersion===>dialog2");
            if (isShowing(dialog2) || isShowing(downloadDialog)) return;
            isForceUpdate = true;

            //强制升级
            if (dialog2 == null) {
                String updateHints = TopApp.getInstance().appVersion.getUpdateHints();
                String[] split = null;
                if (updateHints != null) {
                    split = updateHints.replace("；", "\n").replace(";", "\n").replace(" ", "").split("&");
                }
                if (split != null && split.length > 1) {
                    dialog2 = new PromptDialog(ctx, split[0].toString(), split[1].toString(), "更新", null);
                } else {
                    dialog2 = new PromptDialog(ctx, "检查到有重要更新，请升级软件\n", "確定", null);
                }

            }
            dialog2.setOnCancelListener(new OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    TopApp.getInstance().exitApp();
                }
            });
            dialog2.setCanceledOnTouchOutside(false);
            dialog2.setListener(new DialogBtnOnClickListener() {

                @Override
                public void onClicked(PromptDialog dialog, boolean isCancel) {
                    if (!isCancel) {
                        try {
                            TopApp.getInstance().getHttp().download(TopApp.getInstance().appVersion.getDownloadUrl(), new File(PathUtils.getChatFileDir() + "/" + TopApp.getInstance().appVersion.getVersion() + ".apk"), new ApkDownloadCallback());
                        } catch (Exception e) {
                            PathUtils.resetAppPath();
                            ToastUtil.showCustomToast("下载失败，请重试");
                        }
                    }
                }
            });
            Logger.e("appVersion===>dialog2-show");
            dialog2.show();
        }
    }

    private boolean isShowing(Dialog dialog) {
        if (dialog != null && dialog.isShowing()){
            return true;
        }
        return false;
    }

    /**
     * 判断是否是投资端
     *
     * @return
     */

    protected abstract boolean isInvestor();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 按下键盘上返回按钮
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private static boolean isExit = false;

    /**
     * 双击退出函数
     */
    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            System.out.println("=====exitBy2Click");
            isExit = true; // 准备退出
            com.toobei.common.utils.ToastUtil.showCustomToast("再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            TopApp.getInstance().exitApp();
        }
    }


    protected abstract void refreshIMUnReadCount();

    @Override
    public String getListenerId() {
        return getClass().getName();
    }

    @Override
    public void onMsgReceiver(EMMessage message, List<EMMessage> offLineMsgs) {

    }

    @Override
    public void onMsgSendFailure(EMMessage msg) {
    }

    @Override
    public void onMsgSendSuccess(EMMessage msg) {
    }

    private void checkTokenValid() {
        if (TextUtil.isEmpty(TopApp.getInstance().getLoginService().token)) return;
        new MyNetAsyncTask(ctx, false) {
            LoginResponseEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = TopApp.getInstance().getHttpService().userGesturePwdLogin(TopApp.getInstance().getLoginService().token);

            }

            @Override
            protected void onPost(Exception e) {
                //登录成功
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        LoginResponseData data = response.getData();

                        // 保存token,设置缓存登录信息，更新用户数据
                        TopApp.getInstance().getLoginService().isServerLogin = true;
                        //	TopApp.getInstance().getLoginService().token = data.getToken();
                        //	TopApp.getInstance().getCurUserSp().setToken(data.getToken());
                    }
                } else {
                    com.toobei.common.utils.ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }

    public void initCurUserFromServer() {
        if (TopApp.getInstance().getLoginService().getCurUser() == null || TopApp.getInstance().getLoginService().getCurUser().getEasemobAcct() == null) {
            new MyNetAsyncTask(this, true) {
                @Override
                protected void doInBack() throws Exception {
                    UserInfo userInfo = TopApp.getInstance().getUserService().getCurUserByTokenFromMyServer();
                    if (userInfo != null) {
                        TopApp.getInstance().getLoginService().setCurUser(userInfo);
                        TopApp.getInstance().getUserService().saveAndCache(userInfo);
                        Logger.d("initCurUserFromServer userInfo ====", userInfo.toString());

                    }
                }

                @Override
                protected void onPost(Exception e) {
                    if (e != null && TopApp.getInstance().getLoginService().getCurUser() != null) {
                    }
                    TopApp.getInstance().getLoginService().initLoginEM();
                }
            }.execute();
        } else {
            TopApp.getInstance().getLoginService().initLoginEM();
        }
    }

    /**
     * 初始化活动弹出框
     */
    protected abstract void initHomeFragmentDialog();

    private class ApkDownloadCallback extends HttpCallBack {
        


        @Override
        public void onPreStart() {
            super.onPreStart();
            downloadDialog = new PromptDialogProgressBar(ctx);
            downloadDialog.setCanceledOnTouchOutside(false);
            downloadDialog.setForceUpdate(isForceUpdate);
            if (isForceUpdate) {
                downloadDialog.setOnCancelListener(new OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        TopApp.getInstance().exitApp();
                    }
                });
            }
            if (ctx != null) {
                Logger.e("appVersion-downloadDialog==》show"+"isForceUpdate==>"+isForceUpdate);
                downloadDialog.show();
            }
        }

        @Override
        public void onLoading(long count, long current) {
            super.onLoading(count, current);
            //	System.out.println(" onLoading count = " + count + "current = "+ current);
            if (ctx != null) {
                downloadDialog.setProgress((int) (current * 1000 / count));
            }
        }

        @Override
        public void onSuccess(File file) {
            super.onSuccess(file);
            //System.out.println(" onSuccess  ");
            if (ctx != null) {
                downloadDialog.setProgress(1000);
                downloadDialog.dismiss();
                SystemTool.installApk(ctx, file);
            }
        }

        @Override
        public void onFailure(Throwable t, int errorNo, String strMsg) {
            super.onFailure(t, errorNo, strMsg);
            if (ctx != null) {
                downloadDialog.dismiss();
            }
            t.printStackTrace();
            System.out.println(" onFailure + errorNo" + errorNo + ",strMsg = " + strMsg);
        }
    }


}
