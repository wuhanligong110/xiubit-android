package com.v5ent.xiubit.service;

import android.text.TextUtils;

import com.toobei.common.entity.AccountTypeDatasDataEntity;
import com.toobei.common.entity.BundBankcardDataEntity;
import com.toobei.common.entity.ContactsResponseEntity;
import com.toobei.common.entity.CustomerDetailEntity;
import com.toobei.common.entity.ExistInPlatformEntity;
import com.toobei.common.entity.FreshMissionEntity;
import com.toobei.common.entity.FundAccountEntity;
import com.toobei.common.entity.FundRegisterStatueEntity;
import com.toobei.common.entity.FundSiftEntiy;
import com.toobei.common.entity.HighQualityPlatformEntity;
import com.toobei.common.entity.LiecaiHasUnReadActivitiesEntity;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.entity.NewComerWelfarEntity;
import com.toobei.common.entity.OrgProductUrlEntity;
import com.toobei.common.entity.OrgUserCenterUrlEntity;
import com.toobei.common.entity.ProductClassifyStatisticsEntity;
import com.toobei.common.entity.ProductDatasDataEntity;
import com.toobei.common.entity.UserLevelInfoEntity;
import com.toobei.common.network.httpapi.ThirdPartApi;
import com.toobei.common.service.TopHttpService;
import com.toobei.common.utils.HttpsUtils;
import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.v5ent.xiubit.BuildConfig;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.entity.AccountIncomeDatasDataEntity;
import com.v5ent.xiubit.entity.AccountIncomeHomeEntity;
import com.v5ent.xiubit.entity.AccountManageDatasDataEntity;
import com.toobei.common.entity.ActivityListDatasDataEntity;
import com.v5ent.xiubit.entity.ActivityPlatformDatasDataEntity;
import com.toobei.common.entity.ActivityPlatformPageListDatasDataEntity;
import com.v5ent.xiubit.entity.BillDeclarationDatasDataEntity;
import com.v5ent.xiubit.entity.BrandPromotionEntity;
import com.v5ent.xiubit.entity.CalculatorEntity;
import com.v5ent.xiubit.entity.CfpLevelWarningEntity;
import com.v5ent.xiubit.entity.CfpSaleListItemDatasDataEntity;
import com.v5ent.xiubit.entity.CfpSaleStatEntity;
import com.v5ent.xiubit.entity.ClassRoomDatasDataEntity;
import com.v5ent.xiubit.entity.ContribuPageListDatasDataEntity;
import com.v5ent.xiubit.entity.CustomerHomeEntity;
import com.v5ent.xiubit.entity.CustomerListDatasDataEntity;
import com.v5ent.xiubit.entity.CustomerTradeListDatasDataEntity;
import com.v5ent.xiubit.entity.DefaultConfigEntity;
import com.v5ent.xiubit.entity.DirectCfpPageListDatasDataEntity;
import com.v5ent.xiubit.entity.ExpireRedeemDetailDataEntity;
import com.v5ent.xiubit.entity.FeeCalBaseDataEntity;
import com.v5ent.xiubit.entity.GoodTransEntity;
import com.v5ent.xiubit.entity.GrowthClassifyListEntity;
import com.v5ent.xiubit.entity.GrowthHandbookEntity;
import com.v5ent.xiubit.entity.HaveGoodTransNoReadEntity;
import com.v5ent.xiubit.entity.HomeCfpBuyInfoEntity;
import com.v5ent.xiubit.entity.HomeHotNewsEntity;
import com.v5ent.xiubit.entity.HomePagerBulletinEntity;
import com.v5ent.xiubit.entity.HomePagerDynamicDatasDataEntity;
import com.v5ent.xiubit.entity.HomepageNewActivityEntity;
import com.v5ent.xiubit.entity.IncomeAndOutDetailDatasDataEntity;
import com.v5ent.xiubit.entity.IncomeDetailDatasDataEntity;
import com.v5ent.xiubit.entity.IncomeDetailStatisticsEntity;
import com.v5ent.xiubit.entity.IncomeHomeEntity;
import com.v5ent.xiubit.entity.InvestRecordEntity;
import com.v5ent.xiubit.entity.InvestRecordListDatasDataEntity;
import com.v5ent.xiubit.entity.InvestStatisticsEntity;
import com.toobei.common.entity.InvestedPlatformEntity;
import com.v5ent.xiubit.entity.InvitationCfgRecordEntity;
import com.v5ent.xiubit.entity.InvitationCustomerRecordEntity;
import com.v5ent.xiubit.entity.InvitationNumEntity;
import com.v5ent.xiubit.entity.InviteCfpInPreviewEntity;
import com.v5ent.xiubit.entity.InviteCfpListDatasDataEntity;
import com.v5ent.xiubit.entity.InviteCfpListStatisticsEntity;
import com.v5ent.xiubit.entity.InviteCfpOutCreateQrEntity;
import com.v5ent.xiubit.entity.InviteCfpRecordStatisticEntity;
import com.v5ent.xiubit.entity.InviteCusRecordStatisticsEntity;
import com.v5ent.xiubit.entity.InviteCustomHisEntity;
import com.v5ent.xiubit.entity.InviteCustomerListStatisticsEntity;
import com.v5ent.xiubit.entity.IsBindOrgAcctEntity;
import com.v5ent.xiubit.entity.LeaderProfitDataEntity;
import com.v5ent.xiubit.entity.LeaderProfitStatusEntity;
import com.v5ent.xiubit.entity.LieCaiBalanceEntity;
import com.v5ent.xiubit.entity.MineHomeEntity;
import com.v5ent.xiubit.entity.MineSettingEntity;
import com.v5ent.xiubit.entity.MyLevelDataEntity;
import com.v5ent.xiubit.entity.MyQRCodeEntity;
import com.v5ent.xiubit.entity.MyRankWeeklyCommsionDataEntity;
import com.v5ent.xiubit.entity.MyTeamDetailEntity;
import com.v5ent.xiubit.entity.MyTeamHomeEntity;
import com.v5ent.xiubit.entity.MyTeamListDatasDataEntity;
import com.v5ent.xiubit.entity.MyTeamSalesRecordDatasDataEntity;
import com.v5ent.xiubit.entity.OldGoodTransEntity;
import com.v5ent.xiubit.entity.OrgInfoDatasDataEntity;
import com.v5ent.xiubit.entity.PersonalCenterEntity;
import com.v5ent.xiubit.entity.PersonalCustomizatioEntity;
import com.v5ent.xiubit.entity.PlatFormDetailEntity;
import com.v5ent.xiubit.entity.PlatFormHeadEntity;
import com.v5ent.xiubit.entity.ProductBuyRecurdsEntity;
import com.v5ent.xiubit.entity.ProductPageListStatisticsEntity;
import com.v5ent.xiubit.entity.RankWeeklyCommsionDatasEntity;
import com.v5ent.xiubit.entity.RecommendproductDatasDataEntity;
import com.v5ent.xiubit.entity.RedPacketCountEntity;
import com.v5ent.xiubit.entity.RedPacketDatasDataEntity;
import com.v5ent.xiubit.entity.UserIsNewEntity;
import com.v5ent.xiubit.entity.WealthNewsDatasDataEntity;
import com.v5ent.xiubit.entity.WealthNewsTypeDatasDataEntity;
import com.toobei.common.network.RetrofitHelper;

import org.xsl781.utils.SystemTool;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * 公司:Tophlc 类说明：网络服务
 * <p>
 * date 2015-6-22
 */
public class HttpService extends TopHttpService {

    private static final String TAG = "HttpService";
    private static HttpService service = null;

    private HttpService() {
        super();
    }

    public static synchronized HttpService getInstance() {
        if (service == null) {
            service = new HttpService();
        }
        return service;
    }

    @Override
    public void initParam() {
        //  app_secret = "1786221FA2D4415C96B0906C32F4F4B2";
        app_secret = MyApp.getInstance().getTophlcNative().getLcdsAppSecret();
        orgNumber = "App_channel_android";
        appKind = "channel";
        appClient = "android";
        appVersion = SystemTool.getAppVersion(MyApp.getInstance());
        v = "1.0.0";
        timestamp = org.xsl781.utils.TimeUtils.getCurrentDate(org.xsl781.utils.TimeUtils.FORMAT_DATE_TIME1);
    }

    @Override
    public String getBaseUrl(boolean isHttps) {


        String BASE_URL_HTTPS = BuildConfig.BASE_URL_HTTPS;
        String BASE_URL_HTTP = BASE_URL_HTTPS.replace("https", "http");

        if (!isHttps || MyApp.getInstance().IS_HTTP_TEST) {
            return BASE_URL_HTTP;
        } else {
            return BASE_URL_HTTPS;
        }
    }

    @Override
    public void initSign(Map<String, String> params) {
        super.initSign(params);
    }

    /**
     * 获取Tb服务器域名url
     */
    public String getTbServerDomainUrl() {
        return BuildConfig.TOOBEI_H5_BASE_URL;
    }

    /**
     * 获取貅比特服务器域名url
     */
    public String getLcdsServerDomainUrl() {
        return BuildConfig.LCDS_H5_BASE_URL;
    }

    @Override
    public String getShareUrlEndSuffix() {
        return "fromApp=liecai&os=Android&v="+SystemTool.getAppVersion(MyApp.getInstance());
    }

    public LoginResponseEntity userLogin(String mobile, String password, String deviceToken) throws Exception {
//        mobile	手机号	string
//        password	密码	string
//        deviceId	设备唯一标识	string
//        deviceModel	设备型号，如iPhone 、HTC 603e	string
//        resolution	设备分辨率，如1242*2208	string
//        systemVersion	系统版本，如1.1	string
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", mobile);
        map.put("password", password);
        map.put("deviceId", SystemTool.getDeviceid(MyApp.getInstance()));
        map.put("deviceModel", SystemTool.getDeviceModel());

        map.put("systemVersion", SystemTool.getSystemVersion());
        if (!TextUtils.isEmpty(deviceToken)) {
            map.put("deviceToken", deviceToken);
        }
        map.put("resolution", MyApp.displayWidth + "*" + MyApp.displayHeight);

        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map, "/user/login");
        printResponse("userLogin", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 功能：获取用户信息
     *
     * @param token
     * @return UserLevelInfoEntity
     * @throws Exception
     */
    public UserLevelInfoEntity userLevelInfo(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/user/getUserInfo");
        printResponse("userInfo", response);
        if (response == null) return null;
        // return FastJsonTools.getEntity(response, LoginResponseEntity.class);
        return gson.fromJson(response, UserLevelInfoEntity.class);
    }

    /**
     * 功能：注册
     *
     * @param mobile
     * @param password
     * @param vcode    验证码(已注册账号,不用填)
     * @return
     * @throws Exception
     */
    public LoginResponseEntity userRegister(String mobile, String password, String vcode, String deviceToken, String recommendCode, String deviceId, String deviceModel, String resolution, String systemVersion) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("password", password);
        map.put("mobile", mobile);
        if (!TextUtils.isEmpty(deviceToken)) {
            map.put("deviceToken", deviceToken);
        }
        if (vcode != null) {
            map.put("vcode", vcode);
        }
        map.put("recommendCode", recommendCode);
        map.put("deviceId", deviceId);
        map.put("deviceModel", deviceModel);
        map.put("resolution", resolution);
        map.put("systemVersion", systemVersion);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map, "/user/register");
        printResponse("userRegister", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 功能：我的账户-明细类别
     *
     * @param token token
     * @return AccountTypeDatasDataEntity
     * @throws Exception
     */
    public AccountTypeDatasDataEntity accountGetMyAccountTypes(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("pageIndex", "1");
        map.put("pageSize", "20"); //
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/account/queryAccountType");
        printResponse(" accountGetMyAccountTypes", response);
        if (response == null) return null;
        return gson.fromJson(response, AccountTypeDatasDataEntity.class);
    }

    /**
     * 功能：推荐理财师-微信推荐
     *
     * @param token
     * @param mobile
     * @return
     * @throws Exception
     */
    public InviteCfpOutCreateQrEntity inviteCfpOutsideCreatCode(String token, String mobile) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "invitation.cfp.wechat.creatCode");
        map.put("token", token);
        map.put("mobile", mobile);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse(" inviteCfpOutsideCreatCode", response);
        if (response == null) return null;
        return gson.fromJson(response, InviteCfpOutCreateQrEntity.class);
    }

    /**
     * 功能：推荐理财师-升级客户-预览
     *
     * @param token
     * @param type        邀请类别(2:签名邀请；3:匿名邀请)
     * @param customerIds 客户id，多个客户用逗号隔开 例如：1111sd,sfdsdf3,trt3vbv
     * @return
     * @throws Exception
     */
    public InviteCfpInPreviewEntity inviteCfpInsidePreview(String token, String type, String customerIds) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "invitation.cfp.upgrade.preview");
        map.put("token", token);
        map.put("type", type);
        map.put("customerIds", customerIds);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse(" inviteCfpInsidePreview", response);
        if (response == null) return null;
        return gson.fromJson(response, InviteCfpInPreviewEntity.class);
    }

    /**
     * 功能：推荐理财师-升级客户-分页
     *
     * @param token
     * @param name       姓名或手机号码
     * @param pageIndex  第几页 >=1,默认为1
     * @param //pageSize 页面记录数，默认为10
     * @return
     * @throws Exception
     */
    public CustomerListDatasDataEntity inviteCfpInsideRcPageList(String token, String name, String pageIndex) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "invitation.cfp.upgrade.pageList");
        map.put("token", token);
        if (name != null) {
            map.put("name", name);
        }
        map.put("pageIndex", pageIndex);
        // map.put("pageSize", "100"); //默认100
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse(" inviteCfpInsideRcPageList", response);
        if (response == null) return null;
        return gson.fromJson(response, CustomerListDatasDataEntity.class);
    }

    /**
     * 功能：推荐理财师-升级客户-发送
     *
     * @param token
     * @param type        邀请类别(2:签名邀请；3:匿名邀请)
     * @param customerIds 客户id，多个客户用逗号隔开 例如：1111sd,sfdsdf3,trt3vbv
     * @return
     * @throws Exception
     */
    public LoginResponseEntity inviteCfpInsideSend(String token, String type, String customerIds) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "invitation.cfp.upgrade.send");
        map.put("token", token);
        map.put("type", type);
        map.put("customerIds", customerIds);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse(" inviteCfpInsideSend", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 功能：推荐理财师列表-累计
     *
     * @param token
     * @return
     * @throws Exception
     */
    public InviteCfpListStatisticsEntity inviteCfpListStatistics(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "invitation.cfp.rcList.statistics");
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse(" inviteCfpListStatistics", response);
        if (response == null) return null;
        return gson.fromJson(response, InviteCfpListStatisticsEntity.class);
    }

    /**
     * 功能：推荐理财师列表-分页
     *
     * @param token
     * @param name      姓名或手机号码
     * @param pageIndex 第几页 >=1,默认为1
     * @param pageSize  页面记录数，默认为10
     * @return
     * @throws Exception
     */
    public InviteCfpListDatasDataEntity inviteCfpListPageList(String token, String name, String pageIndex, String pageSize) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "invitation.cfp.rcList.pageList");
        map.put("token", token);
        if (name != null) {
            map.put("name", name);
        }
        map.put("pageIndex", pageIndex);
        if (pageSize != null) {
            map.put("pageSize", pageSize); // 默认10
        }
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse(" inviteCfpListPageList", response);
        if (response == null) return null;
        return gson.fromJson(response, InviteCfpListDatasDataEntity.class);
    }

    /**
     * 功能：推荐理财师-微信推荐-重新推荐
     *
     * @param token
     * @param mobile
     * @return
     * @throws Exception
     */
    public LoginResponseEntity inviteCfpListOutResend(String token, String mobile) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "invitation.cfp.wechat.resend");
        map.put("token", token);
        map.put("mobile", mobile);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse(" inviteCfpListOutResend", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 功能：邀请客户 生成二维码
     *
     * @param token
     * @param //mobile
     * @return
     * @throws Exception
     */
    public InviteCfpOutCreateQrEntity inviteCustomerCreatCode(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        initSign(map);
        printRequest("inviteCustomerCreatCode", map);
        String response = HttpsUtils.sendPostRequest(map, "/invitation/customer/homepage");
        printResponse(" inviteCustomerCreatCode", response);
        if (response == null) return null;
        return gson.fromJson(response, InviteCfpOutCreateQrEntity.class);
    }

    /**
     * 功能：邀请理财师
     *
     * @param token
     * @return
     * @throws Exception
     */
    public InviteCfpOutCreateQrEntity inviteCfpCreateCode(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "invitation.cfp.homepage");
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/invitation/cfp/homepage");
        printResponse(" inviteCfpCreateCode", response);
        if (response == null) return null;
        return gson.fromJson(response, InviteCfpOutCreateQrEntity.class);
    }

    /**
     * 功能：通讯录邀请
     *
     * @param token
     * @param type    邀请类别: 1、邀请客户，2、邀请理财师
     * @param mobiles 手机号，多个用逗号隔开, 如:15022555544,15214525868
     * @param names   通讯录名称，多个用逗号隔开,如:张三,李四
     * @return
     * @throws Exception
     */
    public ContactsResponseEntity inviteContacts(String token, String type, String mobiles, String names) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "invitation.maillist");
        map.put("token", token);
        map.put("type", type);
        map.put("mobiles", mobiles);
        map.put("names", names);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/invitation/maillist");
        printResponse(" inviteContacts", response);
        if (response == null) return null;
        return gson.fromJson(response, ContactsResponseEntity.class);
    }

    /**
     * 功能：通讯录邀请-回调
     *
     * @param token
     * @param mobiles
     * @return
     * @throws Exception
     */
    public LoginResponseEntity inviteContactsCallback(String token, String mobiles) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("mobiles", mobiles);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/invitation/maillist/callback");
        printResponse(" inviteContactsResend", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 功能：通讯录邀请-重发
     *
     * @param token
     * @param mobile
     * @return
     * @throws Exception
     */
    public LoginResponseEntity inviteContactsResendCallback(String token, String mobile) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("mobiles", mobile);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/invitation/maillist/resend");
        printResponse(" inviteContactsResend", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 功能：邀请客户-累计
     *
     * @param token
     * @return
     * @throws Exception
     */
    public InviteCustomerListStatisticsEntity inviteCustomerListStatistics(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        //   map.put("method", "customer.mycustomers.statistics");
        //   map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/invitation/investor/pageList");
        printResponse(" inviteCustomerListStatistics", response);
        if (response == null) return null;
        return gson.fromJson(response, InviteCustomerListStatisticsEntity.class);
    }

    /**
     * 功能：邀请客户-列表
     *
     * @param token
     * @param name
     * @param pageIndex
     * @return
     * @throws Exception
     */
    public InviteCfpListDatasDataEntity inviteCustomerListPageList(String token, String name, String pageIndex) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "customer.rcCustomer.pageList");
        map.put("token", token);
        if (name != null) {
            map.put("name", name);
        }
        map.put("pageIndex", pageIndex);
        // map.put("pageSize", pageSize); 默认10
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/invitation/investor/pageList");
        printResponse(" inviteCustomerListPageList", response);
        if (response == null) return null;
        return gson.fromJson(response, InviteCfpListDatasDataEntity.class);
    }

    /**
     * 功能：客户服务 首页接口
     *
     * @param token
     * @return
     * @throws Exception
     */
    public CustomerHomeEntity customerHomepage(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/customer/homepage");
        printResponse(" customerHomepage", response);
        if (response == null) return null;
        return gson.fromJson(response, CustomerHomeEntity.class);
    }

    public InvestStatisticsEntity customerInvestStatistic(String token, String dateType, String date) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("dateType", dateType);
        map.put("date", date);
        initSign(map);
        printRequest(" customerInvestStatistic", map);
        String response = HttpsUtils.sendPostRequest(map, "/investRecord/cfplanner/customerStatistic");
        printResponse(" customerInvestStatistic", response);
        if (response == null) return null;
        return gson.fromJson(response, InvestStatisticsEntity.class);
    }

    /**
     * 功能：投资统计-分页  /销售统计列表-陈衡-已实现
     *
     * @param token
     * @param dateType  时间类别: 空表示全部 ;1:年；2:季度；3:月；4:日 (默认表示当月) 5周
     * @param date      时间格式（时间类别为1-3的time对应该时间区间第一天日期）:2015-12-24 年:2015-01-01
     *                  季度:一季度2015-01-01 二季度 2015-04-01 …月: 2015-09-01
     * @param pageIndex
     * @return
     * @throws Exception
     */
    public InvestRecordListDatasDataEntity customerInvestStatisticPageList(String token, String dateType, String date, String pageIndex) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("dateType", dateType);  // 如果dateType =5 表示累计 传入 "" 空
        map.put("date", date);
        map.put("type", "1");//类型 1投资记录，2投资客户
        map.put("pageIndex", pageIndex);
        map.put("pageSize", "10");// 默认5
        initSign(map);
        printRequest(" customerInvestStatisticPageList", map);
        String response = HttpsUtils.sendPostRequest(map, "/investRecord/cfplanner/customerInvestRecords");
        printResponse(" customerInvestStatisticPageList", response);
        if (response == null) return null;
        return gson.fromJson(response, InvestRecordListDatasDataEntity.class);
    }

    /**
     * 功能：交易动态
     *
     * @param token
     * @param type      动态类别: 1、申购动态，2、赎回动态
     * @param pageIndex 第几页 >=1,默认为1
     * @return
     * @throws Exception
     */
    public CustomerTradeListDatasDataEntity customerTradelist(String token, String type, String pageIndex) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "customer.tradelist");
        map.put("token", token);
        map.put("type", type);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", "10");// 默认5
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/investRecord/cfplanner/customerTradeMsg");
        printResponse(" customerTradelist", response);
        if (response == null) return null;
        return gson.fromJson(response, CustomerTradeListDatasDataEntity.class);
    }

//    /**
//     * 功能：客户列表-累计
//     *
//     * @param token
//     * @return
//     * @throws Exception
//     */
//    public CustomerListDatasDataEntity customerMycustomersStatistics(String token) throws Exception {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("method", "customer.mycustomers.statistics");
//        map.put("token", token);
//        initSign(map);
//        String response = HttpsUtils.sendPostRequest(map);
//        printResponse(" customerMycustomersStatistics", response);
//        if (response == null)
//            return null;
//        return gson.fromJson(response, CustomerListDatasDataEntity.class);
//    }

    /**
     * 功能：客户列表
     * 接口详情 (id: 163)
     * 接口名称 3.6.7 客户列表(/customer/mycustomers/pageList) -钟灵-已实现
     * 请求类型 get
     * 请求Url /customer/mycustomers/pageList
     *
     * @param token
     * @param name         客户姓名、电话
     * @param customerType 客户类别 1:客户类别 1:投资客户 2:未投资客户 3::重要客户 为空表示全部
     * @param pageIndex    第几页 >=1,默认为1
     * @param sort         * sort	否	string	排序字段(1:投资额；2:注册时间3:投资时间；4:到期时间;5:重要客户)
     * @param order        排序方式: (1:降序，2:升序)
     * @return
     * @throws Exception
     */
    public CustomerListDatasDataEntity customerGetMyCustomers(String token, String name, String customerType, String pageIndex, String pageSize, String sort, String order) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        // map.put("method", "customer.mycustomers.pageList");
        map.put("token", token);
        if (name != null) {
            map.put("name", name);
        }
        if (customerType != null) {
            map.put("customerType", customerType);
        }
        if (pageIndex != null) {
            map.put("pageIndex", pageIndex);
        }
        if (pageSize != null) {
            map.put("pageSize", pageSize); // 默认10
        }
        if (sort == null) {
            map.put("sort", "1");
        } else {
            map.put("sort", sort);
        }
        if (order == null) {
            map.put("order", "1");
        } else {
            map.put("order", order);
        }
        initSign(map);
        printRequest("customerGetMyCustomers", map);
        // System.out.println("=====pageIndex=" + pageIndex + ",sort=" + sort +
        // ",order=" + order);
        String response = HttpsUtils.sendPostRequest(map, "/customer/mycustomers/pageList");
        printResponse("customerGetMyCustomers", response);
        if (response == null) return null;
        return gson.fromJson(response, CustomerListDatasDataEntity.class);
    }

    /**
     * 功能：添加重要客户
     *
     * @param token
     * @param userId 客户userId（必填）
     * @return
     * @throws Exception
     */
    public LoginResponseEntity customerImportantAdd(String token, String userId) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "customer.important.add");
        map.put("token", token);
        map.put("userId", userId);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/customer/important/add");
        printResponse("customerImportantAdd", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 功能：移除重要客户
     *
     * @param token
     * @param userId 客户userId（必填）
     * @return
     * @throws Exception
     */
    public LoginResponseEntity customerImportantRemove(String token, String userId) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "customer.important.remove");
        map.put("token", token);
        map.put("userId", userId);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/customer/important/remove");
        printResponse("customerImportantRemove", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 功能：客户详情
     *
     * @param token
     * @param customerId
     * @return
     * @throws Exception
     */
    public CustomerDetailEntity customerGetDetail(String token, String customerId) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "customer.detail");
        map.put("token", token);
        map.put("userId", customerId);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/customer/detail");
        printResponse("customerGetDetail", response);
        if (response == null) return null;
        return gson.fromJson(response, CustomerDetailEntity.class);
    }

    /**
     * 功能：客户详情->用户所有交易动态-陈衡-已实现
     *
     * @param token
     * @param customerId
     * @param
     * @param pageIndex
     * @return
     * @throws Exception
     */
    public CustomerTradeListDatasDataEntity customerAllTradeMsg(String token, String customerId, String type, String pageIndex) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "customer.detail.pageList");
        map.put("token", token);
        map.put("customerId", customerId);
        // map.put("type", type);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", "10");// 默认10
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/investRecord/cfplanner/customerAllTradeMsg");
        printResponse(" customerAllTradeMsg", response);
        if (response == null) return null;
        return gson.fromJson(response, CustomerTradeListDatasDataEntity.class);
    }

    /**
     * 功能: 客户->即将回款-陈衡-已实现    客户详情->到期日程
     *
     * @param token
     * @param customerId 客户编号（空：所有客户回款记录|不为空=指定客户回款记录）
     * @param pageIndex  页码
     * @return
     * @throws Exception
     */
    public CustomerTradeListDatasDataEntity customerExpireRedeemPageList(String token, String customerId, int pageIndex) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("customerId", customerId);
        map.put("pageIndex", pageIndex + "");
        map.put("pageSize", "10");// 默认10
        initSign(map);
        printRequest("customerExpireRedeemPageList", map);
        String response = HttpsUtils.sendPostRequest(map, "/investRecord/cfplanner/customerImpendRepayment");
        printResponse("customerExpireRedeemPageList", response);
        if (response == null) return null;
        return gson.fromJson(response, CustomerTradeListDatasDataEntity.class);
    }

    /**
     * 功能：全部活动列表
     *
     * @param token
     * @param pageIndex
     * @return
     * @throws Exception
     */
    public ActivityListDatasDataEntity allActivitylist(String token, String pageIndex) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        map.put("token", token);
        // map.put("activityType", "1");
        map.put("pageIndex", pageIndex);
        // map.put("pageSize", pageSize); 默认10
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/activity/pageList");
        printResponse(" activitylist", response);
        if (response == null) return null;
        return gson.fromJson(response, ActivityListDatasDataEntity.class);
    }


    /**
     * 功能：
     *
     * @param token
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    public ExpireRedeemDetailDataEntity expireRedeem(String token, String pageIndex, String pageSize) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "customer.expireRedeem.pageList");
        map.put("token", token);
        map.put("pageIndex", pageIndex);
        //		map.put("pageSize", pageSize);// 默认10
        map.put("pageSize", "10");// 默认10
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse(" activitylist", response);
        if (response == null) return null;
        return gson.fromJson(response, ExpireRedeemDetailDataEntity.class);
    }


    /**
     * 功能：我的首页
     *
     * @return
     * @throws Exception
     */
    public MineHomeEntity personcenterHome() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/personcenter/homepage");
        printResponse(" personcenterHome", response);
        if (response == null) return null;
        return gson.fromJson(response, MineHomeEntity.class);
    }

    /**
     * 功能：个人设置
     *
     * @param token
     * @return
     * @throws Exception
     */
    public MineSettingEntity personcenterMineSetting(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "personcenter.setting");
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse(" personcenterMineSetting", response);
        if (response == null) return null;
        return gson.fromJson(response, MineSettingEntity.class);
    }

    /**
     * 功能：我的收益
     *
     * @param dateType 时间类别: 1:年；2:季度；3:月；4:日
     * @param date     时间格式:2015-12-24 年:2015-01-01 季度:一季度2015-01-01 二季度 2015-04-01
     *                 …月: 2015-09-01 日:2015-09-06
     * @return
     * @throws Exception
     */
    public IncomeHomeEntity personcenterProfit(String token, String dateType, String date) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        if (dateType != null) map.put("dateType", dateType);
        if (date != null) map.put("date", date);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/profit/cfplannerProfitTotal");
        printResponse(" personcenterProfit", response);
        if (response == null) return null;
        return gson.fromJson(response, IncomeHomeEntity.class);
    }

    /**
     * 功能：我的收益-收益明细累计
     *
     * @param dateType   1:年；2:季度；3:月；4:日 (默认表示当月)
     * @param date       日期 时间 格式 2015-10-01	number
     * @param profitType 类别 空表示全部 1001=佣金;1002=推荐收益;1003=理财师职位津贴;1004=季度奖励;
     * @return
     * @throws Exception
     */
    @Deprecated
    public IncomeDetailStatisticsEntity personcenterProfitStatistic(String token, String dateType, String date, String profitType) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        if (dateType != null) map.put("dateType", dateType);
        if (date != null) map.put("date", date);
        map.put("profitType", profitType);
        initSign(map);
        printRequest(" personcenterProfitStatistic", map);
        String response = HttpsUtils.sendPostRequest(map, "/profit/cfplannerProfitItemTotal");
        printResponse(" personcenterProfitStatistic", response);
        if (response == null) return null;
        return gson.fromJson(response, IncomeDetailStatisticsEntity.class);
    }

    /**
     * 月度收益统计-钟灵
     *
     * @param token
     * @param month
     * @return
     * @throws Exception
     */
    public AccountIncomeHomeEntity accountMonthProfixStatistics(String token, String month) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("month", month);
        initSign(map);
        printRequest(" accountMonthProfixStatistics", map);
//        String response = HttpsUtils.sendPostRequest(map, "/account/monthProfixStatistics");
        String response = HttpsUtils.sendPostRequest(map, "/account/monthProfixStatistics/3.0");
        printResponse(" accountMonthProfixStatistics", response);
        if (response == null) return null;
        return gson.fromJson(response, AccountIncomeHomeEntity.class);
    }

    /**
     * 功能：我的收益-收益类别
     *
     * @return
     * @throws Exception
     */
/*    public IncomeTypeDatasDataEntity personcenterGetProfitTypes(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/profit/cfplannerProfitTypes");
        printResponse(" cfplannerProfitTypes", response);
        if (response == null) return null;
        return gson.fromJson(response, IncomeTypeDatasDataEntity.class);
    }*/

    /**
     * 功能：我的收益-收益明细分页
     * 接口详情 (id: 294)
     * 接口名称 月度收益明细列表-钟灵-已实现
     * 请求类型 get
     * 请求Url account/monthProfixDetailList
     *
     * @param token
     * @param month      日期 时间 格式 2015-10
     * @param profixType 收益类型1销售佣金，2推荐津贴，3活动奖励，4团队leader奖励（必填）
     * @param pageIndex
     * @return
     * @throws Exception
     */
    public IncomeDetailDatasDataEntity accountMonthProfixDetailList(String token, String month, String profixType, String pageIndex) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("month", month);
        if (profixType != null) map.put("profixType", profixType);
        if (pageIndex != null) map.put("pageIndex", pageIndex);
        map.put("pageSize", "10"); //默认为10
        initSign(map);
        printRequest("accountMonthProfixDetailList", map);
//        String response = HttpsUtils.sendPostRequest(map, "/account/monthProfixDetailList");
        String response = HttpsUtils.sendPostRequest(map, "/account/monthProfixDetailList/2.1");
        printResponse("accountMonthProfixDetailList", response);
        if (response == null) return null;
        return gson.fromJson(response, IncomeDetailDatasDataEntity.class);
    }

    /**
     * 功能：我的团队 页面的数据
     *
     * @param token
     * @return
     * @throws Exception
     */
    public MyTeamHomeEntity personcenterMyTeamHome(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/personcenter/partner");
        printResponse(" personcenterGetParterHome", response);
        if (response == null) return null;
        return gson.fromJson(response, MyTeamHomeEntity.class);
    }

    /**
     * 功能：我的团队-分页
     *
     * @param token
     * @param name
     * @param pageIndex
     * @param sort      排序字段(1:注册时间，2:直接收益，3:间接收益)
     * @param order     排序方式: (1:降序，2:升序)
     * @return
     * @throws Exception
     */
    public MyTeamListDatasDataEntity personcenterMyTeamList(String token, String name, String pageIndex, String pageSize, String sort, String order) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "personcenter.partner.pageList");
        map.put("token", token);
        if (name != null) map.put("name", name);
        map.put("pageIndex", pageIndex);
        if (pageSize != null) {
            map.put("pageSize", pageSize); // 默认为10
        }
        if (sort == null) {
            map.put("sort", "1");
        } else {
            map.put("sort", sort);
        }
        if (order == null) {
            map.put("order", "1");
        } else {
            map.put("order", order);
        }
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/personcenter/partner/pageList");
        printResponse(" personcenterMyTeamList", response);
        if (response == null) return null;
        return gson.fromJson(response, MyTeamListDatasDataEntity.class);
    }

    /**
     * 功能：团队详情 页面
     *
     * @param token
     * @param userNumber 用户编码
     * @return
     * @throws Exception
     */
    public MyTeamDetailEntity personcenterMyTeamDetail(String token, String userNumber) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("userId", userNumber);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/personcenter/partner/detail");
        printResponse(" personcenterGetParterHome", response);
        if (response == null) return null;
        return gson.fromJson(response, MyTeamDetailEntity.class);
    }

    /**
     * 功能：我的团队-成员销售记录
     *
     * @param token
     * @param userNumber 用户编码
     * @param pageIndex
     * @return
     * @throws Exception
     */
    public MyTeamSalesRecordDatasDataEntity personcenterMyTeamDetailList(String token, String userNumber, String pageIndex) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        map.put("token", token);
        map.put("userId", userNumber);
        map.put("pageIndex", pageIndex);
        // map.put("pageSize", pageSize); 默认为10
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/personcenter/partner/salesRecordList/3.0");
        printResponse(" personcenterMyTeamDetailList", response);
        if (response == null) return null;
        return gson.fromJson(response, MyTeamSalesRecordDatasDataEntity.class);
    }

    /**
     * 功能：任务-职级任务
     *
     * @param token
     * @return
     * @throws Exception
     */
    public MyLevelDataEntity personcenterMyLevel(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "personcenter.task.level");
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse(" personcenterMyLevel", response);
        if (response == null) return null;
        return gson.fromJson(response, MyLevelDataEntity.class);
    }


    /**
     * 功能：首页机构列表
     * 接口名称 优质平台-黄亚林-已实现
     * 请求类型 post
     * 请求Url /homepage/highQualityPlatform
     *
     * @param token
     * @return
     * @throws Exception
     */
    public HighQualityPlatformEntity homeOrginfo(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        map.put("token", token);
        initSign(map);
        printRequest("homeOrgInfoDatas====", map);
        String response = HttpsUtils.sendPostRequest(map, "/platfrom/highQualityPlatform");
        printResponse("homeOrgInfoDatas====", response);
        if (response == null) return null;
        return gson.fromJson(response, HighQualityPlatformEntity.class);
    }


    /**
     * 功能：账户余额月份收益总计列表
     *
     * @param token
     * @param pageIndex
     * @return
     * @throws Exception
     */
    public AccountIncomeDatasDataEntity accountMyAccountProfixTotalList(String token, String pageIndex) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", "10");// 默认10
        initSign(map);
        printRequest("accountMyAccountProfixTotalList", map);
        String response = HttpsUtils.sendPostRequest(map, "/account/monthProfixTotalList/2.1");
        printResponse(" accountMyAccountProfixTotalList", response);
        if (response == null) return null;
        return gson.fromJson(response, AccountIncomeDatasDataEntity.class);
    }

    /**
     * 功能：首页的底部的今日收益 // 交易动态等
     *
     * @param token
     * @return
     * @throws Exception
     */
    @Deprecated
    public HomePagerBulletinEntity homePagerBulletin(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "homepage.bulletin");
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse(" homePagerBulletin", response);
        if (response == null) return null;
        return gson.fromJson(response, HomePagerBulletinEntity.class);
    }

    /**
     * 功能：动态汇总列表
     *
     * @param token
     * @param pageIndex
     * @return
     * @throws Exception
     */
    public HomePagerDynamicDatasDataEntity homepageEventsPageList(String token, String pageIndex) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        map.put("token", token);
        map.put("pageIndex", pageIndex);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/investRecord/cfplanner/dynamic");
        printResponse(" homepageEventsPageList", response);
        if (response == null) return null;
        return gson.fromJson(response, HomePagerDynamicDatasDataEntity.class);
    }

    /**
     * 功能：财富快报-类别列表
     *
     * @param token
     * @return
     * @throws Exception
     */
    public WealthNewsTypeDatasDataEntity homePagerWealthNewsTypes(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "homepage.wealthNews.types");
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse(" homePagerWealthNewsTypes", response);
        if (response == null) return null;
        return gson.fromJson(response, WealthNewsTypeDatasDataEntity.class);
    }

    /**
     * 功能：财富快报-分页
     *
     * @param token
     * @param typeId
     * @param pageIndex
     * @return
     * @throws Exception
     */
    public WealthNewsDatasDataEntity homePagerWealthNewsList(String token, String typeId, String pageIndex) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("pageIndex", pageIndex);
        // map.put("pageSize", pageSize); 默认为10
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/app/newsPageList");
        printResponse(" homePagerWealthNewsList", response);
        if (response == null) return null;
        return gson.fromJson(response, WealthNewsDatasDataEntity.class);
    }


    /**
     * 接口名称 理财产品-机构筛选条件（陈春燕）-已实现
     * 请求类型 get
     * 请求Url /platfrom/platformHead
     * 接口描述 理财产品，机构筛选条件
     * <p/>
     * token	登录状态	string
     *
     * @param token
     * @return
     * @throws Exception
     */
    public PlatFormHeadEntity getplatformHeadData(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        map.put("token", token);


        initSign(map);

        printRequest("getplatformHeadData====", map);
        String response = HttpsUtils.sendPostRequest(map, "/platfrom/platformHead");
        printResponse("getplatformHeadData====", response);
        if (response == null) return null;
        return gson.fromJson(response, PlatFormHeadEntity.class);
    }


    /**
     * 功能：理财产品-产品列表
     * <p/>
     * order	顺序	number	0-升序 1-降序
     * pageIndex	第几页 >=1,默认为1	string
     * pageSize	页面条数，默认为10	string
     * sort	排序	number	1-默认排序 2-年化收益 3-产品期限
     * token	登录令牌	string	必须
     */
    public ProductDatasDataEntity getFinancingProduct(String token, String orgCode, String order, String pageIndex, String pageSize, String sort) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        map.put("token", token);
        if (orgCode != null && orgCode != "") {
            map.put("orgCode", orgCode);
        }
        map.put("order", order);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        map.put("sort", sort);

        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/product/productPageList");
        printResponse("getFinancingProduct", response);
        if (response == null) return null;
        return gson.fromJson(response, ProductDatasDataEntity.class);
    }

    /**
     * 功能: 产品分类统计 v1.1.0  2016/9/19 0019  产品分类统计
     * 请求Url /product/productClassifyStatistics
     * <p/>
     * <p/>
     * cateIdList	产品分类ID列表	string	1-热门产品 2-新手产品 3-img_home_product_time_short 4-img_home_product_high_yield 801-理财师推荐产品 5-稳健收益产品 多个一起查询的时候请使用,分开 如：1,2,3,4,5,801
     * token	登录态	string	查询 801-理财师推荐产品 时必需(投呗端)
     *
     * @return
     * @throws Exception
     */
    public ProductClassifyStatisticsEntity productClassifyStatistics(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/product/productClassifyStatistics");
        printRequest("productClassifyStatistics", map);
        printResponse(" productClassifyStatistics", response);
        if (response == null) return null;
        return gson.fromJson(response, ProductClassifyStatisticsEntity.class);
    }

    /**
     * 功能:  3.4.8 产品分类列表 v1.1.0
     * 请求类型 get
     * 请求Url /product/productClassifyPageList
     * <p/>
     * 理财-产品分类列表-分页排序
     *
     * @param token     投呗(801-理财师推荐产品 ) 猎才大师 必需
     * @param cateId    产品分类id 	number	1-热门产品 2-新手产品 3-img_home_product_time_short 4-img_home_product_high_yield 5-稳健收益产品 801-理财师推荐产品 901-首投标 902-复投标
     * @param orgCode   机构编码	  string	非必需 默认查询全部机构
     * @param order     0-升序 1-降序
     * @param pageIndex pageIndex
     * @param pageSize  pageSize
     * @param sort      1-默认排序 2-年化收益 3-产品期限
     * @return
     * @throws Exception
     */
    public ProductDatasDataEntity productClassifyPageList(String token, String cateId, String orgCode, String order, String pageIndex, String pageSize, String sort) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        map.put("token", token);
        if (orgCode != null && orgCode != "") {
            map.put("orgCode", orgCode);
        }
        map.put("order", order);
        map.put("cateId", cateId);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        map.put("sort", sort);

        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/product/productClassifyPageList");
        printResponse("getFinancingProduct", response);
        if (response == null) return null;
        return gson.fromJson(response, ProductDatasDataEntity.class);
    }


    /**
     * 接口名称 4.0 机构列表 v4.0
     * 请求类型 get
     * 请求Url  /platfrom/orgPageList/4.0
     * 接口描述 机构列表 v4.0
     */
    public OrgInfoDatasDataEntity getOrgInfoListDatas(String pageIndex, String pageSize) throws Exception {
        Map<String, String> map = new HashMap<String, String>();


        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);

        initSign(map);
        printRequest("getOrgInfoListDatas====", map);
        String response = HttpsUtils.sendPostRequest(map, "/platfrom/orgPageList/4.0");
        printResponse("getOrgInfoListDatas====", response);
        if (response == null) return null;
        return gson.fromJson(response, OrgInfoDatasDataEntity.class);
    }

    /**
     * 接口详情 (id: 344)
     * 接口名称 精选平台-貅比特APP_V2.2.0
     * 请求类型 post
     * 请求Url /platfrom/choicenessPlatform
     *
     * @throws Exception
     */
    public OrgInfoDatasDataEntity getOrgInfoListDatas() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        initSign(map);
        printRequest("getOrgInfoListDatas====", map);
        String response = HttpsUtils.sendPostRequest(map, "/platfrom/choicenessPlatform");
        printResponse("getOrgInfoListDatas====", response);
        if (response == null) return null;
        return gson.fromJson(response, OrgInfoDatasDataEntity.class);
    }


//    /**
//     * 功能：首页 -热门产品
//     *
//     * @param token
//     * @param pageIndex
//     * @param pageSize
//     * @throws Exception
//     */
//    public ProductDatasDataEntity getHomeHotProduct(String token, String pageSize, String pageIndex) throws Exception {
//        Map<String, String> map = new HashMap<String, String>();
//
//        map.put("token", token);
//        map.put("pageIndex", pageIndex);
//        map.put("pageSize", pageSize);
//
//        initSign(map);
//        String response = HttpsUtils.sendPostRequest(map, "/product/hotProduct");
//        printResponse("homeHotProducts", response);
//        if (response == null) return null;
//        return gson.fromJson(response, ProductDatasDataEntity.class);
//    }


    /**
     * 功能:机构信息及详情 - 黄亚林(已实现)
     * <p/>
     *
     * @param token
     * @param orgNo orgNo	平台编码	string	必需
     * @return
     * @throws Exception
     */
    public PlatFormDetailEntity getPlatFormDetailData(String token, String orgNo) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("orgNo", orgNo);
        initSign(map);

        printRequest("getPlatFormDetailData====", map);
        String response = HttpsUtils.sendPostRequest(map, "/platfrom/detail");
        printResponse("getPlatFormDetailData====", response);
        if (response == null) return null;
        return gson.fromJson(response, PlatFormDetailEntity.class);
    }


    /**
     * 功能:平台在售的产品
     *
     * @param orgNo
     * @param pageSize
     * @param pageIndex
     * @return
     * @throws Exception
     */
    public ProductDatasDataEntity platformSaleProducts(String token, String orgNo, String pageSize, String pageIndex) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("orgNo", orgNo);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);

        initSign(map);
        printRequest("platformSaleProducts====", map);
        String response = HttpsUtils.sendPostRequest(map, "/platfrom/platformSaleProducts");
        printResponse("platformSaleProducts", response);
        if (response == null) return null;
        return gson.fromJson(response, ProductDatasDataEntity.class);
    }


    /**
     * 功能：产品购买记录
     *
     * @param token
     * @return
     * @throws Exception
     */
    public ProductBuyRecurdsEntity getProductBuyRecurdsPageList(String token, String productId, int pageIndex, int pageSize) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "lcq.product.productBuyRecurdsPageList");
        map.put("token", token);
        map.put("productId", productId);
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put("pageSize", String.valueOf(pageSize));

        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse("getProductBuyRecurdsPageList", response);
        if (response == null) return null;
        return gson.fromJson(response, ProductBuyRecurdsEntity.class);
    }

    /**
     * 功能：理财产品-设置理财师取消推荐
     *
     * @param token
     * @param productId
     * @return
     * @throws Exception
     */
    public LoginResponseEntity productCancleRecommend(String token, String productId) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        map.put("token", token);
        map.put("productId", productId);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/product/cancelRecommend");
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 功能：理财产品-设置理财师推荐
     *
     * @param token
     * @param productId
     * @return
     * @throws Exception
     */
    public LoginResponseEntity productSetRecommend(String token, String productId) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        map.put("token", token);
        map.put("productId", productId);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/product/recommend");
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 功能：理财产品-理财师佣金计算
     *
     * 
     * @param productId
     * @param amount    销售金额
     * @return
     * @throws Exception
     */
    public CalculatorEntity productProfitCalculate(String productId, String amount, String day) throws Exception {

        Map<String, String> map = new HashMap<String, String>();
        //map.put("method", "lcq.product.profitCalculate");
        if (TextUtil.isEmpty(amount)) {
            amount = "0";
        }
        map.put("productId", productId);
        map.put("day", day);
        map.put("amount", amount);
        //  map.put("rewardRatio", rewardRatio);
        initSign(map);
        printRequest("productProfitCalculate", map);
        String response = HttpsUtils.sendPostRequest(map, "/product/profitCalculate");
        printResponse("productProfitCalculate", response);
        //Log.d(TAG, "ProfitCalculateEntity  ===" + response);
        if (response == null) return null;
        return gson.fromJson(response, CalculatorEntity.class);
    }

    /**
     * 功能：app获取默认配置
     *
     * @return
     * @throws Exception
     */
    public DefaultConfigEntity clientGetDefaultConfig() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/app/default-config");
        printResponse(" clientGetDefaultConfig", response);
        if (response == null) return null;
        return gson.fromJson(response, DefaultConfigEntity.class);
    }

    /**
     * 功能：我的二维码
     *
     * @param token
     * @param
     * @return
     * @throws Exception
     */
    public MyQRCodeEntity getMyQRCode(String token) throws Exception {

        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/invitation/customer/homepage111");
        printResponse("getMyQRCode", response);
        if (response == null) return null;
        return gson.fromJson(response, MyQRCodeEntity.class);
    }


    /**
     * 接口名称 红包列表4.0 v4.0
     * 请求类型 get
     * 请求Url  redPacket/queryRedPacket/4.0
     * 接口描述 红包列表4.0版本
     */
    public RedPacketDatasDataEntity redpaperRedpaperInfo(String type, String pageIndex, String pageSize) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", type);
        map.put("pageIndex", pageIndex == null ? "1" : pageIndex);
        map.put("pageSize", pageSize == null ? "10" : pageSize);

        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/redPacket/queryRedPacket/4.0");
        printResponse("redpaperRedpaperInfo", response);
        if (response == null) return null;
        return gson.fromJson(response, RedPacketDatasDataEntity.class);
    }

    /**
     * 功能： 红包发送
     *
     * @param token       参数名	    类型	          描述	       是否必须
     * @param rid         token 	   string 	用户的token 	是
     * @param userMobiles money	   string 	红包金额 	是
     * @return userMobiles    lists 	用户手机列表 	是
     * @throws Exception
     */

    public LoginResponseEntity redpaperSendRedpaper(String token, String rid, String userMobiles) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("rid", rid);
        map.put("userMobiles", userMobiles);
        initSign(map);
        printRequest("redpaperSendRedpaper", map);
        String response = HttpsUtils.sendPostRequest(map, "/redPacket/sendRedPacket");
        printResponse("redpaperSendRedpaper", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 功能: 获取 报表记录
     *
     * @param token
     * @param pageIndex
     * @param pageSize
     * @return BillDeclarationDatasDataEntity
     * @throws Exception x
     */
    public BillDeclarationDatasDataEntity billDeclaration(String token, String pageIndex, String pageSize) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        initSign(map);
        printRequest("billDeclaration", map);
        String response = HttpsUtils.sendPostRequest(map, "/investRecord/cfplanner/unRecordInvestList");
        printResponse("billDeclaration", response);
        if (response == null) return null;
        return gson.fromJson(response, BillDeclarationDatasDataEntity.class);
    }

    /**
     * 功能 :  新增报单-陈衡-已实现
     * 请求类型 get
     * 请求Url investRecord/cfplanner/addUnRecordInvest
     * <p/>
     *
     * @param investAmt           投资金额	number
     * @param investImg           投资截图	string
     * @param mobile              客户手机	string
     * @param name                客户姓名	string
     * @param platfrom            投资平台	string
     * @param platfromName        平台名称	string
     * @param productName         产品名称	string
     * @param investTime          投资时间
     * @param productDeadLine     产品期限
     * @param productDeadLineType 产品期限类型 1=天|2=月|3=年
     * @param token               登录token	string
     */
    public LoginResponseEntity addUnRecordInvest(String token, String investAmt, String investImg, String mobile, String name, String platfrom, String platfromName, String productName, String investTime, String productDeadLine, String productDeadLineType) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("investAmt", investAmt);
        map.put("investImg", investImg);
        map.put("mobile", mobile);
        map.put("name", name);
        map.put("platfrom", platfrom);
        map.put("platfromName", platfromName);
        map.put("productName", productName);
        map.put("investTime", investTime);
        map.put("productDeadLine", productDeadLine);
        map.put("productDeadLineType", productDeadLineType);
        initSign(map);
        printRequest("addUnRecordInvest", map);
        String response = HttpsUtils.sendPostRequest(map, "/investRecord/cfplanner/addUnRecordInvest");
        printResponse("addUnRecordInvest", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }


    /**
     * 功能: 选择机构列表
     * <p/>
     * 请求Url   platfrom/selectPlatfrom
     *
     * @throws Exception
     */
    public AccountManageDatasDataEntity selectPlatfrom(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        initSign(map);
        printRequest("selectPlatfrom", map);
        String response = HttpsUtils.sendPostRequest(map, "/platfrom/selectPlatfrom");
        printResponse("selectPlatfrom", response);
        if (response == null) return null;
        return gson.fromJson(response, AccountManageDatasDataEntity.class);

    }
/* ------------V1.2.1---------------*/

    /**
     * 功能: 产品推荐选择列表
     * 请求Url  /product/recommendChooseList
     *
     * @param token       '
     * @param productId   产品id	string
     * @param searchValue 查询内容（姓名或手机号）	string	非必需
     * @return RecommendproductDatasDataEntity
     * @throws Exception
     */
    public RecommendproductDatasDataEntity recommendChooseList(String token, String productId, String searchValue) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("productId", productId);
        map.put("searchValue", searchValue);
        initSign(map);
        printRequest("recommendChooseList", map);
        String response = HttpsUtils.sendPostRequest(map, "/product/recommendChooseList");
        printResponse("recommendChooseList", response);
        if (response == null) return null;
        return gson.fromJson(response, RecommendproductDatasDataEntity.class);
    }


    /**
     * * 功能： 产品选择推荐
     * 接口名称 3.5.1 产品选择推荐
     * 请求类型 get
     * 请求Url /product/recommendByChoose
     * 接口描述 产品选择推荐 可指定客户推荐产品或者全部推荐
     *
     * @param token        登录状态	string
     *                     param ifAllRecommend 是否全部推荐	number	0-是 1-否
     * @param productId    产品id	string
     * @param userIdString 用户userIdString	string	多个 用,（英文逗号）分割 若全部推荐为0-是 可不传
     * @return LoginResponseEntity
     * @throws Exception
     */
    public LoginResponseEntity recommendByChoose(String token, String productId, String userIdString) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        //   map.put("ifAllRecommend", ifAllRecommend);
        map.put("productId", productId);
        map.put("userIdString", userIdString);
        initSign(map);
        printRequest("recommendByChoose", map);
        String response = HttpsUtils.sendPostRequest(map, "/product/recommendByChoose");
        printResponse("recommendByChoose", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 功能:机构推荐选择列表
     * 请求Url  /platfrom/recommendChooseList
     *
     * @param token       '
     * @param orgCode     机构编码
     * @param searchValue 查询内容（姓名或手机号）	string	非必需
     * @return RecommendproductDatasDataEntity
     * @throws Exception
     */
    public RecommendproductDatasDataEntity platfromRecommendChooseList(String token, String orgCode, String searchValue) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("orgCode", orgCode);
        map.put("searchValue", searchValue);
        initSign(map);
        printRequest("platfromRecommendChooseList", map);
        String response = HttpsUtils.sendPostRequest(map, "/platfrom/recommendChooseList");
        printResponse("platfromRecommendChooseList", response);
        if (response == null) return null;
        return gson.fromJson(response, RecommendproductDatasDataEntity.class);
    }


    /**
     * 接口名称 3.3.6 机构选择推荐
     * 请求类型 get
     * 请求Url /platfrom/recommendByChoose
     * 接口描述 机构选择推荐
     *
     * @param token        登录状态	string
     *                     param //ifAllRecommend 是否全部推荐	number	0-是 1-否
     * @param orgCode      机构编码	string
     * @param userIdString 用户userIdString	string	多个 用,（英文逗号）分割 若全部推荐为0-是 可不传
     * @return LoginResponseEntity
     * @throws Exception
     */
    public LoginResponseEntity platfromRecommendByChoose(String token, String orgCode, String userIdString) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        // map.put("ifAllRecommend", ifAllRecommend);
        map.put("orgCode", orgCode);
        map.put("userIdString", userIdString);
        initSign(map);
        printRequest("recommendByChoose", map);
        String response = HttpsUtils.sendPostRequest(map, "/platfrom/recommendByChoose");
        printResponse("recommendByChoose", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }
/*---------------------------------------------- V2.0新增接口-----------------------------------------------------------------*/

    /**
     * 功能：平台活动列表（分页）  2016/11/4 0004  V2.0 待调试
     * 请求类型 get
     * 请求Url /activity/platform/pageList
     *
     * @param token            token
     * @param activityPlatform 平台名称	string
     * @param pageIndex        第几页	number
     *                         //* @param pageSize         页面大小	number
     * @throws Exception
     */
    public ActivityPlatformPageListDatasDataEntity activityPlatformPagaList(String token, String activityPlatform, String pageIndex) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("activityPlatform", activityPlatform);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", "10");
        initSign(map);
        printRequest("activityPlatformPagaList", map);
        String response = HttpsUtils.sendPostRequest(map, "/activity/platform/pageList");
        printResponse("activityPlatformPagaList", response);
        if (response == null) return null;
        return gson.fromJson(response, ActivityPlatformPageListDatasDataEntity.class);
    }

    /**
     * 功能：平台活动列表（分页）  2016/11/4 0004  V2.0 待调试
     * 请求类型 get
     * 请求Url /activity/platform/pageList
     *
     * @param activityPlatform 平台名称	string
     *                         <p>
     *                         //* @param pageSize         页面大小	number
     * @throws Exception
     */
    public ActivityPlatformPageListDatasDataEntity activityPlatformList(String activityPlatform) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        map.put("activityPlatform", activityPlatform);
        initSign(map);
        printRequest("activityPlatformPagaList", map);
        String response = HttpsUtils.sendPostRequest(map, "/activity/platform/list");
        printResponse("activityPlatformPagaList", response);
        if (response == null) return null;
        return gson.fromJson(response, ActivityPlatformPageListDatasDataEntity.class);
    }

    /**
     * 功能：接口名称 平台活动（封面）2016/11/4 0004  V2.0
     *
     * @param token token
     * @throws Exception
     */
    public ActivityPlatformDatasDataEntity activityPlatform(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/activity/platform");
        printResponse(" activityPlatform", response);
        if (response == null) return null;
        return gson.fromJson(response, ActivityPlatformDatasDataEntity.class);
    }


    /**
     * 功能：接口名称 课程列表 2016/11/4 0004  V2.0
     * 请求类型 get
     * 请求Url classroom/queryClassroomList
     */
    public ClassRoomDatasDataEntity classroomQueryClassroomList(String token, String pageIndex) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", "10");
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/classroom/queryClassroomList");
        printResponse(" classroomQueryClassroomList", response);
        if (response == null) return null;
        return gson.fromJson(response, ClassRoomDatasDataEntity.class);
    }


    /**
     * 接口名称 热推top10
     * 请求Url /api/product/hotRecommendProductListTop/2.0.1
     * 接口描述 热推产品TOP10
     *
     * @throws Exception
     */
    public ProductDatasDataEntity hotRecommendProductListTop(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        map.put("token", token);
        initSign(map);
        printRequest("hotRecommendProductListTop", map);
        String response = HttpsUtils.sendPostRequest(map, "/product/hotRecommendProductListTop/2.0.1");
        printResponse("hotRecommendProductListTop", response);
        if (response == null) return null;
        return gson.fromJson(response, ProductDatasDataEntity.class);
    }

    /**
     * 功能：我的账户 余额
     * 2.0.1版本加入
     *
     * @throws Exception
     */
    public AccountIncomeHomeEntity accountMyAccountIncomeHome(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/account/accountBalance");
        printResponse(" accountMyAccountHome", response);
        if (response == null) return null;
        return gson.fromJson(response, AccountIncomeHomeEntity.class);
    }

    /**
     * 接口名称 全部标的列表 v-2.0.1
     * 请求类型 get
     * 请求Url /product/productTypeList/2.0.1
     *
     * @param token      token
     * @param cateIdList cateIdList 产品分类ID列表	string	非必需 2-新手产品 901-首投标 902-复投标 多个一起查询的时候请使用,
     *                   分开 如：2,901,902 不传时则查询所有的（2,901,902）产品分类
     * @throws Exception
     */
    public ProductClassifyStatisticsEntity productTypeList(String token, String cateIdList) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("cateIdList", cateIdList);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/product/productTypeList/2.0.1");
        printRequest("productClassifyStatistics", map);
        printResponse(" productClassifyStatistics", response);
        if (response == null) return null;
        return gson.fromJson(response, ProductClassifyStatisticsEntity.class);
    }

    /**
     * 接口名称 邀请投资客户记录统计-钟灵-已完成
     * <p>
     * 请求Url invitation/customerRecordStatistics
     */
    public InviteCusRecordStatisticsEntity customerRecordStatistics(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        initSign(map);
        printRequest("customerRecordStatistics", map);
        String response = HttpsUtils.sendPostRequest(true, map, "/invitation/customerRecordStatistics");
        printResponse("customerRecordStatistics", response);
        if (response == null) return null;
        return gson.fromJson(response, InviteCusRecordStatisticsEntity.class);
    }

    /**
     * 接口名称 邀请投资客户记录列表-钟灵-已完成
     * <p>
     * 请求Url invitation/customerRecord
     *
     * @param token     token
     * @param pageIndex 第几页 >=1,默认为1	string	第几页 >=1,默认为1
     * @param pageSize  pageSize	页面记录数，默认为10	string	页面记录数，默认为10
     */
    public InviteCustomHisEntity customerRecord(String token, String pageIndex, String pageSize) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        initSign(map);
        printRequest("/invitation/customerRecord", map);
        String response = HttpsUtils.sendPostRequest(true, map, "/invitation/customerRecord");
        printResponse("/invitation/customerRecord", response);
        if (response == null) return null;
        return gson.fromJson(response, InviteCustomHisEntity.class);
    }

    /**
     * 接口名称 邀请理财师记录统计-钟灵-已完成
     * 请求类型 get
     * 请求Url invitation/cfplannerRecordStatistics
     *
     * @param token token
     * @throws Exception
     */
    public InviteCfpRecordStatisticEntity cfplannerRecordStatistics(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        initSign(map);
        printRequest("cfplannerRecordStatistics", map);
        String response = HttpsUtils.sendPostRequest(true, map, "/invitation/cfplannerRecordStatistics");
        printResponse("cfplannerRecordStatistics", response);
        if (response == null) return null;
        return gson.fromJson(response, InviteCfpRecordStatisticEntity.class);
    }

    /**
     * 接口名称 邀请理财师记录列表-钟灵-已完成
     * 请求类型 get
     * 请求Url invitation/cfplannerRecord
     *
     * @param token     token
     * @param pageIndex 第几页 >=1,默认为1	string	第几页 >=1,默认为1
     * @param pageSize  pageSize	页面记录数，默认为10	string	页面记录数，默认为10
     */
    public InviteCustomHisEntity cfplannerRecord(String token, String pageIndex, String pageSize) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        initSign(map);
        printRequest("/invitation/customerRecord", map);
        String response = HttpsUtils.sendPostRequest(true, map, "/invitation/cfplannerRecord");
        printResponse("/invitation/customerRecord", response);
        if (response == null) return null;
        return gson.fromJson(response, InviteCustomHisEntity.class);
    }
/* ---------------------------------------------------  V2.0.2------------------------------------------------------------------------*/

    /**
     * 功能：投资统计
     *
     * @param token
     * @param dateType 时间类别: 1:年；2:季度；3:月；4:日  5:周
     * @param date     时间格式（时间类别为1-3的time对应该时间区间第一天日期）:2015-12-24 年:2015-01-01
     *                 季度:一季度2015-01-01 二季度 2015-04-01 …月: 2015-09-01
     * @throws Exception
     */
    public CfpSaleStatEntity cfpSaleStatistic(String token, String dateType, String date) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("dateType", dateType);
        map.put("date", date);
        initSign(map);
        printRequest(" cfpSaleStatistic", map);
        String response = HttpsUtils.sendPostRequest(map, "/personcenter/partner/monthSaleStatistics/3.0");
        printResponse(" cfpSaleStatistic", response);
        if (response == null) return null;
        return gson.fromJson(response, CfpSaleStatEntity.class);
    }

    /**
     * 功能：团队本月销售记录列表-钟灵-已完成
     *
     * @param token
     * @param dateType  时间类别: 空表示全部 ;1:年；2:季度；3:月；4:日 (默认表示当月) 5周
     * @param date      时间格式（时间类别为1-3的time对应该时间区间第一天日期）:2015-12-24 年:2015-01-01
     *                  季度:一季度2015-01-01 二季度 2015-04-01 …月: 2015-09-01
     * @param pageIndex
     * @return
     * @throws Exception
     */
    public CfpSaleListItemDatasDataEntity cfpSalePageList(String token, String dateType, String date, String pageIndex) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("dateType", dateType);
        map.put("date", date);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", "10");
        initSign(map);
        printRequest(" cfpSalePageList", map);
        String response = HttpsUtils.sendPostRequest(map, "/personcenter/partner/monthSaleList/3.0");
        printResponse(" cfpSalePageList", response);
        if (response == null) return null;
        return gson.fromJson(response, CfpSaleListItemDatasDataEntity.class);
    }


    /**
     * 接口名称 是否有未读的资讯或活动-2.0.2
     * 请求类型 get
     * 请求Url /app/newsandactivity/readed/2.0.2
     * 接口描述 展开
     * newsReaded：资讯状态，1：有未读、0：没有未读 activityReaded ：活动状态，1：有未读、0：没有未读 liecaiReaded：理财状态，1：有未读、0：没有未读（资讯和活动都...
     *
     * @param token token
     * @return LiecaiHasUnReadActivitiesEntity
     * @throws Exception
     */
    public LiecaiHasUnReadActivitiesEntity isHasUnReadLiecaiAct(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);

        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/app/newsandactivity/readed/2.0.2");
        printResponse(" liecaihasunreadactivitiesentity", response);
        if (response == null) return null;
        return gson.fromJson(response, LiecaiHasUnReadActivitiesEntity.class);
    }

    /**
     * 接口名称 新手任务首页-钟灵-已完成
     * 请求类型 get
     * 请求Url cfpnewcomertask/homepage
     */
    public FreshMissionEntity freshMission(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);

        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/cfpnewcomertask/homepage/2.2");
        printResponse(" FreshMissionEntity", response);
        if (response == null) return null;
        return gson.fromJson(response, FreshMissionEntity.class);
    }


    /**
     * 接口名称 理财师新手福利-钟灵-已完成
     * 请求类型 get
     * 请求Url  cfpnewcomertask/newcomerWelfare
     */
    public NewComerWelfarEntity newComerWelfare(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);

        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/cfpnewcomertask/newcomerWelfare");
        printResponse(" NewComerWelfarEntity", response);
        if (response == null) return null;
        return gson.fromJson(response, NewComerWelfarEntity.class);
    }


    /**
     * 新手任务 完成任务
     *
     * @param taskType 1邀请客户，2邀请理财师，3推荐产品，4推荐平台，5派发红包，6查看收益
     */
    public LoginResponseEntity cfpNewTaskFinish(String token, String taskType) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("taskType", taskType);
        initSign(map);
        printRequest(" cfpNewTaskFinish", map);
        String response = HttpsUtils.sendPostRequest(map, "/cfpnewcomertask/finishTask");
        printResponse(" cfpNewTaskFinish", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 新手任务 领取奖励
     *
     * @param taskType 1邀请客户，2邀请理财师，3推荐产品，4推荐平台，5派发红包，6查看收益
     */
    public LoginResponseEntity cfpNewTaskReward(String token, String taskType) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("taskType", taskType);
        initSign(map);
        printRequest(" cfpNewTaskReward", map);
        String response = HttpsUtils.sendPostRequest(map, "/cfpnewcomertask/receiveTaskReward");
        printResponse(" cfpNewTaskReward", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 接口名称 V2.1.0_貅比特资金明细_账户余额
     * 请求类型 get
     * 请求Url  account/getLieCaiBalance
     *
     * @param token
     * @return
     * @throws Exception
     */
    public LieCaiBalanceEntity getLieCaiBalance(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        initSign(map);
        printRequest(" getLieCaiBalance", map);
        String response = HttpsUtils.sendPostRequest(map, "/account/getLieCaiBalance");
        printResponse(" getLieCaiBalance", response);
        if (response == null) return null;
        return gson.fromJson(response, LieCaiBalanceEntity.class);
    }

    /**
     * 接口名称 V2.1.0理财大师资金明细-收支明细
     * 请求类型 get
     * 请求Url account/queryIncomeAndOutDetail
     *
     * @param token
     * @param pageIndex
     * @param pageSize
     * @param typeValue 收支类型(0=全部1=收入|2=支出)
     * @return
     * @throws Exception
     */
    public IncomeAndOutDetailDatasDataEntity queryIncomeAndOutDetail(String token, String pageIndex, String pageSize, String typeValue) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        map.put("token", token);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        map.put("typeValue", typeValue);

        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/account/queryIncomeAndOutDetail");
        printResponse("queryIncomeAndOutDetail", response);
        if (response == null) return null;
        return gson.fromJson(response, IncomeAndOutDetailDatasDataEntity.class);
    }

    /**
     * 接口名称 首页产品弹出窗--2.1.0
     * 请求类型 get
     * 请求Url  /api/homepage/product/opening/2.1.0
     * 接口描述 首页产品弹出窗
     *  传入参数还未确定
     *
     * @return
     * @throws Exception
     */
    public HomepageNewActivityEntity getHomepageNewActivity() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        initSign(map);
        printRequest(" getHomepageNewActivity", map);
        String response = HttpsUtils.sendPostRequest(map, "/homepage/product/opening/2.1.0");
        printResponse(" getHomepageNewActivity", response);
        if (response == null) return null;
        return gson.fromJson(response, HomepageNewActivityEntity.class);
    }
/*  -----------------------------------------V2.2.0---------------------------------------------*/

    /**
     * 接口详情 (id: 337)
     * 接口名称 周佣榜-排行榜-陈衡-已实现
     * 请求类型 get
     * 请求Url act/rankList/zyb/rank
     *
     * @return
     * @throws Exception
     */
    public RankWeeklyCommsionDatasEntity getRankWeeklyCommission(int pageIndex, int pageSize) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageIndex", pageIndex + "");
        map.put("pageSize", pageSize + "");
        initSign(map);
        printRequest(" getRankWeeklyCommission", map);
        String response = HttpsUtils.sendPostRequest(map, "/act/rankList/zyb/rank");
        printResponse(" getRankWeeklyCommission", response);
        if (response == null) return null;
        return gson.fromJson(response, RankWeeklyCommsionDatasEntity.class);
    }

    public MyRankWeeklyCommsionDataEntity getMyRankWeeklyCommission() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        initSign(map);
        printRequest(" getRankWeeklyCommission", map);
        String response = HttpsUtils.sendPostRequest(map, "/act/rankList/zyb/myRank");
        printResponse(" getRankWeeklyCommission", response);
        if (response == null) return null;
        return gson.fromJson(response, MyRankWeeklyCommsionDataEntity.class);
    }

    /**
     * 接口详情 (id: 337)
     * 接口名称 leader奖励-排行榜-陈衡-未实现
     * 请求类型 get
     * 请求Url act/rankList/tdjl/rank
     *
     * @return
     * @throws Exception
     */
    public RankWeeklyCommsionDatasEntity getRankLeaderCommission(int pageIndex, int pageSize) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageIndex", pageIndex + "");
        map.put("pageSize", pageSize + "");
        initSign(map);
        printRequest(" getRankWeeklyCommission", map);
        String response = HttpsUtils.sendPostRequest(map, "/act/rankList/tdjl/rank");
        printResponse(" getRankWeeklyCommission", response);
        if (response == null) return null;
        return gson.fromJson(response, RankWeeklyCommsionDatasEntity.class);
    }

    /**
     * 接口详情 (id: 338)
     * 接口名称 leader奖励-我的排名-陈衡-未实现
     * 请求类型 get
     * 请求Url act/rankList/tdjl/myRank
     *
     * @return
     * @throws Exception
     */
    public MyRankWeeklyCommsionDataEntity getMyRankLeaderCommission() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        initSign(map);
        printRequest(" getRankWeeklyCommission", map);
        String response = HttpsUtils.sendPostRequest(map, "/act/rankList/tdjl/myRank");
        printResponse(" getRankWeeklyCommission", response);
        if (response == null) return null;
        return gson.fromJson(response, MyRankWeeklyCommsionDataEntity.class);
    }

    /**
     * 接口名称 V2.2.0团队leader奖励-成员贡献明细
     * 请求类型 get
     * 请求Url  /personcenter/partner/contribuPageList
     *
     * @param token
     * @param pageIndex 第几页 >=1,默认为1
     * @param pageSize  页面记录数，默认为10
     * @return
     * @throws Exception
     */
    public ContribuPageListDatasDataEntity getContribuPageList(String token, String pageIndex, String pageSize) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        map.put("token", token);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);

        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/personcenter/partner/contribuPageList");
        printResponse("getContribuPageList", response);
        if (response == null) return null;
        return gson.fromJson(response, ContribuPageListDatasDataEntity.class);
    }

    /**
     * 接口名称 V2.2.0团队leader奖励-直属理财师团队
     * 请求类型 get
     * 请求Url  /personcenter/partner/directCfpPageList
     *
     * @param token
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    public DirectCfpPageListDatasDataEntity getDirectCfpPageList(String token, String pageIndex, String pageSize) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        map.put("token", token);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);

        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/personcenter/partner/directCfpPageList");
        printResponse("getdirectCfpPageList", response);
        if (response == null) return null;
        return gson.fromJson(response, DirectCfpPageListDatasDataEntity.class);
    }

    /**
     * 接口名称 V2.2.0团队leader奖励-累计奖励
     * 请求类型 get
     * 请求Url  /personcenter/partner/leaderProfit
     *
     * @param token
     * @return
     * @throws Exception
     */
    public LeaderProfitDataEntity getLeaderProfitData(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        map.put("token", token);

        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/personcenter/partner/leaderProfit");
        printResponse("getLeaderProfitData", response);
        if (response == null) return null;
        return gson.fromJson(response, LeaderProfitDataEntity.class);
    }

    /**
     * 接口名称 V2.2.0判断理财师leader奖励满足状态
     * 请求类型 get
     * 请求Url /personcenter/partner/leaderProfitStatus
     */
    public LeaderProfitStatusEntity getLeaderProfitStatus() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        initSign(map);
        printRequest("getLeaderProfitStatus", map);
        String response = HttpsUtils.sendPostRequest(map, "/personcenter/partner/leaderProfitStatus");
        printResponse("getLeaderProfitStatus", response);
        if (response == null) return null;
        return gson.fromJson(response, LeaderProfitStatusEntity.class);
    }

    /**
     * 接口名称 是否新用户-陈衡-已实现
     * 请求类型 get
     * 请求Url  /user/isNew
     */
    public UserIsNewEntity getUseIsNew(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        if (token != null) {
            map.put("token", token);
        }
        initSign(map);
        printRequest("getUseIsNew", map);
        String response = HttpsUtils.sendPostRequest(map, "/user/isNew");
        printResponse("getUseIsNew", response);
        if (response == null) return null;
        return gson.fromJson(response, UserIsNewEntity.class);
    }

    /**
     * 接口名称 首页职级弹窗提醒
     * 请求类型 post
     * 请求Url  cim/crmcfplevelrecordtemp/cfpLevelWarning
     * 接口描述 首页职级弹窗提醒
     *
     * @param token
     * @return
     * @throws Exception
     */
    public CfpLevelWarningEntity getCfpLevelWarning(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        if (token != null) {
            map.put("token", token);
        }
        initSign(map);
        printRequest("getCfpLevelWarning", map);
        String response = HttpsUtils.sendPostRequest(map, "/cim/crmcfplevelrecordtemp/cfpLevelWarning");
        printResponse("getCfpLevelWarning", response);
        if (response == null) return null;
        return gson.fromJson(response, CfpLevelWarningEntity.class);
    }


    /**
     * 接口名称 职级收入计算器-基本数据
     * 请求类型 get
     * 请求Url  crm/cfpcommon/feeCalBaseData
     *
     * @return
     * @throws Exception
     */
    public FeeCalBaseDataEntity getFeeCalBaseData() throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/crm/cfpcommon/feeCalBaseData");
        printResponse("getFeeCalBaseData", response);
        if (response == null) return null;
        return gson.fromJson(response, FeeCalBaseDataEntity.class);
    }

    /**
     * 接口名称 是否已绑定的机构 -钟灵 -已实现
     * 请求类型 get
     * 请求Url /platfrom/isBindOrgAcct
     *
     * @param token          登录态
     * @param platFromNumber 机构编码
     * @return
     * @throws Exception
     */
    public IsBindOrgAcctEntity isBindOrgAcct(String token, String platFromNumber) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        if (!TextUtils.isEmpty(token)) {
            map.put("token", token);
        }
        map.put("platFromNumber", platFromNumber);

        initSign(map);
        printRequest("isBindOrgAcct====", map);
        String response = HttpsUtils.sendPostRequest(map, "/platfrom/isBindOrgAcct");
        printResponse("isBindOrgAcct====", response);
        if (response == null) return null;
        return gson.fromJson(response, IsBindOrgAcctEntity.class);
    }

    /**
     * 接口名称 是否存在于第三方平台-钟灵-已实现
     * 请求类型 get
     * 请求Url platfrom/isExistInPlatform
     *
     * @param token          登录态
     * @param platFromNumber 机构编码
     * @return
     * @throws Exception
     */
    public ExistInPlatformEntity isExistInPlatform(String token, String platFromNumber) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        if (!TextUtils.isEmpty(token)) {
            map.put("token", token);
        }
        map.put("platFromNumber", platFromNumber);

        initSign(map);
        printRequest("isExistInPlatform====", map);
        String response = HttpsUtils.sendPostRequest(map, "/platfrom/isExistInPlatform");
        printResponse("isExistInPlatform====", response);
        if (response == null) return null;
        return gson.fromJson(response, ExistInPlatformEntity.class);
    }


    /**
     * 功能 绑定平台账户
     *
     * @param token
     * @param platFromNumber
     * @return
     * @throws Exception
     */
    public LoginResponseEntity bindOrgAcct(String token, String platFromNumber) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("platFromNumber", platFromNumber);

        initSign(map);
        printRequest("bindOrgAcct", map);
        String response = HttpsUtils.sendPostRequest(map, "/platfrom/bindOrgAcct");
        printResponse("bindOrgAcct", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);

    }

    /**
     * 接口名称 获取机构产品详情跳转地址-黄亚林-已完成
     * 请求类型 post
     * 请求Url  /platfrom/getOrgProductUrl
     * 接口描述 模拟..
     *
     * @param token     登录令牌
     * @param orgNo     机构编码
     * @param productId 产品id
     * @return
     * @throws Exception
     */
    public OrgProductUrlEntity getOrgProductUrl(String token, String orgNo, String productId) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        if (!TextUtils.isEmpty(token)) {
            map.put("token", token);
        }
        map.put("orgNo", orgNo);
        map.put("productId", productId);

        initSign(map);
        printRequest("getOrgProductUrl====", map);
        String response = HttpsUtils.sendPostRequest(map, "/platfrom/getOrgProductUrl");
        printResponse("getOrgProductUrl====", response);
        if (response == null) return null;
        return gson.fromJson(response, OrgProductUrlEntity.class);
    }


    /**
     * 接口名称 获取用户中心跳转地址-黄亚林-已完成
     * 请求类型 post
     * 请求Url  /platfrom/getOrgUserCenterUrl
     * 接口描述 模拟地址...
     *
     * @param token 登录令牌
     * @param orgNo 机构编码
     * @return
     * @throws Exception
     */
    public OrgUserCenterUrlEntity getOrgUserCenterUrl(String token, String orgNo) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        if (!TextUtils.isEmpty(token)) {
            map.put("token", token);
        }
        map.put("orgNo", orgNo);

        initSign(map);
        printRequest("getOrgUserCenterUrl====", map);
        String response = HttpsUtils.sendPostRequest(map, "/platfrom/getOrgUserCenterUrl");
        printResponse("getOrgUserCenterUrl====", response);
        if (response == null) return null;
        return gson.fromJson(response, OrgUserCenterUrlEntity.class);
    }

    /**
     * 首页精选产品--4.0.0
     * 请求类型 get
     * 请求Url  /product/selectedProducts/4.0.0
     *
     * @return
     * @throws Exception
     */
    public ProductDatasDataEntity getSelectedProductsList() throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/product/selectedProducts/4.0.0");
        printResponse("getSelectedProductsList", response);
        if (response == null) return null;
        return gson.fromJson(response, ProductDatasDataEntity.class);
    }

    /**
     * 接口名称 貅比特首页热门资讯-4.0.0
     * 请求类型 get
     * 请求Url  /app/newsTop/4.0.0
     * 接口描述 貅比特首页热门资讯 最多显示两个置顶
     */
    public HomeHotNewsEntity getHomeHotNews() throws Exception {
        Map<String, String> map = new HashMap<String, String>();


        initSign(map);
        printRequest("getHomeHotNews====", map);
        String response = HttpsUtils.sendPostRequest(map, "/app/newsTop/4.0.0");
        printResponse("getHomeHotNews====", response);
        if (response == null) return null;
        return gson.fromJson(response, HomeHotNewsEntity.class);
    }

    /**
     * 接口名称 首页理财师发放佣金累计和出单-4.0.0
     * 请求类型 get
     * 请求Url  /homepage/cfp/sysInfo/4.0.0
     * 接口描述 出单按时间排序最近两个月200单
     */
    public HomeCfpBuyInfoEntity getHomeCfpBuyInfo() throws Exception {
        Map<String, String> map = new HashMap<String, String>();


        initSign(map);
        printRequest("getHomeCfpBuyInfo====", map);
        String response = HttpsUtils.sendPostRequest(map, "/homepage/cfp/sysInfo/4.0.0");
        printResponse("getHomeCfpBuyInfo====", response);
        if (response == null) return null;
        return gson.fromJson(response, HomeCfpBuyInfoEntity.class);
    }

    /**
     * 接口名称 4.0 产品筛选列表 v4.0
     * 请求类型 get
     * 请求Url  /product/productPageList/4.0
     * 接口描述 4.0版本 产品筛选列表
     */
    public ProductDatasDataEntity getProductPageList(String deadlineValue
            , String flowRate
            , String ifRookie
            , String order
            , String orgCode
            , String pageIndex
            , String pageSize
            , String sort) throws Exception {

        Map<String, String> map = new HashMap<String, String>();
        map.put("deadlineValue", deadlineValue);
        map.put("flowRate", flowRate);
        map.put("ifRookie", ifRookie);
        map.put("order", order);
        if (!TextUtils.isEmpty(orgCode)) {
            map.put("orgCode", orgCode);
        }
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        map.put("sort", sort);

        initSign(map);
        printRequest("getProductPageList====", map);
        String response = HttpsUtils.sendPostRequest(map, "/product/productPageList/4.0");
        printResponse("getProductPageList====", response);
        if (response == null) return null;
        return gson.fromJson(response, ProductDatasDataEntity.class);
    }


    /**
     * 接口名称 4.0.1 产品筛选统计 v4.0
     * 请求类型 get
     * 请求Url  /product/productPageListStatistics/4.0
     * 接口描述 4.0版本 产品筛选统计
     */
    public ProductPageListStatisticsEntity getProductPageListStatistics(
            String deadlineValue
            , String flowRate
            , String ifRookie
            , String orgCode) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("deadlineValue", deadlineValue);
        map.put("flowRate", flowRate);
        map.put("ifRookie", ifRookie);
        if (!TextUtils.isEmpty(orgCode)) {
            map.put("orgCode", orgCode);
        }

        initSign(map);
        printRequest("getProductPageListStatistics====", map);
        String response = HttpsUtils.sendPostRequest(map, "/product/productPageListStatistics/4.0");
        printResponse("getProductPageListStatistics====", response);
        if (response == null) return null;
        return gson.fromJson(response, ProductPageListStatisticsEntity.class);
    }

    /**
     * 接口名称 V4.0个人中心
     * 请求类型 get
     * 请求Url  /user/personalCenter
     */
    public PersonalCenterEntity getPersonalCenterInfo() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        initSign(map);
        printRequest("getPersonalCenterInfo====", map);
        String response = HttpsUtils.sendPostRequest(map, "/user/personalCenter");
        printResponse("getPersonalCenterInfo====", response);
        if (response == null) return null;
        return gson.fromJson(response, PersonalCenterEntity.class);
    }

    /**
     * 接口名称 4.0 我的投资记录 v4.0
     * 请求类型 get
     * 请求Url  productinvestrecord/myInvestrecord
     * 接口描述 我的投资记录 v4.0
     */
    public InvestRecordEntity getInvestRecord(String investType, String pageIndex, String pageSize) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("investType", investType);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        initSign(map);

        printRequest("getInvestRecord====", map);
        String response = HttpsUtils.sendPostRequest(map, "/productinvestrecord/myInvestrecord");
        printResponse("getInvestRecord====", response);
        if (response == null) return null;
        return gson.fromJson(response, InvestRecordEntity.class);
    }


    /**
     * 接口名称 红包列表数量 4.0 v4.0
     * 请求类型 get
     * 请求Url  edPacket/queryRedPacketCount/4.0
     * 接口描述 红包列表数量 4.0 v4.0
     */
    public RedPacketCountEntity getRedPacketCount() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        initSign(map);

        printRequest("getRedPacketCount====", map);
        String response = HttpsUtils.sendPostRequest(map, "/redPacket/queryRedPacketCount/4.0");
        printResponse("getRedPacketCount====", response);
        if (response == null) return null;
        return gson.fromJson(response, RedPacketCountEntity.class);
    }

    /**
     * 接口名称 我的投资平台--4.0.0
     * 请求类型 get
     * 请求Url  platfrom/investedPlatform/4.0.0
     */
    public InvestedPlatformEntity getInvestedPlatform() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        initSign(map);

        printRequest("getInvestedPlatform====", map);
        String response = HttpsUtils.sendPostRequest(map, "/platfrom/investedPlatform/4.0.0");
        printResponse("getInvestedPlatform====", response);
        if (response == null) return null;
        return gson.fromJson(response, InvestedPlatformEntity.class);
    }


    /**
     * 接口名称 V4.0邀请记录--推荐理财师
     * 请求类型 get
     * 请求Url  /user/invitationCfp
     */
    public InvitationCfgRecordEntity getInvitationCfgRecord(String pageIndex, String pageSize) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        initSign(map);

        printRequest("getInvitationCfgRecord====", map);
        String response = HttpsUtils.sendPostRequest(map, "/user/invitationCfp");
        printResponse("getInvitationCfgRecord====", response);
        if (response == null) return null;
        return gson.fromJson(response, InvitationCfgRecordEntity.class);
    }


    /**
     * 接口名称 V4.0邀请记录--邀请客户
     * 请求类型 get
     * 请求Url  /user/invitationInvestor
     */
    public InvitationCustomerRecordEntity getInvitationCustomerRecord(String pageIndex, String pageSize) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        initSign(map);

        printRequest("getInvitationCustomerRecord====", map);
        String response = HttpsUtils.sendPostRequest(map, "/user/invitationInvestor");
        printResponse("getInvitationCustomerRecord====", response);
        if (response == null) return null;
        return gson.fromJson(response, InvitationCustomerRecordEntity.class);
    }


    /**
     * 接口名称 V4.0邀请记录-统计数量
     * 请求类型 get
     * 请求Url  /user/invitationNum
     */
    public InvitationNumEntity getInvitationNum() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        initSign(map);

        printRequest("getInvitationNum====", map);
        String response = HttpsUtils.sendPostRequest(map, "/user/invitationNum");
        printResponse("getInvitationNum====", response);
        if (response == null) return null;
        return gson.fromJson(response, InvitationNumEntity.class);
    }

    /**
     * 接口名称 v4.0个人品牌推广
     * 请求类型 get
     * 请求Url  /user/brandPromotion
     */
    public BrandPromotionEntity getBrandPromotion(String type) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("type", type);
        initSign(map);

        printRequest("getBrandPromotion====", map);
        String response = HttpsUtils.sendPostRequest(map, "/user/brandPromotion");
        printResponse("getBrandPromotion====", response);
        if (response == null) return null;
        return gson.fromJson(response, BrandPromotionEntity.class);
    }

    /**
     * 接口名称 V4.1.1出单喜报
     * 请求类型 get
     * 请求Url  personcenter/goodTrans
     */
    public GoodTransEntity getGoodTrans(String billId) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("billId", billId);
        initSign(map);

        printRequest("getGoodTrans====", map);
        String response = HttpsUtils.sendPostRequest(map, "/personcenter/goodTrans");
        printResponse("getGoodTrans====", response);
        if (response == null) return null;
        return gson.fromJson(response, GoodTransEntity.class);
    }


    /**
     * 接口名称 V4.1.1往期喜报
     * 请求类型 get
     * 请求Url  personceter/queryOldGoodTrans
     * sort 排序 string 1.按金额 2.按时间
     */
    public OldGoodTransEntity getOldGoodTrans(String pageIndex, String sort) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageSize", "10");
        map.put("pageIndex", pageIndex);
        map.put("sort", sort);

        initSign(map);

        printRequest("getOldGoodTrans====", map);
        String response = HttpsUtils.sendPostRequest(map, "/personcenter/queryOldGoodTrans");
        printResponse("getOldGoodTrans====", response);
        if (response == null) return null;
        return gson.fromJson(response, OldGoodTransEntity.class);
    }


    /**
     * 接口名称 个人定制列表--4.1.1
     * 请求类型 get
     * 请求Url  /growthHandbook/personalCustomization/4.1.1
     * 接口描述 个人定制列表-个人定制下部列表
     */
    public PersonalCustomizatioEntity getPersonalCustomizatio() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        initSign(map);

        printRequest("getPersonalCustomizatio====", map);
        String response = HttpsUtils.sendPostRequest(map, "/growthHandbook/personalCustomization/4.1.1");
        printResponse("getPersonalCustomizatio====", response);
        if (response == null) return null;
        return gson.fromJson(response, PersonalCustomizatioEntity.class);
    }


    /**
     * 接口名称 成长手册分类--4.1.1
     * 请求类型 get
     * 请求Url  /growthHandbook/classify/4.1.1
     * 接口描述 成长手册分类 - 个人定制顶部
     */
    public GrowthHandbookEntity getGrowthHandbook() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        initSign(map);

        printRequest("getGrowthHandbook====", map);
        String response = HttpsUtils.sendPostRequest(map, "/growthHandbook/classify/4.1.1");
        printResponse("getGrowthHandbook====", response);
        if (response == null) return null;
        return gson.fromJson(response, GrowthHandbookEntity.class);
    }


    /**
     * 接口名称 分类列表--4.1.1
     * 请求类型 get
     * 请求Url  /growthHandbook/classifyList/4.1.1
     * 接口描述 成长手册分类列表
     */
    public GrowthClassifyListEntity getGrowthClassifyList(String pageIndex, String typeCode) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageIndex", pageIndex);
        map.put("typeCode", typeCode);
        map.put("pageSize", "10");
        initSign(map);

        printRequest("getGrowthClassifyList====", map);
        String response = HttpsUtils.sendPostRequest(map, "/growthHandbook/classifyList/4.1.1");
        printResponse("getGrowthClassifyList====", response);
        if (response == null) return null;
        return gson.fromJson(response, GrowthClassifyListEntity.class);
    }





    /**
     * 接口名称 V4.1.1未读喜报
     * 请求类型 get
     * 请求Url  api/personcenter/haveGoodTransNoRead
     */
    public HaveGoodTransNoReadEntity haveGoodTransNoRead() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        initSign(map);

        printRequest("haveGoodTransNoRead====", map);
        String response = HttpsUtils.sendPostRequest(map, "/personcenter/haveGoodTransNoRead");
        printResponse("haveGoodTransNoRead====", response);
        if (response == null) return null;
        return gson.fromJson(response, HaveGoodTransNoReadEntity.class);
    }

    /**
     * 接口名称 4.1.1 产品打包列表 V4.1.1
     * 请求类型 get
     * 请求Url   /product/selectedProductsList/4.1.1
     * 接口描述 产品打包列表
     */
    public ProductDatasDataEntity getSelectedProducts(String cateId, String pageIndex) throws Exception {

        Map<String, String> map = new HashMap<String, String>();
        map.put("cateId", cateId);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", "10");

        initSign(map);
        printRequest("getSelectedProducts====", map);
        String response = HttpsUtils.sendPostRequest(map, "/product/selectedProductsList/4.1.1");
        printResponse("getSelectedProducts====", response);
        if (response == null) return null;
        return gson.fromJson(response, ProductDatasDataEntity.class);
    }


    /**
     * 首页-精选基金
     */
    public Observable<FundSiftEntiy> fundSiftList() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        initSign(map);
        return RetrofitHelper.getInstance().getRetrofit().create(ThirdPartApi.class).fundSift(map);
    }


    /**
     * 获取绑卡 状态
     */
    public Observable<BundBankcardDataEntity> bindCardStatue() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        initSign(map);
        return RetrofitHelper.getInstance().getRetrofit().create(ThirdPartApi.class).bindCardStatue(map);
    }

    /**
     * 奕丰基金是否注册
     * @return
     */
    public Observable<FundRegisterStatueEntity> fundRegisterStatue(){
        Map<String, String> map = new HashMap<String, String>();
        initSign(map);
        return RetrofitHelper.getInstance().getRetrofit().create(ThirdPartApi.class).fundRegisterStatue(map);
    }

    /**
     * 注册奕丰基金
     * @return
     */
    public Observable<FundAccountEntity> registFund(){
        Map<String, String> map = new HashMap<String, String>();
        initSign(map);
        return RetrofitHelper.getInstance().getRetrofit().create(ThirdPartApi.class).registFund(map);
    }
    


}
