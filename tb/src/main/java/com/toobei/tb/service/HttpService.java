package com.toobei.tb.service;

import android.text.TextUtils;

import com.toobei.common.entity.AccountTypeDatasDataEntity;
import com.toobei.common.entity.BundBankcardDataEntity;
import com.toobei.common.entity.ContactsResponseEntity;
import com.toobei.common.entity.ExistInPlatformEntity;
import com.toobei.common.entity.LoginResponseEntity;
import com.toobei.common.entity.MsgResponseEntity;
import com.toobei.common.entity.OrgProductUrlEntity;
import com.toobei.common.entity.OrgUserCenterUrlEntity;
import com.toobei.common.entity.ProductClassifyStatisticsEntity;
import com.toobei.common.entity.ProductDatasDataEntity;
import com.toobei.common.entity.UserInfoDatasDataEntity;
import com.toobei.common.entity.UserInfoEntity;
import com.toobei.common.network.RetrofitHelper;
import com.toobei.common.network.httpapi.ThirdPartApi;
import com.toobei.common.service.TopHttpService;
import com.toobei.common.utils.HttpsUtils;
import com.toobei.tb.BuildConfig;
import com.toobei.tb.MyApp;
import com.toobei.tb.entity.AccountBalanceEntity;
import com.toobei.tb.entity.AccountDetailPageListEntity;
import com.toobei.tb.entity.AccountManageDatasDataEntity;
import com.toobei.tb.entity.AccountManagerStatisticsEntity;
import com.toobei.tb.entity.ActivityListDatasDataEntity;
import com.toobei.tb.entity.AnswerInfoEntity;
import com.toobei.tb.entity.BuyProductEntity;
import com.toobei.tb.entity.CateProductEntity;
import com.toobei.tb.entity.CurrInvestAmountEntity;
import com.toobei.tb.entity.CustomerListDatasDataEntity;
import com.toobei.tb.entity.DefaultConfigEntity;
import com.toobei.tb.entity.FinancingProductDetailedEntity;
import com.toobei.tb.entity.HomeHotProductDatasDataEntity;
import com.toobei.tb.entity.HomeOrginfoDatasDataEntity;
import com.toobei.tb.entity.HomePagerBulletinEntity;
import com.toobei.tb.entity.IncomeDetailDatasDataEntity;
import com.toobei.tb.entity.IncomeHomeEntity;
import com.toobei.tb.entity.IncomeTypeDatasDataEntity;
import com.toobei.tb.entity.InvestProfitEntity;
import com.toobei.tb.entity.InvestRecordCountsEntity;
import com.toobei.tb.entity.InvestRecordEntity;
import com.toobei.tb.entity.InvestmentRaiseDetailedEntity;
import com.toobei.tb.entity.InviteCfpOutCreateQrEntity;
import com.toobei.tb.entity.InviteCustomerListStatisticsEntity;
import com.toobei.tb.entity.IsBindOrgAcctEntity;
import com.toobei.tb.entity.MineHomeEntity;
import com.toobei.tb.entity.MineSettingEntity;
import com.toobei.tb.entity.MyAssetEntity;
import com.toobei.tb.entity.MyInvestEntity;
import com.toobei.tb.entity.MyInvestFixedStatisticsEntity;
import com.toobei.tb.entity.MyInvestInvestFixedEntity;
import com.toobei.tb.entity.MyLevelDataEntity;
import com.toobei.tb.entity.MyProfitDetailedEntity;
import com.toobei.tb.entity.MyProfitEntity;
import com.toobei.tb.entity.MyQRCodeEntity;
import com.toobei.tb.entity.MycfpDataEntity;
import com.toobei.tb.entity.OrgInfoDatasDataEntity;
import com.toobei.tb.entity.PlatFormDetailEntity;
import com.toobei.tb.entity.PlatFormHeadEntity;
import com.toobei.tb.entity.ProductBuyRecurdsEntity;
import com.toobei.tb.entity.ProductProfitResponseEntity;
import com.toobei.tb.entity.QuestionsListResponseEntity;
import com.toobei.tb.entity.RecommendEntity;
import com.toobei.tb.entity.RedPacketDatasDataEntity;
import com.toobei.tb.entity.RedPacketResponseEntity;
import com.toobei.tb.entity.RedPaperByProductResponseEntity;
import com.toobei.tb.entity.RewardDetailPageListEntity;
import com.toobei.tb.entity.UnRecordInvestEntity;
import com.toobei.tb.entity.UserEntity;
import com.toobei.tb.entity.UserSettingEntity;

import org.xsl781.utils.SystemTool;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * 公司:xiaoniu 类说明：网络服务
 *
 * @date 2015-6-22
 */
public class HttpService extends TopHttpService {

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

    //    @Override
//    public void initParam() {
//        //	app_key = "investor_android";
//        v = "1.0.0";
//        //	app_version = SystemTool.getAppVersion(MyApp.getInstance());
//        app_secret = "120snkktkznlxczandr";// 安卓
//    }
    @Override
    public void initParam() {
        //  app_secret = "6FC103186EA54739B1052C4D463E4030";
        app_secret = MyApp.getInstance().getTophlcNative().getTbAppSecret();
        orgNumber = "App_investor_android";
        appKind = "investor";
        appClient = "android";
        appVersion = SystemTool.getAppVersion(MyApp.getInstance());
        v = "1.0.0";
        timestamp = org.xsl781.utils.TimeUtils.getCurrentDate(org.xsl781.utils.TimeUtils.FORMAT_DATE_TIME1);
    }

    @Override
    public void initSign(Map<String, String> params) {
        super.initSign(params);
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
        return "";
    }

    @Override
    public String getShareUrlEndSuffix() {
        return  "fromApp=toobei&os=Android&v="+SystemTool.getAppVersion(MyApp.getInstance());
    }

    /*
     * 用户登录
     */
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

    public LoginResponseEntity userGesturePwdLogin(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", MyApp.getInstance().getLoginService().token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map, "/user/gesturePwdLogin");
        printResponse("userGesturePwdLogin", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 功能：获取用户信息
     *
     * @return
     * @throws Exception
     */
    public UserInfoEntity userLevelInfo(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/user/getUserInfo");
        printResponse("userInfo", response);
        if (response == null) return null;
        // return FastJsonTools.getEntity(response, LoginResponseEntity.class);
        return gson.fromJson(response, UserInfoEntity.class);
    }

    public UserInfoDatasDataEntity userInfoByEmId(String token, String easemobAcct) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "user.getUserInfoByEasemob");
        map.put("token", token);
        map.put("easemobAcct", easemobAcct);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/user/getUserInfoByEasemob");
        printResponse("userInfoByEmId", response);
        if (response == null) return null;
        // return FastJsonTools.getEntity(response, LoginResponseEntity.class);
        return gson.fromJson(response, UserInfoDatasDataEntity.class);
    }

    /**
     * 功能：
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
        String response = HttpsUtils.sendPostRequest(map, "/user/sendVcode");
        printResponse("sendVcode", response);
        if (response == null) return null;
        // return FastJsonTools.getEntity(response, LoginResponseEntity.class);
        return gson.fromJson(response, LoginResponseEntity.class);
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
    public LoginResponseEntity userRegister(String mobile, String password, String vcode, String deviceToken) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("password", password);
        map.put("mobile", mobile);
        if (vcode != null) {
            map.put("vcode", vcode);
        }
        if (deviceToken != null) {
            map.put("deviceToken", deviceToken);
        }
        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map, "/user/register");
        printResponse("userRegister", response);
        if (response == null) return null;
        return gson.fromJson(response, LoginResponseEntity.class);
    }

    /**
     * 功能：我的理财师
     *
     * @param token
     * @return
     * @throws Exception
     */
    public UserInfoEntity interactMyCfp(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "interact.mycfp");
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/user/mycfp");
        printResponse("interactMyCfp", response);
        if (response == null) return null;
        return gson.fromJson(response, UserInfoEntity.class);
    }

    /**
     * 功能：我的账户-明细类别
     *
     * @param token
     * @return
     * @throws Exception
     */
    public AccountTypeDatasDataEntity accountGetMyAccountTypes(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "account.myaccountDetail.typeList.investor");
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse("accountGetMyAccountTypes", response);
        if (response == null) return null;
        return gson.fromJson(response, AccountTypeDatasDataEntity.class);
    }



//    /**
//     * 功能：首页 Banner内容
//     *
//     * @param type 广告类型0：所有，1：理财师，2：投资客户
//     * @return
//     * @throws Exception
//     */
//    public HomePagerBannersDatasDataEntity homePagerBanners(String type) throws Exception {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("type", type);
//        initSign(map);
//        printRequest(" homePagerBanners===", map);
//        String response = HttpsUtils.sendPostRequest(map, "/homepage/banners");
//        printResponse(" homePagerBanners===", response);
//        if (response == null) return null;
//        return gson.fromJson(response, HomePagerBannersDatasDataEntity.class);
//    }

    /**
     * 功能：活动列表
     *
     * @param token
     * @param activityType 活动类别 0为客户活动，1位理财师活动
     * @param pageIndex
     * @return
     * @throws Exception
     */
    public ActivityListDatasDataEntity activitylist(String token, String activityType, String pageIndex) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "activity.pageList");
        map.put("token", token);
        map.put("activityType", activityType);
        map.put("pageIndex", pageIndex);
        // map.put("pageSize", pageSize); 默认10
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse(" activitylist", response);
        if (response == null) return null;
        return gson.fromJson(response, ActivityListDatasDataEntity.class);
    }

    /**
     * 功能：首页机构列表
     * 接口名称 优质平台-黄亚林-已实现
     * 请求类型 post
     * 请求Url /homepage/highQualityPlatform
     *
     * @param
     * @return
     * @throws Exception
     */
    public HomeOrginfoDatasDataEntity homeOrginfo() throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        initSign(map);
        printRequest("homeOrgInfoDatas====", map);
        String response = HttpsUtils.sendPostRequest(map, "/platfrom/highQualityPlatform");
        printResponse("homeOrgInfoDatas====", response);
        if (response == null) return null;
        return gson.fromJson(response, HomeOrginfoDatasDataEntity.class);
    }


    /**
     * 功能:机构信息及详情 - 黄亚林(已实现)
     * <p/>
     *
     * @param orgNo orgNo	平台编码	string	必需
     * @return
     * @throws Exception
     */
    public PlatFormDetailEntity getPlatFormDetailData(String orgNo) throws Exception {
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
     * 功能：首页 -热门产品
     *
     * @param pageIndex
     * @param pageSize
     * @throws Exception
     */
    public HomeHotProductDatasDataEntity getHomeHotProduct(String pageSize, String pageIndex) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);

        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/product/hotProduct");
        printResponse("homeHotProducts", response);
        if (response == null) return null;
        return gson.fromJson(response, HomeHotProductDatasDataEntity.class);
    }

    /**
     * 功能：我的首页
     *
     * @param token
     * @return
     * @throws Exception
     */
    public MineHomeEntity personcenterHome(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "personcenter.homepage");
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
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
        printResponse("personcenterMineSetting", response);
        if (response == null) return null;
        return gson.fromJson(response, MineSettingEntity.class);
    }

    /**
     * 功能：我的收益
     *
     * @param token
     * @param date  日期 2015-05-06
     * @return
     * @throws Exception
     */
    public IncomeHomeEntity personcenterProfit(String token, String date, String profitTypeId) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "personcenter.profit");
        map.put("token", token);
        if (date != null) map.put("date", date);
        if (profitTypeId != null) map.put("profitTypeId", profitTypeId);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse(" personcenterProfit", response);
        if (response == null) return null;
        return gson.fromJson(response, IncomeHomeEntity.class);
    }

    /**
     * 功能：我的收益-收益类别
     *
     * @param token
     * @return
     * @throws Exception
     */
    public IncomeTypeDatasDataEntity personcenterGetProfitTypes(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "personcenter.profit.types");
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse(" personcenterProfitTypeList", response);
        if (response == null) return null;
        return gson.fromJson(response, IncomeTypeDatasDataEntity.class);
    }

    /**
     * 功能：我的收益-收益明细分页
     *
     * @param token
     * @param profitTypeId 收益类型id(为空表示全部收益)
     * @param pageIndex    第几页 >=1,默认为1
     * @return
     * @throws Exception
     */
    public IncomeDetailDatasDataEntity personcenterProfitDetailList(String token, String date, String profitTypeId, String pageIndex) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "personcenter.profit.pageList");
        map.put("token", token);
        if (date != null) map.put("date", date);
        if (profitTypeId != null) map.put("profitTypeId", profitTypeId);
        if (pageIndex != null) map.put("pageIndex", pageIndex);
        // map.put("pageSize", pageSize); 默认为10
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse(" personcenterProfitDetailList", response);
        if (response == null) return null;
        return gson.fromJson(response, IncomeDetailDatasDataEntity.class);
    }

    /**
     * 功能：我的等级
     *
     * @param token
     * @return
     * @throws Exception
     */
    public MyLevelDataEntity personcenterMyLevel(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "personcenter.level");
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse(" personcenterMyLevel", response);
        if (response == null) return null;
        return gson.fromJson(response, MyLevelDataEntity.class);
    }

    /**
     * 功能：首页的底部的公告数据 //今日收益 // 交易动态等
     *
     * @param token
     * @return
     * @throws Exception
     */
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

//    /**
//     * 功能：首页推荐 产品
//     *
//     * @param token
//     * @return
//     * @throws Exception
//     */
//    @Deprecated
//    public ProductDetailEntity homePagerRecommend(String token) throws Exception {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("method", "homepage.recommend");
//        map.put("token", token);
//        initSign(map);
//        String response = HttpsUtils.sendPostRequest(map);
//        printResponse(" homePagerRecommend", response);
//        if (response == null) return null;
//        return gson.fromJson(response, ProductDetailEntity.class);
//    }
//
//    /**
//     * 功能：产品库-推荐列表
//     *
//     * @param token
//     * @param pageIndex
//     * @return
//     * @throws Exception
//     */
//    public ProductDetailDatasDataEntity productGetRecommendList(String token, String pageIndex) throws Exception {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("method", "lcq.product.rcselected");
//        map.put("token", token);
//        map.put("pageIndex", pageIndex);
//        // map.put("pageSize", pageSize); 默认为10
//        initSign(map);
//        String response = HttpsUtils.sendPostRequest(map);
//        printResponse(" productGetRecommendList", response);
//        if (response == null) return null;
//        return gson.fromJson(response, ProductDetailDatasDataEntity.class);
//    }

    /**
     //     * 功能：产品库-固定浮动收益列表
     //     *
     //     * @param token
     //     * @param fixFlow   利率类别列表 为空时查询全部 1:固定利率，2:浮动利率
     //     * @param pageIndex
     //     * @return
     //     * @throws Exception
     //     */
//    public ProductDetailDatasDataEntity productGetProductList(String token, String fixFlow, String pageIndex) throws Exception {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("method", "lcq.product.pageList");
//        map.put("token", token);
//        if (fixFlow != null) map.put("fixFlow", fixFlow);
//        map.put("pageIndex", pageIndex);
//        map.put("pageSize", "30"); // 默认为30
//        initSign(map);
//        String response = HttpsUtils.sendPostRequest(map);
//        printResponse(" productGetFixFlowList", response);
//        if (response == null) return null;
//        return gson.fromJson(response, ProductDetailDatasDataEntity.class);
//    }

//    /**
//     * 功能：产品库-活期宝 详情
//     *
//     * @param token
//     * @param pageIndex
//     * @return
//     * @throws Exception
//     */
//    public ProductDetailEntity productGetCurrentDetail(String token) throws Exception {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("method", "lcq.product.current");
//        map.put("token", token);
//        initSign(map);
//        String response = HttpsUtils.sendPostRequest(map);
//        printResponse(" productGetCurrentDetail", response);
//        if (response == null) return null;
//        return gson.fromJson(response, ProductDetailEntity.class);
//    }

//    /**
//     * 功能：产品库-固定浮动产品详情
//     *
//     * @param token
//     * @param productId 产品id
//     * @return
//     * @throws Exception
//     */
//    public ProductDetailEntity productGetFixDetail(String token, String productId) throws Exception {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("method", "lcq.product.fixFlowDetail");
//        map.put("token", token);
//        initSign(map);
//        String response = HttpsUtils.sendPostRequest(map);
//        printResponse(" productGetFixDetail", response);
//        if (response == null) return null;
//        return gson.fromJson(response, ProductDetailEntity.class);
//    }

    /**
     * 功能：app获取默认配置
     *
     * @return
     * @throws Exception
     */
    public DefaultConfigEntity clientGetDefaultConfig() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "client.getDefaultConfig");
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/app/default-config");
        printResponse(" clientGetDefaultConfig", response);
        if (response == null) return null;
        return gson.fromJson(response, DefaultConfigEntity.class);
    }

	/*
     * /** _ooOoo_ o8888888o 88" . "88 (| -_- |) O\ = /O ____/`---'\____ .' \\|
	 * |// `. / \\||| : |||// \ / _||||| -:- |||||- \ | | \\\ - /// | |
	 */

	/*
     * =================================================理财模块======================
	 * =====================================
	 */

    /**
     * 功能：理财产品-产品列表
     * <p/>
     * order	顺序	number	0-升序 1-降序
     * pageIndex	第几页 >=1,默认为1	string
     * pageSize	页面条数，默认为10	string
     * sort	排序	number	1-默认排序 2-年化收益 3-产品期限
     * token	登录令牌	string	必须
     */
    public ProductDatasDataEntity getFinancingProduct(String orgCode, String order, String pageIndex, String pageSize, String sort) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        // map.put("token", token);
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
     * 功能：理财产品-更多-产品分类列表
     *
     * @param token
     * @param pageIndex
     * @param pageSize
     * @throws Exception
     */
    public CateProductEntity getCateList(String token, int pageIndex, int pageSize, String cateId) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "cate.financing.product.pageListNew");
        map.put("token", token);
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put("pageSize", String.valueOf(pageSize));
        map.put("cateId", cateId);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse("getCateList", response);
        if (response == null) return null;
        return gson.fromJson(response, CateProductEntity.class);
    }

    /**
     * 功能：理财产品-产品详情
     *
     * @param token
     * @param productId
     * @throws Exception
     */
    public FinancingProductDetailedEntity getFinancingProductDetailed(String token, String productId) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "financing.product.detail");
        map.put("token", token);
        map.put("productId", productId);
        // map.put("pageSize", pageSize); 默认为10
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        // printResponse(" personcenterMyTeamList", response);
        if (response == null) return null;
        return gson.fromJson(response, FinancingProductDetailedEntity.class);
    }

    /**
     * 投筹产品详情(investor.investmentRaise.detail)
     *
     * @param token
     * @param productId
     * @throws Exception
     */
    public InvestmentRaiseDetailedEntity getInvestmentRaiseDetailed(String token, String productId) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "investor.investmentRaise.detail");
        map.put("token", token);
        map.put("productId", productId);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse("getInvestmentRaiseDetailed", response);
        if (response == null) return null;
        return gson.fromJson(response, InvestmentRaiseDetailedEntity.class);
    }

    /**
     * 功能：购买定期产品
     *
     * @param token
     * @param productId
     * @throws Exception
     */
    public BuyProductEntity buyProduct(String token, String productId, String investAmount, String redId, String payPassword) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "lcq.product.fixedPurchase");
        map.put("token", token);
        map.put("productId", productId);
        map.put("investAmount", investAmount);
        map.put("payPassword", payPassword);
        map.put("redId", redId);
        map.put("platfrom", "Android");
        //		map.put("productType", productType);
        //		map.put("purchaseCopies", purchaseCopies);

        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse("buyProduct", response);
        if (response == null) return null;
        return gson.fromJson(response, BuyProductEntity.class);
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
        map.put("method", "financing.product.productBuyRecurdsPageList");
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

	/*
     * =================================================理财模块======================
	 * =====================================
	 */

	/*
     * =================================================首页模块======================
	 * =====================================
	 */

    /**
     * 功能:今日推荐
     *
     * @param token
     */

    public RecommendEntity homeRecommend(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "homepage.recommend");
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse("homeRecommend", response);
        if (response == null) return null;
        return gson.fromJson(response, RecommendEntity.class);
    }


    /**
     * 功能：我的二维码
     *
     * @param token
     * @return
     * @throws Exception
     */
    public MyQRCodeEntity getMyQRCode(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/invitation/customer/homepage");
        printResponse("getMyQRCode", response);
        if (response == null) return null;
        return gson.fromJson(response, MyQRCodeEntity.class);
    }

	/*
     * =================================================用户模块======================
	 * =====================================
	 */

    /**
     * 功能：用户首页
     *
     * @param token
     * @return
     * @throws Exception
     */
    public UserEntity userHome(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
//        map.put("method", "personcenter.investor.homepage");
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/personcenter/homepage");
        printResponse("userHome", response);
        if (response == null) return null;
        return gson.fromJson(response, UserEntity.class);
    }

    /**
     * 功能：个人设置
     *
     * @param token
     * @return
     * @throws Exception
     */
    public UserSettingEntity userSetting(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "personcenter.setting");
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse("userSetting", response);
        if (response == null) return null;
        return gson.fromJson(response, UserSettingEntity.class);
    }

    /**
     * 功能：我的资产
     *
     * @param token
     * @return
     * @throws Exception
     */
    public MyAssetEntity userMyAsset(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "personcenter.getMyAsset");
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse("userSetting", response);
        if (response == null) return null;
        return gson.fromJson(response, MyAssetEntity.class);
    }

    /**
     * 功能：我的收益
     *
     * @param token
     * @return
     * @throws Exception
     */
    public MyProfitEntity userMyProfit(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "personcenter.investor.profit");
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/profit/cfplannerProfitTotal");
        printResponse("userSetting", response);
        if (response == null) return null;
        return gson.fromJson(response, MyProfitEntity.class);
    }

    /**
     * 功能： 我的收益-收益记录明细
     *
     * @param token
     * @return
     * @throws Exception
     */
    public MyProfitDetailedEntity MyInvestorProfitDetailed(String token, String key, int pageIndex, int pageSize) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "personcenter.investor.profit.pageList");
        map.put("token", token);
        map.put("key", key);
        map.put("date", "");
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put("pageSize", String.valueOf(pageSize));

        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse("MyInvestorProfitDetailed", response);
        if (response == null) return null;
        return gson.fromJson(response, MyProfitDetailedEntity.class);
    }

    /**
     * 功能：我的投资
     *
     * @param token
     * @throws Exception
     * @returnproductInvestType
     */
    public MyInvestEntity MyInvestHomepage(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "personcenter.invest.homepageNew");
        map.put("token", token);

        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse("MyInvestHomepage", response);
        if (response == null) return null;
        return gson.fromJson(response, MyInvestEntity.class);
    }

    /**
     * 功能：我的投资-定期投资统计
     *
     * @param token
     * @return
     * @throws Exception
     */
    public MyInvestFixedStatisticsEntity MyInvestFixedStatistics(String token, String productType) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "personcenter.invest.product.statistics");
        map.put("token", token);
        map.put("productType", productType);
        //		map.put("isFlow", "1");// 1。固定收益 2。浮动收益
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse("MyInvestFixedStatistics", response);
        if (response == null) return null;
        return gson.fromJson(response, MyInvestFixedStatisticsEntity.class);
    }

    /**
     * 功能：我的投资-定期投资明细
     *
     * @param token
     * @return
     * @throws Exception
     */
    public MyInvestInvestFixedEntity MyInvestFixedList(String token, String queryType, String productType, int pageIndex, int pageSize) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "personcenter.invest.product.list");
        map.put("token", token);
        map.put("queryType", queryType);
        map.put("productType", productType);
        map.put("pageIndex", String.valueOf(pageIndex));
        map.put("pageSize", String.valueOf(pageSize));
        map.put("isFlow", "1");// 1。固定收益 2。浮动收益

        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse("MyInvestFixedList", response);
        if (response == null) return null;
        return gson.fromJson(response, MyInvestInvestFixedEntity.class);
    }

    /**
     * 功能：查询问卷问题列表
     *
     * @param token
     * @return
     * @throws Exception
     */
    public QuestionsListResponseEntity queryQuestionsList(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "question.queryQuestionsList");
        map.put("token", token);

        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse("queryQuestionsList", response);
        if (response == null) return null;
        return gson.fromJson(response, QuestionsListResponseEntity.class);
    }

    /**
     * 功能：添加问卷调查信息
     *
     * @param token
     * @param questionId:问题id,多个问题id用#分隔
     * @param answerId:答案id,多个答案id用#分隔，同一个问题的多个答案间用,分隔，例如：1,2#3，表示第一个问题答案是多选，有两个，对应答案id为1和2；第二个问题答案id为3
     * @return
     * @throws Exception
     */
    public AnswerInfoEntity addAnswerInfo(String token, String questionId, String answerId) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "question.addAnswerInfo");
        map.put("token", token);
        map.put("questionId", questionId);
        map.put("answerId", answerId);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map);
        printResponse("addAnswerInfo", response);
        if (response == null) return null;
        return gson.fromJson(response, AnswerInfoEntity.class);
    }

	/*
     * =================================================用户模块======================
	 * =====================================
	 */

	/*
     * =================================================邀请模块======================
	 * =====================================
	 */

    /**
     * 功能：通讯录邀请
     *
     * @param token
     * @return
     * @throws Exception
     */
    public ContactsResponseEntity inviteContacts(String token, String mobiles, String names) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "invitation.maillist");
        map.put("token", token);
        map.put("mobiles", mobiles);
        map.put("names", names);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/invitation/maillist");
        printResponse(" inviteContacts", response);
        if (response == null) return null;
        return gson.fromJson(response, ContactsResponseEntity.class);
    }

    /**
     * 功能：我的邀请-统计
     *
     * @param token
     * @return
     * @throws Exception
     */
    public InviteCustomerListStatisticsEntity inviteCustomerListStatistics(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "invitation.investor.statistics");
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/invitation/investor/statistics");
        printResponse(" inviteCustomerListStatistics", response);
        if (response == null) return null;
        return gson.fromJson(response, InviteCustomerListStatisticsEntity.class);
    }

    /**
     * 功能：我的邀请-列表
     *
     * @param token
     * @param pageIndex
     * @return
     * @throws Exception
     */
    public CustomerListDatasDataEntity inviteCustomerListPage(String token, String pageIndex) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "invitation.investor.pageList");
        map.put("token", token);
        map.put("pageIndex", pageIndex);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/invitation/investor/pageList");
        printResponse(" inviteCustomerListPage", response);
        if (response == null) return null;
        return gson.fromJson(response, CustomerListDatasDataEntity.class);
    }

    /**
     * 功能：邀请客户 生成二维码
     *
     * @param token
     * @return
     * @throws Exception
     */
    public InviteCfpOutCreateQrEntity inviteCustomerCreatCode(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "invitation.customer.homepage");
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/invitation/customer/homepage");
        printResponse(" inviteCustomerCreatCode", response);
        if (response == null) return null;
        return gson.fromJson(response, InviteCfpOutCreateQrEntity.class);
    }

	/*
     * =================================================邀请模块======================
	 * =====================================
	 */

//    /**
//     * 功能：理财师推荐产品
//     *
//     * @param token
//     * @return
//     * @throws Exception
//     */
//    public InteractProductEntity interactProduct(String token) throws Exception {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("method", "interact.cfp.product");
//        map.put("token", token);
//        initSign(map);
//        String response = HttpsUtils.sendPostRequest(map);
//        printResponse("interactProduct", response);
//        if (response == null) return null;
//        return gson.fromJson(response, InteractProductEntity.class);
//    }

    /*
     * 保存用户认证信息
     */
    public LoginResponseEntity saveAuthentication(String token, String userName, String identityCard, String userMobile, String bankCardNo, String bankCode, String bankName) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "account.saveAuthentication");
        map.put("token", token);
        map.put("userName", userName);
        map.put("identityCard", identityCard);
        map.put("userMobile", userMobile);
        map.put("bankCardNo", bankCardNo);
        map.put("bankCode", bankCode);
        map.put("bankName", bankName);

        initSign(map);
        String response = HttpsUtils.sendPostRequest(true, map);
        printResponse("saveAuthentication", response);
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
    public LoginResponseEntity setMsgPush(String token, String platformflag, String interactflag) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "msg.setMsgPush");
        map.put("token", token);
        map.put("platformflag", platformflag);
        map.put("interactflag", interactflag);
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
    public MsgResponseEntity queryMsgPushSet(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "msg.queryMsgPushSet");
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse("queryMsgPushSet", response);
        if (response == null) return null;
        return gson.fromJson(response, MsgResponseEntity.class);
    }

    /**
     * 功能：用户所有红包(activity.queryUserAllRedPaper)
     *
     * @param token
     * @return
     * @throws Exception
     */
    public RedPacketResponseEntity queryMyRedPaper(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "activity.queryUserAllRedPaper");
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/redPacket/queryRedPacket");
        printResponse("queryMyRedPaper", response);
        if (response == null) return null;
        return gson.fromJson(response, RedPacketResponseEntity.class);
    }

    /**
     * 功能：用户购买产品可使用红(activity.queryUserRedPaperByProduct)
     *
     * @param token
     * @param productId
     * @param productMoney
     * @return
     * @throws Exception
     */
    public RedPaperByProductResponseEntity queryUserRedPaperByProduct(String token, String productId, String productMoney) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "activity.queryUserRedPaperByProduct");
        map.put("token", token);
        map.put("productId", productId);
        map.put("productMoney", productMoney);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse("queryUserRedPaperByProduct", response);
        if (response == null) return null;
        return gson.fromJson(response, RedPaperByProductResponseEntity.class);
    }


    /**
     * 功能：理财产品-计算收益(query.product.profit)
     *
     * @param token
     * @param productId
     * @param investAmount
     * @return
     * @throws Exception
     */
    public ProductProfitResponseEntity getProductProfit(String token, String productId, String investAmount) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("method", "query.product.profit");
        map.put("token", token);
        map.put("productId", productId);
        map.put("investAmount", investAmount);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map);
        printResponse("getProductProfit", response);
        if (response == null) return null;
        return gson.fromJson(response, ProductProfitResponseEntity.class);
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
     * 功能：产品->机构列表
     * <p/>
     * 接口名称 3.3.2 平台列表 - 黄亚林
     * 请求类型 post
     * 请求Url /platfrom/pageList
     * <p/>
     * pageIndex	    第几页 >=1,默认为1	number
     * pageSize	        页面记录数，默认为10	number
     * productDeadLine	筛选条件:产品期限(可为空)	string	传入格式要求：min_dead_line <30
     * securityLevel	筛选条件:安全评级(可为空)	string	传入格式要求： >=4
     * token	        登录令牌	string
     * yearProfit        筛选条件:年化收益(可为空)	string	  传入格式要求： min_profit >=8 and max_profit <=12
     *
     * @param token
     * @param pageIndex
     * @param pageSize
     * @param productDeadLine
     * @param securityLevel
     * @param yearProfit
     * @throws Exception
     */
    public OrgInfoDatasDataEntity getOrgInfoListDatas(String token, String pageIndex, String pageSize, String productDeadLine, String securityLevel, String yearProfit) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        if (!TextUtils.isEmpty(token)) {
            map.put("token", token);
        }
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        map.put("productDeadLine", productDeadLine);
        map.put("securityLevel", securityLevel);
        map.put("yearProfit", yearProfit);

        initSign(map);
        printRequest("getOrgInfoListDatas====", map);
        String response = HttpsUtils.sendPostRequest(map, "/platfrom/pageList");
        printResponse("getOrgInfoListDatas====", response);
        if (response == null) return null;
        return gson.fromJson(response, OrgInfoDatasDataEntity.class);
    }

    /**
     * 功能：  红包列表
     *
     * @param token
     * @param type      红包类型（1=可派发|2=已派发|3=已过期）
     * @param pageIndex 第几页 >=1,默认为1
     * @param pageSize  页面记录数，默认为10
     * @return
     * @throws Exception
     */
    public RedPacketDatasDataEntity redpaperRedpaperInfo(String token, String type, String pageIndex, String pageSize) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("type", type);
        map.put("pageIndex", pageIndex == null ? "1" : pageIndex);
        map.put("pageSize", pageSize == null ? "10" : pageSize);

        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/redPacket/queryRedPacket");
        printResponse("queryMyRedPaper", response);
        if (response == null) return null;
        return gson.fromJson(response, RedPacketDatasDataEntity.class);
    }

    /**
     * 功能: 获取我的理财师
     *
     * @param token
     * @return
     * @throws Exception
     */
    public MycfpDataEntity mycfpInfo(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/user/mycfp");
        printResponse("queryMyRedPaper", response);
        if (response == null) return null;
        return gson.fromJson(response, MycfpDataEntity.class);
    }

    /**
     * 功能: 获取理财师推荐产品列表
     *
     * @param token
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    public ProductDatasDataEntity myCfpRecommendProduct(String token, String pageIndex, String pageSize) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        initSign(map);
        printRequest("recdProductPageList", map);
        String response = HttpsUtils.sendPostRequest(map, "/product/recdProductPageList");
        printResponse("recdProductPageList", response);
        if (response == null) return null;
        return gson.fromJson(response, ProductDatasDataEntity.class);

    }

    /**
     * 功能: 平台账户管理统计
     * <p/>
     * 接口名称 平台账户管理统计 -钟灵 -已实现
     * 请求类型 get
     * 请求Url /platfrom/accountManager/statistics
     *
     * @throws Exception
     */
    public AccountManagerStatisticsEntity accountManagerStatistics(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        initSign(map);
        printRequest("accountManagerStatistics", map);
        String response = HttpsUtils.sendPostRequest(map, "/platfrom/accountManager/statistics");
        printResponse("accountManagerStatistics", response);
        if (response == null) return null;
        return gson.fromJson(response, AccountManagerStatisticsEntity.class);

    }

    /**
     * 功能: 平台账户管理统计
     * <p/>
     * 接口名称 平台账户管理列表 -钟灵-已实现
     * 请求类型 get
     * 请求Url /platfrom/accountManager/pageList
     * <p/>
     * pageIndex	第几页 >=1,默认为1（非必填）	string
     * pageSize	页面记录数，默认为10（非必填）	string
     * token	登录态（必填）	string
     * type	类型 1已绑定 2未绑定（必填）	string
     *
     * @throws Exception
     */
    public AccountManageDatasDataEntity accountManagerList(String token, String pageIndex, String pageSize, String type) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        map.put("type", type);
        initSign(map);
        printRequest("accountManagerStatistics", map);
        String response = HttpsUtils.sendPostRequest(map, "/platfrom/accountManager/pageList");
        printResponse("accountManagerStatistics", response);
        if (response == null) return null;
        return gson.fromJson(response, AccountManageDatasDataEntity.class);

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
        if (TextUtils.isEmpty(token)) {
            map.put("token", token);
        }
        map.put("cateIdList", cateIdList);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/product/productTypeList/2.0.1");
        printRequest("productClassifyStatistics", map);
        printResponse(" productClassifyStatistics", response);
        if (response == null) return null;
        return gson.fromJson(response, ProductClassifyStatisticsEntity.class);
    }

    /**
     * 接口名称 绑定完成机构产品跳转地址-黄亚林-已完成
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
     * 接口名称 绑定完成机构用户中心跳转地址-黄亚林-已完成
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
     * 接口详情 (id: 266)
     * 接口名称 PC端 T呗2.0-我的账户-我的理财师-推荐平台 -黄亚林
     * 请求类型 post
     * 请求Url /platfrom/queryPlannerRecommendPlatfrom
     *
     * @param token     token
     * @param pageIndex pageIndex
     * @param pageSize  pageSize
     * @return OrgInfoDatasDataEntity
     * @throws Exception
     */
    public OrgInfoDatasDataEntity queryPlannerRecommendPlatfrom(String token, String pageIndex, String pageSize) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        if (!TextUtils.isEmpty(token)) {
            map.put("token", token);
        }
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);

        initSign(map);
        printRequest("queryPlannerRecommendPlatfrom====", map);
        String response = HttpsUtils.sendPostRequest(map, "/platfrom/queryPlannerRecommendPlatfrom");
        printResponse("queryPlannerRecommendPlatfrom====", response);
        if (response == null) return null;
        return gson.fromJson(response, OrgInfoDatasDataEntity.class);
    }


    /**
     * \
     * 接口名称 我的投资-陈衡-已实现
     * 请求类型 get
     * 请求Url investRecord/customer/investRecord
     *
     * @param pageIndex
     * @param pageSize
     * @param status
     * @param token
     * @return
     * @throws Exception
     */
    public InvestRecordEntity getInvestRecord(String token, String pageIndex, String pageSize, String status) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        if (!TextUtils.isEmpty(token)) {
            map.put("token", token);
        }
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        map.put("status", status);

        initSign(map);
        printRequest("getInvestRecord====", map);
        String response = HttpsUtils.sendPostRequest(map, "/investRecord/customer/investRecord");
        printResponse("getInvestRecord====", response);
        if (response == null) return null;
        return gson.fromJson(response, InvestRecordEntity.class);
    }


    /**
     * \
     * 接口名称 我的投资(其他)-陈衡-已实现
     * 请求类型 get
     * 请求Url  investRecord/customer/unRecordInvestList
     *
     * @param pageIndex
     * @param pageSize
     * @param token
     * @return
     * @throws Exception
     */
    public UnRecordInvestEntity getUnRecordInvest(String token, String pageIndex, String pageSize) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        if (!TextUtils.isEmpty(token)) {
            map.put("token", token);
        }
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);

        initSign(map);
        printRequest("getUnRecordInvest====", map);
        String response = HttpsUtils.sendPostRequest(map, "/investRecord/customer/unRecordInvestList");
        printResponse("getUnRecordInvest====", response);
        if (response == null) return null;
        return gson.fromJson(response, UnRecordInvestEntity.class);
    }

    /**
     * \
     * 接口名称 我的投资数量-陈衡-已实现
     * 请求类型 get
     * 请求Url   investRecord/customer/investRecordCounts
     *
     * @param token
     * @return
     * @throws Exception
     */
    public InvestRecordCountsEntity getInvestRecordCounts(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        if (!TextUtils.isEmpty(token)) {
            map.put("token", token);
        }
        initSign(map);
        printRequest("getInvestRecordCounts====", map);
        String response = HttpsUtils.sendPostRequest(map, "/investRecord/customer/investRecordCounts");
        printResponse("getInvestRecordCounts====", response);
        if (response == null) return null;
        return gson.fromJson(response, InvestRecordCountsEntity.class);
    }


    /**
     * 接口名称 账户明细-已实现
     * 请求类型 post
     * 请求Url  account/myaccountDetail/pageList
     *
     * @param token
     * @return
     * @throws Exception
     */
    public AccountDetailPageListEntity getAccountDetailPageList(String token, String pageIndex, String pageSize, String typeValue) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        if (!TextUtils.isEmpty(token)) {
            map.put("token", token);
        }
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        map.put("typeValue", typeValue);

        initSign(map);
        printRequest("getAccountDetailPageList====", map);
        String response = HttpsUtils.sendPostRequest(map, "/account/myaccountDetail/pageList");
        printResponse("getAccountDetailPageList====", response);
        if (response == null) return null;
        return gson.fromJson(response, AccountDetailPageListEntity.class);
    }

    /**
     * \
     * 接口名称 T呗奖励余额
     * 请求类型 get
     * 请求Url    account/getAccountBalance
     *
     * @param token
     * @return
     * @throws Exception
     */
    public AccountBalanceEntity getAccountBalance(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        if (!TextUtils.isEmpty(token)) {
            map.put("token", token);
        }
        initSign(map);
        printRequest("getAccountBalance====", map);
        String response = HttpsUtils.sendPostRequest(map, "/account/getAccountBalance");
        printResponse("getAccountBalance====", response);
        if (response == null) return null;
        return gson.fromJson(response, AccountBalanceEntity.class);
    }

    /**
     * 接口名称 T呗奖励明细
     * 请求类型 get
     * 请求Url account/queryRewardDetail\
     *
     * @param token
     * @param pageIndex
     * @param pageSize
     * @param typeValue 奖励类型(1=奖励收入明细|2=奖励支出明细)
     * @return
     * @throws Exception
     */

    public RewardDetailPageListEntity queryRewardDetailPageList(String token, String pageIndex, String pageSize, String typeValue) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        if (!TextUtils.isEmpty(token)) {
            map.put("token", token);
        }
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        map.put("typeValue", typeValue);

        initSign(map);
        printRequest("queryRewardDetailPageList====", map);
        String response = HttpsUtils.sendPostRequest(map, "/account/queryRewardDetail");
        printResponse("queryRewardDetailPageList====", response);
        if (response == null) return null;
        return gson.fromJson(response, RewardDetailPageListEntity.class);
    }

    /**
     * 接口名称 用户投资收益-陈衡-已实现
     * 请求类型 get
     * 请求Url  investRecord/customer/investProfit
     *
     * @param token
     * @return
     * @throws Exception
     */
    public InvestProfitEntity getinvestProfit(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();

        if (!TextUtils.isEmpty(token)) {
            map.put("token", token);
        }
        initSign(map);
        printRequest("getinvestProfit====", map);
        String response = HttpsUtils.sendPostRequest(map, "/investRecord/customer/investProfit");
        printResponse("getinvestProfit====", response);
        if (response == null) return null;
        return gson.fromJson(response, InvestProfitEntity.class);
    }

    /**
     * 接口名称 我的在投金额-钟灵-已实现
     * 请求类型 get
     * 请求Url  /personcenter/myCurrInvestAmount
     *
     * @param token
     * @return
     * @throws Exception
     */
    public CurrInvestAmountEntity getCurrInvestAmount(String token) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
//        map.put("method", "personcenter.investor.homepage");
        map.put("token", token);
        initSign(map);
        String response = HttpsUtils.sendPostRequest(map, "/personcenter/myCurrInvestAmount");
        printResponse("getCurrInvestAmount", response);
        if (response == null) return null;
        return gson.fromJson(response, CurrInvestAmountEntity.class);
    }

    /**
     * 获取绑卡 状态
     */
    public Observable<BundBankcardDataEntity> bindCardStatue() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        initSign(map);
        return RetrofitHelper.getInstance().getRetrofit().create(ThirdPartApi.class).bindCardStatue(map);
    }
}
