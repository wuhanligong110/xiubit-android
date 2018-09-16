package com.toobei.common.service;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.entity.UserInfo;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.StringUtil;
import com.toobei.common.utils.UpdateViewCallBack;
import com.toobei.common.view.timeselector.Utils.TextUtil;

import org.xsl781.utils.Log;
import org.xsl781.utils.Logger;
import org.xsl781.utils.MD5Util;

public abstract class TopLoginService {
    //	public User curUser;
    public String curPhone;
    public String token = "";
    public boolean isServerLogin = false;//服务器是否登录
    protected UserInfo curUser;
    private boolean isUpgrade = true;
    public boolean isEMServerLogin = false;//环信服务器是否登录

    public boolean isUpgrade() {
        return isUpgrade;
    }

    public void setUpgrade(boolean isUpgrade) {
        this.isUpgrade = isUpgrade;
    }

    /**
     * 功能：是否有缓存信息
     *
     * @return true 表示已登录，除首次启动。
     */
    public boolean isCachPhoneExist() {
        //判断是否有phone和token
        return curPhone != null && token != null;
    }

    /**
     * 功能：清除登录缓存信息
     */
    public void clearLoginCach() {
        TopApp.getInstance().getCurUserSp().setToken(null);
        curPhone = null;
        isServerLogin = false;
        isEMServerLogin = false;
        token = null;
        curUser = null;
        isUpgrade = true;
    }

    /**
     * 功能：缓存登录状态信息
     *
     * @param token
     * @param phone
     */
    public void cachLoginInfo(String token, String phone) {
        curPhone = phone;
        isServerLogin = true;
        this.token = token;
        TopApp.getInstance().getCurUserSp().setToken(token);
        TopApp.getInstance().getDefaultSp().setCurUserPhone(phone);
    }

    public boolean isGesturePasswdValid(String password) {
        if (com.way.util.StringUtil.isNotEmpty(password)) {
            // 或者是超级密码
            String curUserGestrue = TopApp.getInstance().getCurUserSp().getCurUserGestrue();
            if (MD5Util.MD5(password).equals(curUserGestrue)
                    || password.equals("0,2,8,6,3,1,5,7,4")) {
                return true;
            }
        }
        return false;
    }

    public void setGesturePasswd(String password) {
        TopApp.getInstance().getCurUserSp().setCurUserGestrue(password);
    }

    public boolean isCurUserGesturePasswdExist() {
        ///	String phone = curUser.getPhone();
        String curUserGestrue = TopApp.getInstance().getCurUserSp().getCurUserGestrue();
        return curUserGestrue == null ? false : true;
    }

    private String getUserIdByToken(String token) {
        if (token != null) {
            return token.substring(0, token.indexOf("."));
        }
        return "";
    }

    public String getCurUserId() {
        return getUserIdByToken(token);
    }

    public boolean isEMLogined() {
        return EMChat.getInstance().isLoggedIn();
    }

    public boolean isTokenExsit() {
        return !TextUtil.isEmpty(token);
    }

    private void loginEM(final String emId, final String emPwd) {
        if (StringUtil.isEmpty(emId) || StringUtil.isEmpty(emPwd)) {
            Log.getLog(getClass()).d("环信账号或密码为空！");
            return;
        }
        EMChatManager.getInstance().login(emId, emPwd, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                Log.getLog(getClass()).d(
                        "======登录聊天服务器成功！emId = " + emId + ",emPwd" + emPwd);
                EMChatManager.getInstance().loadAllConversations();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.getLog(getClass()).d("登录聊天服务器失败！");
            }
        });
    }

    public void initLoginEM() {
        if (curUser == null) {
            TopApp.getInstance().getUserService().getCurUser(new UpdateViewCallBack<UserInfo>() {

                @Override
                public void updateView(Exception e, UserInfo object) {
                    if (object != null && object.getEasemobAcct().length() > 0) {
                        Log.getLog(getClass()).d(
                                "====== 本地object emId = " + object.getEasemobAcct() + ",emPwd = "
                                        + object.getEasemobPassword());
                        curUser = object;
                        initLoginEM();
                    }
                }
            });
        } else if (!isEMLogined() || !isEMServerLogin) {
            loginEM(curUser.getEasemobAcct(), curUser.getEasemobPassword());
            Log.getLog(getClass()).d(
                    "====== 正在登录 emId = " + curUser.getEasemobAcct() + ",emPwd = "
                            + curUser.getEasemobPassword());
        } else {
            Log.getLog(getClass()).d(
                    "====== 本地已经登录 emId = " + curUser.getEasemobAcct() + ",emPwd = "
                            + curUser.getEasemobPassword());
        }
    }

    /**
     * 功能：成功回调ture，失败回调false
     *
     * @param activity
     * @param callBack
     */
    public void logoutFromServer(final TopBaseActivity activity,
                                 final UpdateViewCallBack<Boolean> callBack) {
        if (token == null) {
            return;
        }
        new MyNetAsyncTask(activity) {
            LoginResponseEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = TopApp.getInstance().getHttpService().userLogout(token);
            }

            @Override
            protected void onPost(Exception e) {
                // 登录成功
                if (e == null && response != null) {
                    if (response.getCodeNoCheck().equals("0")) {
                        if (callBack != null)
                            callBack.updateView(null, true);
                        return;
                    } else {
                        com.toobei.common.utils.ToastUtil.showCustomToast(response
                                .getErrorsMsgStr());
                    }
                } else {
                    com.toobei.common.utils.ToastUtil.showCustomToast(activity
                            .getString(R.string.pleaseCheckNetwork));
                }
                if (callBack != null)
                    callBack.updateView(null, false);
            }
        }.execute();
    }

    public UserInfo getCurUser() {
        if (curUser != null) {
            Logger.d("UserInfo==getCurUser == >" + curUser.getUserName());
        }
        return curUser;
    }

    public void setCurUser(UserInfo curUser) {
        Logger.d("UserInfo==setCurUser == >" + curUser.getUserName());
        if (curUser != null) {
            this.curUser = curUser;
        }
    }

    public UserInfo getMyCfpUserInfo() {
        return null;
    }
}
