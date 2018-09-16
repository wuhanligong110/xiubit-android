package com.toobei.common.service;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.toobei.common.TopApp;
import com.toobei.common.entity.AccountHomeEntity;
import com.toobei.common.entity.AccountTypeDatasDataEntity;
import com.toobei.common.entity.AddressCityDatasDataEntity;
import com.toobei.common.entity.AddressProvinceDatasDataEntity;
import com.toobei.common.entity.AdvertisementOpeningDataEntity;
import com.toobei.common.entity.AppVersionEntity;
import com.toobei.common.entity.BankCardInfoEntity;
import com.toobei.common.entity.BankInfoDatasDataEntity;
import com.toobei.common.entity.BundBankcardDataEntity;
import com.toobei.common.entity.CheckMobileRegisterEntity;
import com.toobei.common.entity.CheckResponseEntity;
import com.toobei.common.entity.HighQualityPlatformEntity;
import com.toobei.common.entity.HomePagerBannersDatasDataEntity;
import com.toobei.common.entity.ImageResponseEntity;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.entity.MsgListDatasDataEntity;
import com.toobei.common.entity.MsgResponseEntity;
import com.toobei.common.entity.MsgUnreadCountEntity;
import com.toobei.common.entity.ProductClassifyPreferenceEntity;
import com.toobei.common.entity.ProductClassifyStatisticsEntity;
import com.toobei.common.entity.ProductDatasDataEntity;
import com.toobei.common.entity.ResetPayPwdTokenEntity;
import com.toobei.common.entity.ShareContentEntity;
import com.toobei.common.entity.UserInfoDatasDataEntity;
import com.toobei.common.entity.WithdrawRecordDatasDataEntity;
import com.toobei.common.entity.WithdrawSummaryEntity;
import com.toobei.common.utils.HttpsUtils;
import com.toobei.common.utils.SignUtils;
import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.v5ent.xiubit.entity.UnReadMsgCountEntity;

import org.xsl781.utils.Logger;
import org.xsl781.utils.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * 公司:Tophlc
 * 类说明：网络服务 基类
 *
 * @date 2015-12-24
 */
public abstract class TopHttpService {

    /**
     * 下列参数，请先初始化
     */
    protected String orgNumber = "";
    protected String appKind = "";
    protected String appClient = "";
    protected String appVersion = "";
    protected String v = "";
    protected String timestamp = "";
    protected String imgServerBaseUrl = null;
    protected String app_secret = "1786221FA2D4415C96B0906C32F4F4B2";

    protected Gson gson;
    protected static TopHttpService service = null;

    public String getAppKind() {
        return appKind;
    }

    /**
     * 判断客户端是貅比特还是T呗
     *
     * @return true ->lcds  ;false ->T呗
     */
    public boolean isLCDS() {
        return !getAppKind().equals("investor");
    }

    protected TopHttpService() {
        service = this;
        gson = new GsonBuilder().setPrettyPrinting().create();
        initParam();
    }

    /*
        public static synchronized TopHttpService getInstance() {
            return service;
        }
    */
    public abstract String getBaseUrl(boolean isHttps);


    /**
     * 通过appkind 自动获取t呗或者猎财的h5的BaseUrl
     *
     * @return t呗或者猎财的h5的BaseUrl
     */

    public String getBaseH5urlByAppkind() {
        String domainUrl = TopApp.getInstance().getHttpService().getLcdsServerDomainUrl();
        if ("investor".equals(TopApp.getInstance().getHttpService().getAppKind())) { //投资端 t呗
            domainUrl = TopApp.getInstance().getHttpService().getTbServerDomainUrl();
        }
        return domainUrl;
    }

    /**
     * 获取Tb服务器域名url
     * https://market.toobei.com
     */
    public abstract String getTbServerDomainUrl();

    /**
     * 获取貅比特服务器域名url
     * http://liecai.tophlc.com
     */
    public abstract String getLcdsServerDomainUrl();

    /**
     *分享链接后缀，用来判别 是猎财还是tb 以及版本
     */
    public abstract String getShareUrlEndSuffix();
    /**
     * 功能：初化请求公用参数app_key  v app_secret app_version
     */
    public abstract void initParam();

    protected void initSign(Map<String, String> params) {
        String token = TopApp.getInstance().getLoginService().token;
        if (!TextUtils.isEmpty(token)) {
            params.put("token", token);
        }
        params.put("appClient", appClient);
        params.put("appKind", appKind);
        params.put("appVersion", appVersion);
        params.put("orgNumber", orgNumber);
        params.put("timestamp", timestamp);
        params.put("v", v);//服务器API版本号
        String sign = SignUtils.sign(params, app_secret);
        params.put("sign", sign);

    }

    protected void printRequest(String interfaceName, Map<String, String> map) {
        if (TopApp.getInstance().IS_DEBUG) {
            Logger.d(interfaceName, "====request " + StringUtils.formatJson(JSON.toJSONString(map)));
        }
    }

    protected void printResponse(String interfaceName, String response) {
        if (TopApp.getInstance().IS_DEBUG) {
            Logger.d(interfaceName, "====response " + StringUtils.formatJson(response));
        }
    }

    public String getImageServerBaseUrl() {
        if (imgServerBaseUrl == null) {
            imgServerBaseUrl = TopApp.getInstance().getDefaultSp().getImgServerUrl();
        }
        return imgServerBaseUrl;
    }

    public String getImageUrlFormMd5(String imageUrl) {
        if (TextUtil.isEmpty(imageUrl)){
            return imageUrl;
        }
        if (imageUrl.contains("http")) {
            return imageUrl;
        } else {
            String url = TopApp.getInstance().getDefaultSp().getImgServerUrl() + imageUrl;
            if(!url.contains("?f=png")){
                url = url + "?f=png";
            }
            Logger.d("imageUrl====>"+url);
            return url;
        }
    }

    /**
     * 功能：理财产品-产品分享
     *
     * @param token
     * @param productId
     * @return
     * @throws Exception
     */
    public ShareContentEntity productShare(String token, String productId) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        map.put("token", token);
        map.put("productId", productId);
        initSign(map);
        printRequest("productShare", map);
        String response = HttpsUtils.sendPostRequest(map, "/product/share");
        printResponse("productShare", response);
        if (response == null) return null;
        return gson.fromJson(response, ShareContentEntity.class);
    }

    /**
     * 功能：查询客户端版本
     *
     * @return
     * @throws Exception
     */
    public AppVersionEntity clientAppversion() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map, "/app/appVersion");
        printResponse(" clientAppversion", response);
        if (response == null) return null;
        return gson.fromJson(response, AppVersionEntity.class);
    }

    /**
     * 根据环信id 查询用户信息
     *
     * @param token
     * @param easemobAcct
     * @return
     * @throws Exception
     */
    public UserInfoDatasDataEntity userInfoByEmId(String token, String easemobAcct) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("easemobAcct", easemobAcct);
        initSign(map);
        printRequest("userInfoByEmId", map);
        String response = HttpsUtils.sendPostRequest(true, map, "/user/getUserInfoByEasemob");
        printResponse("userInfoByEmId", response);
        if (response == null) return null;
        //		return FastJsonTools.getEntity(response, LoginResponseEntity.class);
        return gson.fromJson(response, UserInfoDatasDataEntity.class);
    }

    public CheckResponseEntity userVerifyLoginPwd(String token, String pwd) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("pwd", pwd);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map, "/user/verifyLoginPwd");
        printResponse(" verifyLoginPwd", response);
        if (response == null) return null;
        return gson.fromJson(response, CheckResponseEntity.class);
    }

    /**
     * 功能：初始化支付密码
     *
     * @param token
     * @param pwd
     * @return
     * @throws Exception
     */
    public LoginResponseEntity userInitPayPwd(String token, String pwd) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("pwd", pwd);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map, "/account/initPayPwd");
        printResponse(" userInitPayPwd", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 功能：重置支付密码-校验验证码
     *
     * @param token
     * @param vcode
     * @return
     * @throws Exception
     */
    public ResetPayPwdTokenEntity userResetPayPwdVerifyVcode(String token, String vcode) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("vcode", vcode);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map, "/account/inputVcode");
        printResponse(" resetPayPwdVerifyVcode", response);
        if (response == null) return null;
        return gson.fromJson(response, ResetPayPwdTokenEntity.class);
    }

    public LoginResponseEntity userResetLoginPwd(String mobile, String vcode, String newPwd) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("vcode", vcode);
        map.put("mobile", mobile);
        map.put("newPwd", newPwd);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map, "/user/resetLoginPwd");
        printResponse("resetLoginPwd", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    public LoginResponseEntity userModifyLoginPwd(String token, String oldPwd, String newPwd) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("oldPwd", oldPwd);
        map.put("newPwd", newPwd);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map, "/user/modifyLoginPwd");
        printResponse("modifyLoginPwd", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 功能：验证支付密码
     *
     * @param token
     * @param pwd
     * @return
     * @throws Exception
     */
    public CheckResponseEntity userVerifyPayPwd(String token, String pwd) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("pwd", pwd);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map, "/account/verifyPayPwd");
        printResponse(" verifyPayPwd", response);
        if (response == null) return null;
        return gson.fromJson(response, CheckResponseEntity.class);
    }

    /**
     * 功能：修改支付密码
     *
     * @param token
     * @param oldPwd
     * @param newPwd
     * @return
     * @throws Exception
     */
    public LoginResponseEntity userModifyPayPwd(String token, String oldPwd, String newPwd) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("oldPwd", oldPwd);
        map.put("newPwd", newPwd);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map, "/account/modifyPayPwd");
        printResponse(" userModifyPayPwd", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 功能：重置支付密码-身份验证
     *
     * @param token
     * @param name
     * @param idCardNo
     * @return
     * @throws Exception
     */
    public CheckResponseEntity userVerifyIdCard(String token, String name, String idCardNo) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("name", name);
        map.put("idCardNo", idCardNo);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map, "/account/verifyIdCard");
        printResponse(" verifyIdCard", response);
        if (response == null) return null;
        return gson.fromJson(response, CheckResponseEntity.class);
    }

    /**
     * 功能：重置支付密码
     *
     * @param token
     * @param resetPayPwdToken
     * @param pwd
     * @return
     * @throws Exception
     */
    public LoginResponseEntity userResetPayPwd(String token, String resetPayPwdToken, String pwd) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("resetPayPwdToken", resetPayPwdToken);
        map.put("pwd", pwd);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map, "/account/resetPayPwd");
        printResponse(" userResetPayPwd", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 功能：检查用户是否设置支付密码
     *
     * @param token
     * @return
     * @throws Exception
     */
    public CheckResponseEntity userVerifyPayPwdState(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map, "/account/verifyPayPwdState");
        printResponse(" userVerifyPayPwdState", response);
        if (response == null) return null;
        return gson.fromJson(response, CheckResponseEntity.class);
    }

    /**
     * 功能： 发送验证码
     *
     * @param token  登录态(登录状态下使用)
     * @param mobile 手机号(非登录状态下使用)
     * @param type   验证码类别:1、注册 2、重置登录密码 3.重置支付密码
     * @return
     * @throws Exception
     */
    public LoginResponseEntity userSendVcode(String token, String mobile, String type) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        if (token != null) {
            map.put("token", token);
        }
        if (mobile != null) {
            map.put("mobile", mobile);
        }
        map.put("type", type);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map, "/user/sendVcode");
        printResponse("sendVcode", response);
        if (response == null) return null;
        // return FastJsonTools.getEntity(response, LoginResponseEntity.class);
        return gson.fromJson(response, LoginResponseEntity.class);
    }


    /**
     * 手势登录
     *
     * @param token
     * @return
     * @throws Exception
     */
    public LoginResponseEntity userGesturePwdLogin(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", TopApp.getInstance().getLoginService().token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map, "/user/gesturePwdLogin");
        printResponse("gesturePwdLogin", response);
        if (response == null) return null;
        // return FastJsonTools.getEntity(response, LoginResponseEntity.class);
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 功能：用户是否注册
     *
     * @param mobile
     * @param recommendCode
     * @return
     * @throws Exception
     */
    public CheckMobileRegisterEntity userCheckMobile(String mobile, String recommendCode) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", mobile);
        map.put("recommendCode", recommendCode);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map, "/user/checkMobile");
        printResponse("userCheckMobile", response);
        if (response == null) return null;
        // return FastJsonTools.getEntity(response, LoginResponseEntity.class);
        return gson.fromJson(response, CheckMobileRegisterEntity.class);
    }

    /*
     * 用户退出
     */
    public LoginResponseEntity userLogout(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        initSign(map);
        printRequest("userLogout", map);
        String response = HttpsUtils.sendPostRequest(true, map, "/user/logout");
        printResponse("userLogout", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 功能：查询银行卡信息
     *
     * @param token
     * @throws Exception
     */
    public BankCardInfoEntity accountGetWithdrawBankCardInfo(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map, "/account/getWithdrawBankCard");
        printResponse(" accountGetBankCardInfo", response);
        if (response == null) return null;
        return gson.fromJson(response, BankCardInfoEntity.class);
    }

    /**
     * 查询绑卡信息
     * 基本的绑卡信息
     *
     * @param token
     * @return
     * @throws Exception
     */
    public BankCardInfoEntity accountGetBankCardInfo(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map, "/account/getUserBindCard");
        printResponse(" accountGetBankCardInfo", response);
        if (response == null) return null;
        return gson.fromJson(response, BankCardInfoEntity.class);
    }

    /**
     * 获取绑卡 状态
     *
     * @param token
     * @return
     * @throws Exception
     */
    public BundBankcardDataEntity accountGetBankCardStatus(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map, "/account/personcenter/setting");
        printResponse(" accountGetBankCardInfo", response);
        if (response == null) return null;
        return gson.fromJson(response, BundBankcardDataEntity.class);
    }

    /**
     * 功能：绑定银行卡信息
     *
     * @param token    登录态
     * @param bankCard 银行卡号
//     * @param bankCode 银行code
//     * @param bankId   银行ID
//     * @param bankName 银行名称
     * @param idCard   身份证号码
     * @param mobile   银行预留手机
     * @param userName 用户名称
     * @return
     * @throws Exception
     * 4.1.1 取消银行信息字段
     */ 
    public BankCardInfoEntity accountAddBankCard(String token, String bankCard,String idCard, String mobile, String userName) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("bankCard", bankCard);
//        map.put("bankCode", bankCode);
//        map.put("bankId", bankId);
//        map.put("bankName", bankName);
        map.put("idCard", idCard);
        map.put("mobile", mobile);
        map.put("userName", userName);
        initSign(map);
        printRequest(" accountAddBankCard", map);
        String response = HttpsUtils.sendPostRequest(true, map, "/account/addBankCard");
        printResponse(" accountAddBankCard", response);
        if (response == null) return null;
        return gson.fromJson(response, BankCardInfoEntity.class);
    }

    /**
     * 功能：功能：提现
     *
     * @param token
     * @param bankCard  银行卡号
     * @param bankName  银行名称
     * @param amount    提现金额,单位: 元
     * @param city      城市 可为null
     * @param kaihuhang 开户行 可为null
     * @return
     * @throws Exception
     */

    public LoginResponseEntity accountUserWithdrawRequest(String token, String amount, String bankCard, String bankName, String city, String kaihuhang) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("bankCard", bankCard);
        map.put("bankName", bankName);
        map.put("amount", amount);
        if (city != null) {
            map.put("city", city);
        }
        if (kaihuhang != null) {
            map.put("kaihuhang", kaihuhang);
        }
        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map, "/account/userWithdrawRequest");
        printResponse(" accountUserWithdrawRequest", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 功能：我的账户
     *
     * @param token
     * @return
     * @throws Exception
     */
    @Deprecated
    public AccountHomeEntity accountMyAccountHome(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map, "/account/myaccount");
        printResponse(" accountMyAccountHome", response);
        if (response == null) return null;
        return gson.fromJson(response, AccountHomeEntity.class);
    }

    /**
     * 功能：我的账户-明细类别
     *
     * @param token
     * @return
     * @throws Exception
     */
    public abstract AccountTypeDatasDataEntity accountGetMyAccountTypes(String token) throws Exception;

    /**
     * 功能：提现记录
     *
     * @param token
     * @param pageIndex
     * @return
     * @throws Exception
     */
    public WithdrawRecordDatasDataEntity accountQueryWithdrawLog(String token, String pageIndex) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("pageIndex", pageIndex);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map, "/account/queryWithdrawLog");
        printResponse(" accountQueryWithdrawLog", response);
        if (response == null) return null;
        return gson.fromJson(response, WithdrawRecordDatasDataEntity.class);
    }

    /**
     * 功能：查询用户累计提现
     *
     * @param token
     * @return
     * @throws Exception
     */
    public WithdrawSummaryEntity accountGetWithdrawSummary(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map, "/account/getWithdrawSummary");
        printResponse(" accountGetWithdrawSummary", response);
        if (response == null) return null;
        return gson.fromJson(response, WithdrawSummaryEntity.class);
    }

    /**
     * 功能：查询省份
     *
     * @param token
     * @return
     * @throws Exception
     */
    public AddressProvinceDatasDataEntity accountQueryAllProvince(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        initSign(map);
        printRequest("accountQueryAllProvince", map);
        String response = HttpsUtils.sendPostRequest(map, "/account/queryAllProvince");
        printResponse(" accountQueryAllProvince", response);
        if (response == null) return null;
        return gson.fromJson(response, AddressProvinceDatasDataEntity.class);
    }

    /**
     * 功能：3.2.16 查询城市
     *
     * @param token
     * @param provinceId
     * @return
     * @throws Exception
     */
    public AddressCityDatasDataEntity accountQueryCityByProvince(String token, String provinceId) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("provinceId", provinceId);
        initSign(map);
        printRequest("accountQueryCityByProvince", map);
        String response = HttpsUtils.sendPostRequest(map, "/account/queryCityByProvince");
        printResponse(" accountQueryCityByProvince", response);
        if (response == null) return null;
        return gson.fromJson(response, AddressCityDatasDataEntity.class);
    }

    /**
     * 查询银行
     *
     * @param token
     * @return
     * @throws Exception
     */
    public BankInfoDatasDataEntity accountQueryAllBank(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/account/queryAllBank");
        printResponse(" accountQueryAllBank", response);
        if (response == null) return null;
        return gson.fromJson(response, BankInfoDatasDataEntity.class);
    }


    /**
     * 功能：消息-未读消息统计
     *
     * @param token
     * @return
     * @throws Exception
     */
    public MsgUnreadCountEntity msgPersonUnreadCount(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/msg/person/unreadCount");
        printResponse(" msgPersonUnreadCount", response);
        if (response == null) return null;
        return gson.fromJson(response, MsgUnreadCountEntity.class);
    }

    /**
     * 功能：公告消息-分页
     *
     * @param token
     * @param pageIndex
     * @return
     * @throws Exception
     */
    public MsgListDatasDataEntity msgBulletinPageList(String token, String pageIndex) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        map.put("token", token);
        map.put("pageIndex", pageIndex);
        // map.put("pageSize", pageSize); 默认为10
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/msg/bulletin/pageList");
        printResponse(" msgBulletinPageList", response);
        if (response == null) return null;
        return gson.fromJson(response, MsgListDatasDataEntity.class);
    }

    /**
     * 功能：我的消息 个人消息 通知消息
     *
     * @param token
     * @param pageIndex
     * @return
     * @throws Exception
     */
    public MsgListDatasDataEntity msgPersonPageList(String token, String pageIndex) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        map.put("token", token);
        map.put("pageIndex", pageIndex);
        // map.put("pageSize", pageSize); 默认为10
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/msg/person/pageList");
        printResponse(" msgBulletinPageList", response);
        if (response == null) return null;
        return gson.fromJson(response, MsgListDatasDataEntity.class);
    }

    /**
     * 功能：个人消息-删除
     *
     * @param token
     * @param msgIds
     * @return
     * @throws Exception
     */
    public LoginResponseEntity msgDeletes(String token, String msgIds) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        //  map.put("method", "msg.person.del");
        map.put("token", token);
        map.put("msgIds", msgIds);
        initSign(map);
        printRequest(" msgDeletes", map);
        String response = HttpsUtils.sendPostRequest(map, "/msg/person/del");
        printResponse(" msgDeletes", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 功能：公告消息-已读
     *
     * @param token
     * @param msgIds
     * @return
     * @throws Exception
     */
    public LoginResponseEntity msgReaded(String token, String msgIds) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        //  map.put("method", "msg.person.del");
        map.put("token", token);
        map.put("msgId", msgIds);
        initSign(map);
        printRequest(" msgDeletes", map);
        String response = HttpsUtils.sendPostRequest(map, "/msg/notice/detail");
        printResponse(" msgDeletes", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 功能：公告消息-全部已读
     *
     * @param token
     * @param
     * @return
     * @throws Exception
     */
    public LoginResponseEntity allReaded(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        //  map.put("method", "msg.person.del");
        map.put("token", token);

        initSign(map);
        printRequest(" msgDeletes", map);
        String response = HttpsUtils.sendPostRequest(map, "/msg/notice/allReaded");
        printResponse(" msgDeletes", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 功能：消息免打扰设置(msg.setMsgPush)
     *
     * @param token
     * @return
     * @throws Exception
     */
    public LoginResponseEntity msgSetMsgPush(String token, String issendNotice) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        //   map.put("method", "msg.setMsgPush");
        map.put("token", token);
        map.put("issendNotice", issendNotice);

        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/msg/setMsgPush");
        printResponse("setMsgPush", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 功能：消息免打扰设置(msg.queryMsgPushSet)
     *
     * @param token
     * @return
     * @throws Exception
     */
    public MsgResponseEntity msgQueryMsgPushSet(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        //     map.put("method", "msg.queryMsgPushSet");
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/msg/queryMsgPushSet");
        printResponse("queryMsgPushSet", response);
        if (response == null) return null;
        return gson.fromJson(response, MsgResponseEntity.class);
    }

    /**
     * 功能：更多-意见反馈
     *
     * @param token
     * @param content
     * @return
     * @throws Exception
     */
    public LoginResponseEntity personcenterFeedback(String token, String content) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("content", content);
        initSign(map);
        printRequest("personcenterFeedback", map);
        String response = HttpsUtils.sendPostRequest(true, map, "/app/suggestion");
        printResponse(" personcenterFeedback", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 功能：上传图片文档
     *
     * @param file
     * @return
     */
    public ImageResponseEntity personcenterUploadImageFile(File file) {
        String response = HttpsUtils.uploadFile(HttpsUtils.getNewHttpClient(TopApp.getInstance(), false), getImageServerBaseUrl() + "upload", file);
        if (response == null) return null;
        try {
            return gson.fromJson(response, ImageResponseEntity.class);
        } catch (Exception e) {
        }
        return null;
    }


    /**
     * 功能：压缩上传图片文档
     *
     * @param file
     * @return
     */
    public ImageResponseEntity uploadImageCompress(File file) {
        String response = HttpsUtils.uploadFileComPress(HttpsUtils.getNewHttpClient(TopApp.getInstance(), false), getImageServerBaseUrl() + "upload", file);
        if (response == null) return null;
        try {
            return gson.fromJson(response, ImageResponseEntity.class);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 功能：修改用户头像
     *
     * @param token
     * @return
     * @throws Exception
     */
    public LoginResponseEntity personcenterUploadIcon(String token, String imageUrl) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "personcenter.uploadIcon");
        map.put("token", token);
        map.put("image", imageUrl);
        initSign(map);
        printRequest("personcenterUploadIcon", map);
        String response = HttpsUtils.sendPostRequest(true, map, "/personcenter/icon");
        printResponse("personcenterUploadIcon", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

//    /**
//     * 功能：首页 Banner内容
//     *
//     * @param token
//     * @param type  广告类型0：所有，1：理财师，2：投资客户
//     * @return
//     * @throws Exception
//     */
//    public HomePagerBannersDatasDataEntity homePagerBanners(String token, String type) throws Exception {
//        Map<String, String> map = new HashMap<String, String>();
//
//        map.put("token", token);
//        map.put("type", type);
//        initSign(map);
//        printRequest(" homePagerBanners===", map);
//        String response = HttpsUtils.sendPostRequest(map, "/homepage/banners");
//        printResponse(" homePagerBanners===", response);
//        if (response == null)
//            return null;
//        return gson.fromJson(response, HomePagerBannersDatasDataEntity.class);
//    }

    /**
     * 功能：开屏广告
     *
     * @return
     * @throws Exception
     */
    public AdvertisementOpeningDataEntity advertisementOpening() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "lcs.advertisement.opening");
        map.put("advType", "app_opening");
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/homepage/opening");
        printResponse("advertisementOpening", response);

        if (response == null) return null;

        return gson.fromJson(response, AdvertisementOpeningDataEntity.class);
    }

    /**
     * 功能：接口名称 pc_广告查询-陈春燕-已实现 (机构banner)   2016/11/4 0004  V2.0
     * 请求类型 post
     * 请求Url /api/homepage/advs
     * 接口描述 show_index ASC
     *
     * @param advPlacement 广告位置描述	string	                     //
     *                     pc首页页中：pc_idx_middle (必填),            //
     *                     pc端banner：pc_banner,                  //
     *                     平台banner:platform_banner,
     *                     //app_home_page
     *                     产品banner:product_banner               //
     * @param appType      端口	number	理财师1，投资端2 （必填）
     * @return ClassRoomDatasDataEntity
     * @throws Exception
     */
    public HomePagerBannersDatasDataEntity homepageAdvs(String token, String advPlacement, String appType) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        if (!TextUtils.isEmpty(token)) {//这个在投资端放开了 （不需要token） 但是在理财师端是需要的
            map.put("token", token);
        }
        map.put("advPlacement", advPlacement);
        map.put("appType", appType);
        initSign(map);
        printRequest(" homepageAdvs  advPlacement==", map);
        String response = HttpsUtils.sendPostRequest(map, "/homepage/advs");
        printResponse(" homepageAdvs  advPlacement==" + advPlacement, response);
        if (response == null) return null;
        return gson.fromJson(response, HomePagerBannersDatasDataEntity.class);
    }

    /**
     * 功能：消息中心未读消息数
     *
     * @param token
     * @return
     * @throws Exception
     */
    public UnReadMsgCountEntity unReadMsgCount(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/msg/person/unreadCount");
        printResponse("unReadMsgCount", response);
        if (response == null) return null;
        return gson.fromJson(response, UnReadMsgCountEntity.class);
    }
/*-----------------------------todo v2.0.1新增接口-----------------------------------------------------------------*/

    /**
     * 接口名称 3.4.7 产品分类统计(根据产品分类表进行产品分类统计) v2.0.1
     * <p>
     * 请求Url /product/productClassifyStatistics/2.0.1
     * <p>
     *
     * @param token      投呗(801-理财师推荐产品 ) 猎才大师 必需
     * @param cateIdList 非必需 默认根据不同的app类型查询对应的所有分类信息 1-热门产品 2-新手产品 3-短期产品 4-高收益产品
     *                   5-稳健收益产品 801-理财师推荐产品 802-热推产品 901-首投标 902-复投标 多个一起查询的时候请使用,分开
     *                   如：1,2,3,4,5,801,901,902 不传时则查询所有的产品分类
     * @return ProductClassifyStatisticsEntity
     * @throws Exception
     */
    public ProductClassifyStatisticsEntity productClassifyStatistics201(String token, String cateIdList) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        if (!TextUtils.isEmpty(token)) {
            map.put("token", token);
        }
        if (!TextUtils.isEmpty(cateIdList)) {
            map.put("cateIdList", cateIdList);
        }
        initSign(map);
        printRequest("productClassifyStatistics201", map);
        String response = HttpsUtils.sendPostRequest(map, "/product/productClassifyStatistics/2.0.1");
        printResponse("productClassifyStatistics201", response);
        if (response == null) return null;
        return gson.fromJson(response, ProductClassifyStatisticsEntity.class);
    }

    /**
     * 功能: 接口名称 3.4.8 产品分类列表 v2.0.1
     * 请求类型 get
     * 请求Url  /product/productClassifyPageList/2.0.1
     * <p/>
     * 理财-产品分类列表-分页排序
     *
     * @param token     投呗(801-理财师推荐产品 ) 猎才大师 必需
     * @param cateId    产品分类id 	number  1-热门产品 2-新手产品 3-短期产品 4-高收益产品 5-稳健收益产品 801-理财师推荐产品 802-热推产品 901-首投标 902-复投标
     * @param orgCode   机构编码	  string	非必需 默认查询全部机构
     * @param order     0-升序 1-降序
     * @param pageIndex pageIndex
     * @param pageSize  pageSize
     * @param sort      1-默认排序 2-年化收益 3-产品期限
     * @return
     * @throws Exception
     */
    public ProductDatasDataEntity productClassifyPageList201(String token, String cateId, String orgCode, String order, String pageIndex, String pageSize, String sort) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        if (!TextUtils.isEmpty(token)) {
            map.put("token", token);
        }
        if (!TextUtils.isEmpty(orgCode)) {
            map.put("orgCode", orgCode);
        }
        if (!TextUtils.isEmpty(order)) {
            map.put("order", order);
        }
        if (!TextUtils.isEmpty(cateId)) {
            map.put("cateId", cateId);
        }
        if (!TextUtils.isEmpty(pageIndex)) {
            map.put("pageIndex", pageIndex);
        }
        if (!TextUtils.isEmpty(pageSize)) {
            map.put("pageSize", pageSize);
        }
        if (!TextUtils.isEmpty(sort)) {
            map.put("sort", sort);
        }
        initSign(map);
        printRequest("productClassifyPageList2.0.1", map);
        String response = HttpsUtils.sendPostRequest(map, "/product/productClassifyPageList/2.0.1");
        printResponse("productClassifyPageList2.0.1", response);
        if (response == null) return null;
        return gson.fromJson(response, ProductDatasDataEntity.class);
    }

    /**
     * 接口名称 产品分类优选 v2.0.1 ( 获取首页 ->理财师热推时 调用)
     * 请求类型 get
     * 请求Url /product/productClassifyPreference/2.0.1
     *
     * @param cateIdList 非必需 2-新手产品 3-短期产品 4-高收益产品 5-稳健收益产品 801-理财师推荐产品
     *                   802-热推产品 多个一起查询的时候请使用,分开 如：2,3,4,5,801,802 不传时则查询所有的产品分类
     */
    public ProductClassifyPreferenceEntity productClassifyPreference(String token, String cateIdList) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        if (!TextUtils.isEmpty(token)) {//这个在投资端放开了 （不需要token） 但是在理财师端是需要的
            map.put("token", token);
        }
        map.put("cateIdList", cateIdList);
        initSign(map);
        printRequest("productClassifyPreference", map);
        String response = HttpsUtils.sendPostRequest(map, "/product/productClassifyPreference/2.0.1");
        printResponse("productClassifyPreference", response);
        if (response == null) return null;
        return gson.fromJson(response, ProductClassifyPreferenceEntity.class);
    }

    /**
     * 接口名称 优质平台-黄亚林-已实现
     * 请求类型 post
     * 请求Url /homepage/highQualityPlatform
     *
     * @param token
     * @return
     * @throws Exception
     */
    public HighQualityPlatformEntity highQualityPlatform(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        map.put("token", token);
        map.put("pageIndex", "1");
        map.put("pageSize", "100");
        initSign(map);
        printRequest("homeOrgInfoDatas====", map);
        String response = HttpsUtils.sendPostRequest(map, "/platfrom/highQualityPlatform");
        printResponse("homeOrgInfoDatas====", response);
        if (response == null) return null;
        return gson.fromJson(response, HighQualityPlatformEntity.class);
    }

    public abstract Observable<BundBankcardDataEntity> bindCardStatue() throws Exception;

}
