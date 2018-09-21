package com.toobei.common.network.httpapi;

import com.toobei.common.entity.BaseResponseEntity;
import com.toobei.common.entity.BundBankcardDataEntity;
import com.toobei.common.entity.FundAccountEntity;
import com.toobei.common.entity.FundAccountPageEntity;
import com.toobei.common.entity.FundBaseDefinedEntiy;
import com.toobei.common.entity.FundDetialPageEntity;
import com.toobei.common.entity.FundHoldingsStatisticEntiy;
import com.toobei.common.entity.FundHomePageEntity;
import com.toobei.common.entity.FundInvestorHoldingsEntiy;
import com.toobei.common.entity.FundInvestorOrderInfoEntiy;
import com.toobei.common.entity.FundRegisterStatueEntity;
import com.toobei.common.entity.FundSiftEntiy;
import com.toobei.common.entity.GotoInsuranceProductDetailEntiy;
import com.toobei.common.entity.GotoPersonInsureEntiy;
import com.toobei.common.entity.GrowthHandbookEntiy;
import com.toobei.common.entity.InsuranceCategoryEntiy;
import com.toobei.common.entity.InsuranceListEntiy;
import com.toobei.common.entity.InsuranceSiftEntiy;
import com.toobei.common.entity.OrgUrlSkipParameter;
import com.toobei.common.entity.OrgUserCenterUrlData;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 公司: tophlc
 * 类说明: 第三方跳转相关
 *
 * @author yangLin
 * @time 2017/8/16
 */

public interface ThirdPartApi {

    /**
     * 接口名称 首页-精选基金
     */
    @FormUrlEncoded    //这个必须要加，不然会报错
    @POST("funds/ifast/fundSift")
    public Observable<FundSiftEntiy> fundSift(@FieldMap Map<String, String> map);

    /**
     * 获取绑卡 状态
     */
    @FormUrlEncoded
    @POST("account/personcenter/setting")
    public Observable<BundBankcardDataEntity> bindCardStatue(@FieldMap Map<String, String> map);


    /**
     * 奕丰基金是否注册
     */
    @FormUrlEncoded
    @POST("funds/ifast/ifRegister")
    public Observable<FundRegisterStatueEntity> fundRegisterStatue(@FieldMap Map<String, String> map);

    /**
     * 注册基金账户
     */
    @FormUrlEncoded
    @POST("funds/ifast/quickCustomerMigration")
    public Observable<FundAccountEntity> registFund(@FieldMap Map<String, String> map);

    /**
     * 奕丰基金个人资产页跳转
     * token
     */
    @FormUrlEncoded
    @POST("funds/ifast/gotoAccount")
    public Observable<FundAccountPageEntity> fundAccountPage(@FieldMap Map<String, String> map);

    /**
     * 奕丰基金详情页跳转
     * productCode	基金代码	string
     * token
     */
    @FormUrlEncoded
    @POST("funds/ifast/gotoFundDetail")
    public Observable<FundDetialPageEntity> fundDetialPage(@FieldMap Map<String, String> map);


    /**
     * 奕丰基金首页跳转
     * token
     */
    @FormUrlEncoded
    @POST("funds/ifast/gotoIndex")
    public Observable<FundHomePageEntity> fundHomePage(@FieldMap Map<String, String> map);


    /**
     * 基金列表
     * fundCodes	基金代码	string	【非必填】 如果指定基金代码（可多个，基金代码直接以逗号分隔，如fundCodes=482002,219003），则返回指定的基金的信息；如为空值，则返回全部。在【基金信息类API】——【批量获取基金信息】接口有返回给商户
     * fundHouseCode	基金公司代码	string	【非必填】 可以在【基金信息类api】-【find-fund-house-list】获取相关的信息
     * fundType	基金类型搜索	string	【非必填】 支持多搜索，格式：fundType=[MM,BOND...]
     * geographicalSector	地理分类搜索	string	【非必填】 支持多搜索，格式：geographicalSector=[china.onshore,sh.hk.connect...]
     * isBuyEnable	是否可以购买	string	【非必填】 如果isBuyEnable=0，则返回所有可以购买的基金；如果isBuyEnable=1，则返回所有不可以购买的基金；如果为空值，则返回全部基金
     * isMMFund	是否是货币基金	string	【非必填】 如果isMMFund=0，则返回所有货币型基金；如果isMMFund=1，则返回所有非货币型基金；如果为空值，则返回全部基金
     * isQDII	是否是QDII基金	string	【非必填】 如果isQDII=0，则返回所有QDII型基金；如果isQDII=1，则返回所有非QDII型基金；如果为空值，则返回全部基金
     * isRecommended	是否是推荐基金	string	【非必填】 如果isRecommended=0，则返回所有奕丰推荐的基金；如果isRecommended=1，则返回所有非奕丰推荐的基金；如果为空值，则返回全部基金
     * pageIndex	页码	string	【非必填】 默认为1
     * pageSize	每页记录数	string	【非必填】 默认为10
     * period	排序字段	string	【非必填】 earningsPer10000：万份收益，nav：基金净值，month3：三月收益率，year1：1年收益率，sinceLaunch：成立以来收益率
     * shortName	基金简称搜索	string	【非必填】 模糊搜索
     * sort	排序方式	string	【非必填】 DESC:降序，默认；ASC:升序
     * specializeSector	行业分类搜索	string	【非必填】 支持多搜索，格式：specializeSector=[一般,金融...]
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("funds/ifast/batchGetFundInfo")
    public Observable<FundSiftEntiy> fundList(@FieldMap Map<String, String> map);

    /**
     * 基金基础定义
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("funds/ifast/baseDefined")
    public Observable<FundBaseDefinedEntiy> fundBaseDefined(@FieldMap Map<String, String> map);

    /**
     * 基金账户持有总资产
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("funds/ifast/getHoldingsStatistic")
    public Observable<FundHoldingsStatisticEntiy> fundHoldingsStatistic(@FieldMap Map<String, String> map);

    /**
     * 我的-基金明细-持有明细
     * fundCode	基金代码	string	非必需 过滤返回属于该基金代码的持有情况
     * portfolioId	用户的投资组合编码	string	非必需 过滤返回属于该组合编码下的基金持有情况
     * token	登录令牌	string	必需
     */
    @FormUrlEncoded
    @POST("funds/ifast/getInvestorHoldingsNew")
    public Observable<FundInvestorHoldingsEntiy> fundInvestorHoldings(@FieldMap Map<String, String> map);

    /**
     * 我的-基金交易明细
     * fundCodes	基金代码	string	非必需 (可多个，基金代码直接以逗号分隔，如fundCodes=['482002','219003'])(如提供，则返回过滤后的数据)
     * merchantNumber	要查询的订单流水编号	string	非必需 (如提供，则返回过滤后的数据)
     * orderDateEnd	查询结束的下单日期	string	非必需 (如提供，则返回过滤后的数据)(yyyy-MM-dd)
     * orderDateStart	查询起始的下单日期	string	非必需 (如提供，则返回过滤后的数据)(yyyy-MM-dd)
     * pageIndex	页码	number	非必需 默认为1
     * pageSize	每页记录数	number	非必需 默认为10
     * portfolioId	组合编码	string	非必需 如提供，则返回过滤后的数据
     * rspId	定投计划代码	string	非必需 如提供，则返回过滤后的数据
     * token	登录令牌	string	必需
     * transactionStatus	交易状态	string	非必需 可多个，基金代码直接以逗号分隔，如transactionType=['received','completed'])(如提供，则返回过滤后的数据
     * transactionType	交易类型	string	非必需 (可多个，基金代码直接以逗号分隔，如transactionType=['buy','sell'])(如提供，则返回过滤后的数据)
     */
    @FormUrlEncoded
    @POST("funds/ifast/getOrderList")
    public Observable<FundInvestorOrderInfoEntiy> fundInvestorOrderInfo(@FieldMap Map<String, String> map);

    /**
     * 首页保险精选
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("insurance/qixin/insuranceSift")
    public Observable<InsuranceSiftEntiy> insuranceSift(@FieldMap Map<String, String> map);

    /**
     * 首页貅比特课堂资讯
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("growthHandbook/list/4.3.0")
    public Observable<GrowthHandbookEntiy> growthHandbookSift(@FieldMap Map<String, String> map);


    /**
     * caseCode	产品方案代码	string
     * token
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("insurance/qixin/gotoProductDetail")
    public Observable<GotoInsuranceProductDetailEntiy> gotoInsuranceProductDetail(@FieldMap Map<String, String> map);

    /**
     * insuranceCategory 保险分类	string	非必需 默认查询所有分类
     * pageIndex	页码	number	非必需 默认为1
     * pageSize 非必需 默认为10
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("insurance/qixin/insuranceList")
    public Observable<InsuranceListEntiy> insuranceList(@FieldMap Map<String, String> map);

    /**
     * 列表-甄选保险 - 保险列表首页
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("insurance/qixin/insuranceSelect")
    public Observable<InsuranceListEntiy> insuranceSelectList(@FieldMap Map<String, String> map);


    /**
     * 保险种类
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("insurance/qixin/insuranceCategory")
    public Observable<BaseResponseEntity<InsuranceCategoryEntiy>> insuranceCategory(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("insurance/qixin/gotoPersonInsureList")
    public Observable<GotoPersonInsureEntiy> gotoPersonInsure(@FieldMap Map<String, String> map);

    /**
     * 获取个人中心地址和必要参数
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("platfrom/getOrgUserCenterUrl")
    public Observable<BaseResponseEntity<OrgUserCenterUrlData>> getOrgUserCenterUrl(@FieldMap Map<String, String> map);

    /**
     * 接口名称 4.6.0 平台跳转地址参数(非产品)
     * 请求类型 get
     * 请求Url  api/platfrom/getOrgUrlSkipParameter
     * @param map
     * @return
     * orgNo	平台编码	string
     * token	登录令牌	string
     */
    @FormUrlEncoded
    @POST("platfrom/getOrgUrlSkipParameter")
    public Observable<BaseResponseEntity<OrgUrlSkipParameter>> orgUrlSkipParameter(@FieldMap Map<String, String> map);
}
