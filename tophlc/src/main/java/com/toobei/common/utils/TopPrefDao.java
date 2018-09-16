package com.toobei.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.toobei.common.R;
import com.toobei.common.TopApp;

import org.xsl781.utils.Logger;

public abstract class TopPrefDao {

    protected Context cxt;
    protected SharedPreferences pref;
    protected SharedPreferences.Editor editor;

    public TopPrefDao(Context cxt) {
        this.cxt = cxt;
        pref = cxt.getSharedPreferences("xiaoniu", Context.MODE_PRIVATE);
        editor = pref.edit();
        Logger.d("PrefDao init no specific user");
    }

    public TopPrefDao(Context cxt, String prefName) {
        this.cxt = cxt;
        pref = cxt.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public boolean getAppFirstRun() {
        return pref.getBoolean("appFirstRun", true);
    }

    public void setAppFirstRun(boolean bool) {
        editor.putBoolean("appFirstRun", bool).commit();
    }

    public boolean getCurVersionFirstRun(String version) {
        return pref.getBoolean(version, true);
    }

    public void setCurVersionFirstRun(String version, boolean bool) {
        editor.putBoolean(version, bool).commit();
    }

    public void setAppPath(String appPath) {
        editor.putString("AppPath2", appPath).commit();
    }

    public String getAppPath() {
        return pref.getString("AppPath2", null);
    }

    boolean getBooleanByResId(int resId) {
        return TopApp.getInstance().getResources().getBoolean(resId);
    }

    /**
     * 是否是最新app版本
     */
    public boolean isLastedAPP() {
        return pref.getBoolean("isLasted", getBooleanByResId(R.bool.defaultIsLatestAPP));
    }

    public void setLastedAPP(boolean isLasted) {
        editor.putBoolean("isLasted", isLasted).commit();
    }

    public void setLastedCrashErrorPath(String path) {
        editor.putString("LastedCrashErrorPath", path).commit();
    }

    public String getLastedCrashErrorPath() {
        return pref.getString("LastedCrashErrorPath", null);
    }

    public void setLastedRestartTime(long time) {
        editor.putLong("LastedRestartTime", time).commit();
    }

    public long getLastedCrashErrorTime() {
        return pref.getLong("LastedRestartTime", -1);
    }

    /**
     * 功能：默认sp中得到当前记录用户
     *
     * @param phone
     */
    public void setCurUserPhone(String phone) {
        editor.putString("CurUserPhone", phone).commit();
    }

    /**
     * 功能：默认sp中设置
     *
     * @return
     */
    public String getCurUserPhone() {
        return pref.getString("CurUserPhone", null);
    }

    public void setCurUserGestrue(String gesturePasswd) {
        editor.putString("CurUserGestrue", gesturePasswd).commit();
    }

    /**
     * 功能：在指定用户的sp下取得数据
     *
     * @return
     */
    public String getCurUserGestrue() {
        return pref.getString("CurUserGestrue", null);
    }

    public void setToken(String token) {
        editor.putString("token", token).commit();
    }

    public String getToken() {
        return pref.getString("token", null);
    }

    public String isBoundedBankCard() {
        String boundedBankCard = pref.getString("BoundedBankCard", null);
        Logger.e("BoundedBankCard==== "+boundedBankCard);
        return boundedBankCard;
    }

    public void setBoundedBankCard(String isBounded) {
        editor.putString("BoundedBankCard", isBounded).commit();
    }

    public String isHasPayPwd() {
        return pref.getString("isHasPayPwd", null);
    }

    public void setHasPayPwd(String isPayPwd) {
        editor.putString("isHasPayPwd", isPayPwd).commit();
    }

    /**
     * 功能：是否是第一次进入，显示引导页
     *
     * @param viewName
     * @return
     */
    public boolean isFirstGuide(String viewName) {
        return pref.getBoolean("FirstGuide" + viewName, true);
    }

    /**
     * 功能：设置第一次引导是否完成
     *
     * @param viewName
     * @param bool
     */
    public void setFirstGuide(String viewName, boolean bool) {
        editor.putBoolean("FirstGuide" + viewName, bool).commit();
    }

    public boolean isNotifyWhenNews() {
        return pref.getBoolean("notifyWhenNews", TopApp.getInstance().getResources().getBoolean(R.bool.defaultNotifyWhenNews));
    }

    public void setNotifyWhenNews(boolean notifyWhenNews) {
        editor.putBoolean("notifyWhenNews", notifyWhenNews).commit();
    }

    public boolean isVoiceNotify() {
        return pref.getBoolean("voiceNotify", getBooleanByResId(R.bool.defaultVoiceNotify));
    }

    public void setVoiceNotify(boolean voiceNotify) {
        editor.putBoolean("voiceNotify", voiceNotify).commit();
    }

    public boolean isVibrateNotify() {
        return pref.getBoolean("vibrateNotify", getBooleanByResId(R.bool.defaultVibrateNotify));
    }

    public void setVibrateNotify(boolean vibrateNotify) {
        editor.putBoolean("vibrateNotify", vibrateNotify).commit();
    }

    public boolean isCustomLoaded() {
        return pref.getBoolean("isCustomLoaded", false);
    }

    public void setCustomLoaded(boolean customLoaded) {
        editor.putBoolean("isCustomLoaded", customLoaded).commit();
    }

    public abstract String getServiceTelephone();

    /**
     * 功能：支付服务协议
     *
     * @return
     */
    public abstract String getZfxy();

    /**
     * 功能：获取客服环信账号
     *
     * @return
     */
    public abstract String getCallServiceEMId();

    /**
     * 功能：获取图片服务器地址
     *
     * @return
     */
    public abstract String getImgServerUrl();

    /**
     * 公告詳情跳轉地址
     *
     * @return
     */
    public abstract String getBulletinDetailDefaultUrl();
// TODO: 2016/11/15 0015 机构动态 、资讯 、课堂详情链接
    /**
     * 2016/11/15 0015 机构动态 、资讯 、课堂详情链接
     *
     * @return
     */
    public abstract String getInformationDetailUrl();

    /**
     * 微信分享logo
     */
    public abstract String getWechatShareLogo();

    /**
     *  2.0.2以后新加的url 统一使用这个字段的url 拼接key 切换不同的页面 2016/12/6 0006
     */
    public abstract String getFrameWebUrl();

    public void setCurUserId(String userId) {
        editor.putString("CurUserId", userId).commit();
    }

    public String getCurUserId() {
       return pref.getString("CurUserId","");
    }
}
